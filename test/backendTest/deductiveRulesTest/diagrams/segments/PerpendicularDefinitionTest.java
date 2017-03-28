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

public class PerpendicularDefinitionTest
{
    
    /**
     * Check Stands on Endpoint? OR check if deductions are being added -Nick 3/28
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
        Diagram diagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 1, 0);
        
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, 0);
        
        Segment am = new Segment(a, m);
        Segment cm = new Segment(c, m);
        
        // add points and segments to diagram object
        diagram.addSegment(am);
        diagram.addSegment(cm);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    /**
     * Out of bounds in fact computer -Nick 3/28
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
        Diagram diagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 1, 0);
        Point b = new Point("B", 3, 0);
        
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        // add points and segments to diagram object
        diagram.addSegment(ab);
        diagram.addSegment(cm);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

    
    /**
     * Intersections are null and null && Not creating any deductions (Probably because null and null) -Nick 3/28
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
        Diagram diagram = new Diagram();
        
        // create points and segments
        Point a = new Point("A", 1, 0);
        Point b = new Point("B", 3, 0);
        
        Point c = new Point("C", 2, 1);
        Point d = new Point("d", 2, -1);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        
        // add points and segments to diagram object
        diagram.addSegment(ab);
        diagram.addSegment(cd);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PERPENDICULAR_DEFINITION.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
