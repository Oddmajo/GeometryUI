package backend.ast.figure.delegates;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutPair;

public class MidpointDelegate
{
    private MidpointDelegate() {}

    /*
     * @param x1 -- represents a coordinate value
     * @param x2 -- represents a coordinate value
     *  @return the one-dimensional midpoint value (average of the values)
     */
    public static double Midpoint(double x1, double x2) { return (x1 + x2) / 2.0; }

    /*
     * @param s -- a segment (finite)
     *  @return the midpoint between the endpoints of the @segment
     */
    public static Point getMidpoint(Segment s)
    {
        return new Point("", Midpoint(s.getPoint1().getX(), s.getPoint2().getX()),
                             Midpoint(s.getPoint1().getY(), s.getPoint2().getY()));
    }

    /*
     * @param a -- a point on this circle
     * @param b -- a point on this circle
     *  @return the midpoint between @a and @b on the circle.
     */
    public static Point Midpoint(Circle circle, Point a, Point b)
    {
        if (!circle.pointLiesOn(a)) return null;
        if (!circle.pointLiesOn(b)) return null;

        // Make the chord.
        Segment chord = new Segment(a, b);

        Point pt1 = null;
        Point pt2 = null;

        // Is this a diameter? If so, quickly return a point perpendicular to the diameter
        if (circle.DefinesDiameter(chord))
        {
            Segment perp = chord.getPerpendicularThrough(circle.getCenter());

            OutPair<Point, Point> out = new OutPair<Point, Point>();
            circle.findIntersection(perp, out);
            pt1 = out.first();
            pt2 = out.second();

            // Arbitrarily choose one of the points.
            return pt1 != null ? pt1 : pt2;
        }

        // Make radius through the midpoint of the chord.
        Segment radius = new Segment(circle.getCenter(), chord.getMidpoint());

        OutPair<Point, Point> out = new OutPair<Point, Point>();
        circle.findIntersection(radius, out);
        pt1 = out.first();
        pt2 = out.second();

        if (pt2 == null) return pt1;

        Point theMidpoint = Arc.StrictlyBetweenMinor(pt1, new MinorArc(circle, a, b)) ? pt1 : pt2;

        double angle1 = new Angle(a, circle.getCenter(), theMidpoint).getMeasure();
        double angle2 = new Angle(b, circle.getCenter(), theMidpoint).getMeasure();
        if (!backend.utilities.math.MathUtilities.doubleEquals(angle1, angle2))
        {
            ExceptionHandler.throwException( new ArgumentException("Midpoint is incorrect; angles do not equate: " + angle1 + " " + angle2));
        }

        return theMidpoint;
    }

    /*
     * @param a -- a point on this circle
     * @param b -- a point on this circle
     * @param sameSide -- a point on the side of the arc we wish to acquire the midpoint
     *  @return the midpoint between @a and @b on the circle (and on the same side as @sameSide).
     */
    public static Point Midpoint(Circle circle, Point a, Point b, Point sameSide)
    {
        Point midpt = Midpoint(circle, a, b);

        Segment segment = new Segment(a, b);
        Segment other = new Segment(midpt, sameSide);

        Point intersection = segment.lineIntersection(other);

        if (Segment.Between(intersection, midpt, sameSide)) return circle.OppositePoint(midpt);

        return midpt;
    }
}
