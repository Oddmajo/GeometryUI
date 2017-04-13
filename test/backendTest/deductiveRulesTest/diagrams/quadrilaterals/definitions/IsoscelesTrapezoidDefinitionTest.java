package backendTest.deductiveRulesTest.diagrams.quadrilaterals.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class IsoscelesTrapezoidDefinitionTest
{

    /**
     * Failed to add edges with GeometricCongruent objects -Nick 4/12
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_IsoscelesTrapezoid();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.ISOSCELES_TRAPEZOID_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 4, flags));
    }

}
