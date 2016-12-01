package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.atoms.calculator.PlanarGraphConstructor;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import channels.fromUI.Diagram;

public class PlanarGraphConstructorTest
{

    @Test public void PlanarGraphConstuctorTriangleTest()
    {
        System.out.println("Running PlanarGraphConstuctorTriangleTest");
        
        // create the triangle diagram
        Diagram diagram = new Diagram();
        diagram.premade_Triangles();
        
        // construct the planar graph
        PlanarGraphConstructor graphConstructor = new PlanarGraphConstructor(diagram);
        
        // get the planar graph
        PlanarGraph graph = graphConstructor.constructGraph();
        
        // print the graph to check
        System.out.println("Planar Graph:");
        System.out.println(graph.toString());
        
        System.out.println("Done");
    }

}
