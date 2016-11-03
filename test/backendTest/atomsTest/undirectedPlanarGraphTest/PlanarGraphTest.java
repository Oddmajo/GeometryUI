package backendTest.atomsTest.undirectedPlanarGraphTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.atoms.undirectedPlanarGraph.PlanarGraphEdge;

public class PlanarGraphTest
{

    @Test public void simplePlanarGraph_NodesOnlyTest()
    {
        System.out.print("\nRunning simplePlnarGraph_NodesOnlyTest...");
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point1 = new Point("point1", 2, 4);
        Point point2 = new Point("point2", 4, 1);
        Point point3 = new Point("point3", 6, 4);
        
        // create points that not added
        Point point4 = new Point("point4", 2, 4);
        Point point5 = new Point ("point5", 0,0);
        
        // print points?
//        System.out.println("point1: " + point1.toString());
//        System.out.println("point2: " + point2.toString());
//        System.out.println("point3: " + point3.toString());
//        System.out.println("point4: " + point4.toString());
//        System.out.println("point5: " + point5.toString());
        
        // add the points to the graph
//        System.out.println("Adding Points:");
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        
        // make sure it won't add duplicate points
        graph.addNode(point1);
        
        // check contains function
//        System.out.println("graph contains point1: " + graph.contains(point1));
//        System.out.println("graph contains point4: " + graph.contains(point4));
//        System.out.println("graph contains point5: " + graph.contains(point5));
//        
//        // print the nodes
//        System.out.println("Node Count: " + graph.count());
//        System.out.println(graph.toString());
//        
//        // indexOf check
//        System.out.println("index of point1: " + graph.indexOf(point1));
//        System.out.println("index point2: " + graph.indexOf(point2));
//        System.out.println("index point3: " + graph.indexOf(point3));

        // assertions thus far
        assert(graph.count() == 3);
        assert(graph.contains(point1) == true);
        assert(graph.contains(point2) == true);
        assert(graph.contains(point3) == true);
        assert(graph.contains(point4) == true);
        assert(graph.contains(point5) == false);
        
        // remove a node
//        System.out.println("removing point1");
        graph.removeNode(point1);
        
        // check if removal worked
//        System.out.println("graph contains point1: " + graph.contains(point1));
        
        // print nodes
//        System.out.println("Node Count: " + graph.count());
//        System.out.println(graph.toString());
        
        // new assertions
        assert(graph.count() == 2);
        assert(graph.contains(point1) == false);
        assert(graph.contains(point2) == true);
        assert(graph.contains(point3) == true);
        assert(graph.contains(point4) == false);
        assert(graph.contains(point5) == false);
        
        System.out.println("Done\n");
        
    }
    
    @Test public void simplePlanarGraph_NodesAndEdgesTest()
    {
        System.out.print("\nRunning simplePlanrGraph_NodesAndEdgesTest...");
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create the Points
        Point point0 = new Point("point0", 2, 4);
        Point point1 = new Point("point1", 4, 1);
        Point point2 = new Point("point2", 6, 4);
        
        // add the points to the graph
//        System.out.println("Adding Points:");
        graph.addNode(point0);
        graph.addNode(point1);
        graph.addNode(point2);
        
        // indexOf check
//        System.out.println("index of point0: " + graph.indexOf(point0));
//        System.out.println("index point1: " + graph.indexOf(point1));
//        System.out.println("index point2: " + graph.indexOf(point2));
        
        // add edges
        graph.addUndirectedEdge(point0, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point1, 0, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point0, point2, 0, EdgeType.REAL_SEGMENT);
        
        // print edges for each node
//        System.out.println("3 EDGES:");
        for (int i = 0; i < graph.count(); i++)
        {
//            System.out.println("Node " + i + ": ");
//            System.out.println("\tNode Degree: " + graph.getNodes().get(i).nodeDegree());
            int numEdges = graph.getNodes().get(i).getEdges().size();
            for (int j = 0; j < numEdges; j++)
            {
                PlanarGraphEdge theEdge = graph.getNodes().get(i).getEdges().get(j);
//                System.out.println("\tEdge " + j + ": " + theEdge.edgeType + " targeting " + theEdge.getTarget());
                assert(theEdge.edgeType == EdgeType.REAL_SEGMENT);
            }
            // each node has 2 edges
            assert(numEdges == 2);
        }
        
        
        // edge removal test
        graph.removeEdge(point0, point2);
        
        // print edges for each node
//        System.out.println("2 EDGES:");
        int totalDegreeCount = 0;
        for (int i = 0; i < graph.count(); i++)
        {
//            System.out.println("Node " + i + ": ");
//            System.out.println("\tNode Degree: " + graph.getNodes().get(i).nodeDegree());
            int numEdges = graph.getNodes().get(i).getEdges().size();
            for (int j = 0; j < numEdges; j++)
            {
                PlanarGraphEdge theEdge = graph.getNodes().get(i).getEdges().get(j);
//                System.out.println("\tEdge " + j + ": " + theEdge.edgeType + " targeting " + theEdge.getTarget());
                assert(theEdge.edgeType == EdgeType.REAL_SEGMENT);
                totalDegreeCount++;
            }
        }
        // total degree count should be 4 after edge removal
        assert(totalDegreeCount == 4);
        
        // get edge and get edge type tests
        PlanarGraphEdge edge = graph.getEdge(point1, point2);
//        System.out.println("getEdge(point1, point2): " + edge.edgeType + " targeting " + edge.getTarget());
        assert(edge.edgeType == EdgeType.REAL_SEGMENT);
        assert(edge.getTarget() == point2);
        
        EdgeType edgeT = graph.getEdgeType(point1, point2);
//        System.out.println("getEdgeType(point1, point2): " + edgeT);
        assert(edgeT == EdgeType.REAL_SEGMENT);
        
        // cycle edge tests
//        System.out.println("Marking edge from point1 to point2 a cycle edge");
        graph.markCycleEdge(point1, point2);
//        System.out.println("point1 to point2 is a cycle edge: " + graph.isCycleEdge(point1, point2));
//        System.out.println("point2 to point1 is a cycle edge: " + graph.isCycleEdge(point2, point1));
//        System.out.println("point1 to point0 is a cycle edge: " + graph.isCycleEdge(point1, point0));
        assert(graph.isCycleEdge(point1, point2) == true);
        assert(graph.isCycleEdge(point2, point1) == true);
        assert(graph.isCycleEdge(point1, point0) == false);
        
//        System.out.println("Reseting cycle edges");
        graph.reset();
//        System.out.println("point1 to point2 is a cycle edge: " + graph.isCycleEdge(point1, point2));
//        System.out.println("point2 to point1 is a cycle edge: " + graph.isCycleEdge(point2, point1));
//        System.out.println("point1 to point0 is a cycle edge: " + graph.isCycleEdge(point1, point0));
        assert(graph.isCycleEdge(point1, point2) == false);
        assert(graph.isCycleEdge(point2, point1) == false);
        assert(graph.isCycleEdge(point1, point0) == false);
        
        System.out.print("Done\n");
    }

}
