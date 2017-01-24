package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.Circle;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class CongruentCircles extends Congruent
{
    protected Circle cc1;
    public Circle getCircle1() { return cc1; }

    protected Circle cc2;
    public Circle getCircle2() { return cc2; }
    
    public CongruentCircles(Circle c1, Circle c2)
    {
        super();
        
        cc1 = c1;
        cc2 = c2;

        if (!verifyCongruentCircles())
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Circles deduced congruent when radii differ " + this));
        }
    }

    @Override
    public boolean isReflexive()
    {
        return cc1.getCenter().equals(cc2.getCenter());
    }

    public Circle OtherCircle(Circle thatCircle)
    {
        if (cc1.structurallyEquals(thatCircle)) return cc2;
        if (cc2.structurallyEquals(thatCircle)) return cc1;

        return null;
    }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        
        if (!(obj instanceof CongruentCircles)) return false;

        CongruentCircles cts = (CongruentCircles)obj;

        return this.cc1.structurallyEquals(cts.cc1) || this.cc2.structurallyEquals(cts.cc2) ||
               this.cc2.structurallyEquals(cts.cc1) || this.cc1.structurallyEquals(cts.cc2);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        
        if (!(obj instanceof CongruentCircles)) return false;

        CongruentCircles cts = (CongruentCircles)obj;

        return this.cc1.equals(cts.cc1) || this.cc2.equals(cts.cc2) ||
               this.cc2.equals(cts.cc1) || this.cc1.equals(cts.cc2);
    }

    public boolean verifyCongruentCircles()
    {
        return MathUtilities.doubleEquals(cc1.getRadius(), cc2.getRadius());
    }

    @Override
    public String toString() { return "Congruent(" + cc1.toString() + ", " + cc2.toString() + ") " + justification; }

}
