package backend.deductiveRules.angles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class AdjacentAnglesPerpendicularImplyComplementary extends Theorem
{
    
    private static final String NAME = "Adjacent Angles Perpendicular Imply Complementary Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AdjacentAnglesPerpendicularImplyComplementary(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ADJACENT_ANGLES_PERPENDICULAR_IMPLY_COMPLEMENTARY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    
    //
    // Perpendicular(M, Segment(A,B), Segment(C, D)),
    //                Angle(A, B, D), Angle(C, B, D) -> Complementary(Angle(A, B, D), Angle(C, B, D))
    //
    //       A     D
    //       |    /
    //       |   /
    //       |  /
    //       | /
    //       |/_____________________ C
    //       B
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Perpendicular> perpendiculars = _qhg.getPerpendicular();      
        List<Angle> angles = _qhg.getAngles();

        for (Perpendicular p : perpendiculars)
        {
            for (int i = 0; i < angles.size() - 1; i++)
            {
                for (int j = i + 1; j < angles.size(); j++)
                {
                    deductions.addAll(CheckAndGeneratePerpendicularImplyCongruentAdjacent(p, angles.get(i), angles.get(j)));
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> CheckAndGeneratePerpendicularImplyCongruentAdjacent(Perpendicular perp, Angle angle1, Angle angle2)
    {
        Set<Deduction> deductions = new HashSet<Deduction>();

        if (!Utilities.CompareValues(angle1.getMeasure() + angle2.getMeasure(), 90)) return deductions;

        // The perpendicular intersection must occur at the same vertex of both angles (we only need check one).
        if (!(angle1.getVertex().equals(perp.getIntersect()) && angle2.getVertex().equals(perp.getIntersect()))) return deductions;

        // Are the angles adjacent?
        Segment sharedRay = angle1.IsAdjacentTo(angle2);
        if (sharedRay == null) return deductions;

        // Do the non-shared rays for both angles align with the segments defined by the perpendicular intersection?
        if (!perp.DefinesBothRays(angle1.OtherRay(sharedRay), angle2.OtherRay(sharedRay))) return deductions;

        //
        // Now we have perpendicular -> complementary angles scenario
        //
        Complementary cas = new Complementary(angle1, angle2);

        // Construct hyperedge
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(perp);
        antecedent.add(angle1);
        antecedent.add(angle2);

        deductions.add(new Deduction(antecedent, cas, ANNOTATION));

        return deductions;
    }

}
