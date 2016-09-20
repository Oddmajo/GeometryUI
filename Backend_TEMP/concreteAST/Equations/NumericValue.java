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
        NumericValue clauseValue = (NumericValue) clause;
        if (clauseValue == null)
        {
            return false;
        }
        
        return Utilities.CompareValues(value, clauseValue.value);
    }
    
    public GroundedClause DeepCopy()
    {
        return (NumericValue)this.MemberwiseClone();
    }
    
    public int getIntValue()
    {
        return (int)value;
    }
    
    public double DoubleValue()
    {
        return value;
    }
    
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    public boolean Equals(Object obj)
    {
        NumericValue thatVal = (NumericValue) obj;
        if (thatVal == null)
        {
            return false;
        }
        
        return Utilities.CompareValues(value, thatVal.value);
    }
}
