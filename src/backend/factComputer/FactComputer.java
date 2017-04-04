package backend.factComputer;

import java.util.ArrayList;

import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.CircleIntersection;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.polygon.ConcavePolygon;
import backend.ast.figure.components.polygon.Polygon;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.precomputer.PolygonCalculator;
import backend.precomputer.Precomputer;
import backend.symbolicAlgebra.equations.AngleArcEquation;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.ArcEquation;
import backend.symbolicAlgebra.equations.SegmentEquation;
import backend.utilities.AngleEquivalenceRelation;
import backend.utilities.PointFactory;
import backend.utilities.exception.ExceptionHandler;

public class FactComputer
{
    //
    // Angles and Points are made available via the AngleFactory and PointFactory.
    //
    //private ArrayList<Strengthened> strengthens; //I believe this is an old concept and should be deleted

    private ArrayList<Circle> circles;
    public ArrayList<Circle> getCircles() { return circles; }

    private ArrayList<Arc> arcs;
    public ArrayList<Arc> getArcs() { return arcs; }

    private ArrayList<Segment> segments;
    public ArrayList<Segment> getSegments() { return segments; }

    private ArrayList<Sector> sectors;

    //This might need to be expanded into circlecircle and circlesegment intersections - simple fix
    //as of JPN commit 3/14/2017 this is populated starting at line 661
    private ArrayList<CircleIntersection> circleIntersections;

    private ArrayList<CongruentArcs> congruentArcs;
    private ArrayList<AngleBisector> angleBisectors;
    public ArrayList<AngleBisector> getAngleBisectors() { return angleBisectors; }

    private ArrayList<Altitude> altitudes;
    private ArrayList<Intersection> intersections;
    public ArrayList<Intersection> getIntersections() { return intersections; }

    private ArrayList<Parallel> parallels;

    private ArrayList<Triangle> triangles;
    public ArrayList<Triangle> getTriangles() { return triangles; }

    private ArrayList<RightTriangle> rightTriangles;
    private ArrayList<Strengthened> strengthRightTriangles;
    private ArrayList<EquilateralTriangle> equilateralTriangles;
    private ArrayList<Strengthened> strengthEquilateralTriangles;
    private ArrayList<IsoscelesTriangle> isoscelesTriangles;
    private ArrayList<Strengthened> strengthIsoscelesTriangles;
    private ArrayList<CongruentTriangles> congruentTriangles;
    private ArrayList<SimilarTriangles> similarTriangles;

    private ArrayList<Perpendicular> perpendiculars;
    private ArrayList<Strengthened> strengthenedPerps;
    private ArrayList<PerpendicularBisector> perpBisectors;
    private ArrayList<Strengthened> strengthPerpBisectors;

    private ArrayList<InMiddle> inMiddles;
    public ArrayList<InMiddle> getInMiddles() { return inMiddles; }

    private ArrayList<ArcInMiddle> arcInMiddles;
    public ArrayList<ArcInMiddle> getArcInMiddles() { return arcInMiddles; }

    private ArrayList<Midpoint> midpoints;
    public ArrayList<Midpoint> getMidpoints() { return midpoints; }

    private ArrayList<Strengthened> strengthMidpoints;
    private ArrayList<Median> medians;
    private ArrayList<SegmentBisector> segmentBisectors;
    private ArrayList<Strengthened> strengthSegmentBisectors;
    private ArrayList<CongruentSegments> congruentSegments;
    public ArrayList<CongruentSegments> getCongruentSegments() { return congruentSegments; }

    private ArrayList<ProportionalSegments> proportionalSegments;
    public ArrayList<ProportionalSegments> getProportionalSegments() { return proportionalSegments; }

    private ArrayList<Trapezoid> trapezoids;
    private ArrayList<Strengthened> strengthTrapezoids;
    private ArrayList<IsoscelesTrapezoid> isoscelesTrapezoids;
    private ArrayList<Strengthened> strengthIsoscelesTrapezoids;
    private ArrayList<Collinear> collinears;

    private ArrayList<RightAngle> rightAngles;
    private ArrayList<Strengthened> strengthRightAngles;
    private ArrayList<Strengthened> strengthAngleEquations;
    private ArrayList<Complementary> complementary;
    public ArrayList<Complementary> getComplementaryAngles() { return complementary; }

    private ArrayList<CongruentAngles> congruentAngles;
    public ArrayList<CongruentAngles> getCongruentAngles() { return congruentAngles; }

