package backendTest.symbolicAlgebraTest.equationsTest.generator;

import backend.ast.figure.components.Point;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.Equation;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.Pair;
import backend.ast.figure.components.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.common.math.*; // https://github.com/google/guava/wiki/Release20

public abstract class EquationGenerator
{
    //
    //
    // The following functions return two equations of the following form:
    //     <original, simplified>
    // Simplification is performed using algebraic notions based solely on the coefficients
    //
    public abstract Pair<Equation, Equation> generateAXequalsBequation();
    public abstract Pair<Equation, Equation> generateAXequalsBXequation();
    public abstract Pair<Equation, Equation> generateAXplusBYequalsZeroequation();
    public abstract Pair<Equation, Equation> generateAXplusBYequalsCZequation();
    public abstract Pair<Equation, Equation> generateAXplusBYequalsCXplusBYequation();

    //
    // Standard GCD algorithm
    //
    protected static int gcd(int a, int b)
    {
        // Google Guava defined gcd: // https://github.com/google/guava/wiki/Release20
        return IntMath.gcd(Math.abs(a), Math.abs(b));
    }

    //
    // Reduces the two given values 
    //
    protected static Pair<Integer, Integer> reduce(int a, int b)
    {
        int gcd = gcd(a, b);
        if (gcd == 0)
            gcd = 1;
        return new Pair<Integer, Integer>(a / gcd, b / gcd);
    }

    //
    // Need a function to determine the GCD of an entire list of values.
    //
    // gcd is associative, so gcd(a, b, c, d) equates to gcd(a, gcd(b, gcd(c, d)))
    //
    protected static int gcd(List<Integer> values)
    {
        if (values.size() < 2) return 1;
        
        int current = gcd(values.get(0), values.get(1));
        
        for (int index = 2; index < values.size(); index++)
        {
            // break out early
            if (current == 1) break;
            
            current = gcd(current, values.get(index));
        }

        return current;
    }
    
    //
    // Reduces a list of values using gcd
    // @return the list of reduced values
    //
    protected static List<Integer> reduce(List<Integer> values)
    {
        ArrayList<Integer> reduced = new ArrayList<Integer>();

        int gcd = gcd(values);
        
        values.forEach((value) -> reduced.add(value / gcd));

        return values;
    }    
    
    
    
    protected static Random rng = new Random(5);
    protected static final int MAX_COORD = 10;

    public static int genCoordinate()
    {
        return rng.nextInt(MAX_COORD) + 1;
    }

    public static String genName()
    {
        return "Segment " + rng.nextInt(MAX_COORD);
    }

    public static Point genPoint()
    {
        return new Point(genName(), genCoordinate(), genCoordinate());
    }

    public static Segment genSegment()
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
        //pg.setMultiplier(multiplier);

        return pg;
    }

    public static Multiplication genSegmentGivenMultiplier(int multiplier)
    {
        return new Multiplication(new NumericValue(multiplier), genSegment());
    }
    
    public static Multiplication genSegmentGivenMultiplierAndSegment(int multiplier, Segment seg)
    {
        return new Multiplication(new NumericValue(multiplier), seg);
    }
    

