package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class DiagonalsOfKiteArePerpendicular extends Theorem
{
    private static final String NAME = "Diagonals Of Kite Are Perpendicular Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_OF_KITE_ARE_PERPENDICULAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsOfKiteArePerpendicular(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_OF_KITE_ARE_PERPENDICULAR;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // Kite(A, B, C, D) -> Perpendicular(Intersection(Segment(A, C), Segment(B, D))
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Kite> kites = _qhg.getKites();   
        Set<Strengthened> strengs = _qhg.getStrengthenedKites();

        for (Kite k : kites)
        {
            deductions.addAll(deduceTheorem(k, k));
        }
        
        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((Kite)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(Kite kite, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Instantiate this kite diagonals ONLY if the original figure has the diagonals drawn.
        if (kite.getDiagonalIntersection() == null) return deductions;

        // Determine the CongruentSegments opposing sides and output that.
        Strengthened newPerpendicular = new Strengthened(kite.getDiagonalIntersection(), new Perpendicular(kite.getDiagonalIntersection()));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newPerpendicular, ANNOTATION));

        return deductions;
    }

}
