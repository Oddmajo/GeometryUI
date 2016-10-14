package equations.operations;

import java.util.ArrayList;
import java.util.List;
import equations.*;

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

    public ArrayList<GroundedClause> collectTerms()
    {
        List<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms());  //What is this in Java?

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
                e.printStackTrace();
            }

            list.add(copyGC);
        }

        return (ArrayList<GroundedClause>) list;
    }

    public boolean containsClause(GroundedClause newG)
    {
        return leftExp.containsClause(newG) || rightExp.containsClause(newG); 
    }

    public void substitute(GroundedClause toFind, GroundedClause toSub)
    {
        if (leftExp.equals(toFind))
        {
            leftExp = toSub;
        }
        else
        {
            leftExp.substitute(toFind, toSub);
        }

        if (rightExp.equals(toFind))
        {
            rightExp = toSub;
        }
        else
        {
            rightExp.substitute(toFind, toSub);
        }
    }

    // Make a deep copy of this object
    public GroundedClause deepCopy() throws CloneNotSupportedException
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
}
