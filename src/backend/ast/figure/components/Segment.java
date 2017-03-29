package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.DimensionalLength;
import backend.ast.figure.Figure;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.delegates.LineDelegate;
import backend.ast.figure.delegates.MidpointDelegate;
import backend.ast.figure.delegates.RayDelegate;
import backend.ast.figure.delegates.SegmentDelegate;
import backend.ast.figure.delegates.intersections.IntersectionDelegate;
import backend.utilities.Pair;
import backend.utilities.math.MathUtilities;
import backend.utilities.translation.OutPair;

public class Segment extends DimensionalLength
{
    protected Point _point1;
    protected Point _point2;
    protected double _length;
    protected double _slope;

    public Point getPoint1() { return _point1; }
    public Point getPoint2() { return _point2; }
    public double length() { return _length; }
    public double slope() { return _slope; }

    //    public boolean DefinesCollinearity() { return collinear.size() > 2; }
    @Override
    public void ClearCollinear()
    {
        super.ClearCollinear();
        _collinear.add(_point1);
        _collinear.add(_point2);
    }

    public Segment(Segment in) { this(in._point1, in._point2); }
    public Segment(Point p1, Point p2)
    {
        _point1 = p1;
        _point2 = p2;
        _length = Point.calcDistance(p1, p2);
        _slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        //_slope = getSlope(); Removed on 11/2/2016 by Nick. Dealing with vertical slopes using Infinity instead of null. 

        //        backend.utilities.list.Utilities.addUniqueStructurally(_point1.getSuperFigures(), this);
        //        backend.utilities.list.Utilities.addUniqueStructurally(_point2.getSuperFigures(), this);


        _collinear = new ArrayList<Point>();
        // We add the two points arbitrarily since this list is vacuously ordered.
        _collinear.add(p1);
        _collinear.add(p2);
    }


    //    /** CTA: Why is this here locally?
    //     * Method to compare two double with the given accuracy
    //     * @param accuracy      accuracy used to compare the doubles
    //     * @param d1            first double to compare
    //     * @param d2            second double to compare
    //     * @return true if the two double are within the given accuracy of each other
    //     */
    //    public static boolean doubleCompare(double accuracy, double d1, double d2)
    //    {
    //        if (Math.abs(d1 - d2) < accuracy) { return true; }
    //        return false;
    //    }

    /*
     * @param pt -- point to be treated as the origin point of the resultant ray
     * @return a Ray corresponding to this segment and origin point @pt.
     */
    public Ray asRay(Point origin)
    {
        // Origin better be an endpoint of this segment
        if (!this.has(origin)) return null;

        return new Ray(origin, this.other(origin));
    }

    /*
     * Do these lines cross (arbitrarily)?
     */
    public boolean intersects(Segment that) { return SegmentDelegate.intersects(this, that); }

    /*
     * @param thisS -- a segment
     * @param that -- a segment
     * Do these lines cross in the middle of each segment?
     */
    public boolean middleCrosses(Segment that) { return SegmentDelegate.middleCrosses(this, that); }

    /* 
     * @return the least of the two segment points (lexicographically)
     */
    public Point leastPoint() { return _point1.compareTo(_point2) < 0 ? _point1 : _point2; }

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
    public Segment segmentByAngle(Point origin, double angle, double length)
    {
        return rayByAngle(origin, angle, length).asSegment();
    }
    public Ray rayByAngle(Point origin, double angle, double length)
    {
        return RayDelegate.rayByAngle(this.asRay(origin), angle, length);
    }

    /*
     * @param pt -- a point
     * @return true / false if this segment (finite) contains the point
     */
    public boolean pointLiesOn(Point pt) { return this.pointLiesOnSegment(pt); }

    /*
     * @param pt -- a point
     * @return true / false if this segment (finite) contains the point
     */
    public boolean pointLiesOnSegment(Point pt) { return SegmentDelegate.pointLiesOnSegment(this, pt); }

