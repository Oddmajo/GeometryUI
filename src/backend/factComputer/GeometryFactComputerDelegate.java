package backend.factComputer;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.CircleIntersection;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MaximalIntersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Arcs_and_Circles.CircleCircleIntersection;
import backend.ast.Descriptors.Arcs_and_Circles.CircleSegmentIntersection;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.polygon.ConcavePolygon;
import backend.ast.figure.components.polygon.Polygon;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.ast.figure.delegates.intersections.IntersectionDelegate;
import backend.precomputer.PolygonCalculator;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleArcEquation;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.ArcEquation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.GeometricSegmentEquation;
import backend.symbolicAlgebra.equations.SegmentEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.MaximalIntersections;
import backend.utilities.MaximalSegments;
import backend.utilities.Pair;
import backend.utilities.PointFactory;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;
import backend.utilities.translation.OutPair;

public class GeometryFactComputerDelegate
{
    // Third-steps for segment relations; needs to be changed
    public static final int PROPORTIONAL_STEP = 3;

    /*
     * @param fc -- a fact computer containing access to segments and points
     * @return the list of new (all) InMiddle fact clauses
     */
    public static HashSet<InMiddle> computeInMiddles(FactComputer fc)
    {
        HashSet<InMiddle> inMiddles = new HashSet<InMiddle>();

        // An InMiddle object consists of a segments and point.
        // A --------------------- M -------------- B
        for (Segment segment : fc.getSegments())
        {
            for (Point p : PointFactory.getAllPoints())
            {
                if (segment.pointLiesBetweenEndpoints(p))
                {
                    inMiddles.add(new InMiddle(p, segment));
                }
            }
        }

        return inMiddles;
    }

    /*
     * @param fc -- a fact computer containing access to InMiddle objects
     * @return via out parameters: the set of all midpoints and the set of all InMIddle objects strengthened
     */
    public static void computeMidpoints(FactComputer fc,
            ArrayList<Midpoint> midpoints,
            ArrayList<Strengthened> strengthMidpoints)
    {        
        for (InMiddle im : fc.getInMiddles())
        {
            // Can this InMiddle be strengthened to a Midpoint?
            // If so, create two distinct objects
            Strengthened s = im.canBeStrengthened();
            if (s != null)
            {
                midpoints.add((Midpoint)s.getStrengthened());
                strengthMidpoints.add(s);
            }
        }
    }

    /*
     * @param fc -- a fact computer containing access to InMiddle objects
     * @return via out parameters: the set of all midpoints and the set of all InMIddle objects strengthened
     */
    public static ArrayList<SegmentEquation> computeMidpointEquations(FactComputer fc)
    {
        ArrayList<SegmentEquation> eqs = new ArrayList<SegmentEquation>();

        for (Midpoint midpoint : fc.getMidpoints())
        {
            // Create the two individual segments
            Segment seg1 = fc.getStructuralSegment(new Segment(midpoint.getSegment().getPoint1(), midpoint.getPoint()));
            Segment seg2 = fc.getStructuralSegment(new Segment(midpoint.getSegment().getPoint2(), midpoint.getPoint()));

            // A----M----B -> 2AM = AB, 2MB = AB 
            Multiplication product1 = new Multiplication(new NumericValue(2), seg1);
            Multiplication product2 = new Multiplication(new NumericValue(2), seg2);

            eqs.add(new SegmentEquation(product1, midpoint.getSegment()));
            eqs.add(new SegmentEquation(product2, midpoint.getSegment()));
        }

        return eqs;
    }

