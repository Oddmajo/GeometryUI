package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class AdjacentAnglesPerpendicularImplyComplementaryTest
{

    /**
     * No Perpendiculars Found
     * Can Perpendiculars not lie on endpoints? 
     * |
     * |        <-- Like that
     * |
     * |_______
     * It does find a right angle which implies perpendicular -Nick 4/10
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Complementary();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.ADJACENT_ANGLES_PERPENDICULAR_IMPLY_COMPLEMENTARY.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
