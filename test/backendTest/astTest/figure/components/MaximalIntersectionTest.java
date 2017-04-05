package backendTest.astTest.figure.components;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MaximalIntersection;
import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.MaximalSegments;

public class MaximalIntersectionTest
{

    /**
     *            D/H
     *             |
     *             |
     *             |
     *  A/E-------C/G--------B/F
     */
    @Test
    public void equals_Test()
    {
        System.out.print("MaximalIntersection equals_Test...");

        // Intersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // Intersection 2
        Point e = new Point("E", 0, 0);
        Point f = new Point("F", 2, 0);
        Segment s3 = new Segment(e,f);
        
        Point g = new Point("G", 1, 0);
        Point h = new Point("H", 1, 2);
        Segment s4 = new Segment(g,h);
        Intersection i2 = new Intersection(g, s3, s4);
        MaximalIntersection mi2 = new MaximalIntersection(i2);
                
        if (mi1.equals(mi2))
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
    
    /**
     *            L
     *            |   
     *            D
     *            |
     *            |
     *            |
     *  A---I----C/K-----J---B
     */
    @Test
    public void not_equals_Test()
    {
        System.out.print("MaximalIntersection not_equals_Test...");

        // Intersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // Intersection 3 (NOT EQUAL)
        Point i = new Point("i", 0.5, 0);
        Point j = new Point("j", 1.5, 0);
        Segment s5 = new Segment(i,j);
        
        Point k = new Point("k", 1, 0);
        Point l = new Point("l", 1, 3);
        Segment s6 = new Segment(k,l);
        Intersection i3 = new Intersection(k, s5, s6);
        MaximalIntersection mi3 = new MaximalIntersection(i3);
                
        if (!mi1.equals(mi3))
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
    public void add_subintersection_Test()
    {
        System.out.print("MaximalIntersection add_subintersection_Test...");
               
        // create maximal Intersection
        // MaximalIntersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // make subintersections
        // subintersection 1
        Point p1 = new Point("A", 0, 0);
        Point p2 = new Point("B", 1, 0);
        Segment seg1 = new Segment(p1,p2);
        
        Point p3 = new Point("C", 1, 0);
        Point p4 = new Point("D", 1, 2);
        Segment seg2 = new Segment(p3,p4);
        Intersection sub1 = new Intersection(p2, seg1, seg2);
        
        // subintersection 2
        Point p5 = new Point("E", 0.5, 0);
        Point p6 = new Point("F", 1.5, 0);
        Segment seg3 = new Segment(p5,p6);
        
        Point p7 = new Point("G", 1, 0);
        Point p8 = new Point("H", 1, 1.5);
        Segment seg4 = new Segment(p7,p8);
        Intersection sub2 = new Intersection(p7, seg3, seg4);
        
        // subintersection 3 (NOT A SUBINTERSECTION)
        Point p9 = new Point("A", 1, 1);
        Point p10 = new Point("B", 1, 0);
        Segment seg5 = new Segment(p9,p10);
        
        Point p11 = new Point("C", 1, 0);
        Point p12 = new Point("D", 1, 2);
        Segment seg6 = new Segment(p11,p12);
        Intersection sub3 = new Intersection(p2, seg5, seg6);
        
        // subintersection 4 (DUPLICATE, SHOULD NOT BE ADDED)
        Point p13 = new Point("E", 0.5, 0);
        Point p14 = new Point("F", 1.5, 0);
        Segment seg7 = new Segment(p13,p14);
        
        Point p15 = new Point("G", 1, 0);
        Point p16 = new Point("H", 1, 1.5);
        Segment seg8 = new Segment(p15,p16);
        Intersection sub4 = new Intersection(p15, seg7, seg8);
        
        // Set up MaximalSegments
        MaximalSegments maximalSegments = new MaximalSegments();
        
        MaximalSegment maxSeg1 = new MaximalSegment(s1);
        MaximalSegment maxSeg2 = new MaximalSegment(s2);
        maximalSegments.addMaximalSegment(maxSeg1);
        maximalSegments.addMaximalSegment(maxSeg2);
        
        maximalSegments.addSubsegment(s1);
        maximalSegments.addSubsegment(s2);
        maximalSegments.addSubsegment(seg1);
        maximalSegments.addSubsegment(seg2);
        maximalSegments.addSubsegment(seg3);
        maximalSegments.addSubsegment(seg4);
        maximalSegments.addSubsegment(seg5);
        maximalSegments.addSubsegment(seg6);
        maximalSegments.addSubsegment(seg7);
        maximalSegments.addSubsegment(seg8);
        
        // add subintersections 
        if (mi1.addSubintersection(sub1) && mi1.addSubintersection(sub2) && !mi1.addSubintersection(sub3)
                && !mi1.addSubintersection(sub4) )
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
        System.out.print("MaximalIntersection contains_Test...");
        
        // create maximal Intersection
        // MaximalIntersection 1
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        
        Point c = new Point("C", 1, 0);
        Point d = new Point("D", 1, 2);
        Segment s2 = new Segment(c,d);
        Intersection i1 = new Intersection(c, s1, s2);
        MaximalIntersection mi1 = new MaximalIntersection(i1);
        
        // make subintersections
        // subintersection 1
        Point p1 = new Point("A", 0, 0);
        Point p2 = new Point("B", 1, 0);
        Segment seg1 = new Segment(p1,p2);
        
        Point p3 = new Point("C", 1, 0);
        Point p4 = new Point("D", 1, 2);
        Segment seg2 = new Segment(p3,p4);
        Intersection sub1 = new Intersection(p2, seg1, seg2);
        
        // subintersection 2
        Point p5 = new Point("E", 0.5, 0);
        Point p6 = new Point("F", 1.5, 0);
        Segment seg3 = new Segment(p5,p6);
        
        Point p7 = new Point("G", 1, 0);
        Point p8 = new Point("H", 1, 1.5);
        Segment seg4 = new Segment(p7,p8);
        Intersection sub2 = new Intersection(p7, seg3, seg4);
        
        // subintersection 3 (NOT A SUBINTERSECTION)
        Point p9 = new Point("A", 1, 1);
        Point p10 = new Point("B", 1, 0);
        Segment seg5 = new Segment(p9,p10);
        
        Point p11 = new Point("C", 1, 0);
        Point p12 = new Point("D", 1, 2);
        Segment seg6 = new Segment(p11,p12);
        
        // subintersection 4 (DUPLICATE, SHOULD NOT BE ADDED)
        Point p13 = new Point("E", 0.5, 0);
        Point p14 = new Point("F", 1.5, 0);
        Segment seg7 = new Segment(p13,p14);
        
        Point p15 = new Point("G", 1, 0);
        Point p16 = new Point("H", 1, 1.5);
        Segment seg8 = new Segment(p15,p16);
        
        // Set up MaximalSegments
        MaximalSegments maximalSegments = new MaximalSegments();
        
        MaximalSegment maxSeg1 = new MaximalSegment(s1);
        MaximalSegment maxSeg2 = new MaximalSegment(s2);
        maximalSegments.addMaximalSegment(maxSeg1);
        maximalSegments.addMaximalSegment(maxSeg2);
        
        maximalSegments.addSubsegment(s1);
        maximalSegments.addSubsegment(s2);
        maximalSegments.addSubsegment(seg1);
        maximalSegments.addSubsegment(seg2);
        maximalSegments.addSubsegment(seg3);
        maximalSegments.addSubsegment(seg4);
        maximalSegments.addSubsegment(seg5);
        maximalSegments.addSubsegment(seg6);
        maximalSegments.addSubsegment(seg7);
        maximalSegments.addSubsegment(seg8);
        
        // add subintersections        
        mi1.addSubintersection(sub1);
        mi1.addSubintersection(sub2);
        
        // check contains
        Segment checkseg1 = new Segment(p1,p2);
        Segment checkseg2 = new Segment(p3,p4);
        Intersection checksub1 = new Intersection(p2, checkseg1, checkseg2);
        
        // subintersection 2
        Segment checkseg3 = new Segment(p5,p6);
        Segment checkseg4 = new Segment(p7,p8);
        Intersection checksub2 = new Intersection(p7, checkseg3, checkseg4);
        
        // subintersection 3 (NOT A SUBINTERSECTION)
        Segment checkseg5 = new Segment(p9,p10);
        Segment checkseg6 = new Segment(p11,p12);
        Intersection checksub3 = new Intersection(p2, checkseg5, checkseg6);
        
        if (mi1.contains(checksub1) && mi1.contains(checksub2) && !mi1.contains(checksub3))
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
