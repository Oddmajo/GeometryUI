package backendTest.astTest.figure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

import com.google.common.graph.*;

import backend.ast.figure.Shape;
import backend.ast.figure.ShapeHierarchy;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Primitive;
import backend.atoms.calculator.PrimitiveToRegionConverter;
import backend.atoms.components.AtomicRegion;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.logger.LoggerFactory;
import backendTest.astTest.figure.delegates.GenericTester;
import backendTest.astTest.figure.delegates.ShapeContainmentTest;

//
// n-ary tree of shapes
//
//  The shape hierarchy defines a well-ordering of shapes where, for shapes A, B, C
//    A is completely contained in B
//    AND there does not exist a shape C in which A is in C and C is in B.
//
//  The shape hierarchy is a k-ary tree where the "figure" is the root;
//  To mimic this structure, we use a graph (with reversed, directed edges).
//      The outer, facade root rectangle points to the root shapes and, in general,
//      a shape points to its contained shapes
//
//
public class ShapeHierarchyTest extends GenericTester
{
   
    public ShapeHierarchyTest()
    {
        super("Shape Hierarchy");
    }
    
    @Test
    public void runTest() 
    {
//        //
//        // Shape hierarchy tests depends strictly on containment tests passing (completely).
//        // If containment testing fails, we do not test the shape hierarchy
//        //
//        if (!ShapeContainmentTest.passed())
//        {
//            LoggerFactory.getLogger(LoggerFactory.DEBUG_OUTPUT_ID).writeln("Cannot test the shape hierarchy; containment tests failed.");
//        }
            
        //
        // Run tests and report
        //
        _testErrorMap.put("empty", testEmpty());
        _testErrorMap.put("singleton", testSingleton());
        //_testErrorMap.put("filtering", testSimpleCircleContainsTriangle());

        //
        // Report results
        //
        System.out.println("\nShape Hierarchy Testing Summary: ");
        int sum = 0;
        for (Map.Entry<String, Integer> entry : _testErrorMap.entrySet())
        {
            System.out.println("\tTest " + entry.getKey() + ": " + entry.getValue() + " errors.");
            sum += entry.getValue();
        }

        System.out.println("\tTotal number of errors: " + sum);
    }

    //
    // Shape hierarchy with zero shapes
    //
    public int testEmpty()
    {
        ShapeHierarchy hierarchy = new ShapeHierarchy();
        int errors = 0;
        
        if (!hierarchy.getSinks().isEmpty())
        {
            emitError("empty hierarchy has sinks");
            errors++;
        }

//        if (!hierarchy.isChild(parent, child))
//        {
//            emitError("empty hierarchy has sinks");
//            errors++;
//        }

        return errors;
    }
    
    //
    // Shape hierarchy with one shape (triangle)
    //
    public int testSingleton()
    {
        ShapeHierarchy hierarchy = new ShapeHierarchy();
        int errors = 0;
        
        
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

        return errors;
    }

    @Override
    public boolean completed()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean passed()
    {
        // TODO Auto-generated method stub
        return false;
    }    
}