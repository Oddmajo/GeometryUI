package backend.ast.figure.components.triangles;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

/*
 * Represents a triangle, which consists of 3 segments in which all 3 are equal length
 * 
 * @author C. Alvin
 */
public class EquilateralTriangle extends IsoscelesTriangle
{
    /*
     * Create a new equilateral triangle bounded by the 3 given segments.
     * The set of points that define these segments should have only 3 distinct elements.
     * @param a segment opposite vertex A
     * @param b segment opposite vertex B
     * @param c segment opposite vertex C
     */
    public EquilateralTriangle(Segment a, Segment b, Segment c)
    {
        super(a, b, c);

        if (!MathUtilities.doubleEquals(a.length(), b.length()))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Eq. Triangle constructed with non-congruent segments " + a.toString() + " " + b.toString()));
        }

        if (!MathUtilities.doubleEquals(a.length(), c.length()))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Eq. Triangle constructed with non-congruent segments " + a.toString() + " " + c.toString()));
        }

        if (!MathUtilities.doubleEquals(b.length(), c.length()))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Eq. Triangle constructed with non-congruent segments " + b.toString() + " " + c.toString()));
        }
    }
    
    public EquilateralTriangle(Triangle tri)
    {
       this(tri.getSegmentA(), tri.getSegmentB(), tri.getSegmentC());
    }

    public EquilateralTriangle(ArrayList<Segment> segs)
    {
        this(segs.get(0), segs.get(1), segs.get(2));
        if (segs.size() != 3) ExceptionHandler.throwException( new IllegalArgumentException("Equilateral Triangle constructed with " + segs.size() + " segments."));
    }

    public EquilateralTriangle(Point a, Point b, Point c)
    {
       this(new Segment(a, b), new Segment(a, c), new Segment(b, c));
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

//            if (tri.provenIsosceles()) return true;
//            if (tri.provenEquilateral()) return false;
//            if (tri.provenRight()) return false;

            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof EquilateralTriangle)) return false;
        
        return super.equals(obj);
    }
}
