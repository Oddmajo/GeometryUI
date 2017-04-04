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

    /**
     * @return parallel arrays of (1) multipliers and (2) terms
     * Example: X - (2 * Y)   results in  multipliers = [1, -2] and terms = [X, Y]
     */
    @Override
    public Pair<ArrayList<Double>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<Double> multipliers = new ArrayList<Double>();
        ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();

        // Example: 2 - 7  results in  multipliers = [1] and terms = [-5]
        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            multipliers.add(1.0);
            terms.add(new NumericValue(-1.0 * ((NumericValue)rightExp).getDoubleValue()));
        }

        //
        // Example: Left - (X + Y)  results in  multipliers = [Left-Multis, -1, -1] and terms = [Left-Terms, X, Y]
        //
        else
        {
            // Sub-expressions on the left get copied like normal
            multipliers = leftExp.collectTerms().getKey();
            terms = leftExp.collectTerms().getValue();

            // Right-hand side we collect and negate those multipliers.
            Pair<ArrayList<Double>, ArrayList<GroundedClause>> right = rightExp.collectTerms();
            ArrayList<Double> rightMultipliers = right.getKey();
            ArrayList<GroundedClause> rightTerms = right.getValue();

            // Multiply each of the terms by negative 1 (subtracting)
            for (int m = 0; m < rightMultipliers.size(); m++)
            {
                rightMultipliers.set(m, -1.0 * rightMultipliers.get(m));
            }

            // Add the right expressions to the multiplier and term lists
            multipliers.addAll(rightMultipliers);
            terms.addAll(rightTerms);
        }

        // Return the new <multiplier, term> set
        return new Pair<ArrayList<Double>, ArrayList<GroundedClause>>(multipliers, terms);
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
