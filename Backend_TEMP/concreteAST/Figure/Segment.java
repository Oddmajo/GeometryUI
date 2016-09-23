package concreteAST.Figure;

import java.util.ArrayList;
import utilities.Utilities;

public class Segment extends Figure
{
    public Point Point1; //public get private set
    public Point Point2; //public get private set
    public double Length; //public get private set
    public double Slope; //public get private set 
    
    public boolean DefinesCollinearity() { return collinear.size() > 2; }

    public Segment(Point p1, Point p2)
    {
        Point1 = p1;
        Point2 = p2;
        Length = Point.calcDistance(p1, p2);
        Slope = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());

        Utilities.AddUniqueStructurally(Point1.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point2.getSuperFigures(), this);


        collinear = new ArrayList<Point>();
        // We add the two points arbitrarily since this list is vacuously ordered.
        collinear.add(p1);
        collinear.add(p2);
    }

    public boolean Crosses(Segment that)
    {
        Point p = this.FindIntersection(that);

        if (p == null) return false;

        return this.PointLiesOnAndExactlyBetweenEndpoints(p) && that.PointLiesOnAndExactlyBetweenEndpoints(p);
    }

    public boolean PointLiesOnAndExactlyBetweenEndpoints(Point thatPoint)
    {
        if (thatPoint == null) return false;

        if (this.HasPoint(thatPoint)) return false;

        return Segment.Between(thatPoint, Point1, Point2);
    }

    public boolean HasPoint(Point p)
    {
        return Point1.equals(p) || Point2.equals(p);
    }

    public Point FindIntersection(Segment thatSegment)
    {
     // Special Case: Collinear, but non-overlapping.
        if (this.CoincidingWithoutOverlap(thatSegment)) return null;

        // Special Case: Intersect at an endpoint
        Point shared = this.SharedVertex(thatSegment);
        if (shared != null) return shared;

        double a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;

        if (this.IsVertical() && thatSegment.IsHorizontal()) return new Point(null, this.Point1.getX(), thatSegment.Point1.getY());

        if (thatSegment.IsVertical() && this.IsHorizontal()) return new Point(null, thatSegment.Point1.getX(), this.Point1.getY());

        if (this.IsVertical())
        {
            MakeLine(thatSegment.Point1.getX(), thatSegment.Point1.getY(), thatSegment.Point2.getX(), thatSegment.Point2.getY(), a, b, e);
            return new Point(null, this.Point1.getX(), EvaluateYGivenX(a, b, e, this.Point1.getX()));
        }
        if (thatSegment.IsVertical())
        {
            MakeLine(this.Point1.getX(), this.Point1.getY(), this.Point2.getX(), this.Point2.getY(), a, b, e);
            return new Point(null, thatSegment.Point1.getX(), EvaluateYGivenX(a, b, e, thatSegment.Point1.getX()));
        }
        if (this.IsHorizontal())
        {
            MakeLine(thatSegment.Point1.getX(), thatSegment.Point1.getY(), thatSegment.Point2.getX(), thatSegment.Point2.getY(), a, b, e);
            return new Point(null, EvaluateXGivenY(a, b, e, this.Point1.getY()), this.Point1.getY());
        }
        if (thatSegment.IsHorizontal())
        {
            MakeLine(this.Point1.getX(), this.Point1.getY(), this.Point2.getX(), this.Point2.getY(), a, b, e);
            return new Point(null, EvaluateXGivenY(a, b, e, thatSegment.Point1.getY()), thatSegment.Point1.getY());
        }

        //
        // ax + by = e
        // cx + dy = f
        // 

        MakeLine(Point1.getX(), Point1.getY(), Point2.getX(), Point2.getY(), a, b, e);
        MakeLine(thatSegment.Point1.getX(), thatSegment.Point1.getY(), thatSegment.Point2.getX(), thatSegment.Point2.getY(), c, d, f);

        double overallDeterminant = a * d - b * c;
        double x = determinant(e, b, f, d) / overallDeterminant;
        double y = determinant(a, e, c, f) / overallDeterminant;

        return new Point("Intersection", x, y);
    }

    private double determinant(double a, double b, double c, double d)
    {
        return a * d - b * c;
    }

    private void MakeLine(double x_1, double y_1, double x_2, double y_2, double a, double b, double c)
    {
        double slope = (y_2 - y_1) / (x_2 - x_1);
        a = - slope;
        b = 1;
        c = y_2 - slope * x_2;
    }

    private double EvaluateXGivenY(double a, double b, double e, double y)
    {
     // ax + by = e
        return (e - b * y) / a;
    }
    
    private double EvaluateYGivenX(double a, double b, double e, double x)
    {
     // ax + by = e
        return (e - a * x) / b;
    }

    private boolean IsHorizontal()
    {
        return Utilities.CompareValues(this.Point1.getY(), this.Point2.getY());
    }

    private boolean IsVertical()
    {
        return Utilities.CompareValues(this.Point1.getX(), this.Point2.getX());
    }

    public boolean IsCollinearWith(Segment otherSegment)
    {
     // If the segments are vertical, just compare the X values of one point of each
        if (this.IsVertical() && otherSegment.IsVertical())
        {
            return Utilities.CompareValues(this.Point1.getX(), otherSegment.Point1.getX());
        }

        // If the segments are horizontal, just compare the Y values of one point of each; this is redundant
        if (this.IsHorizontal() && otherSegment.IsHorizontal())
        {
            return Utilities.CompareValues(this.Point1.getY(), otherSegment.Point1.getY());
        }

        return Utilities.CompareValues(this.Slope, otherSegment.Slope) &&
               this.PointLiesOn(otherSegment.Point1) && this.PointLiesOn(otherSegment.Point2); // Check both endpoints just to be sure
    }

    public Point SharedVertex(Segment s)
    {
        if (Point1.equals(s.Point1)) return Point1;
        if (Point1.equals(s.Point2)) return Point1;
        if (Point2.equals(s.Point1)) return Point2;
        if (Point2.equals(s.Point2)) return Point2;
        return null;
    }

    public boolean CoincidingWithoutOverlap(Segment thatSegment)
    {
        if (!IsCollinearWith(thatSegment)) return false;

        if (this.PointLiesOnAndBetweenEndpoints(thatSegment.Point1)) return false;

        if (this.PointLiesOnAndBetweenEndpoints(thatSegment.Point2)) return false;

        return true;
    }

    //
    // Use point-slope form to determine if the given point is on the line
    //
    public boolean PointLiesOnAndBetweenEndpoints(Point thatPoint)
    {
        if (thatPoint == null) return false;

        return Segment.Between(thatPoint, Point1, Point2);
    }

    public static boolean Between(Point M, Point A, Point B)
    {
        return Utilities.CompareValues(Point.calcDistance(A, M) + Point.calcDistance(M, B),
                Point.calcDistance(A, B));
    }

    public Point OtherPoint(Point p)
    {
        if (p.equals(Point1)) return Point2;
        if (p.equals(Point2)) return Point1;

        return null;
    }

    @Override
    public String toString() { return "Segment(" + Point1.toString() + ", " + Point2.toString() + ")"; }


}
