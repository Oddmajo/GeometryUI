package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.GeometricSegmentEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.symbolicAlgebra.equations.operations.Multiplication;

public class MedianTrapezoidHalfSumBases extends Theorem
{

    private static final String NAME = "The Median of a Trapezoid is Half the Length of the Sum of the Bases";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_TRAPEZOID_LENGTH_HALF_SUM_BASES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public MedianTrapezoidHalfSumBases(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.MEDIAN_TRAPEZOID_LENGTH_HALF_SUM_BASES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    
    //  A    _______  B
    //      /       \
    //   Y /_________\ Z
    //    /           \
    // D /_____________\ C
    //
    // Trapezoid(A, B, C, D), Median(Y, Z) -> 2 * Segment(Y, Z) = Segment(A, B) + Segment(C, D)
    //
    public static Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Trapezoid> traps = _qhg.getTrapezoids();
        HashSet<Strengthened> strengs = _qhg.getStrengthenedTrapezoids();
        
        for(Trapezoid trap : traps)
        {
            deductions.addAll(deduceToTheorem(trap, trap));
        }
        
        for(Strengthened streng : strengs)
        {
            deductions.addAll(deduceToTheorem((Trapezoid)streng.getStrengthened(), streng));
        }
        return deductions;
    }

    private static Set<Deduction> deduceToTheorem(Trapezoid trapezoid, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // If median has not been checked, check now
        if (!trapezoid.IsMedianChecked()) trapezoid.FindMedian();
        // Generate only if the median is valid (exists in the original figure)
        if (!trapezoid.IsMedianValid()) return deductions;

        Addition sum = new Addition(trapezoid.getBaseSegment(), trapezoid.getOppBaseSegment());
        Multiplication product = new Multiplication(new NumericValue(2), trapezoid.getMedian());

        GeometricSegmentEquation gseq = new GeometricSegmentEquation(product, sum);

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gseq, ANNOTATION));

        return deductions;
    }

}
