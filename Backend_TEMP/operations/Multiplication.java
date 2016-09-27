package operations;

import java.util.List;
public class Multiplication extends ArithmeticOperation
{
    public Multiplication()
    {
        super();
    }
    
    

    public Multiplication(GroundedClause left, GroundedClause right)
    {
       super(left, right);       
    }

    public String toString()
    {
        return "(" + leftExp.ToString() + " * " + rightExp.ToString() + ")";
    }

    //
    // In an attempt to avoid issues, all terms collected are copies of the originals.
    //
    public List<GroundedClause> collectTerms()
    {
        List<GroundedClause> list = new List<GroundedClause>();

        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            list.Add(new NumericValue(((NumericValue)leftExp).DoubleValue * ((NumericValue)rightExp).DoubleValue));
            return list;
        }

        if (leftExp instanceof NumericValue)
        {
            for (GroundedClause gc : rightExp.collectTerms())
            {
                GroundedClause copyGC = gc.DeepCopy();
                copyGC.multiplier *= ((NumericValue)leftExp).IntValue;
                list.Add(copyGC);
            }
        }

        if (rightExp instanceof NumericValue)
        {
            for (GroundedClause gc : leftExp.CollectTerms())
            {
                GroundedClause copyGC = gc.DeepCopy();

                copyGC.multiplier *= ((NumericValue)rightExp).IntValue;
                list.Add(copyGC);
            }
        }

        return list;
    }

    public boolean Equals(Object obj)
    {
        if (obj == null || (Multiplication)obj == null) return false;
        return super.equals((Multiplication)obj);
    }

    public int getHashCode() { return super.getHashCode(); }
}

