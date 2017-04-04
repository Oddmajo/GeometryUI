package backendTest.astTest.figure.components;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class MaximalSegmentTest
{

    /**
     *  C       D
     *  A-------B      E--------------F
     */
    @Test
    public void equals_Test()
    {
        System.out.print("MaximalSegment equals_Test...");

        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        MaximalSegment ms1 = new MaximalSegment(s1);
        
        Point c = new Point("C", 0, 0);
        Point d = new Point("D", 2, 0);
        Segment s2 = new Segment(c,d);
        MaximalSegment ms2 = new MaximalSegment(s2);
        
        Point e = new Point("E", 4, 0);
        Point f = new Point("F", 9, 0);
        Segment s3 = new Segment(e,f);
        MaximalSegment ms3 = new MaximalSegment(s3);
                
        if (ms1.equals(ms2) && !ms1.equals(ms3))
        {
            System.out.print("Passed\n");
            assertTrue(ms1.equals(ms2));
        }
        else
        {
            System.out.print("Failed\n");
            assertTrue(ms1.equals(ms2));
        }
    }
    
    @Test
    public void add_subsegment_Test()
    {
        System.out.print("MaximalSegment add_subsegment_Test...");

        // create maximal segment
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 10, 0);
        Segment s1 = new Segment(a,b);
        MaximalSegment ms1 = new MaximalSegment(s1);
        
        // make subsegments
        Segment sub1 = new Segment(new Point("1", 0, 0), new Point("2", 2, 0));
        Segment sub2 = new Segment(new Point("3", 4, 0), new Point("4", 5, 0));
        Segment sub3 = new Segment(new Point("4", 2, 0), new Point("5", 9, 0));
        Segment sub4 = new Segment(new Point("6", 2, 2), new Point("7", 9, 0)); // NOT coinciding, should NOT be added
        Segment sub5 = new Segment(new Point("8", 2, 0), new Point("9", 9, 0)); // duplicate, should NOT be added
        
        // add subsegments        
        if (ms1.addSubsegment(sub1) && ms1.addSubsegment(sub2) && ms1.addSubsegment(sub3)
                && !ms1.addSubsegment(sub4) && !ms1.addSubsegment(sub5))
        {
            System.out.print("Passed\n");
            assertTrue(true);
        }
        else
        {
            System.out.print("Failed\n");
            assertTrue(false);
        }
    }
    
    @Test
    public void contains_Test()
    {
        System.out.print("MaximalSegment contains_Test...");

        // create maximal segment
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 10, 0);
        Segment s1 = new Segment(a,b);
        MaximalSegment ms1 = new MaximalSegment(s1);
        
        // make subsegments
        Segment sub1 = new Segment(new Point("1", 0, 0), new Point("2", 2, 0));
        Segment sub2 = new Segment(new Point("3", 4, 0), new Point("4", 5, 0));
        Segment sub3 = new Segment(new Point("4", 2, 0), new Point("5", 9, 0));
        
        // add subsegments        
        ms1.addSubsegment(sub1);
        ms1.addSubsegment(sub2);
        ms1.addSubsegment(sub3);
        
        // check contains
        Segment checksub1 = new Segment(new Point("a", 0, 0), new Point("b", 2, 0));
        Segment checksub2 = new Segment(new Point("c", 4, 0), new Point("d", 5, 0));
        Segment checksub3 = new Segment(new Point("e", 2, 0), new Point("f", 9, 0));
        Segment checksub4 = new Segment(new Point("e", 4, 0), new Point("f", 9, 0)); // NOT contains
        
        if (ms1.contains(checksub1) && ms1.contains(checksub2) && ms1.contains(checksub3)
                && !ms1.contains(checksub4))
        {
            System.out.print("Passed\n");
            assertTrue(true);
        }
        else
        {
            System.out.print("Failed\n");
            assertTrue(false);
        }
        
    }

}

