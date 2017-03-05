package backend.ast.Descriptors.Relations.Proportionalities;

import backend.ast.figure.components.angles.Angle;

public class GeometricProportionalAngles extends ProportionalAngles
{
    public GeometricProportionalAngles(Angle angle1, Angle angle2) { super(angle1, angle2);  }

    @Override
    public boolean isAlgebraic() { return false; }
    @Override
    public boolean isGeometric() { return true; }

    @Override
    public boolean equals(Object obj)
    {
        GeometricProportionalAngles aps = (GeometricProportionalAngles)obj;
        if (aps == null) return false;

        return super.equals(aps);
    }

    @Override
    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }

    @Override
    public String toString()
    {
        return "GeometricProportional(" + largerAngle.toString() + " < " + dictatedProportion + " > " + smallerAngle.toString() + ") " + justification;
    }

}
