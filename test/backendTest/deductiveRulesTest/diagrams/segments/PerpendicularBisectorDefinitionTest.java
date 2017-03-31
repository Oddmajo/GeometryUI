package backendTest.deductiveRulesTest.diagrams.segments;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class PerpendicularBisectorDefinitionTest
{

    /**
     * Previous errors resolved.
     *      Currently finds 4 deductions, only one is added to QHG
     *      Need to check proper # of deductions
     *       -JPN 3/29
     *      
     *         C
     *         |
     *         |
     * A-------M--------B

     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_PerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_BISECTOR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 4, flags));
    }

    
    /**
     * Previous errors resolved.
     *      Currently finds 24 deductions, adds 6 added as edges to QHG
     *      Of the errors to add:
     *          12 - Target Node Does Not Exist
     *          6  - Edge pre-existing
     *       -JPN 3/29
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
    public void testThrough() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ThroughPerpendicularBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_BISECTOR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
