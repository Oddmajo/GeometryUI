package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.ast.figure.Polygonalizable;
import backend.ast.figure.Shape;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.polygon.Polygon;
import backend.ast.figure.delegates.CircleDelegate;
import backend.ast.figure.delegates.MidpointDelegate;
import backend.ast.figure.delegates.PointLiesOnDelegate;
import backend.ast.figure.delegates.intersections.IntersectionDelegate;
import backend.utilities.Pair;
import backend.utilities.PointFactory;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;
import backend.utilities.translation.OutPair;
import backend.utilities.translation.OutSingle;

// <summary>
// A circle is defined by a _center and _radius.
// </summary>
public class Circle extends Shape
{
    public static final int[] SNAPPING_ANGLE_MEASURES_DEGREES = {0, 30, 45, 60,
                                                                 90, 120, 135, 150,
                                                                 180, 210, 225, 240,
                                                                 270, 300, 315, 330};
    
//    public static ArrayList<Circle> getFigureCircles()
//    {
//        return figureCircles;
//    }

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

    
    /*
     * @param center -- a point defining the center
     * @param r -- radius (> 0)
     * A circle is defined by a center and radius
     */
    public Circle(Point center, double r)
    {
        super();

        this._center = center;
        _radius = r;

        _secants = new Hashtable<Segment, Segment>();
        chords = new ArrayList<Segment>();
        radii = new ArrayList<Segment>();
        diameters = new ArrayList<Segment>();
        tangents = new Hashtable<Segment, Segment>();

        inscribedPolys = new ArrayList<ArrayList<Polygon>>(Polygon.MAX_EXC_POLY_INDEX);
        circumPolys = new ArrayList<ArrayList<Polygon>>(Polygon.MAX_EXC_POLY_INDEX);
        for (int n = 0; n < Polygon.MAX_EXC_POLY_INDEX; n++)
        {
            inscribedPolys.add(new ArrayList<Polygon>());
            circumPolys.add(new ArrayList<Polygon>());
        }

        pointsOnCircle = new ArrayList<Point>();

        minorArcs = new ArrayList<MinorArc>();
        majorArcs = new ArrayList<MajorArc>();
        minorSectors = new ArrayList<Sector>();
        majorSectors = new ArrayList<Sector>();

        approxPoints = new ArrayList<Point>();
        approxSegments = new ArrayList<Segment>();

//        backend.utilities.list.Utilities.addUniqueStructurally(this._center.getSuperFigures(), this);

        //thisAtomicRegion = new ShapeAtomicRegion(this);

        //this.FigureSynthesizerConstructor();
    }

    /*
     * @param circle a standard circle
     * @param angle degree-measured angle
     * @return point on circle defined by the input angle (measured in standard position)
     */
    public Point getPoint(double angle) { return CircleDelegate.pointByAngle(this, angle); }

    /*
     * @return point opposite the given point (on the circle)
     * The given point and the computed point create a diameter.
     */
    public Point OppositePoint(Point that) { return CircleDelegate.oppositePoint(this, that); }

    /*
     * @param a -- a point on this circle
     * @param b -- a point on this circle
     *  @return the midpoint between @a and @b on the circle.
     */
    public Point getMidpoint(Point a, Point b) { return MidpointDelegate.Midpoint(this, a, b); }
    
    /*
     * @param a -- a point on this circle
     * @param b -- a point on this circle
     * @param sameSide -- a point on the side of the arc we wish to acquire the midpoint
     *  @return the midpoint between @a and @b on the circle (and on the same side as @sameSide).
     */
    public Point Midpoint(Point a, Point b, Point sameSide) { return MidpointDelegate.Midpoint(this, a, b, sameSide); }
    
    /*
     * @param circle -- a standard circle
     * @param segment -- candidate radius (finite)
     * @return true / false whether @segment is a radius in the @circle
     */
    public boolean isRadius(Segment segment) { return CircleDelegate.isRadius(this, segment); }
    
    /*
     * @param pt -- a point on the circle 
     * @return radian angle measure in range [-pi, pi); this is an angle measured in standard position
     * Double.POSITIVE_INFINITY if the point is not on the circle
     */
    public double standardAngleMeasure(Point pt) { return CircleDelegate.standardAngleMeasure(this, pt); }

