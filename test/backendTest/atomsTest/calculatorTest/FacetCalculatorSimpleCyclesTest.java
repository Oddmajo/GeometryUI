package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Filament;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.parameters.Utilities;

public class FacetCalculatorSimpleCyclesTest
{

    @Test public void simpleFacetCalculator_SingleTrangleCycleTest()
    {
        System.out.println("\nFACET CALCULATOR SIMPLE TRIANGLE TEST");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 2, 4);
        Point point2 = new Point("point2", 4, 1);
        Point point3 = new Point("point3", 6, 4);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        
        // add edges
        graph.addUndirectedEdge(point3, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point2, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof MinimalCycle)
            {
                MinimalCycle mincycle = (MinimalCycle) prim;
                System.out.println("\t" + mincycle.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        MinimalCycle mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point1(2.000, 4.000), point2(4.000, 1.000), point3(6.000, 4.000) }"));
        
    }
    
    @Test public void simpleFacetCalculator_SingleSquareCycleTest()
    {
        System.out.println("\nFACET CALCULATOR SIMPLE SQUARE TEST:");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 4, 4);
        Point point2 = new Point("point2", 2, 2);
        Point point3 = new Point("point3", 4, 2);
        Point point4 = new Point("point4", 2, 4);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        
        // add edges
        graph.addUndirectedEdge(point3, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point2, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof MinimalCycle)
            {
                MinimalCycle mincycle = (MinimalCycle) prim;
                System.out.println("\t" + mincycle.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        MinimalCycle mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point2(2.000, 2.000), point3(4.000, 2.000), point1(4.000, 4.000), point4(2.000, 4.000) }"));
    }
    
    @Test public void simpleFacetCalculator_DoubleCycleTest()
    {
        System.out.println("\nFACET CALCULATOR DOUBLE CYCLE TEST:");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 4);
        Point point2 = new Point("point2", 4, 4);
        Point point3 = new Point("point3", 1, 1);
        Point point4 = new Point("point4", 4, 1);
        Point point5 = new Point("point5", 7, 3);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        
        // add edges
        graph.addUndirectedEdge(point3, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point5, point2, 0, EdgeType.REAL_SEGMENT);
        
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
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        MinimalCycle mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point3(1.000, 1.000), point4(4.000, 1.000), point2(4.000, 4.000), point1(1.000, 4.000) }"));
        
        prim = calc.GetPrimitives().get(1);
        mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point4(4.000, 1.000), point5(7.000, 3.000), point2(4.000, 4.000) }"));
    }
    
    @Test public void simpleFacetCaclulator_NestedCyclesTest()
    {
        System.out.println("\nFACET CALCULATOR NESTED CYCLES TEST:");
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
        
        // add edges
        graph.addUndirectedEdge(point1, point2, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point1, point4, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point3, point6, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point5, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point4, point7, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point6, point9, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point7, point8, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point8, point9, 0, EdgeType.REAL_SEGMENT);
        
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
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        MinimalCycle mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point1(1.000, 1.000), point2(3.000, 1.000), point5(3.000, 3.000), point4(1.000, 3.000) }"));
        
        prim = calc.GetPrimitives().get(1);
        mincycle = (MinimalCycle) prim;
        assert(mincycle.toString().equals("Cycle { point4(1.000, 3.000), point5(3.000, 3.000), point2(3.000, 1.000), point3(5.000, 1.000), point6(5.000, 3.000), point9(5.000, 5.000), point8(3.000, 5.000), point7(1.000, 5.000) }"));
    }
    
    

}
