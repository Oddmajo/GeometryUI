package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;

public class ComplexCircleGeneratorTest
{

    @Test public void ComplexCircleGenerator_Test()
    {
        System.out.println("Running ComplexCircleGenerator_Test...");
        
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
        
        // print the graph
        System.out.println(graph.toString());
        
        System.out.println("Done");
    }

}
