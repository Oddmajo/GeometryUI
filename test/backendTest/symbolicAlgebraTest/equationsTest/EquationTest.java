package backendTest.symbolicAlgebraTest.equationsTest;

import backend.symbolicAlgebra.equations.Equation;

public abstract class EquationTest
{
    public static void report(Equation original, Equation expected)
    {
        System.out.println("Simplification test; given " + original.toString() +
                                          "; expected: " + expected.toString());        
    }
    
    public static void reportFailure(Equation original, Equation simplified, Equation expected)
    {
        System.out.println("Simplification FAILED; given " + original.toString() +
                                             "; acquired " + simplified.toString() +
                                            "; expected: " + expected.toString());        
    }
    
    public static void reportSuccess(Equation original, Equation simplified, Equation expected)
    {
        System.out.println("Simplification SUCCEEDED; given " + original.toString() +
                                                "; acquired " + simplified.toString() +
                                               "; expected: " + expected.toString());        
    }
}
