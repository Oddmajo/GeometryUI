package backendTest.astTest.figure.components;

import org.junit.Test;

import backend.ast.figure.components.EquationSegment;
import backend.ast.figure.components.Point;

public class EquationSegmentTest
{
    
    /**
     * Method to compare two double with the given accuracy
     * @param accuracy      accuracy used to compare the doubles
     * @param d1            first double to compare
     * @param d2            second double to compare
     * @return true if the two double are within the given accuracy of each other
     */
    public static boolean doubleCompare(double accuracy, double d1, double d2)
    {
        if (Math.abs(d1 - d2) < accuracy) { return true; }
        return false;
    }

    /**
     * Tests the generateRandomPoint() method of the EquationSegment class
     * generates 10 random points
     * asserts the x and y values are with the bounds of the segment
     */
    @Test public void simpleEquationSegment_SmallPointGenerationTest()
    {
        System.out.print("running EquationSegment_SmallPointGenerationTest...");
        
        //create the segment
        Point point1 = new Point("point1", 1, 3);
        Point point2 = new Point("point2", 7, 8);
        
        EquationSegment eqSegment = new EquationSegment(point2, point1);
        
//        System.out.println("eqSegment point1: " + eqSegment.getPoint1());
//        System.out.println("eqSegment point2: " + eqSegment.getPoint2());
//        System.out.println("eqSegment slope: " + eqSegment.slope());
//        System.out.println("eqSegment constant: " + eqSegment.getConstant());
        
        // generate 10 points on the segment
        for (int i = 0; i < 10; i++)
        {
            Point genPoint = eqSegment.getRandomPoint();
            //System.out.println("Point " + i + ": " + genPoint);
            assert (genPoint.getX() >= 1 && genPoint.getX() <= 7);
            assert (genPoint.getY() >= 3 && genPoint.getY() <= 8);
            assert (doubleCompare(0.0000000001, genPoint.getX() * ((double)5/6) + ((double)13/6), genPoint.getY()));
        }
        System.out.println("Done\n");
    }
    
    /**
     * Tests the generateRandomPoint() method of the EquationSegment class
     * generates 100000000 random points
     * asserts the x and y values are with the bounds of the segment
     */
    @Test public void simpleEquationSegment_LargePointGenerationTest()
    {
        System.out.print("running EquationSegment_LargePointGenerationTest...");
        
        //create the segment
        Point point1 = new Point("point1", 1, 3);
        Point point2 = new Point("point2", 7, 8);
        
        EquationSegment eqSegment = new EquationSegment(point2, point1);
        
//        System.out.println("eqSegment point1: " + eqSegment.getPoint1());
//        System.out.println("eqSegment point2: " + eqSegment.getPoint2());
//        System.out.println("eqSegment slope: " + eqSegment.slope());
//        System.out.println("eqSegment constant: " + eqSegment.getConstant());
        
        // generate 100000000 points on the segment
        for (int i = 0; i < 100000000; i++)
        {
            Point genPoint = eqSegment.getRandomPoint();
            if (!(genPoint.getY() >= 3.0 && genPoint.getY() <= 8.0))
            {
                System.out.println("\nPoint " + i + ": " + genPoint);
                System.out.println(genPoint.getX() * ((double)5/6) + ((double)13/6));
                System.out.println(genPoint.getY());
                System.out.println(genPoint.getY() >= 3.0);
                System.out.println(genPoint.getY() < 3.0);
                System.out.println(genPoint.getY() <= 8.0);
                System.out.println(doubleCompare(0.00000001, genPoint.getY(), 3.0));
            }
            assert (genPoint.getX() >= 1 && genPoint.getX() <= 7);
            assert (genPoint.getY() >= 3 && genPoint.getY() <= 8);
            assert (doubleCompare(0.0000000001, genPoint.getX() * ((double)5/6) + ((double)13/6), genPoint.getY()));
        }
        System.out.println("Done\n");
    }
    
    @Test public void simpleEquationSegment_VerticalSegmentPointGenerationTest()
    {
        System.out.print("running simpleEquationSegment_VerticalSegmentPointGenerationTest...");
        
        //create the segment
        Point point1 = new Point("point1", 1, 9);
        Point point2 = new Point("point2", 1, 2);
        
        EquationSegment eqSegment = new EquationSegment(point2, point1);
        
//        System.out.println("eqSegment point1: " + eqSegment.getPoint1());
//        System.out.println("eqSegment point2: " + eqSegment.getPoint2());
//        System.out.println("eqSegment slope: " + eqSegment.slope());
//        System.out.println("eqSegment constant: " + eqSegment.getConstant());
        
        // generate 10 points on the segment
        for (int i = 0; i < 10; i++)
        {
            Point genPoint = eqSegment.getRandomPoint();
            //System.out.println("Point " + i + ": " + genPoint);
            assert (genPoint.getX() == 1);
            assert (genPoint.getY() >= 2 && genPoint.getY() <= 9);
        }
        System.out.println("Done\n");
    }

}
