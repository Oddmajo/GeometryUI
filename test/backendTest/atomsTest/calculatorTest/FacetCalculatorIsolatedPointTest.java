package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.IsolatedPoint;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.parameters.Utilities;

public class FacetCalculatorIsolatedPointTest
{

    @Test public void simpleFacetCalculator_SingleIsolatedPointTest()
    {
        System.out.println("\nFACET CALCULATOR SINGLE POINT TEST:");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 4);
        
        // add the points to the graph
        System.out.println("Adding Point:");
        graph.addNode(point1);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        System.out.println("Primitives: ");
        for (Primitive prim : calc.GetPrimitives())
        {
            if (prim instanceof MinimalCycle)
            {
                MinimalCycle mincycle = (MinimalCycle) prim;
                System.out.println("\t" + mincycle.toString());
            }
            else if (prim instanceof IsolatedPoint)
            {
                IsolatedPoint isopoint = (IsolatedPoint) prim;
                System.out.println("\t" + isopoint.toString());
            }
            
        }
        assert(calc.GetPrimitives().get(0).toString().equals("Point { point1(1.000, 4.000) }"));
    }
    
    @Test public void simpleFacetCalculator_TenIsolatedPointsTest()
    {
        System.out.println("\nFACET CALCULATOR TEN POINTS TEST:");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 1);
        Point point2 = new Point("point2", 3, 1);
        Point point3 = new Point("point3", 5, 1);
        Point point4 = new Point("point4", 1, 3);
        Point point5 = new Point("point5", 3, 3);
        Point point6 = new Point("point6", 5, 3);
        Point point7 = new Point("point7", 1, 5);
        Point point8 = new Point("point8", 3, 5);
        Point point9 = new Point("point9", 5, 5);
        Point point10 = new Point("point10", 100, 100);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        graph.addNode(point6);
        graph.addNode(point7);
        graph.addNode(point8);
        graph.addNode(point9);
        graph.addNode(point10);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        System.out.println("Primitives: ");
        for (Primitive prim : calc.GetPrimitives())
        {
            if (prim instanceof MinimalCycle)
            {
                MinimalCycle mincycle = (MinimalCycle) prim;
                System.out.println("\t" + mincycle.toString());
            }
            else if (prim instanceof IsolatedPoint)
            {
                IsolatedPoint isopoint = (IsolatedPoint) prim;
                System.out.println("\t" + isopoint.toString());
            }
            
        }
        assert(calc.GetPrimitives().get(0).toString().equals("Point { point1(1.000, 1.000) }"));
        assert(calc.GetPrimitives().get(1).toString().equals("Point { point4(1.000, 3.000) }"));
        assert(calc.GetPrimitives().get(2).toString().equals("Point { point7(1.000, 5.000) }"));
        assert(calc.GetPrimitives().get(3).toString().equals("Point { point2(3.000, 1.000) }"));
        assert(calc.GetPrimitives().get(4).toString().equals("Point { point5(3.000, 3.000) }"));
        assert(calc.GetPrimitives().get(5).toString().equals("Point { point8(3.000, 5.000) }"));
        assert(calc.GetPrimitives().get(6).toString().equals("Point { point3(5.000, 1.000) }"));
        assert(calc.GetPrimitives().get(7).toString().equals("Point { point6(5.000, 3.000) }"));
        assert(calc.GetPrimitives().get(8).toString().equals("Point { point9(5.000, 5.000) }"));
        assert(calc.GetPrimitives().get(9).toString().equals("Point { point10(100.000, 100.000) }"));
    }

}
