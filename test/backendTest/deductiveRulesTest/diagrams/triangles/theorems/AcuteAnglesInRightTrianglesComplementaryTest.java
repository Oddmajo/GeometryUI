package backendTest.deductiveRulesTest.diagrams.triangles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class AcuteAnglesInRightTrianglesComplementaryTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_RightTriangle();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.ACUTE_ANGLES_IN_RIGHT_TRIANGLE_ARE_COMPLEMENTARY.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 2, flags));
    }

}
