package equations.operations;

import java.util.ArrayList;
import equations.*;
import utilities.exception.ExceptionHandler;
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
        return "(" + leftExp.toString() + " * " + rightExp.toString() + ")";
    }
    
    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " * " + rightExp.toPrettyString();
    }

    //
    // In an attempt to avoid issues, all terms collected are copies of the originals.
    //
    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            list.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() * ((NumericValue)rightExp).getDoubleValue()));
            return list;
        }

        if (leftExp instanceof NumericValue)
        {
            for (GroundedClause gc : rightExp.collectTerms())
            {
                GroundedClause copyGC = null;
                try
                {
                    copyGC = gc.deepCopy();
                }
                catch (CloneNotSupportedException e)
                {
                    // TODO Auto-generated catch block
                    ExceptionHandler.throwException(e);
                }
                copyGC.setMultiplier(((NumericValue)leftExp).getIntValue());
                list.add(copyGC);
            }
        }

        if (rightExp instanceof NumericValue)
        {
            for (GroundedClause gc : leftExp.collectTerms())
            {
                GroundedClause copyGC = null;
                try
                {
                    copyGC = gc.deepCopy();
                }
                catch (CloneNotSupportedException e)
                {
                    ExceptionHandler.throwException(e);
                }

                copyGC.setMultiplier(((NumericValue)rightExp).getIntValue());
                list.add(copyGC);
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

