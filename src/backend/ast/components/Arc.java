package ast.figure.components;

import java.util.ArrayList;
import java.util.List;

import ast.ASTException;
import ast.GroundedClause;
import ast.figure.Figure;
import utilities.Pair;
import utilities.translation.OutPair;

public abstract class Arc extends Figure implements Cloneable
{
    public abstract Point Midpoint();

    protected Circle _theCircle;
    protected Circle getCircle() { return _theCircle; }

    protected Point _endpoint1;
    protected Point _endpoint2;
    public Point getEndpoint1() { return _endpoint1; }
    public Point getEndpoint2() { return _endpoint2; }

    protected ArrayList<Point> getArcMinorPoints() { return arcMinorPoints; }
    protected ArrayList<Point> getArcMajorPoints() { return arcMajorPoints; }
    protected ArrayList<Point> getApproxPoints() { return approxPoints; }
    protected ArrayList<Segment> getApproxSegments() { return approxSegments; }

    protected ArrayList<Point> arcMinorPoints;
    public ArrayList<Point> getArcMinorPoint() { return arcMinorPoints; }
    protected ArrayList<Point> arcMajorPoints;
    public ArrayList<Point> getArcMajorPoint() { return arcMajorPoints; }

    protected double minorMeasure;
    public double getMinorMeasure() { return minorMeasure; }
    protected double length;
    public double getLength() { return length; }

    public ArrayList<Point> approxPoints;
    public ArrayList<Segment> approxSegments;

    public Arc(Circle circle, Point e1, Point e2)
    {
        this(circle, e1, e2, new ArrayList<Point>(), new ArrayList<Point>());
    }

    public Arc(Circle circle, Point e1, Point e2, ArrayList<Point> minorPts, ArrayList<Point> majorPts)
    {
        super();

        _theCircle = circle;
        _endpoint1 = e1;
        _endpoint2 = e2;
        arcMinorPoints = new ArrayList<Point>(minorPts);
        arcMajorPoints = new ArrayList<Point>(majorPts);

        utilities.ast_helper.Utilities.addStructurallyUnique(e1.getSuperFigures(), this);
        utilities.ast_helper.Utilities.addStructurallyUnique(e2.getSuperFigures(), this);

        minorMeasure = CalculateArcMinorMeasureDegrees();
        length = CalculateArcMinorLength();
        approxPoints = new ArrayList<Point>();
        approxSegments = new ArrayList<Segment>();

        collinear = new ArrayList<Point>();
        // We add the two points arbitrarily since this list instanceof vacuously ordered.
        collinear.add(_endpoint1);
        collinear.add(_endpoint2);
    }

