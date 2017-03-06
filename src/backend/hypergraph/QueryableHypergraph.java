package backend.hypergraph;

import java.util.ArrayList;
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
import backend.ast.Descriptors.Relations.AlgebraicSimilarTriangles;
import backend.ast.Descriptors.Relations.GeometricSimilarTriangles;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.AlgebraicCongruentTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.AlgebraicProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.GeometricProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatioEquation;
import backend.ast.Descriptors.parallel.AlgebraicParallel;
import backend.ast.Descriptors.parallel.GeometricParallel;
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
import backend.symbolicAlgebra.equations.*;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;

public class QueryableHypergraph<T, A extends Annotation> extends Hypergraph<T, A>
{

    // number of nodes
    protected int _nodeCount;
    public int getNodeCount() { return _nodeCount; }

    // number of edges
    protected int _edgeCount;
    public int getEdgeCount() { return _edgeCount; }

    public QueryableHypergraph()
    {
        super();

        _nodeCount = 0;
        _edgeCount = 0;
        initQueryContainers();
    }
    //
    // @author C. Alvin
    //
    // The intent here is two-fold:
    //      (1) add a node to the hypergraph like normal.
    //      (2) add the node to the appropriate queryable containers (for fast access)
    //
    @SuppressWarnings("unchecked")
    @Override
    public boolean addNode(T data)
    {
        // Add to the hypergraph
        if (!super.addNode(data)) return false;

        // add Node ID number and add to queryable container
        if (data instanceof GroundedClause)
        {
            GroundedClause clauseData = (GroundedClause) data;
            clauseData.setID(_nodeCount);
            _nodeCount++;

            // Add to the proper queryable containers
            addToQueryableContainers((T) clauseData);

            return true;
        }

        // not a GroundedClause, return false
        return false;
    }

    //
    // Method to add all nodes from the FactComputerContainer returned by 
    // the fact computer
    // 
    // @author Drew Whitmire
    @SuppressWarnings("unchecked")
    public boolean addAllNodes(ArrayList<HashSet<GroundedClause>> hashSets)
    {
        // for each HashSet in the list
        for (HashSet<GroundedClause> set : hashSets)
        {
            // for each grounded clause in the HashSet
            for (GroundedClause gc : set)
            {
                // add the node
                // if it fails to add, return false
                if (!addNode((T) gc))
                {
                    return false;
                }
            }
        }

        // all nodes were added
        return true;
    }

