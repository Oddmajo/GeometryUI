
/**
 * @author Nick Celiberti
 * Partial translation for PolygonCalculator
 */
package PolyID;

public class Angle extends Figure
{

    public Point A; //public get private set
    public Point B; //public get private set
    public Point C; //public get private set
    public Segment ray1; //public get private set
    public Segment ray2; //public get private set
    public double measure; //public get private set
    
    public Point GetVertex() { return B; }

    public Angle(Segment ray1, Segment ray2) throws ArgumentException
    {
        Point vertex = ray1.SharedVertex(ray2);

        if (vertex == null) throw new ArgumentException("Rays do not share a vertex: " + ray1 + " " + ray2);

        this.A = ray1.OtherPoint(vertex);
        this.B = vertex;
        this.C = ray2.OtherPoint(vertex);
        this.ray1 = ray1;
        this.ray2 = ray2;
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0)
        {
            //Commented out in Source Code ------
            //System.Diagnostics.Debug.WriteLine("NO-OP");
            //throw new ArgumentException("Measure of " + this.ToString() + " is ZERO");
        }
    }


    public Angle(Point a, Point b, Point c)
    {
        if (a.StructurallyEquals(b) || b.StructurallyEquals(c) || a.StructurallyEquals(c))
        {
            return;
            //Commented out in Source Code ------
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
            //Commented out in Source Code ------
            //System.Diagnostics.Debug.WriteLine("NO-OP");
            //throw new ArgumentException("Measure of " + this.ToString() + " is ZERO");
        }
    }


    private double toDegrees(double radians)
    {
        return radians * 180 / Math.PI;
    }

    private double findAngle(Point a, Point b, Point c)
    {
        double v1x = a.getX() - b.getX();
        double v1y = a.getY() - b.getY();
        double v2x = c.getX() - b.getX();
        double v2y = c.getY() - b.getY();
        double dotProd = v1x * v2x + v1y * v2y;
        double cosAngle = dotProd / (Point.calcDistance(a, b) * Point.calcDistance(b, c));

        // Avoid minor calculation issues and retarget the given value to specific angles. 
        // 0 or 180 degrees
        if (Utilities.CompareValues(Math.abs(cosAngle), 1))
        {
            cosAngle = cosAngle < 0 ? -1 : 1;
        }

        // 90 degrees
        if (Utilities.CompareValues(cosAngle, 0)) cosAngle = 0;

        return Math.acos(cosAngle);
    }

    @Override
    public String toString()
    {
        return "Angle( m" + A.name + B.name + C.name + " = " + String.format("{0:N3}", measure) + ")";
    }

}
