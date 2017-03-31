package backend.deductiveRules.segments.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class PerpendicularDefinition extends Definition
{

    private static final String NAME = "Perpendicular Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PERPENDICULAR_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public PerpendicularDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PERPENDICULAR_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromPerpendicular());
        deductions.addAll(deduceToPerpendicular());

        return deductions;
    }

    // original C# for reference
//    //
//    // This implements forward and Backward instantiation
//    //
//    public static List<Deduction> Instantiate(GroundedClause clause)
//    {
//        ANNOTATION.active = EngineUIBridge.JustificationSwitch.PERPENDICULAR_DEFINITION;
//
//        // FROM Perpendicular
//        if (clause is Perpendicular) return InstantiateFromPerpendicular(clause, clause as Perpendicular);
//
//        // TO Perpendicular
//        if (clause is RightAngle || clause is Intersection) return InstantiateToPerpendicular(clause);
//
//        // Handle Strengthening; may be a Perpendicular (FROM) or Right Angle (TO)
//
//        Strengthened streng = clause as Strengthened;
//        if (streng != null)
//        {
//            if (streng.strengthened is Perpendicular && !(streng.strengthened is PerpendicularBisector))
//            {
//                return InstantiateFromPerpendicular(clause, streng.strengthened as Perpendicular);
//            }
//            else if (streng.strengthened is RightAngle)
//            {
//                return InstantiateToPerpendicular(clause);
//            }
//        }
//
//        return new List<Deduction>();
//    }

    // 
    // Perpendicular(B, Segment(A, B), Segment(B, C)) -> RightAngle(), RightAngle()
    //
    public Set<Deduction> deduceFromPerpendicular()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // test stuff
        System.out.println(_qhg.getIntersections().toString());

        Set<Perpendicular> perpendiculars = _qhg.getPerpendicular();
        System.out.println(perpendiculars.toString());
        Set<Strengthened> streng = _qhg.getStrengthenedPerpendicular();
        System.out.println(streng.toString());

        for (Perpendicular p : perpendiculars)
        {
            deductions.addAll(deduceFromPerpendicular(p, p));
        }
        
        for (Strengthened s : streng)
        {
            deductions.addAll(deduceFromPerpendicular(s, (Perpendicular) s.getStrengthened()));
        }

        return deductions;
    }

    private static Set<Deduction> deduceFromPerpendicular(GroundedClause original, Perpendicular perp)
    {
        Set<Deduction> newGrounded = new HashSet<Deduction>();
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        //          top
        //           |
        //           |_
        //           |_|____ right
        if (perp.StandsOnEndpoint())
        {
            Point top = perp.getlhs().other(perp.getIntersect());
            Point right = perp.getrhs().other(perp.getIntersect());

            Strengthened streng = new Strengthened(new Angle(top, perp.getIntersect(), right), new RightAngle(top, perp.getIntersect(), right));

            newGrounded.add(new Deduction(antecedent, streng, ANNOTATION));
        }
        //          top
        //           |
        //           |_
        // left  ____|_|____ right
        else if (perp.standsOn())
        {
            Point top = perp.getlhs().getPoint1();
            Point center = perp.getIntersect();
            Point left = perp.getrhs().getPoint1();
            Point right = perp.getrhs().getPoint2();
            if (perp.getlhs().pointLiesBetweenEndpoints(perp.getIntersect()))
            {
                left = perp.getlhs().getPoint1();
                right = perp.getlhs().getPoint2();
                top = perp.getrhs().other(perp.getIntersect());
            }
            else if (perp.getrhs().pointLiesBetweenEndpoints(perp.getIntersect()))
            {
                left = perp.getrhs().getPoint1();
                right = perp.getrhs().getPoint2();
                top = perp.getlhs().other(perp.getIntersect());
            }
            else return (Set<Deduction>) newGrounded;

            Strengthened topRight = new Strengthened(new Angle(top, center, right), new RightAngle(top, center, right));
            Strengthened topLeft = new Strengthened(new Angle(top, center, left), new RightAngle(top, center, left));

            newGrounded.add(new Deduction(antecedent, topRight, ANNOTATION));
            newGrounded.add(new Deduction(antecedent, topLeft, ANNOTATION));
        }
        //          top
        //           |
        //           |_
        // left  ____|_|____ right
        //           |
        //           |
        //         bottom
        else if (perp.Crossing())
        {
            Point top = perp.getlhs().getPoint1();
            Point bottom = perp.getlhs().getPoint2();
            Point center = perp.getIntersect();
            Point left = perp.getrhs().getPoint1();
            Point right = perp.getrhs().getPoint2();

            Strengthened topRight = new Strengthened(new Angle(top, center, right), new RightAngle(top, center, right));
            Strengthened bottomRight = new Strengthened(new Angle(right, center, bottom), new RightAngle(right, center, bottom));
            Strengthened bottomLeft = new Strengthened(new Angle(left, center, bottom), new RightAngle(left, center, bottom));
            Strengthened topLeft = new Strengthened(new Angle(top, center, left), new RightAngle(top, center, left));

            newGrounded.add(new Deduction(antecedent, topRight, ANNOTATION));
            newGrounded.add(new Deduction(antecedent, bottomRight, ANNOTATION));
            newGrounded.add(new Deduction(antecedent, bottomLeft, ANNOTATION));
            newGrounded.add(new Deduction(antecedent, topLeft, ANNOTATION));
        }

        return newGrounded;
    }

    // 
    // RightAngle(A, B, C), Intersection(B, Segment(A, B), SubSegment(B, C)) -> Perpendicular(B, Segment(A, B), Segment(B, C))
    //
    public Set<Deduction> deduceToPerpendicular()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Intersection> intersects = _qhg.getIntersections();      
        Set<RightAngle> rightAngles = _qhg.getRightAngles();
        Set<Strengthened> strengs = _qhg.getStrengthenedRightAngles();
        
        for (Intersection intersect : intersects)
        {
            for (RightAngle ra : rightAngles)
            {
                deductions.addAll(deduceToPerpendicular(intersect, ra, ra));
            }
        }

        for (Intersection intersect : intersects)
        {
            for (Strengthened streng : strengs)
            {
                deductions.addAll(deduceToPerpendicular(intersect, (RightAngle)streng.getStrengthened(), streng));
            }
        }
        
        return deductions;
    }

    private static Set<Deduction> deduceToPerpendicular(Intersection inter, RightAngle ra, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // This angle must apply to this intersection (same vertex as well as the segments inducing this angle)
        if (!inter.InducesNonStraightAngle(ra)) return deductions;

        // We are strengthening an intersection to a perpendicular 'labeling'
        Strengthened streng = new Strengthened(inter, new Perpendicular(inter));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);
        antecedent.add(inter);

        deductions.add(new Deduction(antecedent, streng, ANNOTATION));

        return deductions;
    }

}
