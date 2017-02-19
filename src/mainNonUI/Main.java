package mainNonUI;

import java.util.ArrayList;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.precomputer.CoordinatePrecomputer;
import backend.utilities.logger.LoggerFactory;
import backend.utilities.test.TestManager;
import channels.fromUI.Diagram;

public class Main
{
    public static void main(String[] args)
    {
        long timeStart = System.nanoTime();
        
        ArrayList<Circle> c = new ArrayList<Circle>();
        ArrayList<Arc> a = new ArrayList<Arc>();
        ArrayList<Segment> s = new ArrayList<Segment>();
        ArrayList<Point> p = new ArrayList<Point>();
        Diagram test = new Diagram();
        test.premade_Triangles();
        s = test.getSegments();
        p = test.getPoints();
        try
        {
            //TestManager.run();
            CoordinatePrecomputer compute = new CoordinatePrecomputer(c,a,s,p);
        }
        catch (Exception e)
        {
        	//LoggerFactory.getLogger(LoggerFactory.EXCEPTION_OUTPUT_ID).write("Test Manager unexpectedly threw an exception.");
        	e.printStackTrace();
        }
        long timeEnd = System.nanoTime();
        
        System.out.println("Run time: " + (timeEnd - timeStart)/1000000000 + "seconds");
    }

    public static void receiveDiagram(Diagram backendRepresentation)
    {
        //This method is called by the fromUI channel
        //Do stuff with the Diagram Starting here
        //Currently the Diagram has two ArrayLists
        //  ArrayList<Point>
        //  ArrayList<Segment>
        
        System.out.println(backendRepresentation.toString());
        
    }
}
