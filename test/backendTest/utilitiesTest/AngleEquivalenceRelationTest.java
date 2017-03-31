package backendTest.utilitiesTest;

import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.factComputer.AngleEquivalence;
import backend.factComputer.FactComputer;
import backend.precomputer.Precomputer;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.AngleFactory;
import backend.utilities.PointFactory;

public class AngleEquivalenceRelationTest
{
    @Test
    public void twoPointsPerRay()
    {
        System.out.print("Starting AngleEquivalenceRelationTest::twoPointsPerRay... ");
        
        PointFactory.clear();
        AngleEquivalenceRelation.getInstance().clear();
        
        //    A    M
        //      |  /
        //    D | /
        //      |/
        //    B /_____E_____ C
        // Construct segments and the corresponding points
        // Create the points
        ArrayList<Point> points = new ArrayList<Point>();
        Point a = new Point("A", 0, 1); points.add(a);
        Point b = new Point("B", 0, 0); points.add(b);
        Point m = new Point("M", 0.5, 0.5); points.add(m);
        Point c = new Point("C", 1, 0); points.add(c);

        Point d = new Point("D", 0, 0.5); points.add(d);
        Point e = new Point("E", 0.5, 0); points.add(e);
        
        ArrayList<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment(a, b));
        segments.add(new Segment(m, b));
        segments.add(new Segment(b, c));
        
        Precomputer pc = new Precomputer(points, segments);
        pc.analyze();

        FactComputer fc = new FactComputer(pc);
        fc.analyze();

        // There are 3 distinct angles resulting with sets size <2, 4, 2>
        if (AngleEquivalenceRelation.getInstance().getRepresentative().size() != 3)
        {
            System.out.println("Expecting " + 3 + " classes of angles; acquired " + AngleEquivalenceRelation.getInstance().getRepresentative().size());
            assert(false);
        }
            
        //
        // There are a specific number of facts generated:
        //
        assert(PointFactory.getAllPoints().size() == 6);
        assert(fc.getInMiddles().size() == 2);
        assert(fc.getSegments().size() == 7);
        assert(AngleEquivalenceRelation.getInstance().size() == 8);
        assert(fc.getCongruentAngles().size() == 20); // 2 * 4C2 + 8
        
        System.out.println("Passed");
    }
    
    @Test
    public void twoPtsOneRay()
    {
        System.out.print("Starting 2Pts 1 ray AngleFactoryTest... ");
        Point p1 = PointFactory.GeneratePoint(0, 0);
        Point p2 = PointFactory.GeneratePoint(1, 0);
        Point p3 = PointFactory.GeneratePoint(.5, .5);
        Point p4 = PointFactory.GeneratePoint(1, 1);
        //Point p5 = PointFactory.GeneratePoint(2, 2);
        Segment s1 = new Segment(p1,p2);
        Segment s2 = new Segment(p1,p3);
        Segment s3 = new Segment(p1,p4);
        //Segment s4 = new Segment(p1,p5);
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(s1);
        segs.add(s2);
        segs.add(s3);
        //segs.add(s4);
        //AngleFactory factory = new AngleFactory(segs);
        AngleFactory.initialize(segs);
//        System.out.println("equivalences: " + AngleFactory.getEquivalences().size());
//        for(AngleEquivalence ae : AngleFactory.getEquivalences())
//        {
//            //System.out.println(ae.getMain());
//            for(Angle a : ae.getEquivalentAngles())
//            {
//                System.out.println(a);
//            }
//        }
        assert(AngleFactory.getEquivalences().size() == 1);
        for(AngleEquivalence ae : AngleFactory.getEquivalences())
        {
            assert(ae.getSize() == 2);
        }
        System.out.println("Passed");
    }
    