    /*
     * @param pt -- a point
     * @return true / false if this segment (finite) contains the point
     */
    public boolean pointLiesOnLine(Point pt) { return LineDelegate.pointLiesOnLine(this, pt); }

    /*
     * @param pt -- a point
     * @return true if the point is on the segment (EXcluding endpoints); finite examination only
     */
    public boolean pointLiesBetweenEndpoints(Point pt) { return SegmentDelegate.pointLiesBetweenEndpoints(this, pt); }

    /*
     * @param that -- another segment
     * @return true / false if the two lines (infinite) are collinear
     */
    public boolean isCollinearWith(Segment that) { return LineDelegate.areCollinear(this, that); }
    
    /**
     * 
     * @param that -- another segment
     * @return true / false if the two lines are collinear but do not touch
     *       A     B
     *       __    __
     *      |  |__|  |
     *      |________|
     */
    public boolean isCollinearWithoutOverlap(Segment that) { return !LineDelegate.areCollinear(this, that) && LineDelegate.collinear(this, that); }

    /*
     * @param that -- another segment
     * @return true / false if this contains @that segment entirely (finite)
     *        this:       *----------v--------v--------------*
     *                               ---that---
     * sub-segment?
     */
    public boolean contains(Segment that) { return SegmentDelegate.contains(this, that); }

    /*
     * @param that -- another segment
     * @return true / false if this contains @that segment entirely (finite)
     *        this:       *----------v--------v--------------*
     *                               ---that---
     * sub-segment?
     */
    public boolean strictContains(Segment that) { return SegmentDelegate.strictContains(this, that); }

    /*
     * @param that -- another segment
     * @return true / false if @that contains @this segment entirely (finite)
     *        that:       *----------v--------v--------------*
     *                               ---this---
     * super-segment?
     */
    public boolean containedIn(Segment that) { return SegmentDelegate.contains(that, this); }

    /*
     * @param that -- another segment
     * @return true / false if @that contains @this segment entirely (finite)
     *        that:       *----------v--------v--------------*
     *                               ---this---
     * super-segment?
     */
    public boolean strictContainedIn(Segment that) { return SegmentDelegate.strictContains(that, this); }

    /*
     * @param p -- a point
     * @return true if @pt is one of the endpoints of this segment
     */
    public boolean has(Point p) { return _point1.equals(p) || _point2.equals(p); }

    /*
     * @return true if this segment is horizontal (by analysis of both endpoints having same y-coordinate)
     */
    public boolean isHorizontal() { return MathUtilities.doubleEquals(_point1.getY(), _point2.getY()); }

    /*
     * @return true if this segment is vertical (by analysis of both endpoints having same x-coordinate)
     */
    public boolean isVertical() { return MathUtilities.doubleEquals(_point1.getX(), _point2.getX()); }

    /*
     * @return the midpoint of this segment (finite)
     */
    public Point getMidpoint() { return MidpointDelegate.getMidpoint(this); }

    /*
     * @param that -- a segment (as a line: infinite)
     * @return the intersection of this segment with that
     */
    public Point lineIntersection(Segment that) {  return IntersectionDelegate.lineIntersection(this, that); }

    /*
     * @param that -- a segment (as a segment: finite)
     * @return the midpoint of this segment (finite)
     */
    public Point segmentIntersection(Segment that) {  return IntersectionDelegate.segmentIntersection(this, that); }

    /*
     * @param pt -- a point
     * @return a segment perpendicular to this segment passing through @pt
     */
    public Segment getPerpendicularThrough(Point pt) { return LineDelegate.getPerpendicularThrough(this, pt); }

    /*
     * @return perpendicular bisector to the given segment
     * The returned segment should be treated as a line (infinite)
     */
    public Segment perpendicularBisector() { return SegmentDelegate.perpendicularBisector(this); }

    /*
     * @param circle -- a circle
     * @return true if this segment (finite) passes through this circle (intersection points are on the interior of the segment)
     *      /     \
     *   __/_______\____
     *    /         \
     */
    public boolean isSecant(Circle circle) { return SegmentDelegate.isSecant(this, circle); }

