package backend.equations;

public class GeometricAngleEquation extends AngleEquation
{
    public GeometricAngleEquation()
    {
        super();
    }
    
    public GeometricAngleEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause deepCopy()
    {
        return new GeometricAngleEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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
