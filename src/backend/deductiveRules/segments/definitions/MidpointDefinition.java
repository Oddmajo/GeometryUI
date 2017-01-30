package backend.deductiveRules.segments.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    //
    // This implements forward and Backward instantiation
    // Forward is Midpoint -> Congruent Clause
    // Backward is Congruent -> Midpoint Clause
    //
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceMidpoints());
        
        
        return deductions;
    }
    
    
    //
    // Midpoint(M, Segment(A, B)) -> InMiddle(A, M, B)
    // Midpoint(M, Segment(A, B)) -> Congruent(Segment(A,M), Segment(M,B)); This implies: AM = MB
    //
    public Set<Deduction> deduceMidpoints()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<Midpoint> midpoints = _qhg.getMidpoints();
        Set<Strengthened> strengs = _qhg.getStrengthenedMidpoints();
        List<InMiddle> inMiddles = _qhg.getInMiddles();
        List<CongruentSegments> conSegs= _qhg.getCongruentSegments();
        
        for (InMiddle im : inMiddles)
        {
            //c# always checks if it is an InMiddle && not Midpoint - not sure how qhg decides what are InMiddles
            if(im instanceof Midpoint) continue;
            
            for (Midpoint midpt : midpoints)
            {
                deductions.addAll(deduceFromMidpoint(im, midpt, midpt));
            }

            for (Strengthened streng : strengs)
            {
                deductions.addAll(deduceFromMidpoint(im, (Midpoint)streng.getStrengthened(), streng));
            }

            for (CongruentSegments css : conSegs)
            {                
                deductions.addAll(deduceToMidpoint(im, css));
            }
        }
        
        return deductions;
    }

    


    public static Set<Deduction> deduceFromMidpoint(InMiddle im, Midpoint midpt, GroundedClause original)
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
    

    //
    // Congruent(Segment(A, M), Segment(M, B)) -> Midpoint(M, Segment(A, B))
    //
    public static Set<Deduction> deduceToMidpoint(InMiddle im, CongruentSegments css)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // A reflexive relationship cannot possibly create a midpoint situation
        if (css.isReflexive()) return deductions;

        // The congruence must relate two collinear segments...
        if (!css.getcs1().isCollinearWith(css.getcs2())) return deductions;

        // ...that share a vertex
        if (css.getcs1().sharedVertex(css.getcs2()) == null) return deductions;

        Point midpoint = css.getcs1().sharedVertex(css.getcs2());

        // Does this InMiddle relate to the congruent segments?
        if (!im.getPoint().structurallyEquals(midpoint)) return deductions;

        // Do the congruent segments combine into a single segment equating to the InMiddle?
        Segment overallSegment = new Segment(css.getcs1().other(midpoint), css.getcs2().other(midpoint));
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