    /*
     * @param segments -- a list of segments
     * @return the common, coincident point of intersection; null if the segment intersection points are not mutually coincident
     * 
     *             |/
     *     --------X------  In this example, three segments intersect at a common point: X
     *            /|
     */
    public static Point coincident(Segment... segments) { return SegmentDelegate.coincident(segments); }

    /*
     * @param that -- a line (infinite)
     * @return true if @this and @that are coinciding lines
     */
    public boolean coinciding(Segment that) { return isCollinearWith(that); }

    /*
     * @param that -- a segment (finite)
     * @return true if @this and @that are coinciding segments
     *             Yes                No
     *     _________ _________     ______.________
     */
    public boolean coincidingWithoutOverlap(Segment that) { return isCollinearWith(that) && !this.contains(that) && !this.containedIn(that); }

    /*
     * @param thatt -- a line (infinite)
     * @return true if @segment and @this are NOT coinciding, but are parallel
     *        Yes                     No
     * -------------------        ---------
     *     -------
     */
    public boolean isParallel(Segment that) { return LineDelegate.isParallel(this, that); }

    /*
     * @param that -- a segment (line)
     * @return true if the lines are perpendicular to one another
     * Verified via slope-based analysis
     */
    public boolean isSegmentPerpendicular(Segment that) { return SegmentDelegate.isPerpendicular(this, that); }

    /*
     * @param that -- a segment (line)
     * @return true if the lines are perpendicular to one another
     * Verified via slope-based analysis
     */
    public boolean isLinePerpendicular(Segment that) { return LineDelegate.isPerpendicular(this, that); }

    /*
     * @param pt -- one of the endpoints of this segment
     * @return the 'other' endpoint of the segment (null if neither endpoint is given)
     */
    public Point other(Point p)
    {
        if (p.structurallyEquals(_point1)) return _point2;
        if (p.structurallyEquals(_point2)) return _point1;

        return null;
    }

    /*
     * @param that -- a segment
     * @return true if the segments coincide, but do not overlap:
     *                    this                  that
     *             ----------------           ---------
     * Note: the segment MAY share an endpoint
     */
    public boolean coincideWithoutOverlap(Segment that)
    {
        if (!isCollinearWith(that)) return false;

        // Check the endpoints of @that 
        if (this.pointLiesBetweenEndpoints(that.getPoint1())) return false;

        if (this.pointLiesBetweenEndpoints(that.getPoint2())) return false;

        return true;
    }

