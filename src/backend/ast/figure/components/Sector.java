package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.Figure;
import backend.ast.figure.Polygonalizable;
import backend.ast.figure.Shape;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.arcs.Semicircle;
import backend.ast.figure.components.polygon.Polygon;
import backend.atoms.components.Connection;
import backend.atoms.components.Connection.ConnectionType;
import backend.utilities.Pair;
import backend.utilities.translation.OutPair;

public class Sector extends Shape
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
        radius1 = new Segment(theArc.getCircle().getCenter(), theArc.getEndpoint1());
        radius2 = new Segment(theArc.getCircle().getCenter(), theArc.getEndpoint2());

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
    

    @Override
    public Polygon getPolygon() { return GetPolygonalized(); }

    @Override
    public boolean contains(Polygonalizable p)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /// <summary>
    /// Make a set of connections for atomic region analysis.
    /// </summary>
    /// <returns></returns>
    @Override
    public ArrayList<Connection> MakeAtomicConnections()
    {
        ArrayList<Connection> connections = new ArrayList<Connection>();

        connections.add(new Connection(theArc.getCircle().getCenter(), theArc.getEndpoint1(), backend.atoms.components.Connection.ConnectionType.SEGMENT, radius1));
        connections.add(new Connection(theArc.getCircle().getCenter(), theArc.getEndpoint2(), backend.atoms.components.Connection.ConnectionType.SEGMENT, radius2));

        connections.add(new Connection(theArc.getEndpoint1(), theArc.getEndpoint2(), ConnectionType.ARC, this.theArc));

        return connections;
    }

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
    //@Override
    public boolean pointLiesInside(Point pt)
    {
        // Is the point : the sector's circle?
        if (!theArc.getCircle().pointLiesInside(pt)) return false;

        // Radii
        if (radius1.pointLiesBetweenEndpoints(pt)) return false;
        if (radius2.pointLiesBetweenEndpoints(pt)) return false;

        //
        // For the Minor Arc, create two angles.
        // The sum must equal the measure of the angle created by the endpoints.
        //
        double originalMinorMeasure = theArc.getMinorMeasure();
        double centralAngle1 = new Angle(theArc.getEndpoint1(), theArc.getCircle().getCenter(), pt).getMeasure();
        double centralAngle2 = new Angle(theArc.getEndpoint2(), theArc.getCircle().getCenter(), pt).getMeasure();

        boolean isInMinorArc = backend.utilities.math.MathUtilities.doubleEquals(theArc.getMinorMeasure(), centralAngle1 + centralAngle2);

        if (theArc instanceof MinorArc) return isInMinorArc;

        if (theArc instanceof MajorArc) return !isInMinorArc;

        if (theArc instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)theArc;

            // The point : question must lie on the same side of the diameter as the middle point
            Segment candSeg = new Segment(pt, semi.getMiddlePoint());

            Point intersection = semi.getDiameter().lineIntersection(candSeg);

            return !candSeg.pointLiesBetweenEndpoints(intersection);
        }

        return false;
    }

    //
    // Point instanceof on the perimeter?
    //
    @Override
    public boolean pointLiesOn(Point pt)
    {
        if (pt == null) return false;

        // Radii
        Pair<Segment, Segment> radii = theArc.GetRadii();
        if (radii.getKey().pointLiesBetweenEndpoints(pt) || radii.getValue().pointLiesBetweenEndpoints(pt)) return true;

        // This point must lie on the circle : question, minimally.
        if (!theArc.getCircle().pointLiesOn(pt)) return false;

        // Arc
        if (theArc instanceof MajorArc) return Arc.BetweenMajor(pt, (MajorArc)theArc);
        else if (theArc instanceof MinorArc) return Arc.BetweenMinor(pt, (MinorArc)theArc);
        else if (theArc instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)theArc;

            // The point : question must lie on the same side of the diameter as the middle point
            Segment candSeg = new Segment(pt, semi.getMiddlePoint());

            Point intersection = semi.getDiameter().lineIntersection(candSeg);

            return !candSeg.pointLiesBetweenEndpoints(intersection);
        }

        return false;
    }

    //@Override
    public boolean PointLiesInOrOn(Point pt)
    {
        if (pt == null) return false;

        return pointLiesOn(pt) || pointLiesInside(pt);
    }



    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Sector)) return false;
        Sector sector = (Sector)obj;

        return theArc.structurallyEquals(sector.theArc);
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
        theArc.getEndpoint1().SimpleToString() + theArc.getCircle().getCenter().CheapPrettyString() + theArc.getEndpoint2().SimpleToString() + ")";
    }


//    //
//    // Does this particular segment intersect one of the sides.
//    //
//    public boolean Covers(Segment that)
//    {
//        if (radius1.Covers(that)) return true;
//
//        if (radius2.Covers(that)) return true;
//
//        return theArc.Covers(that);
//    }
//
//    //
//    // An arc is covered if one side of the polygon defines the endpoints of the arc.
//    //
//    public boolean Covers(Arc that)
//    {
//        if (radius1.Covers(that)) return true;
//
//        if (radius2.Covers(that)) return true;
//
//        return theArc.Covers(that);
//    }

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