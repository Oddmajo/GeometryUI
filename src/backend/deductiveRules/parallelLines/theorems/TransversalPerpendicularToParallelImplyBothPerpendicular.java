package backend.deductiveRules.parallelLines.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class TransversalPerpendicularToParallelImplyBothPerpendicular extends Theorem
{
    private static String NAME = "If a transversal is perpendicular to one of two parallel lines, then it is perpendicular to the other one also."; 
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public TransversalPerpendicularToParallelImplyBothPerpendicular(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deducePerpendicular());

        return deductions;
    }
    
    //
    // Perpendicular(Segment(E, F), Segment(C, D)),
    // Parallel(Segment(E, F), Segment(A, B)),
    // Intersection(M, Segment(A, B), Segment(C, D)) -> Perpendicular(Segment(A, B), Segment(C, D)) 
    //
    //                                   E       B
    //                                   |       |
    //                              C----|-------|--------D
    //                                   | N     | M
    //                                   |       |
    //                                   F       A
    //
    public static Set<Deduction> deducePerpendicular()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Parallel> paras = _qhg.getParallels();
        HashSet<Perpendicular> perps = _qhg.getPerpendicular();
        HashSet<Intersection> inters = _qhg.getIntersections();
        HashSet<Strengthened> strengs = _qhg.getStrengthenedPerpendicular();
        
        for(Parallel para : paras)
        {
            
            for(Perpendicular perp : perps)
            {
                for(Intersection inter : inters)
                {
                    deductions.addAll(CheckAndGeneratePerpendicular(perp, para, inter, perp));
                }
            }
            
            for(Strengthened streng : strengs)
            {
                for(Intersection inter : inters)
                {
                    deductions.addAll(CheckAndGeneratePerpendicular((Perpendicular)streng.getStrengthened(), para, inter, streng));
                }
                
            }
        }
        return deductions;
    }

    private static Set<Deduction> CheckAndGeneratePerpendicular(Perpendicular perp, Parallel parallel, Intersection inter, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The perpendicular intersection must refer to one of the parallel segments
        Segment shared = perp.CommonSegment(parallel);
        if (shared == null) return deductions;

        // The other intersection must refer to a segment in the parallel pair
        Segment otherShared = inter.CommonSegment(parallel);
        if (otherShared == null) return deductions;

        // The two shared segments must be distinct
        if (shared.equals(otherShared)) return deductions;

        // Transversals must align
        if (!inter.OtherSegment(otherShared).equals(perp.OtherSegment(shared))) return deductions;

        // Strengthen the old intersection to be perpendicular
        Strengthened strengthenedPerp = new Strengthened(inter, new Perpendicular(inter));

        // Construct hyperedge
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);
        antecedent.add(parallel);
        antecedent.add(inter);

        deductions.add(new Deduction(antecedent, strengthenedPerp, ANNOTATION));

        return deductions;
    }

}
