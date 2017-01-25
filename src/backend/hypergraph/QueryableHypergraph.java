package backend.hypergraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
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
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.*;
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
import backend.symbolicAlgebra.equations.*;

public class QueryableHypergraph<T, A> extends Hypergraph<T, A>
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
    // All other quadrilaterals
    
    private void initQueryContainers()
    {
        //
        // Basics
        //
        _points = new HashSet<Point>();
        _segments = new HashSet<Segment>();

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
    public Set<Midpoint> getMidpoints() { return _midpoints; }

    //
    // Strengthened Clauses
    //
    public Set<Strengthened> getStrengthenedMidpoints() { return _sMidpoints; }
    public Set<Strengthened> getStrengthenedTriangles() { return _sTriangles; }
    public Set<Strengthened> getStrengthenedQuadrilaterals() { return _sQuadrilaterals; }

    //
    // Equations: Defer to the Query Class
    //
    //
    public SegmentEquation getSegmentEquation(SegmentEquation eq) { return _equationHandler.getSegmentEquation(eq); }
    public AngleEquation getAngleEquation(AngleEquation eq) { return _equationHandler.getAngleEquation(eq); }
    public ArcEquation getArcEquation(ArcEquation eq) { return _equationHandler.getArcEquation(eq); }
    public AngleArcEquation getAngleArcEquation(AngleArcEquation eq) { return _equationHandler.getAngleArcEquation(eq); }
    public Equation getGneralEquation(Equation eq) { return _equationHandler.getGeneralEquation(eq); }

    public Set<AngleBisector> getAngleBisectors()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Altitude> getAltitudes()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Intersection> getIntersections()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Triangle> getTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Perpendicular> getPerpendicular()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedPerpendicular()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<PerpendicularBisector> getPerpendicularBisectors()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedPerpendicularBisectors()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<InMiddle> getInMiddles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Median> getMedians()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<SegmentBisector> getSegmentBisectors()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedSegmentBisectors()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<IsoscelesTrapezoid> getIsoscelesTrapezoids()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<Collinear> getCollinear()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedIsoscelesTrapezoids()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<Strengthened> getStrengthenedRightAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<Trapezoid> getTrapezoids()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<CongruentSegments> getCongruentSegments()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<Strengthened> getStrengthenedTrapezoids()
    {
        // TODO Auto-generated method stub
        return null;
    }
	
    public List<RightAngle> getRightAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Kite> getKites()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedKites()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Quadrilateral> getQuadrilaterals()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<RightTriangle> getRightTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedRightTriangle()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedParallelograms()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Parallelogram> getParallelograms()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Parallel> getParallels()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Rectangle> getRectangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedRectangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Rhombus> getRhombuses()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedRhombuses()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Square> getSquares()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedSquares()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Circle> getCircles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Segment> getRadii(Circle circle)
    {
        List<Segment> radii = new ArrayList<Segment>();
        
        for (Segment segment : this.getSegments())
        {
            if (circle.isRadius(segment)) radii.add(segment);
        }

        return radii;
    }
    private List<Segment> getSegments()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<CongruentCircles> getCongruentCircles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<EquilateralTriangle> getEquilateralTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedEquilateralTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedRightTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedIsoscelesTriangles()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<AngleEquation> getAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Strengthened> getStrengthenedAngleEquations()
    {
        // TODO Auto-generated method stub
        return null;
    }
    public List<Complementary> getComplementaryAngles()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
