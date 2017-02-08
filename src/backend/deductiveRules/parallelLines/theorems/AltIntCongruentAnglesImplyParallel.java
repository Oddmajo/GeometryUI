package backend.deductiveRules.parallelLines.theorems;

import java.util.ArrayList;
import java.util.HashSet;
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
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class AltIntCongruentAnglesImplyParallel extends Theorem
{
    private static String NAME = "Alternate Interior Angles Formed by a Transversal Imply Parallel Lines"; 
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ALT_INT_CONGRUENT_ANGLES_IMPLY_PARALLEL);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AltIntCongruentAnglesImplyParallel(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ALT_INT_CONGRUENT_ANGLES_IMPLY_PARALLEL;
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
    // Congruent(Angle(A, N, F), Angle(A, M, C)) -> Parallel(Segment(C, D), Segment(E, F))
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
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<CongruentAngles> conAngs = _qhg.getCongruentAngles();
        HashSet<Intersection> inters = _qhg.getIntersections();
        Object[] aInters = inters.toArray();

        for(CongruentAngles ang : conAngs)
        {
            if(!ang.isReflexive())
            {
                for (int i = 0; i < aInters.length - 1; i++)
                {
                    for (int j = i + 1; j < aInters.length; j++)
                    {
                        deductions.addAll(CheckAndGenerateAlternateInteriorImplyParallel((Intersection)aInters[i], (Intersection)aInters[j], ang));
                    }
                }
            }
        }
       
        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateAlternateInteriorImplyParallel(Intersection inter1, Intersection inter2, CongruentAngles conAngles)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

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
        // Check to see if they are, in fact, alternate interior angles respectively
        //
        Segment parallelCand1 = inter1.OtherSegment(transversal);
        Segment parallelCand2 = inter2.OtherSegment(transversal);

        // Quick hack check to ensure we have parallel segmenets
        if (!parallelCand1.isParallel(parallelCand2)) return deductions;

        // The resultant candidate parallel segments shouldn't share any vertices
        if (parallelCand1.sharedVertex(parallelCand2) != null) return deductions;

        // A sanity check that the '4th' point does not lie on the other intersection (thus creating an obvious triangle and thus not parallel lines)
        Point fourthPoint1 = parallelCand1.other(inter1.getIntersect());
        Point fourthPoint2 = parallelCand2.other(inter2.getIntersect());
        if (fourthPoint1 != null && fourthPoint2 != null)
        {
            if (parallelCand1.pointLiesOn(fourthPoint2) || parallelCand2.pointLiesOn(fourthPoint1)) return deductions;
        }

        // Are the angles within the interior 
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
        // Will this crossing segment actually intersect the real transversal in the middle of the two segments
        //
        Point intersection = transversal.segmentIntersection(crossing);

        if (!Segment.Between(intersection, inter1.getIntersect(), inter2.getIntersect())) return deductions;

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
