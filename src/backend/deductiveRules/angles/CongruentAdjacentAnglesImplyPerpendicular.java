package backend.deductiveRules.angles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CongruentAdjacentAnglesImplyPerpendicular extends Theorem
{
    private static final String NAME = "Congruent Adjacent Angles Imply Perpendicular Segments";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CONGRUENT_ADJACENT_ANGLES_IMPLY_PERPENDICULAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public CongruentAdjacentAnglesImplyPerpendicular(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_ADJACENT_ANGLES_IMPLY_PERPENDICULAR;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(duduceImpliedPerpendiculars());
        
        return deductions;
    }

    //
    // Intersection(M, Segment(A,B), Segment(C, D)),
    // Congruent(Angle(C, M, B), Angle(D, M, B)) -> Perpendicular(Segment(A, B), Segment(C, D))
    //
    //                                            B
    //                                           /
    //                              C-----------/-----------D
    //                                         / M
    //                                        /
    //  
    public Set<Deduction> duduceImpliedPerpendiculars()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        Set<CongruentAngles> conAngles = _qhg.getCongruentAngles();
        List<Intersection> inters = _qhg.getIntersections();
        
        for(CongruentAngles ca : conAngles)
        {
            // We are interested in adjacent angles, not reflexive
            if (ca.isReflexive()) return deductions;

            // Any congruent angles need to be adjacent to each other.
            if (ca.AreAdjacent() == null) return deductions;
            
            for(Intersection inter : inters)
            {
                deductions.addAll(CheckAndGenerateCongruentAdjacentImplyPerpendicular(inter, ca));
            }
        }

        return deductions;
    }
    

    private static Set<Deduction> CheckAndGenerateCongruentAdjacentImplyPerpendicular(Intersection intersection, CongruentAngles conAngles)
    {
        HashSet<Deduction> newGrounded = new HashSet<Deduction> ();

        // The given angles must belong to the intersection. That is, the vertex must align and all rays must overlay the intersection.
        if (!intersection.InducesBothAngles(conAngles)) return newGrounded;

        //
        // Now we have perpendicular scenario
        //
        Strengthened streng = new Strengthened(intersection, new Perpendicular(intersection));

        // Construct hyperedge
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(intersection);
        antecedent.add(conAngles);

        newGrounded.add(new Deduction(antecedent, streng, ANNOTATION));

        return newGrounded;
    }
}
