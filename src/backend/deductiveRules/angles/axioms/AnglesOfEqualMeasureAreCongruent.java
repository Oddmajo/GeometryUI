package backend.deductiveRules.angles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.Equation;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;

public class AnglesOfEqualMeasureAreCongruent extends Axiom
{
    private static final String NAME = "Angles of Equal Measure Are Congruent";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ANGLES_OF_EQUAL_MEASUREARE_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public AnglesOfEqualMeasureAreCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ANGLES_OF_EQUAL_MEASUREARE_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        
        deductions.addAll(deduceAngles());
        
        return deductions;
    }
    
    public static Set<Deduction> deduceAngles()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        
        HashSet<AngleEquation> hAngleEqs = _qhg.getAngleEquations();
        
        Object[] angleEqs = hAngleEqs.toArray();
        
        for (int i = 0; i < angleEqs.length - 1; i++)
        {
            AngleEquation aeq = (AngleEquation)angleEqs[i];
         // One side must be atomic
            int atomicity = aeq.getAtomicity();
            if (atomicity != Equation.BOTH_ATOMIC) return deductions;

            // Split the information into the angle and its measure
            Pair<Angle, Double> newAngleAndMeasure = ExtractFromEquation(aeq);

            // If splitting failed, we are not interested in the equation
            if (newAngleAndMeasure.getKey() == null) return deductions;
            
            
            for (int j = i + 1; j < angleEqs.length; j++)
            {
                AngleEquation oldEq = (AngleEquation)angleEqs[j];
                Pair<Angle, Double> oldEqAngle = ExtractFromEquation(oldEq);

                // Avoid generating equivalent angles
                if (!newAngleAndMeasure.getKey().equates(oldEqAngle.getKey()))
                {
                    // Do the angles have the same measure
                    if (Utilities.CompareValues(newAngleAndMeasure.getValue(), oldEqAngle.getValue()))
                    {
                        AlgebraicCongruentAngles acas = new AlgebraicCongruentAngles(newAngleAndMeasure.getKey(), oldEqAngle.getKey());

                        // For hypergraph construction
                        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
                        antecedent.add(aeq);
                        antecedent.add(oldEq);

                        deductions.add(new Deduction(antecedent, acas, ANNOTATION));
                    }
                }
            }
        }
        
        return deductions;
    }

    // Acquire the angle and its measure from the equation
    private static Pair<Angle, Double> ExtractFromEquation(AngleEquation eq)
    {   
        NumericValue numeral = null;
        Angle angle = null;

        // Split the sides
        if (eq.getLHS() instanceof NumericValue)
        {
            numeral = (NumericValue)eq.getLHS();
            angle = (Angle)eq.getRHS();
        }
        else if (eq.getRHS() instanceof NumericValue)
        {
            numeral = (NumericValue)eq.getRHS();
            angle = (Angle)eq.getLHS();
        }

        if (numeral == null || angle == null) return new Pair<Angle, Double>(null, (double)0);

        // The multiplier must be one for: 2mABC = 45, not acceptable; something weird happened anyway
        if (angle.getMulitplier() != 1)
        {
            return new Pair<Angle, Double>(angle, numeral.getDoubleValue() * (1.0 / angle.getMulitplier()));
        }

        return new Pair<Angle,Double>(angle, numeral.getDoubleValue());
    }


}
