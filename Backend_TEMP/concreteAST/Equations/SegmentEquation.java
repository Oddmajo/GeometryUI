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
        double sumL = SumSide(left.CollectTerms());
        double sumR = SumSide(right.CollectTerms());

        //if (!Utilities.CompareValues(sumL, sumR))
        //{
        //    throw new ArgumentException("Segment equation is inaccurate; sums differ: " + l + " = " + r);
        //}
        if (Utilities.CompareValues(sumL, 0) && Utilities.CompareValues(sumR, 0))
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
                sum += ((NumericValue)clause).DoubleValue;
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

    public int GetHashCode()
    {
        return super.GetHashCode(); 
    }
    
    public boolean Equals(Object obj)
    {
        SegmentEquation eq = (SegmentEquation) obj;
        if (eq == null)
        {
            return false;
        }
        
        return super.Equals(obj);
    }

}
