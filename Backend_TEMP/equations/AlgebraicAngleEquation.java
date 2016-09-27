package equations;

public class AlgebraicAngleEquation extends AngleArcEquation
{
    public AlgebraicAngleEquation()
    {
        super();
    }

    public AlgebraicAngleEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

  //public AlgebraicAngleEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just) { }
    
    public GroundedClause deepCopy()
    {
        return new AlgebraicAngleEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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

