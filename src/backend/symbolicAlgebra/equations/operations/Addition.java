package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;


public class Addition extends ArithmeticOperation
{
    public Addition()
    { 
        super();

    }
    public Addition(GroundedClause left, GroundedClause right)
    {
        super(left, right);
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
        if (obj == null || (obj instanceof Addition) == false) return false;
        return super.equals((Addition)obj);
    }
    
    public Addition deepCopy()
    {
        return new Addition(leftExp.deepCopy(), rightExp.deepCopy());
    }
}