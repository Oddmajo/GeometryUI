package backend.deductiveRules.triangles.definitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;

public class EquilateralTriangleDefinition extends Definition
{
    private static final String NAME = "Equilateral Triangle Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.EQUILATERAL_TRIANGLE_DEFINITION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public EquilateralTriangleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceFromEquilateralTriangle());
        deductions.addAll(deduceToEquilateralTriangle());

        return deductions;
    }

    // original C# code for reference
    //    public static List<EdgeAggregator> Instantiate(GroundedClause clause)
    //    {
    //        annotation.active = EngineUIBridge.JustificationSwitch.EQUILATERAL_TRIANGLE_DEFINITION;
    //
    //        if (clause is EquilateralTriangle || clause is Strengthened)
    //        {
    //            return InstantiateFromDefinition(clause);
    //        }
    //
    //        if (clause is Triangle || clause is CongruentSegments)
    //        {
    //            return InstantiateToDefinition(clause);
    //        }
    //
    //        return new List<EdgeAggregator>();
    //    }

    //
    // Generate the three pairs of congruent segments.
    //
    public Set<Deduction> deduceFromEquilateralTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<EquilateralTriangle> eqTriangles = _qhg.getEquilateralTriangles();      
        Set<Strengthened> streng = _qhg.getStrengthenedEquilateralTriangles();

        for (EquilateralTriangle eqTriangle : eqTriangles)
        {
            deductions.addAll(deduceFromEquilateralTriangle(eqTriangle, eqTriangle));
        }

        for (Strengthened str : streng)
        {
            deductions.addAll(deduceFromEquilateralTriangle((EquilateralTriangle) str.getStrengthened(), str));
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceFromEquilateralTriangle(EquilateralTriangle tri, GroundedClause original)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(original);

        //
        // Create the 3 sets of congruent segments.
        //
        for (int s = 0; s < tri.getOrderedSides().size(); s++)
        {
            GeometricCongruentSegments gcs = new GeometricCongruentSegments(tri.getOrderedSides().get(s), tri.getOrderedSides().get((s+1) % tri.getOrderedSides().size()));

            deductions.add(new Deduction(antecedent, gcs, ANNOTATION));
        }

        //
        // Create the 3 congruent angles.
        //
        for (int a = 0; a < tri.getAngles().size(); a++)
        {
            GeometricCongruentAngles gcas = new GeometricCongruentAngles(tri.getAngles().get(a), tri.getAngles().get((a + 1) % tri.getAngles().size()));

            deductions.add(new Deduction(antecedent, gcas, ANNOTATION));
        }

        //
        // Create the 3 equations for the measure of each angle being 60 degrees.
        //
        for (int a = 0; a < tri.getAngles().size(); a++)
        {
            GeometricAngleEquation gae = new GeometricAngleEquation(tri.getAngles().get(a), new NumericValue(60));

            deductions.add(new Deduction(antecedent, gae, ANNOTATION));
        }

        return deductions;
    }

    /**
     * Deduce TO Equilateral Triangle
     * @return
     */
    public Set<Deduction> deduceToEquilateralTriangle()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Triangle> triangles = _qhg.getTriangles();      
        Set<CongruentSegments> segmentSet = _qhg.getCongruentSegments();

        // convert segmentSet into a list
        Object[] segmentList = segmentSet.toArray();

        for (Triangle triangle : triangles)
        {
            for (int s1 = 0; s1 < segmentList.length - 1; s1++)
            {
                for (int s2 = s1 + 1; s2 < segmentList.length; s2++)
                {
                    deductions.addAll(deduceToEquilateralTriangle(triangle, segmentList[s1], segmentList[s2]));
                }
            }
        }

        return deductions;
    }

    private static HashSet<Deduction> deduceToEquilateralTriangle(Triangle tri, Object congruentseg1, Object congruentseg2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        // conversions
        CongruentSegments cs1 = (CongruentSegments)congruentseg1;
        CongruentSegments cs2 = (CongruentSegments)congruentseg2;

        // Do these congruences relate one common segment?
        Segment shared = cs1.SharedSegment(cs2);

        if (shared == null) return deductions;

        //
        // Do these congruences apply to this triangle?
        //
        if (!tri.HasSegment(cs1.getcs1()) || !tri.HasSegment(cs1.getcs2())) return deductions;
        if (!tri.HasSegment(cs2.getcs1()) || !tri.HasSegment(cs2.getcs2())) return deductions;

        //
        // These cannot be reflexive congruences.
        //
        if (cs1.isReflexive() || cs2.isReflexive()) return deductions;

        //
        // Are the non-shared segments unique?
        //
        Segment other1 = cs1.OtherSegment(shared);
        Segment other2 = cs2.OtherSegment(shared);

        if (other1.structurallyEquals(other2)) return deductions;

        //
        // Generate the new equialteral clause
        //
        Strengthened newStrengthened = new Strengthened(tri, new EquilateralTriangle(tri));

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(cs1);
        antecedent.add(cs2);
        antecedent.add(tri);

        deductions.add(new Deduction(antecedent, newStrengthened, ANNOTATION));

        return deductions;
    }

}
