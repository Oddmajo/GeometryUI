/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.calculator;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;
import backend.atoms.components.AtomicRegionException;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.atoms.undirectedPlanarGraph.PlanarGraphEdge;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.NotImplementedException;

public class FacetCalculator
{

 // The graph we use as the basis for region identification.
    private PlanarGraph graph;

    // The list of minimal cycles, filaments, and isolated points.
    private ArrayList<Primitive> primitives;
    public ArrayList<Primitive> GetPrimitives() { return primitives; }
    
    /**
     * Local copy of epsilon
     * <p>
     * This is different from the epsilon used elsewhere in the backend.
     * It was calibrated using trial-and-error with the ComplexTriangleGeneratorTest
     * @author Drew Whitmire
     */
    public static final double EPSILON = 0.0000000001;

    /**
     * Locale copy of compare values with local EPSILON to refine facet calculation
     * @param a
     * @param b
     * @return true if values are within EPSILON of each other
     */
    public static boolean CompareValues(double a, double b)
    {
        return Math.abs(a - b) < EPSILON;
    }

    public FacetCalculator(PlanarGraph g)
    {
        // input graph
        graph = g;

        // initialize list of primitives
        primitives = new ArrayList<Primitive>();
        
        // output graph to Logger if debugging
        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            ExceptionHandler.throwException(new AtomicRegionException(graph.toString()));
        }

        //
        // Actual Primitive Calculation
        //
        ExtractPrimitives();
        
        
    }

    //
    // We want our first vector to be downward (-90 degrees std unit circle)
    // (this returns the clockwise-most neighbor)
    // The first neighbor is the opposite of subsequent neighbors in a cycle 
    // using this algorithm.
    //
    private Point GetFirstNeighbor(Point currentPt)
    {
        //System.out.println("Begin get first neighbor"); // this was for debugging
        
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
            if (CompareValues(crossProduct, 0)) angleMeasure = 180;
            
            else if (crossProduct < 0) angleMeasure = 360 - angleMeasure;

            // If there are have the same angle, choose the one farther away (it is due to two connections)
            // So these points are collinear with a segment, but indistinguishable with two arcs.
            if (CompareValues(angleMeasure, currentAngle))
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
            // if angleMeasure is smaller than currentAngle, neighbor becomes the currentNextPoint
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
    // (this returns the counter-clockwise most neighbor)
    //
    private Point GetTightestCounterClockwiseNeighbor(Point prevPt, Point currentPt)
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
                if (CompareValues(crossProduct, 0))
                {
                      // TODO: Dr. Alvin needs to look at this
                      //commenting this out to see if it will work without it - Drew
//                    // Circles create a legitimate situation where we want to walk back in the same 'collinear' path.
//                    if (Point.OppositeVectors(prevCurrVector, currentNeighborVector))
//                    {
//                        ExceptionHandler.throwException(new NotImplementedException());
//                    }
//                    else 
//                    {
//                        angleMeasure = 180;
//                    }
                    angleMeasure = 180;
                }
                
                // if crossProduct < 0, then we want the angle measure going in the opposite direction
                else if (crossProduct < 0) angleMeasure = 360 - angleMeasure;

                // If there are have the same angle, choose the one farther away (it is due to two connections)
                // So these points are collinear with a segment, but indistinguishable with two arcs.
                if (CompareValues(angleMeasure, currentAngle))
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
                // if angleMeasure is smaller than currentAngle, neighbor becomes the currentNextPoint
                else if (angleMeasure < currentAngle)
                {
                    currentAngle = angleMeasure;
                    currentNextPoint = neighbor;
                }
            }
        }

        return currentNextPoint;
    }

    
    /**
     * Main primitive calculator function
     * utilizes GetFirstNeighbor and GetTightestCounterClockwiseNeighbor
     */
    private void ExtractPrimitives()
    {
        //
        // Lexicographically sorted heap of all points in the graph.
        //
        LexicographicPoints heap = new LexicographicPoints();
        
        // add all nodes to the heap
        for (int gIndex = 0; gIndex < graph.count(); gIndex++)
        {
            heap.add(graph.getNodes().get(gIndex).getPoint());
        }

        // if debugging, send the heap to the Logger
        if (Utilities.ATOMIC_REGION_GEN_DEBUG)
        {
            ExceptionHandler.throwException(new AtomicRegionException(heap.toString()));
        }

        //
        // Exhaustively analyze all points in the graph.
        //
        while (!heap.isEmpty())
        {            
            // get lexicographically first point and index
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

        // if it is a filament from a cycle removal
        // this does not count as a primitive and will not be added to the primitive list
        if (graph.isCycleEdge(v0, v1))
        {
            // beginning of filament
            if (graph.getNodes().get(v0Index).nodeDegree() >= 3)
            {
                graph.removeEdge(v0, v1);
                v0 = v1;
                v0Index = graph.indexOf(v0);
                
                // end point of filament
                if (graph.getNodes().get(v0Index).nodeDegree() == 1)
                {
                    v1 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                }
            }

            // while in the filament, remove it
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

            // remove last point if not connected to anything else
            if (graph.getNodes().get(v0Index).nodeDegree() == 0)
            {
                heap.remove(v0);
                graph.removeNode(v0);
            }
        }
        // filament was pre-existing in the graph
        // this counts as a primitive and will be added to the primitive list
        else
        {
            //System.out.println("Not Cycle Edge");
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
                
                // This was missing
                // @author Drew Whitmire
                v0Index = graph.indexOf(v0);
            }
            
            primitive.add(v0);

            if (graph.getNodes().get(v0Index).nodeDegree() == 0)
            {
                heap.remove(v0);
                graph.removeEdge(v0, v1);
                graph.removeNode(v0);
            }
            
            //
            // Add filament to the primitive list
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
    void ExtractPrimitive(Point v0, LexicographicPoints heap)
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
        while (vCurr != null && !vCurr.structurallyEquals(v0) && !visited.contains(vCurr))
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
            // while not root of filament, get the next node
            while (graph.getNodes().get(v0Index).nodeDegree() == 2)
            {
                v0 = graph.getNodes().get(v0Index).getEdges().get(0).getTarget();
                v0Index = graph.indexOf(v0);
            }
            // call ExctractFilament to handle
            ExtractFilament(v0, graph.getNodes().get(v0Index).getEdges().get(0).getTarget(), heap);
        }
        
        //
        // Minimal cycle found.
        //
        else if (vCurr.structurallyEquals(v0))
        {
            //System.out.println("Minimal Cycle");
            MinimalCycle primitive = new MinimalCycle();

            // add to primitive list
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
                if (graph.getNodes().get(v0Index).getEdges().get(0).getTarget().structurallyEquals(v1))
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
