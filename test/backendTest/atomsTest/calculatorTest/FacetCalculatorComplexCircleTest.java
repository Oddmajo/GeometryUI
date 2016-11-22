package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;

public class FacetCalculatorComplexCircleTest
{
    
    @Test public void facetCalculator_SmallComplexCircle_3Points_Test()
    {
        System.out.print("\nRunning facetCalculator_SmallComplexCircle_3Points_Test...");
        
        // create the circle
        Point center = new Point("center", 0, 0);
        int radius = 4;
        Circle theCircle = new Circle(center, radius);
        
        // number of points to generate
        int n = 3;
        
        // generate the circle
        ComplexCircleGenerator circleGen = new ComplexCircleGenerator(theCircle, n);
        
        // get the graph
        PlanarGraph graph = circleGen.getGraph();
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        System.out.print("found " + calc.GetPrimitives().size() + " primitives...");
        
        if (calc.GetPrimitives().size() == (n+1)*(n+1))
        {
            System.out.println("Passed");
        }
        else 
        { 
            System.out.println("Failed");
            assert(false); 
        }
    }
    
    @Test public void facetCalculator_SmallComplexCircle_10Points_Test()
    {
        System.out.print("\nRunning facetCalculator_SmallComplexCircle_10Points_Test...");
        
        // create the circle
        Point center = new Point("center", 0, 0);
        int radius = 4;
        Circle theCircle = new Circle(center, radius);
        
        // number of points to generate
        int n = 10;
        
        // generate the circle
        ComplexCircleGenerator circleGen = new ComplexCircleGenerator(theCircle, n);
        
        // get the graph
        PlanarGraph graph = circleGen.getGraph();
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        System.out.print("found " + calc.GetPrimitives().size() + " primitives...");
        
        if (calc.GetPrimitives().size() == (n+1)*(n+1))
        {
            System.out.println("Passed");
        }
        else 
        { 
            System.out.println("Failed");
            assert(false); 
        }
    }
    
    @Test public void facetCalculator_SmallComplexCircle_20Points_Test()
    {
        System.out.print("\nRunning facetCalculator_SmallComplexCircle_20Points_Test...");
        
        // create the circle
        Point center = new Point("center", 0, 0);
        int radius = 4;
        Circle theCircle = new Circle(center, radius);
        
        // number of points to generate
        int n = 20;
        
        // generate the circle
        ComplexCircleGenerator circleGen = new ComplexCircleGenerator(theCircle, n);
        
        // get the graph
        PlanarGraph graph = circleGen.getGraph();
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        System.out.print("found " + calc.GetPrimitives().size() + " primitives...");
        
        if (calc.GetPrimitives().size() == (n+1)*(n+1))
        {
            System.out.println("Passed");
        }
        else 
        { 
            System.out.println("Failed");
            assert(false); 
        }
    }
    
}
