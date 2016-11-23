package backendTest.equationsTest;

import backend.equations.Equation;
import backend.equations.operations.Addition;
import backend.instantiator.algebra.Simplification;
import backend.ast.figure.components.Point;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.ast.figure.components.*;

import java.util.Random;

public class EquationGenerator
{
    private static Random rng = new Random();
    private static final int MAX_COORD = 10;

    public static int genCoordinate()
    {
        return rng.nextInt() % MAX_COORD + 1;
    }

    public static String genName()
    {
        return "Segment " + rng.nextInt() % MAX_COORD;
    }

    public static Point genPoint()
    {
        return new Point(genName(), genCoordinate(), genCoordinate());
    }

    public static Segment genSegment(int multiplier)
    {
        Point p = genPoint();
        Point q = genPoint();

        //Prevents these points from creating a segment of zero length
        while (p.equals(q))
        {
            p = genPoint();
            q = genPoint();
        }
        Segment pg = new Segment(p, q);
        pg.setMultiplier(multiplier);

        return pg;
    }

    public static Segment genSegment(Segment in, int mult)
    {
        Segment seg = new Segment(in);
        seg.setMultiplier(mult);

        return seg; 
    }

    //
    // Construct a segment equation, also construct the simplified equation.
    // The result of simplification better match.
    //
    
   
    public static Pair<Equation, Equation> genAdditionEquationPair() throws ArgumentException, CloneNotSupportedException
    {
        //
        // LHS
        //
        Segment p = genSegment(rng.nextInt(MAX_COORD));
        Segment q = genSegment(rng.nextInt(MAX_COORD));

        //
        // RHS
        //
        Segment r = genSegment(p, rng.nextInt(MAX_COORD));
        Segment s = genSegment(q, rng.nextInt(MAX_COORD)); 

        while (p.getMulitplier() == r.getMulitplier() && q.getMulitplier() == s.getMulitplier())
        {
            p = genSegment(rng.nextInt(MAX_COORD));
            q = genSegment(rng.nextInt(MAX_COORD));

            r = genSegment(p, rng.nextInt(MAX_COORD));
            s = genSegment(q, rng.nextInt(MAX_COORD)); 
        }
        System.out.println("P's Multiplier: " + p.getMulitplier());
        System.out.println("Q's Multiplier: " + q.getMulitplier());
        System.out.println("R's Multiplier: " + r.getMulitplier());
        System.out.println("S's Multiplier: " + s.getMulitplier());
        Addition sumLHS = new Addition(p, q);
        Addition sumRHS = new Addition(r, s);

        Equation original = new Equation(sumLHS, sumRHS);

        Equation simplified = null;
        Segment simpleLeft = null, simpleRight = null;

        


        if (p.getPoint1().getX() == r.getPoint1().getX() && p.getPoint1().getY() == r.getPoint1().getY()
                && p.getPoint2().getX() == r.getPoint2().getX())
        {
            Segment a = new Segment(p);
            Segment b = new Segment(r);
            if (p.getPoint1().getMulitplier() < r.getPoint1().getMulitplier())
            {
                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
                simpleRight = b;
            }
            else if (p.getPoint1().getMulitplier() > r.getPoint1().getMulitplier())
            {
                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
                simpleLeft = a;
            }
            else
            {
                a.getPoint1().setMultiplier(0);
                simpleLeft = a;
            }
        }
        if (p.getPoint1().getX() == s.getPoint1().getX() && p.getPoint1().getY() == s.getPoint1().getY()
                && p.getPoint2().getX() == s.getPoint2().getX())
        {
            Segment a = new Segment(p);
            Segment b = new Segment(s);
            if (s.getPoint1().getMulitplier() < p.getPoint1().getMulitplier())
            {
                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
                simpleRight = a;
            }
            else if (s.getPoint1().getMulitplier() > p.getPoint1().getMulitplier())
            {
                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
                simpleLeft = b;
            }
            else
            {
                a.getPoint1().setMultiplier(0);
                simpleLeft = a;
            }
        }
        if (q.getPoint1().getX() == r.getPoint1().getX() && q.getPoint1().getY() == r.getPoint1().getY() 
                && q.getPoint2().getX() == r.getPoint2().getX() && q.getPoint2().getY() == r.getPoint2().getY())
        {
            Segment a = new Segment(q);
            Segment b = new Segment(r);
            if (q.getPoint1().getMulitplier() < r.getPoint1().getMulitplier())
            {
                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
                simpleRight = b;
            }
            else if (q.getPoint1().getMulitplier() > r.getPoint1().getMulitplier())
            {
                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
                simpleLeft = a;
            }
            else
            {
                a.getPoint1().setMultiplier(0);
                simpleRight = a;
            }
        }
        if (q.getPoint1().getX() == s.getPoint1().getX() && q.getPoint1().getY() == s.getPoint1().getY()
                && q.getPoint2().getX() == s.getPoint2().getX() && q.getPoint2().getY() == s.getPoint2().getY())
        {
            Segment a = new Segment(q);
            Segment b = new Segment(s);
            if (q.getPoint1().getMulitplier() < s.getPoint1().getMulitplier())
            {
                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
                simpleRight = b;
            }
            else if (q.getPoint1().getMulitplier() > s.getPoint1().getMulitplier())
            {
                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
                simpleRight = a;
            }
            else
            {
                a.getPoint1().setMultiplier(0);
                simpleRight = a;
            }
        }

        simplified = new Equation(simpleLeft, simpleRight);
        
       // simplified = Simplification.simplify(original);
        Pair<Equation, Equation> equations = new Pair<Equation, Equation>(original, simplified);
        return equations;
        /*
        // Build simplified based on the math above (with gcd, etc.)
        SegmentEquation simp = new SegmentEquation(Simplification.simplify(original));

        return new Pair<SegmentEquation, SegmentEquation>(original, simp);
         */
    }

    //
    // The external verifier will perform test:     simplification(original) == simp
    //




}
