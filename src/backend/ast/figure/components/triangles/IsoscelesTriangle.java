package backend.ast.figure.components.triangles;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.math.MathUtilities;

/*
 * Represents a triangle, which consists of 3 segments in which 2 are of equal length
 * 
 * @author C. Alvin
 */
public class IsoscelesTriangle extends Triangle
{
    //
    // Although some of this information is redundant to what is stored in the superclass, it makes the information easily accessible
    //
    private Segment leg1;
    private Segment leg2;
    public Segment getLeg1() { return leg1; }
    public Segment getLeg2() { return leg2; }

    private Segment baseSegment;
    public Segment getBaseSegment() { return baseSegment; }

    private Angle baseAngleOppositeLeg1;
    private Angle baseAngleOppositeLeg2;
    public Angle getBaseAngleOppositeLeg1() { return baseAngleOppositeLeg1; }
    public Angle getBaseAngleOppositeLeg2() { return baseAngleOppositeLeg2; }

    private Angle vertexAngle;
    public Angle getVertexAngle() { return vertexAngle; }
    
    /*
     * Create a new isosceles triangle bounded by the 3 given segments.
     * The set of points that define these segments should have only 3 distinct elements.
     * @param a segment opposite vertex A
     * @param b segment opposite vertex B
     * @param c segment opposite vertex C
     */
    public IsoscelesTriangle(Segment a, Segment b, Segment c)
    {
        super(a, b, c);

        if (!this.isEquilateral()) initialize();
    }

    public IsoscelesTriangle(Triangle tri) { this(tri.getSegmentA(), tri.getSegmentB(), tri.getSegmentC()); }

    private void initialize()
    {
        Segment[] segments = new Segment[3];
        segments[0] = getSegmentA();
        segments[1] = getSegmentB();
        segments[2] = getSegmentC();

        // Find the two congruent segments
        for (int i = 0; i < segments.length; i++)
        {
            int otherSegment = i + 1 < segments.length ? i + 1 : 0;
            if (MathUtilities.doubleEquals(segments[i].length(), segments[otherSegment].length()))
            {
                leg1 = segments[i];
                leg2 = segments[otherSegment];

                baseAngleOppositeLeg1 = oppositeAngle(leg1);
                baseAngleOppositeLeg2 = oppositeAngle(leg2);
                vertexAngle = OtherAngle(baseAngleOppositeLeg1, baseAngleOppositeLeg2);
                baseSegment = oppositeSide(vertexAngle);

                break;
            }
        }
    }
    
    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof EquilateralTriangle) return false;
        if (that instanceof RightTriangle) return true;
        if (that instanceof Triangle)
        {
            Triangle tri = (Triangle)that;

//            if (tri.provenIsosceles()) return false;
//            if (tri.provenEquilateral()) return false;
//            if (tri.provenRight()) return true;

            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof IsoscelesTriangle)) return false;

        return super.equals(obj);
    }
}
