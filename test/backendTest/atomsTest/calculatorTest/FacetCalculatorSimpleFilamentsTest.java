package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Filament;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.parameters.Utilities;

public class FacetCalculatorSimpleFilamentsTest
{

    @Test public void simpleFacetCalculator_SingleNotStraightFilamentTest() 
    {
        System.out.println("\nFACET CALCULATOR SIMPLE NOT STRAIGHT FILAMENT TEST");
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
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof Filament)
            {
                Filament fil = (Filament) prim;
                System.out.println("\tprimitive: " + fil.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        Filament fil = (Filament) prim;
        assert(fil.toString().equals("Filament { point3(6.000, 4.000), point1(2.000, 4.000), point2(4.000, 1.000) }"));
    }
    
    @Test public void simpleFacetCalculator_SingleStriaghtFilamentTest() 
    {
        System.out.println("\nFACET CALCULATOR SIMPLE STRAIGHT FILAMENT TEST");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 2);
        Point point2 = new Point("point2", 3, 2);
        Point point3 = new Point("point3", 7, 2);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point2);
        graph.addNode(point1);
        graph.addNode(point3);
        
        // add edges
        graph.addUndirectedEdge(point2, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof Filament)
            {
                Filament fil = (Filament) prim;
                System.out.println("\tprimitive: " + fil.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        Filament fil = (Filament) prim;
        assert(fil.toString().equals("Filament { point1(1.000, 2.000), point2(3.000, 2.000), point3(7.000, 2.000) }"));
    }
    
    @Test public void simpleFacetCalculator_Single90DegreeLowerFilamentTest() 
    {
        System.out.println("\nFACET CALCULATOR SIMPLE 90 DEGREE LOWER FILAMENT TEST");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 4);
        Point point2 = new Point("point2", 1, 1);
        Point point3 = new Point("point3", 5, 1);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        
        // add edges
        graph.addUndirectedEdge(point2, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof Filament)
            {
                Filament fil = (Filament) prim;
                System.out.println("\tprimitive: " + fil.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        Filament fil = (Filament) prim;
        assert(fil.toString().equals("Filament { point3(5.000, 1.000), point2(1.000, 1.000), point1(1.000, 4.000) }"));
    }
    
    @Test public void simpleFacetCalculator_Single90DegreeUpperFilamentTest() 
    {
        System.out.println("\nFACET CALCULATOR SIMPLE 90 DEGREE UPPER FILAMENT TEST");
        Utilities.OVERRIDE_DEBUG = true;
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 1, 4);
        Point point2 = new Point("point2", 1, 1);
        Point point3 = new Point("point3", 5, 4);
        
        // add the points to the graph
        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        
        // add edges
        graph.addUndirectedEdge(point1, point3, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        
        //print the graph
        System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        for (Primitive prim : calc.GetPrimitives())
        {
            System.out.println("Primitives: ");
            if (prim instanceof Filament)
            {
                Filament fil = (Filament) prim;
                System.out.println("\tprimitive: " + fil.toString());
            }
            
        }
        Primitive prim = calc.GetPrimitives().get(0);
        Filament fil = (Filament) prim;
        assert(fil.toString().equals("Filament { point2(1.000, 1.000), point1(1.000, 4.000), point3(5.000, 4.000) }"));
    }

}
