/*
iTutor � an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author Nick Celiberti
 * 
 */

package ast.figure.components;


import java.util.ArrayList;
import ast.figure.Figure;
import atoms.components.AtomicRegion;
import atoms.components.Connection;
import atoms.components.ShapeAtomicRegion;
import atoms.components.Connection.ConnectionType;
import utilities.Pair;
import utilities.PointFactory;
import utilities.ast_helper.Utilities;
import utilities.translation.OutPair;
import utilities.translation.OutSingle;

public class Polygon extends Figure
{
    public static final int MAX_POLYGON_SIDES = 6;

    //
    // Indices to access a polygon array container
    //
    public static final int MIN_POLY_INDEX = 0;
    public static final int TRIANGLE_INDEX = 0;
    public static final int QUADRILATERAL_INDEX = 1;
    public static final int PENTAGON_INDEX = 2;
    public static final int HEXAGON_INDEX = 3;
    public static final int SEPTAGON_INDEX = 4;
    public static final int OCTAGON_INDEX = 5;
    public static final int NONAGON_INDEX = 6;
    public static final int DECAGON_INDEX = 7;
    public static final int MAX_INC_POLY_INDEX = MAX_POLYGON_SIDES - 3; // For use with <=
    public static final int MAX_EXC_POLY_INDEX = MAX_POLYGON_SIDES - 2; // For use with <  and  array allocation size

    public static int GetPolygonIndex(int numSides) { return numSides - 3; }

    protected ArrayList<Point> points;
    protected ArrayList<Segment> orderedSides; 
    protected ArrayList<Angle> angles;
    protected double area;
    
    public ArrayList<Point> getPoints() { return points; }
    public ArrayList<Segment> getOrderedSides() { return orderedSides; }
    public ArrayList<Angle> getAngles() { return angles; }
    public double getArea() { return area;}
    
    
    

    public Polygon() { }

    public Polygon(Segment s1, Segment s2, Segment s3)
    {
        orderedSides = new ArrayList<Segment>();
        orderedSides.add(s1);
        orderedSides.add(s2);
        orderedSides.add(s3);

        Pair<ArrayList<Point>, ArrayList<Angle>> pair = MakePointsAngle(orderedSides);

        points = pair.getKey();
        angles = pair.getValue();

        thisAtomicRegion = new ShapeAtomicRegion(this);

//       this.FigureSynthesizerConstructor();
    }

    protected Polygon(ArrayList<Segment> segs, ArrayList<Point> pts, ArrayList<Angle> angs)
    {
        orderedSides = segs;
        points = pts;
        angles = angs;

        thisAtomicRegion = new ShapeAtomicRegion(this);

//        this.FigureSynthesizerConstructor();
    }

    //
    // How many of the points that define this polygon were generated by the Point Factory (as opposed to defined in the problem).
    //
    public int GeneratedPointCount()
    {
        int count = 0;
        for (Point pt : points)
        {
            if (PointFactory.isGenerated(pt)) count++;
        }
        return count;
    }

    public boolean HasSegment(Segment thatSegment)
    {
        return utilities.ast_helper.Utilities.hasStructurally(orderedSides, thatSegment);
    }

    @Override
    public boolean PointLiesInside(Point pt)
    {
        if (pt == null) return false;

        if (PointLiesOn(pt)) return false;
        
        return IsInPolygon(pt);
    }

    @Override
    public boolean PointLiesInOrOn(Point pt)
    {
        if (IsInPolygon(pt)) return true;

        for (Segment side : orderedSides)
        {
            if (side.PointLiesOnAndBetweenEndpoints(pt)) return true;
        }

        return false;
    }
    
    @Override
    public boolean PointLiesOn(Point pt)
    {
        for (Segment side : orderedSides)
        {
            if (side.PointLiesOnAndBetweenEndpoints(pt)) return true;
        }

        return false;
    }
    
