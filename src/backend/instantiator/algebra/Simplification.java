package backend.instantiator.algebra;

import backend.equations.*;
import backend.instantiator.GenericRule;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.list.Utilities;
import backend.equations.operations.*;
import backend.ast.GroundedClause;

import java.util.ArrayList;
import java.util.List;

public class Simplification extends GenericRule
{
    //private static read-only string NAME = "Simplification";

    //
    // Given an equation, simplify algebraically using the following notions:
    //     A + A = B  -> 2A = B
    //     A + B = B + C -> A = C
    //     A + B = 2B + C -> A = B + C
    //

    public static Equation simplify(Equation original) throws CloneNotSupportedException, ArgumentException
    {
        //Do we have an equation?
        if (original.equals(null))
        {
            ExceptionHandler.throwException(new ArgumentException(""));
        }

        //Is the equation 0=0?  This should be allowed as it indicates a tautology.
        if (original.lhs.equals(new NumericValue(0)) && original.rhs.equals(new NumericValue(0)))
        {
            return original;
            //ExceptionHandler.throwException(new ArgumentException("Should not have an equation that is 0 = 0: " + original.toString()));
        }

        //
        // Ideally, flattening would:
        // Remove all subtractions -> adding a negative instead
        // Distribute subtraction or multiplication over addition
        //
        // Flatten the equation so that each side is a sum of atomic expressions
        //   Equation copyEq = (Equation) original.deepCopy();
        FlatEquation flattened = new FlatEquation(original.lhs.collectTerms(), original.rhs.collectTerms());

        //Debug.WriteLine("Equation prior to simplification: " + flattened.ToString());

        // Combine terms only on each side (do not cross =)
        FlatEquation combined = combineLikeTerms(flattened);

        //Debug.WriteLine("Equation after like terms combined on both sides: " + combined);

        // Combine terms across the equal sign
        FlatEquation across = combineLikeTermsAcrossEqual(combined);

        //Debug.WriteLine("Equation after simplifying both sides: " + across);

        FlatEquation constSimplify = SimplifyForMultipliersAndConstants(across);

        Equation inflated;
        GroundedClause singleLeftExp = inflateEntireSide(constSimplify.lhsExps);
        GroundedClause singleRightExp = inflateEntireSide(constSimplify.rhsExps);

        if (original instanceof AlgebraicSegmentEquation)
        {
            inflated = new AlgebraicSegmentEquation(singleLeftExp, singleRightExp); 
        }
        else if (original instanceof GeometricSegmentEquation)
        {
            inflated = new GeometricSegmentEquation(singleLeftExp, singleRightExp); 
        }
        else if (original instanceof AlgebraicAngleEquation)
        {
            inflated = new AlgebraicAngleEquation(singleLeftExp, singleRightExp); 
        }
        else if (original instanceof GeometricAngleEquation)
        {
            inflated = new GeometricAngleEquation(singleLeftExp, singleRightExp);         
        }
        else if (original instanceof AlgebraicArcEquation)
        {
            inflated = new AlgebraicArcEquation(singleLeftExp, singleRightExp); 
        }
        else if (original instanceof GeometricArcEquation)
        {
            inflated = new GeometricArcEquation(singleLeftExp, singleRightExp); 
        }
        else if (original instanceof AlgebraicAngleArcEquation)
        {
            inflated = new AlgebraicAngleArcEquation(singleLeftExp, singleRightExp);
        }
        else if (original instanceof GeometricAngleArcEquation)
        {
            inflated = new GeometricAngleArcEquation(singleLeftExp, singleRightExp); 
        }
        else
        {
            inflated = original;
        }

        if (inflated.equals(original))
        {
            return original;
        }

        //
        // 0 = 0 should not be allowable.
        //
        if (inflated.lhs.equals(new NumericValue(0)) && inflated.rhs.equals(new NumericValue(0)))
        {
            return null;
        }

        return inflated;
    }

    //
    // Check for (basically) atomic equations involving one unknown and constants otherwise.
    //
    private static FlatEquation SimplifyForMultipliersAndConstants(FlatEquation inEq)
    {
        if (inEq.lhsExps.size() != 1 || inEq.rhsExps.size() != 1) return inEq;

        //
        // Figure out what we're looking at.
        //
        NumericValue value = null;
        GroundedClause unknown = null;

        if (inEq.lhsTermAt(0) instanceof NumericValue)
        {
            value = (NumericValue)inEq.lhsTermAt(0);
            unknown = inEq.rhsTermAt(0);
        }
        else if (inEq.rhsTermAt(0) instanceof NumericValue)
        {
            value = (NumericValue)inEq.rhsTermAt(0);
            unknown = inEq.rhsTermAt(0);
        }
        else
        {
            return inEq;
        }

        //
        // Divide both sides to simplify.
        //
        if (unknown.getMulitplier() != 1)
        {
            NumericValue newValue = new NumericValue(value.getDoubleValue() / unknown.getMulitplier());

            // reset the multiplier
            unknown.setMultiplier(1);

            return new FlatEquation(Utilities.makeList((GroundedClause)unknown), Utilities.makeList((GroundedClause)newValue));
        }

        // Nothing happened so return original
        return inEq;
    }

