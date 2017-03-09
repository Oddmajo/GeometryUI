package backendTest.hypergraphTest;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Midpoint;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.factComputer.FactConnector;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class QueryableHypergraphEdgeTest
{

    @Test
    public void qhgEdgeAdditionTest()
    {
        //
        // create qhg
        //
        System.out.println("Creating QHG");
        QueryableHypergraph<GroundedClause, Annotation> qhg = new QueryableHypergraph<>();
        
        //
        // add nodes to qhg
        //
        // create midpoints
        Point p1 = new Point("p1", 1, 0);
        Point p2 = new Point("p2", 1, 0);
        
        Segment s1 = new Segment(new Point("s1p1", 0, 0), new Point("s1p2", 2, 0));
        Segment s2 = new Segment(new Point("s2p1", 0, 0), new Point("s2p2", 2, 0));
        
        InMiddle im1 = new InMiddle(p1, s1);
        InMiddle im2 = new InMiddle(p2, s2);
        
        Midpoint mp1 = new Midpoint(im1);
        Midpoint mp2 = new Midpoint(im2);
        
        // add
        qhg.addNode(mp1);
        qhg.addNode(p1);
        
        //
        // make a deduction
        //
        HashSet<GroundedClause> source = new HashSet<>();
        source.add(qhg.getNode(1).data);
        Deduction d = new Deduction(source, mp2, new Annotation("point is a midpoint", false));
        
        
        // add edges to qhg
        System.out.println("Adding edges to QHG");
        qhg.addEdge(d);
        
        // check number of edges
        System.out.println("Number of edges: " + qhg.getEdgeCount());
        System.out.println("Number of nodes: " + qhg.getNodeCount());
        System.out.println(qhg.toString());
        
        assert(qhg.getEdgeCount() == 1);
    }

}
