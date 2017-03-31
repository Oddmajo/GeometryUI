/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @author Nick Celiberti
 */
package backend.ast.figure.components.angles;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.Segment;
import backend.ast.figure.delegates.AngleDelegate;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class Angle extends Figure
{
    protected Point A; 
    protected Point B; 
    protected Point C; 
    public Point getA() { return A; }
    public Point getB() { return B; }
    public Point getC() { return C; }

    public Point getVertex() { return B; }
    
    protected Ray _ray1; 
    protected Ray _ray2; 

    public Ray getRay1() { return _ray1; }
    public Ray getRay2() { return _ray2; }
    
    protected double measure; 
    public double getMeasure() { return measure;}
   
    /**
     * Create a new angle.
     * @param a A point defining the angle.
     * @param b A point defining the angle. This is the vertex point.
     * @param c A point defining the angle.
     */
    public Angle(Point a, Point b, Point c) { initAngle(a, b, c); }
    public Angle(List<Point> pts)
    {
        if (pts.size() != 3)
        {
            ExceptionHandler.throwException( new IllegalArgumentException("Angle constructed with only " + pts.size() + " vertices."));
        }
        initAngle(pts.get(0), pts.get(1), pts.get(2));
    }
    public Angle(Segment ray1, Segment ray2)
    {
        Point vertex = ray1.sharedVertex(ray2);

        if (vertex == null) ExceptionHandler.throwException( new IllegalArgumentException("Rays do not share a vertex: " + ray1 + " " + ray2));

        initAngle(ray1.other(vertex), vertex, ray2.other(vertex));
    }
    public Angle(Ray _ray1, Ray _ray2)
    {
        if (!_ray1.getOrigin().equals(_ray2.getOrigin()))
        {
            ExceptionHandler.throwException( new IllegalArgumentException("Rays do not share same vertex: " + _ray1 + " " + _ray2));
        }

        initAngle(_ray1.getNonOrigin(), _ray1.getOrigin(), _ray2.getNonOrigin());
    }

    /**
     * Common initialization routine for angles
     * @param a A point defining the angle.
     * @param b A point defining the angle. This is the vertex point.
     * @param c A point defining the angle.
     */
    private void initAngle(Point a, Point b, Point c)
    {
        if (a.structurallyEquals(b) || b.structurallyEquals(c) || a.structurallyEquals(c))
        {
            ExceptionHandler.throwException(new IllegalArgumentException("Angle constructed with redundant vertices."));
        }
        this.A = a;
        this.B = b;
        this.C = c;
        
        _ray1 = new Ray(b, a);
        _ray2 = new Ray(b, c);

        this.measure = Math.toDegrees(findAngle(A, B, C));

        if (measure <= 0)
        {
            ExceptionHandler.throwException(new ArgumentException("Measure of " + this.toString() + " is ZERO"));
        }
    }
    /**
     * Find the measure of the angle (in radians) specified by the three points.
     * @param a A point defining the angle.
     * @param b A point defining the angle. This is the point the angle is actually at.
     * @param c A point defining the angle.
     * @return The measure of the angle (in radians) specified by the three points.
     * Uses Law of Cosines to compute angle
     */
    public static double findAngle(Point a, Point b, Point c)
    {
        double v1x = a.getX() - b.getX();
        double v1y = a.getY() - b.getY();
        double v2x = c.getX() - b.getX();
        double v2y = c.getY() - b.getY();
        double dotProd = v1x * v2x + v1y * v2y;
        double cosAngle = dotProd / (Point.calcDistance(a, b) * Point.calcDistance(b, c));

        // Avoid minor calculation issues and retarget the given value to specific angles. 
        // 0 or 180 degrees
        if (backend.utilities.math.MathUtilities.doubleEquals(Math.abs(cosAngle), 1) || backend.utilities.math.MathUtilities.doubleEquals(Math.abs(cosAngle), -1))
        {
            cosAngle = cosAngle < 0 ? -1 : 1;
        }

        // 90 degrees
        if (backend.utilities.math.MathUtilities.doubleEquals(cosAngle, 0)) cosAngle = 0;

        return Math.acos(cosAngle);
    }

    /*
     * Assume that a given angle is defined by 3 points indicating an origin point for two rays.
     * Now, treat those points as a triangle. We can now compute the incenter of the angle (as a triangle)
     * @param angle -- a valid angle of the triangle
     * @return the angle bisector ray originating from the shared vertex of the given @angle
     * The triangle delegate normalizes the given angle to the angle in the triangle before using the angle bisector.
     * Coordinate-based calculation which is based on the triangle incenter computation
     * Uses the formula: Coordinates of the incenter = ( (axa + bxb + cxc)/P , (aya + byb + cyc)/P ) where P is perimeter
     * https://www.easycalculation.com/analytical/learn-triangle-incenter.php
     */
    public Ray angleBisector() { return AngleDelegate.angleBisector(this); }
    
    /*
     * Does the given angle normalize with this angle? (Share same origin and overlays it)
     * @return true / false whether it normalizes or not.
     */
    public boolean normalizesWith(Angle angle) { return AngleDelegate.normalizesWith(this, angle); }

    /*
     * @param ray -- a ray
     * @return true / false whether the @ray overlays one of this angle's rays 
     */
    public boolean overlays(Ray ray) { return _ray1.overlays(ray) || _ray2.overlays(ray); }

    /*
     * Is the given angle the same as this angle?
     * That is, the vertex is the same and the rays overlap (not necessarily with the same endpoints)
     * Can't just be collinear, must be collinear and on same side of an angle
     *           ^
     *          /
     *         ^
     *        /
     *       /
     *      /  Equates
     *     ---------------->------------------------->
     * @param that -- an angle
     * @return true if the angles equate
     */
    public boolean equates(Angle that) { return AngleDelegate.equates(this, that); }
    
    /*
     * @param angle -- an angle
     * @return single shared ray between two angles 
     * null if there is no shared ray OR if the two angles equate
     */
    public Ray sharedRay(Angle angle)
    {
        if (this.equates(angle)) return null;
        
        if (_ray1.overlays(angle._ray1) || _ray1.overlays(angle._ray2)) return _ray1;

        if (_ray2.overlays(angle._ray1) || _ray2.overlays(angle._ray2)) return _ray2;

        return null;
    }

    /*
     * @param angle -- an angle
     * @return a point if the two angles share the same vertex (null is not shared)
     */
    public Point equalVertices(Angle angle) { return getVertex().equals(angle.getVertex()) ? getVertex() : null; }
    
    /*
     * @param that -- an angle
     * @return if these two angle have the same measure (within epsilon)
     */
    public boolean equalMeasure(Angle that) { return MathUtilities.doubleEquals(this.measure, that.measure); }    
    
    
    
//    /**
//     * Converts radians into degrees.
//     * @param radians An angle in radians.
//     * @return An angle in degrees
//     */
//    public static double toDegrees(double radians)
//    {
//        return radians * 180 / Math.PI;
//    }
//
//    /**
//     * Converts degrees into radians
//     * @param degrees An angle in degrees
//     * @return An angle in radians
//     */
//    public static double toRadians(double degrees)
//    {
//        return degrees * Math.PI / 180;
//    }







    //Checking for equality of angles WITHOUT considering possible overlay of rays (i.e. two angles will be considered NOT equal
    //if they contain rays that coincide but are not equivalent)
    public boolean equalRays(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj;

        // Measures better be the same.
        if (!backend.utilities.math.MathUtilities.doubleEquals(this.measure, angle.measure)) return false;

        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    }

    public Point SameVertex(Angle ang)
    {
        return getVertex().equals(ang.getVertex()) ? getVertex() : null;
    }

    public Segment SharesOneRayAndHasSameVertex(Angle ang)
    {
        if (SameVertex(ang) == null) return null;

        return sharedRay(ang).asSegment();
    }


    // Return the shared angle in both congruences
    public Segment IsAdjacentTo(Angle thatAngle)
    {
        if (thatAngle.IsOnInterior(this)) return null;
        if (this.IsOnInterior(thatAngle)) return null;

        //Segment shared =  SharesOneRayAndHasSameVertex(thatAngle);
        //if (shared == null) return null;

        //// Is this a scenario where one angle encompasses completely the other angle?
        //Segment otherThat = thatAngle.OtherRayEquates(shared);
        //Angle tempAngle = new Angle(shared.OtherPoint(getVertex()), getVertex(), this.OtherRayEquates(shared).OtherPoint(getVertex()));

        //if (tempAngle.IsOnInterior(otherThat.OtherPoint(getVertex())) return null; 

        return SharesOneRayAndHasSameVertex(thatAngle);
    }

    //
    // Is this point in the interior of the angle?
    //
    public boolean IsOnInterior(Point pt)
    {
        //     |
        //     |
        //  x  |_____
        // Is the point on either ray such that it is outside the angle? (x in the image above)
        if (_ray1.pointLiesOn(pt))
        {
            // Point between the endpoints of the ray.
            if (Segment.Between(pt, getVertex(), _ray1.getNonOrigin())) return true;
            // Point is on the ray, but extended in the right direction.
            return Segment.Between(_ray1.getNonOrigin(), getVertex(), pt);
        }
        if (_ray2.pointLiesOn(pt))
        {
            // Point between the endpoints of the ray.
            if (Segment.Between(pt, getVertex(), _ray2.getNonOrigin())) return true;
            // Point is on the ray, but extended in the right direction.
            return Segment.Between(_ray2.getNonOrigin(), getVertex(), pt);
        }

        Angle newAngle1 = new Angle(A, getVertex(), pt);
        Angle newAngle2 = new Angle(C, getVertex(), pt);

        // This is an angle addition scenario, BUT not with these two angles; that is, one is contained in the other.
        if (MathUtilities.doubleEquals(newAngle1.measure + newAngle2.measure, this.measure)) return true;

        return newAngle1.measure + newAngle2.measure <= this.measure;
    }

    //
    // Is this point in the interior of the angle?
    //
    public boolean IsOnInteriorExplicitly(Point pt)
    {
        if (_ray1.pointLiesOn(pt)) return false;
        if (_ray2.pointLiesOn(pt)) return false;

        Angle newAngle1 = new Angle(A, getVertex(), pt);
        Angle newAngle2 = new Angle(C, getVertex(), pt);

        // This is an angle addition scenario, BUT not with these two angles; that is, one is contained in the other.
        if (MathUtilities.doubleEquals(newAngle1.measure + newAngle2.measure, this.measure)) return true;

        return newAngle1.measure + newAngle2.measure <= this.measure;
    }

    //
    // Is this angle on the interior of the other?
    //
    public boolean IsOnInterior(Angle thatAngle)
    {
        if (this.measure < thatAngle.measure) return false;

        return this.IsOnInterior(thatAngle.A) && this.IsOnInterior(thatAngle.B) && this.IsOnInterior(thatAngle.C);
    }

    public Point OtherPoint(Segment seg)
    {
        if (seg.has(A) && seg.has(B)) return C;
        if (seg.has(A) && seg.has(C)) return B;
        if (seg.has(B) && seg.has(C)) return A;

        if (seg.pointLiesOn(A) && seg.pointLiesOn(B)) return C;
        if (seg.pointLiesOn(A) && seg.pointLiesOn(C)) return B;
        if (seg.pointLiesOn(B) && seg.pointLiesOn(C)) return A;

        return null;
    }

    //
    // Given one ray of the angle, return the other ray
    //
    public Segment OtherRay(Segment seg)
    {
        if (_ray1.asSegment().equals(seg)) return _ray2.asSegment();
        if (_ray2.asSegment().equals(seg)) return _ray1.asSegment();

        return null;
    }
    
    //
    // Given one ray of the angle, return the other ray
    //
    public Ray other(Ray ray)
    {
        if (_ray1.equates(ray)) return _ray2;
        if (_ray2.equates(ray)) return _ray1;

        return null;
    }


    //
    // Do these segments overlay this angle?
    //
    public boolean IsIncludedAngle(Segment seg1, Segment seg2)
    {
        // Do not allow the same segment.
        if (seg1.structurallyEquals(seg2)) return false;

        // Check direct inclusion
        if (seg1.equals(_ray1) && seg2.equals(_ray2) || seg1.equals(_ray2) && seg2.equals(_ray1)) return true;

        // Check overlaying angle
        Point shared = seg1.sharedVertex(seg2);

        if (shared == null) return false;

        Angle thatAngle = new Angle(seg1.other(shared), shared, seg2.other(shared));

        return this.equates(thatAngle);
    }

    private static final int[] VALID_CONCRETE_SPECIAL_ANGLES = { 0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330 }; // 15, 22.5, ...
    
    /**
     * 
     * @param measure -- an angle measure
     * @return if the double is equal to one of the special angles stated in VALID_CONCRETE_SPECIAL_ANGLES.
     */
    public static boolean isSpecialAngle(double measure)
    {
        // Do we even have an integer given to us?
        if (!MathUtilities.doubleEquals((int)measure, measure)) return false;
        
        // Simple check; all special angles are multiples of 15
        if ((int)measure % 15 != 0) return false;
        
        for (int d : VALID_CONCRETE_SPECIAL_ANGLES)
        {
            if (MathUtilities.GCD((int)measure, d) == d) return true;
        }

        return false;
    }



    // Does this angle lie between the two lines? This is mainly for a parallelism check
    public boolean OnInteriorOf(Intersection inter1, Intersection inter2)
    {
        Intersection angleBelongs = null;
        Intersection angleDoesNotBelong = null;

        // Determine the intersection to which the angle belongs
        if (inter1.InducesNonStraightAngle(this))
        {
            angleBelongs = inter1;
            angleDoesNotBelong = inter2;
        }
        else if (inter2.InducesNonStraightAngle(this))
        {
            angleBelongs = inter2;
            angleDoesNotBelong = inter1;
        }

        if (angleBelongs == null || angleDoesNotBelong == null) return false;

        // Make the transversal out of the points of intersection
        Segment transversal = new Segment(angleBelongs.getIntersect(), angleDoesNotBelong.getIntersect());
        Segment angleRayOnTraversal = this._ray1.IsCollinearWith(transversal) ? _ray1.asSegment() : _ray2.asSegment();

        // Is the endpoint of the angle (on the transversal) between the two intersection points?
        // Or, is that same endpoint on the far end beyond the other line: the other intersection point lies between the other points
        return Segment.Between(angleRayOnTraversal.other(this.getVertex()), angleBelongs.getIntersect(), angleDoesNotBelong.getIntersect()) ||
                Segment.Between(angleDoesNotBelong.getIntersect(), angleBelongs.getIntersect(), angleRayOnTraversal.other(this.getVertex())); 
    }

//    public static ArrayList<GenericInstantiator.EdgeAggregator> Instantiate(GroundedClause pred, GroundedClause c)
//    {
//        ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
//
//        if (c instanceof Angle) return newGrounded;
//
//        //Angle angle = c as Angle;
//
//        //if (IsSpecialAngle(angle.measure))
//        //{
//        //    GeometricAngleEquation angEq = new GeometricAngleEquation(angle, new NumericValue((int)angle.measure), "Given:tbd");
//        //    List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(pred);
//        //    newClauses.Add(new EdgeAggregator(antecedent, angEq));
//        //}
//
//        return newGrounded;
//    }

//    //
//    // Maintain a public repository of all angle objects in the figure
//    //
//    public static void Clear()
//    {
//        figureAngles.clear();
//        candidateTriangles.clear();
//        knownSharedAngles.clear();
//    }
//    public static ArrayList<Angle> figureAngles = new ArrayList<Angle>();
//    public static void Record(GroundedClause clause)
//    {
//        // Record uniquely? For right angles, etc?
//        if (clause instanceof Angle) figureAngles.add((Angle)clause);
//    }
//    public static Angle AcquireFigureAngle(Angle thatAngle)
//    {
//        for (Angle angle : figureAngles)
//        {
//            if (angle.Equates(thatAngle)) return angle;
//        }
//        return null;
//    }

    //
    // Each angle is congruent to itself; only generate if both rays are shared
    //
//    private static ArrayList<Triangle> candidateTriangles = new ArrayList<Triangle>();
//    private static ArrayList<Angle> knownSharedAngles = new ArrayList<Angle>();
//    public static ArrayList<GenericInstantiator.EdgeAggregator> InstantiateReflexiveAngles(GroundedClause clause)
//    {
//        ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
//
//        Triangle newTriangle = (Triangle)clause;
//        if (newTriangle == null) return newGrounded;
//
//        //
//        // Compare all angles in this new triangle to all the angles in the old triangles
//        //
//        for (Triangle oldTriangle : candidateTriangles)
//        {
//            if (newTriangle.HasAngle(oldTriangle.AngleA))
//            {
//                GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleA);
//                if (newClause != null) newGrounded.Add(newClause);
//            }
//
//            if (newTriangle.HasAngle(oldTriangle.AngleB))
//            {
//                GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleB);
//                if (newClause != null) newGrounded.Add(newClause);
//            }
//
//            if (newTriangle.HasAngle(oldTriangle.AngleC))
//            {
//                GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleC);
//                if (newClause != null) newGrounded.Add(newClause);
//            }
//        }
//
//        candidateTriangles.add(newTriangle);
//
//        return newGrounded;
//    }

    //
    // Generate the actual angle congruence
    //
    private static final String REFLEXIVE_ANGLE_NAME = "Reflexive Angles";
//    private static Hypergraph.EdgeAnnotation reflexAnnotation = new Hypergraph.EdgeAnnotation(REFLEXIVE_ANGLE_NAME, EngineUIBridge.JustificationSwitch.REFLEXIVE);

//    public static GenericInstantiator.EdgeAggregator GenerateAngleCongruence(Triangle tri, Angle angle)
//    {
//        //
//        // If we have already generated a reflexive congruence, avoid regenerating
//        //
//        for (Angle oldSharedAngle : knownSharedAngles)
//        {
//            if (oldSharedAngle.Equates(angle)) return null;
//        }
//
//        // Generate
//        GeometricCongruentAngles gcas = new GeometricCongruentAngles(angle, angle);
//
//        // This is an 'obvious' notion so it should be intrinsic to any figure
//        gcas.MakeIntrinsic();
//
//        return new GenericInstantiator.EdgeAggregator(backend.utilities.list.Utilities.makeList(Angle.AcquireFigureAngle(angle)), gcas, reflexAnnotation);
//    }

    public boolean IsComplementaryTo(Angle thatAngle)
    {
        return MathUtilities.doubleEquals(this.measure + thatAngle.measure, 90);
    }

    public boolean IsSupplementaryTo(Angle thatAngle)
    {
        return MathUtilities.doubleEquals(this.measure + thatAngle.measure, 180);
    }

    public boolean IsStraightAngle() { return MathUtilities.doubleEquals(measure, 180); }

    // Checking for structural equality (is it the same segment) excluding the multiplier
    // This is either a direct comparison of the angle based on vertices or 
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj;

        // Measures better be the same.
        if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;

        if (this.equates(angle)) return true;

        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    }

    //Checking for equality of angles WITHOUT considering possible overlay of rays (i.e. two angles will be considered NOT equal
    //if they contain rays that coincide but are not equivalent)
    public boolean EqualRays(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj ;

        // Measures better be the same.
       if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;

        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    }



    public boolean HasPoint(Point p)
    {
        if (A.equals(p)) return true;
        if (B.equals(p)) return true;
        if (C.equals(p)) return true;

        return false;
    }

    
    /**
     * This needs to be checked for correctness
     * @param seg
     * @return true if the segment is contained in the angle
     * @author Drew Whitmire
     */
    public boolean HasSegment(Segment seg)
    {
        // create ray from seg to use overlays() method
        Ray segment = new Ray(seg.getPoint1(), seg.getPoint2());
        
        return _ray1.overlays(segment) || _ray2.overlays(segment);
    }

    // CTA: Be careful with equality; this is object-based equality
    // If we check for angle measure equality that is distinct.
    // If we check to see that a different set of remote vertices describes this angle, that is distinct.
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle that = (Angle)obj;

        // Measures must be the same.
        if (!MathUtilities.doubleEquals(this.measure, that.measure)) return false;

        // The vertices need to be the same.
        if (!this.getVertex().structurallyEquals(that.getVertex())) return false;
        
        // The other points need to be the same
        if (this.A.structurallyEquals(that.A) && this.C.structurallyEquals(that.C)) return true;

        return this.A.structurallyEquals(that.C) && this.C.structurallyEquals(that.A);
    }

