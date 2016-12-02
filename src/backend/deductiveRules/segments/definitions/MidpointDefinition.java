package backend.deductiveRules.segments.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Midpoint;
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
import backend.utilities.exception.ExceptionHandler;

public class MidpointDefinition extends Definition
{

    private static final String NAME = "Definition of Midpoint";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MIDPOINT_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public MidpointDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }
    
    public static void Clear()
    {
        candidateCongruent.clear();
        candidateSegments.clear();
        candidateInMiddle.clear();
        candidateMidpoint.clear();
        candidateStrengthened.clear();
    }

    private static ArrayList<Segment> candidateSegments = new ArrayList<Segment>();
    private static ArrayList<CongruentSegments> candidateCongruent = new ArrayList<CongruentSegments>();
    private static ArrayList<InMiddle> candidateInMiddle = new ArrayList<InMiddle>();
    private static ArrayList<Strengthened> candidateStrengthened = new ArrayList<Strengthened>();
    private static ArrayList<Midpoint> candidateMidpoint = new ArrayList<Midpoint>();

    
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceMidpoints());
        
        
        return deductions;
    }
    private Set<Deduction> deduceMidpoints()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<Midpoint> midpoints = _qhg.getMidpoints();
                
        for (GroundedClause clause : midpoints)
        {
            ArrayList<Deduction> returned = new ArrayList<Deduction>();

            if (clause instanceof Midpoint || clause instanceof Strengthened || clause instanceof InMiddle)
            {
                returned.addAll(deduceFromMidpoint(clause));
            }

            if (clause instanceof CongruentSegments || (clause instanceof InMiddle && !(clause instanceof Midpoint)))
            {
                returned.addAll(deduceToMidpoint(clause));
            }

            if (returned.size() != 2)
            {
                // LoggerFactory.getLogger(LoggerFactory.HYPERGRAPH_EDGE_CONSTRUCTION).writeln(NAME + ": Expect 2 deduced clauses.");
                ExceptionHandler.throwException(NAME + ": Expect 2 deduced clauses.");
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }

    private Set<Deduction> deduceFromMidpoint(GroundedClause clause)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        if (clause instanceof InMiddle && !(clause instanceof Midpoint))
        {
            InMiddle inMid = (InMiddle)clause;

            for (Midpoint midpt : candidateMidpoint)
            {
                deductions.addAll(deduceFromMidpoint(inMid, midpt, midpt));
            }

            for (Strengthened streng : candidateStrengthened)
            {
                deductions.addAll(deduceFromMidpoint(inMid, (Midpoint)streng.getStrengthened(), streng));
            }

            candidateInMiddle.add(inMid);
        }
        else if (clause instanceof Midpoint)
        {
            Midpoint midpt = (Midpoint)clause;

            for (InMiddle im : candidateInMiddle)
            {
                deductions.addAll(deduceFromMidpoint(im, midpt, midpt));
            }

            candidateMidpoint.add(midpt);
        }
        else if (clause instanceof Strengthened)
        {
            Strengthened streng = (Strengthened)clause;

            if (!(streng.getStrengthened() instanceof Midpoint)) return deductions;

            for (InMiddle im : candidateInMiddle)
            {
                deductions.addAll(deduceFromMidpoint(im, (Midpoint)streng.getStrengthened(), streng));
            }

            candidateStrengthened.add(streng);
        }

        return deductions;
    }

    private static Set<Deduction> deduceFromMidpoint(InMiddle im, Midpoint midpt, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Does this ImMiddle apply to this midpoint?
        if (!im.getPoint().structurallyEquals(midpt.getPoint())) return deductions;
        if (!im.getSegment().structurallyEquals(midpt.getSegment())) return deductions;

        // For hypergraph
        ArrayList<GroundedClause> antecedent = Utilities.MakeList(original);

        // Backward: Midpoint(M, Segment(A, B)) -> InMiddle(A, M, B)
        deductions.add(new Deduction(antecedent, im, ANNOTATION));

        //
        // Forward: Midpoint(M, Segment(A, B)) -> Congruent(Segment(A,M), Segment(M,B))
        //
        Segment left = new Segment(midpt.getSegment().getPoint1(), midpt.getPoint());
        Segment right = new Segment(midpt.getPoint(), midpt.getSegment().getPoint2());
        CongruentSegments ccss = new CongruentSegments(left, right);
        deductions.add(new Deduction(antecedent, ccss, ANNOTATION));

        return deductions;
    }
    

    private static Set<Deduction> deduceToMidpoint(GroundedClause clause)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        if (clause instanceof InMiddle && !(clause instanceof Midpoint))
        {
            InMiddle inMid = (InMiddle)clause;

            for (CongruentSegments css : candidateCongruent)
            {
                deductions.addAll(deduceToMidpoint(inMid, css));
            }

            // No need to add this InMiddle object to the list since it was added previously in deduceFrom
        }
        else if (clause instanceof CongruentSegments)
        {
            CongruentSegments css = (CongruentSegments)clause;

            // A reflexive relationship cannot possibly create a midpoint situation
            if (css.isReflexive()) return deductions;

            // The congruence must relate two collinear segments...
            if (!css.getcs1().IsCollinearWith(css.getcs2())) return deductions;

            // ...that share a vertex
            if (css.getcs1().SharedVertex(css.getcs2()) == null) return deductions;

            for (InMiddle im : candidateInMiddle)
            {
                deductions.addAll(deduceToMidpoint(im, css));
            }

            candidateCongruent.add(css);
        }

        return deductions;
    }

    //
    // Congruent(Segment(A, M), Segment(M, B)) -> Midpoint(M, Segment(A, B))
    //
    private static Set<Deduction> deduceToMidpoint(InMiddle im, CongruentSegments css)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Point midpoint = css.getcs1().SharedVertex(css.getcs2());

        // Does this InMiddle relate to the congruent segments?
        if (!im.getPoint().structurallyEquals(midpoint)) return deductions;

        // Do the congruent segments combine into a single segment equating to the InMiddle?
        Segment overallSegment = new Segment(css.getcs1().OtherPoint(midpoint), css.getcs2().OtherPoint(midpoint));
        if (!im.getSegment().structurallyEquals(overallSegment)) return deductions;

        Strengthened newMidpoint = new Strengthened(im, new Midpoint(im)); 

        // For hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(im);
        antecedent.add(css);

        deductions.add(new Deduction(antecedent, newMidpoint, ANNOTATION));

        return deductions;
    }
}
