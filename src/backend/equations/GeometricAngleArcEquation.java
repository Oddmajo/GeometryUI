package equations;

import utilities.exception.ArgumentException;
import utilities.exception.ExceptionHandler;

public class GeometricAngleArcEquation extends AngleArcEquation
{
    public GeometricAngleArcEquation()
    {
        super();
    }
    /*
    public GeometricAngleArcEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy() throws CloneNotSupportedException
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
    */
    public int getHashCode()
    {
        return super.getHashCode();
    }
    
    public String toString()
    {
        return "GeometricEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
