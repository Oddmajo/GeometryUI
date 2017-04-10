package backend.deductiveRules.angles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class PerpendicularImplyCongruentAdjacentAngles extends Theorem
{

    private static final String NAME = "Perpendicular Imply Congruent AdjacentAngles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PERPENDICULAR_IMPLY_CONGRUENT_ADJACENT_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public PerpendicularImplyCongruentAdjacentAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.PERPENDICULAR_IMPLY_CONGRUENT_ADJACENT_ANGLES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // Perpendicular(Segment(A, B), Segment(C, D)), Angle(A, M, D), Angle(D, M, B) -> Congruent(Angle(A, M, D), Angle(D, M, B)) 
    //
    //                                          B
    //                                          |
    //                              C-----------|-----------D
    //                                          | M
    //                                          |
    //                                          A
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Perpendicular> perpendiculars = _qhg.getPerpendicular();      
        Set<Angle> angleSet = _qhg.getAngles();

        // convert angleSet into a list
        Object[] angleList = angleSet.toArray();

        for (Perpendicular p : perpendiculars)
        {
            // Avoid generating if the situation is:
            //
            //   |
            //   |
            //   |_
            //   |_|_________
            //
            if (p.StandsOnEndpoint()) return deductions;

            for (int i = 0; i < angleList.length - 1; i++)
            {
                for (int j = i + 1; j < angleList.length; j++)
                {
                    deductions.addAll(CheckAndGeneratePerpendicularImplyCongruentAdjacent(p, angleList[i], angleList[j]));
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> CheckAndGeneratePerpendicularImplyCongruentAdjacent(Perpendicular perp, Object a1, Object a2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // convert a1 and a2 to Angles objects
        Angle angle1 = (Angle) a1;
        Angle angle2 = (Angle) a2;

        if (!Utilities.CompareValues(angle1.getMeasure(), angle2.getMeasure())) return deductions;

        // The given angles must belong to the intersection. That is, the vertex must align and all rays must overlay the intersection.
        if (!(perp.InducesNonStraightAngle(angle1) && perp.InducesNonStraightAngle(angle1))) return deductions;

        //
        // Now we have perpendicular -> congruent angles scenario
        //
        GeometricCongruentAngles gcas = new GeometricCongruentAngles(angle1, angle2);

        // Construct hyperedge
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(perp);
        antecedent.add(angle1);
        antecedent.add(angle2);

        deductions.add(new Deduction(antecedent, gcas, ANNOTATION));

        return deductions;
    }

}
