package equations;

public class AlgebraicArcEquation extends ArcEquation
{
    public AlgebraicArcEquation()
    {
        super();
    }

    public AlgebraicArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

  //public AlgebraicAngleEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just) { }
    
    public GroundedClause DeepCopy()
    {
        return new AlgebraicArcEquation(this.lhs.DeepCopy(), this.rhs.DeepCopy());
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

