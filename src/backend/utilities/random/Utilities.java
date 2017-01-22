package backend.utilities.random;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Utilities
{
    private Utilities() { }

    /*
     * @param low lower bound of value range
     * @param high upper bound of value range
     * @param n number of desired random integer values in [low, high)
     * @return a list of n randomly generated values in the stated range [low, high)
     *         returned in sorted order
     */
    public static Integer[] genIntegers(int low, int high, int n)
    {
        // Check if more values are requested than are available
        if (high - low > n) return null;
        
        Random rng = new Random();
        TreeSet<Integer> values = new TreeSet<Integer>();

        // Standard generation and containment check algorithm
        int count = 0;
        while (count < n)
        {
            int newValue = low + rng.nextInt(high - low);
            if (!values.contains(newValue))
            {
                values.add(newValue);
                count++;
            }
        }

        return (Integer[])values.toArray();
    }
    
    /*
     * @param low lower bound of value range
     * @param high upper bound of value range
     * @param n number of desired random double values in [low, high)
     * @return a list of n randomly generated values in the stated range [low, high)
     *         returned in sorted order
     */
    public static Double[] genDoubles(int low, int high, int n)
    {
        // Check if more values are requested than are available
        if (high - low > n) return null;
        
        Random rng = new Random();
        TreeSet<Double> values = new TreeSet<Double>();

        // Standard generation and containment check algorithm
        for (int count = 0; count < n; count++)
        {
            double newValue = low + rng.nextInt(high - low);

            values.add(newValue);
        }

        return (Double[])values.toArray();
    }
}