    //
    // Do these arcs cross each other (pseudo-X)?
    //
    public boolean Crosses(Arc that)
    {
        // Need to be from different circles.
        if (this._theCircle.StructurallyEquals(that._theCircle)) return false;

        // Need to touch at a point not at the endpoints; 
        if (this.HasEndpoint(that._endpoint1) && this.HasEndpoint(that._endpoint2)) return false;

        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(that, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        // CTA: Check the original function for translation; may be issue with 'inters'
        //if (inters.getKey() == null && inter2 == null) return false;

        // Pseudo-X
        if (inter1 != null && inter2 == null)
        {
            return this.PointLiesStrictlyOn(inter1) && that.PointLiesStrictlyOn(inter1);
        }

        // Cursive r; an endpoint may be shared here.
        if (inter1 != null && inter2 != null)
        {
            return true;
        }

        return false;
    }

    public Pair<Segment, Segment> GetRadii()
    {
        return new Pair<Segment, Segment>(new Segment(_theCircle.getCenter(), _endpoint1), new Segment(_theCircle.getCenter(), _endpoint2));
    }
    public Angle GetCentralAngle()
    {
        return new Angle(_endpoint1, _theCircle.getCenter(), _endpoint2);
    }
    @Override
    public ArrayList<Point> GetApproximatingPoints()
    {
        if (approxPoints.isEmpty()) Segmentize();

        return approxPoints;
    }
    public ArrayList<Segment> GetApproximatingSegments()
    {
        if (approxSegments.isEmpty()) return Segmentize();

        return approxSegments;
    }

    public Point OtherEndpoint(Point that)
    {
        if (that == null) return null;

        if (that.StructurallyEquals(_endpoint1)) return _endpoint2;
        if (that.StructurallyEquals(_endpoint2)) return _endpoint1;

        return null;
    }

    public Point SharedEndpoint(Arc that)
    {
        if (this._endpoint1.StructurallyEquals(that._endpoint1)) return _endpoint1;
        if (this._endpoint1.StructurallyEquals(that._endpoint2)) return _endpoint1;

        if (this._endpoint2.StructurallyEquals(that._endpoint1)) return _endpoint2;
        if (this._endpoint2.StructurallyEquals(that._endpoint2)) return _endpoint2;

        return null;
    }

    public abstract boolean PointLiesStrictlyOn(Point pt);
    public abstract boolean HasSubArc(Arc that);

    @Override
    public void AddCollinearPoint(Point newPt)
    {
        if (utilities.list.Utilities.HasStructurally(collinear, newPt)) return;

        collinear.add(newPt);

        collinear = (ArrayList<Point>) _theCircle.OrderPoints(collinear);
    }

    //
    // The goal instanceof the return the set of collinear points such that the endpoints bookend the list.
    //
    public ArrayList<Point> GetOrderedCollinearPointsByEndpoints(List<Point> given) throws ASTException
    {
        // Find only the points on this arc.
        ArrayList<Point> applicable = new ArrayList<Point>();

        for (Point pt : given)
        {
            if (this.PointLiesOn(pt)) applicable.add(pt);
        }

        ArrayList<Point> ordered = _theCircle.OrderPoints(applicable);

        int index1 = ordered.indexOf(_endpoint1);
        int index2 = ordered.indexOf(_endpoint2);

        if ((index1 == 0 && index2 == ordered.size()-1) || (index2 == 0 && index1 == ordered.size()-1)) return ordered;

        if (index1 + 1 != index2 && index2 + 1 != index1) throw new ASTException("Logic failure to order points...");

        ArrayList<Point> bookend = new ArrayList<Point>();
        int start = -1;
        int end = -1;

        if (index1 > index2)
        {
            start = index1;
            end = index2;
        }
        else if (index2 > index1)
        {
            start = index2;
            end = index1;
        }
        else throw new ASTException("Logic failure to order points...");

        for (int i = start; i != end; )
        {
            bookend.add(ordered.get(i));
            if (i + 1 == ordered.size()) i = 0;
            else i++;
        }
        bookend.add(ordered.get(end));

        return bookend;
    }

    public ArrayList<Point> GetOrderedByEndpointsWithMidpoints(List<Point> given) throws ASTException
    {
        ArrayList<Point> givenWithMidpoints = _theCircle.ConstructAllMidpoints(given);

        return GetOrderedCollinearPointsByEndpoints(givenWithMidpoints);
    }

    @Override
    public void ClearCollinear()
    {
        collinear.clear();
        collinear.add(_endpoint1);
        collinear.add(_endpoint2);
    }

    //
    // Calculate the length of the arc: s = r * theta (radius * central angle)
    //
    private double CalculateArcMinorLength() { return GetMinorArcMeasureRadians() * _theCircle.getRadius(); }

    //
    // The measure of the minor arc instanceof equal to the measure of the central angle it cuts out.
    // This instanceof calculated : degrees.
    //
    private double CalculateArcMinorMeasureDegrees()
    {
        return new Angle(new Segment(_theCircle.getCenter(), _endpoint1), new Segment(_theCircle.getCenter(), _endpoint2)).measure;
    }
    private double CalculateArcMinorMeasureRadians()
    {
        return Angle.toRadians(new Angle(new Segment(_theCircle.getCenter(), _endpoint1), new Segment(_theCircle.getCenter(), _endpoint2)).measure);
    }

    public double GetMinorArcMeasureDegrees() { return minorMeasure; }
    public double GetMinorArcMeasureRadians() { return Angle.toRadians(GetMinorArcMeasureDegrees()); }
    public double GetMajorArcMeasureDegrees() { return 360 - minorMeasure; }
    public double GetMajorArcMeasureRadians() { return Angle.toRadians(GetMajorArcMeasureDegrees()); }

    //
    // Maintain a public repository of all segment objects : the figure.
    //
    public static void Clear()
    {
        figureMinorArcs.clear();
        figureMajorArcs.clear();
        figureSemicircles.clear();
    }
    public static ArrayList<MinorArc> figureMinorArcs = new ArrayList<MinorArc>();
    public static ArrayList<MajorArc> figureMajorArcs = new ArrayList<MajorArc>();
    public static ArrayList<Semicircle> figureSemicircles = new ArrayList<Semicircle>();
    public static void Record(GroundedClause clause)
    {
        if (clause instanceof MinorArc) figureMinorArcs.add((MinorArc)clause);
        if (clause instanceof MajorArc) figureMajorArcs.add((MajorArc)clause);
        if (clause instanceof Semicircle) figureSemicircles.add((Semicircle)clause);
    }
    public static Arc GetFigureMinorArc(Circle circle, Point pt1, Point pt2)
    {
        MinorArc candArc = new MinorArc(circle, pt1, pt2);

        // Search for exact segment first
        for (MinorArc arc : figureMinorArcs)
        {
            if (arc.StructurallyEquals(candArc)) return arc;
        }

        return null;
    }
    public static Arc GetFigureMajorArc(Circle circle, Point pt1, Point pt2) throws ASTException
    {
        MajorArc candArc = new MajorArc(circle, pt1, pt2);

        // Search for exact segment first
        for (MajorArc arc : figureMajorArcs)
        {
            if (arc.StructurallyEquals(candArc)) return arc;
        }

        return null;
    }
    public static Arc GetFigureSemicircle(Circle circle, Point pt1, Point pt2, Point middle)
    {
        Segment diameter = new Segment(pt1, pt2);
        Semicircle candArc = null;
        try
        {
            candArc = new Semicircle(circle, pt1, pt2, middle, diameter);
        }
        catch (ASTException e)
        {
            return null;
        }

        for (Semicircle arc : figureSemicircles)
        {
            if (arc.StructurallyEquals(candArc)) return arc;
        }

        return null;
    }
    private static Arc GetInscribedInterceptedArc(Circle circle, Angle angle) throws ASTException
    {
        Point endpt1, endpt2;


        OutPair<Point, Point> out = new OutPair<Point, Point>();
        circle.FindIntersection(angle.ray1, out);
        Point pt1 = out.first();
        Point pt2 = out.second();

        endpt1 = pt1.StructurallyEquals(angle.GetVertex()) ? pt2 : pt1;

        circle.FindIntersection(angle.ray2, out);
        pt1 = out.first();
        pt2 = out.second();

        endpt2 = pt1.StructurallyEquals(angle.GetVertex()) ? pt2 : pt1;

        // Need to check if the angle instanceof a diameter and create a semicircle
        Segment chord = new Segment(endpt1, endpt2);
        if (circle.DefinesDiameter(chord))
        {
            Point opp = circle.Midpoint(endpt1, endpt2, angle.GetVertex());
            Semicircle semi = new Semicircle(circle, endpt1, endpt2, circle.OppositePoint(opp), chord);
            //Find a defined semicircle of the figure that lies on the same side
            Semicircle sameSideSemi = null;
            for (Semicircle s : figureSemicircles)
            {
                if (semi.SameSideSemicircle(s))
                {
                    sameSideSemi = s;
                    break;
                }
            }
            //If none were found, should we throw an exception or just return the original semi?
            if (sameSideSemi == null) return semi;
            else return sameSideSemi;
        }

        //Initially assume intercepted arc instanceof the minor arc
        Arc intercepted = null;
        intercepted = new MinorArc(circle, endpt1, endpt2);
        //Verify assumption, create major arc if necessary
        if (Arc.BetweenMinor(angle.GetVertex(), intercepted)) intercepted = new MajorArc(circle, endpt1, endpt2);
        return intercepted;
    }

    //
    // Returns the single (closest) intercepted arc for an angle.
    //
    public static Arc GetInterceptedArc(Circle circle, Angle angle) throws ASTException
    {
        if (circle.IsInscribed(angle)) return GetInscribedInterceptedArc(circle, angle);

        Pair<Arc, Arc> intercepted = Arc.GetInterceptedArcs(circle, angle);

        return intercepted.getKey();
    }


    //
    // Acquires one or two intercepted arcs from an exterior or interior angle vertex.
    //
    public static Pair<Arc, Arc> GetInterceptedArcs(Circle circle, Angle angle)
    {
        Pair<Arc, Arc> nullPair = new Pair<Arc, Arc>(null, null);

        //
        // Get the intersection points of the rays of the angle.
        //
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        circle.FindIntersection(angle.ray1, out);
        Point interRay11 = out.first();
        Point interRay12 = out.second();

        if (!angle.ray1.PointLiesOnAndBetweenEndpoints(interRay11)) interRay11 = null;
        if (!angle.ray1.PointLiesOnAndBetweenEndpoints(interRay12)) interRay12 = null;
        if (interRay11 == null && interRay12 != null) interRay11 = interRay12;

        // non-intersection
        if (interRay11 == null && interRay12 == null) return nullPair;

        circle.FindIntersection(angle.ray2, out);
        Point interRay21 = out.first();
        Point interRay22 = out.second();

        if (!angle.ray2.PointLiesOnAndBetweenEndpoints(interRay21)) interRay21 = null;
        if (!angle.ray2.PointLiesOnAndBetweenEndpoints(interRay22)) interRay22 = null;
        if (interRay21 == null && interRay22 != null) interRay21 = interRay22;

        // non-intersection
        if (interRay21 == null && interRay22 == null) return nullPair;

        //
        // Split the rays into cases based on if they are secants or not.
        //
        boolean isSecRay1 = angle.ray1.IsSecant(circle);
        boolean isSecRay2 = angle.ray2.IsSecant(circle);

        //
        // One Arc: No secants
        //
        if (!isSecRay1 && !isSecRay2)
        {
            // This means the endpoints of the ray were on the circle directly for each.
            return new Pair<Arc,Arc>(Arc.GetFigureMinorArc(circle, interRay11, interRay21), null);
        }
        //
        // One Arc; with one secant and one not.
        //
        else if (!isSecRay1 || !isSecRay2)
        {
            Segment secant = null;
            Segment nonSecant = null;
            Point endPtNonSecant = null;

            if (isSecRay1)
            {
                secant = angle.ray1;
                nonSecant = angle.ray2;
                endPtNonSecant = interRay21;
            }
            else
            {
                secant = angle.ray2;
                nonSecant = angle.ray1;
                endPtNonSecant = interRay11;
            }

            Segment chordOfSecant = circle.ContainsChord(secant);

            Point endptSecant = Segment.Between(chordOfSecant.getPoint1(), angle.GetVertex(), chordOfSecant.getPoint2()) ?
                    chordOfSecant.getPoint1() : chordOfSecant.getPoint2();

                    return new Pair<Arc,Arc>(Arc.GetFigureMinorArc(circle, endPtNonSecant, endptSecant), null);
        }

        //
        // Two arcs
        //
        else
        {
            //
            // Ensure proper ordering of points
            //
            Point closeRay1, farRay1;
            Point closeRay2, farRay2;

            if (Segment.Between(interRay11, angle.GetVertex(), interRay12))
            {
                closeRay1 = interRay11;
                farRay1 = interRay12;
            }
            else
            {
                closeRay1 = interRay12;
                farRay1 = interRay11;
            }

            if (Segment.Between(interRay21, angle.GetVertex(), interRay22))
            {
                closeRay2 = interRay21;
                farRay2 = interRay22;
            }
            else
            {
                closeRay2 = interRay22;
                farRay2 = interRay21;
            }

            return new Pair<Arc, Arc>(Arc.GetFigureMinorArc(circle, closeRay1, closeRay2),
                    Arc.GetFigureMinorArc(circle, farRay1, farRay2));
        }
    }

    // Checking for structural equality (instanceof it the same segment) excluding the multiplier
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Arc)) return false;
        Arc arc = (Arc)obj;

        return this._theCircle.StructurallyEquals(arc._theCircle) && ((this._endpoint1.StructurallyEquals(arc._endpoint1)
                && this._endpoint2.StructurallyEquals(arc._endpoint2))
                || (this._endpoint1.StructurallyEquals(arc._endpoint2)
                        && this._endpoint2.StructurallyEquals(arc._endpoint1)));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Arc)) return false;
        Arc arc = (Arc)obj;

        // Check equality of arc minor / major points?

        // Verify that the arcs match
        boolean arcsMatch = this._theCircle.Equals(arc._theCircle) && ((this._endpoint1.Equals(arc._endpoint1)
                && this._endpoint2.Equals(arc._endpoint2))
                || (this._endpoint1.Equals(arc._endpoint2)
                        && this._endpoint2.Equals(arc._endpoint1)));

        // Verify that the multipliers match
        return arcsMatch && super.equals(obj);
    }

    @Override
    public boolean ContainsClause(GroundedClause target)
    {
        return this.Equals(target);
    }

    @Override
    public String toString() { return "Arc(" + _theCircle + "(" + _endpoint1.toString() + ", " + _endpoint2.toString() + "))"; }

    // Does this arc contain a sub-arc:
    // A-------B-------C------D
    // A subarc instanceof: AB, AC, AD, BC, BD, CD
    public boolean HasMinorSubArc(Arc arc)
    {
        return Arc.BetweenMinor(arc._endpoint1, this) && Arc.BetweenMinor(arc._endpoint2, this);
    }

    public boolean HasStrictMinorSubArc(Arc arc)
    {
        return Arc.StrictlyBetweenMinor(arc._endpoint1, this) && Arc.StrictlyBetweenMinor(arc._endpoint2, this);
    }

    public boolean HasMajorSubArc(Arc arc)
    {
        return Arc.BetweenMajor(arc._endpoint1, this) && Arc.BetweenMajor(arc._endpoint2, this);
    }

    public boolean HasStrictMajorSubArc(Arc arc)
    {
        return Arc.StrictlyBetweenMajor(arc._endpoint1, this) && Arc.StrictlyBetweenMajor(arc._endpoint2, this);
    }

    //
    // Is M between A and B : the minor arc
    //
    public static boolean BetweenMinor(Point m, Arc originalArc)
    {
        if (m == null) return false;

        // Is the point on this circle?
        if (!originalArc._theCircle.PointLiesOn(m)) return false;

        // Create two arcs from this new point to the endpoints; just like with segments,
        // the sum of the arc measures must equate to the overall arc measure.
        MinorArc arc1 = new MinorArc(originalArc._theCircle, m, originalArc._endpoint1);
        MinorArc arc2 = new MinorArc(originalArc._theCircle, m, originalArc._endpoint2);

        return utilities.math.Utilities.doubleEquals(arc1.minorMeasure + arc2.minorMeasure, originalArc.minorMeasure);
    }

    public static boolean StrictlyBetweenMinor(Point m, Arc originalArc)
    {
        if (m == null) return false;

        if (originalArc.HasEndpoint(m)) return false;

        return BetweenMinor(m, originalArc);
    }

    //
    // If it's on the circle and not : the minor arc, it's : the major arc.
    //
    public static boolean BetweenMajor(Point m, Arc originalArc)
    {
        if (originalArc.HasEndpoint(m)) return true;

        if (m == null) return false;

        // Is the point on this circle?
        if (!originalArc._theCircle.PointLiesOn(m)) return false;

        // Is it on the arc minor?
        if (BetweenMinor(m, originalArc)) return false;

        return true;
    }

    public static boolean StrictlyBetweenMajor(Point m, Arc originalArc)
    {
        if (m == null) return false;

        if (originalArc.HasEndpoint(m)) return false;

        return BetweenMajor(m, originalArc);
    }

    public boolean HasEndpoint(Point p)
    {
        return _endpoint1.Equals(p) || _endpoint2.Equals(p);
    }

    //    // Make a deep copy of this object
    //    @Override
    //    public GroundedClause DeepCopy()
    //    {
    //        Arc other = (Arc)(this.MemberwiseClone());
    //        other._endpoint1 = (Point)_endpoint1.DeepCopy();
    //        other._endpoint2 = (Point)_endpoint2.DeepCopy();
    //
    //        return other;
    //    }

    //
    // Is this arc congruent to the given arc : terms of the coordinatization from the UI?
    //
    public boolean CoordinateCongruent(Arc a) { return utilities.math.Utilities.doubleEquals(this.length, a.length); }

    //
    // Is this segment proportional to the given segment : terms of the coordinatization from the UI?
    // We should not report proportional if the ratio between segments instanceof 1
    //
    public Pair<Integer, Integer> CoordinateProportional(Arc a) { return utilities.math.Utilities.RationalRatio(this.length, a.length); }

    //
    // Concentric
    //
    public boolean IsConcentricWith(Arc thatArc) { return this._theCircle.AreConcentric(thatArc._theCircle); }
    //
    // Orthogonal
    //
    //
    // Orthogonal arcs intersect at 90^0: radii connecting to intersection point are perpendicular.
    //
    public boolean AreOrthognal(Arc thatArc)
    {
        if (!this._theCircle.AreOrthognal(thatArc._theCircle)) return false;

        // Find the intersection points
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this._theCircle.FindIntersection(thatArc._theCircle, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        // Is the intersection between the endpoints of both arcs? Check both.
        if (Arc.BetweenMinor(inter1, this) && Arc.BetweenMinor(inter1, thatArc)) return true;
        if (Arc.BetweenMinor(inter2, this) && Arc.BetweenMinor(inter2, thatArc)) return true;

        return false;
    }

    //
    // Tangent circle have 1 intersection point
    //
    public Point AreTangent(Arc thatArc)
    {
        Point intersection = this._theCircle.AreTangent(thatArc._theCircle);

        // Is the intersection between the endpoints of both arcs? Check both.
        if (Arc.BetweenMinor(intersection, this) && Arc.BetweenMinor(intersection, thatArc)) return intersection;
        if (Arc.BetweenMinor(intersection, this) && Arc.BetweenMinor(intersection, thatArc)) return intersection;

        return null;
    }

    public void GetRadii(OutPair<Segment, Segment> out)
    {
        Segment radius1 = this._theCircle.GetRadius(new Segment(this._theCircle.getCenter(), this._endpoint1));
        Segment radius2 = this._theCircle.GetRadius(new Segment(this._theCircle.getCenter(), this._endpoint2));

        if (radius1 == null && radius2 != null)
        {
            radius1 = radius2;
            radius2 = null;
        }

        out.set(radius1, radius2);
    }

    @Override
    public void FindIntersection(Arc that, OutPair<Point, Point> out)
    {
        // Find the points of intersection
        OutPair<Point, Point> localOut = new OutPair<Point, Point>();
        this._theCircle.FindIntersection(that._theCircle, localOut);
        Point inter1 = localOut.first();
        Point inter2 = localOut.second();


        // The points must be on this minor arc.
        if (this instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, this)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, this)) inter2 = null;
        }
        else
        {
            if (!Arc.BetweenMajor(inter1, this)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, this)) inter2 = null;
        }

        // The points must be on thatArc
        if (that instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, that)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, that)) inter2 = null;
        }
        else
        {
            if (!Arc.BetweenMajor(inter1, that)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, that)) inter2 = null;
        }

        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        out.set(inter1, inter2);
    }

    @Override
    public void FindIntersection(Segment that, OutPair<Point, Point> out) // out Point inter1, out Point inter2)
    {
        // Find the points of intersection
        OutPair<Point, Point> localOut = new OutPair<Point, Point>();
        this._theCircle.FindIntersection(that, localOut);
        Point inter1 = localOut.first();
        Point inter2 = localOut.second();

        // The points must be on this minor arc.
        if (this instanceof MinorArc)
        {
            if (!Arc.BetweenMinor(inter1, this)) inter1 = null;
            if (!Arc.BetweenMinor(inter2, this)) inter2 = null;
        }
        else if (this instanceof MajorArc)
        {
            if (!Arc.BetweenMajor(inter1, this)) inter1 = null;
            if (!Arc.BetweenMajor(inter2, this)) inter2 = null;
        }
        else if (this instanceof Semicircle)
        {
            if (!((Semicircle)this).PointLiesOn(inter1)) inter1 = null;
            if (!((Semicircle)this).PointLiesOn(inter2)) inter2 = null;
        }

        if (!that.PointLiesOnAndBetweenEndpoints(inter1)) inter1 = null;
        if (!that.PointLiesOnAndBetweenEndpoints(inter2)) inter2 = null;

        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        out.set(inter1, inter2);
    }

    public boolean Covers(Segment that)
    {
        return (this.HasEndpoint(that.getPoint1()) && this.HasEndpoint(that.getPoint2()));
    }

    public boolean Covers(Arc that)
    {
        return this.PointLiesStrictlyOn(that._endpoint1) || this.PointLiesStrictlyOn(that._endpoint1);
    }
}