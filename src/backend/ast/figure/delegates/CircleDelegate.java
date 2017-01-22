package backend.ast.figure.delegates;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.utilities.Pair;
import backend.utilities.PointFactory;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutPair;

public class CircleDelegate
{

    private CircleDelegate() { }

    /*
     * @param circle -- a standard circle
     * @param segment -- candidate radius (finite)
     * @return true / false whether @segment is a radius in the @circle
     */
    public static boolean isRadius(Circle circle, Segment segment)
    {
        // The center is an endpoint of the segment
        if (!segment.has(circle.getCenter())) return false;
            
        // The non-center endpoint
        Point other = segment.other(circle.getCenter());
        
        // Check that the other point is also on the circle
        return circle.pointLiesOn(other);
    }
    
    /*
     * @param radius -- a point on the circle 
     * @return radian angle measure in range [-pi, pi); this is an angle measured in standard position
     * Double.POSITIVE_INFINITY if the segment is not a radius
     */
    public static double standardAngleMeasure(Circle circle, Point pt)
    {
        if (circle.pointLiesOn(pt)) return Double.POSITIVE_INFINITY;

        double stdX = pt.getX() - circle.getCenter().getX();
        double stdY = pt.getY() - circle.getCenter().getY();

        return Math.atan2(stdY, stdX);
    }
    
    /*
     * @param radius -- a radius of the circle 
     * @return radian angle measure in range [-pi, pi); this is an angle measured in standard position
     * Double.POSITIVE_INFINITY if the segment is not a radius for the circle
     */
    public static double standardAngleMeasureRadius(Circle circle, Segment radius)
    {
        if (isRadius(circle, radius)) return Double.POSITIVE_INFINITY;

        // Use the non-center point for angle measurement
        return standardAngleMeasure(circle, radius.other(circle.getCenter()));
    }
    
    /*
     * @param ray -- a ray (hopefully emanating from the center of the circle) 
     * @return radian angle measure in range [-pi, pi); this is an angle measured in standard position
     * Double.POSITIVE_INFINITY if the segment is not a radius for the circle
     */
    public static double standardAngleMeasure(Circle circle, Ray ray)
    {
        if (ray.isOriginPoint(circle.getCenter())) return Double.POSITIVE_INFINITY;
        
        // Find the intersection point of this segment and the circle
        OutPair<Point, Point> out = new OutPair<Point, Point>();

        // Compute two intersection points (since we treat the ray as a line)
        if (!circle.findIntersection(ray.asLine(), out)) ExceptionHandler.throwException("Two intersection points expected");

        // Determine which of the two intersection points is the one we seek.
        Point intersection = ray.pointLiesOn(out.first()) ? out.first() : out.second();
        
        // Use intersection point which is on the ray for angle measure computation
        return standardAngleMeasure(circle, intersection);
    }
        
    /*
     * @param circle a standard circle
     * @param angle -- standard positional angle measured in degrees 
     * @return point on circle defined by the input angle
     */
    public static Point pointByAngle(Circle circle, double angle)
    {
        Point center = circle.getCenter(); // (h, k)
        double radius = circle.getRadius(); // acts as magnitude of the new point

        double radMeasure = Math.toRadians(angle);

        // Generate the point according to   <h, k> + radius * <x, y>   based on standard computation
        double x = center.getX() + radius * Math.cos(radMeasure);
        double y = center.getY() + radius * Math.sin(radMeasure);

        return PointFactory.GeneratePoint(x, y); 
    }
    
    //
    // For arcs, order the points so that there is a consistency: A, B, C, D-> B between AC, B between AD, etc.
    // Only need to order the points if there are more than three points
    //
    public static ArrayList<Point> OrderPoints(Circle circle, List<Point> points)
    {
        ArrayList<Pair<Double, Point>> pointAngleMap = new ArrayList<Pair<Double, Point>>();

        for (Point point : points)
        {
            double radianAngle = Point.GetRadianStandardAngleWithCenter(circle.getCenter(), point);

            // Angles are between 0 and 2pi
            // insert the point into the correct position (starting from the back); insertion sort-style
            int index;
            for (index = 0; index < pointAngleMap.size(); index++)
            {
                if (radianAngle > pointAngleMap.get(index).getKey()) break;
            }
            pointAngleMap.add(index, new Pair<Double, Point>(radianAngle, point));
        }

        //
        // Put all the points : the final ordered list
        //
        ArrayList<Point> ordered = new ArrayList<Point>();
        for (Pair<Double, Point> pair : pointAngleMap)
        {
            ordered.add(pair.getValue());
        }

        return ordered;
    }

    public ArrayList<Point> ConstructAllMidpoints(Circle circle, List<Point> given)
    {
        ArrayList<Point> ordered = OrderPoints(circle, given);
        ArrayList<Point> ptsWithMidpoints = new ArrayList<Point>();

        if (ordered.size() < 2) return ordered;

        //
        // Walk around the ordered points : a COUNTER-CLOCKWISE direction.
        //
        for (int p = 0; p < ordered.size(); p++)
        {
            ptsWithMidpoints.add(ordered.get(p));

            Point midpt = circle.getMidpoint(ordered.get(p), ordered.get((p + 1) % ordered.size()));
            Point opp = circle.OppositePoint(midpt);

            if (Point.CounterClockwise(ordered.get(p), midpt, ordered.get((p + 1) % ordered.size())))
            {
                ptsWithMidpoints.add(midpt);
            }
            else ptsWithMidpoints.add(opp);
        }

        return ptsWithMidpoints;
    }

    /*
     * @return point opposite the given point (on the circle)
     * Ramification / interpretation: The given point and the computed point create a diameter.
     */
    public static Point oppositePoint(Circle circle, Point that)
    {
        if (!circle.pointLiesOn(that)) return null;

        // Acquire the radian-based, standard angle
        double radianAngle = circle.standardAngleMeasure(that);

        // Opposite angle is 180 degrees away
        radianAngle += Math.PI;
        
        return circle.pointByAngle(radianAngle);
    }
}
