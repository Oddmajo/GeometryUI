package backend.symbolicAlgebra;

import backend.utilities.ast_helper.*;

import java.util.ArrayList;
import java.util.Collections;

import backend.ast.GroundedClause;

public class NumericValue extends ArithmeticNode implements Cloneable
{
    protected double value;
    protected ArrayList<Character> variables = new ArrayList<Character>();

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
        variables = v.getVariables();
    }

    public NumericValue(int v, ArrayList<Character> c)
    {
        value = v;
        variables = c;

    }

    public NumericValue(double v, ArrayList<Character> c)
    {
        value = v;
        variables = c;
    }

    public NumericValue(NumericValue v, ArrayList<Character> c)
    {
        value = v.getDoubleValue();
        variables = c;
    }

    public String toString()
    {
        Collections.sort(variables);
        String v = "";
        for (char c : variables)
            v += c;
        return value + v;
    }

    public String toPrettyString()
    {
        Collections.sort(variables);
        String v = "";
        for (char c : variables)
            v += c;
        if (getDoubleValue() == getIntValue())
            return getIntValue() + v;
        else
            return toString();
    }

    //This method is bypassed when called from an arithmetic operation - Ryan
    public boolean containsClause(GroundedClause clause)
    {

        if ((NumericValue)clause == null)
        {
            return false;
        }
        NumericValue clauseValue = (NumericValue) clause;
        return Utilities.CompareValues(value, clauseValue.value);
    }

    public GroundedClause deepCopy()
    {
        return super.deepCopy();
    }

    public int getIntValue()
    {
        return (int)value;
    }

    public double getDoubleValue()
    {
        return value;
    }
    public ArrayList<Character> getVariables()
    {
        return variables;
    }

    public int getHashCode()
    {
        return super.getHashCode();
    }

    public void substitute(char toFind, char toSub)
    {
        int index = 0;
        for (char c : variables)
        {
            if (c == toFind)
            {
                variables.remove(index);
                variables.add(toSub);
                break;
            }
            index++;
        }
    }

    public void substitute(double toFind, double toSub)
    {
        if ( value == toFind)
        {
            value = toSub;
        }

    }

    public void substitute(int toFind, int toSub)
    {
        substitute((double) toFind, (double) toSub);
    }

    public boolean containsVariable(char toFind)
    {
        for (char c : variables)
        {
            if (c == toFind)
            {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj)
    {
        if (obj == null || (NumericValue) obj == null)
        {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
            return false;
        }
        NumericValue thatVal = (NumericValue) obj;
        return Utilities.CompareValues(value, thatVal.value);
    }
}

