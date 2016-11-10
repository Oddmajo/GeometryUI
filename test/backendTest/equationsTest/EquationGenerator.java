package backendTest.equationsTest;

import backend.equations.Equation;
import backend.equations.SegmentEquation;
import backend.equations.operations.Addition;
import backend.instantiator.algebra.Simplification;
import backend.ast.figure.components.Point;
import backend.utilities.exception.ArgumentException;
import backend.ast.figure.components.*;
import backend.utilities.Pair;

import java.util.Random;

public class EquationGenerator
{
    private static Random rng = new Random();
    private static final int MAX_COORD = 10;

    public static int genCoordinate()
    {
        return rng.nextInt() % MAX_COORD;
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
    public static Equation genAdditionEquationPair() throws ArgumentException, CloneNotSupportedException
    {
        //
        // LHS
        //
        Segment p = genSegment(rng.nextInt(MAX_COORD));
        Segment q = genSegment(rng.nextInt(MAX_COORD));

        Addition sumLHS = new Addition(p, q);
        
        //
        // RHS
        //
        Segment r = genSegment(p, rng.nextInt(MAX_COORD)); 
        Segment s = genSegment(q, rng.nextInt(MAX_COORD)); 

        Addition sumRHS = new Addition(r, s);

        Equation original = new Equation(sumLHS, sumRHS);
    
        return original;
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
