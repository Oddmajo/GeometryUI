package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatioEquation;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;

public class SASSimilarity extends Theorem
{
    private static final String NAME = "SAS Similarity Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SAS_SIMILARITY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SASSimilarity(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.SAS_SIMILARITY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // In order for two triangles to be Similar, we require the following:
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    Proportional(Segment(A, B), Segment(D, E)),
    //    Congruent(Angle(A, B, C), Angle(D, E, F)),
    //    Proportional(Segment(B, C), Segment(E, F)) -> Similar(Triangle(A, B, C), Triangle(D, E, F)),
    //                                                  Proportional(Segment(A, C), Angle(D, F)),
    //                                                  Congruent(Angle(C, A, B), Angle(F, D, E)),
    //                                                  Congruent(Angle(B, C, A), Angle(E, F, D))
    //
    // Note: we need to figure out the proper order of the sides to guarantee similarity
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();
        Set<SegmentRatioEquation> segRatioEquations = _qhg.getSegmentRatioEquations();
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();

        // create lists
        Object[] triangleList = triangles.toArray();

        for (CongruentAngles cas : congruentAngles)
        {
            for (SegmentRatioEquation sr : segRatioEquations)
            {
                for (int i = 0; i < triangleList.length - 1; i++)
                {
                    for (int j = i + 1; j < triangleList.length; j++)
                    {
                        deductions.addAll(CollectAndCheckSAS(triangleList[i], triangleList[j], cas, sr));
                    }
                }
            }
        }

        return deductions;
    }

    //
    // 
    //
    private static Set<Deduction> CollectAndCheckSAS(Object triangleList, Object triangleList2, CongruentAngles cas, SegmentRatioEquation sre)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // get triangle objects
        Triangle ct1 = (Triangle) triangleList;
        Triangle ct2 = (Triangle) triangleList2;

        // Proportions must actually equate
        //if (!pss1.ProportionallyEquals(pss2)) return newGrounded;

        //// The smaller and larger segments of the proportionality must be distinct, respectively.
        //if (!pss1.IsDistinctFrom(pss2)) return newGrounded;

        // The proportional relationships need to link the given triangles
        if (!cas.LinksTriangles(ct1, ct2)) return deductions;
        if (!sre.LinksTriangles(ct1, ct2)) return deductions;
        //if (!pss1.LinksTriangles(ct1, ct2)) return newGrounded;
        //if (!pss2.LinksTriangles(ct1, ct2)) return newGrounded;

        // The smaller segments must belong to one triangle, same for larger segments.
        //if (!(ct1.HasSegment(pss1.smallerSegment) && ct1.HasSegment(pss2.smallerSegment) &&
        //      ct2.HasSegment(pss1.largerSegment) && ct2.HasSegment(pss2.largerSegment)) && 
        //    !(ct2.HasSegment(pss1.smallerSegment) && ct2.HasSegment(pss2.smallerSegment) &&
        //      ct1.HasSegment(pss1.largerSegment) && ct1.HasSegment(pss2.largerSegment)))
        //    return newGrounded;

        Pair<Segment, Segment> segsTri1 = sre.GetSegments(ct1);
        Pair<Segment, Segment> segsTri2 = sre.GetSegments(ct2);

        //Segment seg1Tri1 = ct1.GetSegment(pss1);
        //Segment seg2Tri1 = ct1.GetSegment(pss2);

        //Segment seg1Tri2 = ct2.GetSegment(pss1);
        //Segment seg2Tri2 = ct2.GetSegment(pss2);

        // Avoid redundant segments, if they arise
        if (segsTri1.getKey().structurallyEquals(segsTri1.getValue())) return deductions;
        if (segsTri2.getKey().structurallyEquals(segsTri2.getValue())) return deductions;
        //if (seg1Tri1.StructurallyEquals(seg2Tri1)) return newGrounded;
        //if (seg1Tri2.StructurallyEquals(seg2Tri2)) return newGrounded;

        Angle angleTri1 = ct1._angleBelongs(cas);
        Angle angleTri2 = ct2._angleBelongs(cas);

        // Check both triangles if this is the included angle; if it is, we have SAS
        if (!angleTri1.IsIncludedAngle(segsTri1.getKey(), segsTri1.getValue())) return deductions;
        if (!angleTri2.IsIncludedAngle(segsTri2.getKey(), segsTri2.getValue())) return deductions;

        //
        // Generate Similar Triangles
        //
        Point vertex1 = angleTri1.getVertex();
        Point vertex2 = angleTri2.getVertex();

        // Construct a list of pairs to return; this is the correspondence from triangle 1 to triangle 2
        List<Pair<Point, Point>> pairs = new ArrayList<Pair<Point, Point>>();

        // The vertices of the angles correspond
        pairs.add(new Pair<Point, Point>(vertex1, vertex2));

        // For the segments, look at the congruences and select accordingly
        pairs.add(new Pair<Point, Point>(segsTri1.getKey().other(vertex1), segsTri2.getKey().other(vertex2)));
        pairs.add(new Pair<Point, Point>(segsTri1.getValue().other(vertex1), segsTri2.getValue().other(vertex2)));

        List<GroundedClause> simTriAntecedent = new ArrayList<GroundedClause>();
        simTriAntecedent.add(ct1);
        simTriAntecedent.add(ct2);
        simTriAntecedent.add(cas);
        simTriAntecedent.add(sre);

        deductions.addAll(GenerateCorrespondingParts(pairs, simTriAntecedent, ANNOTATION));

        return deductions;
    }

    public static Set<Deduction> GenerateCorrespondingParts(List<Pair<Point, Point>> pairs, List<GroundedClause> antecedent, Annotation givenAnnotation)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // If pairs is populated, we have a Similiarity
        if (pairs.isEmpty()) return deductions;

        // Create the similarity between the triangles
        List<Point> triangleOne = new ArrayList<Point>();
        List<Point> triangleTwo = new ArrayList<Point>();
        for (Pair<Point, Point> pair : pairs)
        {
            triangleOne.add(pair.getKey());
            triangleTwo.add(pair.getValue());
        }

        SimilarTriangles simTris = new SimilarTriangles(new Triangle(triangleOne), new Triangle(triangleTwo));

        deductions.add(new Deduction(antecedent, simTris, givenAnnotation));

        // Add all the corresponding parts as new Similar clauses
        deductions.addAll(SimilarTriangles.GenerateComponents(simTris, triangleOne, triangleTwo));

        return deductions;
    }

}