//    @Override
//    public boolean CanBeStrengthenedTo(GroundedClause gc)
//    {
//        if (gc == null) return false;
//        if (!(gc instanceof RightAngle)) return false;
//        RightAngle ra = (RightAngle)gc ;
//
//        if (ra == null) return false;
//
//        return this.StructurallyEquals(ra);
//    }

    //
    // Is this angle congruent to the given angle in terms of the coordinatization from the UI?
    //
    public boolean CoordinateCongruent(Angle a)
    {
        return MathUtilities.doubleEquals(a.measure, this.measure);
    }

    public boolean CoordinateAngleBisector(Segment thatSegment)
    {
        if (!thatSegment.pointLiesBetweenEndpoints(this.getVertex())) return false;

        if (thatSegment.isCollinearWith(this._ray1.asSegment()) || thatSegment.isCollinearWith(this._ray2.asSegment())) return false;

        Point interiorPoint = this.IsOnInteriorExplicitly(thatSegment.getPoint1()) ? thatSegment.getPoint1() : thatSegment.getPoint2();
        if (!this.IsOnInteriorExplicitly(interiorPoint)) return false;

        Angle angle1 = new Angle(A, getVertex(), interiorPoint);
        Angle angle2 = new Angle(C, getVertex(), interiorPoint);
        
        return MathUtilities.doubleEquals(angle1.measure, angle2.measure);
    }

    //
    // Is this angle proportional to the given segment in terms of the coordinatization from the UI?
    // We should not report proportional if the ratio between segments is 1
    //
    public backend.utilities.Pair<Integer, Integer> CoordinateProportional(Angle a)
    {
        return MathUtilities.RationalRatio(a.measure, this.measure);
    }
    
    // Make a deep copy of this object
    @Override
    public GroundedClause deepCopy()
    {
        Angle other = null;
        try
        {
            other = (Angle)(this.clone());
            other.A = (Point)this.A.DeepCopy();
            other.B = (Point)this.B.DeepCopy();
            other.C = (Point)this.C.DeepCopy();
            other._ray1 = (Ray)this._ray1.deepCopy();
            other._ray2 = (Ray)this._ray2.deepCopy();
        }
        catch (CloneNotSupportedException e)
        {
            ExceptionHandler.throwException(e);
        }
 
        return other;
    }
    
    @Override
    public String toString()
    {
        return "Angle( m" + A.name + B.name + C.name + " = " + String.format("%1$.3f", measure) + ")";
    }
    
    @Override
    public String toPrettyString()
    {
        return "Angle " + A.name + B.name + C.name;
    }
}
