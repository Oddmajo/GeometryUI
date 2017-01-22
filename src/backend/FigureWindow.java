package backend;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ExceptionHandler;

/*
 * Class that represents the inWindow window Cartesian coordinates.
 * Implemented as Singleton-like for global access and the fact there is only a single window per figure.
 * 
 * @author C. Alvin
 */
public class FigureWindow
{
    protected static int _LOWER_BOUND_X = -100;
    protected static int _UPPER_BOUND_X = 100;
    protected static int _LOWER_BOUND_Y = -100;
    protected static int _UPPER_BOUND_Y = 100;

    private static FigureWindow _window;

    static
    {
        _window = null;
        _LOWER_BOUND_X = -100;
        _UPPER_BOUND_X = 100;
        _LOWER_BOUND_Y = -100;
        _UPPER_BOUND_Y = 100;
    }

    // Prohibit external construction
    private FigureWindow() { }

    public FigureWindow(int lowX, int upperX, int lowY, int upperY)
    {
        _LOWER_BOUND_X = lowX;
        _UPPER_BOUND_X = upperX;
        _LOWER_BOUND_Y = lowY;
        _UPPER_BOUND_Y = upperY;
    }

    /*
     * Constructor-like: can only be called once.
     * Verfies bounds
     */
    public boolean initialize(int lowX, int upperX, int lowY, int upperY)
    {
        if (_window == null) return false;

        //
        // Check for switched bounds
        //
        if (lowX >= upperX)
        {
            String problem = "Problem with X bounds " + lowX + " > " + upperX;
            ExceptionHandler.throwException(new IllegalArgumentException(problem));
        }
        if (lowY >= upperY)
        {
            String problem = "Problem with Y bounds " + lowY + " > " + upperY;
            ExceptionHandler.throwException(new IllegalArgumentException(problem));
        }
        
        // Check upper bound in the first Cartesian Quadrant of the plane.
        if (upperX < 0 || upperY < 0)
        {
            String problem = "Window upper bound must be in the first quadrant (" + upperX + ", " + upperY + ")";
            ExceptionHandler.throwException(new IllegalArgumentException(problem));
        }

        _window = new FigureWindow(lowX, upperX, lowY, upperY);

        return true;
    }

    public static FigureWindow getWindow()
    {
        if (_window == null) _window = new FigureWindow(); 

        return _window;
    }

    /*
     * Accessors
     */
    public int getLowerX() { return _LOWER_BOUND_X; }
    public int getUpperX() { return _UPPER_BOUND_X; }
    public int getLowerY() { return _LOWER_BOUND_Y; }
    public int getUpperY() { return _UPPER_BOUND_Y; }
    public int getCenterX() { return _LOWER_BOUND_X + (_UPPER_BOUND_X - _LOWER_BOUND_X) / 2; }
    public int getCenterY() { return _LOWER_BOUND_Y + (_UPPER_BOUND_Y - _LOWER_BOUND_Y) / 2; }
    public Point getCenter() { return new Point("", getCenterX(), getCenterY()); }

    
    /*
     * @return the minimal horizontal / vertical distance from a given point
     * (distance is positive: > 0 legitimate ; < 0 bad point)
     */
    public double distanceToEdge(Point pt)
    {
        if (!inWindow(pt)) return -1;
        
        double maxX = (int)Math.min(pt.getX() - _LOWER_BOUND_X, _UPPER_BOUND_X - pt.getX());
        double maxY = (int)Math.min(pt.getY() - _LOWER_BOUND_Y, _UPPER_BOUND_Y - pt.getY());

        return Math.min(maxX,  maxY);
    }
    
    /*
     * Allowable Cartesian point in the window
     */
    public boolean inWindow(Point pt) { return inWindowX(pt.getX()) && inWindowY(pt.getY()); }

    /*
     * Allowable X coordinate in the given window
     */
    public boolean inWindowX(double x) { return _LOWER_BOUND_X < x && x < _UPPER_BOUND_X; }

    /*
     * Allowable Y coordinate in the given window
     */
    public boolean inWindowY(double y) { return _LOWER_BOUND_Y < y && y < _UPPER_BOUND_Y; }

    /*
     * Checks the 'poles' of the circle to determine whether they are in bounds
     * @param circle
     * @return true / false whether any of this circle falls out of the window
     * 
     */
    public boolean inWindow(Circle circle)
    {
        final Point center = circle.getCenter();
        final double radius = circle.getRadius();

        if (inWindowX(center.getX() - radius)) return false; // Left
        if (inWindowX(center.getX() + radius)) return false; // Right
        if (inWindowY(center.getY() - radius)) return false; // Bottom
        if (inWindowY(center.getY() + radius)) return false; // Top

        return true;
    }

    /*
     * Checks the 'poles' of the circle to determine whether they are in bounds
     * @param circle
     * @return true / false whether any of this circle falls out of the window
     * 
     */
    public boolean inWindow(Segment segment)
    {
        return inWindow(segment.getPoint1()) && inWindow(segment.getPoint2());
    }

    /*
     * Checks the 'poles' of the circle to determine whether they are in bounds
     * @param circle
     * @return true / false whether any of this circle falls out of the window
     * 
     */
    public boolean inWindow(Polygon polygon)
    {
        for (Segment side : polygon.getOrderedSides())
        {
            if (!inWindow(side)) return false;
        }

        return true;
    }
}
