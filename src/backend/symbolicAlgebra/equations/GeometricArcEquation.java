package backend.symbolicAlgebra.equations;

import backend.ast.GroundedClause;

public class GeometricArcEquation extends ArcEquation
{
    protected GeometricArcEquation() { super(); }
    
    public GeometricArcEquation(Equation eq)
    {
        this(eq.lhs, eq.rhs);
    }
    public GeometricArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        return new GeometricArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
    }
    
    public int getHashCode()
    {
        return super.getHashCode();
    }
    
    public String toString()
    {
        return "GeometricEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
