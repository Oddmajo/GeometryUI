package backend.deductiveRules.angles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class SupplementaryDefinition extends Definition
{

    private static final String NAME = "Definition of Supplementary";

    
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SUPPLEMENTARY_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    
    public SupplementaryDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceSupplementary());    
        
        return deductions;
    }

    
    public static Set<Deduction> deduceSupplementary()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        List<Intersection> inters = _qhg.getIntersections();
        List<CongruentAngles> cas = _qhg.getCongruentAngles();
        
        
        for(Intersection inter : inters)
        {
            deductions.addAll(deduceToSupplementary(inter));
        }
            
        for(CongruentAngles ca : cas)  
        {
            if (ca.isReflexive()) continue;

            if (!(ca.GetFirstAngle() instanceof RightAngle) || !(ca.GetSecondAngle() instanceof RightAngle)) continue;
            
            deductions.addAll(deduceToSupplementary(ca));
        }

        return deductions;
    }

    //
    // All right angles are supplementary.
    //
    public static Set<Deduction> deduceToSupplementary(CongruentAngles cas)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        

        Supplementary supp = new Supplementary(cas.GetFirstAngle(), cas.GetSecondAngle());

        supp.setNotASourceNode();
        supp.setNotAGoalNode();
        supp.setClearDefinition();

        deductions.add(new Deduction(Utilities.MakeList(cas), supp, ANNOTATION));

        return deductions;
    }

    //  A      B
    //   \    /
    //    \  /
    //     \/
    //     /\ X
    //    /  \
    //   /    \
    //  C      D
    //
    // Intersection(X, Segment(A, D), Segment(B, C)) -> Supplementary(Angle(A, X, B), Angle(B, X, D))
    //                                                  Supplementary(Angle(B, X, D), Angle(D, X, C))
    //                                                  Supplementary(Angle(D, X, C), Angle(C, X, A))
    //                                                  Supplementary(Angle(C, X, A), Angle(A, X, B))
    //
    public static Set<Deduction> deduceToSupplementary(Intersection inter)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The situation looks like this:
        //  |
        //  |
        //  |_______
        //
        if (inter.StandsOnEndpoint()) return deductions;

        // The situation looks like this:
        //       |
        //       |
        //  _____|_______
        //
        if (inter.standsOn())
        {
            Point up = null;
            Point left = null;
            Point right = null;
            if (inter.getlhs().has(inter.getIntersect()))
            {
                up = inter.getlhs().other(inter.getIntersect());
                left = inter.getrhs().getPoint1();
                right = inter.getrhs().getPoint2();
            }
            else
            {
                up = inter.getrhs().other(inter.getIntersect());
                left = inter.getlhs().getPoint1();
                right = inter.getlhs().getPoint2();
            }

            // Gets the single angle object from the original figure
            Angle newAngle1 = new Angle(left, inter.getIntersect(), up);
            Angle newAngle2 = new Angle(right, inter.getIntersect(), up);

            Supplementary supp = new Supplementary(newAngle1, newAngle2);
            supp.setNotASourceNode();
            supp.setNotAGoalNode();
            supp.setClearDefinition();

            deductions.add(new Deduction(MakeAntecedent(inter, supp.getAngle1(), supp.getAngle2()), supp, ANNOTATION));
        }

        //
        // The situation is standard and results in 4 supplementary relationships
        //
        else
        {
            Angle newAngle1 = new Angle(inter.getlhs().getPoint1(), inter.getIntersect(), inter.getrhs().getPoint1());
            Angle newAngle2 = new Angle(inter.getlhs().getPoint1(), inter.getIntersect(), inter.getrhs().getPoint2());
            Angle newAngle3 = new Angle(inter.getlhs().getPoint2(), inter.getIntersect(), inter.getrhs().getPoint1());
            Angle newAngle4 = new Angle(inter.getlhs().getPoint2(), inter.getIntersect(), inter.getrhs().getPoint2());

            ArrayList<Supplementary> newSupps = new ArrayList<Supplementary>();
            newSupps.add(new Supplementary(newAngle1, newAngle2));
            newSupps.add(new Supplementary(newAngle2, newAngle4));
            newSupps.add(new Supplementary(newAngle3, newAngle4));
            newSupps.add(new Supplementary(newAngle3, newAngle1));

            for (Supplementary supp : newSupps)
            {
                supp.setNotASourceNode();
                supp.setNotAGoalNode();
                supp.setClearDefinition();

                deductions.add(new Deduction(MakeAntecedent(inter, supp.getAngle1(), supp.getAngle2()), supp, ANNOTATION));
            }
        }

        return deductions;
    }

    private static List<GroundedClause> MakeAntecedent(Intersection inter, Angle angle1, Angle angle2)
    {
        List<GroundedClause> antecedent = Utilities.MakeList(inter);
        antecedent.add(angle1);
        antecedent.add(angle2);
        return antecedent;
    }
    
}
