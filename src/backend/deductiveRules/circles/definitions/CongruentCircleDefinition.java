package backend.deductiveRules.circles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class CongruentCircleDefinition extends Definition
{
    private static final String NAME = "Definition of Congruent Circles";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.CIRCLE_CONGRUENCE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public CongruentCircleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.CIRCLE_CONGRUENCE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromDefinition());
        deductions.addAll(deduceToDefinition());
        
        return deductions;
    }

    //
    // All radii of the congruent circles are congruent.
    //
    public Set<Deduction> deduceFromDefinition()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentCircles> congruentCircles = _qhg.getCongruentCircles();

        for (CongruentCircles congruentCircle : congruentCircles)
        {
            deductions.addAll(deduceFromDefinition(congruentCircle));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromDefinition(CongruentCircles congruentCircle)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Segment> radiiOne = _qhg.getRadii(congruentCircle.getCircle1());
        Set<Segment> radiiTwo = _qhg.getRadii(congruentCircle.getCircle2());
        
        for (Segment radiusOne : radiiOne)
        {
            for (Segment radiusTwo : radiiTwo)
            {
                GeometricCongruentSegments gcs = new GeometricCongruentSegments(radiusOne, radiusTwo);
                deductions.add(new Deduction(congruentCircle, gcs, ANNOTATION));
            }
        }

        return deductions;
    }
    
    //
    // All radii of the congruent circles are congruent.
    //
    public Set<Deduction> deduceToDefinition()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Circle> circleSet = _qhg.getCircles();
        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();
        
        // create list for circles
        Object[] circleList = circleSet.toArray();

        for (int r1 = 0; r1 < circleList.length - 1; r1++)
        {
            for (int r2 = r1 + 1; r2 < circleList.length; r2++)
            {
                for (CongruentSegments congruentSegment : congruentSegments)
                {
                    Circle c1 = (Circle) circleList[r1];
                    Circle c2 = (Circle) circleList[r2];
                    deductions.addAll(deduceToDefinition(c1, c2, congruentSegment));
                }
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToDefinition(Circle c1, Circle c2, CongruentSegments congruentSegment)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Do we have a radius / radius combination that relates the 2 circles
        if (c1.isRadius(congruentSegment.getcs1()) && c2.isRadius(congruentSegment.getcs2()) ||
            c1.isRadius(congruentSegment.getcs2()) && c2.isRadius(congruentSegment.getcs1()))
        {
            List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
            antecedent.add(c1);
            antecedent.add(c2);
            antecedent.add(congruentSegment);

            GeometricCongruentCircles gccs = new GeometricCongruentCircles(c1, c2);
            deductions.add(new Deduction(antecedent, gccs, ANNOTATION));
        }

        return deductions;
    }
}
