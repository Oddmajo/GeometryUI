package equations;

public class GeometricArcEquation extends ArcEquation
{
    public GeometricArcEquation()
    {
        super();
    }
    
    public GeometricArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause DeepCopy()
    {
        return new GeometricArcEquation(this.lhs.DeepCopy, this.rhs.DeepCopy);
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
