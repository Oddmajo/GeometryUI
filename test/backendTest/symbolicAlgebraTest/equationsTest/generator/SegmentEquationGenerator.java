package backendTest.symbolicAlgebraTest.equationsTest.generator;

import backend.ast.figure.components.Segment;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.*;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.Pair;

public class SegmentEquationGenerator extends EquationGenerator
{
//    protected static Segment copySegmentWithNewMultiplier(Segment in, int mult)
//    {
//        Segment seg = new Segment(in);
//        
//        seg.setMultiplier(mult);
//
//        return seg; 
//    }
    
    protected static Multiplication copyAndBuildProduct(Segment in, int multiplier)
    {
        Segment seg = new Segment(in);

        NumericValue num = new NumericValue(multiplier);
        
        Multiplication product = new Multiplication(num, seg);

        return product;
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
        Segment x = EquationGenerator.genSegment();
        Multiplication lhs = copyAndBuildProduct(x, a);

        // Generate RHS numeric constant
        NumericValue rhs = new NumericValue(b);

        // Original
        GeometricSegmentEquation original = new GeometricSegmentEquation(lhs, rhs);

        // Simplified
        Pair<Integer, Integer> reduced = EquationGenerator.reduce(a, b);

        // Simplified: LHS
        Multiplication lhsSim = copyAndBuildProduct(x, reduced.first());

        // Simplified: RHS
        NumericValue rhsSim = new NumericValue(reduced.second());

        // Simplified: Final
        SegmentEquation simplified = new SegmentEquation(lhsSim, rhsSim);

        return new Pair<Equation, Equation>(original, simplified);
    }

    public Pair<Equation, Equation> generateAXequalsBXequation()
    {
        return null;    
    }

    public Pair<Equation, Equation> generateAXplusBYequalsZeroequation()
    {
        return null;
    }

    public Pair<Equation, Equation> generateAXplusBYequalsCZequation()
    {
        return null;
    }

    public Pair<Equation, Equation> generateAXplusBYequalsCXplusBYequation()
    {
        return null;
    }

}
