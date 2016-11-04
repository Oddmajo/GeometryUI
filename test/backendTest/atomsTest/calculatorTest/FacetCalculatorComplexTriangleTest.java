package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Filament;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;

public class FacetCalculatorComplexTriangleTest
{

    @Test public void facetCalculator_SmallComplexTriangle_10Points_Test()
    {
        System.out.print("Running facetCalculator_SmallComplexTriangle_10Points_Test...");
        
        // variable declarations/instantiations
        int n = 10;
        Point p1 = new Point("p1", 1, 1);
        Point p2 = new Point("p2", 7, 8);
        Point p3 = new Point("p3", 14, 1);
        
        // create complex triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(p1, p2, p3, n);
        
        // get the generated graph
        PlanarGraph graph = triGen.getGraph();
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        
//        System.out.println("Number of Primitives: " + calc.GetPrimitives().size());
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            
//        }
        
        // assert the number of facets identified is correct
        assert(calc.GetPrimitives().size() == ((n+1)*(n+1)) );
        System.out.println("Done\n");
    }
    
    @Test public void facetCalculator_SmallComplexTriangle_50Points_Test()
    {
        System.out.print("Running facetCalculator_SmallComplexTriangle_50Points_Test...");
        
        // variable declarations/instantiations
        int n = 50;
        Point p1 = new Point("p1", 1, 1);
        Point p2 = new Point("p2", 7, 8);
        Point p3 = new Point("p3", 14, 1);
        
        // create complex triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(p1, p2, p3, n);
        
        // get the generated graph
        PlanarGraph graph = triGen.getGraph();
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        
//        System.out.println("Number of Primitives: " + calc.GetPrimitives().size());
//        System.out.println("Primitives: ");
//        for (Primitive prim : calc.GetPrimitives())
//        {
//            if (prim instanceof MinimalCycle)
//            {
//                MinimalCycle mincycle = (MinimalCycle) prim;
//                System.out.println("\t" + mincycle.toString());
//            }
//            else if (prim instanceof Filament)
//            {
//                Filament fil = (Filament) prim;
//                System.out.println("\t" +fil.toString());
//            }
//            
//        }
        
        // assert the number of facets identified is correct
        assert(calc.GetPrimitives().size() == ((n+1)*(n+1)) );
        System.out.println("Done\n");
    }

    @Test public void facetCalculator_LargeComplexTriangle_75Points_Test()
    {
        System.out.println("Running facetCalculator_LargeComplexTriangle_75Points_Test...");
        
        // variable declarations/instantiations
        int n = 75;
        Point p1 = new Point("p1", 1, 1);
        Point p2 = new Point("p2", 123, 538);
        Point p3 = new Point("p3", 333, 120);
        
        // create complex triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(p1, p2, p3, n);

        System.out.println("Graph generated...");
        // get the generated graph
        PlanarGraph graph = triGen.getGraph();
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        System.out.println("Number of Primitives: " + calc.GetPrimitives().size());
        
        // assert the number of facets identified is correct
        assert(calc.GetPrimitives().size() == ((n+1)*(n+1)) );
        System.out.println("Done\n");
    }
    
    @Test public void facetCalculator_LargeComplexTriangle_125Points_Test()
    {
        System.out.println("Running facetCalculator_LargeComplexTriangle_125Points_Test...");
        
        // variable declarations/instantiations
        int n = 125;
        Point p1 = new Point("p1", 1, 1);
        Point p2 = new Point("p2", 123, 538);
        Point p3 = new Point("p3", 333, 120);
        
        // create complex triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(p1, p2, p3, n);

        System.out.println("Graph generated...");
        // get the generated graph
        PlanarGraph graph = triGen.getGraph();
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        System.out.println("Number of Primitives: " + calc.GetPrimitives().size());
        
        // assert the number of facets identified is correct
        assert(calc.GetPrimitives().size() == ((n+1)*(n+1)) );
        System.out.println("Done");
    }
}
