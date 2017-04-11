package backendTest.deductiveRulesTest.diagrams.triangles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class MedianDefinitionTest
{

    /**
     * Believe intersections are causing NULL Pointer -Nick 4/10
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_TriangleWithMedian();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.MEDIAN_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }
}
