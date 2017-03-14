package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;

public class Subtraction extends ArithmeticOperation
{
    protected Subtraction() { super(); }


    public Subtraction(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

    //Fix this
    //Use Multiplication collectTerms() as a basis
    //Make Addition have a collectTerms() as well, with no negative multiplication
    public Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<GroundedClause> multipliers = new ArrayList<GroundedClause>();
        ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();

        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            multipliers.add(new NumericValue(1));
            terms.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() - ((NumericValue)rightExp).getDoubleValue()));

        }
        else if (leftExp instanceof NumericValue)
        {
            if (!(rightExp instanceof ArithmeticOperation))
                for (GroundedClause gc : rightExp.collectTerms().getKey())
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
                    multipliers.add(new NumericValue(((NumericValue)(gcMultipliers.get(i))).getDoubleValue() * -1));
                }
            }
        }
        else if (rightExp instanceof NumericValue)
        {
            if (!(leftExp instanceof ArithmeticOperation))
                for (GroundedClause gc : leftExp.collectTerms().getKey())
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
                    multipliers.add(new NumericValue(((NumericValue)(gcMultipliers.get(i))).getDoubleValue() * -1));
                }
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

    public String toString()
    {
        return "(" + leftExp.toString() + " - " + rightExp.toString() + ")";
    }

    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " - " + rightExp.toPrettyString();
    }

    public boolean equals(Object obj)
    {

        if (obj == null) return false;

        if (!(obj instanceof Subtraction)) return false;

        Subtraction that = (Subtraction)obj;

        return (this.leftExp.equals(that.leftExp) && this.rightExp.equals(that.rightExp));
    }

    public Subtraction deepCopy()
    {
        return new Subtraction(leftExp.deepCopy(), rightExp.deepCopy());
    }
}
