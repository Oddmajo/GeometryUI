/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.calculator.lexicographicPoints;

import backend.ast.figure.components.Point;
import java.util.ArrayList;

/**
 * @author Drew W
 *
 */
public class LexicographicPoints
{
    
    ArrayList<Point> ordered = null;

    /**
     * Creates the Min-heap array and places the smallest value possible in array position 0.
     */
    public LexicographicPoints()
    {
        ordered = new ArrayList<Point>();
    }
    
    /**
     * @return true if the list is empty
     */
    public boolean isEmpty() { return ordered.isEmpty(); }
    
    /**
     * Inserts an element to the list.
     * @param thatNode  the Point to insert into the list
     */
    private void insert(Point thatNode)
    {

        boolean added = false; // Drew added this
        
        // Empty List: add to the beginning.
        if (ordered.isEmpty())
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
                added = true;
                break;
            }
        }
        // Add node to the end of the heap
        if (added == false)
        {
            ordered.add(thatNode);
        }
    }
    
    public void add(Point pt)
    {
        insert(pt);
    }
    
    /**
     * Removes the node at the first position: O(log n) due to Heapify
     * @return the node at the first position
     */
    public Point extractMin()
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
    public Point peekMin()
    {
        return ordered.get(0);
    }
    
    /**
     * Return the node at the given index
     * @param index
     * @return
     */
    public Point get(int index)
    {
        return ordered.get(index);
    }
    
    /**
     * Return the size of the ordered list
     * @return
     */
    public int size()
    {
        return ordered.size();
    }
    
    /**
     * Get the node at the second position
     * @return  the node at the second position
     */
    public Point peekNext()
    {
        return ordered.get(1);
    }
    
    /**
     * Remove a specific point from the list.
     * @param pt    the point to be removed
     */
    public void remove(Point pt)
    {
        ordered.remove(pt);
    }
    
    /**
     * For debugging purposes: traverse the list and dump (key, data) pairs
     * @return  the list as a String
     */
    public String toString()
    {
        String retS = "";
        
        // traverse the array and dump the (key, data) pairs
        for (int i = 0; i < ordered.size(); i++)
        {
            retS += "(" + i + ": " + ordered.get(i) + ") "; 
        }
        
        return retS;
    }

}
