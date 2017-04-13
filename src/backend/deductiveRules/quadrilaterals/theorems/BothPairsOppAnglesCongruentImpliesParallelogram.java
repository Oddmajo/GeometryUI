package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class BothPairsOppAnglesCongruentImpliesParallelogram extends Theorem
{
    private static final String NAME = "Both Pairs Opp Angles Congruent Implies Parallelogram Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.OPPOSITE_ANGLES_CONGRUENT_IMPLIES_PARALLELOGRAM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public BothPairsOppAnglesCongruentImpliesParallelogram(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.OPPOSITE_ANGLES_CONGRUENT_IMPLIES_PARALLELOGRAM;
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
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();

        // get list
        Object[] congruentAnglesList = congruentAngles.toArray();
        for (Quadrilateral q : quads)
        {
            for (int i = 0; i < congruentAnglesList.length - 1; i++)
            {
                for (int j = i + 1; j < congruentAnglesList.length; j++)
                {
                    deductions.addAll(deduceTheorem(q, (CongruentAngles) congruentAnglesList[i], (CongruentAngles) congruentAnglesList[j]));
                }
            }
        }

        return deductions;
    }
    
    private static Set<Deduction> deduceTheorem(Quadrilateral quad, CongruentAngles cas1, CongruentAngles cas2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Are the pairs on the opposite side of this quadrilateral?
        if (!quad.HasOppositeCongruentAngles(cas1)) return deductions;
        if (!quad.HasOppositeCongruentAngles(cas2)) return deductions;

        //
        // Create the new Rhombus object
        //
        Strengthened newParallelogram = new Strengthened(quad, new Parallelogram(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(cas1);
        antecedent.add(cas2);

        deductions.add(new Deduction(antecedent, newParallelogram, ANNOTATION));

        return deductions;
    }

}
