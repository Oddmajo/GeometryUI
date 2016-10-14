package atoms.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import ast.ASTException;
import ast.figure.components.Arc;
import ast.figure.components.Circle;
import ast.figure.components.MajorArc;
import ast.figure.components.MinorArc;
import ast.figure.components.Point;
import ast.figure.components.Polygon;
import ast.figure.components.Sector;
import ast.figure.components.Segment;
import ast.figure.components.Semicircle;
import atoms.undirectedPlanarGraph.PlanarGraph;
import atoms.undirectedPlanarGraph.PlanarGraphEdge;
import utilities.ast_helper.Utilities;
import utilities.exception.ArgumentException;
import utilities.translation.OutSingle;
import atoms.components.AtomicRegion;
import atoms.components.Connection;
import atoms.components.Connection.ConnectionType;
import atoms.components.ShapeAtomicRegion;
import atoms.undirectedPlanarGraph.EdgeType;

public class MinimalCycle extends Primitive
{

 // These points were ordered by the minimal basis algorithm; calculates facets.
    public ArrayList<Point> points;

    public MinimalCycle()
    {
        points = new ArrayList<Point>();
    }

    public void Add(Point pt)
    {
        points.add(pt);
    }

    public void AddAll(ArrayList<Point> pts)
    {
        points.addAll(pts);
    }

    public boolean HasExtendedSegment(PlanarGraph graph)
    {
        return GetExtendedSegment(graph) != null;
    }

    public Segment GetExtendedSegment(PlanarGraph graph)
    {
        for (int p = 0; p < points.size(); p++)
        {
            if (graph.getEdgeType(points.get(p), points.get((p + 1) % points.size())) == EdgeType.EXTENDED_SEGMENT)
            {
                return new Segment(points.get(p), points.get(p + 1 < points.size() ? p + 1 : 0));
            }
        }

        return null;
    }

    public boolean HasThisExtendedSegment(PlanarGraph graph, Segment segment)
    {
        if (!points.contains(segment.getPoint1())) return false;
        if (!points.contains(segment.getPoint2())) return false;

        return graph.getEdgeType(segment.getPoint1(), segment.getPoint2()) == EdgeType.EXTENDED_SEGMENT;
    }

    private ArrayList<Point> GetPointsBookEndedBySegment(Segment segment)
    {
        int index1 = points.indexOf(segment.getPoint1());
        int index2 = points.indexOf(segment.getPoint2());

        // Are the points book-ended properly already?
        if (index1 == 0 && index2 == points.size() - 1) return new ArrayList<Point>(points);

        // The set to be returned.
        ArrayList<Point> ordered = new ArrayList<Point>();

        // Are the points book-ended in reverse?
        if (index1 == points.size() - 1 && index2 == 0)
        {
            for (int p = points.size() - 1; p >= 0; p--)
            {
                ordered.add(points.get(p));
            }

            return ordered;
        }

        // The order is the same as specified by the segment; just cycle the points.
        if (index1 < index2)
        {
            for (int p = 0; p < points.size(); p++)
            {
                @SuppressWarnings("unused")
                int tempIndex = (index1 - p) < 0 ? points.size() + (index1 - p) : (index1 - p);
                ordered.add(points.get((index1 - p) < 0 ? points.size() + (index1 - p) : (index1 - p)));
            }
        }
        // The order is NOT the same as specified by the segment (it's reversed).
        else
        {
            for (int p = 0; p < points.size(); p++)
            {
                @SuppressWarnings("unused")
                int tempIndex = (index1 + p) % points.size();
                ordered.add(points.get((index1 + p) % points.size()));
            }
        }

        return ordered;
    }

    public MinimalCycle Compose(MinimalCycle thatCycle, Segment extended)
    {
        MinimalCycle composed = new MinimalCycle();

        ArrayList<Point> thisPts = this.GetPointsBookEndedBySegment(extended);
        ArrayList<Point> thatPts = thatCycle.GetPointsBookEndedBySegment(extended);

        // Add all points from this;
        composed.AddAll(thisPts);

        // Add all points from that (excluding endpoints)
        for (int p = thatPts.size() - 2; p > 0; p--)
        {
            composed.Add(thatPts.get(p));
        }

        return composed;
    }

    public String ToString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Cycle { ");
        for (int p = 0; p < points.size(); p++)
        {
            str.append(points.get(p).toString());
            if (p < points.size() - 1) str.append(", ");
        }
        str.append(" }");

        return str.toString();
    }

