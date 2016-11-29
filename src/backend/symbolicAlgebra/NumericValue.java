package backend.symbolicAlgebra;

import backend.utilities.ast_helper.*;

import backend.ast.GroundedClause;

public class NumericValue extends ArithmeticNode implements Cloneable
{
    protected double value;

    public NumericValue()
    {
        value = 0;
    }

    public NumericValue(int v)
    {
        value = v;
    }

    public NumericValue(double v)
    {
        value = v;
    }
    public NumericValue(NumericValue v)
    {
        value = v.getDoubleValue();
    }

    public String toString()
    {
        return Double.toString(value);
    }

    public String toPrettyString()
    {
        return toString();
    }

    //This method is bypassed when called from an arithmetic operation - Ryan
    public boolean containsClause(GroundedClause clause)
    {
        return this.equals(clause);
    }

    public GroundedClause deepCopy()
    {
        return new NumericValue(value);
    }

    public int getIntValue()
    {
        return (int)value;
    }

    public double getDoubleValue()
    {
        return value;
    }

//    public void substitute(char toFind, char toSub)
//    {
//
//    }
//
//    public void substitute(double toFind, double toSub)
//    {
//        if ( value == toFind)
//        {
//            value = toSub;
//        }
//
//    }
//
//    public void substitute(int toFind, int toSub)
//    {
//        substitute((double) toFind, (double) toSub);
//    }

    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof NumericValue)) return false;

        NumericValue that = (NumericValue) obj;

        return Utilities.CompareValues(value, that.value);
    }
}

