package backend.factComputer;

import java.util.ArrayList;
import java.util.HashSet;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
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
import backend.hypergraph.QueryableHypergraph;
import backend.precomputer.PolygonCalculator;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;

public class FactComputer
{
    //should i even need this since we have all points in the point factory?
    private ArrayList<Point> points;
    private ArrayList<Strengthened> strengthens; //I believe this is an old concept and should be deleted
    private ArrayList<Circle> circles;
    private ArrayList<Arc> arcs;
    private ArrayList<Segment> segments;
    private ArrayList<Sector> sectors;
    
    private ArrayList<CongruentArcs> congruentArcs;
    private ArrayList<AngleBisector> angleBisectors;
    private ArrayList<Altitude> altitudes;
    private ArrayList<Intersection> intersections;
    private ArrayList<Parallel> parallels;
    
    private ArrayList<Triangle> triangles;
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
    private ArrayList<Median> medians;
    private ArrayList<SegmentBisector> segmentBisectors;
    private ArrayList<Strengthened> strengthSegmentBisectors;
    private ArrayList<CongruentSegments> congruentSegments;
    private ArrayList<SegmentRatio> segmentRatios;
    
    private ArrayList<Trapezoid> trapezoids;
    private ArrayList<Strengthened> strengthTrapezoids;
    private ArrayList<IsoscelesTrapezoid> isoscelesTrapezoids;
    private ArrayList<Strengthened> strengthIsoscelesTrapezoids;
    private ArrayList<Collinear> collinears;
    
    private ArrayList<RightAngle> rightAngles;
    private ArrayList<Strengthened> strengthRightAngles;
    private ArrayList<AngleEquation> angleEquations;
    private ArrayList<Strengthened> strengthAngleEquations;
    private ArrayList<Complementary> complementaryAngles;
    private ArrayList<CongruentAngles> congruentAngles;
    private ArrayList<Angle> angles;
    private ArrayList<AnglePairRelation> anglePairRelations;
    private ArrayList<Supplementary> supplementaryAngles;
    private ArrayList<ProportionalAngles> proportionalAngles;
    
    private ArrayList<Kite> kites;
    private ArrayList<Strengthened> strengthKites;
    
    private ArrayList<Quadrilateral> quadrilaterals;
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
    
    //private QueryableHypergraph graph;
    private FactComputerContainer container;
    
    private PolygonCalculator calc;
    ArrayList<ArrayList<Polygon>> polygons;
    
    public ArrayList<Strengthened> getStrengthened()
    {
        return strengthens;
    }
    
    public FactComputer(ArrayList<GroundedClause> figure)
    {
        strengthens = new ArrayList<Strengthened>();
    }
    public FactComputer(ArrayList<Circle> c, ArrayList<Arc> a, ArrayList<Segment> s, ArrayList<Point> p, ArrayList<Sector> sec)
    {
        strengthens = new ArrayList<Strengthened>();
        initializeLists();
        
        if(c!=null)
        {
            circles = c;
        }
        else
        {
            circles = new ArrayList<Circle>();
        }
        if(a!=null)
        {
            arcs = a;
        }
        else
        {
            arcs = new ArrayList<Arc>();
        }
        if(s!=null)
        {
            segments = s;
        }
        else
        {
            segments = new ArrayList<Segment>();
        }
        if(p!=null)
        {
            points = p; //do we need?
        }
        else
        {
            points = new ArrayList<Point>(); //my question is how do we not have points at this stage?????
        }
        if(sec!=null)
        {
            sectors = sec;
        }
        else
        {
            sectors = new ArrayList<Sector>();
        }
            
        //graph = new QueryableHypergraph();
        calc = new PolygonCalculator(segments);
        polygons = calc.GetPolygons();
        setFigures();
        CalculateRelations();
        setContainer();
    }    
    
    public FactComputerContainer getLists()
    {
        return container;
    }
    
