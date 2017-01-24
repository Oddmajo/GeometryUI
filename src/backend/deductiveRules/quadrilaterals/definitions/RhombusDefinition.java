package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class RhombusDefinition extends Definition
{
    private static final String NAME = "Definition of Rhombus";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.RHOMBUS_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public RhombusDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.RHOMBUS_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromRhombus());
        deductions.addAll(deduceToRhombus());

        return deductions;
    }

    //  A ________________  B
    //   |                |
    //   |                |
    //   |                |
    // D |________________| C
    //
    // Rhombus(A, B, C, D) -> 4 C 2 congruent segment pairings
    //
    public Set<Deduction> deduceFromRhombus()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Rhombus> rhombuses = _qhg.getRhombuses();
        List<Strengthened> strengs = _qhg.getStrengthenedRhombuses();

        for (Rhombus rhombus : rhombuses)
        {
            deductions.addAll(deduceFromRhombus(rhombus, rhombus));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromRhombus((Rhombus)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromRhombus(Rhombus rhombus, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // For hypergraph
        // Determine the CongruentSegments : 4 Choose 2
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.top, rhombus.bottom), ANNOTATION));
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.top, rhombus.left), ANNOTATION));
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.top, rhombus.right), ANNOTATION));
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.bottom, rhombus.left), ANNOTATION));
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.bottom, rhombus.right), ANNOTATION));
        deductions.add(new Deduction(original, new GeometricCongruentSegments(rhombus.left, rhombus.right), ANNOTATION));

        return deductions;
    }

    //     A _________________ B
    //      /                /
    //     /                /
    //    /                /
    // D /________________/ C
    //
    // Quadrilateral(A, B, C, D), Congruent(Segment(A, B), Segment(C, D)),
    // Congruent(Segment(C, D), Segment(A, D)), Congruent(Segment(A, D), Segment(B, C)) -> Rhombus(A, B, C, D)
    //
    public Set<Deduction> deduceToRhombus()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Quadrilateral> quadrilaterals = _qhg.getQuadrilaterals();
        List<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();

        for (Quadrilateral quadrilateral : quadrilaterals)
        {
            if (quadrilateral.IsStrictQuadrilateral())
            {
                for (int i = 0; i < congruentSegments.size() - 2; i++)
                {
                    for (int j = i + 1; j < congruentSegments.size() - 1; j++)
                    {
                        for (int k = j + 1; k < congruentSegments.size(); k++)
                        {
                            deductions.addAll(deduceToRhombus(quadrilateral, congruentSegments.get(i),
                                                              congruentSegments.get(j), congruentSegments.get(k)));
                        }
                    }
                }
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToRhombus(Quadrilateral quad, CongruentSegments cs1,
            CongruentSegments cs2, CongruentSegments cs3)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // The 3 congruent segments pairs must relate; one pair must link the two others.
        // Determine the link segments as well as the opposite sides.
        //
        CongruentSegments link = null;
        CongruentSegments opp1 = null;
        CongruentSegments opp2 = null;
        if (cs1.SharedSegment(cs2) != null && cs1.SharedSegment(cs3) != null)
        {
            link = cs1;
            opp1 = cs2;
            opp2 = cs3;
        }
        else if (cs2.SharedSegment(cs1) != null && cs2.SharedSegment(cs3) != null)
        {
            link = cs2;
            opp1 = cs1;
            opp2 = cs3;
        }
        else if (cs3.SharedSegment(cs1) != null && cs3.SharedSegment(cs2) != null)
        {
            link = cs3;
            opp1 = cs1;
            opp2 = cs2;
        }
        else return deductions;

        // Are the pairs on the opposite side of this quadrilateral?
        if (!quad.hasOppositeCongruentSides(opp1)) return deductions;
        if (!quad.hasOppositeCongruentSides(opp2)) return deductions;

        //
        // Create the new Rhombus object
        //
        Strengthened newRhombus = new Strengthened(quad, new Rhombus(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(cs1);
        antecedent.add(cs2);
        antecedent.add(cs3);

        deductions.add(new Deduction(antecedent, newRhombus, ANNOTATION));

        return deductions;
    }
}
