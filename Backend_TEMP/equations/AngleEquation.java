package equations;

import java.util.List;

public class AngleEquation extends Equation
{
    public AngleEquation()
    {
        super();
    }

    public AngleEquation(GroundedClause left, GroundedClause right)
    {
        double sumL = SumSide(left.collectTerms());
        double sumR = SumSide(right.collectTerms());

        if (!utilities.CompareValues(sumL, sumR))
        {
            throw new ArgumentException("Angle equation is inaccurate; sums differ " + left + " " + right);
        }
    }

    private double SumSide(List<GroundedClause> side)
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

    public int getHashCode()
    {
        return super.getHashCode();
    }

    //
    // Equals checks that for both sides of this equation is the same as one entire side of the other equation
    //


    public boolean equals(Object obj)
    {

        if (obj == null || (AngleEquation)obj == null)
        {
            return false;
        }

        AngleEquation thatEquation = (AngleEquation) obj;
        //
        // Collect all basic terms on the left and right hand sides of both equations.
        //

        List<GroundedClause> thisLHS = lhs.collectTerms();
        List<GroundedClause> thisRHS = rhs.collectTerms();

        List<GroundedClause> thatLHS = thatEquation.lhs.collectTerms();
        List<GroundedClause> thatRHS = thatEquation.rhs.collectTerms();

        //Check side length counts as a first step
        if (!((thisLHS.count == thatLHS.count && thisRHS.count == thatRHS.count)) ||
                (thisLHS.count == thatRHS.count && thisLHS.count == thatLHS.count))
        {
            return false;
        }

        // Seek one side equal to one side and then the other equals the other.
        // Cannot do this easily with a union / set intersection set theoretic approach since an equation may have multiple instances of a value
        // In theory, since we always deal with simplified equations, there should not be multiple instances of a particular value.
        // So, this should work.

        // Note operations like multiplication and subtraction have been taken into account.
        List<GroundedClause> unionLHS = new List<GroundedClause>(thisLHS);
        utilities.addUniqueList(unionLHS, thatLHS);

        List<GroundedClause> unionRHS = new List<GroundedClause>(thisRHS);
        utilities.addUniqueList(unionRHS, thatRHS);

        // Exact same sides means the union is the same as each list itself
        if (unionLHS.count == thisLHS.count && unionRHS.count == thisRHS.count)
        {
            return true;
        }

        // Check the other combination of sides
        unionLHS = new List<GroundedClause>(thisLHS);
        utilities.AddUniqueList(unionLHS, thatRHS);

        if (unionLHS.count != thisLHS.count)
        {
            return false;
        }

        unionRHS = new List<GroundedClause>(thisRHS);
        utilities.AddUniqueList(unionRHS, thatLHS);

        //Exact same sides means the union is the same as each list itself
        return unionRHS.count == thisRHS.count;
    }
}