    //sets all the lists in the FactComputerContainer so it can be returned
    private void setContainer()
    {
//        container.setAltitudes(altitudes);
//        container.setAngleBisectors(angleBisectors);
//        container.setAngleEquations(angleEquations);
//        container.setAnglePairRelations(anglePairRelations);
//        container.setAngles(angles);
//        container.setArcs(arcs);
//        container.setCircles(circles);
//        container.setCollinears(collinears);
//        container.setComplementaryAngles(complementaryAngles);
//        container.setConcavePolygons(concavePolygons);
//        container.setCongruentAngles(congruentAngles);
//        container.setCongruentArcs(congruentArcs);
//        container.setCongruentCircles(congruentCircles);
//        container.setCongruentSegments(congruentSegments);
//        container.setCongruentTriangles(congruentTriangles);
//        container.setEquilateralTriangles(equilateralTriangles);
//        container.setInMiddles(inMiddles);
//        container.setIntersections(intersections);
//        container.setIsoscelesTrapezoids(isoscelesTrapezoids);
//        container.setIsoscelesTriangles(isoscelesTriangles);
//        container.setKites(kites);
//        container.setMedians(medians);
//        container.setParallelograms(parallelograms);
//        container.setParallels(parallels);
//        container.setPerpBisectors(perpBisectors);
//        container.setPerpendiculars(perpendiculars);
//        container.setPoints(points);
//        container.setProportionalAngles(proportionalAngles);
//        container.setQuadrilaterals(quadrilaterals);
//        container.setRectangles(rectangles);
//        container.setRhombuses(rhombuses);
//        container.setRightAngles(rightAngles);
//        container.setRightTriangles(rightTriangles);
//        container.setSectors(sectors);
//        container.setSegmentBisectors(segmentBisectors);
//        container.setSegmentRatios(segmentRatios);
//        container.setSegments(segments);
//        container.setSimilarTriangles(similarTriangles);
//        container.setSquares(squares);
//        container.setStrengthAngleEquations(strengthAngleEquations);
//        container.setStrengthenedPerps(strengthenedPerps);
//        container.setStrengthens(strengthens); // I think this is old and needs to be removed
//        container.setStrengthEquilateralTriangles(strengthEquilateralTriangles);
//        container.setStrengthIsoscelesTrapezoids(strengthIsoscelesTrapezoids);
//        container.setStrengthIsoscelesTriangles(strengthIsoscelesTriangles);
//        container.setStrengthKites(strengthKites);
//        container.setStrengthParallelograms(strengthParallelograms);
//        container.setStrengthPerpBisectors(strengthPerpBisectors);
//        container.setStrengthRectangles(strengthRectangles);
//        container.setStrengthRhombuses(strengthRhombuses);
//        container.setStrengthRightAngles(strengthRightAngles);
//        container.setStrengthRightTriangles(strengthRightTriangles);
//        container.setStrengthSegmentBisectors(strengthSegmentBisectors);
//        container.setStrengthSquares(strengthSquares);
//        container.setStrengthTrapezoids(strengthTrapezoids);
//        container.setSupplementaryAngles(supplementaryAngles);
//        container.setTrapezoids(trapezoids);
//        container.setTriangles(triangles);
    }
    private void initializeLists()
    {
        circles = new ArrayList<Circle>();
        arcs = new ArrayList<Arc>();
        segments = new ArrayList<Segment>();
        points = new ArrayList<Point>();
        
        congruentArcs = new ArrayList<CongruentArcs>();
        angleBisectors = new ArrayList<AngleBisector>();
        altitudes = new ArrayList<Altitude>();
        intersections = new ArrayList<Intersection>();
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
        medians = new ArrayList<Median>();
        segmentBisectors = new ArrayList<SegmentBisector>();
        strengthSegmentBisectors = new ArrayList<Strengthened>();
        congruentSegments = new ArrayList<CongruentSegments>(); 
        segmentRatios = new ArrayList<SegmentRatio>();
        
        trapezoids = new ArrayList<Trapezoid>();
        strengthTrapezoids = new ArrayList<Strengthened>();
        isoscelesTrapezoids = new ArrayList<IsoscelesTrapezoid>();
        strengthIsoscelesTrapezoids = new ArrayList<Strengthened>();
        collinears = new ArrayList<Collinear>();
        
        rightAngles = new ArrayList<RightAngle>();
        strengthRightAngles = new ArrayList<Strengthened>();
        angleEquations = new ArrayList<AngleEquation>();
        strengthAngleEquations = new ArrayList<Strengthened>();
        complementaryAngles = new ArrayList<Complementary>();
        congruentAngles = new ArrayList<CongruentAngles>();
        angles = new ArrayList<Angle>();
        anglePairRelations = new ArrayList<AnglePairRelation>();
        supplementaryAngles =  new ArrayList<Supplementary>();
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
        
        container = new FactComputerContainer();
    }

    ArrayList<Descriptor> descriptors = new ArrayList<Descriptor>();
    public ArrayList<Descriptor> GetPrecomputedRelations()
    {
        return descriptors;
    }
    
