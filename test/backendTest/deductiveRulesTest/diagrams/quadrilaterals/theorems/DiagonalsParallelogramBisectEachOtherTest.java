package backendTest.deductiveRulesTest.diagrams.quadrilaterals.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class DiagonalsParallelogramBisectEachOtherTest
{

    /**
     * No Deductions being created (Likely Intersections) -Nick 4/12
     * @throws IOException
     */
    @Test
    public void test() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_ParallelogramBisectors();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.DIAGONALS_PARALLELOGRAM_BISECT_EACH_OTHER.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }


}
