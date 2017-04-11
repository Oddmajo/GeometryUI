package backendTest.deductiveRulesTest.diagrams.triangles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class SASSimilarityTest
{

    /**
     * Segment Ratio Equations not in container -Nick 4/11
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_SimilarRightTriangles();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SAS_SIMILARITY.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
