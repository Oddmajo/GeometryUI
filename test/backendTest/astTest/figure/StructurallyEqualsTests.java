package backendTest.astTest.figure;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class StructurallyEqualsTests
{

    @Test
    public void inMiddleTest()
    {
        Point p1 = new Point("p1", 1, 0);
        Point p2 = new Point("p2", 1, 0);
        
        Point s1p1 = new Point("s1p1", 0,0);
        Point s1p2 = new Point("s1p2", 2, 0);
        Segment s1 = new Segment(s1p1, s1p2);
        
        Point s2p1 = new Point("s2p1", 0,0);
        Point s2p2 = new Point("s2p2", 2, 0);
        Segment s2 = new Segment(s2p1, s2p2);
        
        InMiddle im1 = new InMiddle(p1, s1);
        InMiddle im2 = new InMiddle(p2, s2);
        
        assertTrue(im1.structurallyEquals(im2));
    }
    
    @Test
    public void midpointTest()
    {
        // midpoint structurally equals test
        // create midpoints
        Point p1 = new Point("p1", 1, 0);
        Point p2 = new Point("p2", 1, 0);
        
        Segment s1 = new Segment(new Point("s1p1", 0, 0), new Point("s1p2", 2, 0));
        Segment s2 = new Segment(new Point("s2p1", 0, 0), new Point("s2p2", 2, 0));
        
        InMiddle im1 = new InMiddle(p1, s1);
        InMiddle im2 = new InMiddle(p2, s2);
        
        Midpoint mp1 = new Midpoint(im1);
        mp1.setID(234);
        Midpoint mp2 = new Midpoint(im2);
        
        // assert structurally equals
        assert(mp1.structurallyEquals(mp2));
    }
    
    @Test
    public void congruentSegmentsTest()
    {
        Point s1p1 = new Point("s1p1", 0,0);
        Point s1p2 = new Point("s1p2", 2, 0);
        Segment s1 = new Segment(s1p1, s1p2);
        
        Point s2p1 = new Point("s2p1", 2,0);
        Point s2p2 = new Point("s2p2", 4, 0);
        Segment s2 = new Segment(s2p1, s2p2);
        
        CongruentSegments cs1 = new CongruentSegments(s1, s2);
        
        Point s3p1 = new Point("s3p1", 0,0);
        Point s3p2 = new Point("s3p2", 2, 0);
        Segment s3 = new Segment(s1p1, s1p2);
        
        Point s4p1 = new Point("s4p1", 2,0);
        Point s4p2 = new Point("s4p2", 4, 0);
        Segment s4 = new Segment(s2p1, s2p2);
        
        CongruentSegments cs2 = new CongruentSegments(s3, s4);
        
        assertTrue(cs1.structurallyEquals(cs2));
    }
    
    @Test
    public void strengthenedMidpointTest()
    {
     // midpoint structurally equals test
        // create midpoints
        Point p1 = new Point("p1", 1, 0);
        Point p2 = new Point("p2", 1, 0);
        
        Segment s1 = new Segment(new Point("s1p1", 0, 0), new Point("s1p2", 2, 0));
        Segment s2 = new Segment(new Point("s2p1", 0, 0), new Point("s2p2", 2, 0));
        
        InMiddle im1 = new InMiddle(p1, s1);
        InMiddle im2 = new InMiddle(p2, s2);
        
        Strengthened str1 = im1.canBeStrengthened();
        Strengthened str2 = im2.canBeStrengthened();
        
        System.out.println(str1.toString());
        
        assertTrue(str1.structurallyEquals(str2));
        
    }

}
