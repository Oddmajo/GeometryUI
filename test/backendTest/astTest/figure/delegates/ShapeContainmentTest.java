package backendTest.astTest.figure.delegates;

import java.util.Map;

import org.junit.Test;

import backend.ast.figure.ShapeHierarchy;
import backend.ast.figure.components.triangles.Triangle;

/*
* The shape hierarchy is based on shape containment: one shape must be able to be determined
* if it is completely inside another
* The point of these tests is for whether any shape is inside another.
* 
* The shapes that are considered are as follows:
*     Circle
*     Semicircle
*     Polygons: 3, 4, 5, 6 sided
*     Concave Polygons: 4, 5, 6 sided
* 
* @author C. Alvin
*/
public class ShapeContainmentTest extends GenericShapeTester
{
    //
    // > 0: Test has been run with errors
    // == 0: Test passed with zero errors
    // < 0: Test has not been run
    //
    protected static int _passedAllTests = -1;
    public boolean passed() { return _passedAllTests == 0; }
    public boolean testsHaveBeenRun() { return _passedAllTests >= 0; }
    @Override public boolean completed() { return false; }
    
    
    public ShapeContainmentTest()
    {
        super("Shape Containment");
    }

    @Test
    public void runTest()
    {
        // Check if we've run these tests before; prevent redundancy
        if (testsHaveBeenRun()) return;

        //
        // Run tests and report
        //
//        _testErrorMap.put("empty", testEmpty());
//        _testErrorMap.put("singleton", testSingleton());
//        _testErrorMap.put("filtering", testSimpleCircleContainsTriangle());

        //
        // Report results
        //
        System.out.println("\nShape Containment Testing Summary: ");
        int sum = 0;
        for (Map.Entry<String, Integer> entry : _testErrorMap.entrySet())
        {
            System.out.println("\tTest " + entry.getKey() + ": " + entry.getValue() + " errors.");
            sum += entry.getValue();
        }

        System.out.println("\tTotal number of errors: " + sum);

        // Save the fact we ran these tests once
        _passedAllTests = sum;
    }


//    //
//    // Shape hierarchy with zero shapes
//    //
//    public int testEmpty()
//    {
//        ShapeHierarchy hierarchy = new ShapeHierarchy();
//        int errors = 0;
//
//        if (!hierarchy.getSinks().isEmpty())
//        {
//            emitError("empty hierarchy has sinks");
//            errors++;
//        }
//
//        if (!hierarchy.isChild(parent, child))
//        {
//            emitError("empty hierarchy has sinks");
//            errors++;
//        }
//
//        return errors;
//    }
//
//    //
//    // Shape hierarchy with one shape (triangle)
//    //
//    public int testSingleton()
//    {
//        ShapeHierarchy hierarchy = new ShapeHierarchy();
//        int errors = 0;
//
//
//        Triangle t = new Triangle();
//
//        if (!hierarchy.getSinks().isEmpty())
//        {
//            emitError("empty hierarchy has sinks");
//            errors++;
//        }
//
//        if (!hierarchy.isChild(parent, child))
//        {
//            emitError("empty hierarchy has sinks");
//            errors++;
//        }
//
//        return errors;
//    }
}
