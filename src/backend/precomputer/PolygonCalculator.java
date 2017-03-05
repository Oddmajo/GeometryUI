package backend.precomputer;

import java.util.ArrayList;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.ast_helper.Utilities;

/// <summary>
/// Live Geometry does not define ALL components of the figure, we must acquire those implied components.
/// </summary>
public class PolygonCalculator
{
    private ArrayList<ArrayList<Polygon>> polygons;
    private ArrayList<Segment> segments;

    public PolygonCalculator(ArrayList<Segment> segs)
    {
        polygons = null;
        segments = segs;
    }

    public ArrayList<ArrayList<Polygon>> GetPolygons()
    {
        if (polygons == null)
        {
            polygons = Polygon.ConstructPolygonContainer();
            CalculateImpliedPolygons();
        }

        return polygons;
    }

    //
    // Not all shapes are explicitly stated by the user; find all the implied shapes.
    // This populates the polygon array with any such shapes (concave or convex)
    //
    // Using a restricted powerset construction (from the bottom-up), construct all polygons.
    // Specifically: 
    //    (1) begin with all nC3 sets of segments.
    //    (2) If the set of 3 segments makes a triangle, create polygon, stop.
    //    (3) If the set of 3 segments does NOT make a triangle, construct all possible sets of size 4.
    //    (4) Inductively repeat for sets of size up MAX_POLY
    // No set of segments are collinear.
    //
    // This construction must be done in a breadth first manner (triangles then quads then pentagons...)
    //
    private void CalculateImpliedPolygons()
    {
        boolean[][] eligible = DetermineEligibleCombinations();
        ArrayList<ArrayList<Integer>> constructedPolygonSets = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> failedPolygonSets = new ArrayList<ArrayList<Integer>>();

        //
        // Base case: construct all triangles.
        // For all non-triangle set of 3 segments, inductively look for polygons with more sides.
        //
        for (int s1 = 0; s1 < segments.size() - 2; s1++)
        {
            for (int s2 = s1 + 1; s2 < segments.size() - 1; s2++)
            {
                if (eligible[s1][s2])
                {
                    for (int s3 = s2 + 1; s3 < segments.size() - 0; s3++)
                    {
                        // Does this set create a triangle?
                        if (eligible[s1][s3] && eligible[s2][s3])
                        {
                            
                            ArrayList<Integer> indices = new ArrayList<Integer>();
                            indices.add(s1);
                            indices.add(s2);
                            indices.add(s3);
                            ArrayList<Segment> segs = MakeSegmentsList(indices);
                            //Added for testing purposes without the Implied Component Calculator. Nick 11/2/2016
//                            if(!(segs.get(0).isCollinearWith(segs.get(1)) || segs.get(1).isCollinearWith(segs.get(2)) || segs.get(0).isCollinearWith(segs.get(2))))
                            {
                                Polygon poly = Polygon.MakePolygon(segs);
                                if (poly == null)
                                {
                                    
                                    failedPolygonSets.add(indices);
                                }
                                else
                                {
                                    polygons.get(Polygon.GetPolygonIndex(indices.size())).add(poly);

                                    // Keep track of all existent sets of segments which created polygons.
                                    constructedPolygonSets.add(indices);
                                }
                            }
                        }
                    }
                }
            }
        }

        //
        // Inductively look for polygons with more than 3 sides.
        //
        InductivelyConstructPolygon(failedPolygonSets, eligible, constructedPolygonSets);
    }

    //
    // For each given set, add 1 new side (at a time) to the list of sides in order to construct polygons.
    //
    private void InductivelyConstructPolygon(ArrayList<ArrayList<Integer>> openPolygonSets, boolean[][] eligible, ArrayList<ArrayList<Integer>> constructedPolygonSets)
    {
        // Stop if no sets to consider and grow from.
        if (openPolygonSets.isEmpty()) return;

        // Stop at a maximum number of sides;  we say n is the number of sides
        if (openPolygonSets.get(0).size() == Polygon.MAX_POLYGON_SIDES) return;

        ///Check this length 
        int matrixLength = eligible[0].length; 

        // The set of sets that contains n+1 elements that do not make a polygon (sent to the next round)
        ArrayList<ArrayList<Integer>> failedPolygonSets = new ArrayList<ArrayList<Integer>>();

        // Breadth first consruction / traversal
        for (ArrayList<Integer> currentOpenSet : openPolygonSets)
        {
            // Since indices will be ordered least to greatest, we start looking at the largest index (last place in the list).
            for (int s = currentOpenSet.get(currentOpenSet.size() - 1); s < matrixLength; s++)
            {
                if (IsEligible(currentOpenSet, eligible, s))
                {
                    ArrayList<Integer> newIndices = new ArrayList<Integer>(currentOpenSet);
                    newIndices.add(s);

                    // Did we already create a polygon with a subset of these indices?
                    if (!Utilities.ListHasSubsetOfSet(constructedPolygonSets, newIndices))
                    {
                        ArrayList<Segment> segs = MakeSegmentsList(newIndices);
                        Polygon poly = Polygon.MakePolygon(segs);
                        if (poly == null)
                        {
                            failedPolygonSets.add(newIndices);
                        }
                        else
                        {
                            polygons.get(Polygon.GetPolygonIndex(segs.size())).add(poly);

                            // Keep track of all existent sets of segments which created polygons.
                            constructedPolygonSets.add(newIndices);
                        }
                    }
                }
            }
        }

        InductivelyConstructPolygon(failedPolygonSets, eligible, constructedPolygonSets);
    }

    // Make a list of segments based on indices; a helper function.
    private ArrayList<Segment> MakeSegmentsList(ArrayList<Integer> indices)
    {
        ArrayList<Segment> segs = new ArrayList<Segment>();

        for (int index : indices)
        {
            segs.add(segments.get(index));
        }

        return segs;
    }

    //
    // Given the set, is the given single term eligible?
    //
    private boolean IsEligible(ArrayList<Integer> indices, boolean[][] eligible, int newElement)
    {
        for (int index : indices)
        {
            if (!eligible[index][newElement]) return false;
        }

        return true;
    }

    //
    // Eligibility means that each pair of segment combinations does not:
    //   (1) Cross the other segment through the middle (creating an X or |-)
    //   (2) Coincide with overlap (or share a vertex)
    //
    private boolean[][] DetermineEligibleCombinations()
    {
        boolean[][] eligible = new boolean[segments.size()][segments.size()]; // defaults to false

        for (int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for (int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                // Crossing
                if (!segments.get(s1).middleCrosses(segments.get(s2)))
                {
                    if (!segments.get(s1).isCollinearWith(segments.get(s2)))
                    {
                        eligible[s1][s2] = true;
                        eligible[s2][s1] = true;
                    }
                    else
                    {
                        //
                        // Coinciding and sharing a vertex, is disallowed by default.
                        //
                        //                                           __    __
                        // Coinciding ; Can have something like :   |  |__|  |
                        //                                          |________|
                        //
                        if (segments.get(s1).sharedVertex(segments.get(s2)) == null)
                        {
                            if (segments.get(s1).isCollinearWithoutOverlap(((((segments.get(s2)))))))
                            {
                                eligible[s1][s2] = true;
                                eligible[s2][s1] = true;
                            }
                        }
                    }
                }
            }
        }

        return eligible;
    }
}