package backend.ast.figure.components.triangles;

import java.util.ArrayList;
import java.util.List;

import backend.ast.GroundedClause;
import backend.utilities.translation.OutPair;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.polygon.Polygon;
import backend.ast.figure.delegates.SegmentDelegate;
import backend.ast.figure.delegates.TriangleDelegate;


public class Triangle extends Polygon
{
    /// <summary>
    /// Represents a triangle, which consists of 3 segments
    /// </summary>
    protected Point _point1;
    protected Point _point2;
    protected Point _point3;

    protected Segment _segmentA;
    protected Segment _segmentB;
    protected Segment _segmentC;

    protected Angle _angleA;
    protected Angle _angleB;
    protected Angle _angleC;

    //    protected boolean isRight;
    //    public boolean givenRight;
        protected boolean provenRight;
    //    protected Angle rightAngle;
    //    protected boolean isIsosceles;
        protected boolean provenIsosceles;
    //    protected boolean isEquilateral;
    //    protected boolean provenEquilateral;
    //    private ArrayList<Triangle> congruencePairs;
    //    public boolean WasDeducedCongruent(Triangle that) { return congruencePairs.contains(that); }
    //    private ArrayList<Triangle> similarPairs;
    //    public boolean WasDeducedSimilar(Triangle that) { return similarPairs.contains(that); }

    //    @Override
    //    public boolean strengthened() { return provenIsosceles || provenRight || provenEquilateral; }

    public Point getPoint1() { return _point1; }
    public Point getPoint2() { return _point2; }
    public Point getPoint3() { return _point3; }

    public Segment getSegmentA() { return _segmentA; }
    public Segment getSegmentB() { return _segmentB; }
    public Segment getSegmentC() { return _segmentC; }
    public Angle getAngleA() { return _angleA; }
    public Angle getAngleB() { return _angleB; }
    public Angle getAngleC() { return _angleC; }

    //    public boolean isRight() { return isRight; }
    //    public boolean provenRight() { return provenRight; }
    //    public Angle rightAngle() { return rightAngle; }
    //    public boolean isIsosceles() { return isIsosceles; }
    //    public boolean provenIsosceles() { return provenIsosceles; }
    //    public boolean isEquilateral() { return isEquilateral; }
    //    public boolean provenEquilateral() { return provenEquilateral; }
    //    private ArrayList<Triangle> congruencePairs() { return congruencePairs; }
    //    private ArrayList<Triangle> similarPairs() { return similarPairs; }

    public Triangle(Segment ... segs) { setTriangle(segs[0], segs[1], segs[2]); }

    public Triangle(Point ... pts) { setTriangle(pts[0], pts[1], pts[2]); }

    //public Triangle(List<Point> points) { setTriangle(points.get(0), points.get(1), points.get(2)); }

    public <T> Triangle(List<T> obj) 
    {
        if(obj.get(0) instanceof Point)
        {
            setTriangle((Point)obj.get(0), (Point)obj.get(1), (Point)obj.get(2));
        }

        if(obj.get(0) instanceof Segment)
        {
            setTriangle((Segment)obj.get(0), (Segment)obj.get(1), (Segment)obj.get(2));
            if (obj.size() != 3) ExceptionHandler.throwException( new IllegalArgumentException("Triangle constructed with " + obj.size() + " segments."));
        }
    }

    /*
     * @param a -- a point
     * @param b -- a point
     * @param c -- a point
     * Construct the three segments to create a triangle.
     */
    private void setTriangle(Point a, Point b, Point c) { setTriangle(new Segment(a, b), new Segment(b, c), new Segment(a, c)); }

    /*
     * @param a -- the segment opposite point a
     * @param b -- the segment opposite point b
     * @param c -- the segment opposite point c
     * Create a new triangle bounded by the 3 given segments. The set of points that define these segments should have only 3 distinct elements.
     */
    private void setTriangle(Segment a, Segment b, Segment c)
    {
        _segmentA = a;
        _segmentB = b;
        _segmentC = c;

        _point1 = _segmentA.getPoint1();
        _point2 = _segmentA.getPoint2();
        _point3 = _point1.equals(_segmentB.getPoint1()) || _point2.equals(_segmentB.getPoint1()) ? _segmentB.getPoint2() : _segmentB.getPoint1();

        _angleA = new Angle(_point1, _point2, _point3);
        _angleB = new Angle(_point2, _point3, _point1);
        _angleC = new Angle(_point3, _point1, _point2);

        //        isRight = isRightTriangle();
        //        provenRight = false;
        //        givenRight = false;
        //        isIsosceles = IsIsosceles();
        //        provenIsosceles = false;
        //        isEquilateral = IsEquilateral();
        //        provenEquilateral = false;

        //        congruencePairs = new ArrayList<Triangle>();
        //        similarPairs = new ArrayList<Triangle>();

        orderedSides = new ArrayList<Segment>();
        orderedSides.add(a);
        orderedSides.add(b);
        orderedSides.add(c);

        //        addSuperFigureToDependencies();
    }

