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


    public ArrayList<GroundedClause> collectTerms()
    {
        ArrayList<GroundedClause> list = new ArrayList<GroundedClause>();

        list.addAll(leftExp.collectTerms());
        list.addAll(rightExp.collectTerms());

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

        if (obj == null) return false;
        
        if (!(obj instanceof Subtraction)) return false;

        Subtraction that = (Subtraction)obj;
        
        return (this.leftExp.equals(that.leftExp) && this.rightExp.equals(that.rightExp));
    }

//    public void simplify()
//    {
//        if (leftExp.getClass().equals(rightExp.getClass()))
//        {
//            if (leftExp instanceof AlgebraicSegmentEquation)
//            {
//
//            }
//            else if (leftExp instanceof GeometricSegmentEquation)
//            {
//
//            }
//            else if (leftExp instanceof AlgebraicAngleEquation)
//            {
//
//            }
//            else if (leftExp instanceof GeometricAngleEquation)
//            {
//
//            }
//            else if (leftExp instanceof AlgebraicArcEquation)
//            {
//
//            }
//            else if (leftExp instanceof GeometricArcEquation)
//            {
//
//            }
//            else if (leftExp instanceof AlgebraicAngleArcEquation)
//            {
//
//            }
//            else if (leftExp instanceof GeometricAngleArcEquation)
//            {
//
//            }
//            else if (leftExp instanceof NumericValue)
//            {
//
//            }
//            else if (leftExp instanceof ArithmeticOperation)
//            {
//                ((ArithmeticOperation) leftExp).simplify();
//                ((ArithmeticOperation) rightExp).simplify();
//            }
//        }
//    }
}
