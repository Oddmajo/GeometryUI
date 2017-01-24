package backend.deductiveRules.angles;

import java.util.ArrayList;
import java.util.HashSet;
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
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(duduceImpliedPerpendiculars());
        
        return deductions;
    }

    public Set<Deduction> duduceImpliedPerpendiculars()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        Set<CongruentAngles> ca = _qhg.getCongruentAngles();
        
        for(GroundedClause clause : ca)
        {
            deductions.addAll(deduceFromCongruentAngles(clause));
        }
        
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
    //                                       A
    public static Set<Deduction> deduceFromCongruentAngles(GroundedClause c)
    {
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_ADJACENT_ANGLES_IMPLY_PERPENDICULAR;

        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> newGrounded = new HashSet<Deduction>();

        if (c instanceof CongruentAngles)
        {
            CongruentAngles conAngles = (CongruentAngles)c;

            // We are interested in adjacent angles, not reflexive
            if (conAngles.isReflexive()) return newGrounded;

            // Any candidates congruent angles need to be adjacent to each other.
            if (conAngles.AreAdjacent() == null) return newGrounded;

            // Find two candidate lines cut by the same transversal
            for (Intersection inter : candIntersection)
            {
                newGrounded.addAll(CheckAndGenerateCongruentAdjacentImplyPerpendicular(inter, conAngles));
            }

            candAngles.Add(conAngles);
        }
        else if (c instanceof Intersection)
        {
            Intersection newIntersection = (Intersection)c;

            if (!newIntersection.IsStraightAngleIntersection()) return newGrounded;

            for (CongruentAngles cas : candAngles)
            {
                newGrounded.addAll(CheckAndGenerateCongruentAdjacentImplyPerpendicular(newIntersection, cas));
            }

            candIntersection.Add(newIntersection);
        }

        return newGrounded;
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
