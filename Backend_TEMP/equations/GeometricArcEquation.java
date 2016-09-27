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
    
    public GroundedClause deepCopy()
    {
        return new GeometricArcEquation(this.lhs.deepCopy, this.rhs.deepCopy);
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