    //
    //
    // Create the actual set of atomic regions for this cycle.
    //
    //   We need to check to see if any of the cycle segments are based on arcs.
    //   We have to handle the degree of each segment: do many circles intersect at these points?
    //
    public ArrayList<AtomicRegion> ConstructAtomicRegions(ArrayList<Circle> circles, PlanarGraph graph) throws Exception
    {
        ArrayList<AtomicRegion> regions = new ArrayList<>();

        AtomicRegion region = null;

        //
        // Check for a direct polygon (no arcs).
        //
        region = PolygonDefinesRegion(graph);
        if (region != null)
        {
            regions.add(region);
            return regions;
        }

        //
        // Does this region define a sector? 
        //
        ArrayList<AtomicRegion> sectors = SectorOrTruncationDefinesRegion(circles, graph);
        if (sectors != null && !sectors.isEmpty())
        {
            regions.addAll(sectors);
            return regions;
        }

        //
        // Do we have a set of regions defined by a polygon in which circle(s) cut out some of that region? 
        //
        regions.addAll(MixedArcChordedRegion(circles, graph));

        return regions;
    }

    private AtomicRegion PolygonDefinesRegion(PlanarGraph graph) throws ArgumentException
    {
        ArrayList<Segment> sides = new ArrayList<Segment>();

        //
        // All connections between adjacent connections MUST be segments.
        //
        for (int p = 0; p < points.size(); p++)
        {
            Segment segment = new Segment(points.get(p), points.get((p + 1) % points.size()));

            sides.add(segment);

            if (graph.getEdge(points.get(p), points.get((p + 1) % points.size())).edgeType != EdgeType.REAL_SEGMENT) return null;
        }

        //
        // All iterative connections cannot be arcs.
        //
        for (int p1 = 0; p1 < points.size() - 1; p1++)
        {
            // We want to check for a direct cycle, therefore, p2 starts at p1 not p1 + 1
            for (int p2 = p1; p2 < points.size(); p2++)
            {
                PlanarGraphEdge edge = graph.getEdge(points.get(p1), points.get((p2 + 1) % points.size()));

                if (edge != null)
                {
                    if (edge.edgeType == EdgeType.REAL_ARC) return null;
                }
            }
        }

        //
        // Make the Polygon
        //
        Polygon poly = Polygon.MakePolygon(sides);

        if (poly == null) throw new ArgumentException("Real segments should define a polygon; they did not.");

        return new ShapeAtomicRegion(poly);
    }

    private ArrayList<AtomicRegion> SectorOrTruncationDefinesRegion(ArrayList<Circle> circles, PlanarGraph graph) throws Exception
    {
        //
        // Do there exist any real-dual edges or extended segments? If so, this is not a sector.
        //
        for (int p = 0; p < points.size(); p++)
        {
            PlanarGraphEdge edge = graph.getEdge(points.get(p), points.get((p + 1) % points.size()));

            if (edge.edgeType == EdgeType.EXTENDED_SEGMENT) return null;
            else if (edge.edgeType == EdgeType.REAL_DUAL) return null;
        }

        //
        // Collect all segments; split into two collinear lists.
        //
        ArrayList<Segment> segments = CollectSegments(graph);
        ArrayList<ArrayList<Segment>> collinearSegmentSet = SplitSegmentsIntoCollinearSequences(segments);

        // A sector requires one (semicircl) or two sets of segments ('normal' arc).
        if (collinearSegmentSet.size() > 2) return null;

        //
        // Collect all arcs.
        //
        ArrayList<MinorArc> arcs = CollectStrictArcs(circles, graph);
        ArrayList<ArrayList<MinorArc>> collinearArcSet = SplitArcsIntoCollinearSequences(arcs);

        // A sector requires one set of arcs (no more, no less).
        if (collinearArcSet.size() != 1) return null;

        // Semicircle has one set of sides
        if (collinearSegmentSet.size() == 1) return ConvertToTruncationOrSemicircle(collinearSegmentSet.get(0), collinearArcSet.get(0));

        // Pacman shape created with a circle results in Sector
        return ConvertToGeneralSector(collinearSegmentSet.get(0), collinearSegmentSet.get(1), collinearArcSet.get(0));
    }

    //
    // Collect all segments attributed to this this cycle
    //
    private ArrayList<Segment> CollectSegments(PlanarGraph graph)
    {
        ArrayList<Segment> segments = new ArrayList<>();

        for (int p = 0; p < points.size(); p++)
        {
            PlanarGraphEdge edge = graph.getEdge(points.get(p), points.get((p + 1) % points.size()));

            if (edge.edgeType == EdgeType.REAL_SEGMENT)
            {
                segments.add(new Segment(points.get(p), points.get((p + 1) % points.size())));
            }
        }

        return segments;
    }

    //
    // Split the segments into sets of collinear segments.
    // NOTE: This code assumes an input ordering of segments and returns sets of ordered collinear segments.
    //
    private ArrayList<ArrayList<Segment>> SplitSegmentsIntoCollinearSequences(ArrayList<Segment> segments)
    {
        ArrayList<ArrayList<Segment>> collinearSet = new ArrayList<ArrayList<Segment>>();

        for (Segment segment : segments)
        {
            boolean collinearFound = false;
            for (ArrayList<Segment> collinear : collinearSet)
            {
                // Find the set of collinear segments
                if (segment.IsCollinearWith(collinear.get(0)))
                {
                    collinearFound = true;
                    int i = 0;
                    for (i = 0; i < collinear.size(); i++)
                    {
                        if (segment.getPoint2().StructurallyEquals(collinear.get(i).getPoint1())) break;
                    }
                    collinear.add(i, segment);
                }
            }

            if (!collinearFound) collinearSet.add(Utilities.MakeList(segment));
        }

        return collinearSet;
    }

