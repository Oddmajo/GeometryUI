package backend.deductiveRules.circles.definitions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CircleDefinition extends Definition
{
    private static final String NAME = "Definition of Circle: Radii Congruent";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CIRCLE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public CircleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.CIRCLE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromCircle());

        return deductions;
    }

    //
    // All radii of a circle are congruent.
    //
    public Set<Deduction> deduceFromCircle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Circle> circles = _qhg.getCircles();

        for (Circle circle : circles)
        {
            deductions.addAll(deduceFromCircle(circle));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromCircle(Circle circle)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Segment> radiiSet = _qhg.getRadii(circle);
        
        // create list of radii
        Object[] radiiList = radiiSet.toArray();
        
        for (int r1 = 0; r1 < radiiList.length - 1; r1++)
        {
            for (int r2 = r1 + 1; r2 < radiiList.length; r2++)
            {
                Segment radius1 = (Segment) radiiList[r1];
                Segment radius2 = (Segment) radiiList[r2];
                GeometricCongruentSegments gcs = new GeometricCongruentSegments(radius1, radius2);
                deductions.add(new Deduction(circle, gcs, ANNOTATION));
            }
        }

        return deductions;
    }
}
