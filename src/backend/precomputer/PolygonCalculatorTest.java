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
    public void test() throws IllegalArgumentException
    {
        Point pt1 = new Point("1", 1, 1);
        Point pt2 = new Point("2", 2, 1);
        Point pt3 = new Point("3", 1, 2);
        Point pt4 = new Point("4", 3, 4);
        
        Segment seg1 = new Segment(pt1, pt2);
        Segment seg2 = new Segment(pt2, pt3);
        Segment seg3 = new Segment(pt3, pt1);
        Segment seg4 = new Segment(pt1, pt4);
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(seg1);
        segs.add(seg2);
        segs.add(seg3);
        segs.add(seg4);
        Polygon poly = new Polygon(seg1, seg2, seg3);
        Polygon poly2 = new Polygon(seg1, seg2, seg4);
        PolygonCalculator pc = new PolygonCalculator(segs);
        ArrayList<ArrayList<Polygon>> polys = pc.GetPolygons();
        assertNotNull(polys);
        System.out.println(poly);
        System.out.println(poly2);
        System.out.println(polys);
        for(ArrayList<Polygon> pl : polys)
        {
            for(Polygon p : pl)
            {
                System.out.println(p.toString());
            }
            System.out.println(pl.toString());
        }
    }

}
