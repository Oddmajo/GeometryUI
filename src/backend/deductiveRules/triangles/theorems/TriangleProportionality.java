package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.Descriptors.parallel.Parallel;
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

public class TriangleProportionality extends Theorem
{
    private static final String NAME = "Triangle Proportionality Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRIANGLE_PROPORTIONALITY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public TriangleProportionality(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.TRIANGLE_PROPORTIONALITY;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //
    // Triangle(A, B, C),
    // Intersection(D, Segment(A,B), Segment(D, E)),
    // Intersection(E, Segment(A,C), Segment(D, E)),
    // Parallel(Segment(D, E), Segment(B, C)) -> Proportional(Segment(A, D), Segment(A, B)),
    //                                           Proportional(Segment(A, E), Segment(A, C))
    //            A
    //           /\
    //          /  \
    //         /    \
    //      D /------\ E
    //       /        \
    //    B /__________\ C
    //
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();
        Set<Intersection> intersections = _qhg.getIntersections();
        Set<Parallel> parallels = _qhg.getParallels();

        // get lists
        Object[] intersectionList = intersections.toArray();

        for (Triangle tri : triangles)
        {
            for (Parallel parallel : parallels)
            {
                for (int i = 0; i < intersectionList.length; i++)
                {
                    for (int j = i + 1; j < intersectionList.length; j++)
                    {
                        deductions.addAll(CheckAndGenerateProportionality(tri, intersectionList[i], intersectionList[j], parallel));
                    }
                }
            }
        }

        return deductions;
    }

    private static Set<Deduction> CheckAndGenerateProportionality(Triangle tri, Object intersectList,
            Object intersectList2, Parallel parallel)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // get Intersection objects
        Intersection intersectionList = (Intersection) intersectList;
        Intersection intersectionList2 = (Intersection) intersectList2;

        // The two intersections should not be at the same vertex
        if (intersectionList.getIntersect().equals(intersectionList2.getIntersect())) return deductions;

        //
        // Do these intersections share a segment? That is, do they share the transversal?
        //
        Segment transversal = intersectionList.AcquireTransversal(intersectionList2);
        if (transversal == null) return deductions;

        //
        // Is the transversal a side of the triangle? It should not be.
        //
        if (tri.LiesOn(transversal)) return deductions;

        //
        // Determine if one parallel segment is a side of the triangle (which must occur)
        //
        Segment coinciding = tri.DoesParallelCoincideWith(parallel);
        if (coinciding == null) return deductions;

        // The transversal and common segment must be distinct
        if (coinciding.isCollinearWith(transversal)) return deductions;

        //
        // Determine if the simplified transversal is within the parallel relationship.
        //
        Segment parallelTransversal = parallel.OtherSegment(coinciding);
        Segment simpleParallelTransversal = new Segment(intersectionList.getIntersect(), intersectionList2.getIntersect());

        if (!parallelTransversal.isCollinearWith(simpleParallelTransversal)) return deductions;

        //            A
        //           /\
        //          /  \
        //         /    \
        //  off1  /------\ off2
        //       /        \
        //    B /__________\ C

        //
        // Both intersections should create a T-shape.
        //
        Point off1 = intersectionList.CreatesTShape();
        Point off2 = intersectionList2.CreatesTShape();
        if (off1 == null || off2 == null) return deductions;

        // Get the intersection segments which should coincide with the triangle sides
        Pair<Segment, Segment> otherSides = tri.OtherSides(coinciding);

        // The intersections may be outside this triangle
        if (otherSides.getKey() == null || otherSides.getValue() == null) return deductions;

        Segment side1 = intersectionList.OtherSegment(transversal);
        Segment side2 = intersectionList2.OtherSegment(transversal);

        // Get the actual sides of the triangle
        Segment triangleSide1 = null;
        Segment triangleSide2 = null;
        if (side1.isCollinearWith(otherSides.getKey()) && side2.isCollinearWith(otherSides.getValue()))
        {
            triangleSide1 = otherSides.getKey();
            triangleSide2 = otherSides.getValue();
        }
        else if (side1.isCollinearWith(otherSides.getValue()) && side2.isCollinearWith(otherSides.getKey()))
        {
            triangleSide1 = otherSides.getValue();
            triangleSide2 = otherSides.getKey();
        }
        else return deductions;

        // Verify the opposing parts of the T are on the opposite sides of the triangle
        if (!triangleSide1.pointLiesBetweenEndpoints(off2)) return deductions;
        if (!triangleSide2.pointLiesBetweenEndpoints(off1)) return deductions;

        //
        // Construct the new proprtional relationship and resultant equation
        //
        Point sharedVertex = triangleSide1.sharedVertex(triangleSide2);
        SegmentRatio newProp1 = new SegmentRatio(new Segment(sharedVertex, off2), triangleSide1);
        SegmentRatio newProp2 = new SegmentRatio(new Segment(sharedVertex, off1), triangleSide2);

        SegmentEquation newEq = new SegmentEquation(newProp1, newProp2);

        // Construct hyperedge
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri);
        antecedent.add(intersectionList);
        antecedent.add(intersectionList2);
        antecedent.add(parallel);

        deductions.add(new Deduction(antecedent, newEq, ANNOTATION));

        return deductions;
    }

}
