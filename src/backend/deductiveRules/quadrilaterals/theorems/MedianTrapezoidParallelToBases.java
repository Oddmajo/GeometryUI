package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class MedianTrapezoidParallelToBases extends Theorem
{

    private static final String NAME = "The Median of a Trapezoid is Parallel to the Bases";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_TRAPEZOID_PARALLEL_TO_BASE);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public MedianTrapezoidParallelToBases(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.MEDIAN_TRAPEZOID_PARALLEL_TO_BASE;
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
    // Trapezoid(A, B, C, D), Median(Y, Z) -> Parallel(Segment(A, B), Segment(Y, Z)), Parallel(Segment(C, D), Segment(Y, Z))
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
        if (!trapezoid.IsMedianChecked()) return deductions;
        // Generate only if the median is valid (exists in the original figure)
        if (!trapezoid.IsMedianValid()) return deductions;

        GeometricParallel newParallel1 = new GeometricParallel(trapezoid.getMedian(), trapezoid.getOppBaseSegment());
        GeometricParallel newParallel2 = new GeometricParallel(trapezoid.getMedian(), trapezoid.getBaseSegment());

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newParallel1, ANNOTATION));
        deductions.add(new Deduction(antecedent, newParallel2, ANNOTATION));

        return deductions;
    }

}
