package mainNonUI;

import backend.utilities.logger.LoggerFactory;
import backend.utilities.test.TestManager;
import channels.fromUI.Diagram;

public class Main
{
    public static void main(String[] args)
    {
        //Notion of receiving UI stuff
        //default, hardcoded Diagram for UI-less testing

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
        // TODO Auto-generated method stub
        
    }
}
