package backend.ast.figure.delegates;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Semicircle;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.translation.OutPair;

public class ContainsDelegate
{
    private ContainsDelegate() { }
    
//    //
//    // A shape within this shape?
//    //
//    @Override
//    public boolean Contains(Figure that)
//    {
//        if (that instanceof Circle) return ContainsCircle((Circle)that);
//        if (that instanceof Polygon) return ContainsPolygon((Polygon)that);
//        if (that instanceof Sector) return ContainsSector((Sector)that);
//
//        return false;
//    }
    
    /*
     * @param polygon -- a general polygon
     * @param circle -- a circle
     * @return true if @polygon contains @circle completely: The center lies inside the
     * polygon and there are no intersection points with the sides.
     */
    public static boolean ContainsCircle(Polygon polygon, Circle that)
    {
        for (Segment side : polygon.getOrderedSides())
        {
            Point pt1 = null;
            Point pt2 = null;
            OutPair<Point, Point> out = new OutPair<Point, Point>(pt1, pt2);
            that.findIntersection(side, out );
            pt1 = out.getKey();
            pt2 = out.getValue();
            if (pt1 != null && pt2 != null) return false;
        }

        //return that.PointLiesInside(that.center);
        return polygon.pointLiesInside(that.getCenter());
    }

    /*
     * @param thisPoly -- a general polygon
     * @param that -- a polygon
     * @return true if @thisPoly contains @that completely.
     */
    private static boolean ContainsPolygon(Polygon thisPoly, Polygon that)
    {
        //
        // All points are interior to the polygon.
        //
        for (Point thatPt : that.getPoints())
        {
            if (!thisPoly.PointLiesInOrOn(thatPt)) return false;
        }

        //
        // Check that all intersections so that there are no crossings.
        //
        for (Segment thisSide : thisPoly.getOrderedSides())
        {
            for (Segment thatSide : that.getOrderedSides())
            {
                if (thisSide.LooseCrosses(thatSide)) return false;
            }
        }

        return true;
    }

    /*
     * @param polygon -- a general polygon
     * @param that -- a sector
     * @return true if @polygon contains @that sector completely.
     */
    private static boolean ContainsSector(Polygon polygon, Sector that)
    {
        if (!polygon.PointLiesInOrOn(that.getArc().getEndpoint1())) return false;
        if (!polygon.PointLiesInOrOn(that.getArc().getEndpoint2())) return false;

        if (!polygon.PointLiesInOrOn(that.getArc().getCircle().getCenter())) return false;

        if (that.getArc() instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)that.getArc();
            if (!polygon.PointLiesInOrOn(semi.getMiddlePoint())) return false;
            if (!polygon.PointLiesInOrOn(semi.getCircle().getMidpoint(semi.getEndpoint1(), semi.getMiddlePoint()))) return false;
            if (!polygon.PointLiesInOrOn(semi.getCircle().getMidpoint(semi.getEndpoint2(), semi.getMiddlePoint()))) return false;
        }
        else
        {
            if (!polygon.PointLiesInOrOn(that.getArc().Midpoint())) return false;
        }
        // Check all point approximations for containment.
        //List<Point> approx = that.GetFigureAsAtomicRegion().GetVertices();
        //foreach (Point pt in approx)
        //{
        //    if (!polygon.PointLiesInOrOn(pt)) return false;
        //}

        return true;
    }

    //
    //
    // Sectors
    //
    //
    //
    // The center lies inside the polygon and there are no intersection points with the sides.
    //
    private static boolean ContainsCircle(Sector sector, Circle that)
    {
        // Center lies (strictly) inside of this sector
        if (!sector.pointLiesInside(that.getCenter())) return false;

        // As a simple heuristic, the radii lengths must support inclusion.
        if (Point.calcDistance(sector.getArc().getCircle().getCenter(), that.getCenter()) + that.getRadius() > sector.getArc().getCircle().getRadius()) return false;

        //
        // Any intersections between the sides of the sector and the circle must be tangent.
        //
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        that.findIntersection(sector.getRadius1(), out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        if (pt2 != null) return false;

        that.findIntersection(sector.getRadius2(), out);
        pt1 = out.first();
        pt2 = out.second();
        
        if (pt2 != null) return false;

        // CTA: BUG here? Check only arc, not the entire circle
        that.findIntersection(sector.getArc().getCircle(), out);
        pt1 = out.first();
        pt2 = out.second();
        
        if (pt2 != null) return false;

        return true;
    }

    //
    // All points of the polygon are on or : the sector.
    // No need to check that any sides of the polygon pass
    // through the sector since that implies a vertex exterior to the sector.
    //
    private static boolean ContainsPolygon(Sector sector, Polygon polygon)
    {
        for (Point thatPt : polygon.getPoints())
        {
            if (!sector.PointLiesInOrOn(thatPt)) return false;
        }

        for (Segment side : polygon.getOrderedSides())
        {
            if (!sector.PointLiesInOrOn(side.getMidpoint())) return false;
        }

        return true;
    }

    //
    // that Sector lies within this sector
    //
    private static boolean ContainsSector(Sector sector, Sector that)
    {
        if (sector.structurallyEquals(that)) return true;

        //
        // Is this sector from the same circle as that sector?
        //
        if (sector.getArc().getCircle().structurallyEquals(that.getArc().getCircle()))
        {
            for (Point pt : that.getArc().GetApproximatingPoints())
            {
                if (!sector.PointLiesInOrOn(pt)) return false;
            }

            return true;
        }

        // this radius must be longer than that.
        if (backend.utilities.math.MathUtilities.greaterThan(that.getArc().getCircle().getRadius(), sector.getArc().getCircle().getRadius())) return false;

        //
        // Check containment of the points of that sector.
        //
        if (!sector.PointLiesInOrOn(that.getArc().getEndpoint1())) return false;
        if (!sector.PointLiesInOrOn(that.getArc().getEndpoint2())) return false;

        if (!sector.PointLiesInOrOn(that.getArc().getCircle().getCenter())) return false;

        // Check midpoint instanceof also within the sector.
        if (!sector.PointLiesInOrOn(that.getArc().Midpoint())) return false;

        return true;
    }

//    //
//    // A shape within this shape?
//    //
//    @Override
//    public boolean Contains(Figure that)
//    {
//        if (that instanceof Circle) return ContainsCircle((Circle)that);
//        if (that instanceof Polygon) return ContainsPolygon((Polygon)that);
//        if (that instanceof Sector) return ContainsSector((Sector)that);
//
//        return false;
//    }
}
