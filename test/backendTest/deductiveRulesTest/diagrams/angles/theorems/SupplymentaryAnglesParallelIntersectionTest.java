package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class SupplymentaryAnglesParallelIntersectionTest
{

    @Test
    public void test() throws IOException
    {
        fail("Needs diagram");
        // create diagram
        Diagram diagram = DiagramGenerator.premade_AngleBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SUPPLEMENTARY_ANGLES_PARALLEL_INTERSECTION.ordinal());
        
//        assertTrue(TestDeductiveRule.test(diagram, 1, flags));

    }

}
