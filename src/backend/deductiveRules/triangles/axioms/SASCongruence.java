package backend.deductiveRules.triangles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class SASCongruence extends Axiom
{
    private static final String NAME = "SAS";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SAS_CONGRUENCE);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public SASCongruence(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.SAS_CONGRUENCE;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceSAS());

        return deductions;
    }

    //      A             D
    //      /\           / \
    //     /  \         /   \
    //    /    \       /     \
    //   /______\     /_______\
    //  B        C   E         F
    //
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    Congruent(Segment(A, B), Segment(D, E)),
    //    Congruent(Angle(A, B, C), Angle(D, E, F)),
    //    Congruent(Segment(B, C), Segment(E, F)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
    //                                               Congruent(Segment(A, C), Angle(D, F)),
    //                                               Congruent(Angle(C, A, B), Angle(F, D, E)),
    //                                               Congruent(Angle(B, C, A), Angle(E, F, D)),
    //
    public static Set<Deduction> deduceSAS()
    {
        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Triangle> tris = _qhg.getTriangles();
        HashSet<CongruentSegments> conSegs = _qhg.getCongruentSegments();
        HashSet<CongruentAngles> conAngles = _qhg.getCongruentAngles();
        Object[] aTris = tris.toArray();
        Object[] aConSegs = conSegs.toArray();

        for(int n = 0; n < aConSegs.length - 1; n++)
        {
            for(int m = n + 1; m < aConSegs.length; m++)
            {
                for (int i = 0; i < aTris.length - 1; i++)
                {
                    for (int j = i + 1; j < aTris.length; j++)
                    {
                        for (CongruentAngles cas : conAngles)
                        {
                            deductions.addAll(deduceSAS((Triangle)aTris[i], (Triangle)aTris[j], (CongruentSegments)aConSegs[n], (CongruentSegments)aConSegs[m], cas));
                        }
                    }
                }
            }
        }
        
        return deductions;
    }

    //
    // Checks for SAS given the 5 values
    //
    private static Set<Deduction> deduceSAS(Triangle tri1, Triangle tri2, CongruentSegments css1, CongruentSegments css2, CongruentAngles cas)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // All congruence pairs must minimally relate the triangles
        //
        if (!css1.LinksTriangles(tri1, tri2)) return deductions;
        if (!css2.LinksTriangles(tri1, tri2)) return deductions;
        if (!cas.LinksTriangles(tri1, tri2)) return deductions;

        // Is this angle an 'extension' of the actual triangle angle? If so, acquire the normalized version of
        // the angle, using only the triangle vertices to represent the angle
        Angle angleTri1 = tri1.NormalizeAngle(tri1._angleBelongs(cas));
        Angle angleTri2 = tri2.NormalizeAngle(tri2._angleBelongs(cas));

        Segment seg1Tri1 = tri1.GetSegment(css1);
        Segment seg1Tri2 = tri2.GetSegment(css1);

        Segment seg2Tri1 = tri1.GetSegment(css2);
        Segment seg2Tri2 = tri2.GetSegment(css2);

        if (!angleTri1.IsIncludedAngle(seg1Tri1, seg2Tri1) || !angleTri2.IsIncludedAngle(seg1Tri2, seg2Tri2)) return deductions;

        //
        // Construct the corrsesponding points between the triangles
        //
        ArrayList<Point> triangleOne = new ArrayList<Point>();
        ArrayList<Point> triangleTwo = new ArrayList<Point>();

        triangleOne.add(angleTri1.getVertex());
        triangleTwo.add(angleTri2.getVertex());

        triangleOne.add(seg1Tri1.other(angleTri1.getVertex()));
        triangleTwo.add(seg1Tri2.other(angleTri2.getVertex()));

        triangleOne.add(seg2Tri1.other(angleTri1.getVertex()));
        triangleTwo.add(seg2Tri2.other(angleTri2.getVertex()));

        //
        // Construct the new clauses: congruent triangles and CPCTC
        //
        GeometricCongruentTriangles gcts = new GeometricCongruentTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));

        // Hypergraph
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri1);
        antecedent.add(tri2);
        antecedent.add(css1);
        antecedent.add(css2);
        antecedent.add(cas);

        deductions.add(new Deduction(antecedent, gcts, ANNOTATION));

        // Add all the corresponding parts as new congruent clauses
        deductions.addAll(CongruentTriangles.GenerateCPCTC(gcts, triangleOne, triangleTwo));

        return deductions;
    }

}
