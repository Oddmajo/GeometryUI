package backend.factComputer;

import java.util.ArrayList;
import java.util.HashSet;

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
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Relations.AlgebraicSimilarTriangles;
import backend.ast.Descriptors.Relations.GeometricSimilarTriangles;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentCircles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
import backend.ast.Descriptors.parallel.AlgebraicParallel;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.polygon.ConcavePolygon;
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
import backend.symbolicAlgebra.equations.AngleArcEquation;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.ArcEquation;
import backend.symbolicAlgebra.equations.SegmentEquation;

public class FactComputerContainer
{
    private ArrayList<Point> points;
    //private ArrayList<Strengthened> strengthens;
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
    private ArrayList<ArcInMiddle> arcInMiddles;
    private ArrayList<Midpoint> midpoints;
    private ArrayList<Strengthened> strengthMidPoints;
    private ArrayList<Median> medians;
    private ArrayList<SegmentBisector> segmentBisectors;
    private ArrayList<Strengthened> strengthSegmentBisectors;
    private ArrayList<CongruentSegments> congruentSegments;
    private ArrayList<ProportionalSegments> segmentRatios;
    
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
    
    private ArrayList<GeometricParallel> geometricParallels;
    private ArrayList<AlgebraicParallel> algebraicParallels;
    private ArrayList<GeometricSimilarTriangles> geometricSimilarTriangles;
    private ArrayList<AlgebraicSimilarTriangles> algebraicSimilarTriangles;
    private ArrayList<SegmentEquation> segmentEquations;
    private ArrayList<ArcEquation> arcEquations;
    private ArrayList<AngleArcEquation> angleArcEquations;
    
    public ArrayList<HashSet<GroundedClause>> getAllLists()
    {
        ArrayList<HashSet<GroundedClause>> master = new ArrayList<HashSet<GroundedClause>>();
        
        master.add(new HashSet<>(points));
        //master.add(new HashSet<>(strengthens)); //I think this is an old concept
        master.add(new HashSet<>(circles));
        master.add(new HashSet<>(arcs));
        master.add(new HashSet<>(segments));
        
        master.add(new HashSet<>(sectors));
        master.add(new HashSet<>(congruentArcs));
        master.add(new HashSet<>(angleBisectors));
        master.add(new HashSet<>(altitudes));
        master.add(new HashSet<>(intersections));
        
        master.add(new HashSet<>(parallels));
        master.add(new HashSet<>(triangles));
        master.add(new HashSet<>(rightTriangles));
        master.add(new HashSet<>(strengthRightTriangles));
        master.add(new HashSet<>(equilateralTriangles));
        
        master.add(new HashSet<>(strengthEquilateralTriangles));
        master.add(new HashSet<>(isoscelesTriangles));
        master.add(new HashSet<>(strengthIsoscelesTriangles));
        master.add(new HashSet<>(congruentTriangles));
        master.add(new HashSet<>(similarTriangles));
        
        master.add(new HashSet<>(perpendiculars));
        master.add(new HashSet<>(strengthenedPerps));
        master.add(new HashSet<>(perpBisectors));
        master.add(new HashSet<>(strengthPerpBisectors));
        master.add(new HashSet<>(inMiddles));
        master.add(new HashSet<>(arcInMiddles));
        master.add(new HashSet<>(strengthMidPoints));
        
        master.add(new HashSet<>(medians));
        master.add(new HashSet<>(segmentBisectors));
        master.add(new HashSet<>(strengthSegmentBisectors));
        master.add(new HashSet<>(congruentSegments));
        master.add(new HashSet<>(segmentRatios));
        
        master.add(new HashSet<>(trapezoids));
        master.add(new HashSet<>(strengthTrapezoids));
        master.add(new HashSet<>(isoscelesTrapezoids));
        master.add(new HashSet<>(strengthIsoscelesTrapezoids));
        master.add(new HashSet<>(collinears));
        
        master.add(new HashSet<>(rightAngles));
        master.add(new HashSet<>(strengthRightAngles));
        master.add(new HashSet<>(angleEquations));
        master.add(new HashSet<>(strengthAngleEquations));
        master.add(new HashSet<>(complementaryAngles));
        
        master.add(new HashSet<>(congruentAngles));
        master.add(new HashSet<>(angles));
        master.add(new HashSet<>(anglePairRelations));
        master.add(new HashSet<>(supplementaryAngles));
        master.add(new HashSet<>(proportionalAngles));
        
        master.add(new HashSet<>(kites));
        master.add(new HashSet<>(strengthKites));
        master.add(new HashSet<>(quadrilaterals));
        master.add(new HashSet<>(parallelograms));
        master.add(new HashSet<>(strengthParallelograms));
        
        master.add(new HashSet<>(rectangles));
        master.add(new HashSet<>(strengthRectangles));
        master.add(new HashSet<>(rhombuses));
        master.add(new HashSet<>(strengthRhombuses));
        master.add(new HashSet<>(squares));
        
        master.add(new HashSet<>(strengthSquares));
        master.add(new HashSet<>(congruentCircles));
        master.add(new HashSet<>(concavePolygons));
        master.add(new HashSet<>(midpoints));
        
        master.add(new HashSet<>(geometricParallels));
        master.add(new HashSet<>(algebraicParallels));
        master.add(new HashSet<>(geometricSimilarTriangles));
        master.add(new HashSet<>(algebraicSimilarTriangles));
        master.add(new HashSet<>(segmentEquations));
        
        master.add(new HashSet<>(arcEquations));
        master.add(new HashSet<>(angleArcEquations));
        
        return master;
    }
    
