package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class KiteDefinition extends Definition
{
    private static final String NAME = "Definition of Kite";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.KITE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public KiteDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.KITE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromKite());
        deductions.addAll(deduceToKite());

        return deductions;
    }

    //       A   
    //      /|\ 
    //   D /_|_\ B
    //     \ | /
    //      \|/
    //       C
    // Kite(A, B, C, D) -> Congruent(Segment(A, B), Segment(A, D)), Congruent(Segment(C, D), Segment(C, B))
    //
    public Set<Deduction> deduceFromKite()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Kite> kites = _qhg.getKites();
        List<Strengthened> strengs = _qhg.getStrengthenedKites();

        for (Kite kite : kites)
        {
            deductions.addAll(deduceFromKite(kite, kite));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromKite((Kite)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromKite(Kite kite, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine the CongruentSegments opposing sides and output that.
        //
        GeometricCongruentSegments gcs1 = new GeometricCongruentSegments(kite.getPairASegment1(), kite.getPairASegment2());
        GeometricCongruentSegments gcs2 = new GeometricCongruentSegments(kite.getPairBSegment1(), kite.getPairBSegment2());

        // For hypergraph
        deductions.add(new Deduction(original, gcs1, ANNOTATION));
        deductions.add(new Deduction(original, gcs2, ANNOTATION));

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    //
    // Quadrilateral(A, B, C, D), Congruent(Segment(A, B), Segment(C, D)), Congruent(Segment(A, D), Segment(B, C)) -> Kite(A, B, C, D)
    //
    public Set<Deduction> deduceToKite()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Quadrilateral> quadrilaterals = _qhg.getQuadrilaterals();      
        List<CongruentSegments> cgs = _qhg.getCongruentSegments();

        for (Quadrilateral quadrilateral : quadrilaterals)
        {
            if (quadrilateral.IsStrictQuadrilateral())
            {
                for (int i = 0; i < cgs.size() - 1; i++)
                {
                    for (int j = i + 1; j < cgs.size(); j++)
                    {
                        deductions.addAll(deduceToKite(quadrilateral, cgs.get(i), cgs.get(j)));
                    }   
                }
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToKite(Quadrilateral quad, CongruentSegments cs1, CongruentSegments cs2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The congruences should not share a side.
        if (cs1.SharedSegment(cs2) != null) return deductions;

        // The congruent pairs should not also be congruent to each other
        if (cs1.getcs1().CoordinateCongruent(cs2.getcs1())) return deductions;

        // Does both set of congruent segments apply to the quadrilateral?
        if (!quad.hasAdjacentCongruentSides(cs1)) return deductions;
        if (!quad.hasAdjacentCongruentSides(cs2)) return deductions;

        //
        // Create the new Kite object
        //
        Strengthened newKite = new Strengthened(quad, new Kite(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(cs1);
        antecedent.add(cs2);

        deductions.add(new Deduction(antecedent, newKite, ANNOTATION));

        return deductions;
    }
}
