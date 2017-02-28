package backend.hypergraph;

import java.util.HashSet;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatioEquation;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.*;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.triangles.*;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.angles.axioms.AngleAdditionAxiom;
import backend.deductiveRules.angles.axioms.AnglesOfEqualMeasureAreCongruent;
import backend.deductiveRules.angles.axioms.CongruentCorrespondingAnglesImplyParallel;
import backend.deductiveRules.angles.axioms.CorrespondingAnglesOFParallelLines;
import backend.deductiveRules.angles.definitions.AngleBisectorDefinition;
import backend.deductiveRules.angles.definitions.ComplementaryDefinition;
import backend.deductiveRules.angles.definitions.PerpendicularBisectorDefinition;
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
import backend.symbolicAlgebra.equations.*;
import backend.deductiveRules.RuleFactory.JustificationSwitch.DeductionJustType;

public class QueryableHypergraph<T, A extends Annotation> extends Hypergraph<T, A>
{
    public QueryableHypergraph()
    {
        super();

        initQueryContainers();
    }
    //
    // @author C. Alvin
    //
    // The intent here is two-fold:
    //      (1) add a node to the hypergraph like normal.
    //      (2) add the node to the appropriate queryable containers (for fast access)
    //
    @Override
    public boolean addNode(T data)
    {
        // Add to the hypergraph
        if (!super.addNode(data)) return false;

        // Add to the proper queryable containers
        addToQueryableContainers(data);

        return true;
    }

    

    //
    //
    //
    // ----------  The queryable containers -------------
    //
    //
    //
    private HashSet<Point> _points;
    private HashSet<Segment> _segments;
    
    //
    // Points
    //
    public HashSet<Collinear> _collinear;
    
    //
    // Angles
    //
    public HashSet<AngleBisector> _angleBisectors;
    public HashSet<RightAngle> _rightAngles;
    
    //
    // Triangles
    //
    public HashSet<Altitude> _altitudes;
    public HashSet<Triangle> _triangles;
    public HashSet<RightTriangle> _rightTriangles;
    
    //
    // Segments
    // 
    public HashSet<Intersection> _intersections;
    public HashSet<Perpendicular> _perpendiculars;
    public HashSet<PerpendicularBisector> _perpendicularBisectors;
    public HashSet<InMiddle> _inMiddles;
    public HashSet<Median> _medians;
    public HashSet<SegmentBisector> _segmentBisectors;
    public HashSet<CongruentSegments> _congruentSegments;
    
    //
    // Parallels
    //
    public HashSet<Parallel> _parallels;
    
    //
    // Quadrilaterals
    //
    public HashSet<Quadrilateral> _quadrilaterals;
    public HashSet<IsoscelesTrapezoid> _isoscelesTrapezoids;
    public HashSet<Trapezoid> _trapezoids;
    public HashSet<Kite> _kites;
    public HashSet<Parallelogram> _parallelograms;
    public HashSet<Rectangle> _rectangles;
    public HashSet<Rhombus> _rhombuses;

    //
    // Circles
    //
    private HashSet<Circle> _circles;
    private HashSet<MinorArc> _minorArcs;
    private HashSet<MajorArc> _majorArcs;
    private HashSet<Arc> _arcs;

    //
    // Descriptors
    //
    private HashSet<Midpoint> _midpoints;

    //
    // Equations
    //
    protected EquationQueryHandler _equationHandler;

    //
    // Strengthened Clauses
    //
    private HashSet<Strengthened> _sMidpoints;
    private HashSet<Strengthened> _sTriangles;
    private HashSet<Strengthened> _sIsoTriangles;
    private HashSet<Strengthened> _sRightTriangles;
    private HashSet<Strengthened> _sEqTriangles;
    private HashSet<Strengthened> _sQuadrilaterals;
    public HashSet<Strengthened> _strengthenedPerpendicular;
    public HashSet<Strengthened> _strengthenedPerpendicularBisectors;
    public HashSet<Strengthened> _strengthenedSegmentBisectors;
    public HashSet<Strengthened> _strengthenedIsoscelesTrapezoids;
    public HashSet<Strengthened> _strengthenedRightAngles;
    public HashSet<Strengthened> _strengthenedTrapezoids;
    public HashSet<Strengthened> _strengthenedKites;
    public HashSet<Strengthened> _strengthenedRightTriangle;
    public HashSet<Strengthened> _strengthenedParallelograms;
    public HashSet<Strengthened> _strengthenedRectangles;
    public HashSet<Strengthened> _strengthenedRhombuses;
    
    // All other quadrilaterals

