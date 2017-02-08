package backend.deductiveRules.parallelLines.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;

public class ParallelImplySameSideInteriorSupplementary extends Theorem
{
    private static String NAME = "Same-Side Supplementary"; // "If two parallel lines are cut by a transversal, then Same-Side Interior Angles are Supplementary.";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PARALLEL_IMPLY_SAME_SIDE_INTERIOR_SUPPLEMENTARY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public ParallelImplySameSideInteriorSupplementary(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PARALLEL_IMPLY_SAME_SIDE_INTERIOR_SUPPLEMENTARY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceSupplementary());

        return deductions;
    }

    //
    // Parallel(Segment(C, D), Segment(E, F)),
    // Intersection(M, Segment(A,B), Segment(C, D)),
    // Intersection(N, Segment(A,B), Segment(E, F))-> Supplementary(Angle(F, N, M), Angle(N, M, D))
    //                                                Supplementary(Angle(E, N, M), Angle(N, M, C))
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
    public static Set<Deduction> deduceSupplementary()
    {
        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Parallel> paras = _qhg.getParallels();
        HashSet<Intersection> inters = _qhg.getIntersections();
        Object[] aInters = inters.toArray();
        
        for(Parallel par : paras)
        {
            for (int i = 0; i < aInters.length - 1; i++)
            {
                for (int j = i + 1; j < aInters.length; j++)
                {
                    deductions.addAll(CheckAndGenerateParallelImplySameSide((Intersection)aInters[i], (Intersection)aInters[j], par));
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateParallelImplySameSide(Intersection inter1, Intersection inter2, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The two intersections should not be at the same vertex
        if (inter1.getIntersect().equals(inter2.getIntersect())) return deductions;

        // Determine the transversal
        Segment transversal = inter1.AcquireTransversal(inter2);
        if (transversal == null) return deductions;

        //
        // Ensure the non-traversal segments align with the parallel segments
        //
        Segment parallel1 = inter1.OtherSegment(transversal);
        Segment parallel2 = inter2.OtherSegment(transversal);

        // The non-transversals should not be the same (coinciding)
        if (parallel1.isCollinearWith(parallel2)) return deductions;

        Segment coincidingParallel1 = parallel.CoincidesWith(parallel1);
        Segment coincidingParallel2 = parallel.CoincidesWith(parallel2);

        // The pair of non-transversals needs to align exactly with the parallel pair of segments
        if (coincidingParallel1 == null || coincidingParallel2 == null) return deductions;

        // Both intersections should not be referring to an intersection point on the same parallel segment
        if (parallel.getSegment1().pointLiesOn(inter1.getIntersect()) && parallel.getSegment1().pointLiesOn(inter2.getIntersect())) return deductions;
        if (parallel.getSegment2().pointLiesOn(inter1.getIntersect()) && parallel.getSegment2().pointLiesOn(inter2.getIntersect())) return deductions;

        // The resultant candidate parallel segments shouldn't share any vertices
        if (coincidingParallel1.sharedVertex(coincidingParallel2) != null) return deductions;

        if (inter1.StandsOnEndpoint() && inter2.StandsOnEndpoint())
        {
            //
            // Creates a basic S-Shape with standsOnEndpoints
            //
            //                  ______ offThat
            //                 |
            //   offThis ______|
            //
            // Return <offThis, offThat>
            Pair<Point, Point> sShapePoints = inter1.CreatesBasicSShape(inter2);
            if (sShapePoints.getKey() != null && sShapePoints.getValue() != null) return deductions;

            //
            // Creates a basic C-Shape with standsOnEndpoints
            //
            //   offThis   ______
            //                   |
            //   offThat   ______|
            //
            // Return <offThis, offThat>
            Pair<Point, Point> cShapePoints = inter1.CreatesBasicCShape(inter2);
            if (cShapePoints.getKey() != null && cShapePoints.getValue() != null)
            {
                return GenerateSimpleC(parallel, inter1, cShapePoints.getKey(), inter2, cShapePoints.getValue());
            }

            return deductions;
        }

        //     _______/________
        //           /
        //          /
        //   ______/_______
        //        /
        if (inter1.Crossing() && inter2.Crossing()) return GenerateDualCrossings(parallel, inter1, inter2);

        // Alt. Int if an H-Shape
        //
        // |     |
        // |_____|
        // |     |
        // |     |
        //
        if (inter1.CreatesHShape(inter2)) return GenerateH(parallel, inter1, inter2);

        // Corresponding if a flying-Shape
        //
        // |     |
        // |_____|___ offCross
        // |     |
        // |     |
        //
        Point offCross = inter1.CreatesFlyingShapeWithCrossing(inter2);
        if (offCross != null)
        {
            return GenerateFlying(parallel, inter1, inter2, offCross);
        }

        //
        // Creates a shape like an extended t
        //     offCross                          offCross  
        //      |                                   |
        // _____|____                         ______|______
        //      |                                   |
        //      |_____ offStands     offStands _____|
        //
        // Returns <offStands, offCross>
        Pair<Point, Point> tShapePoints = inter1.CreatesCrossedTShape(inter2);
        if (tShapePoints.getKey() != null && tShapePoints.getValue() != null)
        {
            return GenerateCrossedT(parallel, inter1, inter2, tShapePoints.getKey(), tShapePoints.getValue());
        }

        //
        // Creates a Topped F-Shape
        //            top
        // offLeft __________ offEnd    <--- Stands on
        //             |
        //             |_____ off       <--- Stands on 
        //             |
        //             | 
        //           bottom
        //
        //   Returns: <bottom, off>
        Pair<Intersection, Point> toppedFShapePoints = inter1.CreatesToppedFShape(inter2);
        if (toppedFShapePoints.getKey() != null && toppedFShapePoints.getValue() != null)
        {
            return GenerateToppedFShape(parallel, inter1, inter2, toppedFShapePoints.getKey(), toppedFShapePoints.getValue());
        }

        // Corresponding angles if:
        //    ____________
        //       |    |
        //       |    |
        //
        Pair<Point, Point> piShapePoints = inter1.CreatesPIShape(inter2);
        if (piShapePoints.getKey() != null && piShapePoints.getValue() != null)
        {
            return InstantiatePiIntersection(parallel, inter1, piShapePoints.getKey(), inter2, piShapePoints.getValue());
        }

        // Corresponding angles if:
        //    _____
        //   |
        //   |_____ 
        //   |
        //   |
        //
        Pair<Point, Point> fShapePoints = inter1.CreatesFShape(inter2);
        if (fShapePoints.getKey() != null && fShapePoints.getValue() != null)
        {
            return InstantiateFIntersection(parallel, inter1, fShapePoints.getKey(), inter2, fShapePoints.getValue());
        }

        return deductions;
    }

    //
    // Creates a Topped F-Shape
    //            top
    // oppSide __________       <--- Stands on
    //             |
    //             |_____ off   <--- Stands on 
    //             |
    //             | 
    //           bottom
    //
    //   Returns: <bottom, off>
    private static Set<Deduction> GenerateToppedFShape(Parallel parallel, Intersection inter1, Intersection inter2, Intersection bottom, Point off)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Intersection top = inter1.equals(bottom) ? inter2 : inter1;

        // Determine the side of the top intersection needed for the angle
        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment parallelTop = top.OtherSegment(transversal);
        Segment parallelBottom = bottom.OtherSegment(transversal);

        Segment crossingTester = new Segment(off, parallelTop.getPoint1());
        Point intersection = transversal.segmentIntersection(crossingTester);

        Point oppSide = transversal.pointLiesBetweenEndpoints(intersection) ? parallelTop.getPoint1() : parallelTop.getPoint2();
        Point sameSide = parallelTop.other(oppSide);

        //
        // Generate the new congruence
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(off, bottom.getIntersect(), top.getIntersect()),
                                               new Angle(sameSide, top.getIntersect(), bottom.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }


    // Corresponding angles if:
    //
    //   up  <- may not occur
    //   |_____  offEnd
    //   |
    //   |_____  offStands
    //   |
    //   |
    //  down 
    //
    private static Set<Deduction> InstantiateFIntersection(Parallel parallel, Intersection inter1, Point off1, Intersection inter2, Point off2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Point offEnd = null;
        Point offStands = null;
        Intersection endpt = null;
        Intersection stands = null;
        if (inter1.StandsOnEndpoint())
        {
            endpt = inter1;
            offEnd = off1;
            stands = inter2;
            offStands = off2;
        }
        else if (inter2.StandsOnEndpoint())
        {
            endpt = inter2;
            offEnd = off2;
            stands = inter1;
            offStands = off1;
        }

        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment nonParallelEndpt = endpt.GetCollinearSegment(transversal);
        Segment nonParallelStands = stands.GetCollinearSegment(transversal);

        Point down = null;
        Point up = null;
        if (Segment.Between(stands.getIntersect(), nonParallelStands.getPoint1(), endpt.getIntersect()))
        {
            down = nonParallelStands.getPoint1();
            up = nonParallelStands.getPoint2();
        }
        else
        {
            down = nonParallelStands.getPoint2();
            up = nonParallelStands.getPoint1();
        }

        //
        //   up  <- may not occur
        //   |_____  offEnd
        //   |
        //   |_____  offStands
        //   |
        //   |
        //  down 
        //

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(offEnd, endpt.getIntersect(), stands.getIntersect()),
                                               new Angle(offStands, stands.getIntersect(), endpt.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }

    // Same-Side supplementary angles if:
    //
    //   left _____________ right
    //           |     |
    //           |     |
    //          off1  off2
    //
    //     Inter 1       Inter 2
    //
    private static Set<Deduction> InstantiatePiIntersection(Parallel parallel, Intersection inter1, Point off1, Intersection inter2, Point off2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //Segment transversal = inter1.AcquireTransversal(inter2);

        //Segment nonParallel1 = inter1.GetCollinearSegment(transversal);
        //Segment nonParallel2 = inter2.GetCollinearSegment(transversal);

        //Point left = Segment.Between(inter1.getIntersect(), nonParallel1.getPoint1(), inter2.getIntersect()) ? nonParallel1.getPoint1() : nonParallel1.getPoint2();
        //Point right = Segment.Between(inter2.getIntersect(), inter1.getIntersect(), nonParallel2.getPoint1()) ? nonParallel2.getPoint1() : nonParallel2.getPoint2();

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        // CTA:
        // Hack to avoid exception during long tests.
        try
        {
            Supplementary supp = new Supplementary(new Angle(off1, inter1.getIntersect(), inter2.getIntersect()),
                                                   new Angle(off2, inter2.getIntersect(), inter1.getIntersect()));
            newSupps.add(supp);

        } catch (Exception e)
        {
//            if (Utilities.DEBUG) System.Diagnostics.Debug.WriteLine(e.ToString());

            return deductions;
        }

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }

    //
    // Creates a shape like an extended t
    //           offCross                           offCross  
    //              |                                   |
    // oppSide _____|____ sameSide       sameSide ______|______ oppSide
    //              |                                   |
    //              |_____ offStands     offStands _____|
    //
    // Returns <offStands, offCross>
    private static Set<Deduction> GenerateCrossedT(Parallel parallel, Intersection inter1, Intersection inter2, Point offStands, Point offCross)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine which is the crossing intersection and which stands on the endpoints
        //
        Intersection crossingInter = null;
        Intersection standsInter = null;
        if (inter1.Crossing() && inter2.StandsOnEndpoint())
        {
            crossingInter = inter1;
            standsInter = inter2;
        }
        else if (inter2.Crossing() && inter1.StandsOnEndpoint())
        {
            crossingInter = inter2;
            standsInter = inter1;
        }

        // Determine the side of the crossing needed for the angle
        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment parallelStands = standsInter.OtherSegment(transversal);
        Segment parallelCrossing = crossingInter.OtherSegment(transversal);

        Segment crossingTester = new Segment(offStands, parallelCrossing.getPoint1());
        Point intersection = transversal.segmentIntersection(crossingTester);

        Point sameSide = transversal.pointLiesBetweenEndpoints(intersection) ? parallelCrossing.getPoint2() : parallelCrossing.getPoint1();
        Point oppSide = parallelCrossing.other(sameSide);

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(offStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                               new Angle(sameSide, crossingInter.getIntersect(), standsInter.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }


    //
    // Creates a basic C-Shape with standsOnEndpoints
    //
    //   offThis   ______
    //                   |
    //   offThat   ______|
    //
    // Return <offThis, offThat>
    private static Set<Deduction> GenerateSimpleC(Parallel parallel, Intersection inter1, Point offThis, Intersection inter2, Point offThat)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(offThis, inter1.getIntersect(), inter2.getIntersect()),
                                               new Angle(offThat, inter2.getIntersect(), inter1.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }

    //                 offCross
    //                     |
    // leftCross     ______|______   rightCross
    //                     |
    // leftStands     _____|_____    rightStands
    //
    private static Set<Deduction> GenerateFlying(Parallel parallel, Intersection inter1, Intersection inter2, Point offCross)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Determine which is the crossing intersection and which stands on the endpoints
        //
        Intersection crossingInter = null;
        Intersection standsInter = null;
        if (inter1.Crossing())
        {
            crossingInter = inter1;
            standsInter = inter2;
        }
        else if (inter2.Crossing())
        {
            crossingInter = inter2;
            standsInter = inter1;
        }

        // Determine the side of the crossing needed for the angle
        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment parallelStands = standsInter.OtherSegment(transversal);
        Segment parallelCrossing = crossingInter.OtherSegment(transversal);

        // Determine point orientation in the plane
        Point leftStands = parallelStands.getPoint1();
        Point rightStands = parallelStands.getPoint2();
        Point leftCross = null;
        Point rightCross = null;

        Segment crossingTester = new Segment(leftStands, parallelCrossing.getPoint1());
        Point intersection = transversal.segmentIntersection(crossingTester);
        if (transversal.pointLiesBetweenEndpoints(intersection))
        {
            leftCross = parallelCrossing.getPoint2();
            rightCross = parallelCrossing.getPoint1();
        }
        else
        {
            leftCross = parallelCrossing.getPoint1();
            rightCross = parallelCrossing.getPoint2();
        }

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(leftStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                               new Angle(leftCross, crossingInter.getIntersect(), standsInter.getIntersect()));
        newSupps.add(supp);

        supp = new Supplementary(new Angle(rightStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                 new Angle(rightCross, crossingInter.getIntersect(), standsInter.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, inter1, inter2);
    }

    //     leftTop    rightTop
    //         |         |
    //         |_________|
    //         |         |
    //         |         |
    //   leftBottom rightBottom
    //
    private static Set<Deduction> GenerateH(Parallel parallel, Intersection crossingInterLeft, Intersection crossingInterRight)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Segment transversal = crossingInterLeft.AcquireTransversal(crossingInterRight);

        //
        // Find tops and bottoms
        //
        Segment crossingLeftParallel = crossingInterLeft.OtherSegment(transversal);
        Segment crossingRightParallel = crossingInterRight.OtherSegment(transversal);

        //
        // Determine which points are on the same side of the transversal.
        //
        Segment testingCrossSegment = new Segment(crossingLeftParallel.getPoint1(), crossingRightParallel.getPoint1());
        Point intersection = transversal.segmentIntersection(testingCrossSegment);

        Point leftTop = crossingLeftParallel.getPoint1();
        Point leftBottom = crossingLeftParallel.getPoint2();

        Point rightTop = null;
        Point rightBottom = null;
        if (transversal.pointLiesBetweenEndpoints(intersection))
        {
            rightTop = crossingRightParallel.getPoint2();
            rightBottom = crossingRightParallel.getPoint1();
        }
        else
        {
            rightTop = crossingRightParallel.getPoint1();
            rightBottom = crossingRightParallel.getPoint2();
        }

        //
        // Generate the new supplement relationship
        //
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(leftTop, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                               new Angle(rightTop, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newSupps.add(supp);

        supp = new Supplementary(new Angle(leftBottom, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                 new Angle(rightBottom, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, crossingInterLeft, crossingInterRight);
    }

    //          leftTop    rightTop
    //              |         |
    //  offleft ____|_________|_____ offRight
    //              |         |
    //              |         |
    //         leftBottom rightBottom
    //
    private static Set<Deduction> GenerateDualCrossings(Parallel parallel, Intersection crossingInterLeft, Intersection crossingInterRight)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Segment transversal = crossingInterLeft.AcquireTransversal(crossingInterRight);

        //
        // Find offLeft and offRight
        //
        Segment crossingLeftParallel = crossingInterLeft.OtherSegment(transversal);
        Segment crossingRightParallel = crossingInterRight.OtherSegment(transversal);

        //
        // Determine which points are on the same side of the transversal.
        //
        Segment testingCrossSegment = new Segment(crossingLeftParallel.getPoint1(), crossingRightParallel.getPoint1());
        Point intersection = transversal.segmentIntersection(testingCrossSegment);

        Point leftTop = crossingLeftParallel.getPoint1();
        Point leftBottom = crossingLeftParallel.getPoint2();

        Point rightTop = null;
        Point rightBottom = null;
        if (transversal.pointLiesBetweenEndpoints(intersection))
        {
            rightTop = crossingRightParallel.getPoint2();
            rightBottom = crossingRightParallel.getPoint1();
        }
        else
        {
            rightTop = crossingRightParallel.getPoint1();
            rightBottom = crossingRightParallel.getPoint2();
        }

        // Point that is outside of the parallel lines and transversal
        Segment leftTransversal = crossingInterLeft.GetCollinearSegment(transversal);
        Segment rightTransversal = crossingInterRight.GetCollinearSegment(transversal);

        Point offLeft = Segment.Between(crossingInterLeft.getIntersect(), leftTransversal.getPoint1(), crossingInterRight.getIntersect()) ? leftTransversal.getPoint1() : leftTransversal.getPoint2();
        Point offRight = Segment.Between(crossingInterRight.getIntersect(), crossingInterLeft.getIntersect(), rightTransversal.getPoint1()) ? rightTransversal.getPoint1() : rightTransversal.getPoint2();

        //
        // Generate the new congruences
        //

        //          leftTop    rightTop
        //              |         |
        //  offleft ____|_________|_____ offRight
        //              |         |
        //              |         |
        //         leftBottom rightBottom
        ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();

        Supplementary supp = new Supplementary(new Angle(leftTop, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                               new Angle(rightTop, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newSupps.add(supp);

        supp = new Supplementary(new Angle(leftBottom, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                 new Angle(rightBottom, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newSupps.add(supp);

        return MakeHypergraphRelation(newSupps, parallel, crossingInterLeft, crossingInterRight);
    }

    private static Set<Deduction> MakeHypergraphRelation(ArrayList<Supplementary> newSupps, Parallel parallel, Intersection inter1, Intersection inter2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(parallel);
        antecedent.add(inter1);
        antecedent.add(inter2);

        for (Supplementary supp : newSupps)
        {
            deductions.add(new Deduction(antecedent, supp, ANNOTATION));
        }

        return deductions;
    }
}
