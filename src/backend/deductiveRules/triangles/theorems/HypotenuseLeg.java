package backend.deductiveRules.triangles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class HypotenuseLeg extends Theorem
{
    private static final String NAME = "Hypotenuse Leg Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.HYPOTENUSE_LEG);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public HypotenuseLeg(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.HYPOTENUSE_LEG;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceTheorem());

        return deductions;
    }

    //  A
    //     |\
    //     | \
    //     |_ \
    //     |_|_\
    //    B     C
    // In order for two right triangles to be congruent, we require the following:
    //    RightTriangle(A, B, C), RightTriangle(D, E, F),
    //    Congruent(Segment(A, B), Segment(D, E)),
    //    Congruent(Segment(A, C), Segment(D, F)) -> Congruent(Triangle(A, B, C), Triangle(D, E, F)),
    //                                               Congruent(Segment(A, C), Angle(D, F)),
    //                                               Congruent(Angle(C, A, B), Angle(F, D, E)),
    //                                               Congruent(Angle(B, C, A), Angle(E, F, D)),
    //
    // Note: we need to figure out the proper order of the sides to guarantee congruence
    //

    //
    // Acquires all of the applicable congruent segments; then checks HL
    //
    public Set<Deduction> deduceTheorem()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();      
        Set<RightTriangle> rightTriangles = _qhg.getRightTriangles();
        Set<Strengthened> strengs = _qhg.getStrengthenedRightTriangles();

        // get lists
        Object[] cSegList = congruentSegments.toArray();
        Object[] rTriList = rightTriangles.toArray();
        Object[] strengList = strengs.toArray();

        // Check all combinations of strict right triangle objects
        for (int i = 0; i < rTriList.length - 1; i++)
        {
            for (int j = i + 1; j < rTriList.length; j++)
            {
                for (int k = 0; k < cSegList.length - 1; k++)
                {
                    for (int l = k + 1; l < cSegList.length; l++)
                    {
                        deductions.addAll(ReconfigureAndCheck((RightTriangle)rTriList[i], (RightTriangle)rTriList[j], 
                                (CongruentSegments)cSegList[k], (CongruentSegments)cSegList[l]));
                    }
                }
            }
        }

        // Check all combinations of (1) strict right triangle and (2) a strengthened triangle
        for (RightTriangle rt : rightTriangles)
        {
            for (Strengthened streng : strengs)
            {
                for (int k = 0; k < cSegList.length - 1; k++)
                {
                    for (int l = k + 1; l < cSegList.length; l++)
                    {
                        deductions.addAll(ReconfigureAndCheck(rt, (RightTriangle)streng.getStrengthened(), 
                                (CongruentSegments)cSegList[k], (CongruentSegments)cSegList[l]));
                    }
                }
            }
        }

        // Check all combinations of strengthened triangles
        for (int i = 0; i < strengList.length - 1; i++)
        {
            for (int j = i + 1; j < strengList.length; j++)
            {
                for (int k = 0; k < cSegList.length - 1; k++)
                {
                    for (int l = k + 1; l < cSegList.length; l++)
                    {
                        deductions.addAll(ReconfigureAndCheck((RightTriangle)((Strengthened)strengList[i]).getStrengthened(), (RightTriangle)((Strengthened)strengList[j]).getStrengthened(), (CongruentSegments)cSegList[k], (CongruentSegments)cSegList[l]));
                    }
                }
            }
        }



        return deductions;
    }

    private static Set<Deduction> ReconfigureAndCheck(RightTriangle rt1, RightTriangle rt2, CongruentSegments css1, CongruentSegments css2)
    {
        return CollectAndCheckHL(rt1, rt2, css1, css2, rt1, rt2);
    }

    @SuppressWarnings("unused")
    private static Set<Deduction> ReconfigureAndCheck(RightTriangle rt1,  Strengthened streng, CongruentSegments css1, CongruentSegments css2)
    {
        return CollectAndCheckHL(rt1, (RightTriangle)streng.getStrengthened(), css1, css2, rt1, streng);
    }

    @SuppressWarnings("unused")
    private static Set<Deduction> ReconfigureAndCheck(Strengthened streng1,  Strengthened streng2, CongruentSegments css1, CongruentSegments css2)
    {
        return CollectAndCheckHL((RightTriangle)streng1.getStrengthened() , (RightTriangle)streng2.getStrengthened(), css1, css2, streng1, streng2);
    }

    //
    // Acquires all of the applicable congruent segments; then checks HL
    //
    private static Set<Deduction> CollectAndCheckHL(RightTriangle rt1, RightTriangle rt2, 
            CongruentSegments css1, CongruentSegments css2,
            GroundedClause original1, GroundedClause original2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // The Congruence pairs must relate the two triangles
        if (!css1.LinksTriangles(rt1, rt2) || !css2.LinksTriangles(rt1, rt2)) return deductions;

        // One of the congruence pairs must relate the hypotenuses
        Segment hypotenuse1 = rt1.OtherSide(rt1.getRightAngle());
        Segment hypotenuse2 = rt2.OtherSide(rt2.getRightAngle());

        // Determine the specific congruence pair that relates the hypotenuses
        CongruentSegments hypotenuseCongruence = null;
        CongruentSegments nonHypotenuseCongruence = null;
        if (css1.HasSegment(hypotenuse1) && css1.HasSegment(hypotenuse2))
        {
            hypotenuseCongruence = css1;
            nonHypotenuseCongruence = css2;
        }
        else if (css2.HasSegment(hypotenuse1) && css2.HasSegment(hypotenuse2))
        {
            hypotenuseCongruence = css2;
            nonHypotenuseCongruence = css1;
        }
        else return deductions;

        // Sanity check that the non hypotenuse congruence pair does not contain hypotenuse
        if (nonHypotenuseCongruence.HasSegment(hypotenuse1) || nonHypotenuseCongruence.HasSegment(hypotenuse2)) return deductions;

        //
        // We now have a hypotenuse leg situation; acquire the point-to-point congruence mapping
        //
        List<Point> triangleOne = new ArrayList<Point>();
        List<Point> triangleTwo = new ArrayList<Point>();

        // Right angle vertices correspond
        triangleOne.add(rt1.getRightAngle().getVertex());
        triangleTwo.add(rt2.getRightAngle().getVertex());

        Point nonRightVertexRt1 = rt1.GetSegment(nonHypotenuseCongruence).sharedVertex(hypotenuse1);
        Point nonRightVertexRt2 = rt2.GetSegment(nonHypotenuseCongruence).sharedVertex(hypotenuse2);

        triangleOne.add(nonRightVertexRt1);
        triangleTwo.add(nonRightVertexRt2);

        triangleOne.add(hypotenuse1.other(nonRightVertexRt1));
        triangleTwo.add(hypotenuse2.other(nonRightVertexRt2));

        //
        // Construct the new deduced relationships
        //
        CongruentTriangles ccts = new CongruentTriangles(new Triangle(triangleOne),
                new Triangle(triangleTwo));

        //
        // Hypergraph
        //
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        
        // Add all the corresponding parts as new congruent clauses
        antecedent.addAll(CongruentTriangles.GenerateCPCTCSegments(triangleOne, triangleTwo));
        
        antecedent.add(original1);
        antecedent.add(original2);
        antecedent.add(css1);
        antecedent.add(css2);

        deductions.add(new Deduction(antecedent, ccts, ANNOTATION));



        return deductions;
    }

}
