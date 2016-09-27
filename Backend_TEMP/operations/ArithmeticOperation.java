package operations;

import java.util.List;

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

    public List<GroundedClause> collectTerms()
    {
        List<GroundedClause> list = new List<GroundedClause>();

        list.addRange(leftExp.CollectTerms());  //What is this in Java?

        for(GroundedClause gc : rightExp.CollectTerms())
        {
            GroundedClause copyGC = gc.DeepCopy();

            list.Add(copyGC);
        }

        return list;
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
    public GroundedClause deepCopy()
    {
        ArithmeticOperation other = (ArithmeticOperation)(this.memberwiseClone());
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
