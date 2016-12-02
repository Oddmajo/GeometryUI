package backend.deductiveRules.segments.axioms;

import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Midpoint;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.equations.GeometricSegmentEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.utilities.exception.ExceptionHandler;

public class SegmentAdditionAxiom extends Axiom
{
    private static final String NAME = "Segment Addition Axiom";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SEGMENT_ADDITION_AXIOM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public SegmentAdditionAxiom(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceInMiddle());
        
        
        return deductions;
    }

    public Set<Deduction> deduceInMiddle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<Midpoint> inMiddles = _qhg.getMidpoints();
                
        for (InMiddle middle : inMiddles)
        {
            Set<Deduction> returned = deduceFromInMiddle(middle, middle);
            
            if (returned.size() != 1)
            {
                // LoggerFactory.getLogger(LoggerFactory.HYPERGRAPH_EDGE_CONSTRUCTION).writeln(NAME + ": Expect 2 deduced clauses.");
                ExceptionHandler.throwException(NAME + ": Expect 1 deduced clause.");
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }

    private Set<Deduction> deduceFromInMiddle(GroundedClause original, InMiddle middle)
    {
        HashSet<Deduction> deduction = new HashSet<Deduction>();
        InMiddle im = (InMiddle) middle;

        Segment s1 = new Segment(im.getSegment().getPoint1(), im.getPoint());
        Segment s2 = new Segment(im.getPoint(), im.getSegment().getPoint2());
        Addition sum = new Addition(s1, s2);
        GeometricSegmentEquation eq = new GeometricSegmentEquation(sum, im.getSegment());
        eq.makeAxiomatic();

        // For hypergraph
//        List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(im);
        deduction.add(new Deduction(original, eq, ANNOTATION));
       

        return deduction;
    }

}
