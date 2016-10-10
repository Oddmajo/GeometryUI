package concreteAST.Figure;
/**
 * @author Nick Celiberti
 * Needs AtomicRegion
 */
import java.util.ArrayList;

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

    protected ArrayList<Point> points ;
        public ArrayList<Point> getPoints() { return points; }
    protected ArrayList<Segment> orderedSides;
        public ArrayList<Segment> getOrderedSides() { return orderedSides; }
    protected ArrayList<Angle> angles;
        public ArrayList<Angle> getAngles() { return angles; }

    protected double area;
        public double getArea() { return area; }

        public Polygon() { }

        public Polygon(Segment s1, Segment s2, Segment s3) throws ArgumentException
        {
            orderedSides = new ArrayList<Segment>();
            orderedSides.add(s1);
            orderedSides.add(s2);
            orderedSides.add(s3);

            KVEntry<ArrayList<Point>, ArrayList<Angle>> pair = MakePointsAngle(orderedSides);

            points = pair.getKey();
            angles = pair.getValue();

            //thisAtomicRegion = new ShapeAtomicRegion(this);
            ////this.FigureSynthesizerConstructor();

            
        }
        
        protected Polygon(ArrayList<Segment> segs, ArrayList<Point> pts, ArrayList<Angle> angs)
        {
            orderedSides = segs;
            points = pts;
            angles = angs;

            thisAtomicRegion = new ShapeAtomicRegion(this);

            ////this.FigureSynthesizerConstructor();
        }

        protected static KVEntry<ArrayList<Point>, ArrayList<Angle>> MakePointsAngle(ArrayList<Segment> orderedSides) throws ArgumentException
        {
            ArrayList<Point> points = new ArrayList<Point>();
            ArrayList<Angle> angles = new ArrayList<Angle>();

            for (int s = 0; s < orderedSides.size(); s++)
            {
                Point vertex = orderedSides.get(s).SharedVertex(orderedSides.get(s + 1 == orderedSides.size() ? 0 : s + 1));

                points.add(vertex);
                angles.add(new Angle(orderedSides.get(s), orderedSides.get(s + 1 == orderedSides.size() ? 0 : s + 1)));
            }

            return new KVEntry<ArrayList<Point>,ArrayList<Angle>>(points, angles);
        }

        
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
    // Returns whether this list of segments can be used to construct a valid polygon.
    // Criteria:
    //   * All vertices are exactly of degree 2.
    //
    // We assume filtration of crossing and coinciding segments
    //
    public static Polygon MakePolygon(ArrayList<Segment> theseSegs) throws ArgumentException 
    {
     // Parallel arrays of (1) vertices and (2) segments that share the given vertex.
        ArrayList<Point> vertices = new ArrayList<Point>();
        ArrayList<KVEntry<Segment, Segment>> pairs = new ArrayList<KVEntry<Segment, Segment>>();

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
                    pairs.add(new KVEntry<Segment, Segment>(theseSegs.get(s1),theseSegs.get(s2)));
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
                    if (side.PointLiesOnAndExactlyBetweenEndpoints(vertex))
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

 // Parallel arrays of (1) vertices and (2) segments that share that given vertex.
    public static Polygon ConstructPolygon(ArrayList<Point> vertices, ArrayList<KVEntry<Segment, Segment>> pairs) throws ArgumentException
    {
        ArrayList<Segment> orderedSides = new ArrayList<Segment>();

        // Follow the trail of sides starting at one of the first sides
        Segment currentSide = pairs.get(0).getKey();
        orderedSides.add(currentSide);
        Point currentVertex = currentSide.Point1;

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
            throw new ArgumentException("Construction of new polygon failed.");
        }

        return ActuallyConstructThePolygonObject(orderedSides);
    }

    private static Polygon ActuallyConstructThePolygonObject(ArrayList<Segment> orderedSides) throws ArgumentException
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

        KVEntry<ArrayList<Point>, ArrayList<Angle>> pair = MakePointsAngle(orderedSides);

        // If the polygon is concave, make that object.
        if (IsConcavePolygon(pair.getKey()))
            {
            System.out.println("Is Concave");
                return new ConcavePolygon(orderedSides, pair.getKey(), pair.getValue());
            }

        // Otherwise, make the other polygons
        switch (orderedSides.size())
        {
            case 3:
                System.out.println("Its a Triangle");
                return new Triangle(orderedSides);
            case 4:
                System.out.println("Its a Quadrilateral");
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
        // find the dot product AB · BC. If the sign of
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

    private static Polygon MakeOrderedPolygon(ArrayList<Segment> theseSegs) throws ArgumentException
    {
        for (int s = 0; s < theseSegs.size(); s++)
        {
            if (theseSegs.get(s).SharedVertex(theseSegs.get((s+1) % theseSegs.size())) == null) return null;
        }

        return ActuallyConstructThePolygonObject(theseSegs);
    }

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

}
