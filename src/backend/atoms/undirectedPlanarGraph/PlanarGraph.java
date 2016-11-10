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
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

/**
 * A class for Planar Graphs
 * @author Drew W
 *
 */
public class PlanarGraph
{

    // variable declarations
    private ArrayList<PlanarGraphNode> nodes;
    public ArrayList<PlanarGraphNode> getNodes() { return nodes; }

    /**
     * Constructor
     */
    public PlanarGraph()
    {
        nodes = new ArrayList<PlanarGraphNode>();
    }

    /**
     * Shallow copy constructor
     * @param thatG     the graph to copy from
     */
    public PlanarGraph(PlanarGraph thatG)
    {
        this();
        
        for (PlanarGraphNode node : thatG.getNodes())
        {
            nodes.add(new PlanarGraphNode(node));
        }
    }

    /**
     * Add a node to the graph
     * @param value     the Point to add
     */
    public void addNode(Point value) // , NodePointType type)
    {
        // make PlanarGraphNode
        PlanarGraphNode node = new PlanarGraphNode(value);
        // Avoid redundant additions.
        if (indexOf(value) != -1) return;

        addNode(node); // , type));
    }

    /**
     * Add a node to the Graph
     * @param node      the Node to add
     */
    private void addNode(PlanarGraphNode node)
    {
        nodes.add(node);
    }

