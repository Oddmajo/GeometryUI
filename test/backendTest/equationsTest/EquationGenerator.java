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
        System.out.println("P's Multiplier: " + p.getMulitplier());
        Segment q = genSegment(rng.nextInt(MAX_COORD));
        System.out.println("Q's Multiplier: " + q.getMulitplier());
        Addition sumLHS = new Addition(p, q);
        
        //
        // RHS
        //
        Segment r = genSegment(p, rng.nextInt(MAX_COORD));
        System.out.println("R's Multiplier: " + r.getMulitplier());
        Segment s = genSegment(q, rng.nextInt(MAX_COORD)); 
        System.out.println("S's Multiplier: " + s.getMulitplier());

        Addition sumRHS = new Addition(r, s);
        
        Equation original = new Equation(sumLHS, sumRHS);
        
        Equation simplified = null;
        
        simplified = Simplification.simplify(original);

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
