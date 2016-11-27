package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.utilities.exception.ExceptionHandler;

public class Subtraction extends ArithmeticOperation
{
    protected Subtraction() { super(); }

    public Subtraction(GroundedClause left, GroundedClause right)
    {
        super(left, right);
    }

    @Override
    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms());

        for (GroundedClause gc : rightExp.collectTerms())
        {
            GroundedClause copyGC = gc.deepCopy();

            copyGC.setMultiplier(-1 * copyGC.getMulitplier());

            list.add(copyGC);
        }

        return list;
    }

    // Make a deep copy of this object
    @Override
    public GroundedClause deepCopy()
    {
        return new Subtraction(leftExp.deepCopy(), rightExp.deepCopy());
    }
    
    public String toString()
    {
        return "(" + leftExp.toString() + " - " + rightExp.toString() + ")";
    }

    public String toPrettyString()
    {
        return leftExp.toPrettyString() + " - " + rightExp.toPrettyString();
    }

    public boolean equals(Object obj)
    {
        if (obj == null) return false;        
        if (!(obj instanceof Subtraction)) return false;

        Subtraction that = (Subtraction)obj;
        
        return (this.leftExp.equals(that.leftExp) && this.rightExp.equals(that.rightExp));
    }
}
