package backendTest.symbolicAlgebraTest.equationsTest;

import org.junit.*;

import backend.deductiveRules.algebra.Simplification;
import backend.symbolicAlgebra.equations.*;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backendTest.symbolicAlgebraTest.equationsTest.generator.EquationGenerator;

public class AlgebraicSegmentEquationTest
{
    @Test
    public void algebraicSegmentEquationTest()
    {
        System.out.println("Running AlgebraicSegmentEquation Test...");
        for (int i = 0; i < 10000; i++)
        {
            AlgebraicSegmentEquation eq = null, eq2 = null, key = null, value = null;
            try
            {
                Pair<Equation, Equation> tests = EquationGenerator.genAdditionEquationPair();
                
                key = new AlgebraicSegmentEquation(tests.getKey());
                value = new AlgebraicSegmentEquation(tests.getValue());
                eq = new AlgebraicSegmentEquation(tests.getKey());
                System.out.println("eq (before): " + eq);
                eq2 = new AlgebraicSegmentEquation(tests.getValue());
                System.out.println("eq2 (before): " + eq2);
                eq = (AlgebraicSegmentEquation) Simplification.simplify(eq);
                
                System.out.println("eq (after): " + eq);
                System.out.println("eq2 (after): " + eq2);
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
            else if (!eq.equals(value))
            {
                System.out.println("Failed from eq != eq2 at iteration " + i + "...");
                break;
            }
        }
        System.out.println("Done");
    }
}
