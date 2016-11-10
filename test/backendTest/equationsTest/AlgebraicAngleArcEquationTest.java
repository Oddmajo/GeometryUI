package backendTest.equationsTest;

import org.junit.*;

import backend.equations.AlgebraicAngleArcEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class AlgebraicAngleArcEquationTest
{
    @Test
    public void algebraicAngleArcEquationTest()
    {
        System.out.println("Running AlgebraicAngleArcEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            AlgebraicAngleArcEquation eq = null, eq2 = null;
            try
            {
                eq = new AlgebraicAngleArcEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = (AlgebraicAngleArcEquation) Simplification.simplify(eq);
            }
            catch (ArgumentException e)
            {
                System.out.println("Argument");
                ExceptionHandler.throwException(new ArgumentException(e.toString()));
            }
            catch (CloneNotSupportedException e)
            {
                System.out.println("Clone");
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
