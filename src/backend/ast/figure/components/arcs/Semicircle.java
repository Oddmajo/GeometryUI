package backend.ast.figure.components.arcs;

import java.util.List;

import backend.ast.ASTException;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.atoms.components.Connection;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.translation.OutTriple;

import java.util.ArrayList;

public class Semicircle extends Arc
{
    protected Segment _diameter;
    public Segment getDiameter() { return _diameter; }
    public Point _middlePoint;
    public Point getMiddlePoint() { return _middlePoint; }

    public Semicircle(Circle circle, Point e1, Point e2, Point m, Segment d)
    {
        this(circle, e1, e2, m, new ArrayList<Point>(), new ArrayList<Point>(), d);
    }

    public Semicircle(Circle circle, Point e1, Point e2, Point m, List<Point> minorPts, List<Point> majorPts, Segment d)
    {
        super(circle, e1, e2, minorPts, majorPts);

        _diameter = d;
        _middlePoint = m;

        if (!circle.DefinesDiameter(_diameter))
        {
            ExceptionHandler.throwException(new ASTException("Semicircle constructed without a _diameter"));
        }

        if (!circle.DefinesDiameter(new Segment(e1, e2)))
        {
            ExceptionHandler.throwException(new ASTException("Semicircle constructed without a _diameter"));
        }

        // thisAtomicRegion = new ShapeAtomicRegion(this);
    }

    @Override
    public boolean CoordinateCongruent(Figure that)
    {
        if (that == null) return false;
        if (that instanceof Semicircle) return false;
        Semicircle thatSemi = (Semicircle)that;

        return _theCircle.CoordinateCongruent(thatSemi._theCircle);
    }

    //
    // Area-Related Computations
    //
    protected double Area(double radius)
    {
        return 0.5 * radius * radius * Math.PI;
    }
    protected double RationalArea(double radius)
    {
        return Area(radius) / Math.PI;
    }
//    @Override
//    public boolean IsComputableArea() { return true; }
//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Radius / Circle 
//        return _theCircle.CanAreaBeComputed(known);
//    }
//    @Override
//    public override double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Radius / Circle
//        double circArea = _theCircle.GetArea(known);
//
//        if (circArea <= 0) return -1;
//
//        // The area is a proportion of the circle defined by the angle.
//        return 0.5 * circArea;
//    }

    @Override
    public boolean pointLiesOn(Point pt)
    {
        return Arc.BetweenMinor(pt, new MinorArc(_theCircle, _endpoint1, _middlePoint)) ||
               Arc.BetweenMinor(pt, new MinorArc(_theCircle, _endpoint2, _middlePoint));
    }

    @Override
    public boolean PointLiesStrictlyOn(Point pt)
    {
        if (pt.structurallyEquals(_middlePoint)) return true;

        return Arc.StrictlyBetweenMinor(pt, new MinorArc(_theCircle, _endpoint1, _middlePoint)) ||
                Arc.StrictlyBetweenMinor(pt, new MinorArc(_theCircle, _endpoint2, _middlePoint));
    }

    @Override
    public boolean HasSubArc(Arc that)
    {
        if (!this._theCircle.structurallyEquals(that._theCircle)) return false;

        if (that instanceof MajorArc) return false;
        if (that instanceof Semicircle)
        {
            Semicircle semi = (Semicircle)that;
            return this.HasMinorSubArc(new MinorArc(semi._theCircle, semi._endpoint1, semi._middlePoint)) &&
                   this.HasMinorSubArc(new MinorArc(semi._theCircle, semi._endpoint2, semi._middlePoint));
        }

        return this.HasMinorSubArc(that);
    }

    public boolean SameSideSemicircle(Semicircle thatSemi)
    {
        // First, the endpoints and the _diameter must match
        if (!(this._diameter.structurallyEquals(thatSemi._diameter) && super.structurallyEquals(thatSemi))) return false;

        // if either of the 2 minor arcs formed by this semicircle's middlepoint contain the middlepoint of thatSemi,
        // then the two semicircles form the same 'side' of the circle
        MinorArc m = new MinorArc(this._theCircle, this._endpoint1, this._middlePoint);
        MinorArc m2 = new MinorArc(this._theCircle, this._middlePoint, this._endpoint2);
        if (Arc.BetweenMinor(thatSemi._middlePoint, m) || Arc.BetweenMinor(thatSemi._middlePoint, m2)) return true;
        else return false;
    }

