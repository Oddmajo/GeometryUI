package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class OppositeSidesOfParallelogramAreCongruent extends Theorem
{
    private static final String NAME = "Opposite Sides of a Parallelogram are Congruent";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.OPPOSITE_SIDES_PARALLELOGRAM_ARE_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public OppositeSidesOfParallelogramAreCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.OPPOSITE_SIDES_PARALLELOGRAM_ARE_CONGRUENT;
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
    // Parallelogram(A, B, C, D) -> Congruent(Segment(A, B), Segment(C, D)), Congruent(Segment(A, D), Segment(B, C))
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
        GeometricCongruentSegments gcs1 = new GeometricCongruentSegments(parallelogram.top, parallelogram.bottom);
        GeometricCongruentSegments gcs2 = new GeometricCongruentSegments(parallelogram.left, parallelogram.right);

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gcs1, ANNOTATION));
        deductions.add(new Deduction(antecedent, gcs2, ANNOTATION));

        return deductions;
    }


}
