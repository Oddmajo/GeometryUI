package backend.ast.figure.delegates;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.PointFactory;
import backend.utilities.math.MathUtilities;

/*
 * A processing, delegation class.
 * All functionality here treats any / all given segments AS LINES (infinite notions)
 * 
 */
public class LineDelegate
{
    /*
     * @param pt -- a point
     * @return true / false if this segment (infinite) contains the point
     * Use slope-intercept form to verify
     */
    public static boolean pointLiesOnLine(Segment segment, Point pt)
    {
        if (pt == null) return false;

        return MathUtilities.doubleEquals(segment.getPoint1().getY() - pt.getY(),
                                          segment.slope() * (segment.getPoint1().getX() - pt.getX()));
    }
    
    /*
     * @param that -- another segment
     * @return true / false if the two lines are strictly collinear
     */
    public static boolean areCollinear(Segment thisS, Segment that)
    {
        // If the segments are vertical, just compare the X values of one point of each
        // Also ensure that the segments have some overlap
        if (thisS.isVertical() && that.isVertical())
        {
            return MathUtilities.doubleEquals(thisS.getPoint1().getX(), that.getPoint1().getX())
                && (thisS.pointLiesOn(that.getPoint1()) || thisS.pointLiesOn(that.getPoint2()));
        }

        // If the segments are horizontal, just compare the Y values of one point of each; this is redundant
        if (thisS.isHorizontal() && that.isHorizontal())
        {
            return MathUtilities.doubleEquals(thisS.getPoint1().getY(), that.getPoint2().getY())
                && (thisS.pointLiesOn(that.getPoint1()) || thisS.pointLiesOn(that.getPoint2()));
        }

        // Slopes equate
        return MathUtilities.doubleEquals(thisS.slope(), that.slope()) &&
               (thisS.pointLiesOn(that.getPoint1()) || thisS.pointLiesOn(that.getPoint2()));
    }

    /*
     * Are the given segments collinear? Collinearity achieved (as a line, not segment)
     */
    public static boolean collinear(Segment thisS, Segment that)
    {
        return collinear(thisS, that.getPoint1()) && collinear(thisS, that.getPoint2());
    }
    /*
     * is the given point on the segment? Collinearity achieved (as a line, not segment)
     */
    public static boolean collinear(Segment s, Point pt)
    {
        return collinear(s.getPoint1(), s.getPoint2(), pt);
    }

    /*
     * Determines whether the three points are infinitely collinear.
     * Computes three distances; checks whether two sum to the third a la Betweenness check.
     */
    public static boolean collinear(Point one, Point two, Point three)
    {
        double dist1 = Point.calcDistance(one, two);
        double dist2 = Point.calcDistance(one, three);
        double dist3 = Point.calcDistance(two, three);
        
        // Betweeness for one combination?
        if (backend.utilities.math.MathUtilities.doubleEquals(dist1 + dist2, dist3)) return true;
        if (backend.utilities.math.MathUtilities.doubleEquals(dist1 + dist3, dist2)) return true;
        if (backend.utilities.math.MathUtilities.doubleEquals(dist2 + dist3, dist1)) return true;
        
        return false;
    }
    
    /*
     * @param segment -- a segment
     * @param pt -- any point in the plane
     * @return the perpendicular projection from @pt onto @segment
     * 
     * Given:                                  Result:
     *                                                     
     *      pt -->   x                            pt -->   x
     *                                                     
     *                                                     
     *             ------------------                    --P---------------
     */
    public static Point projectOnto(Segment segment, Point pt)
    {
        //
        // Special Cases
        //
        if (segment.isVertical())
        {
            return PointFactory.GeneratePoint(segment.getPoint1().getX(), pt.getY());
        }

        if (segment.isHorizontal())
        {
            return PointFactory.GeneratePoint(pt.getX(), segment.getPoint1().getY());
        }

        //
        // General Cases
        //

        // Find the line perpendicular; specifically, a point on that line
        double perpSlope = -1 / segment.slope();

        // We will choose a random value for x (to acquire y); we choose 1.
        double newX = pt.getX() == 0 ? 1 : 0;

        double newY = pt.getY() + perpSlope * (newX - pt.getX());

        // The new perpendicular segment is defined by (newX, newY) and pt
        return new Point("", newX, newY);
    }