    public HashSet<Point> getPoints()
    {
        return new HashSet(points);
    }


    public void setPoints(ArrayList<Point> points)
    {
        this.points = points;
    }


//    public HashSet<Strengthened> getStrengthens()
//    {
//        return new HashSet<Strengthened>(strengthens);
//    }
//
//
//    public void setStrengthens(ArrayList<Strengthened> strengthens)
//    {
//        this.strengthens = strengthens;
//    }


    public HashSet<Circle> getCircles()
    {
        return  new HashSet<Circle>(circles);
    }


    public void setCircles(ArrayList<Circle> circles)
    {
        this.circles = circles;
    }


    public HashSet<Arc> getArcs()
    {
        return new HashSet<Arc>(arcs);
    }


    public void setArcs(ArrayList<Arc> arcs)
    {
        this.arcs = arcs;
    }


    public HashSet<Segment> getSegments()
    {
        return new HashSet<Segment>(segments);
    }


    public void setSegments(ArrayList<Segment> segments)
    {
        this.segments = segments;
    }


    public HashSet<Sector> getSectors()
    {
        HashSet<Sector> s = new HashSet<Sector>(sectors);
        return s;
    }


    public void setSectors(ArrayList<Sector> sectors)
    {
        this.sectors = sectors;
    }


    public HashSet<CongruentArcs> getCongruentArcs()
    {
        HashSet<CongruentArcs> c = new HashSet<CongruentArcs>(congruentArcs);
        return c;
    }


    public void setCongruentArcs(ArrayList<CongruentArcs> congruentArcs)
    {
        this.congruentArcs = congruentArcs;
    }


    public HashSet<AngleBisector> getAngleBisectors()
    {
        HashSet<AngleBisector> a = new HashSet<AngleBisector>(angleBisectors);
        return a;
    }


    public void setAngleBisectors(ArrayList<AngleBisector> angleBisectors)
    {
        this.angleBisectors = angleBisectors;
    }


    public HashSet<Altitude> getAltitudes()
    {
        HashSet<Altitude> a = new HashSet<Altitude>(altitudes);
        return a;
    }


    public void setAltitudes(ArrayList<Altitude> altitudes)
    {
        this.altitudes = altitudes;
    }


    public HashSet<Intersection> getIntersections()
    {
        return new HashSet<Intersection>(intersections);
    }


    public void setIntersections(ArrayList<Intersection> intersections)
    {
        this.intersections = intersections;
    }


    public HashSet<Parallel> getParallels()
    {
        return new HashSet<Parallel>(parallels);
    }


    public void setParallels(ArrayList<Parallel> parallels)
    {
        this.parallels = parallels;
    }


    public HashSet<Triangle> getTriangles()
    {
        return new HashSet<Triangle>(triangles);
    }


    public void setTriangles(ArrayList<Triangle> triangles)
    {
        this.triangles = triangles;
    }


    public HashSet<RightTriangle> getRightTriangles()
    {
        return new HashSet(rightTriangles);
    }


