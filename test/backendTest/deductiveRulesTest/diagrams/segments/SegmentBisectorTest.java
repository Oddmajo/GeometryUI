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

public class SegmentBisectorTest
{

    /**
     * Breaks somewhere in fact computer -Nick 3/28
     *      C
     *       \
     *        \
     * A-------M--------B

     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = new Diagram();
        diagram.premade_SegmentBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SEGMENT_BISECTOR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    
    /**
     * Intersections are null and null -Nick 3/28
     *      C
     *       \
     *        \
     * A-------M--------B
     *          \
     *           \
     *            D

     * @throws IOException
     */
    @Test
    public void testThrough() throws IOException
    {
        // create diagram
        Diagram diagram = new Diagram();
        diagram.premade_ThroughSegmentBisector();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SEGMENT_BISECTOR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }
}
