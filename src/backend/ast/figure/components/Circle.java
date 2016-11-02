package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.utilities.Pair;
import backend.utilities.translation.OutPair;
import backend.utilities.translation.OutSingle;

// <summary>
// A circle is defined by a _center and _radius.
// </summary>
public class Circle extends Figure
{
    public static ArrayList<Circle> getFigureCircles()
    {
        return figureCircles;
    }
    
    protected Point _center;
    public Point getCenter() { return _center; }

    protected double _radius;
    public double getRadius() {  return _radius; }

    // We define a secant to be a segment that passes through a circle (and contains a chord)
    // < Original secant segment, chord >
    protected Hashtable<Segment, Segment> _secants;
    public Dictionary<Segment, Segment> get_secants() { return _secants; }

    // We define a chord to strictly be a segment that has BOTH endpoints on the circle (and does not extend).
    protected ArrayList<Segment> chords;
    public ArrayList<Segment> getChords() { return chords; }

    // Any radii defined by the figure.
    protected ArrayList<Segment> radii;
    public ArrayList<Segment> getRadii() { return radii; }

    // A diameter is a special chord that passes through the _center of the circle.
    protected ArrayList<Segment> diameters;
    public ArrayList<Segment> getDiameters() { return diameters;}

    // Tangents intersect the circle at one point; the pair is <tangent, _radius> where _radius creates the 90^o angle.
    protected Hashtable<Segment, Segment> tangents;
    public Dictionary<Segment, Segment> getTangents() { return tangents;}

    // Polygons that are circumscribed about the circle. 
    protected ArrayList<ArrayList<Polygon>> circumPolys;
    public ArrayList<ArrayList<Polygon>> getCircumPolys() { return circumPolys; }
    
    // Polygons that are inscribed : the circle. 
    protected ArrayList<ArrayList<Polygon>> inscribedPolys;
    public ArrayList<ArrayList<Polygon>> getInscribedPolys() { return inscribedPolys; }
    
    // The list of points from the UI which involve this circle.
    public ArrayList<Point> pointsOnCircle;
    public ArrayList<Point> getPointsOnCircle() { return pointsOnCircle; }

    // The minor Arcs of this circle (based on pointsOnCircle list)
    protected ArrayList<MinorArc> minorArcs;
    protected ArrayList<MajorArc> majorArcs;
    public ArrayList<MinorArc> getMinorArcs() { return minorArcs; }
    public ArrayList<MajorArc> getMajorArcs() { return majorArcs; }

    // The sectors of this circle (based on pointsOnCircle list)
    protected ArrayList<Sector> minorSectors;
    public ArrayList<Sector> getMinorSectors() { return minorSectors; }
    protected ArrayList<Sector> majorSectors;
    public ArrayList<Sector> getMajorSectors() { return majorSectors; }

    // Points that approximate the circle using straight-line segments.
    protected ArrayList<Point> approxPoints;
    public ArrayList<Point> getApproxPoints() { return approxPoints; }
    protected ArrayList<Segment> approxSegments;
    public ArrayList<Segment> getApproxSegments() { return approxSegments; }




    /// <summary>
    /// Create a new ConcreteSegment. 
    /// </summary>
    /// <param name="p1">A point defining the segment.</param>
    /// <param name="p2">Another point defining the segment.</param>
    public Circle(Point _center, double r)
    {
        super();
        
        this._center = _center;
        _radius = r;

        _secants = new Hashtable<Segment, Segment>();
        chords = new ArrayList<Segment>();
        radii = new ArrayList<Segment>();
        diameters = new ArrayList<Segment>();
        tangents = new Hashtable<Segment, Segment>();

        inscribedPolys = new ArrayList<ArrayList<Polygon>>(Polygon.MAX_EXC_POLY_INDEX);
        circumPolys = new ArrayList<ArrayList<Polygon>>(Polygon.MAX_EXC_POLY_INDEX);
        for (int n = Polygon.MIN_POLY_INDEX; n < Polygon.MAX_EXC_POLY_INDEX; n++)
        {
            inscribedPolys.set(n, new ArrayList<Polygon>());
            circumPolys.set(n, new ArrayList<Polygon>());
        }

        pointsOnCircle = new ArrayList<Point>();

        minorArcs = new ArrayList<MinorArc>();
        majorArcs = new ArrayList<MajorArc>();
        minorSectors = new ArrayList<Sector>();
        majorSectors = new ArrayList<Sector>();

        approxPoints = new ArrayList<Point>();
        approxSegments = new ArrayList<Segment>();

        backend.utilities.list.Utilities.addUniqueStructurally(this._center.getSuperFigures(), this);

        //thisAtomicRegion = new ShapeAtomicRegion(this);

        //this.FigureSynthesizerConstructor();
    }

    public void AddMinorArc(MinorArc mArc) { minorArcs.add(mArc); }
    public void AddMajorArc(MajorArc mArc) { majorArcs.add(mArc); }
    public void AddMinorSector(Sector mSector) { minorSectors.add(mSector); }
    public void AddMajorSector(Sector mSector) { majorSectors.add(mSector); }

    public void SetPointsOnCircle(List<Point> pts) { OrderPoints(pts); }

    public boolean DefinesRadius(Segment seg)
    {
        if (_center.structurallyEquals(seg.getPoint1()) && this.PointLiesOn(seg.getPoint2())) return true;

        return _center.structurallyEquals(seg.getPoint1()) && this.PointLiesOn(seg.getPoint2());
    }

    public boolean DefinesDiameter(Segment seg)
    {
        if (!seg.pointLiesOnAndExactlyBetweenEndpoints(_center)) return false;

        return this.PointLiesOn(seg.getPoint1()) && this.PointLiesOn(seg.getPoint2());
    }

    //
    // Area-Related Computations
    //
    protected double Area(double _radius)
    {
        return _radius * _radius * Math.PI;
    }
    protected double RationalArea(double _radius)
    {
        return Area(_radius) / Math.PI;
    }
    @Override
    public boolean IsComputableArea() { return true; }
    
