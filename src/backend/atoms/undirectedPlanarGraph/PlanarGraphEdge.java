/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package atoms.undirectedPlanarGraph;

import ast.figure.components.Point;

/**
 * 
 * @author Drew W
 */
public class PlanarGraphEdge
{
    // the Point of the edge
    private Point target;
    public Point getTarget() { return target; }
    
    // the cost of the edge
    private double cost;
    public double getCost() { return cost; }
    
    // the type of the edge
    public EdgeType edgeType;
    
    // the number of connections between two nodes that are ARCS
    public int degree;
    
    // if the edge is a cycle or not
    public boolean isCycle;

    /**
     * Full Constructor
     * @param targ          the target Point
     * @param type          the EdgeType
     * @param cost          the cost
     * @param initDegree    the initial degree of the Point    
     */
    public PlanarGraphEdge(Point targ, EdgeType type, double cost, int initDegree)
    {
        this.target = targ;
        edgeType = type;
        
        this.cost = cost;
        degree = initDegree;
        isCycle = false;
    }
    
    /**
     * For quick construction only
     * @param targ  the target Point
     */
    public PlanarGraphEdge(Point targ)
    {
        this.target = targ;
    }
    
    
    /**
     * Shallow Copy Constructor
     * @param thatEdge
     */
    public PlanarGraphEdge(PlanarGraphEdge thatEdge)
    {
        this.target = thatEdge.target;
        this.edgeType = thatEdge.edgeType;
        this.cost = thatEdge.cost;
        this.degree = thatEdge.degree;
    }
    
    /**
     * Set isCycle to false
     */
    public void clear()
    {
        isCycle = false;
    }
    
    /* 
     * Overrides the Object hashCode() method
     */
    public int hashCode()
    {
        return super.hashCode();
    }
    
    /*
     * Equality is only based on the point in the graph
     */
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof PlanarGraphEdge)
        {
            PlanarGraphEdge thatEdge = (PlanarGraphEdge) obj;
            return this.target == thatEdge.target;
        }
        else
        {
            return false;
        }
    }
    
    /* 
     * Overrides the toString method
     */
    public String toString()
    {
        return target.toString() + "(" + edgeType + ")";
    }
}