    public void CalculateRelations()
    {
        //Segment, Parallel, and Perpendicular, and congruences
        for(int s1 = 0; s1 < segments.size(); s1++)
        {
            for(int s2 = s1 + 1; s2 < segments.size(); s2++)
            {
                //Congruence
                if(segments.get(s1).coordinateCongruent(segments.get(s2)))
                {
                    congruentSegments.add(new CongruentSegments(segments.get(s1),segments.get(s2)));
                }
                
                //Parallel
                if(segments.get(s1).isParallel(segments.get(s2)))
                {
                    parallels.add(new Parallel(segments.get(s1),segments.get(s2)));
                }
                
                //Perpendicular, bisector, perpendicular bisector
                else
                {
                    //these are the general intersection points between the endpoints or on the endpoints of the segments (in some cases)
                    Point intersectionPerp = segments.get(s1).coordinatePerpendicular(segments.get(s2));
                    // is segment[s2] a bisector of segment[s1]?
                    Point intersectionBisec = segments.get(s1).coordinateBisector(segments.get(s2));//return the actual intersection point
                    if(intersectionPerp != null && intersectionBisec != null)
                    {
                        perpBisectors.add(new PerpendicularBisector(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2)), segments.get(s2)));
                    }
                    else if(intersectionPerp != null)
                    {
                        perpendiculars.add(new Perpendicular(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2))));
                    }
                    else if(intersectionBisec != null)
                    {
                        segmentBisectors.add(new SegmentBisector(new Intersection(intersectionBisec, segments.get(s1), segments.get(s2)),segments.get(s2)));
                    }
                    
                    //we may have a bisector in the other direction
                    intersectionBisec = segments.get(s2).coordinateBisector(segments.get(s1));
                    if(intersectionPerp != null && intersectionBisec != null)
                    {
                                perpBisectors.add(new PerpendicularBisector(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2)),segments.get(s1)));
                    }
                    else if(intersectionBisec != null)
                    {
                        segmentBisectors.add(new SegmentBisector(new Intersection(intersectionBisec, segments.get(s2), segments.get(s1)), segments.get(s1)));
                    }
                }
                
                //Proportional line segments
                //just generate if the ratio is really an integer or half-step
                Pair<Integer,Integer> proportion = segments.get(s1).coordinateProportional(segments.get(s2));
                if(proportion.getValue() != -1)
                {
                    if(proportion.getValue() <=2 || proportion.getKey() <= 2)
                    {
                        if(Utilities.DEBUG)
                        {
                            //System.Diagnostics.Debug.WriteLine("< " + proportion.getKey() + ", " + proportion.getValue() + " >: " + segments.get(s1) + " : " + segments.get(s2));
                            ExceptionHandler.throwException(new DebugException("< " + proportion.getKey() + ", " + proportion.getValue() + " >: " + segments.get(s1) + " : " + segments.get(s2)));
                        }
                        segmentRatios.add(new SegmentRatio(segments.get(s1), segments.get(s2)));
                    }
                }
            }
        }
        
        //Angle congruences; complementary and supplementary
        for(int a1 = 0; a1 < angles.size(); a1++)
        {
            for(int a2 = a1 +1; a2< angles.size(); a2++)
            {
                if(angles.get(a1).CoordinateCongruent(angles.get(a2)) && !Utilities.CompareValues(angles.get(a1).getMeasure(), 180))
                {
                    congruentAngles.add(new CongruentAngles(angles.get(a1),angles.get(a2)));
                }
                
                if(angles.get(a1).IsComplementaryTo(angles.get(a2)))
                {
                    complementaryAngles.add(new Complementary(angles.get(a1),angles.get(a2)));
                }
                else if(angles.get(a1).IsSupplementaryTo(angles.get(a2)))
                {
                    supplementaryAngles.add(new Supplementary(angles.get(a1), angles.get(a2)));
                }
                
                //Proportional angle measure
                //just generate if the ratio is really an integer or half-step
                Pair<Integer,Integer> proportion = angles.get(a1).CoordinateProportional(angles.get(a2));
                if(proportion.getValue() != -1)
                {
                    if(proportion.getValue() <= 2 || proportion.getKey() <= 2)
                    {
                        if(Utilities.DEBUG)
                        {
                            //System.Diagnostics.Debu.WriteLine("< " + proportion.getKey() + ", " + proportion.getValue() + " >: " + angles.get(a1) + " : " + angles.get(a2));
                            ExceptionHandler.throwException(new DebugException("< " + proportion.getKey() + ", " + proportion.getValue() + " >: " + angles.get(a1) + " : " + angles.get(a2)));
                        }
                        proportionalAngles.add(new ProportionalAngles(angles.get(a1), angles.get(a2)));
                    }
                }
            }
        }
        
        //
        // Triangle congruences OR similarity (congruence is a stronger relationship than similarity)
        //
        for (int t1 = 0; t1< triangles.size(); t1++)
        {
            for(int t2 = t1 +1; t2 < triangles.size(); t2++)
            {
                Pair<Triangle,Triangle> corresponding = triangles.get(t1).CoordinateCongruent(triangles.get(t2));
                if(corresponding.getKey() != null && corresponding.getValue() != null)
                {
                    congruentTriangles.add(new CongruentTriangles(corresponding.getKey(), corresponding.getValue()));
                }
                else if(triangles.get(t1).CoordinateSimilar(triangles.get(t2)))
                {
                    similarTriangles.add(new SimilarTriangles(triangles.get(t1), triangles.get(t2)));
                }
            }
        }
        
        
        
        
