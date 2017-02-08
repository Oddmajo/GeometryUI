package backend.factComputer;

import java.util.ArrayList;

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
    private ArrayList<Strengthened> strengthens;
    private ArrayList<Circle> circles;
    private ArrayList<Arc> arcs;
    private ArrayList<Segment> segments;
    private ArrayList<Sector> sectors;
    
    private ArrayList<AngleBisector> angleBisctors;
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
    
    
    private ArrayList<Perpendicular> perpendiculars;
    private ArrayList<Strengthened> strengthenedPerps;
    private ArrayList<PerpendicularBisector> perpBisctors;
    private ArrayList<Strengthened> strengthPerpBisectors;
    
    private ArrayList<InMiddle> inMiddles;
    private ArrayList<Median> medians;
    private ArrayList<SegmentBisector> segmentBisectors;
    private ArrayList<Strengthened> strengthSegmentBisectors;
    private ArrayList<CongruentSegments> congruentSegments; //is this a thing?
    
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
    
    private QueryableHypergraph graph;
    
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
        
        circles = c;
        arcs = a;
        segments = s;
        points = p; //do we need?
        sectors = sec;
        graph = new QueryableHypergraph();
        calc = new PolygonCalculator(segments);
        polygons = calc.GetPolygons();
        CalculateRelations();
        setFigures();
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
                    descriptors.add(new CongruentSegments(segments.get(s1),segments.get(s2)));
                }
                
                //Parallel and Perpendicular lines
                if(segments.get(s1).isParallel(segments.get(s2)))
                {
                    descriptors.add(new Parallel(segments.get(s1),segments.get(s2)));
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
                        descriptors.add(new PerpendicularBisector(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2)), segments.get(s2)));
                    }
                    else if(intersectionPerp != null)
                    {
                        descriptors.add(new Perpendicular(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2))));
                    }
                    else if(intersectionBisec != null)
                    {
                        descriptors.add(new SegmentBisector(new Intersection(intersectionBisec, segments.get(s1), segments.get(s2)),segments.get(s2)));
                    }
                    
                    //we may have a bisector in the other direction
                    intersectionBisec = segments.get(s2).coordinateBisector(segments.get(s1));
                    if(intersectionPerp != null && intersectionBisec != null)
                    {
                                descriptors.add(new PerpendicularBisector(new Intersection(intersectionPerp, segments.get(s1), segments.get(s2)),segments.get(s1)));
                    }
                    else if(intersectionBisec != null)
                    {
                        descriptors.add(new SegmentBisector(new Intersection(intersectionBisec, segments.get(s2), segments.get(s1)), segments.get(s1)));
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
                        descriptors.add(new SegmentRatio(segments.get(s1), segments.get(s2)));
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
                    descriptors.add(new CongruentAngles(angles.get(a1),angles.get(a2)));
                }
                
                if(angles.get(a1).IsComplementaryTo(angles.get(a2)))
                {
                    descriptors.add(new Complementary(angles.get(a1),angles.get(a2)));
                }
                else if(angles.get(a1).IsSupplementaryTo(angles.get(a2)))
                {
                    descriptors.add(new Supplementary(angles.get(a1), angles.get(a2)));
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
                        descriptors.add(new ProportionalAngles(angles.get(a1), angles.get(a2)));
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
                    descriptors.add(new CongruentTriangles(corresponding.getKey(), corresponding.getValue()));
                }
                else if(triangles.get(t1).CoordinateSimilar(triangles.get(t2)))
                {
                    descriptors.add(new SimilarTriangles(triangles.get(t1), triangles.get(t2)));
                }
            }
        }
        
        
        
        //WARNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //THIS IS UNTESTED. IT SEEMS TO ONLY COMPILE IF I TAKE OUT THE TEMPLATE PARAMETER
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
                    descriptors.add(new Median(segment, tri));
                }
                
                //Altitude
                if(tri.isAltitude(segment))
                {
                    descriptors.add(new Altitude(tri,segment));
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
                        descriptors.add(new AngleBisector(angle,segment));
                    }
                }
            }
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
                    descriptors.add(new CongruentArcs(arcs.get(a1), arcs.get(a2)));
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
        for(ArrayList<Polygon> list: polygons)
        {
            
        }
    }

    
}