    //
    // Collect all arcs attributed to this this cycle; 
    //
    private ArrayList<MinorArc> CollectStrictArcs(ArrayList<Circle> circles, PlanarGraph graph)
    {
        ArrayList<MinorArc> minors = new ArrayList<MinorArc>();

        for (int p = 0; p < points.size(); p++)
        {
            PlanarGraphEdge edge = graph.getEdge(points.get(p), points.get((p + 1) % points.size()));

            if (edge.edgeType == EdgeType.REAL_ARC)
            {
                // Find the applicable circle.
                Circle theCircle = null;
                for (Circle circle : circles)
                {
                    if (circle.HasArc(points.get(p), points.get((p + 1) % points.size())))
                    {
                        theCircle = circle;
                        break;
                    }
                }

                minors.add(new MinorArc(theCircle, points.get(p), points.get((p + 1) % points.size())));
            }
        }

        return minors;
    }

    //
    // Split the segments into sets of collinear segments.
    // NOTE: This code assumes an input ordering of segments and returns sets of ordered collinear segments.
    //
    private ArrayList<ArrayList<MinorArc>> SplitArcsIntoCollinearSequences(ArrayList<MinorArc> minors) throws Exception
    {
        ArrayList<ArrayList<MinorArc>> collinearSet = new ArrayList<ArrayList<MinorArc>>();

        //
        // Collect all the related arcs
        //
        for (MinorArc minor : minors)
        {
            boolean collinearFound = false;
            for (ArrayList<MinorArc> collinear : collinearSet)
            {
                // Do the arcs belong to the same circle?
                if (minor.getCircle().StructurallyEquals(collinear.get(0).getCircle()))
                {
                    collinearFound = true;
                    int i = 0;
                    for (i = 0; i < collinear.size(); i++)
                    {
                        if (minor.getEndpoint2().StructurallyEquals(collinear.get(i).getEndpoint1())) break;
                    }
                    collinear.add(i, minor);
                }
            }

            if (!collinearFound) collinearSet.add(Utilities.MakeList(minor));
        }

        //
        // Sort each arc set.
        //
        for (int arcSetIndex = 0; arcSetIndex < collinearSet.size(); arcSetIndex++)
        {
            //collinearSet[arcSetIndex] = SortArcSet(collinearSet[arcSetIndex]);
            // This should be checked to make sure it's doing what the above line does in C#
            // Drew Whitmire
            collinearSet.get(arcSetIndex).clear();
            collinearSet.get(arcSetIndex).addAll(SortArcSet(collinearSet.get(arcSetIndex)));
        }

        return collinearSet;
    }

    //
    // Order the arcs so the endpoints are clear in the first in last positions.
    //
    private ArrayList<MinorArc> SortArcSet(ArrayList<MinorArc> arcs) throws Exception
    {
        if (arcs.size() <= 2) return arcs;

        boolean[] marked = new boolean[arcs.size()];
        ArrayList<MinorArc> sorted = new ArrayList<MinorArc>();

        //
        // Find the 'first' endpoint of the arc.
        //
        int sharedCount = 0;
        int arcIndex = -1;
        for (int a1 = 0; a1 < arcs.size(); a1++)
        {
            sharedCount = 0;
            for (int a2 = 0; a2 < arcs.size(); a2++)
            {
                if (a1 != a2)
                {
                    if (arcs.get(a1).SharedEndpoint(arcs.get(a2)) != null) sharedCount++;
                }
            }
            arcIndex = a1;
            if (sharedCount == 1) break;
        }

        // An 'end'-arc found; book-ends of list.
        switch(sharedCount)
        {
            case 0:
                throw new Exception("Expected a shared count of 1 or 2, not 0");
            case 1:
                sorted.add(arcs.get(arcIndex));
                marked[arcIndex] = true;
                break;
            case 2:
                // Middle arc
                break;
            default:
                throw new Exception("Expected a shared count of 1 or 2, not (" + sharedCount + ")");
        }

        MinorArc working = sorted.get(0);
        while (Arrays.asList(marked).contains(false)) 
        {
            Point shared;
            for (arcIndex = 0; arcIndex < arcs.size(); arcIndex++)
            {
                if (!marked[arcIndex])
                {
                    shared = working.SharedEndpoint(arcs.get(arcIndex));
                    if (shared != null) break;
                }
            }
            marked[arcIndex] = true;
            sorted.add(arcs.get(arcIndex));
            working = arcs.get(arcIndex);
        }

        return sorted;
    }

