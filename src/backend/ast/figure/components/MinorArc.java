package backend.ast.figure.components;

import java.util.List;

import backend.ast.figure.Figure;
import backend.utilities.translation.OutTriple;

import java.util.ArrayList;

public class MinorArc extends Arc
{
    public MinorArc(Circle circle, Point e1, Point e2)
    {
        this(circle, e1, e2, new ArrayList<Point>(), new ArrayList<Point>());
    }

    public MinorArc(Circle circle, Point e1, Point e2, List<Point> minorPts, List<Point> majorPts)
    {
        super(circle, e1, e2, minorPts, majorPts);
    }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof MinorArc) return false;
        MinorArc arc = (MinorArc)obj;

        return this._theCircle.StructurallyEquals(arc._theCircle) && ((this._endpoint1.StructurallyEquals(arc._endpoint1)
                && this._endpoint2.StructurallyEquals(arc._endpoint2))
                || (this._endpoint1.StructurallyEquals(arc._endpoint2)
                        && this._endpoint2.StructurallyEquals(arc._endpoint1)));
    }

    @Override
    public Point Midpoint() { return _theCircle.Midpoint(_endpoint1, _endpoint2); }

    @Override
    public boolean CoordinateCongruent(Figure that)
    {
        if (that == null) return false;
        if (!(that instanceof MinorArc)) return false;
        MinorArc thatArc = (MinorArc)that;

        if (!_theCircle.CoordinateCongruent(thatArc._theCircle)) return false;

        return backend.utilities.math.Utilities.doubleEquals(this.GetMinorArcMeasureDegrees(), thatArc.GetMinorArcMeasureDegrees());
    }

    private void GetStartEndPoints(double angle1, double angle2, OutTriple<Point, Point, Double> out)
    {
        Point start = null;
        Point end = null;
        double angle = -1;

        if (angle2 - angle1 > 0 && angle2 - angle1 < Angle.toRadians(180))
        {
            start = _endpoint1;
            end = _endpoint2;
            angle = angle1;
        }
        else if (angle1 - angle2 > 0 && angle1 - angle2 < Angle.toRadians(180))
        {
            start = _endpoint2;
            end = _endpoint1;
            angle = angle2;
        }
        else if (angle2 - angle1 > 0 && angle2 - angle1 >= Angle.toRadians(180))
        {
            start = _endpoint2;
            end = _endpoint1;
            angle = angle2;
        }
        else if (angle1 - angle2 > 0 && angle1 - angle2 >= Angle.toRadians(180))
        {
            start = _endpoint1;
            end = _endpoint2;
            angle = angle1;
        }
        
        out.set(start, end, angle);
    }

    @Override
    public List<Segment> Segmentize()
    {
        if (!approxSegments.isEmpty()) return approxSegments;

        // How much we will change the angle measure as we create segments.
        double angleIncrement = Angle.toRadians(this.minorMeasure / Figure.NUM_SEGS_TO_APPROX_ARC);

        // Find the first point so we sweep : a counter-clockwise manner.
        double angle1 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint1);
        double angle2 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint2);

        OutTriple<Point, Point, Double> out = new OutTriple<Point, Point, Double>();
        GetStartEndPoints(angle1, angle2, out);
        Point firstPoint = null;
        Point secondPoint = null;
        double angle = -1;
        
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
    public boolean PointLiesOn(Point pt)
    {
        return Arc.BetweenMinor(pt, this);
    }

    @Override
    public boolean PointLiesStrictlyOn(Point pt)
    {
        return Arc.StrictlyBetweenMinor(pt, this);
    }

    @Override
    public boolean HasSubArc(Arc that)
    {
        if (!this._theCircle.StructurallyEquals(that._theCircle)) return false;

        if (that instanceof MajorArc || that instanceof Semicircle) return false;

        return this.HasMinorSubArc(that);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof MinorArc) return false;
        MinorArc arc = (MinorArc)obj;

        // Check equality of arc minor / major points?

        return super.equals(obj);
    }

    @Override
    public String toString() { return "MinorArc(" + _theCircle + "(" + _endpoint1.toString() + ", " + _endpoint2.toString() + "))"; }
}