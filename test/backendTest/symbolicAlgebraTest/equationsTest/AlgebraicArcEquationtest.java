package backendTest.symbolicAlgebraTest.equationsTest;

import org.junit.*;

import backend.deductiveRules.algebra.Simplification;
import backend.symbolicAlgebra.equations.*;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backendTest.symbolicAlgebraTest.equationsTest.generator.EquationGenerator;

public class AlgebraicArcEquationtest
{
    @Test
    public void algebraicArcEquationTest()
    {
        System.out.println("Running AlgebraicArcEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            AlgebraicAngleEquation eq = null, eq2 = null;
            try
            {
                System.out.println("1");
                eq = new AlgebraicAngleEquation(EquationGenerator.genAdditionEquationPair());
                System.out.println("2");
                eq2 = (AlgebraicAngleEquation) Simplification.simplify(eq);
                System.out.println("3");
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