    private ArrayList<AtomicRegion> ConvertToGeneralSector(ArrayList<Segment> sideSet1, ArrayList<Segment> sideSet2, ArrayList<MinorArc> arcs) throws Exception
    {
        Segment side1 = ComposeSegmentsIntoSegment(sideSet1);
        Segment side2 = ComposeSegmentsIntoSegment(sideSet2);
        Arc theArc = ComposeArcsIntoArc(arcs);

        //
        // Verify that both sides of the sector contains the center.
        // And many other tests to ensure proper sector acquisition.
        //
        if (!side1.hasPoint(theArc.getCircle().getCenter())) return null;
        if (!side2.hasPoint(theArc.getCircle().getCenter())) return null;

        Point sharedCenter = side1.SharedVertex(side2);
        if (sharedCenter == null)
        {
            throw new Exception("Sides do not share a vertex as expected; they share " + sharedCenter);
        }

        if (!sharedCenter.StructurallyEquals(theArc.getCircle().getCenter()))
        {
            throw new Exception("Center and deduced center do not equate: " + sharedCenter + " " + theArc.getCircle().getCenter());
        }

        Point segEndpoint1 = side1.OtherPoint(sharedCenter);
        Point segEndpoint2 = side2.OtherPoint(sharedCenter);

        if (!theArc.HasEndpoint(segEndpoint1) || !theArc.HasEndpoint(segEndpoint2))
        {
            throw new Exception("Side endpoints do not equate to the arc endpoints");
        }

        // Satisfied constraints, create the actual sector.
        Sector sector = new Sector(theArc);

        return Utilities.MakeList(new ShapeAtomicRegion(sector));
    }

    private ArrayList<AtomicRegion> ConvertToTruncationOrSemicircle(ArrayList<Segment> sideSet, ArrayList<MinorArc> arcs) throws Exception
    {
        Segment side = ComposeSegmentsIntoSegment(sideSet);
        Arc theArc = ComposeArcsIntoArc(arcs);

        // Verification Step 1.
        if (!theArc.HasEndpoint(side.getPoint1()) || !theArc.HasEndpoint(side.getPoint2()))
        {
            throw new Exception("Semicircle / Truncation: Side endpoints do not equate to the arc endpoints");
        }

        if (theArc != null && theArc instanceof Semicircle) 
            {
                Semicircle theSemiCircle = (Semicircle) theArc;
                return ConvertToSemicircle(side, theSemiCircle);
            }
        if (theArc != null && theArc instanceof MinorArc)
        {
            MinorArc theMinorArc = (MinorArc) theArc;
            return ConvertToTruncation(side, theMinorArc);
        }
        else
        {
            return null;
        }
    }

    private ArrayList<AtomicRegion> ConvertToTruncation(Segment chord, MinorArc arc)
    {
        AtomicRegion atom = new AtomicRegion();

        atom.AddConnection(new Connection(chord.getPoint1(), chord.getPoint2(), ConnectionType.SEGMENT, chord));

        atom.AddConnection(new Connection(chord.getPoint1(), chord.getPoint2(), ConnectionType.ARC, arc));

        return Utilities.MakeList(atom);
    }

    private ArrayList<AtomicRegion> ConvertToSemicircle(Segment diameter, Semicircle semi) throws Exception
    {
        // Verification Step 2.
        if (!diameter.pointLiesOnAndExactlyBetweenEndpoints(semi.getCircle().getCenter()))
        {
            throw new Exception("Semicircle: expected center between endpoints.");
        }

        Sector sector = new Sector(semi);

        return Utilities.MakeList(new ShapeAtomicRegion(sector));
    }

    private Segment ComposeSegmentsIntoSegment(ArrayList<Segment> segments)
    {
        return new Segment(segments.get(0).getPoint1(), segments.get(segments.size() - 1).getPoint2());
    }

    private Arc ComposeArcsIntoArc(ArrayList<MinorArc> minors) throws ASTException
    {
        // if (minors.Count == 1) return minors[0];

        // Determine what type of arc to create.
        double arcMeasure = 0;
        for (MinorArc minor : minors)
        {
            arcMeasure += minor.getMinorMeasure();
        }

        //
        // Create the arc
        //

        // Determine the proper endpoints.
        Point endpt1 = minors.get(0).OtherEndpoint(minors.get(0).SharedEndpoint(minors.get(1)));
        Point endpt2 = minors.get(minors.size()-1).OtherEndpoint(minors.get(minors.size()-1).SharedEndpoint(minors.get(minors.size()-2)));

        // Create the proper arc.
        Circle theCircle = minors.get(0).getCircle();

        if (Utilities.CompareValues(arcMeasure, 180))
        {
            Segment diameter = new Segment(endpt1, endpt2);

            // Get the midpoint that is on the same side.
            Point midpt = theCircle.Midpoint(diameter.getPoint1(), diameter.getPoint2(), minors.get(0).getEndpoint2());
            return new Semicircle(minors.get(0).getCircle(), diameter.getPoint1(), diameter.getPoint2(), midpt, diameter);
        }
        else if (arcMeasure < 180) return new MinorArc(theCircle, endpt1, endpt2);
        else if (arcMeasure > 180) return new MajorArc(theCircle, endpt1, endpt2);

        return null;
    }

