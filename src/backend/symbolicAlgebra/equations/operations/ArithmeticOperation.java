package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.ArithmeticNode;

public class ArithmeticOperation extends ArithmeticNode
{
    protected GroundedClause leftExp;
    public GroundedClause getLeftExp() { return leftExp; }
    
    protected GroundedClause rightExp;
    public GroundedClause getRightExp() { return rightExp; }

    
    public ArithmeticOperation() { super(); }

    public ArithmeticOperation(GroundedClause left, GroundedClause right)
    {
        leftExp = left;
        rightExp = right;
    }
    
    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms());
        list.addAll(rightExp.collectTerms());

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

    public String toString()
    {
        return "(" + leftExp.toString() + " + " + rightExp.toString() + ")";
    }
    

    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof ArithmeticOperation)) return false;

        ArithmeticOperation ao = (ArithmeticOperation) obj;
        
        return leftExp.equals(ao.leftExp) && rightExp.equals(ao.rightExp) ||
               leftExp.equals(ao.rightExp) && rightExp.equals(ao.leftExp) && super.equals(obj);
    }
}