    /*
     * @param M -- a point
     * @param A -- a point
     * @param B -- a point
     * @return true if the three points are (1) collinear and (2) M is between A and B
     *                                     A-------------M---------B
     * Note: returns true if M is one of the endpoints
     */
    public static boolean Between(Point M, Point A, Point B)
    {
        return MathUtilities.doubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B), Point.calcDistance(A, B));
    }

    @Override
    public boolean intersection(Arc arc, OutPair<Point, Point> out) { return arc.intersection(this, out); }

    @Override
    public boolean intersection(Segment segment, OutPair<Point, Point> out)
    {
        Point intersectionPt = this.segmentIntersection(segment);

        out = new OutPair<Point, Point>(intersectionPt, null);

        return intersectionPt != null;
    }


    //
    // To be verified as a necessity....BEGIN
    //
    //
    public static boolean LooseBetween(Point M, Point A, Point B)
    {
        return backend.utilities.math.MathUtilities.looseDoubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B),
                Point.calcDistance(A, B));
    }
    public boolean LooseCrosses(Segment that)
    {
        Point p = this.lineIntersection(that);

        if (p == null) return false;

        if (this.has(p) || that.has(p)) return false;

        return LooseBetween(p, this._point1, this._point2) && LooseBetween(p, that.getPoint1(), that.getPoint2());
    }
    //
    // To be verified as a necessity....END
    //
    //


    @Override
    public String toString() { return "Segment(" + _point1.toString() + ", " + _point2.toString() + ")"; }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Segment)) return false;
        Segment that = (Segment)obj;

        return (_point1.structurallyEquals(that.getPoint1()) && _point2.structurallyEquals(that.getPoint2())) ||
               (_point1.structurallyEquals(that.getPoint2()) && _point2.structurallyEquals(that.getPoint1()));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!this.structurallyEquals(obj)) return false;

        Segment that = (Segment)obj;

        return this.has(that.getPoint1()) && this.has(that.getPoint2());
    }


    public Point sharedVertex(Segment s)
    {
        if (_point1.equals(s._point1)) return _point1;
        if (_point1.equals(s._point2)) return _point1;
        if (_point2.equals(s._point1)) return _point2;
        if (_point2.equals(s._point2)) return _point2;
        return null;
    }

    //
    //
    // Coordinate-Based Computations for FactComputer (to be moved eventually)
    //
    //
    //

    //
    // is this segment congruent to the given segment in terms of the coordinatization from the UI?
    //
    public boolean coordinateCongruent(Segment s)
    {
        return MathUtilities.doubleEquals(s.length(), this.length());
    }

    //
    // is this segment perpendicular to the given segment in terms of the coordinatization from the UI?
    //
    public Point coordinatePerpendicular(Segment s)
    {
        if (!this.isSegmentPerpendicular(s)) return null;

        return this.segmentIntersection(s);
    }

    // is this segment proportional to the given segment in terms of the coordinatization from the UI?
    //
    public Pair<Integer, Integer> coordinateProportional(Segment s)
    {
        return MathUtilities.RationalRatio(s.length(), this.length());
    }

    //
    //   
    //  ------------------------ this
    //              /
    //             / that
    //            /
    public Point coordinateBisector(Segment that)
    {
        // Do these segments intersect within both sets of stated endpoints?
        Point intersection = this.segmentIntersection(that);
        
        if(intersection == null)
            return null;
        
        // Do they intersect in the middle of this segment
        return MathUtilities.doubleEquals(Point.calcDistance(this.getPoint1(), intersection),
                Point.calcDistance(this.getPoint2(), intersection)) ? intersection : null;
    }

















    //    
    //    
    //

    //

    //

    //
    //
    //

    //
    //
    //
        // Does this segment contain a subsegment:
        // A-------B-------C------D
        // A subsegment is: AB, AC, AD, BC, BD, CD
        public boolean HasSubSegment(Segment possSubSegment)
        {
            return this.pointLiesBetweenEndpoints(possSubSegment._point1) && this.pointLiesBetweenEndpoints(possSubSegment._point2);
        }
    //
    //    public boolean HasStrictSubSegment(Segment possSubSegment)
    //    {
    //        return (this.pointLiesBetweenEndpoints(possSubSegment._point1) && this.pointLiesBetweenEndpoints(possSubSegment._point2)) ||
    //                (this.pointLiesBetweenEndpoints(possSubSegment._point2) && this.pointLiesBetweenEndpoints(possSubSegment._point1));
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

    //

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

    //    
    //    //
    //    // Do these angles share this segment overlay this angle?
    //    //
        public boolean isIncludedSegment(Angle ang1, Angle ang2)
        {
            return this.equals(ang1.sharedRay(ang2));
        }
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
    //
    //

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
    ////    //
    ////    // Return the line passing through the given point which is perpendicular to this segment. 
    ////    //
    ////    public Point ProjectOnto(Point pt) { return SegmentDelegate.projectOnto(pt); }
    //

    //    

    //
    //    //
    //    // Given one of the fixed endpoints on this segment, return a congruent segment with the: fixed endpoint and the other point being moved.
    //    //
    //    public Segment GetOppositeSegment(Point pt)
    //    {
    //        if (!has(pt)) return null;
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
    //        Segment perp = this.getPerpendicularThrough(pt);
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
    //        Point ptOnLine = LineDelegate.projectOnto(this, pt);
    //
    //        Segment perp = new Segment(pt, ptOnLine);
    //
    //        return (perp.GetOppositeSegment(ptOnLine)).OtherPoint(ptOnLine);
    //    }
    //

    //

    //

    //

    //
    //
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

}
