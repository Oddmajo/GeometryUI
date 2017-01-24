package backend.ast.figure.components.triangles;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.math.MathUtilities;

public class RightTriangle extends Triangle
{
    protected Angle _rightAngle;
    public Angle getRightAngle() { return _rightAngle; }

    /// <summary>
    /// Represents a right triangle, FOR PRECOMPTUATION purposes only now.
    /// </summary>
    public RightTriangle(Segment a, Segment b, Segment c)
    {
        super(a,b,c);

        if (MathUtilities.doubleEquals(this.getAngleA().getMeasure(), 90)) _rightAngle = getAngleA();
        if (MathUtilities.doubleEquals(this.getAngleB().getMeasure(), 90)) _rightAngle = getAngleB();
        if (MathUtilities.doubleEquals(this.getAngleC().getMeasure(), 90)) _rightAngle = getAngleC();
    }

    public RightTriangle(Triangle t) { this(t.getSegmentA(), t.getSegmentB(), t.getSegmentC()); }
    public RightTriangle(Point pt1, Point pt2, Point pt3)
    {
        this(new Segment(pt1, pt2), new Segment(pt1, pt3), new Segment(pt2, pt3));
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
    public boolean equals(Object obj)
    {
        if (obj == null) return false;

        if (!(obj instanceof RightTriangle)) return false;
        
        return super.equals(obj);
    }

    @Override
    public String toString()
    {
        return "RightTriangle(" + getPoint1().toString() + ", " + getPoint2().toString() + ", " + getPoint3().toString() + ")";
    }
    
}