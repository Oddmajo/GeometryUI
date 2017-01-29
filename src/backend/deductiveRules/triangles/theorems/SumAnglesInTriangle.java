package backend.deductiveRules.triangles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.utilities.ast_helper.Utilities;

public class SumAnglesInTriangle extends Theorem
{

    private static final String NAME = "Sum of Measure of Three Angles in Triangle is 180";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SUM_ANGLES_IN_TRIANGLE_180);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SumAnglesInTriangle(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.MEDIAN_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTriangles());

        return deductions;
    }

    public Set<Deduction> deduceTriangles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Triangle> triangles = _qhg.getTriangles();

        for (Triangle t : triangles)
        {
            deductions.addAll(deduceTriangles(t));
        }

        return deductions;
    }

    // 
    // Triangle(A, B, C) -> m\angle ABC + m\angle CAB + m\angle BCA = 180^o
    //
    public static Set<Deduction> deduceTriangles(Triangle tri)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Generate, by definition the sum of the three angles equal 180^o
        Angle a1 = new Angle(tri.getPoint1(), tri.getPoint2(), tri.getPoint3());
        Angle a2 = new Angle(tri.getPoint3(), tri.getPoint1(), tri.getPoint2());
        Angle a3 = new Angle(tri.getPoint2(), tri.getPoint3(), tri.getPoint1());

        Addition add = new Addition(a1, a2);
        Addition overallAdd = new Addition(add, a3);
        NumericValue value = new NumericValue(180); // Sum is 180^o
        GeometricAngleEquation eq = new GeometricAngleEquation(overallAdd, value);

        List<GroundedClause> antecedent = Utilities.MakeList(tri);
        deductions.add(new Deduction(antecedent, eq, ANNOTATION));

        return deductions;
    }

}
