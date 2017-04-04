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

    /**
     * @return parallel arrays of (1) multipliers and (2) terms
     * Example: 2 X   results in  multipliers = [1] and terms = [X]
     * Example: 2 X   results in  multipliers = [1] and terms = [X] 
     */
    @Override
    public Pair<ArrayList<Double>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<Double> multipliers = new ArrayList<Double>();
        ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();

        // Example: 2 * 4  results in  multipliers = [1] and terms = [8]
        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            multipliers.add(1.0);
            terms.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() * ((NumericValue)rightExp).getDoubleValue()));
        }

        //
        // Multiply each of the terms by the constant
        //
        // Example: 2 * (Sub-Exp = X + Y)  results in  multipliers = [2, 2] and terms = [X, Y]
        //
        else if (leftExp instanceof NumericValue)
        {
            // Left constant
            double leftMultiplier = ((NumericValue)leftExp).getDoubleValue();

            // Sub-expression multipliers and terms
            multipliers = rightExp.collectTerms().getKey();
            terms = rightExp.collectTerms().getValue();

            // Multiply the left multiplier of the set of multipliers
            for (int m = 0; m < multipliers.size(); m++)
            {
                multipliers.set(m, leftMultiplier * multipliers.get(m));
            }
        }

        // Same as the previous code, but with the right sub-expression
        else if (rightExp instanceof NumericValue)
        {
            // Right constant
            double rightMultiplier = ((NumericValue)rightExp).getDoubleValue();

            // Sub-expression multipliers and terms
            multipliers = leftExp.collectTerms().getKey();
            terms = leftExp.collectTerms().getValue();

            // Multiply the left multiplier of the set of multipliers
            for (int m = 0; m < multipliers.size(); m++)
            {
                multipliers.set(m, rightMultiplier * multipliers.get(m));
            }
        }

        //
        // Multiply each of the terms by the term: distribute
        //
        else if (leftExp instanceof ArithmeticOperation && rightExp instanceof ArithmeticOperation)
        {
            ExceptionHandler.throwException("Multiplication: (X+Y)*(A*B) in multiplication unimplemented.");
        }

        // Return the new <multiplier, term> set
        return new Pair<ArrayList<Double>, ArrayList<GroundedClause>>(multipliers, terms);
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