    private void initQueryContainers()
    {
        //
        // Basics
        //
        _points = new HashSet<Point>();
        _segments = new HashSet<Segment>();
        
        //
        // Points
        //
        _collinear = new HashSet<Collinear>();
        
        //
        // Angles
        //
        _angleBisectors = new HashSet<AngleBisector>();
        _rightAngles = new HashSet<RightAngle>();
        
        //
        // Triangles
        //
        _altitudes = new HashSet<Altitude>();
        _triangles = new HashSet<Triangle>();
        _rightTriangles = new HashSet<RightTriangle>();
        
        //
        // Segments
        //
        _intersections = new HashSet<Intersection>();
        _perpendiculars = new HashSet<Perpendicular>();
        _perpendicularBisectors = new HashSet<PerpendicularBisector>();
        _inMiddles = new HashSet<InMiddle>();
        _medians = new HashSet<Median>();
        _segmentBisectors = new HashSet<SegmentBisector>();
        _congruentSegments = new HashSet<CongruentSegments>();
        
        //
        // Parallels
        //
        _parallels = new HashSet<Parallel>();
        
        //
        // Quadrilaterals
        // 
        _quadrilaterals = new HashSet<Quadrilateral>();
        _isoscelesTrapezoids = new HashSet<IsoscelesTrapezoid>();
        _trapezoids = new HashSet<Trapezoid>();
        _kites = new HashSet<Kite>();
        _parallelograms = new HashSet<Parallelogram>();
        _rectangles = new HashSet<Rectangle>();

        //
        // Circles
        //
        _circles = new HashSet<Circle>();
        _minorArcs = new HashSet<MinorArc>();
        _majorArcs = new HashSet<MajorArc>();
        _arcs = new HashSet<Arc>();

        //
        // Descriptors
        //
        _midpoints = new HashSet<Midpoint>();

        //
        // Equations
        //
        _equationHandler = new EquationQueryHandler();

        //
        // Strengthened Clauses
        //
        _sMidpoints = new HashSet<Strengthened>();
        _sTriangles = new HashSet<Strengthened>();
        _sIsoTriangles = new HashSet<Strengthened>();
        _sRightTriangles = new HashSet<Strengthened>();
        _sEqTriangles = new HashSet<Strengthened>();
        _sQuadrilaterals = new HashSet<Strengthened>();
        _strengthenedPerpendicular = new HashSet<Strengthened>();
        _strengthenedPerpendicularBisectors = new HashSet<Strengthened>();
        _strengthenedSegmentBisectors = new HashSet<Strengthened>();
        _strengthenedIsoscelesTrapezoids = new HashSet<Strengthened>();
        _strengthenedRightAngles = new HashSet<Strengthened>();
        _strengthenedTrapezoids = new HashSet<Strengthened>();
        _strengthenedKites = new HashSet<Strengthened>();
        _strengthenedRightTriangle = new HashSet<Strengthened>();
        _strengthenedParallelograms = new HashSet<Strengthened>();
        _strengthenedRectangles = new HashSet<Strengthened>();

    }

    private void addToQueryableContainers(T data)
    {
        //
        // Basics
        //
        if (data instanceof Point)
        {
            _points.add((Point)data);
        }
        else if (data instanceof Segment)
        {
            _segments.add((Segment)data);
        }
        //
        // Circles
        //
        else if (data instanceof Circle)
        {
            _circles.add((Circle)data);
        }
        else if (data instanceof Arc)
        {
            _arcs.add((Arc)data);

            if (data instanceof MinorArc)
            {
                _minorArcs.add((MinorArc)data);
            }
            else if (data instanceof MajorArc)
            {
                _majorArcs.add((MajorArc)data);
            }
        }
        else if (data instanceof Midpoint)
        {
            _midpoints.add((Midpoint)data);

            //
            // Handle all equation types
            //
        }
        //
        // Defer equation processing to the query container
        //
        else if (data instanceof Equation)
        {
            _equationHandler.add((Equation)data);
        }

        //
        // Handle all strengthened clauses
        //
        else if (data instanceof Strengthened)
        {
            addToStrengthened((Strengthened)data);
        }
    }

    private void addToStrengthened(Strengthened s)
    {
        GroundedClause strengthened = s.getStrengthened();

        if (strengthened instanceof Midpoint)
        {
            _sMidpoints.add(s);
        }
        else if (strengthened instanceof Triangle)
        {
            _sTriangles.add(s);

            if (strengthened instanceof IsoscelesTriangle)
            {
                _sIsoTriangles.add(s);
            }
            else if (strengthened instanceof RightTriangle)
            {
                _sRightTriangles.add(s);
            }
            else if (strengthened instanceof EquilateralTriangle)
            {
                _sEqTriangles.add(s);
            }
        }
        else if (strengthened instanceof Triangle)
        {
            _sQuadrilaterals.add(s);

            // ...
        }
    }

    //
    //
    //
    //
    // The queries (as methods)
    //
    //
    //
    public HashSet<Midpoint> getMidpoints() { return _midpoints; }

    //
    // Strengthened Clauses
    //
    public HashSet<Strengthened> getStrengthenedMidpoints() { return _sMidpoints; }
    public HashSet<Strengthened> getStrengthenedTriangles() { return _sTriangles; }
    public HashSet<Strengthened> getStrengthenedQuadrilaterals() { return _sQuadrilaterals; }

    //
    // Equations: Defer to the Query Class
    //
    //
    public SegmentEquation getSegmentEquation(SegmentEquation eq) { return _equationHandler.getSegmentEquation(eq); }
    public AngleEquation getAngleEquation(AngleEquation eq) { return _equationHandler.getAngleEquation(eq); }
    public ArcEquation getArcEquation(ArcEquation eq) { return _equationHandler.getArcEquation(eq); }
    public AngleArcEquation getAngleArcEquation(AngleArcEquation eq) { return _equationHandler.getAngleArcEquation(eq); }
    public Equation getGeneralEquation(Equation eq) { return _equationHandler.getGeneralEquation(eq); }

