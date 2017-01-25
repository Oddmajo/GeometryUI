package backend.deductiveRules.angles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.operations.Addition;
import backend.utilities.Pair;

public class ComplementaryDefinition extends Definition
{

    private static final String NAME = "Median Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public ComplementaryDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.COMPLEMENTARY_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromComplementary());
        deductions.addAll(deduceToComplementary());

        return deductions;
    }

    // original C# for reference
    //    //
    //    // This implements forward and Backward instantiation
    //    //
    //    public static List<EdgeAggregator> Instantiate(GroundedClause clause)
    //    {
    //        if (clause is Complementary) return InstantiateFromComplementary(clause as Complementary);
    //
    //        if (clause is RightAngle || clause is Strengthened || clause is AngleEquation)
    //        {
    //            return InstantiateToComplementary(clause);
    //        }
    //
    //        return new List<EdgeAggregator>();
    //    }


    // 
    // Complementary(Angle(A, B, C), Angle(D, E, F)) -> Angle(A, B, C) + Angle(D, E, F) = 90
    //
    public Set<Deduction> deduceFromComplementary()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Complementary> complementaries = _qhg.getComplementaryAngles();

        for (Complementary comp : complementaries)
        {
            deductions.addAll(deduceFromComplementary(comp));
        }

        return deductions;
    }

    public static HashSet<Deduction> deduceFromComplementary(Complementary comp)
    {
        HashSet<Deduction> newGrounded = new HashSet<>();
        HashSet<GroundedClause> antecedent = new HashSet<>();
        antecedent.add(comp);

        GeometricAngleEquation angEq = new GeometricAngleEquation(new Addition(comp.getAngle1(), comp.getAngle2()), new NumericValue(90));

        newGrounded.add(new Deduction(antecedent, angEq, ANNOTATION));

        return newGrounded;
    }


    // 
    // RightAngle(A, B, C), Angle(A, B, X) + Angle(X, B, C) = 90 -> Complementary(Angle(A, B, X), Angle(X, B, C))
    //
    public Set<Deduction> deduceToComplementary()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<RightAngle> rightAngles = _qhg.getRightAngles();      
        List<AngleEquation> angleEquations = _qhg.getAngleEquations();
        List<Strengthened> strengs = _qhg.getStrengthenedAngleEquations();

        for (RightAngle rightAngle : rightAngles)
        {
            for (AngleEquation angleEquation : angleEquations)
            {
                deductions.addAll(deduceToComplementary(angleEquation, rightAngle, angleEquation));
            }
        }

        for (RightAngle rightAngle : rightAngles)
        {
            for (Strengthened streng : strengs)
            {
                deductions.addAll(deduceToComplementary((AngleEquation)streng.getStrengthened(), rightAngle, streng));
            }
        }

        return deductions;
    }

    public static HashSet<Deduction> deduceToComplementary(AngleEquation eq, RightAngle ra, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // Acquire the two angles from the equation
        //
        Pair<Integer, Integer> cards = eq.getCardinalities();
        List<GroundedClause> terms = cards.getKey() == 2 ? eq.getLHS().collectTerms() : eq.getRHS().collectTerms();
        List<GroundedClause> singleton = cards.getKey() == 1 ? eq.getLHS().collectTerms() : eq.getRHS().collectTerms();

        Angle angle1 = (Angle) terms.get(0);
        Angle angle2 = (Angle) terms.get(1);

        // Create the resultant angle to compare to the input right angle
        Segment shared = angle1.IsAdjacentTo(angle2);
        if (!ra.HasSegment(angle1.OtherRay(shared)) || !ra.HasSegment(angle2.OtherRay(shared))) return deductions;

        // Success, we have correspondence
        Complementary comp = new Complementary(angle1, angle2);

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, comp, ANNOTATION));

        return deductions;
    }
}
