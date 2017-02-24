package backend.ast.figure.delegates.intersections;

import java.util.ArrayList;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.arcs.Semicircle;
import backend.ast.figure.components.polygon.ConcavePolygon;
import backend.ast.figure.components.polygon.Polygon;
import backend.ast.figure.delegates.FigureDelegate;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutPair;

public class IntersectionDelegate extends FigureDelegate
{
    /*
     * <Line, Line> Intersection (assumes infinite treatment)
     */
    public static Point lineIntersection(Segment thisS, Segment that)
    {
        return LineIntersectionDelegate.intersection(thisS, that);
    }

    /*
     * <Segment, Segment> Intersection (finite treatment of segments)
     */
    public static Point segmentIntersection(Segment thisS, Segment that)
    {
        Point p = SegmentIntersectionDelegate.findIntersection(thisS, that);

        if(p != null) return p;

        return null;
    }

    //
    //
    // Polygons
    //
    //
    
    /*
     * <Polygon, Segment> Intersection
     */
    public static boolean findIntersection(Polygon polygon, Segment that, OutPair<Point, Point> inters)
    {
        inters = new OutPair<Point, Point>();

        Point foundInter = null;
        ArrayList<Point> intersections = new ArrayList<Point>();
        for (Segment side : polygon.getOrderedSides())
        {
            if (side.isCollinearWith(that))
            {
                if (that.pointLiesBetweenEndpoints(side.getPoint1())) Utilities.AddStructurallyUnique(intersections, side.getPoint1());
                if (that.pointLiesBetweenEndpoints(side.getPoint2())) Utilities.AddStructurallyUnique(intersections, side.getPoint2());

                if (side.pointLiesBetweenEndpoints(that.getPoint1())) Utilities.AddStructurallyUnique(intersections, that.getPoint1());
                if (side.pointLiesBetweenEndpoints(that.getPoint2())) Utilities.AddStructurallyUnique(intersections, that.getPoint2());
            }
            else
            {
                foundInter = side.segmentIntersection(that);

                // A segment may intersect a polygon through up to 2 vertices creating 4 intersections.
                if (!Utilities.HasStructurally(intersections, foundInter)) intersections.add(foundInter);
            }
        }

        if (!(polygon instanceof ConcavePolygon) && intersections.size() > 2)
        {
            ExceptionHandler.throwException( new Exception("A segment intersecting a polygon may have up to 2 intersection points, not: " + intersections.size()));
        }

        if (intersections.isEmpty()) return false;

        if (intersections.size() > 1)
        {
            inters.set(intersections.get(0), intersections.get(1));
            return true;
        }

        if (!intersections.isEmpty())
        {
            inters.set(intersections.get(0), null);
            return true;
        }

        return false;
    }

    //
    //
    // Circles
    //
    //
    
    /*
     * <Circle, Segment> Intersection
     *  Points of intersection of one circle and one segment may be 0, 1, or 2.
     */
    public static boolean findIntersection(Circle circle, Segment ts, OutPair<Point, Point> out)
    {
        Point inter1 = null;
        Point inter2 = null;

        // Make a copy
        Segment s = new Segment(ts.getPoint1(), ts.getPoint2());

        double radius = circle.getRadius();
        Point center = circle.getCenter();

        // SEE: http://stackoverflow.com/questions/1073336/circle-line-collision-detection

        // We have line AB, circle _center C, and _radius R.
        double lengthAB = s.length();
        double[] D = { (ts.getPoint2().getX() - ts.getPoint1().getX()) / lengthAB, (ts.getPoint2().getY() - ts.getPoint1().getY()) / lengthAB }; //Direction vector from A to B

        // Now the line equation is x = D.get(0)*t + A.getX(), y = D.get(1)*t + A.getY() with 0 <= t <= 1.
        double t = D[0] * (center.getX() - ts.getPoint1().getX()) + D[1] * (center.getY() - ts.getPoint1().getY()); //Closest point to circle center
        double[] E = { t * D[0] + ts.getPoint1().getX(), t * D[1] + ts.getPoint1().getY() }; //The point described by t.

        double lengthEC = Math.sqrt(Math.pow(E[0] - center.getX(), 2) + Math.pow(E[1] - center.getY(), 2));

        // Possible Intersection?
        if (lengthEC < radius)
        {
            // Compute distance from t to circle intersection point
            double dt = Math.sqrt(Math.pow(radius, 2) - Math.pow(lengthEC, 2));

            // First intersection - find and verify that the point lies on the segment
            Point possibleInter1 = new Point("", (t - dt) * D[0] + ts.getPoint1().getX(), (t - dt) * D[1] + ts.getPoint1().getY());
            /* if (ts.pointLiesBetweenEndpoints(possibleInter1)) */ inter1 = possibleInter1;

            // Second intersection - find and verify that the point lies on the segment
            Point possibleInter2 = new Point("", (t + dt) * D[0] + ts.getPoint1().getX(), (t + dt) * D[1] + ts.getPoint1().getY());
            /* if (ts.pointLiesBetweenEndpoints(possibleInter2)) */ inter2 = possibleInter2;
        }
        //
        // Tangent point (E)
        //
        else if (backend.utilities.math.MathUtilities.doubleEquals(lengthEC, radius))
        {
            // First intersection
            inter1 = new Point("", E[0], E[1]);
        }

        // Put the intersection into inter1 if there is only one intersection.
        if (inter1 == null && inter2 != null) { inter1 = inter2; inter2 = null; }

        out.set(inter1, inter2);

        return inter1 != null;
    }

