package backend.equations;

import backend.ast.GroundedClause;

public abstract class ArithmeticNode extends GroundedClause implements Cloneable
{
    public ArithmeticNode()
    {
        super();
    }
    
    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!!
        return super.getHashCode();
    }
    
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
    
    public abstract String toString();
}
