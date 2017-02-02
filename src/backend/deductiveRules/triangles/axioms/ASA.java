package backend.deductiveRules.triangles.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class ASA extends Axiom
{
    private static final String NAME = "ASA";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ASA);
    @Override public Annotation getAnnotation() { return ANNOTATION; }
    public ASA(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ASA;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceASA());
        
        return deductions;
    }

        //       A
        //      /\ 
        //     /  \
        //    /    \
        //   /______\
        //  B        C      
        //
        // In order for two triangles to be congruent, we require the following:
        //    Triangle(A, B, C), Triangle(D, E, F),
        //    Congruent(Angle(A, B, C), Angle(D, E, F)),
        //    Congruent(Segment(B, C), Segment(E, F)),
        //    Congruent(Angle(A, C, B), Angle(D, F, E)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
        //                                                 Congruent(Segment(A, B), Angle(D, E)),
        //                                                 Congruent(Segment(A, C), Angle(D, F)),
        //                                                 Congruent(Angle(B, A, C), Angle(E, D, F)),
        //
        public static Set<Deduction> deduceASA()
        {
            // The list of new grounded clauses if they are deduced
            HashSet<Deduction> deductions = new HashSet<Deduction>();

            HashSet<CongruentSegments> conSegs = _qhg.getCongruentSegments();
            HashSet<Triangle> hTris = _qhg.getTriangles();
            HashSet<CongruentAngles> hAngles = _qhg.getCongruentAngles();
            
            Object[] tris = hTris.toArray();
            Object[] angles = hAngles.toArray();
            for (CongruentSegments cs : conSegs)
            {
             // Check all combinations of triangles to see if they are congruent
                // This congruence must include the new segment congruence
                for (int i = 0; i < tris.length - 1; i++)
                {
                    for (int j = i + 1; j < tris.length; j++)
                    {
                        for (int m = 0; m < angles.length - 1; m++)
                        {
                            for (int n = m + 1; n < angles.length; n++)
                            {
                                deductions.addAll(deduceASA((Triangle)tris[i], (Triangle)tris[j], (CongruentAngles)angles[m], (CongruentAngles)angles[n], cs));
                            }
                        }
                    }
                }
            }
            return deductions;
        } 

        //
        // Checks for ASA given the 5 values
        //
        private static Set<Deduction> deduceASA(Triangle tri1, Triangle tri2, CongruentAngles cas1, CongruentAngles cas2, CongruentSegments css)
        {
            HashSet<Deduction> deductions = new HashSet<Deduction>();

            //
            // All congruence pairs must minimally relate the triangles
            //
            if (!cas1.LinksTriangles(tri1, tri2)) return deductions;
            if (!cas2.LinksTriangles(tri1, tri2)) return deductions;
            if (!css.LinksTriangles(tri1, tri2)) return deductions;

            // Is this angle an 'extension' of the actual triangle angle? If so, acquire the normalized version of
            // the angle, using only the triangle vertices to represent the angle
            Angle angle1Tri1 = tri1.NormalizeAngle(tri1._angleBelongs(cas1));
            Angle angle1Tri2 = tri2.NormalizeAngle(tri2._angleBelongs(cas1));

            Angle angle2Tri1 = tri1.NormalizeAngle(tri1._angleBelongs(cas2));
            Angle angle2Tri2 = tri2.NormalizeAngle(tri2._angleBelongs(cas2));

            // The angles for each triangle must be distinct
            if (angle1Tri1.equals(angle2Tri1) || angle1Tri2.equals(angle2Tri2)) return deductions;

            Segment segTri1 = tri1.GetSegment(css);
            Segment segTri2 = tri2.GetSegment(css);

            if (!segTri1.isIncludedSegment(angle1Tri1, angle2Tri1) || !segTri2.isIncludedSegment(angle1Tri2, angle2Tri2)) return deductions;

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
            triangleOne.add(tri1.oppositePoint(segTri1));
            triangleTwo.add(tri2.oppositePoint(segTri2));

            //
            // Construct the new clauses: congruent triangles and CPCTC
            //
//            GeometricCongruentTriangles gcts = new GeometricCongruentTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));
            CongruentTriangles gcts = new CongruentTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));


            // Hypergraph
            ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
            antecedent.add(tri1);
            antecedent.add(tri2);
            antecedent.add(cas1);
            antecedent.add(cas2);
            antecedent.add(css);

            deductions.add(new Deduction(antecedent, gcts, ANNOTATION));

            return deductions;
        }
    }