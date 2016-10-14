package ast.figure.components.triangles;

import ast.figure.components.Polygon;
import ast.figure.components.Segment;
import ast.figure.components.Triangle;
import utilities.math.Utilities;

public class RightTriangle extends Triangle
{
    /// <summary>
    /// Represents a right triangle, FOR PRECOMPTUATION purposes only now.
    /// </summary>
    public RightTriangle(Segment a, Segment b, Segment c)
    {
        super(a,b,c);
        provenRight = true;

        rightAngle = Utilities.doubleEquals(this.getAngleA().getMeasure(), 90) ? getAngleA() : rightAngle;
        rightAngle = Utilities.doubleEquals(this.getAngleB().getMeasure(), 90) ? getAngleB() : rightAngle;
        rightAngle = Utilities.doubleEquals(this.getAngleC().getMeasure(), 90) ? getAngleC() : rightAngle;
    }

    public RightTriangle(Triangle t) 
    {
        super(t.getSegmentA(), t.getSegmentB(), t.getSegmentC());
        provenRight = true;

        rightAngle = Utilities.doubleEquals(this.getAngleA().getMeasure(), 90) ? getAngleA() : rightAngle;
        rightAngle = Utilities.doubleEquals(this.getAngleB().getMeasure(), 90) ? getAngleB() : rightAngle;
        rightAngle = Utilities.doubleEquals(this.getAngleC().getMeasure(), 90) ? getAngleC() : rightAngle;
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof EquilateralTriangle) return false;
        if (that instanceof IsoscelesTriangle) return false;
        if (that instanceof RightTriangle) return false;
        if (that instanceof Triangle) return true;

        return false;
    }

    //
    // Is this triangle encased in the given, larger triangle.
    // That is, two sides are defined by the larger triangle and one side by the altitude
    //
//    public boolean IsDefinedBy(RightTriangle rt, Altitude altitude)
//    {
//        // The opposite side of the smaller triangle has to be a side of the larger triangle.
//        if (!rt.HasSegment(this.GetOppositeSide(this.rightAngle))) return false;
//
//        // The smaller triangle's right angle must have a ray collinear with the altitude segment
//        Segment altSegment = null;
//        if (this.rightAngle.getRay1().IsCollinearWith(altitude.segment))
//        {
//            altSegment = this.rightAngle.getRay1();
//        }
//        else if (this.rightAngle.getRay2().IsCollinearWith(altitude.segment))
//        {
//            altSegment = this.rightAngle.getRay2();
//        }
//        if (altSegment == null) return false;
//
//        // The last segment needs to be collinear with a side of the larger triangle
//        return rt.LiesOn(this.rightAngle.OtherRayEquates(altSegment));
//    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }

    @Override
    public boolean Equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof RightTriangle)) return false;
//        RightTriangle triangle = (RightTriangle)obj;
        
        return super.Equals(obj);
    }

    @Override
    public String toString()
    {
        return "RightTriangle(" + getPoint1().toString() + ", " + getPoint2().toString() + ", " + getPoint3().toString() + ")";
    }
}