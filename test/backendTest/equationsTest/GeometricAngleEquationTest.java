package backendTest.equationsTest;

import org.junit.*;

import backend.equations.GeometricAngleEquation;
import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class GeometricAngleEquationTest
{
    @Test
    public void geometricAngleEquationTest()
    {
        System.out.println("Running GeometricAngleEquation Test...");
        for (int i = 0; i < 100; i++)
        {
            
            GeometricAngleEquation eq = null, eq2 = null;
            try
            {
                eq = new GeometricAngleEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = new GeometricAngleEquation(eq);
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
