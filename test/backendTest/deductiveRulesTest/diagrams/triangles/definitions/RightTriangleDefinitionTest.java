package backendTest.deductiveRulesTest.diagrams.triangles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class RightTriangleDefinitionTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_RightTriangle();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.RIGHT_TRIANGLE_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 3, flags));
    }


}
