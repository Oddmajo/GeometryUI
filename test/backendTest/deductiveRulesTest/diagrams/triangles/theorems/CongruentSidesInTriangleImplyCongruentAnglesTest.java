package backendTest.deductiveRulesTest.diagrams.triangles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class CongruentSidesInTriangleImplyCongruentAnglesTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_IsoscelesTriangle();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.CONGRUENT_SIDES_IN_TRIANGLE_IMPLY_CONGRUENT_ANGLES.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