    @Override
    public boolean CoordinateCongruent(Figure that)
    {
        if (that == null) return false;
        if (!(that instanceof Circle)) return false;
        Circle thatCirc = (Circle)that;

        return backend.utilities.math.MathUtilities.doubleEquals(this._radius, thatCirc._radius);
    }
    
//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Any _radius known?
//        for (Segment this_radius : radii)
//        {
//            double length = known.GetSegmentLength(this_radius);
//            if (length > 0) return true;
//        }
//
//        return false;
//    }
//    public override double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Any _radius known?
//        double length = -1;
//        for (Segment this_radius : radii)
//        {
//            length = known.GetSegmentLength(this_radius);
//            if (length > 0) break;
//        }
//
//        if (length < 0) return -1;
//
//        return Area(length);
//    }

    //
    // For arcs, order the points so that there is a consistency: A, B, C, D-> B between AC, B between AD, etc.
    // Only need to order the points if there are more than three points
    //
    public ArrayList<Point> OrderPoints(List<Point> points)
    {
        ArrayList<Pair<Double, Point>> pointAngleMap = new ArrayList<Pair<Double, Point>>();

        for (Point point : points)
        {
            double radianAngle = Point.GetRadianStandardAngleWithCenter(this._center, point);

            // Angles are between 0 and 2pi
            // insert the point into the correct position (starting from the back); insertion sort-style
            int index;
            for (index = 0; index < pointAngleMap.size(); index++)
            {
                if (radianAngle > pointAngleMap.get(index).getKey()) break;
            }
            pointAngleMap.add(index, new Pair<Double, Point>(radianAngle, point));
        }

        //
        // Put all the points : the final ordered list
        //
        ArrayList<Point> ordered = new ArrayList<Point>();
        for (Pair<Double, Point> pair : pointAngleMap)
        {
            pointsOnCircle.add(pair.getValue());
            ordered.add(pair.getValue());
        }

        return ordered;
    }

    public ArrayList<Point> ConstructAllMidpoints(List<Point> given)
    {
        ArrayList<Point> ordered = this.OrderPoints(given);
        ArrayList<Point> ptsWithMidpoints = new ArrayList<Point>();

        if (ordered.size() < 2) return ordered;

        //
        // Walk around the ordered points : a COUNTER-CLOCKWISE direction.
        //
        for (int p = 0; p < ordered.size(); p++)
        {
            ptsWithMidpoints.add(ordered.get(p));

            Point midpt = this.Midpoint(ordered.get(p), ordered.get((p + 1) % ordered.size()));
            Point opp = this.OppositePoint(midpt);

            if (Point.CounterClockwise(ordered.get(p), midpt, ordered.get((p + 1) % ordered.size())))
            {
                ptsWithMidpoints.add(midpt);
            }
            else ptsWithMidpoints.add(opp);
        }

        return ptsWithMidpoints;
    }

    public Segment GetRadius(Segment r)
    {
        if (r == null) return null;

        return backend.utilities.list.Utilities.GetStructurally(radii, r);
    }

    @Override
    public boolean PointLiesInOrOn(Point pt)
    {
        if (pt == null) return false;

        return PointLiesOn(pt) || PointLiesInside(pt);
    }

    @Override
    public boolean PointLiesInside(Point pt)
    {
        if (pt == null) return false;

        if (PointLiesOn(pt)) return false;

        return PointIsInterior(pt);
    }

    @Override
    public ArrayList<Segment> Segmentize()
    {
        if (!approxSegments.isEmpty()) return approxSegments;

        // How much we will change the angle measure as we create segments.
        double angleIncrement = 2 * Math.PI / Figure.NUM_SEGS_TO_APPROX_ARC;

        // The first point will always be at 0 degrees.
        Point firstPoint = Point.GetPointFromAngle(_center, _radius, 0.0);
        Point secondPoint = null;
        double angle = 0;
        for (int i = 1; i <= Figure.NUM_SEGS_TO_APPROX_ARC; i++)
        {
            approxPoints.add(firstPoint);

            // Get the next point.
            angle += angleIncrement;
            secondPoint = Point.GetPointFromAngle(_center, _radius, angle);

            // Make the segment.
            approxSegments.add(new Segment(firstPoint, secondPoint));

            // Rotate points.
            firstPoint = secondPoint;
        }

        approxPoints.add(secondPoint);

        return approxSegments;
    }

    // Make the circle into a regular n-gon that approximates it.
    @Override
    public Polygon GetPolygonalized()
    {
        if (polygonalized != null) return polygonalized;

        polygonalized = Polygon.MakePolygon(Segmentize());

        return polygonalized;
    }

    //
    // For each polygon, it is inscribed : the circle? Is it circumscribed?
    //
    public void AnalyzePolygonInscription(Polygon poly)
    {
        int index = Polygon.GetPolygonIndex(poly.orderedSides.size());

        if (PolygonCircumscribesCircle(poly.orderedSides)) circumPolys.get(index).add(poly);
        if (CircleCircumscribesPolygon(poly.orderedSides)) inscribedPolys.get(index).add(poly);
    }

    //
    // The input polygon is : the form of segments (so it can be used by a polygon)
    //
    public boolean PolygonCircumscribesCircle(List<Segment> segments)
    {
        // All of the sides of the polygon must be tangent to the circle to be circumscribed about the circle.
        for (Segment segment : segments)
        {
            if (this.IsTangent(segment) == null) return false;
        }

        return true;
    }

    //
    // The input polygon is : the form of segments (so it can be used by a polygon)
    //
    public boolean CircleCircumscribesPolygon(List<Segment> segments)
    {
        // All of the vertices of the polygon must be on the circle to be inscribed : the circle.
        // That is, all of the segments must be chords.
        for (Segment segment : segments)
        {
            if (!this.IsChord(segment)) return false;
        }

        return true;
    }

