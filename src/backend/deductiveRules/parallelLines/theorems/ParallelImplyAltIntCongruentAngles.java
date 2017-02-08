package backend.deductiveRules.parallelLines.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
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

public class ParallelImplyAltIntCongruentAngles extends Theorem
{
    private static String NAME = "Alternate Interior Angles"; //"Parallel Lines Imply Congruent Alternate Interior Angles";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PARALLEL_IMPLY_ALT_INT_CONGRUENT_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public ParallelImplyAltIntCongruentAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PARALLEL_IMPLY_ALT_INT_CONGRUENT_ANGLES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceAngles());

        return deductions;
    }

    //
    // Parallel(Segment(C, D), Segment(E, F)),
    // Intersection(M, Segment(A,B), Segment(C, D)),
    // Intersection(N, Segment(A,B), Segment(E, F))-> Congruent(Angle(F, N, M), Angle(N, M, C))
    //                                                Congruent(Angle(E, N, M), Angle(N, M, D))
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
    public static Set<Deduction> deduceAngles(  )
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
                    deductions.addAll(CheckAndGenerateParallelImplyAlternateInterior((Intersection)aInters[i], (Intersection)aInters[j], par));
                }
            }
        }
        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateParallelImplyAlternateInterior(Intersection inter1, Intersection inter2, Parallel parallel)
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
            //   offThis   ______
            //                   |
            //   offThat   ______|
            //
            // Return <offThis, offThat>
            Pair<Point, Point> cShapePoints = inter1.CreatesBasicCShape(inter2);
            if (cShapePoints.getKey() != null && cShapePoints.getValue() != null) return deductions;

            //
            // Creates a basic S-Shape with standsOnEndpoints
            //
            //                  ______ offThat       _______
            //                 |                     \
            //   offThis ______|                 _____\
            //
            // Return <offThis, offThat>
            Pair<Point, Point> sShapePoints = inter1.CreatesBasicSShape(inter2);
            if (sShapePoints.getKey() != null && sShapePoints.getValue() != null)
            {
                return GenerateSimpleS(parallel, inter1, sShapePoints.getKey(), inter2, sShapePoints.getValue());
            }

            return deductions;
        }

        //     _______/________
        //           /
        //          /
        //   ______/_______
        //        /
        if (inter1.Crossing() && inter2.Crossing()) return GenerateDualCrossings(parallel, inter1, inter2);

        // No alt int in this case:
        // Creates an F-Shape
        //   top
        //    _____ offEnd     <--- Stands on Endpt
        //   |
        //   |_____ offStands  <--- Stands on 
        //   |
        //   | 
        //  bottom
        Pair<Point, Point> fShapePoints = inter1.CreatesFShape(inter2);
        if (fShapePoints.getKey() != null && fShapePoints.getValue() != null) return deductions;

        // Alt. Int if an H-Shape
        //
        // |     |
        // |_____|
        // |     |
        // |     |
        //
        if (inter1.CreatesHShape(inter2)) return GenerateH(parallel, inter1, inter2);

        // Creates an S-Shape
        //
        //         |______
        //         |
        //   ______|
        //         |
        //
        //   Order of non-collinear points is order of intersections: <this, that>
        Pair<Point, Point> standardSShapePoints = inter1.CreatesStandardSShape(inter2);
        if (standardSShapePoints.getKey() != null && standardSShapePoints.getValue() != null)
        {
            return GenerateSimpleS(parallel, inter1, standardSShapePoints.getKey(), inter2, standardSShapePoints.getValue());
        }

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

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(off, bottom.getIntersect(), top.getIntersect()),
                                                                    new Angle(oppSide, top.getIntersect(), bottom.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
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
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                                                    new Angle(oppSide, crossingInter.getIntersect(), standsInter.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }


    //
    // Creates a basic S-Shape with standsOnEndpoints
    //
    //                  ______ offThat
    //                 |
    //   offThis ______|
    //
    // Return <offThis, offThat>
    private static Set<Deduction> GenerateSimpleS(Parallel parallel, Intersection inter1, Point offThis, Intersection inter2, Point offThat)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offThat, inter2.getIntersect(), inter1.getIntersect()),
                                                                    new Angle(offThis, inter1.getIntersect(), inter2.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
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
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(rightCross, crossingInter.getIntersect(), standsInter.getIntersect()),
                                                                    new Angle(leftStands, standsInter.getIntersect(), crossingInter.getIntersect()));
        newAngleRelations.add(gca);

        gca = new GeometricCongruentAngles(new Angle(leftCross, crossingInter.getIntersect(), standsInter.getIntersect()),
                                           new Angle(rightStands, standsInter.getIntersect(), crossingInter.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
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
        // Generate the new congruences
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(leftTop, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                                                    new Angle(rightBottom, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(rightTop, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()),
                                           new Angle(leftBottom, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, crossingInterLeft, crossingInterRight);
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
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(leftTop, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                                                    new Angle(rightBottom, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(rightTop, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()),
                                           new Angle(leftBottom, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, crossingInterLeft, crossingInterRight);
    }

    private static Set<Deduction> MakeRelations(ArrayList<CongruentAngles> newAngleRelations, Parallel parallel, Intersection inter1, Intersection inter2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(parallel);
        antecedent.add(inter1);
        antecedent.add(inter2);

        for (CongruentAngles newAngles : newAngleRelations)
        {
            deductions.add(new Deduction(antecedent, newAngles, ANNOTATION));
        }

        return deductions;
    }

}
