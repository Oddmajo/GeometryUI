package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.arcs.Arc;

public class AlgebraicCongruentArcs extends CongruentArcs
{
    public AlgebraicCongruentArcs(Arc c1, Arc c2) { super(c1, c2); }

    @Override
    public boolean isAlgebraic() { return true; }
    @Override
    public boolean isGeometric() { return false; }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String toString() { return "AlgebraicCongruent(" + ca1.toString() + ", " + ca2.toString() + ") " + justification; }

}
