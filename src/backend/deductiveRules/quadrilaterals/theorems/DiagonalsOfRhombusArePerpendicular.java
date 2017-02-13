package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class DiagonalsOfRhombusArePerpendicular extends Theorem
{
    private static final String NAME = "Diagonals Of Rhombus Are Perpendicular";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.DIAGONALS_OF_RHOMBUS_ARE_PERPENDICULAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public DiagonalsOfRhombusArePerpendicular(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.DIAGONALS_OF_RHOMBUS_ARE_PERPENDICULAR;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //  A __________  B
    //   |          |
    //   |          |
    //   |          |
    // D |__________| C
    //
    // Rhombus(A, B, C, D) -> Perpendicular(Intersection(Segment(A, C), Segment(B, D))
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Rhombus> rhombuses = _qhg.getRhombuses();
        Set<Strengthened> strengs = _qhg.getStrengthenedRhombuses();

        for (Rhombus r : rhombuses)
        {
            deductions.addAll(deduceTheorem(r, r));
        }
        
        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((Rhombus)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(Rhombus rhombus, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Instantiate this rhombus diagonals ONLY if the original figure has the diagonals drawn.
        if (rhombus.getDiagonalIntersection() == null) return deductions;

        // Determine the CongruentSegments opposing sides and output that.
        Strengthened newPerpendicular = new Strengthened(rhombus.getDiagonalIntersection(), new Perpendicular(rhombus.getDiagonalIntersection()));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newPerpendicular, ANNOTATION));

        return deductions;
    }

}