    //    public Triangle(ArrayList<Point> pts) 
    //    {
    //        setTriangle(pts.get(0), pts.get(1), pts.get(2));
    //    }

    //
    //    protected void addSuperFigureToDependencies()
    //    {
    //        Utilities.AddUniqueStructurally(_segmentA.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_segmentB.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_segmentC.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_point1.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_point2.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_point3.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_angleA.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_angleB.getSuperFigures(), this);
    //        Utilities.AddUniqueStructurally(_angleC.getSuperFigures(), this);
    //    }
    //
        public void SetProvenToBeRight() { provenRight = true; }
        public void SetProvenToBeIsosceles() { provenIsosceles = true; } 
    //    public void SetProvenToBeEquilateral() { provenEquilateral = true; }

    //    //
    //    // Maintain a public repository of all triangles objects in the figure.
    //    //
    //    public static void Clear()
    //    {
    //        figureTriangles.clear();
    //    }
    //    public static ArrayList<Triangle> figureTriangles = new ArrayList<Triangle>();
    //    public static void Record(GroundedClause clause)
    //    {
    //        // Record uniquely? For right angles, etc?
    //        if (clause instanceof Triangle) figureTriangles.add((Triangle)clause);
    //    }
    //    public static Triangle GetFigureTriangle(Point pt1, Point pt2, Point pt3)
    //    {
    //        Triangle candTriangle = new Triangle(pt1, pt2, pt3);
    //
    //        // Search for exact segment first
    //        for (Triangle tri : figureTriangles)
    //        {
    //            if (tri.structurallyEquals(candTriangle)) return tri;
    //        }
    //
    //        return null;
    //    }

    /*
     * @param segment -- a segment not necessarily in the triangle, but contains the entire side of the triangle
     * @return the segment in the triangle which equates to the given segment;
     * if no segment normalizes, null is returned
     */
    public Segment normalize(Segment segment) { return TriangleDelegate.normalize(this, segment); }

    /*
     * @param angle -- an angle not necessarily in the triangle
     * @return the angle in the triangle which normalizes with the given angle;
     * if no angle normalizes, null is returned
     */
    public Angle normalize(Angle angle) { return TriangleDelegate.normalize(this, angle); }

    /*
     * @return the perimeter of the triangle (sum of the side lengths)
     */
    public double perimeter() { return TriangleDelegate.perimeter(this); }

    /*
     * @return the set of three altitudes
     */
    public List<Segment> altitudes() { return TriangleDelegate.altitudes(this); }

    /*
     * @param segment -- one of the sides of the triangle 
     * @return the altitude w.r.t @segment to be treated as a line
     * The algorithm is a slope-based analysis
     */
    public Segment altitude(Segment segment) { return TriangleDelegate.altitude(this, segment); }

    /*
     * @return the set of 3 altitudes w.r.t to this triangle
     */
    public List<Segment> altitudesOf() { return TriangleDelegate.altitudesOf(this); }

    /*
     * @param segment -- a segment
     * @return is the given segment an altitude of this triangle (coordinate-based computation).
     * http://www.mathopenref.com/coordcentroid.html
     */
    public boolean isAltitude(Segment segment) { return TriangleDelegate.isAltitude(this, segment); }

    /*
     * @return the orthocenter of the given triangle (intersection point of three altitudes)
     */
    public Point orthocenter() { return TriangleDelegate.orthocenter(this); }

    /*
     * @return the circumcenter of the given triangle (intersection point of perpendicular bisectors)
     * or the center of a circumscribed circle.
     */
    public Point circumcenter() { return TriangleDelegate.circumcenter(this); }

    /*
     * @return perpendicular bisector to the given segment
     * The returned segment should be treated as a line (infinite)
     */
    public Segment perpendicularBisector(Segment segment) { return TriangleDelegate.perpendicularBisector(this, segment); }

    /*
     * @return the incenter of the given triangle (intersection point of angle bisectors)
     * Coordinate-based calculation; can be used used for angle bisectors as well
     * Uses the formula: Coordinates of the incenter = ( (axa + bxb + cxc)/P , (aya + byb + cyc)/P ) where P is perimeter
     * https://www.easycalculation.com/analytical/learn-triangle-incenter.php
     */
    public Point incenter() { return TriangleDelegate.incenter(this); }

