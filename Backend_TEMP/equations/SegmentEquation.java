package equations;

import java.util.List;

public class SegmentEquation extends Equation
{
    public SegmentEquation()
    {
        super();
    }

    public SegmentEquation(GroundedClause left, GroundedClause right)
    {
        double sumL = SumSide(left.collectTerms());
        double sumR = SumSide(right.collectTerms());

        //if (!Utilities.CompareValues(sumL, sumR))
        //{
        //    throw new ArgumentException("Segment equation is inaccurate; sums differ: " + l + " = " + r);
        //}
        if (utilities.compareValues(sumL, 0) && utilities.compareValues(sumR, 0))
        {
            throw new ArgumentException("Should not have an equation that is 0 = 0: " + this.ToString());
        }
    }

    public double SumSide(List<GroundedClause> side)
    {
        double sum = 0;
        for (GroundedClause clause : side)
        {
            if (clause.getClass().isInstance(new NumericValue()))
            {
                sum += ((NumericValue)clause).getDoubleValue();
            }
            else if (clause.getClass().isInstance(new Segment()))
            {
                sum += clause.multiplier * ((Segment)clause.length);
            }
        }
        return sum;
    }

    //public SegmentEquation(GroundedClause l, GroundedClause r, string just) : base(l, r, just)
    //{
    //    double sumL = SumSide(l.CollectTerms());
    //    double sumR = SumSide(r.CollectTerms());

    //    if (!Utilities.CompareValues(sumL, sumR))
    //    {
    //        throw new ArgumentException("Segment equation is inaccurate; sums differ: " + l + " " + r);
    //    }
    //    if (sumL == 0 && sumR == 0)
    //    {
    //        throw new ArgumentException("Should not have an equation that is 0 = 0: " + this.ToString());
    //    }
    //}

    public int getHashCode()
    {
        return super.getHashCode(); 
    }
    
    public boolean equals(Object obj)
    {
        if (obj == null || (SegmentEquation) obj == null)
        {
            return false;
        }
        
        return super.equals(obj);
    }

}
