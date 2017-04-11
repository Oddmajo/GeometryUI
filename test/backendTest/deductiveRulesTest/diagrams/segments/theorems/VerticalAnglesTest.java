package backendTest.deductiveRulesTest.diagrams.segments.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class VerticalAnglesTest
{

    /**
     * Not creating edges - Probably structurally equals -Nick 3/28
     * One of the deductions is being duplicated and has no "from" grounded clause - Drew 3/30
     *      C
     *       \
     *        \
     * A----------------B
     *          \
     *           \
     *            D
     * @throws IOException
     */
    @Test
    public void testTheorem() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughSegmentBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.VERTICAL_ANGLES.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
