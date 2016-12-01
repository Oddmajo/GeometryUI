package mainNonUI;

import java.util.ArrayList;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.logger.LoggerFactory;
import backend.utilities.test.TestManager;
import channels.fromUI.Diagram;

public class Main
{
    public static void main(String[] args)
    {
        long timeStart = System.nanoTime();
        try
        {
            TestManager.run();
        }
        catch (Exception e)
        {
        	LoggerFactory.getLogger(LoggerFactory.EXCEPTION_OUTPUT_ID).write("Test Manager unexpectedly threw an exception.");
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
