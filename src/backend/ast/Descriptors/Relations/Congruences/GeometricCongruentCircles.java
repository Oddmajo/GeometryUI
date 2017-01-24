package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.Circle;

public class GeometricCongruentCircles extends CongruentCircles
{
    public GeometricCongruentCircles(Circle c1, Circle c2)
    {
        super(c1, c2);
    }

    @Override public boolean isAlgebraic() { return false; }
    
    @Override public boolean isGeometric() { return true; }

    @Override
    public String toString() { return "GeometricCongruent(" + cc1.toString() + ", " + cc2.toString() + ") " + justification; }
}
