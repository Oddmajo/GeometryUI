/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package atoms.calculator;

import ast.figure.components.Point;

/**
 * A class to store isolated Points.
 * @author Drew W
 *
 */
public class IsolatedPoint extends Primitive
{
    // Variables
    private Point thePoint;
    
    /**
     * Constructor
     */
    public IsolatedPoint()
    {
        thePoint = null;
    }
    
    public IsolatedPoint(Point p)
    {
        thePoint = p;
    }
    
    /**
     * Sets the Point 
     * @param set
     */
    public void setPoint(Point set)
    {
        thePoint = set;
    }
    
    /**
     * Get the Point
     * @return  the Point
     */
    public Point getPoint()
    {
        return thePoint;
    }
    
    /* 
     * Return the IsolatedPoint as a String
     */
    public String toString()
    {
        return "Point { " + thePoint.toString() + " }";
    }

}
