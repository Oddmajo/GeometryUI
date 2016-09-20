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
        double sumL = SumSide(left.CollectTerms());
        double sumR = SumSide(right.CollectTerms());

        //if (!Utilities.CompareValues(sumL, sumR))
        //{
        //    throw new ArgumentException("Segment equation is inaccurate; sums differ: " + l + " = " + r);
        //}

        if (Utilities.CompareValues(sumL, 0) && Utilities.CompareValues(sumR, 0))
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

    public int GetHashCode()
    {
        return super.GetHashCode();
    }

    public boolean Equals(Object obj)
    {
        ArcEquation eq = (ArcEquation) obj;
        if (eq == null)
        {
            return false;
        }
        return super.equals(obj);
    }
}
