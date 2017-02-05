package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CongruentAnglesInTriangleImplyCongruentSides extends Theorem
{
    
    private static final String NAME = "Congruent Angles In Triangle Imply Congruent Sides Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CONGRUENT_ANGLES_IN_TRIANGLE_IMPLY_CONGRUENT_SIDES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public CongruentAnglesInTriangleImplyCongruentSides(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.CONGRUENT_ANGLES_IN_TRIANGLE_IMPLY_CONGRUENT_SIDES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceCongruentAnglesInTriangleImplyCongruentSides());

        return deductions;
    }
    
    //
    //       A
    //      / \
    //     B---C
    //
    // Triangle(A, B, C), Congruent(\angle ABC, \angle ACB) -> Congruent(Segment(A, B), Segment(A, C))
    //
    public Set<Deduction> deduceCongruentAnglesInTriangleImplyCongruentSides()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();      
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();

        for (Triangle tri : triangles)
        {
            for (CongruentAngles cas : congruentAngles)
            {
                if (tri.HasAngle(cas.GetFirstAngle()) && tri.HasAngle(cas.GetSecondAngle()) && !cas.isReflexive())
                {
                    deductions.add(deduceCongruentAnglesInTriangleImplyCongruentSides(tri, cas));

                }
            }
        }

        return deductions;
    }

    //
    // Just generate the new triangle
    //
    private static Deduction deduceCongruentAnglesInTriangleImplyCongruentSides(Triangle tri, CongruentAngles cas)
    {
        CongruentSegments newConSegs = new CongruentSegments(tri.OtherSide(cas.GetFirstAngle()), tri.OtherSide(cas.GetSecondAngle()));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(cas);
        antecedent.add(tri);

        return new Deduction(antecedent, newConSegs, ANNOTATION);
    }

}
