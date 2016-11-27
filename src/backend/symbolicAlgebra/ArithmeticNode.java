package backend.symbolicAlgebra;

import backend.ast.GroundedClause;

public abstract class ArithmeticNode extends GroundedClause implements Cloneable
{
    protected ArithmeticNode() { super(); }
    
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
    
    public abstract String toString();
}
