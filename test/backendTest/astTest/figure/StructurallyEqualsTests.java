package backendTest.astTest.figure;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.ast.Descriptors.InMiddle;
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

}
