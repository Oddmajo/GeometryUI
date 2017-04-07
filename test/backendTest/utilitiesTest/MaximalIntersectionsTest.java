package backendTest.utilitiesTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MaximalIntersection;
import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.MaximalIntersections;
import backend.utilities.MaximalSegments;
import backend.utilities.logger.LoggerFactory;

public class MaximalIntersectionsTest
{

    @Test
    public void add_Maximal_Intersections_Test() throws IOException
    {
        LoggerFactory.initialize();
        
        System.out.print("MaximalIntersection add_Maximal_Intersections_Test...");
        
        // create maximal Intersections
        // MaximalIntersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // MaximalIntersection 2
        Point e = new Point("E", 5, 9);
        Point f = new Point("F", 10, 9);
        Segment s3 = new Segment(e,f);
        
        Point g = new Point("G", 7, 4);
        Point h = new Point("H", 7, 12);
        Segment s4 = new Segment(g,h);
        Point intersect_p = new Point ("intersect i2", 7, 9);
        Intersection i2 = new Intersection(intersect_p, s3, s4);
        MaximalIntersection mi2 = new MaximalIntersection(i2);
        
        // MaximalIntersection 3
        Point i = new Point("I", 1, 1);
        Point j = new Point("J", 4, 4);
        Segment s5 = new Segment(i,j);
        
        Point k = new Point("C", 4, 1);
        Point l = new Point("D", 1, 4);
        Segment s6 = new Segment(k,l);
        Point intersect_p2 = new Point ("intersect i3", 2, 2);
        Intersection i3 = new Intersection(intersect_p2, s5, s6);
        MaximalIntersection mi3 = new MaximalIntersection(i3);
        
        // set up MaximalSegments
        MaximalSegments maximalSegments = MaximalSegments.getInstance();
        
        MaximalSegment ms1 = new MaximalSegment(s1);
        MaximalSegment ms2 = new MaximalSegment(s2);
        MaximalSegment ms3 = new MaximalSegment(s3);
        MaximalSegment ms4 = new MaximalSegment(s4);
        MaximalSegment ms5 = new MaximalSegment(s5);
        MaximalSegment ms6 = new MaximalSegment(s6);
        maximalSegments.addMaximalSegment(ms1);
        maximalSegments.addMaximalSegment(ms2);
        maximalSegments.addMaximalSegment(ms3);
        maximalSegments.addMaximalSegment(ms4);
        maximalSegments.addMaximalSegment(ms5);
        maximalSegments.addMaximalSegment(ms6);
        
        maximalSegments.addSubsegment(s1);
        maximalSegments.addSubsegment(s2);
        maximalSegments.addSubsegment(s3);
        maximalSegments.addSubsegment(s4);
        maximalSegments.addSubsegment(s5);
        maximalSegments.addSubsegment(s6);
        
        // Make Set of MaximalIntersections for addition
        HashSet<MaximalIntersection> max_intersect_set = new HashSet<>();
        max_intersect_set.add(mi1);
        max_intersect_set.add(mi2);
        max_intersect_set.add(mi3);
        
        // make subintersections (will fail in addition)
        // subintersection 1 (subintersection)
        Point p1 = new Point("A", 0, 0);
        Point p2 = new Point("B", 1, 0);
        Segment seg1 = new Segment(p1,p2);
        
        Point p3 = new Point("C", 1, 0);
        Point p4 = new Point("D", 1, 2);
        Segment seg2 = new Segment(p3,p4);
        Intersection sub1 = new Intersection(p2, seg1, seg2);
        MaximalIntersection max_sub1 = new MaximalIntersection(sub1);
        
        // subintersection 2 (duplicate)
        Point p5 = new Point("E", 0, 0);
        Point p6 = new Point("F", 2, 0);
        Segment seg3 = new Segment(p5,p6);
        
        Point p7 = new Point("G", 1, 0);
        Point p8 = new Point("H", 1, 2);
        Segment seg4 = new Segment(p7,p8);
        Intersection sub2 = new Intersection(p7, seg3, seg4);
        MaximalIntersection max_dup1 = new MaximalIntersection(sub2);
        
        maximalSegments.addSubsegment(seg1);
        maximalSegments.addSubsegment(seg2);
        maximalSegments.addSubsegment(seg3);
        maximalSegments.addSubsegment(seg4);
        
        // get the instance of MaximalIntersections
        MaximalIntersections maximalIntersections = MaximalIntersections.getInstance();
        
        // add all MaximalIntersection object
        assertTrue(maximalIntersections.addMaximalIntersection(mi1));
        
        // one is a duplicate and is not added (which is why it returns false)
        // the other two are added (total of three)
        assertFalse(maximalIntersections.addMaximalIntersections(max_intersect_set)); 
        assertTrue(maximalIntersections.getMaximalIntersections().size() == 3);
        
        // assertFalse for the subintersections
        assertFalse(maximalIntersections.addMaximalIntersection(max_sub1));
        assertFalse(maximalIntersections.addMaximalIntersection(max_dup1));
        
        if (maximalIntersections.getMaximalIntersections().size() == 3)
        {
            System.out.print("Passed\n");
            assertTrue(true);
        }
        else
        {
            System.out.print("Failed\n");
            assertTrue(false);
        }
        
        LoggerFactory.close();
        MaximalIntersections.clear();
    }
    
