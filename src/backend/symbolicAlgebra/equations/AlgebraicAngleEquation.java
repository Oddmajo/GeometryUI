package backend.symbolicAlgebra.equations;

import backend.utilities.exception.ArgumentException;
import backend.ast.GroundedClause;
import backend.utilities.exception.ExceptionHandler;

public class AlgebraicAngleEquation extends AngleArcEquation implements Cloneable
{
    protected AlgebraicAngleEquation() { super(); }

    public AlgebraicAngleEquation(Equation eq)
    {
        this(eq.lhs, eq.rhs);
    }
    public AlgebraicAngleEquation(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

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

