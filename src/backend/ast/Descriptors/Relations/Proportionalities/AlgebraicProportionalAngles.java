package backend.ast.Descriptors.Relations.Proportionalities;

import backend.ast.figure.components.angles.Angle;

public class AlgebraicProportionalAngles extends ProportionalAngles
{
    public AlgebraicProportionalAngles(Angle angle1, Angle angle2) { super(angle1, angle2); }

    public boolean IsAlgebraic() { return true; }
    public boolean IsGeometric() { return false; }

    public boolean Equals(Object obj)
    {
        AlgebraicProportionalAngles aps = (AlgebraicProportionalAngles)obj;
        if (aps == null) return false;
        return super.equals(aps);
    }

    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }

    public String toString()
    {
        return "AlgebraicProportional(" + largerAngle.toString() + " < " + dictatedProportion + " > " + smallerAngle.toString() + ") " + justification;
    }

}
