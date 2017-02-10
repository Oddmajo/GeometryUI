package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.triangles.Triangle;

public class GeometricCongruentTriangles extends CongruentTriangles
{

    public GeometricCongruentTriangles(Triangle t1, Triangle t2)
    { 
        super(t1, t2);
    }

    public boolean IsAlgebraic() { return false; }
    
    public boolean IsGeometric() { return true; }

    public String ToString() { return "GeometricCongruent(" + ct1.toString() + ", " + ct2.toString() + ") " + justification; }

    public String ToPrettyString() { return ct1.toPrettyString() + "is congruent to " + ct2.toPrettyString() + "."; }

}
