package backend.ast.figure.components.quadrilaterals;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Quadrilateral;
import backend.ast.figure.components.Segment;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class IsoscelesTrapezoid extends Trapezoid
{
    public IsoscelesTrapezoid(Quadrilateral quad)
    { 
        this(quad.left, quad.right, quad.top, quad.bottom);
    }

    public IsoscelesTrapezoid(Segment left, Segment right, Segment top, Segment bottom)
    {
        super(left, right, top, bottom);
        if (!MathUtilities.doubleEquals(getLeftLeg().length(), getRightLeg().length()))
        {
            ExceptionHandler.throwException( new IllegalArgumentException("Trapezoid does not define an isosceles trapezoid; sides are not equal length: " + this));
        }
    }

    @Override
    public boolean IsStrongerThan(Polygon that)
    {
        if (that instanceof Kite) return false;
        if (that instanceof Parallelogram) return false;
        if (that instanceof Trapezoid) return true;
        if (that instanceof Quadrilateral) return true;

        return false;
    }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof IsoscelesTrapezoid)) return false;
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
        return "IsoscelesTrapezoid(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) 
        {
            str.append(pt.CheapPrettyString());
        }
        return "IsoTrap(" + str.toString() + ")";
    }

    //
    // Attempt trapezoidal formulas; if they fail, call the base method: splitting into triangles.
    //
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        if (calculatedHeight > 0)
//        {
//            double area = GetBaseBasedArea(calculatedHeight, known);
//        }
//
//        return super.GetArea(known);
//    }
}
