package backendTest.deductiveRulesTest;

import java.io.IOException;
import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.factComputer.FactComputer;
import backend.factComputer.FactComputerContainer;
import backend.factComputer.FactConnector;
import backend.hypergraph.Annotation;
import backend.hypergraph.DeductionFlags;
import backend.hypergraph.QueryableHypergraph;
import backend.precomputer.Precomputer;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.PointFactory;
import backend.utilities.logger.LoggerFactory;
import channels.fromUI.Diagram;

public class TestDeductiveRule
{
    public TestDeductiveRule() {}
    
    public static Boolean test(Diagram d, int expected, ArrayList<Integer> flags) throws IOException
    {        
        LoggerFactory.initialize();
        PointFactory.clear();
        AngleEquivalenceRelation.getInstance().clear();
        
        // set flags
        // activate specific flags
        DeductionFlags.setFlags(flags);
        
        // create the precomputer object
        Precomputer precomp = new Precomputer(d.getPoints(), d.getSegments());
        precomp.analyze();
        
        // create fact computer object and get lists
        FactComputer factComp = new FactComputer(precomp);
        factComp.analyze();
        FactComputerContainer container = factComp.getFacts();        
        
        // create qhg
        System.out.println("Creating QHG");
        QueryableHypergraph<GroundedClause, Annotation> qhg = new QueryableHypergraph<>();
        
        // add nodes to qhg
        System.out.println("Adding nodes to QHG");
        qhg.addAllNodes(container.getAllLists());
        
        // get deductions
        FactConnector deductions = new FactConnector(qhg);
        System.out.println(deductions.toString()); 
        
        // add edges to qhg
        System.out.println("Adding edges to QHG");
        qhg.addAllEdges(deductions.getDeductions());
        
        // check number of edges
        System.out.println("Number of edges: " + qhg.getEdgeCount());
        System.out.println("Number of nodes: " + qhg.getNodeCount());
        System.out.println(qhg.toString());
        
        LoggerFactory.close();
        
        return qhg.getEdgeCount() == expected;
    }
}
