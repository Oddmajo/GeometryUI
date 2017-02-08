package backend.deductiveRules.triangles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class IsoscelesTriangleTheorem extends Theorem
{
    private static final String NAME = "Isosceles Triangle Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ISOSCELES_TRIANGLE_THEOREM);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public IsoscelesTriangleTheorem(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ISOSCELES_TRIANGLE_THEOREM;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // In order for two triangles to be isosceles, we require the following:
    //    IsoscelesTriangle(A, B, C) -> \angle BAC \cong \angle BCA
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<IsoscelesTriangle> isoTris = _qhg.getIsoscelesTriangles();      
        Set<Strengthened> strengs = _qhg.getStrengthenedIsoscelesTriangles();

        for (IsoscelesTriangle isoTri : isoTris)
        {
            deductions.add(deduceTheorem(isoTri, isoTri));
        }
        
        for (Strengthened streng : strengs)
        {
            deductions.add(deduceTheorem(streng, (IsoscelesTriangle)streng.getStrengthened()));
        }

        return deductions;
    }

    public static Deduction deduceTheorem(GroundedClause original, IsoscelesTriangle isoTri)
    {
        
        CongruentAngles newCongSegs = new CongruentAngles(isoTri.getBaseAngleOppositeLeg1(), isoTri.getBaseAngleOppositeLeg2());

        List<GroundedClause> antecedent = Utilities.MakeList(original);

        return new Deduction(antecedent, newCongSegs, ANNOTATION);
    }

}
