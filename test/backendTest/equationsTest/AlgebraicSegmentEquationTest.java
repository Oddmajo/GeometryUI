package backendTest.equationsTest;

import org.junit.*;

import backend.equations.AlgebraicSegmentEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class AlgebraicSegmentEquationTest
{
    @Test
    public void algebraicSegmentEquationTest()
    {
        System.out.println("Running AlgebraicSegmentEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            AlgebraicSegmentEquation eq = null, eq2 = null;
            try
            {
                eq = new AlgebraicSegmentEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = (AlgebraicSegmentEquation) Simplification.simplify(eq);
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
