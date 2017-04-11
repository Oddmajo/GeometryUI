package backendTest.deductiveRulesTest.diagrams.segments.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class PerpendicularParallelTransversalTest
{
    

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_PerpendicularTransversal();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
