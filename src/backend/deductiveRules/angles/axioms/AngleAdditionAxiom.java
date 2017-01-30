package backend.deductiveRules.angles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.operations.Addition;

public class AngleAdditionAxiom extends Axiom
{

    private static final String NAME = "Angle Addition Axiom";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ANGLE_ADDITION_AXIOM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AngleAdditionAxiom(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ANGLE_ADDITION_AXIOM;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();

        deductions.addAll(deduceAngles());

        return deductions;
    }


    //
    // Angle(A, B, C), Angle(C, B, D) -> Angle(A, B, C) + Angle(C, B, D) = Angle(A, B, D)
    //
    public static Set<Deduction> deduceAngles()
    {

        HashSet<Deduction> deductions = new HashSet<>();

        List<Angle> angles = _qhg.getAngles();

        //
        // Determine if another angle in the candidate unify list can be combined with this new angle
        //

        for (int i = 0; i < angles.size() - 1; i++)
        {
            for (int j = i + 1; j < angles.size(); j++)
            {
                deductions.addAll(deduceAngles(angles.get(i), angles.get(j)));
            }
        }



        return deductions;
    }

    private static Set<Deduction> deduceAngles(Angle angle1, Angle angle2)
    {
        HashSet<Deduction> deductions = new HashSet<>();

//        // An angle may have multiple names
//        if (angle1.equates(angle2)) return deductions;

        if (!angle1.getVertex().equals(angle2.getVertex())) return deductions;

        // Determine the shared segment if we have an adjacent situation
        Segment shared = angle1.IsAdjacentTo(angle2);

        if (shared == null) return deductions;

        //
        // If we combine these two angles, the result is a third angle, which, when measured,
        // would be less than 180; this is contradictory since we measure angles greedily and no circular angle is measured as > 180 
        //
        if (angle1.getMeasure() + angle2.getMeasure() > 180) return deductions;

        // Angle(A, B, C), Angle(C, B, D) -> Angle(A, B, C) + Angle(C, B, D) = Angle(A, B, D)
        Point vertex = angle1.getVertex();
        Point exteriorPt1 = angle2.OtherPoint(shared);
        Point exteriorPt2 = angle1.OtherPoint(shared);
        Angle newAngle = new Angle(exteriorPt1, vertex, exteriorPt2);
        Addition sum = new Addition(angle1, angle2);
        GeometricAngleEquation geoAngEq = new GeometricAngleEquation(sum, newAngle);
        geoAngEq.makeAxiomatic(); // This is an axiomatic equation

        // For hypergraph construction
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(angle1);
        antecedent.add(angle2);

        deductions.add(new Deduction(antecedent, geoAngEq, ANNOTATION));

        return deductions;
    }

}
