/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package utilities.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Set -based functionality: difference, union, intersection, etc.
 * 
 * @author Chris Alvin
 * @author Drew W
 *
 */
public class SetUtilities
{
	
    // Is set1 \equals set2 (and the input sets are ordered for efficiency)
    public static boolean equalOrderedSets(List<Integer> set1, List<Integer> set2)
    {
        if (set1.size() != set2.size()) return false;

        for (int i = 0; i < set1.size(); i++)
        {
            if (set1.get(i) != set2.get(i)) return false;
        }

        return true;
    }

    
    // Are the sets disjoint? Like the merge part of a merge-sort.
    public static List<Integer> DifferenceIfsubsetOrderedSets(List<Integer> set1, List<Integer> set2)
    {
        if (equalOrderedSets(set1, set2)) return null;

        //
        // Acquire the larger set of integer values.
        //
        List<Integer> larger = null;
        List<Integer> smaller = null;

        if (set1.size() < set2.size())
        {
            larger = set2;
            smaller = set1;
        }
        else
        {
            larger = set1;
            smaller = set2;
        }

        //
        // Find the difference of the larger minus the smaller.
        //
        List<Integer> diff = new ArrayList<Integer>();

        int s = 0;
        int ell = 0;
        for (int d = 0; s < smaller.size() && d < larger.size(); d++)
        {
            if (smaller.get(s) < larger.get(ell))
            {
                return null;
            }
            else if (larger.get(ell) < smaller.get(s))
            {
                diff.add(larger.get(ell));
                ell++;
            }
            else if (smaller.get(s) == larger.get(ell))
            {
                s++;
                ell++;
            }
        }

        if (s < smaller.size()) return null;

        // Pick up the rest of the larger set.
        for ( ; ell < larger.size(); ell++)
        {
            diff.add(larger.get(ell));
        }

        return diff;
    }
    


    // Are the sets disjoint? Like the merge part of a merge-sort.
    
    public static List<Integer> UnionIfDisjointOrderedSets(List<Integer> set1, List<Integer> set2)
    {
        if (equalOrderedSets(set1, set2)) return null;

        List<Integer> union = new ArrayList<Integer>();

        int s1 = 0;
        int s2 = 0;
        for (int u = 0; s1 < set1.size() && s2 < set2.size() && u < set1.size() + set2.size(); u++)
        {
            if (set1.get(s1) < set2.get(s2))
            {
                union.add(set1.get(s1));
                s1++;
            }
            else if (set2.get(s2) < set1.get(s1))
            {
                union.add(set2.get(s2));
                s2++;
            }
            else if (set1.get(s1) == set2.get(s2)) return null;
        }

        //
        // Pick up remainder of values from one of the lists.
        //
        for ( ; s1 < set1.size(); s1++)
        {
            union.add(set1.get(s1));
        }

        for ( ; s2 < set2.size(); s2++)
        {
            union.add(set2.get(s2));
        }

        return union;
    }
    
    
    
    public static List<Integer> ComplementList(List<Integer> theList, int size)
    {
        // Ensure smallest to largest ordering.
    	Collections.sort(theList);

        // Eventual complementary list.
        List<Integer> complement = new ArrayList<Integer>();

        int index, ell;
        for (index = 0, ell = 0; index < size && ell < theList.size(); index++)
        {
            if (index != theList.get(ell))
            {
                complement.add(index);
            }
            else ell++;
        }

        // Picking up remaining values up to size
        for (; index < size; index++)
        {
            complement.add(index);
        }

        return complement;
    }
    
    
    // Is smaller \subseteq larger
    public static <T> boolean subset(List<T> larger, List<T> smaller)
    {
        for (T o : smaller)
        {
            if (!larger.contains(o)) return false;
        }

        return true;
    }

    // Is the list a subset of any of the sets : the list of lists?
    public static <T> boolean ListHassubsetOfSet(List<List<T>> sets, List<T> theSet)
    {
        // Do not consider a new subset which contains an existent polygon.
        for(List<T> set : sets)
        {
            if (subset(theSet, set)) return true;
        }

        return false;
    }

    // Is set1 \equals set2
    public static <T> boolean equalSets(List<T> set1, List<T> set2)
    {
        if (set1.size() != set2.size()) return false;

        return subset(set1, set2);      // redundant since we checked same size && subset<T>(set2, set1);
    }



    // set1 \cap set2
    public static <T> List<T> Intersection(List<T> set1, List<T> set2)
    {
        List<T> inter = new ArrayList<T>();

        for (T t : set1)
        {
            if (set2.contains(t)) inter.add(t);
        }

        return inter;
    }
}
