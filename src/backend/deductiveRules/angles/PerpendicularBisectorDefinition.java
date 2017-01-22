package backend.deductiveRules.angles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class PerpendicularBisectorDefinition extends Definition
{
    private static final String NAME = "Perpendicular Bisector Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PERPENDICULAR_BISECTOR_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public PerpendicularBisectorDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PERPENDICULAR_BISECTOR_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromPerpendicularBisector());

        return deductions;
    }
    
    public Set<Deduction> deduceFromPerpendicularBisector()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        List<PerpendicularBisector> perpBisectors = _qhg.getPerpendicularBisectors();      
        List<Strengthened> strengs = _qhg.getStrengthenedPerpendicularBisectors();
        
        for (PerpendicularBisector perpBisector : perpBisectors)
        {
            deductions.addAll(deduceFromPerpendicularBisector(perpBisector, perpBisector));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceFromPerpendicularBisector((PerpendicularBisector)streng.getStrengthened(), streng));
        }

        return deductions;
    }
    
    //
    // PerpendicularBisector(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D))) ->
    //                         Perpendicular(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D))),
    //                         SegmentBisector(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D)))
    //
    public static Set<Deduction> deduceFromPerpendicularBisector(PerpendicularBisector pb, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Strengthened streng1 = new Strengthened(pb.getoriginalInter(), new Perpendicular(pb.getoriginalInter()));
        Strengthened streng2 = new Strengthened(pb.getoriginalInter(), new SegmentBisector(pb.getoriginalInter(), pb.getBisector()));

        deductions.add(new Deduction(original, streng1, ANNOTATION));
        deductions.add(new Deduction(original, streng2, ANNOTATION));

        return deductions;
    }

}
