package backend.deductiveRules.quadrilaterals.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class BaseAnglesIsoscelesTrapezoidCongruent extends Theorem
{

    private static final String NAME = "Base Angles Isosceles Trapezoid Congruent Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.BASE_ANGLES_OF_ISOSCELES_TRAPEZOID_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public BaseAnglesIsoscelesTrapezoidCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.BASE_ANGLES_OF_ISOSCELES_TRAPEZOID_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //  A    _______  B
    //      /       \
    //     /         \
    //    /           \
    // D /_____________\ C
    //
    // IsoscelesTrapezoid(A, B, C, D) -> Congruent(Angle(A, D, C), Angle(B, C, D)), Congruent(Angle(D, A, B), Angle(C, B, A))
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<IsoscelesTrapezoid> isoTraps = _qhg.getIsoscelesTrapezoids();      
        Set<Strengthened> Strengs = _qhg.getStrengthenedIsoscelesTrapezoids();

        for (IsoscelesTrapezoid isoTrap : isoTraps)
        {
                deductions.addAll(deduceTheorem(isoTrap, isoTrap));
        }
        
        for (Strengthened streng : Strengs)
        {
                deductions.addAll(deduceTheorem((IsoscelesTrapezoid) streng.getStrengthened(), streng));
        }

        return deductions;
    }

    private static Set<Deduction> deduceTheorem(IsoscelesTrapezoid trapezoid, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        GeometricCongruentAngles gcas1 = new GeometricCongruentAngles(trapezoid.getBottomLeftBaseAngle(), trapezoid.getBottomRightBaseAngle());
        GeometricCongruentAngles gcas2 = new GeometricCongruentAngles(trapezoid.getTopLeftBaseAngle(), trapezoid.getTopRightBaseAngle());

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gcas1, ANNOTATION));
        deductions.add(new Deduction(antecedent, gcas2, ANNOTATION));

        return deductions;
    }

}
