package backend.symbolicAlgebra.equations.operations;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.ast.figure.components.Segment;
import backend.symbolicAlgebra.ArithmeticNode;
import backend.symbolicAlgebra.NumericValue;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;

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

    public Pair<ArrayList<Double>, ArrayList<GroundedClause>> collectTerms()
    {
        ArrayList<Double> multipliers = new ArrayList<Double>();
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        Pair<ArrayList<Double>, ArrayList<GroundedClause>> left = leftExp.collectTerms();
        Pair<ArrayList<Double>, ArrayList<GroundedClause>> right = rightExp.collectTerms();
        
        multipliers.addAll(left.getKey());
        multipliers.addAll(right.getKey());
        
        list.addAll(left.getValue());
        list.addAll(right.getValue());

        return new Pair<ArrayList<Double>, ArrayList<GroundedClause>>(multipliers, list);
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

    @Override
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

    public boolean structurallyEquals(Object obj)
    {
        if (!(obj instanceof ArithmeticOperation))
        {
            return false;
        }
        ArithmeticOperation ao = (ArithmeticOperation) obj;
        if (!(this.rightExp instanceof Segment))
            return (this.rightExp).equals(ao.rightExp);
        Segment s = (Segment)(this.rightExp);
        return s.equals((Segment)(ao.rightExp));
    }

}
