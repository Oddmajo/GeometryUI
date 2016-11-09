package backendTest.equationsTest;

import org.junit.*;

import backend.equations.SegmentEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class SegmentEquationTest
{
    @Test
    public SegmentEquationTest()
    {
        System.out.println("Running SegmentEquation...");
        SegmentEquation eq = null, eq2 = null;
        try
        {
            eq = new SegmentEquation(EquationGenerator.genAdditionEquationPair());
            eq2 = (SegmentEquation) Simplification.simplify(eq);
        }
        catch (ArgumentException e)
        {
            ExceptionHandler.throwException(new ArgumentException(e.toString()));
        }
        catch (CloneNotSupportedException e)
        {
            ExceptionHandler.throwException(new CloneNotSupportedException());
        }

        if (eq != null && eq.equals(eq2))
        {
            System.out.println("Done");
        }
        else
        {
            System.out.println("Failed...");
        }
    }

}
