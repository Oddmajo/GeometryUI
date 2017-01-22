package backendTest.precomputerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.precomputer.PolygonCalculator;
public class PolygonCalculatorTest
{

    @Test
    public void testTri() 
    {
        System.out.print("Testing Triangle...");
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 1, 2);
        Point pt2 = new Point("2", 2, 1);

        Segment seg1 = new Segment(pt0, pt1);
        Segment seg2 = new Segment(pt1, pt2);
        Segment seg3 = new Segment(pt2, pt0);
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        assertNotNull(polys);
        System.out.println(polys);
        System.out.println("Done");
    }
    
    @Test
    public void testQuad() 
    {
        System.out.print("Testing Quadrilateral...");
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 1, 2);
        Point pt2 = new Point("2", 4, 2);
        Point pt3 = new Point("3", 4, 1);
        
        Segment seg1 = new Segment(pt0, pt1);
        Segment seg2 = new Segment(pt1, pt2);
        Segment seg3 = new Segment(pt2, pt3);
        Segment seg4 = new Segment(pt3, pt0);
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        assertNotNull(polys);
        System.out.println(polys);
        System.out.println("Done");
    }
    
    @Test
    public void testPentagon()
    {
        System.out.print("Testing Pentagon...");
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 1, 2);
        Point pt2 = new Point("2", 2, 3);
        Point pt3 = new Point("3", 3, 2);
        Point pt4 = new Point("4", 3, 1);
        
        Segment seg1 = new Segment(pt0, pt1);
        Segment seg2 = new Segment(pt1, pt2);
        Segment seg3 = new Segment(pt2, pt3);
        Segment seg4 = new Segment(pt3, pt4);
        Segment seg5 = new Segment(pt4, pt0);
        
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        segs.add(seg5);
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        assertNotNull(polys);
        System.out.println(polys);
        System.out.println("Done");
    }
    
    @Test
    public void testHexagon()
    {
        System.out.print("Testing Hexagon...");
        
        Point pt0 = new Point("0", 1, 2);
        Point pt1 = new Point("1", 1, 3);
        Point pt2 = new Point("2", 2, 4);
        Point pt3 = new Point("3", 3, 3);
        Point pt4 = new Point("4", 3, 2);
        Point pt5 = new Point("5", 2, 1);
        
        Segment seg1 = new Segment(pt0, pt1);
        Segment seg2 = new Segment(pt1, pt2);
        Segment seg3 = new Segment(pt2, pt3);
        Segment seg4 = new Segment(pt3, pt4);
        Segment seg5 = new Segment(pt4, pt5);
        Segment seg6 = new Segment(pt5, pt0);
        
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        segs.add(seg5);
        segs.add(seg6);
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        assertNotNull(polys);
        System.out.println(polys);
        System.out.println("Done");
    }
    
    @Test
    public void testBugs()
    {
        System.out.print("Testing past bugs...");
        
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 3, 5);
        Point pt2 = new Point("2", 5, 1);
        Point pt3 = new Point("3", 4, 3);
        Point pt4 = new Point("4", 2, 3);
        Point pt5 = new Point("5", 3, (double)7/3);
        
        ArrayList<Segment> segs = new ArrayList<Segment>();
        Segment seg0 = new Segment(pt0, pt1);
        Segment seg1 = new Segment(pt1, pt4);
        Segment seg2 = new Segment(pt0, pt4);
        segs.add(seg0);
        segs.add(seg1);
        segs.add(seg2);
        
        ArrayList<Segment> segs2 = new ArrayList<Segment>();
        Segment seg3 = new Segment(pt0, pt5);
        Segment seg4 = new Segment(pt3, pt5);
        Segment seg5 = new Segment(pt0, pt3);
        segs2.add(seg3);
        segs2.add(seg4);
        segs2.add(seg5);
        
        ArrayList<Segment> segs3 = new ArrayList<Segment>();
        Segment segA = new Segment(pt1, pt2);
        Segment segB = new Segment(pt2, pt5);
        Segment segC = new Segment(pt5, pt3);
        Segment segD = new Segment(pt3, pt4);
        Segment segE = new Segment(pt4, pt1);
        segs3.add(segA);
        segs3.add(segB);
        segs3.add(segC);
        segs3.add(segD);
        segs3.add(segE);
        
        ArrayList<Segment> segs4 = new ArrayList<Segment>();
        Segment segAA = new Segment(pt0, pt2);
        Segment segAB = new Segment(pt2, pt5);
        Segment segAC = new Segment(pt5, pt4);
        Segment segAD = new Segment(pt4, pt3);
        Segment segAE = new Segment(pt3, pt5);
        Segment segAF = new Segment(pt5, pt0);
        segs4.add(segAA);
        segs4.add(segAB);
        segs4.add(segAC);
        segs4.add(segAD);
        segs4.add(segAE);
        segs4.add(segAF);

        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        
        PolygonCalculator pc2 = new PolygonCalculator(segs2);
        ArrayList<ArrayList<Polygon>> polys2 = pc2.GetPolygons();
        
        PolygonCalculator pc3 = new PolygonCalculator(segs3);
        ArrayList<ArrayList<Polygon>> polys3 = pc3.GetPolygons();
        
        PolygonCalculator pc4 = new PolygonCalculator(segs4);
        ArrayList<ArrayList<Polygon>> polys4 = pc4.GetPolygons();
        
        
        System.out.println("Collinear Bug Long: " + polys);
        System.out.println("Collinear Bug Short: " + polys2 );
        System.out.println("Not Failed Polygon Bug: " + polys3);
        System.out.println("Crosses Bug: " + polys4);
        assertTrue(polys3.get(2).size() == 0);
        assertTrue(polys4.get(0).size() == 2);
        System.out.println("done");
    }
    
    
    @Test
    public void testComplicated()
    {
        System.out.print("Testing Fancy Triangle...");
        ArrayList<Segment> segs = new ArrayList<Segment>();
        ArrayList<Segment> segs2 = new ArrayList<Segment>();
        ArrayList<Segment> segs3 = new ArrayList<Segment>();
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 3, 5);
        Point pt2 = new Point("2", 5, 1);
        Point pt3 = new Point("3", 4, 3);
        Point pt4 = new Point("4", 2, 3);
        Point pt5 = new Point("5", 3, (double)7/3);
        
        Segment seg0 = new Segment(pt0, pt1);
        Segment seg1 = new Segment(pt1, pt2);
        Segment seg2 = new Segment(pt2, pt0);
        Segment seg3 = new Segment(pt2, pt3);
        Segment seg4 = new Segment(pt0, pt4);
        Segment seg5 = new Segment(pt4, pt3);
        
        segs.add(seg0);
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        segs.add(seg5);
        
        Segment Tseg0 = new Segment(pt0, pt4);
        Segment Tseg1 = new Segment(pt0, pt5);
        Segment Tseg2 = new Segment(pt0, pt2);
        Segment Tseg3 = new Segment(pt1, pt4);
        Segment Tseg4 = new Segment(pt1, pt3);
        Segment Tseg5 = new Segment(pt2, pt3);
        Segment Tseg6 = new Segment(pt2, pt5);
        Segment Tseg7 = new Segment(pt3, pt4);
        Segment Tseg8 = new Segment(pt3, pt5);
        Segment Tseg9 = new Segment(pt4, pt5);
        Segment Tseg10 = new Segment(pt0, pt1);
        Segment Tseg11 = new Segment(pt1, pt2);
        Segment Tseg12 = new Segment(pt0, pt3);
        Segment Tseg13 = new Segment(pt2, pt4);

        segs2.add(Tseg1);
        segs2.add(Tseg2);
        segs2.add(Tseg3);
        segs2.add(Tseg4);
        segs2.add(Tseg6);
        segs2.add(Tseg7);
        segs2.add(Tseg8);
        segs2.add(Tseg9);
        segs2.add(Tseg12);
        segs2.add(Tseg13);
        
        segs3.add(Tseg0);
        segs3.add(Tseg1);
        segs3.add(Tseg2);
        segs3.add(Tseg3);
        segs3.add(Tseg4);
        segs3.add(Tseg5);
        segs3.add(Tseg6);
        segs3.add(Tseg7);
        segs3.add(Tseg8);
        segs3.add(Tseg9);
        segs3.add(Tseg10);
        segs3.add(Tseg11);
        segs3.add(Tseg12);
        segs3.add(Tseg13);
        
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
       
        PolygonCalculator pc2 = new PolygonCalculator(segs2);
        ArrayList<ArrayList<Polygon>> polys2 = pc2.GetPolygons();
        
        PolygonCalculator pc3 = new PolygonCalculator(segs3);
        ArrayList<ArrayList<Polygon>> polys3 = pc3.GetPolygons();
        assertNotNull(polys);
        
        System.out.println("Only Long: " +  polys);
        System.out.println("Only Short: " + polys2);
        System.out.println("All Segments: " + polys3);
        System.out.println("done");
    }

}
