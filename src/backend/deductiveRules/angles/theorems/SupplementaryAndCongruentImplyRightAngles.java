package backend.deductiveRules.angles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.angles.RightAngle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class SupplementaryAndCongruentImplyRightAngles extends Theorem
{
    
    private static final String NAME = "Supplementary And Congruent Imply Right Angles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CONGRUENT_SUPPLEMENTARY_ANGLES_IMPLY_RIGHT_ANGLES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SupplementaryAndCongruentImplyRightAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_SUPPLEMENTARY_ANGLES_IMPLY_RIGHT_ANGLES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceSupplementaryAndCongruentImplyRightAngles());

        return deductions;
    }
    
    //
    // Supplementary(Angle(A, B, D), Angle(B, A, C))
    // Congruent(Angle(A, B, D), Angle(B, A, C)) -> RightAngle(Angle(A, B, D))
    //                                           -> RightAngle(Angle(B, A, C))
    //                            
    //                            C            D
    //                            |            |
    //                            |____________|
    //                            A            B
    //                     
    public Set<Deduction> deduceSupplementaryAndCongruentImplyRightAngles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();      
        Set<Supplementary> supplementaries = _qhg.getSupplementaryAngles();

        for (CongruentAngles ca : congruentAngles)
        {
            for (Supplementary s : supplementaries)
            {
                deductions.addAll(deduceSupplementaryAndCongruentImplyRightAngles(s, ca));
            }
        }

        return deductions;
    }

    private static Set<Deduction> deduceSupplementaryAndCongruentImplyRightAngles(Supplementary supplementary, CongruentAngles conAngles)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //The given pairs must contain the same angles (i.e., the angles must be both supplementary AND congruent)
        if (!((supplementary.getAngle1().equals(conAngles.first()) && supplementary.getAngle2().equals(conAngles.second())) ||
           (supplementary.getAngle2().equals(conAngles.first()) && supplementary.getAngle1().equals(conAngles.second())))) return deductions;
        //if (!(supplementary.StructurallyEquals(conAngles))) return newGrounded;

        //
        // Now we have two supplementary and congruent angles, which must be right angles
        //
        Strengthened streng = new Strengthened(supplementary.getAngle1(), new RightAngle(supplementary.getAngle1()));
        Strengthened streng2 = new Strengthened(supplementary.getAngle2(), new RightAngle(supplementary.getAngle2()));

        // Construct hyperedges
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(supplementary);
        antecedent.add(conAngles);

        deductions.add(new Deduction(antecedent, streng, ANNOTATION));
        deductions.add(new Deduction(antecedent, streng2, ANNOTATION));

        return deductions;
    }

}
