package backend.ast.figure.delegates.intersections;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.delegates.FigureDelegate;

public class SegmentIntersectionDelegate extends FigureDelegate
{
    /*
     * <Segment, Segment> intersection
     * @param thisS -- (this Segment)
     * @param that -- a Segment to intersect with
     * @return the intersection of @thisS and @that
     */
    public static Point findIntersection(Segment thisS, Segment that)
    {
        // <line, line> intersection
        Point inter = thisS.lineIntersection(that);
        
        // Point lies on both segments
        if (thisS.pointLiesBetweenEndpoints(inter)) return null;

        if (that.pointLiesBetweenEndpoints(inter)) return null;
        
        return inter;
    }
}