    /*
     * <Circle, Circle> Intersection
     *  Points of intersection of two circles; may be 0, 1, or 2.
     *  Uses the technique found here: http://mathworld.wolfram.com/Circle-CircleIntersection.html
     */
    public static boolean findIntersection(Circle thisS, Circle that, OutPair<Point, Point> out)
    {
        Point inter1 = null;
        Point inter2 = null;

        Point thisCenter = thisS.getCenter();
        Point thatCenter = that.getCenter();
        double thisRadius = thisS.getRadius();
        double thatRadius = that.getRadius();

        // SEE: http://stackoverflow.com/questions/3349125/circle-circle-intersection-points

        // Distance between _centers
        double d = Math.sqrt(Math.pow(thatCenter.getX() - thisCenter.getX(), 2) +
                Math.pow(thatCenter.getY() - thisCenter.getY(), 2));

        // Separate circles
        if (d > thisRadius + thatRadius) { }

        // One circle contained : the other
        else if (d < Math.abs(thisRadius - thatRadius)) { }

        // Coinciding circles
        else if (d == 0 && thisRadius == thatRadius) { }

        // We have intersection(s)!
        else
        {
            // Distance from _center of this to midpt of intersections
            double a = (Math.pow(thisRadius, 2) - Math.pow(thatRadius, 2) + Math.pow(d, 2)) / (2 * d);

            // Midpoint of the intersections
            double[] midpt = { thisCenter.getX() + a * (thatCenter.getX() - thisCenter.getX()) / d,
                    thisCenter.getY() + a * (thatCenter.getY() - thisCenter.getY()) / d };

            // Distance from midpoint to intersections
            double h = Math.sqrt(Math.pow(thisRadius, 2) - Math.pow(a, 2));

            // Only one intersection
            if (h == 0) inter1 = new Point("", midpt[0], midpt[1]);
            // Two intersections
            else
            {
                inter1 = new Point("", midpt[0] + h * (thatCenter.getY() - thisCenter.getY()) / d,
                        midpt[1] - h * (thatCenter.getX() - thisCenter.getX()) / d);

                inter2 = new Point("", midpt[0] - h * (thatCenter.getY() - thisCenter.getY()) / d,
                        midpt[1] + h * (thatCenter.getX() - thisCenter.getX()) / d);
            }
        }

        // Put the intersection into inter1 if there is only one intersection.
        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        //
        // Are the circles close enough to merit one intersection point instead of two?
        // That is, are the intersection points the same (within epsilon)?
        //
        if (inter1 != null && inter2 != null)
        {
            if (inter1.structurallyEquals(inter2)) inter2 = null;
        }

        out.set(inter1,  inter2);

        return inter1 != null;
    }
    
    //
    //
    // Arcs
    //
    //
    
    //@Override
    public static boolean findIntersection(Arc thisA, Arc that, OutPair<Point, Point> out)
    {
        // Find the points of intersection
        OutPair<Point, Point> localOut = new OutPair<Point, Point>();
        thisA.getCircle().findIntersection(that.getCircle(), localOut);
        Point inter1 = localOut.first();
        Point inter2 = localOut.second();


        // The points must be on this minor arc.
        if (thisA instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, thisA)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, thisA)) inter2 = null;
        }
        else
        {
            if (!Arc.BetweenMajor(inter1, thisA)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, thisA)) inter2 = null;
        }

        // The points must be on thatArc
        if (that instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, that)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, that)) inter2 = null;
        }
        else
        {
            if (!Arc.BetweenMajor(inter1, that)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, that)) inter2 = null;
        }

        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        out.set(inter1, inter2);

        return inter1 != null;
    }

    //@Override
    public static boolean findIntersection(Arc thisA, Segment that, OutPair<Point, Point> out) // out Point inter1, out Point inter2)
    {
        // Find the points of intersection
        OutPair<Point, Point> localOut = new OutPair<Point, Point>();
        thisA.getCircle().findIntersection(that, localOut);
        Point inter1 = localOut.first();
        Point inter2 = localOut.second();

        // The points must be on this minor arc.
        if (thisA instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, thisA)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, thisA)) inter2 = null;
        }
        else if (thisA instanceof MajorArc)
        {
            if (!Arc.BetweenMajor(inter1, thisA)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, thisA)) inter2 = null;
        }
        else if (thisA instanceof Semicircle)
        {
            if (!((Semicircle)thisA).pointLiesOn(inter1)) inter1 = null;
            if (!((Semicircle)thisA).pointLiesOn(inter2)) inter2 = null;
        }

        if (!that.pointLiesBetweenEndpoints(inter1)) inter1 = null;
        if (!that.pointLiesBetweenEndpoints(inter2)) inter2 = null;

        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        out.set(inter1, inter2);
        
        return inter1 != null;
    }
}
