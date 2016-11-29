package backend.symbolicAlgebra.equations;

import backend.ast.GroundedClause;

public class GeometricSegmentEquation extends SegmentEquation
{
    protected GeometricSegmentEquation() { super(); }

    public GeometricSegmentEquation(Equation eq)
    {
        super(eq);
    }
    
    public GeometricSegmentEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        return new GeometricSegmentEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
    }
    
    public String toString()
    {
        return "GeometricEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
