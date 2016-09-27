package equations;

import java.util.List;

public class ArcEquation extends Equation
{
    public ArcEquation()
    {
        super();
    }

    public ArcEquation(GroundedClause left, GroundedClause right)
    {
        double sumL = SumSide(left.collectTerms());
        double sumR = SumSide(right.collectTerms());

        //if (!Utilities.CompareValues(sumL, sumR))
        //{
        //    throw new ArgumentException("Segment equation is inaccurate; sums differ: " + l + " = " + r);
        //}

        if (utilities.compareValues(sumL, 0) && utilities.compareValues(sumR, 0))
        {
            throw new ArgumentException("Should not have an equation that is 0 = 0: " + this.toString());
        }
    }

    private double SumSide(List<GroundedClause> side)
    {
        double sum = 0;
        for (GroundedClause clause : side)
        {
            if (clause.getClass().isInstance(new NumericValue()))
            {
                sum += ((NumericValue)clause).DoubleValue();
            }
            else if (clause.getClass().isInstance(new Arc()))
            {
                sum += clause.multiplier * ((Arc)clause).length();
            }
        }
        return sum;
    }

    public int getHashCode()
    {
        return super.getHashCode();
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        return super.equals(obj);
    }
}
