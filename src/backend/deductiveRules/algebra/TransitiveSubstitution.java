package backend.deductiveRules.algebra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Axiom;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.Congruent;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.Descriptors.Relations.Proportionalities.AlgebraicProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.GeometricProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.GeometricSegmentRatioEquation;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.hypergraph.*;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AlgebraicAngleArcEquation;
import backend.symbolicAlgebra.equations.AlgebraicAngleEquation;
import backend.symbolicAlgebra.equations.AlgebraicArcEquation;
import backend.symbolicAlgebra.equations.AlgebraicSegmentEquation;
import backend.symbolicAlgebra.equations.AngleArcEquation;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.ArcEquation;
import backend.symbolicAlgebra.equations.Equation;
import backend.symbolicAlgebra.equations.GeometricAngleArcEquation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.symbolicAlgebra.equations.GeometricArcEquation;
import backend.symbolicAlgebra.equations.GeometricSegmentEquation;
import backend.symbolicAlgebra.equations.SegmentEquation;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;

public class TransitiveSubstitution extends Axiom
{
    private static final String NAME = "Transitive Substitution";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
 // Congruences imply equations: AB \cong CD -> AB = CD
    private static HashSet<GeometricCongruentSegments> geoCongSegments;
    private static HashSet<GeometricCongruentAngles> geoCongAngles;
    private static HashSet<GeometricCongruentArcs> geoCongArcs;

  // These are transitively deduced congruences
    private static HashSet<AlgebraicCongruentSegments> algCongSegments;
    private static HashSet<AlgebraicCongruentAngles> algCongAngles;
    private static HashSet<AlgebraicCongruentArcs> algCongArcs;

  // Old segment equations
    private static HashSet<GeometricSegmentEquation> geoSegmentEqs;
    private static HashSet<AlgebraicSegmentEquation> algSegmentEqs;

  // Old angle measure equations
    private static HashSet<AlgebraicAngleEquation> algAngleEqs;
    private static HashSet<GeometricAngleEquation> geoAngleEqs;

  // Old arc equations
    private static HashSet<GeometricArcEquation> geoArcEqs;
    private static HashSet<AlgebraicArcEquation> algArcEqs;

  // Old angle-arc equation
    private static HashSet<GeometricAngleArcEquation> geoAngleArcEqs;
    private static HashSet<AlgebraicAngleArcEquation> algAngleArcEqs;

  // Old Proportional Segment Expressions
    private static HashSet<SegmentRatio> propSegs = _qhg.getSegmentRatios();

  // Old Proportional Angle Expressions
    private static HashSet<GeometricProportionalAngles> geoPropAngs = _qhg.getGeometricProportionalAngles();
    private static HashSet<AlgebraicProportionalAngles> algPropAngs = _qhg.getAlgebraicProportionalAngles();

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRANSITIVE_SUBSTITUTION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public TransitiveSubstitution(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.TRANSITIVE_SUBSTITUTION;
        
     // Congruences imply equations: AB \cong CD -> AB = CD
        geoCongSegments = _qhg.getGeometricCongruentSegments();
        geoCongAngles = _qhg.getGeomtricCongruentAngles();
        geoCongArcs = _qhg.getGeometricCongruentArcs();

      // These are transitively deduced congruences
        algCongSegments = _qhg.getAlgebraicCongruentSegments();
        algCongAngles = _qhg.getAlgebraicCongruentAngles();
        algCongArcs = _qhg.getAlgebraicCogruentArcs();

      // Old segment equations
        geoSegmentEqs = _qhg.getGeometricSegmentEquations();
        algSegmentEqs = _qhg.getAlgebraicSegmentEquations();

      // Old angle measure equations
        algAngleEqs = _qhg.getAlgebraicAngleEquations();
        geoAngleEqs = _qhg.getGeometricAngleEquations();

      // Old arc equations
        geoArcEqs = _qhg.getGeomtricArcEquations();
        algArcEqs = _qhg.getAlgebraicArcEquations();

      // Old angle-arc equation
        geoAngleArcEqs = _qhg.getGeometricAngleArcEquations();
        algAngleArcEqs = _qhg.getAlgebraicAngleArcEquations();

      // Old Proportional Segment Expressions
        propSegs = _qhg.getSegmentRatios();

      // Old Proportional Angle Expressions
        geoPropAngs = _qhg.getGeometricProportionalAngles();
        algPropAngs = _qhg.getAlgebraicProportionalAngles();
    }
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        deductions.addAll(deduceEquations());
        return deductions;
    }

    // For cosntruction of the new equations
    private static final int SEGMENT_EQUATION = 0;
    private static final int ANGLE_EQUATION = 1;
    private static final int ARC_EQUATION = 2;
    private static final int ANGLE_ARC_EQUATION = 3;
    private static final int EQUATION_ERROR = 1;


    //
    // Implements transitivity with equations
    // Equation(A, B), Equation(B, C) -> Equation(A, C)
    //
    // This includes CongruentSegments and CongruentAngles
    //
    // Generation of new equations instanceof restricted to the following rules; let G be Geometric and A algebriac
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static ArrayList<Deduction> deduceEquations()
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // Do we have an equation or congruence?
//        if (!(clause instanceof Equation) && !(clause instanceof Congruent) && !(clause instanceof SegmentRatio)) return deductions;

        HashSet<SegmentEquation> ses = _qhg.getSegmentEquations();
        HashSet<AngleEquation> aes = _qhg.getAngleEquations();
        HashSet<ArcEquation> arcEs = _qhg.getArcEquations();
        HashSet<AngleArcEquation> aaes = _qhg.getAngleArcEquations();
        HashSet<CongruentAngles> cas = _qhg.getCongruentAngles();
        HashSet<CongruentSegments> css = _qhg.getCongruentSegments();
        HashSet<CongruentArcs> cArcs = _qhg.getCongruentArcs();
        HashSet<SegmentRatio> srs = _qhg.getSegmentRatios();
        
     
        // Has this clause been generated before?
        // Since generated clauses will eventually be instantiated as well, this will reach a fixed point and stop.
        // Uniqueness of clauses needs to be handled by the class calling this
//        if (ClauseHasBeenDeduced(clause)) return deductions; --Checked in every loop

        // A reflexive expression provides no information of interest or consequence.
