package backend.ast.figure.components.angles;


import backend.ast.figure.components.Point;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

/// <summary>
/// Represents an angle (degrees), defined by 3 points.
/// </summary>
public class RightAngle extends Angle
{
    public RightAngle(Point a, Point b, Point c)
    {
        super(a, b, c);
        if (!MathUtilities.doubleEquals(this.measure, 90))
        {
             ExceptionHandler.throwException(new DebugException("Problem"));
             ExceptionHandler.throwException( new ArgumentException("Right angles should measure 90 degrees, not (" + this.measure + ") degrees."));
        }
    }
    public RightAngle(Angle angle) 
    {
        super(angle.A, angle.B, angle.C);
        if (!MathUtilities.doubleEquals(angle.measure, 90))
        {
             ExceptionHandler.throwException(new DebugException(("Problem")));
             ExceptionHandler.throwException( new ArgumentException("Right angles should measure 90 degrees, not (" + angle.measure + ") degrees."));
        }
    }

    // CTA: Be careful with equality; this is object-based equality
    // If we check for angle measure equality that is distinct.
    // If we check to see that a different set of remote vertices describes this angle, that is distinct.
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof RightAngle)) return false;
        RightAngle angle = (RightAngle)obj;

        // Measures must be the same.
        if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;

        return super.equals(obj) && structurallyEquals(obj);
    }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String toString()
    {
        return "RightAngle( m" + A.name + B.name + C.name + " = " + measure + ") " + justification;
    }

    @Override
    public String toPrettyString()
    {
        return "Angle " + A.name + B.name + C.name + " is a right angle.";
    }

}
