package backend.deductiveRules.quadrilaterals.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class ParallelogramDefinition extends Definition
{
    private static final String NAME = "Definition of Parallelogram";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PARALLELOGRAM_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public ParallelogramDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.PARALLELOGRAM_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromParallelogram());
        deductions.addAll(deduceToParallelogram());

        return deductions;
    }

    //     A _________________ B
    //      /                /
    //     /                /
    //    /                /
    // D /________________/ C
    //
    // Parallelogram(A, B, C, D) -> Parallel(Segment(A, B), Segment(C, D)), Parallel(Segment(A, D), Segment(B, C))
    //
    public Set<Deduction> deduceFromParallelogram()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Parallelogram> parallelograms = _qhg.getParallelograms();
        List<Strengthened> strengs = _qhg.getStrengthenedParallelograms();

        for (Parallelogram parallelogram : parallelograms)
        {
            deductions.addAll(deduceFromParallelogram(parallelogram, parallelogram));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromParallelogram((Parallelogram)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromParallelogram(Parallelogram parallelogram, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine the parallel opposing sides and output that.
        //
        Parallel newParallel1 = new Parallel(parallelogram.top, parallelogram.bottom);
        Parallel newParallel2 = new Parallel(parallelogram.left, parallelogram.right);

        // For hypergraph
        deductions.add(new Deduction(original, newParallel1, ANNOTATION));
        deductions.add(new Deduction(original, newParallel2, ANNOTATION));

        return deductions;
    }

    //     A __________ B
    //      /          \
    //     /            \
    //    /              \
    // D /________________\ C
    //
    //
    // Quadrilateral(A, B, C, D), Parallel(Segment(A, B), Segment(C, D)),
    //                            Parallel(Segment(A, D), Segment(B, C)) -> Parallelogram(A, B, C, D)
    //
    public Set<Deduction> deduceToParallelogram()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Quadrilateral> quadrilaterals = _qhg.getQuadrilaterals();
        List<Parallel> parallels = _qhg.getParallels();

        for (Quadrilateral quadrilateral : quadrilaterals)
        {
            if (quadrilateral.IsStrictQuadrilateral())
            {
                for (int i = 0; i < parallels.size() - 1; i++)
                {
                    for (int j = i + 1; j < parallels.size(); j++)
                    {
                        deductions.addAll(deduceToParallelogram(quadrilateral, parallels.get(i), parallels.get(j)));
                    }   
                }
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToParallelogram(Quadrilateral quad, Parallel parallel1, Parallel parallel2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this paralle set apply to this triangle?
        if (!quad.hasOppositeParallelSides(parallel1)) return deductions;
        if (!quad.hasOppositeParallelSides(parallel2)) return deductions;

        //
        // Create the new Parallelogram object
        //
        Strengthened newParallelogram = new Strengthened(quad, new Parallelogram(quad));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(quad);
        antecedent.add(parallel1);
        antecedent.add(parallel2);

        deductions.add(new Deduction(antecedent, newParallelogram, ANNOTATION));

        return deductions;
    }
}
