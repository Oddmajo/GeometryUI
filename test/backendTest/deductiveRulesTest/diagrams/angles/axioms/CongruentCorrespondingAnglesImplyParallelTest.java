package backendTest.deductiveRulesTest.diagrams.angles.axioms;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class CongruentCorrespondingAnglesImplyParallelTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ParallelTransversal();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.CONGRUENT_CORRESPONDING_ANGLES_IMPLY_PARALLEL.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 4, flags));
    }

}
