package backend.deductiveRules.angles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

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

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceVerticalAngles());

        return deductions;
    }
    
    //
    // Intersect(X, Segment(A, B), Segment(C, D)) -> Congruent(Angle(A, X, C), Angle(B, X, D)),
    //                                               Congruent(Angle(A, X, D), Angle(C, X, B))
    //
    public Set<Deduction> deduceVerticalAngles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Intersection> intersections = _qhg.getIntersections();   

        for (Intersection intersect : intersections)
        {
                deductions.addAll(deduceVerticalAngles(intersect));
        }

        return deductions;
    }
    
    public static Set<Deduction> deduceVerticalAngles(Intersection inter)
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
        if (inter.standsOn()) return deductions;

        //
        // Congruent(Angle(A, X, C), Angle(B, X, D))
        //
        List<GroundedClause> antecedent1 = Utilities.MakeList(inter); 
        Angle ang1Set1 = Angle.AcquireFigureAngle(new Angle(inter.getlhs().getPoint1(), inter.getIntersect(), inter.getrhs().getPoint1()));
        Angle ang2Set1 = Angle.AcquireFigureAngle(new Angle(inter.getlhs().getPoint2(), inter.getIntersect(), inter.getrhs().getPoint2()));
        antecedent1.add(ang1Set1);
        antecedent1.add(ang2Set1);
        GeometricCongruentAngles cca1 = new GeometricCongruentAngles(ang1Set1, ang2Set1);
        cca1.makeIntrinsic(); // This is an 'obvious' notion so it should be intrinsic to any figure
        deductions.add(new Deduction(antecedent1, cca1, ANNOTATION));

        //
        // Congruent(Angle(A, X, D), Angle(C, X, B))
        //
        List<GroundedClause> antecedent2 = Utilities.MakeList(inter);
        Angle ang1Set2 = Angle.AcquireFigureAngle(new Angle(inter.getlhs().getPoint1(), inter.getIntersect(), inter.getrhs().getPoint2()));
        Angle ang2Set2 = Angle.AcquireFigureAngle(new Angle(inter.getlhs().getPoint2(), inter.getIntersect(), inter.getrhs().getPoint1()));
        antecedent2.add(ang1Set2);
        antecedent2.add(ang2Set2);
        GeometricCongruentAngles cca2 = new GeometricCongruentAngles(ang1Set2, ang2Set2);
        cca2.makeIntrinsic(); // This is an 'obvious' notion so it should be intrinsic to any figure
        deductions.add(new Deduction(antecedent2, cca2, ANNOTATION));

        return deductions;
    }

}