    @Test
    public void get_Maximal_Intersection_Test() throws IOException
    {
        LoggerFactory.initialize();
        
        System.out.print("MaximalIntersections get_Maximal_Intersection_Test...");
        
        // create maximal Intersections
        // MaximalIntersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // set up MaximalSegments
        MaximalSegments maximalSegments = MaximalSegments.getInstance();
        
        MaximalSegment ms1 = new MaximalSegment(s1);
        MaximalSegment ms2 = new MaximalSegment(s2);
        maximalSegments.addMaximalSegment(ms1);
        maximalSegments.addMaximalSegment(ms2);
        
        maximalSegments.addSubsegment(s1);
        maximalSegments.addSubsegment(s2);
        
        // Make Set of MaximalIntersections for addition
        HashSet<MaximalIntersection> max_intersect_set = new HashSet<>();
        max_intersect_set.add(mi1);
        
        // make subintersections (will fail in addition)
        // subintersection 1 (subintersection)
        Point p1 = new Point("A", 0, 0);
        Point p2 = new Point("B", 1, 0);
        Segment seg1 = new Segment(p1,p2);
        
        Point p3 = new Point("C", 1, 0);
        Point p4 = new Point("D", 1, 2);
        Segment seg2 = new Segment(p3,p4);
        Intersection sub1 = new Intersection(p2, seg1, seg2);
        
        // subintersection 2 (duplicate)
        Point p5 = new Point("E", 0, 0);
        Point p6 = new Point("F", 2, 0);
        Segment seg3 = new Segment(p5,p6);
        
        Point p7 = new Point("G", 1, 0);
        Point p8 = new Point("H", 1, 2);
        Segment seg4 = new Segment(p7,p8);
        Intersection sub2 = new Intersection(p7, seg3, seg4);
        
        maximalSegments.addSubsegment(seg1);
        maximalSegments.addSubsegment(seg2);
        maximalSegments.addSubsegment(seg3);
        maximalSegments.addSubsegment(seg4);
        
        // get the instance of MaximalIntersections
        MaximalIntersections maximalIntersections = MaximalIntersections.getInstance();
        
        // add maximal intersections 
        assertTrue(maximalIntersections.addMaximalIntersections(max_intersect_set)); 
        
        // check getMaximalIntersection
        Intersection returnedIntersection = maximalIntersections.getMaximalIntersection(sub1);
        Intersection returnedIntersection2 = maximalIntersections.getMaximalIntersection(sub2);
        
        if (returnedIntersection.structurallyEquals(mi1.getMaximalIntersection()) &&
                returnedIntersection2.structurallyEquals(mi1.getMaximalIntersection()))
        {
            System.out.print("Passed\n");
            assertTrue(true);
        }
        else
        {
            System.out.print("Failed\n");
            assertTrue(false);
        }
        
        
        LoggerFactory.close();
        MaximalIntersections.clear();
    }

}
