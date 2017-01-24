package backend.deductiveRules.segments.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.Strengthened;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;

public class PerpendicularSegments extends Definition
{
    private static final String NAME = "Perpendicular segments from intersection";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

//    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PERPENDICULAR_SEGMENTS);
    @Override public Annotation getAnnotation() { return new Annotation(NAME, false); }


    public PerpendicularSegments(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    //
    // Collinear(A, B, C, D, ...) -> Angle(A, B, C), Angle(A, B, D), Angle(A, C, D), Angle(B, C, D),...
    // All angles will have measure 180^o
    // There will be nC3 resulting clauses.
    //
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromIntersection());

        return deductions;
    }
    
    public Set<Deduction> deduceFromIntersection()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        List<Intersection> inters = _qhg.getIntersections();      
        
        for (Intersection inter : inters)
        {
            deductions.addAll(deduceFromIntersection(inter));
        }

        return deductions;
    }
    
    //
    // Collinear(A, B, C, D, ...) -> Angle(A, B, C), Angle(A, B, D), Angle(A, C, D), Angle(B, C, D),...
    // All angles will have measure 180^o
    // There will be nC3 resulting clauses.
    //
    public static List<Pair<List<GroundedClause>, GroundedClause>> deduceFromIntersection(GroundedClause c)
    {

        Intersection pCand = (Intersection)c;
        List<Pair<List<GroundedClause>, GroundedClause>> newGrounded = new ArrayList<Pair<List<GroundedClause>, GroundedClause>>();

        if (pCand.isPerpendicular() == true)
        {
//            Perpendicular newPerpendicular = new Perpendicular(pCand.getlhs(),pCand.getrhs(), NAME);
            Perpendicular newPerpendicular = new Perpendicular(pCand);
            List<GroundedClause> antecedent = Utilities.MakeList(pCand);
            newGrounded.add(new Pair<List<GroundedClause>, GroundedClause>(antecedent, newPerpendicular));
         }
                    
                
            
       

        return newGrounded;
    }

}
