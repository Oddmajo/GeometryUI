/**
 * @author Nick Celiberti
 */

package backend.precomputer;

import java.util.ArrayList;

import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Segment;

public class PolygonCalculator
{
    private ArrayList<ArrayList<Polygon>> polygons;
    private ArrayList<Segment> segments;

    public PolygonCalculator(ArrayList<Segment> segs)
    {
        polygons = null;
        segments = segs;
    }

    public ArrayList<ArrayList<Polygon>> GetPolygons() throws IllegalArgumentException 
    {
        if(polygons == null)
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
    // This construction must be done : a breadth first manner (triangles then quads then pentagons...)
    //
    public void CalculateImpliedPolygons() throws IllegalArgumentException 
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
                            System.out.println(segs);
                            Polygon poly = Polygon.MakePolygon(segs);
                            if (poly == null)
                            {
                                System.out.println("failed");
                                failedPolygonSets.add(indices);
                            }
                            else
                            {
                                System.out.println("Adding Poly");
                                polygons.get(Polygon.GetPolygonIndex(indices.size())).add(poly);
                                // Keep track of all existent sets of segments which created polygons.
                                constructedPolygonSets.add(indices);
                            }
                        } else { System.out.println(eligible[s1][s3]); System.out.println(eligible[s2][s3]);}
                    }
                }
            }
        }
        //
        // Inductively look for polygons with more than 3 sides.
        //
        InductivelyConstructPolygon(failedPolygonSets, eligible, constructedPolygonSets);
    }

    private void InductivelyConstructPolygon(ArrayList<ArrayList<Integer>> openPolygonSets, boolean[][] eligible,
            ArrayList<ArrayList<Integer>> constructedPolygonSets) throws IllegalArgumentException
    {
        // Stop if no sets to consider and grow from.
        if (openPolygonSets.isEmpty()) return;

        // Stop at a maximum number of sides;  we say n is the number of sides
        if (openPolygonSets.get(0).size() == Polygon.MAX_POLYGON_SIDES) return;

        int matrixLength = eligible[0].length; 

        // The set of sets that contains n+1 elements that do not make a polygon (sent to the next round)
        ArrayList<ArrayList<Integer>> failedPolygonSets = new ArrayList<ArrayList<Integer>>();

        // Breadth first construction / traversal
        for (ArrayList<Integer> currOpenSet : openPolygonSets)
        {
            ArrayList<Integer> currentOpenSet = currOpenSet;
            // Since indices will be ordered least to greatest, we start looking at the largest index (last place : the list).
            for (int s = currentOpenSet.get(currentOpenSet.size() - 1); s < matrixLength; s++)
            {
                if (IsEligible(currentOpenSet, eligible, s))
                {
                    ArrayList<Integer> newIndices = new ArrayList<Integer>(currentOpenSet);
                    newIndices.add(s);

                    // Did we already create a polygon with a subset of these indices?
                    //if (!GeometryTutorLib.Utilities.ListHasSubsetOfSet<Integer>(constructedPolygonSets, newIndices))
                    if(true)
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

    private ArrayList<Segment> MakeSegmentsList(ArrayList<Integer> indices)
    {
        ArrayList<Segment> segs = new ArrayList<Segment>();

        for (int index : indices)
        {
            segs.add(segments.get(index));
        }

        return segs;
    }
    
    private boolean IsEligible(ArrayList<Integer> indices, boolean[][] eligible, int newElement)
    {
        for (int index : indices)
        {
            if (!eligible[index][newElement]) return false;
        }

        return true;
    }

    

    private boolean[][] DetermineEligibleCombinations()
    {
        boolean[][] eligible = new boolean[segments.size()][segments.size()]; // defaults to false

        for (int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for (int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                // Crossing
                if (!segments.get(s1).Crosses(segments.get(s2)))
                {
                    if (!segments.get(s1).IsCollinearWith(segments.get(s2)))
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
                        if (segments.get(s1).SharedVertex(segments.get(s2)) == null)
                        {
                            if (segments.get(s1).CoincidingWithoutOverlap(segments.get(s2)))
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

