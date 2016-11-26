package mainNonUI;

import backend.utilities.test.TestManager;
import channels.fromUI.Diagram;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        //TestManager managerBob = new TestManager();
        
        //long timeStart = System.nanoTime();
        //managerBob.run();
        //long timeEnd = System.nanoTime();
        
        //System.out.println("Run time: " + (timeEnd - timeStart)/1000000000 + "seconds");
        
        Diagram test = new Diagram();
        test.premade_Triangles();
        System.out.println(test.toString());
    }
    
    public static void receiveDiagram(Diagram d)
    {
        //Do stuff with the Diagram
        //Currently there is an 
        //      Arraylist<Segment> of Segments
        //      Arraylist<Point> of Points
        System.out.println(d.toString());
    }

}