    private ArrayList<AnglePairRelation> anglePairRelations;
    private ArrayList<Supplementary> supplementary;
    public ArrayList<Supplementary> getSupplementaryAngles() { return supplementary; }

    private ArrayList<ProportionalAngles> proportionalAngles;
    public ArrayList<ProportionalAngles> getProportionalAngles() { return proportionalAngles; }

    private ArrayList<Kite> kites;
    private ArrayList<Strengthened> strengthKites;

    private ArrayList<Quadrilateral> quadrilaterals;
    public ArrayList<Quadrilateral> getQuadrilaterals() { return quadrilaterals; }

    private ArrayList<Parallelogram> parallelograms;
    private ArrayList<Strengthened> strengthParallelograms;
    private ArrayList<Rectangle> rectangles;
    private ArrayList<Strengthened> strengthRectangles;
    private ArrayList<Rhombus> rhombuses;
    private ArrayList<Strengthened> strengthRhombuses;
    private ArrayList<Square> squares;
    private ArrayList<Strengthened> strengthSquares;

    private ArrayList<CongruentCircles> congruentCircles;
    private ArrayList<ConcavePolygon> concavePolygons;

    private ArrayList<SegmentEquation> segmentEquations;
    private ArrayList<ArcEquation> arcEquations;
    private ArrayList<AngleEquation> angleEquations;
    public ArrayList<AngleEquation> getAngleEquations() { return angleEquations; }
    
    private ArrayList<AngleArcEquation> angleArcEquations;

    //
    // Aggregate structure for all computed facts
    //
    private FactComputerContainer _factContainer;
    public FactComputerContainer getFacts() { return _factContainer; }

    // 
    // All figure polygons
    //
    ArrayList<ArrayList<Polygon>> polygons;

//    public ArrayList<Strengthened> getStrengthened()
//    {
//        return strengthens;
//    }

    public FactComputer(ArrayList<Circle> c, ArrayList<Arc> a, ArrayList<Segment> s, ArrayList<Point> p, ArrayList<Sector> sec)
    {
//        strengthens = new ArrayList<Strengthened>();

        initializeLists();

        circles = c != null ? c : new ArrayList<Circle>();
        arcs = a != null ? a : new ArrayList<Arc>();
        segments = s != null ? s : new ArrayList<Segment>();
        //points = p != null ? p : new ArrayList<Point>();
        sectors = sec != null ? sec : new ArrayList<Sector>();
    }

    /**
     * 
     * @param pc -- a precomputer object
     * Initialize the fact computer directly with a precomputer;
     * Is is assumed that the precomputer 
     */
    public FactComputer(Precomputer pc)
    {
        if (!pc.analyzed())
        {
            ExceptionHandler.throwException("Precomputer analysis was not invoked prior to fact computation.");
        }
        
        //strengthens = new ArrayList<Strengthened>();

        initializeLists();

        circles = pc.getCircles();
        arcs = pc.getArcs();
        segments = pc.getSegments();
        //points = p != null ? p : new ArrayList<Point>();
        sectors = pc.getSectors();
    }
    
