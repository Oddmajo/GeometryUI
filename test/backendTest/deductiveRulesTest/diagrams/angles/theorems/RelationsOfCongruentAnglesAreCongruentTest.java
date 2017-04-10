package backendTest.deductiveRulesTest.diagrams.angles.theorems;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import backend.deductiveRules.RuleFactory;
import backendTest.deductiveRulesTest.TestDeductiveRule;
import channels.fromUI.Diagram;
import channels.fromUI.DiagramGenerator;

public class RelationsOfCongruentAnglesAreCongruentTest
{

    /**
     * No Congruent Angles Found -Nick 4/10
     * @throws IOException
     */
    @Test
    public void Test_First_Relations() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_CongruentRealtions1();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.RELATIONS_OF_CONGRUENT_ANGLES_ARE_CONGRUENT.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }
    
    /**
     * No Congruent Angles Found -Nick 4/10
     * @throws IOException
     */
    @Test
    public void Test_Second_Relations() throws IOException
    {
        // create diagram
        Diagram diagram = DiagramGenerator.premade_CongruentRealtions2();
        
        // create flags array
        ArrayList<Integer> flags = new ArrayList<>();
        flags.add(RuleFactory.JustificationSwitch.DeductionJustType.RELATIONS_OF_CONGRUENT_ANGLES_ARE_CONGRUENT.ordinal());
        
        assertTrue(TestDeductiveRule.test(diagram, 1, flags));
    }

}
