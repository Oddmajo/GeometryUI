package backend.ast.figure.components.quadrilaterals;

import backend.ast.Descriptors.Intersection;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class Rectangle extends Parallelogram
{
    public Rectangle(Quadrilateral quad) 
    { 
        this(quad.left, quad.right, quad.top, quad.bottom,
                quad.TopLeftDiagonalIsValid(), quad.BottomRightDiagonalIsValid(), quad.getDiagonalIntersection());
    }

    public Rectangle(Segment a, Segment b, Segment c, Segment d )
    {
        super(a, b, c, d);
        boolean tlDiag = false;
        boolean brDiag = false;
        //            Intersection inter = null;
        for (Angle angle : angles)
        {
            if (!MathUtilities.doubleEquals(angle.getMeasure(), 90))
            {
                ExceptionHandler.throwException( new IllegalArgumentException("Quadrilateral is not a Rectangle; angle does not measure 90^o: " + angle));
            }
        }

        //Set the diagonal and intersection values
        if (!tlDiag) this.SetTopLeftDiagonalInValid();
        if (!brDiag) this.SetBottomRightDiagonalInValid();
        //            this.SetIntersection(inter);
    }
    
    public Rectangle(Segment a, Segment b, Segment c, Segment d, boolean tlDiag, boolean brDiag, Intersection inter)
    {
    	super(a,b,c,d);
    	 //            Intersection inter = null;
        for (Angle angle : angles)
        {
            if (!MathUtilities.doubleEquals(angle.getMeasure(), 90))
            {
                ExceptionHandler.throwException( new IllegalArgumentException("Quadrilateral is not a Rectangle; angle does not measure 90^o: " + angle));
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
    public boolean structurallyEquals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Rectangle)) return false;
        Rectangle thatRect = (Rectangle)obj;

        //return base.StructurallyEquals(obj);
        return super.HasSamePoints((Quadrilateral)obj);
    }

    @Override
    public boolean equals(Object obj)
    {
        return structurallyEquals(obj);
    }

    @Override
    public String toString()
    {
        return "Rectangle(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) str.append(pt.CheapPrettyString());
        return "Rect(" + str.toString() + ")";
    }
}