    public void setRightTriangles(ArrayList<RightTriangle> rightTriangles)
    {
        this.rightTriangles = rightTriangles;
    }


    public HashSet<Strengthened> getStrengthRightTriangles()
    {
        return new HashSet<Strengthened>(strengthRightTriangles);
    }


    public void setStrengthRightTriangles(ArrayList<Strengthened> strengthRightTriangles)
    {
        this.strengthRightTriangles = strengthRightTriangles;
    }


    public HashSet<EquilateralTriangle> getEquilateralTriangles()
    {
        return new HashSet<EquilateralTriangle>(equilateralTriangles);
    }


    public void setEquilateralTriangles(ArrayList<EquilateralTriangle> equilateralTriangles)
    {
        this.equilateralTriangles = equilateralTriangles;
    }


    public HashSet<Strengthened> getStrengthEquilateralTriangles()
    {
        return new HashSet<Strengthened>(strengthEquilateralTriangles);
    }


    public void setStrengthEquilateralTriangles(ArrayList<Strengthened> strengthEquilateralTriangles)
    {
        this.strengthEquilateralTriangles = strengthEquilateralTriangles;
    }


    public HashSet<IsoscelesTriangle> getIsoscelesTriangles()
    {
        return new HashSet<IsoscelesTriangle>(isoscelesTriangles);
    }


    public void setIsoscelesTriangles(ArrayList<IsoscelesTriangle> isoscelesTriangles)
    {
        this.isoscelesTriangles = isoscelesTriangles;
    }


    public HashSet<Strengthened> getStrengthIsoscelesTriangles()
    {
        return new HashSet<Strengthened>(strengthIsoscelesTriangles);
    }


    public void setStrengthIsoscelesTriangles(ArrayList<Strengthened> strengthIsoscelesTriangles)
    {
        this.strengthIsoscelesTriangles = strengthIsoscelesTriangles;
    }


    public HashSet<CongruentTriangles> getCongruentTriangles()
    {
        return new HashSet<CongruentTriangles>(congruentTriangles);
    }


    public void setCongruentTriangles(ArrayList<CongruentTriangles> congruentTriangles)
    {
        this.congruentTriangles = congruentTriangles;
    }


    public HashSet<SimilarTriangles> getSimilarTriangles()
    {
        return new HashSet<SimilarTriangles>(similarTriangles);
    }


    public void setSimilarTriangles(ArrayList<SimilarTriangles> similarTriangles)
    {
        this.similarTriangles = similarTriangles;
    }


    public HashSet<Perpendicular> getPerpendiculars()
    {
        return new HashSet<Perpendicular>(perpendiculars);
    }


    public void setPerpendiculars(ArrayList<Perpendicular> perpendiculars)
    {
        this.perpendiculars = perpendiculars;
    }


    public HashSet<Strengthened> getStrengthenedPerps()
    {
        return new HashSet<Strengthened>(strengthenedPerps);
    }


    public void setStrengthenedPerps(ArrayList<Strengthened> strengthenedPerps)
    {
        this.strengthenedPerps = strengthenedPerps;
    }


    public HashSet<PerpendicularBisector> getPerpBisectors()
    {
        return new HashSet<PerpendicularBisector>(perpBisectors);
    }


    public void setPerpBisectors(ArrayList<PerpendicularBisector> perpBisectors)
    {
        this.perpBisectors = perpBisectors;
    }


    public HashSet<Strengthened> getStrengthPerpBisectors()
    {
        return new HashSet<Strengthened>(strengthPerpBisectors);
    }


    public void setStrengthPerpBisectors(ArrayList<Strengthened> strengthPerpBisectors)
    {
        this.strengthPerpBisectors = strengthPerpBisectors;
    }


    public HashSet<InMiddle> getInMiddles()
    {
        return new HashSet<InMiddle>(inMiddles);
    }


    public void setInMiddles(ArrayList<InMiddle> inMiddles)
    {
        this.inMiddles = inMiddles;
    }
    
    public HashSet<ArcInMiddle> getArcInMiddles()
    {
        return new HashSet<ArcInMiddle>(arcInMiddles);
    }    

    public void setArcInMiddles(ArrayList<ArcInMiddle> arcInMiddles)
    {
        this.arcInMiddles = arcInMiddles;
    }
    
