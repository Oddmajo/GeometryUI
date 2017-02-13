package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class OnePairOppSidesCongruentParallelImpliesParallelogram extends Theorem
{

    private static final String NAME = "If One Pair of Opposite Sides of a Quadrilateral are Congruent and Parallel, then the quadrilateral is a Parallelogram";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ONE_PAIR_OPPOSITE_SIDES_CONGRUENT_PARALLEL_IMPLIES_PARALLELOGRAM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public OnePairOppSidesCongruentParallelImpliesParallelogram(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ONE_PAIR_OPPOSITE_SIDES_CONGRUENT_PARALLEL_IMPLIES_PARALLELOGRAM;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    //
    // Quadrilateral(A, B, C, D), Congruent(Segment(A, B), Segment(C, D)), Congruent(Segment(A, D), Segment(B, C)) -> Parallelogram(A, B, C, D)
    //
    public static Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Quadrilateral> quads = _qhg.getQuadrilaterals();
        HashSet<CongruentSegments> conSegs = _qhg.getCongruentSegments();
        HashSet<Parallel> paras = _qhg.getParallels();
        
        for(Quadrilateral quad : quads)
        {
            if (!quad.IsStrictQuadrilateral()) return deductions;

            for (CongruentSegments conSeg : conSegs)
            {
                if(!conSeg.isReflexive())
                {
                    for (Parallel parallel : paras)
                    {
                        deductions.addAll(deduceToTheorem(quad, conSeg, parallel));
                    }
                }
            }
        }
        return deductions;
    }

    private static Set<Deduction> deduceToTheorem(Quadrilateral quad, CongruentSegments cs, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Are the pairs on the opposite side of this quadrilateral?
        if (!quad.hasOppositeCongruentSides(cs)) return deductions;
        if (!quad.hasOppositeParallelSides(parallel)) return deductions;

        // Do the congruent segments coincide with these parallel segments?
        if (!parallel.getSegment1().HasSubSegment(cs.getcs1()) && !parallel.getSegment2().HasSubSegment(cs.getcs1())) return deductions;
        if (!parallel.getSegment1().HasSubSegment(cs.getcs2()) && !parallel.getSegment2().HasSubSegment(cs.getcs2())) return deductions;

        //
        // Create the new Rhombus object
        //
        Strengthened newParallelogram = new Strengthened(quad, new Parallelogram(quad));

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(cs);
        antecedent.add(parallel);

        deductions.add(new Deduction(antecedent, newParallelogram, ANNOTATION));

        return deductions;
    }
}
