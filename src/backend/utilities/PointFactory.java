package backend.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import backend.ast.figure.components.Point;

/*
 * Given a pair of coordinates; generate a unique name for it; return that point object.
 * Names go from A..Z..AA..ZZ..AAA...ZZZ
 */
public class PointFactory
{
    // This is a static class (one instance)
    private PointFactory() {}

    private static final String _prefix = "_*_";
    private static String currentName = "A";
    private static int numLetters = 1;

    //
    // A hased container for the database of points;
    // This requires the Point class implement equals based solely on the individual values and not a name
    // We need a get() method; HashSet doesn't offer one.
    // Each entry is a <Key, Value> pair where Key == Value
    //
    private static HashMap<Point, Point> database = new HashMap<Point, Point>();

    /**
     * @param points A list of named points
     * 
     */
    public static void initialize(List<Point> points)
    {
        for (Point pt : points)
        {
            PointFactory.generatePoint(pt);
        }
    }

    /*
     * Has this point been generated previously?
     */
    public static boolean isGenerated(Point that)
    {
        if (that == null) return false;

        if (that.name.length() < _prefix.length()) return false;

        return _prefix.equals(that.name.substring(0, 3));
    }

    /**
     * @param pt -- (x, y) coordinate pair object
     * @return a point (if it already exists) or a completely new point that has been added to the database
     */
    public static Point generatePoint(Point pt)
    {
        return GeneratePoint(pt.name, pt.getX(), pt.getY());
    }
    
    /**
     * @param x -- single coordinate
     * @param y -- single coordinate
     * @return a point (if it already exists) or a completely new point that has been added to the database
     */
    public static Point GeneratePoint(double x, double y)
    {
        //
        // Point already contained?
        //
        Point pt = database.get(new Point("", x, y));
        if (pt != null) return pt;
        
        //
        // Point not contained so generate and add it
        //
        Point newPt = new Point(getCurrentName(), x, y);
        database.put(newPt, newPt);

        return newPt;
    }

    /**
     * @param name -- the name of the point 
     * @param x -- single coordinate
     * @param y -- single coordinate
     * @return a point (if it already exists) or a completely new point that has been added to the database
     *  The name of the point is used only in the case where a NEW point is generated
     */
    public static Point GeneratePoint(String name, double x, double y)
    {
        //
        // Point already contained?
        //
        Point pt = database.get(new Point("", x, y));
        if (pt != null) return pt;
        
        //
        // Point not contained so generate and add it
        //
        Point newPt = new Point(name != "" ? name : getCurrentName(), x, y);
        database.put(newPt, newPt);

        return newPt;
    }

    /**
     * @param x -- single coordinate
     * @param y -- single coordinate
     * @return simple containment; no updating
     */
    public static boolean contains(double x, double y) { return database.containsKey(new Point("", x, y)); }
    public static boolean contains(Point p) { return contains(p.getX(), p.getY()); }

    // Reset for the next problem
    public static void reset()
    {
        currentName = "A";
        numLetters = 1;
    }

    private static String getCurrentName()
    {
        String name = _prefix + currentName;

        updateName();

        return name;
    }

    private static void updateName()
    {
        // Restart at the beginning of the alphabet
        if (currentName.charAt(0) == 'Z')
        {
            // We rolled over to more letter.
            numLetters++;

            currentName = "";
            for (int i = 0; i < numLetters; i++)
            {
                currentName += 'A';
            }
        }
        // Simple increment from A to B, etc.
        else
        {
            char alpha = currentName.charAt(0);
            alpha++;

            currentName = "";
            for (int i = 0; i < numLetters; i++)
            {
                currentName += alpha;
            }
        }
    }

    /**
     * @return The entire database of points.
     */
    public static Set<Point> getAllPoints()
    {
        return database.keySet();
    }
    
    /**
     * Clear the factory
     */
    public static void clear()
    {
        database.clear();
    }
}