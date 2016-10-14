// Need license....

/**
 * @author Nick Celiberti
 * Partial translation for PolygonCalculator
 */
package ast.figure.components;
import ast.figure.Figure;

public class Angle extends Figure
{

    // CTA: Need getter methods...protected members...
    public Point A; //public get private set
    public Point B; //public get private set
    public Point C; //public get private set
    public Segment ray1; //public get private set
    public Segment ray2; //public get private set
    public double measure; //public get private set
    
    public Point GetVertex() { return B; }

    public Angle(Segment ray1, Segment ray2) throws IllegalArgumentException
    {
        Point vertex = ray1.SharedVertex(ray2);

        if (vertex == null) throw new IllegalArgumentException("Rays do not share a vertex: " + ray1 + " " + ray2);

        this.A = ray1.OtherPoint(vertex);
        this.B = vertex;
        this.C = ray2.OtherPoint(vertex);
        this.ray1 = ray1;
        this.ray2 = ray2;
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0) throw new IllegalArgumentException("Measure of " + this.toString() + " is ZERO");
    }


    public Angle(Point a, Point b, Point c)
    {
        if (a.StructurallyEquals(b) || b.StructurallyEquals(c) || a.StructurallyEquals(c))
        {
            return;
            //Commented out : Source Code ------
            //throw new ArgumentException("Angle constructed with redundant vertices.");
        }
        this.A = a;
        this.B = b;
        this.C = c;
        ray1 = new Segment(a, b);
        ray2 = new Segment(b, c);
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0)
        {
            //Commented out : Source Code ------
            //System.Diagnostics.Debug.WriteLine("NO-OP");
            //throw new ArgumentException("Measure of " + this.toString() + " is ZERO");
        }
    }


    /// <summary>
    /// Find the measure of the angle (in radians) specified by the three points.
    /// </summary>
    /// <param name="a">A point defining the angle.</param>
    /// <param name="b">A point defining the angle. This is the point the angle is actually at.</param>
    /// <param name="c">A point defining the angle.</param>
    /// <returns>The measure of the angle (in radians) specified by the three points.</returns>
    public static double findAngle(Point a, Point b, Point c)
    {
        double v1x = a.getX() - b.getX();
        double v1y = a.getY() - b.getY();
        double v2x = c.getX() - b.getX();
        double v2y = c.getY() - b.getY();
        double dotProd = v1x * v2x + v1y * v2y;
        double cosAngle = dotProd / (Point.calcDistance(a, b) * Point.calcDistance(b, c));

        // Avoid minor calculation issues and retarget the given value to specific angles. 
        // 0 or 180 degrees
        if (utilities.math.Utilities.doubleEquals(Math.abs(cosAngle), 1))
        {
            cosAngle = cosAngle < 0 ? -1 : 1;
        }

        // 90 degrees
        if (utilities.math.Utilities.doubleEquals(cosAngle, 0)) cosAngle = 0;

        return Math.acos(cosAngle);
    }

    /// <summary>
    /// Converts radians into degrees.
    /// </summary>
    /// <param name="radians">An angle in radians</param>
    /// <returns>An angle in degrees</returns>
    public static double toDegrees(double radians)
    {
        return radians * 180 / Math.PI;
    }

    /// <summary>
    /// Converts degrees into radians
    /// </summary>
    /// <param name="degrees">An angle in degrees</param>
    /// <returns>An angle in radians</returns>
    public static double toRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }


    //
    // Looks for a single shared ray
    //
    public Segment SharedRay(Angle ang)
    {
        if (ray1.rayOverlays(ang.ray1) || ray1.rayOverlays(ang.ray2)) return ray1;

        if (ray2.rayOverlays(ang.ray1) || ray2.rayOverlays(ang.ray2)) return ray2;

        return null;
    }
    
    //
    // Is the given angle the same as this angle? that is, the vertex is the same and the rays coincide
    // (not necessarily with the same endpoints)
    // Can't just be collinear, must be collinear and on same side of an angle
    //
    public boolean equates(Angle thatAngle)
    {
        //if (this.Equals(thatAngle)) return true;

        // Vertices must equate
        if (!this.GetVertex().Equals(thatAngle.GetVertex())) return false;

        // Rays must originate at the vertex and emanate outward
        return (ray1.rayOverlays(thatAngle.ray1) && ray2.rayOverlays(thatAngle.ray2)) ||
               (ray2.rayOverlays(thatAngle.ray1) && ray1.rayOverlays(thatAngle.ray2));
    }
    
    // Checking for structural equality (is it the same segment) excluding the multiplier
    // This is either a direct comparison of the angle based on vertices or 
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj;

        // Measures better be the same.
        if (!utilities.math.Utilities.doubleEquals(this.measure, angle.measure)) return false;

        if (this.equates(angle)) return true;

        return (angle.A.StructurallyEquals(A) && angle.B.StructurallyEquals(B) && angle.C.StructurallyEquals(C)) ||
               (angle.A.StructurallyEquals(C) && angle.B.StructurallyEquals(B) && angle.C.StructurallyEquals(A));
    }

    //Checking for equality of angles WITHOUT considering possible overlay of rays (i.e. two angles will be considered NOT equal
    //if they contain rays that coincide but are not equivalent)
    public boolean equalRays(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj;
        
        // Measures better be the same.
        if (!utilities.math.Utilities.doubleEquals(this.measure, angle.measure)) return false;

        return (angle.A.StructurallyEquals(A) && angle.B.StructurallyEquals(B) && angle.C.StructurallyEquals(C)) ||
               (angle.A.StructurallyEquals(C) && angle.B.StructurallyEquals(B) && angle.C.StructurallyEquals(A));
    }
    
    @Override
    public String toString()
    {
        return "Angle( m" + A.name + B.name + C.name + " = " + String.format("{0:N3}", measure) + ")";
    }

}
