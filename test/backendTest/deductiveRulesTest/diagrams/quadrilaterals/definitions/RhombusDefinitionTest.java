package backendTest.deductiveRulesTest.diagrams.quadrilaterals.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class RhombusDefinitionTest
{
    /**
     * Rhombus container in QHG is null
     * ALSO Quadrilaterals not in QHG container -Nick 4/12 -Nick 4/12
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Rhombus();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.RHOMBUS_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 8, flags));
    }

}
