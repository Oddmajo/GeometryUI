package equations.operations;

import equations.*;

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
        if (obj == null || (Addition)obj == null) return false;
        return super.equals((Addition)obj);
    }

    public int getHashCode() {  return super.getHashCode(); }
    
    public boolean containsClause(GroundedClause c)
    {
        if (leftExp.containsClause(c) || rightExp.containsClause(c))
            return true;
        return false;
    }
}