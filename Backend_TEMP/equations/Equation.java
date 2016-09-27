package equations;

import java.util.List;

public class Equation extends ArithmeticNode
{
    public GroundedClause lhs;
    public GroundedClause rhs;
    public String justification;
    
    public Equation()
    {
        super();
    }

    public Equation(GroundedClause left, GroundedClause right)
    {
        lhs = left;
        rhs = right;
    }

    //public Equation(GroundedClause l, GroundedClause r, string just) : base()
    //{
    //    lhs = l;
    //    rhs = r;
    //    justification = just;
    //}

    public void Substitute(GroundedClause toFind, GroundedClause toSub)
    {
        if (lhs.equals(toFind))
        {
            lhs.toSub.deepCopy();
        }
        else
        {
            lhs.substitute(toFind, toSub);
        }

        if (rhs.equals(toFind))
        {
            rhs.toSub.deepCopy();
        }
        else
        {
            rhs.substitute(toFind, toSub);
        }
    }

    public boolean containsClause(GroundedClause target)
    {
        // If a composite node, check accordingly; this will return false if they are atomic
        return lhs.containsClause(target) || rhs.containsClause(target);
    }

    //
    // Determines if the equation has one side being atomic; no compound expressions
    // returns -1 (left is atomic), 0 (neither atomic), 1 (right is atomic)
    // both atomic: 2
    //

    public final int LEFT_ATOMIC = -1;
    public final int NONE_ATOMIC = 0;
    public final int RIGHT_ATOMIC = 1;
    public final int BOTH_ATOMIC = 2;

    public int GetAtomicity()
    {
        boolean leftIs = lhs.getClass().isInstance(new Angle()) || lhs.getClass().isInstance(new Segment()) || lhs.getClass().isInstance(new Arc()) || lhs.getClass().isInstance(new NumericValue();
        boolean rightIs = rhs.getClass().isInstance(new Angle()) || rhs.getClass().isInstance(new Segment()) || rhs.getClass().isInstance(new Arc()) || rhs.getClass().isInstance(new NumericValue();;

        if (leftIs && rightIs) return BOTH_ATOMIC;
        if (!leftIs && !rightIs) return NONE_ATOMIC;
        if (leftIs) return LEFT_ATOMIC;
        if (rightIs) return RIGHT_ATOMIC;

        return NONE_ATOMIC;
    }

    // Collect all the terms and return a count for both sides <left, right>
    public KeyValuePair<int, int> getCardinalities()
    {
        List<GroundedClause> left = lhs.collectTerms();
        List<GroundedClause> right = rhs.collectTerms();
        return new KeyValuePair<int, int>(left.count, right.count);
    }

    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!!
        return super.getHashCode();
    }

    public boolean structurallyEquals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        return equals(obj);
    }

    //
    // Equals checks that for both sides of this equation is the same as one entire side of the other equation
    //
    public boolean equals(Object obj)
    {

        if (obj == null || (Equation)obj == null)
        {
            return false;
        }
        Equation thatEquation = (Equation) obj;
        //
        // Collect all basic terms on the left and right hand sides of both equations.
        //
        List<GroundedClause> thisLHS = lhs.collectTerms();
        List<GroundedClause> thisRHS = rhs.collectTerms();

        List<GroundedClause> thatLHS = thatEquation.lhs.collectTerms();
        List<GroundedClause> thatRHS = thatEquation.rhs.collectTerms();

        // Check side length counts as a first step.
        if (!(thisLHS.count == thatLHS.count && thisRHS.count == thatRHS.count) && !(thisLHS.count == thatRHS.count && thisRHS.count == thatLHS.count)) return false;

        // Seek one side equal to one side and then the other equals the other.
        // Cannot do this easily with a union / set interection set theoretic approach since an equation may have multiple instances of a value
        // In theory, since we always deal with simplified equations, there should not be multiple instances of a particular value.
        // So, this should work.

        // Note operations like multiplication and substraction have been taken into account.
        List<GroundedClause> unionLHS = new List<GroundedClause>(thisLHS);
        utilities.addUniqueList(unionLHS, thatLHS);

        List<GroundedClause> unionRHS = new List<GroundedClause>(thisRHS);
        utilities.addUniqueList(unionRHS, thatRHS);

        // Exact same sides means the union is the same as each list itself
        if (unionLHS.count == thisLHS.count && unionRHS.count == thisRHS.count) return true;

        // Check the other combination of sides
        unionLHS = new List<GroundedClause>(thisLHS);
        utilities.addUniqueList(unionLHS, thatRHS);

        if (unionLHS.count != thisLHS.count) return false;

        unionRHS = new List<GroundedClause>(thisRHS);
        utilities.addUniqueList(unionRHS, thatLHS);

        // Exact same sides means the union is the same as each list itself
        return unionRHS.count == thisRHS.count;


    }
}