    /*
     * @param segment -- a segment
     * @param pt -- any point in the plane
     * @return the line passing through the given point which is perpendicular to this segment.
     * 
     * Given:                                  Result:
     *                                                     |
     *      pt -->   x                            pt -->   x
     *                                                     |
     *                                                     | 90
     *             ------------------                    --P---------------
     */
    public static Segment getPerpendicularThrough(Segment segment, Point pt)
    {
        // If the given point is already on the line.
        if (segment.pointLiesBetweenEndpoints(pt))
        {
            if (segment.isVertical())
            {
                return new Segment(pt, new Point("", pt.getX() + 1, pt.getY()));
            }
            else if (segment.isHorizontal())
            {
                return new Segment(pt, new Point("", pt.getX(), pt.getY() + 1));
            }
            else
            {
                return new Segment(pt, new Point("", pt.getX() + 1, (- 1 / segment.slope()) + pt.getY()));
            }
        }

        Point projection = LineDelegate.projectOnto(segment, pt);

        // The new perpendicular segment is defined by the projection and the point
        return new Segment(projection, pt);
    }
    
    /*
     * @param thisS -- a line (infinite)
     * @param that -- a line (infinite)
     * @return true if @segment and @this are NOT coinciding, but are parallel
     *        Yes                     No
     * -------------------        ---------
     *     -------
     */
    public static boolean isParallel(Segment thisS, Segment that)
    {
        // Prohibit coinciding lines
        if (thisS.coinciding(that)) return false;

        if (thisS.isVertical() && that.isVertical()) return true;

        if (thisS.isHorizontal() && that.isHorizontal()) return true;

        return MathUtilities.doubleEquals(thisS.slope(), that.slope());
    }
    