//    @Test
//    public void supplementaryAngle() //do we want 180 degree angles
//    {
//        System.out.print("Starting supplementaryAngle test in AngleFactoryTest...");
//        Point p1 = PointFactory.GeneratePoint(0, 0);
//        Point p2 = PointFactory.GeneratePoint(2, 0);
//        Point p3 = PointFactory.GeneratePoint(1, 2);
//        Point p4 = PointFactory.GeneratePoint(4, 0);
//        Segment s1 = new Segment(p1,p4);
//        Segment s2 = new Segment(p2,p3);
//        Segment s3 = new Segment(p1,p2);
//        Segment s4 = new Segment(p2,p4);
//        ArrayList<Segment> segs = new ArrayList<Segment>();
//        segs.add(s1);
//        segs.add(s2);
//        segs.add(s3);
//        segs.add(s4);
//        AngleFactory.initialize(segs);
//        System.out.println(AngleFactory.getEquivalences().size());
//        for(AngleEquivalence ae : AngleFactory.getEquivalences())
//        {
//            for(Angle a : ae.getEquivalentAngles())
//            {
//                System.out.println(a);
//            }
//        }
//    }
    
    @Test public void manyPointsOneAngle()
    {
        System.out.print("Starting AngleEquivalenceRelationTest::manyPointsOneAngle test... ");

        PointFactory.clear();
        AngleEquivalenceRelation.getInstance().clear();

        //
        //                    F
        //  
        //               E
        //  
        //         D
        //  
        //  
        //  A-----C-----B----G-----H
        //

        // Create the points
        ArrayList<Point> points = new ArrayList<Point>();
        
        Point a = new Point("A", 0, 0); points.add(a);
        Point b = new Point("B", 1, 0); points.add(b);
        Point c = new Point("C", 0.5, 0.5); points.add(c);
        Point d = new Point("D", 1, 1); points.add(d);
        Point e = new Point("E", 1.5, 1.5); points.add(e);
        Point f = new Point("F", 2, 2); points.add(f);
        Point g = new Point("G", 1.5, 0); points.add(g);
        Point h = new Point("H", 2, 0); points.add(h);

        ArrayList<Segment> segments = new ArrayList<Segment>();
        Segment s1 = new Segment(a, f); segments.add(s1);
        Segment s2 = new Segment(a, h); segments.add(s2);
//        Segment s3 = new Segment(a, e); segments.add(s3);
//        Segment s4 = new Segment(a, f); segments.add(s4);
//        Segment s5 = new Segment(a, b); segments.add(s5);
//        Segment s6 = new Segment(a, g); segments.add(s6);
//        Segment s7 = new Segment(a, h); segments.add(s7);
//        
//        Segment s8 = new Segment(b, g); segments.add(s8);
//        Segment s9 = new Segment(b, h); segments.add(s9);
//        Segment s10 = new Segment(g, h); segments.add(s10);
//        Segment s11 = new Segment(c, d); segments.add(s11);
//        Segment s12 = new Segment(c, e); segments.add(s12);
//        Segment s13 = new Segment(c, f); segments.add(s13);
//        Segment s14 = new Segment(d, e); segments.add(s14);
//        Segment s15 = new Segment(d, f); segments.add(s15);
//        Segment s16 = new Segment(e, f); segments.add(s16);

        Precomputer pc = new Precomputer(points, segments);
        pc.analyze();

        FactComputer fc = new FactComputer(pc);
        fc.analyze();
        
        // There are 3 distinct angles resulting with sets size <2, 4, 2>
        if (AngleEquivalenceRelation.getInstance().getRepresentative().size() != 1)
        {
            System.out.println("Expecting " + 3 + " classes of angles; acquired " + AngleEquivalenceRelation.getInstance().getRepresentative().size());
            assert(false);
        }
            
        //
        // There are a specific number of facts generated:
        //
        assert(PointFactory.getAllPoints().size() == 8);
        assert(fc.getSegments().size() == 16);
        assert(fc.getInMiddles().size() == 11);
        assert(AngleEquivalenceRelation.getInstance().size() == 12);
        assert(fc.getCongruentAngles().size() == 66); // 12C2
        
        System.out.println("Passed");
    }
}
