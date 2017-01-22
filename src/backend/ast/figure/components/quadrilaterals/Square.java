package backend.ast.figure.components.quadrilaterals;

import backend.ast.Descriptors.Intersection;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class Square extends Rhombus
{
   public Square(Quadrilateral quad)
   { 
        this(quad.left, quad.right, quad.top, quad.bottom,
        quad.TopLeftDiagonalIsValid(), quad.BottomRightDiagonalIsValid(), quad.getDiagonalIntersection());
   }
    
    public Square(Segment left, Segment right, Segment top, Segment bottom)
    {
        this(left, right, top, bottom, false, false, null);
    }

//    public Square(Segment left, Segment right, Segment top, Segment bottom, boolean tlDiag, boolean brDiag, Intersection inter) 
    public Square(Segment left, Segment right, Segment top, Segment bottom, boolean tlDiag, boolean brDiag, Intersection inter) 
    {
        super(left, right, top, bottom);  
        if (!MathUtilities.doubleEquals(topLeftAngle.getMeasure(), 90))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Quadrilateral is not a Square; angle does not measure 90^o: " + topLeftAngle));
        }
        if (!MathUtilities.doubleEquals(topRightAngle.getMeasure(), 90))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Quadrilateral is not a Square; angle does not measure 90^o: " + topRightAngle));
        }

        if (!MathUtilities.doubleEquals(bottomLeftAngle.getMeasure(), 90))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Quadrilateral is not a Square; angle does not measure 90^o: " + bottomLeftAngle));
        }

        if (!MathUtilities.doubleEquals(bottomRightAngle.getMeasure(), 90))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Quadrilateral is not a Square; angle does not measure 90^o: " + bottomRightAngle));
        }

        //Set the diagonal and intersection values
        if (!tlDiag) this.SetTopLeftDiagonalInValid();
        if (!brDiag) this.SetBottomRightDiagonalInValid();
         this.SetIntersection(inter);
    }

//    //
//    // Area-Related Computations
//    //
//    // Side-squared
//    protected double Area(double s)
//    {
//        return s * s;
//    }
//    protected double RationalArea(double s) { return Area(s); }
//    @Override
//    public boolean IsComputableArea() { return true; }

//    private double ClassicArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        for (Segment side : orderedSides)
//        {
//            double sideLength = known.GetSegmentLength(side);
//
//            if (sideLength > 0) return Area(sideLength);
//        }
//
//        return -1;
//    }

//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Check side-squared.
//        if (ClassicArea(known) > 0) return true;
//
//        // If not side-squared, check the general quadrilateral split into triangles.
//        return super.CanAreaBeComputed(known);
//    }
//
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Check side-squared.
//        double area = ClassicArea(known);
//
//        if (area > 0) return area;
//
//        // If not side-squared, check the general quadrilateral split into triangles.
//        return super.GetArea(known);
//    }


//    public boolean IsStrongerThan(Polygon that)
//    {
//        if (that instanceof Trapezoid) return false;
//        if (that instanceof Kite) return false;
//        if (that instanceof Rectangle) return true;
//        if (that instanceof Rhombus) return true;
//        if (that instanceof Parallelogram) return true;
//        if (that instanceof Quadrilateral) return true;
//
//        return false;
//    }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Square)) return false;
        Square thatSquare = (Square)obj;

        //return base.StructurallyEquals(obj);
        return this.HasSamePoints((Quadrilateral)obj);
    }

    @Override
    public boolean equals(Object obj)
    {
        return structurallyEquals(obj);
    }

    @Override
    public String toString()
    {
        return "Square(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) str.append(pt.CheapPrettyString());
        return "Sq(" + str.toString() + ")";
    }
}

