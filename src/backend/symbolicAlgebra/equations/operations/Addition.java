package backend.symbolicAlgebra.equations.operations;

import backend.ast.GroundedClause;


public class Addition extends ArithmeticOperation
{
    protected Addition() { super(); }

    public Addition(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

    // Make a deep copy of this object
    @Override
    public GroundedClause deepCopy()
    {
        return new Addition(leftExp.deepCopy(), rightExp.deepCopy());
    }
    
    public String toString()
    {
        return "(" + leftExp.toString() + " + " + rightExp.toString() + ")";
    }

    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " + " + rightExp.toPrettyString();
    }

    public  boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Addition)) return false;

        return super.equals(obj);
    }
}