//        //Arc congruences
//        CalculateArcCongruences(minorArcs);
//        CalculateArcCongruences(majorArcs);
//        CalculateArcCongruences(semiCircles);
        
        //
        // Calculate all segment relations to triangles: bisector, median, altitude, perpendicular bisector
        //
        for(Triangle tri : triangles)
        {
            for(Segment segment : segments)
            {
                //median
                if(tri.isMedian(segment))
                {
                    medians.add(new Median(segment, tri));
                }
                
                //Altitude
                if(tri.isAltitude(segment))
                {
                    altitudes.add(new Altitude(tri,segment));
                }
            }
            for(Strengthened s : Triangle.canBeStrengthened(tri))
            {
                if(s.getStrengthened() instanceof IsoscelesTriangle)
                {
                    strengthIsoscelesTriangles.add(s);
                    isoscelesTriangles.add((IsoscelesTriangle)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof EquilateralTriangle)
                {
                    strengthEquilateralTriangles.add(s);
                    equilateralTriangles.add((EquilateralTriangle)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof RightTriangle)
                {
                    strengthRightTriangles.add(s);
                    rightTriangles.add((RightTriangle)s.getStrengthened());
                }
            }
        }
        
        //calculate ande bisectors
        for(Angle angle : angles)
        {
            if(!Utilities.CompareValues(angle.getMeasure(),  180))
            {
                for(Segment segment : segments)
                {
                    //angle bisector
                    if(angle.CoordinateAngleBisector(segment))
                    {
                        angleBisectors.add(new AngleBisector(angle,segment));
                    }
                }
            }
        }
        
        for(Quadrilateral quad : quadrilaterals)
        {
            for(Strengthened s : Quadrilateral.CanBeStrengthened(quad))
            {
                if(s.getStrengthened() instanceof Kite)
                {
                    strengthKites.add(s);
                    kites.add((Kite)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof Rectangle)
                {
                    strengthRectangles.add(s);
                    rectangles.add((Rectangle)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof Square)
                {
                    strengthSquares.add(s);
                    squares.add((Square)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof Parallelogram)
                {
                    strengthParallelograms.add(s);
                    parallelograms.add((Parallelogram)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof Trapezoid)
                {
                    strengthTrapezoids.add(s);
                    trapezoids.add((Trapezoid)s.getStrengthened());
                }
               
                else if(s.getStrengthened() instanceof Rhombus)
                {
                    strengthRhombuses.add(s);
                    rhombuses.add((Rhombus)s.getStrengthened());
                }
                else if(s.getStrengthened() instanceof IsoscelesTrapezoid)
                {
                    strengthIsoscelesTrapezoids.add(s);
                    isoscelesTrapezoids.add((IsoscelesTrapezoid)s.getStrengthened());
                }
            }
        }
        
        for(Triangle t : triangles)
        {
            
        }
        
        //Dumping the relations
        if(Utilities.DEBUG)
        {
            //System.Diagnostics.Debug.WriteLine("Precomputed Relations");
            ExceptionHandler.throwException(new DebugException("Precomputer Relations"));
            for(Descriptor descriptor : descriptors)
            {
                //System.Diagnostics.Debug.WriteLine(descriptors.toString());
                ExceptionHandler.throwException(new DebugException(descriptors.toString()));
            }
            
        }
    }
    
    //slightly changed from C# but this should be alright
    //CAN THIS EVER NOT BE AN ARC? IF NOT WHY WAS IT TYPE T?
    private <T extends Arc> void CalculateArcCongruences(ArrayList<T> arcs)
    {
        for(int a1 = 0; a1< arcs.size(); a1++)
        {
            for(int a2 = a1+1; a2 < arcs.size(); a2++)
            {
                if(arcs.get(a1).CoordinateCongruent(arcs.get(a2)))
                { 
                    congruentArcs.add(new CongruentArcs(arcs.get(a1), arcs.get(a2)));
                }
            }
        }
    }
    
    //can we determine any stregnthening in the figure class (scalene to equilateral, etc)
    ArrayList<Strengthened> strengthened = new ArrayList<Strengthened>();
    public ArrayList<Strengthened> GetStrengthenedClauses()
    {
        return strengthened;
    }
    
    //need to calculate and then split them accordingly
    public void CalculateStrengthening()
    {
        //
        // Can a quadrilateral be strenghtened? Quad -> trapezoid, Quad -> Parallelogram?, etc.
        //
        for(Quadrilateral quad : quadrilaterals)
        {
            //strengthened.addAll(Quadrilateral.CanBeStrengthened(quad));
            for(Strengthened obj : Quadrilateral.CanBeStrengthened(quad))
            {
                if(obj.getStrengthened() instanceof Parallelogram)
                {
                    strengthParallelograms.add(obj);
                    parallelograms.add((Parallelogram)obj.getStrengthened());
                }
                else if(obj.getStrengthened() instanceof Rectangle)
                {
                    strengthRectangles.add(obj);
                    rectangles.add((Rectangle)obj.getStrengthened());
                }
                else if(obj.getStrengthened() instanceof Rhombus)
                {
                    strengthRhombuses.add(obj);
                    rhombuses.add((Rhombus)obj.getStrengthened());
                }
                else if(obj.getStrengthened() instanceof Square)
                {
                    strengthSquares.add(obj);
                    squares.add((Square)obj.getStrengthened());
                }
            }
        }
        
        //can a triangle be strengthened? scalene -> isosceles -> equilateral?
        for(Triangle t : triangles)
        {
            //strengthened.addAll(Triangle.canBeStrengthened(t));
            for(Strengthened obj : Triangle.canBeStrengthened(t))
            {
                if(obj.getStrengthened() instanceof RightTriangle)
                {
                    strengthRightTriangles.add(obj);
                    rightTriangles.add((RightTriangle)obj.getStrengthened());
                }
                else if(obj.getStrengthened() instanceof EquilateralTriangle)
                {
                    strengthEquilateralTriangles.add(obj);
                    equilateralTriangles.add((EquilateralTriangle)obj.getStrengthened());
                }
                else if(obj.getStrengthened() instanceof IsoscelesTriangle)
                {
                    strengthIsoscelesTriangles.add(obj);
                    isoscelesTriangles.add((IsoscelesTriangle)obj.getStrengthened());
                }
                
            }
        }
        
        
        
        
        //This needs to be changed to new Lists
        //can an inMiddle relationship be classified as a midpoint?
        for(InMiddle im : inMiddles)
        {
            Strengthened s = im.canBeStrengthened();
            if(s!=null)
            {
                strengthened.add(s);
            }
        }
        
        //right angles
        for(Angle angle : angles)
        {
            if(Utilities.CompareValues(angle.getMeasure(), 90))
            {
                //is this okay?
                RightAngle a = new RightAngle(angle);
                strengthRightAngles.add(new Strengthened(angle, a));
                rightAngles.add(a);
            }
        }
        
        //Dumping the strengthening
        if(Utilities.DEBUG)
        {
            //System.Diagnostics.Debug.WriteLine("Precomputed Strengthening");
            ExceptionHandler.throwException(new DebugException("Precomputer Strengthening"));
            for(Strengthened s : strengthened)
            { 
                //System.Diagnostics.Debug.WriteLine(s.toString());
                ExceptionHandler.throwException(new DebugException(s.toString()));
            }
        }
    }
    
    public void setFigures()
    {
//        for(ArrayList<Polygon> list: polygons)
//        {
//            System.out.println(list.toString());
//        }
//        for(Segment s : segments)
//        {
//            System.out.println(s.toPrettyString());
//        }
//        for(Point p : points)
//        {
//            System.out.println(p.toString());
//        }
//        for(InMiddle m: inMiddles)
//        {
//            System.out.println(" here" + m.toString());
//        }
        for(ArrayList<Polygon> list: polygons)
        {
            for(Polygon p : list)
            {
                if(p != null)
                {
                    if(p instanceof Triangle)
                    {
                        triangles.add((Triangle)p);
                    }
                    else if(p instanceof Quadrilateral)
                    {
                        quadrilaterals.add((Quadrilateral)p);
                    }
                    else if(p instanceof ConcavePolygon)
                    {
                        concavePolygons.add((ConcavePolygon)p);
                    }
                }
            }
        }
    }

    
}
