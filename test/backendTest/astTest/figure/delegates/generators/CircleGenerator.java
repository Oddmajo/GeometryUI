package backendTest.astTest.figure.delegates.generators;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.triangles.Triangle;
import backend.ast.figure.delegates.LineDelegate;
import backend.utilities.exception.ExceptionHandler;

public class CircleGenerator extends AbstractGenerator
{
    protected PointGenerator _pointGen;
    protected TriangleGenerator _triangleGen;

    public CircleGenerator()
    {
        super();
        _pointGen = new PointGenerator();
        _triangleGen = new TriangleGenerator();
    }

    /*
     * @return a circle in the allowable window
     */
    public Circle genCircle()
    {
        // Acquire a point and radius
        Point center = _pointGen.genPoint();
        int radius = genRadius(center);
        Circle circle = new Circle(center, radius);
        
        if (!_window.inWindow(circle))
        {
             ExceptionHandler.throwException("Generated circle outside the given window");
        }
            
        return circle;
    }

    /*
     * @return a circle that passes through the three points
     */
    public Circle genCircleThrough(Point one, Point two, Point three)
    {
        // The given points must be non-collinear
        if (LineDelegate.collinear(one, two, three)) return null;
            
        // Acquire a point and radius
        Point center = _pointGen.genPoint();
        int radius = genRadius(center);
        Circle circle = new Circle(center, radius);
        
        if (!_window.inWindow(circle))
        {
             ExceptionHandler.throwException("Generated circle outside the given window");
        }
            
        return circle;
    }
    
    /*
     * @param tri -- a general triangle
     * @return a circle that is circumscribed about the given @triangle
     */
    public Circle genCircumscribedAbout(Triangle tri)
    {
        return genCircleThrough(tri.getPoint1(), tri.getPoint2(), tri.getPoint3());
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
        return _triangleGen.genInscribedCircleIn(tri);
    }
    
    /*
     * @return a radius for a circle allowable in this window assuming an input center point.
     * radius is at least 1 unit
     */    
    private int genRadius(Point center)
    {
        return _rng.nextInt((int)_window.distanceToEdge(center) - 1) + 1;
    }
}
