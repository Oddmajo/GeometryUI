package backend.ast.figure.delegates;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.utilities.math.MathUtilities;
import backend.utilities.translation.OutPair;

/*
 * A processing, delegation class.
 * All functionality here treats any / all given segments AS LINES (infinite notions)
 * 
 * @author C. Alvin
 */
public class SegmentDelegate
{
    /*
     * Standard linear slope computation
     */
    public static double slope(Point p1, Point p2)
    {
        return (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    }
    
    /*
     * Do these lines intersects (arbitrarily)?
     */
    public static boolean intersects(Segment thisS, Segment that)
    {
        return thisS.lineIntersection(that) != null;
    }
    
    /*
     * @param thisS -- a segment
     * @param that -- a segment
     * Do these lines cross in the middle of each segment?
     */
    public static boolean middleCrosses(Segment thisS, Segment that)
    {
        Point intersection = thisS.lineIntersection(that);
        
        return thisS.pointLiesBetweenEndpoints(intersection) && that.pointLiesBetweenEndpoints(intersection);
    }
    
    /*
     * Pictorially we view the given segment as a ray of an angle
     *              /
     *             /  <-- This method generates this segment (with exact length)
     *            /
     *           /\ angle (measure)
     *   pt --> .__\________________  <-- segment
     * 
     * 
     * @param segment -- a segment we treat as a ray 
     * @param angle -- desired angle measurement in degrees [0, 360)
     * @param length -- desired length of the resulting segment object (User may want to specify this notion)
     * @return a segment (corresponding to a ray) with same origin point with desired length 
     */
    public Segment segmentByAngle(Segment segment, Point origin, double angle, double length)
    {
        if (!segment.has(origin)) return null;

        Ray ray = new Ray(origin, segment.other(origin));

        return RayDelegate.rayByAngle(ray, angle, length).asSegment();
    }
    /* @param segment -- a segment we treat as a ray 
     * @param angle -- desired angle measurement in degrees [0, 360)
     * @return a segment (corresponding to a ray) with same origin point with default size 1
     */
    public Segment segmentByAngle(Segment segment, Point origin, double angle) { return segmentByAngle(segment, origin, angle, 1); }

    /*
     * @param that -- another segment
     * @return true / false if the two lines (finite) are collinear
     */
    public static boolean areCollinear(Segment thisS, Segment that)
    {
        if (!LineDelegate.areCollinear(thisS, that)) return false;

        // Slopes equate
        return MathUtilities.doubleEquals(thisS.slope(), that.slope()) &&
               thisS.pointLiesOn(that.getPoint1()) && thisS.pointLiesOn(that.getPoint2());
    }

    /*
     * @param pt -- a point
     * @return true / false if this segment (finite) contains the point
     */
    public static boolean pointLiesOnSegment(Segment segment, Point pt)
    {
        if (pt == null) return false;

        return Segment.Between(pt, segment.getPoint1(), segment.getPoint2());
    }
    
    /*
     * @param pt -- a point
     * @return true if the point is on the segment (EXcluding endpoints); finite examination only
     */
    public static boolean pointLiesBetweenEndpoints(Segment segment, Point pt)
    {
        if (pt == null || segment.has(pt)) return false;

        return Segment.Between(pt, segment.getPoint1(), segment.getPoint2());
    }
    
    /*
     * @param thisS -- a segment
     * @param that -- another segment
     * @return true / false if this contains @that segment entirely (finite)
     *        this:       *----------v--------v--------------*
     *                               ---that---
     */
    public static boolean contains(Segment thisS, Segment that)
    {
        if (!thisS.isCollinearWith(that)) return false;

        return thisS.pointLiesOnSegment(that.getPoint1()) && thisS.pointLiesOnSegment(that.getPoint2());
    }
    
    /*
     * @param thisS -- a segment
     * @param that -- another segment
     * @return true / false if this contains @that segment entirely (finite)
     *        this:       *----------v--------v--------------*
     *                               ---that---
     */
    public static boolean strictContains(Segment thisS, Segment that)
    {
        return thisS.pointLiesBetweenEndpoints(that.getPoint1()) &&
               thisS.pointLiesBetweenEndpoints(that.getPoint2());
    }
    
    /*
     * @param thisS -- a segment
     * @param that -- another segment
     * @return true : segment-based crossing strictly between endpoints only (finite)
     */
    public boolean segmentCrosses(Segment thisS, Segment that)
    {
        Point inter = thisS.segmentIntersection(that);
        
        if (inter == null) return false;
        
        return thisS.pointLiesBetweenEndpoints(inter) && thisS.pointLiesBetweenEndpoints(inter);
    }

    /*
     * @return perpendicular bisector to the given segment
     * The returned segment should be treated as a line (infinite)
     */
    public static Segment perpendicularBisector(Segment segment)
    {
        Point midpoint = segment.getMidpoint();

        return segment.getPerpendicularThrough(midpoint);
    }

    /*
     * @param segment -- a segment (finite)
     * @param circle -- a circle
     * @return true if this segment (finite) passes through this circle (intersection points are on the interior of the segment)
     *      /     \
     *   __/_______\____
     *    /         \
     */
    public static boolean isSecant(Segment segment, Circle circle)
    {
        // Find the intersections
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        if (!circle.findIntersection(segment, out)) return false;

        // If there is only one intersection, clearly not a secant
        if (out.second() == null) return false;
        
        return segment.pointLiesBetweenEndpoints(out.first()) && segment.pointLiesBetweenEndpoints(out.second());
    }
    
    /*
     * @param segments -- a list of segments
     * @return the common, coincident point of intersection; null if the segment intersection points are not mutually coincident
     * 
     *             |/
     *     --------X------  In this example, three segments intersect at a common point: X
     *            /|
     */
    public static Point coincident(Segment... segments)
    {
        Point intersection = segments[0].lineIntersection(segments[1]);

        for (int index = 1; index < segments.length - 1; index++)
        {
            Point inter = segments[index].lineIntersection(segments[index + 1]);
            
            if (!intersection.equals(inter)) return null;
        }

        return intersection;
    }
    
    /*
     * @param that -- a segment (line)
     * @return true if the segments are perpendicular to one another
     * Verified via slope-based analysis
     */
    public static boolean isPerpendicular(Segment thisS, Segment that)
    {
        if (!thisS.isLinePerpendicular(that)) return false;

        return thisS.intersects(that);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public Point sharedVertex(Segment s)
//    {
//        if (_point1.equals(s._point1)) return _point1;
//        if (_point1.equals(s._point2)) return _point1;
//        if (_point2.equals(s._point1)) return _point2;
//        if (_point2.equals(s._point2)) return _point2;
//        return null;
//    }
//

//
//
//
//    public static boolean Between(Point M, Point A, Point B)
//    {
//        return backend.utilities.math.MathUtilities.doubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B),
//                Point.calcDistance(A, B));
//    }
//
//    public Point OtherPoint(Point p)
//    {
//        if (p.equals(_point1)) return _point2;
//        if (p.equals(_point2)) return _point1;
//
//        return null;
//    }
//
//
//
//
//
//
//
//    // Does this segment contain a subsegment:
//    // A-------B-------C------D
//    // A subsegment is: AB, AC, AD, BC, BD, CD
//    public boolean HasSubSegment(Segment possSubSegment)
//    {
//        return this.pointLiesBetweenEndpoints(possSubSegment._point1) && this.pointLiesBetweenEndpoints(possSubSegment._point2);
//    }
//
//    public boolean HasStrictSubSegment(Segment possSubSegment)
//    {
//        return (this.pointLiesBetweenEndpoints(possSubSegment._point1) && this.pointLiesOnAndExactlyBetweenEndpoints(possSubSegment._point2)) ||
//                (this.pointLiesBetweenEndpoints(possSubSegment._point2) && this.pointLiesOnAndExactlyBetweenEndpoints(possSubSegment._point1));
//    }
//
//    //
//    // Are the segments coinciding and share an endpoint?
//    //
//    public boolean AdjacentCoinciding(Segment thatSegment)
//    {
//        if (!IsCollinearWith(thatSegment)) return false;
//
//        Point shared = this.sharedVertex(thatSegment);
//
//        return shared == null ? false : true;
//    }
//
//    //
//    // Are the segments coinciding with overlap? That is, it's ok to be coinciding with no overlap.
//    //
//    public boolean CoincidingWithOverlap(Segment thatSegment)
//    {
//        if (!IsCollinearWith(thatSegment)) return false;
//
//        if (this.structurallyEquals(thatSegment)) return true;
//
//        if (this.pointLiesOnAndExactlyBetweenEndpoints(thatSegment._point1)) return true;
//
//        if (this.pointLiesOnAndExactlyBetweenEndpoints(thatSegment._point2)) return true;
//
//        return false;
//    }
//
//    //
//    // Do these segments creates an X?
//    //
//    public boolean Crosses(Segment that)
//    {
//        Point p = this.FindIntersection(that);
//
//        if (p == null) return false;
//
//        return this.pointLiesOnAndExactlyBetweenEndpoints(p) && that.pointLiesOnAndExactlyBetweenEndpoints(p);
//    }
//
//
//    //
//    //
//    //
//    //
//    // Analysis and Manipulation
//    //
//    //
//    //
//    //
//    public Point FindIntersection(Segment thatSegment)
//    {
//        return IntersectionDelegate.findIntersection(this, thatSegment);
//    }
//
//    //
//    // Do these angles share this segment overlay this angle?
//    //
//    public boolean IsIncludedSegment(Angle ang1, Angle ang2)
//    {
//        return this.equals(ang1.SharedRay(ang2));
//    }
//
//    //
//    //     PointA
//    //     |
//    //     |             X (pt)
//    //     |_____________________ otherSegment
//    //     |
//    //     |
//    //     PointB
//    //
//    public Point SameSidePoint(Segment otherSegment, Point pt)
//    {
//        // Is the given point on other? If so, we cannot make a determination.
//        if (otherSegment.PointLiesOn(pt)) return null;
//
//        // Make a vector out of this vector as well as the vector connecting one of the points to the given pt
//        Vector thisVector = new Vector(_point1, _point2);
//        Vector thatVector = new Vector(_point1, pt);
//
//        Vector projectionOfOtherOntoThis = thisVector.Projection(thatVector);
//
//        // We are interested most in the endpoint of the projection (which is not the 
//        Point projectedEndpoint = projectionOfOtherOntoThis.NonOriginEndpoint();
//
//        // Find the intersection between the two lines
//        Point intersection = FindIntersection(otherSegment);
//
//        if (this.PointLiesOn(projectedEndpoint))
//        {
//            ExceptionHandler.throwException(new ASTException("Unexpected: Projection does not lie on this line. " + this + " " + projectedEndpoint));
//        }
//
//        // The endpoint of the projection is on this vector. Therefore, we can judge which side of the given segment the given pt lies on.
//        if (Segment.Between(projectedEndpoint, _point1, intersection)) return _point1;
//        if (Segment.Between(projectedEndpoint, _point2, intersection)) return _point2;
//
//        return null;
//    }
//
//    public Point Midpoint()
//    {
//        return new Point(null, (_point1.getX() + _point2.getX()) / 2.0, (_point1.getY() + _point2.getY()) / 2.0);
//    }
//
//    public static boolean LooseBetween(Point M, Point A, Point B)
//    {
//        return backend.utilities.math.MathUtilities.looseDoubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B),
//                Point.calcDistance(A, B));
//    }
//
//    public boolean LooseCrosses(Segment that)
//    {
//        Point p = this.FindIntersection(that);
//
//        if (p == null) return false;
//
//        if (this.hasPoint(p) || that.hasPoint(p)) return false;
//
//        return LooseBetween(p, this._point1, this._point2) && LooseBetween(p, that.getPoint1(), that.getPoint2());
//    }
//
//    // @Override
//    public void FindIntersection(Segment that, OutPair<Point, Point> out)
//    {
//        Point inter1 = FindIntersection(that);
//        Point inter2 = null;
//
//        if (!this.pointLiesBetweenEndpoints(inter1)) inter1 = null;
//        if (!that.pointLiesBetweenEndpoints(inter1)) inter1 = null;
//
//        out.set(inter1,  inter2);
//    }
//
//    @Override
//    public void FindIntersection(Arc that, OutPair<Point, Point> out)
//    {
//        that.FindIntersection(this, out);
//    }
//
//    private static class Vector
//    {
//        private double originX;
//        private double originY;
//        private double otherX;
//        private double otherY;
//
//        public Vector(Point origin, Point other)
//        {
//            originX = origin.getX();
//            originY = origin.getY();
//            otherX = other.getX();
//            otherY = other.getY();
//        }
//
//        public Vector(double x1, double y1, double x2, double y2)
//        {
//            originX = x1;
//            originY = y1;
//            otherX = x2;
//            otherY = y2;
//        }
//
//        public Point NonOriginEndpoint() { return new Point("ProjectedEndpoint", otherX, otherY); }
//
//        private double DotProduct() { return originX * otherX + originY * otherY; }
//        private static double EuclideanDistance(double x1, double y1, double x2, double y2)
//        {
//            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
//        }
//
//        //
//        // Projects the given vector onto this vector using standard vector projection
//        //
//        public Vector Projection(Vector thatVector)
//        {
//            double magnitude = EuclideanDistance(thatVector.originX, thatVector.originY, thatVector.otherX, thatVector.otherY);
//            double cosIncluded = CosineOfIncludedAngle(thatVector);
//
//            double projectionDistance = magnitude * cosIncluded;
//
//            return new Vector(originX, originY, otherX / projectionDistance, otherY / projectionDistance);
//        }
//
//        //
//        // Use Law of Cosines to determine cos(\theta)
//        //      ^
//        //      / \
//        //   a /   \ c
//        //    /\    \
//        //   /__\____\__>
//        //       b 
//        //
//        private double CosineOfIncludedAngle(Vector thatVector)
//        {
//            if (HasSameOriginPoint(thatVector)) return -2;
//
//            double a = EuclideanDistance(originX, originY, otherX, otherY);
//            double b = EuclideanDistance(originX, originY, thatVector.otherX, thatVector.otherY);
//            double c = EuclideanDistance(otherX, otherY, thatVector.otherX, thatVector.otherY);
//
//            // Law of Cosines
//            return (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b);
//        }
//
//        private boolean HasSameOriginPoint(Vector thatVector)
//        {
//            return backend.utilities.math.MathUtilities.doubleEquals(originX, thatVector.originX) &&
//                    backend.utilities.math.MathUtilities.doubleEquals(originY, thatVector.originY);
//        }
//
//        @Override
//        public String toString()
//        {
//            return "(" + originX + ", " + originY + ") -> (" + otherX + ", " + otherY + ")";
//        }
//    }
//
//    //
//    // Given one of the fixed endpoints on this segment, return a congruent segment with the: fixed endpoint and the other point being moved.
//    //
//    public Segment GetOppositeSegment(Point pt)
//    {
//        if (!hasPoint(pt)) return null;
//
//        Point fixedPt = pt;
//        Point variablePt = this.OtherPoint(pt);
//
//        Point vector = Point.MakeVector(fixedPt, variablePt);
//        Point opp = Point.GetOppositeVector(vector);
//
//        // 'Move' the vector to begin at its starting point: pt
//        return new Segment(pt, new Point("", pt.getX() + opp.getX(), pt.getY() + opp.getY()));
//    }
//
//    //
//    // Return the line perpendicular to this segment at the given point. 
//    // The point is ON the segment.
//    public Segment GetPerpendicularByLength(Point pt, double length)
//    {
//        Segment perp = this.GetPerpendicular(pt);
//
//        //
//        // Find the point which is length distance from the given point.
//        //
//        // Treat the given perpendicular as a vector; normalize and then multiply.
//        //
//        Point vector = Point.MakeVector(perp.getPoint1(), perp.getPoint2());
//        vector = Point.Normalize(vector);
//        vector = Point.ScalarMultiply(vector, length);
//
//        // 'Move' the vector to begin at its starting point: pt
//        // Return the perpendicular of proper length.
//        return new Segment(pt, new Point("", pt.getX() + vector.getX(), pt.getY() + vector.getY()));
//    }
//
//    //
//    // Is the given segment a secant THROUGH this circle? (2 intersection points)
//    //
//    public boolean IsSecant(Circle circle)
//    {
//        OutPair<Point, Point> out = new OutPair<Point, Point>();
//        circle.FindIntersection(this, out);
//        Point pt1 = out.first();
//        Point pt2 = out.second();
//
//        if (!this.pointLiesBetweenEndpoints(pt1)) pt1 = null;
//        if (!this.pointLiesBetweenEndpoints(pt2)) pt2 = null;
//
//        return pt1 != null && pt2 != null;
//    }
//
//    public boolean Covers(Segment that)
//    {
//        if (this.HasSubSegment(that)) return true;
//
//        if (this.CoincidingWithOverlap(that)) return true;
//
//        return false;
//    }
//
//    public boolean Covers(Arc that)
//    {
//        return that.HasEndpoint(this._point1) && that.HasEndpoint(this._point2);
//    }
//
//    ////
//    //// Force this segment into standard position; only 1st and second quadrants allowed.
//    ////
//    //public Segment Standardize()
//    //{
//    //    Point vector = Point.MakeVector(this.Point1, this.Point2);
//
//    //    // If this segment is in the 3rd or 4th quadrant, force into the second by taking the opposite.
//    //    if (vector.getY() < 0) vector = Point.GetOppositeVector(vector);
//
//    //    return new Segment(origin, vector);
//    //}
//
//    //    public Segment ConstructSegmentByAngle(Point tail, int angle, int length)
//    //    {
//    //        // Make a vector in standard position
//    //        Point vector = Point.MakeVector(tail, this.OtherPoint(tail));
//    //
//    //        // Calculate the angle from standard position.
//    //        double stdPosAngle = Point.GetDegreeStandardAngleWithCenter(Point.ORIGIN, vector);
//    //
//    //        // Get the exact point we want.  ; CTA: Find this function
//    //        Point rotatedPoint = Figure.GetPointByLengthAndAngleInStandardPosition(length, stdPosAngle - angle);
//    //
//    //        return new Segment(tail, rotatedPoint);
//    //    }
//
//    //
//    // Is this segment congruent to the given segment in terms of the coordinatization from the UI?
//    //
//    public boolean CoordinateCongruent(Segment s)
//    {
//        return MathUtilities.doubleEquals(s.length(), this.length());
//    }
//
//    // Is this segment proportional to the given segment in terms of the coordinatization from the UI?
//    //
//    public Pair<Integer, Integer> CoordinateProportional(Segment s)
//    {
//        return MathUtilities.RationalRatio(s.length(), this.length());
//    }
//
//    //
//    // Acquire the point that is opposite the given point w.r.t. to this line.
//    //
//    //              x   given
//    //              |
//    //   ----------------------------
//    //              |
//    //              y   <-- opp returned
//    //
//    public Point GetReflectionPoint(Point pt)
//    {
//        Point ptOnLine = this.ProjectOnto(pt);
//
//        Segment perp = new Segment(pt, ptOnLine);
//
//        return (perp.GetOppositeSegment(ptOnLine)).OtherPoint(ptOnLine);
//    }
//
//    //
//    // Parallel and not Coinciding
//    //
//    public boolean IsParallelWith(Segment s)
//    {
//        if (IsCollinearWith(s)) return false;
//
//        if (IsVertical() && s.IsVertical()) return true;
//
//        if (IsHorizontal() && s.IsHorizontal()) return true;
//
//        return backend.utilities.math.MathUtilities.doubleEquals(s.slope(), this.slope());
//    }
//
//    //
//    // Parallel and not Coinciding
//    //
//    public boolean IsPerpendicularTo(Segment thatSegment)
//    {
//        if (IsVertical() && thatSegment.IsHorizontal()) return true;
//
//        if (IsHorizontal() && thatSegment.IsVertical()) return true;
//
//        return backend.utilities.math.MathUtilities.doubleEquals(thatSegment.slope() * this.slope(), -1);
//    }
//
//    //
//    // Is this segment parallel to the given segment in terms of the coordinatization from the UI?
//    //
//    public boolean CoordinateParallel(Segment s)
//    {
//        return IsParallelWith(s);
//    }
//
//    public static boolean IntersectAtSamePoint(Segment seg1, Segment seg2, Segment seg3)
//    {
//        Point intersection1 = seg1.FindIntersection(seg3);
//        Point intersection2 = seg2.FindIntersection(seg3);
//
//        return intersection1.equals(intersection2);
//    }
//
//    //
//    // Is this segment perpendicular to the given segment in terms of the coordinatization from the UI?
//    //
//    public Point CoordinatePerpendicular(Segment thatSegment)
//    {
//        //
//        // Do these segments intersect within both sets of stated endpoints?
//        //
//        Point intersection = this.FindIntersection(thatSegment);
//
//        if (!this.pointLiesBetweenEndpoints(intersection)) return null;
//        if (!thatSegment.pointLiesBetweenEndpoints(intersection)) return null;
//
//        //
//        // Special Case
//        //
//        if ((IsVertical() && thatSegment.IsHorizontal()) || (thatSegment.IsVertical() && IsHorizontal())) return intersection;
//
//        // Does m1 * m2 = -1 (opposite reciprocal slopes)
//        return backend.utilities.math.MathUtilities.doubleEquals(thatSegment.slope() * this.slope(), -1) ? intersection : null;
//    }
//
//    // Checking for structural equality (is it the same segment) excluding the multiplier
//    @Override
//    public boolean structurallyEquals(Object obj)
//    {
//        if(obj == null || !(obj instanceof Segment)) return false;
//
//        Segment segment = (Segment)obj;
//
//        if (segment._point1 == null || segment._point2 == null)
//            return false;
//        return ((segment._point1.structurallyEquals(_point1) && segment._point2.structurallyEquals(_point2)) ||
//                (segment._point1.structurallyEquals(_point2) && segment._point2.structurallyEquals(_point1)));
//    }
//
//    //
//    // Is thatSegment a bisector of this segment in terms of the coordinatization from the UI?
//    //
//    public Point CoordinateBisector(Segment thatSegment)
//    {
//        // Do these segments intersect within both sets of stated endpoints?
//        Point intersection = this.FindIntersection(thatSegment);
//
//        if (!this.pointLiesOnAndExactlyBetweenEndpoints(intersection)) return null;
//        if (!thatSegment.pointLiesBetweenEndpoints(intersection)) return null;
//
//        // Do they intersect in the middle of this segment
//        return backend.utilities.math.MathUtilities.doubleEquals(Point.calcDistance(this.getPoint1(), intersection), Point.calcDistance(this.getPoint2(), intersection)) ? intersection : null;
//    }
}
