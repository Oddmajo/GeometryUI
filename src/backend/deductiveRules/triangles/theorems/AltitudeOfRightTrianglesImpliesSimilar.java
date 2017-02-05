package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class AltitudeOfRightTrianglesImpliesSimilar extends Theorem
{
    
    private static final String NAME = "Altitude Of Right Triangles Implies Similar Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ALTITUDE_OF_RIGHT_TRIANGLES_IMPLIES_SIMILAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public AltitudeOfRightTrianglesImpliesSimilar(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ALTITUDE_OF_RIGHT_TRIANGLES_IMPLIES_SIMILAR;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceAltitudeOfRightTrianglesImpliesSimilar());

        return deductions;
    }
    
    //
    //   T
    //   |\
    //   | \
    //   |  \ N <-----------Right Angle
    //   | / \
    //   |/___\
    //   U     S
    //
    //  Altitude(Segment(U, N), Triangle(S, U, T)), RightTriangle(S, U, T) -> Similar(RightTriangle(S, U, T), RightTriangle(S, N, U)),
    //                                                                        Similar(RightTriangle(S, N, U), RightTriangle(U, N, T))
    //                                                                        Similar(RightTriangle(U, N, T), RightTriangle(S, U, T))
    //
    public Set<Deduction> deduceAltitudeOfRightTrianglesImpliesSimilar()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<RightTriangle> rightTriangles = _qhg.getRightTriangles();
        Set<Strengthened> strengs = _qhg.getStrengthenedRightTriangles();   
        Set<Altitude> altitudes = _qhg.getAltitudes();

        for (RightTriangle rt : rightTriangles)
        {
            for (Altitude a : altitudes)
            {
                deductions.addAll(deduceAltitudeOfRightTrianglesImpliesSimilar(rt, a, rt, rightTriangles));
            }
        }
        
        for (Strengthened streng : strengs)
        {
            for (Altitude a : altitudes)
            {
                deductions.addAll(deduceAltitudeOfRightTrianglesImpliesSimilar((RightTriangle)streng.getStrengthened(), a, streng, rightTriangles));
            }
        }

        return deductions;
    }

    public static Set<Deduction> deduceAltitudeOfRightTrianglesImpliesSimilar(RightTriangle rt, Altitude altitude, GroundedClause original, Set<RightTriangle> rightTriangles)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The altitude must connect the vertex defining the right angle and the opposite side.
        if (!altitude.getSegment().has(rt.getRightAngle().getVertex())) return deductions;

        // The other point of the altitude must lie on the opposite side of the triangle
        Point altPointOppRightAngle = altitude.getSegment().other(rt.getRightAngle().getVertex());

        Segment oppositeSide = rt.oppositeSide(rt.getRightAngle());

        if (!Segment.Between(altPointOppRightAngle, oppositeSide.getPoint1(), oppositeSide.getPoint2())) return deductions;

        //
        // Find the two smaller right triangles in the candidate list (which should be in the list at this point)
        //
        RightTriangle first = null;
        RightTriangle second = null;
        for (RightTriangle smallerRT : rightTriangles)
        {
            if (smallerRT.IsDefinedBy(rt, altitude))
            {
                if (first == null)
                {
                    first = smallerRT;
                }
                else
                {
                    second = smallerRT;
                    break;
                }
            }
        }

        // CTA: We did not check to see points aligned, but these are the original triangles from the figure
        SimilarTriangles gsts1 = new SimilarTriangles(rt, first);
        SimilarTriangles gsts2 = new SimilarTriangles(rt, second);
        SimilarTriangles gsts3 = new SimilarTriangles(first, second);

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);
        antecedent.add(altitude);

        deductions.add(new Deduction(antecedent, gsts1, ANNOTATION));
        deductions.add(new Deduction(antecedent, gsts2, ANNOTATION));
        deductions.add(new Deduction(antecedent, gsts3, ANNOTATION));

        return deductions;
    }

}
