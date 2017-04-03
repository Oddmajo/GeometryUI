package backend.deductiveRules.angles.axioms;

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
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;

public class CorrespondingAnglesOfParallelLines extends Axiom
{
    private static String NAME = "Corresponding Angles"; // "If Two Parallel Lines are Cut by a Transversal, then Corresponding Angles are Congruent (Axiom)";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CORRESPONDING_ANGLES_OF_PARALLEL_LINES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public CorrespondingAnglesOfParallelLines(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CORRESPONDING_ANGLES_OF_PARALLEL_LINES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceLines());

        return deductions;
    }

    public static Set<Deduction> deduceLines()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Parallel> paras = _qhg.getParallels();
        HashSet<Intersection> inters = _qhg.getIntersections();
        Object[] aInters = inters.toArray();
        for(Parallel para : paras)
        {
            for (int i = 0; i < aInters.length; i++)
            {
                for (int j = i + 1; j < aInters.length; j++)
                {
                    deductions.addAll(InstantiateIntersection(para, (Intersection)aInters[i], (Intersection)aInters[j]));
                }
            }
        }
        
        return deductions;
    }


    private static Set<Deduction> InstantiateIntersection(Parallel parallel, Intersection inter1, Intersection inter2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // Avoid:
        //      |            |
        //    __|    ________|
        //      |            |
        //      |            |
        // Both intersections (transversal segments) must contain the actual transversal; that is, a direct, segment relationship must exist
        if (!inter1.CreatesAValidTransversalWith(inter2)) return deductions;

        // No corresponding angles if we have:
        //
        //    |          |         |
        //    |__________|         |_________
        //                                   |
        //                                   |
        //
        if (inter1.StandsOnEndpoint() && inter2.StandsOnEndpoint()) return deductions;

        // if (Utilities.DEBUG) System.Diagnostics.Debug.WriteLine("Working on: \n\t" + inter1.ToString() + "\n\t" + inter2.ToString());

        //
        // Verify we have a parallel / intersection situation using the given information
        //
        Segment transversal = inter1.AcquireTransversal(inter2);

        // Ensure the non-traversal segments align with the parallel segments
        Segment coincidingParallel1 = parallel.CoincidesWith(inter1.OtherSegment(transversal));
        Segment coincidingParallel2 = parallel.CoincidesWith(inter2.OtherSegment(transversal));

        // The pair of non-transversals needs to align exactly with the parallel pair of segments
        if (coincidingParallel1 == null || coincidingParallel2 == null) return deductions;

        // STANDARD Dual Crossings
        // Corresponding angles:
        //
        //      |          |  
        //   ___|__________|__
        //      |          |  
        //      |          | 
        //
        if (inter1.Crossing() && inter2.Crossing()) return InstantiateCompleteIntersection(parallel, inter1, inter2);

        // NOT Corresponding if an H-Shape
        //
        // |     |
        // |_____|
        // |     |
        // |     |
        //
        if (inter1.CreatesHShape(inter2)) return deductions;

        // NOT Corresponding angles if:
        //
        //         |______
        //         |
        //   ______|
        //         |
        //
        Pair<Point, Point> sShapePoints = inter1.CreatesStandardSShape(inter2);
        if (sShapePoints.getKey() != null && sShapePoints.getValue() != null) return deductions;

        // NOT Corresponding angles if:
        //
        //       |______
        //       |
        // ______|
        Pair<Point, Point> leanerShapePoints = inter1.CreatesLeanerShape(inter2);
        if (leanerShapePoints.getKey() != null && leanerShapePoints.getValue() != null) return deductions;

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

        sShapePoints = inter1.CreatesSimpleSShape(inter2);
        if (sShapePoints.getKey() != null && sShapePoints.getValue() != null) return deductions;

        // Corresponding angles if:
        //                o       e
        // standsOn (o)   o       e    standsOnEndpoint (e)
        //             eeeoeeeeeeee
        //                o
        //                o           
        //
        Pair<Point, Point> simpleTShapePoints = inter1.CreatesSimpleTShape(inter2);
        if (simpleTShapePoints.getKey() != null && simpleTShapePoints.getValue() != null)
        {
            return InstantiateSimpleTIntersection(parallel, inter1, inter2, simpleTShapePoints.getKey(), simpleTShapePoints.getValue());
        }

        // Corresponding angles if:
        //    ____________
        //       |    |
        //       |    |
        //
        Pair<Point, Point> piShapePoints = inter1.CreatesSimplePIShape(inter2);
        if (piShapePoints.getKey() != null && piShapePoints.getValue() != null)
        {
            return InstantiateSimplePiIntersection(parallel, inter1, inter2, piShapePoints.getKey(), piShapePoints.getValue());
        }

        // Corresponding if:
        //
        // |     |        |
        // |_____|____    |_________
        // |              |     |
        // |              |     |
        //
        Pair<Point, Point> chairShapePoints = inter1.CreatesChairShape(inter2);
        if (chairShapePoints.getKey() != null && chairShapePoints.getValue() != null)
        {
            return InstantiateChairIntersection(parallel, inter1, chairShapePoints.getKey(), inter2, chairShapePoints.getValue());
        }

        // Corresponding angles if:
        //    ____________
        //       |    |
        //       |    |
        //
        piShapePoints = inter1.CreatesPIShape(inter2);
        if (piShapePoints.getKey() != null && piShapePoints.getValue() != null)
        {
            return InstantiatePiIntersection(parallel, inter1, piShapePoints.getKey(), inter2, piShapePoints.getValue());
        }

        //
        //      |                |
        // _____|____      ______|______
        //      |                |
        //      |_____      _____|
        //
        Pair<Point, Point> crossedTShapePoints = inter1.CreatesCrossedTShape(inter2);
        if (crossedTShapePoints.getKey() != null && crossedTShapePoints.getValue() != null)
        {
            return InstantiateCrossedTIntersection(parallel, inter1, inter2, crossedTShapePoints.getKey(), crossedTShapePoints.getValue());
        }

        // Corresponding if a flying-Shape
        //
        // |     |
        // |_____|___
        // |     |
        // |     |
        //
        Pair<Intersection, Point> flyingShapeValues = inter1.CreatesFlyingShape(inter2);
        if (flyingShapeValues.getKey() != null && flyingShapeValues.getValue() != null)
        {
            return InstantiateFlyingIntersection(parallel, inter1, inter2, flyingShapeValues.getKey(), flyingShapeValues.getValue());
        }

        //        |
        //  ______|______
        //        |
        //   _____|_____
        Point offCross = inter1.CreatesFlyingShapeWithCrossing(inter2);
        if (offCross != null) return InstantiateFlyingCrossedIntersection(parallel, inter1, inter2, offCross);

        //        |
        //  ______|______
        //        |
        //        |_____
        //        |
        offCross = inter1.CreatesExtendedChairShape(inter2);
        if (offCross != null) return InstantiateExtendedChairIntersection(parallel, inter1, inter2, offCross);

        return deductions;
    }

    //                   top
    //                    o
    //  offStands  oooooooe
    //                    e
    //offEndpoint   eeeeeee
    //                    o
    //                 bottom
    //                       Returns: <offEndpoint, offStands>
    private static Set<Deduction> InstantiateSimplePiIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Point offEndpoint, Point offStands)
    {
        HashSet<Deduction> deductions = new HashSet<>();

        //
        // Determine which is the endpoint and stands intersections
        //
        Intersection endpointInter = null;
        Intersection standsInter = null;
        if (inter1.StandsOnEndpoint() && inter2.standsOn())
        {
            endpointInter = inter1;
            standsInter = inter2;
        }
        else if (inter2.StandsOnEndpoint() && inter1.standsOn())
        {
            endpointInter = inter2;
            standsInter = inter1;
        }
        else return deductions;

        //
        // Determine the top and bottom points
        //
        Segment transversal = inter1.AcquireTransversal(inter2);
        Segment transversalStands = standsInter.GetCollinearSegment(transversal);

        Point top = null;
        Point bottom = null;
        if (Segment.Between(standsInter.getIntersect(), transversalStands.getPoint1(), endpointInter.getIntersect()))
        {
            top = transversalStands.getPoint1();
            bottom = transversalStands.getPoint2();
        }
        else
        {
            top = transversalStands.getPoint2();
            bottom = transversalStands.getPoint1();
        }

        //
        // Generate the new congruences
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(top, standsInter.getIntersect(), offStands),
                                                                    new Angle(standsInter.getIntersect(), endpointInter.getIntersect(), offEndpoint));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(bottom, endpointInter.getIntersect(), offEndpoint), 
                                           new Angle(endpointInter.getIntersect(), standsInter.getIntersect(), offStands));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    // Corresponding angles if:
    //            sameSide offRightEnd
    // standsOn (o)   o       e
    //                o       e    standsOnEndpoint (e)
    // offLeftEnd  eeeoeeeeeeee
    //                o
    //                o           
    //
    // Returns <offLeftEnd, offRightEnd>
    //
    private static Set<Deduction> InstantiateSimpleTIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Point offLeftEnd, Point offRightEnd)
    {
        HashSet<Deduction> deductions = new HashSet<>();

        //
        // Determine which is the endpoint and stands intersections
        //
        Intersection endpointInter = null;
        Intersection standsInter = null;
        if (inter1.StandsOnEndpoint() && inter2.standsOn())
        {
            endpointInter = inter1;
            standsInter = inter2;
        }
        else if (inter2.StandsOnEndpoint() && inter1.standsOn())
        {
            endpointInter = inter2;
            standsInter = inter1;
        }
        else return deductions;

        // Determine the sameSide point
        Segment transversal = inter1.AcquireTransversal(inter2);
        Segment parallelStands = standsInter.OtherSegment(transversal);
        Segment crossingTester = new Segment(offRightEnd, parallelStands.getPoint1());
        Point intersection = transversal.segmentIntersection(crossingTester);

        Point sameSide = transversal.pointLiesBetweenEndpoints(intersection) ? parallelStands.getPoint2() : parallelStands.getPoint2();

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offLeftEnd, standsInter.getIntersect(), sameSide),
                                                                    new Angle(standsInter.getIntersect(), endpointInter.getIntersect(), offRightEnd));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }



    //     offCross
    //        |
    //  ______|______ rightCrossing
    //        |
    //        |_____  offStands
    //        |
    //        |
    //     bottomStands
    private static Set<Deduction> InstantiateExtendedChairIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Point offCross)
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

        //
        // Determination of Points
        //
        Point offStands = standsInter.CreatesTShape();

        Segment transversal = inter1.AcquireTransversal(inter2);
        Segment transversalStands = standsInter.GetCollinearSegment(transversal);

        Point bottomStands = Segment.Between(standsInter.getIntersect(), transversalStands.getPoint1(), crossingInter.getIntersect()) ? transversalStands.getPoint1() : transversalStands.getPoint2();

        // Which side for rightCrossing
        Segment parallelCrossing = crossingInter.OtherSegment(transversal);
        Segment crossingTester = new Segment(offStands, parallelCrossing.getPoint1());
        Point intersection = transversal.lineIntersection(crossingTester);

        Point rightCrossing = transversal.pointLiesBetweenEndpoints(intersection) ? parallelCrossing.getPoint2() : parallelCrossing.getPoint1();

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(bottomStands, standsInter.getIntersect(), offStands),
                                                                    new Angle(standsInter.getIntersect(), crossingInter.getIntersect(), rightCrossing));
        newAngleRelations.add(gca);

        gca = new GeometricCongruentAngles(new Angle(offStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                           new Angle(rightCrossing, crossingInter.getIntersect(), offCross));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    //                 offCross
    //                     |
    // leftCross     ______|______   rightCross
    //                     |
    // leftStands     _____|_____    rightStands
    //
    //
    private static Set<Deduction> InstantiateFlyingCrossedIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Point offCross)
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
        Point intersection = transversal.lineIntersection(crossingTester);
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

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offCross, crossingInter.getIntersect(), rightCross),
                                                                    new Angle(crossingInter.getIntersect(), standsInter.getIntersect(), rightStands));
        newAngleRelations.add(gca);

        gca = new GeometricCongruentAngles(new Angle(offCross, crossingInter.getIntersect(), leftCross),
                                                                    new Angle(crossingInter.getIntersect(), standsInter.getIntersect(), leftStands));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    //
    // Creates a shape like an extended t
    //     offCross                          offCross  
    //      |                                   |
    // _____|____ sameSide       sameSide ______|______
    //      |                                   |
    //      |_____ offStands     offStands _____|
    //
    // Returns <offStands, offCross>
    private static Set<Deduction> InstantiateCrossedTIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Point offStands, Point offCross)
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
        Point intersection = transversal.lineIntersection(crossingTester);

        Point sameSide = transversal.pointLiesBetweenEndpoints(intersection) ? parallelCrossing.getPoint2() : parallelCrossing.getPoint1();

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offStands, standsInter.getIntersect(), crossingInter.getIntersect()),
                                                                    new Angle(sameSide, crossingInter.getIntersect(), offCross));
        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }


    //
    // Chair Corresponding
    //
    // |     |                  |
    // |_____|____   leftInter  |_________ tipOfT
    // |                        |     |
    // |                        |     |
    //                         off   tipOfT
    //
    //                                bottomInter
    private static Set<Deduction> InstantiateChairIntersection(Parallel parallel, Intersection inter1, Point off, Intersection inter2, Point bottomTip)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Segment transversal = inter1.AcquireTransversal(inter2);
        Point tipOfT1 = inter1.CreatesTShape();
        Point tipOfT2 = inter2.CreatesTShape();

        Point leftTip = null;
        Intersection leftInter = null;
        Intersection bottomInter = null;

        if (transversal.pointLiesOn(tipOfT1))
        {
            leftInter = inter1;
            bottomInter = inter2;
            leftTip = tipOfT1;
        }
        // thatInter is leftInter
        else if (transversal.pointLiesOn(tipOfT2))
        {
            leftInter = inter2;
            bottomInter = inter1;
            leftTip = tipOfT2;
        }

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        // CTA: Hack fix to alleviate exception thrown from improper congruent constructions.
        GeometricCongruentAngles gca = null;
        try
        {
            gca = new GeometricCongruentAngles(new Angle(leftTip, bottomInter.getIntersect(), bottomTip),
                                               new Angle(bottomInter.getIntersect(), leftInter.getIntersect(), off));
        }
        catch (Exception e)
        {
//            if (Utilities.DEBUG) System.Diagnostics.Debug.WriteLine(e.ToString());

            return deductions;
        }

        newAngleRelations.add(gca);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    //
    // Creates a shape like a crazy person flying
    //
    //            top   top
    //             |     |
    // larger      |_____|___ off
    //             |     |
    //             |     |
    //
    // Similar to H-shape with an extended point
    // Returns the 'larger' intersection that contains the point: off
    private static Set<Deduction> InstantiateFlyingIntersection(Parallel parallel, Intersection inter1, Intersection inter2, Intersection larger, Point off)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Intersection smallerInter = inter1.equals(larger) ? inter2 : inter1;

        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment parallel1 = inter1.OtherSegment(transversal);
        Segment parallel2 = inter2.OtherSegment(transversal);

        Point largerTop = parallel1.getPoint1();
        Point largerBottom = parallel1.getPoint2();

        Point otherTop = null;
        Point otherBottom = null;

        Segment crossingTester = new Segment(parallel1.getPoint1(), parallel2.getPoint1());
        Point intersection = transversal.segmentIntersection(crossingTester);
        // opposite sides
        if (transversal.pointLiesBetweenEndpoints(intersection))
        {
            otherTop = parallel2.getPoint2();
            otherBottom = parallel2.getPoint1();
        }
        // same sides
        else
        {
            otherTop = parallel2.getPoint1();
            otherBottom = parallel2.getPoint2();
        }

        //
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca1 = new GeometricCongruentAngles(new Angle(off, smallerInter.getIntersect(), otherTop),
                                                                     new Angle(smallerInter.getIntersect(), larger.getIntersect(), largerTop));
        newAngleRelations.add(gca1);
        GeometricCongruentAngles gca2 = new GeometricCongruentAngles(new Angle(off, smallerInter.getIntersect(), otherBottom),
                                                                     new Angle(smallerInter.getIntersect(), larger.getIntersect(), largerBottom));
        newAngleRelations.add(gca2);

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    // Corresponding angles if:
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

        Segment transversal = inter1.AcquireTransversal(inter2);

        Segment nonParallel1 = inter1.GetCollinearSegment(transversal);
        Segment nonParallel2 = inter2.GetCollinearSegment(transversal);

        Point left = Segment.Between(inter1.getIntersect(), nonParallel1.getPoint1(), inter2.getIntersect()) ? nonParallel1.getPoint1() : nonParallel1.getPoint2();
        Point right = Segment.Between(inter2.getIntersect(), inter1.getIntersect(), nonParallel2.getPoint1()) ? nonParallel2.getPoint1() : nonParallel2.getPoint2();

        //
        // Generate the new congruences
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        // CTA: Hack to avoid an exception being thrown during testing.
        try
        {
            GeometricCongruentAngles gca1 = new GeometricCongruentAngles(new Angle(left, inter1.getIntersect(), off1),
                                                                         new Angle(inter1.getIntersect(), inter2.getIntersect(), off2));
            newAngleRelations.add(gca1);

            GeometricCongruentAngles gca2 = new GeometricCongruentAngles(new Angle(right, inter2.getIntersect(), off2),
                                                                         new Angle(inter2.getIntersect(), inter1.getIntersect(), off1));
            newAngleRelations.add(gca2);
        }
        catch (Exception e)
        {
            return deductions;
        }

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
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
        // Generate the new congruence
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        if (!down.equals(stands.getIntersect()))
        {
            GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(offEnd, endpt.getIntersect(), stands.getIntersect()),
                                                                        new Angle(offStands, stands.getIntersect(), down));
            newAngleRelations.add(gca);
        }

        if (!up.equals(endpt.getIntersect()))
        {
            GeometricCongruentAngles gcaOptional = new GeometricCongruentAngles(new Angle(up, endpt.getIntersect(), offEnd),
                                                                                new Angle(endpt.getIntersect(), stands.getIntersect(), offStands));
            newAngleRelations.add(gcaOptional);
        }

        return MakeRelations(newAngleRelations, parallel, inter1, inter2);
    }

    // Corresponding angles if (we have 8 points here)
    //
    //  InterLeft                        InterRight
    //                |          |
    //      offLeft __|__________|__ offRight
    //                |          |
    //                |          |
    //
    private static Set<Deduction> InstantiateCompleteIntersection(Parallel parallel, Intersection crossingInterLeft, Intersection crossingInterRight)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Segment transversal = crossingInterLeft.AcquireTransversal(crossingInterRight);

        //
        // Find off1 and off2
        //
        Segment crossingLeftParallel = crossingInterLeft.OtherSegment(transversal);
        Segment crossingRightParallel = crossingInterRight.OtherSegment(transversal);

        //
        // Determine which points are on the same side of the transversal.
        //
        Segment testingCrossSegment = new Segment(crossingLeftParallel.getPoint1(), crossingRightParallel.getPoint1());
        Point intersection = transversal.segmentIntersection(testingCrossSegment);

        Point crossingLeftTop = crossingLeftParallel.getPoint1();
        Point crossingLeftBottom = crossingLeftParallel.getPoint2();

        Point crossingRightTop = null;
        Point crossingRightBottom = null;
        if (transversal.pointLiesBetweenEndpoints(intersection))
        {
            crossingRightTop = crossingRightParallel.getPoint2();
            crossingRightBottom = crossingRightParallel.getPoint1();
        }
        else
        {
            crossingRightTop = crossingRightParallel.getPoint1();
            crossingRightBottom = crossingRightParallel.getPoint2();
        }

        // Point that is outside of the parallel lines and transversal
        Segment leftTransversal = crossingInterLeft.GetCollinearSegment(transversal);
        Segment rightTransversal = crossingInterRight.GetCollinearSegment(transversal);

        Point offCrossingLeft = Segment.Between(crossingInterLeft.getIntersect(), leftTransversal.getPoint1(), crossingInterRight.getIntersect()) ? leftTransversal.getPoint1() : leftTransversal.getPoint2();
        Point offCrossingRight = Segment.Between(crossingInterRight.getIntersect(), crossingInterLeft.getIntersect(), rightTransversal.getPoint1()) ? rightTransversal.getPoint1() : rightTransversal.getPoint2();

        //
        // Generate the new congruences
        //
        ArrayList<CongruentAngles> newAngleRelations = new ArrayList<CongruentAngles>();

        GeometricCongruentAngles gca = new GeometricCongruentAngles(new Angle(crossingLeftTop, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                                                    new Angle(crossingRightTop, crossingInterRight.getIntersect(), offCrossingRight));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(crossingLeftTop, crossingInterLeft.getIntersect(), offCrossingLeft),
                                           new Angle(crossingRightTop, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(crossingLeftBottom, crossingInterLeft.getIntersect(), offCrossingLeft),
                                           new Angle(crossingRightBottom, crossingInterRight.getIntersect(), crossingInterLeft.getIntersect()));
        newAngleRelations.add(gca);
        gca = new GeometricCongruentAngles(new Angle(crossingLeftBottom, crossingInterLeft.getIntersect(), crossingInterRight.getIntersect()),
                                           new Angle(crossingRightBottom, crossingInterRight.getIntersect(), offCrossingRight));
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
            newAngles.makeAxiomatic();
            deductions.add(new Deduction(antecedent, newAngles, ANNOTATION));
        }

        return deductions;
    }

}
