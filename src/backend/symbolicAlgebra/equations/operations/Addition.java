package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;


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

    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " + " + rightExp.toPrettyString();
    }

    public  boolean equals(Object obj)
    {
        if (obj == null || (obj instanceof Addition) == false) return false;
        return super.equals((Addition)obj);
    }
    
    //NOTE
    //Print out the parallel arrays as a way to check during debugging
    public Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<GroundedClause> multipliers = new ArrayList<GroundedClause>();
        ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();

        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            terms.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() + ((NumericValue)rightExp).getDoubleValue()));
            multipliers.add(new NumericValue(1));

        }
        else if (leftExp instanceof NumericValue)
        {
            if (!(rightExp instanceof ArithmeticOperation))
                for (GroundedClause gc : rightExp.collectTerms().getKey())
                {
                    GroundedClause copyGC = null;

                    copyGC = gc.deepCopy();

                    terms.add(copyGC);
                    multipliers.add(leftExp);
                }
        }
        else if (rightExp instanceof NumericValue)
        {
            if (!(leftExp instanceof ArithmeticOperation))
                for (GroundedClause gc : leftExp.collectTerms().getKey())
                {
                    GroundedClause copyGC = null;

                    copyGC = gc.deepCopy();

                    terms.add(copyGC);
                    multipliers.add(rightExp);
                }
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

}