    /*
     * @param angle -- a valid angle of the triangle
     * @return the angle bisector ray originating from the shared vertex of the given @angle
     * The triangle delegate normalizes the given angle to the angle in the triangle before using the angle bisector.
     * Coordinate-based calculation which is based on the triangle incenter computation
     * Uses the formula: Coordinates of the incenter = ( (axa + bxb + cxc)/P , (aya + byb + cyc)/P ) where P is perimeter
     * https://www.easycalculation.com/analytical/learn-triangle-incenter.php
     */
    public Ray angleBisector(Angle angle) { return TriangleDelegate.angleBisector(this, angle); }

    /*
     * @return the centroid of this triangle (coordinate-based computation).
     * http://www.mathopenref.com/coordcentroid.html
     */
    public Point centroid() { return TriangleDelegate.centroid(this); }

    /*
     * @param segment -- side of the triangle
     * @return the median of this triangle w.r.t. to the given @segment (coordinate-based computation using the centroid).
     * http://www.mathopenref.com/coordcentroid.html
     */
    public Segment median(Segment segment) { return TriangleDelegate.median(this, segment); }

    /*
     * @return the medians of this triangle
     */
    public List<Segment> mediansOf() { return TriangleDelegate.mediansOf(this); }

    /*
     * @param segment -- side of the triangle
     * @return the median of this triangle w.r.t. to the given @segment (coordinate-based computation using the centroid).
     * http://www.mathopenref.com/coordcentroid.html
     */
    public boolean isMedian(Segment segment) { return TriangleDelegate.isMedian(this, segment); }

    /*
     * Determines if this is a right traingle. 
     * @return TRUE if this is a right triangle
     */
    public boolean isRight() { return TriangleDelegate.isRight(this); }

    /*
     * Determines if this is a isosceles triangle. 
     * @return TRUE if this is an isosceles triangle
     */
    public boolean isIsosceles() { return TriangleDelegate.isIsosceles(this); }

    /*
     * Determines if this is an equilateral triangle. 
     * @return TRUE if this is an equilateral triangle
     */
    public boolean isEquilateral() { return TriangleDelegate.isEquilateral(this); }

    /*
     * @param pt -- a point 
     * @return true if @pt is one of the vertices
     */
    public boolean has(Point p) { return _point1.equals(p) || _point2.equals(p) || _point3.equals(p); }

    /*
     * @param segment -- one of the sides of the triangle (unchecked) 
     * @return the point in the triangle opposite the given segment
     */
    public Point oppositePoint(Segment segment)
    {
        if (!segment.has(_point1)) return _point1;
        if (!segment.has(_point2)) return _point2;
        if (!segment.has(_point3)) return _point3;

        return null;
    }

    /*
     * @param p1 -- one of the vertices in the triangle (unchecked) 
     * @param p2 -- one of the vertices in the triangle (unchecked) 
     * @return the remaining point in the triangle
     */
    public Point otherPoint(Point p1, Point p2) { return oppositePoint(new Segment(p1, p2)); }


    /*
     * @param segment -- a segment 
     * @return the angle opposite segment @segment
     */
    public Angle oppositeAngle(Segment s)
    {
        Point oppVertex = this.oppositePoint(s);

        if (oppVertex.equals(_angleA.getVertex())) return _angleA;
        if (oppVertex.equals(_angleB.getVertex())) return _angleB;
        if (oppVertex.equals(_angleC.getVertex())) return _angleC;

        return null;
    }

    /*
     * @param angle -- one of the angles with vertex in the triangle
     * @return the angle opposite segment @segment
     */
    public Segment oppositeSide(Angle angle)
    {
        Point vertex = angle.getVertex();

        if (!_segmentA.has(vertex)) return _segmentA;
        if (!_segmentB.has(vertex)) return _segmentB;
        if (!_segmentC.has(vertex)) return _segmentC;

        return null;
    }

    /*
     * @param vertex -- a vertex in this triangle
     * @return the segment opposite the @vertex
     */
    public Segment oppositeSide(Point vertex)
    {
        if (!_segmentA.has(vertex)) return _segmentA;
        if (!_segmentB.has(vertex)) return _segmentB;
        if (!_segmentC.has(vertex)) return _segmentC;

        return null;
    }









    public void GetOtherAngles(Angle that, OutPair<Angle, Angle> out)
    {
        Angle outAng1 = null;
        Angle outAng2 = null;

        if (_angleA.structurallyEquals(that))
        {
            outAng1 = _angleB;
            outAng2 = _angleC;
        }
        else if (_angleB.structurallyEquals(that))
        {
            outAng1 = _angleA;
            outAng2 = _angleC;
        }
        else if (_angleC.structurallyEquals(that))
        {
            outAng1 = _angleA;
            outAng2 = _angleB;
        }

        out = new OutPair<Angle, Angle>(outAng1, outAng2);
    }

