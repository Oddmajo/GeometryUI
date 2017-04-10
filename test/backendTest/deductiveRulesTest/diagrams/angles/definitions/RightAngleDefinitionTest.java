package backendTest.deductiveRulesTest.diagrams.angles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class RightAngleDefinitionTest
{

    @Test
    public void testDefinition() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Perpendicular();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.RIGHT_ANGLE_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 3, flags));
    }

    @Test
    public void testTransitivity() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_TwoRightAngles();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.TRANSITIVE_CONGRUENT_ANGLE_WITH_RIGHT_ANGLE.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 2, flags));
    }

}
