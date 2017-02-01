package backend.deductiveRules.triangles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;

public class AcuteAnglesInRightTriangleComplementary extends Theorem
{

    private static final String NAME = "Acute Angles In Right Triangle Complementary Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ACUTE_ANGLES_IN_RIGHT_TRIANGLE_ARE_COMPLEMENTARY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AcuteAnglesInRightTriangleComplementary(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ACUTE_ANGLES_IN_RIGHT_TRIANGLE_ARE_COMPLEMENTARY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceAcuteAnglesInRightTriangleComplementary());

        return deductions;
    }

    //
    // In order for two triangles to be congruent, we require the following:
    //    RightTriangle(A, B, C) -> Complementary(Angle(B, A, C), Angle(A, C, B))
    //     A 
    //    | \
    //    |  \
    //    |   \
    //    |    \
    //    |_____\
    //    B      C
    //
    public Set<Deduction> deduceAcuteAnglesInRightTriangleComplementary()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<RightTriangle> rightTriangles = _qhg.getRightTriangles();
        List<Strengthened> strengs = _qhg.getStrengthenedRightTriangles();

        for (RightTriangle rt : rightTriangles)
        {
            deductions.addAll(deduceAcuteAnglesInRightTriangleComplementary(rt, rt));
        }

        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceAcuteAnglesInRightTriangleComplementary((RightTriangle)streng.getStrengthened(), streng));
        }

        return deductions;
    }

    //
    // We know at this point that we have a right triangle
    //
    private static Set<Deduction> deduceAcuteAnglesInRightTriangleComplementary(Triangle tri, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Pair<Angle, Angle> acuteAngles = tri.GetAcuteAngles();

        Complementary newComp = new Complementary(acuteAngles.getKey(), acuteAngles.getValue());

        // Hypergraph
        List<GroundedClause> antecedent = Utilities.MakeList(original);

        deductions.add(new Deduction(antecedent, newComp, ANNOTATION));

        return deductions;
    }

}
