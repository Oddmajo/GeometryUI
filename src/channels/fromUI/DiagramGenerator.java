package channels.fromUI;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class DiagramGenerator
{
    /** 
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     *      
     *      o ____ o ____ o
     */
    public static Diagram premade_Midpoint()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 2.0, 0.0);
        Point C = new Point("C", 1.0, 0.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        
        Segment one = new Segment(A,B);
        
        d.addSegment(one);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      ________/__________
     *             /          
     *      ______/____________
     *           /
     */
    public static Diagram premade_ParallelTransversal()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -2.0, 1.0);
        Point B = new Point("B", 2.0, 1.0);
        
        Point C = new Point("C", -2.0, -1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        Point E = new Point("E", 1.0, 2.0);
        Point F = new Point("F", -1.0, -2.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        d.addPoint(E);
        d.addPoint(F);
        
        Segment top = new Segment(A, B);
        Segment bottom = new Segment(C, D);
        Segment trans = new Segment(E, F);
        
        d.addSegment(top);
        d.addSegment(bottom);
        d.addSegment(trans);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      o______________o
     *      \              /\
     *       \            /  \
     *        \          /    \
     *         \        /      \
     *          \      /        \
     *           \    /          \
     *            \  /            \
     *            o\/______________\o
     */
    public static Diagram premade_ParallelogramDiagonal()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", -1.0, 1.0);
        Point B = new Point("B", 0.0, -1.0);
        Point C = new Point("C", 1.0, 1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        
        Segment left = new Segment(A, B);
        Segment bottom = new Segment(B, D);
        Segment right = new Segment(D, C);
        Segment top = new Segment(C, A);
        Segment diagonal = new Segment(C, B);
        
        d.addSegment(left);
        d.addSegment(bottom);
        d.addSegment(right);
        d.addSegment(top);
        d.addSegment(diagonal);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     * 
     *           o
     *          /\
     *         /  \
     *       o/____\o
     *       /\    /\
     *      /   \/   \
     *     /   /  \   \
     *    /  /      \  \
     *   / /          \ \
     * o//______________\\o
     */
    public static Diagram premade_Triangles()
    {
        Diagram d = new Diagram();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 4.0, 0.0);
        Point C = new Point("C", 2.0, 4.0);
        Point D = new Point("D", 1.0, 2.0);
        Point E = new Point("E", 3.0, 2.0);
        
        d.addPoint(A);
        d.addPoint(B);
        d.addPoint(C);
        d.addPoint(D);
        d.addPoint(E);
        
        Segment one = new Segment(A,B);
        Segment two = new Segment(A,C);
        Segment three = new Segment(B,C);
        Segment four = new Segment(D,E);
        Segment five = new Segment(A,E);
        Segment six = new Segment(D,B);
        
        d.addSegment(one);
        d.addSegment(two);
        d.addSegment(three);
        d.addSegment(four);
        d.addSegment(five);
        d.addSegment(six);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      \
     *       \
     *        \
     *   ______\______
     */
    public static Diagram premade_SegmentBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        
        Point c = new Point("C", 1, 2);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      \
     *       \
     *        \
     *   ______\______
     *          \
     *           \ 
     *            \
     */
    public static Diagram premade_ThroughSegmentBisector()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 1, 2);
        Point d = new Point("D", 3, -2);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(d);
        
        diagram.addSegment(ab);
        diagram.addSegment(cd);
        
        return diagram;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|______
     */
    public static Diagram premade_PerpendicularBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|
     */
    public static Diagram premade_Perpendicular()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point c = new Point("C", 1, 1);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, c);
        Segment cm = new Segment(c, m);
        
        d.addPoint(a);
        d.addPoint(c);
        d.addPoint(m);
        
        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|______
     *         |
     *         |
     *         |
     *         |
     */
    public static Diagram premade_ThroughPerpendicularBisector()
    {
        Diagram d = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, -1);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        d.addPoint(a);
        d.addPoint(b);
        d.addPoint(c);
        d.addPoint(m);
        
        d.addSegment(ab);
        d.addSegment(cm);
        
        return d;
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *    _________________
     *    
     *    _________________
     *    
     *    _________________
     */
    public static Diagram premade_TransitiveParallels()
    {
        Diagram diagram = new Diagram();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        
        Point c = new Point("C", 0, 2);
        Point d = new Point("D", 2, 2);
        
        Point e = new Point("E", 0, 4);
        Point f = new Point("F", 2, 4);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        Segment ef = new Segment(e, f);
        
        diagram.addPoint(a);
        diagram.addPoint(b);
        diagram.addPoint(c);
        diagram.addPoint(d);
        diagram.addPoint(e);
        diagram.addPoint(f);
        
        diagram.addSegment(ab);
        diagram.addSegment(cd);
        diagram.addSegment(ef);
        
        return diagram;
    }
}
