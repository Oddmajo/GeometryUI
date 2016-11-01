/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.undirectedPlanarGraph;

import java.util.ArrayList;
import backend.ast.figure.components.Point;

/**
 * For atomic region identification
 * 
 * @author Drew W
 *
 */
public class PlanarGraphNode
{
    // Variable Declarations
    private ArrayList<PlanarGraphEdge> edges;
    public ArrayList<PlanarGraphEdge> getEdges() { return edges; }
    
    private Point thePoint;
    public Point getPoint() { return thePoint; }

    
    /**
     * Constructor
     * @param value     the point to construct the node with
     */
    public PlanarGraphNode(Point value) // , NodePointType t)
    {
        thePoint = value;
        //type = t;
        edges = new ArrayList<PlanarGraphEdge>();
    }

    /**
     * Shallow copy constructor
     * @param thatNode
     */
    public PlanarGraphNode(PlanarGraphNode thatNode)
    {
        thePoint = thatNode.thePoint;
        // type = thatNode.type;
        edges = new ArrayList<PlanarGraphEdge>();

        for (PlanarGraphEdge e : thatNode.getEdges())
        {
            edges.add(new PlanarGraphEdge(e));
        }
    }

    /**
     * Add an edge to the graph
     * @param targ          the Point to add
     * @param type          the EdgeType
     * @param c             the cost
     * @param initDegree    the degree of the Node
     */
    public void addEdge(Point targ, EdgeType type, double c, int initDegree)
    {
        edges.add(new PlanarGraphEdge(targ, type, c, initDegree));
    }

    /**
     * Get the edge of the target Point
     * @param targ      the target Point
     * @return          the edge of the target Point
     */
    public PlanarGraphEdge GetEdge(Point targ)
    {
        int index = edges.indexOf(new PlanarGraphEdge(targ));

        return index == -1 ? null : edges.get(index);
    }

    /**
     * Remove the target Point from the graph
     * @param targetNode    the target Point
     */
    public void removeEdge(Point targetNode)
    {
        edges.remove(new PlanarGraphEdge(targetNode));
    }

    /**
     * Mark the edge of the target Point in the graph
     * @param targetNode    the target Point
     */
    public void markEdge(Point targetNode)
    {
        PlanarGraphEdge edge = GetEdge(targetNode);

        if (edge == null) return;

        edge.isCycle = true;
    }

    /**
     * Return true if the target Point is a cyclic edge
     * @param targetNode    the target Point
     * @return              true if the target Point is a cyclic edge
     */
    public boolean isCyclicEdge(Point targetNode)
    {
        PlanarGraphEdge edge = GetEdge(targetNode);

        if (edge == null) return false;

        return edge.isCycle;
    }

    /**
     * get the degree of the node as an int
     * @return  the degree of the node
     */
    public int nodeDegree()
    {
        return edges.size();
    }

    /**
     * Clear the edges of the graph
     */
    public void Clear()
    {
        for (PlanarGraphEdge e : edges)
        {
            e.clear();
        }
    }

    /**
     * Get the hash code of the graph
     * @return  the hash code of the graph
     */
    public int getHashCode() { return super.hashCode(); }

    /* 
     * Equality is only based on the point in the graph.
     */
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof PlanarGraphNode)
        {
            PlanarGraphNode node = (PlanarGraphNode) obj;
            return this.thePoint.equals(node.thePoint);
        }
        else { return false; }
    }

    /* 
     * Return the graph as a string
     */
    public String toString()
    {
        String retS = "";
        
        retS += "<" + thePoint.toString() + "(" + edges.size() + "): ";
        for (PlanarGraphEdge edge : edges)
        {
            retS += edge.toString() + "\t";
        }
        
        return retS;
    }

}
