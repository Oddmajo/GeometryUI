package backend.equations;

import backend.utilities.exception.ArgumentException;
import backend.ast.GroundedClause;
import backend.utilities.exception.ExceptionHandler;

public class GeometricAngleArcEquation extends AngleArcEquation
{
    public GeometricAngleArcEquation()
    {
        super();
    }
    
    public GeometricAngleArcEquation(Equation eq)
    {
        super(eq);
    }
    public GeometricAngleArcEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        try
        {
            return new GeometricAngleArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
        }
        catch (ArgumentException e)
        {
            // TODO Auto-generated catch block
            ExceptionHandler.throwException(e);
        }
        return this;
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
