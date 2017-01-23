package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Midpoint;
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
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;

public class RightTriangleDefinition extends Definition
{
    
    private static final String NAME = "Right Angle Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private static Annotation annotation = new Annotation(NAME, RuleFactory.JustificationSwitch.STRAIGHT_ANGLE_DEFINITION);
    public Annotation getAnnotation() { return annotation; }

    public RightTriangleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceRightAngles());
        deductions.addAll(deduceStrengthened());
        
        return deductions;
    }
    
    /**
     * There was no comment in the original C# code
     * @param clause
     * @return
     * 
     * 
     */
    public static HashSet<Deduction> deduceRightAngles()
    {
        annotation.active = RuleFactory.JustificationSwitch.RIGHT_ANGLE_DEFINITION;
        
        // I need to ask about transAnnotation vs defAnnotation - Drew
        //defAnnotation.active = EngineUIBridge.JustificationSwitch.RIGHT_ANGLE_DEFINITION;
        //transAnnotation.active = EngineUIBridge.JustificationSwitch.TRANSITIVE_CONGRUENT_ANGLE_WITH_RIGHT_ANGLE;

        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Midpoint clauses from the hypergraph
        Set<RightAngle> rightAngles = _qhg.getRightAngles();

        //Strengthened streng = clause as Strengthened;
        
        for (GroundedClause clause : rightAngles)
        {
            // FROM or TO RightAngle as needed
            if (clause instanceof RightAngle)
            {
                deductions.addAll(deduceFromRightAngle(clause));
                deductions.addAll(deduceToRightAngle(clause));
            }
    
            // TO RightAngle
            if (clause instanceof CongruentAngles || clause instanceof AngleEquation)
            {
                return deduceToRightAngle(clause);
            }
        }

        return deductions;
    }
    
    public Set<Deduction> deduceStrengthened()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire all Strengthened midpoint clauses from the hypergraph
        Set<Strengthened> strengtheneds = _qhg.getStrengthenedRightAngles();
                
        for (Strengthened strengthened : strengtheneds)
        {
            Set<Deduction> returned = deduceFromRightAngle(strengthened);
            
         // FROM or TO RightAngle as needed
            if (strengthened instanceof RightAngle)
            {
                returned.addAll(deduceFromRightAngle(strengthened));
                returned.addAll(deduceToRightAngle(strengthened));
            }
    
            // TO RightAngle
            if (strengthened instanceof CongruentAngles || strengthened instanceof AngleEquation)
            {
                return deduceToRightAngle(strengthened);
            }
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }

    public static HashSet<Deduction> deduceFromRightAngle(GroundedClause clause)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        RightAngle ra = null;

        //if (clause instanceof Strengthened) ra = ((clause as Strengthened).strengthened) as RightAngle;
        //else if (clause instanceof RightAngle) ra = clause as RightAngle;
        //else return newGrounded;
        if (clause instanceof Strengthened) ra = (RightAngle) (((Strengthened) clause).strengthened);
        else if (clause instanceof RightAngle) ra = (RightAngle) clause;
        else return deductions;

        // Strengthening may be something else
        if (ra == null) return deductions;

        GeometricAngleEquation angEq = new GeometricAngleEquation(ra, new NumericValue(90));

        ArrayList<GroundedClause> antecedent = Utilities.MakeList(clause);

        deductions.add(new Deduction(antecedent, angEq, defAnnotation));

        return deductions;
    }

    public static void Clear()
    {
        candidateCongruentAngles.clear();
        candidateRightAngles.clear();
        candidateStrengthened.clear();
    }

    private static ArrayList<CongruentAngles> candidateCongruentAngles = new ArrayList<CongruentAngles>();
    private static ArrayList<Strengthened> candidateStrengthened = new ArrayList<Strengthened>();
    private static ArrayList<RightAngle> candidateRightAngles = new ArrayList<RightAngle>();
    
    public static HashSet<Deduction> deduceToRightAngle(GroundedClause clause)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        if (clause instanceof AngleEquation)
        {
            AngleEquation eq = (AngleEquation) clause;
            //Filter for acceptable equations - both sides atomic
            int atomicity = eq.getAtomicity();
            if (atomicity != Equation.BOTH_ATOMIC) return deductions;

            //Check that the terms equate an angle to a measure
            ArrayList<GroundedClause> lhs = eq.getLHS().collectTerms();
            ArrayList<GroundedClause> rhs = eq.getRHS().collectTerms();

            Angle angle = null;
            NumericValue value = null;
            if (lhs.get(0) instanceof Angle && rhs.get(0) instanceof NumericValue)
            {
                angle = (Angle) lhs.get(0);
                value = (NumericValue) rhs.get(0);
            }
            else if (rhs.get(0) instanceof Angle && lhs.get(0) instanceof NumericValue)
            {
                angle = (Angle) rhs.get(0);
                value = (NumericValue) lhs.get(0);
            }
            else
                return deductions;

            //Verify that the angle is a right angle
            if (!Utilities.CompareValues(value.getDoubleValue(), 90.0)) return deductions;

            Strengthened newRightAngle = new Strengthened(angle, new RightAngle(angle));

            ArrayList<GroundedClause> antecedent = Utilities.MakeList(clause);

            deductions.add(new Deduction(antecedent, newRightAngle, defAnnotation));

            return deductions;

        }
        else if (clause instanceof CongruentAngles)
        {
            CongruentAngles cas = (CongruentAngles) clause;

            // Not interested in reflexive relationships in this case
            if (cas.isReflexive()) return deductions;

            for (RightAngle ra : candidateRightAngles)
            {
                deductions.addAll(deduceToRightAngle(ra, cas, ra));
            }

            for (Strengthened streng : candidateStrengthened)
            {
                deductions.addAll(deduceToRightAngle(streng.strengthened as RightAngle, cas, streng));
            }

            candidateCongruentAngles.add((CongruentAngles) clause);
        }
        else if (clause instanceof RightAngle)
        {
            RightAngle ra = (RightAngle) clause;

            for (CongruentAngles oldCas : candidateCongruentAngles)
            {
                deductions.addAll(deduceToRightAngle(ra, oldCas, ra));
            }

            candidateRightAngles.add(ra);
        }
        else if (clause instanceof Strengthened)
        {
            Strengthened streng = (Strengthened) clause;

            // Only intrerested in right angles
            if (!(streng.strengthened instanceof RightAngle)) return deductions;

            for (CongruentAngles oldCas : candidateCongruentAngles)
            {
                deductions.addAll(deduceToRightAngle(streng.strengthened as RightAngle, oldCas, streng));
            }

            candidateStrengthened.add(streng);
        }

        return deductions;
    }

    //
    // Implements 'transitivity' with right angles; that is, we may know two angles are congruent and if one is a right angle, the other is well
    //
    // Congruent(Angle(A, B, C), Angle(D, E, F), RightAngle(A, B, C) -> RightAngle(D, E, F)
    //
    public static HashSet<Deduction> deduceToRightAngle(RightAngle ra, CongruentAngles cas, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The congruent must have the given angle in order to generate
        if (!cas.HasAngle(ra)) return deductions;

        Angle toBeRight = cas.OtherAngle(ra);
        Strengthened newRightAngle = new Strengthened(toBeRight, new RightAngle(toBeRight));

        ArrayList<GroundedClause> antecedent = Utilities.MakeList(original);
        antecedent.add(cas);

        deductions.add(new Deduction(antecedent, newRightAngle, transAnnotation));

        return deductions;
    }

}
