package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.List;

import backend.ast.ASTException;
import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutPair;
import backend.utilities.translation.OutTriple;

public class Segment extends Figure
{
    protected Point _point1;
    protected Point _point2;
    protected double _length;
    protected double _slope;

    public Point getPoint1() { return _point1; }
    public Point getPoint2() { return _point2; }
    public double length() { return _length; }
    public double slope() { return _slope; }
    
    public boolean DefinesCollinearity() { return collinear.size() > 2; }

    public Segment(Point p1, Point p2)
    {
        _point1 = p1;
        _point2 = p2;
        _length = Point.calcDistance(p1, p2);
        _slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());

        backend.utilities.list.Utilities.addUniqueStructurally(_point1.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(_point2.getSuperFigures(), this);


        collinear = new ArrayList<Point>();
        // We add the two points arbitrarily since this list is vacuously ordered.
        collinear.add(p1);
        collinear.add(p2);
    }

    public boolean crosses(Segment that)
    {
        Point p = this.FindIntersection(that);

        if (p == null) return false;

        return this.pointLiesOnAndExactlyBetweenEndpoints(p) && that.pointLiesOnAndExactlyBetweenEndpoints(p);
    }

    public boolean pointLiesOnAndExactlyBetweenEndpoints(Point thatPoint)
    {
        if (thatPoint == null) return false;

        if (this.hasPoint(thatPoint)) return false;

        return Segment.Between(thatPoint, _point1, _point2);
    }

    public boolean hasPoint(Point p)
    {
        return _point1.equals(p) || _point2.equals(p);
    }

    public boolean IsHorizontal()
    {
        return backend.utilities.math.Utilities.doubleEquals(this._point1.getY(), this._point2.getY());
    }

    public boolean IsVertical()
    {
        return backend.utilities.math.Utilities.doubleEquals(this._point1.getX(), this._point2.getX());
    }

    public boolean IsCollinearWith(Segment otherSegment)
    {
     // If the segments are vertical, just compare the X values of one point of each
        if (this.IsVertical() && otherSegment.IsVertical())
        {
            return backend.utilities.math.Utilities.doubleEquals(this._point1.getX(), otherSegment._point1.getX());
        }

        // If the segments are horizontal, just compare the Y values of one point of each; this is redundant
        if (this.IsHorizontal() && otherSegment.IsHorizontal())
        {
            return backend.utilities.math.Utilities.doubleEquals(this._point1.getY(), otherSegment._point1.getY());
        }

        return backend.utilities.math.Utilities.doubleEquals(this._slope, otherSegment._slope) &&
               this.PointLiesOn(otherSegment._point1) && this.PointLiesOn(otherSegment._point2); // Check both endpoints just to be sure
    }

    public Point SharedVertex(Segment s)
    {
        if (_point1.equals(s._point1)) return _point1;
        if (_point1.equals(s._point2)) return _point1;
        if (_point2.equals(s._point1)) return _point2;
        if (_point2.equals(s._point2)) return _point2;
        return null;
    }

    public boolean CoincidingWithoutOverlap(Segment thatSegment)
    {
        if (!IsCollinearWith(thatSegment)) return false;

        if (this.PointLiesOnAndBetweenEndpoints(thatSegment._point1)) return false;

        if (this.PointLiesOnAndBetweenEndpoints(thatSegment._point2)) return false;

        return true;
    }

    //
    // Use point-_slope form to determine if the given point is on the line
    //
    public boolean PointLiesOnAndBetweenEndpoints(Point thatPoint)
    {
        if (thatPoint == null) return false;

        return Segment.Between(thatPoint, _point1, _point2);
    }

