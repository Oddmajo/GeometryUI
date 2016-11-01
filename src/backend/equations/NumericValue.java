package backend.equations;

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
    
    public String toString()
    {
        return value + "";
    }
    
    public String toPrettyString()
    {
        if (getDoubleValue() == getIntValue())
            return getIntValue() + "";
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
