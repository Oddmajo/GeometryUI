package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.triangles.Triangle;

public class AlgebraicCongruentTriangles extends CongruentTriangles
{
    public AlgebraicCongruentTriangles(Triangle t1, Triangle t2) { super(t1, t2); }

    public boolean isAlgebraic() { return true; }
    public boolean isGeometric() { return false; }

    public int getHashCode() { return super.getHashCode(); }

    public String ToString() { return "AlgebraicCongruent(" + ct1.toString() + ", " + ct2.toString() + ") " + justification; }
}
