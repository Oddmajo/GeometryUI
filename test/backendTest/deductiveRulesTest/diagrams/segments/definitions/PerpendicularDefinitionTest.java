package backendTest.deductiveRulesTest.diagrams.segments.definitions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class PerpendicularDefinitionTest
{
    
    /**
     * Check Stands on Endpoint? OR check if deductions are being added -Nick 3/28
     *      Check if the intersection is even being found - JPN 3/29
     *         C
     *         |
     *         |
     * A-------M

     * @throws IOException
     * 
     * Intersections are ignoring endpoints, only checking betweenness @author Drew W
     */
    @Test
    public void testOnEnd() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_Perpendicular();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    /**
     * Previous issues resolved.
     *      finds 2 deductions, no edges added
     *       - JPN 3/29
     *         C
     *         |
     *         |
     * A-------M--------B

     * @throws IOException
     * 
     * 
     */
    @Test
    public void testOn() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_PerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    
    /**
     * Previous issues fixed.
     *      12 deductions found
     *      All failed to be added as edges (Target Node DNE)
     *      - JPN 3/29
     * 
     *         C
     *         |
     *         |
     * A-------M--------B
     *         |
     *         |
     *         D

     * @throws IOException
     * 
     * PerpendicularBisector block needs strenghthened and perpendicular objects added
     */
    @Test
    public void testCrossing() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughPerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
