package backend.ast.figure.delegates;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.utilities.math.MathUtilities;

/*
 * When treating a segment as a ray (as with angles), use this class for processing.
 * 
 * @author C. Alvin
 */
public class RayDelegate
{
    /*
     * Pictorially we view the given segment as a ray of an angle
     *              /
     *             /  <-- This method generates this segment (with exact length)
     *            /
     *           /\ angle (measure)
     *   pt --> .__\________________  <-- segment
     * 
     * 
     * @param ray -- a Ray object 
     * @param angle -- desired angle measurement in degrees [0, 360)
     * @param length -- desired length of the resulting ray object (User may want to specify this notion)
     * @return a ray with same origin point with desired length 
     */
    public static Ray rayByAngle(Ray ray, double angle, double length)
    {
        // Create a circle centered at the origin point with radius defined by @length
        Circle circle = new Circle(ray.getOrigin(), length);

        // Compute the standard angle measure of the given segment
        double baseAngleMeasure = circle.standardAngleMeasure(ray);

        // The desired angle is added to the base to sweep from the base in std position
        double desiredStdMeasure = baseAngleMeasure + Math.toRadians(angle);

        // The endpoint of the new ray (which is on the circle and thus defines a radius) 
        Point endpoint = circle.pointByAngle(desiredStdMeasure);
        
        return new Ray(ray.getOrigin(), endpoint);
    }
    
    /*
     * Pictorially we view the given segment as a ray of an angle
     *              /
     *             /  <-- This method generates this segment (with exact length)
     *            /
     *           /\ angle (measure)
     *   pt --> .__\________________  <-- segment
     * 
     * 
     * @param ray -- a Ray object 
     * @param angle -- desired angle measurement in degrees [0, 360)
     * @return a ray with same origin point with desired length 
     */
    public static Ray rayByAngle(Ray ray, double angle)
    {
        // Same routine with a default radius length 1
        return rayByAngle(ray, angle, 1);
    }
    
    /*
     * @param thisRay -- a ray
     * @param thatRay -- a ray
     * @return Does thatRay overlay thisRay? As in, both share same origin point, but other two points
     * are not common: one extends over the other.
     */
    public static boolean overlays(Ray thisRay, Ray thatRay)
    {
        // Same ray?
        if (thisRay.equals(thatRay)) return true;
        
        // Share same origin vertex?
        if (!thisRay.getOrigin().equals(thatRay.getOrigin())) return false;
        
        // Overall collinearity of the 3 points?
        if (!LineDelegate.collinear(thisRay.getOrigin(), thisRay.getNonOrigin(), thatRay.getNonOrigin())) return false;
        
        // Rays pointing in the same direction?
        // Avoid: <--------------------- . ---------------->
        return MathUtilities.doubleEquals(thisRay.standardAngleMeasure(), thatRay.standardAngleMeasure());
    }
}
