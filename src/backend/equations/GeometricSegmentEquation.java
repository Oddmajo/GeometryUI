package backend.equations;

import backend.ast.GroundedClause;

public class GeometricSegmentEquation extends SegmentEquation
{
    public GeometricSegmentEquation()
    {
        super();
    }

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
    
    public int getHashCode()
    {
        return super.getHashCode();
    }
    
    public String toString()
    {
        return "GeometricEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
