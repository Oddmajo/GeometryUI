package backend.ast.figure.components;

import java.util.List;
import java.util.ArrayList;

import backend.ast.figure.Figure;
import backend.atoms.components.Connection;
import backend.utilities.Pair;
import backend.utilities.translation.OutPair;

public class Sector extends Figure
{
    protected Arc theArc;
    public Arc getArc() { return theArc; }

    protected Segment radius1;
    public Segment getRadius1() { return radius1; }

    protected Segment radius2;
    public Segment getRadius2() { return radius2; }

    public Sector(Arc a)
    {
        theArc = a;
        radius1 = new Segment(theArc._theCircle.getCenter(), theArc._endpoint1);
        radius2 = new Segment(theArc._theCircle.getCenter(), theArc._endpoint2);

        //thisAtomicRegion = new ShapeAtomicRegion(this);
    }

    //        public override List<Point> GetApproximatingPoints() { return theArc.GetApproximatingPoints(); }

    @Override
    public Polygon GetPolygonalized()
    {
        if (polygonalized != null) return polygonalized;

        ArrayList<Segment> sides = new ArrayList<Segment>(theArc.GetApproximatingSegments());

        sides.add(radius1);
        sides.add(radius2);

        // Make a polygon out of the radii and the sector
        polygonalized = Polygon.MakePolygon(sides);

        return polygonalized;
    }

    /// <summary>
    /// Make a set of connections for atomic region analysis.
    /// </summary>
    /// <returns></returns>
//    @Override
//    public List<Connection> MakeAtomicConnections()
//    {
//        List<Connection> connections = new ArrayList<Connection>();
//
//        connections.add(new Connection(theArc._theCircle.getCenter(), theArc.getEndpoint1(), atoms.components.SEGMENT, radius1));
//        connections.add(new Connection(theArc._theCircle.getCenter(), theArc.endpoint2, atoms.components.SEGMENT, radius2));
//
//        connections.add(new Connection(theArc.getEndpoint1(), theArc._endpoint2, ConnectionType.ARC, this.theArc));
//
//        return connections;
//    }

    @Override
    public ArrayList<Segment> Segmentize()
    {
        ArrayList<Segment> segments = new ArrayList<Segment>();

        // Add radii
        segments.add(radius1);
        segments.add(radius2);

        // Segmentize the arc
        segments.addAll(theArc.Segmentize());

        return segments;
    }

    //
    // Point must be : the given circle and then, specifically : the specified angle
    //
    @Override
    public boolean PointLiesInside(Point pt)
    {
        // Is the point : the sector's circle?
        if (!theArc._theCircle.PointLiesInside(pt)) return false;

        // Radii
        if (radius1.PointLiesOnAndBetweenEndpoints(pt)) return false;
        if (radius2.PointLiesOnAndBetweenEndpoints(pt)) return false;

        //
        // For the Minor Arc, create two angles.
        // The sum must equal the measure of the angle created by the endpoints.
        //
        double originalMinorMeasure = theArc.minorMeasure;
        double centralAngle1 = new Angle(theArc.getEndpoint1(), theArc._theCircle.getCenter(), pt).measure;
        double centralAngle2 = new Angle(theArc._endpoint2, theArc._theCircle.getCenter(), pt).measure;

        boolean isInMinorArc = backend.utilities.math.MathUtilities.doubleEquals(theArc.minorMeasure, centralAngle1 + centralAngle2);

        if (theArc instanceof MinorArc) return isInMinorArc;

        if (theArc instanceof MajorArc) return !isInMinorArc;

        if (theArc instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)theArc;

            // The point : question must lie on the same side of the diameter as the middle point
            Segment candSeg = new Segment(pt, semi.getMiddlePoint());

            Point intersection = semi._diameter.FindIntersection(candSeg);

            return !candSeg.PointLiesOnAndBetweenEndpoints(intersection);
        }

