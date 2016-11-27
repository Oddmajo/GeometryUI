package channels.fromUI;

import java.util.ArrayList;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class Diagram 
{
    //The default constructor should accept a vector of ConstructionObject 
    //More constructors that accept integer values for default problems
    public Diagram()
    {
        points = new ArrayList<Point>();
        lineSegments = new ArrayList<Segment>();
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
    /**
     * This method will purge the diagram of any components, and then create a default diagram for testing purposes.
     * 		Specifically, this will create a large triangle, with a segment connecting the midpoints of two sides, 
     * 		and segments from each midpoint to the opposing corner of the large triangle.
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
    
    
    
}
