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

public class MidpointTest
{
    

    /**
     *  Midpoint Test
     *   A          M          B
     * @throws IOException 
     *   @----------@----------@
     *   
     */
    @Test
    public void testTheorem() throws IOException
    {
        // create diagram
        Diagram midpointDiagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Point m = new Point("M", 1, 0);
        Segment ab = new Segment(a, b);
        
        // add points and segments to diagram object
        midpointDiagram.addSegment(ab);
        midpointDiagram.addPoint(m);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.MIDPOINT_THEOREM.ordinal());
        
        assertTrue(TestDeductiveRule.test(midpointDiagram, 1, flags));
    }
    
    @Test
    public void testDefinition() throws IOException
    {
        // create diagram
        Diagram midpointDiagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Point m = new Point("M", 1, 0);
        Segment ab = new Segment(a, b);
        
        // add points and segments to diagram object
        midpointDiagram.addSegment(ab);
        midpointDiagram.addPoint(m);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.MIDPOINT_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(midpointDiagram, 1, flags));
    }
}