    public void analyze()
    {
        // Debug
        //        System.out.println(PointFactory.getAllPoints());
        
        //
        // (1) Compute all angles (resulting angles are in the AngleFactory)
        //
        GeometryFactComputerDelegate.computeAngles(this);
        
        // Debug
        //System.out.println(AngleEquivalenceRelation.getInstance());

        //
        // (2) Calculate all polygons (facilitating further analysis of polygon relationships)
        //
        PolygonCalculator polygonCalculator = new PolygonCalculator(segments);
        polygons = polygonCalculator.getPolygons();
        splitPolygonsIntoClasses(polygons);

        //
        // (3) Segment-Based Facts and Equations
        //
        // Segment Addition
        inMiddles.addAll(GeometryFactComputerDelegate.computeInMiddles(this));
        segmentEquations.addAll(GeometryFactComputerDelegate.computeSegmentAdditionEquations(this));
        // Midpoints
        GeometryFactComputerDelegate.computeMidpoints(this, midpoints, strengthMidpoints);
        segmentEquations.addAll(GeometryFactComputerDelegate.computeMidpointEquations(this));
        // Congruences
        congruentSegments.addAll(GeometryFactComputerDelegate.computeSegmentCongruences(this));
        segmentEquations.addAll(GeometryFactComputerDelegate.computeSegmentEqualityEquations(this));
        // Parallel
        parallels.addAll(GeometryFactComputerDelegate.computeParallel(this));
        // Intersections: Perpendicular, Bisector, Perpendicular Bisector
        intersections.addAll(GeometryFactComputerDelegate.computeSegmentSegmentIntersections(this));
        GeometryFactComputerDelegate.computeSpecificIntersections(this, perpendiculars, segmentBisectors, perpBisectors);
        // Proportions
        proportionalSegments.addAll(GeometryFactComputerDelegate.computeProportionalSegments(this));
        segmentEquations.addAll(GeometryFactComputerDelegate.computeProportionalSegmentEquations(this));

        //
        // (4) Angle-Based Facts
        //
        // Right Angles
        GeometryFactComputerDelegate.computeRightAngles(rightAngles,  strengthRightAngles);
        // Congruent Angles
        congruentAngles.addAll(GeometryFactComputerDelegate.computeCongruentAngles());
        angleEquations.addAll(GeometryFactComputerDelegate.computeEqualAngleEquations(this));
        // Proportional Angles
        proportionalAngles.addAll(GeometryFactComputerDelegate.computeProportionalAngles(this));
        angleEquations.addAll(GeometryFactComputerDelegate.computeProportionalAngleEquations(this));
        // Complementary, Supplementary
        complementary.addAll(GeometryFactComputerDelegate.computeComplementaryAngles());
        angleEquations.addAll(GeometryFactComputerDelegate.computeComplementaryEquations(this));
        supplementary.addAll(GeometryFactComputerDelegate.computeSupplementaryAngles());        
        angleEquations.addAll(GeometryFactComputerDelegate.computeSupplementaryEquations(this));
        // Angle Bisectors
        angleBisectors.addAll(GeometryFactComputerDelegate.computeAngleBisectors(this));
        angleEquations.addAll(GeometryFactComputerDelegate.computeAngleBisectorEquations(this));

        //
        // (4) Triangle-Based Facts
        //
        // Congruent, Similar
        congruentTriangles.addAll(GeometryFactComputerDelegate.computeCongruentTriangles(this));
        similarTriangles.addAll(GeometryFactComputerDelegate.computeSimilarTriangles(this));
        // Medians, Altitudes
        GeometryFactComputerDelegate.computeMedianAltitude(this, medians, altitudes);
        // Strengthened
        GeometryFactComputerDelegate.computeStrengthenedTriangles(this, isoscelesTriangles, strengthIsoscelesTriangles,
                equilateralTriangles, strengthEquilateralTriangles,
                rightTriangles, strengthRightTriangles);

        //
        // (4) Quadrilateral-Based Facts
        //
        GeometryFactComputerDelegate.computeStrengthenedQuadrilaterals(this, kites, strengthKites,
                rectangles, strengthRectangles,
                squares, strengthSquares,
                parallelograms, strengthParallelograms,
                trapezoids, strengthTrapezoids,
                rhombuses, strengthRhombuses,
                isoscelesTrapezoids, strengthIsoscelesTrapezoids);

        //
        // (5) Arc- and Circle-Based Facts
        //
        // Arcs
        // InMiddles
        arcInMiddles.addAll(GeometryFactComputerDelegate.computeArcInMiddles(this));
        congruentArcs.addAll(GeometryFactComputerDelegate.computeCongruentArcs(this));
        arcEquations.addAll(GeometryFactComputerDelegate.computeArcAdditionEquations(this));
        // Circles
        // Congruence
        congruentCircles.addAll(GeometryFactComputerDelegate.computeCongruentCircles(this));
        circleIntersections.addAll(GeometryFactComputerDelegate.computeCircleIntersection(this));

        //
        // (6) Dimension-based facts
        //
        angleEquations.addAll(DimensionFactComputerDelegate.computeRestrictedAngleMeasureEquations());
        
        //
        // (7) TBD: Exhaustive Equation Construction
        //
        
        
        
        // Populate the Fact Container
        setContainer();
    }

    /*
     * @ polygons -- a list of triangles, quadrilaterals, etc. (list of lists)
     * Populates the polygon lists appropriately
     */
    public void splitPolygonsIntoClasses(ArrayList<ArrayList<Polygon>> polygons)
    {
        //
        // Triangles, etc are defined in particular indices in the container
        //
        polygons.get(Polygon.TRIANGLE_INDEX).forEach(polygon->triangles.add((Triangle)polygon));
        polygons.get(Polygon.QUADRILATERAL_INDEX).forEach(polygon->quadrilaterals.add((Quadrilateral)polygon));

        // Find the concave Polygons
        for (ArrayList<Polygon> list : polygons)
        {
            for (Polygon p : list)
            {
                if (p == null) ExceptionHandler.throwException("PolygonCalculator is returning a null polygon. Why?");

                if(p instanceof ConcavePolygon) concavePolygons.add((ConcavePolygon)p);
            }
        }
    }


