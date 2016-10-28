package instantiator.algebra;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Angle;
import backend.equations.*;
import backend.instantiator.EdgeAggregator;
import backend.instantiator.GenericRule;
import backend.equations.*;
import backend.utilities.ast_helper.*;

@SuppressWarnings("unused")
public class TransitiveSubstitution extends GenericRule
{
    /*
    private static String NAME = "Transitive Substitution";
    private static Hypergraph.EdgeAnnotation annotation = new Hypergraph.EdgeAnnotation(NAME, EngineUIBridge.JustificationSwitch.TRANSITIVE_SUBSTITUTION);

    // Congruences imply equations: AB \cong CD -> AB = CD
    private static List<GeometricCongruentSegments> geoCongSegments = new ArrayList<GeometricCongruentSegments>();
    private static List<GeometricCongruentAngles> geoCongAngles = new ArrayList<GeometricCongruentAngles>();
    private static List<GeometricCongruentArcs> geoCongArcs = new ArrayList<GeometricCongruentArcs>();

    // These are transitively deduced congruences
    private static List<AlgebraicCongruentSegments> algCongSegments = new ArrayList<AlgebraicCongruentSegments>();
    private static List<AlgebraicCongruentAngles> algCongAngles = new ArrayList<AlgebraicCongruentAngles>();
    private static List<AlgebraicCongruentArcs> algCongArcs = new ArrayList<AlgebraicCongruentArcs>();

    // Old segment equations
    private static List<GeometricSegmentEquation> geoSegmentEqs = new ArrayList<GeometricSegmentEquation>();
    private static List<AlgebraicSegmentEquation> algSegmentEqs = new ArrayList<AlgebraicSegmentEquation>();

    // Old angle measure equations
    private static List<AlgebraicAngleEquation> algAngleEqs = new ArrayList<AlgebraicAngleEquation>();
    private static List<GeometricAngleEquation> geoAngleEqs = new ArrayList<GeometricAngleEquation>();

    // Old arc equations
    private static List<GeometricArcEquation> geoArcEqs = new ArrayList<GeometricArcEquation>();
    private static List<AlgebraicArcEquation> algArcEqs = new ArrayList<AlgebraicArcEquation>();

    // Old angle-arc equation
    private static List<GeometricAngleArcEquation> geoAngleArcEqs = new ArrayList<GeometricAngleArcEquation>();
    private static List<AlgebraicAngleArcEquation> algAngleArcEqs = new ArrayList<AlgebraicAngleArcEquation>();

    // Old Proportional Segment Expressions
    private static List<SegmentRatio> propSegs = new ArrayList<SegmentRatio>();

    // Old Proportional Angle Expressions
    private static List<GeometricProportionalAngles> geoPropAngs = new ArrayList<GeometricProportionalAngles>();
    private static List<AlgebraicProportionalAngles> algPropAngs = new ArrayList<AlgebraicProportionalAngles>();

    // For construction of the new equations
    private static int SEGMENT_EQUATION = 0;
    private static int ANGLE_EQUATION = 1;
    private static int ARC_EQUATION = 2;
    private static int ANGLE_ARC_EQUATION = 3;
    private static int EQUATION_ERROR = 1;

    // Resets all saved data.
    public static void clear()
    {
        geoCongSegments.clear();
        geoCongAngles.clear();
        geoCongArcs.clear();

        algCongSegments.clear();
        algCongAngles.clear();
        algCongArcs.clear();

        geoSegmentEqs.clear();
        algSegmentEqs.clear();

        algAngleEqs.clear();
        geoAngleEqs.clear();

        algArcEqs.clear();
        geoArcEqs.clear();

        algAngleArcEqs.clear();
        geoAngleArcEqs.clear();

        propSegs.clear();

        geoPropAngs.clear();
        algPropAngs.clear();
    }

    //
    // Implements transitivity with equations
    // Equation(A, B), Equation(B, C) -> Equation(A, C)
    //
    // This includes CongruentSegments and CongruentAngles
    //
    // Generation of new equations is restricted to the following rules; let G be Geometric and A algebraic
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static List<EdgeAggregator> instantiate(GroundedClause clause)
    {
        annotation.active = EngineUIBridge.justificationSwitch.TRANSITIVE_SUBSTITUTION;

        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();

        // Do we have an equation or congruence?
        if (!(clause instanceof Equation) && !(clause instanceof Congruent) && !(clause instanceof SegmentRatio)) return newGrounded;

        // Has this clause been generated before?
        // Since generated clauses will eventually be instantiated as well, this will reach a fixed point and stop.
        // Uniqueness of clauses needs to be handled by the class calling this
        if (clauseHasBeenDeduced(clause)) return newGrounded;

        // A reflexive expression provides no information of interest or consequence.
        if (clause.isReflexive()) return newGrounded;

        //
        // Process the clause
        //
        if (clause instanceof SegmentEquation)
        {
            newGrounded.addRange(handleNewSegmentEquation((SegmentEquation) clause));
        }
        else if (clause instanceof AngleEquation)
        {
            newGrounded.addRange(handleNewAngleEquation((AngleEquation) clause));
        }
        else if (clause instanceof ArcEquation)
        {
            newGrounded.addRange(handleNewArcEquation((ArcEquation) clause));
        }
        else if (clause instanceof AngleArcEquation)
        {
            newGrounded.addRange(handleNewAngleArcEquation((AngleArcEquation) clause));
        }
        else if (clause instanceof CongruentAngles)
        {
            newGrounded.addRange(handleNewCongruentAngles((CongruentAngles) clause));
        }
        else if (clause instanceof CongruentSegments)
        {
            newGrounded.addRange(handleNewCongruentSegments((CongruentSegments)clause));
        }
        else if (clause instanceof CongruentArcs)
        {
            newGrounded.addRange(handleNewCongruentArcs((CongruentArcs) clause));
        }
        else if (clause instanceof SegmentRatio)
        {
            SegmentRatio ratio = (SegmentRatio) clause;
            if (!ratio.ProportionValueKnown()) return newGrounded;

            // Avoid using proportional segments that should really be congruent (they are deduced from similar triangles which are, : fact, congruent)
            if (utilities.ast_helper.Utilities.CompareValues((SegmentRatio)clause).dictatedProportion(), 1)) return newGrounded;

            newGrounded.addAll(handleNewSegmentRatio((SegmentRatio)clause));
        }

        // add the new clause to the right list for later combining
        addToAppropriateList(clause);

        // add predecessors
        markPredecessors(newGrounded);

        return newGrounded;
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Segments
    //
    private static List<EdgeAggregator> handleNewCongruentSegments(CongruentSegments congSegs)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentSegments gcss : geoCongSegments)
        {
            newGrounded.addRange(createCongruentSegments(gcss, congSegs));
        }

        // New equations? G + G -> A
        for (GeometricSegmentEquation gseqs : geoSegmentEqs)
        {
            newGrounded.addRange(createSegmentEquation(gseqs, congSegs));
        }

        // New proportions? G + G -> A
        for (SegmentRatio ps : propSegs)
        {
            newGrounded.addRange(createSegmentRatio(ps, congSegs));
        }

        if (congSegs instanceof GeometricCongruentSegments)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentSegments acss : algCongSegments)
            {
                newGrounded.addRange(createCongruentSegments(acss, congSegs));
            }

            // New equations? G + A -> A
            for (AlgebraicSegmentEquation aseqs : algSegmentEqs)
            {
                newGrounded.addRange(createSegmentEquation(aseqs, congSegs));
            }

            // New proportions? G + A -> A
            //for (AlgebraicSegmentRatio aps : algPropSegs)
            //{
            //    newGrounded.addRange(CreateSegmentRatio(aps, congSegs));
            //}
        }

        //
        // NEW
        //
        else if (congSegs instanceof AlgebraicCongruentSegments)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentSegments oldACSS : algCongSegments)
            {
                newGrounded.addRange(cakePurelyAlgebraic(createCongruentSegments(oldACSS, congSegs)));
            }

            // New equations? A + A -> A
            for (AlgebraicSegmentEquation aseqs : algSegmentEqs)
            {
                newGrounded.addRange(cakePurelyAlgebraic(createSegmentEquation(aseqs, congSegs)));
            }

            // New proportions? A + A -> A
            //for (AlgebraicSegmentRatio aps : algPropSegs)
            //{
            //    newGrounded.addRange(makePurelyAlgebraic(CreateSegmentRatio(aps, congSegs)));
            //}
        }

        return newGrounded;
    }
    //
    // For generation of transitive congruent segments
    //
    private static List<EdgeAggregator> createCongruentSegments(CongruentSegments css, CongruentSegments geoCongSeg)
    {
        int numSharedExps = css.sharesNumClauses(geoCongSeg);
        return createCongruent<CongruentSegments>(css, geoCongSeg, numSharedExps);
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Segments
    //
    private static List<EdgeAggregator> HandleNewSegmentRatio(SegmentRatio newRatio)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        for (SegmentRatio old : propSegs)
        {
            newGrounded.addAll(SegmentRatio.createProportionEquation(old, newRatio));
        }

        // New transitivity? G + G -> A
        //for (GeometricCongruentSegments gcs : geoCongSegments)
        //{
        //    newGrounded.addRange(CreateSegmentRatio(propSegs, gcs));
        //}

        //if (propSegs instanceof SegmentRatio)
        //{
        //    // New transitivity? G + A -> A
        //    for (AlgebraicCongruentSegments acs : algCongSegments)
        //    {
        //        newGrounded.addRange(CreateSegmentRatio(propSegs, acs));
        //    }
        //}

        //else if (propSegs instanceof AlgebraicSegmentRatio)
        //{
        //    // New transitivity? A + A -> A
        //    for (AlgebraicCongruentSegments acs : algCongSegments)
        //    {
        //        newGrounded.addRange(makePurelyAlgebraic(CreateSegmentRatio(propSegs, acs)));
        //    }
        //}

        return newGrounded;
    }

    //
    // For generation of transitive proportional segments
    //
    private static List<EdgeAggregator> createSegmentRatio(SegmentRatio pss, CongruentSegments conSeg)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        int numSharedExps = pss.sharesNumClauses(conSeg);
        switch (numSharedExps)
        {
            case 0:
                // Nothing is shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                //return SegmentRatio.CreateTransitiveProportion(pss, conSeg);

            case 2:
                // This is either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:
                throw new Exception("Proportional / Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return newGrounded;
    }

    //
    // Generate all new relationships from an Equation Containing Segments
    //
    private static List<EdgeAggregator> handleNewSegmentEquation(SegmentEquation newSegEq)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentSegments gcss : geoCongSegments)
        {
            newGrounded.addRange(createSegmentEquation(newSegEq, gcss));
        }

        // New equations? G + G -> A
        for (GeometricSegmentEquation gSegs : geoSegmentEqs)
        {
            newGrounded.addRange(createNewEquation(gSegs, newSegEq));
        }

        if (newSegEq instanceof GeometricSegmentEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentSegments acss : algCongSegments)
            {
                newGrounded.addRange(createSegmentEquation(newSegEq, acss));
            }

            // New equations? G + A -> A
            for (AlgebraicSegmentEquation aSegs : algSegmentEqs)
            {
                newGrounded.addRange(createNewEquation(aSegs, newSegEq));
            }
        }

        //
        // NEW
        //
        else if (newSegEq instanceof AlgebraicSegmentEquation)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentSegments oldACSS : algCongSegments)
            {
                newGrounded.addRange(makePurelyAlgebraic(createSegmentEquation(newSegEq, oldACSS)));
            }

            // New equations? A + A -> A
            for (AlgebraicSegmentEquation aSegs : algSegmentEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(aSegs, newSegEq)));
            }
        }


        //
        // Combining TWO algebraic equations only if the result is a congruence: A + A -> Congruent
        //
        //else if (newSegEq is AlgebraicSegmentEquation)
        //{
        //    for (AlgebraicSegmentEquation asegs : algSegmentEqs)
        //    {
        //        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newEquationList = createNewEquation(newSegEq, asegs);
        //        if (newEquationList.any())
        //        {
        //            KeyValuePair<List<GroundedClause>, GroundedClause> newEq = newEquationList[0];

        //            if (newEq.Value instanceof AlgebraicCongruentSegments)
        //            {
        //                newGrounded.addRange(newEquationList);
        //            }
        //        }
        //    }
        //}
        return newGrounded;
    }

    //
    // Substitute this new segment congruence into old segment equations
    //
    private static List<EdgeAggregator> createSegmentEquation(SegmentEquation segEq, CongruentSegments congSeg)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();
        EdgeAggregator newEquationEdge;

        newEquationEdge = performEquationSubstitution(segEq, congSeg, congSeg.cs1, congSeg.cs2);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);
        newEquationEdge = performEquationSubstitution(segEq, congSeg, congSeg.cs2, congSeg.cs1);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);

        return newGrounded;
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Angles
    //
    private static List<EdgeAggregator> handleNewCongruentAngles(CongruentAngles congAngs)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcss : geoCongAngles)
        {
            newGrounded.addRange(createCongruentAngles(gcss, congAngs));
        }

        // New equations? G + G -> A
        for (GeometricAngleEquation gseqs : geoAngleEqs)
        {
            newGrounded.addRange(createAngleEquation(gseqs, congAngs));
        }

        for (GeometricAngleArcEquation gseqs : geoAngleArcEqs)
        {
            newGrounded.addRange(createAngleArcEquationFromAngleSubstitution(gseqs, congAngs));
        }

        // New proportions? G + G -> A
        for (GeometricProportionalAngles gpas : geoPropAngs)
        {
            newGrounded.addRange(createProportionalAngles(gpas, congAngs));
        }

        if (congAngs instanceof GeometricCongruentAngles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acss : algCongAngles)
            {
                newGrounded.addRange(createCongruentAngles(acss, congAngs));
            }

            // New equations? G + A -> A
            for (AlgebraicAngleEquation aseqs : algAngleEqs)
            {
                newGrounded.addRange(createAngleEquation(aseqs, congAngs));
            }

            for (AlgebraicAngleArcEquation aseqs : algAngleArcEqs)
            {
                newGrounded.addRange(createAngleArcEquationFromAngleSubstitution(aseqs, congAngs));
            }

            // New proportions? G + A -> A
            for (AlgebraicProportionalAngles apas : algPropAngs)
            {
                newGrounded.addRange(createProportionalAngles(apas, congAngs));
            }
        }

        //
        // NEW
        //
        else if (congAngs instanceof AlgebraicCongruentAngles)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentAngles oldACSS : algCongAngles)
            {
                newGrounded.addRange(makePurelyAlgebraic(createCongruentAngles(oldACSS, congAngs)));
            }

            // New equations? A + A -> A
            for (AlgebraicAngleEquation aseqs : algAngleEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleEquation(aseqs, congAngs)));
            }

            for (AlgebraicAngleArcEquation aseqs : algAngleArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleArcEquationFromAngleSubstitution(aseqs, congAngs)));
            }

            // New proportions? G + A -> A
            for (AlgebraicProportionalAngles apas : algPropAngs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createProportionalAngles(apas, congAngs)));
            }
        }

        return newGrounded;
    }

    //
    // For generation of transitive congruent Angles
    //
    private static List<EdgeAggregator> createCongruentAngles(CongruentAngles css, CongruentAngles congAng)
    {
        int numSharedExps = css.sharesNumClauses(congAng);
        return createCongruent<CongruentAngles>(css, congAng, numSharedExps);
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Segments
    //
    private static List<EdgeAggregator> HandleNewProportionalAngles(ProportionalAngles propAngs)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            newGrounded.addRange(createProportionalAngles(propAngs, gcas));
        }

        if (propAngs instanceof GeometricProportionalAngles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                newGrounded.addRange(createProportionalAngles(propAngs, acas));
            }
        }

        //
        // NEW
        //
        if (propAngs instanceof AlgebraicProportionalAngles)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                newGrounded.addRange(makePurelyAlgebraic(createProportionalAngles(propAngs, acas)));
            }
        }


        return newGrounded;
    }

    //
    // For generation of transitive proportional angles
    //
    private static List<EdgeAggregator> createProportionalAngles(ProportionalAngles pas, CongruentAngles conAng)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        int numSharedExps = pas.sharesNumClauses(conAng);
        switch (numSharedExps)
        {
            case 0:
                // Nothing is shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                //return ProportionalAngles.CreateTransitiveProportion(pas, conAng);

            case 2:
                // This is either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:
                throw new Exception("Proportional / Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return newGrounded;
    }

    //
    // Generate all new relationships from an Equation Containing Angle measurements
    //
    private static List<EdgeAggregator> HandleNewAngleEquation(AngleEquation newAngEq)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            newGrounded.addRange(createAngleEquation(newAngEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricAngleEquation gangs : geoAngleEqs)
        {
            newGrounded.addRange(createNewEquation(gangs, newAngEq));
        }

        if (newAngEq instanceof GeometricAngleEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                newGrounded.addRange(createAngleEquation(newAngEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                newGrounded.addRange(createNewEquation(aangs, newAngEq));
            }
        }

        //
        // NEW
        //
        else if (newAngEq instanceof AlgebraicAngleEquation)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentAngles oldACAS : algCongAngles)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleEquation(newAngEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(aangs, newAngEq)));
            }
        }

        return newGrounded;
    }

    //
    // Substitute this new angle congruence into old angle equations
    //
    private static List<EdgeAggregator> createAngleEquation(AngleEquation angEq, CongruentAngles congAng)
    {
        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();
        EdgeAggregator newEquationEdge;

        //            if (angEq.HasRelationPredecessor(congAng) || congAng.HasRelationPredecessor(angEq)) return newGrounded;

        newEquationEdge = performEquationSubstitution(angEq, congAng, congAng.ca1, congAng.ca2);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);
        newEquationEdge = performEquationSubstitution(angEq, congAng, congAng.ca2, congAng.ca1);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);

        return newGrounded;
    }

    //
    // If both sides of the substituted equation are atomic and not Numeric Values, create a congruence relationship instead.
    //
    private static GroundedClause handleAngleRelation(Equation simplified)
    {
        // One side must be atomic
        int atomicity = simplified.getAtomicity();
        if (atomicity == Equation.NONE_ATOMIC) return null;

        GroundedClause atomic = null;
        GroundedClause nonAtomic = null;
        if (atomicity == Equation.LEFT_ATOMIC)
        {
            atomic = simplified.lhs;
            nonAtomic = simplified.rhs;
        }
        else if (atomicity == Equation.RIGHT_ATOMIC)
        {
            atomic = simplified.rhs;
            nonAtomic = simplified.lhs;
        }
        else if (atomicity == Equation.BOTH_ATOMIC)
        {
            return handleCollinearPerpendicular(simplified.lhs, simplified.rhs);
        }



        NumericValue atomicValue = (NumericValue) atomic;
        if (atomicValue == null) return null;

        //
        // We need only consider special angles (90 or 180)
        //
        if (!utilities.compareValues(atomicValue.getIntValue(), 90) && !utilities.compareValues(atomicValue.getIntValue(), 180)) return null;

        List<GroundedClause> nonAtomicSide = nonAtomic.collectTerms();

        // Check multiplier for all terms; it must be 1.
        for (GroundedClause gc : nonAtomicSide)
        {
            if (gc.multiplier != 1) return null;
        }

        //
        // Complementary or Supplementary
        //
        AnglePairRelation newRelation = null;
        if (nonAtomicSide.count == 2)
        {
            if (utilities.compareValues(atomicValue.getIntValue(), 90))
            {
                newRelation = new Complementary((Angle)nonAtomicSide[0], (Angle)nonAtomicSide[1]);
            }
            else if (utilities.compareValues(atomicValue.getIntValue(), 180))
            {
                newRelation = new Supplementary((Angle)nonAtomicSide[0], (Angle)nonAtomicSide[1]);
            }
        }

        return newRelation;
    }

    //
    // Create a deduced collinear or perpendicular relationship
    //
    private static GroundedClause handleCollinearPerpendicular(GroundedClause left, GroundedClause right)
    {
        NumericValue numeral = null;
        Angle angle = null;

        //
        // Split the sides
        //
        if (left instanceof NumericValue)
        {
            numeral = (NumericValue) left;
            angle = (Angle) right;
        }
        else if (right instanceof NumericValue)
        {
            numeral = (NumericValue) right;
            angle = (Angle) left;
        }

        if (numeral == null || angle == null) return null;

        //
        // Create the new relationships
        //
        Descriptor newDescriptor = null;
        //if (utilities.compareValues(numeral.value, 90))
        //{
        //    newDescriptor = new Perpendicular(angle.GetVertex(), angle.ray1, angle.ray2);
        //}
        //else
        if (utilities.compareValues(numeral.IntValue, 180))
        {
            List<Point> pts = new List<Point>();
            pts.add(angle.A);
            pts.add(angle.B);
            pts.add(angle.C);
            newDescriptor = new Collinear(pts);
        }

        return newDescriptor;
    }

    //
    // Generate all new relationships from a Geometric, Congruent Pair of Arcs
    //
    private static List<EdgeAggregator> HandleNewCongruentArcs(CongruentArcs congArcs)
    {
        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentArcs gcss : geoCongArcs)
        {
            newGrounded.addRange(createCongruentArcs(gcss, congArcs));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation gseqs : geoArcEqs)
        {
            newGrounded.addRange(createArcEquation(gseqs, congArcs));
        }

        for (GeometricAngleArcEquation gseqs : geoAngleArcEqs)
        {
            newGrounded.addRange(createAngleArcEquationFromArcSubstitution(gseqs, congArcs));
        }

        //// New proportions? G + G -> A
        //for (GeometricProportionalAngles gpas : geoPropAngs)
        //{
        //    newGrounded.addRange(createProportionalAngles(gpas, congAngs));
        //}

        if (congArcs instanceof GeometricCongruentArcs)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                newGrounded.addRange(createCongruentArcs(acas, congArcs));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aseqs : algArcEqs)
            {
                newGrounded.addRange(createArcEquation(aseqs, congArcs));
            }

            for (AlgebraicAngleArcEquation gseqs : algAngleArcEqs)
            {
                newGrounded.addRange(createAngleArcEquationFromArcSubstitution(gseqs, congArcs));
            }

            // New proportions? G + A -> A
            //for (AlgebraicProportionalAngles apas : algPropAngs)
            //{
            //    newGrounded.addRange(createProportionalAngles(apas, congAngs));
            //}
        }

        //
        // NEW
        //
        else if (congArcs instanceof AlgebraicCongruentArcs)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentArcs oldACAS : algCongArcs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createCongruentArcs(oldACAS, congArcs)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aseqs : algArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createArcEquation(aseqs, congArcs)));
            }

            for (AlgebraicAngleArcEquation gseqs : algAngleArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleArcEquationFromArcSubstitution(gseqs, congArcs)));
            }

            //// New proportions? G + A -> A
            //for (AlgebraicProportionalAngles apas : algPropAngs)
            //{
            //    newGrounded.addRange(makePurelyAlgebraic(createProportionalAngles(apas, congAngs)));
            //}
        }

        return newGrounded;
    }

    //
    // For generation of transitive congruent Arcs
    //
    private static List<EdgeAggregator> createCongruentArcs(CongruentArcs cas1, CongruentArcs cas2)
    {
        int numSharedExps = cas1.sharesNumClauses(cas2);
        return createCongruent<CongruentArcs>(cas1, cas2, numSharedExps);
    }

    //
    // Generate all new relationships from an Equation Containing Arc measurements
    //
    private static List<EdgeAggregator> handleNewArcEquation(ArcEquation newArcEq)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        // New transitivity? G + G -> A
        for (GeometricCongruentArcs gcas : geoCongArcs)
        {
            newGrounded.addRange(createArcEquation(newArcEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation gangs : geoArcEqs)
        {
            newGrounded.addRange(createNewEquation(gangs, newArcEq));
        }

        if (newArcEq instanceof GeometricArcEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                newGrounded.addRange(createArcEquation(newArcEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                newGrounded.addRange(createNewEquation(aarcs, newArcEq));
            }
        }

        //
        // NEW
        //
        else if (newArcEq instanceof AlgebraicArcEquation)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentArcs oldACAS : algCongArcs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createArcEquation(newArcEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(aarcs, newArcEq)));
            }
        }

        return newGrounded;
    }

    //
    // Substitute this new arc congruence into old arc equations
    //
    private static List<EdgeAggregator> createArcEquation(ArcEquation arcEq, CongruentArcs congArcs)
    {
        List<EdgeAggregator> newGrounded = new List<EdgeAggregator>();
        EdgeAggregator newEquationEdge;

        //            if (angEq.HasRelationPredecessor(congAng) || congAng.HasRelationPredecessor(angEq)) return newGrounded;

        newEquationEdge = performEquationSubstitution(arcEq, congArcs, congArcs.ca1, congArcs.ca2);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);
        newEquationEdge = performEquationSubstitution(arcEq, congArcs, congArcs.ca2, congArcs.ca1);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);

        return newGrounded;
    }

    private static List<EdgeAggregator> handleNewAngleArcEquation(AngleArcEquation angleArcEq)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        //New transitivity?
        for (GeometricCongruentArcs gcas : geoCongArcs)
        {
            newGrounded.addRange(createAngleArcEquationFromArcSubstitution(angleArcEq, gcas));
        }
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            newGrounded.addRange(createAngleArcEquationFromAngleSubstitution(angleArcEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation garcs : geoArcEqs)
        {
            newGrounded.addRange(createNewEquation(garcs, angleArcEq));
        }
        for (GeometricAngleEquation gangs : geoAngleEqs)
        {
            newGrounded.addRange(createNewEquation(gangs, angleArcEq));
        }
        for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
        {
            newGrounded.addRange(createNewEquation(gangs, angleArcEq));
        }

        if (angleArcEq instanceof GeometricAngleArcEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                newGrounded.addRange(createAngleArcEquationFromArcSubstitution(angleArcEq, acas));
            }

            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                newGrounded.addRange(createAngleArcEquationFromAngleSubstitution(angleArcEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                newGrounded.addRange(createNewEquation(aarcs, angleArcEq));
            }
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                newGrounded.addRange(createNewEquation(aangs, angleArcEq));
            }
            for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
            {
                newGrounded.addRange(createNewEquation(gangs, angleArcEq));
            }
        }

        //
        // NEW
        //
        else if (angleArcEq instanceof AlgebraicAngleArcEquation)
        {
            // New transitivity? A + A -> A
            for (AlgebraicCongruentArcs oldACAS : algCongArcs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleArcEquationFromArcSubstitution(angleArcEq, oldACAS)));
            }

            for (AlgebraicCongruentAngles oldACAS : algCongAngles)
            {
                newGrounded.addRange(makePurelyAlgebraic(createAngleArcEquationFromAngleSubstitution(angleArcEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(aarcs, angleArcEq)));
            }
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(aangs, angleArcEq)));
            }
            for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
            {
                newGrounded.addRange(makePurelyAlgebraic(createNewEquation(gangs, angleArcEq)));
            }
        }

        return newGrounded;
    }

    private static List<EdgeAggregator> createAngleArcEquationFromArcSubstitution(AngleArcEquation angleArcEq, CongruentArcs congArcs) 
    {
        return createEquationFromSubstitution(angleArcEq, congArcs, congArcs.ca1, congArcs.ca2);
    }

    private static List<EdgeAggregator> createAngleArcEquationFromAngleSubstitution(AngleArcEquation angleArcEq, CongruentAngles congAngles)
    {
        return createEquationFromSubstitution(angleArcEq, congAngles, congAngles.ca1, congAngles.ca2);
    }

    private static List<EdgeAggregator> createEquationFromSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause c1, GroundedClause c2)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();
        EdgeAggregator newEquationEdge;

        newEquationEdge = performEquationSubstitution(eq, subbedEq, c1, c2);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);
        newEquationEdge = performEquationSubstitution(eq, subbedEq, c2, c1);
        if (newEquationEdge != null) newGrounded.add(newEquationEdge);

        return newGrounded;
    }

    //
    // Substitute some clause (subbedEq) into an equation (eq)
    //
    private static EdgeAggregator performEquationSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause toFind, GroundedClause toSub)
    {
        //Debug.WriteLine("Substituting with " + eq.ToString() + " and " + subbedEq.ToString());

        if (!eq.containsClause(toFind)) return null;

        //
        // Make a deep copy of the equation
        //
        Equation newEq = null;
        if (eq instanceof SegmentEquation)
        {
            newEq = new AlgebraicSegmentEquation(eq.lhs.deepCopy(), eq.rhs.deepCopy());
        }
        else if (eq instanceof AngleEquation)
        {
            newEq = new AlgebraicAngleEquation(eq.lhs.deepCopy(), eq.rhs.deepCopy());
        }
        else if (eq instanceof ArcEquation)
        {
            newEq = new AlgebraicArcEquation(eq.lhs.deepCopy(), eq.rhs.deepCopy());
        }
        else if (eq instanceof AngleArcEquation)
        {
            newEq = new AlgebraicAngleArcEquation(eq.lhs.deepCopy(), eq.rhs.deepCopy());
        }

        // Substitute into the copy
        newEq.Substitute(toFind, toSub);

        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq);
        antecedent.add(subbedEq);

        //
        // simplify the equation
        //
        Equation simplified = Simplification.simplify(newEq);

        // Create a congruence relationship if it applies
        GroundedClause newCongruence = handleCongruence(simplified);
        if (newCongruence != null) return new EdgeAggregator(antecedent, newCongruence, annotation);

        // If a congruence was not established, create a complementary or supplementary relationship, if applicable
        if (simplified instanceof AngleEquation && newCongruence == null)
        {
            GroundedClause newRelation = handleAngleRelation(simplified);
            if (newRelation != null) return new EdgeAggregator(antecedent, newRelation, annotation);
        }

        return new EdgeAggregator(antecedent, simplified, annotation);
    }

    //
    // This is pure transitivity where A + B = C , A + B = D -> C = D
    //
    private static EdgeAggregator performNonAtomicEquationSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause toFind, GroundedClause toSub)
    {
        //Debug.WriteLine("Substituting with " + eq.ToString() + " and " + subbedEq.ToString());

        // If there is a deduction relationship between the given congruences, do not perform another substitution
        //  subbedEq.HasPredecessor(eq)
        //if (eq.HasGeneralPredecessor(subbedEq) || subbedEq.HasGeneralPredecessor(eq)) return new EdgeAggregator(null, null);

        //
        // Verify that the non-atomic sides to both equations are the exact same
        //
        GroundedClause nonAtomicOriginal = null;
        GroundedClause atomicOriginal = null;
        if (eq.getAtomicity() == Equation.LEFT_ATOMIC)
        {
            nonAtomicOriginal = eq.rhs;
            atomicOriginal = eq.lhs;
        }
        else if (eq.getAtomicity() == Equation.RIGHT_ATOMIC)
        {
            nonAtomicOriginal = eq.lhs;
            atomicOriginal = eq.rhs;
        }

        // We collect all the flattened terms
        List<GroundedClause> originalTerms = nonAtomicOriginal.collectTerms();
        List<GroundedClause> subbedTerms = toFind.collectTerms();

        // Now, the lists must be the same; we check for containment : both directions
        for (GroundedClause originalTerm : originalTerms)
        {
            if (!subbedTerms.contains(originalTerm)) return null;
        }

        for (GroundedClause subbedTerm : subbedTerms)
        {
            if (!originalTerms.contains(subbedTerm)) return null;
        }

        // Hypergraph
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq);
        antecedent.add(subbedEq);
        EdgeAggregator newEdge;

        //
        // Generate a simple equation or an algebraic congruent statement
        //
        if (atomicOriginal instanceof NumericValue || toSub instanceof NumericValue)
        {
            Equation newEquation = null;
            if (eq instanceof AngleEquation)
            {
                newEquation = new AlgebraicAngleEquation(atomicOriginal.deepCopy(), toSub.deepCopy());
            }
            else if (eq instanceof SegmentEquation)
            {
                newEquation = new AlgebraicSegmentEquation(atomicOriginal.deepCopy(), toSub.deepCopy());
            }

            if (newEquation == null)
            {
                //debug.writeLine("");
                throw new NullReferenceException("Unexpected Problem : Non-atomic substitution (equation)...");
            }

            newEdge = new EdgeAggregator(antecedent, newEquation, annotation);
        }
        else
        {
            Congruent newCongruent = null;
            if (eq instanceof AngleEquation)
            {
                newCongruent = new AlgebraicCongruentAngles((Angle)atomicOriginal.deepCopy(), (Angle)toSub.deepCopy());
            }
            else if (eq instanceof SegmentEquation)
            {
                newCongruent = new AlgebraicCongruentSegments((Segment)atomicOriginal.deepCopy(), (Segment)toSub.deepCopy());
            }

            if (newCongruent == null)
            {
                Debug.WriteLine("");
                throw new NullReferenceException("Unexpected Problem : Non-atomic substitution (Congruence)...");
            }

            newEdge = new EdgeAggregator(antecedent, newCongruent, annotation);
        }

        return newEdge;
    }

    //
    // Given two equations, perform a direct, transitive substitution of one equation into the other (and vice versa)
    //
    private static List<EdgeAggregator> performEquationTransitiviteSubstitution(Equation eq1, Equation eq2)
    {
        List<GroundedClause> newRelations = new ArrayList<GroundedClause>();

        //
        // Collect the terms from each side of both equations
        //
        List<GroundedClause> lhsTermsEq1 = eq1.lhs.collectTerms();
        List<GroundedClause> lhsTermsEq2 = eq2.lhs.collectTerms();
        List<GroundedClause> rhsTermsEq1 = eq1.rhs.collectTerms();
        List<GroundedClause> rhsTermsEq2 = eq2.rhs.collectTerms();

        int equationType = getEquationType(eq1);

        //
        // Construct the new equations using all possible combinations
        //
        if (equalLists(lhsTermsEq1, lhsTermsEq2))
        {
            GroundedClause newEq = constructNewEquation(equationType, eq1.rhs, eq2.rhs);
            if (newEq != null) newRelations.add(newEq);
        }
        if (equalLists(lhsTermsEq1, rhsTermsEq2))
        {
            GroundedClause newEq = constructNewEquation(equationType, eq1.rhs, eq2.lhs);
            if (newEq != null) newRelations.add(newEq);
        }
        if (equalLists(rhsTermsEq1, lhsTermsEq2))
        {
            GroundedClause newEq = constructNewEquation(equationType, eq1.lhs, eq2.rhs);
            if (newEq != null) newRelations.add(newEq);
        }
        if (equalLists(rhsTermsEq1, rhsTermsEq2))
        {
            GroundedClause newEq = constructNewEquation(equationType, eq1.lhs, eq2.lhs);
            if (newEq != null) newRelations.add(newEq);
        }

        //
        // Construct the hypergraph edges for all of the new relationships
        //
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq1);
        antecedent.add(eq2);

        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        for (GroundedClause gc : newRelations)
        {
            newGrounded.add(new EdgeAggregator(antecedent, gc, annotation));
        }

        return newGrounded;
    }

    //
    // Given two equations, if one equation is atomic, substitute into the other (and vice versa)
    //
    //private static List<KeyValuePair<List<GroundedClause>, GroundedClause>> PerformEquationDirectSubstitution(Equation eq1, Equation eq2)
    //{
    //    //
    //    // Does the new equation have one side which is isolated (an atomic expression)?
    //    //
    //    int atomicSide = newEq.GetAtomicity();

    //    GroundedClause atomicExp = null;
    //    GroundedClause otherSide = null;
    //    switch (atomicSide)
    //    {
    //        case Equation.LEFT_ATOMIC:
    //            atomicExp = newEq.lhs;
    //            otherSide = newEq.rhs;
    //            break;

    //        case Equation.RIGHT_ATOMIC:
    //            atomicExp = newEq.rhs;
    //            otherSide = newEq.lhs;
    //            break;

    //        case Equation.BOTH_ATOMIC:
    //            // Choose both sides
    //            break;

    //        case Equation.NONE_ATOMIC:
    //            // If neither side of this new equation are atomic, for simplicity,
    //            // we do not perform a substitution
    //            return new List<EdgeAggregator>();
    //    }

    //    KeyValuePair<List<GroundedClause>, GroundedClause> cl;
    //    // One side of the equation is atomic
    //    if (atomicExp != null)
    //    {
    //        // Check to see if the old equation is dually atomic as
    //        // we will want to substitute the old eq into the new one
    //        int oldAtomic = oldEq.GetAtomicity();
    //        if (oldAtomic != Equation.BOTH_ATOMIC)
    //        {
    //            // Simple sub of new equation into old
    //            cl = performEquationSubstitution(oldEq, newEq, atomicExp, otherSide);
    //            if (cl.Value != null) newGrounded.add(cl);

    //            //
    //            // In the case where we have a situation of the form: A + B = C
    //            //                                                    A + B = D  -> C = D
    //            //
    //            // Perform a non-atomic substitution
    //            cl = performNonAtomicEquationSubstitution(oldEq, newEq, otherSide, atomicExp);
    //            if (cl.Value != null) newGrounded.add(cl);
    //        }
    //        else if (oldAtomic == Equation.BOTH_ATOMIC)
    //        {
    //            // Dual sub of old equation into new
    //            cl = performEquationSubstitution(newEq, oldEq, oldEq.lhs, oldEq.rhs);
    //            if (cl.Value != null) newGrounded.add(cl);
    //            cl = performEquationSubstitution(newEq, oldEq, oldEq.rhs, oldEq.lhs);
    //            if (cl.Value != null) newGrounded.add(cl);
    //        }
    //    }
    //    // The new equation has both sides atomic; try to sub : the other side
    //    else
    //    {
    //        cl = performEquationSubstitution(oldEq, newEq, newEq.lhs, newEq.rhs);
    //        if (cl.Value != null) newGrounded.add(cl);
    //        cl = performEquationSubstitution(oldEq, newEq, newEq.rhs, newEq.lhs);
    //        if (cl.Value != null) newGrounded.add(cl);
    //    }

    //    return newGrounded;
    //}

    //
    // If both sides of the substituted equation are atomic and not Numeric Values, create a congruence relationship instead.
    //
    private static GroundedClause handleCongruence(Equation simplified)
    {
        // Both sides must be atomic and multiplied by a factor of 1 for a proper congruence

        //Why can't I access the variable BOTH_ATOMIC?  How do I reference it?
        if (simplified.getAtomicity() != Equation.BOTH_ATOMIC) return null;         //HALP

        if (!simplified.isProperCongruence()) return null;
        // Then create a congruence, whether it be angle or segment
        Congruent newCongruent = null;
        if (simplified instanceof AlgebraicAngleEquation)
        {
            // Do not generate for lines; that is, 180^o angles
            //if (((Angle)simplified.lhs).IsStraightAngle() && ((Angle)simplified.lhs).IsStraightAngle()) return null;
            newCongruent = new AlgebraicCongruentAngles((Angle)simplified.lhs, (Angle)simplified.rhs);
        }
        else if (simplified instanceof AlgebraicSegmentEquation)
        {
            newCongruent = new AlgebraicCongruentSegments((Segment)simplified.lhs, (Segment)simplified.rhs);
        }
        else if (simplified instanceof AlgebraicArcEquation)
        {
            newCongruent = new AlgebraicCongruentArcs((Arc)simplified.lhs, (Arc)simplified.rhs);
        }

        // There is no need to simplify a congruence, so just return
        return newCongruent;
    }

    //
    // For generic generation of transitive congruent clauses
    //

    //I have not a clue for this one...

    //Original C# Signature
    //private static List<EdgeAggregator> createCongruent<T>(T css, T geoCong, int numSharedExps) where T : Congruent
    //Attempted Translation
    private static <T extends Equation> List<EdgeAggregator> createCongruent(T css, T geoCong, int numSharedExps) 
    {
        {

            List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

            //if (css.HasRelationPredecessor(geoCongSeg) || geoCongSeg.HasRelationPredecessor(css)) return newGrounded;

            switch (numSharedExps)
            {
                case 0:
                    // Nothing is shared: do nothing
                    break;

                case 1:
                    // Expected case to create a new congruence relationship
                    return Congruent.createTransitiveCongruence(css, geoCong);

                case 2:
                    // This is either reflexive or the exact same congruence relationship (which shouldn't happen)
                    break;

                default:
                    throw new Exception("Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
            }

            return newGrounded;
        }
    }

    private static int getEquationType(Equation eq)
    {
        if (eq instanceof SegmentEquation) return SEGMENT_EQUATION;
        if (eq instanceof AngleEquation) return ANGLE_EQUATION;
        if (eq instanceof ArcEquation) return ARC_EQUATION;
        if (eq instanceof AngleArcEquation) return ANGLE_ARC_EQUATION;
        return EQUATION_ERROR;
    }

    //
    // Given two news sides for an equation, create the equation. If possible, create a congruence or a supplementary / complementary relationship
    //
    private static GroundedClause constructNewEquation(int equationType, GroundedClause left, GroundedClause right)
    {
        //
        //Account for possibility that substitution may require a change : equation type between Angle, Arc, and AngleArc
        //
        if (equationType != SEGMENT_EQUATION)
        {
            List<GroundedClause> terms = new List<GroundedClause>();
            terms.addRange(left.collectTerms());
            terms.addRange(right.collectTerms());

            //I need a refresher on how any should work
            boolean hasAngle = false;
            for (GroundedClause gc : terms)
                if (gc instanceof Angle)
                {
                    hasAngle = true;
                    break;
                }

            boolean hasArc = false;
            for (GroundedClause gc : terms)
                if (gc instanceof Angle)
                {
                    hasAngle = true;
                    break;
                }
            if (hasAngle && hasArc) equationType = ANGLE_ARC_EQUATION;
            else if (hasAngle) equationType = ANGLE_EQUATION;
            else if (hasArc) equationType = ARC_EQUATION;
        }

        //
        // Construct the new equation with a given left / right side
        //
        Equation newEq = null;
        if (equationType == SEGMENT_EQUATION)
        {
            newEq = new AlgebraicSegmentEquation(left, right);
        }
        else if (equationType == ANGLE_EQUATION)
        {
            newEq = new AlgebraicAngleEquation(left, right);
        }
        else if (equationType == ARC_EQUATION)
        {
            //Do not try to relate arcs from non-congruent circles
            Arc arc1 = (left instanceof Arc) ? (Arc) left : null;
            Arc arc2 = (right instanceof Arc) ?  (Arc) right : null;
            if (arc1 != null && arc2 != null && !utilities.compareValues(arc1.theCircle.radius, arc2.theCircle.radius)) return null;

            newEq = new AlgebraicArcEquation(left, right);
        }
        else if (equationType == ANGLE_ARC_EQUATION)
        {
            newEq = new AlgebraicAngleArcEquation(left, right);
        }

        //
        // simplify the equation
        //
        Equation simplified = Simplification.simplify(newEq);
        if (simplified == null) return null;

        //
        // Create a congruence relationship if it applies
        //
        GroundedClause newCongruence = handleCongruence(simplified);
        if (newCongruence != null) return newCongruence;

        //
        // If a congruence was not established, create a complementary or supplementary relationship, if applicable
        //
        if (equationType == ANGLE_EQUATION)
        {
            GroundedClause newRelation = handleAngleRelation(simplified);
            if (newRelation != null) return newRelation;
        }

        // Just return the simplified equation if nothing else could be deduced
        return simplified;
    }


    //
    // Given an old and new set of angle measure equations substitute if possible.
    //
    private static List<EdgeAggregator> createNewEquation(Equation oldEq, Equation newEq)
    {
        List<EdgeAggregator> newGrounded = new ArrayList<EdgeAggregator>();

        //Debug.WriteLine("Considering combining: " + oldEq + " + " + newEq);

        // Avoid redundant equation generation
        //            if (oldEq.HasRelationPredecessor(newEq) || newEq.HasRelationPredecessor(oldEq)) return newGrounded;

        // Determine if there is a direct, transitive relationship with the equations
        newGrounded.addAll(performEquationTransitiviteSubstitution(oldEq, newEq));

        // If we have an atomic situation, substitute into the other equation
        //newGrounded.addRange(PerformEquationDirectSubstitution(oldEq, newEq));

        return newGrounded;
    }
    //
    // add predecessors to the equations, congruence relationships, etc.
    //
    private static void markPredecessors(List<EdgeAggregator> edges)
    {
        for (EdgeAggregator edge : edges)
        {
            for (GroundedClause predNode : edge.getAntecedent())
            {
                edge.consequent.addRelationPredecessor(predNode);
                edge.consequent.addRelationPredecessors(predNode.getRelationPredecessors());
            }
        }
    }

    // Sets all of the deduced nodes to be purely algebraic: A + A -> A
    private static List<EdgeAggregator> makePurelyAlgebraic(List<EdgeAggregator> edges)
    {
        for (EdgeAggregator edge : edges)
        {
            edge.consequent.makePurelyAlgebraic();
        }

        return edges;
    }

    //
    // Check equivalence of lists by verifying dual containment.
    //
    private static boolean equalLists(List<GroundedClause> list1, List<GroundedClause> list2)
    {
        for (GroundedClause val1 : list1)
        {
            if (!list2.contains(val1)) return false;
        }

        for (GroundedClause val2 : list2)
        {
            if (!list1.contains(val2)) return false;
        }

        return true;
    }

    //
    // add the new grounded clause to the correct list.
    //

    private static void addToAppropriateList(GroundedClause c)
    {
        if (c instanceof GeometricCongruentSegments)
        {
            geoCongSegments.add((GeometricCongruentSegments) c);
        }
        else if (c instanceof GeometricSegmentEquation)
        {
            geoSegmentEqs.add((GeometricSegmentEquation) c);
        }
        else if (c instanceof GeometricCongruentAngles)
        {
            geoCongAngles.add((GeometricCongruentAngles) c);
        }
        else if (c instanceof GeometricAngleEquation)
        {
            geoAngleEqs.add((GeometricAngleEquation) c);
        }
        else if (c instanceof GeometricCongruentArcs)
        {
            geoCongArcs.add((GeometricCongruentArcs) c);
        }
        else if (c instanceof GeometricArcEquation)
        {
            geoArcEqs.add((GeometricArcEquation) c);
        }
        else if (c instanceof GeometricAngleArcEquation)
        {
            geoAngleArcEqs.add((GeometricAngleArcEquation) c);
        }
        else if (c instanceof AlgebraicSegmentEquation)
        {
            algSegmentEqs.add((AlgebraicSegmentEquation) c);
        }
        else if (AlgebraicAngleEquation)
        {
            algAngleEqs.add((AlgebraicAngleEquation) c);
        }
        else if (c instanceof AlgebraicArcEquation)
        {
            algArcEqs.add((AlgebraicArcEquation) c);
        }
        else if (c instanceof AlgebraicAngleArcEquation)
        {
            algAngleArcEqs.add((AlgebraicAngleArcEquation) c);
        }
        else if (c instanceof AlgebraicCongruentSegments)
        {
            algCongSegments.add((AlgebraicCongruentSegments) c);
        }
        else if (c instanceof AlgebraicCongruentAngles)
        {
            algCongAngles.add((AlgebraicCongruentAngles) c);
        }
        else if (c instanceof AlgebraicCongruentArcs)
        {
            algCongArcs.add((AlgebraicCongruentArcs) c);
        }
        else if (c instanceof SegmentRatio)
        {
            propSegs.add((SegmentRatio) c);
        }
        //else if (c instanceof AlgebraicSegmentRatioEquation)
        //{
        //    algPropSegs.add((AlgebraicSegmentRatioEquation) c);
        //}
        else if (c instanceof GeometricProportionalAngles)
        {
            geoPropAngs.add((GeometricProportionalAngles) c);
        }
        else if (c instanceof AlgebraicProportionalAngles)
        {
            algPropAngs.add((AlgebraicProportionalAngles) c);
        }
    }

    //
    // add the new grounded clause to the correct list.
    //
    private static boolean clauseHasBeenDeduced(GroundedClause c)
    {
        if (c instanceof GeometricCongruentSegments)
        {
            return geoCongSegments.contains((GeometricCongruentSegments) c);
        }
        else if (c instanceof GeometricSegmentEquation)
        {
            return geoSegmentEqs.contains((GeometricSegmentEquation) c);
        }
        else if (c instanceof GeometricCongruentAngles)
        {
            return geoCongAngles.contains((GeometricCongruentAngles) c);
        }
        else if (c instanceof GeometricAngleEquation)
        {
            return geoAngleEqs.contains((GeometricAngleEquation) c);
        }
        else if (c instanceof GeometricCongruentArcs)
        {
            return geoCongArcs.contains((GeometricCongruentArcs) c);
        }
        else if (c instanceof GeometricArcEquation)
        {
            return geoArcEqs.contains((GeometricArcEquation) c);
        }
        else if (c instanceof GeometricAngleArcEquation)
        {
            return geoAngleArcEqs.contains((GeometricAngleArcEquation) c);
        }
        else if (c instanceof AlgebraicSegmentEquation)
        {
            return algSegmentEqs.contains((AlgebraicSegmentEquation) c);
        }
        else if (c instanceof AlgebraicAngleEquation)
        {
            return algAngleEqs.contains((AlgebraicAngleEquation) c);
        }
        else if (c instanceof AlgebraicArcEquation)
        {
            return algArcEqs.contains((AlgebraicArcEquation) c);
        }
        else if (c instanceof AlgebraicAngleArcEquation)
        {
            return algAngleArcEqs.contains((AlgebraicAngleArcEquation) c);
        }
        else if (c instanceof AlgebraicCongruentSegments)
        {
            return algCongSegments.contains((AlgebraicCongruentSegments) c);
        }
        else if (c instanceof AlgebraicCongruentAngles)
        {
            return algCongAngles.contains((AlgebraicCongruentAngles) c);
        }
        else if (c instanceof AlgebraicCongruentArcs)
        {
            return algCongArcs.contains((AlgebraicCongruentArcs) c);
        }
        else if (c instanceof GeometricSegmentRatioEquation)
        {
            return propSegs.contains((SegmentRatio) c);
        }
        //else if (c instanceof AlgebraicSegmentRatioEquation)
        //{
        //    return algPropSegs.contains((AlgebraicSegmentRatioEquation) c);
        //}
        else if (c instanceof GeometricProportionalAngles)
        {
            return geoPropAngs.contains((GeometricProportionalAngles) c);
        }
        else if (c instanceof AlgebraicProportionalAngles)
        {
            return algPropAngs.contains((AlgebraicProportionalAngles) c);
        }

        return false;
    }
    */
}