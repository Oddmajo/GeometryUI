package backendTest.utilitiesTest;

import java.util.ArrayList;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.factComputer.AngleEquivalence;
import backend.utilities.AngleFactory;
import backend.utilities.PointFactory;

public class AngleFactoryTest
{
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
    
    @Test
    public void supplementaryAngle() //do we want 180 degree angles
    {
        System.out.print("Starting supplementaryAngle test in AngleFactoryTest...");
        Point p1 = PointFactory.GeneratePoint(0, 0);
        Point p2 = PointFactory.GeneratePoint(2, 0);
        Point p3 = PointFactory.GeneratePoint(1, 2);
        Point p4 = PointFactory.GeneratePoint(4, 0);
        Segment s1 = new Segment(p1,p4);
        Segment s2 = new Segment(p2,p3);
        Segment s3 = new Segment(p1,p2);
        Segment s4 = new Segment(p2,p4);
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(s1);
        segs.add(s2);
        segs.add(s3);
        segs.add(s4);
        AngleFactory.initialize(segs);
        System.out.println(AngleFactory.getEquivalences().size());
        for(AngleEquivalence ae : AngleFactory.getEquivalences())
        {
            for(Angle a : ae.getEquivalentAngles())
            {
                System.out.println(a);
            }
        }
    }
    
    @Test public void manyPointsOneAngle()
    {
        System.out.print("Starting manyPointsOneAngle test in AngleFactoryTest... ");
        Point p1 = PointFactory.GeneratePoint(0, 0);
        Point p2 = PointFactory.GeneratePoint(1, 0);
        Point p3 = PointFactory.GeneratePoint(.5, .5);
        Point p4 = PointFactory.GeneratePoint(1, 1);
        Point p5 = PointFactory.GeneratePoint(1.5, 1.5);
        Point p6 = PointFactory.GeneratePoint(2, 2);
        Point p7 = PointFactory.GeneratePoint(1.5, 0);
        Point p8 = PointFactory.GeneratePoint(2, 0);
        
        Segment s1 = new Segment(p1,p3);
        Segment s2 = new Segment(p1,p4);
        Segment s3 = new Segment(p1,p5);
        Segment s4 = new Segment(p1,p6);
        Segment s5 = new Segment(p1,p2);
        Segment s6 = new Segment(p1,p7);
        Segment s7 = new Segment(p1,p8);
        
        Segment s8 = new Segment(p2,p7);
        Segment s9 = new Segment(p2,p8);
        Segment s10 = new Segment(p7,p8);
        Segment s11 = new Segment(p3,p4);
        Segment s12 = new Segment(p3,p5);
        Segment s13 = new Segment(p3,p6);
        Segment s14 = new Segment(p4,p5);
        Segment s15 = new Segment(p4,p6);
        Segment s16 = new Segment(p5,p6);
        
        ArrayList<Segment> segs = new ArrayList<Segment>();
        segs.add(s1);
        segs.add(s2);
        segs.add(s3);
        segs.add(s4);
        segs.add(s5);
        segs.add(s6);
        segs.add(s7);
        segs.add(s8);
        segs.add(s9);
        segs.add(s10);
        segs.add(s11);
        segs.add(s12);
        segs.add(s13);
        segs.add(s14);
        segs.add(s15);
        segs.add(s16);
        
        AngleFactory.initialize(segs);
        System.out.println(AngleFactory.getEquivalences().size());
        
        for(AngleEquivalence ae : AngleFactory.getEquivalences())
        {
            System.out.println(ae.getMain());
            for(Angle a : ae.getEquivalentAngles())
            {
                System.out.println(a);
            }
        }
    }
}
