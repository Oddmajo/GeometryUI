package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class TwoPairsCongruentAnglesImplyThirdPairCongruent extends Theorem
{
    private static final String NAME = "Two Pairs Congruent Angles Imply Third Pair Congruent Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TWO_PAIRS_CONGRUENT_ANGLES_IMPLY_THIRD_PAIR_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public TwoPairsCongruentAnglesImplyThirdPairCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.TWO_PAIRS_CONGRUENT_ANGLES_IMPLY_THIRD_PAIR_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //      A              D
    //      /\             /\
    //     /  \           /  \
    //    /    \         /    \
    //   /______\       /______\
    //  B        C      E       F
    //
    // In order for two triangles to be congruent, we require the following:
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    Congruent(Angle(A, B, C), Angle(D, E, F)),
    //    Congruent(Angle(A, C, B), Angle(D, F, E)) -> Congruent(Angle(B, A, C), Angle(E, D, F)),
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();    
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();

        // create lists
        Object[] triangleList = triangles.toArray();
        Object[] congruentAngleList = congruentAngles.toArray();

        // Check all combinations of triangles to see if they have congruent pairs
        // This congruence must include the new segment congruence
        for (int i = 0; i < triangleList.length - 1; i++)
        {
            for (int j = i + 1; j < triangleList.length; j++)
            {
                for (int k = 0; k < congruentAngleList.length - 1; k++)
                {
                    // Reflexive relationships will not relate two distinct triangles
                    if ( ((CongruentAngles)congruentAngleList[k]).isReflexive() ) continue;
                    
                    for (int l = k + 1; l < congruentAngleList.length; l++)
                    {
                        // Reflexive relationships will not relate two distinct triangles
                        if (((CongruentAngles)congruentAngleList[k]).isReflexive()) continue;
                        
                        deductions.addAll(CheckAndGenerateThirdCongruentPair((Triangle)triangleList[i], (Triangle)triangleList[j], (CongruentAngles)congruentAngleList[k], (CongruentAngles)congruentAngleList[l]));
                    }
                }
            }
        }

        return deductions;
    }

    //
    // Acquires all of the applicable congruent segments as well as congruent angles. Then checks for SAS
    //
    private static Set<Deduction> CheckAndGenerateThirdCongruentPair(Triangle tri1, Triangle tri2, CongruentAngles cas1, CongruentAngles cas2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // We have eliminated all reflexive relationships at this point

        // The congruent relations should not share any angles
        if (cas1.AngleShared(cas2) != null) return deductions;

        // Both congruent pairs of angles must relate both triangles
        if (!cas1.LinksTriangles(tri1, tri2)) return deductions;
        if (!cas2.LinksTriangles(tri1, tri2)) return deductions;

        Angle firstAngleTri1 = tri1._angleBelongs(cas1);
        Angle secondAngleTri1 = tri1._angleBelongs(cas2);

        Angle firstAngleTri2 = tri2._angleBelongs(cas1);
        Angle secondAngleTri2 = tri2._angleBelongs(cas2);

        Angle thirdAngle1 = tri1.OtherAngle(firstAngleTri1, secondAngleTri1);
        Angle thirdAngle2 = tri2.OtherAngle(firstAngleTri2, secondAngleTri2);

        CongruentAngles newCas = new CongruentAngles(thirdAngle1, thirdAngle2);

        // Hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri1);
        antecedent.add(tri2);
        antecedent.add(cas1);
        antecedent.add(cas2);

        deductions.add(new Deduction(antecedent, newCas, ANNOTATION));

        return deductions;
    }
}
