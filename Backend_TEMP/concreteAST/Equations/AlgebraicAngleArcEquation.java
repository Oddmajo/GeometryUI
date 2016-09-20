package equations;

public class AlgebraicAngleArcEquation extends AngleArcEquation
{
    public AlgebraicAngleArcEquation()
    {
        super();
    }

    public AlgebraicAngleArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

    public GroundedClause DeepCopy()
    {
        return new AlgebraicAngleArcEquation(this.lhs.DeepCopy(), this.rhs.DeepCopy());
    }

    public boolean IsAlgebraic()
    {
        return true;
    }
    
    public boolean IsGeometric()
    {
        return false;
    }
    
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    public String toString()
    {
        return "AlgebraicEquation(" + lhs + " = " + rhs + "): " + justification;
    }
}
