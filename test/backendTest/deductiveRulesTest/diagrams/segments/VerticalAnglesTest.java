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

public class VerticalAnglesTest
{

    /**
     * Not creating edges - Probably structurally equals -Nick 3/28
     *      C
     *       \
     *        \
     * A----------------B
     *          \
     *           \
     *            D
     * @throws IOException
     */
    @Test
    public void testTheorem() throws IOException
    {
        // create diagram
        Diagram diagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 3, 0);
        
        Point c = new Point("C", 1, 1);
        Point d = new Point("D", 2, -1);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        
        // add points and segments to diagram object
        diagram.addSegment(ab);
        diagram.addSegment(cd);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.VERTICAL_ANGLES.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
