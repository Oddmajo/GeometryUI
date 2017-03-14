package backend.deductiveRules.algebra;

import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.list.Utilities;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.*;
import backend.symbolicAlgebra.equations.operations.*;

import java.util.ArrayList;
import java.util.List;

import com.google.common.math.IntMath;

public class Simplification
{    
    //
    // Given an equation, simplify algebraically using the following notions:
    //     A + A = B  -> 2A = B
    //     A + B = B + C -> A = C
    //     A + B = 2B + C -> A = B + C
    //

    //Copied from EquationGenerator
    protected static int gcd(GroundedClause a, GroundedClause b)
    {
        // Google Guava defined gcd: // https://github.com/google/guava/wiki/Release20
        return IntMath.gcd(Math.abs(((NumericValue)(a)).getIntValue()), Math.abs(((NumericValue)(b)).getIntValue()));
    }

    protected static int gcd(int a, GroundedClause b)
    {
        // Google Guava defined gcd: // https://github.com/google/guava/wiki/Release20
        return IntMath.gcd(Math.abs(a), Math.abs(((NumericValue)(b)).getIntValue()));
    }
    //Copied from EquationGenerator
    protected static int gcd(List<GroundedClause> values)
    {
        if (values.size() < 2) return 1;

        boolean allZero = true;
        for (GroundedClause gc: values)
        {
            if (!(gc.equals(new NumericValue(0))))
            {
                allZero = false;
                break;
            }
        }

        if (allZero)
            return 0;
        
        int current = gcd(values.get(0), values.get(1));

        for (int index = 2; index < values.size(); index++)
        {
            // break out early
            if (current == 1) break;

            current = gcd(current, values.get(index));
        }

        return current;
    }


