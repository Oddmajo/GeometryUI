/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package backend.utilities.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import backend.ast.figure.Figure;
import backend.utilities.Pair;

/**
 * The Utilities class from the GeometryTutor project, converted from C#
 * <p>
 * Currently, I only have the booleans at the beginning and the following methods:
 *      AddUnique
 *      AddUniqueList
 *      makeList
 * The rest are commented out to be converted to Java : the future.
 * <p>
 * I have attempted to convert many of the functions, but I think some are done
 * incorrectly.  I have left them commented out for sanity's sake and to get the
 * Facet Identification package going.  I will convert the methods as needed.
 * @author Chris Alvin
 * @author Drew W
 *
 */
public class PowersetUtilities
{



    //
    // Constructs an integer representation of the powerset based on input value integer n
    // e.g. 4 -> { {}, {0}, {1}, {2}, {3}, {0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}, {0, 1, 2}, {0, 1, 3}, {0, 2, 3}, {1, 2, 3}, {0, 1, 2, 3} }
    //
    class SetCompare implements Comparator<List<Integer>>
    {
        private static final int GREATER = 1;
        private static final int EQUAL = 0;
        private static final int LESS = -1;

        @Override
        public int compare(List<Integer> set1, List<Integer> set2)
        {
            // Discriminate based on set size foremost
            if (set1.size() < set2.size()) return LESS;
            if (set1.size() > set2.size()) return GREATER;

            for (int i = 0; i < set1.size() && i < set2.size(); i++)
            {
                if (set1.get(i) < set2.get(i)) return LESS;
                if (set1.get(i) > set2.get(i)) return GREATER;
            }

            return EQUAL;
        }
    }
    
    private static List<List<Integer>> ConstructRestrictedPowerSet(int n, int maxCardinality)
    {
        if (n <= 0) return backend.utilities.list.Utilities.makeList(new ArrayList<Integer>());

        List<List<Integer>> powerset = ConstructRestrictedPowerSet(n - 1, maxCardinality);
        List<List<Integer>> newCopies = new ArrayList<List<Integer>>();

        for (List<Integer> intlist : powerset)
        {
            if (intlist.size() < maxCardinality)
            {
                // Make a copy, add to copy, add to overall list
                List<Integer> copy = new ArrayList<Integer>(intlist);
                copy.add(n - 1); // We are dealing with indices, subtract 1
                newCopies.add(copy);
            }
        }

        powerset.addAll(newCopies);

        return powerset;
    }

    // A memoized copy of all the powersets. 10 is large for this, we expect max of 5.
    // Note, we use a matrix since maxCardinality may change
    // We maintain ONLY an array because we are using this for a specific purpose : this project
    private static List<List<List<Integer>>> memoized = new ArrayList<List<List<Integer>>>(14);
    private static List<List<String>> memoizedCompressed = new ArrayList<List<String>>(14);
    private static List<List<List<Integer>>> memoizedWithSingletons = new ArrayList<List<List<Integer>>>(14);
    private static List<List<String>> memoizedCompressedWithSingletons = new ArrayList<List<String>>(14);
    private static void ConstructPowerSetWithNoEmptyHelper(int n, int maxCardinality)
    {
        if (memoized.get(n) != null) return;

        // Construct the powerset and remove the emptyset
        List<List<Integer>> powerset = ConstructRestrictedPowerSet(n, maxCardinality);
        powerset.remove(0);

        // Sort so the smallest sets are first and sets of the same size are compared based on elements.
//        Collections.sort(powerset, new SetCompare());

        // Now remove the singleton sets
        // CTA: Verify this technique works : Java
        powerset.subList(0, n).clear(); // powerset.RemoveRange(0, n);

        // Save this construction
        memoized.set(n, powerset);

        // Save the compressed versions
        List<String> compressed = new ArrayList<String>();
        for (List<Integer> subset : powerset)
        {
            compressed.add(CompressUniqueIntegerList(subset));
        }
        memoizedCompressed.set(n, compressed);
    }
    private static void ConstructPowerSetWithNoEmptyHelperWithSingletons(int n, int maxCardinality)
    {
        if (memoizedWithSingletons.get(n) != null) return;

        // Construct the powerset and remove the emptyset
        List<List<Integer>> powerset = ConstructRestrictedPowerSet(n, maxCardinality);
        powerset.remove(0);

        // Sort so the smallest sets are first and sets of the same size are compared based on elements.
//        Collections.sort(powerset, new SetCompare());

        // Save this construction
        memoizedWithSingletons.set(n, powerset);

        // Save the compressed versions
        List<String> compressed = new ArrayList<String>();
        for (List<Integer> subset : powerset)
        {
            compressed.add(CompressUniqueIntegerList(subset));
        }
        memoizedCompressedWithSingletons.set(n, compressed);
    }
    public static List<List<Integer>> ConstructPowerSetWithNoEmpty(int n, int maxCardinality)
    {
        ConstructPowerSetWithNoEmptyHelper(n, maxCardinality);

        return memoized.get(n);
    }
    public static List<List<Integer>> ConstructPowerSetWithNoEmpty(int n)
    {
        ConstructPowerSetWithNoEmptyHelperWithSingletons(n, n);

        return memoizedWithSingletons.get(n);
    }

    public static List<String> ConstructPowerSetStringsWithNoEmpty(int n, int maxCardinality)
    {
        ConstructPowerSetWithNoEmptyHelper(n, maxCardinality);

        return memoizedCompressed.get(n);
    }
    // Unchecked, we assume a unique list of integers
    // Takes an integer list and compresses it into a String: { 0 1 2 } -> 012
    // Note, this is only a useful encoding for unit digits (like with powersets above)
    public static String CompressUniqueIntegerList(List<Integer> list)
    {
        String compressed = "";
        
        for (Integer item : list)
        {
            compressed += item;
        }
        
        return compressed;
    }
    // Splits a compressed String (from above) into two parts: the subString we have already processed and the tail we have yet to process
    // 012 -> < 01, 2>
    public static Pair<String, Integer> SplitStringIntoKnownToProcess(String s)
    {       
        return new Pair<String, Integer>(s.substring(0, s.length() - 1), Character.getNumericValue(s.charAt(s.length() - 1)));
    }
    // Decompresses a String of integers directly into an integer list: 012 -> { 0, 1, 2 }
    public static List<Integer> DecompressStringToList(String s)
    {
        List<Integer> intList = new ArrayList<Integer>();
        for (char c : s.toCharArray()) intList.add(Character.getNumericValue(c));
        return intList;
    }
}