    //
    // Determine if this segment is applicable to the circle: _secants, tangent, and chords.
    //
    public void AnalyzeSegment(Segment thatSegment, ArrayList<Point> figPoints)
    {
        Segment tangentRadius = IsTangent(thatSegment);
        if (tangentRadius != null) tangents.put(thatSegment, tangentRadius);

        if (DefinesDiameter(thatSegment))
        {
            // Add radii to the list.
            backend.utilities.list.Utilities.AddStructurallyUnique(radii, new Segment(this._center, thatSegment.getPoint1()));
            backend.utilities.list.Utilities.AddStructurallyUnique(radii, new Segment(this._center, thatSegment.getPoint2()));
        }

        if (IsChord(thatSegment))
        {
            backend.utilities.list.Utilities.addUnique(chords, thatSegment);
        }
        else
        {
            // Atomizer.AtomicRegion _secants and diameters (thus radii : special cases)
            OutSingle<Segment> out = new OutSingle<Segment>();
            if (IsSecant(thatSegment, figPoints, out))
            {
                Segment chord = out.get();
                
                // Add to the _secants for this circle.
                _secants.put(thatSegment, chord);

                // Also add to the chord list.
                backend.utilities.list.Utilities.addUnique(chords, chord);
            }
        }

        // Is a _radius the result of a segment starting at the _center and extending outward?
        // We collect all other types below.
        Segment _radius = IsRadius(thatSegment, figPoints);
        if (_radius != null) backend.utilities.list.Utilities.addUnique(radii, _radius);
    }

    //
    // Determine all applicable _secants, tangent, and chords for this circle
    //
    public void cleanUp()
    {
        // Now that we have all the chords for this triangle, which are diameters?
        for (Segment chord : chords)
        {
            // The _center needs to be the midpoint, but verifying the _center is on the chord suffices : this context.
            if (chord.pointLiesOnAndExactlyBetweenEndpoints(this._center))
            {
                // Add to diameters....
                backend.utilities.list.Utilities.addUnique(diameters, chord);

                // but also collect radii
                Segment new_radius = Segment.GetFigureSegment(this._center, chord.getPoint1());
                if (new_radius == null) new_radius = new Segment(this._center, chord.getPoint1());
                backend.utilities.list.Utilities.AddStructurallyUnique(radii, new_radius);

                new_radius = Segment.GetFigureSegment(this._center, chord.getPoint2());
                if (new_radius == null) new_radius = new Segment(this._center, chord.getPoint2());
                backend.utilities.list.Utilities.AddStructurallyUnique(radii, new_radius);
            }
        }
    }

    //
    // Determine tangency of the given segment.
    // Indicate tangency by returning the segment which creates the 90^0 angle.
    //
    public Segment IsTangent(Segment segment)
    {
        // If the _center and the segment points are collinear, this will not be a tangent.
        if (segment.PointLiesOn(this._center)) return null;

        // Acquire the line perpendicular to the segment that passes through the _center of the circle.
        Segment perpendicular = segment.GetPerpendicular(this._center);

        // If the segment was found to pass through the _center, it is not a tangent
        if (perpendicular.equals(segment)) return null;

        // Is this perpendicular segment a _radius? Check length
        //if (!Utilities.doubleEquals(perpendicular.Length, this._radius)) return null;

        // Is the perpendicular a _radius? Check that the intersection of the segment and the perpendicular is on the circle
        Point intersection = segment.FindIntersection(perpendicular);
        if (!this.PointLiesOn(intersection)) return null;

        // The intersection between the perpendicular and the segment must be within the endpoints of the segment.
        return segment.PointLiesOnAndBetweenEndpoints(intersection) ? perpendicular : null;
    }

    //
    // Does the given segment pass through the circle so that it acts as a diameter (or contains a diameter)?
    //
    private boolean ContainsDiameter(Segment segment)
    {
        if (!segment.PointLiesOnAndBetweenEndpoints(this._center)) return false;

        // the endpoints of the segment must be on or outside the circle.
        double distance = Point.calcDistance(this._center, segment.getPoint1());
        if (distance < this._radius) return false;

        distance = Point.calcDistance(this._center, segment.getPoint2());
        if (distance < this._radius) return false;

        return true;
    }


    //
    // Given the secant, there is a midpoint along the secant (wrt to the circle), given the distance,
    // find the two points of intersection between the secant and the circle.
    // Return the resultant chord segment.
    //
    private Segment ConstructChord(Segment segment, Point midpt, double distance, ArrayList<Point> figPoints)
    {
        //                distance
        //      circPt1    _____   circPt2
        //
        // Find the exact coordinates of the two 'circ' points.
        //
        double deltaX = 0;
        double deltaY = 0;
        if (segment.IsVertical())
        {
            deltaX = 0;
            deltaY = distance;
        }
        else if (segment.IsHorizontal())
        {
            deltaX = distance;
            deltaY = 0;
        }
        else
        {
            deltaX = Math.sqrt(Math.pow(distance, 2) / (1 + Math.pow(segment.slope(), 2)));
            deltaY = segment.slope() * deltaX;
        }
        Point circPt1 = backend.utilities.ast_helper.Utilities.AcquirePoint(figPoints, new Point("", midpt.getX() + deltaX, midpt.getY() + deltaY));

        // intersection is the midpoint of circPt1 and pt2.
        Point circPt2 = backend.utilities.ast_helper.Utilities.AcquirePoint(figPoints, new Point("", 2 * midpt.getX() - circPt1.getX(), 2 * midpt.getY() - circPt1.getY()));

        // Create the actual chord
        return new Segment(circPt1, circPt2);
    }

