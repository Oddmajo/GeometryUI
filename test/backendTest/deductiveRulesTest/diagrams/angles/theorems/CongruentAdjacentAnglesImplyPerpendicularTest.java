package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class CongruentAdjacentAnglesImplyPerpendicularTest
{

    /**
     * Angle.SharesOneRayAndHasSameVertex has null pointer exception -Nick 4/10
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughPerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.CONGRUENT_ADJACENT_ANGLES_IMPLY_PERPENDICULAR.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