    public HashSet<Midpoint> getMidPoints()
    {
        return new HashSet<Midpoint>(midpoints);
    }
    
    public void setMidPoints(ArrayList<Midpoint> midpoints)
    {
        this.midpoints = midpoints;
    }

    public HashSet<Strengthened> getStrengthMidPoints()
    {
        return new HashSet<Strengthened>(strengthMidPoints);
    }
    
    public void setStrengthMidpoints(ArrayList<Strengthened> _sMidPoints)
    {
        this.strengthMidPoints = _sMidPoints;
    }
    public HashSet<Median> getMedians()
    {
        return new HashSet<Median>(medians);
    }


    public void setMedians(ArrayList<Median> medians)
    {
        this.medians = medians;
    }


    public HashSet<SegmentBisector> getSegmentBisectors()
    {
        return new HashSet<SegmentBisector>(segmentBisectors);
    }


    public void setSegmentBisectors(ArrayList<SegmentBisector> segmentBisectors)
    {
        this.segmentBisectors = segmentBisectors;
    }


    public HashSet<Strengthened> getStrengthSegmentBisectors()
    {
        return new HashSet<Strengthened>(strengthSegmentBisectors);
    }


    public void setStrengthSegmentBisectors(ArrayList<Strengthened> strengthSegmentBisectors)
    {
        this.strengthSegmentBisectors = strengthSegmentBisectors;
    }


    public HashSet<CongruentSegments> getCongruentSegments()
    {
        return new HashSet<CongruentSegments>(congruentSegments);
    }


    public void setCongruentSegments(ArrayList<CongruentSegments> congruentSegments)
    {
        this.congruentSegments = congruentSegments;
    }


    public HashSet<ProportionalSegments> getSegmentRatios()
    {
        return new HashSet<ProportionalSegments>(segmentRatios);
    }


    public void setSegmentRatios(ArrayList<ProportionalSegments> segmentRatios)
    {
        this.segmentRatios = segmentRatios;
    }


    public HashSet<Trapezoid> getTrapezoids()
    {
        return new HashSet<Trapezoid>(trapezoids);
    }


    public void setTrapezoids(ArrayList<Trapezoid> trapezoids)
    {
        this.trapezoids = trapezoids;
    }


    public HashSet<Strengthened> getStrengthTrapezoids()
    {
        return new HashSet<Strengthened>(strengthTrapezoids);
    }


    public void setStrengthTrapezoids(ArrayList<Strengthened> strengthTrapezoids)
    {
        this.strengthTrapezoids = strengthTrapezoids;
    }


    public HashSet<IsoscelesTrapezoid> getIsoscelesTrapezoids()
    {
        return new HashSet<IsoscelesTrapezoid>(isoscelesTrapezoids);
    }


    public void setIsoscelesTrapezoids(ArrayList<IsoscelesTrapezoid> isoscelesTrapezoids)
    {
        this.isoscelesTrapezoids = isoscelesTrapezoids;
    }


    public HashSet<Strengthened> getStrengthIsoscelesTrapezoids()
    {
        return new HashSet<Strengthened>(strengthIsoscelesTrapezoids);
    }


    public void setStrengthIsoscelesTrapezoids(ArrayList<Strengthened> strengthIsoscelesTrapezoids)
    {
        this.strengthIsoscelesTrapezoids = strengthIsoscelesTrapezoids;
    }


    public HashSet<Collinear> getCollinears()
    {
        return new HashSet<Collinear>(collinears);
    }


    public void setCollinears(ArrayList<Collinear> collinears)
    {
        this.collinears = collinears;
    }


    public HashSet<RightAngle> getRightAngles()
    {
        return new HashSet<RightAngle>(rightAngles);
    }


    public void setRightAngles(ArrayList<RightAngle> rightAngles)
    {
        this.rightAngles = rightAngles;
    }


    public HashSet<Strengthened> getStrengthRightAngles()
    {
        return new HashSet<Strengthened>(strengthRightAngles);
    }


    public void setStrengthRightAngles(ArrayList<Strengthened> strengthRightAngles)
    {
        this.strengthRightAngles = strengthRightAngles;
    }


    public HashSet<AngleEquation> getAngleEquations()
    {
        return new HashSet<AngleEquation>(angleEquations);
    }