    //
    // single edge addition
    //
    // @author Drew Whitmire
    @SuppressWarnings("unchecked")
    public boolean addEdge(Deduction d)
    {
        // find target node within QHG
        GroundedClause data = d.getConsequent();
        GroundedClause target = null;

        //
        // Points
        //
        if (data instanceof Point)
        {
            for (Point p : _points)
            {
                if (data.structurallyEquals(p))
                {
                    target = p;
                    break;
                }

            }
        }
        else if (data instanceof Collinear) 
        {
            for (Collinear c : _collinear)
            {
                if (data.structurallyEquals(c))
                {
                    target = c;
                    break;
                }
            }
        }

        //
        // Angles
        //
        else if (data instanceof Angle)
        {
            if (data instanceof RightAngle)
            {
                for (RightAngle ra : _rightAngles)
                {
                    if (data.structurallyEquals(ra))
                    {
                        target = ra;
                        break;
                    }
                }
            }
            else // general angle list
            {
                for (Angle a : _angles)
                {
                    if (data.structurallyEquals(a))
                    {
                        target = a;
                        break;
                    }
                }
            }
        }      
        else if (data instanceof AnglePairRelation)
        {
            if (data instanceof Complementary)
            {
                for (Complementary c : _complementaryAngles)
                {
                    if (data.structurallyEquals(c))
                    {
                        target = c;
                        break;
                    }
                }
            }
            else if (data instanceof Supplementary)
            {
                for (Supplementary s : _supplementaryAngles)
                {
                    if (data.structurallyEquals(s))
                    {
                        target = s;
                        break;
                    }
                }
            }
            else // not a special case
            {
                for (AnglePairRelation apr : _anglePairRelations)

                {
                    if (data.structurallyEquals(apr))
                    {
                        target = apr;
                        break;
                    }
                }
            }
        }        
        else if (data instanceof AngleBisector) 
        {
            for (AngleBisector ab : _angleBisectors)
            {
                if (data.structurallyEquals(ab))
                {
                    target = ab; 
                    break;
                }
            }
        }
        else if (data instanceof CongruentAngles)
        {
            for (CongruentAngles ca : _CongruentAngles)
            {
                if (data.structurallyEquals(ca))
                {
                    target = ca;
                    break;
                }
            }
        }

        //
        // Triangles
        //
        else if (data instanceof Triangle)
        {
            // specific types of triangles
            if (data instanceof RightTriangle)
            {
                for (RightTriangle rt : _rightTriangles)
                {
                    if (data.structurallyEquals(rt))
                    {
                        target = rt;
                        break;
                    }
                }
            }
            else if (data instanceof EquilateralTriangle)
            {
                for (EquilateralTriangle eq : _equilateralTriangles)
                {
                    if (data.structurallyEquals(eq))
                    {
                        target = eq;
                        break;
                    }
                }
            }
            else if (data instanceof IsoscelesTriangle)
            {
                for (IsoscelesTriangle it : _isoscelesTriangles)
                {
                    if (data.structurallyEquals(it))
                    {
                        target = it;
                        break;
                    }
                }
            }
            else // not a special type of triangle, go through all triangles list
            {
                for (Triangle t : _triangles)
                {
                    if (data.structurallyEquals(t))
                    {
                        target = t;
                        break;
                    }
                }
            }
        }
        else if (data instanceof Altitude)
        {
            for (Altitude a : _altitudes)
            {
                if (data.structurallyEquals(a))
                {
                    target = a;
                    break;
                }
            }
        }

        //
        // Segments
        //
        else if (data instanceof Segment)
        {
            for (Segment s : _segments)
            {
                if (data.structurallyEquals(s))
                {
                    target = s;
                    break;
                }
            }
        }

        else if (data instanceof Intersection)
        {
            // specific types of intersections
            if (data instanceof Perpendicular)
            {
                // specific types of perpendiculars
                if (data instanceof PerpendicularBisector)
                {
                    for (PerpendicularBisector pb : _perpendicularBisectors)
                    {
                        if (data.structurallyEquals(pb))
                        {
                            target = pb;
                            break;
                        }
                    }
                }
                else // all perpendiculars
                {
                    for (Perpendicular p : _perpendiculars)
                    {
                        if (data.structurallyEquals(p))
                        {
                            target = p;
                            break;
                        }
                    }
                }
            }
            else // all intersections
            {
                for (Intersection i : _intersections)
                {
                    if (data.structurallyEquals(i))
                    {
                        target = i;
                        break;
                    }
                }
            }

        }
        else if (data instanceof CongruentSegments)
        {
            for (CongruentSegments cs : _congruentSegments)
            {
                if (data.structurallyEquals(cs))
                {
                    target = cs;
                    break;
                }
            }
        }
        else if (data instanceof SegmentBisector)
        {
            for (SegmentBisector sb : _segmentBisectors)
            {
                if (data.structurallyEquals(sb))
                {
                    target = sb;
                    break;
                }
            }
        }
        else if (data instanceof InMiddle)
        {
            if (data instanceof Midpoint)
            {
                for (Midpoint m : _midpoints)
                {
                    if (data.structurallyEquals(m))
                    {
                        target = m;
                        break;
                    }
                }
            }
            else // all InMiddles
            {
                for (InMiddle im : _inMiddles)
                {
                    if (data.structurallyEquals(im))
                    {
                        target = im;
                        break;
                    }
                }
            }
        }
        else if (data instanceof Median)
        {
            for (Median m : _medians)
            {
                if (data.structurallyEquals(m))
                {
                    target = m;
                    break;
                }
            }
        }

        //
        // Parallels
        //
        else if (data instanceof Parallel)
        {
            for (Parallel p : _parallels)
            {
                if (data.structurallyEquals(p))
                {
                    target = p;
                    break;
                }
            }
        }

        //
        // Quadrilaterals
        // 
        else if (data instanceof Quadrilateral)
        {
            // specific quadrilaterals
            if (data instanceof Trapezoid)
            {
                if (data instanceof IsoscelesTrapezoid)
                {
                    for (IsoscelesTrapezoid it : _isoscelesTrapezoids)
                    {
                        if (data.structurallyEquals(it))
                        {
                            target = it;
                            break;
                        }
                    }
                }
                else // all trapezoids
                {
                    for (Trapezoid t : _trapezoids)
                    {
                        if (data.structurallyEquals(t))
                        {
                            target = t;
                            break;
                        }
                    }
                }
            }
            else if (data instanceof Parallelogram)
            {
                if (data instanceof Rectangle)
                {
                    for (Rectangle r : _rectangles)
                    {
                        if (data.structurallyEquals(r))
                        {
                            target = r;
                            break;
                        }
                    }
                }
                else // all parallelograms
                {
                    for (Parallelogram p : _parallelograms)
                    {
                        if (data.structurallyEquals(p))
                        {
                            target = p;
                            break;
                        }
                    }
                }
            }
            else if (data instanceof Kite)
            {
                for (Kite k : _kites)
                {
                    if (data.structurallyEquals(k))
                    {
                        target = k;
                        break;
                    }
                }
            }
            else if (data instanceof Square)
            {
                for (Square s : _Squares)
                {
                    if (data.structurallyEquals(s))
                    {
                        target = s;
                        break;
                    }
                }
            }
            else // all quadrilaterals
            {
                for (Quadrilateral q : _quadrilaterals)
                {
                    if (data.structurallyEquals(q))
                    {
                        target = q;
                        break;
                    }
                }
            }
        }

        //
        // Circles
        //
        else if (data instanceof Circle)
        {
            for (Circle c : _circles)
            {
                if (data.structurallyEquals(c))
                {
                    target = c;
                    break;
                }
            }
        }
        else if (data instanceof Arc)
        {
            if (data instanceof MinorArc)
            {
                for (MinorArc ma : _minorArcs)
                {
                    if (data.structurallyEquals(ma))
                    {
                        target = ma;
                        break;
                    }
                }
            }
            else if (data instanceof MajorArc)
            {
                for (MajorArc ma : _majorArcs)
                {
                    if (data.structurallyEquals(ma))
                    {
                        target = ma;
                        break;
                    }
                }
            }
            else // all arcs
            {
                for (Arc a : _arcs)
                {
                    if (data.structurallyEquals(a))
                    {
                        target = a;
                        break;
                    }
                }
            }
        }
        else if (data instanceof Midpoint)
        {
            for (Midpoint m : _midpoints)
            {
                if (data.structurallyEquals(m))
                {
                    target = m;
                    break;
                }
            }
        }

        // 
        // Descriptors
        //

        //
        // Handle all equation types
        //
        // Defer equation processing to the query container
        //
        else if (data instanceof Equation)
        {
            // add specific equations
            if (data instanceof AngleEquation)
            {
                for (AngleEquation ae : _angleEquations)
                {
                    if (data.structurallyEquals(ae))
                    {
                        target = ae;
                        break;
                    }
                }
            }
            else // TODO: equation handler 
            {
                //                if (data.structurallyEquals(_equationHandler))
                //                {
                //                    target = _equationHandler;
                //                }
            }
        }
        else if (data instanceof SegmentRatioEquation)
        {
            for (SegmentRatioEquation sre : _segmentRatioEquations)
            {
                if (data.structurallyEquals(sre))
                {
                    target = sre;
                    break;
                }
            }
        }

        // if target is not null, get target ID
        if (target != null)
        {
            int targetID = target.getID();

            // create list of source IDs
            ArrayList<Integer> sourceIDs = new ArrayList<>();
            for (GroundedClause source : d.getAntecedent())
            {
                sourceIDs.add(source.getID());
            }

            // create a hyperedge
            Hyperedge<Annotation> newEdge = new Hyperedge<Annotation>(sourceIDs, targetID, d.getAnnotation());

            // add hyperedge to the QHG
            if (!super.addEdge((Hyperedge<A>) newEdge))
            {
                // throw exception, something bad happened
                ExceptionHandler.throwException(new DebugException("QHG: Edge Unsuccessfully Added.  Edge pre-existing"));
                return false;
            }
            else // edge added successfully
            {
                // increase edge count
                _edgeCount++;
                return true;
            }
        }
        else // target is not in QHG, can't add edge
        {
            // throw exception, something bad happened
            ExceptionHandler.throwException(new DebugException("QHG: Edge Unsuccessfully Added.  Target Node Does Not Exist."));
            return false;
        }

    }

