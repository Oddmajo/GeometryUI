package backendTest.deductiveRulesTest.diagrams;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import backend.ast.GroundedClause;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.RuleFactory;
import backend.factComputer.FactComputer;
import backend.factComputer.FactComputerContainer;
import backend.factComputer.FactConnector;
import backend.hypergraph.Annotation;
import backend.hypergraph.DeductionFlags;
import backend.hypergraph.QueryableHypergraph;
import backend.precomputer.CoordinatePrecomputer;
import channels.fromUI.Diagram;

public class MidpointTest
{
    

    /**
     *  Midpoint Test
     *   A          M          B
     *   @----------@----------@
     *   
     */
    @Test
    public void test()
    {
        // create diagram
        Diagram midpointDiagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Point m = new Point("M", 1, 0);
        Segment ab = new Segment(a, b);
        
        // add points and segments to diagram object
        midpointDiagram.addSegment(ab);
        midpointDiagram.addPoint(m);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.MIDPOINT_THEOREM.ordinal());
        
        assert(testFramework(midpointDiagram, 1, flags));
    }
    
    
    public static Boolean testFramework(Diagram d, int expected, ArrayList<Integer> flags)
    {        
        // set flags
        // activate specific flags
        DeductionFlags.setFlags(flags);
        
        // create the precomputer object
        CoordinatePrecomputer precomp = new CoordinatePrecomputer(null, null, d.getSegments(), d.getPoints());
        
        // create fact computer object and get lists
        FactComputer factComp = new FactComputer(precomp.getCircles(), precomp.getArcs(), precomp.getSegments(), precomp.getPoints(), precomp.getSectors());
        FactComputerContainer container = factComp.getLists();        
        
        // create qhg
        QueryableHypergraph<GroundedClause, Annotation> qhg = new QueryableHypergraph<>();
        
        // add nodes to qhg
        qhg.addAllNodes(container.getAllLists());
        
        // get deductions
        FactConnector deductions = new FactConnector(qhg);
        
        // add edges to qhg
        qhg.addAllEdges(deductions.getDeductions());
        
        // check number of edges
        System.out.println("Number of edges: " + qhg.getEdgeCount());
        System.out.println("Number of nodes: " + qhg.getNodeCount());
        System.out.println(qhg.toString());
        return qhg.getEdgeCount() == expected;
    }

}
