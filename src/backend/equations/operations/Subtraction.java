package backend.equations.operations;

import java.util.ArrayList;
import backend.equations.*;
import backend.utilities.exception.ExceptionHandler;

@SuppressWarnings("unused")
public class Subtraction extends ArithmeticOperation
{
    public Subtraction() 
    {
        super();
    }


    public Subtraction(GroundedClause left, GroundedClause right) {
        super(left, right);
    }


    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll((leftExp.collectTerms()));
        list.addAll((rightExp.collectTerms()));
        /*
        for (GroundedClause gc : rightExp.collectTerms())
        {
            GroundedClause copyGC = null;
            try
            {
                copyGC = gc.deepCopy();
            }
            catch (CloneNotSupportedException e)
            {
                // TODO Auto-generated catch block
                ExceptionHandler.throwException(e);
            }

            copyGC.setMultiplier(copyGC.getMulitplier() * -1);
            list.add(copyGC);
        }
         */
        return list;
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

        if (obj == null || (Subtraction)obj == null) return false;
        return super.equals((Subtraction)obj);
    }

    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }

    public boolean containsClause(GroundedClause c)
    {
        if (leftExp.containsClause(c) || rightExp.containsClause(c))
            return true;
        return false;
    }
}
