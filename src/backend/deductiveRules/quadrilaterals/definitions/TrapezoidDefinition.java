package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.exception.ExceptionHandler;

public class TrapezoidDefinition extends Definition
{
    private static final String NAME = "Definition of Trapezoid";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRAPEZOID_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public TrapezoidDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.TRAPEZOID_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromTrapezoid());
        deductions.addAll(deduceToTrapezoid());

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    // Trapezoid(A, B, C, D) -> Parallel(Segment(A, B), Segment(C, D))
    //
    public Set<Deduction> deduceFromTrapezoid()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Trapezoid> trapezoids = _qhg.getTrapezoids();
        List<Strengthened> strengs = _qhg.getStrengthenedTrapezoids();

        for (Trapezoid trapezoid : trapezoids)
        {
            deductions.addAll(deduceFromTrapezoid(trapezoid, trapezoid));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromTrapezoid((Trapezoid)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromTrapezoid(Trapezoid trapezoid, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine the parallel opposing sides and output that.
        //
        GeometricParallel newParallel = new GeometricParallel(trapezoid.getBaseSegment(), trapezoid.getOppBaseSegment());

        // For hypergraph
        deductions.add(new Deduction(original, newParallel, ANNOTATION));

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    //
    // Quadrilateral(A, B, C, D), Parallel(Segment(A, B), Segment(C, D)) -> Trapezoid(A, B, C, D)
    //
    public Set<Deduction> deduceToTrapezoid()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Quadrilateral> quadrilaterals = _qhg.getQuadrilaterals();
        List<Parallel> parallels = _qhg.getParallels();
        
        for (Quadrilateral quadrilateral : quadrilaterals)
        {
            for (Parallel parallel : parallels)
            {            
                deductions.addAll(deduceToTrapezoid(quadrilateral, parallel));
            }
        }

        return deductions;
    }

    private HashSet<Deduction> deduceToTrapezoid(Quadrilateral quadrilateral, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // Does this parallel set apply to this quadrilateral?
        if (!quadrilateral.hasOppositeParallelSides(parallel)) return InstantiateToTrapezoidSubsegments(quadrilateral, parallel);

        //
        // The other set of sides should NOT be parallel (that's a parallelogram)
        //
        List<Segment> otherSides = quadrilateral.GetOtherSides(parallel);

        if (otherSides.size() != 2)
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Expected TWO sides returned from a quadrilateral / parallel relationship: " + quadrilateral + " " + parallel));
        }

        if (otherSides.get(0).isParallel(otherSides.get(1))) return deductions;

        return MakeTrapezoid(quadrilateral, parallel);
    }
    
    //
    // Are the parallel sides subsegments of sides of the quadrilateral?
    // If so, instantiate.
    //
    private HashSet<Deduction> InstantiateToTrapezoidSubsegments(Quadrilateral quad, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this parallel set apply to this quadrilateral?
        if (!quad.hasOppositeParallelSubsegmentSides(parallel)) return deductions;

        //
        // The other set of sides should NOT be parallel (that's a parallelogram)
        //
        List<Segment> otherSides = quad.GetOtherSubsegmentSides(parallel);

        if (otherSides.size() != 2)
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Expected TWO sides returned from a quadrilateral / parallel relationship: "
                                        + quad + " " + parallel + " returned " + otherSides.size()));
        }

        if (otherSides.get(0).isParallel(otherSides.get(1))) return deductions;

        return MakeTrapezoid(quad, parallel);
    }
    
    private static HashSet<Deduction> MakeTrapezoid(Quadrilateral quad, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Create the new Trapezoid object
        //
        Strengthened newTrapezoid = new Strengthened(quad, new Trapezoid(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(parallel);

        deductions.add(new Deduction(antecedent, newTrapezoid, ANNOTATION));

        return deductions;
    }
}