    private ArrayList<Circle> GetAllApplicableCircles(ArrayList<Circle> circles, Point pt1, Point pt2)
    {
        ArrayList<Circle> applicCircs = new ArrayList<Circle>();

        for (Circle circle : circles)
        {
            if (circle.PointLiesOn(pt1) && circle.PointLiesOn(pt2))
            {
                applicCircs.add(circle);
            }
        }

        return applicCircs;
    }

    private ArrayList<AtomicRegion> MixedArcChordedRegion(ArrayList<Circle> thatCircles, PlanarGraph graph)
    {
        ArrayList<AtomicRegion> regions = new ArrayList<AtomicRegion>();

        // Every segment may be have a set of circles. (on each side) surrounding it.
        // Keep parallel lists of: (1) segments, (2) (real) arcs, (3) left outer circles, and (4) right outer circles
        Segment[] regionsSegments = new Segment[points.size()];
        Arc[] arcSegments = new Arc[points.size()];
        Circle[] leftOuterCircles = new Circle[points.size()];
        Circle[] rightOuterCircles = new Circle[points.size()];

        //
        // Populate the parallel arrays.
        //
        int currCounter = 0;
        for (int p = 0; p < points.size(); )
        {
            PlanarGraphEdge edge = graph.getEdge(points.get(p), points.get((p + 1) % points.size()));
            Segment currSegment = new Segment(points.get(p), points.get((p + 1) % points.size()));

            //
            // If a known segment, seek a sequence of collinear segments.
            //
            if (edge.edgeType == EdgeType.REAL_SEGMENT)
            {
                Segment actualSeg = currSegment;

                boolean collinearExists = false;
                int prevPtIndex;
                for (prevPtIndex = p + 1; prevPtIndex < points.size(); prevPtIndex++)
                {
                    // Make another segment with the next point.
                    Segment nextSeg = new Segment(points.get(p), points.get((prevPtIndex + 1) % points.size()));

                    // CTA: This criteria seems invalid in some cases....; may not have collinearity

                    // We hit the end of the line of collinear segments.
                    if (!currSegment.IsCollinearWith(nextSeg)) break;

                    collinearExists = true;
                    actualSeg = nextSeg;
                }

                // If there exists an arc over the actual segment, we have an embedded circle to consider.
                regionsSegments[currCounter] = actualSeg;

                if (collinearExists)
                {
                    PlanarGraphEdge collEdge = graph.getEdge(actualSeg.getPoint1(), actualSeg.getPoint2());
                    if (collEdge != null)
                    {
                        if (collEdge.edgeType == EdgeType.REAL_ARC)
                        {
                            // Find all applicable circles
                            ArrayList<Circle> circles = GetAllApplicableCircles(thatCircles, actualSeg.getPoint1(), actualSeg.getPoint2());

                            // Get the exact outer circles for this segment (and create any embedded regions).
                            OutSingle<Circle> leftOuterCircleCurrCount; 
                            leftOuterCircleCurrCount.set(leftOuterCircles[currCounter]);
                            OutSingle<Circle> rightOuterCircleCurrCount;
                            rightOuterCircleCurrCount.set(rightOuterCircles[currCounter]);
                            regions.addAll(ConvertToCircleCircle(actualSeg, circles, leftOuterCircleCurrCount, rightOuterCircleCurrCount));
                        }
                    }
                }

                currCounter++;
                p = prevPtIndex;
            }
            else if (edge.edgeType == EdgeType.REAL_DUAL)
            {
                regionsSegments[currCounter] = new Segment(points.get(p), points.get((p + 1) % points.size()));

                // Get the exact chord and set of circles
                Segment chord = regionsSegments[currCounter];

                // Find all applicable circles
                ArrayList<Circle> circles = GetAllApplicableCircles(thatCircles, points.get(p), points.get((p + 1) % points.size()));

                // Get the exact outer circles for this segment (and create any embedded regions).
                OutSingle<Circle> leftOuterCircleCurrCount; 
                leftOuterCircleCurrCount.set(leftOuterCircles[currCounter]);
                OutSingle<Circle> rightOuterCircleCurrCount;
                rightOuterCircleCurrCount.set(rightOuterCircles[currCounter]);
                regions.addAll(ConvertToCircleCircle(chord, circles, leftOuterCircleCurrCount, rightOuterCircleCurrCount));

                currCounter++;
                p++;
            }
            else if (edge.edgeType == EdgeType.REAL_ARC)
            {
                //
                // Find the unique circle that contains these two points.
                // (if more than one circle has these points, we would have had more intersections and it would be a direct chorded region)
                //
                ArrayList<Circle> circles = GetAllApplicableCircles(thatCircles, points.get(p), points.get((p + 1) % points.size()));

                if (circles.size() != 1) throw new Exception("Need ONLY 1 circle for REAL_ARC atom id; found (" + circles.size() + ")");

                arcSegments[currCounter++] = new MinorArc(circles.get(0), points.get(p), points.get((p + 1) % points.size()));

                p++;
            }
        }

        //
        // Check to see if this is a region in which some connections are segments and some are arcs.
        // This means there were no REAL_DUAL edges.
        //
        ArrayList<AtomicRegion> generalRegions = GeneralAtomicRegion(regionsSegments, arcSegments);
        if (!generalRegions.isEmpty()) return generalRegions;

        // Copy the segments into a list (ensuring no nulls)
        ArrayList<Segment> actSegments = new ArrayList<Segment>();
        for (Segment side : regionsSegments)
        {
            if (side != null) actSegments.add(side);
        }

        // Construct a polygon out of the straight-up segments
        // This might be a polygon that defines a pathological region.
        Polygon poly = Polygon.MakePolygon(actSegments);

        // Determine which outermost circles apply inside of this polygon.
        Circle[] circlesCutInsidePoly = new Circle[actSegments.size()];
        for (int p = 0; p < actSegments.size(); p++)
        {
            if (leftOuterCircles[p] != null && rightOuterCircles[p] == null)
            {
                circlesCutInsidePoly[p] = CheckCircleCutInsidePolygon(poly, leftOuterCircles[p], actSegments.get(p).getPoint1(), actSegments.get(p).getPoint2());
            }
            else if (leftOuterCircles[p] == null && rightOuterCircles[p] != null)
            {
                circlesCutInsidePoly[p] = CheckCircleCutInsidePolygon(poly, rightOuterCircles[p], actSegments.get(p).getPoint1(), actSegments.get(p).getPoint2());
            }
            else if (leftOuterCircles[p] != null && rightOuterCircles[p] != null)
            {
                circlesCutInsidePoly[p] = CheckCircleCutInsidePolygon(poly, leftOuterCircles[p], actSegments.get(p).getPoint1(), actSegments.get(p).getPoint2());

                if (circlesCutInsidePoly[p] == null) circlesCutInsidePoly[p] = rightOuterCircles[p];
            }
            else
            {
                circlesCutInsidePoly[p] = null;
            }
        }

        boolean isStrictPoly = true;
        for (int p = 0; p < actSegments.size(); p++)
        {
            if (circlesCutInsidePoly[p] != null || arcSegments[p] != null)
            {
                isStrictPoly = false;
                break;
            }
        }

        // This is just a normal shape region: polygon.
        if (isStrictPoly)
        {
            regions.add(new ShapeAtomicRegion(poly));
        }
        // A circle cuts into the polygon.
        else
        {
            //
            // Now that all interior arcs have been identified, construct the atomic (probably pathological) region
            //
            AtomicRegion pathological = new AtomicRegion();
            for (int p = 0; p < actSegments.size(); p++)
            {
                //
                // A circle cutting inside the polygon
                //
                if (circlesCutInsidePoly[p] != null)
                {
                    Arc theArc = null;

                    if (circlesCutInsidePoly[p].DefinesDiameter(regionsSegments[p]))
                    {
                        Point midpt = circlesCutInsidePoly[p].Midpoint(regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2());

                        if (!poly.IsInPolygon(midpt)) midpt = circlesCutInsidePoly[p].OppositePoint(midpt);

                        theArc = new Semicircle(circlesCutInsidePoly[p], regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2(), midpt, regionsSegments[p]);
                    }
                    else
                    {
                        theArc = new MinorArc(circlesCutInsidePoly[p], regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2());
                    }

                    pathological.addConnection(regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2(), ConnectionType.ARC, theArc);
                }
                //
                else
                {
                    // We have a direct arc
                    if (arcSegments[p] != null)
                    {
                        pathological.addConnection(regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2(),
                                                   ConnectionType.ARC, arcSegments[p]);
                    }
                    // Use the segment
                    else
                    {
                        pathological.addConnection(regionsSegments[p].getPoint1(), regionsSegments[p].getPoint2(),
                                                   ConnectionType.SEGMENT, regionsSegments[p]);
                    }
                }
            }

            regions.add(pathological);
        }


        return regions;
    }

