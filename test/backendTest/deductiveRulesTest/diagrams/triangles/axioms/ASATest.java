package backendTest.deductiveRulesTest.diagrams.triangles.axioms;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class ASATest
{

    /**
     * Fails to create deductions -Nick 4/10
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_CongruentTriangles();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.ASA.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