    //
    // Determine if the segment passes through the circle (we know it is not a chord since they have been filtered).
    //
    private boolean IsSecant(Segment segment, ArrayList<Point> figPoints, OutSingle<Segment> out)
    {
        // Make it null and overwrite when necessary.
        Segment chord = null;

        // Is the segment exterior to the circle, but intersects at an endpoint (and wasn't tangent).
        if (this.PointIsExterior(segment.getPoint1()) && this.PointLiesOn(segment.getPoint2())) return false;
        if (this.PointIsExterior(segment.getPoint2()) && this.PointLiesOn(segment.getPoint1())) return false;

        // Is one endpoint of the segment simply on the interior of the circle (so we have nothing)?
        if (this.PointIsInterior(segment.getPoint1()) || this.PointIsInterior(segment.getPoint2())) return false;

        if (ContainsDiameter(segment))
        {
            chord = ConstructChord(segment, this._center, this._radius, figPoints);

            // Add radii to the list.
            radii.add(new Segment(this._center, chord.getPoint1()));
            radii.add(new Segment(this._center, chord.getPoint2()));

            return true;
        }

        // Acquire the line perpendicular to the segment that passes through the _center of the circle.
        Segment perpendicular = segment.GetPerpendicular(this._center);

        // Is this perpendicular segment a _radius? If so, it's tangent, not a secant
        //if (Utilities.doubleEquals(perpendicular.Length, this._radius)) return false;

        // Is the perpendicular a _radius? Check if the intersection of the segment and the perpendicular is on the circle. If so, it's tangent
        Point intersection = segment.FindIntersection(perpendicular);
        if (this.PointLiesOn(intersection)) return false;

        //Adjust perpendicular segment to include intersection with segment
        perpendicular = new Segment(intersection, this._center);

        // Filter the fact that there are no intersections
        if (perpendicular.length() > this._radius) return false;

        //            1/2 chord length
        //                 _____   circPoint
        //                |    /
        //                |   /
        // perp.Length    |  / _radius
        //                | /
        //                |/
        // Determine the half-chord length via Pyhtagorean Theorem.
        double halfChordLength = Math.sqrt(Math.pow(this._radius, 2) - Math.pow(perpendicular.length(), 2));

        chord = ConstructChord(segment, perpendicular.OtherPoint(this._center), halfChordLength, figPoints);

        out.set(chord);
        
        return true;
    }

    //
    // Is this a direct _radius segment where one endpoint originates at the origin and extends outward?
    // Return the exact _radius.
    private Segment IsRadius(Segment segment, ArrayList<Point> figPoints)
    {
        // The segment must originate from the circle _center.
        if (!segment.hasPoint(this._center)) return null;

        // The segment must be at least as long as a _radius.
        if (!backend.utilities.math.MathUtilities.doubleEquals(segment.length(), this._radius)) return null;

        Point non_centerPt = segment.OtherPoint(this._center);

        // Check for a direct _radius.
        if (this.PointLiesOn(non_centerPt)) return segment;

        //
        // Check for an extended segment.
        //
        //                _radius
        //      _center    _____   circPt
        //
        // Find the exact coordinates of the 'circ' points.
        //
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(segment, out);
        Point inter1 = out.first();
        Point inter2 = out.second();
        
        Point figPoint = backend.utilities.list.Utilities.GetStructurally(figPoints, inter1);
        if (figPoint == null) figPoint = backend.utilities.list.Utilities.GetStructurally(figPoints, inter2);

        return new Segment(_center, figPoint);
    }

    //
    // Find the points of intersection of two circles; may be 0, 1, or 2.
    //
    //   @Override
    public void FindIntersection(Segment ts, OutPair<Point, Point> out)
    {
        Point inter1 = null;
        Point inter2 = null;

        Segment s = new Segment(ts.getPoint1(), ts.getPoint2());

        // SEE: http://stackoverflow.com/questions/1073336/circle-line-collision-detection

        // We have line AB, circle _center C, and _radius R.
        double lengthAB = s.length();
        double[] D = { (ts.getPoint2().getX() - ts.getPoint1().getX()) / lengthAB, (ts.getPoint2().getY() - ts.getPoint1().getY()) / lengthAB }; //Direction vector from A to B

        // Now the line equation is x = D.get(0)*t + A.getX(), y = D.get(1)*t + A.getY() with 0 <= t <= 1.
        double t = D[0] * (this._center.getX() - ts.getPoint1().getX()) + D[1] * (this._center.getY() - ts.getPoint1().getY()); //Closest point to circle _center
        double[] E = { t * D[0] + ts.getPoint1().getX(), t * D[1] + ts.getPoint1().getY() }; //The point described by t.

        double lengthEC = Math.sqrt(Math.pow(E[0] - this._center.getX(), 2) + Math.pow(E[1] - this._center.getY(), 2));

        // Possible Intersection?
        if (lengthEC < this._radius)
        {
            // Compute distance from t to circle intersection point
            double dt = Math.sqrt(Math.pow(this._radius, 2) - Math.pow(lengthEC, 2));

            // First intersection - find and verify that the point lies on the segment
            Point possibleInter1 = new Point("", (t - dt) * D[0] + ts.getPoint1().getX(), (t - dt) * D[1] + ts.getPoint1().getY());
            /* if (ts.PointLiesOnAndBetweenEndpoints(possibleInter1)) */ inter1 = possibleInter1;

            // Second intersection - find and verify that the point lies on the segment
            Point possibleInter2 = new Point("", (t + dt) * D[0] + ts.getPoint1().getX(), (t + dt) * D[1] + ts.getPoint1().getY());
            /* if (ts.PointLiesOnAndBetweenEndpoints(possibleInter2)) */ inter2 = possibleInter2;
        }
        //
        // Tangent point (E)
        //
        else if (backend.utilities.math.MathUtilities.doubleEquals(lengthEC, this._radius))
        {
            // First intersection
            inter1 = new Point("", E[0], E[1]);
        }

        // Put the intersection into inter1 if there is only one intersection.
        if (inter1 == null && inter2 != null) { inter1 = inter2; inter2 = null; }
    }