    //sets all the lists in the FactComputerContainer so it can be returned
    private void setContainer()
    {
        _factContainer.setAltitudes(altitudes);
        _factContainer.setAngleArcEquation(angleArcEquations);
        _factContainer.setAngleBisectors(angleBisectors);
        _factContainer.setAngleEquations(angleEquations);
        _factContainer.setAnglePairRelations(anglePairRelations);
        _factContainer.setArcs(arcs);
        _factContainer.setArcEquation(arcEquations);
        _factContainer.setCircles(circles);
        _factContainer.setCollinears(collinears);
        _factContainer.setComplementaryAngles(complementary);
        _factContainer.setConcavePolygons(concavePolygons);
        _factContainer.setCongruentAngles(congruentAngles);
        _factContainer.setCongruentArcs(congruentArcs);
        _factContainer.setCongruentCircles(congruentCircles);
        _factContainer.setCongruentSegments(congruentSegments);
        _factContainer.setCongruentTriangles(congruentTriangles);
        _factContainer.setEquilateralTriangles(equilateralTriangles);
        _factContainer.setInMiddles(inMiddles);
        _factContainer.setArcInMiddles(arcInMiddles);
        _factContainer.setIntersections(intersections);
        _factContainer.setIsoscelesTrapezoids(isoscelesTrapezoids);
        _factContainer.setIsoscelesTriangles(isoscelesTriangles);
        _factContainer.setKites(kites);
        _factContainer.setMedians(medians);
        _factContainer.setMidPoints(midpoints);
        _factContainer.setStrengthMidpoints(strengthMidpoints);
        _factContainer.setParallelograms(parallelograms);
        _factContainer.setParallels(parallels);
        _factContainer.setPerpBisectors(perpBisectors);
        _factContainer.setPerpendiculars(perpendiculars);
        _factContainer.setProportionalAngles(proportionalAngles);
        _factContainer.setQuadrilaterals(quadrilaterals);
        _factContainer.setRectangles(rectangles);
        _factContainer.setRhombuses(rhombuses);
        _factContainer.setRightAngles(rightAngles);
        _factContainer.setRightTriangles(rightTriangles);
        _factContainer.setSectors(sectors);
        _factContainer.setSegmentBisectors(segmentBisectors);
        _factContainer.setSegmentEquation(segmentEquations);
        _factContainer.setSegmentRatios(proportionalSegments);
        _factContainer.setSegments(segments);
        _factContainer.setSimilarTriangles(similarTriangles);
        _factContainer.setSquares(squares);
        _factContainer.setStrengthAngleEquations(strengthAngleEquations);
        _factContainer.setStrengthenedPerps(strengthenedPerps);
        _factContainer.setStrengthEquilateralTriangles(strengthEquilateralTriangles);
        _factContainer.setStrengthIsoscelesTrapezoids(strengthIsoscelesTrapezoids);
        _factContainer.setStrengthIsoscelesTriangles(strengthIsoscelesTriangles);
        _factContainer.setStrengthKites(strengthKites);
        _factContainer.setStrengthParallelograms(strengthParallelograms);
        _factContainer.setStrengthPerpBisectors(strengthPerpBisectors);
        _factContainer.setStrengthRectangles(strengthRectangles);
        _factContainer.setStrengthRhombuses(strengthRhombuses);
        _factContainer.setStrengthRightAngles(strengthRightAngles);
        _factContainer.setStrengthRightTriangles(strengthRightTriangles);
        _factContainer.setStrengthSegmentBisectors(strengthSegmentBisectors);
        _factContainer.setStrengthSquares(strengthSquares);
        _factContainer.setStrengthTrapezoids(strengthTrapezoids);
        _factContainer.setSupplementaryAngles(supplementary);
        _factContainer.setTrapezoids(trapezoids);
        _factContainer.setTriangles(triangles);
    }
    
