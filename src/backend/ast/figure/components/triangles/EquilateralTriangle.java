package backend.ast.figure.components.triangles;

import java.util.ArrayList;

import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.Triangle;
import backend.utilities.math.MathUtilities;

public class EquilateralTriangle extends IsoscelesTriangle
{
    /// <summary>
    /// Create a new equilateral triangle bounded by the 3 given segments. The set of points that define these segments should have only 3 distinct elements.
    /// </summary>
    /// <param name="a">The segment opposite point a</param>
    /// <param name="b">The segment opposite point b</param>
    /// <param name="c">The segment opposite point c</param>
    public EquilateralTriangle(Segment a, Segment b, Segment c)
    {
        super(a,b,c);
        provenIsosceles = true;
        provenEquilateral = true;
    }
    public EquilateralTriangle(Triangle t)
    {
        super(t.getSegmentA(), t.getSegmentB(), t.getSegmentC());
        if (!MathUtilities.doubleEquals(t.getSegmentA().length(), t.getSegmentB().length()))
        {
            throw new IllegalArgumentException("Equilateral Triangle constructed with non-congruent segments " + t.getSegmentA().toString() + " " + t.getSegmentB().toString());
        }

        if (!MathUtilities.doubleEquals(t.getSegmentA().length(), t.getSegmentC().length()))
        {
            throw new IllegalArgumentException("Equilateral Triangle constructed with non-congruent segments " + t.getSegmentA().toString() + " " + t.getSegmentC().toString());
        }

        if (!MathUtilities.doubleEquals(t.getSegmentB().length(), t.getSegmentC().length()))
        {
            throw new IllegalArgumentException("Equilateral Triangle constructed with non-congruent segments " + t.getSegmentB().toString() + " " + t.getSegmentC().toString());
        }

        provenIsosceles = true;
        provenEquilateral = true;

        // Need to capture all owners as well; if a triangle is strengthened.
        this.AddAtomicRegions(t.atoms);
//        this.GetFigureAsAtomicRegion().AddOwners(t.GetFigureAsAtomicRegion().getOwners());
    }
    public EquilateralTriangle(ArrayList<Segment> segs)
    {
        this(segs.get(0), segs.get(1), segs.get(2));
        if (segs.size() != 3) throw new IllegalArgumentException("Equilateral Triangle constructed with " + segs.size() + " segments.");
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof EquilateralTriangle) return false;
        if (that instanceof IsoscelesTriangle) return true;
        if (that instanceof RightTriangle) return false;
        if (that instanceof Triangle)
        {
            Triangle tri = (Triangle)that;

            if (tri.provenIsosceles()) return true;
            if (tri.provenEquilateral()) return false;
            if (tri.provenRight()) return false;

            return true;
        }

        return false;
    }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof EquilateralTriangle)) return false;
//        EquilateralTriangle triangle = (EquilateralTriangle)obj;
        
        return super.equals(obj);
    }
}
