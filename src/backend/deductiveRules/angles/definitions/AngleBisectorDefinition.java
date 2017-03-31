package backend.deductiveRules.angles.definitions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.Pair;
import backend.utilities.list.Utilities;

public class AngleBisectorDefinition extends Definition
{
    private static final String NAME = "Angle Bisector Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private static Annotation annotation = new Annotation(NAME, RuleFactory.JustificationSwitch.ANGLE_BISECTOR_DEFINITION);
    public Annotation getAnnotation() { return annotation; }

    public AngleBisectorDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceFromAngleBisectors());
        deductions.addAll(deduceToAngleBisectors());

        return deductions;
    }
    
    
    
    //      V---------------A
    //     / \
    //    /   \
    //   /     \
    //  B       C
    //
    // AngleBisector(Angle(A, V, B), Segment(V, C)) -> Congruent(Angle, A, V, C), Angle(C, V, B))
    //
    public Set<Deduction> deduceFromAngleBisectors()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        Set<AngleBisector> angleBisectors = _qhg.getAngleBisectors();
        
        
        for (AngleBisector angleBisector : angleBisectors)
        {
            //Get the two adjacent angles
            Pair<Angle, Angle> angles = angleBisector.getBisectedAngles();
            CongruentAngles cas = new CongruentAngles(angles.first(),angles.second());
            
            
            deductions.add(new Deduction((GroundedClause)angleBisector, cas, annotation));
        }
        
        return deductions;
    }
    
    //
    // Construct the AngleBisector if we have
    //
    //      V---------------A
    //     / \
    //    /   \
    //   /     \
    //  B       C
    //
    // Congruent(Angle, A, V, C), Angle(C, V, B)),  Segment(V, C)) -> AngleBisector(Angle(A, V, B)  
    //
    //
    
    private static List<Segment> candidateSegments;
    private static List<GeometricCongruentAngles> candidateCongruent;
    public Set<Deduction> deduceToAngleBisectors()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        Set<CongruentAngles> congruentAngles = _qhg.getCongruentAngles();
        Set<Segment> segments = _qhg.getSegments();
        
        //Need to create a different angle bisector for each segment that is bisecting the angle
        for(CongruentAngles cas : congruentAngles)
        {
            if(!cas.isReflexive())
            {
                if(cas.AreAdjacent() != null)
                {
                    //Need to find the large angle
                    Segment shared = cas.AreAdjacent();
                    
                    //take the first angle, and passing the shared segment as a ray, get the other ray, and return it as a segment
                    //then do the same for the other angle
                    Segment seg1 = cas.first().other(shared.asRay(cas.first().getVertex())).asSegment();
                    Segment seg2 = cas.second().other(shared.asRay(cas.second().getVertex())).asSegment();
                    
                    Angle overall = new Angle(seg1, seg2);
                    
                    for(Segment segment : segments)
                    {
                        
                        if(segment.isCollinearWith(shared))
                        {
                            //Check to see if the the segment has a point on the vertex
                            if(segment.getPoint1().equals(overall.getVertex()) || segment.getPoint2().equals(overall.getVertex()))
                            {
                                //Check to see if the other point is 'inside of' the overall angle
                                // The bisector cannot be of the form:
                                //    \
                                //     \
                                //      V---------------A
                                //     /
                                //    /
                                //   B
                                if(overall.IsOnInteriorExplicitly(segment.getPoint2()) || overall.IsOnInteriorExplicitly(segment.getPoint1()))
                                {
                                    HashSet<GroundedClause> antecedent = new HashSet<GroundedClause>();
                                    antecedent.add(segment);
                                    antecedent.add(overall);
                                    antecedent.add(cas);
                                    
                                    deductions.add(new Deduction(antecedent, new AngleBisector(overall, segment), annotation));
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        
        return deductions;
    }
    
    
    
    
}
