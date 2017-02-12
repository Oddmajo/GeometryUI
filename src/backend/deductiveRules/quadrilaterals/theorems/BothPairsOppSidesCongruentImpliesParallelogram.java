package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class BothPairsOppSidesCongruentImpliesParallelogram extends Theorem
{
    private static final String NAME = "Both Pairs Opp Sides Congruent Implies Parallelogram Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.OPPOSITE_SIDES_CONGRUENT_IMPLIES_PARALLELOGRAM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public BothPairsOppSidesCongruentImpliesParallelogram(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.OPPOSITE_SIDES_CONGRUENT_IMPLIES_PARALLELOGRAM;
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
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Quadrilateral> quads = _qhg.getQuadrilaterals();      
        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();

        // get list
        Object[] congruentSegmentList = congruentSegments.toArray();

        for (Quadrilateral q : quads)
        {
            for (int i = 0; i < congruentSegmentList.length - 1; i++)
            {
                for (int j = i + 1; j < congruentSegmentList.length; j++)
                {
                    deductions.addAll(deduceTheorem(q, (CongruentSegments) congruentSegmentList[i], (CongruentSegments) congruentSegmentList[j]));
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(Quadrilateral quad, CongruentSegments cs1, CongruentSegments cs2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Are the pairs on the opposite side of this quadrilateral?
        if (!quad.hasOppositeCongruentSides(cs1)) return deductions;
        if (!quad.hasOppositeCongruentSides(cs2)) return deductions;

        //
        // Create the new Rhombus object
        //
        Strengthened newParallelogram = new Strengthened(quad, new Parallelogram(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(cs1);
        antecedent.add(cs2);

        deductions.add(new Deduction(antecedent, newParallelogram, ANNOTATION));

        return deductions;
    }

}
