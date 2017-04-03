package backend.factComputer;

import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory.JustificationSwitch.DeductionJustType;
import backend.deductiveRules.angles.axioms.AngleAdditionAxiom;
import backend.deductiveRules.angles.axioms.AnglesOfEqualMeasureAreCongruent;
import backend.deductiveRules.angles.axioms.CongruentCorrespondingAnglesImplyParallel;
import backend.deductiveRules.angles.axioms.CorrespondingAnglesOfParallelLines;
import backend.deductiveRules.angles.definitions.AngleBisectorDefinition;
import backend.deductiveRules.angles.definitions.ComplementaryDefinition;
import backend.deductiveRules.angles.definitions.RightAngleDefinition;
import backend.deductiveRules.angles.definitions.StraightAngleDefinition;
import backend.deductiveRules.angles.definitions.SupplementaryDefinition;
import backend.deductiveRules.angles.theorems.AdjacentAnglesPerpendicularImplyComplementary;
import backend.deductiveRules.angles.theorems.AngleBisectorTheorem;
import backend.deductiveRules.angles.theorems.CongruentAdjacentAnglesImplyPerpendicular;
import backend.deductiveRules.angles.theorems.PerpendicularImplyCongruentAdjacentAngles;
import backend.deductiveRules.angles.theorems.RelationsOfCongruentAnglesAreCongruent;
import backend.deductiveRules.angles.theorems.SupplementaryAndCongruentImplyRightAngles;
import backend.deductiveRules.angles.theorems.SupplementaryAnglesParallelIntersection;
import backend.deductiveRules.circles.definitions.CircleDefinition;
import backend.deductiveRules.circles.definitions.CongruentCircleDefinition;
import backend.deductiveRules.parallelLines.theorems.AltIntCongruentAnglesImplyParallel;
import backend.deductiveRules.parallelLines.theorems.ParallelImplyAltIntCongruentAngles;
import backend.deductiveRules.parallelLines.theorems.ParallelImplySameSideInteriorSupplementary;
import backend.deductiveRules.parallelLines.theorems.SameSideSuppleAnglesImplyParallel;
import backend.deductiveRules.parallelLines.theorems.TransversalPerpendicularToParallelImplyBothPerpendicular;
import backend.deductiveRules.quadrilaterals.definitions.IsoscelesTrapezoidDefinition;
import backend.deductiveRules.quadrilaterals.definitions.KiteDefinition;
import backend.deductiveRules.quadrilaterals.definitions.ParallelogramDefinition;
import backend.deductiveRules.quadrilaterals.definitions.RectangleDefinition;
import backend.deductiveRules.quadrilaterals.definitions.RhombusDefinition;
import backend.deductiveRules.quadrilaterals.definitions.SquareDefinition;
import backend.deductiveRules.quadrilaterals.definitions.TrapezoidDefinition;
import backend.deductiveRules.quadrilaterals.theorems.BaseAnglesIsoscelesTrapezoidCongruent;
import backend.deductiveRules.quadrilaterals.theorems.BothPairsOppAnglesCongruentImpliesParallelogram;
import backend.deductiveRules.quadrilaterals.theorems.BothPairsOppSidesCongruentImpliesParallelogram;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsBisectEachOtherImplyParallelogram;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsOfKiteArePerpendicular;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsOfRectangleAreCongruent;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsOfRhombusArePerpendicular;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsOfRhombusBisectRhombusAngles;
import backend.deductiveRules.quadrilaterals.theorems.DiagonalsParallelogramBisectEachOther;
import backend.deductiveRules.quadrilaterals.theorems.MedianTrapezoidHalfSumBases;
import backend.deductiveRules.quadrilaterals.theorems.MedianTrapezoidParallelToBases;
import backend.deductiveRules.quadrilaterals.theorems.OnePairOppSidesCongruentParallelImpliesParallelogram;
import backend.deductiveRules.quadrilaterals.theorems.OppositeAnglesOfParallelogramAreCongruent;
import backend.deductiveRules.quadrilaterals.theorems.OppositeSidesOfParallelogramAreCongruent;
import backend.deductiveRules.segments.axioms.CongruentSegmentsImplySegmentRatioDefinition;
import backend.deductiveRules.segments.axioms.SegmentAdditionAxiom;
import backend.deductiveRules.segments.definitions.MidpointDefinition;
import backend.deductiveRules.segments.definitions.PerpendicularBisectorDefinition;
import backend.deductiveRules.segments.definitions.PerpendicularDefinition;
import backend.deductiveRules.segments.definitions.PerpendicularSegments;
import backend.deductiveRules.segments.definitions.SegmentBisectorDefinition;
import backend.deductiveRules.segments.theorems.MidpointTheorem;
import backend.deductiveRules.segments.theorems.ParallelSegmentsTransitivity;
import backend.deductiveRules.segments.theorems.PerpendicularParallelTransversal;
import backend.deductiveRules.segments.theorems.VerticalAnglesTheorem;
import backend.deductiveRules.triangles.axioms.AASimilarity;
import backend.deductiveRules.triangles.axioms.ASA;
import backend.deductiveRules.triangles.axioms.SASCongruence;
import backend.deductiveRules.triangles.axioms.SSS;
import backend.deductiveRules.triangles.definitions.AltitudeDefinition;
import backend.deductiveRules.triangles.definitions.CoordinateRightIsoscelesTriangles;
import backend.deductiveRules.triangles.definitions.EquilateralTriangleDefinition;
import backend.deductiveRules.triangles.definitions.IsoscelesTriangleDefinition;
import backend.deductiveRules.triangles.definitions.MedianDefinition;
import backend.deductiveRules.triangles.definitions.RightTriangleDefinition;
import backend.deductiveRules.triangles.theorems.AAS;
import backend.deductiveRules.triangles.theorems.AcuteAnglesInRightTriangleComplementary;
import backend.deductiveRules.triangles.theorems.AltitudeOfRightTrianglesImpliesSimilar;
import backend.deductiveRules.triangles.theorems.AngleBisectorIsPerpendicularBisectorInIsosceles;
import backend.deductiveRules.triangles.theorems.CongruentAnglesInTriangleImplyCongruentSides;
import backend.deductiveRules.triangles.theorems.CongruentSidesInTriangleImplyCongruentAngles;
import backend.deductiveRules.triangles.theorems.EquilateralTriangleHasSixtyDegreeAngles;
import backend.deductiveRules.triangles.theorems.ExteriorAngleEqualSumRemoteAngles;
import backend.deductiveRules.triangles.theorems.HypotenuseLeg;
import backend.deductiveRules.triangles.theorems.IsoscelesTriangleTheorem;
import backend.deductiveRules.triangles.theorems.SASSimilarity;
import backend.deductiveRules.triangles.theorems.SSSSimilarity;
import backend.deductiveRules.triangles.theorems.SumAnglesInTriangle;
import backend.deductiveRules.triangles.theorems.TriangleProportionality;
import backend.deductiveRules.triangles.theorems.TwoPairsCongruentAnglesImplyThirdPairCongruent;
import backend.hypergraph.Annotation;
import backend.hypergraph.DeductionFlags;
import backend.hypergraph.QueryableHypergraph;

