package backend.utilities;
//write tests for AngleFactory
import java.util.ArrayList;
import java.util.HashSet;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.factComputer.AngleEquivalence;

public class AngleFactory
{
    private static HashSet<AngleEquivalence> equivalences;
    
    public AngleFactory(ArrayList<Segment> segments)
    {
//        equivalences = new HashSet<AngleEquivalence>();
//        for(int s1 = 0; s1 < segments.size(); s1++)
//        {
//            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
//            {
//              //do the two segments create an Angle?
//                if(segments.get(s1).sharedVertex(segments.get(s2)) != null)
//                {
//                    Point shared = segments.get(s1).sharedVertex(segments.get(s2));
//                    ArrayList<Point> p1 = new ArrayList<Point>();
//                    ArrayList<Point> p2 = new ArrayList<Point>();
//                    //get all the points on each segment
//                    for(Point p : PointFactory.getAllPoints())
//                    {
//                        if(segments.get(s1).pointLiesOn(p))
//                        {
//                            p1.add(p);
//                        }
//                        else if(segments.get(s2).pointLiesOn(p))
//                        {
//                            p2.add(p);
//                        }
//                    }
//                    //for each pair of points on the segments create a new angle to be added
//                    for(Point ps1 : p1)
//                    {
//                        for(Point ps2 : p2)
//                        {
//                            //System.out.println("ps1 " + ps1);
//                            //System.out.println("ps2: " + ps2);
//                            if((!ps1.structurallyEquals(shared))  && (!ps2.structurallyEquals(shared)) )
//                            {
//                                if(Angle.findAngle(ps1, shared, ps2) != 0)
//                                {
//                                    //System.out.println("ps1: " + ps1 + " shared: " + shared +  " ps2: " + ps2);
//                                    addAngle(new Angle(ps1,shared,ps2));   
//                                }
//                            }
//                        } 
//                    }
//                }
//            }
//        }
    }
    
    public static HashSet<AngleEquivalence> initialize(ArrayList<Segment> segments)
    {
        equivalences = new HashSet<AngleEquivalence>();
        for(int s1 = 0; s1 < segments.size(); s1++)
        {
            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
              //do the two segments create an Angle?
                if(segments.get(s1).sharedVertex(segments.get(s2)) != null)
                {
                    Point shared = segments.get(s1).sharedVertex(segments.get(s2));
                    ArrayList<Point> p1 = new ArrayList<Point>();
                    ArrayList<Point> p2 = new ArrayList<Point>();
                    //get all the points on each segment
                    for(Point p : PointFactory.getAllPoints())
                    {
                        if(segments.get(s1).pointLiesOn(p))
                        {
                            p1.add(p);
                        }
                        else if(segments.get(s2).pointLiesOn(p))
                        {
                            p2.add(p);
                        }
                    }
                    //for each pair of points on the segments create a new angle to be added
                    for(Point ps1 : p1)
                    {
                        for(Point ps2 : p2)
                        {
                            //System.out.println("ps1 " + ps1);
                            //System.out.println("ps2: " + ps2);
                            if((!ps1.structurallyEquals(shared))  && (!ps2.structurallyEquals(shared)) )
                            {
                                if(Angle.findAngle(ps1, shared, ps2) != 0  && Angle.findAngle(ps1, shared, ps2) != Math.PI) //should we be adding angles of 180???
                                {
                                    addAngle(new Angle(ps1,shared,ps2));   
                                }
                            }
                        } 
                    }
                }
            }
        }
        return equivalences;
    }
    
    public static void addAngle(Angle a)
    {
        
        boolean belong = false;
        boolean made = false;
        for(AngleEquivalence list: equivalences)
        {
            if(list.isEquivalent(a) && !structurallyContains(list,a)) 
            //structurallyEquals is equivalence
            //if(!structurallyContains(list,a))
            {
//                System.out.println(a);
//                System.out.println(a.getA());
//                System.out.println(a.getB());
//                System.out.println(a.getC());
                belong = true;
                list.addEquivalence(a);
            }
            if(list.getMain().structurallyEquals(a))
            {
                made = true;
            }
        }
        if(!belong && !made)
        {
//            System.out.println("Creating new AngleEquivalence");
//            System.out.println(a);
//            System.out.println(a.getA());
//            System.out.println(a.getB());
//            System.out.println(a.getC());
            equivalences.add(new AngleEquivalence(a));
        }
    }
    
    public static Angle getAngle(Point p1, Point v, Point p2)
    {
        Angle a = new Angle(p1,v,p2);
        for(AngleEquivalence list : equivalences)
        {
            if(list.isEquivalent(a))
            {
                return list.getMain();
            }
        }
        return null;
    }
    
    public static HashSet<AngleEquivalence> getEquivalences()
    {
        return equivalences;
    }
    
    public static boolean structurallyContains(AngleEquivalence ae, Angle a)
    {
        for(Angle ang : ae.getEquivalentAngles())
        {
            if(checkPoints(ang,a))
            {
                //System.out.println("ang " + ang + " is a: " + a);
                return true;
            }
            //System.out.println("ang " + ang + " is NOT a: " + a);
        }
        return false;
    }
    
    public static boolean checkPoints(Angle a, Angle b)
    {
        return ((a.getA().structurallyEquals(b.getA()) && (a.getC().structurallyEquals(b.getC())) && a.getB().structurallyEquals(b.getB())) 
             || (a.getA().structurallyEquals(b.getC()) && (a.getC().structurallyEquals(b.getA())) && a.getB().structurallyEquals(b.getB())));
    }
    
}
