package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.ArithmeticNode;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;

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

    public Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();
        ArrayList<GroundedClause> multipliers = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms().getKey());
        list.addAll(rightExp.collectTerms().getKey());

        GroundedClause copyGC = null;
        for(GroundedClause gc : rightExp.collectTerms().getKey())
        {
            copyGC = gc.deepCopy();
            list.add(copyGC);
            multipliers.add(new NumericValue(1));
        }
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> pair = new Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>>(list, multipliers);
        return pair;
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
        if (obj == null) return false;
        if (obj instanceof ArithmeticOperation)
        {
            ArithmeticOperation ao = (ArithmeticOperation) obj;
            return leftExp.equals(ao.leftExp) && rightExp.equals(ao.rightExp) ||
                    leftExp.equals(ao.rightExp) && rightExp.equals(ao.leftExp) && super.equals(obj);
        }
        if (obj instanceof NumericValue)
        {
            NumericValue nv = (NumericValue) obj;
            return nv.equals(new NumericValue(0));
        }
        return false;
    }
}