    //
    // Inflate a single term possibly creating a subtraction and / or multiplication node
    //
    private static GroundedClause inflateTerm(GroundedClause clause)
    {
        GroundedClause newClause = null;

        // This may not be necessary....
        // If the multiplier is non-unit (not 1), create a multiplication node
        if (Math.abs(clause.getMulitplier()) != 1)
        {
            newClause = new Multiplication(new NumericValue(Math.abs(clause.getMulitplier())), clause);
        }

        // If the multiplier is negative, convert to subtraction
        if (clause.getMulitplier() < 0)
        {
            newClause = new Subtraction(new NumericValue(0), newClause == null ? clause : newClause);
        }
        // Reset multiplier accordingly
        clause.setMultiplier(1);

        return newClause == null ? clause : newClause;
    }
    //
    // Inflate am entire flattened side of an equation
    //
    private static GroundedClause inflateEntireSide(List<GroundedClause> side)
    {
        GroundedClause singleExp;

        if (side.size() <= 1)
        {
            singleExp = inflateTerm(side.get(0));
        }
        else
        {
            singleExp = new Addition(inflateTerm(side.get(0)), inflateTerm(side.get(1)));
            for (int i = 2; i < side.size(); i++)
            {
                singleExp = new Addition(singleExp, inflateTerm(side.get(i)));
            }
        }

        return singleExp;
    }

    private static FlatEquation combineLikeTerms(FlatEquation eq)
    {
        return new FlatEquation(combineSideLikeTerms(eq.lhsExps), combineSideLikeTerms(eq.rhsExps));
    }

    private static List<GroundedClause> combineSideLikeTerms(List<GroundedClause> sideExps)
    {
        if (sideExps.size() == 0) return sideExps;

        if (sideExps.size() == 1)
        {
                return Utilities.makeList((GroundedClause)sideExps.get(0).deepCopy());
        }

        // The new simplified side of the equation
        List<GroundedClause> simp = new ArrayList<GroundedClause>();

        // To collect all constants
        List<NumericValue> constants = new ArrayList<NumericValue>();

        // To avoid checking nodes we have already combined
        boolean[] checkedExp = new boolean[sideExps.size()];//Auto-init to false
        for (int i = 0; i < sideExps.size(); i++)
        {
            if (!checkedExp[i])
            {
                GroundedClause iExp = sideExps.get(i);

                // Collect all constants specially
                if (iExp instanceof NumericValue)
                {
                    constants.add((NumericValue)iExp);
                }
                else
                {
                    // Collect all like terms on this side
                    List<GroundedClause> likeTerms = new ArrayList<GroundedClause>();
                    for (int j = i + 1; j < sideExps.size(); j++)
                    {
                        // If same node, add to the list
                        if (sideExps.get(i).structurallyEquals(sideExps.get(j)))
                        {
                            likeTerms.add(sideExps.get(j));
                            checkedExp[j] = true;
                        }
                    }

                    // Combine all the terms together into one node
                    GroundedClause copyExp;                
                    try
                    {
                        copyExp = iExp.deepCopy(); // Note, iExp represents the 'first' like term
                    }
                    catch (Exception e)
                    {
                        copyExp = null;
                        ExceptionHandler.throwException(e);
                    }
                    for (GroundedClause term : likeTerms)
                    {
                        copyExp.setMultiplier(copyExp.getMulitplier() + term.getMulitplier()) ;
                    }
                    // If everything cancels, add a 0 to the equation
                    if (copyExp.getMulitplier() == 0)
                    {
                        constants.add(new NumericValue(0));
                    }
                    else
                    {
                        simp.add(copyExp);
                    }
                }
            }

            checkedExp[i] = true;


        }
        //
        // Combine all the constants together
        //
        if (!constants.isEmpty())
        {
            double sum = 0;
            for (NumericValue constant : constants)
            {
                if (constant instanceof NumericValue)
                {
                    sum += ((NumericValue)constant).getDoubleValue();
                }
            }
            simp.add(new NumericValue(sum));
        }
        return simp;
    }

