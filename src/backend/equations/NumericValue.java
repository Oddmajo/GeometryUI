package backend.equations;

import backend.utilities.ast_helper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import backend.ast.GroundedClause;

public class NumericValue extends ArithmeticNode implements Cloneable
{
    protected double value;
    ArrayList<Character> variables;
    
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
    
    public String toString()
    {
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
    public boolean ContainsClause(GroundedClause clause)
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
    
    public List<Character> getVariables()
    {
        return variables;
    }
    public int getHashCode()
    {
        return super.getHashCode();
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
