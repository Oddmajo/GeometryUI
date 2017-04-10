package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class SupplementaryAndCongruentImplyRightAnglesTest
{

    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_CongruentSupplementary();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.CONGRUENT_SUPPLEMENTARY_ANGLES_IMPLY_RIGHT_ANGLES.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 2, flags));
    }

}
