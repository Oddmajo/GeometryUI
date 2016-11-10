package backendTest.equationsTest;

import org.junit.*;

import backend.equations.AngleArcEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class AngleArcEquationTest
{
    @Test
    public void angleArcEquationTest()
    {
        System.out.println("Running AngleArcEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            AngleArcEquation eq = null, eq2 = null;
            try
            {
                eq = new AngleArcEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = (AngleArcEquation) Simplification.simplify(eq);
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
