package ast.figure.components;

import java.util.ArrayList;
import java.util.List;

/// <summary>
/// Represents a concave polygon (which consists of n >= 4 segments)
/// </summary>
public class ConcavePolygon extends Polygon
{
    public ConcavePolygon() { }

    public ConcavePolygon(List<Segment> segs, List<Point> pts, List<Angle> angs)
    {
        super();

        orderedSides = segs;
        points = pts;
        angles = angs;

        //this.FigureSynthesizerConstructor();
    }

    //        public List<Area_Based_Analyses.Atomizer.AtomicRegion> Atomize(List<Point> figurePoints)
    //        {
    //            //
    //            // Clear collinearities : preparation for determining intersection points.
    //            //
    //            List<Segment> extendedSegments = new ArrayList<Segment>();
    //            for (Segment side : orderedSides)
    //            {
    //                side.ClearCollinear();
    //            }
    //
    //            //
    //            // Determine if any side intersects a non-adjacent side.
    //            // If so, track all the intersection points.
    //            //
    //            List<Point> imagPts = new ArrayList<Point>();
    //            for (int s1 = 0; s1 < orderedSides.Count - 1; s1++)
    //            {
    //                // +2 excludes this side and the adjacent side
    //                for (int s2 = s1 + 2; s2 < orderedSides.Count; s2++)
    //                {
    //                    // Avoid intersecting the first with the last.
    //                    if (s1 != 0 || s2 != orderedSides.Count - 1)
    //                    {
    //                        Point intersection = orderedSides[s1].FindIntersection(orderedSides[s2]);
    //
    //                        intersection = Utilities.AcquirePoint(figurePoints, intersection);
    //
    //                        if (intersection != null)
    //                        {
    //                            // The point of interest must be on the perimeter of the polygon.
    //                            if (this.PointLiesOn(intersection) || this.PointLiesInside(intersection))
    //                            {
    //                                orderedSides[s1].addCollinearPoint(intersection);
    //                                orderedSides[s2].addCollinearPoint(intersection);
    //
    //                                // The intersection point may be a vertex; avoid redundant additions.
    //                                if (!Utilities.HasStructurally<Point>(imagPts, intersection))
    //                                {
    //                                    imagPts.add(intersection);
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //
    //            //
    //            // Add the imaginary points to the list of figure points;
    //            // this is needed for consistency among all regions / polygons.
    //            //
    //            Utilities.addUniqueList<Point>(figurePoints, imagPts);
    //
    //            //
    //            // Construct the Planar graph for atomic region identification.
    //            //
    //            Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph graph = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph();
    //
    //            // Add all imaginary points and intersection points
    //            for (Point pt : this.points)
    //            {
    //                graph.addNode(pt);
    //            }
    //
    //            for (Point pt : imagPts)
    //            {
    //                graph.addNode(pt);
    //            }
    //
    //            //
    //            // Cycle through collinearities adding to the graph.
    //            // Ensure that a connection is interior to this polygon.
    //            //
    //            for (Segment side : orderedSides)
    //            {
    //                for (int p = 0; p < side.collinear.Count - 1; p++)
    //                {
    //                    //
    //                    // Find the midpoint of this segment and determine if it is interior to the polygon.
    //                    // If it is, then this is a legitimate connection.
    //                    //
    //                    Segment thisSegment = new Segment(side.collinear[p], side.collinear[p + 1]);
    //                    Point midpoint = thisSegment.Midpoint();
    //                    if (this.PointLiesInOrOn(midpoint))
    //                    {
    //                        graph.addUndirectedEdge(side.collinear[p], side.collinear[p + 1], thisSegment.Length,
    //                                                Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
    //                    }
    //                }
    //            }
    //
    //            //
    //            // Convert the planar graph to atomic regions.
    //            //
    //            Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph copy = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph(graph);
    //            FacetCalculator atomFinder = new FacetCalculator(copy);
    //            List<Primitive> primitives = atomFinder.GetPrimitives();
    //            List<AtomicRegion> atoms = PrimitiveToRegionConverter.Convert(graph, primitives, new ArrayList<Circle>()); // No circles here.
    //
    //            // State ownership
    //            for (AtomicRegion atom : atoms)
    //            {
    //                atom.addOwner(this);
    //                this.addAtomicRegion(atom);
    //            }
    //
    //            return atoms;
    //        }

    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof ConcavePolygon)) return false;
        ConcavePolygon thatPoly = (ConcavePolygon) obj;

        return super.StructurallyEquals(obj);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof ConcavePolygon)) return false;
        ConcavePolygon thatPoly = (ConcavePolygon) obj;

        return super.equals(obj);
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Concave(");
        str.append(super.toString());
        str.append(")");

        return str.toString();
    }
}