    /**
     * Get the index of the given Point
     * Return -1 if the node is not in the graph
     * @param pt    the given point
     * @return      the index of the point
     */
    public int indexOf(Point pt)
    {
        //return nodes.indexOf(new PlanarGraphNode(pt)); // , NodePointType.REAL));
        PlanarGraphNode node = new PlanarGraphNode(pt);
        for (int i = 0; i < nodes.size(); i++)
        {
            if (node.getPoint().getX() == nodes.get(i).getPoint().getX() &&
                    node.getPoint().getY() == nodes.get(i).getPoint().getY())
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determine the new, updated edge type.
     * @param oldType   the old edge type
     * @param newType   the new edge type
     * @return          the updated edge type
     */
    private EdgeType updateEdge(EdgeType oldType, EdgeType newType)
    {
        if (oldType == EdgeType.REAL_SEGMENT && newType == EdgeType.REAL_SEGMENT)
        {
            return EdgeType.REAL_SEGMENT;
            //throw new ArgumentException("Cannot have two edges defined by a real segment.");
        }

        if (oldType == EdgeType.EXTENDED_SEGMENT || newType == EdgeType.EXTENDED_SEGMENT)
        {
            return EdgeType.EXTENDED_SEGMENT;
//            throw new ArgumentException("Cannot change an edge to / from an extended segment type.");
        }

        if (newType == EdgeType.REAL_DUAL)
        {
            return EdgeType.REAL_DUAL;

//            throw new ArgumentException("Cannot change an edge to be dual.");
        }

        // DUAL + ARC / SEGMENT = DUAL
        if (oldType == EdgeType.REAL_DUAL) return EdgeType.REAL_DUAL;

        // SEGMENT + ARC = DUAL
        if (oldType == EdgeType.REAL_SEGMENT && newType == EdgeType.REAL_ARC) return EdgeType.REAL_DUAL;

        // ARC + SEGMENT = DUAL
        if (oldType == EdgeType.REAL_ARC && newType == EdgeType.REAL_SEGMENT) return EdgeType.REAL_DUAL;

        // ARC + ARC = ARC
        if (oldType == EdgeType.REAL_ARC && newType == EdgeType.REAL_ARC) return EdgeType.REAL_ARC;

        // default should not be reached.
        return EdgeType.REAL_DUAL;
    }

    /**
     * Add an undirected edge to the graph
     * @param from                  the Point the edge is from
     * @param to                    the Point the edge is going to
     * @param cost                  the cost of the edge
     * @param eType                 the edge type
     * @throws ArgumentException    
     */
    public void addUndirectedEdge(Point from, Point to, double cost, EdgeType eType)
    {
        //
        // Are these nodes in the graph?
        //
        int fromNodeIndex = indexOf(from);
        int toNodeIndex = indexOf(to);
        
        if (fromNodeIndex == -1 || toNodeIndex == -1)
        {
            ExceptionHandler.throwException(new ArgumentException("Edge uses undefined nodes: " + from + " " + to));
        }

        //
        // Check if the edge already exists
        //
        PlanarGraphEdge fromToEdge = nodes.get(fromNodeIndex).GetEdge(to);
        if (fromToEdge != null)
        {
            PlanarGraphEdge toFromEdge = nodes.get(toNodeIndex).GetEdge(from);

            fromToEdge.edgeType = updateEdge(fromToEdge.edgeType, eType);
            toFromEdge.edgeType = fromToEdge.edgeType;

            // Increment the degree if it is an arc.
            if (eType == EdgeType.REAL_ARC)
            {
                fromToEdge.degree++;
                toFromEdge.degree++;
            }
        }
        //
        // The edge does not exist.
        //
        else
        {
            nodes.get(fromNodeIndex).addEdge(to, eType, cost, (eType == EdgeType.REAL_ARC ? 1 : 0));
            nodes.get(toNodeIndex).addEdge(from, eType, cost, (eType == EdgeType.REAL_ARC ? 1 : 0));
        }
    }

    /**
     * Return true if the given Point is contained in the graph
     * @param value     the given Point
     * @return          true if the Point is contained in the graph
     */
    public boolean contains(Point value)
    {
        //return nodes.contains(new PlanarGraphNode(value));
        if (indexOf(value) == -1)
        {
            return false;
        }
        return true;
    }

    /**
     * Remove the given Point form the graph
     * @param value     the given Point
     * @return          true if the Point was successfully removed from the graph
     */
    public boolean removeNode(Point value)
    {
        //if (!nodes.remove(new PlanarGraphNode(value))) return false;
        // get the node index
        int index = indexOf(value);
        
        // if index = -1, node is not in the graph
        if (index == -1) return false;
        
        // remove the edge
        nodes.remove(index);
        
        // enumerate through each node in the nodes, removing edges to this node
        for (PlanarGraphNode node : nodes)
        {
            node.removeEdge(value);
        }

        return true;
    }

    /**
     * Remove the given range of Points from the graph
     * @param from      the start of the range
     * @param to        the end of the range
     * @return          true if the range of Points was successfully removed from the graph
     */
    public boolean removeEdge(Point from, Point to)
    {
        // Does this edge exist already?
        int fromNodeIndex = indexOf(from);
        if (fromNodeIndex == -1) return false;
        nodes.get(fromNodeIndex).removeEdge(to);

        int toNodeIndex = indexOf(to);
        if (toNodeIndex == -1) return false;
        nodes.get(toNodeIndex).removeEdge(from);

        return true;
    }

    /**
     * Get the edge between the from and two Points
     * @param from      the from Point
     * @param to        the to Point
     * @return          the edge
     */
    public PlanarGraphEdge getEdge(Point from, Point to)
    {
        // Does this edge exist already?
        int fromNodeIndex = indexOf(from);
        if (fromNodeIndex == -1) return null;

        int toNodeIndex = indexOf(to);
        if (toNodeIndex == -1) return null;

        return nodes.get(fromNodeIndex).GetEdge(to);
    }

    /**
     * Get the edgetype of the edge between the from and two Points
     * @param from      the from Point
     * @param to        the to Point
     * @return          the the Edgetype of the edge
     */
    public EdgeType getEdgeType(Point from, Point to)
    {
        int fromNodeIndex = indexOf(from);
        //System.out.println("getEdgeType: Point from = " + from.toString());
        //System.out.println("getEdgeType: fromNodeIndex = " + fromNodeIndex);

        return nodes.get(fromNodeIndex).GetEdge(to).edgeType;
    }

    /**
     * Mark the edge between the from and to Points a cycle
     * @param from      the from Point
     * @param to        the to Point
     */
    public void markCycleEdge(Point from, Point to)
    {
        int fromNodeIndex = indexOf(from);
        if (fromNodeIndex == -1) return;
        nodes.get(fromNodeIndex).markEdge(to);

        int toNodeIndex = indexOf(to);
        if (toNodeIndex == -1) return;
        nodes.get(toNodeIndex).markEdge(from);
    }

    /**
     * Return true if the edge between the from and to Points has been marked a cycle edge
     * @param from      the from Point
     * @param to        the to Point
     * @return          True if the edge has been marked a cycle edge
     */
    public boolean isCycleEdge(Point from, Point to)
    {
        // Does this edge exist already?
        int fromNodeIndex = indexOf(from);

        if (fromNodeIndex == -1) return false;

        return nodes.get(fromNodeIndex).isCyclicEdge(to);
    }

    /**
     * Unmark any marked nodes and edges
     */
    public void reset()
    {
        for (PlanarGraphNode node : nodes)
        {
            node.Clear();
        }
    }

    /**
     * Return the number of nodes in the graph
     * @return  the number of nodes in the graph
     */
    public int count()
    {
        return nodes.size();
    }

    /* 
     * Return the graph as a string
     */
    public String toString()
    {
        String retS = "";

        for(PlanarGraphNode node : nodes)
        {
            retS += node.toString() + "\n";
        }

        return retS;
    }

}
