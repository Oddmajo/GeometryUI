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
                            if(ps1.structurallyEquals(ps2))
                            {
                                addAngle(new Angle(ps1,shared,ps2));
                            }
                        } 
                    }
                }
            }
        }
    }
    
    
    public static void addAngle(Angle a)
    {
        boolean belong = false;
        for(AngleEquivalence list: equivalences)
        {
            //if it successfully adds it to the list then it belonged to that Equivalence class relation
            if(list.addEquivalence(a))
            {
                belong = true;
            }
        }
        if(belong == false)
        {
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
    
    
}