//    //
//    // Construct a segment equation, also construct the simplified equation.
//    // The result of simplification better match.
//    //
//    public static Pair<Equation, Equation> genAdditionEquationPair() throws ArgumentException, CloneNotSupportedException
//    {
//        //
//        // LHS
//        //
//        Segment p = genSegment(rng.nextInt(MAX_COORD));
//        Segment q = genSegment(rng.nextInt(MAX_COORD));
//
//        //
//        // RHS
//        //
//        Segment r = genSegment(p, rng.nextInt(MAX_COORD));
//        Segment s = genSegment(q, rng.nextInt(MAX_COORD)); 
//
//        while (p.getMulitplier() == r.getMulitplier() && q.getMulitplier() == s.getMulitplier())
//        {
//            p = genSegment(rng.nextInt(MAX_COORD));
//            q = genSegment(rng.nextInt(MAX_COORD));
//
//            r = genSegment(p, rng.nextInt(MAX_COORD));
//            s = genSegment(q, rng.nextInt(MAX_COORD)); 
//        }
//        System.out.println("P's Multiplier: " + p.getMulitplier());
//        System.out.println("Q's Multiplier: " + q.getMulitplier());
//        System.out.println("R's Multiplier: " + r.getMulitplier());
//        System.out.println("S's Multiplier: " + s.getMulitplier());
//        Addition sumLHS = new Addition(p, q);
//        Addition sumRHS = new Addition(r, s);
//
//        Equation original = new Equation(sumLHS, sumRHS);
//
//        Equation simplified = null;
//        Segment simpleLeft = null, simpleRight = null;
//
//
//
//
//        if (p.getPoint1().getX() == r.getPoint1().getX() && p.getPoint1().getY() == r.getPoint1().getY()
//                && p.getPoint2().getX() == r.getPoint2().getX())
//        {
//            Segment a = new Segment(p);
//            Segment b = new Segment(r);
//            if (p.getPoint1().getMulitplier() < r.getPoint1().getMulitplier())
//            {
//                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
//                simpleRight = b;
//            }
//            else if (p.getPoint1().getMulitplier() > r.getPoint1().getMulitplier())
//            {
//                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
//                simpleLeft = a;
//            }
//            else
//            {
//                a.getPoint1().setMultiplier(0);
//                simpleLeft = a;
//            }
//        }
//        if (p.getPoint1().getX() == s.getPoint1().getX() && p.getPoint1().getY() == s.getPoint1().getY()
//                && p.getPoint2().getX() == s.getPoint2().getX())
//        {
//            Segment a = new Segment(p);
//            Segment b = new Segment(s);
//            if (s.getPoint1().getMulitplier() < p.getPoint1().getMulitplier())
//            {
//                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
//                simpleRight = a;
//            }
//            else if (s.getPoint1().getMulitplier() > p.getPoint1().getMulitplier())
//            {
//                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
//                simpleLeft = b;
//            }
//            else
//            {
//                a.getPoint1().setMultiplier(0);
//                simpleLeft = a;
//            }
//        }
//        if (q.getPoint1().getX() == r.getPoint1().getX() && q.getPoint1().getY() == r.getPoint1().getY() 
//                && q.getPoint2().getX() == r.getPoint2().getX() && q.getPoint2().getY() == r.getPoint2().getY())
//        {
//            Segment a = new Segment(q);
//            Segment b = new Segment(r);
//            if (q.getPoint1().getMulitplier() < r.getPoint1().getMulitplier())
//            {
//                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
//                simpleRight = b;
//            }
//            else if (q.getPoint1().getMulitplier() > r.getPoint1().getMulitplier())
//            {
//                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
//                simpleLeft = a;
//            }
//            else
//            {
//                a.getPoint1().setMultiplier(0);
//                simpleRight = a;
//            }
//        }
//        if (q.getPoint1().getX() == s.getPoint1().getX() && q.getPoint1().getY() == s.getPoint1().getY()
//                && q.getPoint2().getX() == s.getPoint2().getX() && q.getPoint2().getY() == s.getPoint2().getY())
//        {
//            Segment a = new Segment(q);
//            Segment b = new Segment(s);
//            if (q.getPoint1().getMulitplier() < s.getPoint1().getMulitplier())
//            {
//                b.getPoint1().setMultiplier(b.getMulitplier() - a.getMulitplier());
//                simpleRight = b;
//            }
//            else if (q.getPoint1().getMulitplier() > s.getPoint1().getMulitplier())
//            {
//                a.getPoint1().setMultiplier(a.getMulitplier() - b.getMulitplier());
//                simpleRight = a;
//            }
//            else
//            {
//                //This should be zero, but on which side?
//            }
//        }
//
//        //simplified = new Equation(simpleLeft, simpleRight);
//
//        simplified = Simplification.simplify(original);
//        Pair<Equation, Equation> equations = new Pair<Equation, Equation>(original, simplified);
//        return equations;
//        /*
//        // Build simplified based on the math above (with gcd, etc.)
//        SegmentEquation simp = new SegmentEquation(Simplification.simplify(original));
//
//        return new Pair<SegmentEquation, SegmentEquation>(original, simp);
//         */
//    }
//
//    //
//    // The external verifier will perform test:     simplification(original) == simp
//    //

}