    public HashSet<AngleBisector> getAngleBisectors()
    {
        return _angleBisectors;
    }
    
    public HashSet<Altitude> getAltitudes()
    {
        return _altitudes;
    }
    
    public HashSet<Intersection> getIntersections()
    {
        return _intersections;
    }
    
    public HashSet<Triangle> getTriangles()
    {
        return _triangles;
    }
    
    public HashSet<Perpendicular> getPerpendicular()
    {
        return _perpendiculars;
    }
    
    public HashSet<Strengthened> getStrengthenedPerpendicular()
    {
        return _strengthenedPerpendicular;
    }
    
    public HashSet<PerpendicularBisector> getPerpendicularBisectors()
    {
        return _perpendicularBisectors;
    }
    
    public HashSet<Strengthened> getStrengthenedPerpendicularBisectors()
    {
        return _strengthenedPerpendicularBisectors;
    }
    
    public HashSet<InMiddle> getInMiddles()
    {
        return _inMiddles;
    }
    
    public HashSet<Median> getMedians()
    {
        return _medians;
    }
    
    public HashSet<SegmentBisector> getSegmentBisectors()
    {
        return _segmentBisectors;
    }
    
    public HashSet<Strengthened> getStrengthenedSegmentBisectors()
    {
        return _strengthenedSegmentBisectors;
    }

    public HashSet<IsoscelesTrapezoid> getIsoscelesTrapezoids()
    {
        return _isoscelesTrapezoids;
    }

    public HashSet<Collinear> getCollinear()
    {
        return _collinear;
    }
    
    public HashSet<Strengthened> getStrengthenedIsoscelesTrapezoids()
    {
        return _strengthenedIsoscelesTrapezoids;
    }

    public HashSet<Strengthened> getStrengthenedRightAngles()
    {
        return _strengthenedRightAngles;
    }

    public HashSet<Trapezoid> getTrapezoids()
    {
        return _trapezoids;
    }

    public HashSet<CongruentSegments> getCongruentSegments()
    {
        return _congruentSegments;
    }

    public HashSet<Strengthened> getStrengthenedTrapezoids()
    {
        return _strengthenedTrapezoids;
    }

    public HashSet<RightAngle> getRightAngles()
    {
        return _rightAngles;
    }
    
    public HashSet<Kite> getKites()
    {
        return _kites;
    }
    
    public HashSet<Strengthened> getStrengthenedKites()
    {
        return _strengthenedKites;
    }
    
    public HashSet<Quadrilateral> getQuadrilaterals()
    {
        return _quadrilaterals;
    }
    
    public HashSet<RightTriangle> getRightTriangles()
    {
        return _rightTriangles;
    }
    
    public HashSet<Strengthened> getStrengthenedRightTriangle()
    {
        return _strengthenedRightTriangle;
    }
    
    public HashSet<Strengthened> getStrengthenedParallelograms()
    {
        return _strengthenedParallelograms;
    }
    
    public HashSet<Parallelogram> getParallelograms()
    {
        return _parallelograms;
    }
    
    public HashSet<Parallel> getParallels()
    {
        return _parallels;
    }
    
    public HashSet<Rectangle> getRectangles()
    {
        return _rectangles;
    }
    
    public HashSet<Strengthened> getStrengthenedRectangles()
    {
        return _strengthenedRectangles;
    }
    
    public HashSet<Rhombus> getRhombuses()
    {
        return _rhombuses;
    }
    
    public HashSet<Strengthened> getStrengthenedRhombuses()
    {
        return _strengthenedRhombuses;
    }
    
    public HashSet<Square> getSquares()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Strengthened> getStrengthenedSquares()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Circle> getCircles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Segment> getRadii(Circle circle)
    {
        HashSet<Segment> radii = new HashSet<Segment>();

        for (Segment segment : this.getSegments())
        {
            if (circle.isRadius(segment)) radii.add(segment);
        }

        return radii;
    }
    public HashSet<Segment> getSegments()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<CongruentCircles> getCongruentCircles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<EquilateralTriangle> getEquilateralTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Strengthened> getStrengthenedEquilateralTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Strengthened> getStrengthenedRightTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Strengthened> getStrengthenedIsoscelesTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<AngleEquation> getAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Strengthened> getStrengthenedAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Complementary> getComplementaryAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<CongruentAngles> getCongruentAngles()
    {
        //TODO Auto-generated method stub
        return null;
    }
    public HashSet<Supplementary> getSupplementaryAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<Angle> getAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public HashSet<IsoscelesTriangle> getIsoscelesTriangles()
    {
        //TODO Auto-generated method stub
        return null;
    }
    public HashSet<AnglePairRelation> getAnglePairRelations()
    {
        //TODO Auto-generated method stub
        return null;
    }
    public Set<SegmentRatioEquation> getSegmentRatioEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
