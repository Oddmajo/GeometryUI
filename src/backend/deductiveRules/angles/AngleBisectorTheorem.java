package backend.deductiveRules.angles;

import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;

public class AngleBisectorTheorem extends Theorem
{
    private static final String NAME = "Angle Bisector Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ANGLE_BISECTOR_THEOREM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public AngleBisectorTheorem(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceFromAngleBisector());
        
        return deductions;
    }

    public Set<Deduction> deduceFromAngleBisector()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<AngleBisector> angleBisectors = _qhg.getAngleBisectors();
                
        for (AngleBisector ab : angleBisectors)
        {
            Set<Deduction> returned = deduceEquations(ab);
            
            if (returned.size() != 2)
            {
                // LoggerFactory.getLogger(LoggerFactory.HYPERGRAPH_EDGE_CONSTRUCTION).writeln(NAME + ": Expect 2 deduced clauses.");
                ExceptionHandler.throwException(NAME + ": Expect 2 deduced clauses in AngleBisectorTheorem.");
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }
    
    //
    // AngleBisector(Segment(A, D), Angle(C, A, B)) -> 2 m\angle CAD = m \angle CAB,
    //                                                2 m\angle BAD = m \angle CAB
    //
    //   A ________________________B
    //    |\
    //    | \ 
    //    |  \
    //    |   \
    //    |    \
    //    C     D
    //
    public static HashSet<Deduction> deduceEquations(AngleBisector angleBisector)
    {
        ANNOTATION.active = RuleFactory.JustificationSwitch.ANGLE_BISECTOR_THEOREM;

        HashSet<Deduction> newGrounded = new HashSet<Deduction>();

        Pair<Angle, Angle> adjacentAngles = angleBisector.getBisectedAngles();

        // Construct 2 m\angle CAD
        Multiplication product1 = new Multiplication(new NumericValue(2), adjacentAngles.getKey());
        // Construct 2 m\angle BAD
        Multiplication product2 = new Multiplication(new NumericValue(2), adjacentAngles.getValue());

        // 2X = AB
        GeometricAngleEquation newEq1 = new GeometricAngleEquation(product1, angleBisector.getAngle());
        GeometricAngleEquation newEq2 = new GeometricAngleEquation(product2, angleBisector.getAngle());

        // For hypergraph
        newGrounded.add(new Deduction(angleBisector, newEq1, ANNOTATION));
        newGrounded.add(new Deduction(angleBisector, newEq2, ANNOTATION));
        
        return newGrounded;
    }
}
