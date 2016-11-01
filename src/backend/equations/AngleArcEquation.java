package backend.equations;

import backend.ast.figure.components.*;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;
import backend.utilities.list.*;
import java.util.ArrayList;
import java.util.List;
import backend.ast.GroundedClause;

@SuppressWarnings("unused")
public class AngleArcEquation extends Equation
{
    
    public AngleArcEquation()
    {
        super();
    }
    
    public AngleArcEquation(GroundedClause left, GroundedClause right) throws ArgumentException
    {
        double sumL = SumSide(left.collectTerms());
        double sumR = SumSide(right.collectTerms());

        if (!Utilities.CompareValues(sumL, sumR))
        {
            throw new ArgumentException("Angle-Arc equation is inaccurate; sums differ: " + left + " " + right);
        }
    }
    
    public double SumSide(List<GroundedClause> side)
    {
        double sum = 0;
        for (GroundedClause clause : side)
        {
            if (clause instanceof NumericValue)
            {
                sum += ((NumericValue)clause).getDoubleValue();
            }
            /*
            else if (clause instanceof Angle)
            {
                sum += clause.getMulitplier() * ((Angle)clause).measure();
            }

            else if (clause instanceof MinorArc)
            {
                sum += clause.getMulitplier() * ((MinorArc)clause).GetMinorArcMeasureDegrees();
            }

            else if (clause instanceof MajorArc)
            {
                sum += clause.getMulitplier() * ((MajorArc)clause).GetMajorArcMeasureDegrees();
            }

            else if (clause instanceof Semicircle)
            {
                sum += clause.getMulitplier() * ((MajorArc)clause).GetMinorArcMeasureDegrees();
            }
            */
        }
        return sum;
    }
     
    public int getHashCode() { return super.getHashCode(); }

    public boolean equals(Object obj)
    {
        if (obj == null || (AngleArcEquation)obj == null) { return false;  }
        AngleArcEquation thatEquation = (AngleArcEquation) obj;

        //
        // Collect all basic terms on the left and right hand sides of both equations.
        //
        List<GroundedClause> thisLHS = lhs.collectTerms();
        List<GroundedClause> thisRHS = rhs.collectTerms();

        List<GroundedClause> thatLHS = thatEquation.lhs.collectTerms();
        List<GroundedClause> thatRHS = thatEquation.rhs.collectTerms();

        // Check side length counts as a first step.
        if (!((thisLHS.size() == thatLHS.size() && thisRHS.size() == thatRHS.size()) ||
                (thisLHS.size() == thatRHS.size() && thisLHS.size() == thatLHS.size()))) return false;

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
    
}