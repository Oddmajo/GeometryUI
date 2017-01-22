package backendTest.astTest.figure.delegates.generators;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;

public class TriangleGenerator extends AbstractGenerator
{
    protected PointGenerator _pointGen;
    protected SegmentGenerator _segmentGen;

    public TriangleGenerator()
    {
        super();
        _pointGen = new PointGenerator();
        _segmentGen = new SegmentGenerator();
    }

    /*
     * @return a triangle (unchecked type); odds are the resulting triangle is scalene
     */
    public Triangle genTriangle()
    {
        List<Point> points = _pointGen.genNonCollinearPoints(3);

        return new Triangle(points.get(0), points.get(1), points.get(2));
    }

    /*
     * @return a right triangle in the figure window
     */
    public RightTriangle genRightTriangle()
    {
        RightTriangle rt = null;

        do
        {
            Segment baseSegment = _segmentGen.genSegment();

            Segment perp = baseSegment.getPerpendicularThrough(baseSegment.getPoint1());
            
            Point perpPt = perp.other(baseSegment.getPoint1());
            
            rt = new RightTriangle(baseSegment.getPoint1(), baseSegment.getPoint2(), perpPt);
            
        } while (_window.inWindow(rt));

        return rt;
    }
    
    /*
     * @return an equilateral triangle (length unspecified) in the figure window
     */
    public EquilateralTriangle genEquilateralTriangle()
    {
        EquilateralTriangle eqt = null;

        do
        {
            eqt = genEquilateralTriangleHelper();
            
        } while (_window.inWindow(eqt));

        return eqt;
    }
    
    /*
     * @return an equilateral triangle (length unspecified) in the figure window
     * 
     * Algorithm:
     *  1) Construct a 'base' segment
     *  2) Construct a 'ray' with 60 degree angle measure and same length
     *  3) Voila
     */
    private EquilateralTriangle genEquilateralTriangleHelper()
    {      
        // Construct a base segment we will treat as a ray
        Segment baseSegment = _segmentGen.genSegment();

        // Find the Lexicographically least of the two points
        Point least = baseSegment.leastPoint();

        // Segment by 60 degree angle measure
        Ray side = baseSegment.segmentByAngle(least, 60, baseSegment.length()).asRay(least);

        return new EquilateralTriangle(baseSegment.getPoint1(), baseSegment.getPoint2(), side.getNonOrigin());
    }
    
    /*
     * @param tri -- a triangle
     * @return a circle circumscribed about the given @triangle
     * Algorithm:
     *   Construct the perpendicular bisector of one side of triangle.
     *   Construct the perpendicular bisector of another side.
     *   Where they cross is the center of the Circumscribed circle.
     *   Place compass on the center point, adjust its length to reach any corner of the triangle, and draw your Circumscribed circle!
     */
    public Circle genCircumscribedCircleAbout(Triangle tri)
    {
        Point circumcenter = tri.circumcenter();
        
        double radius = Point.calcDistance(circumcenter, tri.getPoint1());
        
        return new Circle(circumcenter, radius);
    }
    
    /*
     * @param tri -- a triangle
     * @return a circle inscribed within the given @triangle (tangent to all three sides)
     * Algorithm:
     *   Incenter is the center of the circle
     *   Use an angle bisector ray to intersect the opposite side of the triangle;
     *   the result is a point we can compute radius length
     *   
     *                  . vertex
     *                 /|\
     *                / |<---- angleBisector
     *  incenter -----> |  \
     *              /   |   \
     *             /____|____\ oppositeSegment
     *                  x
     */       
    public Circle genInscribedCircleIn(Triangle tri)
    {
        Ray angleBisector = tri.angleBisector(tri.getAngleA());
        
        Segment oppositeSegment = tri.oppositeSide(tri.getAngleA());
        
        Point x = oppositeSegment.segmentIntersection(angleBisector.asSegment());

        // Circle centered at the incenter
        Point incenter = tri.circumcenter();
        double radius = Point.calcDistance(incenter, x);
        
        return new Circle(incenter, radius);
    }
    
    /*
     * @param circle -- a standard circle
     * @return a 'snapped', inscribed right triangle.
     * That is, an inscribed right triangle in which all three points on the circle are
     * standard unit circle angle measures
     */
    private RightTriangle genRightTriangleByCircle(Circle circle)
    {      
        List<RightTriangle> rightTriangles = genAllRightTrianglesByCircle(circle);

        int index = _rng.nextInt(rightTriangles.size());

        return rightTriangles.get(index);
    }
    
    /*
     * @param circle -- a standard circle
     * @return all such 'snapped', inscribed right triangles.
     * All triangles will be (1) right, (2) inscribed, and (3) all three points on the circle are
     * standard unit circle angle measures.
     */
    public List<RightTriangle> genAllRightTrianglesByCircle(Circle circle)
    {      
        List<Triangle> triangles = genAllTrianglesByCircle(circle);
        List<RightTriangle> rightTriangles = new ArrayList<RightTriangle>();
        
        for (Triangle tri : triangles)
        {
            if (tri.isEquilateral()) rightTriangles.add(new RightTriangle(tri));
        }
        
        return rightTriangles;
    }
    
