package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class AltitudeDefinition extends Definition
{
    private static final String NAME = "Altitude Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ALTITUDE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AltitudeDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);

        ANNOTATION.active = RuleFactory.JustificationSwitch.ALTITUDE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromAltitude());
        deductions.addAll(deduceToAltitude());

        return deductions;
    }

    //
    //       A
    //      /|\
    //     / | \
    //    /  |  \
    //   /   |_  \
    //  /____|_|__\
    // B     M     C
    //
    // Altitude(Segment(A, M), Triangle(A, B, C)), Intersection(M, Segment(A, M), Segment(B, C)) -> Perpendicular(M, Segment(A, M), Segment(B, C))
    //
    private Set<Deduction> deduceFromAltitude()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Intersection> intersections = _qhg.getIntersections();
        Set<Altitude> altitudes = _qhg.getAltitudes();

        for (Altitude altitude : altitudes) // Fewer altitudes in a diagram than intersections (run altitudes first)
        {
            for (Intersection intersection : intersections)
            {
                // We are only interested in straight-angle type intersections
                if (!intersection.StandsOnEndpoint() && intersection.isPerpendicular())
                {
                    deductions.addAll(deduceFromAltitude(intersection, altitude));
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> deduceFromAltitude(Intersection inter, Altitude altitude)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The intersection should contain the altitude segment
        if (!inter.HasSegment(altitude.getSegment())) return deductions;

        // The triangle should contain the other segment in the intersection
        Segment triangleSide = altitude.getTriangle().CoincidesWithASide(inter.OtherSegment(altitude.getSegment()));
        if (triangleSide == null) return deductions;
        if (!inter.OtherSegment(altitude.getSegment()).containedIn(triangleSide)) return deductions;

        //
        // Create the Perpendicular relationship
        //
        Strengthened streng = new Strengthened(inter, new Perpendicular(inter));

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(inter);
        antecedent.add(altitude);

        deductions.add(new Deduction(antecedent, streng, ANNOTATION));

        return deductions;
    }

    //
    //       A
    //      /|\
    //     / | \
    //    /  |  \
    //   /   |_  \
    //  /____|_|__\
    // B     M     C
    //
    // Triangle(A, B, C), Perpendicular(M, Segment(A, M), Segment(B, C)) -> Altitude(Segment(A, M), Triangle(A, B, C))
    //
    private  Set<Deduction> deduceToAltitude()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();
        Set<Perpendicular> perpendiculars = _qhg.getPerpendicular();
        Set<Strengthened> strengs = _qhg.getStrengthenedPerpendicular();


        for (Perpendicular perpendicular : perpendiculars)
        {   
            for (Triangle triangle : triangles)
            {
                deductions.addAll(deduceToAltitude(triangle, perpendicular, perpendicular));
            }
        }

        for (Strengthened streng : strengs)
        {   
            for (Triangle triangle : triangles)
            {
                deductions.addAll(deduceToAltitude(triangle, (Perpendicular)streng.getStrengthened(), streng));
            }
        }

        return deductions;
    }

    private Set<Deduction> deduceToAltitude(Triangle triangle, Perpendicular perp, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Acquire the side of the triangle containing the intersection point
        // This point may or may not be directly on the triangle side
        Segment baseSegment = triangle.GetSegmentWithPointOnOrExtends(perp.getIntersect());
        if (baseSegment == null) return deductions;

        // The altitude must pass through the intersection point as well as the opposing vertex
        Point oppositeVertex = triangle.oppositePoint(baseSegment);

        Segment altitude = new Segment(perp.getIntersect(), oppositeVertex);

        // The alitude must alig with the intersection
        if (!perp.ImpliesRay(altitude)) return deductions;

        // The opposing side must align with the intersection
        if (!perp.OtherSegment(altitude).isCollinearWith(baseSegment)) return deductions;


        //
        // Create the new Altitude object
        //
        Altitude newAltitude = new Altitude(triangle, altitude);

        // For hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(triangle);
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, newAltitude, ANNOTATION));

        //
        // Check if this induces a second altitude for a right triangle (although we don't know this is a strengthened triangle)
        // The intersection must be on the vertex of the triangle
        if (triangle.has(perp.getIntersect()))
        {
            Angle possRightAngle = new Angle(triangle.oppositePoint(new Segment(perp.getIntersect(), oppositeVertex)), perp.getIntersect(), oppositeVertex);

            if (triangle.HasAngle(possRightAngle))
            {
                Altitude secondAltitude = new Altitude(triangle, new Segment(perp.getIntersect(), oppositeVertex));
                deductions.add(new Deduction(antecedent, secondAltitude, ANNOTATION));
            }
        }

        return deductions;
    }
}
