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

public class CongruentSegmentsIMplySegmentRationDefinitionTest
{

    /**
     *  Not adding edges - Likely Structurally Equals -Nick 3/28
     *  
     *     C            F
     *     /\           /\
     *    /  \         /  \
     *   /    \       /    \
     *  /______\     /______\
     *  A       B   D        E
     *  
     *  
     * @throws IOException
     * 
     * GeometricSegmentRatioEquation currently lives in ast->descriptors->relations->proportionalities
     * ProportionalEquation is actually a SegmentRatioEquation (same directory as above)
     * These are not currently being calculated in the fact computer and believed to be in the wrong directory
     * @author Drew W 3/30
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = new Diagram();

        // create points and segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Point c = new Point("C", 1, 3);
        
        Point d = new Point("d", 3, 0);
        Point e = new Point("e", 5, 0);
        Point f = new Point("f", 4, 3);
        
        
        
        Segment ab = new Segment(a, b);
        Segment bc = new Segment(b, c);
        Segment ac = new Segment(a, c);
        
        Segment de = new Segment(d, e);
        Segment ef = new Segment(e, f);
        Segment fd = new Segment(f, d);
        // add points and segments to diagram object
        diagram.addSegment(ab);
        diagram.addSegment(bc);
        diagram.addSegment(ac);
        diagram.addSegment(de);
        diagram.addSegment(ef);
        diagram.addSegment(fd);
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.CONGRUENT_SEGMENTS_IMPLY_PROPORTIONAL_SEGMENTS_DEFINITION.ordinal());

        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