    /*
     * @param that -- a segment (line)
     * @return true if the lines are perpendicular to one another
     * Verified via slope-based analysis
     */
    public static boolean isPerpendicular(Segment thisS, Segment that)
    {
        if (thisS.isVertical() && that.isHorizontal()) return true;

        if (thisS.isHorizontal() && that.isVertical()) return true;

        return MathUtilities.doubleEquals(that.slope() * thisS.slope(), -1);
    }
    
    
    
    

    
    
////    
////    public static boolean isCollinearWith(Segment s, Point p)
////    {
////        // If the segments are vertical, just compare the X values of one point of each
////        if (s.isVertical() && otherSegment.isVertical())
////        {
////            return backend.utilities.math.MathUtilities.doubleEquals(this._point1.getX(), otherSegment._point1.getX());
////        }
////
////        // If the segments are horizontal, just compare the Y values of one point of each; this is redundant
////        if (this.isHorizontal() && otherSegment.isHorizontal())
////        {
////            return backend.utilities.math.MathUtilities.doubleEquals(this._point1.getY(), otherSegment._point1.getY());
////        }
////
////        return backend.utilities.math.MathUtilities.doubleEquals(this._slope, otherSegment._slope) &&
////                this.PointLiesOn(otherSegment._point1) && this.PointLiesOn(otherSegment._point2); // Check both endpoints just to be sure
////    }
//
//    public boolean CoincidingWithoutOverlap(Segment thatSegment)
//    {
//        if (!isCollinearWith(thatSegment)) return false;
//
//        if (this.pointLiesBetweenEndpoints(thatSegment._point1)) return false;
//
//        if (this.pointLiesBetweenEndpoints(thatSegment._point2)) return false;
//
//        return true;
//    }
//
//    //
//    // Use point-_slope form to determine if the given point is on the line
//    //
//    public boolean pointLiesBetweenEndpoints(Point thatPoint)
//    {
//        if (thatPoint == null) return false;
//
//        return Segment.Between(thatPoint, _point1, _point2);
//    }
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
//        if (!isCollinearWith(thatSegment)) return false;
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
//        if (!isCollinearWith(thatSegment)) return false;
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
//    public boolean isIncludedSegment(Angle ang1, Angle ang2)
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
//        // is the given point on other? If so, we cannot make a determination.
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
//    // Return the line passing through the given point which is perpendicular to this segment. 
//    //
//    public Point ProjectOnto(Point pt)
//    {
//        //
//        // Special Cases
//        //
//        if (this.isVertical())
//        {
//            Point newPoint = Point.GetFigurePoint(new Point("", this.getPoint1().getX(), pt.getY()));
//
//            return newPoint != null ? newPoint : new Point("", this.getPoint1().getX(), pt.getY());
//        }
//
//        if (this.isHorizontal())
//        {
//            Point newPoint = Point.GetFigurePoint(new Point("", pt.getX(), this.getPoint1().getY()));
//
//            return newPoint != null ? newPoint : new Point("", pt.getX(), this.getPoint1().getY());
//        }
//
//        //
//        // General Cases
//        //
//
//        // Find the line perpendicular; specifically, a point on that line
//        double perpSlope = -1 / _slope;
//
//        // We will choose a random value for x (to acquire y); we choose 1.
//        double newX = pt.getX() == 0 ? 1 : 0;
//
//        double newY = pt.getY() + perpSlope * (newX - pt.getX());
//
//        // The new perpendicular segment is defined by (newX, newY) and pt
//        return new Point("", newX, newY);
//    }
//
//    //
//    // Return the line passing through the given point which is perpendicular to this segment. 
//    //
//    public Segment GetPerpendicular(Point pt)
//    {
//        // If the given point is already on the line.
//        if (this.pointLiesBetweenEndpoints(pt))
//        {
//            if (this.isVertical())
//            {
//                return new Segment(pt, new Point("", pt.getX() + 1, pt.getY()));
//            }
//            else if (this.isHorizontal())
//            {
//                return new Segment(pt, new Point("", pt.getX(), pt.getY() + 1));
//            }
//            else
//            {
//                return new Segment(pt, new Point("", pt.getX() + 1, (- 1 / this._slope) + pt.getY()));
//            }
//        }
//
//        Point projection = ProjectOnto(pt);
//
//        // The new perpendicular segment is defined by the projection and the point
//        return new Segment(projection, pt);
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
//    // is the given segment a secant THROUGH this circle? (2 intersection points)
//    //
//    public boolean isSecant(Circle circle)
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
//    // is this segment congruent to the given segment in terms of the coordinatization from the UI?
//    //
//    public boolean CoordinateCongruent(Segment s)
//    {
//        return MathUtilities.doubleEquals(s.length(), this.length());
//    }
//
//    // is this segment proportional to the given segment in terms of the coordinatization from the UI?
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
//    public boolean isParallelWith(Segment s)
//    {
//        if (isCollinearWith(s)) return false;
//
//        if (isVertical() && s.isVertical()) return true;
//
//        if (isHorizontal() && s.isHorizontal()) return true;
//
//        return backend.utilities.math.MathUtilities.doubleEquals(s.slope(), this.slope());
//    }
//
//    //
//    // Parallel and not Coinciding
//    //
//    public boolean isPerpendicularTo(Segment thatSegment)
//    {
//        if (isVertical() && thatSegment.isHorizontal()) return true;
//
//        if (isHorizontal() && thatSegment.isVertical()) return true;
//
//        return backend.utilities.math.MathUtilities.doubleEquals(thatSegment.slope() * this.slope(), -1);
//    }
//
//    //
//    // is this segment parallel to the given segment in terms of the coordinatization from the UI?
//    //
//    public boolean CoordinateParallel(Segment s)
//    {
//        return isParallelWith(s);
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
//    // is this segment perpendicular to the given segment in terms of the coordinatization from the UI?
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
//        if ((isVertical() && thatSegment.isHorizontal()) || (thatSegment.isVertical() && isHorizontal())) return intersection;
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
//    // is thatSegment a bisector of this segment in terms of the coordinatization from the UI?
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