    /*
     * @param radius -- a radius of the circle 
     * @return radian angle measure in range [-pi, pi); this is an angle measured in standard position
     * Double.POSITIVE_INFINITY if the point is not on the circle
     */
    public double standardAngleMeasureRadius(Segment radius) { return CircleDelegate.standardAngleMeasureRadius(this, radius); }

    /*
     * @param radius -- a ray that (minimally) starts at the center of the circle and goes outward 
     * @return radian angle measure in range [-pi, pi); this is an angle measured from standard position
     * Double.POSITIVE_INFINITY if the point is not on the circle
     */
    public double standardAngleMeasure(Ray ray) { return CircleDelegate.standardAngleMeasure(this, ray); }
    public static double angleMeasure(Ray ray)
    {
        return new Circle(ray.getOrigin(), ray.asSegment().length()).standardAngleMeasure(ray);
    }
    
    /**
     * @param angle -- standard positional angle measured in degrees
     * @return the point on the circle at the given angle
     */
    public Point pointByAngle(double angle) { return CircleDelegate.pointByAngle(this, Math.toRadians(angle)); }
    
    /*
     * <Circle, Circle> Intersection
     *  Points of intersection of two circles; may be 0, 1, or 2.
     *  Uses the technique found here: http://mathworld.wolfram.com/Circle-CircleIntersection.html
     */
    public boolean findIntersection(Circle that, OutPair<Point, Point> out)
    {
        return IntersectionDelegate.findIntersection(this, that, out);
    }
    
    /*
     * <Circle, Segment> Intersection
     *  Points of intersection of one circle and one segment may be 0, 1, or 2.
     */
    public boolean findIntersection(Segment that, OutPair<Point, Point> out)
    {
        return IntersectionDelegate.findIntersection(this, that, out);
    }
    
    
    
    @Override
    public Polygon getPolygon()
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean contains(Polygonalizable p)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    
    
    




    
    
//    public void AddMinorArc(MinorArc mArc) { minorArcs.add(mArc); }
//    public void AddMajorArc(MajorArc mArc) { majorArcs.add(mArc); }
//    public void AddMinorSector(Sector mSector) { minorSectors.add(mSector); }
//    public void AddMajorSector(Sector mSector) { majorSectors.add(mSector); }
//
//    //public void SetPointsOnCircle(List<Point> pts) { OrderPoints(pts); }

    public boolean DefinesRadius(Segment seg)
    {
        if (_center.structurallyEquals(seg.getPoint1()) && this.pointLiesOn(seg.getPoint2())) return true;

        return _center.structurallyEquals(seg.getPoint1()) && this.pointLiesOn(seg.getPoint2());
    }

    public boolean DefinesDiameter(Segment seg)
    {
        if (!seg.pointLiesBetweenEndpoints(_center)) return false;

        return this.pointLiesOn(seg.getPoint1()) && this.pointLiesOn(seg.getPoint2());
    }


    
    
    
    @Override
    public boolean CoordinateCongruent(Figure that)
    {
        if (that == null) return false;
        if (!(that instanceof Circle)) return false;
        Circle thatCirc = (Circle)that;

        return backend.utilities.math.MathUtilities.doubleEquals(this._radius, thatCirc._radius);
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

        return pointLiesOn(pt) || pointLiesInside(pt);
    }

    @Override
    public boolean pointLiesInside(Point pt)
    {
        if (pt == null) return false;

        if (pointLiesOn(pt)) return false;

        return PointIsInterior(pt);
    }