    //
    // Determine if this is a true polygon situation or if it is a sequence of segments and arcs.
    //
    private ArrayList<AtomicRegion> GeneralAtomicRegion(Segment[] segments, Arc[] arcs) throws ASTException
    {
        ArrayList<AtomicRegion> regions = new ArrayList<AtomicRegion>();

        //
        // Determine if the parts are all segments.
        // Concurrently determine the proper starting point in the sequence to construct the atomic region.
        //
        boolean hasArc = false;
        boolean hasSegment = false;
        int startIndex = 0;
        for (int i = 0; i < segments.length && i < arcs.length; i++)
        {
            // Both an arc and a segment.
            if (segments[i] != null && arcs[i] != null) return regions;

            // Determine if we have an arc and/or a segment.
            if (segments[i] != null) hasSegment = true;
            if (arcs[i] != null) hasArc = true;

            // A solid starting point is an arc right after a null.
            if (arcs[i] == null && arcs[(i + 1) % arcs.length] != null)
            {
                // Assign only once to the startIndex
                if (startIndex == 0) startIndex = (i + 1) % arcs.length;
            }
        }

        // If only segments, we have a polygon.
        if (hasSegment && !hasArc) return regions;

        //
        // If the set ONLY consists of arcs, ensure we have a good starting point.
        //
        if (hasArc && !hasSegment)
        {
            // Seek the first index where a change among arcs occurs.
            for (int i = 0; i < arcs.length; i++)
            {
                // A solid starting point is an arc right after a null.
                if (!arcs[i].getCircle().StructurallyEquals(arcs[(i + 1) % arcs.length].getCircle()))
                {
                    startIndex = (i + 1) % arcs.length;
                    break;
                }
            }
        }

        AtomicRegion theRegion = new AtomicRegion();
        for (int i = 0; i < segments.length && i < arcs.length; i++)
        {
            int currIndex = (i + startIndex) % arcs.length;

            if (segments[currIndex] == null && arcs[currIndex] == null) { /* No-Op */ }

            if (segments[currIndex] != null)
            {
                theRegion.AddConnection(new Connection(segments[currIndex].getPoint1(),
                                                       segments[currIndex].getPoint2(), ConnectionType.SEGMENT, segments[currIndex]));
            }
            else if (arcs[currIndex] != null)
            {
                //
                // Compose the arcs (from a single circle) together.
                //
                ArrayList<MinorArc> sequentialArcs = new ArrayList<MinorArc>();
                if (arcs[currIndex] != null && arcs[currIndex] instanceof MinorArc)
                {
                    MinorArc arcCurrIndex = (MinorArc) arcs[currIndex];
                    sequentialArcs.add(arcCurrIndex);
                }

                int seqIndex;
                for (seqIndex = (currIndex + 1) % arcs.length; ; seqIndex = (seqIndex + 1) % arcs.length, i++)
                {
                    if (arcs[seqIndex] == null) break;

                    if (arcs[currIndex].getCircle().StructurallyEquals(arcs[seqIndex].getCircle()))
                    {
                        if (arcs[seqIndex] != null && arcs[seqIndex] instanceof MinorArc)
                        {
                            MinorArc arcSeqIndex = (MinorArc) arcs[seqIndex];
                            sequentialArcs.add(arcSeqIndex);
                        }
                    }
                    else break;
                }

                Arc composed;
                if (sequentialArcs.size() > 1) composed = this.ComposeArcsIntoArc(sequentialArcs);
                else composed = arcs[currIndex];

                //
                // Add the connection.
                //
                theRegion.AddConnection(new Connection(composed.getEndpoint1(), composed.getEndpoint2(), ConnectionType.ARC, composed));
            }
        }

        return Utilities.MakeList(theRegion);
    }

