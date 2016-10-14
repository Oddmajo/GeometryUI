/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package utilities.math;

import utilities.Pair;

/**
 * Utilities focused on math-based functionality
 * 
 * @author Chris Alvin
 *
 */
public class Utilities
{
    // -1 is an error
    public static int IntegerRatio(double x, double y)
    {
        return Utilities.doubleEquals(x / y, Math.floor(x / y)) ? (int)Math.floor(x / y) : -1;
    }

    // -1 is an error
    // A reasonable value for geometry problems must be less than 10 for a ratio
    // This is arbitrarily chosen and can be modified
    private static final int RATIO_MAX = 10;
    public static int GET_RATIO_MAX() { return RATIO_MAX; }
    
    public static Pair<Integer, Integer> RationalRatio(double x, double y)
    {
        for (int numer = 2; numer < RATIO_MAX; numer++)
        {
            for (int denom = 1; denom < RATIO_MAX; denom++)
            {
                if (numer != denom)
                {
                    if (Utilities.doubleEquals(x / y, (double)(numer) / denom))
                    {
                        int gcd = GCD(numer, denom);
                        return numer > denom ? new Pair<Integer,Integer>(numer / gcd, denom / gcd)
                                             : new Pair<Integer,Integer>(denom / gcd, numer / gcd);
                    }
                }
            }
        }

        return new Pair<Integer,Integer>(-1, -1);
    }
    public static Pair<Integer, Integer> RationalRatio(double x)
    {
        for (int val = 2; val < RATIO_MAX; val++)
        {
            // Do we acquire an integer?
            if (Utilities.doubleEquals(x * val, Math.floor(x * val)))
            {
                int gcd = Utilities.GCD(val, (int)Math.round(x * val));
                return x < 1 ? new Pair<Integer,Integer>(val / gcd, (int)Math.round(x * val) / gcd) :
                               new Pair<Integer,Integer>((int)Math.round(x * val) / gcd, val / gcd);
            }
        }

        return new Pair<Integer,Integer>(-1, -1);
    }

    public static boolean IsInteger(double x)
    {
        return Utilities.doubleEquals(x, (int)x);
    }
    

    public static int GCD(int a, int b)
    {
        return b == 0 ? a : GCD(b, a % b);
    }
   
    
    public static final double EPSILON = 0.000001;

    public static boolean doubleEquals(double a, double b)
    {
        return Math.abs(a - b) < EPSILON;
    }
    
    public static boolean looseDoubleEquals(double a, double b)
    {
        return Math.abs(a - b) < 0.0001;
    }
    
    public static boolean lessThan(double a, double b)
    {
        if (doubleEquals(a, b)) return false;
        else return a - b - EPSILON < 0;
    }
    
    public static boolean greaterThan(double a, double b)
    {
        if (doubleEquals(a, b)) return false;
        return a - b + EPSILON > 0;
    }
    
    private static long[] memoizedFactorials = new long[30];
    public static long factorial(int x)
    {
        if (x <= 1) return 1;

        // Return saved
        if (memoizedFactorials[x] != 0) return memoizedFactorials[x];

        long result = 1;

        for (int i = 2; i <= x; i++)
        {
            result *= i;
        }

        // Save it
        memoizedFactorials[x] = result;
        return result;
    }

    public static long permutation(int n, int r)
    {
        if (r == 0) return 0;

        if (n == 0) return 0;

        return ((r >= 0) && (r <= n)) ? factorial(n) / factorial(n - r) : 0;
    }

    public static long combination(int a, int b)
    {
        if (a <= 1) return 1;

        return factorial(a) / (factorial(b) * factorial(a - b));
    }


}
