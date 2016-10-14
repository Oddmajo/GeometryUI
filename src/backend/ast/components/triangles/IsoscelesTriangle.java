package ast.figure.components.triangles;

import ast.figure.components.Angle;
import ast.figure.components.Polygon;
import ast.figure.components.Segment;
import ast.figure.components.Triangle;
import utilities.math.Utilities;

public class IsoscelesTriangle extends Triangle
{
    /// <summary>
    /// Represents a triangle, which consists of 3 segments where 2 are of equal length
    /// </summary>
    //
    // Although some of this information is redundant to what is stored in the superclass, it makes the information easily accessible
    //
    private Segment leg1;
    private Segment leg2;
    private Segment baseSegment;
    private Angle baseAngleOppositeLeg1;
    private Angle baseAngleOppositeLeg2;
    private Angle vertexAngle;

    public Segment getLeg1() { return leg1; }
    public Segment getLeg2() { return leg2; }
    public Segment getBaseSegment() { return baseSegment; }
    public Angle getBaseAngleOppositeLeg1() { return baseAngleOppositeLeg1; }
    public Angle getBaseAngleOppositeLeg2() { return baseAngleOppositeLeg2; }
    public Angle getVertexAngle() { return vertexAngle; }

    private void DetermineIsoscelesValues()
    {
        Segment[] segments = new Segment[3];
        segments[0] = getSegmentA();
        segments[1] = getSegmentB();
        segments[2] = getSegmentC();

        // Find the two congruent segments
        for (int i = 0; i < segments.length; i++)
        {
            int otherSegment = i + 1 < segments.length ? i + 1 : 0;
            if (Utilities.doubleEquals(segments[i].length(), segments[otherSegment].length()))
            {
                leg1 = segments[i];
                leg2 = segments[otherSegment];

                baseAngleOppositeLeg1 = GetOppositeAngle(leg1);
                baseAngleOppositeLeg2 = GetOppositeAngle(leg2);
                vertexAngle = OtherAngle(baseAngleOppositeLeg1, baseAngleOppositeLeg2);
                baseSegment = GetOppositeSide(vertexAngle);

                break;
            }
        }
    }

    /// <summary>
    /// Create a new isosceles triangle bounded by the 3 given segments. The set of points that define these segments should have only 3 distinct elements.
    /// </summary>
    /// <param name="a">The segment opposite point a</param>
    /// <param name="b">The segment opposite point b</param>
    /// <param name="c">The segment opposite point c</param>
    public IsoscelesTriangle(Segment a, Segment b, Segment c)
    {
        super(a, b, c);
        if (!this.isEquilateral)
        {
            DetermineIsoscelesValues();
        }
        provenIsosceles = true;
    }

    public IsoscelesTriangle(Triangle tri) 
    {
        super(tri.getSegmentA(), tri.getSegmentB(), tri.getSegmentC());
        DetermineIsoscelesValues();
        provenIsosceles = true;
    }

    public Angle GetVertexAngle()
    {
        return vertexAngle;
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof EquilateralTriangle) return false;
        if (that instanceof RightTriangle) return true;
        if (that instanceof Triangle)
        {
            Triangle tri = (Triangle)that;

            if (tri.provenIsosceles()) return false;
            if (tri.provenEquilateral()) return false;
            if (tri.provenRight()) return true;

            return true;
        }

        return false;
    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }

    @Override
    public boolean Equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof IsoscelesTriangle)) return false;
//        IsoscelesTriangle triangle = (IsoscelesTriangle)obj;
        return super.Equals(obj);
    }

}
