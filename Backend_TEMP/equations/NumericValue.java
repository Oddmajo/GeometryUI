package equations;

public class NumericValue extends ArithmeticNode
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
    
    public boolean ContainsClause(GroundedClause clause)
    {
        
        if ((NumericValue)clause == null)
        {
            return false;
        }
        NumericValue clauseValue = (NumericValue) clause;
        return utilities.compareValues(value, clauseValue.value);
    }
    
    public GroundedClause deepCopy()
    {
        return (NumericValue)this.memberwiseClone();
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
        return utilities.compareValues(value, thatVal.value);
    }
}