    public void GetOtherSides(Segment s, OutPair<Segment, Segment> out)
    {
        Segment outSeg1 = null;
        Segment outSeg2 = null;

        if (s.structurallyEquals(_segmentA))
        {
            outSeg1 = _segmentB;
            outSeg2 = _segmentC;
        }
        else if (s.structurallyEquals(_segmentB))
        {
            outSeg1 = _segmentA;
            outSeg2 = _segmentC;
        }
        else if (s.structurallyEquals(_segmentC))
        {
            outSeg1 = _segmentA;
            outSeg2 = _segmentB;
        }
        out = new OutPair<Segment, Segment>(outSeg1, outSeg2);
    }



    public Segment OtherSide(Angle a)
    {
        Point vertex = a.getVertex();

        if (!_segmentA.has(vertex)) return _segmentA;
        if (!_segmentB.has(vertex)) return _segmentB;
        if (!_segmentC.has(vertex)) return _segmentC;

        return null;
    }

    public Segment GetSegmentWithPointOnOrExtends(Point pt)
    {
        if (_segmentA.pointLiesOn(pt)) return _segmentA;
        if (_segmentB.pointLiesOn(pt)) return _segmentB;
        if (_segmentC.pointLiesOn(pt)) return _segmentC;

        return null;
    }

    public Segment GetSegmentWithPointDirectlyOn(Point pt)
    {
        if (Segment.Between(pt, _segmentA.getPoint1(), _segmentA.getPoint2())) return _segmentA;
        if (Segment.Between(pt, _segmentB.getPoint1(), _segmentB.getPoint2())) return _segmentB;
        if (Segment.Between(pt, _segmentC.getPoint1(), _segmentC.getPoint2())) return _segmentC;

        return null;
    }

    public void MakeIsosceles()
    {
        if (!isIsosceles())
        {
            //NC  Was Console.Write in source
            System.out.println("Deduced fact that this triangle is isosceles does not match empirical information for " + this.toString());
        }

        //        provenIsosceles = true;
    }

    public boolean LiesOn(Segment cs)
    {
        return _segmentA.isCollinearWith(cs) || _segmentB.isCollinearWith(cs) || _segmentC.isCollinearWith(cs);
    }

    // Does this triangle have this specific angle with these vertices
    private boolean HasThisSpecificAngle(Angle ca)
    {
        return (HasSegment(ca.getRay1().asSegment()) && HasSegment(ca.getRay2().asSegment())); // Could call sharedVertex
    }

    // Does the given angle correspond to any of the angles?
    public boolean ExtendsAnAngle(Angle ca)
    {
        return ExtendsSpecificAngle(ca) != null;
    }

    // Does the given angle correspond to any of the angles?
    public Angle ExtendsSpecificAngle(Angle ca)
    {
        if (_angleA.equates(ca)) return _angleA;
        if (_angleB.equates(ca)) return _angleB;
        if (_angleC.equates(ca)) return _angleC;

        return null;
    }

    // Does the given angle correspond to any of the angles?
    public Angle GetAngleWithVertex(Point pt)
    {
        if (_angleA.getVertex().equals(pt)) return _angleA;
        if (_angleB.getVertex().equals(pt)) return _angleB;
        if (_angleC.getVertex().equals(pt)) return _angleC;

        return null;
    }

    //
    // Check directly if this angle is in the triangle
    // Also check indirectly that the given angle is an extension (subangle) of this angle
    // That is, all the rays are shared and the vertex is shared, but the endpoint of rays may be different
    //
    public boolean HasAngle(Angle ca)
    {
        return HasThisSpecificAngle(ca) || ExtendsAnAngle(ca);
    }

    public Segment SharesSide(Triangle cs)
    {
        if (_segmentA.equals(cs._segmentA) || _segmentA.equals(cs._segmentB) || _segmentA.equals(cs._segmentC))
        {
            return _segmentA;
        }
        if (_segmentB.equals(cs._segmentA) || _segmentB.equals(cs._segmentB) || _segmentB.equals(cs._segmentC))
        {
            return _segmentB;
        }
        if (_segmentC.equals(cs._segmentA) || _segmentC.equals(cs._segmentB) || _segmentC.equals(cs._segmentC))
        {
            return _segmentC;
        }

        return null;
    }

    
//     Acquire the particular angle which belongs to this triangle (of a congruence)
    
        public Angle _angleBelongs(CongruentAngles ccas)
        {
            if (HasAngle(ccas.GetFirstAngle())) return ccas.GetFirstAngle();
            if (HasAngle(ccas.GetSecondAngle())) return ccas.GetSecondAngle();
            return null;
        }