    public static Equation simplify(Equation original) throws CloneNotSupportedException, ArgumentException
    {
        //Do we have an equation?
        if (original == null)
        {
            ExceptionHandler.throwException(new ArgumentException(""));
        }

        //Is the equation 0=0?  This should be allowed as it indicates a tautology.
        //NOTE:  This should be reviewed as to whether an exception should be thrown or not
        if (original.getLHS().equals(new NumericValue(0)) && original.getRHS().equals(new NumericValue(0)))
        {
            //ExceptionHandler.throwException(new ArgumentException("Should not have an equation that is 0 = 0: " + original.toString()));
            return original;
        }

        //
        // Ideally, flattening would:
        // Remove all subtractions -> adding a negative instead
        // Distribute subtraction or multiplication over addition
        //
        // Flatten the equation so that each side is a sum of atomic expressions
        // Equation copyEq = (Equation) original.deepCopy();
        //VariableFactory varFact = new VariableFactory();

        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> left = original.getLHS().collectTerms();
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> right = original.getRHS().collectTerms();

        FlatEquation flattened = new FlatEquation(left.getValue(), right.getValue());

        ArrayList<GroundedClause>leftSideMultipliers = new ArrayList<GroundedClause>(left.getKey());
        //NumericValue[] leftSideMultiplers = new NumericValue[flattened.lhsExps.size()];

        ArrayList<GroundedClause> rightSideMultipliers = new ArrayList<GroundedClause>(right.getKey());
        //NumericValue[] rightSideMultiplers = new NumericValue[flattened.rhsExps.size()];

        //Debug.WriteLine("Equation prior to simplification: " + flattened.ToString());

        // Combine terms only on each side (do not cross =)
        // Pair<ArrayList<GroundedClause>, FlatEquation> test = new Pair<ArrayList<GroundedClause>, FlatEquation>(original.getLHS().collectTerms().getKey(), combineLikeTerms(flattened, multiplierList));

        EquationCollection combineLikeTerms = combineLikeTerms(flattened, leftSideMultipliers, rightSideMultipliers);
        FlatEquation combined = combineLikeTerms.getEquation();
        leftSideMultipliers = combineLikeTerms.getLeft();
        rightSideMultipliers = combineLikeTerms.getRight();

        //NOTE
        //Every time you modify the FlatEquation, modify the multiplier lists as well.

        //leftSideMultipliers = new ArrayList<GroundedClause>(original.getLHS().collectTerms().getKey());
        //rightSideMultipliers = new ArrayList<GroundedClause>(original.getRHS().collectTerms().getKey());
        //Debug.WriteLine("Equation after like terms combined on both sides: " + combined);

        // Combine terms across the equal sign
        combineLikeTerms = combineLikeTermsAcrossEqual(combined, leftSideMultipliers, rightSideMultipliers);
        FlatEquation across = combineLikeTerms.getEquation();
        leftSideMultipliers = combineLikeTerms.getLeft();
        rightSideMultipliers = combineLikeTerms.getRight();

        //Debug.WriteLine("Equation after simplifying both sides: " + across);

        FlatEquation constSimplify = SimplifyForMultipliersAndConstants(across);

        boolean constantFound = false;
        for (int i = 0; i < constSimplify.lhsExps.size(); i++)
        {
            if (constSimplify.lhsExps.get(i) instanceof NumericValue)
            {
                GroundedClause storedMultiplier = leftSideMultipliers.get(i);
                GroundedClause storedVariable = constSimplify.lhsExps.get(i);
                leftSideMultipliers.set(i, storedVariable);
                constSimplify.lhsExps.set(i, storedMultiplier);
                constantFound = true;
            }
        }
        if (!constantFound)
        {
            for (int i = 0; i < constSimplify.rhsExps.size(); i++)
            {
                if (constSimplify.rhsExps.get(i) instanceof NumericValue)
                {
                    GroundedClause storedMultiplier = rightSideMultipliers.get(i);
                    GroundedClause storedVariable = constSimplify.rhsExps.get(i);
                    rightSideMultipliers.set(i, storedVariable);
                    constSimplify.rhsExps.set(i, storedMultiplier);
                }
            }
        }
        if ((gcd(leftSideMultipliers) != 0) && (gcd(rightSideMultipliers) != 0))
        {
            //Compile a list of all of the multipliers, then check for a GCD among all sides
            ArrayList<GroundedClause> allMultipliers = new ArrayList<GroundedClause>();
            allMultipliers.addAll(leftSideMultipliers);
            allMultipliers.addAll(rightSideMultipliers);

            int gcd = gcd(allMultipliers);
            if (gcd != 1)
            {
                ArrayList<GroundedClause> newLeft = new ArrayList<GroundedClause>();
                ArrayList<GroundedClause> newRight = new ArrayList<GroundedClause>();
                int counter = 0;

                while (counter < leftSideMultipliers.size())
                {
                    newLeft.add(new NumericValue(((NumericValue)(leftSideMultipliers.get(counter))).getDoubleValue() / gcd));
                    counter++;
                }
                while (counter < allMultipliers.size())
                {
                    newRight.add(new NumericValue(((NumericValue)(rightSideMultipliers.get(counter - leftSideMultipliers.size()))).getDoubleValue() / gcd));
                    counter++;
                }
                leftSideMultipliers = newLeft;
                rightSideMultipliers = newRight;
            }
        }
        else //One of the sides is equal to zero
        {
            ArrayList<GroundedClause> newMults = new ArrayList<GroundedClause>();
            if (gcd(leftSideMultipliers) == 0)
            {
                for (GroundedClause gc : rightSideMultipliers)
                    newMults.add(new NumericValue(0));
                rightSideMultipliers = newMults;
            }
            else
            {
                 for (GroundedClause gc : leftSideMultipliers)
                    newMults.add(new NumericValue(0));
                 leftSideMultipliers = newMults;
            }
        }


        Equation inflated;
        GroundedClause singleLeftExp = inflateEntireSide(constSimplify.lhsExps, leftSideMultipliers);

        GroundedClause singleRightExp = inflateEntireSide(constSimplify.rhsExps, rightSideMultipliers);
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
            inflated = new Equation(singleLeftExp, singleRightExp);
        }


