package backendTest.astTest;

import org.junit.Test;

import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Midpoint;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class GroundedClauseTest
{

    // structurally equals test
    @Test
    public void structurallyEqualsTest()
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

}
