package backendTest.astTest.figure.components;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class MaximalSegmentTest
{

    /**
     *  A-------B     C-------D
     */
    @Test
    public void equals_Collinear_Not_Coinciding_Test()
    {
        System.out.print("MaximalSegment equals_(Collinear_Not_Coinciding)_Test...");
        // two colinear but non-coinciding  Maximal Segments
        Point a = new Point("A", 0, 0);
        Point b = new Point("B", 2, 0);
        Segment s1 = new Segment(a,b);
        MaximalSegment ms1 = new MaximalSegment(s1);
        
        Point c = new Point("C", 0, 0);
        Point d = new Point("D", 2, 0);
        Segment s2 = new Segment(c,d);
        MaximalSegment ms2 = new MaximalSegment(s2);
                
        if (!ms1.equals(ms2))
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

}