    private Circle CheckCircleCutInsidePolygon(Polygon poly, Circle circle, Point pt1, Point pt2)
    {
        Segment diameter = new Segment(pt1, pt2);

        // A semicircle always cuts into the polygon.
        if (circle.DefinesDiameter(diameter)) return circle;
        else
        {
            // Is the midpoint on the interior of the polygon?
            Point midpt = circle.Midpoint(pt1, pt2);

            // Is this point in the interior of this polygon?
            if (poly.IsInPolygon(midpt)) return circle;
        }

        return null;
    }

    //
    // This is a complex situation because we need to identify situations where circles intersect with the resultant regions:
    //    (|     (|)
    //   ( |    ( | )
    //  (  |   (  |  )
    //   ( |    ( | )
    //    (|     (|)
    //
    // Note: There will always be a chord because of our implied construction.
    // We are interested in only minor arcs of the given circles.
    //
    @SuppressWarnings("null")
    private ArrayList<AtomicRegion> ConvertToCircleCircle(Segment chord,
                                                              ArrayList<Circle> circles,
                                                              OutSingle<Circle> leftOuterCircle,
                                                              OutSingle<Circle> rightOuterCircle) throws ASTException
    {
        ArrayList<AtomicRegion> regions = new ArrayList<AtomicRegion>();
        leftOuterCircle = null;
        rightOuterCircle = null;

        //
        // Simple cases that require no special attention.
        //
        if (circles.isEmpty()) return null;
        if (circles.size() == 1)
        {
            leftOuterCircle.set(circles.get(0));

            regions.addAll(ConstructBasicLineCircleRegion(chord, circles.get(0)));

            return regions;
        }

        // All circles that are on each side of the chord 
        ArrayList<Circle> leftSide = new ArrayList<Circle>();
        ArrayList<Circle> rightSide = new ArrayList<Circle>();

        // For now, assume max, one circle per side.
        // Construct a collinear list of points that includes all circle centers as well as the single intersection point between the chord and the line passing through all circle centers.
        // This orders the sides and provides implied sizes.

        Segment centerLine = new Segment(circles.get(0).getCenter(), circles.get(1).getCenter());
        for (int c = 2; c < circles.size(); c++)
        {
            centerLine.AddCollinearPoint(circles.get(c).getCenter());
        }
        // Find the intersection between the center-line and the chord; add that to the list.
        Point intersection = centerLine.FindIntersection(chord);
        centerLine.AddCollinearPoint(intersection);

        ArrayList<Point> collPoints = centerLine.getCollinear();
        int interIndex = collPoints.indexOf(intersection);

        for (int i = 0; i < collPoints.size(); i++)
        {
            // find the circle based on center
            int c;
            for (c = 0; c < circles.size(); c++)
            {
                if (circles.get(c).getCenter().StructurallyEquals(collPoints.get(i))) break;
            }

            // Add the circle in order
            if (i < interIndex) leftSide.add(circles.get(c));
            else if (i > interIndex) rightSide.add(circles.get(c));
        }

        // the outermost circle is first in the left list and last in the right list.
        if (!leftSide.isEmpty()) leftOuterCircle.set(leftSide.get(0));
        if (!rightSide.isEmpty()) rightOuterCircle.set(rightSide.get(rightSide.size() - 1));

        //
        // Main combining algorithm:
        //     Assume: Increasing Arc sequence A \in A_1, A_2, ..., A_n and the single chord C
        //
        //     Construct region B = (C, A_1)
        //     For the increasing Arc sequence (k subscript)  A_2, A_3, ..., A_n
        //         B = Construct ((C, A_k) \ B)
        //         
        // Alternatively:
        //     Construct(C, A_1)
        //     for each pair Construct (A_k, A_{k+1})
        //
        //
        // Handle each side: left and right.
        //
        if (!leftSide.isEmpty()) regions.addAll(ConstructBasicLineCircleRegion(chord, leftSide.get(leftSide.size() - 1)));
        for (int ell = 0; ell < leftSide.size() - 2; ell++)
        {
            regions.add(ConstructBasicCircleCircleRegion(chord, leftSide.get(ell), leftSide.get(ell + 1)));
        }

        if (!rightSide.isEmpty()) regions.addAll(ConstructBasicLineCircleRegion(chord, rightSide.get(0)));
        for (int r = 1; r < rightSide.size() - 1; r++)
        {
            regions.add(ConstructBasicCircleCircleRegion(chord, rightSide.get(r), rightSide.get(r + 1)));
        }

        return regions;
    }

