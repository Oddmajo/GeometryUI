package equations;

public class GeometricSegmentEquation extends SegmentEquation
{
    public GeometricSegmentEquation()
    {
        super();
    }
    
    public GeometricSegmentEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }
    
    public GroundedClause DeepCopy()
    {
        return new GeometricSegmentEquation(this.lhs.DeepCopy, this.rhs.DeepCopy);
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
