package backend.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import java.util.List;
import backend.equations.*;
import backend.utilities.exception.ExceptionHandler;

@SuppressWarnings("unused")
public class ArithmeticOperation extends ArithmeticNode
{
    protected GroundedClause leftExp;
    protected GroundedClause rightExp;

    public ArithmeticOperation() { 
        super();
    }

    public ArithmeticOperation(GroundedClause left, GroundedClause right)
    {
        leftExp = left;
        rightExp = right;
    }
   

    public GroundedClause getLeftExp()
    {
        return leftExp;
    }
    
    public GroundedClause getRightExp()
    {
        return rightExp;
    }
    
    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms());
        list.addAll(rightExp.collectTerms());

        /*     
        GroundedClause copyGC = null;
        for(GroundedClause gc : rightExp.collectTerms())
        {
            try
            {
                copyGC = gc.deepCopy();
            }
            catch (CloneNotSupportedException e)
            {
                // TODO Auto-generated catch block
                ExceptionHandler.throwException(e);
            }

            list.add(copyGC);
        }
*/
        return list;
    }

    public boolean containsClause(GroundedClause newG)
    {
        return leftExp.containsClause(newG) || rightExp.containsClause(newG); 
    }

    public void substitute(GroundedClause toFind, GroundedClause toSub)
    {
        if (leftExp.containsClause(toFind))
        {
            leftExp = toSub;
        }
        else
        {
            leftExp.substitute(toFind, toSub);
        }

        if (rightExp.containsClause(toFind))
        {
            rightExp = toSub;
        }
        else
        {
            rightExp.substitute(toFind, toSub);
        }
    }

    // Make a deep copy of this object
    public GroundedClause deepCopy()
    {
        ArithmeticOperation other = (ArithmeticOperation)(this.deepCopy());
        other.leftExp = leftExp.deepCopy();
        other.rightExp = rightExp.deepCopy();

        return other;
    }

    public String toString()
    {
        return "(" + leftExp.toString() + " + " + rightExp.toString() + ")";
    }
    

    public boolean equals(Object obj)
    {
        if (obj == null || (ArithmeticOperation)obj == null) return false;
        ArithmeticOperation ao = (ArithmeticOperation) obj;
        return leftExp.equals(ao.leftExp) && rightExp.equals(ao.rightExp) ||
                leftExp.equals(ao.rightExp) && rightExp.equals(ao.leftExp) && super.equals(obj);
    }

    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }
    
    public void simplify()
    {
        
    }
}
