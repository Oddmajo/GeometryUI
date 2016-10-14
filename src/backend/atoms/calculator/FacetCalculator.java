package atoms.calculator;

import java.util.ArrayList;

import ast.figure.components.Point;
import atoms.calculator.lexicographicPoints.LexicographicPoints;
import atoms.components.AtomicRegionException;
import atoms.undirectedPlanarGraph.PlanarGraph;
import atoms.undirectedPlanarGraph.PlanarGraphEdge;
import utilities.ast_helper.Utilities;
import utilities.exception.ExceptionHandler;

public class FacetCalculator
{

 // The graph we use as the basis for region identification.
    private PlanarGraph graph;

    // The list of minimal cycles, filaments, and isolated points.
    private ArrayList<Primitive> primitives;
    public ArrayList<Primitive> GetPrimitives() { return primitives; }

    public FacetCalculator(PlanarGraph g) throws AtomicRegionException
    {
        graph = g;

        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            ExceptionHandler.throwException(new AtomicRegionException(graph.toString()));
        }

        primitives = new ArrayList<Primitive>();

        ExtractPrimitives();
    }

    //
    // We want our first vector to be downward (-90 degrees std unit circle)
    //
    private Point GetFirstNeighbor(Point currentPt)
    {
        Point imaginaryPrevPt = new Point("", currentPt.getX(), currentPt.getY() + 1);
        Point prevCurrVector = Point.MakeVector(imaginaryPrevPt, currentPt);

        // We want the point that creates the smallest angle w.r.t. to the stdVector

        // Information that will change along with the current candidate next point. 
        double currentAngle = 360; // This will be overwritten
        Point currentNextPoint = null;

        // Index of the current point so we can get its neighbors.
        int currentPtIndex = graph.indexOf(currentPt);

        for (PlanarGraphEdge edge : graph.getNodes().get(currentPtIndex).getEdges())
        {
            int neighborIndex = graph.indexOf(edge.getTarget());
            Point neighbor = graph.getNodes().get(neighborIndex).getPoint();

            // Create a vector of the current point with it's neighbor
            Point currentNeighborVector = Point.MakeVector(currentPt, neighbor);

            // Cross product of the two vectors to determine if we have an angle that is < 180 or > 180.
            double crossProduct = Point.CrossProduct(prevCurrVector, currentNeighborVector);

            double angleMeasure = Point.AngleBetween(prevCurrVector, currentNeighborVector);

            // if (GeometryTutorLib.Utilities.GreaterThan(crossProduct, 0)) angleMeasure = angleMeasure;
            if (Utilities.CompareValues(crossProduct, 0)) angleMeasure = 180;
            else if (crossProduct < 0) angleMeasure = 360 - angleMeasure;

            // If there are have the same angle, choose the one farther away (it is due to two connections)
            // So these points are collinear with a segment, but indistinguishable with two arcs.
            if (Utilities.CompareValues(angleMeasure, currentAngle))
            {
                double currentDist = Point.calcDistance(currentPt, currentNextPoint);
                double candDist = Point.calcDistance(currentPt, neighbor);

                // Take the farthest point.
                if (candDist > currentDist)
                {
                    currentAngle = angleMeasure;
                    currentNextPoint = neighbor;
                }
            }
            else if (angleMeasure < currentAngle)
            {
                currentAngle = angleMeasure;
                currentNextPoint = neighbor;
            }
        }

        return currentNextPoint;
    }

    //
    // With respect to the given vector (based on prevPt and currentPt), return the tightest counter-clockwise neighbor.
    //
    private Point GetTightestCounterClockwiseNeighbor(Point prevPt, Point currentPt) throws AtomicRegionException
    {
        Point prevCurrVector = Point.MakeVector(prevPt, currentPt);

        // We want the point that creates the smallest angle w.r.t. to the stdVector

        // Information that will change along with the current candidate next point. 
        double currentAngle = 360; // This will be overwritten
        Point currentNextPoint = null;

        // Index of the current point so we can get its neighbors.
        int prevPtIndex = graph.indexOf(prevPt);
        int currentPtIndex = graph.indexOf(currentPt);

        for (PlanarGraphEdge edge : graph.getNodes().get(currentPtIndex).getEdges())
        {
            int neighborIndex = graph.indexOf(edge.getTarget());

            if (prevPtIndex != neighborIndex)
            {
                Point neighbor = graph.getNodes().get(neighborIndex).getPoint();

                // Create a vector of the current point with it's neighbor
                Point currentNeighborVector = Point.MakeVector(currentPt, neighbor);

                // Cross product of the two vectors to determine if we have an angle that is < 180 or > 180.
                double crossProduct = Point.CrossProduct(prevCurrVector, currentNeighborVector);

                double angleMeasure = Point.AngleBetween(Point.GetOppositeVector(prevCurrVector), currentNeighborVector);

                // if (GeometryTutorLib.Utilities.GreaterThan(crossProduct, 0)) angleMeasure = angleMeasure;
                if (Utilities.CompareValues(crossProduct, 0))
                {
                    // Circles create a legitimate situation where we want to walk back in the same 'collinear' path.
                    if (Point.OppositeVectors(prevCurrVector, currentNeighborVector))
                    {
                        throw new AtomicRegionException("FacetCalculator has collinear points in graph, but a cycle in the edges.");
                    }
                    else 
                    {
                        angleMeasure = 180;
                    }
                }
                else if (crossProduct < 0) angleMeasure = 360 - angleMeasure;

                // If there are have the same angle, choose the one farther away (it is due to two connections)
                // So these points are collinear with a segment, but indistinguishable with two arcs.
                if (Utilities.CompareValues(angleMeasure, currentAngle))
                {
                    double currentDist = Point.calcDistance(currentPt, currentNextPoint);
                    double candDist = Point.calcDistance(currentPt, neighbor);

                    // Take the farthest point.
                    if (candDist > currentDist)
                    {
                        currentAngle = angleMeasure;
                        currentNextPoint = neighbor;
                    }
                }
                if (angleMeasure < currentAngle)
                {
                    currentAngle = angleMeasure;
                    currentNextPoint = neighbor;
                }
            }
        }

        return currentNextPoint;
    }

    private void ExtractPrimitives() throws AtomicRegionException
    {
        //
        // Lexicographically sorted heap of all points in the graph.
        //
        LexicographicPoints heap = new LexicographicPoints();
        for (int gIndex = 0; gIndex < graph.count(); gIndex++)
        {
            heap.add(graph.getNodes().get(gIndex).getPoint());
        }

        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            ExceptionHandler.throwException(new AtomicRegionException(heap.toString()));
        }

        //
        // Exhaustively analyze all points in the graph.
        //
        while (!heap.isEmpty())
        {
            Point v0 = heap.peekMin();
            int v0Index = graph.indexOf(v0);

            switch(graph.getNodes().get(v0Index).nodeDegree())
            {
                case 0:
                    // Isolated point
                    ExtractIsolatedPoint(v0, heap);
                    break;

                case 1:
                    // Filament: start at this node and indicate the next point is its only neighbor
                    ExtractFilament(v0, graph.getNodes().get(v0Index).getEdges().get(0).getTarget(), heap);
                    break;

                default:
                    // filament or minimal cycle
                    ExtractPrimitive(v0, heap);
                    break;
            }
        }
    }

    //
    // Remove the isolated point from the graph and heap; add to list of primitives.
    //
    void ExtractIsolatedPoint (Point v0, LexicographicPoints heap)
    {
        heap.remove(v0);

        graph.removeNode(v0);

        primitives.add(new IsolatedPoint(v0));

        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            ExceptionHandler.throwException(new AtomicRegionException(primitives.get(primitives.size() - 1).toString()));
        }
    }

    void ExtractFilament (Point v0, Point v1, LexicographicPoints heap)
    {
        int v0Index = graph.indexOf(v0);

        if (graph.isCycleEdge(v0, v1))
        {
            if (graph.getNodes().get(v0Index).nodeDegree() >= 3)
            {
                graph.removeEdge(v0, v1);
                v0 = v1;
                v0Index = graph.indexOf(v0);
                if (graph.getNodes().get(v0Index).nodeDegree() == 1)
                {
                    v1 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                }
            }

            while (graph.getNodes().get(v0Index).nodeDegree() == 1)
            {
                v1 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                
                if (graph.isCycleEdge(v0, v1))
                {
                    heap.remove(v0);
                    graph.removeEdge(v0, v1);
                    graph.removeNode(v0);
                    v0 = v1;
                    v0Index = graph.indexOf(v0);
                }
                else
                {
                    break;
                }
            }

            if (graph.getNodes().get(v0Index).nodeDegree() == 0)
            {
                heap.remove(v0);
                graph.removeNode(v0);
            }
        }
        else
        {
            Filament primitive = new Filament();

            if (graph.getNodes().get(v0Index).nodeDegree() >= 3)
            {
                primitive.add(v0);
                graph.removeEdge(v0,v1);
                v0 = v1;

                v0Index = graph.indexOf(v0);
                if (graph.getNodes().get(v0Index).nodeDegree() == 1)
                {
                    v1 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                }
            }

            while (graph.getNodes().get(v0Index).nodeDegree() == 1)
            {
                primitive.add(v0);
                v1 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                heap.remove(v0);
                graph.removeEdge(v0, v1);
                graph.removeNode(v0);
                v0 = v1;
            }

            primitive.add(v0);

            if (graph.getNodes().get(v0Index).nodeDegree() == 0)
            {
                heap.remove(v0);
                graph.removeEdge(v0, v1);
                graph.removeNode(v0);
            }
            
            primitives.add(primitive);

            if (Utilities.ATOMIC_REGION_GEN_DEBUG)
            {
                ExceptionHandler.throwException(new AtomicRegionException(primitive.toString()));
            }
        }
    }

    //
    // Extract a minimal cycle or a filament
    //
    void ExtractPrimitive(Point v0, LexicographicPoints heap) throws AtomicRegionException
    {
        ArrayList<Point> visited = new ArrayList<Point>();
        ArrayList<Point> sequence = new ArrayList<Point>();

        sequence.add(v0);

        // Create an initial line as (downward) vertical w.r.t. v0; v1 is based on the vertical line through v0
        Point v1 = GetFirstNeighbor(v0); //  GetClockwiseMost(new Point("", v0.X, v0.Y + 1), v0);
        Point vPrev = v0;
        Point vCurr = v1;

        int v0Index = graph.indexOf(v0);
        int v1Index = graph.indexOf(v1);

        // Loop until we have a cycle or we have a null (filament)
        while (vCurr != null && !vCurr.Equals(v0) && !visited.contains(vCurr))
        {
            sequence.add(vCurr);
            visited.add(vCurr);
            Point vNext = GetTightestCounterClockwiseNeighbor(vPrev, vCurr);
            vPrev = vCurr;
            vCurr = vNext;
        }

        //
        // Filament: hit an endpoint
        //
        if (vCurr == null)
        {
            // Filament found, not necessarily rooted at v0.
            ExtractFilament(v0, graph.getNodes().get(v0Index).getEdges().get(0).getTarget(), heap);
        }
        //
        // Minimal cycle found.
        //
        else if (vCurr.Equals(v0))
        {
            MinimalCycle primitive = new MinimalCycle();

            primitive.AddAll(sequence);

            primitives.add(primitive);

            if (Utilities.ATOMIC_REGION_GEN_DEBUG)
            {
                ExceptionHandler.throwException(new AtomicRegionException(primitive.toString()));
            }

            // Mark that these edges are a part of a cycle
            for (int p = 0; p < sequence.size(); p++)
            {
                graph.markCycleEdge(sequence.get(p), sequence.get(p+1 < sequence.size() ? p+1 : 0));
            }

            graph.removeEdge(v0, v1);

            //
            // Check filaments for v0 and v1
            //
            if (graph.getNodes().get(v0Index).nodeDegree() == 1)
            {
                // Remove the filament rooted at v0.
                ExtractFilament(v0, graph.getNodes().get(v0Index).getEdges().get(0).getTarget(), heap);
            }

            //
            // indices may have changed; update.
            //
            v1Index = graph.indexOf(v1);
            if (v1Index != -1)
            {
                if (graph.getNodes().get(v1Index).nodeDegree() == 1)
                {
                    // Remove the filament rooted at v1.
                    ExtractFilament(v1, graph.getNodes().get(v1Index).getEdges().get(0).getTarget(), heap);
                }
            }
        }
        //
        // vCurr was visited earlier
        //
        else
        {
            // A cycle has been found, but is not guaranteed to be a minimal
            // cycle. This implies v0 is part of a filament. Locate the
            // starting point for the filament by traversing from v0 away
            // from the initial v1.
            while (graph.getNodes().get(v0Index).nodeDegree() == 2)
            {
                // Choose between the the two neighbors
                if (graph.getNodes().get(v0Index).getEdges().get(0).getTarget().Equals(v1))
                {
                    v1 = v0;
                    v0 = graph.getNodes().get(v0Index).getEdges().get(1).getTarget();
                }
                else
                {
                    v1 = v0;
                    v0 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                }

                // Find the next v0 index
                v0Index = graph.indexOf(v0);
            }
            ExtractFilament(v0, v1, heap);
        }
    }

}
