package backend.deductiveRules.segments.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class VerticalAnglesTheorem extends Theorem
{
    private static final String NAME = "Vertical Angles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.VERTICAL_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public VerticalAnglesTheorem(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        
        ANNOTATION.active = RuleFactory.JustificationSwitch.VERTICAL_ANGLES;
    }
    
    //
    // Intersect(X, Segment(A, B), Segment(C, D)) -> Congruent(Angle(A, X, C), Angle(B, X, D)),
    //                                               Congruent(Angle(A, X, D), Angle(C, X, B))
    //
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // Acquire all Midpoint clauses from the hypergraph
        Set<Intersection> intersections = _qhg.getIntersections();

        for (Intersection intersection : intersections)
        {
           deductions.addAll(deduceTheorem(intersection));
        }
        
        return deductions;
    }

    public Set<Deduction> deduceTheorem(Intersection intersection)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Verify that this intersection is composed of two overlapping segments
        // That is, we do not allow a segment to stand on another:
        //      \
        //       \
        //        \
        //   ______\_______
        //
        if (intersection.standsOn()) return deductions;

        //
        // Congruent(Angle(A, X, C), Angle(B, X, D))
        //
        List<GroundedClause> antecedent1 = new ArrayList<GroundedClause>();
        antecedent1.add(intersection);
        
        Angle ang1Set1 = new Angle(intersection.getlhs().getPoint1(), intersection.getIntersect(), intersection.getrhs().getPoint1());
        Angle ang2Set1 = new Angle(intersection.getlhs().getPoint2(), intersection.getIntersect(), intersection.getrhs().getPoint2());
        antecedent1.add(ang1Set1);
        antecedent1.add(ang2Set1);
        
        GeometricCongruentAngles cca1 = new GeometricCongruentAngles(ang1Set1, ang2Set1);
        //cca1.MakeIntrinsic();                   // This is an 'obvious' notion so it should be intrinsic to any figure
        deductions.add(new Deduction(antecedent1, cca1, ANNOTATION));

        //
        // Congruent(Angle(A, X, D), Angle(C, X, B))
        //
        List<GroundedClause> antecedent2 = new ArrayList<GroundedClause>();
        antecedent1.add(intersection);

        Angle ang1Set2 = new Angle(intersection.getlhs().getPoint1(), intersection.getIntersect(), intersection.getrhs().getPoint2());
        Angle ang2Set2 = new Angle(intersection.getlhs().getPoint2(), intersection.getIntersect(), intersection.getrhs().getPoint1());
        antecedent1.add(ang1Set2);
        antecedent1.add(ang2Set2);
       
        
        GeometricCongruentAngles cca2 = new GeometricCongruentAngles(ang1Set2, ang2Set2);
        //cca2.MakeIntrinsic();                    // This is an 'obvious' notion so it should be intrinsic to any figure
        deductions.add(new Deduction(antecedent2, cca2, ANNOTATION));
        
        return deductions;
    }
}