package backend.factComputer;

import java.util.ArrayList;
import java.util.HashSet;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.angles.Angle;
import backend.utilities.AngleFactory;
import backend.utilities.math.MathUtilities;

public class AngleEquivalence
{
    private Angle main;
    private HashSet<Angle> equivalentAngles;
    public AngleEquivalence(Angle m)
    {
        main = m;
        equivalentAngles = new HashSet<Angle>();
        equivalentAngles.add(main);
        //equivalentAngles.addAll(getEquivalentAngles(main));
    }
    
    public AngleEquivalence(Angle m, ArrayList<Angle> eq)
    {
        main = m;
        equivalentAngles = new HashSet<Angle>(eq);
        if(!equivalentAngles.contains(m))
        {
            equivalentAngles.add(m);
        }
    }
    
    public int getSize()
    {
        return equivalentAngles.size();
    }
    
    public static HashSet<Angle> getEquivalentAngles(Angle s) //this function currently doesn't work
    {
        HashSet<Angle> equivalantAngles = new HashSet<Angle>();
        
        HashSet<Point> points = new HashSet<Point>(); //please get rid of this when 
        for(Point p : /*all avaliable points*/points)
        {
            //Is the point on the first Ray?
            if(s.getRay1().pointLiesOn(p))
            {
                //new angle is second ray nonOrigin, origin and the point found
                equivalantAngles.add(new Angle(s.getRay2().getNonOrigin(),s.getRay2().getOrigin(),p));
            }
            //Is the point on the second Ray?
            else if(s.getRay2().pointLiesOn(p))
            {
                //new angle is first ray nonOrigin, origin, and the point foun
                equivalantAngles.add(new Angle(p,s.getRay1().getOrigin(),s.getRay1().getNonOrigin()));
            }
        }
        
        return equivalantAngles;
    }
    
    public static HashSet<Angle> getEquivalentAngles(Angle start, ArrayList<Angle> other)
    {
        HashSet<Angle> equivalantAngles = new HashSet<Angle>();
        
        for(Angle a : other)
        {
            
        }
        
        return equivalantAngles;
    }
    
    public HashSet<Angle> getEquivalentAngles()
    {
        return equivalentAngles;
    }
    
    public static Boolean isEquivalent(Angle ag1, Angle ag2)
    {
        if((ag1.getRay1().equates(ag2.getRay1()))  && (ag1.getRay2().equates(ag2.getRay2())))
        {
            return true;
        }
        else if((ag1.getRay2().equates(ag2.getRay1()))  && (ag1.getRay1().equates(ag2.getRay2())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Boolean isEquivalent(Angle ag)
    {
        //check to see if the two rays, vertex, and angle measure are equal
        if(ag.getVertex().structurallyEquals(main.getVertex()))
        {
            if(MathUtilities.doubleEquals(ag.getMeasure(),main.getMeasure()))
            {
                if(ag.getRay1().equates(main.getRay1()))
                {
                    if(ag.getRay2().equates(main.getRay2()))
                    {
                        return true;
                    }
                }
                else if(ag.getRay1().equates(main.getRay2()))
                {
                    if(ag.getRay2().equates(main.getRay1()))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Boolean addEquivalence(Angle ag)
    {
        if(isEquivalent(ag) && !structurallyContains(ag))
        {
            equivalentAngles.add(ag);
            return true;
        }
        return false;
    }
    
    public Angle getMain()
    {
        return main;
    }
    
    public Boolean structurallyContains(Angle a)
    {
        for(Angle ang : equivalentAngles)
        {
            if(AngleFactory.checkPoints(ang,a))
            {
                //System.out.println("ang " + ang + " is a: " + a);
                return true;
            }
            //System.out.println("ang " + ang + " is NOT a: " + a);
        }
        return false;
    }
}
