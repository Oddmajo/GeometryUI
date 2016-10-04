/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package backend.utilities.ast_helper;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Arc;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.Pair;
import backend.utilities.translation.OutSingle;

/**
 * Functions based on hypergraph and clause utilities
 * 
 * @author Chris Alvin
 *
 */
public class Utilities
{
    //
    // Given a list, remove duplicates
    //
    public static <T extends backend.ast.GroundedClause> List<T> RemoveDuplicates(List<T> list)
    {
        List<T> cleanList = new ArrayList<T>();

        for (int i = 0; i < list.size() - 1; i++)
        {
            if (list.get(i) != null)
            {
                for (int j = i + 1; j < list.size(); j++)
                {
                    if (list.get(j) != null && isDuplicate(list.get(i), list.get(j)))
                    {
                        list.set(j,  null);
                    }
                }
            }
        }

        for (T t : list)
        {
            if (t != null)
            {
                cleanList.add(t);
            }
        }

        return cleanList;
    }



    private static <T extends backend.ast.GroundedClause> boolean isDuplicate(T clause1, T clause2)
    {
        if (clause1 instanceof Angle)
        {
            //Do not want to remove angles that are different but have coinciding rays (would cause problems when trying to find
            //inscribed and central angles inside circles)
            backend.ast.figure.components.Angle angle = (backend.ast.figure.components.Angle)(clause1);
            return angle.equalRays(clause2);
        }
        else return clause1.StructurallyEquals(clause2);
    }


    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    //
    public static <T extends backend.ast.GroundedClause> int structuralIndex(List<T> list, T t)
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).StructurallyEquals(t)) return i;
        }

        return -1;
    }


    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    //

    public static <T extends backend.ast.GroundedClause> boolean hasStructurally(List<T> list, T t)
    {
        return Utilities.structuralIndex(list, t) != -1;
    }


    //
    // Given a list of grounded clauses, get the structurally unique.
    //
    public static <T extends backend.ast.GroundedClause> T getStructurally(List<T> list, T t)
    {
        for (T oldT : list)
        {
            if (oldT.StructurallyEquals(t)) return oldT;
        }

        return null;
    }


    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    //
    public static <T extends backend.ast.GroundedClause> boolean addStructurallyUnique(List<T> list, T t)
    {
        if (hasStructurally(list, t)) return false;

        list.add(t);

        return true;
    }

    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    // This has been commented out because it is handled by a previous method
    //

    public static <T extends backend.ast.GroundedClause> int StructuralIndex(List<T> list, T t) 
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).StructurallyEquals(t)) return i;
        }

        return -1;
    }


    // Given a sorted list, insert the element from the front to the back.
    public static void InsertAscendingOrdered(List<Integer> list, int value)
    {
        // Special Cases
        if (list.isEmpty())
        {
            list.add(value);
            return;
        }

        if (value > list.get(list.size()-1))
        {
            list.add(value);
            return;
        }

        // General Case
        for (int i = 0; i < list.size(); i++)
        {
            if (value < list.get(i))
            {
                list.add(i, value);
                return;
            }
        }
    }

    //	// Acquire the index of the clause : the hypergraph based only on structure
    //
    //	public static int StructuralIndex(Hypergraph.Hypergraph<ast.GroundedClause, Hypergraph.EdgeAnnotation> graph, ast.GroundedClause g)
    //	{
    //		//
    //		// Handle general case
    //		//
    //		List<Hypergraph.HyperNode<ast.GroundedClause, Hypergraph.EdgeAnnotation>> vertices = graph.vertices;
    //
    //		for (int v = 0; v < vertices.size(); v++)
    //		{
    //			if (vertices[v].data.StructurallyEquals(g)) return v;
    //
    //			if (vertices[v].data is ast.Strengthened)
    //			{
    //				if ((vertices[v].data as ast.Strengthened).strengthened.StructurallyEquals(g)) return v;
    //			}
    //		}
    //
    //
    //		//
    //		// Handle strengthening by seeing if the clause is found without a 'strengthening' component
    //		//
    //		ast.Strengthened streng = g as ast.Strengthened;
    //		if (streng != null)
    //		{
    //			int index = StructuralIndex(graph, streng.strengthened);
    //			if (index != -1) return index;
    //		}
    //
    //		return -1;
    //	}


    //	//
    //	// Acquires the hypergraph index value of the given nodes using structural equality
    //	//
    //
    //	public static List<Integer> CollectGraphIndices(Hypergraph.Hypergraph<ast.GroundedClause, Hypergraph.EdgeAnnotation> graph, List<ast.GroundedClause> clauses)
    //	{
    //		List<int> indices = new ArrayList<int>();
    //
    //		for (ast.GroundedClause gc : clauses)
    //		{
    //			int index = Utilities.StructuralIndex(graph, gc);
    //			if (index != -1)
    //			{
    //				indices.add(index);
    //			}
    //			else
    //			{
    //				System.Diagnostics.Debug.WriteLine("We expect to find the given node (we did not): " + gc.toString());
    //			}
    //		}
    //
    //		return indices;
    //	}



    //    //
    //	// Ensure uniqueness of additions
    //	//
    //	public static void addUniqueStructurally(ArrayList<Figure> figures, Figure f)
    //	{
    //		for (Figure figure : figures)
    //		{
    //			if (figure.StructurallyEquals(f)) return;
    //		}
    //		figures.add(f);
    //	}
    //

    //
    // Get a point : the given list OR create a new list.
    //
    public static Point AcquirePoint(List<Point> points, Point that)
    {
        if (that == null) return null;

        // Avoid parallel line intersections at infinity
        if (Double.isInfinite(that.getX()) || Double.isInfinite(that.getY()) || Double.isNaN(that.getX()) || Double.isNaN(that.getY())) return null;

        Point pt = getStructurally(points, that);
        if (pt == null) pt = backend.utilities.PointFactory.GeneratePoint(that.getX(), that.getY());
        return pt;
    }

    //
    // Get a point : the given list OR create a new list that is internal to the given segments.
    //
    public static Point AcquireRestrictedPoint(List<Point> points, Point that,
            Segment seg1, Segment seg2)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !seg1.PointLiesOnAndBetweenEndpoints(pt) || !seg2.PointLiesOnAndBetweenEndpoints(pt) ? null : pt;
    }

    public static Point AcquireRestrictedPoint(List<Point> points, Point that, Arc arc1, Arc arc2)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !arc1.PointLiesOn(pt) || !arc2.PointLiesOn(pt) ? null : pt;
    }

    public static Point AcquireRestrictedPoint(List<Point> points, Point that,
            Segment seg, backend.ast.figure.components.Arc arc)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !seg.PointLiesOnAndBetweenEndpoints(pt) || !arc.PointLiesOn(pt) ? null : pt;
    }

    //    public static String TimeToString(System.TimeSpan span)
    //    {
    //        return System.String.Format("{0:00}:{1:00}.{2:00}", span.Minutes, span.Seconds, span.Milliseconds / 10);
    //    }

    public static List<Segment> FilterForMinimal(List<Segment> segments)
    {
        List<Segment> minimal = new ArrayList<Segment>();

        for (int s1 = 0; s1 < segments.size(); s1++)
        {
            boolean min = true;
            for (int s2 = 0; s2 < segments.size(); s2++)
            {
                if (s1 != s2)
                {
                    if (segments.get(s1).HasSubSegment(segments.get(s2)))
                    {
                        min = false;
                        break;
                    }
                }
            }
            if (min) minimal.add(segments.get(s1));
        }

        return minimal;
    }

    public static List<Segment> FilterForMaximal(List<Segment> segments)
    {
        List<Segment> maximal = new ArrayList<Segment>();

        for (int s1 = 0; s1 < segments.size(); s1++)
        {
            boolean max = true;
            for (int s2 = 0; s2 < segments.size(); s2++)
            {
                if (s1 != s2)
                {
                    if (segments.get(s2).HasSubSegment(segments.get(s1)))
                    {
                        max = false;
                        break;
                    }
                }
            }
            if (max) maximal.add(segments.get(s1));
        }

        return maximal;
    }

    public static List<Segment> FilterShared(List<Segment> segments, OutSingle<List<Segment>> out)
    {
        List<Segment> unique = new ArrayList<Segment>();
        List<Segment> shared = new ArrayList<Segment>();

        boolean[] marked = new boolean[segments.size()];
        for (int s1 = 0; s1 < segments.size(); s1++)
        {
            if (!marked[s1])
            {
                for (int s2 = 0; s2 < segments.size(); s2++)
                {
                    if (s1 != s2)
                    {
                        if (segments.get(s1).HasSubSegment(segments.get(s2)))
                        {
                            marked[s1] = true;
                            marked[s2] = true;
                        }
                    }
                }
            }
        }

        //
        // Pick up all unmarked segments.
        //
        for (int m = 0; m < marked.length; m++)
        {
            if (!marked[m]) unique.add(segments.get(m));
            else shared.add(segments.get(m));
        }

        out.set(shared);
        
        return unique;
    }
}