    //
    // Find the points of intersection of two circles; may be 0, 1, or 2.
    // Uses the technique found here: http://mathworld.wolfram.com/Circle-CircleIntersection.html
    //
    public void FindIntersection(Circle thatCircle, OutPair<Point, Point> out)
    {
        Point inter1 = null;
        Point inter2 = null;

        // SEE: http://stackoverflow.com/questions/3349125/circle-circle-intersection-points

        // Distance between _centers
        double d = Math.sqrt(Math.pow(thatCircle._center.getX() - this._center.getX(), 2) +
                   Math.pow(thatCircle._center.getY() - this._center.getY(), 2));

        // Separate circles
        if (d > this._radius + thatCircle._radius) { }

        // One circle contained : the other
        else if (d < Math.abs(this._radius - thatCircle._radius)) { }

        // Coinciding circles
        else if (d == 0 && this._radius == thatCircle._radius) { }

        // We have intersection(s)!
        else
        {
            // Distance from _center of this to midpt of intersections
            double a = (Math.pow(this._radius, 2) - Math.pow(thatCircle._radius, 2) + Math.pow(d, 2)) / (2 * d);

            // Midpoint of the intersections
            double[] midpt = { this._center.getX() + a * (thatCircle._center.getX() - this._center.getX()) / d, this._center.getY() + a * (thatCircle._center.getY() - this._center.getY()) / d };

            // Distance from midpoint to intersections
            double h = Math.sqrt(Math.pow(this._radius, 2) - Math.pow(a, 2));

            // Only one intersection
            if (h == 0)
            {
                inter1 = new Point("", midpt[0], midpt[1]);
            }
            // Two intersections
            else
            {
                inter1 = new Point("", midpt[0] + h * (thatCircle._center.getY() - this._center.getY()) / d,
                                       midpt[1] - h * (thatCircle._center.getX() - this._center.getX()) / d);

                inter2 = new Point("", midpt[0] - h * (thatCircle._center.getY() - this._center.getY()) / d,
                                       midpt[1] + h * (thatCircle._center.getX() - this._center.getX()) / d);
            }
        }

        // Put the intersection into inter1 if there is only one intersection.
        if (inter1 == null && inter2 != null)
        {
            inter1 = inter2;
            inter2 = null;
        }

        //
        // Are the circles close enough to merit one intersection point instead of two?
        // That is, are the intersection points the same (within epsilon)?
        //
        if (inter1 != null && inter2 != null)
        {
            if (inter1.structurallyEquals(inter2)) inter2 = null;
        }
        
        out.set(inter1,  inter2);
    }

    //
    // Are the segment endpoints directly on the circle? 
    //
    private boolean IsChord(Segment segment)
    {
        return this.PointLiesOn(segment.getPoint1()) && this.PointLiesOn(segment.getPoint2());
    }

    //
    // Determine if the given point is on the circle via substitution into (x1 - x2)^2 + (y1 - y2)^2 = r^2
    //
    @Override
    public boolean PointLiesOn(Point pt)
    {
        return backend.utilities.math.MathUtilities.doubleEquals(Math.pow(_center.getX() - pt.getX(), 2) + Math.pow(_center.getY() - pt.getY(), 2), Math.pow(this._radius, 2));
    }

    //
    // Determine if the given point is a point : the interioir of the circle: via substitution into (x1 - x2)^2 + (y1 - y2)^2 = r^2
    //
    public boolean PointIsInterior(Point pt)
    {
        return backend.utilities.math.MathUtilities.lessThan(Point.calcDistance(this._center, pt), this._radius);
    }
    public boolean PointIsExterior(Point pt)
    {
        return backend.utilities.math.MathUtilities.greaterThan(Point.calcDistance(this._center, pt), this._radius);
    }

    //
    // Concentric circles share the same _center, but radii differ.
    //
    public boolean AreConcentric(Circle thatCircle)
    {
        return this._center.structurallyEquals(thatCircle._center) && !backend.utilities.math.MathUtilities.doubleEquals(thatCircle._radius, this._radius);
    }

