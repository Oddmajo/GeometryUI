package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class MedianDefinition extends Definition
{
    private static final String NAME = "Median Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public MedianDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.MEDIAN_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromMedian());
        deductions.addAll(deduceToMedian());

        return deductions;
    }

    public Set<Deduction> deduceFromMedian()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<InMiddle> inMiddles = _qhg.getInMiddles();      
        Set<Median> medians = _qhg.getMedians();

        for (Median median : medians)
        {
            for (InMiddle inMiddle : inMiddles)
            {
                deductions.addAll(deduceFromMedian(inMiddle, median));
            }
        }

        return deductions;
    }

    //     B ---------V---------A
    //                 \
    //                  \
    //                   \
    //                    C
    //
    // InMiddle(V, A, B), Median(Segment(V, C), Triangle(C, A, B)) -> Midpoint(V, Segment(B, A))
    //
    private Set<Deduction> deduceFromMedian(InMiddle im, Median median)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Which point is on the side of the triangle?
        Point vertexOnTriangle = median.getTheTriangle().getVertexOn(median.getMedianSegment());
        Segment segmentCutByMedian = median.getTheTriangle().oppositeSide(vertexOnTriangle);
        Point midpt = segmentCutByMedian.segmentIntersection(median.getMedianSegment());

        // This is to acquire the name of the midpoint, nothing more.
        if (midpt.equals(median.getMedianSegment().getPoint1())) midpt = median.getMedianSegment().getPoint1();
        else if (midpt.equals(median.getMedianSegment().getPoint2())) midpt = median.getMedianSegment().getPoint2();

        // Does this median apply to this InMiddle? Point check ...
        if (!im.getPoint().structurallyEquals(midpt)) return deductions;

        // Segment check
        if (!im.getSegment().structurallyEquals(segmentCutByMedian)) return deductions;

        // Create the midpoint
        Strengthened newMidpoint = new Strengthened(im, new Midpoint(im));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(im);
        antecedent.add(median);

        deductions.add(new Deduction(antecedent, newMidpoint, ANNOTATION));

        return deductions;
    }

    //     B ---------V---------A
    //                 \
    //                  \
    //                   \
    //                    C
    //
    // SegmentBisector(Segment(V, C), Segment(B, A)), Triangle(A, B, C) -> Median(Segment(V, C), Triangle(A, B, C))
    //
    public Set<Deduction> deduceToMedian()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();      
        Set<SegmentBisector> segmentBisectors = _qhg.getSegmentBisectors();
        Set<Strengthened> strengs = _qhg.getStrengthenedSegmentBisectors();
        
        for (SegmentBisector segmentBisector : segmentBisectors)
        {
            for (Triangle triangle : triangles)
            {
                deductions.addAll(deduceToMedian(triangle, segmentBisector, segmentBisector));
            }
        }

        for (Strengthened streng : strengs)
        {
            for (Triangle triangle : triangles)
            {
                deductions.addAll(deduceToMedian(triangle, (SegmentBisector)streng.getStrengthened(), streng));
            }
        }
        
        return deductions;
    }

    //
    // Take the angle congruence and bisector and create the AngleBisector relation
    //
    private Set<Deduction> deduceToMedian(Triangle tri, SegmentBisector sb, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The Bisector cannot be a side of the triangle.
        if (tri.CoincidesWithASide(sb.getBisector()) != null) return deductions;

        // Acquire the intersection segment that coincides with the base of the triangle
        Segment triangleBaseCandidate = sb.getBisected().OtherSegment(sb.getBisector());
        Segment triangleBase = tri.CoincidesWithASide(triangleBaseCandidate);
        if (triangleBase == null) return deductions;

        // The candidate base and the actual triangle side must equate exactly
        if (!triangleBase.containedIn(triangleBaseCandidate) || !triangleBaseCandidate.containedIn(triangleBase)) return deductions;

        // The point opposite the base of the triangle must be within the endpoints of the bisector
        Point oppPoint = tri.oppositePoint(triangleBase);
        if (!sb.getBisector().pointLiesOn(oppPoint)) return deductions;

        // -> Median(Segment(V, C), Triangle(A, B, C))
        Median newMedian = new Median(sb.getBisector(), tri);

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri);
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newMedian, ANNOTATION));

        return deductions;
    }
}