    @Override
    public Polygon GetPolygonalized() { return this; }

//    @Override
    public void FindIntersection(Segment that, OutSingle<Point> inter1, OutSingle<Point> inter2) throws Exception
    {
        inter1 = null;
        inter2 = null;

        Point foundInter = null;
        ArrayList<Point> intersections = new ArrayList<Point>();
        for (Segment side : orderedSides)
        {
            if (side.IsCollinearWith(that))
            {
                if (that.PointLiesOnAndBetweenEndpoints(side.getPoint1())) Utilities.addStructurallyUnique(intersections, side.getPoint1());
                if (that.PointLiesOnAndBetweenEndpoints(side.getPoint2())) Utilities.addStructurallyUnique(intersections, side.getPoint2());

                if (side.PointLiesOnAndBetweenEndpoints(that.getPoint1())) Utilities.addStructurallyUnique(intersections, that.getPoint1());
                if (side.PointLiesOnAndBetweenEndpoints(that.getPoint2())) Utilities.addStructurallyUnique(intersections, that.getPoint2());
            }
            else
            {
                foundInter = side.FindIntersection(that);

                // Is the intersection in the middle of the segments?
                if (side.PointLiesOnAndBetweenEndpoints(foundInter) && that.PointLiesOnAndBetweenEndpoints(foundInter))
                {
                    // A segment may intersect a polygon through up to 2 vertices creating 4 intersections.
                    if (!Utilities.hasStructurally(intersections, foundInter)) intersections.add(foundInter);
                }
            }
        }
        if (!(this instanceof ConcavePolygon) && intersections.size() > 2)
        {
            throw new Exception("A segment intersecting a polygon may have up to 2 intersection points, not: " + intersections.size());
        }

        if (!intersections.isEmpty()) inter1.set(intersections.get(0));
        if (intersections.size() > 1) inter2.set(intersections.get(1));
    }

    //
    // Return the side that overlaps.
    //
    public Segment Overlap(Segment that)
    {
        for (Segment side : orderedSides)
        {
            if (side.CoincidingWithOverlap(that)) return side;
        }

        return null;
    }

    /// <summary>
    /// Make a set of connections for atomic region analysis.
    /// </summary>
    /// <returns></returns>
    @Override
    public ArrayList<Connection> MakeAtomicConnections()
    {
        ArrayList<Connection> connections = new ArrayList<Connection>();

        for (Segment side : orderedSides)
        {
            connections.add(new Connection(side.getPoint1(), side.getPoint2(), ConnectionType.SEGMENT, side));
        }

        return connections;
    }

