package mainNonUI;

import backend.utilities.test.TestManager;

public class Main
{

    public static void main(String[] args) throws Exception
    {

        TestManager managerBob = new TestManager();
        
        long timeStart = System.nanoTime();
        managerBob.run();
        long timeEnd = System.nanoTime();
        
        System.out.println("Run time: " + (timeEnd - timeStart)/1000000000 + "seconds");
    }

}