    private void initializeLists()
    {
        circles = new ArrayList<Circle>();
        arcs = new ArrayList<Arc>();
        segments = new ArrayList<Segment>();
        //        points = new ArrayList<Point>();

        congruentArcs = new ArrayList<CongruentArcs>();
        angleBisectors = new ArrayList<AngleBisector>();
        altitudes = new ArrayList<Altitude>();
        intersections = new ArrayList<Intersection>();
        circleIntersections = new ArrayList<CircleIntersection>();
        parallels = new ArrayList<Parallel>();

        triangles = new ArrayList<Triangle>();
        rightTriangles =  new ArrayList<RightTriangle>();
        strengthRightTriangles = new ArrayList<Strengthened>();
        equilateralTriangles = new ArrayList<EquilateralTriangle>();
        strengthEquilateralTriangles = new ArrayList<Strengthened>();
        isoscelesTriangles = new ArrayList<IsoscelesTriangle>();
        strengthIsoscelesTriangles = new ArrayList<Strengthened>();
        congruentTriangles = new ArrayList<CongruentTriangles>();
        similarTriangles = new ArrayList<SimilarTriangles>();

        perpendiculars = new ArrayList<Perpendicular>();
        strengthenedPerps = new ArrayList<Strengthened>();
        perpBisectors = new ArrayList<PerpendicularBisector>();
        strengthPerpBisectors = new ArrayList<Strengthened>();

        inMiddles = new ArrayList<InMiddle>();
        arcInMiddles = new ArrayList<ArcInMiddle>();
        midpoints = new ArrayList<Midpoint>();
        strengthMidpoints = new ArrayList<Strengthened>();
        medians = new ArrayList<Median>();
        segmentBisectors = new ArrayList<SegmentBisector>();
        strengthSegmentBisectors = new ArrayList<Strengthened>();
        congruentSegments = new ArrayList<CongruentSegments>(); 
        proportionalSegments = new ArrayList<ProportionalSegments>();

        trapezoids = new ArrayList<Trapezoid>();
        strengthTrapezoids = new ArrayList<Strengthened>();
        isoscelesTrapezoids = new ArrayList<IsoscelesTrapezoid>();
        strengthIsoscelesTrapezoids = new ArrayList<Strengthened>();
        collinears = new ArrayList<Collinear>();

        rightAngles = new ArrayList<RightAngle>();
        strengthRightAngles = new ArrayList<Strengthened>();
        angleEquations = new ArrayList<AngleEquation>();
        strengthAngleEquations = new ArrayList<Strengthened>();
        complementary = new ArrayList<Complementary>();
        congruentAngles = new ArrayList<CongruentAngles>();
        //        angles = new ArrayList<Angle>();
        anglePairRelations = new ArrayList<AnglePairRelation>();
        supplementary =  new ArrayList<Supplementary>();
        proportionalAngles = new ArrayList<ProportionalAngles>();

        kites = new ArrayList<Kite>();
        strengthKites = new ArrayList<Strengthened>();

        quadrilaterals = new ArrayList<Quadrilateral>();
        parallelograms = new ArrayList<Parallelogram>();
        strengthParallelograms = new ArrayList<Strengthened>();
        rectangles = new ArrayList<Rectangle>();
        strengthRectangles = new ArrayList<Strengthened>();
        rhombuses = new ArrayList<Rhombus>();
        strengthRhombuses = new ArrayList<Strengthened>();
        squares = new ArrayList<Square>();
        strengthSquares = new ArrayList<Strengthened>();

        congruentCircles = new ArrayList<CongruentCircles>();
        concavePolygons = new ArrayList<ConcavePolygon>();

        segmentEquations = new ArrayList<SegmentEquation>();
        arcEquations = new ArrayList<ArcEquation>();
        angleEquations = new ArrayList<AngleEquation>();
        angleArcEquations = new ArrayList<AngleArcEquation>();

        _factContainer = new FactComputerContainer();
    }

    ArrayList<Descriptor> descriptors = new ArrayList<Descriptor>();
    public ArrayList<Descriptor> GetPrecomputedRelations()
    {
        return descriptors;
    }

    /*
     * @param that -- a segment
     * @return the structurally equivalent segment
     *         (so we maintain a single segment object in the system)
     */
    public Segment getStructuralSegment(Segment that)
    {
        for(Segment segment : segments)
        {
            if (segment.structurallyEquals(that)) return segment;
        }

        return null;
    }

    /*
     * @param that -- an arc
     * @return the structurally equivalent arc
     *         (so we maintain a single arc object in the system)
     */
    public Arc getStructuralArc(Arc that)
    {
        for(Arc arc : arcs)
        {
            if (arc.structurallyEquals(that)) return arc;
        }

        return null;
    }
}
