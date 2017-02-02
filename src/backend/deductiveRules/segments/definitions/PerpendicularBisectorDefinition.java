package backend.deductiveRules.segments.definitions;

import java.util.HashSet;
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

    private static final String NAME = "Definition of Perpendicular Bisector";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PERPENDICULAR_BISECTOR_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    
    public PerpendicularBisectorDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PERPENDICULAR_BISECTOR_DEFINITION;
    }

    
    //
    // PerpendicularBisector(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D))) ->
    //                         Perpendicular(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D))),
    //                         SegmentBisector(Intersection(X, Segment(A, B), Segment(C, D)), Bisector(Segment(C, D)))
    //
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        HashSet<PerpendicularBisector> PerpendicularBisectors = _qhg.getPerpendicularBisectors();
        HashSet<Strengthened> Strengtheneds = _qhg.getStrengthenedPerpendicularBisectors();
        
        for(PerpendicularBisector pb : PerpendicularBisectors)
        {
            deductions.addAll(deduceFromPerpendicularBisector(pb, pb));
        }
        
        for(Strengthened streng : Strengtheneds)
        {
            deductions.addAll(deduceFromPerpendicularBisector(streng, (PerpendicularBisector)streng.getStrengthened()));
        }  

        return new HashSet<Deduction>();
    }


    public static Set<Deduction> deduceFromPerpendicularBisector(GroundedClause original, PerpendicularBisector pb)
    {
        HashSet<Deduction> newGrounded = new HashSet<Deduction>();
        HashSet<GroundedClause> antecedent = new HashSet<GroundedClause>();
        antecedent.add(original);

        Strengthened streng1 = new Strengthened(pb.getoriginalInter(), new Perpendicular(pb.getoriginalInter()));
        Strengthened streng2 = new Strengthened(pb.getoriginalInter(), new SegmentBisector(pb.getoriginalInter(), pb.getBisector()));

        newGrounded.add(new Deduction(antecedent, streng1, ANNOTATION));
        newGrounded.add(new Deduction(antecedent, streng2, ANNOTATION));

        return newGrounded;
    }


}
