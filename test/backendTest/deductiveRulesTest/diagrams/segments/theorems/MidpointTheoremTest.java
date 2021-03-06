package backendTest.deductiveRulesTest.diagrams.segments.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class MidpointTheoremTest
{
    

    /**
     *  Midpoint Test
     *   A          M          B
     * @throws IOException 
     *   @----------@----------@
     *   
     */
    @Test
    public void testTheorem() throws IOException
    {
        // create diagram
        Diagram midpointDiagram = DiagramGenerator.premade_Midpoint();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.MIDPOINT_THEOREM.ordinal());
        
        assertTrue(TestDeductiveRule.test(midpointDiagram, 4, flags));
    }
    

}
