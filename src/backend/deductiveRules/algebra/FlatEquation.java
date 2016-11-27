package backend.deductiveRules.algebra;

import java.util.List;
import backend.ast.GroundedClause;
import backend.symbolicAlgebra.equations.AlgebraicSegmentEquation;
import backend.symbolicAlgebra.equations.Equation;

public class FlatEquation extends Equation
{
    private List<GroundedClause> lhsExps;
    public List<GroundedClause> getLhsExps() { return lhsExps; }

    private List<GroundedClause> rhsExps;
    public List<GroundedClause> getRhsExps() { return rhsExps; }

    protected FlatEquation() { super(); }

    public FlatEquation(List<GroundedClause> left, List<GroundedClause> right) 
    {
        super();
        lhsExps = left;
        rhsExps = right;
    }
    
    public GroundedClause lhsTermAt(int i)
    {
        return lhsExps.get(i);
    }

    public GroundedClause rhsTermAt(int i)
    {
        return rhsExps.get(i);
    }
    


    public GroundedClause deepCopy()
    {
        System.out.println("Cannot be properly tested at this time.");
        return this;
       /* return new AlgebraicSegmentEquation(this.lhs.deepCopy(), this.rhs.deepCopy()); */
    }

//    public boolean equals(Object obj)
//    {
//        if (obj == null) return false;
//        
//        if (!(obj instanceof AlgebraicSegmentEquation)) return false;
//
//        AlgebraicSegmentEquation eq = (AlgebraicSegmentEquation) obj;
//
//        return (lhs.equals(eq.lhs) && rhs.equals(eq.rhs)) || (lhs.equals(eq.rhs) && rhs.equals(eq.lhs));
//    }

    public String toString()
    {
        String retS = "";
        for (GroundedClause lc : lhsExps)
        {
            retS += lc.getMulitplier() + " * " + lc.toString() + " + "; 
        }
        retS = retS.substring(0, retS.length() - 3) + " = ";
        for (GroundedClause rc : rhsExps)
        {
            retS += rc.getMulitplier() + " * " + rc.toString() + " + ";
        }

        return retS.substring(0, retS.length() - 3);
    }
}

