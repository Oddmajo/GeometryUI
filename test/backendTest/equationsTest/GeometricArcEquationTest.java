package backendTest.equationsTest;

import org.junit.*;

import backend.instantiator.algebra.Simplification;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.equations.GeometricArcEquation;

public class GeometricArcEquationTest
{
    @Test
    public void geometricArcEquationTest() throws Exception
    {
        System.out.println("Running GeometricArcEquation Test...");
        
        LoggerFactory.initialize();
        
        for (int i = 0; i < 100; i++)
        {
            GeometricArcEquation eq = null, eq2 = null;
            try
            {
                eq = new GeometricArcEquation(EquationGenerator.genAdditionEquationPair());
                eq2 = new GeometricArcEquation(eq);
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
        
        LoggerFactory.close();
        
        System.out.println("Done");
        
    }
}
