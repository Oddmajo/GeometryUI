package backend.symbolicAlgebra.equations;

import java.util.ArrayList;
import java.util.List;
import backend.ast.figure.components.*;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.symbolicAlgebra.ArithmeticNode;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;
import backend.ast.GroundedClause;
import com.google.common.collect.*;

public class Equation extends ArithmeticNode
{
    protected GroundedClause lhs;
    public GroundedClause getLHS() { return lhs; }

    protected GroundedClause rhs;
    public GroundedClause getRHS() { return rhs; }

    // Disallow empty object creation
    protected Equation()
    { 
        System.out.println("Empty equation creation.");
        ExceptionHandler.throwException(new IllegalArgumentException("Empty equation creation."));
    }

    public Equation(Equation eq) { this(eq.lhs, eq.rhs); }

    public Equation(GroundedClause left, GroundedClause right)
    {
        super();

        lhs = left;
        rhs = right;
    }

    public void substitute(GroundedClause toFind, GroundedClause toSub)
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
        boolean leftIs =  lhs instanceof Angle || lhs instanceof Segment ||  lhs instanceof Arc ||  lhs instanceof NumericValue;
        boolean rightIs =  rhs instanceof Angle || rhs instanceof Segment ||  rhs instanceof Arc  ||  rhs instanceof NumericValue;

        if (leftIs && rightIs) return BOTH_ATOMIC;
        if (!leftIs && !rightIs) return NONE_ATOMIC;
        if (leftIs) return LEFT_ATOMIC;
        if (rightIs) return RIGHT_ATOMIC; 

        return NONE_ATOMIC;
    }


    // Collect all the terms and return a size() for both sides <left, right>
    public Pair<Integer, Integer> getCardinalities()
    {
        List<GroundedClause> left = lhs.collectTerms().getValue();
        List<GroundedClause> right = rhs.collectTerms().getValue();
        return new Pair<Integer, Integer>(left.size(), right.size());
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
        List<GroundedClause> thisLHS = lhs.collectTerms().getValue();
        List<GroundedClause> thisRHS = rhs.collectTerms().getValue();

        List<GroundedClause> thatLHS = thatEquation.lhs.collectTerms().getValue();
        List<GroundedClause> thatRHS = thatEquation.rhs.collectTerms().getValue();

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

    //
    //
    //
    //
    // A template is dictated solely by the multipliers of the equation.
    //     For example, 2AM = AB        has template  {  <2>   ;   <1>  }
    //                  x + y = z + w   has template  { <1, 1> ; <1, 1> }
    //
    // Both the left hand side and right hand side are considered to be sets; thus use set-based processing.
    //
    //
    public EquationTemplate getTemplate()
    {
        ArrayList<Integer> left = new ArrayList<Integer>();
        ArrayList<Integer> right = new ArrayList<Integer>();

        // Construct the template by visiting the LHS and RHS
        /*HALP
        lhs.collectTerms().getValue().forEach((term) -> left.add(term.getMulitplier()));
        rhs.collectTerms().getValue().forEach((term) -> right.add(term.getMulitplier()));
         */
        return new EquationTemplate(left, right);
    }

    //
    // A template is based on two lists corresponding to LHS and RHS
    // We do not store lists, instead, for simplicity and speed we store an integer
    //
    public class EquationTemplate
    {
        private Multiset<Integer> _lhs;
        private Multiset<Integer> _rhs;

        // Disallow empty creation
        private EquationTemplate() { }

        public EquationTemplate(List<Integer> lhs, List<Integer> rhs)
        {
            _lhs = HashMultiset.create(lhs);
            _rhs = HashMultiset.create(rhs);
        }

        public boolean satisfies(Equation that)
        {
            return this.equals(that.getTemplate());
        }

        // Set based processing for equality
        public boolean equals(Object o)
        {
            if (o == null) return false;

            if (!(o instanceof EquationTemplate)) return false;

            EquationTemplate that = (EquationTemplate)o;

            return equals(that);
        }

        // Set based processing for equality
        public boolean equals(EquationTemplate that)
        {
            // Multi-set equality check
            return (this._lhs.equals(that._lhs) && this._rhs.equals(that._rhs)) ||
                    (this._lhs.equals(that._rhs) && this._rhs.equals(that._lhs));
        }
    }
}