    //
    // For each polygon, it is inscribed : the circle? Is it circumscribed?
    //
    public void AnalyzePolygonInscription(Polygon poly)
    {
        int index = Polygon.GetPolygonIndex(poly.getOrderedSides().size());

        if (PolygonCircumscribesCircle(poly.getOrderedSides())) circumPolys.get(index).add(poly);
        if (CircleCircumscribesPolygon(poly.getOrderedSides())) inscribedPolys.get(index).add(poly);
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

//    //
//    // Determine all applicable _secants, tangent, and chords for this circle
//    //
//    public void cleanUp()
//    {
//        // Now that we have all the chords for this triangle, which are diameters?
//        for (Segment chord : chords)
//        {
//            // The _center needs to be the midpoint, but verifying the _center is on the chord suffices : this context.
//            if (chord.pointLiesOnAndExactlyBetweenEndpoints(this._center))
//            {
//                // Add to diameters....
//                backend.utilities.list.Utilities.addUnique(diameters, chord);
//
//                // but also collect radii
//                Segment new_radius = Segment.GetFigureSegment(this._center, chord.getPoint1());
//                if (new_radius == null) new_radius = new Segment(this._center, chord.getPoint1());
//                backend.utilities.list.Utilities.AddStructurallyUnique(radii, new_radius);
//
//                new_radius = Segment.GetFigureSegment(this._center, chord.getPoint2());
//                if (new_radius == null) new_radius = new Segment(this._center, chord.getPoint2());
//                backend.utilities.list.Utilities.AddStructurallyUnique(radii, new_radius);
//            }
//        }
//    }

    //
    // Determine tangency of the given segment.
    // Indicate tangency by returning the segment which creates the 90^0 angle.
    //
    public Segment IsTangent(Segment segment)
    {
        // If the _center and the segment points are collinear, this will not be a tangent.
        if (segment.pointLiesOn(this._center)) return null;

        // Acquire the line perpendicular to the segment that passes through the _center of the circle.
        Segment perpendicular = segment.getPerpendicularThrough(this._center);

        // If the segment was found to pass through the _center, it is not a tangent
        if (perpendicular.equals(segment)) return null;

        // Is this perpendicular segment a _radius? Check length
        //if (!Utilities.doubleEquals(perpendicular.Length, this._radius)) return null;

        // Is the perpendicular a _radius? Check that the intersection of the segment and the perpendicular is on the circle
        Point intersection = segment.lineIntersection(perpendicular);
        if (!this.pointLiesOn(intersection)) return null;

        // The intersection between the perpendicular and the segment must be within the endpoints of the segment.
        return segment.pointLiesBetweenEndpoints(intersection) ? perpendicular : null;
    }

    //
    // Does the given segment pass through the circle so that it acts as a diameter (or contains a diameter)?
    //
    private boolean ContainsDiameter(Segment segment)
    {
        if (!segment.pointLiesBetweenEndpoints(this._center)) return false;

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
        if (segment.isVertical())
        {
            deltaX = 0;
            deltaY = distance;
        }
        else if (segment.isHorizontal())
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
        if (this.PointIsExterior(segment.getPoint1()) && this.pointLiesOn(segment.getPoint2())) return false;
        if (this.PointIsExterior(segment.getPoint2()) && this.pointLiesOn(segment.getPoint1())) return false;

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
        Segment perpendicular = segment.getPerpendicularThrough(this._center);

        // Is this perpendicular segment a _radius? If so, it's tangent, not a secant
        //if (Utilities.doubleEquals(perpendicular.Length, this._radius)) return false;

        // Is the perpendicular a _radius? Check if the intersection of the segment and the perpendicular is on the circle. If so, it's tangent
        Point intersection = segment.lineIntersection(perpendicular);
        if (this.pointLiesOn(intersection)) return false;

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

        chord = ConstructChord(segment, perpendicular.other(this._center), halfChordLength, figPoints);

        out.set(chord);

        return true;
    }

    //
    // Is this a direct _radius segment where one endpoint originates at the origin and extends outward?
    // Return the exact _radius.
    public Segment IsRadius(Segment segment, ArrayList<Point> figPoints)
    {
        // The segment must originate from the circle _center.
        if (!segment.has(this._center)) return null;

        // The segment must be at least as long as a _radius.
        if (!MathUtilities.doubleEquals(segment.length(), this._radius)) return null;

        Point non_centerPt = segment.other(this._center);

        // Check for a direct _radius.
        if (this.pointLiesOn(non_centerPt)) return segment;

        //
        // Check for an extended segment.
        //
        //                _radius
        //      _center    _____   circPt
        //
        // Find the exact coordinates of the 'circ' points.
        //
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.findIntersection(segment, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        Point figPoint = backend.utilities.list.Utilities.GetStructurally(figPoints, inter1);
        if (figPoint == null) figPoint = backend.utilities.list.Utilities.GetStructurally(figPoints, inter2);

        return new Segment(_center, figPoint);
    }

    //
    // Are the segment endpoints directly on the circle? 
    //
    private boolean IsChord(Segment segment)
    {
        return this.pointLiesOn(segment.getPoint1()) && this.pointLiesOn(segment.getPoint2());
    }

    //
    // Determine if the given point is on the circle via substitution into (x1 - x2)^2 + (y1 - y2)^2 = r^2
    //
    @Override
    public boolean pointLiesOn(Point pt) { return PointLiesOnDelegate.pointLiesOn(this, pt); }

    //
    // Determine if the given point is a point : the interioir of the circle: via substitution into (x1 - x2)^2 + (y1 - y2)^2 = r^2
    //
    public boolean PointIsInterior(Point pt)
    {
        return MathUtilities.lessThan(Point.calcDistance(this._center, pt), this._radius);
    }
    public boolean PointIsExterior(Point pt)
    {
        return MathUtilities.greaterThan(Point.calcDistance(this._center, pt), this._radius);
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
        this.findIntersection(thatCircle, out);
        Point inter1 = out.first();
        Point inter2 = out.second();

        // If the circles intersect at 0 points.
        if (inter1 == null) return false;

        // If the circles intersect at 1 point they are tangent
        if (inter2 == null) return false;

        // Create two radii, one for each circle; arbitrarily choose the first point (both work)
        Segment radiusThis = new Segment(this._center, inter1);
        Segment radiusThat = new Segment(this._center, inter1);

        return radiusThis.isLinePerpendicular(radiusThat);
    }

    //
    // Tangent circle have 1 intersection point
    //
    public Point AreTangent(Circle thatCircle)
    {
        // Find the intersection points
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.findIntersection(thatCircle, out);
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
            if (thatSegment.contains(_radius)) return true;
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
            if (key.contains(thatSegment))
            {
                Segment value = _secants.get(key);
                if (thatSegment.contains(value)) return value;
            }
        }

        return null;
    }


    public boolean IsCentral(Angle angle)
    {
        if (this._center.structurallyEquals(angle.getVertex()))
        {
            // The rays need to contain radii of the circle.
            if (this.ContainsRadiusWithin(angle.getRay1().asSegment()) &&
                this.ContainsRadiusWithin(angle.getRay2().asSegment()))
            {
                return true;
            }
        }
        return false;
    }

//    public static ArrayList<Circle> IsCentralAngle(Angle angle)
//    {
//        ArrayList<Circle> circles = new ArrayList<Circle>();
//
//        for (Circle circle : figureCircles)
//        {
//            if (circle.IsCentral(angle))
//            {
//                circles.add(circle);
//            }
//        }
//
//        return circles;
//    }

    public boolean IsInscribed(Angle angle)
    {
        // If the angle has vertex on the circle
        if (!this.pointLiesOn(angle.getVertex())) return false;

        // Do the angle rays form or contain chords? 
        // GetChord() will check if the segment is a chord, and if it is not, it will check if the segment is a secant containing a chord
        Segment chord1 = this.GetChord(angle.getRay1().asSegment());
        Segment chord2 = this.GetChord(angle.getRay2().asSegment());

        return chord1 != null && chord2 != null;
    }

//    public static ArrayList<Circle> IsInscribedAngle(Angle angle)
//    {
//        ArrayList<Circle> circles = new ArrayList<Circle>();
//
//        for (Circle circle : figureCircles)
//        {
//            if (circle.IsInscribed(angle)) circles.add(circle);
//        }
//
//        return circles;
//    }
//
//    public static ArrayList<Circle> GetChordCircles(Segment segment)
//    {
//        ArrayList<Circle> circles = new ArrayList<Circle>();
//
//        for (Circle circle : figureCircles)
//        {
//            if (circle.chords.contains(segment)) circles.add(circle);
//        }
//
//        return circles;
//    }
//
//    public static ArrayList<Circle> GetSecantCircles(Segment segment)
//    {
//        ArrayList<Circle> secCircles = new ArrayList<Circle>();
//
//        for (Circle circle : figureCircles)
//        {
//            if (circle._secants.containsKey(segment)) secCircles.add(circle);
//            if (circle.chords.contains(segment)) secCircles.add(circle);
//        }
//
//        return secCircles;
//    }

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
        return this.pointLiesOn(p1) && this.pointLiesOn(p2);
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
        for (Point thatPt : that.getPoints())
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
        return this.pointLiesOn(that.getPoint1()) && this.pointLiesOn(that.getPoint2());
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
}