    public static boolean Between(Point M, Point A, Point B)
    {
        return backend.utilities.math.Utilities.doubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B),
                Point.calcDistance(A, B));
    }

    public Point OtherPoint(Point p)
    {
        if (p.equals(_point1)) return _point2;
        if (p.equals(_point2)) return _point1;

        return null;
    }
    
    //
    // Does the given segment overlay this segment; we are looking at both as a RAY only.
    // We assume both rays share the same start vertex
    //
    public boolean rayOverlays(Segment thatRay)
    {
        if (this.Equals(thatRay)) return true;

        if (!this.IsCollinearWith(thatRay)) return false;

        // Do they share a vertex?
        Point shared = this.SharedVertex(thatRay);

        if (shared == null) return false;

        Point thatOtherPoint = thatRay.OtherPoint(shared);
        Point thisOtherPoint = this.OtherPoint(shared);

        // Is thatRay smaller than the this ray
        if (Between(thatOtherPoint, shared, thisOtherPoint)) return true;

        // Or if that Ray extends this Ray
        if (Between(thisOtherPoint, shared, thatOtherPoint)) return true;

        return false;
    }

    @Override
    public String toString() { return "Segment(" + _point1.toString() + ", " + _point2.toString() + ")"; }


    // Does this segment contain a subsegment:
    // A-------B-------C------D
    // A subsegment is: AB, AC, AD, BC, BD, CD
    public boolean HasSubSegment(Segment possSubSegment)
    {
        return this.PointLiesOnAndBetweenEndpoints(possSubSegment._point1) && this.PointLiesOnAndBetweenEndpoints(possSubSegment._point2);
    }

    public boolean HasStrictSubSegment(Segment possSubSegment)
    {
        return (this.PointLiesOnAndBetweenEndpoints(possSubSegment._point1) && this.pointLiesOnAndExactlyBetweenEndpoints(possSubSegment._point2)) ||
               (this.PointLiesOnAndBetweenEndpoints(possSubSegment._point2) && this.pointLiesOnAndExactlyBetweenEndpoints(possSubSegment._point1));
    }

    //
    // Are the segments coinciding and share an endpoint?
    //
    public boolean AdjacentCoinciding(Segment thatSegment)
    {
        if (!IsCollinearWith(thatSegment)) return false;

        Point shared = this.SharedVertex(thatSegment);

        return shared == null ? false : true;
    }

    //
    // Are the segments coinciding with overlap? That is, it's ok to be coinciding with no overlap.
    //
    public boolean CoincidingWithOverlap(Segment thatSegment)
    {
        if (!IsCollinearWith(thatSegment)) return false;

        if (this.StructurallyEquals(thatSegment)) return true;

        if (this.pointLiesOnAndExactlyBetweenEndpoints(thatSegment._point1)) return true;

        if (this.pointLiesOnAndExactlyBetweenEndpoints(thatSegment._point2)) return true;

        return false;
    }

    //
    // Do these segments creates an X?
    //
    public boolean Crosses(Segment that)
    {
        Point p = this.FindIntersection(that);

        if (p == null) return false;

        return this.pointLiesOnAndExactlyBetweenEndpoints(p) && that.pointLiesOnAndExactlyBetweenEndpoints(p);
    }


    //
    //
    //
    //
    // Analysis and Manipulation
    //
    //
    //
    //
    
    //
    // Do these angles share this segment overlay this angle?
    //
    public boolean IsIncludedSegment(Angle ang1, Angle ang2)
    {
        return this.equals(ang1.SharedRay(ang2));
    }

    //
    // Determine the intersection point of the two segments
    //
    //
    // | a b |
    // | c d |
    //
    private double determinant(double a, double b, double c, double d)
    {
        return a * d - b * c;
    }
    
    private void MakeLine(double x_1, double y_1, double x_2, double y_2, OutTriple<Double, Double, Double> out)
    {
        double slope = (y_2 - y_1) / (x_2 - x_1);
        double a = - slope;
        double b = 1;
        double c = y_2 - slope * x_2;
        
        out.set(a, b, c);
    }

    private double EvaluateYGivenX(double a, double b, double e, double x)
    {
        // ax + by = e
        return (e - a * x) / b;
    }

    private double EvaluateXGivenY(double a, double b, double e, double y)
    {
        // ax + by = e
        return (e - b * y) / a;
    }
    
    public Point FindIntersection(Segment thatSegment)
    {
        // Special Case: Collinear, but non-overlapping.
        if (this.CoincidingWithoutOverlap(thatSegment)) return null;

        // Special Case: Intersect at an endpoint
        Point shared = this.SharedVertex(thatSegment);
        if (shared != null) return shared;

        double a, b, c, d, e, f;

        if (this.IsVertical() && thatSegment.IsHorizontal()) return new Point(null, this.getPoint1().getX(), thatSegment.getPoint1().getY());

        if (thatSegment.IsVertical() && this.IsHorizontal()) return new Point(null, thatSegment.getPoint1().getX(), this.getPoint1().getY());

        OutTriple<Double, Double, Double> out = new OutTriple<Double, Double, Double>(); 
        if (this.IsVertical())
        {
            MakeLine(thatSegment.getPoint1().getX(), thatSegment.getPoint1().getY(), thatSegment.getPoint2().getX(), thatSegment.getPoint2().getY(), out);// a, out b, out e);
            a = out.getFirst();
            b = out.getSecond();
            e = out.getThird();
            return new Point(null, this.getPoint1().getX(), EvaluateYGivenX(a, b, e, this.getPoint1().getX()));
        }
        if (thatSegment.IsVertical())
        {
            MakeLine(this.getPoint1().getX(), this.getPoint1().getY(), this.getPoint2().getX(), this.getPoint2().getY(), out);
            a = out.getFirst();
            b = out.getSecond();
            e = out.getThird();
            return new Point(null, thatSegment.getPoint1().getX(), EvaluateYGivenX(a, b, e, thatSegment.getPoint1().getX()));
        }
        if (this.IsHorizontal())
        {
            MakeLine(thatSegment.getPoint1().getX(), thatSegment.getPoint1().getY(), thatSegment.getPoint2().getX(), thatSegment.getPoint2().getY(), out);
            a = out.getFirst();
            b = out.getSecond();
            e = out.getThird();

            return new Point(null, EvaluateXGivenY(a, b, e, this.getPoint1().getY()), this.getPoint1().getY());
        }
        if (thatSegment.IsHorizontal())
        {
            MakeLine(this.getPoint1().getX(), this.getPoint1().getY(), this.getPoint2().getX(), this.getPoint2().getY(), out);
            a = out.getFirst();
            b = out.getSecond();
            e = out.getThird();

            return new Point(null, EvaluateXGivenY(a, b, e, thatSegment.getPoint1().getY()), thatSegment.getPoint1().getY());
        }

        //
        // ax + by = e
        // cx + dy = f
        // 

        MakeLine(_point1.getX(), _point1.getY(), _point2.getX(), _point2.getY(), out);
        a = out.getFirst();
        b = out.getSecond();
        e = out.getThird();

        MakeLine(thatSegment.getPoint1().getX(), thatSegment.getPoint1().getY(), thatSegment.getPoint2().getX(), thatSegment.getPoint2().getY(), out);
        c = out.getFirst();
        d = out.getSecond();
        f = out.getThird();

        
        double overallDeterminant = a * d - b * c;
        double x = determinant(e, b, f, d) / overallDeterminant;
        double y = determinant(a, e, c, f) / overallDeterminant;

        return new Point("Intersection", x, y);
    }

    //
    //     PointA
    //     |
    //     |             X (pt)
    //     |_____________________ otherSegment
    //     |
    //     |
    //     PointB
    //
    public Point SameSidePoint(Segment otherSegment, Point pt)
    {
        // Is the given point on other? If so, we cannot make a determination.
        if (otherSegment.PointLiesOn(pt)) return null;

        // Make a vector out of this vector as well as the vector connecting one of the points to the given pt
        Vector thisVector = new Vector(_point1, _point2);
        Vector thatVector = new Vector(_point1, pt);

        Vector projectionOfOtherOntoThis = thisVector.Projection(thatVector);

        // We are interested most in the endpoint of the projection (which is not the 
        Point projectedEndpoint = projectionOfOtherOntoThis.NonOriginEndpoint();

        // Find the intersection between the two lines
        Point intersection = FindIntersection(otherSegment);

        if (this.PointLiesOn(projectedEndpoint))
        {
            ExceptionHandler.throwException(new ASTException("Unexpected: Projection does not lie on this line. " + this + " " + projectedEndpoint));
        }

        // The endpoint of the projection is on this vector. Therefore, we can judge which side of the given segment the given pt lies on.
        if (Segment.Between(projectedEndpoint, _point1, intersection)) return _point1;
        if (Segment.Between(projectedEndpoint, _point2, intersection)) return _point2;

        return null;
    }

    public Point Midpoint()
    {
        return new Point(null, (_point1.getX() + _point2.getX()) / 2.0, (_point1.getY() + _point2.getY()) / 2.0);
    }
    
    public static boolean LooseBetween(Point M, Point A, Point B)
    {
        return backend.utilities.math.Utilities.looseDoubleEquals(Point.calcDistance(A, M) + Point.calcDistance(M, B),
                                                          Point.calcDistance(A, B));
    }
    
    public boolean LooseCrosses(Segment that)
    {
        Point p = this.FindIntersection(that);

        if (p == null) return false;

        if (this.hasPoint(p) || that.hasPoint(p)) return false;

        return LooseBetween(p, this._point1, this._point2) && LooseBetween(p, that.getPoint1(), that.getPoint2());
    }
    
    // @Override
    public void FindIntersection(Segment that, OutPair<Point, Point> out)
    {
        Point inter1 = FindIntersection(that);
        Point inter2 = null;

        if (!this.PointLiesOnAndBetweenEndpoints(inter1)) inter1 = null;
        if (!that.PointLiesOnAndBetweenEndpoints(inter1)) inter1 = null;
        
        out.set(inter1,  inter2);
    }

    @Override
    public void FindIntersection(Arc that, OutPair<Point, Point> out)
    {
        that.FindIntersection(this, out);
    }

    private static class Vector
    {
        private double originX;
        private double originY;
        private double otherX;
        private double otherY;

        public Vector(Point origin, Point other)
        {
            originX = origin.getX();
            originY = origin.getY();
            otherX = other.getX();
            otherY = other.getY();
        }

        public Vector(double x1, double y1, double x2, double y2)
        {
            originX = x1;
            originY = y1;
            otherX = x2;
            otherY = y2;
        }

        public Point NonOriginEndpoint() { return new Point("ProjectedEndpoint", otherX, otherY); }

        private double DotProduct() { return originX * otherX + originY * otherY; }
        private static double EuclideanDistance(double x1, double y1, double x2, double y2)
        {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        }

        //
        // Projects the given vector onto this vector using standard vector projection
        //
        public Vector Projection(Vector thatVector)
        {
            double magnitude = EuclideanDistance(thatVector.originX, thatVector.originY, thatVector.otherX, thatVector.otherY);
            double cosIncluded = CosineOfIncludedAngle(thatVector);

            double projectionDistance = magnitude * cosIncluded;

            return new Vector(originX, originY, otherX / projectionDistance, otherY / projectionDistance);
        }

        //
        // Use Law of Cosines to determine cos(\theta)
        //      ^
        //      / \
        //   a /   \ c
        //    /\    \
        //   /__\____\__>
        //       b 
        //
        private double CosineOfIncludedAngle(Vector thatVector)
        {
            if (HasSameOriginPoint(thatVector)) return -2;

            double a = EuclideanDistance(originX, originY, otherX, otherY);
            double b = EuclideanDistance(originX, originY, thatVector.otherX, thatVector.otherY);
            double c = EuclideanDistance(otherX, otherY, thatVector.otherX, thatVector.otherY);

            // Law of Cosines
            return (Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b);
        }

        private boolean HasSameOriginPoint(Vector thatVector)
        {
            return backend.utilities.math.Utilities.doubleEquals(originX, thatVector.originX) &&
                   backend.utilities.math.Utilities.doubleEquals(originY, thatVector.originY);
        }

        @Override
        public String toString()
        {
            return "(" + originX + ", " + originY + ") -> (" + otherX + ", " + otherY + ")";
        }
    }

    //
    // Return the line passing through the given point which is perpendicular to this segment. 
    //
    public Point ProjectOnto(Point pt)
    {
        //
        // Special Cases
        //
        if (this.IsVertical())
        {
            Point newPoint = Point.GetFigurePoint(new Point("", this.getPoint1().getX(), pt.getY()));

            return newPoint != null ? newPoint : new Point("", this.getPoint1().getX(), pt.getY());
        }

        if (this.IsHorizontal())
        {
            Point newPoint = Point.GetFigurePoint(new Point("", pt.getX(), this.getPoint1().getY()));

            return newPoint != null ? newPoint : new Point("", pt.getX(), this.getPoint1().getY());
        }

        //
        // General Cases
        //

        // Find the line perpendicular; specifically, a point on that line
        double perpSlope = -1 / _slope;

        // We will choose a random value for x (to acquire y); we choose 1.
        double newX = pt.getX() == 0 ? 1 : 0;

        double newY = pt.getY() + perpSlope * (newX - pt.getX());

        // The new perpendicular segment is defined by (newX, newY) and pt
        return new Point("", newX, newY);
    }

    //
    // Return the line passing through the given point which is perpendicular to this segment. 
    //
    public Segment GetPerpendicular(Point pt)
    {
        // If the given point is already on the line.
        if (this.PointLiesOnAndBetweenEndpoints(pt))
        {
            if (this.IsVertical())
            {
                return new Segment(pt, new Point("", pt.getX() + 1, pt.getY()));
            }
            else if (this.IsHorizontal())
            {
                return new Segment(pt, new Point("", pt.getX(), pt.getY() + 1));
            }
            else
            {
                return new Segment(pt, new Point("", pt.getX() + 1, (- 1 / this._slope) + pt.getY()));
            }
        }

        Point projection = ProjectOnto(pt);

        // The new perpendicular segment is defined by the projection and the point
        return new Segment(projection, pt);
    }

    //
    // Given one of the fixed endpoints on this segment, return a congruent segment with the: fixed endpoint and the other point being moved.
    //
    public Segment GetOppositeSegment(Point pt)
    {
        if (!hasPoint(pt)) return null;

        Point fixedPt = pt;
        Point variablePt = this.OtherPoint(pt);

        Point vector = Point.MakeVector(fixedPt, variablePt);
        Point opp = Point.GetOppositeVector(vector);

        // 'Move' the vector to begin at its starting point: pt
        return new Segment(pt, new Point("", pt.getX() + opp.getX(), pt.getY() + opp.getY()));
    }

    //
    // Return the line perpendicular to this segment at the given point. 
    // The point is ON the segment.
    public Segment GetPerpendicularByLength(Point pt, double length)
    {
        Segment perp = this.GetPerpendicular(pt);

        //
        // Find the point which is length distance from the given point.
        //
        // Treat the given perpendicular as a vector; normalize and then multiply.
        //
        Point vector = Point.MakeVector(perp.getPoint1(), perp.getPoint2());
        vector = Point.Normalize(vector);
        vector = Point.ScalarMultiply(vector, length);

        // 'Move' the vector to begin at its starting point: pt
        // Return the perpendicular of proper length.
        return new Segment(pt, new Point("", pt.getX() + vector.getX(), pt.getY() + vector.getY()));
    }

    //
    // Is the given segment a secant THROUGH this circle? (2 intersection points)
    //
    public boolean IsSecant(Circle circle)
    {
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        circle.FindIntersection(this, out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        if (!this.PointLiesOnAndBetweenEndpoints(pt1)) pt1 = null;
        if (!this.PointLiesOnAndBetweenEndpoints(pt2)) pt2 = null;

        return pt1 != null && pt2 != null;
    }

    public boolean Covers(Segment that)
    {
        if (this.HasSubSegment(that)) return true;

        if (this.CoincidingWithOverlap(that)) return true;

        return false;
    }

    public boolean Covers(Arc that)
    {
        return that.HasEndpoint(this._point1) && that.HasEndpoint(this._point2);
    }

    ////
    //// Force this segment into standard position; only 1st and second quadrants allowed.
    ////
    //public Segment Standardize()
    //{
    //    Point vector = Point.MakeVector(this.Point1, this.Point2);

    //    // If this segment is in the 3rd or 4th quadrant, force into the second by taking the opposite.
    //    if (vector.getY() < 0) vector = Point.GetOppositeVector(vector);

    //    return new Segment(origin, vector);
    //}

//    public Segment ConstructSegmentByAngle(Point tail, int angle, int length)
//    {
//        // Make a vector in standard position
//        Point vector = Point.MakeVector(tail, this.OtherPoint(tail));
//
//        // Calculate the angle from standard position.
//        double stdPosAngle = Point.GetDegreeStandardAngleWithCenter(Point.ORIGIN, vector);
//
//        // Get the exact point we want.  ; CTA: Find this function
//        Point rotatedPoint = Figure.GetPointByLengthAndAngleInStandardPosition(length, stdPosAngle - angle);
//
//        return new Segment(tail, rotatedPoint);
//    }

    //
    // Acquire the point that is opposite the given point w.r.t. to this line.
    //
    //              x   given
    //              |
    //   ----------------------------
    //              |
    //              y   <-- opp returned
    //
    public Point GetReflectionPoint(Point pt)
    {
        Point ptOnLine = this.ProjectOnto(pt);

        Segment perp = new Segment(pt, ptOnLine);

        return (perp.GetOppositeSegment(ptOnLine)).OtherPoint(ptOnLine);
    }
    
    //
    // Is this segment proportional to the given segment in terms of the coordinatization from the UI?
    // We should not report proportional if the ratio between segments is 1
    //
    public Pair<Integer, Integer> CoordinateProportional(Segment s)
    {
        return Utilities.RationalRatio(s.length(), this.length());
    }
    
    //
    // Parallel and not Coinciding
    //
    public boolean IsParallelWith(Segment s)
    {
        if (IsCollinearWith(s)) return false;

        if (IsVertical() && s.IsVertical()) return true;

        if (IsHorizontal() && s.IsHorizontal()) return true;

        return backend.utilities.math.Utilities.doubleEquals(s.slope(), this.slope());
    }

    //
    // Parallel and not Coinciding
    //
    public boolean IsPerpendicularTo(Segment thatSegment)
    {
        if (IsVertical() && thatSegment.IsHorizontal()) return true;

        if (IsHorizontal() && thatSegment.IsVertical()) return true;

        return backend.utilities.math.Utilities.doubleEquals(thatSegment.slope() * this.slope(), -1);
    }

    //
    // Is this segment parallel to the given segment in terms of the coordinatization from the UI?
    //
    public boolean CoordinateParallel(Segment s)
    {
        return IsParallelWith(s);
    }

    public static boolean IntersectAtSamePoint(Segment seg1, Segment seg2, Segment seg3)
    {
        Point intersection1 = seg1.FindIntersection(seg3);
        Point intersection2 = seg2.FindIntersection(seg3);

        return intersection1.Equals(intersection2);
    }

    //
    // Is this segment perpendicular to the given segment in terms of the coordinatization from the UI?
    //
    public Point CoordinatePerpendicular(Segment thatSegment)
    {
        //
        // Do these segments intersect within both sets of stated endpoints?
        //
        Point intersection = this.FindIntersection(thatSegment);

        if (!this.PointLiesOnAndBetweenEndpoints(intersection)) return null;
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(intersection)) return null;

        //
        // Special Case
        //
        if ((IsVertical() && thatSegment.IsHorizontal()) || (thatSegment.IsVertical() && IsHorizontal())) return intersection;

        // Does m1 * m2 = -1 (opposite reciprocal slopes)
        return backend.utilities.math.Utilities.doubleEquals(thatSegment.slope() * this.slope(), -1) ? intersection : null;
    }

    //
    // Is thatSegment a bisector of this segment in terms of the coordinatization from the UI?
    //
    public Point CoordinateBisector(Segment thatSegment)
    {
        // Do these segments intersect within both sets of stated endpoints?
        Point intersection = this.FindIntersection(thatSegment);

        if (!this.pointLiesOnAndExactlyBetweenEndpoints(intersection)) return null;
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(intersection)) return null;

        // Do they intersect in the middle of this segment
        return backend.utilities.math.Utilities.doubleEquals(Point.calcDistance(this.getPoint1(), intersection), Point.calcDistance(this.getPoint2(), intersection)) ? intersection : null;
    }
    
    //
    //
    //
    //
    // Maintain a public repository of all segment objects in the figure.
    //
    //
    //
    //
    public static List<Segment> figureSegments;

    static
    {
       figureSegments = new ArrayList<Segment>();        
    }

    public static void clear()
    {
        figureSegments.clear();
    }
    
    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof Segment)) return false;
        Segment segment = (Segment)obj;

        return ((segment._point1.StructurallyEquals(_point1) && segment._point2.StructurallyEquals(_point2)) ||
                (segment._point1.StructurallyEquals(_point2) && segment._point2.StructurallyEquals(_point1)));
    }
    
    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
        if (clause instanceof Segment) figureSegments.add((Segment)clause);
    }
    public static Segment GetFigureSegment(Point pt1, Point pt2)
    {
        Segment candSegment = new Segment(pt1, pt2);

        // Search for exact segment first
        for (Segment segment : figureSegments)
        {
            if (segment.StructurallyEquals(candSegment)) return segment;
        }

        // Otherwise, find a maximal segment.
        for (Segment segment : figureSegments)
        {
            if (segment.HasSubSegment(candSegment)) return segment;
        }

        return null;
    }
}
