package channels.fromUI;

import java.util.ArrayList;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.PointFactory;

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
    //
    //  ALL PREMADE CONSTRUCTORS HAVE BEEN MOVED TO DiagramGenerator.java
    //-----------------------------------------------------------------------------------------------------------------------
    
    
    
    
    
    
    
    
}
