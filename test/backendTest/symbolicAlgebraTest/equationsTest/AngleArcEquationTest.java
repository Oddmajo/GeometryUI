package backendTest.symbolicAlgebraTest.equationsTest;

import org.junit.*;

import backend.deductiveRules.algebra.Simplification;
import backend.symbolicAlgebra.equations.*;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backendTest.symbolicAlgebraTest.equationsTest.generator.EquationGenerator;

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
                eq2 = new AngleArcEquation(eq);
                Simplification.simplify(eq);
            }
            catch (ArgumentException e)
            {
                ExceptionHandler.throwException(new ArgumentException(e.toString()));
            }
            catch (CloneNotSupportedException e)
            {
                ExceptionHandler.throwException(new CloneNotSupportedException());
            }

            if (eq == null)
            {
                System.out.println("Failed from eq being null at iteration " + i + "...");
                break;
            }
            if (eq2 == null)
            {
                System.out.println("Failed from eq2 being null at iteration " + i + "...");
                break;
            }
            else if (!eq.equals(eq2))
            {
                System.out.println("Failed from eq != eq2 at iteration " + i + "...");
                break;
            }
        }
        System.out.println("Done");
    }
}
