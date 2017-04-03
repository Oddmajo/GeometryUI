package backendTest.deductiveRulesTest.diagrams.angles.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class SupplementaryDefinitionTest
{

    /**
     * Not Creating Angles -Nick 4/2
     * @throws IOException
     */
    @Test
    public void testRightAngleSupplementary() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_TwoRightAngles();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.STRAIGHT_ANGLE_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    /**
     * Not Creating Angles -Nick 4/2
     * @throws IOException
     */
    @Test
    public void testIntersectionSupplementary() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughSegmentBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.STRAIGHT_ANGLE_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }
}