    public boolean AngleIsInscribed(Angle angle)
    {
        if (!this._theCircle.IsInscribed(angle)) return false;

        // Verify that angle points match _diameter endpoints
        Point endpt1, endpt2;

        endpt1 = angle.getRay1().getNonOrigin();
        endpt2 = angle.getRay2().getNonOrigin();
        
        if (!this._diameter.has(endpt1) || !this._diameter.has(endpt2)) return false;

        // Verify that the vertex is within the semicircle
        if (!this.arcMajorPoints.contains(angle.getVertex())) return false;

        return true;
    }

    @Override
    public Point Midpoint()
    {
        Point midpt = _theCircle.getMidpoint(_endpoint1, _endpoint2);

        if (!this.pointLiesOn(midpt)) midpt = _theCircle.OppositePoint(midpt);

        return midpt;
    }

//    /// <summary>
//    /// Make a set of connections for atomic region analysis.
//    /// </summary>
//    /// <returns></returns>
//    @Override
//    public List<Connection> MakeAtomicConnections()
//    {
//        List<Segment> segments = this.Segmentize();
//        List<Connection> connections = new ArrayList<Connection>();
//
//        for (Segment approxSide : segments)
//        {
//            connections.add(new Connection(approxSide.Point1, approxSide.Point2, atoms.components.ARC,
//                    new MinorArc(this._theCircle, approxSide.Point1, approxSide.Point2)));
//        }
//
//        connections.add(new Connection(_diameter.Point1, _diameter.Point2, atoms.components.SEGMENT, this));
//
//        return connections;
//    }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Semicircle)) return false;
        Semicircle semi = (Semicircle)obj;

        return this._diameter.structurallyEquals(semi._diameter) && this._middlePoint.structurallyEquals(semi._middlePoint) && super.structurallyEquals(obj);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Semicircle)) return false;
        Semicircle semi = (Semicircle)obj;

        return this._diameter.equals(semi._diameter) && this._middlePoint.equals(semi._middlePoint) && super.equals(obj);
    }

    @Override
    public String toString()
    {
        return "SemiCircle(" + _theCircle + "(" + _endpoint1.toString() + ", " + _middlePoint.toString() + ", " + _endpoint2.toString() + "), Diameter(" + _diameter + "))";
    }

    @Override
    public String CheapPrettyString()
    {
        return "Semicircle(" + _endpoint1.SimpleToString() + _theCircle.getCenter().SimpleToString() + _endpoint2.SimpleToString() + _middlePoint.SimpleToString() + ")";
    }

    private void GetStartEndPoints(OutTriple<Point, Point, Double> out)
    {
        Point start = null;
        Point end = null;
        double startAngle = -1;

        // Find the first point so we sweep : a counter-clockwise manner.
        double angle1 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint1);
        double angle2 = Point.GetRadianStandardAngleWithCenter(_theCircle.getCenter(), _endpoint2);

        // Define to vectors: vect1: A----->B
        //                    vect2: B----->C
        // Cross product vect1 and vect2. 
        // If the result is positive, the sequence A-->B-->C is Counter-clockwise. 
        // If the result is negative, the sequence A-->B-->C is clockwise.
        Point vect1 = Point.MakeVector(this._middlePoint, this._endpoint1);
        Point vect2 = Point.MakeVector(this._endpoint2, this._middlePoint);

        if (Point.CrossProduct(vect1, vect2) > 0)
        {
            start = _endpoint1;
            end = _endpoint2;
            startAngle = angle1;
        }
        else
        {
            start = _endpoint2;
            end = _endpoint1;
            startAngle = angle2;
        }
        
        out.set(start, end, startAngle);
    }

    @Override
    public ArrayList<Segment> Segmentize()
    {
        if (!approxSegments.isEmpty()) return approxSegments;

        // How much we will change the angle measure as we create segments.
        double angleIncrement = Math.PI / Figure.NUM_SEGS_TO_APPROX_ARC;

        OutTriple<Point, Point, Double> out = new OutTriple<Point, Point, Double>();
        GetStartEndPoints(out);
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
}
