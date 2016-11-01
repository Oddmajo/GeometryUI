package backend.equations;

import java.util.ArrayList;
import java.util.List;
import backend.ast.figure.*;
import backend.ast.figure.components.*;
import backend.utilities.Pair;

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

    public void Substitute(GroundedClause toFind, GroundedClause toSub) throws CloneNotSupportedException
    {
        if (lhs.equals(toFind))
        {
            lhs = toSub.deepCopy();
        }
        else
        {
            lhs.substitute(toFind, toSub);
        }

        if (rhs.equals(toFind))
        {
            rhs = toSub.deepCopy();
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

    public final static int LEFT_ATOMIC = -1;
    public final static int NONE_ATOMIC = 0;
    public final static int RIGHT_ATOMIC = 1;
    public final static int BOTH_ATOMIC = 2;

    
    public int getAtomicity()
    {
        boolean leftIs = lhs.getClass().isInstance(new Angle()) || lhs.getClass().isInstance(new Segment()) || /* lhs.getClass().isInstance(new Arc()) || */ lhs instanceof NumericValue;
        boolean rightIs = rhs.getClass().isInstance(new Angle()) || rhs.getClass().isInstance(new Segment()) || /* rhs.getClass().isInstance(new Arc()))  || */rhs instanceof NumericValue;

        if (leftIs && rightIs) return BOTH_ATOMIC;
        if (!leftIs && !rightIs) return NONE_ATOMIC;
        if (leftIs) return LEFT_ATOMIC;
        if (rightIs) return RIGHT_ATOMIC; 

        return NONE_ATOMIC;
    }
    

    // Collect all the terms and return a size() for both sides <left, right>
    public Pair<Integer, Integer> getCardinalities()
    {
        List<GroundedClause> left = lhs.collectTerms();
        List<GroundedClause> right = rhs.collectTerms();
        return new Pair<Integer, Integer>(left.size(), right.size());
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
        if (!(thisLHS.size() == thatLHS.size() && thisRHS.size() == thatRHS.size()) && !(thisLHS.size() == thatRHS.size() && thisRHS.size() == thatLHS.size())) return false;

        // Seek one side equal to one side and then the other equals the other.
        // Cannot do this easily with a union / set intersection set theoretic approach since an equation may have multiple instances of a value
        // In theory, since we always deal with simplified equations, there should not be multiple instances of a particular value.
        // So, this should work.

        // Note operations like multiplication and subtraction have been taken into account.
        List<GroundedClause> unionLHS = new ArrayList<GroundedClause>(thisLHS);
        backend.utilities.list.Utilities.AddUniqueList(unionLHS, thatLHS);

        List<GroundedClause> unionRHS = new ArrayList<GroundedClause>(thisRHS);
        backend.utilities.list.Utilities.AddUniqueList(unionRHS, thatRHS);

        // Exact same sides means the union is the same as each list itself
        if (unionLHS.size() == thisLHS.size() && unionRHS.size() == thisRHS.size()) return true;

        // Check the other combination of sides
        unionLHS = new ArrayList<GroundedClause>(thisLHS);
        backend.utilities.list.Utilities.AddUniqueList(unionLHS, thatRHS);

        if (unionLHS.size() != thisLHS.size()) return false;

        unionRHS = new ArrayList<GroundedClause>(thisRHS);
        backend.utilities.list.Utilities.AddUniqueList(unionRHS, thatLHS);

        // Exact same sides means the union is the same as each list itself
        return unionRHS.size() == thisRHS.size();


    }
    
    public String toString()
    {
        return "(" +  lhs.toString() + " = " + rhs.toString() + ")";
    }
    
    public String toPrettyString()
    {
        return lhs.toPrettyString() + " = " + rhs.toPrettyString();
    }
}
