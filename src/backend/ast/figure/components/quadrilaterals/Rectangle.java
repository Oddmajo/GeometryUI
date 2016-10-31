package backend.ast.figure.components.quadrilaterals;

import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Quadrilateral;
import backend.ast.figure.components.Segment;
import backend.utilities.math.Utilities;

public class Rectangle extends Parallelogram
{
    //        public Rectangle(Quadrilateral quad) 
    //        { 
    //            this(quad.left, quad.right, quad.top, quad.bottom,
    //                    quad.TopLeftDiagonalIsValid(), quad.BottomRightDiagonalIsValid(), quad.diagonalIntersection)
    //        }

    public Rectangle(Segment a, Segment b, Segment c, Segment d)
    {
        super(a, b, c, d);
        boolean tlDiag = false;
        boolean brDiag = false;
        //            Intersection inter = null;
        for (Angle angle : angles)
        {
            if (!Utilities.doubleEquals(angle.getMeasure(), 90))
            {
                throw new IllegalArgumentException("Quadrilateral is not a Rectangle; angle does not measure 90^o: " + angle);
            }
        }

        //Set the diagonal and intersection values
        if (!tlDiag) this.SetTopLeftDiagonalInValid();
        if (!brDiag) this.SetBottomRightDiagonalInValid();
        //            this.SetIntersection(inter);
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof Trapezoid) return false;
        if (that instanceof Kite) return false;
        if (that instanceof Rectangle) return false;
        if (that instanceof Rhombus) return false;
        if (that instanceof Parallelogram) return true;
        if (that instanceof Quadrilateral) return true;

        return false;
    }

    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Rectangle)) return false;
        Rectangle thatRect = (Rectangle)obj;

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
        return "Rectangle(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) str.append(pt.CheapPrettyString());
        return "Rect(" + str.toString() + ")";
    }

    //
    // Calculate base * height ; defer to splitting triangles from there.
    //
    public double Area(double b, double h) { return b * h; }

//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        double[] sideVals = new double[orderedSides.size()];
//
//        for (int s = 0; s < orderedSides.size(); s++)
//        {
//            sideVals[s] = known.GetSegmentLength(orderedSides.get(s));
//        }
//
//        // One pair of adjacent sides is required for the area computation.
//        for (int s = 0; s < sideVals.length; s++)
//        {
//            double baseVal = sideVals[s];
//            double heightVal = sideVals[(s+1) % sideVals.length];
//
//            if (baseVal > 0 && heightVal > 0) return Area(baseVal, heightVal);
//        }
//
//        return SplitTriangleArea(known);
//    }
}
