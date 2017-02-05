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

public class CongruentSidesInTriangleImplyCongruentAngles extends Theorem
{
    private static final String NAME = "Congruent Sides In Triangle Imply Congruent Angles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public CongruentSidesInTriangleImplyCongruentAngles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceCongruentSidesInTriangleImplyCongruentAngles());

        return deductions;
    }
    
    //
    //       A
    //      / \
    //     B---C
    //
    // Triangle(A, B, C), Congruent(Segment(A, B), Segment(A, C)) -> Congruent(\angle ABC, \angle ACB)
    //
    public Set<Deduction> deduceCongruentSidesInTriangleImplyCongruentAngles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();      
        Set<Triangle> triangles = _qhg.getTriangles();

        for (CongruentSegments css : congruentSegments)
        {
         // Only generate or add to possible congruent pairs if this is a non-reflexive relation
            if (css.isReflexive()) continue;
            for (Triangle tri : triangles)
            {
                deductions.addAll(deduceCongruentSidesInTriangleImplyCongruentAngles(tri, css));
            }
        }

        return deductions;
    }

    //
    // Just generate the new angle congruence
    //
    private static Set<Deduction> deduceCongruentSidesInTriangleImplyCongruentAngles(Triangle tri, CongruentSegments css)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        if (!tri.HasSegment(css.GetFirstSegment()) || !tri.HasSegment(css.GetSecondSegment())) return deductions;

        CongruentAngles newConAngs = new CongruentAngles(tri.oppositeAngle(css.GetFirstSegment()), tri.oppositeAngle(css.GetSecondSegment()));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(css);
        antecedent.add(tri);

        deductions.add(new Deduction(antecedent, newConAngs, ANNOTATION));
        
        return deductions;
    }

}
