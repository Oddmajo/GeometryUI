package channels.fromUI;

import java.util.ArrayList;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;

public class Diagram 
{
    //The default constructor should accept a vector of ConstructionObject 
    //More constructors that accept integer values for default problems
    public Diagram()
    {
        points = new ArrayList<Point>();
        lineSegments = new ArrayList<Segment>();
        circles = new ArrayList<Circle>();
        arcs = new ArrayList<Arc>();
    }
    public String toString()
    {
        String s = "Points:\n";
        for(int i = 0; i < points.size(); i++)
        {
            s += "\t" + points.get(i).toString() + "\n";
        }
        s += "\nSegments:\n";
        for(int i = 0; i < lineSegments.size(); i++)
        {
            s += "\t" + lineSegments.get(i).toString() + "\n";
        }
        return s;
    }

    //There will need to be a separate ArrayList for each type of component
    //-----------------------------------------------------------------------------------------------------------------------
    private ArrayList<Point> points;
    /**
     * @author Jacob Nash
     * @return The Arraylist<Point> of Points for the Diagram
     */
    public ArrayList<Point> getPoints()
    {
        return points;
    }
    public void addPoint(Point p)
    {
        points.add(p);
    }
    
    public boolean removePoint(Point p)
    {
        return points.remove(p);
    }
    
    public boolean containsPoint(Point p)
    {
        return points.contains(p);
    }
    
    
    //-----------------------------------------------------------------------------------------------------------------------
    private ArrayList<Segment> lineSegments;
    /**
     * @author Jacob Nash
     * @return The ArrayList<Segment> of Segments for the Diagram
     */
    public ArrayList<Segment> getSegments()
    {
        return lineSegments;
    }
    public void addSegment(Segment s)
    {
        lineSegments.add(s);
    }
    public boolean removeSegment(Segment s)
    {
        return lineSegments.remove(s);
    }
    public boolean containsSegment(Segment s)
    {
        return lineSegments.contains(s);
    }
    
    //-----------------------------------------------------------------------------------------------------------------------
    private ArrayList<Circle> circles;
    
    public ArrayList<Circle> getCircles()
    {
        return circles;
    }
    public void addCircle(Circle c)
    {
        circles.add(c);
    }
    public boolean removeCircle(Circle c)
    {
        return circles.remove(c);
    }
    public boolean containsCircle(Circle c)
    {
        return circles.contains(c);
    }
    
    //-----------------------------------------------------------------------------------------------------------------------
    private ArrayList<Arc> arcs;
    
    public ArrayList<Arc> getArcs()
    {
        return arcs;
    }
    public void addArc(Arc a)
    {
        arcs.add(a);
    }
    public boolean removeSegment(Arc a)
    {
        return arcs.remove(a);
    }
    public boolean containsSegment(Arc a)
    {
        return arcs.contains(a);
    }
    //-----------------------------------------------------------------------------------------------------------------------
    //
    // The following section will include all premade diagrams for testing purposes.  
    //      Naming convention currently as follows:
    //      premade_CamelCaseShortDescriptor
    //-----------------------------------------------------------------------------------------------------------------------
    /**
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     * 		Specifically, this will create a large triangle, with a segment connecting the midpoints of two sides, 
     * 		and segments from each midpoint to the opposing corner of the large triangle.
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
    public void premade_Triangles()
    {
    	this.lineSegments.clear();
    	this.points.clear();
    	
    	Point A = new Point("A", 0.0, 0.0);
    	Point B = new Point("B", 4.0, 0.0);
    	Point C = new Point("C", 2.0, 4.0);
    	Point D = new Point("D", 1.0, 2.0);
    	Point E = new Point("E", 3.0, 2.0);
    	
    	this.points.add(A);
    	this.points.add(B);
    	this.points.add(C);
    	this.points.add(D);
    	this.points.add(E);
    	
    	Segment one = new Segment(A,B);
    	Segment two = new Segment(A,C);
    	Segment three = new Segment(B,C);
    	Segment four = new Segment(D,E);
    	Segment five = new Segment(A,E);
    	Segment six = new Segment(D,B);
    	
    	this.lineSegments.add(one);
    	this.lineSegments.add(two);
    	this.lineSegments.add(three);
    	this.lineSegments.add(four);
    	this.lineSegments.add(five);
    	this.lineSegments.add(six);
    	
    }
    
    /** 
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     *      Specifically, this will create a segment with a midpoint.
     *      
     *      o ____ o ____ o
     */
    public void premade_Midpoint()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point A = new Point("A", 0.0, 0.0);
        Point B = new Point("B", 2.0, 0.0);
        Point C = new Point("C", 1.0, 0.0);
        
