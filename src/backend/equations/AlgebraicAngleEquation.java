package backend.equations;

import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class AlgebraicAngleEquation extends AngleArcEquation
{
    public AlgebraicAngleEquation()
    {
        super();
    }
    
    public AlgebraicAngleEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        super(left, right);
    }

  //public AlgebraicAngleEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just) { }

    public GroundedClause deepCopy() 
    {
        try
        {
            return new AlgebraicAngleEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
        }
        catch (ArgumentException e)
        {
            ExceptionHandler.throwException(e);
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

