package backendTest.deductiveRulesTest.diagrams.angles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class ComplementaryDefinition
{

    /**
     * Problem with Collect Terms in Complementary Deductive Rule -Nick 4/2
     * Actually, Could be that angles are not getting created. (Out of bounds exception)
     * @throws IOException
     */
    @Test
    public void testToComplementary() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Complementary();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.COMPLEMENTARY_DEFINITION.ordinal());
        
        //Two from, one To
        assertTrue(TestDeductiveRule.test(diagram, 3, flags));
    }


}
