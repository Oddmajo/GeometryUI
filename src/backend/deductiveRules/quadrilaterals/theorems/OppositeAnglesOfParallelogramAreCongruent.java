package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class OppositeAnglesOfParallelogramAreCongruent extends Theorem
{
    private static final String NAME = "Opposite Angles of a Parallelogram are Congruent";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.OPPOSITE_ANGLES_PARALLELOGRAM_ARE_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public OppositeAnglesOfParallelogramAreCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.OPPOSITE_ANGLES_PARALLELOGRAM_ARE_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    

    //     A _________________ B
    //      /                /
    //     /                /
    //    /                /
    // D /________________/ C
    //
    // Parallelogram(A, B, C, D) -> Congruent(Angle(DAB), Angle(BCD)), Congruent(Angle(ADC), Angle(ABC))
    //
    public static Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Parallelogram> paralls = _qhg.getParallelograms();
        HashSet<Strengthened> strengs = _qhg.getStrengthenedParallelograms();
        
        for(Parallelogram paral : paralls)
        {
            deductions.addAll(deduceTheorem(paral, paral));
        }
        for(Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((Parallelogram)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(Parallelogram parallelogram, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Determine the CongruentSegments opposing sides and output that.
        GeometricCongruentAngles gcas1 = new GeometricCongruentAngles(parallelogram.topLeftAngle, parallelogram.bottomRightAngle);
        GeometricCongruentAngles gcas2 = new GeometricCongruentAngles(parallelogram.bottomLeftAngle, parallelogram.topRightAngle);

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gcas1, ANNOTATION));
        deductions.add(new Deduction(antecedent, gcas2, ANNOTATION));

        return deductions;
    }

}
