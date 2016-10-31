package equations;

import java.util.List;
import javax.swing.text.Segment;
import backend.utilities.ast_helper.*;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.*;

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
        if (Utilities.CompareValues(sumL, 0) && Utilities.CompareValues(sumR, 0))
        {
            ExceptionHandler.throwException(new ArgumentException("Should not have an equation that is 0 = 0: " + this.toString()));
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
            else if (clause instanceof Segment)
            {
                sum += clause.getMulitplier() * ((Segment)clause).length();
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
