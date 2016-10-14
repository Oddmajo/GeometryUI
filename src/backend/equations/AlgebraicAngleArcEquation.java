package equations;

import utilities.exception.ArgumentException;

public class AlgebraicAngleArcEquation extends AngleArcEquation
{
    public AlgebraicAngleArcEquation()
    {
        super();
    }

    public AlgebraicAngleArcEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        super(left, right);
    }

    public GroundedClause deepCopy() throws CloneNotSupportedException
    {
        try
        {
            return new AlgebraicAngleArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
        }
        catch (ArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