        //
        // 0 = 0 should not be allowable.
        //
        if (inflated.getLHS() == null || inflated.getRHS() == null)
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
            unknown = inEq.lhsTermAt(0);
        }
        else
        {
            return inEq;
        }

        //
        // Divide both sides to simplify.
        //
        if (unknown != null)
        {
            NumericValue newValue = new NumericValue(value.getDoubleValue());

            //HALP
            return new FlatEquation(Utilities.makeList((GroundedClause)unknown), Utilities.makeList((GroundedClause)newValue));
        }

        // Nothing happened so return original
        return inEq;
    }

    //
    // Inflate a single term possibly creating a subtraction and / or multiplication node
    //
    private static GroundedClause inflateTerm(GroundedClause clause, NumericValue multiplier)
    {

        GroundedClause newClause = null;

        if (clause instanceof NumericValue)
        {
            return new NumericValue(((NumericValue) clause).getDoubleValue() * multiplier.getDoubleValue());
        }

        // This may not be necessary....
        // If the multiplier is non-unit (not 1), create a multiplication node

        if (Math.abs(multiplier.getDoubleValue()) != 1)
        {
            newClause = new Multiplication(new NumericValue(Math.abs(multiplier.getDoubleValue())), clause);
        }

        //If the multiplier is negative, convert to subtraction
        if (multiplier.getDoubleValue() < 0)
        {
            newClause = new Subtraction(new NumericValue(0), newClause == null ? clause : newClause);
        }


        // Reset multiplier accordingly

        return newClause == null ? clause : newClause;
    }
    //
    // Inflate am entire flattened side of an equation
    //
    private static GroundedClause inflateEntireSide(List<GroundedClause> side, ArrayList<GroundedClause> multipliers)
    {
        GroundedClause singleExp;

        if (side.size() <= 1)   
        {
            singleExp = inflateTerm(side.get(0), ((NumericValue)multipliers.get(0)));
        }
        else
        {
            singleExp = new Addition(inflateTerm(side.get(0), (NumericValue)multipliers.get(0)), inflateTerm(side.get(1), (NumericValue)multipliers.get(0)));
            for (int i = 2; i < side.size(); i++)
            {
                singleExp = new Addition(singleExp, inflateTerm(side.get(i), (NumericValue)multipliers.get(i)));
            }
        }

        return singleExp;
    }

    private static class EquationCollection
    {
        ArrayList<GroundedClause> leftMults, rightMults;
        FlatEquation result;

        EquationCollection(ArrayList<GroundedClause> lhs, ArrayList<GroundedClause> rhs, FlatEquation eq)
        {
            leftMults = new ArrayList<GroundedClause>(lhs);
            rightMults = new ArrayList<GroundedClause>(rhs);
            result = new FlatEquation(eq);
        }

        public ArrayList<GroundedClause> getLeft()
        {
            return leftMults;
        }

        public ArrayList<GroundedClause> getRight()
        {
            return rightMults;
        }

        public FlatEquation getEquation()
        {
            return result;
        }
    };

    private static EquationCollection combineLikeTerms(FlatEquation eq, ArrayList<GroundedClause> leftMult, ArrayList<GroundedClause> rightMult)
    {
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> left = combineSideLikeTerms(eq.lhsExps, leftMult);
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> right = combineSideLikeTerms(eq.rhsExps, rightMult);

        //Note: Ryan is a dumbass
        ArrayList<GroundedClause> leftSideMultipliers = left.getKey();
        ArrayList<GroundedClause> rightSideMultipliers = right.getKey();

        FlatEquation equation =  new FlatEquation(left.getValue(), right.getValue());

        return new Simplification.EquationCollection(leftSideMultipliers, rightSideMultipliers, equation);
    }

    private static Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> combineSideLikeTerms(List<GroundedClause> sideExps, ArrayList<GroundedClause> multiplierList)
    {

        if (sideExps.size() == 1)
        { 
            ArrayList<GroundedClause> originalList = Utilities.makeList((GroundedClause)(sideExps.get(0)));
            ArrayList<GroundedClause> originalMultipliers = Utilities.makeList((GroundedClause)(multiplierList.get(0)));
            return new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(originalMultipliers, originalList);
        }

        // The new simplified side of the equation
        ArrayList<GroundedClause> simp = new ArrayList<GroundedClause>();

        // To collect all constants
        ArrayList<NumericValue> constants = new ArrayList<NumericValue>();

        //The list that will store the multipliers
        ArrayList<GroundedClause> multipliers = new ArrayList<GroundedClause>();

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
                    constants.add(new NumericValue(((NumericValue)iExp).getDoubleValue() * ((NumericValue)multiplierList.get(i)).getDoubleValue()));
                }
                else
                {
                    // Collect all like terms on this side
                    List<GroundedClause> likeTerms = new ArrayList<GroundedClause>();

                    //Keep track of which terms are alike by using a boolean array.
                    //Each time an occurrence of that term occurs, mark that position in the boolean list as true

                    boolean[] checkedMultipliers = new boolean[sideExps.size()]; //Automatically initialized to false
                    for (int j = i + 1; j < sideExps.size(); j++)
                    {
                        // If same node, add to the list
                        if (sideExps.get(i).structurallyEquals(sideExps.get(j))) //HALP
                        {
                            likeTerms.add(sideExps.get(j));
                            checkedMultipliers[j] = true;
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

                    //Now all of the terms should have properly marked, we can loop through the array
                    //For each multiplier marked true, add or subtract the multipliers
                    NumericValue totalMultiplier = new NumericValue(0);
                    boolean likeTermFound = false;
                    for (int k = i; k < checkedMultipliers.length; k++)
                    {
                        if (checkedMultipliers[k])
                        {
                            likeTermFound = true;
                            totalMultiplier = new NumericValue(totalMultiplier.getDoubleValue() + ((NumericValue)(multiplierList.get(k))).getDoubleValue());
                        }
                    }

                    // If everything cancels, add a 0 to the equation
                    if (likeTermFound && totalMultiplier.getDoubleValue() == 0)
                    {
                        constants.add(new NumericValue(0));
                    }
                    else
                    {
                        simp.add(copyExp);
                        if (likeTermFound)
                            multipliers.add(totalMultiplier);
                        else
                            multipliers.add(new NumericValue(1));
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
            multipliers.add(new NumericValue(1));
        }
        //HALP
        //How do I return a FlatEquation and an equation list?  Remember, this is going back to the combineLikeTerms() method.
        return new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(multipliers, simp);
    }

    private static EquationCollection combineLikeTermsAcrossEqual(FlatEquation eq, ArrayList<GroundedClause> leftMult, ArrayList<GroundedClause> rightMult) throws CloneNotSupportedException
    {
        // The new simplified side of the equation
        List<GroundedClause> leftSimp = new ArrayList<GroundedClause>();
        List<GroundedClause> rightSimp = new ArrayList<GroundedClause>();

        ArrayList<GroundedClause> leftMults = new ArrayList<GroundedClause>();
        ArrayList<GroundedClause> rightMults = new ArrayList<GroundedClause>();

        // The resultant constant values on the left / right sides
        Pair<Double, Double> constantPair = handleConstants(eq, leftMult, rightMult);

        boolean[] rightCheckedExp = new boolean[eq.rhsExps.size()]; //Auto-init to false

        for (int i = 0; i < eq.lhsExps.size(); i++)
        {
            if (!(eq.lhsExps.get(i) instanceof NumericValue))
            {
                int rightExpIndex = backend.utilities.ast_helper.Utilities.StructuralIndex(eq.rhsExps, eq.lhsExps.get(i));

                //
                // Left expression has like term on the right?
                //
                // it doesn't have a like term
                if (rightExpIndex == -1)
                {
                    leftSimp.add(eq.lhsExps.get(i));
                    leftMults.add(leftMult.get(i));
                }
                //
                // Expression has like term on the right
                //
                else
                {
                    rightCheckedExp[rightExpIndex] = true;
                    GroundedClause rightExp = eq.rhsExps.get(rightExpIndex);
                    GroundedClause copyExp = eq.lhsExps.get(i).deepCopy();

                    // Seek to keep positive values for the resultant, simplified expression
                    if (((NumericValue)leftMult.get(i)).getDoubleValue() - ((NumericValue)rightMult.get(i)).getDoubleValue() > 0)
                    {
                        // copyExp.setMultiplier(eq.lhsExps.get(i).getMulitplier() - rightExp.getMulitplier());
                        leftSimp.add(copyExp);
                        leftMults.add(new NumericValue(((NumericValue)leftMult.get(i)).getDoubleValue() - ((NumericValue)rightMult.get(i)).getDoubleValue()));
                    }
                    else if (((NumericValue)rightMult.get(i)).getDoubleValue() - ((NumericValue)leftMult.get(i)).getDoubleValue() > 0)
                    {
                        // copyExp.setMultiplier(rightExp.getMulitplier() - eq.lhsExps.get(i).getMulitplier());
                        rightSimp.add(copyExp);
                        rightMults.add(new NumericValue(((NumericValue)rightMult.get(i)).getDoubleValue() - ((NumericValue)leftMult.get(i)).getDoubleValue()));
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
                rightMults.add(new NumericValue(((NumericValue)rightMult.get(i)).getDoubleValue()));
            }
        }

        //
        // Add back the constant to the correct side
        //
        if (constantPair.getKey() != 0)
        {
            leftSimp.add(new NumericValue(constantPair.getKey()));
            leftMults.add(new NumericValue(1));
        }
        if (constantPair.getValue() != 0) 
        {
            rightSimp.add(new NumericValue(constantPair.getValue()));
            rightMults.add(new NumericValue(1));
        }

        //
        // Now check coefficients: both sides all have coefficients that evenly divide the other side.
        //
        if (!leftSimp.isEmpty() && !rightSimp.isEmpty())
        {
            // Calculate the gcd
            int gcd = ((NumericValue)(leftMults.get(0))).getIntValue();
            for (int i = 1; i < leftSimp.size(); i++)
            {
                gcd = backend.utilities.math.MathUtilities.GCD(gcd, ((NumericValue)(leftMults.get(0))).getIntValue());
            }
            for (GroundedClause rightExp : rightSimp)
            {
                gcd = backend.utilities.math.MathUtilities.GCD(gcd, ((NumericValue)(rightMults.get(0))).getIntValue());
            }

            if (gcd != 1)
            {
                ArrayList<GroundedClause> newLeft = new ArrayList<GroundedClause>();
                ArrayList<GroundedClause> newRight = new ArrayList<GroundedClause>();

                for (GroundedClause leftExp : leftMults)
                {
                    newLeft.add(new NumericValue(((NumericValue)leftExp).getDoubleValue() / gcd));
                }
                for (GroundedClause rightExp : rightMults)
                {
                    newRight.add(new NumericValue(((NumericValue)rightExp).getDoubleValue() / gcd));
                }

                leftMults = newLeft;
                rightMults = newRight;
            }
        }
        // Check for extreme case in which one side has no elements; in this case, add a zero
        if (leftSimp.isEmpty()) 
        {
            leftSimp.add(new NumericValue(0));
            leftMults.add(new NumericValue(1));
        }
        if (rightSimp.isEmpty()) 
        {
            rightSimp.add(new NumericValue(0));
            rightMults.add(new NumericValue(1));
        }

        FlatEquation result = new FlatEquation(leftSimp, rightSimp);
        return new EquationCollection(leftMults, rightMults, result);
    }

    //
    // Returns a positive constant on the LHS or RHS as appropriate.
    //
    private static Pair<Double, Double> handleConstants(FlatEquation eq, ArrayList<GroundedClause> leftMult, ArrayList<GroundedClause> rightMult)
    {
        double lhs = collectConstants(eq.lhsExps, leftMult);
        double rhs = collectConstants(eq.rhsExps, rightMult);

        double simpLeft = lhs > rhs ? lhs - rhs : 0;
        double simpRight = rhs > lhs ? rhs - lhs : 0;

        return new Pair<Double, Double>(simpLeft, simpRight);
    }

    //
    // Sum the constants on one side of an equation
    //
    private static double collectConstants(List<GroundedClause> side, ArrayList<GroundedClause> multiplier)
    {
        double result = 0;
        for (int i = 0; i < side.size(); i++)
        {
            if (side.get(i) instanceof NumericValue)
            {
                result += ((NumericValue)side.get(i)).getDoubleValue() * ((NumericValue)multiplier.get(i)).getDoubleValue();
            }
        }
        return result;
    }
}
