package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.equations.SegmentEquation;
import backend.utilities.Pair;

public class SSSSimilarity extends Theorem
{
    private static final String NAME = "SSS Similarity Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SSS_SIMILARITY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SSSSimilarity(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.SSS_SIMILARITY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // In order for two triangles to be congruent, we require the following:
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    SegmentRatio(Segment(A, B), Segment(D, E)),
    //    SegmentRatio(Segment(A, C), Segment(D, F)),
    //    SegmentRatio(Segment(B, C), Segment(E, F)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
    //                                                  Congruent(Angles(A, B, C), Angle(D, E, F)),
    //                                                  Congruent(Angles(C, A, B), Angle(F, D, E)),
    //                                                  Congruent(Angles(B, C, A), Angle(E, F, D)),
    //
    // Note: we need to figure out the proper order of the sides to guarantee congruence
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();    
        Set<SegmentRatio> segEquations = _qhg.getSegmentRatios();

        // create lists
        Object[] triangleList = triangles.toArray();
        Object[] segRatioList = segEquations.toArray();

        // Check all combinations of triangles to see if they are congruent
        // This congruence must include the new segment congruence
        for (int i = 0; i < triangleList.length; i++)
        {
            for (int j = i + 1; j < triangleList.length; j++)
            {
                for (int m = 0; m < segRatioList.length - 1; m++)
                {
                    for (int n = m + 1; n < segRatioList.length; n++)
                    {
                        deductions.addAll(CheckForSSS((Triangle)triangleList[i], (Triangle)triangleList[j], (SegmentRatio)segRatioList[m], (SegmentRatio)segRatioList[n]));
                    }
                }
            }
        }

        return deductions;
    }

    //
    // Of all the congruent segment pairs, choose a subset of 3. Exhaustively check all; if they work, return the set.
    //
    private static Set<Deduction> CheckForSSS(Triangle ct1, Triangle ct2, SegmentRatio sre1, SegmentRatio sre2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // The proportional relationships need to link the given triangles
        //
        if (!sre1.LinksTriangles(ct1, ct2)) return deductions;
        if (!sre2.LinksTriangles(ct1, ct2)) return deductions;

        //
        // Both equations must share a fraction (ratio)
        //
        if (!sre1.SharesRatio(sre2)) return deductions;

        //
        // Collect all of the applicable segments
        //
        SegmentRatio shared = sre1.GetSharedRatio(sre2);
        SegmentRatio other1 = sre1.GetOtherRatio(shared);
        SegmentRatio other2 = sre2.GetOtherRatio(shared);

        Segment seg1Tri1 = ct1.GetSegment(shared);
        Segment seg2Tri1 = ct1.GetSegment(other1);
        Segment seg3Tri1 = ct1.GetSegment(other2);

        if (seg1Tri1 == null || seg2Tri1 == null || seg3Tri1 == null) return deductions;

        Segment seg1Tri2 = ct2.GetSegment(shared);
        Segment seg2Tri2 = ct2.GetSegment(other1);
        Segment seg3Tri2 = ct2.GetSegment(other2);

        if (seg1Tri2 == null || seg2Tri2 == null || seg3Tri2 == null) return deductions;

        // Avoid redundant segments, if they arise
        if (seg1Tri1.structurallyEquals(seg2Tri1) || seg1Tri1.structurallyEquals(seg3Tri1) || seg2Tri1.structurallyEquals(seg3Tri1)) return deductions;
        if (seg1Tri2.structurallyEquals(seg2Tri2) || seg1Tri2.structurallyEquals(seg3Tri2) || seg2Tri2.structurallyEquals(seg3Tri2)) return deductions;

        //
        // Collect the corresponding points
        //
        List<Pair<Point, Point>> pointPairs = new ArrayList<Pair<Point, Point>>();
        pointPairs.add(new Pair<Point, Point>(seg1Tri1.sharedVertex(seg2Tri1), seg1Tri2.sharedVertex(seg2Tri2)));
        pointPairs.add(new Pair<Point, Point>(seg1Tri1.sharedVertex(seg3Tri1), seg1Tri2.sharedVertex(seg3Tri2)));
        pointPairs.add(new Pair<Point, Point>(seg2Tri1.sharedVertex(seg3Tri1), seg2Tri2.sharedVertex(seg3Tri2)));

        List<GroundedClause> simTriAntecedent = new ArrayList<GroundedClause>();
        simTriAntecedent.add(ct1);
        simTriAntecedent.add(ct2);
        simTriAntecedent.add(sre1);
        simTriAntecedent.add(sre2);

        deductions.addAll(SASSimilarity.GenerateCorrespondingParts(pointPairs, simTriAntecedent, ANNOTATION));

        return deductions;
    }

}
