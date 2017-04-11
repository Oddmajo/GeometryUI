package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.NotImplementedException;

public class AngleBisectorIsPerpendicularBisectorInIsosceles extends Theorem
{

    private static final String NAME = "Angle Bisector Is Perpendicular Bisector In Isosceles Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ANGLE_BISECTOR_IS_PERPENDICULAR_BISECTOR_IN_ISOSCELES);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AngleBisectorIsPerpendicularBisectorInIsosceles(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ANGLE_BISECTOR_IS_PERPENDICULAR_BISECTOR_IN_ISOSCELES;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceAngleBisectorIsPerpendicularBisectorInIsosceles());

        return deductions;
    }

    //
    // IsoscelesTriangle(A, B, C),
    // AngleBisector(Segment(M, C), Angle(A, C, B)),
    // Intersection(M, Segment(M, C), Segment(A, B) -> PerpendicularBisector(M, Segment(M, C), Segment(A, B)),
    //
    //   A _____M_____B
    //     \    |    /
    //      \   |   /
    //       \  |  / 
    //        \ | /
    //         \|/
    //          C
    //
    public Set<Deduction> deduceAngleBisectorIsPerpendicularBisectorInIsosceles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<IsoscelesTriangle> isoTriangles = _qhg.getIsoscelesTriangles();
        Set<Strengthened> strengs = _qhg.getStrengthenedIsoscelesTriangles();      
        Set<Intersection> intersections = _qhg.getIntersections();
        Set<AngleBisector> angleBisectors = _qhg.getAngleBisectors();

        for (IsoscelesTriangle isoTri : isoTriangles)
        {
            for (AngleBisector ab : angleBisectors)
            {
                for (Intersection i : intersections)
                {
                    deductions.addAll(deduceAngleBisectorIsPerpendicularBisectorInIsosceles(isoTri, ab, i));
                }
            }
        }
        
        for (Strengthened streng : strengs)
        {
            for (AngleBisector ab : angleBisectors)
            {
                for (Intersection i : intersections)
                {
                    deductions.addAll(deduceAngleBisectorIsPerpendicularBisectorInIsosceles((IsoscelesTriangle)streng.getStrengthened(), ab, i));
                }
            }
        }

        return deductions;
    }

    public static Set<Deduction> deduceAngleBisectorIsPerpendicularBisectorInIsosceles(GroundedClause tri, AngleBisector ab, Intersection inter)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        IsoscelesTriangle isoTri = (IsoscelesTriangle) (tri instanceof Strengthened ? ((Strengthened) tri).getStrengthened() : tri);

        if (tri instanceof EquilateralTriangle)
        {
            ExceptionHandler.throwException(new NotImplementedException("AngleBisectorIsPerpendicularBisectorInIsosceles: Dr. Alvin needs to look at this"));
        }

        // Does the Angle Bisector occur at the vertex angle (non-base angles) of the Isosceles triangle?
        try
        {
            if (!ab.getAngle().getVertex().equals(isoTri.getVertexAngle().getVertex())) return deductions;
        }
        catch (Exception e)
        {
            ExceptionHandler.throwException(e.toString());
        }

        // Is the intersection point between the endpoints of the base of the triangle?
        if (!Segment.Between(inter.getIntersect(), isoTri.getBaseSegment().getPoint1(), isoTri.getBaseSegment().getPoint2())) return deductions;

        // Does this intersection define this angle bisector situation? That is, the bisector and base must align with the intersection
        if (!inter.ImpliesRay(ab.getBisector())) return deductions;
        if (!inter.HasSegment(isoTri.getBaseSegment())) return deductions;

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(tri);
        antecedent.add(ab);
        antecedent.add(inter);

        // PerpendicularBisector(M, Segment(M, C), Segment(A, B))
        Strengthened newPerpB = new Strengthened(inter, new PerpendicularBisector(inter, ab.getBisector()));
        deductions.add(new Deduction(antecedent, newPerpB, ANNOTATION));

        return deductions;
    }

}
