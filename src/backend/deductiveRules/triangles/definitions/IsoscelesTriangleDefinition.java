package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class IsoscelesTriangleDefinition extends Definition
{

    private static final String NAME = "Isosceles Triangle Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.ISOSCELES_TRIANGLE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public IsoscelesTriangleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.ISOSCELES_TRIANGLE_DEFINITION;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromIsoscelesTriangle());
        deductions.addAll(deduceToIsoscelesTriangle());

        return deductions;
    }

    // original c# for reference
    //    public static List<EdgeAggregator> Instantiate(GroundedClause c)
    //    {
    //        ANNOTATION.active = EngineUIBridge.JustificationSwitch.ISOSCELES_TRIANGLE_DEFINITION;
    //
    //        if (c is IsoscelesTriangle || c is Strengthened) return InstantiateDefinition(c);
    //
    //        // The list of new grounded clauses if they are deduced
    //        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();
    //
    //        if (!(c is CongruentSegments) && !(c is Triangle)) return newGrounded;
    //
    //        //
    //        // Unify
    //        //
    //        if (c is CongruentSegments)
    //        {
    //            CongruentSegments css = c as CongruentSegments;
    //
    //            // Only generate or add to possible congruent pairs if this is a non-reflexive relation
    //            if (css.IsReflexive()) return newGrounded;
    //
    //            for (int t = 0; t < candTris.Count; t++)
    //            {
    //                if (candTris[t].HasSegment(css.cs1) && candTris[t].HasSegment(css.cs2))
    //                {
    //                    newGrounded.Add(StrengthenToIsosceles(candTris[t], css));
    //
    //                    // There should be only one possible Isosceles triangle from this congruent segments
    //                    // So we can remove this relationship and triangle from consideration
    //                    candTris.RemoveAt(t);
    //
    //                    return newGrounded;
    //                }
    //            }
    //
    //            candSegs.Add(css);
    //        }
    //
    //        else if (c is Triangle)
    //        {
    //            Triangle newTriangle = c as Triangle;
    //
    //            //
    //            // Do any of the congruent segment pairs merit calling this new triangle isosceles?
    //            //
    //            for (int cs = 0; cs < candSegs.Count; cs++)
    //            {
    //                if (newTriangle.HasSegment(candSegs[cs].cs1) && newTriangle.HasSegment(candSegs[cs].cs2))
    //                {
    //                    newGrounded.Add(StrengthenToIsosceles(newTriangle, candSegs[cs]));
    //
    //                    return newGrounded;
    //                }
    //            }
    //
    //            // Add to the list of candidates if it was not determined isosceles now.
    //            candTris.Add(newTriangle);
    //        }
    //
    //        return newGrounded;
    //    }


    //
    // In order for two triangles to be isosceles, we require the following:
    //
    //    Triangle(A, B, C), Congruent(Segment(A, B), Segment(B, C)) -> IsoscelesTriangle(A, B, C)
    //
    //  This does not generate a new clause explicitly; it simply strengthens the existent object
    //
    public Set<Deduction> deduceToIsoscelesTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();      
        Set<CongruentSegments> congruentSegments = _qhg.getCongruentSegments();
        Set<Strengthened> strengs = _qhg.getStrengthenedIsoscelesTriangles();

        for (CongruentSegments cs : congruentSegments)
        {
            for (Triangle triangle : triangles)
            {
                deductions.add(deduceToIsoscelesTriangle(triangle, cs));
            }
        }

        for (Strengthened streng : strengs)
        {
            for (CongruentSegments cs : congruentSegments)
            {
                deductions.add(deduceToIsoscelesTriangle((Triangle) streng.getStrengthened(), cs));
            }
        }

        return deductions;
    }

    //
    // DO NOT generate a new clause, instead, report the result and generate all applicable
    // clauses attributed to this strengthening of a triangle from scalene to isosceles
    //
    private static Deduction deduceToIsoscelesTriangle(Triangle tri, CongruentSegments ccss)
    {
        Strengthened newStrengthened = new Strengthened(tri, new IsoscelesTriangle(tri));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(ccss);
        antecedent.add(tri);

        return new Deduction(antecedent, newStrengthened, ANNOTATION);
    }

    // original c# for reference
//    private static List<EdgeAggregator> InstantiateDefinition(GroundedClause clause)
//    {
//        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();
//
//        if (clause is EquilateralTriangle || (clause as Strengthened).strengthened is EquilateralTriangle) return newGrounded;
//
//        if (clause is IsoscelesTriangle) return InstantiateDefinition(clause, clause as IsoscelesTriangle);
//
//        if ((clause as Strengthened).strengthened is IsoscelesTriangle)
//        {
//            return InstantiateDefinition(clause, (clause as Strengthened).strengthened as IsoscelesTriangle);
//        }
//
//        return new List<EdgeAggregator>();
//    }

    //
    // IsoscelesTriangle(A, B, C) -> Congruent(Segment(A, B), Segment(A, C))
    //    
    public Set<Deduction> deduceFromIsoscelesTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        Set<IsoscelesTriangle> isoTriangles = _qhg.getIsoscelesTriangles();  
        Set<Strengthened> strengIsoTriangles = _qhg.getStrengthenedIsoscelesTriangles(); 

        for (IsoscelesTriangle isoTri : isoTriangles)
        {
            deductions.addAll(deduceFromIsoscelesTriangle(isoTri, isoTri));
        }
        
        for (Strengthened streng : strengIsoTriangles)
        {
            deductions.addAll(deduceFromIsoscelesTriangle(streng, (IsoscelesTriangle) streng.getStrengthened()));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromIsoscelesTriangle(GroundedClause original, IsoscelesTriangle isoTri)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        GeometricCongruentSegments gcs = new GeometricCongruentSegments(isoTri.getLeg1(), isoTri.getLeg2());

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        deductions.add(new Deduction(antecedent, gcs, ANNOTATION));

        return deductions;
    }

}
