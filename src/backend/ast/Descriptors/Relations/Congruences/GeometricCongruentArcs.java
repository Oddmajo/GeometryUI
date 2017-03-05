package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.arcs.Arc;

public class GeometricCongruentArcs extends CongruentArcs
{
    public GeometricCongruentArcs(Arc c1, Arc c2) { super(c1, c2); }

    @Override
    public boolean isGeometric() { return true; }
    @Override
    public boolean isAlgebraic() { return false; }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String toString() { return "GeometricCongruent(" + ca1.toString() + ", " + ca2.toString() + ") " + justification; }
}