    //
    // multiple edge addition
    //
    // @author Drew Whitmire
    public boolean addAllEdges(Set<Deduction> deductions)
    {
        System.out.println("QHG: in addAllEdges");
        // add each deduction as hyperedge
        for (Deduction d : deductions)
        {
            System.out.println("QHG: Attempting to add edge");
            // if the edge is unsuccessfully added, return false
            if (!addEdge(d))
            {
                System.out.println("QHG: Failed to add edge");
                return false;
            }
        }

        // all edges successfully added
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
    public HashSet<Complementary> _complementaryAngles;
    public HashSet<CongruentAngles> _CongruentAngles;
    public HashSet<Supplementary> _supplementaryAngles;
    public HashSet<Angle> _angles;
    public HashSet<AnglePairRelation> _anglePairRelations;

    //
    // Triangles
    //
    public HashSet<Altitude> _altitudes;
    public HashSet<Triangle> _triangles;
    public HashSet<RightTriangle> _rightTriangles;
    public HashSet<EquilateralTriangle> _equilateralTriangles;
    public HashSet<IsoscelesTriangle> _isoscelesTriangles;

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
    public HashSet<Square> _Squares;
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
    public HashSet<CongruentCircles> _congruentCircles;

    //
    // Descriptors
    //
    private HashSet<Midpoint> _midpoints;

    //
    // Equations
    //
    protected EquationQueryHandler _equationHandler;
    public HashSet<Equation> _equations;
    public HashSet<AngleEquation> _angleEquations;
    public Set<SegmentRatioEquation> _segmentRatioEquations;

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
    public HashSet<Strengthened> _strengthenedParallelograms;
    public HashSet<Strengthened> _strengthenedRectangles;
    public HashSet<Strengthened> _strengthenedRhombuses;
    public HashSet<Strengthened> _strengthenedSquares;
    public HashSet<Strengthened> _strengthenedAngleEquations;

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
        _complementaryAngles = new HashSet<Complementary>();
        _CongruentAngles = new HashSet<CongruentAngles>();
        _supplementaryAngles = new HashSet<Supplementary>();
        _angles = new HashSet<Angle>();
        _anglePairRelations = new HashSet<AnglePairRelation>();

        //
        // Triangles
        //
        _altitudes = new HashSet<Altitude>();
        _triangles = new HashSet<Triangle>();
        _rightTriangles = new HashSet<RightTriangle>();
        _equilateralTriangles = new HashSet<EquilateralTriangle>();
        _isoscelesTriangles = new HashSet<IsoscelesTriangle>();

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
        _Squares = new HashSet<Square>();
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
        _angleEquations = new HashSet<AngleEquation>();
        _segmentRatioEquations = new HashSet<SegmentRatioEquation>();


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
        _strengthenedParallelograms = new HashSet<Strengthened>();
        _strengthenedRectangles = new HashSet<Strengthened>();
        _strengthenedSquares = new HashSet<Strengthened>();
        _strengthenedAngleEquations = new HashSet<Strengthened>();

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
        // Points
        //
        else if (data instanceof Collinear) 
        {
            _collinear.add((Collinear) data);
        }

        //
        // Angles
        //
        else if (data instanceof Angle)
        {
            // general angle list
            _angles.add((Angle) data);

            // specific types of angles list
            if (data instanceof RightAngle)
            {
                _rightAngles.add((RightAngle) data);
            }

        }      
        else if (data instanceof AnglePairRelation)
        {
            _anglePairRelations.add((AnglePairRelation) data);

            if (data instanceof Complementary)
            {
                _complementaryAngles.add((Complementary) data);
            }
            else if (data instanceof Supplementary)
            {
                _supplementaryAngles.add((Supplementary) data);
            }
        }        
        else if (data instanceof AngleBisector) 
        {
            _angleBisectors.add((AngleBisector) data);
        }
        else if (data instanceof CongruentAngles)
        {
            _CongruentAngles.add((CongruentAngles) data);
        }

        //
        // Triangles
        //
        else if (data instanceof Triangle)
        {
            // all triangles
            _triangles.add((Triangle) data);

            // specific types of triangles
            if (data instanceof RightTriangle)
            {
                _rightTriangles.add((RightTriangle) data);
            }
            else if (data instanceof EquilateralTriangle)
            {
                _equilateralTriangles.add((EquilateralTriangle) data);
            }
            else if (data instanceof IsoscelesTriangle)
            {
                _isoscelesTriangles.add((IsoscelesTriangle) data);
            }
        }
        else if (data instanceof Altitude)
        {
            _altitudes.add((Altitude) data);
        }

        //
        // Segments
        //
        else if (data instanceof Intersection)
        {
            // all intersections
            _intersections.add((Intersection) data);

            // specific types of intersections
            if (data instanceof Perpendicular)
            {
                _perpendiculars.add((Perpendicular) data);

                // specific types of perpendiculars
                if (data instanceof PerpendicularBisector)
                {
                    _perpendicularBisectors.add((PerpendicularBisector) data);
                }
            }

        }
        else if (data instanceof CongruentSegments)
        {
            _congruentSegments.add((CongruentSegments) data);
        }
        else if (data instanceof SegmentBisector)
        {
            _segmentBisectors.add((SegmentBisector) data);
        }
        else if (data instanceof InMiddle)
        {
            _inMiddles.add((InMiddle) data);

            if (data instanceof Midpoint)
            {
                _midpoints.add((Midpoint) data);
            }
        }
        else if (data instanceof Median)
        {
            _medians.add((Median) data);
        }

        //
        // Parallels
        //
        else if (data instanceof Parallel)
        {
            _parallels.add((Parallel) data);
        }

        //
        // Quadrilaterals
        // 
        else if (data instanceof Quadrilateral)
        {
            // all quadrilaterals
            _quadrilaterals = new HashSet<Quadrilateral>();

            // specific quadrilaterals
            if (data instanceof Trapezoid)
            {
                _trapezoids.add((Trapezoid) data);

                if (data instanceof IsoscelesTrapezoid)
                {
                    _isoscelesTrapezoids.add((IsoscelesTrapezoid) data);
                }
            }
            else if (data instanceof Parallelogram)
            {
                _parallelograms.add((Parallelogram) data);

                if (data instanceof Rectangle)
                {
                    _rectangles.add((Rectangle) data);
                }
            }
            else if (data instanceof Kite)
            {
                _kites.add((Kite) data);
            }
            else if (data instanceof Square)
            {
                _Squares.add((Square) data);
            }
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

        //
        // Descriptors
        //

        //
        // Handle all equation types
        //
        // Defer equation processing to the query container
        //
        else if (data instanceof Equation)
        {
            _equationHandler.add((Equation)data);

            // add specific equations
            if (data instanceof AngleEquation)
            {
                _angleEquations.add((AngleEquation) data);
            }
        }
        else if (data instanceof SegmentRatioEquation)
        {
            _segmentRatioEquations.add((SegmentRatioEquation) data);
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
        else if (strengthened instanceof Quadrilateral)
        {
            _sQuadrilaterals.add(s);

            // specific types
            if (strengthened instanceof Parallelogram)
            {
                _strengthenedParallelograms.add(s);

                if (strengthened instanceof Rectangle)
                {
                    _strengthenedRectangles.add(s);
                }
            }
            else if (strengthened instanceof Kite)
            {
                _strengthenedKites.add(s);
            }
            else if (strengthened instanceof Trapezoid)
            {
                _strengthenedTrapezoids.add(s);

                if (strengthened instanceof IsoscelesTrapezoid)
                {
                    _strengthenedIsoscelesTrapezoids.add(s);
                }
            }
            else if (strengthened instanceof Square)
            {
                _strengthenedSquares.add(s);
            }
        }
        else if (strengthened instanceof Perpendicular)
        {
            _strengthenedPerpendicular.add(s);
        }
        else if (strengthened instanceof PerpendicularBisector)
        {
            _strengthenedPerpendicularBisectors.add(s);
        }
        else if (strengthened instanceof SegmentBisector)
        {
            _strengthenedSegmentBisectors.add(s);
        }
        else if (strengthened instanceof RightAngle)
        {
            _strengthenedRightAngles.add(s);
        }
        else if (strengthened instanceof AngleEquation)
        {
            _strengthenedAngleEquations.add(s);
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
        return _sRightTriangles;
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
        return _Squares;
    }

    public HashSet<Strengthened> getStrengthenedSquares()
    {
        return _strengthenedSquares;
    }

    public HashSet<Circle> getCircles()
    {
        return _circles;
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
        return _segments;
    }

    public HashSet<CongruentCircles> getCongruentCircles()
    {
        return _congruentCircles;
    }

    public HashSet<EquilateralTriangle> getEquilateralTriangles()
    {
        return _equilateralTriangles;
    }

    public HashSet<Strengthened> getStrengthenedEquilateralTriangles()
    {
        return _sEqTriangles;
    }

    public HashSet<Strengthened> getStrengthenedRightTriangles()
    {
        return _sRightTriangles;
    }

    public HashSet<Strengthened> getStrengthenedIsoscelesTriangles()
    {
        return _sIsoTriangles;
    }

    public HashSet<AngleEquation> getAngleEquations()
    {
        return _angleEquations;
    }

    public HashSet<Strengthened> getStrengthenedAngleEquations()
    {
        return _strengthenedAngleEquations;
    }

    public HashSet<Complementary> getComplementaryAngles()
    {
        return _complementaryAngles;
    }

    public HashSet<CongruentAngles> getCongruentAngles()
    {
        return _CongruentAngles;
    }

    public HashSet<Supplementary> getSupplementaryAngles()
    {
        return _supplementaryAngles;
    }

    public HashSet<Angle> getAngles()
    {
        return _angles;
    }

    public HashSet<IsoscelesTriangle> getIsoscelesTriangles()
    {
        return _isoscelesTriangles;
    }

    public HashSet<AnglePairRelation> getAnglePairRelations()
    {
        return _anglePairRelations;
    }

    public Set<SegmentRatioEquation> getSegmentRatioEquations()
    {
        return _segmentRatioEquations;
    }

    public HashSet<GeometricCongruentSegments> getGeometricCongruentSegments()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricCongruentAngles> getGeomtricCongruentAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricCongruentArcs> getGeometricCongruentArcs()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicCongruentSegments> getAlgebraicCongruentSegments()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicCongruentAngles> getAlgebraicCongruentAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicCongruentArcs> getAlgebraicCogruentArcs()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricSegmentEquation> getGeometricSegmentEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicSegmentEquation> getAlgebraicSegmentEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicAngleEquation> getAlgebraicAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricAngleEquation> getGeometricAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricArcEquation> getGeomtricArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicArcEquation> getAlgebraicArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricAngleArcEquation> getGeometricAngleArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicAngleArcEquation> getAlgebraicAngleArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<SegmentRatio> getSegmentRatios()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricProportionalAngles> getGeometricProportionalAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicProportionalAngles> getAlgebraicProportionalAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<SimilarTriangles> getSimilarTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<GeometricCongruentTriangles> getGeometricCongruentTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AlgebraicCongruentTriangles> getAlgebraicCongruentTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<GeometricParallel> getGeometricParallels()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<AlgebraicParallel> getAlgebraicParallels()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<GeometricSimilarTriangles> getGeometricSimilarTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<AlgebraicSimilarTriangles> getAlgebraicSimilarTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<SegmentEquation> getSegmentEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<ArcEquation> getArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<AngleArcEquation> getAngleArcEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public HashSet<CongruentArcs> getCongruentArcs()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
