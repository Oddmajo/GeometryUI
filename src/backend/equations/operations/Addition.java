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

    public  boolean equals(Object obj)
    {
        if (obj == null || (Addition)obj == null) return false;
        return super.equals((Addition)obj);
    }

    public int getHashCode() {  return super.getHashCode(); }
}