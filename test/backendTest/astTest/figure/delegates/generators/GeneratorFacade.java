package backendTest.astTest.figure.delegates.generators;

import java.util.ArrayList;
import java.util.Collections;

import backend.FigureWindow;
import backend.ast.figure.ShapeHierarchy;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.ast.figure.delegates.LineDelegate;
import backend.utilities.PointFactory;

/*
 * Singleton-like access to functionality for automated construction of segments, shapes, and figures.
 * 
 * This class is a 
 */
public final class GeneratorFacade
{
    private PointGenerator _pointGen;
    private SegmentGenerator _segmentGen;
    private TriangleGenerator _triangleGen;
    //private QuadrilateralGenerator _quadGen;
    
    private GeneratorFacade()
    {
        _pointGen = new PointGenerator();
        _segmentGen = new SegmentGenerator();
        _triangleGen = new TriangleGenerator();
        //_quadGen = new QuadrilateralGenerator();
    }
    
    private static GeneratorFacade _generator;

    static
    {
        _generator = null;     
    }

    /*
     * Singleton-like access to the one instance of this class
     */
    private static GeneratorFacade get()
    {
        if (_generator == null) _generator = new GeneratorFacade();
        return _generator;
    }

    
    public Point genPoint() { return _pointGen.genPoint(); }
    public Segment genSegment() { return _segmentGen.genSegment(); }


    /*
     * Construct n unique non-collinear points
     * @return a list of points in lexicographically sorted order
     */
    private ArrayList<Point> genNonCollinearPoints(int n)
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
