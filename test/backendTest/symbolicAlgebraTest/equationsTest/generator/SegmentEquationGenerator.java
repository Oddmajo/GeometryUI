package backendTest.symbolicAlgebraTest.equationsTest.generator;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Segment;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.*;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.Pair;

public class SegmentEquationGenerator extends EquationGenerator
{
    protected static Multiplication copyMultiplicationWithNewMultiplier(Multiplication in, int mult)
    {
        return new Multiplication(new NumericValue(mult), in.getRightExp());
    }

    //
    // Generate a segment equation of the form ax = b where a, b are integer coefficients
    //
    public Pair<Equation, Equation> generateAXequalsBequation()
    {
        // Generate a and b
        int a = EquationGenerator.genCoordinate();
        int b = EquationGenerator.genCoordinate();

        // Generate equation variable: LHS
        Multiplication x = EquationGenerator.genSegmentGivenMultiplier(a);

        // Generate RHS numeric constant
        NumericValue rhs = new NumericValue(b);

        // Original
        GeometricSegmentEquation original = new GeometricSegmentEquation(x, rhs);

        // Simplified
        Pair<Integer, Integer> reduced = EquationGenerator.reduce(a, b);

        // Simplified: LHS
        Multiplication xSim = copyMultiplicationWithNewMultiplier(x, reduced.first());

        // Simplified: RHS
        NumericValue rhsSim = new NumericValue(reduced.second());

        // Simplified: Final
        SegmentEquation simplified = new SegmentEquation(xSim, rhsSim);

        return new Pair<Equation, Equation>(original, simplified);
    }

    public Pair<Equation, Equation> generateAXequalsBXequation()
    {
        // Generate a and b
        int a = EquationGenerator.genCoordinate();
        int b = EquationGenerator.genCoordinate();

        Segment x = EquationGenerator.genSegment();
        // Generate equation variable: LHS
        Multiplication lhs = EquationGenerator.genSegmentGivenMultiplierAndSegment(a, x);

        // Generate RHS numeric constant
        Multiplication rhs = EquationGenerator.genSegmentGivenMultiplierAndSegment(b, x);

        // Original
        GeometricSegmentEquation original = new GeometricSegmentEquation(lhs, rhs);

        // Simplified
        Pair<Integer, Integer> reduced = EquationGenerator.reduce(a, b);

        // Simplified: LHS
        Multiplication lhsSim = copyMultiplicationWithNewMultiplier(lhs, reduced.first());

        // Simplified: RHS
        Multiplication rhsSim = copyMultiplicationWithNewMultiplier(rhs, reduced.second());

        // Simplified: Final
        SegmentEquation simplified = new SegmentEquation(lhsSim, rhsSim);

        return new Pair<Equation, Equation>(original, simplified);  
    }

    public Pair<Equation, Equation> generateAXplusBYequalsZeroequation()
    {
        // Generate a and b
        int a = EquationGenerator.genCoordinate();
        int b = EquationGenerator.genCoordinate();

        Segment x, y;
        do
        {
            x = EquationGenerator.genSegment();
            y = EquationGenerator.genSegment();
        } while (x.equals(y));

        // Generate equation variable: LHS
        Multiplication firstSegment = EquationGenerator.genSegmentGivenMultiplierAndSegment(a, x);

        // Generate RHS numeric constant
        Multiplication secondSegment = EquationGenerator.genSegmentGivenMultiplierAndSegment(b, y);

        //Create the two sides
        Addition lhs = new Addition(firstSegment, secondSegment);
        NumericValue zero = new NumericValue(0);

        // Original
        GeometricSegmentEquation original = new GeometricSegmentEquation(lhs, zero);

        // Simplified
        Pair<Integer, Integer> reduced = EquationGenerator.reduce(a, b);

        // Simplified: X
        Multiplication firstSegmentSim = copyMultiplicationWithNewMultiplier(firstSegment, 1);

        // Simplified: Y
        Multiplication secondSegmentSim = copyMultiplicationWithNewMultiplier(secondSegment, 1);

        //Simplified: LHS
        Addition lhsSim = new Addition(firstSegmentSim, secondSegmentSim);

        // Simplified: Final
        SegmentEquation simplified = new SegmentEquation(lhsSim, zero);

        return new Pair<Equation, Equation>(original, simplified);  
    }

    public Pair<Equation, Equation> generateAXplusBYequalsCZequation()
    {
        // Generate a, b, and c
        int a = EquationGenerator.genCoordinate();
        int b = EquationGenerator.genCoordinate();
        int c = EquationGenerator.genCoordinate();

        Segment x, y, z;
        do
        {
            x = EquationGenerator.genSegment();
            y = EquationGenerator.genSegment();
            z = EquationGenerator.genSegment();
        } while (x.equals(y) || x.equals(z) || y.equals(z));

        // Generate equation variable: LHS
        Multiplication firstSegment = EquationGenerator.genSegmentGivenMultiplierAndSegment(a, x);

        // Generate RHS numeric constant
        Multiplication secondSegment = EquationGenerator.genSegmentGivenMultiplierAndSegment(b, y);

        //Create the two sides
        Addition lhs = new Addition(firstSegment, secondSegment);
        Multiplication rhs = EquationGenerator.genSegmentGivenMultiplierAndSegment(c, z);

        // Original
        GeometricSegmentEquation original = new GeometricSegmentEquation(lhs, rhs);

        // Simplified
        ArrayList<Integer> values = new ArrayList<Integer>();
        values.add(a);
        values.add(b);
        values.add(c);
        
        List<Integer> reduced = EquationGenerator.reduce(values);

        // Simplified: X
        Multiplication firstSegmentSim = copyMultiplicationWithNewMultiplier(firstSegment, reduced.get(0));

        // Simplified: Y
        Multiplication secondSegmentSim = copyMultiplicationWithNewMultiplier(secondSegment, reduced.get(1));

        //Simplified: LHS
        Addition lhsSim = new Addition(firstSegmentSim, secondSegmentSim);
        
        //Simplified: RHS
        Multiplication rhsSim = copyMultiplicationWithNewMultiplier(rhs, reduced.get(2));

        // Simplified: Final
        SegmentEquation simplified = new SegmentEquation(lhsSim, rhsSim);

        return new Pair<Equation, Equation>(original, simplified);  
    }

    public Pair<Equation, Equation> generateAXplusBYequalsCXplusBYequation()
    {
        return null;
    }

}
