package backend.ast.figure.components.quadrilaterals;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Quadrilateral;
import backend.ast.figure.components.Segment;
import backend.utilities.math.Utilities;

public class Kite extends Quadrilateral
{
    private Segment pairASegment1;
    private Segment pairASegment2;
    private Segment pairBSegment1;
    private Segment pairBSegment2;

    public Segment getPairASegment1() { return pairASegment1; }
    public Segment getPairASegment2() { return pairASegment2; }
    public Segment getPairBSegment1() { return pairBSegment1; }
    public Segment getPairBSegment2() { return pairBSegment2; }

//    public Kite(Quadrilateral quad)
//    { 
//        this(quad.left, quad.right, quad.top, quad.bottom,
//                quad.TopLeftDiagonalIsValid(), quad.BottomRightDiagonalIsValid(), quad.getDiagonalIntersection());
//    }

    public Kite(Segment left, Segment right, Segment top, Segment bottom)
    {
//        boolean tlDiag = false;
//        boolean brDiag = false;
//        Intersection inter = null;
        this(left, right, top, bottom, false, false);
    }
    
//    public Kite(Segment left, Segment right, Segment top, Segment bottom, boolean tlDiag, boolean brDiag, Intersection inter)
    public Kite(Segment left, Segment right, Segment top, Segment bottom, boolean tlDiag, boolean brDiag)
    {
        super(left, right, top, bottom);
        
        if (Utilities.doubleEquals(left.length(), top.length()) && Utilities.doubleEquals(right.length(), bottom.length()))
        {
            pairASegment1 = left;
            pairASegment2 = top;

            pairBSegment1 = right;
            pairBSegment2 = bottom;
        }
        else if (Utilities.doubleEquals(left.length(), bottom.length()) && Utilities.doubleEquals(right.length(), top.length()))
        {
            pairASegment1 = left;
            pairASegment2 = bottom;

            pairBSegment1 = right;
            pairBSegment2 = top;
        }
        else
        {
            throw new IllegalArgumentException("Quadrilateral does not define a kite; no two adjacent sides are equal lengths: " + this);
        }

        //Set the diagonal and intersection values
        if (!tlDiag) this.SetTopLeftDiagonalInValid();
        if (!brDiag) this.SetBottomRightDiagonalInValid();
//        this.SetIntersection(inter);
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof Kite) return false;
        if (that instanceof Parallelogram) return false;
        if (that instanceof Trapezoid) return false;
        if (that instanceof Quadrilateral) return true;

        return false;
    }

//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Acquire the diagonals.
//        if (this.topLeftBottomRightDiagonal == null || this.bottomLeftTopRightDiagonal == null)
//        {
////            System.Diagnostics.Debug.WriteLine("No-Op");
//        }
//
//        double diag1Length = known.GetSegmentLength(this.bottomLeftTopRightDiagonal);
//        double diag2Length = known.GetSegmentLength(this.topLeftBottomRightDiagonal);
//
//        // Multiply base * height.
//        double thisArea = -1;
//
//        if (diag1Length < 0 || diag2Length < 0) thisArea = -1;
//        else thisArea = 0.5 * diag1Length * diag2Length;
//
//        return thisArea > 0 ? thisArea : SplitTriangleArea(known);
//    }

    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof Trapezoid)) return false;
//        Kite thatKite = (Kite)obj;

        //return base.StructurallyEquals(obj);
        return super.HasSamePoints((Quadrilateral)obj);
    }

    @Override
    public boolean Equals(Object obj)
    {
        return StructurallyEquals(obj);
    }

    @Override
    public String toString()
    {
        return "Kite(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points)
        {
            str.append(pt.CheapPrettyString());
        }
        return "Kite(" + str.toString() + ")";
    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }
}
