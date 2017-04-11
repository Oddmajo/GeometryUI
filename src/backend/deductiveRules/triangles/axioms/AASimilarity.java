package backend.deductiveRules.triangles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.GeometricSimilarTriangles;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class AASimilarity extends Axiom
{

    private static final String NAME = "AA Similarity";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.AA_SIMILARITY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AASimilarity(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.AA_SIMILARITY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceAASimilarity());

        return deductions;
    }

    //       A
    //      /\ 
    //     /  \
    //    /    \
    //   /______\
    //  B        C      
    //
    // In order for two triangles to be Similar, we require the following:
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    Congruent(Angle(B, C, A), Angle(E, F, D))
    //    Congruent(Angle(A, B, C), Angle(D, E, F)) -> Similar(Triangle(A, B, C), Triangle(D, E, F)),
    //                                                 Proportional(Segment(A, C), Angle(D, F)),
    //                                                 Proportional(Segment(A, B), Segment(D, E)),
    //                                                 Proportional(Segment(B, C), Segment(E, F))
    //                                                 Congruent(Angle(C, A, B), Angle(F, D, E)),
    //
    private Set<Deduction> deduceAASimilarity()
    {

        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentAngles> hConAngles = _qhg.getCongruentAngles();
        Set<Triangle> hTris = _qhg.getTriangles();

        Object[] conAngles = hConAngles.toArray();
        Object[] tris = hTris.toArray();

        for(int i = 0; i < conAngles.length - 1; i++)
        {
            for(int j = i + 1 ; j < conAngles.length; j++)
            {
                // Check all combinations of triangles to see if they are congruent
                // This congruence must include the new segment congruence
                for (int n = 0; n < tris.length - 1; n++)
                {
                    for (int m = n + 1; m < tris.length; m++)
                    {
                        deductions.addAll(deduceAASimilarity((Triangle)tris[n], (Triangle)tris[m], (CongruentAngles)conAngles[i], (CongruentAngles)conAngles[j]));
                    }
                }
            }
        }
        return deductions;
    }

    //
    // Checks for AA given the 4 values
    //
    private static Set<Deduction> deduceAASimilarity(Triangle tri1, Triangle tri2, CongruentAngles cas1, CongruentAngles cas2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // All congruence pairs must minimally relate the triangles
        //
        if (!cas1.LinksTriangles(tri1, tri2)) return deductions;
        if (!cas2.LinksTriangles(tri1, tri2)) return deductions;

        // Is this angle an 'extension' of the actual triangle angle? If so, acquire the normalized version of
        // the angle, using only the triangle vertices to represent the angle
        Angle angle1Tri1 = tri1.NormalizeAngle(tri1._angleBelongs(cas1));
        Angle angle1Tri2 = tri2.NormalizeAngle(tri2._angleBelongs(cas1));

        Angle angle2Tri1 = tri1.NormalizeAngle(tri1._angleBelongs(cas2));
        Angle angle2Tri2 = tri2.NormalizeAngle(tri2._angleBelongs(cas2));

        // The angles for each triangle must be distinct
        if (angle1Tri1.equals(angle2Tri1) || angle1Tri2.equals(angle2Tri2)) return deductions;

        //
        // Construct the corrsesponding points between the triangles
        //
        ArrayList<Point> triangleOne = new ArrayList<Point>();
        ArrayList<Point> triangleTwo = new ArrayList<Point>();

        triangleOne.add(angle1Tri1.getVertex());
        triangleTwo.add(angle1Tri2.getVertex());

        triangleOne.add(angle2Tri1.getVertex());
        triangleTwo.add(angle2Tri2.getVertex());

        // We know the segment endpoint mappings above, now acquire the opposite point
        //        triangleOne.Add(tri1.otherPoint(new Segment(angle1Tri1.getVertex(), angle2Tri1.getVertex())));
        //        triangleTwo.Add(tri2.otherPoint(new Segment(angle1Tri2.getVertex(), angle2Tri2.getVertex())));
        triangleOne.add(tri1.otherPoint(angle1Tri1.getVertex(), angle2Tri1.getVertex()));
        triangleTwo.add(tri2.otherPoint(angle1Tri2.getVertex(), angle2Tri2.getVertex()));

        //
        // Construct the new clauses: similar triangles and resultant components
        //
        GeometricSimilarTriangles simTris = new GeometricSimilarTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));

        // Hypergraph edge
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri1);
        antecedent.add(tri2);
        antecedent.add(cas1);
        antecedent.add(cas2);

        deductions.add(new Deduction(antecedent, simTris, ANNOTATION));

        // Add all the corresponding parts as new Similar clauses
        deductions.addAll(SimilarTriangles.GenerateComponents(simTris, triangleOne, triangleTwo));

        return deductions;
    }
}

