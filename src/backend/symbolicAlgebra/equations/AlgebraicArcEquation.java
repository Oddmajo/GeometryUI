package backend.symbolicAlgebra.equations;

import backend.ast.GroundedClause;
public class AlgebraicArcEquation extends ArcEquation
{
    public AlgebraicArcEquation()
    {
        super();
    }
    
    public AlgebraicArcEquation(Equation eq)
    {
        super(eq);
    }

    public AlgebraicArcEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

  //public AlgebraicAngleEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just) { }
    
    public GroundedClause deepCopy()
    {
        return new AlgebraicArcEquation(this.lhs.deepCopy(), this.rhs.deepCopy());
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