    //
    // Acquire the particular angle which belongs to this triangle (of a congruence)
    //
    public Angle OtherAngle(Angle that_angle1, Angle that_angle2)
    {
        if (_angleA.equates(that_angle1) && _angleB.equates(that_angle2) || _angleA.equates(that_angle2) && _angleB.equates(that_angle1)) return _angleC;
        if (_angleB.equates(that_angle1) && _angleC.equates(that_angle2) || _angleB.equates(that_angle2) && _angleC.equates(that_angle1)) return _angleA;
        if (_angleA.equates(that_angle1) && _angleC.equates(that_angle2) || _angleA.equates(that_angle2) && _angleC.equates(that_angle1)) return _angleB;

        return null;
    }

    public boolean IsIncludedAngle(Segment s1, Segment s2, Angle a)
    {
        if (!HasSegment(s1) || !HasSegment(s2) && !HasAngle(a)) return false;

        // If the shared vertex between the segments is the vertex of this given angle, then
        // the angle is the included angle as desired
        return s1.sharedVertex(s2).equals(a.getVertex());
    }

//     Of the congruent pair, return the segment that applies to this triangle
        public Segment GetSegment(CongruentSegments ccss)
        {
            if (HasSegment(ccss.GetFirstSegment())) return ccss.GetFirstSegment();
            if (HasSegment(ccss.GetSecondSegment())) return ccss.GetSecondSegment();
    
            return null;
        }

    // Of the propportional pair, return the segment that applies to this triangle
    //    public Segment GetSegment(SegmentRatio prop)
    //    {
    //        if (HasSegment(prop.smallerSegment)) return prop.smallerSegment;
    //        if (HasSegment(prop.largerSegment)) return prop.largerSegment;
    //
    //        return null;
    //    }



    public Pair<Angle, Angle> GetAcuteAngles()
    {
        if (_angleA.getMeasure() >= 90) return new Pair<Angle,Angle>(_angleB, _angleC);
        if (_angleB.getMeasure() >= 90) return new Pair<Angle,Angle>(_angleA, _angleC);
        if (_angleC.getMeasure() >= 90) return new Pair<Angle,Angle>(_angleA, _angleB);

        return new Pair<Angle,Angle>(null, null);
    }

    //
    // Is this angle an 'extension' of the actual triangle angle? If so, acquire the normalized version of
    // the angle, using only the triangles vertices to represent the angle
    //
    public Angle NormalizeAngle(Angle extendedAngle)
    {
        return ExtendsSpecificAngle(extendedAngle);
    }

    //
    // Returns the longest side of the triangle; arbitary choice if equal and longest
    //
    public Segment longestSide()
    {
        if (_segmentA.length() > _segmentB.length())
        {
            if (_segmentA.length() > _segmentC.length())
            {
                return _segmentA;
            }
        }
        else if (_segmentB.length() > _segmentC.length())
        {
            return _segmentB;
        }

        return _segmentC;
    }

    //
    // return the hypotenuse if we know we have a right triangle
    //
    public Segment hypotenuse() { return isRight() ? longestSide() : null; }

    //
    // Two sides known, return the third side
    //
    public Segment OtherSide(Segment s1, Segment s2)
    {
        if (!HasSegment(s1) || !HasSegment(s2)) return null;
        if (!_segmentA.equals(s1) && !_segmentA.equals(s2)) return _segmentA;
        if (!_segmentB.equals(s1) && !_segmentB.equals(s2)) return _segmentB;
        if (!_segmentC.equals(s1) && !_segmentC.equals(s2)) return _segmentC;
        return null;
    }

