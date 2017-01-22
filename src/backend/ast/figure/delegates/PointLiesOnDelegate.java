package backend.ast.figure.delegates;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

/*
 * A delegate class used strictly for computing whether a point lies on a given figure (shape or other).
 * 
 * This is an attempt to localize all such functionality into one class.
 * 
 * Figures Covered:
 *     Circle
 *     Semicircle
 *     Polygons: 3, 4, 5, 6 sided
 *     Concave Polygons: 4, 5, 6 sided
 */
public final class PointLiesOnDelegate extends FigureDelegate
{
    public static boolean pointLiesOn(Figure fig, Point pt)
    {
        if (fig instanceof Segment) return pointLiesOn((Segment)fig, pt);
        else if (fig instanceof Circle) return pointLiesOn((Circle)fig, pt);
        else if (fig instanceof Polygon) return pointLiesOn((Circle)fig, pt);
        else if (fig instanceof Sector) return pointLiesOn((Sector)fig, pt);
        else if (fig instanceof Ray) return pointLiesOn((Ray)fig, pt);
        else ExceptionHandler.throwException(new IllegalArgumentException("Unrecognized Figure" + fig));
        
        return false;
    }
    
    /*
     * Circle: Uses substitution into standard form (x1 - x2)^2 + (y1 - y2)^2 = r^2
     */
    public static boolean pointLiesOn(Circle circle, Point pt)
    {
        Point center = circle.getCenter();
        double radius = circle.getRadius();
        
        return MathUtilities.doubleEquals(Math.pow(center.getX() - pt.getX(), 2) +
                                          Math.pow(center.getY() - pt.getY(), 2), Math.pow(radius, 2));
    }

    /*
     * Polygon
     */
    public static boolean pointLiesOn(Polygon polygon, Point pt)
    {
        for (Segment side : polygon.getOrderedSides())
        {
            if (side.pointLiesBetweenEndpoints(pt)) return true;
        }

        return false;
    }

    /*
     * Ray (half-finite / half-infinite)
     */
    public static boolean pointLiesOn(Ray ray, Point pt)
    {
        // Is the point on the infinite line?
        if (!pointLiesOn(ray.asLine(), pt)) return false;
        
        // Is pt not on the ray "to the right of the origin point"?
        // It's NOT if it is on the opposing side that the other point falls on; visually:
        //     pt     .--------------.------>
        //           origin        other
        //
        return !Segment.Between(ray.getOrigin(), pt, ray.getNonOrigin());
    }
    
    /*
     * Line (infinite)
     */
    public static boolean pointLiesOn(Segment s, Point pt)
    {
        // If the segments are vertical, just compare the X values of one point of each
        if (s.isVertical()) return MathUtilities.doubleEquals(s.getPoint1().getX(), pt.getX());

        // If the segments are horizontal, just compare the Y values of one point of each; this is redundant
        if (s.isHorizontal()) return MathUtilities.doubleEquals(s.getPoint2().getY(), pt.getY());

        // Verify that the point is on the line (using point-slope form of linear equation)
        return MathUtilities.doubleEquals(s.getPoint1().getY() - pt.getY(), s.slope() * (s.getPoint1().getX() - pt.getX()));
    }
    
    /*
     * Segment (finite)
     */
    public static boolean pointLiesOnSegment(Segment s, Point pt)
    {
        // Is the point on this line?
        if (!pointLiesOn(s, pt)) return false;
        
        // Is this point in the middle (or endpoint) of this segment
        return Segment.Between(pt, s.getPoint1(), s.getPoint2());
    }
}