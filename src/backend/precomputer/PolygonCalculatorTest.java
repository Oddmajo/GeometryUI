package backend.precomputer;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Segment;
public class PolygonCalculatorTest
{

    @Test
    public void testTri() 
    {
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
    }
    
    @Test
    public void testSquare() 
    {
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 1, 2);
        Point pt2 = new Point("2", 2, 2);
        Point pt3 = new Point("3", 2, 1);
        
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
    }
    
    @Test
    public void testQuad() 
    {
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
    }
    
    @Test
    public void testPentagon()
    {
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
    }
    
    @Test
    public void testHexagon()
    {
        
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
    }
    
    @Test
    public void testAngle()
    {
        ArrayList<Segment> segs = new ArrayList<Segment>();
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 3, 5);
        Point pt4 = new Point("4", 2, 3);
        
        Segment seg0 = new Segment(pt0, pt1);
        Segment seg1 = new Segment(pt1, pt4);
        Segment seg2 = new Segment(pt0, pt4);
        
        segs.add(seg0);
        segs.add(seg1);
        segs.add(seg2);
        
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        
        assertNotNull(polys);
        System.out.println("T4: " + polys);
    }
    
    
    @Test
    public void testComplicated()
    {
        ArrayList<Segment> segs = new ArrayList<Segment>();
        ArrayList<Segment> segs2 = new ArrayList<Segment>();
        ArrayList<Segment> segs3 = new ArrayList<Segment>();
        Point pt0 = new Point("0", 1, 1);
        Point pt1 = new Point("1", 3, 5);
        Point pt2 = new Point("2", 5, 1);
        Point pt3 = new Point("3", 4, 3);
        Point pt4 = new Point("4", 2, 3);
        Point pt5 = new Point("5", 2, 7/3);
        
        Segment seg0 = new Segment(pt0, pt1);
        Segment seg1 = new Segment(pt1, pt2);
        Segment seg2 = new Segment(pt2, pt0);
        Segment seg3 = new Segment(pt0, pt3);
        Segment seg4 = new Segment(pt2, pt4);
        Segment seg5 = new Segment(pt4, pt3);
        
        segs.add(seg0);
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        segs.add(seg5);
        
        Segment seg6 = new Segment(pt0, pt4);
        Segment seg7 = new Segment(pt3, pt2);
        
        segs2.add(seg6);
        segs2.add(seg7);
        segs2.add(seg2);
        segs2.add(seg3);
        segs2.add(seg4);
        segs2.add(seg5);
        
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
        
        System.out.println("T1: " +  polys);
        System.out.println("T2: " + polys2);
        System.out.println("T3: " + polys3);
    }

}