    // 
    // Triangle(A, B, C) -> Segment(A, B), Segment(B, C), Segment(A, C),
    //                      Angle(A, B, C), Angle(B, C, A), Angle(C, A, B)
    //
    // RightTriangle(A, B, C) -> Segment(A, B), Segment(B, C), Segment(A, C), m\angle ABC = 90^o
    //
    //    private static final String INTRINSIC_NAME = "Intrinsic";
    //    private static Hypergraph.EdgeAnnotation intrinsicAnnotation = new Hypergraph.EdgeAnnotation(INTRINSIC_NAME, true);
    //
    //    public static ArrayList<GenericInstantiator.EdgeAggregator> Instantiate(GroundedClause c)
    //    {
    //        ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
    //
    //        Triangle tri = (Triangle)c;
    //        if (tri == null) return newGrounded;
    //
    //        // Generate the FOL for segments
    //        List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(tri);
    //        //tri._segmentA.SetJustification("Intrinsic");
    //        //tri._segmentB.SetJustification("Intrinsic");
    //        //tri._segmentC.SetJustification("Intrinsic");
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._segmentA, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._segmentB, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._segmentC, intrinsicAnnotation));
    //
    //        //tri._angleA.SetJustification("Intrinsic");
    //        //tri._angleB.SetJustification("Intrinsic");
    //        //tri._angleC.SetJustification("Intrinsic");
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._angleA, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._angleB, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri._angleC, intrinsicAnnotation));
    //
    //        // If this is a right triangle, generate the FOL equation
    //        if (tri.provenRight)
    //        {
    //            newGrounded.AddRange(Angle.Instantiate(tri, tri.rightAngle));
    //        }
    //
    //        return newGrounded;
    //    }

    //
    // Is the given angle directly exterior to this triangle?
    //
    // The triangle must share a side with the angle, the non-shared side must extend the adjacent side
    public boolean HasExteriorAngle(Angle extAngle)
    {
        // Disallow any angle in this triangle (since it technically satisfies the requirements)
        if (HasAngle(extAngle)) return false;

        // Determine which angle in the triangle has the same vertex as the input angle
        Angle triangleAngle = GetAngleWithVertex(extAngle.getVertex());

        if (triangleAngle == null) return false;

        // Acquire the ray that is shared between the angle and the triangle
        Ray sharedRay = triangleAngle.sharedRay(extAngle);

        if (sharedRay == null) return false;

        // Acquire the other side of the triangle
        Segment otherTriangleSegment = triangleAngle.OtherRay(sharedRay.asSegment());

        if (otherTriangleSegment == null) return false;

        // Acquire the ray that is not shared
        Ray exteriorSegment = extAngle.other(sharedRay);

        if (exteriorSegment == null) return false;

        //           DISALLOW                                     ALLOW
        //              /                                           /
        //             / \                                         / \
        //            /TRI\                                       /TRI\
        //           /-----\                                     /-----\
        //                 /                                            \
        //                /                                              \
        //               /                                                \
        return otherTriangleSegment.isCollinearWith(exteriorSegment.asSegment());
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
    public Pair<Segment, Segment> OtherSides(Segment candidate)
    {
        if (_segmentA.equals(candidate)) return new Pair<Segment,Segment>(_segmentB, _segmentC);
        if (_segmentB.equals(candidate)) return new Pair<Segment, Segment>(_segmentA, _segmentC);
        if (_segmentC.equals(candidate)) return new Pair<Segment, Segment>(_segmentA, _segmentB);

        return new Pair<Segment, Segment>(null, null);
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
    public Segment CoincidesWithASide(Segment candidate)
    {
        if (_segmentA.isCollinearWith(candidate)) return _segmentA;
        if (_segmentB.isCollinearWith(candidate)) return _segmentB;
        if (_segmentC.isCollinearWith(candidate)) return _segmentC;

        return null;
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
        public Segment DoesParallelCoincideWith(Parallel p)
        {
            if (CoincidesWithASide(p.getSegment1()) != null) return p.getSegment1();
            if (CoincidesWithASide(p.getSegment2()) != null) return p.getSegment2();
    
            return null;
        }

    //
    // Given a point on the triangle, return the two angles not at that vertex
    //
    public void AcquireRemoteAngles(Point givenVertex, OutPair<Angle, Angle> out)
    {
        Angle remote1 = null;
        Angle remote2 = null;

        if (!has(givenVertex)) return;

        if (_angleA.getVertex().equals(givenVertex))
        {
            remote1 = _angleB;
            remote2 = _angleC;
        }
        else if (_angleB.getVertex().equals(givenVertex))
        {
            remote1 = _angleA;
            remote2 = _angleC;
        }
        else
        {
            remote1 = _angleA;
            remote2 = _angleB;
        }
        out = new OutPair<Angle, Angle>(remote1, remote2);
    }

    /*
     * @param thatSegment -- an arbitrary segment
     * @return the vertex of this triangle in which thatSegment passes through (if it does);
     *  Technique uses coinciding method since two triangle sides and the given segment may pass through that single vertex
     */
    public Point getVertexOn(Segment thatSegment)
    {
        if (Segment.coincident(this._segmentA, this._segmentB, thatSegment) != null) return _segmentA.sharedVertex(_segmentB);
        if (Segment.coincident(this._segmentA, this._segmentC, thatSegment) != null) return _segmentA.sharedVertex(_segmentC);
        if (Segment.coincident(this._segmentB, this._segmentC, thatSegment) != null) return _segmentB.sharedVertex(_segmentC);

        return null;
    }

    //    //
    //    // Have we deduced a congrunence between this triangle and t already?
    //    //
    //    public boolean HasEstablishedCongruence(Triangle t)
    //    {
    //        return congruencePairs.contains(t);
    //    }
    //
    //    // Add to the list of congruent triangles
    //    public void AddCongruentTriangle(Triangle t)
    //    {
    //        congruencePairs.add(t);
    //    }

    //    //
    //    // Have we deduced a similarity between this triangle and t already?
    //    //
    //    public boolean HasEstablishedSimilarity(Triangle t)
    //    {
    //        return similarPairs.contains(t);
    //    }

    //    // Add to the list of similar triangles
    //    public void AddSimilarTriangle(Triangle t)
    //    {
    //        similarPairs.add(t);
    //    }
















    //
    //
    // Coordinate-Based Analyses: To be moved
    //
    //


    //
    // Is this triangle congruent to the given triangle in terms of the coordinatization from the UI?
    //
    public Pair<Triangle, Triangle> CoordinateCongruent(Triangle thatTriangle)
    {
        boolean[] marked = new boolean[3];
        ArrayList<Segment> thisSegments = this.orderedSides;
        ArrayList<Segment> thatSegments = thatTriangle.orderedSides;

        ArrayList<Segment> corrSegmentsThis = new ArrayList<Segment>();
        ArrayList<Segment> corrSegmentsThat = new ArrayList<Segment>();

        for (int thisS = 0; thisS < thisSegments.size(); thisS++)
        {
            boolean found = false;
            for (int thatS = 0; thatS < thatSegments.size(); thatS++)
            {
                if (!marked[thatS])
                {
                    if (thisSegments.get(thisS).CoordinateCongruent(thatSegments.get(thatS)))
                    {
                        corrSegmentsThat.add(thatSegments.get(thatS));
                        corrSegmentsThis.add(thisSegments.get(thisS));
                        marked[thatS] = true;
                        found = true;
                        break;
                    }
                }
            }
            if (!found) return new Pair<Triangle,Triangle>(null, null);
        }

        //
        // Find the exact corresponding points
        //
        ArrayList<Point> triThis = new ArrayList<Point>();
        ArrayList<Point> triThat = new ArrayList<Point>();

        for (int i = 0; i < 3; i++)
        {
            triThis.add(corrSegmentsThis.get(i).sharedVertex(corrSegmentsThis.get(i + 1 < 3 ? i + 1 : 0)));
            triThat.add(corrSegmentsThat.get(i).sharedVertex(corrSegmentsThat.get(i + 1 < 3 ? i + 1 : 0)));
        }

        return new Pair<Triangle, Triangle>(new Triangle(triThis), new Triangle(triThat));
    }

    //
    // Is this triangle similar to the given triangle in terms of the coordinatization from the UI?
    //
    public boolean CoordinateSimilar(Triangle thatTriangle)
    {
        ArrayList<Boolean> congruentAngle = new ArrayList<Boolean>();
        ArrayList<Angle> thisAngles = this.angles;
        ArrayList<Angle> thatAngles = thatTriangle.angles;

        for (int thisS = 0; thisS < thisAngles.size(); thisS++)
        {
            int thatS = 0;
            for (; thatS < thatAngles.size(); thatS++)
            {
                {
                    congruentAngle.add(thisS, true);
                    break;
                }
            }

            if (thatS == thatAngles.size()) return false;
        }

        Pair<Triangle, Triangle> corresponding = CoordinateCongruent(thatTriangle);
        return !congruentAngle.contains(false) && (corresponding.getKey() == null && corresponding.getValue() == null); // CTA: Congruence is stronger than Similarity
    }

    //
    // Can this triangle be strengthened to Isosceles, Equilateral, Right, or Right Isosceles?
    //
    public static ArrayList<Strengthened> canBeStrengthened(Triangle thatTriangle)
    {
        ArrayList<Strengthened> strengthened = new ArrayList<Strengthened>();

        //
        // Equilateral cannot be strengthened
        //
        if (thatTriangle instanceof EquilateralTriangle) return strengthened;

        if (thatTriangle instanceof IsoscelesTriangle)
        {
            //
            // Isosceles -> Equilateral
            //
            if (thatTriangle.isEquilateral())
            {
                strengthened.add(new Strengthened(thatTriangle, new EquilateralTriangle(thatTriangle)));
            }

            //
            // Isosceles -> Right Isosceles
            //
            if (thatTriangle.isRight())
            {
                strengthened.add(new Strengthened(thatTriangle, new RightTriangle(thatTriangle)));
            }

            return strengthened;
        }

        //
        // Scalene -> Isosceles
        //
        if (thatTriangle.isIsosceles())
        {
            strengthened.add(new Strengthened(thatTriangle, new IsoscelesTriangle(thatTriangle)));
        }

        //
        // Scalene -> Equilateral
        //
        if (thatTriangle.isEquilateral())
        {
            strengthened.add(new Strengthened(thatTriangle, new EquilateralTriangle(thatTriangle)));
        }

        //
        // Scalene -> Right
        //
        if (!(thatTriangle instanceof RightTriangle))
        {
            if (thatTriangle.isRight())
            {
                strengthened.add(new Strengthened(thatTriangle, new RightTriangle(thatTriangle)));
            }
        }

        return strengthened;
    }






    //
    //    @Override
    //    public boolean canBeStrengthenedTo(GroundedClause gc)
    //    {
    //        if(gc == null) return false;
    //        if(!(gc instanceof Triangle)) return false;
    //        Triangle tri = (Triangle)gc;
    //
    //        //if (gc is Strengthened)
    //        //{
    //        //    return this.StructurallyEquals((gc as Strengthened).original);
    //        //}
    //
    //        // Handles isosceles, right, or equilateral
    //        if (!this.structurallyEquals(gc)) return false;
    //
    //        // Ensure we know the original has been 'proven' (given) to be a particular type of triangle
    //        if (tri.provenIsosceles) this.provenIsosceles = true;
    //        if (tri.provenEquilateral) this.provenEquilateral = true;
    //        if (tri.provenRight) this.provenRight = true;
    //
    //        return true;
    //    }


    //    // Returns the exact correspondence between the triangles; <this, that>
    //    public Dictionary<Point, Point> PointsCorrespond(Triangle thatTriangle)
    //    {
    //        if (!this.StructurallyEquals(thatTriangle)) return null;
    //
    //        List<Point> thatTrianglePts = thatTriangle.points;
    //        List<Point> thisTrianglePts = this.points;
    //
    //        // Find the index of the first point (in this Triangle) 
    //        int i = 0;
    //        for (; i < 3; i++)
    //        {
    //            if (thisTrianglePts.get(0).StructurallyEquals(thatTrianglePts.get(i))) break;
    //        }
    //
    //        // Sanity check; something bad happened
    //        if (i == 3) return null;
    //
    //        Dictionary<Point, Point> correspondence = new Dictionary<Point, Point>();
    //        for (int j = 0; j < 3; j++)
    //        {
    //            if (!thisTrianglePts.get(j).StructurallyEquals(thatTrianglePts.get((j + i) % 3))) return null;
    //            correspondence.Add(thisTrianglePts.get(j), thatTrianglePts.get((j + i) % 3));
    //        }
    //
    //        return correspondence;
    //    }




    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Triangle)) return false;
        Triangle thatTriangle = (Triangle)obj;

        return thatTriangle.has(this._point1) && thatTriangle.has(this._point2) && thatTriangle.has(this._point3);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(this instanceof IsoscelesTriangle)) return false;
        // An isosceles triangle is not the same as a scalene triangle object even if the points are the same.
        IsoscelesTriangle isoTriangle = (IsoscelesTriangle)obj;


        // An isosceles triangle is not the same as a scalene triangle object even if the points are the same.
        if (!(this instanceof IsoscelesTriangle)) return false; //NC Should this check if its Equilateral?
        EquilateralTriangle equiTriangle = (EquilateralTriangle)obj;

        if (!(this instanceof Triangle)) return false;
        Triangle triangle = (Triangle)obj ;


        //            // Is this a strengthened version?
        //            if (triangle.provenIsosceles != this.provenIsosceles) return false;
        //    
        //            // Is this a strenghtened version?
        //            if (triangle.provenRight != this.provenRight) return false;

        return structurallyEquals(obj) && super.equals(obj);
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        //        if (provenEquilateral)
        //        {
        //            str.append("Equilateral ");
        //        }
        //        else if (provenIsosceles)
        //        {
        //            str.append("Isosceles ");
        //        }
        //
        //        if (provenRight)
        //        {
        //            str.append("Right ");
        //        }
        str.append("Triangle(" + _point1.toString() + ", " + _point2.toString() + ", " + _point3.toString() + ") ");
        return str.toString();
    }

    @Override
    public String toPrettyString()
    {
        StringBuilder str = new StringBuilder();

        //        if (provenEquilateral)
        //        {
        //            str.append("Equilateral ");
        //        }
        //        else if (provenIsosceles)
        //        {
        //            str.append("Isosceles ");
        //        }
        //
        //        if (provenRight)
        //        {
        //            str.append("Right ");
        //        }
        str.append("Triangle(" + _point1.toPrettyString() + ", " + _point2.toPrettyString() + ", " + _point3.toPrettyString() + ") ");
        return str.toString();
    }

    //    @Override
    //    public String CheapPrettyString()
    //    {
    //        StringBuilder str = new StringBuilder();
    //
    //        if (provenEquilateral || this instanceof EquilateralTriangle)
    //        {
    //            str.append("Eq");
    //        }
    //        else if (provenIsosceles || this instanceof IsoscelesTriangle)
    //        {
    //            str.append("Iso");
    //        }
    //
    //        if (provenRight || this instanceof RightTriangle)
    //        {
    //            str.append("Right");
    //        }
    //
    //        str.append("Tri(" + _point1.CheapPrettyString() + _point2.CheapPrettyString() + _point3.CheapPrettyString() + ")");
    //
    //        return str.toString();
}