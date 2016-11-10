package backend.equations;

import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.ast.GroundedClause;

public class AlgebraicAngleArcEquation extends AngleArcEquation
{
    
    public AlgebraicAngleArcEquation()
    {
        super();
    }
    
    public AlgebraicAngleArcEquation(Equation eq)
    {
        super(eq);
    }
    public AlgebraicAngleArcEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        try
        {
            return new AlgebraicAngleArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
        }
        catch (ArgumentException e)
        {
           
            ExceptionHandler.throwException(new ArgumentException(""));
        }
        return this;
    }

    public boolean isAlgebraic()
    {
        return true;
    }
    
    public boolean isGeometric()
    {
        return false;
    }
    
    public int getHashCode()
    {
        return super.getHashCode();
    }
    
    public String toString()
    {
        return "AlgebraicEquation(" + lhs + " = " + rhs + "): " + justification;
    }
    
}
