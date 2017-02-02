package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.Equation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;

public class RightTriangleDefinition extends Definition
{

    private static final String NAME = "Right Triangle Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.RIGHT_TRIANGLE_DEFINITION);
    public Annotation getAnnotation() { return ANNOTATION; }

    public RightTriangleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.RIGHT_TRIANGLE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromRightTriangle());
        deductions.addAll(deduceToRightTriangle());

        return deductions;
    }
    
    // original C# code for reference
//    public static Set<Deduction> Instantiate(GroundedClause clause)
//    {
//        annotation.active = EngineUIBridge.JustificationSwitch.RIGHT_TRIANGLE_DEFINITION;
//
//        //
//        // Instantiating FROM a right triangle
//        //
//        Strengthened streng = clause as Strengthened;
//        if (clause is RightTriangle) return InstantiateFromRightTriangle(clause as RightTriangle, clause);
//        if (streng != null && streng.strengthened is RightTriangle) return InstantiateFromRightTriangle(streng.strengthened as RightTriangle, clause);
//
//        //
//        // Instantiating TO a right triangle
//        //
//        if (clause is RightAngle || clause is Strengthened || clause is Triangle)
//        {
//            return InstantiateToRightTriangle(clause);
//        }
//
//        return new List<EdgeAggregator>();
//    }

    public Set<Deduction> deduceFromRightTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<RightTriangle> rt = _qhg.getRightTriangles();   
        Set<Strengthened> str = _qhg.getStrengthenedRightTriangle();

        for (RightTriangle rightTriangle : rt)
        {
            deductions.addAll(deduceFromRightTriangle(rightTriangle, rightTriangle));
        }
        
        for (Strengthened strengthened : str)
        {
            deductions.addAll(deduceFromRightTriangle((RightTriangle) strengthened.getStrengthened(), strengthened));
        }

        return deductions;
    }


    private static HashSet<Deduction> deduceFromRightTriangle(RightTriangle rightTri, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<>();

        // Strengthen the old triangle to a right triangle
        Strengthened newStrengthened = new Strengthened(rightTri.getRightAngle(), new RightAngle(rightTri.getRightAngle()));

        // Hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newStrengthened, ANNOTATION));

        return deductions;
    }

    //   A
    //   |\
    //   | \
    //   |  \
    //   |   \
    //   |_   \
    //   |_|___\
    //   B      C
    //
    // Triangle(A, B, C), RightAngle(A, B, C) -> RightTriangle(A, B, C)
    //
    //
    public Set<Deduction> deduceToRightTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();
        Set<RightAngle> rightAngles = _qhg.getRightAngles();

        for (RightAngle rightAngle : rightAngles)
        {
            for (Triangle triangle : triangles)
            {
                deductions.addAll(deduceToRightTriangle(triangle, rightAngle, rightAngle));
            }
        }
        
        return deductions;
    }

    //
    // DO NOT generate a new clause, instead, report the result and generate all applicable
    //
    private static HashSet<Deduction> deduceToRightTriangle(Triangle tri, RightAngle ra, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // This angle must belong to this triangle.
        if (!tri.HasAngle(ra)) return deductions;

        // Strengthen the old triangle to a right triangle
        Strengthened newStrengthened = new Strengthened(tri, new RightTriangle(tri));
        //tri.SetProvenToBeRight();

        // Hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri);
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newStrengthened, ANNOTATION));

        return deductions;
    }
}