    //
    // Orthogonal Circles intersect at 90^0: radii connecting to intersection point are perpendicular
    //
    public boolean AreOrthognal(Circle thatCircle)
    {
        // Find the intersection points
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(thatCircle, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        // If the circles intersect at 0 points.
        if (inter1 == null) return false;

        // If the circles intersect at 1 point they are tangent
        if (inter2 == null) return false;

        // Create two radii, one for each circle; arbitrarily choose the first point (both work)
        Segment radiusThis = new Segment(this._center, inter1);
        Segment radiusThat = new Segment(this._center, inter1);

        return radiusThis.IsPerpendicularTo(radiusThat);
    }

    //
    // Tangent circle have 1 intersection point
    //
    public Point AreTangent(Circle thatCircle)
    {
        // Find the intersection points
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(thatCircle, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        
        // If the circles have one valid point of intersection.
        if (inter1 != null && inter2 == null) return inter1;

        return null;
    }

    //
    // Does the given segment contain a _radius of this circle?
    //
    public boolean ContainsRadiusWithin(Segment thatSegment)
    {
        for (Segment _radius : radii)
        {
            if (thatSegment.HasSubSegment(_radius)) return true;
        }

        return false;
    }

    //
    // Does the given segment contain a chord? Return the chord.
    //
    public Segment ContainsChord(Segment thatSegment)
    {
        for (Segment key : _secants.keySet())
        {
            // Does the secant contain that segment? If so, is the chord contained : that Segment?
            if (key.HasSubSegment(thatSegment))
            {
                Segment value = _secants.get(key);
                if (thatSegment.HasSubSegment(value)) return value;
            }
        }

        return null;
    }



    public boolean IsCentral(Angle angle)
    {
        if (this._center.structurallyEquals(angle.GetVertex()))
        {
            // The rays need to contain radii of the circle.
            if (this.ContainsRadiusWithin(angle.ray1) && this.ContainsRadiusWithin(angle.ray2))
            {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Circle> IsCentralAngle(Angle angle)
    {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        for (Circle circle : figureCircles)
        {
            if (circle.IsCentral(angle))
            {
                circles.add(circle);
            }
        }

        return circles;
    }

    public boolean IsInscribed(Angle angle)
    {
        // If the angle has vertex on the circle
        if (!this.PointLiesOn(angle.GetVertex())) return false;

        // Do the angle rays form or contain chords? 
        // GetChord() will check if the segment is a chord, and if it is not, it will check if the segment is a secant containing a chord
        Segment chord1 = this.GetChord(angle.ray1);
        Segment chord2 = this.GetChord(angle.ray2);

        return chord1 != null && chord2 != null;
    }

    public static ArrayList<Circle> IsInscribedAngle(Angle angle)
    {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        for (Circle circle : figureCircles)
        {
            if (circle.IsInscribed(angle)) circles.add(circle);
        }

        return circles;
    }

    public static ArrayList<Circle> GetChordCircles(Segment segment)
    {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        for (Circle circle : figureCircles)
        {
            if (circle.chords.contains(segment)) circles.add(circle);
        }

        return circles;
    }

    public static ArrayList<Circle> GetSecantCircles(Segment segment)
    {
        ArrayList<Circle> secCircles = new ArrayList<Circle>();

        for (Circle circle : figureCircles)
        {
            if (circle._secants.containsKey(segment)) secCircles.add(circle);
            if (circle.chords.contains(segment)) secCircles.add(circle);
        }

        return secCircles;
    }

    // A lookup of a chord based on the given secant.
    public Segment GetChord(Segment thatSegment)
    {
        Segment chord = null;

        // If the given segment is a chord, return that segment
        if (chords.contains(thatSegment)) return thatSegment;

        // Otherwise, check to see if it is a secant containing a chord
        chord = _secants.get(thatSegment);

        return chord;
    }

    // return the midpoint between these two on the circle.
    public Point Midpoint(Point a, Point b)
    {
        if (!this.PointLiesOn(a)) return null;
        if (!this.PointLiesOn(b)) return null;

        // Make the chord.
        Segment chord = new Segment(a, b);

        Point pt1 = null;
        Point pt2 = null;

        // Is this a diameter? If so, quickly return a point perpendicular to the diameter
        if (DefinesDiameter(chord))
        {
            Segment perp = chord.GetPerpendicular(_center);

            OutPair<Point, Point> out = new OutPair<Point, Point>();
            this.FindIntersection(perp, out);
            pt1 = out.first();
            pt2 = out.second();

            // Arbitrarily choose one of the points.
            return pt1 != null ? pt1 : pt2;
        }

        // Make _radius through the midpoint of the chord.
        Segment _radius = new Segment(_center, chord.Midpoint());

        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(_radius, out);
        pt1 = out.first();
        pt2 = out.second();

        if (pt2 == null) return pt1;

        Point theMidpoint = Arc.StrictlyBetweenMinor(pt1, new MinorArc(this, a, b)) ? pt1 : pt2;

        double angle1 = new Angle(a, _center, theMidpoint).measure;
        double angle2 = new Angle(b, _center, theMidpoint).measure;
        if (!backend.utilities.math.MathUtilities.doubleEquals(angle1, angle2))
        {
            throw new IllegalArgumentException("Midpoint is incorrect; angles do not equate: " + angle1 + " " + angle2);
        }

        return theMidpoint;
    }

    // return the midpoint between these two on the circle.
    public Point Midpoint(Point a, Point b, Point sameSide)
    {
        Point midpt = Midpoint(a, b);

        Segment segment = new Segment(a, b);
        Segment other = new Segment(midpt, sameSide);

        Point intersection = segment.FindIntersection(other);

        if (Segment.Between(intersection, midpt, sameSide)) return this.OppositePoint(midpt);

        return midpt;
    }

    // return the midpoint between these two on the circle.
    public Point OppositePoint(Point that)
    {
        if (!this.PointLiesOn(that)) return null;

        // Make the _radius
        Segment _radius = new Segment(_center, that);

        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(_radius, out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        if (pt2 == null) return null;

        return pt1.structurallyEquals(that) ? pt2 : pt1;
    }

    public boolean CircleContains(Circle that)
    {
        return Point.calcDistance(this._center, that._center) <= Math.abs(this._radius - that._radius);
    }

    public boolean HasArc(Arc arc)
    {
        return this.structurallyEquals(arc.getCircle());
    }

    public boolean HasArc(Point p1, Point p2)
    {
        return this.PointLiesOn(p1) && this.PointLiesOn(p2);
    }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Circle)) return false;
        Circle thatCircle = (Circle)obj;

        return thatCircle._center.structurallyEquals(_center) && backend.utilities.math.MathUtilities.doubleEquals(thatCircle._radius, this._radius);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Circle)) return false;
        Circle thatCircle = (Circle)obj;

        return thatCircle._center.equals(_center) && backend.utilities.math.MathUtilities.doubleEquals(thatCircle._radius, this._radius);
    }

    @Override
    public String toString()
    {
        return "Circle(" + this._center + ": r = " + this._radius + ")";
    }

    @Override
    public String CheapPrettyString()
    {
        return "Circle(" + this._center.SimpleToString() + ")";
    }

//    public ArrayList<Area_Based_Analyses.Atomizer.AtomicRegion> Atomize(List<Point> figurePoints)
//    {
//        ArrayList<Segment> constructedChords = new ArrayList<Segment>();
//        ArrayList<Segment> constructedRadii = new ArrayList<Segment>();
//        ArrayList<Point> imagPoints = new ArrayList<Point>();
//
//        ArrayList<Point> interPts = GetIntersectingPoints();
//
//        //
//        // Construct the radii
//        //
//        switch (interPts.size())
//        {
//            // If there are no points of interest, the circle is the atomic region.
//            case 0:
//                return Utilities.makeList<AtomicRegion>(new ShapeAtomicRegion(this));
//
//                // If only 1 intersection point, create the diameter.
//            case 1:
//                Point opp = Utilities.AcquirePoint(figurePoints, this.OppositePoint(interPts.get(0)));
//                constructedRadii.add(new Segment(_center, interPts.get(0)));
//                constructedRadii.add(new Segment(_center, opp));
//                imagPoints.add(opp);
//                interPts.add(opp);
//                break;
//
//            default:
//                for (Point interPt : interPts)
//                {
//                    constructedRadii.add(new Segment(_center, interPt));
//                }
//                break;
//        }
//
//        //
//        // Construct the chords
//        //
//        ArrayList<Segment> chords = new ArrayList<Segment>();
//        for (int p1 = 0; p1 < interPts.size() - 1; p1++)
//        {
//            for (int p2 = p1 + 1; p2 < interPts.size(); p2++)
//            {
//                Segment chord = new Segment(interPts[p1], interPts[p2]);
//                if (!DefinesDiameter(chord)) constructedChords.add(chord);
//            }
//        }
//
//        //
//        // Do any of the created segments result : imaginary intersection points.
//        //
//        for (Segment chord : constructedChords)
//        {
//            for (Segment _radius : constructedRadii)
//            {
//                Point inter = Utilities.AcquireRestrictedPoint(figurePoints, chord.FindIntersection(_radius), chord, _radius);
//                if (inter != null)
//                {
//                    chord.addCollinearPoint(inter);
//                    _radius.addCollinearPoint(inter);
//
//                    // if (!Utilities.HasStructurally<Point>(figurePoints, inter)) imagPoints.add(inter);
//                    backend.utilities.list.Utilities.addUnique(imagPoints, inter);
//                }
//            }
//        }
//
//        for (int c1 = 0; c1 < constructedChords.size() - 1; c1++)
//        {
//            for (int c2 = c1 + 1; c2 < constructedChords.size(); c2++)
//            {
//                Point inter = constructedChords[c1].FindIntersection(constructedChords[c2]);
//                inter = Utilities.AcquireRestrictedPoint(figurePoints, inter, constructedChords[c1], constructedChords[c2]);
//                if (inter != null)
//                {
//                    constructedChords[c1].addCollinearPoint(inter);
//                    constructedChords[c2].addCollinearPoint(inter);
//
//                    //if (!Utilities.HasStructurally<Point>(figurePoints, inter)) imagPoints.add(inter);
//                    backend.utilities.list.Utilities.addUnique(imagPoints, inter);
//                }
//            }
//        }
//
//        //
//        // Add all imaginary points to the list of figure points.
//        //
//        backend.utilities.list.Utilities.addUniqueList(figurePoints, imagPoints);
//
//        //
//        // Construct the Planar graph for atomic region identification.
//        //
//        Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph graph = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph();
//
//        //
//        // Add all imaginary points, intersection points, and _center.
//        //
//        for (Point pt : imagPoints)
//        {
//            graph.addNode(pt);
//        }
//
//        for (Point pt : interPts)
//        {
//            graph.addNode(pt);
//        }
//
//        graph.addNode(this._center);
//
//        //
//        // Add all chords and radii as edges.
//        //
//        for (Segment chord : constructedChords)
//        {
//            for (int p = 0; p < chord.collinear.size() - 1; p++)
//            {
//                graph.addUndirectedEdge(chord.collinear[p], chord.collinear[p + 1],
//                        new Segment(chord.collinear[p], chord.collinear[p + 1]).Length,
//                        Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
//            }
//        }
//
//        for (Segment _radius : constructedRadii)
//        {
//            for (int p = 0; p < _radius.collinear.size() - 1; p++)
//            {
//                graph.addUndirectedEdge(_radius.collinear[p], _radius.collinear[p + 1],
//                        new Segment(_radius.collinear[p], _radius.collinear[p + 1]).Length,
//                        Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
//            }
//        }
//
//        //
//        // Add all arcs
//        //
//        ArrayList<Point> arcPts = this.ConstructAllMidpoints(interPts);
//        for (int p = 0; p < arcPts.size(); p++)
//        {
//            graph.addNode(arcPts[p]);
//            graph.addNode(arcPts[(p + 1) % arcPts.size()]);
//
//            graph.addUndirectedEdge(arcPts[p], arcPts[(p + 1) % arcPts.size()],
//                    new Segment(arcPts[p], arcPts[(p + 1) % interPts.size()]).Length,
//                    Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_ARC);
//        }
//
//        //
//        // Convert the planar graph to atomic regions.
//        //
//        Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph copy = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph(graph);
//        FacetCalculator atomFinder = new FacetCalculator(copy);
//        ArrayList<Primitive> primitives = atomFinder.GetPrimitives();
//        ArrayList<AtomicRegion> atoms = PrimitiveToRegionConverter.Convert(graph, primitives, Utilities.makeList<Circle>(this));
//
//        //
//        // A filament may result : the creation of a major AND minor arc; both are not required.
//        // Figure out which one to omit.
//        // Multiple semi-circles may arise as well; omit if they can be broken into constituent elements.
//        //
//        List <AtomicRegion> trueAtoms = new ArrayList<AtomicRegion>();
//
//        for (int a1 = 0; a1 < atoms.size(); a1++)
//        {
//            boolean trueAtom = true;
//            for (int a2 = 0; a2 < atoms.size(); a2++)
//            {
//                if (a1 != a2)
//                {
//                    if (atoms[a1].contains(atoms[a2]))
//                    {
//                        trueAtom = false;
//                        break;
//                    }
//
//                }
//            }
//
//            if (trueAtom) trueAtoms.add(atoms[a1]);
//        }
//
//        atoms = trueAtoms;
//
//        return trueAtoms;
//    }

    //private double CentralAngleMeasure(Point pt1, Point pt2)
    //{
    //    return (new MinorArc(this, pt1, pt2)).GetMinorArcMeasureDegrees();
    //}
//
//    public void ConstructImpliedAreaBasedSectors(out ArrayList<Sector> minorSectors,
//            out ArrayList<Sector> majorSectors,
//            out ArrayList<Semicircle> semicircles)
//    {
//        minorSectors = new ArrayList<Sector>();
//        majorSectors = new ArrayList<Sector>();
//        semicircles = new ArrayList<Semicircle>();
//
//        // Points of interest for atomic region identification (and thus arc / sectors).
//        ArrayList<Point> interPts = this.OrderPoints(GetIntersectingPoints());
//
//        // If there are no points of interest, the circle is the atomic region.
//        if (!interPts.Any()) return;
//
//        // Cycle through all n C 2 intersection points and resultant arcs / sectors.
//        for (int p1 = 0; p1 < interPts.size() - 1; p1++)
//        {
//            for (int p2 = p1 + 1; p2 < interPts.size(); p2++)
//            {
//                //
//                // Do we have a diameter?
//                //
//                Segment diameter = new Segment(interPts[p1], interPts[p2]);
//                if (this.DefinesDiameter(diameter))
//                {
//                    // Create two semicircles; for simplicity, we choose the points on the semi-circle to be midpoints o neither, respective side.
//                    Point midpoint = this.Midpoint(interPts[p1], interPts[p2]);
//                    Point oppMidpoint = this.OppositePoint(midpoint);
//
//                    // Altogether, these 4 points define 4 quadrants (with the _center).
//                    semicircles.add(new Semicircle(this, interPts[p1], interPts[p2], midpoint, diameter));
//                    semicircles.add(new Semicircle(this, interPts[p1], interPts[p2], oppMidpoint, diameter));
//                }
//
//                //
//                // Normal major / minor sector construction.
//                //
//                else
//                {
//                    minorSectors.add(new Sector(new MinorArc(this, interPts[p1], interPts[p2])));
//                    majorSectors.add(new Sector(new MajorArc(this, interPts[p1], interPts[p2])));
//                }
//            }
//        }
//    }
//
//    /// <summary>
//    /// Make a set of connections for atomic region analysis.
//    /// </summary>
//    /// <returns></returns>
//    public override ArrayList<Connection> MakeAtomicConnections()
//    {
//        ArrayList<Segment> segments = this.Segmentize();
//        ArrayList<Connection> connections = new ArrayList<Connection>();
//
//        for (Segment approxSide : segments)
//        {
//            connections.add(new Connection(approxSide.getPoint1(), approxSide.getPoint2(), ConnectionType.ARC,
//                    new MinorArc(this, approxSide.getPoint1(), approxSide.getPoint2())));
//        }
//
//        return connections;
//    }

    //
    // that circle lies within this circle.
    //
    private boolean ContainsCircle(Circle that)
    {
        if (this._radius - that._radius < 0) return false;

        return Point.calcDistance(this._center, that._center) <= this._radius - that._radius;
    }

    //
    // that Polygon lies within this circle.
    //
    private boolean ContainsPolygon(Polygon that)
    {
        //
        // All points are interior to the polygon.
        //
        for (Point thatPt : that.points)
        {
            if (!this.PointLiesInOrOn(thatPt)) return false;
        }

        return true;
    }

    //
    // that Polygon lies within this circle.
    //
    private boolean ContainsSector(Sector that)
    {
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint1())) return false;
        if (!this.PointLiesInOrOn(that.theArc.getEndpoint2())) return false;

