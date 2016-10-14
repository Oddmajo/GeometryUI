package equations;

import java.util.List;
import ast.figure.components.*;
import utilities.exception.*;
import utilities.ast_helper.*;

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

        if (Utilities.CompareValues(sumL, 0) && Utilities.CompareValues(sumR, 0))
        {
            ExceptionHandler.throwException(new ArgumentException("Should not have an equation that is 0 = 0: " + this.toString()));
        }
    }

    private double SumSide(List<GroundedClause> side)
    {
        double sum = 0;
        for (GroundedClause clause : side)
        {
            if (clause instanceof NumericValue)
            {
                sum += ((NumericValue)clause).getDoubleValue();
            }
            else if (clause instanceof Arc)
            {
                sum += clause.getMulitplier() * ((Arc)clause).getLength();
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
