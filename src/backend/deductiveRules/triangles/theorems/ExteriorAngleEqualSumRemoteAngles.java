package backend.deductiveRules.triangles.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.translation.OutPair;

public class ExteriorAngleEqualSumRemoteAngles extends Theorem
{
    private static final String NAME = "Exterior Angle Equal Sum Remote Angles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.EXTERIOR_ANGLE_EQUAL_SUM_REMOTE_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public ExteriorAngleEqualSumRemoteAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.EXTERIOR_ANGLE_EQUAL_SUM_REMOTE_ANGLES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }
    
    //
    // Triangle(A, B, C), Angle(D, A, B) -> Equation(m\angle DAB = m\angle ABC + m\angle BCA)
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();      
        Set<Angle> angles = _qhg.getAngles();

        for (Triangle t : triangles)
        {
            for (Angle a : angles)
            {
                deductions.add(deduceTheorem(t, a));
            }
        }

        return deductions;
    }

    private static Deduction deduceTheorem(Triangle tri, Angle extAngle)
    {
        //
        // Acquire the remote angles
        //
        Angle remote1 = null;
        Angle remote2 = null;
        
        // OutPair for AcquireRemoteAngles
        OutPair<Angle, Angle> out = new OutPair<Angle, Angle>();

        tri.AcquireRemoteAngles(extAngle.getVertex(), out);

        //
        // Construct the new equation
        //
        Addition sum = new Addition(remote1, remote2);
        AngleEquation eq = new AngleEquation(extAngle, sum);

        //
        // For the hypergraph
        //
        List<GroundedClause> antecedent = Utilities.MakeList(tri);
        antecedent.add(extAngle);

        return new Deduction(antecedent, eq, ANNOTATION);
    }

}