    /*
     * @param circle -- a standard circle
     * @return a 'snapped', inscribed eq. triangle.
     * That is, an inscribed equilateral triangle in which all three points on the circle are
     * standard unit circle angle measures (take 0, 120, 240 and rotate any angle measurement to acquire eq. triangles)
     */
    public EquilateralTriangle genEquilateralTriangleByCircle(Circle circle)
    {      
        List<EquilateralTriangle> eqTriangles = genAllEquilateralTrianglesByCircle(circle);

        int index = _rng.nextInt(eqTriangles.size());

        return eqTriangles.get(index);
    }
    
    /*
     * @param circle -- a standard circle
     * @return all such 'snapped', inscribed right triangles.
     * All triangles will be (1) right, (2) inscribed, and (3) all three points on the circle are
     * standard unit circle angle measures.
     */
    public List<EquilateralTriangle> genAllEquilateralTrianglesByCircle(Circle circle)
    {      
        List<Triangle> triangles = genAllTrianglesByCircle(circle);
        List<EquilateralTriangle> eqTriangles = new ArrayList<EquilateralTriangle>();
        
        for (Triangle tri : triangles)
        {
            if (tri.isEquilateral()) eqTriangles.add(new EquilateralTriangle(tri));
        }
        
        return eqTriangles;
    }
    
    /*
     * @param circle -- a standard circle
     * @return a 'snapped', inscribed isosceles triangle.
     * That is, an inscribed isosceles triangle in which all three points on the circle are
     * standard unit circle angle measures (take 0, 180, 90 and rotate any angle measurement; repeat with 30, 150, 90)
     */
    public IsoscelesTriangle genIsoscelesTriangleByCircle(Circle circle)
    {
        List<IsoscelesTriangle> isoTriangles = genAllIsoscelesTrianglesByCircle(circle);

        int index = _rng.nextInt(isoTriangles.size());

        return isoTriangles.get(index);
    }
    
    /*
     * @param circle -- a standard circle
     * @return all such 'snapped', inscribed isosceles triangles.
     * All triangles will be (1) isosceles, (2) inscribed, and (3) all three points on the circle are
     * standard unit circle angle measures.
     */
    public List<IsoscelesTriangle> genAllIsoscelesTrianglesByCircle(Circle circle)
    {
        List<Triangle> triangles = genAllTrianglesByCircle(circle);
        List<IsoscelesTriangle> isoTriangles = new ArrayList<IsoscelesTriangle>();
        
        for (Triangle tri : triangles)
        {
            if (tri.isIsosceles()) isoTriangles.add(new IsoscelesTriangle(tri));
        }
        
        return isoTriangles;
    }

    
    /*
     * @param circle -- a standard circle
     * @return a 'snapped', inscribed, right isosceles triangle.
     * That is, an inscribed right isosceles triangle in which all three points on the circle are
     * standard unit circle angle measures
     */
    public RightTriangle genRightIsoscelesTriangleByCircle(Circle circle)
    {      
        List<RightTriangle> rTriangles = genAllRightIsoscelesTrianglesByCircle(circle);

        int index = _rng.nextInt(rTriangles.size());

        return rTriangles.get(index);
    }
    
    /*
     * @param circle -- a standard circle
     * @return all such 'snapped', inscribed right isosceles triangles.
     * All triangles will be (1) isosceles, (2) right, (3) inscribed, and (4) all three points on the circle are
     * standard unit circle angle measures.
     */
    private List<RightTriangle> genAllRightIsoscelesTrianglesByCircle(Circle circle)
    {
        List<IsoscelesTriangle> isoTriangles = genAllIsoscelesTrianglesByCircle(circle);
        List<RightTriangle> rTriangles = new ArrayList<RightTriangle>();
        
        for (IsoscelesTriangle tri : isoTriangles)
        {
            if (tri.isRight()) rTriangles.add(new RightTriangle(tri));
        }
        
        return rTriangles;

    }
    
    /*
     * @param circle -- a standard circle
     * @return a 'snapped', inscribed triangle.
     * That is, an inscribed triangle in which all three points on the circle are
     * standard unit circle angle measures
     */
    public Triangle genTriangleByCircle(Circle circle)
    {
        // Choose three random snapping points on the circle
        return new Triangle(_pointGen.genSnappingPointsOn(circle, 3));
    }
    
    /*
     * @param circle -- a standard circle
     * @return all such 'snapped', inscribed triangles.
     * All triangles will be (1) inscribed and (2) all three points on the circle are
     * standard unit circle angle measures.
     * Some of these triangles will be right triangles
     */
    public List<Triangle> genAllTrianglesByCircle(Circle circle)
    {
        List<Point> points = _pointGen.genAllSnappingPointsOn(circle);
        List<Triangle> triangles = new ArrayList<Triangle>();
        
        // Generate all possible combinations of 3 snapping points
        for (int p1 = 0; p1 < points.size() - 2; p1++)
        {
            for (int p2 = p1 + 1; p2 < points.size() - 1; p2++)
            {
                for (int p3 = p2 + 1; p3 < points.size(); p3++)
                {
                    triangles.add(new Triangle(points.get(p1), points.get(p2), points.get(p3)));                    
                }
            }
        }
        
        return triangles;
    }
}
