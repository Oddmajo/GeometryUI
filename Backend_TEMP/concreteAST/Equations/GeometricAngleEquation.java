package equations;

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
    
    public GroundedClause DeepCopy()
    {
        return new GeometricAngleEquation(this.lhs.DeepCopy, this.rhs.DeepCopy);
    }
    
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    public String toString()
    {
        return "GeometricEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