    /*
     * @param fc -- a fact computer containing access to segments and points
     * @return void; all angles added to the AngleEquivalenceRelation
     */
    public static void computeAngles(FactComputer fc)
    {
        ArrayList<Segment> segments = fc.getSegments();

        for(int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                //
                // CTA: Does this technique handle angle crossings?
                //

                //
                // Do the two segments create an Angle?
                //
                Point shared = segments.get(s1).sharedVertex(segments.get(s2));
                if (shared != null)
                {
                    // Disallow straight angles (for now?)
                    if (!segments.get(s1).isCollinearWith(segments.get(s2)))
                    {
                        AngleEquivalenceRelation.getInstance().add(new Angle(segments.get(s1).other(shared), shared, segments.get(s2).other(shared)));
                    }               
                    //                    //
                    //                    // get all the points on each segment
                    //                    //
                    //                    ArrayList<Point> p1 = new ArrayList<Point>();
                    //                    ArrayList<Point> p2 = new ArrayList<Point>();
                    //                    for (Point p : PointFactory.getAllPoints())
                    //                    {
                    //                        if (segments.get(s1).pointLiesOn(p))
                    //                        {
                    //                            p1.add(p);
                    //                        }
                    //                        else if (segments.get(s2).pointLiesOn(p))
                    //                        {
                    //                            p2.add(p);
                    //                        }
                    //                    }
                    //
                    //                    //
                    //                    // for each triple <p1, vertex, p2> of points results in a new angle
                    //                    //
                    //                    for(Point ps1 : p1)
                    //                    {
                    //                        for(Point ps2 : p2)
                    //                        {
                    //                            if(ps1.structurallyEquals(ps2))
                    //                            {
                    //                                AngleEquivalenceRelation.getInstance().add(new Angle(ps1, shared, ps2));
                    //                            }
                    //                        } 
                    //                    }
                }
            }
        }
    }

    /*
     * @param inMiddles -- a list of InMiddle objects
     * Each in-the-middle relation results in a segment equation (corresponding to the segment addition axiom).
     * @return the list of new segment equations.
     */
    public static ArrayList<SegmentEquation> computeSegmentAdditionEquations(FactComputer fc)
    {
        ArrayList<SegmentEquation> eqs = new ArrayList<SegmentEquation>();

        for (InMiddle inMiddle : fc.getInMiddles())
        {
            // Create the two individual segments
            Segment seg1 = fc.getStructuralSegment(new Segment(inMiddle.getSegment().getPoint1(), inMiddle.getPoint()));
            Segment seg2 = fc.getStructuralSegment(new Segment(inMiddle.getSegment().getPoint2(), inMiddle.getPoint()));

            if (seg1 == null)
            {
                System.out.println("computeSegmentAdditionEquations::Precomputer failed to identify all segments: " + new Segment(inMiddle.getSegment().getPoint1(), inMiddle.getPoint()));
                ExceptionHandler.throwException("computeSegmentAdditionEquations::Precomputer failed to identify all segments: " + new Segment(inMiddle.getSegment().getPoint1(), inMiddle.getPoint()));
            }
            
            if (seg2 == null)
            {
                System.out.println("computeSegmentAdditionEquations::Precomputer failed to identify all segments: " + new Segment(inMiddle.getSegment().getPoint2(), inMiddle.getPoint()));
                ExceptionHandler.throwException("computeSegmentAdditionEquations::Precomputer failed to identify all segments: " + new Segment(inMiddle.getSegment().getPoint2(), inMiddle.getPoint()));
            }

            eqs.add(new SegmentEquation(new Addition(seg1, seg2), inMiddle.getSegment())); // AB + BC = AC
        }

        return eqs;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of congruent segment pairs
     */
    public static ArrayList<CongruentSegments> computeSegmentCongruences(FactComputer fc)
    {
        ArrayList<CongruentSegments> congruences = new ArrayList<CongruentSegments>();
        ArrayList<Segment> segments = fc.getSegments();

        for(int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                if(segments.get(s1).coordinateCongruent(segments.get(s2)))
                {
                    congruences.add(new CongruentSegments(segments.get(s1), segments.get(s2)));
                }
            }
        }

        // A segment needs to be congruent to itself
        for (Segment segment : segments)
        {
            congruences.add(new CongruentSegments(segment, segment));
        }

        return congruences;
    }

    /*
     * @param fc -- a fact computer containing access to segment congruences
     * @return the list of new (geometric) segment equations of the form AB = CD
     */
    public static ArrayList<SegmentEquation> computeSegmentEqualityEquations(FactComputer fc)
    {
        ArrayList<CongruentSegments> congruences = fc.getCongruentSegments();
        ArrayList<SegmentEquation> eqs = new ArrayList<SegmentEquation>();

        for (CongruentSegments cs : congruences)
        {
            // Avoid reflexive relationships: B = B
            if (!cs.isReflexive())
            {
                eqs.add(new GeometricSegmentEquation(cs.getcs1(), cs.getcs2()));
            }
        }

        return eqs;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of new segment equations.
     */
    public static ArrayList<Parallel> computeParallel(FactComputer fc)
    {
        ArrayList<Segment> segments = fc.getSegments();
        ArrayList<Parallel> parallels = new ArrayList<Parallel>();

        for(int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                if(segments.get(s1).isParallel(segments.get(s2)))
                {
                    parallels.add(new Parallel(segments.get(s1),segments.get(s2)));
                }
            }
        }

        return parallels;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of all intersection objects
     * An intersection is defined as two segments maintaining a single point of intersection (but not coinciding)
     * 
     * This method of intersection calculation currently excludes when either of them are intersecting at an endpoint
     * This is caused by segmentIntersection returning null when it occurs at an endpoint
     */
    public static MaximalIntersections computeSegmentSegmentIntersections(FactComputer fc)
    {
        MaximalSegments mss = fc.getMaximalSegments();
        MaximalIntersections mi = MaximalIntersections.getInstance();
        ArrayList<MaximalSegment> maxsegs = new ArrayList<MaximalSegment>(mss.getMaximalSegments());
        
        for(int ms1 = 0; ms1 < maxsegs.size() -1; ms1++)
        {
            for(int ms2 = ms1 +1; ms2< maxsegs.size(); ms2++)
            {
                Point inter = maxsegs.get(ms1).segmentIntersection(maxsegs.get(ms2));
                if(inter != null)
                {
                    if(!maxsegs.get(ms1).coinciding(maxsegs.get(ms2)))
                    {
                        mi.addMaximalIntersection(new MaximalIntersection(inter, maxsegs.get(ms1), maxsegs.get(ms2)));
                    }
                }
            }
        }
        
        return mi;
//        ArrayList<Intersection> intersections = new ArrayList<Intersection>();
//
//        for(int s1 = 0; s1 < segments.size() - 1; s1++)
//        {
//            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
//            {
//                // Intersects
//                Point inter = segments.get(s1).segmentIntersection(segments.get(s2));
//                if (inter != null)
//                {
//                    // Not coinciding
//                    if(!segments.get(s1).coinciding(segments.get(s2)))
//                    {
//                        intersections.add(new Intersection(inter, segments.get(s1), segments.get(s2)));
//                    }
//                }
//            }
//        }
//
//        return intersections;
    }

    /*
     * @param fc -- a fact computer containing access to intersections
     * @return the list of all intersections that are: perpendicular, bisectors, perpendicular bisectors
     */
    public static void computeSpecificIntersections(FactComputer fc,
            ArrayList<Perpendicular> perps,
            ArrayList<SegmentBisector> segBisectors,
            ArrayList<PerpendicularBisector> perpBisectors)
    {
        ArrayList<Intersection> intersections = fc.getIntersections();

        for (Intersection inter : intersections)
        {
            // General perpendicularity
            Point perpPoint = inter.getlhs().coordinatePerpendicular(inter.getrhs());
            if (perpPoint != null)
            {
                perps.add(new Perpendicular(inter));
            }

            // Segment Bisection; check both directions since an intersection may result in 2 distinct bisection facts
            Point bisecPoint1 = inter.getlhs().coordinateBisector(inter.getrhs());
            if (bisecPoint1 != null)
            {
                segBisectors.add(new SegmentBisector(inter, inter.getrhs()));
            }
            Point bisecPoint2 = inter.getrhs().coordinateBisector(inter.getlhs());
            if (bisecPoint2 != null)
            {
                segBisectors.add(new SegmentBisector(inter, inter.getlhs()));
            }

            // Perpendicular Bisectors
            if (perpPoint != null && bisecPoint1 != null)
            {
                perpBisectors.add(new PerpendicularBisector(inter, inter.getrhs()));
            }
            if (perpPoint != null && bisecPoint2 != null)
            {
                perpBisectors.add(new PerpendicularBisector(inter, inter.getlhs()));
            }
        }
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of all Proportional Segment pairs
     */
    public static ArrayList<ProportionalSegments> computeProportionalSegments(FactComputer fc)
    {
        ArrayList<Segment> segments = fc.getSegments();
        ArrayList<ProportionalSegments> props = new ArrayList<ProportionalSegments>();

        for (int s1 = 0; s1 < segments.size() - 1; s1++)
        {
            for (int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                // Ratio is really an integer or half-step
                Pair<Integer, Integer> proportion = segments.get(s1).coordinateProportional(segments.get(s2));
                if (proportion.getValue() != -1)
                {
                    if (proportion.getValue() <= PROPORTIONAL_STEP || proportion.getKey() <= PROPORTIONAL_STEP)
                    {
                        props.add(new ProportionalSegments(segments.get(s1), segments.get(s2)));
                    }
                }
            }
        }

        return props;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of all Proportional Segment pairs
     */
    public static ArrayList<SegmentEquation> computeProportionalSegmentEquations(FactComputer fc)
    {
        ArrayList<ProportionalSegments> props = fc.getProportionalSegments();
        ArrayList<SegmentEquation> eqs = new ArrayList<SegmentEquation>();

        for (ProportionalSegments prop : props)
        {
            Multiplication product = new Multiplication(new NumericValue(prop.getDictatedProportion()), prop.getSmallerSegment());

            eqs.add(new SegmentEquation(product, prop.getLargerSegment() ));
        }

        return eqs;
    }

    /*
     * @out-param the set of all right angles (strengthened and not)
     */
    public static void computeRightAngles(ArrayList<RightAngle> ras, ArrayList<Strengthened> sRas)
    {
        for (Angle ra : AngleEquivalenceRelation.getInstance().getCongruent(90))
        {
            Strengthened s = new Strengthened(ra, new RightAngle(ra));
            ras.add((RightAngle)s.getStrengthened());
            sRas.add(s);
        }
    }

    /*
     * @return the set of all congruent angles
     */
    public static HashSet<CongruentAngles> computeCongruentAngles()
    {
        // We use the angle container quite a bit; call once
        AngleEquivalenceRelation angleContainer = AngleEquivalenceRelation.getInstance();

        // An angle needs to be congruent to itself
        HashSet<CongruentAngles> congruences = new HashSet<CongruentAngles>();

        // The set of representative angles only
        Set<Angle> repAngles = angleContainer.getRepresentative();

        // For each representative angle, find all congruent angles (not just equivalence class)
        for (Angle rep : repAngles)
        {
            Set<Angle> congruentAngles = angleContainer.getCongruent(rep);
            Angle[] congruent = congruentAngles.toArray(new Angle[congruentAngles.size()]);

            // Construct the exhaustive set of all congruences.
            for (int a1 = 0; a1 < congruent.length - 1; a1++)
            {
                for (int a2 = a1 + 1; a2 < congruent.length; a2++)
                {
                    congruences.add(new CongruentAngles(congruent[a1], congruent[a2]));
                }
            }
        }

        //
        // An angle needs to be congruent to itself: reflexive
        //
        for (Angle angle : AngleEquivalenceRelation.getInstance().getAllAngles())
        {
            congruences.add(new CongruentAngles(angle, angle));
        }

        return congruences;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of angle equations relating direct equality
     */
    public static ArrayList<AngleEquation> computeEqualAngleEquations(FactComputer fc)
    {
        ArrayList<CongruentAngles> cass = fc.getCongruentAngles();
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (CongruentAngles cas : cass)
        {
            // Avoid reflexive relationships: B = B
            if (!cas.isReflexive())
            {
                eqs.add(new GeometricAngleEquation(cas.first(), cas.second()));
            }
        }

        return eqs;
    }

    /*
     * @param fc -- a fact computer containing access to segments
     * @return the list of all Proportional Segment pairs
     */
    public static HashSet<ProportionalAngles> computeProportionalAngles(FactComputer fc)
    {
        HashSet<ProportionalAngles> props = new HashSet<ProportionalAngles>();

        // Check all combinations of representative angle 
        Set<Angle> representative  = AngleEquivalenceRelation.getInstance().getRepresentative();
        Angle[] repAngles = representative.toArray(new Angle[representative.size()]);

        for (int a1 = 0; a1 < repAngles.length - 1; a1++)
        {
            for (int a2 = a1 + 1; a2 < repAngles.length; a2++)
            {
                Pair<Integer,Integer> proportion = repAngles[a1].CoordinateProportional(repAngles[a2]);

                if (proportion.getValue() != -1)
                {
                    if (proportion.getValue() <= PROPORTIONAL_STEP || proportion.getKey() <= PROPORTIONAL_STEP)
                    {
                        //
                        // All related angles are also proportional 
                        //
                        for (Angle first : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a1]))
                        {
                            for (Angle second : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a2]))
                            {
                                props.add(new ProportionalAngles(first, second));
                            }
                        }
                    }
                }
            }
        }

        return props;
    }

    /*
     * @param fc -- a fact computer containing access to all proportional angle facts
     * @return the list of all Proportional Angle equations
     */
    public static ArrayList<AngleEquation> computeProportionalAngleEquations(FactComputer fc)
    {
        ArrayList<ProportionalAngles> props = fc.getProportionalAngles();
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (ProportionalAngles prop : props)
        {
            Multiplication product = new Multiplication(new NumericValue(prop.getDictatedProportion()), prop.getSmallerAngle());

            eqs.add(new AngleEquation(product, prop.getLargerAngle()));
        }

        return eqs;
    }

    /*
     * @return the list of all Complementary Angle pairs
     */
    public static ArrayList<Complementary> computeComplementaryAngles()
    {
        ArrayList<Complementary> comps = new ArrayList<Complementary>();

        // Check all combinations of representative angle 
        Set<Angle> representative  = AngleEquivalenceRelation.getInstance().getRepresentative();
        Angle[] repAngles = representative.toArray(new Angle[representative.size()]);

        for (int a1 = 0; a1 < repAngles.length - 1; a1++)
        {
            for (int a2 = a1 + 1; a2 < repAngles.length; a2++)
            {
                if (repAngles[a1].IsComplementaryTo(repAngles[a2]))
                {
                    //
                    // All related angles are complementary 
                    //
                    for (Angle first : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a1]))
                    {
                        for (Angle second : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a2]))
                        {
                            comps.add(new Complementary(first, second));
                        }
                    }
                }
            }
        }

        return comps;
    }

    /*
     * @param fc -- a fact computer containing access to all complementary angle facts
     * @return the list of all equations of the form m<ABC + m<DEF = 90
     */
    public static ArrayList<AngleEquation> computeComplementaryEquations(FactComputer fc)
    {
        ArrayList<Complementary> comps = fc.getComplementaryAngles();
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (Complementary comp : comps)
        {
            Addition sum = new Addition(comp.getAngle1(), comp.getAngle2());

            eqs.add(new AngleEquation(sum, new NumericValue(90)));
        }

        return eqs;
    }

    /*
     * @return the list of all Complementary Angle pairs
     */
    public static ArrayList<Supplementary> computeSupplementaryAngles()
    {
        ArrayList<Supplementary> supps = new ArrayList<Supplementary>();

        // Check all combinations of representative angle 
        Set<Angle> representative  = AngleEquivalenceRelation.getInstance().getRepresentative();
        Angle[] repAngles = representative.toArray(new Angle[representative.size()]);

        for (int a1 = 0; a1 < repAngles.length - 1; a1++)
        {
            for (int a2 = a1 + 1; a2 < repAngles.length; a2++)
            {
                if (repAngles[a1].IsSupplementaryTo(repAngles[a2]))
                {
                    //
                    // All related angles are complementary 
                    //
                    for (Angle first : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a1]))
                    {
                        for (Angle second : AngleEquivalenceRelation.getInstance().getEquivalent(repAngles[a2]))
                        {
                            supps.add(new Supplementary(first, second));
                        }
                    }
                }
            }
        }

        return supps;
    }

    /*
     * @param fc -- a fact computer containing access to all supplementary angle facts
     * @return the list of all equations of the form m<ABC + m<DEF = 180
     */
    public static ArrayList<AngleEquation> computeSupplementaryEquations(FactComputer fc)
    {
        ArrayList<Supplementary> supps = fc.getSupplementaryAngles();
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (Supplementary supp : supps)
        {
            Addition sum = new Addition(supp.getAngle1(), supp.getAngle2());

            eqs.add(new AngleEquation(sum, new NumericValue(180)));
        }

        return eqs;
    }

    /*
     * @param fc -- FactComputer providing a list of Segments (angles come from the equivalence class)
     * @return the list of AngleBisectors facts
     */
    public static ArrayList<AngleBisector> computeAngleBisectors(FactComputer fc)
    {
        ArrayList<AngleBisector> abs = new ArrayList<AngleBisector>();

        // For all representative angles 
        for (Angle angle : AngleEquivalenceRelation.getInstance().getRepresentative())
        {
            // We do not consider straight angles
            if(!MathUtilities.doubleEquals(angle.getMeasure(), 180))
            {
                // Angle with segment results in the angle being bisected
                for (Segment segment : fc.getSegments())
                {
                    if (angle.CoordinateAngleBisector(segment))
                    {
                        // Construct angle bisectors for all equivalence class angles
                        for (Angle eqAngle : AngleEquivalenceRelation.getInstance().getEquivalent(angle))
                        {
                            abs.add(new AngleBisector(eqAngle, segment));
                        }
                    }
                }
            }
        }

        return abs;
    }

    /*
     * @param fc -- FactComputer providing a list of Segments (angles come from the equivalence class)
     * @return Each AngleBisector fact results in 4 new equations:
     *         (1) m<ABC = m<DBF
     *         (2) m<ABC + m<DBF = m<ABF
     *         (3) 2 m<ABC = m<ABF
     *         (4) 2 m<DBF = m<ABF
     */
    public static ArrayList<AngleEquation> computeAngleBisectorEquations(FactComputer fc)
    {
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        // For all representative angles 
        for (AngleBisector ab : fc.getAngleBisectors())
        {
            Pair<Angle, Angle> p = ab.getBisectedAngles();
            eqs.add(new AngleEquation( p.first() , p.second() ));
            eqs.add(new AngleEquation(new Addition(p.first(),p.second()), ab.getAngle()));
            eqs.add(new AngleEquation(new Multiplication(new NumericValue(2),  p.first()), ab.getAngle()));
            eqs.add(new AngleEquation(new Multiplication(new NumericValue(2),  p.second()), ab.getAngle()));
        }
        return eqs;
    }

    /*
     * @param fc -- a fact computer containing access to triangles
     * @return the list of all Congruent Triangle pairs
     */
    public static ArrayList<CongruentTriangles> computeCongruentTriangles(FactComputer fc)
    {
        ArrayList<Triangle> triangles = fc.getTriangles();
        ArrayList<CongruentTriangles> cts = new ArrayList<CongruentTriangles>();

        for (int t1 = 0; t1 < triangles.size(); t1++)
        {
            for (int t2 = t1 + 1; t2 < triangles.size(); t2++)
            {
                // Returns the proper ordering of points between the triangles
                Pair<Triangle,Triangle> corresponding = triangles.get(t1).CoordinateCongruent(triangles.get(t2));

                if(corresponding.getKey() != null && corresponding.getValue() != null)
                {
                    cts.add(new CongruentTriangles(corresponding.getKey(), corresponding.getValue()));
                }
            }
        }

        return cts;
    }

    /*
     * @param fc -- a fact computer containing access to triangles
     * @return the list of all Similar Triangles
     */
    public static ArrayList<SimilarTriangles> computeSimilarTriangles(FactComputer fc)
    {
        ArrayList<Triangle> triangles = fc.getTriangles();
        ArrayList<SimilarTriangles> sts = new ArrayList<SimilarTriangles>();

        //
        // Triangle congruences OR similarity (congruence is a stronger relationship than similarity)
        //
        for (int t1 = 0; t1 < triangles.size(); t1++)
        {
            for (int t2 = t1 + 1; t2 < triangles.size(); t2++)
            {
                // Should we coordinate the vertices?
                if (triangles.get(t1).CoordinateSimilar(triangles.get(t2)))
                {
                    sts.add(new SimilarTriangles(triangles.get(t1), triangles.get(t2)));
                }
            }
        }

        return sts;
    }

    /*
     * @param fc -- a fact computer containing access to triangles
     * @out-param medians -- a list of medians to be populated
     * @out-param altitudes -- a list of altitudes to be populated
     */
    public static void computeMedianAltitude(FactComputer fc,
            ArrayList<Median> medians, ArrayList<Altitude> altitudes)
    {
        ArrayList<Segment> segments = fc.getSegments();
        ArrayList<Triangle> triangles = fc.getTriangles();

        //
        // For all triangles, match segments to those triangles
        //
        for (Triangle tri : triangles)
        {
            for(Segment segment : segments)
            {
                if (tri.isMedian(segment)) medians.add(new Median(segment, tri));
                if(tri.isAltitude(segment)) altitudes.add(new Altitude(tri,segment));
            }
        }
    }

    /*
     * @param fc -- FactComputer providing a list of triangles
     * @out-param isoTris -- List of isosceles triangles
     * @out-param sIsoTris -- List of scalene triangles strengthtened to isosceles
     * @out-param eqTris -- List of equilateral triangles
     * @out-param sEqTris -- List of scalene triangles strengthtened to equilateral
     * @out-param rightTris -- List of right triangles
     * @out-param sRightTris -- List of scalene triangles strengthtened to right
     */
    public static void computeStrengthenedTriangles(FactComputer fc,
            ArrayList<IsoscelesTriangle> isoTris,
            ArrayList<Strengthened> sIsoTris,
            ArrayList<EquilateralTriangle> eqTris,
            ArrayList<Strengthened> sEqTris,
            ArrayList<RightTriangle> rightTris,
            ArrayList<Strengthened> sRightTris)
    {
        //
        // Each triangle may be strengthened multiple times: tri -> iso -> eq
        //
        for(Triangle tri : fc.getTriangles())
        {
            for(Strengthened s : Triangle.canBeStrengthened(tri))
            {
                if(s.getStrengthened() instanceof IsoscelesTriangle)
                {
                    sIsoTris.add(s);
                    isoTris.add((IsoscelesTriangle)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof EquilateralTriangle)
                {
                    sEqTris.add(s);
                    eqTris.add((EquilateralTriangle)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof RightTriangle)
                {
                    sRightTris.add(s);
                    rightTris.add((RightTriangle)s.getStrengthened());
                }
            }
        }
    }

    /*
     * @param fc -- FactComputer providing a list of triangles
     * @out-param -- List of specific quadrilaterals
     * @out-param -- List of strengthened form of the quadrilateral
     */
    public static void computeStrengthenedQuadrilaterals(FactComputer fc,
            ArrayList<Kite> kites,
            ArrayList<Strengthened> sKites,
            ArrayList<Rectangle> rectangles,
            ArrayList<Strengthened> sRectangles,
            ArrayList<Square> squares,
            ArrayList<Strengthened> sSquares,
            ArrayList<Parallelogram> parallelograms,
            ArrayList<Strengthened> sParallelograms,
            ArrayList<Trapezoid> trapezoids,
            ArrayList<Strengthened> sTrapezoids,
            ArrayList<Rhombus> rhombuses,
            ArrayList<Strengthened> sRhombuses,
            ArrayList<IsoscelesTrapezoid> isoTrapezoids,
            ArrayList<Strengthened> sIsoTrapezoids)
    {
        //
        // Each quadrilateral may be strenghtened multiple times: Quad -> trapezoid -> isoTrap; Quad -> Parallelogram?, etc.
        //
        for(Quadrilateral quad : fc.getQuadrilaterals())
        {
            for (Strengthened s : Quadrilateral.CanBeStrengthened(quad))
            {
                if (s.getStrengthened() instanceof Kite)
                {
                    sKites.add(s);
                    kites.add((Kite)s.getStrengthened());
                }
                else if (s.getStrengthened() instanceof Rectangle)
                {
                    sRectangles.add(s);
                    rectangles.add((Rectangle)s.getStrengthened());
                }
                else if (s.getStrengthened() instanceof Square)
                {
                    sSquares.add(s);
                    squares.add((Square)s.getStrengthened());
                }
                else if (s.getStrengthened() instanceof Parallelogram)
                {
                    sParallelograms.add(s);
                    parallelograms.add((Parallelogram)s.getStrengthened());
                }
                else if (s.getStrengthened() instanceof Trapezoid)
                {
                    sTrapezoids.add(s);
                    trapezoids.add((Trapezoid)s.getStrengthened());
                }

                else if (s.getStrengthened() instanceof Rhombus)
                {
                    sRhombuses.add(s);
                    rhombuses.add((Rhombus)s.getStrengthened());
                }
                else if (s.getStrengthened() instanceof IsoscelesTrapezoid)
                {
                    sIsoTrapezoids.add(s);
                    isoTrapezoids.add((IsoscelesTrapezoid)s.getStrengthened());
                }
            }
        }
    }




    //WARNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //THIS IS UNTESTED. IT SEEMS TO ONLY COMPILE IF I TAKE OUT THE TEMPLATE PARAMETER
    //        //Arc congruences
    //        CalculateArcCongruences(minorArcs);
    //        CalculateArcCongruences(majorArcs);
    //        CalculateArcCongruences(semiCircles);    

    //calculate congruent circles
    //calculate circle intersections
    //calculate congruent arcs
    //calculate arc intersections
    //calculate inscribed angle?
    //

    /*
     * @param fc -- FactComputer providing a list of circles
     * @return the set of all pairs of congruent circles
     */
    public static ArrayList<CongruentCircles> computeCongruentCircles(FactComputer fc)
    {
        ArrayList<CongruentCircles> congruences = new ArrayList<CongruentCircles>();
        ArrayList<Circle> circles = fc.getCircles();

        for (int c1 = 0; c1 < circles.size() - 1; c1++)
        {
            for (int c2 = c1 + 1; c2 < circles.size(); c2++)
            {
                if (circles.get(c1).CoordinateCongruent(circles.get(c2)))
                {
                    congruences.add(new CongruentCircles(circles.get(c1), circles.get(c2)));
                }
            }
        }

        return congruences;
    }

    /*
     * @param fc -- FactComputer providing a list of circles
     * @return the set of all pairs of congruent circles
     */
    public static ArrayList<CircleIntersection> computeCircleIntersection(FactComputer fc)
    {
        ArrayList<CircleIntersection> intersections = new ArrayList<CircleIntersection>();
        ArrayList<Circle> circles = fc.getCircles();

        for (int c1 = 0; c1 < circles.size() - 1; c1++)
        {
            for (int c2 = c1 + 1; c2 < circles.size(); c2++)
            {
                // Circle-Circle Intersections
                OutPair<Point, Point> intersectionPts = new OutPair<Point, Point>();
                if(IntersectionDelegate.findIntersection(circles.get(c1), circles.get(c2), intersectionPts))
                {
                    // Known issue:
                    // CircleCircleIntersection accepts a single point for intersection and most of the code is commented out 
                    //  this works for tangential intersection, and breaks otherwise

                    // it could be that for each intersection, a CircleCircleIntersection is created - this will be followed for now
                    intersections.add(new CircleCircleIntersection(intersectionPts.getKey(), circles.get(c1), circles.get(c2)));

                    if(intersectionPts.getValue() != null)
                    {
                        intersections.add(new CircleCircleIntersection(intersectionPts.getValue(), circles.get(c1), circles.get(c2)));
                    }
                }
            }
        }

        //
        // Circle-Segment intersections
        //
        for (Circle circle : circles)
        {
            for (Segment segment : fc.getSegments())
            {
                OutPair<Point, Point> intersectionPts = new OutPair<Point, Point>();
                if(IntersectionDelegate.findIntersection(circle, segment, intersectionPts))
                {
                    //There exists the same issue in CircleSegmentIntersection - only a single point is realized
                    //  handling in same fashion as above for now
                    intersections.add(new CircleSegmentIntersection(intersectionPts.getKey(), circle, segment));
                    if(intersectionPts.getValue() != null)
                    {
                        intersections.add(new CircleSegmentIntersection(intersectionPts.getValue(), circle, segment));
                    }
                }
            }
        }

        //calculate inscribed angles?
        //  there isn't a descriptor class for this - so doesn't look like it needs to be done
        //  in case it needs to be in the future:
        //  for every angle
        //      check if vertex is on the circle
        //      check if both rays extend into the circle
        //          (note this is slightly more complicated than at first glance - i.e. the second point that defines the ray could lay 
        //              in the circle, on the circle, or outside the circle)

        return intersections;
    }

    /*
     * @param fc -- a fact computer containing access to arcs and points
     * @return the list of new (all) ArcInMiddle fact clauses
     */
    public static ArrayList<ArcInMiddle> computeArcInMiddles(FactComputer fc)
    {
        ArrayList<ArcInMiddle> inMiddles = new ArrayList<ArcInMiddle>();

        // An InMiddle object consists of an arc and point.
        // A --------------------- M -------------- B
        for (Arc arc : fc.getArcs())
        {
            for (Point p : PointFactory.getAllPoints())
            {
                if (arc.PointLiesStrictlyOn(p))
                {
                    inMiddles.add(new ArcInMiddle(p, arc));
                }
            }
        }

        return inMiddles;
    }

    /*
     * @param fc -- FactComputer providing a list of arcs
     * @return the set of all pairs of congruent arcs
     */
    public static ArrayList<CongruentArcs> computeCongruentArcs(FactComputer fc)
    {
        ArrayList<CongruentArcs> congruences = new ArrayList<CongruentArcs>();
        ArrayList<Arc> arcs = fc.getArcs();

        for (int a1 = 0; a1 < arcs.size() - 1; a1++)
        {
            for (int a2 = a1 + 1; a2 < arcs.size(); a2++)
            {
                if (arcs.get(a1).CoordinateCongruent(arcs.get(a2)))
                {
                    congruences.add(new CongruentArcs(arcs.get(a1), arcs.get(a2)));
                }
            }
        }

        return congruences;
    }

    /*
     * @param inMiddles -- a list of InMiddle objects
     * Each in-the-middle relation results in a segment equation (corresponding to the segment addition axiom).
     * @return the list of new segment equations.
     */
    public static ArrayList<ArcEquation> computeArcAdditionEquations(FactComputer fc)
    {
        ArrayList<ArcEquation> eqs = new ArrayList<ArcEquation>();

        for (ArcInMiddle im : fc.getArcInMiddles())
        {
            // Create the two individual segments
            Arc arc1 = fc.getStructuralArc(new MinorArc(im.getArc().getCircle(), im.getArc().getEndpoint1(), im.getPoint()));
            Arc arc2 = fc.getStructuralArc(new MinorArc(im.getArc().getCircle(), im.getArc().getEndpoint2(), im.getPoint()));

            if (arc1 == null || arc2 == null)
            {
                ExceptionHandler.throwException("computeArcAdditionEquations::Precomputer failed to identify all arcs.");
            }

            eqs.add(new ArcEquation(new Addition(arc1, arc2), im.getArc())); // AB + BC = AC
        }

        return eqs;
    }
}