    // Construct the region between a chord and the circle arc:
    //    (|
    //   ( |
    //  (  |
    //   ( |
    //    (|
    //
    private ArrayList<AtomicRegion> ConstructBasicLineCircleRegion(Segment chord, Circle circle) throws ASTException
    {
        //
        // Standard
        //
        if (!circle.DefinesDiameter(chord))
        {
            AtomicRegion region = new AtomicRegion();

            Arc theArc = new MinorArc(circle, chord.getPoint1(), chord.getPoint2());

            region.addConnection(chord.getPoint1(), chord.getPoint2(), ConnectionType.ARC, theArc);

            region.addConnection(chord.getPoint1(), chord.getPoint2(), ConnectionType.SEGMENT, chord);

            return Utilities.MakeList(region);
        }

        //
        // Semi-circles
        //

        Point midpt = circle.Midpoint(chord.getPoint1(), chord.getPoint2());
        Arc semi1 = new Semicircle(circle, chord.getPoint1(), chord.getPoint2(), midpt, chord);
        ShapeAtomicRegion region1 = new ShapeAtomicRegion(new Sector(semi1));

        Point opp = circle.OppositePoint(midpt);
        Arc semi2 = new Semicircle(circle, chord.getPoint1(), chord.getPoint2(), opp, chord);
        ShapeAtomicRegion region2 = new ShapeAtomicRegion(new Sector(semi2));

        ArrayList<AtomicRegion> regions = new ArrayList<AtomicRegion>();
        regions.add(region1);
        regions.add(region2);

        return regions;
    }

    // Construct the region between a circle and circle:
    //     __
    //    ( (
    //   ( ( 
    //  ( (  
    //   ( ( 
    //    ( (
    //     --
    private AtomicRegion ConstructBasicCircleCircleRegion(Segment chord, Circle smaller, Circle larger) throws ASTException
    {
        AtomicRegion region = new AtomicRegion();

        Arc arc1 = null;
        if (smaller.DefinesDiameter(chord))
        {
            Point midpt = smaller.Midpoint(chord.getPoint1(), chord.getPoint2(), larger.Midpoint(chord.getPoint1(), chord.getPoint2()));

            arc1 = new Semicircle(smaller, chord.getPoint1(), chord.getPoint2(), midpt, chord);
        }
        else
        {
            arc1 = new MinorArc(smaller, chord.getPoint1(), chord.getPoint2());
        }

        MinorArc arc2 = new MinorArc(larger, chord.getPoint1(), chord.getPoint2());

        region.addConnection(chord.getPoint1(), chord.getPoint2(), ConnectionType.ARC, arc1);

        region.addConnection(chord.getPoint1(), chord.getPoint2(), ConnectionType.ARC, arc2);

        return region;
    }

}