    public void setAngleEquations(ArrayList<AngleEquation> angleEquations)
    {
        this.angleEquations = angleEquations;
    }


    public HashSet<Strengthened> getStrengthAngleEquations()
    {
        return new HashSet<Strengthened>(strengthAngleEquations);
    }


    public void setStrengthAngleEquations(ArrayList<Strengthened> strengthAngleEquations)
    {
        this.strengthAngleEquations = strengthAngleEquations;
    }


    public HashSet<Complementary> getComplementaryAngles()
    {
        return new HashSet<Complementary>(complementaryAngles);
    }


    public void setComplementaryAngles(ArrayList<Complementary> complementaryAngles)
    {
        this.complementaryAngles = complementaryAngles;
    }


    public HashSet<CongruentAngles> getCongruentAngles()
    {
        return new HashSet<CongruentAngles>(congruentAngles);
    }


    public void setCongruentAngles(ArrayList<CongruentAngles> congruentAngles)
    {
        this.congruentAngles = congruentAngles;
    }


    public HashSet<Angle> getAngles()
    {
        return new HashSet<Angle>(angles);
    }


    public void setAngles(ArrayList<Angle> angles)
    {
        this.angles = angles;
    }


    public HashSet<AnglePairRelation> getAnglePairRelations()
    {
        return new HashSet<AnglePairRelation>(anglePairRelations);
    }


    public void setAnglePairRelations(ArrayList<AnglePairRelation> anglePairRelations)
    {
        this.anglePairRelations = anglePairRelations;
    }


    public HashSet<Supplementary> getSupplementaryAngles()
    {
        return new HashSet<Supplementary>(supplementaryAngles);
    }


    public void setSupplementaryAngles(ArrayList<Supplementary> supplementaryAngles)
    {
        this.supplementaryAngles = supplementaryAngles;
    }


    public HashSet<ProportionalAngles> getProportionalAngles()
    {
        return new HashSet<ProportionalAngles>(proportionalAngles);
    }


    public void setProportionalAngles(ArrayList<ProportionalAngles> proportionalAngles)
    {
        this.proportionalAngles = proportionalAngles;
    }


    public HashSet<Kite> getKites()
    {
        return new HashSet<Kite>(kites);
    }


    public void setKites(ArrayList<Kite> kites)
    {
        this.kites = kites;
    }


    public HashSet<Strengthened> getStrengthKites()
    {
        return new HashSet<Strengthened>(strengthKites);
    }


    public void setStrengthKites(ArrayList<Strengthened> strengthKites)
    {
        this.strengthKites = strengthKites;
    }


    public HashSet<Quadrilateral> getQuadrilaterals()
    {
        return new HashSet<Quadrilateral>(quadrilaterals);
    }


    public void setQuadrilaterals(ArrayList<Quadrilateral> quadrilaterals)
    {
        this.quadrilaterals = quadrilaterals;
    }


    public HashSet<Parallelogram> getParallelograms()
    {
        return new HashSet<Parallelogram>(parallelograms);
    }


    public void setParallelograms(ArrayList<Parallelogram> parallelograms)
    {
        this.parallelograms = parallelograms;
    }


    public HashSet<Strengthened> getStrengthParallelograms()
    {
        return new HashSet<Strengthened>(strengthParallelograms);
    }


    public void setStrengthParallelograms(ArrayList<Strengthened> strengthParallelograms)
    {
        this.strengthParallelograms = strengthParallelograms;
    }


    public HashSet<Rectangle> getRectangles()
    {
        return new HashSet<Rectangle>(rectangles);
    }


    public void setRectangles(ArrayList<Rectangle> rectangles)
    {
        this.rectangles = rectangles;
    }


    public HashSet<Strengthened> getStrengthRectangles()
    {
        return new HashSet<Strengthened>(strengthRectangles);
    }


    public void setStrengthRectangles(ArrayList<Strengthened> strengthRectangles)
    {
        this.strengthRectangles = strengthRectangles;
    }


    public HashSet<Rhombus> getRhombuses()
    {
        return new HashSet<Rhombus>(rhombuses);
    }


    public void setRhombuses(ArrayList<Rhombus> rhombuses)
    {
        this.rhombuses = rhombuses;
    }