        this.points.add(A);
        this.points.add(B);
        this.points.add(C);
        
        Segment one = new Segment(A,B);
        
        this.lineSegments.add(one);
    }
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      Specifically this will create two parallel lines with a transversal.
     *      
     *      ________/__________
     *             /          
     *      ______/____________
     *           /
     */
    public void premade_ParallelTransversal()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point A = new Point("A", -2.0, 1.0);
        Point B = new Point("B", 2.0, 1.0);
        
        Point C = new Point("C", -2.0, -1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        Point E = new Point("E", 1.0, 2.0);
        Point F = new Point("F", -1.0, -2.0);
        
        this.points.add(A);
        this.points.add(B);
        this.points.add(C);
        this.points.add(D);
        this.points.add(E);
        this.points.add(F);
        
        Segment top = new Segment(A, B);
        Segment bottom = new Segment(C, D);
        Segment trans = new Segment(E, F);
        
        this.lineSegments.add(top);
        this.lineSegments.add(bottom);
        this.lineSegments.add(trans);
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      Specifically this will create a parallelogram with a single diagonal.
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
    public void premade_Parallelogram()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point A = new Point("A", -1.0, 1.0);
        Point B = new Point("B", 0.0, -1.0);
        Point C = new Point("C", 1.0, 1.0);
        Point D = new Point("D", 2.0, -1.0);
        
        this.points.add(A);
        this.points.add(B);
        this.points.add(C);
        this.points.add(D);
        
        Segment left = new Segment(A, B);
        Segment bottom = new Segment(B, D);
        Segment right = new Segment(D, C);
        Segment top = new Segment(C, A);
        Segment diagonal = new Segment(C, B);
        
        this.lineSegments.add(left);
        this.lineSegments.add(bottom);
        this.lineSegments.add(right);
        this.lineSegments.add(top);
        this.lineSegments.add(diagonal);
        
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *      \
     *       \
     *        \
     *   ______\______
     */
    public void premade_SegmentBisector()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        
        Point c = new Point("C", 1, 2);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        this.addPoint(a);
        this.addPoint(b);
        this.addPoint(c);
        this.addPoint(m);
        
        this.addSegment(ab);
        this.addSegment(cm);
        
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
    public void premade_ThroughSegmentBisector()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 1, 2);
        Point d = new Point("D", 3, -2);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        
        this.addPoint(a);
        this.addPoint(b);
        this.addPoint(c);
        this.addPoint(d);
        
        this.addSegment(ab);
        this.addSegment(cd);
        
    }
    
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|______
     */
    public void premade_PerpendicularBisector()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        this.addPoint(a);
        this.addPoint(b);
        this.addPoint(c);
        this.addPoint(m);
        
        this.addSegment(ab);
        this.addSegment(cm);
        
    }
    /**
     * This method will purge the diagram of any components, then create a default diagram for testing purposes.
     *      
     *         |
     *         |
     *         |
     *   ______|
     */
    public void premade_Perpendicular()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, 0);
        
        Segment ab = new Segment(a, c);
        Segment cm = new Segment(c, m);
        
        this.addPoint(a);
        this.addPoint(c);
        this.addPoint(m);
        
        this.addSegment(ab);
        this.addSegment(cm);
        
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
    public void premade_ThroughPerpendicularBisector()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 4, 0);
        Point c = new Point("C", 2, 1);
        Point m = new Point("M", 2, -1);
        
        Segment ab = new Segment(a, b);
        Segment cm = new Segment(c, m);
        
        this.addPoint(a);
        this.addPoint(b);
        this.addPoint(c);
        this.addPoint(m);
        
        this.addSegment(ab);
        this.addSegment(cm);
        
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
    public void premade_TransitiveParallels()
    {
        this.lineSegments.clear();
        this.points.clear();
        
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        
        Point c = new Point("C", 0, 2);
        Point d = new Point("D", 2, 2);
        
        Point e = new Point("E", 0, 4);
        Point f = new Point("F", 2, 4);
        
        Segment ab = new Segment(a, b);
        Segment cd = new Segment(c, d);
        Segment ef = new Segment(e, f);
        
        this.addPoint(a);
        this.addPoint(b);
        this.addPoint(c);
        this.addPoint(d);
        this.addPoint(e);
        this.addPoint(f);
        
        this.addSegment(ab);
        this.addSegment(cd);
        this.addSegment(ef);
        
    }
}
