package backendTest.utilitiesTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.MaximalSegments;
import backend.utilities.logger.LoggerFactory;

public class MaximalSegmentsTest
{

    @Test
    public void add_Maximal_Segments_test() throws IOException
    {
        System.out.print("MaximalSegments add_Maximal_Segments_Test...");
        
        LoggerFactory.initialize();

        // create MaximalSegments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        MaximalSegment ms1 = new MaximalSegment(s1);
        
        Point c = new Point("C", 3, 8);
        Point d = new Point("D", 8, 1);
        Segment s2 = new Segment(c,d);
        MaximalSegment ms2 = new MaximalSegment(s2);
        
        Point e = new Point("E", 4, 0);
        Point f = new Point("F", 9, 0);
        Segment s3 = new Segment(e,f);
        MaximalSegment ms3 = new MaximalSegment(s3);
        
        // DUPLICATE, SHOULD NOT BE ADDED
        Point g = new Point("G", 4, 0);
        Point h = new Point("H", 9, 0);
        Segment s4 = new Segment(g,h);
        MaximalSegment ms4 = new MaximalSegment(s4);
        
        // Create a set for set addition check
        HashSet<MaximalSegment> maxSegList = new HashSet<>();
        maxSegList.add(ms2);
        maxSegList.add(ms3);
        maxSegList.add(ms1); // should not be added, duplicate
        
        // Create MaximalSegments object and add stuff to it
        MaximalSegments maximalSegments = new MaximalSegments();
        
        // add single maximal segment
        assertTrue(maximalSegments.addMaximalSegment(ms1));
        
        // add multiple maximal segments
        // should return false, will only add 2 of the 3 maximal segments in the set
        // because one is a duplicate
        assertFalse(maximalSegments.addMaximalSegments(maxSegList));
        
        // try to add duplicate, shouldn't be added
        assertFalse(maximalSegments.addMaximalSegment(ms4));
        
        LoggerFactory.close();
        
        // assert that there are 3 maximal segment objects
        if (maximalSegments.getMaximalSegments().size() == 3)
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
    public void add_subsements_Test()
    {
        System.out.print("MaximalSegments add_subsegments_Test...");

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
        
        // create set of subsegments
        HashSet<Segment> subSegs = new HashSet<>();
        subSegs.add(sub1);
        subSegs.add(sub2);
        subSegs.add(sub3);
        subSegs.add(sub4);
        
        // create MaximalSegments object
        MaximalSegments maximalSegments = new MaximalSegments();
        maximalSegments.addMaximalSegment(ms1);
        
        // add subsegments
        assertTrue(maximalSegments.addSubsegment(sub1));
        assertFalse(maximalSegments.addSubsegments(subSegs));
        assertFalse(maximalSegments.addSubsegment(sub5));
        
        // get maximalsegment object
        MaximalSegment max = (MaximalSegment) maximalSegments.getMaximalSegments().toArray()[0];
        
        if (max.getSubsegments().size() == 3)
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
    public void get_maximal_segment_Test()
    {
        System.out.print("MaximalSegments get_maximal_segment_Test...");

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
        
        // create set of subsegments
        HashSet<Segment> subSegs = new HashSet<>();
        subSegs.add(sub1);
        subSegs.add(sub2);
        subSegs.add(sub3);
        subSegs.add(sub4);
        
        // create MaximalSegments object
        MaximalSegments maximalSegments = new MaximalSegments();
        maximalSegments.addMaximalSegment(ms1);
        
        // add subsegments
        assertTrue(maximalSegments.addSubsegment(sub1));
        assertFalse(maximalSegments.addSubsegments(subSegs));
        assertFalse(maximalSegments.addSubsegment(sub5));
        
        // get maximal segment of a subsegment
        Segment newSub = new Segment(new Point("10", 0, 0), new Point("11", 2, 0));
        Segment returnedMaxSeg = maximalSegments.getMaximalSegment(newSub);
        
        if (returnedMaxSeg.structurallyEquals(ms1.getMaximalSegment()))
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
