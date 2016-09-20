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
    
    public GroundedClause DeepCopy()
    {
        return new AlgebraicSegmentEquation(this.lhs.DeepCopy(), this.rhs.DeepCopy());
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

