package equations;

import java.util.ArrayList;

public class AngleEquation extends Equation
{
    public AngleEquation()
    {
        super();
    }

    public AngleEquation(GroundedClause left, GroundedClause right)
    {
        double sumL = SumSide(left.CollectTerms());
        double sumR = SumSide(right.CollectTerms());

        if (!Utilities.CompareValues(sumL, sumR))
        {
            throw new ArgumentException("Angle equation is inaccurate; sums differ " + left + " " + right);
        }
    }

    private double SumSide(ArrayList<GroundedClause> side)
    {
        double sum = 0;
        for (GroundedClause clause: side)
        {
            if (clause.getClass().isInstance(new NumericValue()))
            {
                sum += (((NumericValue) clause).DoubleValue());
            }

            else if (clause.getClass().isInstance(new Angle()))
            {
                sum += clause.multiplier() * ((Angle) clause.measure());
            }

            else if (clause.getClass().isInstance(new MinorArc()))
            {
                sum += clause.multiplier() * ((MinorArc) clause.GetMinorArcMeasureDegrees());
            }

            else if (clause.getClass().isInstance(new MajorArc()))
            {
                sum += clause.multiplier() * ((MajorArc) clause.GetMajorArcMeasureDegrees());
            }
        }

        return sum;
    }

    //public AngleEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just)
    //{
    //    double sumL = SumSide(l.CollectTerms());
    //    double sumR = SumSide(r.CollectTerms());

    //    if (!Utilities.CompareValues(sumL, sumR))
    //    {
    //        throw new ArgumentException("Angle equation is inaccurate; sums differ: " + l + " " + r);
    //    }

    //    //if (sumL == 0 && sumR == 0)
    //    //{
    //    //    throw new ArgumentException("Should not have an equation that is 0 = 0: " + this.ToString());
    //    //}
    //}

    public int GetHashCode()
    {
        return super.getHashCode();
    }

    //
    // Equals checks that for both sides of this equation is the same as one entire side of the other equation
    //


    public boolean Equals(Object target)
    {
        AngleEquation thatEquation = (AngleEquation) target;

        if (thatEquation == null)
        {
            return false;
        }

        //
        // Collect all basic terms on the left and right hand sides of both equations.
        //

        List<GroundedClause> thisLHS = lhs.CollectTerms();
        List<GroundedClause> thisRHS = rhs.CollectTerms();

        List<GroundedClause> thatLHS = thatEquation.lhs.CollectTerms();
        List<GroundedClause> thatRHS = thatEquation.rhs.CollectTerms();

        //Check side length counts as a first step
        if (!((thisLHS.Count == thatLHS.Count && thisRHS.Count == thatRHS.Count)) ||
                (thisLHS.Count == thatRHS.Count && thisLHS.Count == thatLHS.Count))
        {
            return false;
        }

        // Seek one side equal to one side and then the other equals the other.
        // Cannot do this easily with a union / set intersection set theoretic approach since an equation may have multiple instances of a value
        // In theory, since we always deal with simplified equations, there should not be multiple instances of a particular value.
        // So, this should work.

        // Note operations like multiplication and subtraction have been taken into account.
        List<GroundedClause> unionLHS = new List<GroundedClause>(thisLHS);
        Utilities.AddUniqueList(unionLHS, thatLHS);

        List<GroundedClause> unionRHS = new List<GroundedClause>(thisRHS);
        Utilities.AddUniqueList(unionRHS, thatRHS);

        // Exact same sides means the union is the same as each list itself
        if (unionLHS.Count == thisLHS.Count && unionRHS.Count == thisRHS.Count)
        {
            return true;
        }

        // Check the other combination of sides
        unionLHS = new List<GroundedClause>(thisLHS);
        Utilities.AddUniqueList(unionLHS, thatRHS);

        if (unionLHS.Count != thisLHS.Count)
        {
            return false;
        }

        unionRHS = new List<GroundedClause>(thisRHS);
        Utilities.AddUniqueList(unionRHS, thatLHS);

        //Exact same sides means the union is the same as each list itself
        return unionRHS.Count == thisRHS.Count;
    }
}
