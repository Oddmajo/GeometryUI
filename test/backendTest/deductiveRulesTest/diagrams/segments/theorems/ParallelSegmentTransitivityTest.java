package backendTest.deductiveRulesTest.diagrams.segments.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class ParallelSegmentTransitivityTest
{

    /**
     * There is a problem with the query not adding deductions in ParallelSegmentTransitivity -Nick 3/14
     *      ParallelSegmentTransitivity in backend/deductiveRules/segments/theorems does not actually 
     *      populate its list of deductions when called - JPN 3/29
     * 
     * E---F
     * C---D
     * A---B    
     * 
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_TransitiveParallels();
        
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.PARALLEL_SEGMENTS_TRANSITIVITY.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
