/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package atomicRegions.AtomicRegionCalculator.OrderedPointList;

import java.util.ArrayList;
import concreteAST.Figure.Point;

/**
 * @author Drew W
 *
 */
public class OrderedPointList
{
    
    ArrayList<Point> ordered = null;

    /**
     * Creates the Min-heap array and places the smallest value possible in array position 0.
     */
    public OrderedPointList()
    {
        ordered = new ArrayList<Point>();
    }
    
    /**
     * @return true if the list is empty
     */
    public boolean IsEmpty() { return ordered.isEmpty(); }
    
    /**
     * Inserts an element to the list.
     * @param thatNode  the Point to insert into the list
     */
    private void Insert(Point thatNode)
    {
        // Empty List: add to the beginning.
        if (ordered.isEmpty() )
        {
            ordered.add(thatNode);
            return;
        }
        
        // General Insertion
        for (int i = 0; i < ordered.size(); i++)
        {
            if (Point.LexicographicOrdering(thatNode, ordered.get(i)) <= 0 )
            {
                ordered.add(i, thatNode);
                break;
            }
        }
    }
    
    public void Add(Point pt)
    {
        Insert(pt);
    }
    
    /**
     * Removes the node at the first position: O(log n) due to Heapify
     * @return the node at the first position
     */
    public Point ExtractMin()
    {
        // return null for an empty list
        if (ordered.isEmpty()) return null;
        
        // else return the first position
        Point min = ordered.get(0);
        ordered.remove(0);
        return min;
    }
    
    /**
     * Get the node at the first position
     * @return  the node at the first position
     */
    public Point PeekMin()
    {
        return ordered.get(0);
    }
    
    /**
     * Remove a specific point from the list.
     * @param pt    the point to be removed
     */
    public void Remove(Point pt)
    {
        ordered.remove(pt);
    }
    
    /**
     * For debugging purposes: traverse the list and dump (key, data) pairs
     * @return  the list as a String
     */
    public String ToString()
    {
        String retS = "";
        
        // traverse the array and dump the (key, data) pairs
        for (int i = 0; i < ordered.size(); i++)
        {
            retS += "(" + i + ": " + ordered.get(i) + ") "; 
        }
        
        return retS + "\n";
    }

}
