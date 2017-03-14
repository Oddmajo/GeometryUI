package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;

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
    public Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<GroundedClause> multipliers = new ArrayList<GroundedClause>();
        ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();

        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            multipliers.add(new NumericValue(1));
            terms.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() * ((NumericValue)rightExp).getDoubleValue()));
            
            Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> list = new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(multipliers, terms);
            return list;
        }

        else if (leftExp instanceof NumericValue)
        {
            if (!(rightExp instanceof ArithmeticOperation))
                for (GroundedClause gc : rightExp.collectTerms().getValue())
                {
                    terms.add(gc);
                    multipliers.add(leftExp);
                }
            if(rightExp instanceof ArithmeticOperation)
            {
                ArrayList<GroundedClause> gcMultipliers = rightExp.collectTerms().getKey();
                ArrayList<GroundedClause> gcTerms = rightExp.collectTerms().getValue();
                for(int i = 0; i < gcMultipliers.size(); i++)
                {
                    terms.add(gcTerms.get(i));
                    multipliers.add(new NumericValue(((NumericValue)(gcMultipliers.get(i))).getDoubleValue() * ((NumericValue)leftExp).getDoubleValue()));
                }
            }
            Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> list = new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(multipliers, terms);
            return list;
        }

        else if (rightExp instanceof NumericValue)
        {
            if (!(leftExp instanceof ArithmeticOperation))
                for (GroundedClause gc : leftExp.collectTerms().getValue())
                {
                    terms.add(gc);
                    multipliers.add(rightExp);
                }
            if(leftExp instanceof ArithmeticOperation)
            {
                ArrayList<GroundedClause> gcMultipliers = leftExp.collectTerms().getKey();
                ArrayList<GroundedClause> gcTerms = leftExp.collectTerms().getValue();
                for(int i = 0; i < gcMultipliers.size(); i++)
                {
                    terms.add(gcTerms.get(i));
                    multipliers.add(new NumericValue(((NumericValue)(gcMultipliers.get(i))).getDoubleValue() * ((NumericValue)rightExp).getDoubleValue()));
                }
            }
            Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> list = new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(multipliers, terms);
            return list;
        }
        else if ((!(leftExp instanceof ArithmeticOperation)) && (!(rightExp instanceof ArithmeticOperation)))
        {
            terms.add(leftExp);
            multipliers.add(new NumericValue(1));
            
            terms.add(rightExp);
            multipliers.add(new NumericValue(1));
        }
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> list = new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(multipliers, terms);
        return list;

    }
    public Multiplication deepCopy()
    {
        return new Multiplication(leftExp.deepCopy(), rightExp.deepCopy());
    }
    
    public boolean Equals(Object obj)
    {
        if (obj == null || (Multiplication)obj == null) return false;
        return super.equals((Multiplication)obj);
    }

    public int getHashCode() { return super.getHashCode(); }

}

