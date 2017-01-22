package backendTest.astTest.figure.delegates.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.delegates.CircleDelegate;
import backend.ast.figure.delegates.LineDelegate;
import backend.utilities.PointFactory;
import backend.utilities.random.Utilities;

public class PointGenerator extends AbstractGenerator
{
    public PointGenerator() { super(); }

    /*
     * @return a randomly generated point in the window;
     * For computational purposes, we seek <integer, integer> pairs
     * To avoid redundancy, we generate unique points (to avoid sharing)
     */
    public Point genPoint()
    {
        Point p;
        do
        {
            p = new Point("", genIntX(), genIntY());
        }
        while(PointFactory.contains(p));

        return PointFactory.generatePoint(p);
    }
    private int genIntX() { return _rng.nextInt(_window.getUpperX()); }
    private int genIntY() { return _rng.nextInt(_window.getUpperY()); }

    /*
     * @return a point on a given circle 
     * The generated point is equally likely to be on a (standard position) angle on the unit circle.
     */
    public Point genPointOn(Circle circle)
    {
        int index = _rng.nextInt(Circle.SNAPPING_ANGLE_MEASURES_DEGREES.length);

        return circle.getPoint(Circle.SNAPPING_ANGLE_MEASURES_DEGREES[index]);
    }

    /*
     * @param circle a standard circle
     * @return the set of snapping points defined as points on the standard unit circle {0, 30, 45, 60, 90, ..., 330 }
     */
    public List<Point> genAllSnappingPointsOn(Circle circle)
    {
        // CTA: Need to abstract away these points...
        return genPointsOn(circle, Circle.SNAPPING_ANGLE_MEASURES_DEGREES);
    }

    /*
     * @param circle a standard circle
     * @return the set of snapping points defined as points on the standard unit circle poles {0, 90, 270, 360}
     */
    public List<Point> genPolePointsOn(Circle circle)
    {
        // CTA: Need to abstract away these points...
        return genPointsOn(circle, 0, 90, 180, 270);
    }
    /*
     * @param circle a standard circle
     * @param angles are assumed given in degrees (and are then converted to radians)
     * @return the set of snapping points defined as points on the circle defined by the given angle
     */
    private List<Point> genPointsOn(Circle circle, int... angles)
    {
        ArrayList<Point> points = new ArrayList<Point>();

        for (int angle : angles)
        {
            points.add(circle.getPoint(angle));
        }

        return points;
    }

    /*
     * @param circle a standard circle
     * @param n (integer number of points to be generated on the circle)
     * @return a list of n unit circle points (ordered by angle measure)
     */
    public List<Point> genSnappingPointsOn(Circle circle, int n)
    {
        Integer[] indices = Utilities.genIntegers(0, Circle.SNAPPING_ANGLE_MEASURES_DEGREES.length, n);

        ArrayList<Point> points = new ArrayList<Point>();
        for (int index : indices)
        {
            points.add(circle.getPoint(Circle.SNAPPING_ANGLE_MEASURES_DEGREES[index]));
        }

        return points;
    }

    /*
     * @param circle a standard circle
     * @param n (integer number of points to be generated on the circle)
     * @return a list of n unit circle points (ordered by angle measure)
     */
    public List<Point> genGeneralPointsOn(Circle circle, int n)
    {
        // Generate the angles (in order: [0, 360))
        Double[] angles = Utilities.genDoubles(0,  360,  n);

        // Generate the actual points on the circle in the order
        ArrayList<Point> points = new ArrayList<Point>();
        for (double angle : angles)
        {
            points.add(circle.getPoint(angle));
        }

        return points;
    }

    /*
     * Construct n unique non-collinear points
     * @return a list of points in lexicographically sorted order
     */
    public ArrayList<Point> genNonCollinearPoints(int n)
    {
        ArrayList<Point> points = new ArrayList<Point>();

        // Construct two base points
        points.add(genPoint());
        points.add(genPoint());

        while (points.size() < n)
        {
            // Acquire a new point
            Point newPt = genPoint();

            // If not collinear with the rest, add it to the list
            if (!localCheckCollinear(points, newPt)) points.add(newPt);
        }

        // Sort lexicographically (since Points are Comparable)
        Collections.sort(points);
        return points;
    }

    /*
     * Check collinearity of all combinations of points with a new given point;
     * assumes list-based points are all non-collinear
     *
     * Matrix-style comparison of points (upper diagonal)
     */
    private boolean localCheckCollinear(ArrayList<Point> points, Point pt)
    {
        for (int i = 0; i < points.size() - 1; i++)
        {
            for (int j = i + 1; j < points.size(); j++)
            {
                if (LineDelegate.collinear(points.get(i), points.get(j), pt)) return true;
            }
        }
        return false;
    }
}
