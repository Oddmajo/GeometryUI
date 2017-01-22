package backend.ast.figure;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import com.google.common.graph.*;

import backend.FigureWindow;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.utilities.exception.ExceptionHandler;

//
// n-ary tree of shapes
//
//  The shape hierarchy defines a well-ordering of shapes where, for shapes A, B, C
//    A is completely contained in B
//    AND there does not exist a shape C in which A is in C and C is in B.
//
//  The shape hierarchy is a k-ary tree where the "figure" is the root;
//  To mimic this structure, we use a graph (with reversed, directed edges).
//      The outer, facade root rectangle points to the root shapes and, in general,
//      a shape points to its contained shapes
//
//
public class ShapeHierarchy
{
    // The Guava version of a graph
    protected MutableGraph<Shape> graph;

    // The root shape contains ALL other shapes
    protected final Shape _root;

    private static Rectangle buildDefaultFigureRectangle()
    {
        FigureWindow window = FigureWindow.getWindow();

        Point bottomLeft = new Point("Default Bottom Left", window.getLowerX(), window.getLowerY());
        Point topLeft = new Point("Default Top Left", window.getLowerX(), window.getUpperY());
        Point bottomRight = new Point("Default Bottom Right", window.getUpperX(), window.getLowerY());
        Point topRight = new Point("Default Top Right", window.getUpperX(), window.getUpperY());

        return new Rectangle(Quadrilateral.MakeQuadrilateral(bottomLeft, topLeft, topRight, bottomRight));
    }

    public ShapeHierarchy()
    {
        // Build the graph based on the Guava interface: http://google.github.io/guava/releases/20.0/api/docs/
        graph = GraphBuilder.directed().allowsSelfLoops(false).build();

        // Create the outermost root shape
        _root = buildDefaultFigureRectangle();

        // Add this node to the graph
        graph.addNode(_root);
    }

    /*
     * @return the set of root shapes in the overall figure (neighbors of the 'outer' root rectangle)
     */
    public Set<Shape> getRootShapes()
    {
        return graph.adjacentNodes(_root);
    }

    /*
     * BFS to acquire all sinks; start from the root
     */
    public Set<Shape> getSinks() { return getLeaves(); }
    public Set<Shape> getLeaves()
    {
        Set<Shape> sinks = new HashSet<Shape>();
        Queue<Shape> q = new ArrayDeque<Shape>();

        // Check for an empty hierarchy with only the 'outer' root (this should not happen).
        if (graph.nodes().size() <= 1) return sinks;

        q.add(_root);
        while (!q.isEmpty())
        {
            Shape s = q.remove();

            Set<Shape> neighbors = graph.adjacentNodes(s);

            // No neighbors, add to the set of sink nodes
            if (neighbors.isEmpty()) sinks.add(s);
            else q.addAll(neighbors);
        }

        return sinks;
    }

    /*
     * Determine where to place this shape and update the associated links in the graph / shape hierarchy
     * @param s a shape to be added to the hierarchy
     * @throws when the given shape is not contained with the default root rectangle
     */
    public void add(Shape s) throws IllegalArgumentException
    {
        // CTA: Ensure this contains() method refers to the proper implementation
        if (_root.contains(s))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Shape " + s + " not in the default root rectangle"));
        }

        addHelper(_root, s);
    }

    private void addHelper(Shape node, Shape s)
    {
        // If we have reached this point, node.contains(s) is true

        // Check against all neighbor (contained)s regions
        throw new IllegalArgumentException();

    }

    /*
     * Acquire all shapes that are directly contained by the given shape.
     * Throws an exception if this shape is not in the Shape Hierarchy
     */
    public Set<Shape> getDirectlyContainedShapes(Shape s) throws IllegalArgumentException
    {
        return graph.adjacentNodes(s);        
    }

    /*
     * Acquire all descendant shapes of this shape. Tackle via BFS.
     * Throws an exception if this shape is not in the Shape Hierarchy
     */
    public Set<Shape> getContainedShapes(Shape s) throws IllegalArgumentException
    {
        if (s == null) ExceptionHandler.throwException(new NullPointerException());

        Set<Shape> descendants = new HashSet<Shape>();
        Queue<Shape> q = new ArrayDeque<Shape>();

        // Prime the pump with this node
        Set<Shape> neighbors = graph.adjacentNodes(s);
        q.addAll(neighbors);

        while (!q.isEmpty())
        {
            // Pop from the queue
            Shape r = q.remove();

            // This node is a descendant
            descendants.add(r);

            // Queue all descendant's neighbors
            q.addAll(graph.adjacentNodes(r));
        }

        return descendants;
    }

    /*
     * Is child a descendant of parent in the hierarchy?
     * Throws an exception if either shape is not in the Shape Hierarchy
     */
    public boolean isDescendant(Shape parent, Shape child) throws IllegalArgumentException
    {
        if (parent == null || child == null)
        {
            ExceptionHandler.throwException(new NullPointerException());
        }

        // Collect all descendants; verify child is in the set        
        return getContainedShapes(parent).contains(child);
    }

    /*
     * Is @child a child (direct descendant) of parent in the hierarchy?
     * Throws an exception if either shape is not in the Shape Hierarchy
     */
    public boolean isChild(Shape parent, Shape child) throws IllegalArgumentException
    {
        if (parent == null || child == null)
        {
            ExceptionHandler.throwException(new NullPointerException());
        }

        // Collect all descendants; verify child is in the set        
        return getDirectlyContainedShapes(parent).contains(child);
    }

    /*
     * Is @parent a parent (direct parent) of child in the hierarchy?
     * Throws an exception if either shape is not in the Shape Hierarchy
     */
    public boolean isParent(Shape child, Shape parent) throws IllegalArgumentException
    {
        return isChild(parent, child);
    }
}