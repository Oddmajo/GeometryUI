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
     * 
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
        
        // create points and segments
        Point a = new Point("A", 1, 0);
        Point b = new Point("B", 3, 0);
        
        Point c = new Point("C", 1, 2);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        // add points and segments to diagram object
        diagram.addSegment(ab);
        diagram.addSegment(cm);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.SEGMENT_BISECTOR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