    /// <summary>
    /// Determines if the given point is inside the polygon; http://alienryderflex.com/polygon/
    /// </summary>
    /// <param name="polygon">the vertices of polygon</param>
    /// <param name="testPoint">the given point</param>
    /// <returns>true if the point is inside the polygon; otherwise, false</returns>
    public boolean IsInPolygon(Point thatPoint)
    {
        if (thatPoint == null)
        {
//            System.Diagnostics.Debug.WriteLine(new System.Diagnostics.StackTrace(true).ToString());
            throw new IllegalArgumentException("Null passed to isInPolygon");
        }

        boolean result = false;
        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++)
        {
            if (points.get(i).getY() < thatPoint.getY() &&
                points.get(j).getY() >= thatPoint.getY() || points.get(j).getY() < thatPoint.getY() &&
                                              points.get(i).getY() >= thatPoint.getY())
            {
                if (points.get(i).getX() + (thatPoint.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) * (points.get(j).getX() - points.get(i).getX()) < thatPoint.getX())
                {
                    result = !result;
                }
            }
            j = i;
        }
        return result;
    }

    //
    // Is this polygon stronger than that?
    // That is, triangle -> isosceles -> equilateral.
    //
    public boolean IsStrongerThan(Polygon that) { return false; }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Polygon(");
        for (int p = 0; p < points.size(); p++)
        {
            str.append(points.get(p).toString());
            if (p < points.size() - 1) str.append(", ");
        }
        str.append(")");

        return str.toString();
    }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Polygon(");
        for (int p = 0; p < points.size(); p++)
        {
            str.append(points.get(p).CheapPrettyString());
        }
        str.append(")");

        return str.toString();
    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }

    // Parallel arrays of (1) vertices and (2) segments that share that given vertex.
    public static Polygon ConstructPolygon(ArrayList<Point> vertices, ArrayList<Pair<Segment, Segment>> pairs) throws Exception
    {
        ArrayList<Segment> orderedSides = new ArrayList<Segment>();

        // Follow the trail of sides starting at one of the first sides
        Segment currentSide = pairs.get(0).getKey();
        orderedSides.add(currentSide);
        Point currentVertex = currentSide.getPoint1();

        for (int v = 1; v < vertices.size(); v++)
        {
            // Where is the current vertex located?
            int nextVertexIndex = vertices.indexOf(currentVertex);

            // Find the next side to follow.
            if (pairs.get(nextVertexIndex).getKey().StructurallyEquals(currentSide))
            {
                currentSide = pairs.get(nextVertexIndex).getValue();
            }
            else
            {
                currentSide = pairs.get(nextVertexIndex).getKey();
            }
            orderedSides.add(currentSide);

            // Find the next vertex (moving along the next side)
            currentVertex = currentSide.OtherPoint(currentVertex);
        }

        if (orderedSides.size() != vertices.size())
        {
            throw new Exception("Construction of new polygon failed.");
        }

        return ActuallyConstructThePolygonObject(orderedSides);
    }

    private static Polygon ActuallyConstructThePolygonObject(ArrayList<Segment> orderedSides)
    {
        //
        // Check for lines that are actually collinear (and can be compressed into a single segment).
        //
        boolean change = true;
        while (change)
        {
            change = false;
            for (int s = 0; s < orderedSides.size(); s++)
            {
                Segment first = orderedSides.get(s);
                Segment second = orderedSides.get((s + 1) % orderedSides.size());
                Point shared = first.SharedVertex(second);

                // We know these lines share an endpoint and that they are collinear.
                if (first.IsCollinearWith(second))
                {
                    Segment newSegment = new Segment(first.OtherPoint(shared), second.OtherPoint(shared));

                    // Replace the two original lines with the new line.
                    orderedSides.add(s, newSegment);
                    orderedSides.remove(first);
                    orderedSides.remove(second);
                    change = true;
                }
            }
        }

        Pair<ArrayList<Point>, ArrayList<Angle>> pair = MakePointsAngle(orderedSides);

        // If the polygon is concave, make that object.
        if (IsConcavePolygon(pair.getKey())) return new ConcavePolygon(orderedSides, pair.getKey(), pair.getValue());

        // Otherwise, make the other polygons
        switch (orderedSides.size())
        {
            case 3:
                return new Triangle(orderedSides);
            case 4:
                return Quadrilateral.GenerateQuadrilateral(orderedSides);
            default:
                return new Polygon(orderedSides, pair.getKey(), pair.getValue());
        }

        //return null;
    }

    //
    // Return True if the polygon is convex.
    // http://blog.csharphelper.com/2010/01/04/determine-whether-a-polygon-is-convex-in-c.aspx
    //
    protected static boolean IsConcavePolygon(ArrayList<Point> orderedPts)
    {
        // For each set of three adjacent points A, B, C,
        // find the dot product AB � BC. If the sign of
        // all the dot products is the same, the angles
        // are all positive or negative (depending on the
        // order in which we visit them) so the polygon
        // is convex.
        boolean got_negative = false;
        boolean got_positive = false;
        int B, C;
        for (int A = 0; A < orderedPts.size(); A++)
        {
            B = (A + 1) % orderedPts.size();
            C = (B + 1) % orderedPts.size();

            // Create normalized vectors and find the cross-product.
            Point vec1 = Point.MakeVector(orderedPts.get(A), orderedPts.get(B));
            Point vec2 = Point.MakeVector(orderedPts.get(B), orderedPts.get(C));

            double cross_product = Point.CrossProduct(vec1, vec2);

            if (cross_product < 0)
            {
                got_negative = true;
            }
            else if (cross_product > 0)
            {
                got_positive = true;
            }
            if (got_negative && got_positive) return true;
        }

        // If we got this far, the polygon is convex.
        return false;
    }

    protected static Pair<ArrayList<Point>, ArrayList<Angle>> MakePointsAngle(ArrayList<Segment> orderedSides)
    {
        ArrayList<Point> points = new ArrayList<Point>();
        ArrayList<Angle> angles = new ArrayList<Angle>();

        for (int s = 0; s < orderedSides.size(); s++)
        {
            Point vertex = orderedSides.get(s).SharedVertex(orderedSides.get(s + 1 == orderedSides.size() ? 0 : s + 1));

            points.add(vertex);
            angles.add(new Angle(orderedSides.get(s), orderedSides.get(s + 1 == orderedSides.size() ? 0 : s + 1)));
        }

        return new Pair<ArrayList<Point>, ArrayList<Angle>>(points, angles);
    }

    //
    // Returns whether this list of segments can be used to construct a valid polygon.
    // Criteria:
    //   * All vertices are exactly of degree 2.
    //
    // We assume filtration of crossing and coinciding segments
    //
    public static Polygon MakePolygon(ArrayList<Segment> theseSegs) throws Exception
    {
        // Parallel arrays of (1) vertices and (2) segments that share the given vertex.
        ArrayList<Point> vertices = new ArrayList<Point>();
        ArrayList<Pair<Segment, Segment>> pairs = new ArrayList<Pair<Segment, Segment>>();

        for (int s1 = 0; s1 < theseSegs.size() - 1; s1++)
        {
            for (int s2 = s1 + 1; s2 < theseSegs.size(); s2++)
            {
                Point vertex = theseSegs.get(s1).SharedVertex(theseSegs.get(s2));

                // We have a shared vertex
                if (vertex != null)
                {
                    // Shared vertices must be unique among all combinations of segments
                    // if this vertex is already in the list, we don't have a polygon
                    if (vertices.contains(vertex))
                    {
                        return null;
                    }

                    // We have a candidate vertex: save the vertex and the 2 segments which created it.
                    vertices.add(vertex);
                    pairs.add(new Pair<Segment, Segment>(theseSegs.get(s1), theseSegs.get(s2)));
                }
            }
        }

        // We must have the same number of vertices as input segments (ensures degree 2 for all vertices)
        if (vertices.size() != theseSegs.size()) return null;

        //
        // Walk around the set of ordered sides.
        // Those sides should not pass through a previous vertex.
        //
        //
        //        /\
        //       /  \
        //      /    \____
        //     /    / \   |
        //    /____/   \__|
        //
        //
        //
        // Interioir tangent circles are such that the approximations are extremely close to each other.
        // Do not perform this check if this polygon construction is an approximation.
        //
        if (theseSegs.size() < Point.NUM_SEGS_TO_APPROX_ARC)
        {
            for (Segment side : theseSegs)
            {
                for (Point vertex : vertices)
                {
                    if (side.pointLiesOnAndExactlyBetweenEndpoints(vertex))
                    {
                        return null;
                    }
                }
            }
        }

        // If we are given the sides already ordered, just make the polygon straight-away.
        Polygon simple = MakeOrderedPolygon(theseSegs);
        if (simple != null) return simple;

        // These segments make a polygon; the Polygon class will order the segments appropriately.
        return Polygon.ConstructPolygon(vertices, pairs);
    }

    //
    // If we are given the sides already ordered, just make the polygon stright-away.
    //
    public static Polygon MakeOrderedPolygon(ArrayList<Segment> theseSegs)
    {
        for (int s = 0; s < theseSegs.size(); s++)
        {
            if (theseSegs.get(s).SharedVertex(theseSegs.get((s+1) % theseSegs.size())) == null) return null;
        }

        return ActuallyConstructThePolygonObject(theseSegs);
    }

    //
    // The center lies inside the polygon and there are no intersection points with the sides.
    //
    private boolean ContainsCircle(Circle that)
    {
        for (Segment side : orderedSides)
        {
            Point pt1 = null;
            Point pt2 = null;
            OutPair<Point, Point> out = new OutPair<Point, Point>(pt1, pt2);
            that.FindIntersection(side, out );
            pt1 = out.getKey();
            pt2 = out.getValue();
            if (pt1 != null && pt2 != null) return false;
        }

        //return that.PointLiesInside(that.center);
        return this.PointLiesInside(that.getCenter());
    }

    private boolean ContainsPolygon(Polygon that)
    {
        //
        // All points are interior to the polygon.
        //
        for (Point thatPt : that.points)
        {
            if (!this.PointLiesInOrOn(thatPt)) return false;
        }

        //
        // Check that all intersections so that there are no crossings.
        //
        for (Segment thisSide : this.orderedSides)
        {
            for (Segment thatSide : that.orderedSides)
            {
                if (thisSide.LooseCrosses(thatSide)) return false;
            }
        }

        return true;
    }

    //
    // that Polygon lies within this circle.
    //
    private boolean ContainsSector(Sector that)
    {
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint1())) return false;
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint2())) return false;

        if (!this.PointLiesInOrOn(that.theArc.getCircle().getCenter())) return false;

        if (that.theArc instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)that.theArc;
            if (!this.PointLiesInOrOn(semi.getMiddlePoint())) return false;
            if (!this.PointLiesInOrOn(semi.getCircle().Midpoint(semi.getEndpoint1(), semi.getMiddlePoint()))) return false;
            if (!this.PointLiesInOrOn(semi.getCircle().Midpoint(semi.getEndpoint2(), semi.getMiddlePoint()))) return false;
        }
        else
        {
            if (!this.PointLiesInOrOn(that.theArc.Midpoint())) return false;
        }
        // Check all point approximations for containment.
        //List<Point> approx = that.GetFigureAsAtomicRegion().GetVertices();
        //foreach (Point pt in approx)
        //{
        //    if (!this.PointLiesInOrOn(pt)) return false;
        //}

        return true;
    }

    //
    // A shape within this shape?
    //
    @Override
    public boolean Contains(Figure that)
    {
        if (that instanceof Circle) return ContainsCircle((Circle)that);
        if (that instanceof Polygon) return ContainsPolygon((Polygon)that);
        if (that instanceof Sector) return ContainsSector((Sector)that);

        return false;
    }

    //
    // Find the points of intersection of two polygons.
    //
    public ArrayList<Point> FindIntersections(Polygon that) throws Exception
    {
        ArrayList<Point> intersections = new ArrayList<Point>();

        for (Segment thatSide : that.orderedSides)
        {
            Point pt1 = null;
            Point pt2 = null;

            OutPair<Point, Point> out = new OutPair<Point, Point>(pt1, pt2);
            that.FindIntersection(thatSide, out );
            pt1 = out.getKey();
            pt2 = out.getValue();
            if (pt1 != null) Utilities.addStructurallyUnique(intersections, pt1);
            if (pt2 != null) Utilities.addStructurallyUnique(intersections, pt2);
        }

        return intersections;
    }

    //
    // Find the points of intersection of a polygon and a circle.
    //
    public ArrayList<Point> FindIntersections(Circle that)
    {
        ArrayList<Point> intersections = new ArrayList<Point>();

        for (Segment side : orderedSides)
        {
            Point pt1 = null;
            Point pt2 = null;

            OutPair<Point, Point> out = new OutPair<Point, Point>(pt1, pt2);
            that.FindIntersection(side, out );
            pt1 = out.getKey();
            pt2 = out.getValue();

            if (pt1 != null && side.PointLiesOnAndBetweenEndpoints(pt1)) Utilities.addStructurallyUnique(intersections, pt1);
            if (pt2 != null && side.PointLiesOnAndBetweenEndpoints(pt2)) Utilities.addStructurallyUnique(intersections, pt2);
        }

        return intersections;
    }

    public boolean HasSamePoints(Polygon that)
    {
        if (that == null) return false;

        if (this.points.size() != that.points.size()) return false;
        
        return false;

//        return Utilities.equalSets(this.points, that.points);
    }

    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Polygon)) return false;
        if (obj instanceof Quadrilateral) return false;  
        Polygon thatPoly = (Polygon)obj;

        

        for (Point pt : thatPoly.points)
        {
            if (!Utilities.hasStructurally(this.points, pt)) return false; 
        }

        return true;
    }

    @Override
    public boolean Equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Polygon)) return false;
        Polygon thatPoly = (Polygon)obj;

        for (Point pt : thatPoly.points)
        {
            if (!(this.points.contains(pt))) return false;
        }

        return true;
    }

    //
    // Construct the array of polygons. This data structure will be the same across a figure.
    //
    public static ArrayList<ArrayList<Polygon>> ConstructPolygonContainer()
    {
        ArrayList<ArrayList<Polygon>> polygons;
        polygons = new ArrayList<ArrayList<Polygon>>();

        for (int n = Polygon.MIN_POLY_INDEX; n < Polygon.MAX_EXC_POLY_INDEX; n++)
        {
            polygons.add(n, new ArrayList<Polygon>());
        }

        return polygons;
    }

    //
    // Does this particular segment intersect one of the sides.
    //
    public boolean Covers(Segment that)
    {
        for (Segment side : orderedSides)
        {
            if (side.Covers(that)) return true;
        }

        return false;
    }

    //
    // An arc is covered if one side of the polygon defines the endpoints of the arc.
    //
    public boolean Covers(Arc that)
    {
        for (Segment side : orderedSides)
        {
            if (side.Covers(that)) return true;
        }

        return false;
    }

    //
    // Does the atom have a connection which intersects the sides of the polygon.
    //
    @Override
    public boolean Covers(AtomicRegion atom)
    {
        for (Connection conn : atom.getConnections())
        {
            if (conn.type == ConnectionType.SEGMENT)
            {
                if (Covers((Segment)conn.segmentOrArc)) return true;
            }
            else if (conn.type == ConnectionType.ARC)
            {
                if (Covers((Arc)conn.segmentOrArc  )) return true;
            }
        }

        return false;
    }

}
