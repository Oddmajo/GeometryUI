package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class PerpendicularCongruentAdjacentAnglesTest
{

    /**
     * It is duplicating two of the deductions , Should find 4, is finding 6 -Nick 4/10
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughPerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_IMPLY_CONGRUENT_ADJACENT_ANGLES.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 4, flags));
    }

}
