package equations;

public class AlgebraicSegmentEquation extends SegmentEquation
{
    public AlgebraicSegmentEquation()
    {
        super();
    }
    
    public AlgebraicSegmentEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

  //public AlgebraicSegmentEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just) { }
    
    public GroundedClause deepCopy() throws CloneNotSupportedException
    {
        return new AlgebraicSegmentEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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

