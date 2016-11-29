package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.NumericValue;

public class Multiplication extends ArithmeticOperation
{
    public Multiplication()
    {
        super();
    }

    public Multiplication(GroundedClause left, GroundedClause right)
    {
        super(left, right);       
    }

    public String toString()
    {
        return "(" + leftExp.toString() + " * " + rightExp.toString() + ")";
    }

    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " * " + rightExp.toPrettyString();
    }

    // Make a deep copy of this object
    @Override
    public GroundedClause deepCopy()
    {
        return new Multiplication(leftExp.deepCopy(), rightExp.deepCopy());
    }
    
    //
    // In an attempt to avoid issues, all terms collected are copies of the originals.
    //
    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        //
        // Two constants are multiplied
        //
        if (leftExp instanceof NumericValue && rightExp instanceof NumericValue)
        {
            NumericValue left = (NumericValue)leftExp;
            NumericValue right = (NumericValue)rightExp;
            
            list.add(new NumericValue(left.getDoubleValue() * right.getDoubleValue()));
            
            return list;
        }

        //
        // A constant (on the left) multiplied by another expression
        //
        if (leftExp instanceof NumericValue)
        {
            NumericValue left = (NumericValue)leftExp;
            
            for (GroundedClause gc : rightExp.collectTerms())
            {
                GroundedClause copyGC = gc.deepCopy();

                copyGC.setMultiplier(copyGC.getMulitplier() * left.getIntValue());

                list.add(copyGC);
            }
        }

        //
        // A constant (on the left) multiplied by another expression
        //
        if (rightExp instanceof NumericValue)
        {
            NumericValue right = (NumericValue)rightExp;

            for (GroundedClause gc : leftExp.collectTerms())
            {
                GroundedClause copyGC = gc.deepCopy();

                copyGC.setMultiplier(copyGC.getMulitplier() * right.getIntValue());

                list.add(copyGC);
            }
        }

        return list;
    }

    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Multiplication)) return false;

        return super.equals(obj);
    }    
}

