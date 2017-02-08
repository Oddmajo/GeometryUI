package backend.deductiveRules.parallelLines.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class SameSideSuppleAnglesImplyParallel extends Theorem
{
    private static String NAME = "Same-Side Interior Angles Formed by a Transversal Are Supplementary Imply Parallel Lines"; 
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SAME_SIDE_SUPPLE_ANGLES_IMPLY_PARALLEL);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public SameSideSuppleAnglesImplyParallel(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.SAME_SIDE_SUPPLE_ANGLES_IMPLY_PARALLEL;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceParallel());

        return deductions;
    }
    
    //
    // Intersection(M, Segment(A,B), Segment(C, D)),
    // Intersection(N, Segment(A,B), Segment(E, F)),
    // Supplmentary(Angle(F, N, M), Angle(D, M, N)) -> Parallel(Segment(C, D), Segment(E, F))
    //
    //                                            B
    //                                           /
    //                              C-----------/-----------D
    //                                         / M
    //                                        /
    //                             E---------/-----------F
    //                                      / N
    //                                     A
    //
    public static Set<Deduction> deduceParallel()
    {
        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<>();
        HashSet<Supplementary> supps = _qhg.getSupplementaryAngles();
        HashSet<Intersection> inters = _qhg.getIntersections();
        Object[] aInters = inters.toArray();
        
        for(Supplementary supp : supps)
        {
            for (int i = 0; i < aInters.length; i++)
            {
                for (int j = i + 1; j < aInters.length; j++)
                {
                    deductions.addAll(CheckAndGenerateSameSideInteriorImplyParallel((Intersection)aInters[i], (Intersection)aInters[j], supp));
                }
            }
        }
        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateSameSideInteriorImplyParallel(Intersection inter1, Intersection inter2, Supplementary supp)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Get the transversal (shared segment), if it exists
        Segment transversal = inter1.AcquireTransversal(inter2);
        if (transversal == null) return deductions;

        Angle angleI = inter1.GetInducedNonStraightAngle(supp);
        Angle angleJ = inter2.GetInducedNonStraightAngle(supp);

        //
        // Do we have valid intersections and congruent angle pairs
        //
        if (angleI == null || angleJ == null) return deductions;

        //
        // Check to see if they are, in fact, alternate interior angles respectively
        //
        // Are the angles within the interior 
        Segment parallelCand1 = inter1.OtherSegment(transversal);
        Segment parallelCand2 = inter2.OtherSegment(transversal);

        if (!angleI.OnInteriorOf(inter1, inter2) || !angleJ.OnInteriorOf(inter1, inter2)) return deductions;

        //
        // Are these angles on the opposite side of the transversal?
        //
        // Make a simple transversal from the two intersection points
        Segment simpleTransversal = new Segment(inter1.getIntersect(), inter2.getIntersect());

        // Find the rays the lie on the transversal
        Segment rayNotOnTransversalI = angleI.OtherRay(simpleTransversal);
        Segment rayNotOnTransversalJ = angleJ.OtherRay(simpleTransversal);

        Point pointNotOnTransversalNorVertexI = rayNotOnTransversalI.other(angleI.getVertex());
        Point pointNotOnTransversalNorVertexJ = rayNotOnTransversalJ.other(angleJ.getVertex());

        // Create a segment from these two points so we can compare distances
        Segment crossing = new Segment(pointNotOnTransversalNorVertexI, pointNotOnTransversalNorVertexJ);

        //
        // Will this crossing segment intersect the real transversal in the middle of the two segments? If it DOES NOT, it is same side
        //
        Point intersection = transversal.segmentIntersection(crossing);

        if (Segment.Between(intersection, inter1.getIntersect(), inter2.getIntersect())) return deductions;

        //
        // Now we have an alternate interior scenario
        //
        GeometricParallel newParallel = new GeometricParallel(parallelCand1, parallelCand2);

        // Construct hyperedge
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(inter1);
        antecedent.add(inter2);
        antecedent.add(supp);

        deductions.add(new Deduction(antecedent, newParallel, ANNOTATION));

        return deductions;
    }

}
