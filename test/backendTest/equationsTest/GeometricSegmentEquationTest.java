package backendTest.equationsTest;

import org.junit.*;

import backend.equations.GeometricSegmentEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class GeometricSegmentEquationTest
{
    @Test
    public void geometricSegmentEquationTest()
    {
        System.out.println("Running GeometricSegmentEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            GeometricSegmentEquation eq = null, eq2 = null;
            try
            {
                eq = new GeometricSegmentEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = (GeometricSegmentEquation) Simplification.simplify(eq);
            }
            catch (ArgumentException e)
            {
                ExceptionHandler.throwException(new ArgumentException(e.toString()));
            }
            catch (CloneNotSupportedException e)
            {
                ExceptionHandler.throwException(new CloneNotSupportedException());
            }

            if (eq == null || !eq.equals(eq2))
            {
                System.out.println("Failed at iteration " + i + "...");
                break;
            }
        }
        System.out.println("Done");
    }
}
