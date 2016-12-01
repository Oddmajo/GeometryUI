package backendTest.symbolicAlgebraTest.equationsTest;

import java.io.IOException;

import org.junit.*;

import backend.deductiveRules.algebra.Simplification;
import backend.symbolicAlgebra.equations.*;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.logger.LoggerFactory;
import backendTest.symbolicAlgebraTest.equationsTest.generator.SegmentEquationGenerator;

public class SegmentEquationTest extends EquationTest
{
    @Test
    public void segmentEquationTest() throws IOException
    {
        LoggerFactory.initialize();
        System.out.println("Running SegmentEquation Test...");
        axEqualsB();
        System.out.println("Done");
        LoggerFactory.close();
    }
    
    public void axEqualsB()
    {
        System.out.print("\t aX = b...");

        for (int i = 0; i < 100; i++)
        {
            SegmentEquationGenerator generator = new SegmentEquationGenerator(); 
            
            Pair<Equation, Equation> eqs = generator.generateAXequalsBequation();
            
            SegmentEquation original = (SegmentEquation)eqs.first();
            SegmentEquation algSimplified = (SegmentEquation)eqs.second();

            report(original, algSimplified);
            
            SegmentEquation symSimplified = null;
            try
            {
                symSimplified = (SegmentEquation) Simplification.simplify(original);
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
            catch (ArgumentException e)
            {
                e.printStackTrace();
            }

            //
            // Verify the results
            //
            if (!symSimplified.structurallyEquals(algSimplified))
            {
                reportFailure(original, symSimplified, algSimplified);
            }
            else
            {
                reportSuccess(original, symSimplified, algSimplified);
            }
        }
        
        System.out.println("Done");
    }
    
    
    
//    
//    
//    @Test
//    public void segmentEquationTest()
//    {
//        System.out.println("Running SegmentEquation Test...");
//        for (int i = 0; i < 100; i++)
//        {
//            SegmentEquation eq = null, eq2 = null;
//            try
//            {
//                eq = new SegmentEquation(EquationGenerator.genAdditionEquationPair());
//                eq2 = new SegmentEquation(eq);
//                Simplification.simplify(eq);
//            }
//            catch (ArgumentException e)
//            {
//                ExceptionHandler.throwException(new ArgumentException(e.toString()));
//            }
//            catch (CloneNotSupportedException e)
//            {
//                ExceptionHandler.throwException(new CloneNotSupportedException());
//            }
//
//            if (eq == null)
//            {
//                System.out.println("Failed from eq being null at iteration " + i + "...");
//                break;
//            }
//            if (eq2 == null)
//            {
//                System.out.println("Failed from eq2 being null at iteration " + i + "...");
//                break;
//            }
//            else if (!eq.equals(eq2))
//            {
//                System.out.println("Failed from eq != eq2 at iteration " + i + "...");
//                break;
//            }
//        }
//        System.out.println("Done");
//    }
}
