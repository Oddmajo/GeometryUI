package backend.ast.figure.components;

import backend.utilities.math.Utilities;

/// <summary>
/// Represents an angle (degrees), defined by 3 points.
/// </summary>
public class RightAngle extends Angle
{
    public RightAngle(Point a, Point b, Point c)
    {
        super(a, b, c);
        if (!Utilities.doubleEquals(this.measure, 90))
        {
            //                System.Diagnostics.Debug.WriteLine("Problem");
            // throw new ArgumentException("Right angles should measure 90 degrees, not (" + this.measure + ") degrees.");
        }
    }
    public RightAngle(Angle angle) 
    {
        super(angle.A, angle.B, angle.C);
        if (!Utilities.doubleEquals(angle.measure, 90))
        {
            //                System.Diagnostics.Debug.WriteLine("Problem");
            // throw new ArgumentException("Right angles should measure 90 degrees, not (" + angle.measure + ") degrees.");
        }
    }

    // CTA: Be careful with equality; this is object-based equality
    // If we check for angle measure equality that is distinct.
    // If we check to see that a different set of remote vertices describes this angle, that is distinct.
    @Override
    public boolean Equals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof RightAngle)) return false;
        RightAngle angle = (RightAngle)obj;

        // Measures must be the same.
        if (!Utilities.doubleEquals(this.measure, angle.measure)) return false;

        return super.Equals(obj) && StructurallyEquals(obj);
    }

    @Override
    public int GetHashCode() { return super.GetHashCode(); }

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
