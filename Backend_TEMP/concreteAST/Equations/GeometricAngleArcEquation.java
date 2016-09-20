package equations;

public class GeometricAngleArcEquation extends AngleArcEquation
{
    public GeometricAngleArcEquation()
    {
        super();
    }
    
    public GeometricAngleArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause DeepCopy()
    {
        return new GeometricAngleArcEquation(this.lhs.DeepCopy, this.rhs.DeepCopy);
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
