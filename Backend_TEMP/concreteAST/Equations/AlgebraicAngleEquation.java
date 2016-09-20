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
    
    public GroundedClause DeepCopy()
    {
        return new AlgebraicAngleEquation(this.lhs.DeepCopy(), this.rhs.DeepCopy());
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

