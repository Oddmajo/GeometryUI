package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.Circle;

public class AlgebraicCongruentCircles extends CongruentCircles
{
    public AlgebraicCongruentCircles(Circle c1, Circle c2)
    {
        super(c1, c2);
    }

    @Override public boolean isAlgebraic() { return true; }
    
    @Override public boolean isGeometric() { return false; }

    @Override
    public String toString() { return "AlgebraicCongruent(" + cc1.toString() + ", " + cc2.toString() + ") " + justification; }
}
