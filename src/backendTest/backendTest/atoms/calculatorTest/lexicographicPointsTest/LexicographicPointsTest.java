package backendTest.atoms.calculatorTest.lexicographicPointsTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.parameters.Utilities;

public class LexicographicPointsTest
{

    @Test public void LexicographicPointsAddTest()
    {
        System.out.println("\nLEXICOGRAPHIC POINT ADD TEST:");
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
        System.out.println("Adding Points...");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        
        //
        // Lexicographically sorted heap of all points in the graph.
        //
        LexicographicPoints heap = new LexicographicPoints();
        System.out.println("graph.count: " + graph.count());
        for (int gIndex = 0; gIndex < graph.count(); gIndex++)
        {
            System.out.println("adding gIndex " + gIndex + ": " + graph.getNodes().get(gIndex).getPoint() + " to heap");
            heap.add(graph.getNodes().get(gIndex).getPoint());
            System.out.println("heap: " + heap.toString());
        }
        
        assert(heap.toString().equals("(0: point3(1.000, 1.000)) (1: point1(1.000, 4.000)) (2: point4(4.000, 1.000)) (3: point2(4.000, 4.000)) (4: point5(7.000, 3.000)) "));
    }

}
