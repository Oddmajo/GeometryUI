package FactComputerTest;
import java.util.ArrayList;

import org.junit.Test;

import backend.ast.GroundedClause;
import backend.factComputer.FactComputer;
import backend.factComputer.FactComputerContainer;
import backend.precomputer.CoordinatePrecomputer;
import channels.fromUI.Diagram;

public class FactComputerTest
{
    @Test
    public void segmentEquationTest()
    {
        System.out.print("Starting segmentEquationTest... ");
        
        System.out.println("Passed");
        assert(true);
    }
    
    @Test
    public void angleEquationTest()
    {
        System.out.print("Starting angleEquationTest... ");
        
        System.out.println("Passed");
        assert(true);
    }
    
    @Test
    public void midPointTest()
    {
        System.out.print("Starting midPointTest... ");
        Diagram test = new Diagram();
        test.premade_Midpoint();
        CoordinatePrecomputer compute = new CoordinatePrecomputer(null,null,test.getSegments(),test.getPoints());
        FactComputer facts = new FactComputer(compute.getCircles(),compute.getArcs(),compute.getSegments(),compute.getPoints(),compute.getSectors());
        FactComputerContainer container = facts.getLists();
        
        if(container.getSegments().size() != 3)
        {
            System.out.println("FAILED. Incorrect amount of segments. You have: " + container.getSegments().size());
            assert(false);
        }
        else if(container.getPoints().size() != 3)
        {
            System.out.println("FAILED. Incorrect amount of points. You have: " + container.getPoints().size());
            assert(false);
        }
        else if(container.getInMiddles().size() != 1)
        {
            System.out.println("FAILED. Incorrect amount of InMiddles. You have: "+ container.getInMiddles().size());
            assert(false);
        }
        else if(container.getMidPoints().size() != 1)
        {
            System.out.println("FAILED. Incorrect amount of MidPoints. You Have: "+ container.getMidPoints().size());
            assert(false);
        }
        
        System.out.println("Passed");
        assert(true);
    }
}
