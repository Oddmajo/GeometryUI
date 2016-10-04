package backend.utilities;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Point;

//
// Given a pair of coordinates; generate a unique name for it; return that point object.
// Names go from A..Z..AA..ZZ..AAA...ZZZ
//
public class PointFactory
{
    private static final String _prefix = "_*_";
    private static String currentName = "A";
    private static int numLetters = 1;

    private static List<Point> database = new ArrayList<Point>();

    public static void initialize(List<Point> initPoints)
    {
        database.addAll(initPoints);
    }

    public static boolean isGenerated(Point that)
    {
        if (that == null) return false;

        if (that.name.length() < _prefix.length()) return false;

        return _prefix.equals(that.name.substring(0, 3));
    }

    public static Point GeneratePoint(double x, double y)
    {
        int index = database.indexOf(new Point("", x, y));
        if (index != -1) return database.get(index);

        Point newPt = new Point(getCurrentName(), x, y);
        Point oldPt = backend.utilities.ast_helper.Utilities.getStructurally(database, newPt);

        if (oldPt != null) return oldPt;

        database.add(newPt);

        return newPt;
    }

    public static Point generatePoint(Point pt)
    {
        return GeneratePoint(pt.getX(), pt.getY());
    }

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
}