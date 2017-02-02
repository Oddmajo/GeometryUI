package backend.deductiveRules.angles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CongruentCorrespondingAnglesImplyParallel extends Axiom
{
    private static final String NAME = "Corresponding Congruent Angles Imply Parallel Lines";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CONGRUENT_CORRESPONDING_ANGLES_IMPLY_PARALLEL);
    @Override public Annotation getAnnotation() { return ANNOTATION; }



    public CongruentCorrespondingAnglesImplyParallel(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_CORRESPONDING_ANGLES_IMPLY_PARALLEL;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        
        deductions.addAll(deduceAngles());
        
        return deductions;
    }
    
    //
    // Intersection(M, Segment(A,B), Segment(C, D)),
    // Intersection(N, Segment(A,B), Segment(E, F)),
    // Congruent(Angle(A, N, F), Angle(A, M, D)) -> Parallel(Segment(C, D), Segment(E, F))
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
    public static Set<Deduction> deduceAngles()
    {

        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<>();
        
        HashSet<CongruentAngles> cangs = _qhg.getCongruentAngles();
        HashSet<Intersection> hInters = _qhg.getIntersections();
        
        Object[] inters = hInters.toArray();
        
        for (CongruentAngles ca : cangs)
        {
            if (ca.isReflexive()) continue;

            // Find two candidate lines cut by the same transversal
            for (int i = 0; i < inters.length - 1; i++)
            {
                for (int j = i + 1; j < inters.length; j++)
                {
                    deductions.addAll(CheckAndGenerateCorrespondingAnglesImplyParallel((Intersection)inters[i], (Intersection)inters[j], ca));
                }
            }
        }


        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateCorrespondingAnglesImplyParallel(Intersection inter1, Intersection inter2, CongruentAngles conAngles)
    {
        HashSet<Deduction> deductions = new HashSet<>();

        // The two intersections should not be at the same vertex
        if (inter1.getIntersect().equals(inter2.getIntersect())) return deductions;

        Segment transversal = inter1.CommonSegment(inter2);

        if (transversal == null) return deductions;

        Angle angleI = inter1.GetInducedNonStraightAngle(conAngles);
        Angle angleJ = inter2.GetInducedNonStraightAngle(conAngles);

        //
        // Do we have valid intersections and congruent angle pairs
        //
        if (angleI == null || angleJ == null) return deductions;

        //
        // Check to see if they are, in fact, Corresponding Angles
        //
        Segment parallelCand1 = inter1.OtherSegment(transversal);
        Segment parallelCand2 = inter2.OtherSegment(transversal);

        // The resultant candidate parallel segments shouldn't share any vertices
        if (parallelCand1.sharedVertex(parallelCand2) != null) return deductions;

        // Numeric hack to discount any non-parallel segments
        if (!parallelCand1.isParallel(parallelCand2)) return deductions;

        // A sanity check that the '4th' point does not lie on the other intersection (thus creating an obvious triangle and thus not parallel lines)
        Point fourthPoint1 = parallelCand1.other(inter1.getIntersect());
        Point fourthPoint2 = parallelCand2.other(inter2.getIntersect());
        if (fourthPoint1 != null && fourthPoint2 != null)
        {
            if (parallelCand1.pointLiesOn(fourthPoint2) || parallelCand2.pointLiesOn(fourthPoint1)) return deductions;
        }

        // Both angles should NOT be in the interioir OR the exterioir; a combination is needed.
        boolean ang1Interior = angleI.OnInteriorOf(inter1, inter2);
        boolean ang2Interior = angleJ.OnInteriorOf(inter1, inter2);
        if (ang1Interior && ang2Interior) return deductions;
        if (!ang1Interior && !ang2Interior) return deductions;

        //
        // Are these angles on the same side of the transversal?
        //
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
        // Will this crossing segment actually intersect the real transversal in the middle of the two segments It should NOT.
        //
        Point intersection = transversal.lineIntersection(crossing);

        if (Segment.Between(intersection, inter1.getIntersect(), inter2.getIntersect())) return deductions;

        //
        // Now we have an alternate interior scenario
        //
        GeometricParallel newParallel = new GeometricParallel(parallelCand1, parallelCand2);

        // Construct hyperedge
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(inter1);
        antecedent.add(inter2);
        antecedent.add(conAngles);

        deductions.add(new Deduction(antecedent, newParallel, ANNOTATION));

        return deductions;
    }


}