    public HashSet<Strengthened> getStrengthRhombuses()
    {
        return new HashSet<Strengthened>(strengthRhombuses);
    }


    public void setStrengthRhombuses(ArrayList<Strengthened> strengthRhombuses)
    {
        this.strengthRhombuses = strengthRhombuses;
    }


    public HashSet<Square> getSquares()
    {
        return new HashSet<Square>(squares);
    }


    public void setSquares(ArrayList<Square> squares)
    {
        this.squares = squares;
    }


    public HashSet<Strengthened> getStrengthSquares()
    {
        return new HashSet<Strengthened>(strengthSquares);
    }


    public void setStrengthSquares(ArrayList<Strengthened> strengthSquares)
    {
        this.strengthSquares = strengthSquares;
    }


    public HashSet<CongruentCircles> getCongruentCircles()
    {
        return new HashSet<CongruentCircles>(congruentCircles);
    }


    public void setCongruentCircles(ArrayList<CongruentCircles> congruentCircles)
    {
        this.congruentCircles = congruentCircles;
    }


    public HashSet<ConcavePolygon> getConcavePolygons()
    {
        return new HashSet<ConcavePolygon>(concavePolygons);
    }


    public void setConcavePolygons(ArrayList<ConcavePolygon> concavePolygons)
    {
        this.concavePolygons = concavePolygons;
    }

    public HashSet<GeometricParallel> getGeometricParallels()
    {
        return new HashSet<GeometricParallel>(geometricParallels);
    }
    
    public void setGeometricParallels(ArrayList<GeometricParallel> geometricParallels)
    {
        this.geometricParallels = geometricParallels;
    }

    public HashSet<AlgebraicParallel> getAlgebraicParallels()
    {
        return new HashSet<AlgebraicParallel>(algebraicParallels);
    }
    
    public void setAlgebraicParallels(ArrayList<AlgebraicParallel> algebraicParallels)
    {
        this.algebraicParallels = algebraicParallels;
    }
    
    public HashSet<GeometricSimilarTriangles> getGeometricSimilarTriangles()
    {
        return new HashSet<GeometricSimilarTriangles>(geometricSimilarTriangles);
    }
    
    public void setGeometricSimilarTriangles(ArrayList<GeometricSimilarTriangles> geometricSimilarTriangles)
    {
        this.geometricSimilarTriangles = geometricSimilarTriangles;
    }
    
    public HashSet<AlgebraicSimilarTriangles> getAlgebraicSimilarTriangles()
    {
        return new HashSet<AlgebraicSimilarTriangles>(algebraicSimilarTriangles);
    }
    
    public void setAlgebraicSimilarTriangles(ArrayList<AlgebraicSimilarTriangles> slgebraicSimilarTriangles)
    {
        this.algebraicSimilarTriangles = slgebraicSimilarTriangles;
    }
    
    public HashSet<SegmentEquation> getSegmentEquation()
    {
        return new HashSet<SegmentEquation>(segmentEquations);
    }
    
    public void setSegmentEquation(ArrayList<SegmentEquation> segmentEquation)
    {
        this.segmentEquations = segmentEquation;
    }
    
    public HashSet<ArcEquation> getArcEquation()
    {
        return new HashSet<ArcEquation>(arcEquations);
    }
    
    public void setArcEquation(ArrayList<ArcEquation> arcEquations)
    {
        this.arcEquations = arcEquations;
    }
    
    public HashSet<AngleArcEquation> getAngleArcEquation()
    {
        return new HashSet<AngleArcEquation>(angleArcEquations);
    }
    
    public void setAngleArcEquation(ArrayList<AngleArcEquation> angleArcEquations)
    {
        this.angleArcEquations = angleArcEquations;
    }
    
    public FactComputerContainer()
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
        segmentRatios = new ArrayList<ProportionalSegments>();
        
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
        
        geometricParallels = new ArrayList<GeometricParallel>();
        algebraicParallels = new ArrayList<AlgebraicParallel>();
        geometricSimilarTriangles = new ArrayList<GeometricSimilarTriangles>();
        algebraicSimilarTriangles = new ArrayList<AlgebraicSimilarTriangles>();
        segmentEquations = new ArrayList<SegmentEquation>();
        arcEquations = new ArrayList<ArcEquation>();
        angleArcEquations = new ArrayList<AngleArcEquation>();
    }
}
