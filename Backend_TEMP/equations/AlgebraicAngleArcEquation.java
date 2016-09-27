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

    public GroundedClause deepCopy()
    {
        return new AlgebraicAngleArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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
