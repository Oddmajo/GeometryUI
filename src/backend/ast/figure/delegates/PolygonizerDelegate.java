package backend.ast.figure.delegates;

import java.util.ArrayList;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;

public class PolygonizerDelegate
{

    private PolygonizerDelegate() { }

//    public static ArrayList<Segment> Segmentize(Circle circle)
//    {
//        if (!approxSegments.isEmpty()) return approxSegments;
//
//        // How much we will change the angle measure as we create segments.
//        double angleIncrement = 2 * Math.PI / Figure.NUM_SEGS_TO_APPROX_ARC;
//
//        // The first point will always be at 0 degrees.
//        Point firstPoint = Point.GetPointFromAngle(_center, _radius, 0.0);
//        Point secondPoint = null;
//        double angle = 0;
//        for (int i = 1; i <= Figure.NUM_SEGS_TO_APPROX_ARC; i++)
//        {
//            approxPoints.add(firstPoint);
//
//            // Get the next point.
//            angle += angleIncrement;
//            secondPoint = Point.GetPointFromAngle(_center, _radius, angle);
//
//            // Make the segment.
//            approxSegments.add(new Segment(firstPoint, secondPoint));
//
//            // Rotate points.
//            firstPoint = secondPoint;
//        }
//
//        approxPoints.add(secondPoint);
//
//        return approxSegments;
//    }
//
//    // Make the circle into a regular n-gon that approximates it.
//    @Override
//    public Polygon GetPolygonalized()
//    {
//        if (polygonalized != null) return polygonalized;
//
//        polygonalized = Polygon.MakePolygon(Segmentize());
//
//        return polygonalized;
//    }
}
