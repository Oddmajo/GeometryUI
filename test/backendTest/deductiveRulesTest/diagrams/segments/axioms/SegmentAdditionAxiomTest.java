package backendTest.deductiveRulesTest.diagrams.segments.axioms;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class SegmentAdditionAxiomTest
{

    /**
     * A     M     B
     * *-----*-----*
     * 
     * AM + MB = AB
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Midpoint();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SEGMENT_ADDITION_AXIOM.ordinal());

        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
