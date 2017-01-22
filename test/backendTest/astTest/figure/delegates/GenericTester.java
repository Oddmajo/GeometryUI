package backendTest.astTest.figure.delegates;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class GenericTester
{
    protected HashMap<String, Integer> _testErrorMap;
    protected Random _rng;
    protected String _prefix;
    
    public GenericTester(String prefix)
    {
        _testErrorMap = new HashMap<String, Integer>();
        _rng = new Random();
        _rng.setSeed(0); // consistent testing
        _prefix = prefix;
    }
    
    public abstract void runTest();
    public abstract boolean completed();
    public abstract boolean passed();
    
    public void emitError(String error)
    {
        System.out.println(_prefix + ": " + error);
    }

    //
    // Report the results of this test
    //
    protected int report()
    {
        System.out.println("\n" + _prefix + " Testing Summary: ");
        
        int sum = 0;
        for (Map.Entry<String, Integer> entry : _testErrorMap.entrySet())
        {
            System.out.println("\tTest " + entry.getKey() + ": " + entry.getValue() + " errors.");
            sum += entry.getValue();
        }

        System.out.println("\tTotal number of errors: " + sum);
        return sum;
    }
}