    private static FlatEquation combineLikeTermsAcrossEqual(FlatEquation eq) throws CloneNotSupportedException
    {
        // The new simplified side of the equation
        List<GroundedClause> leftSimp = new ArrayList<GroundedClause>();
        List<GroundedClause> rightSimp = new ArrayList<GroundedClause>();

        // The resultant constant values on the left / right sides
        Pair<Double, Double> constantPair = handleConstants(eq);

        boolean[] rightCheckedExp = new boolean[eq.rhsExps.size()];
        for (GroundedClause leftExp : eq.lhsExps)
        {
            if (!(leftExp instanceof NumericValue))
            {
                //HALP
                int rightExpIndex = backend.utilities.ast_helper.Utilities.StructuralIndex(eq.rhsExps, leftExp);

                //
                // Left expression has like term on the right?
                //
                // it doesn't have a like term
                if (rightExpIndex == -1)
                {
                    leftSimp.add(leftExp);
                }
                //
                // Expression has like term on the right
                //
                else
                {
                    rightCheckedExp[rightExpIndex] = true;
                    GroundedClause rightExp = eq.rhsExps.get(rightExpIndex);
                    GroundedClause copyExp = leftExp.deepCopy();

                    // Seek to keep positive values for the resultant, simplified expression
                    if (leftExp.getMulitplier() - rightExp.getMulitplier() > 0)
                    {
                        copyExp.setMultiplier(leftExp.getMulitplier() - rightExp.getMulitplier());
                        leftSimp.add(copyExp);
                    }
                    else if (rightExp.getMulitplier() - leftExp.getMulitplier() > 0)
                    {
                        copyExp.setMultiplier(rightExp.getMulitplier() - leftExp.getMulitplier());
                        rightSimp.add(copyExp);
                    }
                    else // Cancellation of the terms
                    {
                    }
                }
            }
        }
        // Pick up all the expressions on the right hand side which were not like terms of those on the left
        for (int i = 0; i < eq.rhsExps.size(); i++)
        {
            if (!rightCheckedExp[i] && !(eq.rhsExps.get(i) instanceof NumericValue))
            {
                rightSimp.add(eq.rhsExps.get(i));
            }
        }

        //
        // Add back the constant to the correct side
        //
        if (constantPair.getKey() != 0) leftSimp.add(new NumericValue(constantPair.getKey()));
        if (constantPair.getValue() != 0) rightSimp.add(new NumericValue(constantPair.getValue()));

        //
        // Now check coefficients: both sides all have coefficients that evenly divide the other side.
        //
        if (!leftSimp.isEmpty() && !rightSimp.isEmpty())
        {
            // Calculate the gcd
            int gcd = leftSimp.get(0).getMulitplier();
            for (int i = 1; i < leftSimp.size(); i++)
            {
                gcd = backend.utilities.math.MathUtilities.GCD(gcd, leftSimp.get(i).getMulitplier());
            }
            for (GroundedClause rightExp : rightSimp)
            {
                gcd = backend.utilities.math.MathUtilities.GCD(gcd, rightExp.getMulitplier());
            }

            if (gcd != 1)
            {
                for (GroundedClause leftExp : leftSimp)
                {
                    leftExp.setMultiplier(leftExp.getMulitplier() / gcd);
                }
                for (GroundedClause rightExp : rightSimp)
                {
                    rightExp.setMultiplier(rightExp.getMulitplier() / gcd);
                }
            }
        }
        // Check for extreme case in which one side has no elements; in this case, add a zero
        if (leftSimp.isEmpty()) leftSimp.add(new NumericValue(0));
        if (rightSimp.isEmpty()) rightSimp.add(new NumericValue(0));

        return new FlatEquation(leftSimp, rightSimp);
    }

    //
    // Returns a positive constant on the LHS or RHS as appropriate.
    //
    private static Pair<Double, Double> handleConstants(FlatEquation eq)
    {
        double lhs = collectConstants(eq.lhsExps);
        double rhs = collectConstants(eq.rhsExps);

        double simpLeft = lhs > rhs ? lhs - rhs : 0;
        double simpRight = rhs > lhs ? rhs - lhs : 0;

        return new Pair<Double, Double>(simpLeft, simpRight);
    }

    //
    // Sum the constants on one side of an equation
    //
    private static double collectConstants(List<GroundedClause> side)
    {
        double result = 0;
        for (GroundedClause term : side)
        {
            if (term instanceof NumericValue)
            {
                result += term.getMulitplier() * ((NumericValue)term).getDoubleValue();
            }
        }
        return result;
    }
}