public class FactConnector
{
    
    private QueryableHypergraph<GroundedClause, Annotation> _qhg;
    private Set<Deduction> deductions;
    public Set<Deduction> getDeductions() { return deductions; }
    
    public String toString()
    {
        String output = "Deductions: " + deductions.size() + " [\n";
        
        for (Deduction d : deductions)
        {
            output += d + "\n";
        }
        
        output += "]";
        
        return output;
    }

    public FactConnector(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        // instantiations
        _qhg = qhg;
        deductions = new HashSet<Deduction>();
        
        // get deducitons
        deduce();
    }
    
    /**
     * Deduce all Theorems, Axioms, and Definitions to add all edges to the 
     * QueryableHypergraph
     * 
     * @author Drew Whitmire
     */
    public void deduce()
    {
        //
        // NOTE: DeductionFlags.dFlags is active when false and inactive when true
        // _qhg is because the default value of a Boolean is false
        //

        //
        // ALGEBRA
        //
        // none of these are translated/translated correctly.  Will come back to these
        // @Drew


        // 
        // ANGLES
        //
        // Axioms
        if (!DeductionFlags.dFlags[DeductionJustType.ANGLE_ADDITION_AXIOM.ordinal()])
        {
            AngleAdditionAxiom angleAdditionAxiom = new AngleAdditionAxiom((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(angleAdditionAxiom.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ANGLES_OF_EQUAL_MEASUREARE_CONGRUENT.ordinal()]) 
        {
            AnglesOfEqualMeasureAreCongruent anglesOfEqualMeasureAreCongruent = new AnglesOfEqualMeasureAreCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(anglesOfEqualMeasureAreCongruent.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_CORRESPONDING_ANGLES_IMPLY_PARALLEL.ordinal()]) 
        {
            CongruentCorrespondingAnglesImplyParallel congruentCorrespondingAnglesImplyParallel = new CongruentCorrespondingAnglesImplyParallel((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentCorrespondingAnglesImplyParallel.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CORRESPONDING_ANGLES_OF_PARALLEL_LINES.ordinal()]) 
        {
            CorrespondingAnglesOfParallelLines correspondingAnglesOFParallelLines = new CorrespondingAnglesOfParallelLines((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(correspondingAnglesOFParallelLines.deduce());
        }

        // Definitions
        if (!DeductionFlags.dFlags[DeductionJustType.ANGLE_BISECTOR_DEFINITION.ordinal()]) 
        {
            AngleBisectorDefinition angleBisectorDefinition = new AngleBisectorDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(angleBisectorDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.COMPLEMENTARY_DEFINITION.ordinal()]) 
        {
            ComplementaryDefinition complementaryDefinition = new ComplementaryDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(complementaryDefinition.deduce());
        }

//        if (!DeductionFlags.dFlags[DeductionJustType.PERPENDICULAR_BISECTOR_DEFINITION.ordinal()]) 
//        {
//            PerpendicularBisectorDefinition perpendicularBisectorDefinition = new PerpendicularBisectorDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
//            deductions.addAll(perpendicularBisectorDefinition.deduce());
//        }

        if (!DeductionFlags.dFlags[DeductionJustType.RIGHT_ANGLE_DEFINITION.ordinal()]) 
        {
            RightAngleDefinition rightAngleDefinition = new RightAngleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(rightAngleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.STRAIGHT_ANGLE_DEFINITION.ordinal()]) 
        {
            StraightAngleDefinition straightAngleDefinition = new StraightAngleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(straightAngleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SUPPLEMENTARY_DEFINITION.ordinal()]) 
        {
            SupplementaryDefinition supplementaryDefinition = new SupplementaryDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(supplementaryDefinition.deduce());
        }

        // Theorems
        if (!DeductionFlags.dFlags[DeductionJustType.ADJACENT_ANGLES_PERPENDICULAR_IMPLY_COMPLEMENTARY.ordinal()]) 
        {
            AdjacentAnglesPerpendicularImplyComplementary adjacentAnglesPerpendicularImplyComplementary = 
                    new AdjacentAnglesPerpendicularImplyComplementary((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(adjacentAnglesPerpendicularImplyComplementary.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ANGLE_BISECTOR_THEOREM.ordinal()]) 
        {
            AngleBisectorTheorem angleBisectorTheorem = new AngleBisectorTheorem((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(angleBisectorTheorem.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_ADJACENT_ANGLES_IMPLY_PERPENDICULAR.ordinal()]) 
        {
            CongruentAdjacentAnglesImplyPerpendicular congruentAdjacentAnglesImplyPerpendicular = new CongruentAdjacentAnglesImplyPerpendicular((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentAdjacentAnglesImplyPerpendicular.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PERPENDICULAR_IMPLY_CONGRUENT_ADJACENT_ANGLES.ordinal()]) 
        {
            PerpendicularImplyCongruentAdjacentAngles perpendicularImplyCongruentAdjacentAngles = new PerpendicularImplyCongruentAdjacentAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(perpendicularImplyCongruentAdjacentAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.RELATIONS_OF_CONGRUENT_ANGLES_ARE_CONGRUENT.ordinal()]) 
        {
            RelationsOfCongruentAnglesAreCongruent relationsOfCongruentAnglesAreCongruent = new RelationsOfCongruentAnglesAreCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(relationsOfCongruentAnglesAreCongruent.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_SUPPLEMENTARY_ANGLES_IMPLY_RIGHT_ANGLES.ordinal()]) 
        {
            SupplementaryAndCongruentImplyRightAngles supplementaryAndCongruentImplyRightAngles = new SupplementaryAndCongruentImplyRightAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(supplementaryAndCongruentImplyRightAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SUPPLEMENTARY_ANGLES_PARALLEL_INTERSECTION.ordinal()]) 
        {
            SupplementaryAnglesParallelIntersection supplementaryAnglesParallelIntersection = new SupplementaryAnglesParallelIntersection((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(supplementaryAnglesParallelIntersection.deduce());
        }

        //
        // CIRCLES
        //
        // Definitions
        if (!DeductionFlags.dFlags[DeductionJustType.CIRCLE_DEFINITION.ordinal()]) 
        {
            CircleDefinition circleDefinition = new CircleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(circleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CIRCLE_CONGRUENCE_DEFINITION.ordinal()]) 
        {
            CongruentCircleDefinition congruentCircleDefinition = new CongruentCircleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentCircleDefinition.deduce());
        }

        //
        // PARALLEL LINES
        //
        // Theorems
        if (!DeductionFlags.dFlags[DeductionJustType.ALT_INT_CONGRUENT_ANGLES_IMPLY_PARALLEL.ordinal()]) 
        {
            AltIntCongruentAnglesImplyParallel altIntCongruentAnglesImplyParallel = new AltIntCongruentAnglesImplyParallel((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(altIntCongruentAnglesImplyParallel.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PARALLEL_IMPLY_ALT_INT_CONGRUENT_ANGLES.ordinal()]) 
        {
            ParallelImplyAltIntCongruentAngles parallelImplyAltIntCongruentAngles = new ParallelImplyAltIntCongruentAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(parallelImplyAltIntCongruentAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PARALLEL_IMPLY_SAME_SIDE_INTERIOR_SUPPLEMENTARY.ordinal()]) 
        {
            ParallelImplySameSideInteriorSupplementary parallelImplySameSideInteriorSupplementary = 
                    new ParallelImplySameSideInteriorSupplementary((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(parallelImplySameSideInteriorSupplementary.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SAME_SIDE_SUPPLE_ANGLES_IMPLY_PARALLEL.ordinal()]) 
        {
            SameSideSuppleAnglesImplyParallel sameSideSuppleAnglesImplyParallel = new SameSideSuppleAnglesImplyParallel((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sameSideSuppleAnglesImplyParallel.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR.ordinal()]) 
        {
            TransversalPerpendicularToParallelImplyBothPerpendicular transversalPerpendicularToParallelImplyBothPerpendicular = 
                    new TransversalPerpendicularToParallelImplyBothPerpendicular((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(transversalPerpendicularToParallelImplyBothPerpendicular.deduce());
        }

        //
        // QUADRILATERALS
        //
        // Definitions
        if (!DeductionFlags.dFlags[DeductionJustType.ISOSCELES_TRAPEZOID_DEFINITION.ordinal()]) 
        {
            IsoscelesTrapezoidDefinition isoscelesTrapezoidDefinition = new IsoscelesTrapezoidDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(isoscelesTrapezoidDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.KITE_DEFINITION.ordinal()]) 
        {
            KiteDefinition kiteDefinition = new KiteDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(kiteDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PARALLELOGRAM_DEFINITION.ordinal()]) 
        {
            ParallelogramDefinition parallelogramDefinition = new ParallelogramDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(parallelogramDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.RECTANGLE_DEFINITION.ordinal()]) 
        {
            RectangleDefinition rectangleDefinition = new RectangleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(rectangleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.RHOMBUS_DEFINITION.ordinal()]) 
        {
            RhombusDefinition rhombusDefinition = new RhombusDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(rhombusDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SQUARE_DEFINITION.ordinal()]) 
        {
            SquareDefinition squareDefinition = new SquareDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(squareDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.TRAPEZOID_DEFINITION.ordinal()]) 
        {
            TrapezoidDefinition trepezoidDefinition = new TrapezoidDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(trepezoidDefinition.deduce());
        }

        // Theorems
        if (!DeductionFlags.dFlags[DeductionJustType.BASE_ANGLES_OF_ISOSCELES_TRAPEZOID_CONGRUENT.ordinal()]) 
        {
            BaseAnglesIsoscelesTrapezoidCongruent baseAnglesIsosclesTrapezoidCongruent = 
                    new BaseAnglesIsoscelesTrapezoidCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(baseAnglesIsosclesTrapezoidCongruent.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.OPPOSITE_ANGLES_CONGRUENT_IMPLIES_PARALLELOGRAM.ordinal()]) 
        {
            BothPairsOppAnglesCongruentImpliesParallelogram BothPairsOppAnglesCongruentImpliesParallelogram = 
                    new BothPairsOppAnglesCongruentImpliesParallelogram((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(BothPairsOppAnglesCongruentImpliesParallelogram.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.OPPOSITE_SIDES_CONGRUENT_IMPLIES_PARALLELOGRAM.ordinal()]) 
        {
            BothPairsOppSidesCongruentImpliesParallelogram BothPairsOppSidesCongruentImpliesParallelogram = 
                    new BothPairsOppSidesCongruentImpliesParallelogram((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(BothPairsOppSidesCongruentImpliesParallelogram.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_BISECT_EACH_OTHER_IMPLY_PARALLELOGRAM.ordinal()]) 
        {
            DiagonalsBisectEachOtherImplyParallelogram diagonalsBisectEachOtherImplyParallelogram = 
                    new DiagonalsBisectEachOtherImplyParallelogram((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsBisectEachOtherImplyParallelogram.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_OF_KITE_ARE_PERPENDICULAR.ordinal()]) 
        {
            DiagonalsOfKiteArePerpendicular diagonalsOfKiteArePerpendicular = new DiagonalsOfKiteArePerpendicular((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsOfKiteArePerpendicular.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_OF_RECTANGLE_ARE_CONGRUENT.ordinal()]) 
        {
            DiagonalsOfRectangleAreCongruent diagonalsOfRectangleAreCongruent = new DiagonalsOfRectangleAreCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsOfRectangleAreCongruent.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_OF_RHOMBUS_ARE_PERPENDICULAR.ordinal()]) 
        {
            DiagonalsOfRhombusArePerpendicular diagonalsOfRhombusArePerpendicular = new DiagonalsOfRhombusArePerpendicular((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsOfRhombusArePerpendicular.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_OF_RHOMBUS_BISECT_ANGLES_OF_RHOMBUS.ordinal()]) 
        {
            DiagonalsOfRhombusBisectRhombusAngles diagonalsOfRhombusBisectRhombusAngles = 
                    new DiagonalsOfRhombusBisectRhombusAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsOfRhombusBisectRhombusAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.DIAGONALS_PARALLELOGRAM_BISECT_EACH_OTHER.ordinal()]) 
        {
            DiagonalsParallelogramBisectEachOther diagonalsParallelogramBisectEachOther = 
                    new DiagonalsParallelogramBisectEachOther((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(diagonalsParallelogramBisectEachOther.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.MEDIAN_TRAPEZOID_LENGTH_HALF_SUM_BASES.ordinal()]) 
        {
            MedianTrapezoidHalfSumBases medianTrapezoidHalfSumBases = new MedianTrapezoidHalfSumBases((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(medianTrapezoidHalfSumBases.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.MEDIAN_TRAPEZOID_PARALLEL_TO_BASE.ordinal()]) 
        {
            MedianTrapezoidParallelToBases medianTrapezoidParallelToBases = new MedianTrapezoidParallelToBases((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(medianTrapezoidParallelToBases.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ONE_PAIR_OPPOSITE_SIDES_CONGRUENT_PARALLEL_IMPLIES_PARALLELOGRAM.ordinal()]) 
        {
            OnePairOppSidesCongruentParallelImpliesParallelogram onePairOppSidesCongruentParallelImpliesParallelogram = 
                    new OnePairOppSidesCongruentParallelImpliesParallelogram((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(onePairOppSidesCongruentParallelImpliesParallelogram.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.OPPOSITE_ANGLES_PARALLELOGRAM_ARE_CONGRUENT.ordinal()]) 
        {
            OppositeAnglesOfParallelogramAreCongruent oppositeAnglesOfParallelogramAreCongruent = 
                    new OppositeAnglesOfParallelogramAreCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(oppositeAnglesOfParallelogramAreCongruent.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.OPPOSITE_SIDES_PARALLELOGRAM_ARE_CONGRUENT.ordinal()]) 
        {
            OppositeSidesOfParallelogramAreCongruent oppositeSidesOfParallelogramAreCongruent = 
                    new OppositeSidesOfParallelogramAreCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(oppositeSidesOfParallelogramAreCongruent.deduce());
        }

        //
        // SEGMENTS
        //
        // Axioms
        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_SEGMENTS_IMPLY_PROPORTIONAL_SEGMENTS_DEFINITION.ordinal()]) 
        {
            CongruentSegmentsImplySegmentRatioDefinition congruentSegmentsImplySegmentRatioDefinition = 
                    new CongruentSegmentsImplySegmentRatioDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentSegmentsImplySegmentRatioDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SEGMENT_ADDITION_AXIOM.ordinal()]) 
        {
            SegmentAdditionAxiom segmentAdditionAxiom = new SegmentAdditionAxiom((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(segmentAdditionAxiom.deduce());
        }

        // Definitions
        if (!DeductionFlags.dFlags[DeductionJustType.MIDPOINT_DEFINITION.ordinal()]) 
        {
            MidpointDefinition midpointDefinition = new MidpointDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(midpointDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PERPENDICULAR_BISECTOR_DEFINITION.ordinal()]) 
        {
            PerpendicularBisectorDefinition perpendicularBisectorDefinition1 = new PerpendicularBisectorDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(perpendicularBisectorDefinition1.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PERPENDICULAR_DEFINITION.ordinal()]) 
        {
            PerpendicularDefinition perpendicularDefinition = new PerpendicularDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(perpendicularDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PERPENDICULAR_SEGMENTS.ordinal()]) 
        {
            PerpendicularSegments perpendicularSegments = new PerpendicularSegments((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(perpendicularSegments.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SEGMENT_BISECTOR_DEFINITION.ordinal()]) 
        {
            SegmentBisectorDefinition segmentBisectorDefinition = new SegmentBisectorDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(segmentBisectorDefinition.deduce());
        }

        // Theorems
        if (!DeductionFlags.dFlags[DeductionJustType.MIDPOINT_THEOREM.ordinal()]) 
        {
            MidpointTheorem midpointTheorem = new MidpointTheorem((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(midpointTheorem.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.PARALLEL_SEGMENTS_TRANSITIVITY.ordinal()]) 
        {
            ParallelSegmentsTransitivity parallelSegmentsTransitivity = new ParallelSegmentsTransitivity((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(parallelSegmentsTransitivity.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR.ordinal()]) 
        {
            PerpendicularParallelTransversal perpendicularParallelTransversal = new PerpendicularParallelTransversal((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(perpendicularParallelTransversal.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.VERTICAL_ANGLES.ordinal()]) 
        {
            VerticalAnglesTheorem VerticalAnglesTheorem = new VerticalAnglesTheorem((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(VerticalAnglesTheorem.deduce());
        }

        //
        // TRIANGLES
        //
        // Axioms
        if (!DeductionFlags.dFlags[DeductionJustType.AA_SIMILARITY.ordinal()]) 
        {
            AASimilarity aASimilarity = new AASimilarity((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(aASimilarity.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ASA.ordinal()]) 
        {
            ASA aSA = new ASA((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(aSA.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SAS_CONGRUENCE.ordinal()]) 
        {
            SASCongruence sASCongruence = new SASCongruence((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sASCongruence.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SSS.ordinal()]) 
        {
            SSS sSS = new SSS((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sSS.deduce());
        }

        // Definitions
        if (!DeductionFlags.dFlags[DeductionJustType.ALTITUDE_DEFINITION.ordinal()]) 
        {
            AltitudeDefinition altitudeDefinition = new AltitudeDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(altitudeDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.RIGHT_TRIANGLE_DEFINITION.ordinal()]) 
        {
            CoordinateRightIsoscelesTriangles coordinateRightIsoscelesTriangle = new CoordinateRightIsoscelesTriangles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(coordinateRightIsoscelesTriangle.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.EQUILATERAL_TRIANGLE_DEFINITION.ordinal()]) 
        {
            EquilateralTriangleDefinition equilateralTriangleDefinition = new EquilateralTriangleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(equilateralTriangleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ISOSCELES_TRIANGLE_DEFINITION.ordinal()]) 
        {
            IsoscelesTriangleDefinition isoscelesTriangleDefinition = new IsoscelesTriangleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(isoscelesTriangleDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.MEDIAN_DEFINITION.ordinal()]) 
        {
            MedianDefinition medianDefinition = new MedianDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(medianDefinition.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.RIGHT_TRIANGLE_DEFINITION.ordinal()]) 
        {
            RightTriangleDefinition rightTriangleDefinition = new RightTriangleDefinition((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(rightTriangleDefinition.deduce());
        }

        // Theorems
        if (!DeductionFlags.dFlags[DeductionJustType.AAS.ordinal()]) 
        {
            AAS aAS = new AAS((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(aAS.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ACUTE_ANGLES_IN_RIGHT_TRIANGLE_ARE_COMPLEMENTARY.ordinal()]) 
        {
            AcuteAnglesInRightTriangleComplementary acuteAnglesInRightTriangleComplementary = 
                    new AcuteAnglesInRightTriangleComplementary((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(acuteAnglesInRightTriangleComplementary.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ALTITUDE_OF_RIGHT_TRIANGLES_IMPLIES_SIMILAR.ordinal()]) 
        {
            AltitudeOfRightTrianglesImpliesSimilar altitudeOfRightTrianglesImpliesSimilar = 
                    new AltitudeOfRightTrianglesImpliesSimilar((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(altitudeOfRightTrianglesImpliesSimilar.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ANGLE_BISECTOR_IS_PERPENDICULAR_BISECTOR_IN_ISOSCELES.ordinal()]) 
        {
            AngleBisectorIsPerpendicularBisectorInIsosceles angleBisectorIsPerpendicularBisectorInIsosceles = 
                    new AngleBisectorIsPerpendicularBisectorInIsosceles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(angleBisectorIsPerpendicularBisectorInIsosceles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_ANGLES_IN_TRIANGLE_IMPLY_CONGRUENT_SIDES.ordinal()])
        {
            CongruentAnglesInTriangleImplyCongruentSides congruentAnglesInTriangleImplyCongruentSides = 
                    new CongruentAnglesInTriangleImplyCongruentSides((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentAnglesInTriangleImplyCongruentSides.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.CONGRUENT_SIDES_IN_TRIANGLE_IMPLY_CONGRUENT_ANGLES.ordinal()])
        {
            CongruentSidesInTriangleImplyCongruentAngles congruentSidesInTriangleImplyCongruentAngles = 
                    new CongruentSidesInTriangleImplyCongruentAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(congruentSidesInTriangleImplyCongruentAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.EQUILATERAL_TRIANGLE_HAS_SIXTY_DEGREE_ANGLES.ordinal()]) 
        {
            EquilateralTriangleHasSixtyDegreeAngles equilateralTriangleHasSixtyDegreeAngles = 
                    new EquilateralTriangleHasSixtyDegreeAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(equilateralTriangleHasSixtyDegreeAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.EXTERIOR_ANGLE_EQUAL_SUM_REMOTE_ANGLES.ordinal()])
        {
            ExteriorAngleEqualSumRemoteAngles exteriorAngleEqualSumRemoteAngles = new ExteriorAngleEqualSumRemoteAngles((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(exteriorAngleEqualSumRemoteAngles.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.HYPOTENUSE_LEG.ordinal()])
        {
            HypotenuseLeg hypotenuseLeg = new HypotenuseLeg((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(hypotenuseLeg.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.ISOSCELES_TRIANGLE_THEOREM.ordinal()])
        {
            IsoscelesTriangleTheorem isoscelesTriangleTheorem = new IsoscelesTriangleTheorem((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(isoscelesTriangleTheorem.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SAS_SIMILARITY.ordinal()])
        {
            SASSimilarity sASSimilarity = new SASSimilarity((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sASSimilarity.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SSS_SIMILARITY.ordinal()]) 
        {
            SSSSimilarity sSSSimiliary = new SSSSimilarity((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sSSSimiliary.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.SUM_ANGLES_IN_TRIANGLE_180.ordinal()]) 
        {
            SumAnglesInTriangle sumAnglesInTriangle = new SumAnglesInTriangle((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(sumAnglesInTriangle.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.TRIANGLE_PROPORTIONALITY.ordinal()]) 
        {
            TriangleProportionality triangleProportionality = new TriangleProportionality((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(triangleProportionality.deduce());
        }

        if (!DeductionFlags.dFlags[DeductionJustType.TWO_PAIRS_CONGRUENT_ANGLES_IMPLY_THIRD_PAIR_CONGRUENT.ordinal()])
        {
            TwoPairsCongruentAnglesImplyThirdPairCongruent twoPairsCongruentAnglesImplyThirdPairCongruent = 
                    new TwoPairsCongruentAnglesImplyThirdPairCongruent((QueryableHypergraph<GroundedClause, Annotation>) _qhg);
            deductions.addAll(twoPairsCongruentAnglesImplyThirdPairCongruent.deduce());
        }

    }

}
