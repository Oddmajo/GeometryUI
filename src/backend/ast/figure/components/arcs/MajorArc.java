package backend.ast.figure.components.arcs;

import java.util.ArrayList;
import java.util.List;

import backend.ast.ASTException;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutTriple;

public class MajorArc extends Arc
{
    public MajorArc(Circle circle, Point e1, Point e2)
    {
        this(circle, e1, e2, new ArrayList<Point>(), new ArrayList<Point>());
    }

    public MajorArc(Circle circle, Point e1, Point e2, List<Point> minorPts, List<Point> majorPts)
    {
        super(circle, e1, e2, minorPts, majorPts);
        
        if (circle.DefinesDiameter(new Segment(e1, e2)))
        {
            ExceptionHandler.throwException(new ASTException("Major Arc should not be constructed when a semicircle is appropriate."));
        }
    }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof MajorArc)) return false;
        MajorArc arc = (MajorArc)obj;

        return this._theCircle.structurallyEquals(arc._theCircle) && ((this._endpoint1.structurallyEquals(arc._endpoint1)
                && this._endpoint2.structurallyEquals(arc._endpoint2))
                || (this._endpoint1.structurallyEquals(arc._endpoint2)
                        && this._endpoint2.structurallyEquals(arc._endpoint1)));
    }

    @Override
    public Point Midpoint()
    {
        return _theCircle.OppositePoint(_theCircle.getMidpoint(_endpoint1, _endpoint2));
    }

    @Override
    public boolean CoordinateCongruent(Figure that)
    {
        if (that == null) return false;
        if (!(that instanceof MajorArc)) return false;
        MajorArc thatArc = (MajorArc)that;

        if (!_theCircle.CoordinateCongruent(thatArc._theCircle)) return false;

        return backend.utilities.math.MathUtilities.doubleEquals(this.GetMajorArcMeasureDegrees(), thatArc.GetMajorArcMeasureDegrees());
    }

    private void GetStartEndPoints(double angle1, double angle2, OutTriple<Point, Point, Double> out)
    {
        Point start = null;
        Point end = null;
        double angle = -1;

        if (angle2 - angle1 > 0 && angle2 - angle1 >= Math.toRadians(180))
        {
            start = _endpoint1;
            end = _endpoint2;
            angle = angle1;
        }
        else if (angle1 - angle2 > 0 && angle1 - angle2 >= Math.toRadians(180))
        {
            start = _endpoint2;
            end = _endpoint1;
            angle = angle2;
        }
        else if (angle2 - angle1 > 0 && angle2 - angle1 < Math.toRadians(180))
        {
            start = _endpoint2;
            end = _endpoint1;
            angle = angle2;
        }
        else if (angle1 - angle2 > 0 && angle1 - angle2 < Math.toRadians(180))
        {
            start = _endpoint1;
            end = _endpoint2;
            angle = angle1;
        }

        out.set(start, end, angle);
    }

    @Override
    public ArrayList<Segment> Segmentize()
    {
        if (!approxSegments.isEmpty()) return approxSegments;

        // How much we will change the angle measure as we create segments.
        double angleIncrement = this.GetMajorArcMeasureRadians() / Figure.NUM_SEGS_TO_APPROX_ARC;

        // Find the first point so we sweep : a counter-clockwise manner.
        double angle1 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint1);
        double angle2 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint2);


        OutTriple<Point, Point, Double> out = new OutTriple<Point, Point, Double>();
        GetStartEndPoints(angle1, angle2, out);
        Point firstPoint = out.getFirst();
        Point secondPoint = out.getSecond();
        double angle = out.getThird();
        
        for (int i = 1; i <= Figure.NUM_SEGS_TO_APPROX_ARC; i++)
        {
            // Save this as an approximating point.
            approxPoints.add(firstPoint);

            // Get the next point.
            angle += angleIncrement;
            secondPoint = Point.GetPointFromAngle(_theCircle.getCenter(), _theCircle.getRadius(), angle);

            // Make the segment.
            approxSegments.add(new Segment(firstPoint, secondPoint));

            // Rotate points.
            firstPoint = secondPoint;
        }

        // Save this as an approximating point.
        approxPoints.add(secondPoint);

        return approxSegments;
    }

    @Override
    public boolean pointLiesOn(Point pt)
    {
        return Arc.BetweenMajor(pt, this);
    }

    @Override
    public boolean PointLiesStrictlyOn(Point pt)
    {
        return Arc.StrictlyBetweenMajor(pt, this);
    }

    @Override
    public boolean HasSubArc(Arc that)
    {
        if (!this._theCircle.structurallyEquals(that._theCircle)) return false;

        if (that instanceof MajorArc) return this.HasMajorSubArc(that);
        if (that instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)that;
            return this.HasMinorSubArc(new MinorArc(semi._theCircle, semi._endpoint1, semi._middlePoint)) &&
                   this.HasMinorSubArc(new MinorArc(semi._theCircle, semi._endpoint2, semi._middlePoint));
        }

        return this.HasMinorSubArc(that);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof MajorArc)) return false;
        // MajorArc arc = (MajorArc)obj;

        // Check equality of MajorArc Major / major points?

        return super.equals(obj);
    }

    @Override
    public String toString() { return "MajorArc(" + _theCircle + "(" + _endpoint1.toString() + ", " + _endpoint2.toString() + "))"; }
}