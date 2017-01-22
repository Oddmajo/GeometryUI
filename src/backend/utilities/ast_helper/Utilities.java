/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.utilities.ast_helper;

import java.util.ArrayList;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.utilities.PointFactory;

/**
 * The Utilities class from the GeometryTutor project, converted from C#
 * <p>
 * Currently, I only have the booleans at the beginning and the following methods:
 *      AddUnique
 *      AddUniqueList
 *      MakeList
 * The rest are commented out to be converted to Java in the future.
 * <p>
 * I have attempted to convert many of the functions, but I think some are done
 * incorrectly.  I have left them commented out for sanity's sake and to get the
 * Facet Identification package going.  I will convert the methods as needed.
 * @author Chris Alvin
 * @author Drew W
 *
 */
public class Utilities
{

    public static final boolean OVERRIDE_DEBUG = false;

    @SuppressWarnings("unused")
    public static final boolean DEBUG                       = OVERRIDE_DEBUG && true;
    @SuppressWarnings("unused")
    public static final boolean CONSTRUCTION_DEBUG          = OVERRIDE_DEBUG && true;   // Generating clauses when analyzing input figure
    @SuppressWarnings("unused")
    public static final boolean PEBBLING_DEBUG              = OVERRIDE_DEBUG && false;   // Hypergraph edges and pebbled nodes
    @SuppressWarnings("unused")
    public static final boolean PROBLEM_GEN_DEBUG           = OVERRIDE_DEBUG && true;   // Generating the actual problems
    @SuppressWarnings("unused")
    public static final boolean BACKWARD_PROBLEM_GEN_DEBUG  = OVERRIDE_DEBUG && true;   // Generating backward problems
    @SuppressWarnings("unused")
    public static final boolean ATOMIC_REGION_GEN_DEBUG     = OVERRIDE_DEBUG && true;   // Generating atomic regions
    @SuppressWarnings("unused")
    public static final boolean SHADED_AREA_SOLVER_DEBUG    = OVERRIDE_DEBUG && true;   // Solving a shaded area problem.
    public static final boolean FIGURE_SYNTHESIZER_DEBUG    = true;   // Solving a shaded area problem.

    // If the user specifies that an axiom, theorem, or definition is not to be used.
    public static final boolean RESTRICTING_AXS_DEFINITIONS_THEOREMS = true;

    // Handles negatives and positives.
    public static int Modulus(int x, int m) { return (x % m + m) % m; }
    

    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    //
    
    public static <T extends GroundedClause> boolean HasStructurally(List<T> list, T t)
    {
        return Utilities.StructuralIndex(list, t) != -1;
    }
    

    //
    // Given a list of grounded clauses, get the structurally unique.
    //
    public static <T extends GroundedClause> T GetStructurally(List<T> list, T t)
    {
        for (T oldT : list)
        {
            if (oldT.structurallyEquals(t)) return oldT;
        }

        return null;
    }
    

    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    //
    public static <T extends GroundedClause> boolean AddStructurallyUnique(List<T> list, T t)
    {
        if (HasStructurally(list, t)) return false;

        list.add(t);

        return true;
    }

    //
    // Given a list of grounded clauses, add a new value which is structurally unique.
    // This has been commented out because it is handled by a previous method
    //
    
    public static <T extends GroundedClause> int StructuralIndex(List<T> list, T t) 
    {
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).structurallyEquals(t)) return i;
        }

        return -1;
    }
   
    // Ensure uniqueness of additions
    public static void AddUniqueStructurally(List<Figure> figures, Figure f)
    {
        for (Figure figure : figures)
        {
            if (figure.structurallyEquals(f)) return;
        }
        figures.add(f);
        
    }

    // Makes a list containing a single element
    public static <T> ArrayList<T> MakeList(T obj)
    {
        ArrayList<T> l = new ArrayList<T>();

        l.add(obj);

        return l;
    }
    
    // Makes a list containing a single element
    public static <T> boolean AddUnique(List<T> list, T obj)
    {
        if (list.contains(obj)) return false;

        list.add(obj);
        return true;
    }
    
    // Makes a list containing a single element
    public static <T> void AddUniqueList(List<T> list, List<T> objList)
    {
        for (T o : objList)
        {
            AddUnique(list, o);
        }
    }
    

    
    public static final double EPSILON = 0.000001;

    public static boolean CompareValues(double a, double b)
    {
        return Math.abs(a - b) < EPSILON;
    }
    

    public static boolean LessThan(double x, int i)
    {
        return x < i;
    }
    
    public static boolean GreaterThan(double x, int i)
    {
        return x > i;
    }
    

    //
    // Get a point in the given list OR create a new list.
    //
    public static Point AcquirePoint(List<Point> points, Point that)
    {
        if (that == null) return null;

        // Avoid parallel line intersections at infinity
        //if (double.IsInfinity(that.X) || double.IsInfinity(that.Y) || double.IsNaN(that.X) || double.IsNaN(that.Y)) return null;
        if (Double.isNaN(that.getX()) ||Double.isNaN(that.getY())) return null;

        Point pt = GetStructurally(points, that);
        if (pt == null) pt = PointFactory.GeneratePoint(that.getX(), that.getY());
        return pt;
    }

    //
    // Get a point in the given list OR create a new list that is internal to the given segments.
    //
    public static Point AcquireRestrictedPoint(List<Point> points, Point that,
                                                           Segment seg1, Segment seg2)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !seg1.pointLiesBetweenEndpoints(pt) || !seg2.pointLiesBetweenEndpoints(pt) ? null : pt;
    }
    
    public static Point AcquireRestrictedPoint(List<Point> points, Point that,
                                                           Arc arc1, Arc arc2)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !arc1.pointLiesOn(pt) || !arc2.pointLiesOn(pt) ? null : pt;
    }

    public static Point AcquireRestrictedPoint(List<Point> points, Point that,
                                                           Segment seg, Arc arc)
    {
        Point pt = AcquirePoint(points, that);

        if (pt == null) return null;

        return !seg.pointLiesBetweenEndpoints(pt) || !arc.pointLiesOn(pt) ? null : pt;
    }
    
    // Is smaller subset larger
    public static <T> boolean Subset(ArrayList<T> larger, ArrayList<T> smaller)
    {
        for (T o : smaller)
        {
            if (!larger.contains(o)) return false;
        }

        return true;
    }

    
    // Is the list a subset of any of the sets in the list of lists?
    public static <T> boolean ListHasSubsetOfSet(ArrayList<ArrayList<T>> sets, ArrayList<T> theSet)
    {
        // Do not consider a new subset which contains an existent polygon.
        for(ArrayList<T> set : sets)
        {
            if (Subset(theSet, set)) return true;
        }

        return false;
    }

}