        if (!this.PointLiesInOrOn(that.theArc.getCircle().getCenter())) return false;
        if (!this.PointLiesInOrOn(that.theArc.Midpoint())) return false;

        return true;
    }

//    //
//    // A shape within this shape?
//    //
//    @Override
//    public boolean contains(Figure that)
//    {
//        if (that instanceof Circle) return ContainsCircle((Circle)that);
//        if (that instanceof Polygon) return ContainsPolygon((Polygon)that);
//        if (that instanceof Sector) return ContainsSector((Sector)that);
//
//        return false;
//    }

    //
    // Does this particular segment intersect one of the sides.
    //
    public boolean Covers(Segment that)
    {
        return this.PointLiesOn(that.getPoint1()) && this.PointLiesOn(that.getPoint2());
    }

    //
    // An arc is covered if one side of the polygon defines the endpoints of the arc.
    //
    public boolean Covers(Arc that)
    {
        return this.structurallyEquals(that.getCircle());
    }

//    //
//    // Does the atom have a connection which intersects the sides of the polygon.
//    //
//    @Override
//    public boolean Covers(AtomicRegion atom)
//    {
//        for (Connection conn : atom.connections)
//        {
//            if (conn.type == ConnectionType.SEGMENT)
//            {
//                if (this.Covers((Segment)conn.segmentOrArc)) return true;
//            }
//            else if (conn.type == ConnectionType.ARC)
//            {
//                if (this.Covers((Arc)conn.segmentOrArc)) return true;
//            }
//        }
//
//        return false;
//    }
    
    
    //
    //
    //
    //
    // Static Parts
    //
    //
    //
    //
    // Maintain a public repository of all circle objects : the figure.
    //
    public static void clear()
    {
        figureCircles.clear();
    }
    public static ArrayList<Circle> figureCircles = new ArrayList<Circle>();
    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
        if (clause instanceof Circle) figureCircles.add((Circle)clause);
    }
    public static Circle GetFigureCircle(Point cen, double rad)
    {
        Circle candCircle = new Circle(cen, rad);

        // Search for exact segment first
        for (Circle circle : figureCircles)
        {
            if (circle.structurallyEquals(candCircle)) return circle;
        }

        return null;
    }

    public static ArrayList<Circle> GetFigureCirclesBy_radius(Segment _radius)
    {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        for (Circle circle : figureCircles)
        {
            if (circle.radii.contains(_radius))
            {
                circles.add(circle);
            }
        }

        return circles;
    }
}