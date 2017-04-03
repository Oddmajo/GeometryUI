package backend.deductiveRules.angles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.Equation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;


public class RightAngleDefinition extends Definition
{
    private static final String DEF_NAME = "Definition of Right Angle";
    private static final String TRANS_NAME = "Transitivity of Congruent Angles With a Right Angle";
    public String getName() { return DEF_NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation DEF_ANNOTATION = new Annotation(DEF_NAME, RuleFactory.JustificationSwitch.RIGHT_ANGLE_DEFINITION);
    private final static Annotation TRANS_ANNOTATION = new Annotation(TRANS_NAME, RuleFactory.JustificationSwitch.TRANSITIVE_CONGRUENT_ANGLE_WITH_RIGHT_ANGLE);
    @Override public Annotation getAnnotation() { return DEF_ANNOTATION; }


    public RightAngleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        DEF_ANNOTATION.active = RuleFactory.JustificationSwitch.RIGHT_ANGLE_DEFINITION;
        TRANS_ANNOTATION.active = RuleFactory.JustificationSwitch.TRANSITIVE_CONGRUENT_ANGLE_WITH_RIGHT_ANGLE;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceRightAngles());

        return deductions;
    }

//
//    // Not interested in reflexive relationships in this case
//    if (cas.isReflexive()) return newGrounded;
//
//    for (RightAngle ra : candidateRightAngles)
//    {
//        newGrounded.addAll(deduceToRightAngle(ra, cas, ra));
//    }
//
//    for (Strengthened streng : candidateStrengthened)
//    {
//        newGrounded.addAll(deduceToRightAngle((RightAngle)streng.getStrengthened(), cas, streng));
//    }
//
//    candidateCongruentAngles.add((CongruentAngles)clause);

    public Set<Deduction> deduceRightAngles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        HashSet<RightAngle> rightAngles = _qhg.getRightAngles();
        HashSet<Strengthened> streng = _qhg.getStrengthenedRightAngles();
        HashSet<AngleEquation> aes = _qhg.getAngleEquations();
        HashSet<CongruentAngles> conAngles = _qhg.getCongruentAngles();
        
        for (RightAngle ra : rightAngles)
        {
            deductions.addAll(deduceFromRightAngle(ra, ra));
        }
        
        for (Strengthened str : streng)
        {
            deductions.addAll(deduceFromRightAngle(str, (RightAngle)(((Strengthened)str).getStrengthened())));
        }
        
        for (CongruentAngles ca : conAngles)
        {   
          for (RightAngle ra : rightAngles)
          {
              deductions.addAll(deduceToRightAngle(ra, ca, ra));
          }
      
          for (Strengthened str : streng)
          {
              deductions.addAll(deduceToRightAngle((RightAngle)str.getStrengthened(), ca, str));
          }
        }
        
        for (AngleEquation ae : aes)
        {
            deductions.addAll(deduceToRightAngle(ae));
        }
        return deductions;
    }

    public static Set<Deduction> deduceFromRightAngle(GroundedClause clause, RightAngle ra)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();


        if (clause instanceof Strengthened) ra = (RightAngle)(((Strengthened)clause).getStrengthened());
        else if (clause instanceof RightAngle) ra = (RightAngle)clause;
        else return deductions;

        // Strengthening may be something else
        if (ra == null) return deductions;

        GeometricAngleEquation angEq = new GeometricAngleEquation(ra, new NumericValue(90));

//        ArrayList<GroundedClause> antecedent = Utilities.MakeList(clause);

        deductions.add(new Deduction(clause, angEq, DEF_ANNOTATION));

        return deductions;
    }
    
    public static Set<Deduction> deduceToRightAngle(AngleEquation eq)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        //Filter for acceptable equations - both sides atomic
        int atomicity = eq.getAtomicity();
        
        if (atomicity != Equation.BOTH_ATOMIC) return deductions;

        //Check that the terms equate an angle to a measure
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> lhs = eq.getLHS().collectTerms();
        Pair<ArrayList<GroundedClause>, ArrayList<GroundedClause>> rhs = eq.getRHS().collectTerms();

        //TODO Not sure how collectTerms works now. CTA -Nick 3/28
        Angle angle = null;
        NumericValue value = null;
        
        if (lhs.second().get(0) instanceof Angle && rhs.second().get(0) instanceof NumericValue)
        {
            angle = (Angle)lhs.second().get(0);
            value = (NumericValue)rhs.second().get(0);
        }
        else if (rhs.second().get(0) instanceof Angle && lhs.second().get(0) instanceof NumericValue)
        {
            angle = (Angle)rhs.second().get(0);
            value = (NumericValue)lhs.second().get(0);
        }
        else
            return deductions;

        //Verify that the angle is a right angle
        if (!Utilities.CompareValues(value.getDoubleValue(), 90.0)) return deductions;

        Strengthened newRightAngle = new Strengthened(angle, new RightAngle(angle));

//        ArrayList<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(clause);

        deductions.add(new Deduction(eq, newRightAngle, DEF_ANNOTATION));

        return deductions;
    }

    //
    // Implements 'transitivity' with right angles; that is, we may know two angles are congruent and if one is a right angle, the other is well
    //
    // Congruent(Angle(A, B, C), Angle(D, E, F), RightAngle(A, B, C) -> RightAngle(D, E, F)
    //
    public static Set<Deduction> deduceToRightAngle(RightAngle ra, CongruentAngles cas, GroundedClause original)
    {
        
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Not interested in reflexive relationships in this case
        if (cas.isReflexive()) return deductions;
        
        // The congruent must have the given angle in order to generate
        if (!cas.HasAngle(ra)) return deductions;

        Angle toBeRight = cas.OtherAngle(ra);
        Strengthened newRightAngle = new Strengthened(toBeRight, new RightAngle(toBeRight));

        ArrayList<GroundedClause> antecedent = Utilities.MakeList(original);
        antecedent.add(cas);

        deductions.add(new Deduction(antecedent, newRightAngle, TRANS_ANNOTATION));

        return deductions;
    }

}
