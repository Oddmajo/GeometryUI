package backend.hypergraph;

import java.util.ArrayList;
import java.util.Arrays;

import backend.deductiveRules.RuleFactory.JustificationSwitch.DeductionJustType;

public class DeductionFlags
{
    
    //
    // NOTE: DeductionFlags.dFlags is active when false and inactive when true
    // this is because the default value of a Boolean is false
    //
    
    public static Boolean[] dFlags = new Boolean[DeductionJustType.values().length];

    private static void setAllDFlags(Boolean flag) {
        Arrays.fill(dFlags, flag);
    }
    
    public static void setFlags(ArrayList<Integer> flags)
    {
        setAllDFlags(true);
        for (Integer flag : flags)
        {
            dFlags[flag] = false; 
        }
    }

}
