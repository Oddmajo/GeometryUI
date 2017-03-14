package backend.symbolicAlgebra.equations;

import backend.utilities.exception.ArgumentException;
import backend.ast.GroundedClause;
import backend.utilities.exception.ExceptionHandler;

public class GeometricAngleArcEquation extends AngleArcEquation
{
    public GeometricAngleArcEquation(Equation eq)
    {
        this(eq.lhs, eq.rhs);
    }
    
    public GeometricAngleArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        return new GeometricAngleArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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
