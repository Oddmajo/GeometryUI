package backendTest.deductiveRulesTest.diagrams.triangles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class TwoPairsCongruentAnglesImplyThirdPairCongruentTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_CongruentRightTriangles();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.TWO_PAIRS_CONGRUENT_ANGLES_IMPLY_THIRD_PAIR_CONGRUENT.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 6, flags));
    }

}
