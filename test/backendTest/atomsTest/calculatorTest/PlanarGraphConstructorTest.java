package backendTest.atomsTest.calculatorTest;

import java.util.ArrayList;

import org.junit.Test;

import backend.atoms.calculator.PlanarGraphConstructor;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.atoms.undirectedPlanarGraph.PlanarGraphNode;
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
        
        // count the total degree of the graph
        ArrayList<PlanarGraphNode> nodes = graph.getNodes();
        int count = 0;
        for (PlanarGraphNode n : nodes)
        {
            count += n.nodeDegree();
        }
        System.out.println("Total Node Count: " + graph.count());
        System.out.println("Total Graph Degree: " + count);
        
        if (count ==  28 && graph.count() == 6)
        {
            System.out.println("Pass");
        }
        else
        {
            System.out.println("Fail");
            assert(false);
        }
    }

}
