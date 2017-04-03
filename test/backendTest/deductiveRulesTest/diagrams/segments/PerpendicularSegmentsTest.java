package backendTest.deductiveRulesTest.diagrams.segments;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class PerpendicularSegmentsTest
{

    /**
     * Not Finding Deductive Rules
     *         C
     *         |
     *         |
     * A-------M

     * @throws IOException
     */
    @Test
    public void testOnEnd() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Perpendicular();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_SEGMENTS.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    /**
     * Not finding Deductions -Nick 3/28
     *         C
     *         |
     *         |
     * A-------M--------B

     * @throws IOException
     */
    @Test
    public void testOn() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_PerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_SEGMENTS.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    
    /**
     * 
     *         C
     *         |
     *         |
     * A-------M--------B
     *         |
     *         |
     *         D

     * @throws IOException
     */
    @Test
    public void testCrossing() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughPerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_SEGMENTS.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
