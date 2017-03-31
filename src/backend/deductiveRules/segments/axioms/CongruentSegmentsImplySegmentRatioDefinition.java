package backend.deductiveRules.segments.axioms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Proportionalities.GeometricSegmentRatioEquation;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CongruentSegmentsImplySegmentRatioDefinition extends Axiom
{
    private static final String NAME = "Congruent Segments Imply Proportional Segments";

    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CONGRUENT_SEGMENTS_IMPLY_PROPORTIONAL_SEGMENTS_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }


    public CongruentSegmentsImplySegmentRatioDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_SEGMENTS_IMPLY_PROPORTIONAL_SEGMENTS_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        deductions.addAll(deduceSegments());

        return deductions;
    }

    //
    // Generate Proportional relationships only if those proportions may be used by a figure (in this case, only triangles)
    //
    //    Triangle(A, B, C), Triangle(D, E, F),
    //    
    //    Congruent(Segment(A, B), Segment(B, C)), Congruent(Segment(D, E), Segment(E, F)) -> Similar(Triangle(A, B, C), Triangle(D, E, F)),
    //                                                  Proportional(Segment(A, B), Segment(D, E)),
    //                                                  Proportional(Segment(A, B), Segment(E, F)),
    //                                                  Proportional(Segment(B, C), Segment(D, E)),
    //                                                  Proportional(Segment(B, C), Segment(E, F)),
    //
    public static Set<Deduction> deduceSegments()
    {
        // The list of new grounded clauses if they are deduced
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        HashSet<Triangle> tris = _qhg.getTriangles();
        HashSet<CongruentSegments> cons = _qhg.getCongruentSegments();

        // If this is a new triangle, check for triangles which may be Similar to this new triangle
        Object[] aTris = tris.toArray();
        Object[] aCons = cons.toArray();
        for(int i = 0; i < aTris.length - 1; i++)
        {
            for(int k = i + 1; k < aTris.length; k++)
            {
                for (int m = 0; m < aCons.length - 1; m++)
                {
                    for (int n = m + 1; n < aCons.length; n++)
                    {
                        deductions.addAll(IfCongruencesApplyToTrianglesGenerate((Triangle)aTris[i], (Triangle)aTris[k], (CongruentSegments)aCons[m], (CongruentSegments)aCons[n]));
                    }
                }
            }
        }

        return deductions;
    }

    //
    // 
    //
    private static Set<Deduction> IfCongruencesApplyToTrianglesGenerate(Triangle ct1, Triangle ct2, CongruentSegments css1, CongruentSegments css2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        //
        // The congruent relationships need to link the given triangles
        //
        if (!css1.LinksTriangles(ct1, ct2)) return deductions;
        if (!css2.LinksTriangles(ct1, ct2)) return deductions;

        Segment seg1Tri1 = ct1.GetSegment(css1);
        Segment seg2Tri1 = ct1.GetSegment(css2);

        Segment seg1Tri2 = ct2.GetSegment(css1);
        Segment seg2Tri2 = ct2.GetSegment(css2);

        // Avoid redundant segments, if it arises
        if (seg1Tri1.structurallyEquals(seg2Tri1)) return deductions;
        if (seg1Tri2.structurallyEquals(seg2Tri2)) return deductions;

        //
        // Proportional Segments (we generate only as needed to avoid bloat in the hypergraph (assuming they are used by both triangles)
        // We avoid generating proportions if they are truly congruences.
        //
        ArrayList<GroundedClause> propAntecedent = new ArrayList<GroundedClause>();
        propAntecedent.add(css1);
        propAntecedent.add(css2);

        ProportionalSegments ratio1 = new ProportionalSegments(seg1Tri1, seg1Tri2);
        ProportionalSegments ratio2 = new ProportionalSegments(seg2Tri1, seg2Tri2);

        GeometricSegmentRatioEquation newEq = new GeometricSegmentRatioEquation(ratio1, ratio2);

        deductions.add(new Deduction(propAntecedent, newEq, ANNOTATION));

        ////
        //// Only generate if ratios are not 1.
        ////



        //GeometricSegmentRatio newProp = null;
        //if (!Utilities.CompareValues(seg1Tri1.Length, seg1Tri2.Length))
        //{
        //    newProp = new GeometricSegmentRatio(seg1Tri1, seg1Tri2);
        //    newGrounded.Add(new EdgeAggregator(propAntecedent, newProp, annotation));
        //}
        //if (!Utilities.CompareValues(seg1Tri1.Length, seg2Tri2.Length))
        //{
        //    newProp = new GeometricSegmentRatio(seg1Tri1, seg2Tri2);
        //    newGrounded.Add(new EdgeAggregator(propAntecedent, newProp, annotation));
        //}
        //if (!Utilities.CompareValues(seg2Tri1.Length, seg1Tri2.Length))
        //{
        //    newProp = new GeometricSegmentRatio(seg2Tri1, seg1Tri2);
        //    newGrounded.Add(new EdgeAggregator(propAntecedent, newProp, annotation));
        //}
        //if (!Utilities.CompareValues(seg2Tri1.Length, seg2Tri2.Length))
        //{
        //    newProp = new GeometricSegmentRatio(seg2Tri1, seg2Tri2);
        //    newGrounded.Add(new EdgeAggregator(propAntecedent, newProp, annotation));
        //}

        return deductions;
    }

}
