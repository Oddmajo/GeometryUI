package FactComputerTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.angles.Angle;
import backend.factComputer.AngleEquivalence;
import backend.utilities.PointFactory;

public class AngleEquivalenceTest
{
    @Test
    public void sameAngles()
    {
        System.out.print("Starting AngleEquivalenceTest sameAngles...");
        Angle a1 = new Angle(PointFactory.GeneratePoint(.5, .5),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
        Angle a2 = new Angle(PointFactory.GeneratePoint(.5, .5),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
        AngleEquivalence ae = new AngleEquivalence(a1);
        
        assert(ae.isEquivalent(a2));
        System.out.print("Done");
    }
    
    @Test
    public void equivalentAngles()
    {
        System.out.print("Starting AngleEquivalenceTest equivalentAngles...");
        Angle a1 = new Angle(PointFactory.GeneratePoint(1, 1),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
        Angle a2 = new Angle(PointFactory.GeneratePoint(.5, .5),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
        AngleEquivalence ae = new AngleEquivalence(a1);
        
        assert(ae.isEquivalent(a2));
        System.out.println("Done");
    }
    
//    @Test
//    public void getEquivalences()
//    {
//        System.out.print("Starting AngleEquivalenceTest getEquivalences...");
//        Angle a1 = new Angle(PointFactory.GeneratePoint(1, 1),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
//        Angle a2 = new Angle(PointFactory.GeneratePoint(.5, .5),PointFactory.GeneratePoint(0, 0),PointFactory.GeneratePoint(1, 0));
//        AngleEquivalence ae = new AngleEquivalence(a1);
//        
//        for(Angle a : ae.getEquivalentAngles())
//        {
//            System.out.println(a);
//        }
//        System.out.println("Done");
//    }
}