        return false;
    }

    //
    // Point instanceof on the perimeter?
    //
    @Override
    public boolean PointLiesOn(Point pt)
    {
        if (pt == null) return false;

        // Radii
        Pair<Segment, Segment> radii = theArc.GetRadii();
        if (radii.getKey().PointLiesOnAndBetweenEndpoints(pt) || radii.getValue().PointLiesOnAndBetweenEndpoints(pt)) return true;

        // This point must lie on the circle : question, minimally.
        if (!theArc._theCircle.PointLiesOn(pt)) return false;

        // Arc
        if (theArc instanceof MajorArc) return Arc.BetweenMajor(pt, (MajorArc)theArc);
        else if (theArc instanceof MinorArc) return Arc.BetweenMinor(pt, (MinorArc)theArc);
        else if (theArc instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)theArc;

            // The point : question must lie on the same side of the diameter as the middle point
            Segment candSeg = new Segment(pt, semi.getMiddlePoint());

            Point intersection = semi._diameter.FindIntersection(candSeg);

            return !candSeg.PointLiesOnAndBetweenEndpoints(intersection);
        }

        return false;
    }

    @Override
    public boolean PointLiesInOrOn(Point pt)
    {
        if (pt == null) return false;

        return PointLiesOn(pt) || PointLiesInside(pt);
    }

    //
    // The center lies inside the polygon and there are no intersection points with the sides.
    //
    private boolean ContainsCircle(Circle that)
    {
        // Center lies (strictly) inside of this sector
        if (!this.PointLiesInside(that.getCenter())) return false;

        // As a simple heuristic, the radii lengths must support inclusion.
        if (Point.calcDistance(this.theArc._theCircle.getCenter(), that.getCenter()) + that.getRadius() > this.theArc._theCircle.getRadius()) return false;

        //
        // Any intersections between the sides of the sector and the circle must be tangent.
        //
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        that.FindIntersection(radius1, out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        if (pt2 != null) return false;

        that.FindIntersection(radius2, out);
        pt1 = out.first();
        pt2 = out.second();
        
        if (pt2 != null) return false;

        that.FindIntersection(this.theArc, out);
        pt1 = out.first();
        pt2 = out.second();
        
        if (pt2 != null) return false;

        return true;
    }

    //
    // All points of the polygon are on or : the sector.
    // No need to check that any sides of the polygon pass
    // through the sector since that implies a vertex exterior to the sector.
    //
    private boolean ContainsPolygon(Polygon that)
    {
        for (Point thatPt : that.points)
        {
            if (!this.PointLiesInOrOn(thatPt)) return false;
        }

        for (Segment side : that.orderedSides)
        {
            if (!this.PointLiesInOrOn(side.Midpoint())) return false;
        }

        return true;
    }

    //
    // that Sector lies within this sector
    //
    private boolean ContainsSector(Sector that)
    {
        if (this.StructurallyEquals(that)) return true;

        //
        // Is this sector from the same circle as that sector?
        //
        if (this.theArc._theCircle.StructurallyEquals(that.theArc._theCircle))
        {
            for (Point pt : that.theArc.GetApproximatingPoints())
            {
                if (!this.PointLiesInOrOn(pt)) return false;
            }

            return true;
        }

        // this radius must be longer than that.
        if (backend.utilities.math.MathUtilities.greaterThan(that.theArc._theCircle.getRadius(), this.theArc._theCircle.getRadius())) return false;

        //
        // Check containment of the points of that sector.
        //
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint1())) return false;
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint2())) return false;

        if (!this.PointLiesInOrOn(that.theArc._theCircle.getCenter())) return false;

        // Check midpoint instanceof also within the sector.
        if (!this.PointLiesInOrOn(that.theArc.Midpoint())) return false;

        return true;
    }

//    //
//    // A shape within this shape?
//    //
//    @Override
//    public boolean Contains(Figure that)
//    {
//        if (that instanceof Circle) return ContainsCircle((Circle)that);
//        if (that instanceof Polygon) return ContainsPolygon((Polygon)that);
//        if (that instanceof Sector) return ContainsSector((Sector)that);
//
//        return false;
//    }

    //
    // Area-Related Computations
    //
    protected double Area(double radAngleMeasure, double radius)
    {
        return 0.5 * radius * radius * radAngleMeasure;
    }
    protected double RationalArea(double radAngleMeasure, double radius)
    {
        return Area(radAngleMeasure, radius) / Math.PI;
    }
//    @Override
//    public boolean IsComputableArea() { return true; }
//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Central Angle
//        if (known.GetAngleMeasure(this.theArc.GetCentralAngle()) < 0) return false;
//
//        // Radius / Circle 
//        return theArc._theCircle.CanAreaBeComputed(known);
//    }
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        if (theArc instanceof Semicircle) return ((Semicircle)theArc).GetArea(known);
//
//        // Central Angle; this instanceof minor arc measure by default
//        double angleMeasure = Angle.toRadians(known.GetAngleMeasure(this.theArc.GetCentralAngle()));
//
//        if (angleMeasure <= 0) return -1;
//
//        // Make a major arc measure, if needed.
//        if (theArc instanceof MajorArc) angleMeasure = 2 * Math.PI - angleMeasure;
//
//        // Radius / Circle
//        double circArea = theArc._theCircle.GetArea(known);
//
//        if (circArea <= 0) return -1;
//
//        // The area instanceof a proportion of the circle defined by the angle.
//        return (angleMeasure / (2 * Math.PI)) * circArea;
//    }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Sector)) return false;
        Sector sector = (Sector)obj;

        return theArc.StructurallyEquals(sector.theArc);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Sector)) return false;
        Sector sector = (Sector)obj;

        return theArc.equals(sector.theArc);
    }

    @Override
    public String toString() { return "Sector(" + theArc + ")"; }

    @Override
    public String CheapPrettyString()
    {
        String prefix = "";

        if (theArc instanceof MajorArc) prefix = "Major";
        if (theArc instanceof MinorArc) prefix = "Minor";
        if (theArc instanceof Semicircle) return theArc.CheapPrettyString();

        return prefix + "(" +
        theArc.getEndpoint1().SimpleToString() + theArc._theCircle.getCenter().CheapPrettyString() + theArc._endpoint2.SimpleToString() + ")";
    }

    //
    // Does this particular segment intersect one of the sides.
    //
    public boolean Covers(Segment that)
    {
        if (radius1.Covers(that)) return true;

        if (radius2.Covers(that)) return true;

        return theArc.Covers(that);
    }

    //
    // An arc is covered if one side of the polygon defines the endpoints of the arc.
    //
    public boolean Covers(Arc that)
    {
        if (radius1.Covers(that)) return true;

        if (radius2.Covers(that)) return true;

        return theArc.Covers(that);
    }

//    //
//    // Does the atom have a connection which intersects the sides of the polygon.
//    //
//    @Override
//    public boolean Covers(AtomicRegion atom)
//    {
//        for (Connection conn : atom.connections)
//        {
//            if (conn.type == atoms.components.SEGMENT)
//            {
//                if (this.Covers((Segment)conn.segmentOrArc)) return true;
//            }
//            else if (conn.type == atoms.components.ARC)
//            {
//                if (this.Covers((Arc)conn.segmentOrArc)) return true;
//            }
//        }
//
//        return false;
//    }
}