package backend.deductiveRules.segments.theorems;

import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.GeometricSegmentEquation;
import backend.symbolicAlgebra.equations.SegmentEquation;
import backend.symbolicAlgebra.equations.operations.Multiplication;
import backend.utilities.exception.ExceptionHandler;

public class MidpointTheorem extends Theorem
{
    private static final String NAME = "Midpoint Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MIDPOINT_THEOREM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public MidpointTheorem(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }
    
    //
    // Midpoint(M, Segment(A, B)) -> 2AM = AB, 2BM = AB          A ------------- M ------------- B
    //
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceMidpoints());
        deductions.addAll(deduceStrengthened());        
        
        return deductions;
    }

    public Set<Deduction> deduceMidpoints()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<Midpoint> midpoints = _qhg.getMidpoints();
        System.out.println("Midpoint Theorem: midpoints = " + midpoints.toString());
                
        for (Midpoint midpt : midpoints)
        {
            Set<Deduction> returned = deduceFromMidpoint(midpt, midpt);
            
            if (returned.size() != 2)
            {
                System.out.println("MidpointTheorem: expect 2 deduced clauses.");
                // LoggerFactory.getLogger(LoggerFactory.HYPERGRAPH_EDGE_CONSTRUCTION).writeln(NAME + ": Expect 2 deduced clauses.");
                ExceptionHandler.throwException(NAME + ": Expect 2 deduced clauses.");
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }
    
    public Set<Deduction> deduceStrengthened()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Strengthened midpoint clauses from the hypergraph
        Set<Strengthened> strengtheneds = _qhg.getStrengthenedMidpoints();
                
        for (Strengthened strengthened : strengtheneds)
        {
            Set<Deduction> returned = deduceFromMidpoint(strengthened, (Midpoint)strengthened.getStrengthened());
            
            if (returned.size() != 2)
            {
                // LoggerFactory.getLogger(LoggerFactory.HYPERGRAPH_EDGE_CONSTRUCTION).writeln(NAME + ": Expect 2 deduced clauses.");
                ExceptionHandler.throwException(NAME + ": Expected 2 deduced clauses.");
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }

    //
    // Midpoint(M, Segment(A, B)) -> 2AM = AB, 2BM = AB          A ------------- M ------------- B
    //
    public Set<Deduction> deduceFromMidpoint(GroundedClause original, Midpoint midpt)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Construct the desired equations; look the exact version up in the hypergraph
        //
        
        // Construct 2AM
        Multiplication product1 = new Multiplication(new NumericValue(2), new Segment(midpt.getPoint(), midpt.getSegment().getPoint1()));

        // Construct 2BM
        Multiplication product2 = new Multiplication(new NumericValue(2), new Segment(midpt.getPoint(), midpt.getSegment().getPoint2()));

        // 2X = AB
        // TODO: these do not work @author Drew W
        //  TODO: verify that these actually work now @author JPN
        SegmentEquation hgEq1 = _qhg.getSegmentEquation(new GeometricSegmentEquation(product1, midpt.getSegment()));
        SegmentEquation hgEq2 = _qhg.getSegmentEquation(new GeometricSegmentEquation(product2, midpt.getSegment()));
        
        System.out.println("MidpointTheorem: hgEq1 = " + hgEq1);
        System.out.println("MidpointTheorem: hgEq2 = " + hgEq2);

        // For hypergraph
        Deduction d1 = new Deduction(original, hgEq1, ANNOTATION);
        Deduction d2 = new Deduction(original, hgEq2, ANNOTATION);

        deductions.add(d1);
        deductions.add(d2);
        
        return deductions;
    }    
}