package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class DiagonalsParallelogramBisectEachOther extends Theorem
{

    private static final String NAME = "Diagonals of a Parallelogram Bisect Each Other";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_PARALLELOGRAM_BISECT_EACH_OTHER);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsParallelogramBisectEachOther(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_PARALLELOGRAM_BISECT_EACH_OTHER;
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
    // Parallelogram(A, B, C, D) -> SegmentBisector(Segment(A, C), Segment(B, D)), SegmentBisector(Segment(B, D), Segment(A, C)),
    //
    public static Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Parallelogram> parals = _qhg.getParallelograms();
        HashSet<Strengthened> strengs = _qhg.getStrengthenedParallelograms();
        
        for(Parallelogram paral : parals)
        {
            deductions.addAll(deduceTheorem(paral, paral));
        }
        
        for(Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((Parallelogram)streng.getStrengthened(), streng));
        }
        return deductions;
    }

    //     A _________________ B
    //      /                /
    //     /     \/         /
    //    /      /\        /
    // D /________________/ C
    //
    // Parallelogram(A, B, C, D) -> SegmentBisector(Segment(A, C), Segment(B, D)), SegmentBisector(Segment(B, D), Segment(A, C)),
    //
    private static Set<Deduction> deduceTheorem(Parallelogram parallelogram, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Generate only if the diagonals are a part of the original figure.
        if (parallelogram.getDiagonalIntersection() == null) return deductions;

        // Determine the CongruentSegments opposing sides and output that.
        Intersection diagInter = parallelogram.getDiagonalIntersection();
        Strengthened sb1 = new Strengthened(diagInter, new SegmentBisector(diagInter, diagInter.getlhs()));
        Strengthened sb2 = new Strengthened(diagInter, new SegmentBisector(diagInter, diagInter.getrhs()));

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, sb1, ANNOTATION));
        deductions.add(new Deduction(antecedent, sb2, ANNOTATION));

        return deductions;
    }
}
