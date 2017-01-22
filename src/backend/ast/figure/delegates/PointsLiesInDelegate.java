package backend.ast.figure.delegates;

import java.util.List;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;

public class PointsLiesInDelegate
{
    private PointsLiesInDelegate() { }

    /// <summary>
    /// Determines if the given point is inside the polygon; http://alienryderflex.com/polygon/
    /// </summary>
    /// <param name="polygon">the vertices of polygon</param>
    /// <param name="testPoint">the given point</param>
    /// <returns>true if the point is inside the polygon; otherwise, false</returns>
    public static boolean pointLiesIn(Polygon polygon, Point thatPoint)
    {
        List<Point> points = polygon.getPoints();

        if (thatPoint == null)
        {
            ExceptionHandler.throwException( new DebugException(Thread.currentThread().getStackTrace().toString()));
            ExceptionHandler.throwException( new IllegalArgumentException("Null passed to isInPolygon"));
        }

        boolean result = false;
        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++)
        {
            if (points.get(i).getY() < thatPoint.getY() &&
                points.get(j).getY() >= thatPoint.getY() || points.get(j).getY() < thatPoint.getY() &&
                                              points.get(i).getY() >= thatPoint.getY())
            {
                if (points.get(i).getX() + (thatPoint.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) * (points.get(j).getX() - points.get(i).getX()) < thatPoint.getX())
                {
                    result = !result;
                }
            }
            j = i;
        }
        return result;
    }
}
