package backend.deductiveRules.triangles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.utilities.ast_helper.Utilities;

public class EquilateralTriangleHasSixtyDegreeAngles extends Theorem
{
    private static final String NAME = "Equilateral Triangle Has Sixty Degree Angles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.EQUILATERAL_TRIANGLE_HAS_SIXTY_DEGREE_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public EquilateralTriangleHasSixtyDegreeAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.EQUILATERAL_TRIANGLE_HAS_SIXTY_DEGREE_ANGLES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    //
    // EquilateralTriangle(A, B, C) -> Equation(m \angle ABC = 60),  
    //                                 Equation(m \angle BCA = 60),
    //                                 Equation(m \angle CAB = 60)
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<EquilateralTriangle> eqTriangles = _qhg.getEquilateralTriangles();      
        Set<Strengthened> strengs = _qhg.getStrengthenedEquilateralTriangles();

        for (EquilateralTriangle et : eqTriangles)
        {
            deductions.addAll(deduceTheorem(et));
        }
        
        for (Strengthened streng : strengs)
        {
            deductions.addAll(deduceTheorem((EquilateralTriangle) streng.getStrengthened()));
        }

        return deductions;
    }

    public static Set<Deduction> deduceTheorem(EquilateralTriangle eqTri)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // EquilateralTriangle(A, B, C) ->
        List<GroundedClause> antecedent = Utilities.MakeList(eqTri);

        //                              -> Equation(m \angle ABC = 60),  
        //                                 Equation(m \angle BCA = 60),
        //                                 Equation(m \angle CAB = 60)
        AngleEquation eq1 = new AngleEquation(eqTri.getAngleA(), new NumericValue(60));
        AngleEquation eq2 = new AngleEquation(eqTri.getAngleB(), new NumericValue(60));
        AngleEquation eq3 = new AngleEquation(eqTri.getAngleC(), new NumericValue(60));

        deductions.add(new Deduction(antecedent, eq1, ANNOTATION));
        deductions.add(new Deduction(antecedent, eq2, ANNOTATION));
        deductions.add(new Deduction(antecedent, eq3, ANNOTATION));

        return deductions;
    }

}
