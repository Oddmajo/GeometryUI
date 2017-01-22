package backend.ast.figure.delegates;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class TriangleDelegate
{
    private TriangleDelegate() {}

    /*
     * @param angle -- an angle not necessarily in the triangle
     * @return the angle in the triangle which normalizes with the given angle;
     * if no angle normalizes, null is returned
     */
    public static Angle normalize(Triangle tri, Angle angle)
    {
        if (tri.getAngleA().normalizesWith(angle)) return tri.getAngleA();
        if (tri.getAngleB().normalizesWith(angle)) return tri.getAngleB();
        if (tri.getAngleC().normalizesWith(angle)) return tri.getAngleC();

        return null;
    }

    /*
     * @param segment -- a segment not necessarily in the triangle, but contains the entire side of the triangle
     * @return the segment in the triangle which equates to the given segment;
     * if no segment normalizes, null is returned
     */
    public static Segment normalize(Triangle tri, Segment segment)
    {
        if (segment.contains(tri.getSegmentA())) return tri.getSegmentA();
        if (segment.contains(tri.getSegmentB())) return tri.getSegmentB();
        if (segment.contains(tri.getSegmentC())) return tri.getSegmentC();

        return null;
    }

    /*
     * @param triangle -- a triangle
     * @return the perimeter of the triangle (sum of the side lengths)
     */
    public static double perimeter(Triangle triangle)
    {
        return triangle.getSegmentA().length() + triangle.getSegmentB().length() + triangle.getSegmentC().length();
    }

    /*
     * @param tri -- a triangle
     * @return the set of three altitudes
     */
    public static List<Segment> altitudes(Triangle tri)
    {
        List<Segment> altitudes = new ArrayList<Segment>();

        altitudes.add(tri.altitude(tri.getSegmentA()));
        altitudes.add(tri.altitude(tri.getSegmentB()));
        altitudes.add(tri.altitude(tri.getSegmentC()));

        return altitudes;
    }

    /*
     * @param triangle -- a triangle
     * @param segment -- one of the sides of the @triangle
     * @return the altitude w.r.t @segment to be treated as a line
     * The algorithm is a slope-based analysis
     */
    public static Segment altitude(Triangle triangle, Segment segment)
    {
        // Normalize to this triangle (probably not needed)
        Segment normalized = triangle.normalize(segment);

        // Point opposite the given segment
        Point opposite = triangle.oppositePoint(normalized);

        // The actual altitude is the perpendicular through @opposite and @segment 
        Segment altitude = normalized.getPerpendicularThrough(opposite);

        return altitude;
    }

    /*
     * @return the set of 3 altitudes w.r.t to this triangle
     */
    public static List<Segment> altitudesOf(Triangle tri)
    {
        ArrayList<Segment> altitudes = new ArrayList<Segment>();
        
        for (Segment side : tri.getOrderedSides())
        {
            altitudes.add(tri.altitude(side));
        }
        
        return altitudes;
    }
    
    /*
     * @param tri -- a triangle
     * @param segment -- side of the triangle
     * @return is the given segment an exact altitude of this triangle?
     * http://www.mathopenref.com/coordcentroid.html
     */
    public static boolean isAltitude(Triangle tri, Segment segment)
    {
        return tri.altitudesOf().contains(segment);
    }  
    
    /*
     * @param triangle -- a triangle
     * @return the orthocenter of the given triangle (intersection point of three altitudes)
     */
    public static Point orthocenter(Triangle triangle)
    {
        List<Segment> altitudes = triangle.altitudes();

        // Find the first / last intersection point
        Point inter = altitudes.get(0).lineIntersection(altitudes.get(altitudes.size() - 1));

        // Compute intersections for error-checking
        for (int index = 0; index < altitudes.size() - 1; index++)
        {
            Point intersection = altitudes.get(index).lineIntersection(altitudes.get(index + 1));

            if (!inter.equals(intersection)) ExceptionHandler.throwException("Orthocenter intersection points do not align.");
        }

        return inter;        
    }

    /*
     * @return the circumcenter of the given triangle (intersection point of perpendicular bisectors)
     * or the center of a circumscribed circle.
     */
    public static Point circumcenter(Triangle tri)
    {
        Segment pbA = tri.getSegmentA().perpendicularBisector();
        Segment pbB = tri.getSegmentB().perpendicularBisector();
        Segment pbC = tri.getSegmentC().perpendicularBisector();

        // All 3 Intersection points of perpendicular bisectors 
        Point interAB = pbA.lineIntersection(pbB);
        Point interAC = pbA.lineIntersection(pbC);
        Point interBC = pbB.lineIntersection(pbC);

        // Error Check
        if (!interAB.equals(interAC)) ExceptionHandler.throwException("Circumcenter computation problem.");
        if (!interBC.equals(interAC)) ExceptionHandler.throwException("Circumcenter computation problem.");

        return interAB;
    }

    /*
     * @triangle -- a triangle
     * @param segment -- a side of the @triangle
     * @return perpendicular bisector to the given segment
     * The returned segment should be treated as a line (infinite)
     */
    public static Segment perpendicularBisector(Triangle triangle, Segment segment)
    {
        Segment normalized = triangle.normalize(segment);

        return normalized.perpendicularBisector();
    }

    /*
     * @return the incenter of the given triangle (intersection point of angle bisectors)
     * Coordinate-based calculation; can be used used for angle bisectors as well
     * Uses the formula: Coordinates of the incenter = ( (axa + bxb + cxc)/P , (aya + byb + cyc)/P ) where P is perimeter
     * https://www.easycalculation.com/analytical/learn-triangle-incenter.php
     */
    public static Point incenter(Triangle tri)
    {
        Point vertexA = tri.getPoint1();
        Point vertexB = tri.getPoint2();
        Point vertexC = tri.getPoint3();

        double a = tri.oppositeSide(vertexA).length();
        double b = tri.oppositeSide(vertexB).length();
        double c = tri.oppositeSide(vertexC).length();

        double p = tri.perimeter();

        double xCoord = (a * vertexA.getX() + b * vertexB.getX() + c * vertexC.getX()) / p;
        double yCoord = (a * vertexA.getY() + b * vertexB.getY() + c * vertexC.getY()) / p;

        return new Point("", xCoord, yCoord);
    }

    /*
     * @param angle -- a valid angle of the triangle
     * @return the angle bisector ray originating from the shared vertex of the given @angle
     * null if the given angle is not referring to an angle in the @triangle
     * The triangle delegate normalizes the given angle to the angle in the triangle before using the angle bisector.
     * Coordinate-based calculation which is based on the triangle incenter computation
     * Uses the formula: Coordinates of the incenter = ( (axa + bxb + cxc)/P , (aya + byb + cyc)/P ) where P is perimeter
     * https://www.easycalculation.com/analytical/learn-triangle-incenter.php
     */
    public static Ray angleBisector(Triangle tri, Angle angle)
    {
        // Normalize the given angle to this particular triangle
        Angle normalizedAngle = tri.normalize(angle);

        if (normalizedAngle == null) return null;

        return normalizedAngle.angleBisector();
    }

    /*
     * @return the centroid of this triangle (coordinate-based computation).
     * The centroid is the average of the coordinates (sum and divide by 3)
     */
    public static Point centroid(Triangle tri)
    {
        double xCoord = (tri.getPoint1().getX() + tri.getPoint2().getX() + tri.getPoint3().getX()) / 3;
        double yCoord = (tri.getPoint1().getY() + tri.getPoint2().getY() + tri.getPoint3().getY()) / 3;

        return new Point("", xCoord, yCoord);
    }

    /*
     * @param tri -- a triangle
     * @param segment -- side of the triangle
     * @return the median of this triangle w.r.t. to the given @segment (coordinate-based computation using the centroid).
     * http://www.mathopenref.com/coordcentroid.html
     * Use the centroid
     */
    public static Segment median(Triangle tri, Segment segment)
    {
        Segment normalized = tri.normalize(segment);

        Point opposite = tri.oppositePoint(normalized);
        Point midpoint = normalized.getMidpoint();

        return new Segment(opposite, midpoint);
    }

    /*
     * @param tri -- a triangle
     * @return the medians of this triangle
     */
    public static List<Segment> mediansOf(Triangle tri)
    {
        ArrayList<Segment> medians = new ArrayList<Segment>();
        
        for (Segment side : tri.getOrderedSides())
        {
            medians.add(tri.median(side));
        }
        
        return medians;
    }
    
    /*
     * @param tri -- a triangle
     * @param segment -- side of the triangle
     * @return is the given segment an exact median of this triangle? (coordinate-based computation using the centroid).
     * http://www.mathopenref.com/coordcentroid.html
     */
    public static boolean isMedian(Triangle tri, Segment segment)
    {
        return tri.mediansOf().contains(segment);
    }
    
    /*
     * Determines if this is a right traingle. 
     * @return TRUE if this is a right triangle
     */
    public static boolean isRight(Triangle tri)
    {
        return  MathUtilities.doubleEquals(tri.getAngleA().getMeasure(), 90) ||
                MathUtilities.doubleEquals(tri.getAngleB().getMeasure(), 90) ||
                MathUtilities.doubleEquals(tri.getAngleC().getMeasure(), 90);
    }

    /*
     * Determines if this is a isosceles triangle. 
     * @return TRUE if this is an isosceles triangle
     */
    public static boolean isIsosceles(Triangle tri)
    {
        if (MathUtilities.doubleEquals(tri.getSegmentA().length(), tri.getSegmentB().length())) return true;
        if (MathUtilities.doubleEquals(tri.getSegmentA().length(), tri.getSegmentC().length())) return true;
        if (MathUtilities.doubleEquals(tri.getSegmentB().length(), tri.getSegmentC().length())) return true;

        return false;
    }

    /*
     * Determines if this is an equilateral triangle. 
     * @return TRUE if this is an equilateral triangle
     */
    public static boolean isEquilateral(Triangle tri)
    {
        return MathUtilities.doubleEquals(tri.getSegmentA().length(), tri.getSegmentB().length()) &&
                MathUtilities.doubleEquals(tri.getSegmentB().length(), tri.getSegmentC().length());
    }
}
