package backend.deductiveRules.segments.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class SegmentBisectorDefinition extends Definition
{
    private static final String NAME = "Segment Bisector Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SegmentBisectorDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromSegmentBisector());
        deductions.addAll(deduceToSegmentBisector());

        return deductions;
    }

    // original C# code for reference
//    //
//    // This implements forward and Backward instantiation
//    //
//    public static List<EdgeAggregator> Instantiate(GroundedClause clause)
//    {
//        ANNOTATION.active = EngineUIBridge.JustificationSwitch.SEGMENT_BISECTOR_DEFINITION;
//
//        if (clause is SegmentBisector || clause is Strengthened || clause is InMiddle) return InstantiateFromSegmentBisector(clause);
//
//        if (clause is Intersection || clause is CongruentSegments) return InstantiateToSegmentBisector(clause);
//
//        return new List<EdgeAggregator>();
//    }

    //     B ---------V---------A
    //                 \
    //                  \
    //                   \
    //                    C
    //
    // SegmentBisector(Segment(V, C), Segment(B, A)) -> Midpoint(V, Segment(B, A))
    //    
    public Set<Deduction> deduceFromSegmentBisector()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<InMiddle> middles = _qhg.getInMiddles();
        List<SegmentBisector> segmentBisectors = _qhg.getSegmentBisectors();
        List<Strengthened> strengs = _qhg.getStrengthenedSegmentBisectors();

        for (SegmentBisector segmentBisector : segmentBisectors)
        {
            for (InMiddle middle : middles)
            {
                deductions.addAll(deduceFromSegmentBisector(middle, segmentBisector, segmentBisector));
            }
        }

        for (Strengthened streng : strengs)
        {
            for (InMiddle middle : middles)
            {
                deductions.addAll(deduceFromSegmentBisector(middle, (SegmentBisector)streng.getStrengthened(), streng));
            }
        }

        return deductions;
    }

    public static Set<Deduction> deduceFromSegmentBisector(InMiddle im, SegmentBisector sb, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this bisector apply to this InMiddle? Check point of intersection
        if (!im.getPoint().structurallyEquals(sb.getBisected().getIntersect())) return deductions;

        // Segments must equate
        if (!im.getSegment().structurallyEquals(sb.getBisected().OtherSegment(sb.getBisector()))) return deductions;

        // Create the midpoint
        Strengthened newMidpoint = new Strengthened(im, new Midpoint(im));

        // For hypergraph
        List<GroundedClause> antecedent = Utilities.MakeList(original);
        antecedent.add(im);

        deductions.add(new Deduction(antecedent, newMidpoint, ANNOTATION));

        return deductions;
    }

    //     B ---------V---------A
    //                 \
    //                  \
    //                   \
    //                    C
    //
    // Congruent(Segment(B, V), Segment(V, A)), Intersection(V, Segment(B, A), Segment(V, C)) -> SegmentBisector(Segment(V, C), Segment(B, A))
    //
    public Set<Deduction> deduceToSegmentBisector()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();      
        List<Intersection> intersections = _qhg.getIntersections();

        for (CongruentSegments cs : congruentSegments)
        {
            // We are not interested in a reflexive relationship
            if (cs.isReflexive()) return deductions;

            // The segments need to be collinear
            if (!cs.getcs1().isCollinearWith(cs.getcs2())) return deductions;

            for (Intersection i : intersections)
            {
                // The congruent segments need to share an endpoint (adjacent congruent segments)
                Point shared = i.getIntersect();
                if (shared == null) return deductions;

                // If we have a corner situation, we are not interested
                if (i.StandsOnEndpoint()) return deductions;

                deductions.addAll(deduceToSegmentBisector(shared, i, cs));
            }
        }

        return deductions;
    }

    //
    // Take the angle congruence and bisector and create the AngleBisector relation
    //              \
    //               \
    //     B ---------V---------A
    //                 \
    //                  \
    //                   C
    //
    private static Set<Deduction> deduceToSegmentBisector(Point intersectionPoint, Intersection inter, CongruentSegments cs)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does the given point of intersection apply to this actual intersection object
        if (!intersectionPoint.equals(inter.getIntersect())) return deductions;

        // The entire segment AB
        Segment overallSegment = new Segment(cs.getcs1().other(intersectionPoint), cs.getcs2().other(intersectionPoint));

        // The segment must align completely with one of the intersection segments
        Segment interCollinearSegment = inter.GetCollinearSegment(overallSegment);
        if (interCollinearSegment == null) return deductions;

        // Does this intersection have the entire segment AB
        if (!inter.HasSegment(overallSegment)) return deductions;

        Segment bisector = inter.OtherSegment(overallSegment);
        Segment bisectedSegment = inter.GetCollinearSegment(overallSegment);

        // Check if the bisected segment extends is the exact same segment as the overall segment AB
        if (!bisectedSegment.structurallyEquals(overallSegment))
        {
            if (overallSegment.pointLiesBetweenEndpoints(bisectedSegment.getPoint1()) &&
                    overallSegment.pointLiesBetweenEndpoints(bisectedSegment.getPoint2())) return deductions;
        }

        SegmentBisector newSB = new SegmentBisector(inter, bisector);

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(inter);
        antecedent.add(cs);

        deductions.add(new Deduction(antecedent, newSB, ANNOTATION));
        return deductions;
    }

}