//        if (clause.isReflexive()) return deductions; --Checked in every loop

        //
        // Process the clause
        //
        for(SegmentEquation newSegEq : ses)
        {
            if (!newSegEq.isReflexive()) deductions.addAll(HandleNewSegmentEquation(newSegEq));
        }
        
        for(AngleEquation ae : aes)
        {
            if (!ae.isReflexive()) deductions.addAll(HandleNewAngleEquation(ae));
        }
        
        for(ArcEquation arce : arcEs)
        {
            if(!arce.isReflexive()) deductions.addAll(HandleNewArcEquation(arce));
        }

        for(AngleArcEquation aae : aaes)
        {
            if(!aae.isReflexive()) deductions.addAll(HandleNewAngleArcEquation(aae));
        }

        for(CongruentAngles ca : cas)
        {
            if(!ca.isReflexive()) deductions.addAll(HandleNewCongruentAngles(ca));
        }
        
        for(CongruentSegments cs : css)
        {
            if(!cs.isReflexive()) deductions.addAll(HandleNewCongruentSegments(cs));
        }
        
        for(CongruentArcs ca : cArcs)
        {
            if(!ca.isReflexive()) deductions.addAll(HandleNewCongruentArcs(ca));
        }

        for(SegmentRatio ratio : srs)
        {
            if(!ratio.isReflexive())
            if (!ratio.ProportionValueKnown()) return deductions;

            // Avoid using proportional segments that should really be congruent (they are deduced from similar triangles which are, : fact, congruent)
            if (Utilities.CompareValues((ratio).getDictatedProportion(), 1)) return deductions;

            deductions.addAll(HandleNewSegmentRatio(ratio));
        }

        // add predecessors
        MarkPredecessors(deductions);

        return deductions;
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Segments
    //
    private static ArrayList<Deduction> HandleNewCongruentSegments(CongruentSegments congSegs)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentSegments gcss : geoCongSegments)
        {
            deductions.addAll(CreateCongruentSegments(gcss, congSegs));
        }

        // New equations? G + G -> A
        for (GeometricSegmentEquation gseqs : geoSegmentEqs)
        {
            deductions.addAll(CreateSegmentEquation(gseqs, congSegs));
        }

        // New proportions? G + G -> A
        for (SegmentRatio ps : propSegs)
        {
            deductions.addAll(CreateSegmentRatio(ps, congSegs));
        }

        if (congSegs instanceof GeometricCongruentSegments)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentSegments acss : algCongSegments)
            {
                deductions.addAll(CreateCongruentSegments(acss, congSegs));
            }

            // New equations? G + A -> A
            for (AlgebraicSegmentEquation aseqs : algSegmentEqs)
            {
                deductions.addAll(CreateSegmentEquation(aseqs, congSegs));
            }

            // New proportions? G + A -> A
            //for (AlgebraicSegmentRatio aps : algPropSegs)
            //{
            //    deductions.addAll(CreateSegmentRatio(aps, congSegs));
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
                deductions.addAll(MakePurelyAlgebraic(CreateCongruentSegments(oldACSS, congSegs)));
            }

            // New equations? A + A -> A
            for (AlgebraicSegmentEquation aseqs : algSegmentEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateSegmentEquation(aseqs, congSegs)));
            }

            // New proportions? A + A -> A
            //for (AlgebraicSegmentRatio aps : algPropSegs)
            //{
            //    deductions.addAll(MakePurelyAlgebraic(CreateSegmentRatio(aps, congSegs)));
            //}
        }

        return deductions;
    }
    //
    // For generation of transitive congruent segments
    //
    private static ArrayList<Deduction> CreateCongruentSegments(CongruentSegments css, CongruentSegments geoCongSeg)
    {
        int numSharedExps = css.SharesNumClauses(geoCongSeg);
        return CreateCongruent<CongruentSegments>(css, geoCongSeg, numSharedExps);
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Segments
    //
    private static ArrayList<Deduction> HandleNewSegmentRatio(SegmentRatio newRatio)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        for (SegmentRatio old : propSegs)
        {
            deductions.addAll(SegmentRatio.CreateProportionEquation(old, newRatio));
        }

        // New transitivity? G + G -> A
        //for (GeometricCongruentSegments gcs : geoCongSegments)
        //{
        //    deductions.addAll(CreateSegmentRatio(propSegs, gcs));
        //}

        //if (propSegs instanceof SegmentRatio)
        //{
        //    // New transitivity? G + A -> A
        //    for (AlgebraicCongruentSegments acs : algCongSegments)
        //    {
        //        deductions.addAll(CreateSegmentRatio(propSegs, acs));
        //    }
        //}

        //else if (propSegs instanceof AlgebraicSegmentRatio)
        //{
        //    // New transitivity? A + A -> A
        //    for (AlgebraicCongruentSegments acs : algCongSegments)
        //    {
        //        deductions.addAll(MakePurelyAlgebraic(CreateSegmentRatio(propSegs, acs)));
        //    }
        //}

        return deductions;
    }

    //
    // For generation of transitive proportional segments
    //
    private static ArrayList<Deduction> CreateSegmentRatio(SegmentRatio pss, CongruentSegments conSeg)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        int numSharedExps = pss.SharesNumClauses(conSeg);
        switch (numSharedExps)
        {
            case 0:
                // Nothing instanceof shared: do nothing
                break;

            case 1:
            // Expected case to create a new congruence relationship
            //return SegmentRatio.CreateTransitiveProportion(pss, conSeg);

            case 2:
                // This instanceof either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:
                throw new Exception("Proportional / Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return deductions;
    }

    //
    // Generate all new relationships from an Equation Containing Segments
    //
    private static ArrayList<Deduction> HandleNewSegmentEquation(SegmentEquation newSegEq)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentSegments gcss : geoCongSegments)
        {
            deductions.addAll(CreateSegmentEquation(newSegEq, gcss));
        }

        // New equations? G + G -> A
        for (GeometricSegmentEquation gSegs : geoSegmentEqs)
        {
            deductions.addAll(CreateNewEquation(gSegs, newSegEq));
        }

        if (newSegEq instanceof GeometricSegmentEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentSegments acss : algCongSegments)
            {
                deductions.addAll(CreateSegmentEquation(newSegEq, acss));
            }

            // New equations? G + A -> A
            for (AlgebraicSegmentEquation aSegs : algSegmentEqs)
            {
                deductions.addAll(CreateNewEquation(aSegs, newSegEq));
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
                deductions.addAll(MakePurelyAlgebraic(CreateSegmentEquation(newSegEq, oldACSS)));
            }

            // New equations? A + A -> A
            for (AlgebraicSegmentEquation aSegs : algSegmentEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(aSegs, newSegEq)));
            }
        }


        //
        // Combining TWO algebraic equations only if the result is a congruence: A + A -> Congruent
        //
        //else if (newSegEq is AlgebraicSegmentEquation)
        //{
        //    foreach (AlgebraicSegmentEquation asegs in algSegmentEqs)
        //    {
        //        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newEquationList = CreateNewEquation(newSegEq, asegs);
        //        if (newEquationList.Any())
        //        {
        //            KeyValuePair<List<GroundedClause>, GroundedClause> newEq = newEquationList[0];

        //            if (newEq.Value is AlgebraicCongruentSegments)
        //            {
        //                deductions.addAll(newEquationList);
        //            }
        //        }
        //    }
        //}
        return deductions;
    }

    //
    // Substitute this new segment congruence into old segment equations
    //
    private static ArrayList<Deduction> CreateSegmentEquation(SegmentEquation segEq, CongruentSegments congSeg)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();
        Deduction newEquationEdge;

        newEquationEdge = PerformEquationSubstitution(segEq, congSeg, congSeg.getcs1(), congSeg.getcs2());
        if (newEquationEdge != null) deductions.add(newEquationEdge);
        newEquationEdge = PerformEquationSubstitution(segEq, congSeg, congSeg.getcs2(), congSeg.getcs1());
        if (newEquationEdge != null) deductions.add(newEquationEdge);

        return deductions;
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Angles
    //
    private static ArrayList<Deduction> HandleNewCongruentAngles(CongruentAngles congAngs)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcss : geoCongAngles)
        {
            deductions.addAll(CreateCongruentAngles(gcss, congAngs));
        }

        // New equations? G + G -> A
        for (GeometricAngleEquation gseqs : geoAngleEqs)
        {
            deductions.addAll(CreateAngleEquation(gseqs, congAngs));
        }

        for (GeometricAngleArcEquation gseqs : geoAngleArcEqs)
        {
            deductions.addAll(CreateAngleArcEquationFromAngleSubstitution(gseqs, congAngs));
        }

        // New proportions? G + G -> A
        for (GeometricProportionalAngles gpas : geoPropAngs)
        {
            deductions.addAll(CreateProportionalAngles(gpas, congAngs));
        }

        if (congAngs instanceof GeometricCongruentAngles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acss : algCongAngles)
            {
                deductions.addAll(CreateCongruentAngles(acss, congAngs));
            }

            // New equations? G + A -> A
            for (AlgebraicAngleEquation aseqs : algAngleEqs)
            {
                deductions.addAll(CreateAngleEquation(aseqs, congAngs));
            }

            for (AlgebraicAngleArcEquation aseqs : algAngleArcEqs)
            {
                deductions.addAll(CreateAngleArcEquationFromAngleSubstitution(aseqs, congAngs));
            }

            // New proportions? G + A -> A
            for (AlgebraicProportionalAngles apas : algPropAngs)
            {
                deductions.addAll(CreateProportionalAngles(apas, congAngs));
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
                deductions.addAll(MakePurelyAlgebraic(CreateCongruentAngles(oldACSS, congAngs)));
            }

            // New equations? A + A -> A
            for (AlgebraicAngleEquation aseqs : algAngleEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateAngleEquation(aseqs, congAngs)));
            }

            for (AlgebraicAngleArcEquation aseqs : algAngleArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateAngleArcEquationFromAngleSubstitution(aseqs, congAngs)));
            }

            // New proportions? G + A -> A
            for (AlgebraicProportionalAngles apas : algPropAngs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateProportionalAngles(apas, congAngs)));
            }
        }

        return deductions;
    }

    //
    // For generation of transitive congruent Angles
    //
    private static ArrayList<Deduction> CreateCongruentAngles(CongruentAngles css, CongruentAngles congAng)
    {
        int numSharedExps = css.SharesNumClauses(congAng);
        return CreateCongruent<CongruentAngles>(css, congAng, numSharedExps);
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Segments
    //
    private static ArrayList<Deduction> HandleNewProportionalAngles(ProportionalAngles propAngs)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            deductions.addAll(CreateProportionalAngles(propAngs, gcas));
        }

        if (propAngs instanceof GeometricProportionalAngles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                deductions.addAll(CreateProportionalAngles(propAngs, acas));
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
                deductions.addAll(MakePurelyAlgebraic(CreateProportionalAngles(propAngs, acas)));
            }
        }


        return deductions;
    }

    //
    // For generation of transitive proportional angles
    //
    private static ArrayList<Deduction> CreateProportionalAngles(ProportionalAngles pas, CongruentAngles conAng)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        int numSharedExps = pas.SharesNumClauses(conAng);
        switch (numSharedExps)
        {
            case 0:
                // Nothing instanceof shared: do nothing
                break;

            case 1:
            // Expected case to create a new congruence relationship
            //return ProportionalAngles.CreateTransitiveProportion(pas, conAng);

            case 2:
                // This instanceof either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:
                throw new Exception("Proportional / Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return deductions;
    }

    //
    // Generate all new relationships from an Equation Containing Angle measurements
    //
    private static ArrayList<Deduction> HandleNewAngleEquation(AngleEquation newAngEq)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            deductions.addAll(CreateAngleEquation(newAngEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricAngleEquation gangs : geoAngleEqs)
        {
            deductions.addAll(CreateNewEquation(gangs, newAngEq));
        }

        if (newAngEq instanceof GeometricAngleEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                deductions.addAll(CreateAngleEquation(newAngEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                deductions.addAll(CreateNewEquation(aangs, newAngEq));
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
                deductions.addAll(MakePurelyAlgebraic(CreateAngleEquation(newAngEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(aangs, newAngEq)));
            }
        }

        return deductions;
    }

    //
    // Substitute this new angle congruence into old angle equations
    //
    private static ArrayList<Deduction> CreateAngleEquation(AngleEquation angEq, CongruentAngles congAng)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();
        Deduction newEquationEdge;

        //            if (angEq.HasRelationPredecessor(congAng) || congAng.HasRelationPredecessor(angEq)) return deductions;

        newEquationEdge = PerformEquationSubstitution(angEq, congAng, congAng.GetFirstAngle(), congAng.GetSecondAngle());
        if (newEquationEdge != null) deductions.add(newEquationEdge);
        newEquationEdge = PerformEquationSubstitution(angEq, congAng, congAng.GetSecondAngle(), congAng.GetFirstAngle());
        if (newEquationEdge != null) deductions.add(newEquationEdge);

        return deductions;
    }

    //
    // If both sides of the substituted equation are atomic and not Numeric Values, create a congruence relationship instead.
    //
    private static GroundedClause HandleAngleRelation(Equation simplified)
    {
        // One side must be atomic
        int atomicity = simplified.getAtomicity();
        if (atomicity == Equation.NONE_ATOMIC) return null;

        GroundedClause atomic = null;
        GroundedClause nonAtomic = null;
        if (atomicity == Equation.LEFT_ATOMIC)
        {
            atomic = simplified.getLHS();
            nonAtomic = simplified.getRHS();
        }
        else if (atomicity == Equation.RIGHT_ATOMIC)
        {
            atomic = simplified.getRHS();
            nonAtomic = simplified.getLHS();
        }
        else if (atomicity == Equation.BOTH_ATOMIC)
        {
            return HandleCollinearPerpendicular(simplified.getLHS(), simplified.getRHS());
        }

        NumericValue atomicValue = (NumericValue)atomic;
        if (atomicValue == null) return null;

        //
        // We need only consider special angles (90 or 180)
        //
        if (!Utilities.CompareValues(atomicValue.getIntValue(), 90) && !Utilities.CompareValues(atomicValue.getIntValue(), 180)) return null;

        ArrayList<GroundedClause> nonAtomicSide = nonAtomic.collectTerms();

        // Check multiplier for all terms; it must be 1.
        for (GroundedClause gc : nonAtomicSide)
        {
            if (gc.multiplier != 1) return null;
        }

        //
        // Complementary or Supplementary
        //
        AnglePairRelation newRelation = null;
        if (nonAtomicSide.size() == 2)
        {
            if (Utilities.CompareValues(atomicValue.getIntValue(), 90))
            {
                newRelation = new Complementary((Angle)nonAtomicSide.get(0), (Angle)nonAtomicSide.get(1));
            }
            else if (Utilities.CompareValues(atomicValue.getIntValue(), 180))
            {
                newRelation = new Supplementary((Angle)nonAtomicSide.get(0), (Angle)nonAtomicSide.get(1));
            }
        }

        return newRelation;
    }

    //
    // Create a deduced collinear or perpendicular relationship
    //
    private static GroundedClause HandleCollinearPerpendicular(GroundedClause left, GroundedClause right)
    {
        NumericValue numeral = null;
        Angle angle = null;

        //
        // Split the sides
        //
        if (left instanceof NumericValue)
        {
            numeral = (NumericValue)left;
            angle = (Angle)right;
        }
        else if (right instanceof NumericValue)
        {
            numeral = (NumericValue)right;
            angle = (Angle)left;
        }

        if (numeral == null || angle == null) return null;

        //
        // Create the new relationships
        //
        Descriptor newDescriptor = null;
        //if (Utilities.CompareValues(numeral.value, 90))
        //{
        //    newDescriptor = new Perpendicular(angle.GetVertex(), angle.ray1, angle.ray2);
        //}
        //else
        if (Utilities.CompareValues(numeral.getIntValue(), 180))
        {
            ArrayList<Point> pts = new ArrayList<Point>();
            pts.add(angle.getA());
            pts.add(angle.getB());
            pts.add(angle.getC());
            newDescriptor = new Collinear(pts);
        }

        return newDescriptor;
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Arcs
    //
    private static ArrayList<Deduction> HandleNewCongruentArcs(CongruentArcs congArcs)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentArcs gcss : geoCongArcs)
        {
            deductions.addAll(CreateCongruentArcs(gcss, congArcs));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation gseqs : geoArcEqs)
        {
            deductions.addAll(CreateArcEquation(gseqs, congArcs));
        }

        for (GeometricAngleArcEquation gseqs : geoAngleArcEqs)
        {
            deductions.addAll(CreateAngleArcEquationFromArcSubstitution(gseqs, congArcs));
        }

        //// New proportions? G + G -> A
        //for (GeometricProportionalAngles gpas : geoPropAngs)
        //{
        //    deductions.addAll(CreateProportionalAngles(gpas, congAngs));
        //}

        if (congArcs instanceof GeometricCongruentArcs)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                deductions.addAll(CreateCongruentArcs(acas, congArcs));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aseqs : algArcEqs)
            {
                deductions.addAll(CreateArcEquation(aseqs, congArcs));
            }

            for (AlgebraicAngleArcEquation gseqs : algAngleArcEqs)
            {
                deductions.addAll(CreateAngleArcEquationFromArcSubstitution(gseqs, congArcs));
            }

            // New proportions? G + A -> A
            //for (AlgebraicProportionalAngles apas : algPropAngs)
            //{
            //    deductions.addAll(CreateProportionalAngles(apas, congAngs));
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
                deductions.addAll(MakePurelyAlgebraic(CreateCongruentArcs(oldACAS, congArcs)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aseqs : algArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateArcEquation(aseqs, congArcs)));
            }

            for (AlgebraicAngleArcEquation gseqs : algAngleArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateAngleArcEquationFromArcSubstitution(gseqs, congArcs)));
            }

            //// New proportions? G + A -> A
            //for (AlgebraicProportionalAngles apas : algPropAngs)
            //{
            //    deductions.addAll(MakePurelyAlgebraic(CreateProportionalAngles(apas, congAngs)));
            //}
        }

        return deductions;
    }

    //
    // For generation of transitive congruent Arcs
    //
    private static ArrayList<Deduction> CreateCongruentArcs(CongruentArcs cas1, CongruentArcs cas2)
    {
        int numSharedExps = cas1.SharesNumClauses(cas2);
        return CreateCongruent<CongruentArcs>(cas1, cas2, numSharedExps);
    }

    //
    // Generate all new relationships from an Equation Containing Arc measurements
    //
    private static ArrayList<Deduction> HandleNewArcEquation(ArcEquation newArcEq)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricCongruentArcs gcas : geoCongArcs)
        {
            deductions.addAll(CreateArcEquation(newArcEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation gangs : geoArcEqs)
        {
            deductions.addAll(CreateNewEquation(gangs, newArcEq));
        }

        if (newArcEq instanceof GeometricArcEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                deductions.addAll(CreateArcEquation(newArcEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                deductions.addAll(CreateNewEquation(aarcs, newArcEq));
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
                deductions.addAll(MakePurelyAlgebraic(CreateArcEquation(newArcEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(aarcs, newArcEq)));
            }
        }

        return deductions;
    }

    //
    // Substitute this new arc congruence into old arc equations
    //
    private static ArrayList<Deduction> CreateArcEquation(ArcEquation arcEq, CongruentArcs congArcs)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();
        Deduction newEquationEdge;

        //            if (angEq.HasRelationPredecessor(congAng) || congAng.HasRelationPredecessor(angEq)) return deductions;

        newEquationEdge = PerformEquationSubstitution(arcEq, congArcs, congArcs.getFirstArc(), congArcs.getSecondArc());
        if (newEquationEdge != null) deductions.add(newEquationEdge);
        newEquationEdge = PerformEquationSubstitution(arcEq, congArcs, congArcs.getSecondArc(), congArcs.getFirstArc());
        if (newEquationEdge != null) deductions.add(newEquationEdge);

        return deductions;
    }


    private static ArrayList<Deduction> HandleNewAngleArcEquation(AngleArcEquation angleArcEq)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        //New transitivity?
        for (GeometricCongruentArcs gcas : geoCongArcs)
        {
            deductions.addAll(CreateAngleArcEquationFromArcSubstitution(angleArcEq, gcas));
        }
        for (GeometricCongruentAngles gcas : geoCongAngles)
        {
            deductions.addAll(CreateAngleArcEquationFromAngleSubstitution(angleArcEq, gcas));
        }

        // New equations? G + G -> A
        for (GeometricArcEquation garcs : geoArcEqs)
        {
            deductions.addAll(CreateNewEquation(garcs, angleArcEq));
        }
        for (GeometricAngleEquation gangs : geoAngleEqs)
        {
            deductions.addAll(CreateNewEquation(gangs, angleArcEq));
        }
        for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
        {
            deductions.addAll(CreateNewEquation(gangs, angleArcEq));
        }

        if (angleArcEq instanceof GeometricAngleArcEquation)
        {
            // New transitivity? G + A -> A
            for (AlgebraicCongruentArcs acas : algCongArcs)
            {
                deductions.addAll(CreateAngleArcEquationFromArcSubstitution(angleArcEq, acas));
            }

            for (AlgebraicCongruentAngles acas : algCongAngles)
            {
                deductions.addAll(CreateAngleArcEquationFromAngleSubstitution(angleArcEq, acas));
            }

            // New equations? G + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                deductions.addAll(CreateNewEquation(aarcs, angleArcEq));
            }
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                deductions.addAll(CreateNewEquation(aangs, angleArcEq));
            }
            for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
            {
                deductions.addAll(CreateNewEquation(gangs, angleArcEq));
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
                deductions.addAll(MakePurelyAlgebraic(CreateAngleArcEquationFromArcSubstitution(angleArcEq, oldACAS)));
            }

            for (AlgebraicCongruentAngles oldACAS : algCongAngles)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateAngleArcEquationFromAngleSubstitution(angleArcEq, oldACAS)));
            }

            // New equations? A + A -> A
            for (AlgebraicArcEquation aarcs : algArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(aarcs, angleArcEq)));
            }
            for (AlgebraicAngleEquation aangs : algAngleEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(aangs, angleArcEq)));
            }
            for (GeometricAngleArcEquation gangs : geoAngleArcEqs)
            {
                deductions.addAll(MakePurelyAlgebraic(CreateNewEquation(gangs, angleArcEq)));
            }
        }

        return deductions;
    }

    private static ArrayList<Deduction> CreateAngleArcEquationFromArcSubstitution(AngleArcEquation angleArcEq, CongruentArcs congArcs) 
    {
        return CreateEquationFromSubstitution(angleArcEq, congArcs, congArcs.getFirstArc(), congArcs.getSecondArc());
    }

    private static ArrayList<Deduction> CreateAngleArcEquationFromAngleSubstitution(AngleArcEquation angleArcEq, CongruentAngles congAngles)
    {
        return CreateEquationFromSubstitution(angleArcEq, congAngles, congAngles.GetFirstAngle(), congAngles.GetSecondAngle());
    }

    private static ArrayList<Deduction> CreateEquationFromSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause c1, GroundedClause c2)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();
        Deduction newEquationEdge;

        newEquationEdge = PerformEquationSubstitution(eq, subbedEq, c1, c2);
        if (newEquationEdge != null) deductions.add(newEquationEdge);
        newEquationEdge = PerformEquationSubstitution(eq, subbedEq, c2, c1);
        if (newEquationEdge != null) deductions.add(newEquationEdge);

        return deductions;
    }

    //
    // Substitute some clause (subbedEq) into an equation (eq)
    //
    private static Deduction PerformEquationSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause toFind, GroundedClause toSub)
    {
        //Debug.WriteLine("Substituting with " + eq.ToString() + " and " + subbedEq.ToString());

        if (!eq.containsClause(toFind)) return null;

        //
        // Make a deep copy of the equation
        //
        Equation newEq = null;
        if (eq instanceof SegmentEquation)
        {
            newEq = new AlgebraicSegmentEquation(eq.getLHS().deepCopy(), eq.getRHS().deepCopy());
        }
        else if (eq instanceof AngleEquation)
        {
            newEq = new AlgebraicAngleEquation(eq.getLHS().deepCopy(), eq.getRHS().deepCopy());
        }
        else if (eq instanceof ArcEquation)
        {
            newEq = new AlgebraicArcEquation(eq.getLHS().deepCopy(), eq.getRHS().deepCopy());
        }
        else if (eq instanceof AngleArcEquation)
        {
            newEq = new AlgebraicAngleArcEquation(eq.getLHS().deepCopy(), eq.getRHS().deepCopy());
        }

        // Substitute into the copy
        newEq.substitute(toFind, toSub);

        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq);
        antecedent.add(subbedEq);

        //
        // Simplify the equation
        //
        Equation simplified = Simplification.simplify(newEq);

        // Create a congruence relationship if it applies
        GroundedClause newCongruence = HandleCongruence(simplified);
        if (newCongruence != null) return new Deduction(antecedent, newCongruence, ANNOTATION);

        // If a congruence was not established, create a complementary or supplementary relationship, if applicable
        if (simplified instanceof AngleEquation && newCongruence == null)
        {
            GroundedClause newRelation = HandleAngleRelation(simplified);
            if (newRelation != null) return new Deduction(antecedent, newRelation, ANNOTATION);
        }

        return new Deduction(antecedent, simplified, ANNOTATION);
    }

    //
    // This instanceof pure transitivity where A + B = C , A + B = D -> C = D
    //
    private static Deduction PerformNonAtomicEquationSubstitution(Equation eq, GroundedClause subbedEq, GroundedClause toFind, GroundedClause toSub)
    {
        //Debug.WriteLine("Substituting with " + eq.ToString() + " and " + subbedEq.ToString());

        // If there instanceof a deduction relationship between the given congruences, do not perform another substitution
        //  subbedEq.HasPredecessor(eq)
        //if (eq.HasGeneralPredecessor(subbedEq) || subbedEq.HasGeneralPredecessor(eq)) return new Deduction(null, null);

        //
        // Verify that the non-atomic sides to both equations are the exact same
        //
        GroundedClause nonAtomicOriginal = null;
        GroundedClause atomicOriginal = null;
        if (eq.getAtomicity() == Equation.LEFT_ATOMIC)
        {
            nonAtomicOriginal = eq.getRHS();
            atomicOriginal = eq.getLHS();
        }
        else if (eq.getAtomicity() == Equation.RIGHT_ATOMIC)
        {
            nonAtomicOriginal = eq.getLHS();
            atomicOriginal = eq.getRHS();
        }

        // We collect all the flattened terms
        ArrayList<GroundedClause> originalTerms = nonAtomicOriginal.collectTerms();
        ArrayList<GroundedClause> subbedTerms = toFind.collectTerms();

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
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq);
        antecedent.add(subbedEq);
        Deduction newEdge;

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
                ExceptionHandler.throwException(new Exception("Unexpected Problem : Non-atomic substitution (equation)..."));
            }

            newEdge = new Deduction(antecedent, newEquation, ANNOTATION);
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
                ExceptionHandler.throwException(new Exception("Unexpected Problem : Non-atomic substitution (Congruence)..."));
            }

            newEdge = new Deduction(antecedent, newCongruent, ANNOTATION);
        }

        return newEdge;
    }

    //
    // Given two equations, perform a direct, transitive substitution of one equation into the other (and vice versa)
    //
    private static ArrayList<Deduction> PerformEquationTransitiviteSubstitution(Equation eq1, Equation eq2)
    {
        ArrayList<GroundedClause> newRelations = new ArrayList<GroundedClause>();

        //
        // Collect the terms from each side of both equations
        //
        ArrayList<GroundedClause> lhsTermsEq1 = eq1.getLHS().collectTerms();
        ArrayList<GroundedClause> lhsTermsEq2 = eq2.getLHS().collectTerms();
        ArrayList<GroundedClause> rhsTermsEq1 = eq1.getRHS().collectTerms();
        ArrayList<GroundedClause> rhsTermsEq2 = eq2.getRHS().collectTerms();

        int equationType = GetEquationType(eq1);

        //
        // Construct the new equations using all possible combinations
        //
        if (EqualLists(lhsTermsEq1, lhsTermsEq2))
        {
            GroundedClause newEq = ConstructNewEquation(equationType, eq1.getRHS(), eq2.getRHS());
            if (newEq != null) newRelations.add(newEq);
        }
        if (EqualLists(lhsTermsEq1, rhsTermsEq2))
        {
            GroundedClause newEq = ConstructNewEquation(equationType, eq1.getRHS(), eq2.getLHS());
            if (newEq != null) newRelations.add(newEq);
        }
        if (EqualLists(rhsTermsEq1, lhsTermsEq2))
        {
            GroundedClause newEq = ConstructNewEquation(equationType, eq1.getLHS(), eq2.getRHS());
            if (newEq != null) newRelations.add(newEq);
        }
        if (EqualLists(rhsTermsEq1, rhsTermsEq2))
        {
            GroundedClause newEq = ConstructNewEquation(equationType, eq1.getLHS(), eq2.getLHS());
            if (newEq != null) newRelations.add(newEq);
        }

        //
        // Construct the hypergraph edges for all of the new relationships
        //
        ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(eq1);
        antecedent.add(eq2);

        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        for (GroundedClause gc : newRelations)
        {
            deductions.add(new Deduction(antecedent, gc, ANNOTATION));
        }

        return deductions;
    }

    //
    // Given two equations, if one equation instanceof atomic, substitute into the other (and vice versa)
    //
    //private static ArrayList<KeyValuePair<ArrayList<GroundedClause>, GroundedClause>> PerformEquationDirectSubstitution(Equation eq1, Equation eq2)
    //{
    //    //
    //    // Does the new equation have one side which instanceof isolated (an atomic expression)?
    //    //
    //    int atomicSide = newEq.GetAtomicity();

    //    GroundedClause atomicExp = null;
    //    GroundedClause otherSide = null;
    //    switch (atomicSide)
    //    {
    //        case Equation.LEFT_ATOMIC:
    //            atomicExp = newEq.getLHS();
    //            otherSide = newEq.getRHS();
    //            break;

    //        case Equation.RIGHT_ATOMIC:
    //            atomicExp = newEq.getRHS();
    //            otherSide = newEq.getLHS();
    //            break;

    //        case Equation.BOTH_ATOMIC:
    //            // Choose both sides
    //            break;

    //        case Equation.NONE_ATOMIC:
    //            // If neither side of this new equation are atomic, for simplicty,
    //            // we do not perform a substitution
    //            return new ArrayList<Deduction>();
    //    }

    //    KeyValuePair<ArrayList<GroundedClause>, GroundedClause> cl;
    //    // One side of the equation instanceof atomic
    //    if (atomicExp != null)
    //    {
    //        // Check to see if the old equation instanceof dually atomic as
    //        // we will want to substitute the old eq into the new one
    //        int oldAtomic = oldEq.GetAtomicity();
    //        if (oldAtomic != Equation.BOTH_ATOMIC)
    //        {
    //            // Simple sub of new equation into old
    //            cl = PerformEquationSubstitution(oldEq, newEq, atomicExp, otherSide);
    //            if (cl.Value != null) deductions.add(cl);

    //            //
    //            // In the case where we have a situation of the form: A + B = C
    //            //                                                    A + B = D  -> C = D
    //            //
    //            // Perform a non-atomic substitution
    //            cl = PerformNonAtomicEquationSubstitution(oldEq, newEq, otherSide, atomicExp);
    //            if (cl.Value != null) deductions.add(cl);
    //        }
    //        else if (oldAtomic == Equation.BOTH_ATOMIC)
    //        {
    //            // Dual sub of old equation into new
    //            cl = PerformEquationSubstitution(newEq, oldEq, oldEq.getLHS(), oldEq.getRHS());
    //            if (cl.Value != null) deductions.add(cl);
    //            cl = PerformEquationSubstitution(newEq, oldEq, oldEq.getRHS(), oldEq.getLHS());
    //            if (cl.Value != null) deductions.add(cl);
    //        }
    //    }
    //    // The new equation has both sides atomic; try to sub : the other side
    //    else
    //    {
    //        cl = PerformEquationSubstitution(oldEq, newEq, newEq.getLHS(), newEq.getRHS());
    //        if (cl.Value != null) deductions.add(cl);
    //        cl = PerformEquationSubstitution(oldEq, newEq, newEq.getRHS(), newEq.getLHS());
    //        if (cl.Value != null) deductions.add(cl);
    //    }

    //    return deductions;
    //}

    //
    // If both sides of the substituted equation are atomic and not Numeric Values, create a congruence relationship instead.
    //
    private static GroundedClause HandleCongruence(Equation simplified)
    {
        // Both sides must be atomic and multiplied by a factor of 1 for a proper congruence
        if (simplified.getAtomicity() != Equation.BOTH_ATOMIC) return null;

        if (!simplified.IsProperCongruence()) return null;

        // Then create a congruence, whether it be angle or segment
        Congruent newCongruent = null;
        if (simplified instanceof AlgebraicAngleEquation)
        {
            // Do not generate for lines; that is, 180^o angles
            //if (((Angle)simplified.getLHS()).IsStraightAngle() && ((Angle)simplified.getLHS()).IsStraightAngle()) return null;
            newCongruent = new AlgebraicCongruentAngles((Angle)simplified.getLHS(), (Angle)simplified.getRHS());
        }
        else if (simplified instanceof AlgebraicSegmentEquation)
        {
            newCongruent = new AlgebraicCongruentSegments((Segment)simplified.getLHS(), (Segment)simplified.getRHS());
        }
        else if (simplified instanceof AlgebraicArcEquation)
        {
            newCongruent = new AlgebraicCongruentArcs((Arc)simplified.getLHS(), (Arc)simplified.getRHS());
        }

        // There instanceof no need to simplify a congruence, so just return
        return newCongruent;
    }

    //
    // For generic generation of transitive congruent clauses
    //
    private static ArrayList<Deduction> CreateCongruent<T>(T css, T geoCong, int numSharedExps)
    {
        {
            ArrayList<Deduction> deductions = new ArrayList<Deduction>();

            //if (css.HasRelationPredecessor(geoCongSeg) || geoCongSeg.HasRelationPredecessor(css)) return deductions;

            switch (numSharedExps)
            {
                case 0:
                    // Nothing instanceof shared: do nothing
                    break;

                case 1:
                    // Expected case to create a new congruence relationship
                    return Congruent.CreateTransitiveCongruence(css, geoCong);

                case 2:
                    // This instanceof either reflexive or the exact same congruence relationship (which shouldn't happen)
                    break;

                default:
                    throw new Exception("Congruent Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
            }

            return deductions;
        }
    }

    private static int GetEquationType(Equation eq)
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
    private static GroundedClause ConstructNewEquation(int equationType, GroundedClause left, GroundedClause right)
    {
        //
        //Account for possibility that substitution may require a change : equation type between Angle, Arc, and AngleArc
        //
        if (equationType != SEGMENT_EQUATION)
        {
            ArrayList<GroundedClause> terms = new ArrayList<GroundedClause>();
            terms.addAll(left.collectTerms());
            terms.addAll(right.collectTerms());
            Boolean hasAngle = terms.Any(c => c instanceof Angle);
            Boolean hasArc = terms.Any(c => c instanceof Arc);
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
            Arc arc1 = (left instanceof Arc) ? (Arc)left : null;
            Arc arc2 = (right instanceof Arc) ? (Arc)right : null;
            if (arc1 != null && arc2 != null && !Utilities.CompareValues(arc1.getCircle().getRadius(), arc2.getCircle().getRadius())) return null;

            newEq = new AlgebraicArcEquation(left, right);
        }
        else if (equationType == ANGLE_ARC_EQUATION)
        {
            newEq = new AlgebraicAngleArcEquation(left, right);
        }

        //
        // Simplify the equation
        //
        Equation simplified = Simplification.simplify(newEq);
        if (simplified == null) return null;
        
        //
        // Create a congruence relationship if it applies
        //
        GroundedClause newCongruence = HandleCongruence(simplified);
        if (newCongruence != null) return newCongruence;

        //
        // If a congruence was not established, create a complementary or supplementary relationship, if applicable
        //
        if (equationType == ANGLE_EQUATION)
        {
            GroundedClause newRelation = HandleAngleRelation(simplified);
            if (newRelation != null) return newRelation;
        }

        // Just return the simplified equation if nothing else could be deduced
        return simplified;
    }

    //
    // Given an old and new set of angle measure equations substitute if possible.
    //
    private static ArrayList<Deduction> CreateNewEquation(Equation oldEq, Equation newEq)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        //Debug.WriteLine("Considering combining: " + oldEq + " + " + newEq);

        // Avoid redundant equation generation
        //            if (oldEq.HasRelationPredecessor(newEq) || newEq.HasRelationPredecessor(oldEq)) return deductions;

        // Determine if there instanceof a direct, transitive relationship with the equations
        deductions.addAll(PerformEquationTransitiviteSubstitution(oldEq, newEq));

        // If we have an atomic situation, substitute into the other equation
        //deductions.addAll(PerformEquationDirectSubstitution(oldEq, newEq));

        return deductions;
    }

    //
    // add predecessors to the equations, congruence relationships, etc.
    //
    private static void MarkPredecessors(ArrayList<Deduction> edges)
    {
        for (Deduction edge : edges)
        {
            for (GroundedClause predNode : edge.getAntecedent())
            {
                edge.getConsequent().addRelationPredecessor(predNode);
                edge.getConsequent().addRelationPredecessors(predNode.getRelationPredecessors());
            }
        }
    }

    // Sets all of the deduced nodes to be purely algebraic: A + A -> A
    private static ArrayList<Deduction> MakePurelyAlgebraic(ArrayList<Deduction> edges)
    {
        for (Deduction edge : edges)
        {
            edge.getConsequent().makePurelyAlgebraic();
        }

        return edges;
    }

    //
    // Check equivalence of lists by verifying dual containment.
    //
    private static Boolean EqualLists(ArrayList<GroundedClause> list1, ArrayList<GroundedClause> list2)
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
            geoCongSegments.add((GeometricCongruentSegments)c);
        }
        else if (c instanceof GeometricSegmentEquation)
        {
            geoSegmentEqs.add((GeometricSegmentEquation)c);
        }
        else if (c instanceof GeometricCongruentAngles)
        {
            geoCongAngles.add((GeometricCongruentAngles)c);
        }
        else if (c instanceof GeometricAngleEquation)
        {
            geoAngleEqs.add((GeometricAngleEquation)c);
        }
        else if (c instanceof GeometricCongruentArcs)
        {
            geoCongArcs.add((GeometricCongruentArcs)c);
        }
        else if (c instanceof GeometricArcEquation)
        {
            geoArcEqs.add((GeometricArcEquation)c);
        }
        else if (c instanceof GeometricAngleArcEquation)
        {
            geoAngleArcEqs.add((GeometricAngleArcEquation)c);
        }
        else if (c instanceof AlgebraicSegmentEquation)
        {
            algSegmentEqs.add((AlgebraicSegmentEquation)c);
        }
        else if (c instanceof AlgebraicAngleEquation)
        {
            algAngleEqs.add((AlgebraicAngleEquation)c);
        }
        else if (c instanceof AlgebraicArcEquation)
        {
            algArcEqs.add((AlgebraicArcEquation)c);
        }
        else if (c instanceof AlgebraicAngleArcEquation)
        {
            algAngleArcEqs.add((AlgebraicAngleArcEquation)c);
        }
        else if (c instanceof AlgebraicCongruentSegments)
        {
            algCongSegments.add((AlgebraicCongruentSegments)c);
        }
        else if (c instanceof AlgebraicCongruentAngles)
        {
            algCongAngles.add((AlgebraicCongruentAngles)c);
        }
        else if (c instanceof AlgebraicCongruentArcs)
        {
            algCongArcs.add((AlgebraicCongruentArcs)c);
        }
        else if (c instanceof SegmentRatio)
        {
            propSegs.add((SegmentRatio)c);
        }
        //else if (c instanceof AlgebraicSegmentRatioEquation)
        //{
        //    algPropSegs.add(c as AlgebraicSegmentRatioEquation);
        //}
        else if (c instanceof GeometricProportionalAngles)
        {
            geoPropAngs.add((GeometricProportionalAngles)c);
        }
        else if (c instanceof AlgebraicProportionalAngles)
        {
            algPropAngs.add((AlgebraicProportionalAngles)c);
        }
    }

    //
    // add the new grounded clause to the correct list.
    //
    private static Boolean ClauseHasBeenDeduced(GroundedClause c)
    {
        if (c instanceof GeometricCongruentSegments)
        {
            return geoCongSegments.contains((GeometricCongruentSegments)c);
        }
        else if (c instanceof GeometricSegmentEquation)
        {
            return geoSegmentEqs.contains((GeometricSegmentEquation)c);
        }
        else if (c instanceof GeometricCongruentAngles)
        {
            return geoCongAngles.contains((GeometricCongruentAngles)c);
        }
        else if (c instanceof GeometricAngleEquation)
        {
            return geoAngleEqs.contains((GeometricAngleEquation)c);
        }
        else if (c instanceof GeometricCongruentArcs)
        {
            return geoCongArcs.contains((GeometricCongruentArcs)c);
        }
        else if (c instanceof GeometricArcEquation)
        {
            return geoArcEqs.contains((GeometricArcEquation)c);
        }
        else if (c instanceof GeometricAngleArcEquation)
        {
            return geoAngleArcEqs.contains((GeometricAngleArcEquation)c);
        }
        else if (c instanceof AlgebraicSegmentEquation)
        {
            return algSegmentEqs.contains((AlgebraicSegmentEquation)c);
        }
        else if (c instanceof AlgebraicAngleEquation)
        {
            return algAngleEqs.contains((AlgebraicAngleEquation)c);
        }
        else if (c instanceof AlgebraicArcEquation)
        {
            return algArcEqs.contains((AlgebraicArcEquation)c);
        }
        else if (c instanceof AlgebraicAngleArcEquation)
        {
            return algAngleArcEqs.contains((AlgebraicAngleArcEquation)c);
        }
        else if (c instanceof AlgebraicCongruentSegments)
        {
            return algCongSegments.contains((AlgebraicCongruentSegments)c);
        }
        else if (c instanceof AlgebraicCongruentAngles)
        {
            return algCongAngles.contains((AlgebraicCongruentAngles)c);
        }
        else if (c instanceof AlgebraicCongruentArcs)
        {
            return algCongArcs.contains((AlgebraicCongruentArcs)c);
        }
        else if (c instanceof GeometricSegmentRatioEquation)
        {
            return propSegs.contains((SegmentRatio)c);
        }
        //else if (c instanceof AlgebraicSegmentRatioEquation)
        //{
        //    return algPropSegs.Contains(c as AlgebraicSegmentRatioEquation);
        //}
        else if (c instanceof GeometricProportionalAngles)
        {
            return geoPropAngs.contains((GeometricProportionalAngles)c);
        }
        else if (c instanceof AlgebraicProportionalAngles)
        {
            return algPropAngs.contains((AlgebraicProportionalAngles)c);
        }

        return false;
    }


}