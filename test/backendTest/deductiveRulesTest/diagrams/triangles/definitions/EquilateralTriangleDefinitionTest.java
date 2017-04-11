package backendTest.deductiveRulesTest.diagrams.triangles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class EquilateralTriangleDefinitionTest
{

    /**
     * Not adding half of the edges -Nick 4/10
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_EquilateralTriangle();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.EQUILATERAL_TRIANGLE_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 12, flags));
    }

}
