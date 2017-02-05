package backend.deductiveRules.triangles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class SSS extends Axiom
{
    private static final String NAME = "SSS";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SSS);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SSS(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.SSS;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceSSS());

        return deductions;
    }
    
//  A             D
    //      /\           / \
    //     /  \         /   \
    //    /    \       /     \
    //   /______\     /_______\
    //  B        C   E         F
    //
    // In order for two triangles to be congruent, we require the following:
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    Congruent(Segment(A, B), Segment(D, E)),
    //    Congruent(Segment(A, C), Angle(D, F)),
    //    Congruent(Segment(B, C), Segment(E, F)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
    //                                               Congruent(Angle(A, B, C), Angle(D, E, F)),
    //                                               Congruent(Angle(C, A, B), Angle(F, D, E)),
    //                                               Congruent(Angle(B, C, A), Angle(E, F, D)),
    //
    public static Set<Deduction> deduceSSS()
    {
        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<CongruentSegments> conSegs = _qhg.getCongruentSegments();
        HashSet<Triangle> tris = _qhg.getTriangles();
        Object[] aConSegs = conSegs.toArray();
        Object[] aTris = tris.toArray();
        
        for (int i = 0; i < aTris.length - 1; i++)
        {
            for (int j = i + 1; j < aTris.length; j++)
            {
                for(int x = 0; x < aConSegs.length - 2 ; x++)
                {
                    for (int m = x + 1; m < aConSegs.length - 1; m++)
                    {
                        for (int n = m + 1; n < aConSegs.length; n++)
                        {
                            deductions.addAll(deduceSSS((Triangle)aTris[i], (Triangle)aTris[j], (CongruentSegments)aConSegs[x], (CongruentSegments)aConSegs[m], (CongruentSegments)aConSegs[n]));
                        }
                    }
                }
            }
        }
        return deductions;
    }

    //
    // Checks for SSS given the 5 values
    //
    private static Set<Deduction> deduceSSS(Triangle tri1, Triangle tri2, CongruentSegments css1, CongruentSegments css2, CongruentSegments css3)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // All congruence pairs must minimally relate the triangles
        //
        if (!css1.LinksTriangles(tri1, tri2)) return deductions;
        if (!css2.LinksTriangles(tri1, tri2)) return deductions;
        if (!css3.LinksTriangles(tri1, tri2)) return deductions;

        Segment seg1Tri1 = tri1.GetSegment(css1);
        Segment seg1Tri2 = tri2.GetSegment(css1);

        Segment seg2Tri1 = tri1.GetSegment(css2);
        Segment seg2Tri2 = tri2.GetSegment(css2);

        Segment seg3Tri1 = tri1.GetSegment(css3);
        Segment seg3Tri2 = tri2.GetSegment(css3);

        //
        // The vertices of both triangles must all be distinct and cover the triangle completely.
        //
        Point vertex1Tri1 = seg1Tri1.sharedVertex(seg2Tri1);
        Point vertex2Tri1 = seg2Tri1.sharedVertex(seg3Tri1);
        Point vertex3Tri1 = seg1Tri1.sharedVertex(seg3Tri1);

        if (vertex1Tri1 == null || vertex2Tri1 == null || vertex3Tri1 == null) return deductions;
        if (vertex1Tri1.structurallyEquals(vertex2Tri1) ||
            vertex1Tri1.structurallyEquals(vertex3Tri1) ||
            vertex2Tri1.structurallyEquals(vertex3Tri1)) return deductions;

        Point vertex1Tri2 = seg1Tri2.sharedVertex(seg2Tri2);
        Point vertex2Tri2 = seg2Tri2.sharedVertex(seg3Tri2);
        Point vertex3Tri2 = seg1Tri2.sharedVertex(seg3Tri2);

        if (vertex1Tri2 == null || vertex2Tri2 == null || vertex3Tri2 == null) return deductions;
        if (vertex1Tri2.structurallyEquals(vertex2Tri2) ||
            vertex1Tri2.structurallyEquals(vertex3Tri2) ||
            vertex2Tri2.structurallyEquals(vertex3Tri2)) return deductions;
        
        //
        // Construct the corresponding points between the triangles
        //
        ArrayList<Point> triangleOne = new ArrayList<Point>();
        ArrayList<Point> triangleTwo = new ArrayList<Point>();

        triangleOne.add(vertex1Tri1);
        triangleTwo.add(vertex1Tri2);

        triangleOne.add(vertex2Tri1);
        triangleTwo.add(vertex2Tri2);

        triangleOne.add(vertex3Tri1);
        triangleTwo.add(vertex3Tri2);

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
        antecedent.add(css3);

        deductions.add(new Deduction(antecedent, gcts, ANNOTATION));

        // Add all the corresponding parts as new congruent clauses
        deductions.AddRange(CongruentTriangles.GenerateCPCTC(gcts, triangleOne, triangleTwo));

        return deductions;
    }

}
