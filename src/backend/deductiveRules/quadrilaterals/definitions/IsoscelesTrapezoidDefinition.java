package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class IsoscelesTrapezoidDefinition extends Definition
{
    private static final String NAME = "Isosceles Trapezoid Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ISOSCELES_TRAPEZOID_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public IsoscelesTrapezoidDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.ISOSCELES_TRAPEZOID_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Forward and Backward deduction
        deductions.addAll(deduceFromIsoscelesTrapezoid());
        deductions.addAll(deduceToIsoscelesTrapezoid());

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    // IsoscelesTrapezoid(A, B, C, D) -> CongruentSegments(Segment(A, D), Segment(B, C))
    //    
    private Set<Deduction> deduceFromIsoscelesTrapezoid()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<IsoscelesTrapezoid> isoTraps = _qhg.getIsoscelesTrapezoids();
        Set<Strengthened> strengs = _qhg.getStrengthenedIsoscelesTrapezoids();

        for (IsoscelesTrapezoid isoTrap : isoTraps)
        {
            deductions.addAll(deduceFromIsoscelesTrapezoid(isoTrap, isoTrap));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromIsoscelesTrapezoid((IsoscelesTrapezoid)streng.getStrengthened(), streng));
        }
        
        return deductions;
    }

    private HashSet<Deduction> deduceFromIsoscelesTrapezoid(Trapezoid trapezoid, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Create the congruent, opposing side segments
        GeometricCongruentSegments gcs = new GeometricCongruentSegments(trapezoid.getLeftLeg(), trapezoid.getRightLeg());

        deductions.add(new Deduction(original, gcs, ANNOTATION));

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    //
    // Trapezoid(A, B, C, D), CongruentSegments(Segment(A, D), Segment(B, C)) -> IsoscelesTrapezoid(A, B, C, D)
    //
    private Set<Deduction> deduceToIsoscelesTrapezoid()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Trapezoid> trapezoids = _qhg.getTrapezoids();
        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();
        Set<Strengthened> strengs = _qhg.getStrengthenedTrapezoids();

        for (Trapezoid trapezoid : trapezoids)
        {
            for (CongruentSegments congruentSegment : congruentSegments)
            {
                deductions.addAll(deduceToIsoscelesTrapezoid(trapezoid, congruentSegment, trapezoid));
            }
        }

        for (Strengthened streng : strengs)
        {
            for (CongruentSegments congruentSegment : congruentSegments)
            {
                deductions.addAll(deduceToIsoscelesTrapezoid((Trapezoid)streng.getStrengthened(), congruentSegment, streng));
            }
        }
        
        return deductions;
    }
    
    private Set<Deduction> deduceToIsoscelesTrapezoid(Trapezoid trapezoid, CongruentSegments css, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this parallel set apply to this triangle?
        if (!trapezoid.createsCongruentLegs(css)) return deductions;

        //
        // Create the new IsoscelesTrapezoid object
        //
        Strengthened newIsoscelesTrapezoid = new Strengthened(trapezoid, new IsoscelesTrapezoid(trapezoid));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);
        antecedent.add(css);

        deductions.add(new Deduction(antecedent, newIsoscelesTrapezoid, ANNOTATION));

        return deductions;
    }
}
