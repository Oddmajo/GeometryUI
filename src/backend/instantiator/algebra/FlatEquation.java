package instantiator.algebra;

import backend.equations.*;
import java.util.List;

public class FlatEquation extends Equation
{
    public List<GroundedClause> lhsExps;
    public List<GroundedClause> rhsExps;

    public List<GroundedClause> getLhsExps()
    {
        return lhsExps;
    }
    public List<GroundedClause> getRhsExps()
    {
        return rhsExps;
    }

    public FlatEquation(){

        super();
    }
    
    public GroundedClause lhsTermAt(int i)
    {
        return lhsExps.get(i);
    }

    public GroundedClause rhsTermAt(int i)
    {
        return rhsExps.get(i);
    }
    
    public FlatEquation(List<GroundedClause> left, List<GroundedClause> right) 
    {
        super();
        lhsExps = left;
        rhsExps = right;
    }

    public GroundedClause deepCopy() throws CloneNotSupportedException
    {
        System.out.println("Cannot be properly tested at this time.");
        return this;
       /* return new AlgebraicSegmentEquation(this.lhs.deepCopy(), this.rhs.deepCopy()); */
    }

    public boolean equals(Object obj)
    {

        if (obj == null || (AlgebraicSegmentEquation) obj == null) return false;
        AlgebraicSegmentEquation eq = (AlgebraicSegmentEquation) obj;
        return (lhs.equals(eq.lhs) && rhs.equals(eq.rhs)) || (lhs.equals(eq.rhs) && rhs.equals(eq.lhs));
    }

    public  int getHashCode() { return super.getHashCode(); }

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

