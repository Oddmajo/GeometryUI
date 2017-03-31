package backend.ast.figure.delegates;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Ray;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

public class AngleDelegate extends FigureDelegate
{
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
    public static Ray angleBisector(Angle angle)
    {
        // Compute the incenter
        Triangle tri = new Triangle(angle.getA(), angle.getB(), angle.getC());
        Point incenter = tri.incenter();

        // The angle bisector passes through the angle's vertex toward the incenter
        return new Ray(angle.getVertex(), incenter);
    }

    /*
     * Does the given angle normalize with this angle? (Share same origin and overlays it)
     * @return true / false whether it normalizes or not.
     */
    public static boolean normalizesWith(Angle thisAngle, Angle thatAngle)
    {
        // Must share the same vertex
        if (!thisAngle.getVertex().equals(thatAngle.getVertex())) return false;

        // Error check to verify the 'thatAngle' rays are distinct
        if (thatAngle.getRay1().overlays(thatAngle.getRay2()))
        {
            ExceptionHandler.throwException("Given angle is not valid.");
        }
        
        // Rays must overlay
        return thisAngle.overlays(thatAngle.getRay1()) &&
               thisAngle.overlays(thatAngle.getRay2());
    }

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
     * @param angle -- an angle
     * @return true if the angles equate
     */
    public static boolean equates(Angle thisA, Angle that)
    {
        // Vertices must equate
        if (!thisA.getVertex().equals(that.getVertex())) return false;

        //
        // Rays must originate at the vertex and emanate outward (2 cases to check)
        //
        if (thisA.getRay1().overlays(that.getRay1()) && thisA.getRay2().overlays(that.getRay2())) return true;
        
        return thisA.getRay2().overlays(that.getRay1()) && thisA.getRay1().overlays(that.getRay2());
    }
    
    /*
     * @param thisA -- an angle
     * @param that -- an angle
     * @return single shared ray between two angles 
     * null if there is no shared ray OR if the two angles equate
     */
    public static Ray sharedRay(Angle thisA, Angle that)
    {
        if (thisA.equates(that)) return null;
        
        if (thisA.getRay1().overlays(that.getRay1()) || thisA.getRay1().overlays(that.getRay2())) return thisA.getRay1();

        if (thisA.getRay2().overlays(that.getRay1()) || thisA.getRay2().overlays(that.getRay2())) return thisA.getRay2();

        return null;
    }
    
    
    /**
     * Find the measure of the angle (in radians) specified by the three points.
     * @param a A point defining the angle.
     * @param b A point defining the angle. This is the point the angle is actually at.
     * @param c A point defining the angle.
     * @return The measure of the angle (in radians) specified by the three points.
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
        if (MathUtilities.doubleEquals(Math.abs(cosAngle), 1))
        {
            cosAngle = cosAngle < 0 ? -1 : 1;
        }

        // 90 degrees
        if (MathUtilities.doubleEquals(cosAngle, 0)) cosAngle = 0;

        return Math.acos(cosAngle);
    }

    
    
    
    
    
    

    //    //Checking for equality of angles WITHOUT considering possible overlay of rays (i.e. two angles will be considered NOT equal
    //    //if they contain rays that coincide but are not equivalent)
    //    public boolean equalRays(Object obj)
    //    {
    //        if (obj == null) return false;
    //        if (!(obj instanceof Angle)) return false;
    //        Angle angle = (Angle)obj;
    //
    //        // Measures better be the same.
    //        if (!backend.utilities.math.MathUtilities.doubleEquals(this.measure, angle.measure)) return false;
    //
    //        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
    //                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    //    }
    //
    //    public Segment SharesOneRayAndHasSameVertex(Angle ang)
    //    {
    //        if (SameVertex(ang) == null) return null;
    //
    //        return SharedRay(ang);
    //    }
    //
    //
    //    // Return the shared angle in both congruences
    //    public Segment IsAdjacentTo(Angle thatAngle)
    //    {
    //        if (thatAngle.IsOnInterior(this)) return null;
    //        if (this.IsOnInterior(thatAngle)) return null;
    //
    //        //Segment shared =  SharesOneRayAndHasSameVertex(thatAngle);
    //        //if (shared == null) return null;
    //
    //        //// Is this a scenario where one angle encompasses completely the other angle?
    //        //Segment otherThat = thatAngle.OtherRayEquates(shared);
    //        //Angle tempAngle = new Angle(shared.OtherPoint(GetVertex()), GetVertex(), this.OtherRayEquates(shared).OtherPoint(GetVertex()));
    //
    //        //if (tempAngle.IsOnInterior(otherThat.OtherPoint(GetVertex())) return null; 
    //
    //        return SharesOneRayAndHasSameVertex(thatAngle);
    //    }
    //
    //    //
    //    // Is this point in the interior of the angle?
    //    //
    //    public boolean IsOnInterior(Point pt)
    //    {
    //        //     |
    //        //     |
    //        //  x  |_____
    //        // Is the point on either ray such that it is outside the angle? (x in the image above)
    //        if (ray1.PointLiesOn(pt))
    //        {
    //            // Point between the endpoints of the ray.
    //            if (Segment.Between(pt, GetVertex(), ray1.OtherPoint(GetVertex()))) return true;
    //            // Point is on the ray, but extended in the right direction.
    //            return Segment.Between(ray1.OtherPoint(GetVertex()), GetVertex(), pt);
    //        }
    //        if (ray2.PointLiesOn(pt))
    //        {
    //            // Point between the endpoints of the ray.
    //            if (Segment.Between(pt, GetVertex(), ray2.OtherPoint(GetVertex()))) return true;
    //            // Point is on the ray, but extended in the right direction.
    //            return Segment.Between(ray2.OtherPoint(GetVertex()), GetVertex(), pt);
    //        }
    //
    //        Angle newAngle1 = new Angle(A, GetVertex(), pt);
    //        Angle newAngle2 = new Angle(C, GetVertex(), pt);
    //
    //        // This is an angle addition scenario, BUT not with these two angles; that is, one is contained in the other.
    //        if (MathUtilities.doubleEquals(newAngle1.measure + newAngle2.measure, this.measure)) return true;
    //
    //        return newAngle1.measure + newAngle2.measure <= this.measure;
    //    }
    //
    //    //
    //    // Is this point in the interior of the angle?
    //    //
    //    public boolean IsOnInteriorExplicitly(Point pt)
    //    {
    //        if (ray1.PointLiesOn(pt)) return false;
    //        if (ray2.PointLiesOn(pt)) return false;
    //
    //        Angle newAngle1 = new Angle(A, GetVertex(), pt);
    //        Angle newAngle2 = new Angle(C, GetVertex(), pt);
    //
    //        // This is an angle addition scenario, BUT not with these two angles; that is, one is contained in the other.
    //        if (MathUtilities.doubleEquals(newAngle1.measure + newAngle2.measure, this.measure)) return true;
    //
    //        return newAngle1.measure + newAngle2.measure <= this.measure;
    //    }
    //
    //    //
    //    // Is this angle on the interior of the other?
    //    //
    //    public boolean IsOnInterior(Angle thatAngle)
    //    {
    //        if (this.measure < thatAngle.measure) return false;
    //
    //        return this.IsOnInterior(thatAngle.A) && this.IsOnInterior(thatAngle.B) && this.IsOnInterior(thatAngle.C);
    //    }
    //
    //    public Point OtherPoint(Segment seg)
    //    {
    //        if (seg.hasPoint(A) && seg.hasPoint(B)) return C;
    //        if (seg.hasPoint(A) && seg.hasPoint(C)) return B;
    //        if (seg.hasPoint(B) && seg.hasPoint(C)) return A;
    //
    //        if (seg.PointLiesOn(A) && seg.PointLiesOn(B)) return C;
    //        if (seg.PointLiesOn(A) && seg.PointLiesOn(C)) return B;
    //        if (seg.PointLiesOn(B) && seg.PointLiesOn(C)) return A;
    //
    //        return null;
    //    }
    //
    //    //
    //    // Given one ray of the angle, return the other ray
    //    //
    //    public Segment OtherRay(Segment seg)
    //    {
    //        if (ray1.equals(seg)) return ray2;
    //        if (ray2.equals(seg)) return ray1;
    //
    //        return null;
    //    }
    //
    //    //
    //    // Given one ray of the angle, return the other ray
    //    //
    //    public Segment OtherRayEquates(Segment seg)
    //    {
    //        if (ray1.rayOverlays(seg)) return ray2;
    //        if (ray2.rayOverlays(seg)) return ray1;
    //
    //        if (ray1.IsCollinearWith(seg)) return ray2;
    //        if (ray2.IsCollinearWith(seg)) return ray1;
    //
    //        return null;
    //    }
    //
    //    //
    //    // Do these segments overlay this angle?
    //    //
    //    public boolean IsIncludedAngle(Segment seg1, Segment seg2)
    //    {
    //        // Do not allow the same segment.
    //        if (seg1.structurallyEquals(seg2)) return false;
    //
    //        // Check direct inclusion
    //        if (seg1.equals(ray1) && seg2.equals(ray2) || seg1.equals(ray2) && seg2.equals(ray1)) return true;
    //
    //        // Check overlaying angle
    //        Point shared = seg1.sharedVertex(seg2);
    //
    //        if (shared == null) return false;
    //
    //        Angle thatAngle = new Angle(seg1.OtherPoint(shared), shared, seg2.OtherPoint(shared));
    //
    //        return this.Equates(thatAngle);
    //    }
    //
    //    private static final int[] VALID_CONCRETE_SPECIAL_ANGLES = { 30, 45 }; // 0 , 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330 }; // 15, 22.5, ...
    //
    //
    //    private static boolean IsSpecialAngle(double measure)
    //    {
    //        for (int d : VALID_CONCRETE_SPECIAL_ANGLES)
    //        {
    //            if (MathUtilities.GCD((int)measure, d) == d) return true;
    //        }
    //
    //        return false;
    //    }
    //
    //    @Override
    //    public boolean containsClause(GroundedClause target)
    //    {
    //        return this.equals(target);
    //    }
    //
    //    //
    //    // Is the given angle the same as this angle? that is, the vertex is the same and the rays coincide
    //    // (not necessarily with the same endpoints)
    //    // Can't just be collinear, must be collinear and on same side of an angle
    //    //
    //    public boolean Equates(Angle thatAngle)
    //    {
    //        //if (this.Equals(thatAngle)) return true;
    //
    //        // Vertices must equate
    //        if (!this.GetVertex().equals(thatAngle.GetVertex())) return false;
    //
    //        // Rays must originate at the vertex and emanate outward
    //        return (ray1.rayOverlays(thatAngle.ray1) && ray2.rayOverlays(thatAngle.ray2)) ||
    //                (ray2.rayOverlays(thatAngle.ray1) && ray1.rayOverlays(thatAngle.ray2));
    //    }
    //
    //    // Does this angle lie between the two lines? This is mainly for a parallelism check
    //    //        public boolean OnInteriorOf(Intersection inter1, Intersection inter2)
    //    //        {
    //    //            Intersection angleBelongs = null;
    //    //            Intersection angleDoesNotBelong = null;
    //    //
    //    //            // Determine the intersection to which the angle belongs
    //    //            if (inter1.InducesNonStraightAngle(this))
    //    //            {
    //    //                angleBelongs = inter1;
    //    //                angleDoesNotBelong = inter2;
    //    //            }
    //    //            else if (inter2.InducesNonStraightAngle(this))
    //    //            {
    //    //                angleBelongs = inter2;
    //    //                angleDoesNotBelong = inter1;
    //    //            }
    //    //
    //    //            if (angleBelongs == null || angleDoesNotBelong == null) return false;
    //    //
    //    //            // Make the transversal out of the points of intersection
    //    //            Segment transversal = new Segment(angleBelongs.intersect, angleDoesNotBelong.intersect);
    //    //            Segment angleRayOnTraversal = this.ray1.IsCollinearWith(transversal) ? ray1 : ray2;
    //    //
    //    //            // Is the endpoint of the angle (on the transversal) between the two intersection points?
    //    //            // Or, is that same endpoint on the far end beyond the other line: the other intersection point lies between the other points
    //    //            return Segment.Between(angleRayOnTraversal.OtherPoint(this.GetVertex()), angleBelongs.intersect, angleDoesNotBelong.intersect) ||
    //    //                    Segment.Between(angleDoesNotBelong.intersect, angleBelongs.intersect, angleRayOnTraversal.OtherPoint(this.GetVertex())); 
    //    //        }
    //
    //    //        public static ArrayList<GenericInstantiator.EdgeAggregator> Instantiate(GroundedClause pred, GroundedClause c)
    //    //        {
    //    //            ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
    //    //
    //    //            if (c instanceof Angle) return newGrounded;
    //    //
    //    //            //Angle angle = c as Angle;
    //    //
    //    //            //if (IsSpecialAngle(angle.measure))
    //    //            //{
    //    //            //    GeometricAngleEquation angEq = new GeometricAngleEquation(angle, new NumericValue((int)angle.measure), "Given:tbd");
    //    //            //    List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(pred);
    //    //            //    newClauses.Add(new EdgeAggregator(antecedent, angEq));
    //    //            //}
    //    //
    //    //            return newGrounded;
    //    //        }
    //
    //    //        //
    //    //        // Maintain a public repository of all angle objects in the figure
    //    //        //
    //    //        public static void Clear()
    //    //        {
    //    //            figureAngles.clear();
    //    //            candidateTriangles.clear();
    //    //            knownSharedAngles.clear();
    //    //        }
    //    //        public static ArrayList<Angle> figureAngles = new ArrayList<Angle>();
    //    //        public static void Record(GroundedClause clause)
    //    //        {
    //    //            // Record uniquely? For right angles, etc?
    //    //            if (clause instanceof Angle) figureAngles.add((Angle)clause);
    //    //        }
    //    //        public static Angle AcquireFigureAngle(Angle thatAngle)
    //    //        {
    //    //            for (Angle angle : figureAngles)
    //    //            {
    //    //                if (angle.Equates(thatAngle)) return angle;
    //    //            }
    //    //            return null;
    //    //        }
    //
    //    //
    //    // Each angle is congruent to itself; only generate if both rays are shared
    //    //
    //    //        private static ArrayList<Triangle> candidateTriangles = new ArrayList<Triangle>();
    //    //        private static ArrayList<Angle> knownSharedAngles = new ArrayList<Angle>();
    //    //        public static ArrayList<GenericInstantiator.EdgeAggregator> InstantiateReflexiveAngles(GroundedClause clause)
    //    //        {
    //    //            ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
    //    //
    //    //            Triangle newTriangle = (Triangle)clause;
    //    //            if (newTriangle == null) return newGrounded;
    //    //
    //    //            //
    //    //            // Compare all angles in this new triangle to all the angles in the old triangles
    //    //            //
    //    //            for (Triangle oldTriangle : candidateTriangles)
    //    //            {
    //    //                if (newTriangle.HasAngle(oldTriangle.AngleA))
    //    //                {
    //    //                    GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleA);
    //    //                    if (newClause != null) newGrounded.Add(newClause);
    //    //                }
    //    //
    //    //                if (newTriangle.HasAngle(oldTriangle.AngleB))
    //    //                {
    //    //                    GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleB);
    //    //                    if (newClause != null) newGrounded.Add(newClause);
    //    //                }
    //    //
    //    //                if (newTriangle.HasAngle(oldTriangle.AngleC))
    //    //                {
    //    //                    GenericInstantiator.EdgeAggregator newClause = GenerateAngleCongruence(newTriangle, oldTriangle.AngleC);
    //    //                    if (newClause != null) newGrounded.Add(newClause);
    //    //                }
    //    //            }
    //    //
    //    //            candidateTriangles.add(newTriangle);
    //    //
    //    //            return newGrounded;
    //    //        }
    //
    //    //
    //    // Generate the actual angle congruence
    //    //
    //    private static final String REFLEXIVE_ANGLE_NAME = "Reflexive Angles";
    //    //        private static Hypergraph.EdgeAnnotation reflexAnnotation = new Hypergraph.EdgeAnnotation(REFLEXIVE_ANGLE_NAME, EngineUIBridge.JustificationSwitch.REFLEXIVE);
    //
    //    //        public static GenericInstantiator.EdgeAggregator GenerateAngleCongruence(Triangle tri, Angle angle)
    //    //        {
    //    //            //
    //    //            // If we have already generated a reflexive congruence, avoid regenerating
    //    //            //
    //    //            for (Angle oldSharedAngle : knownSharedAngles)
    //    //            {
    //    //                if (oldSharedAngle.Equates(angle)) return null;
    //    //            }
    //    //
    //    //            // Generate
    //    //            GeometricCongruentAngles gcas = new GeometricCongruentAngles(angle, angle);
    //    //
    //    //            // This is an 'obvious' notion so it should be intrinsic to any figure
    //    //            gcas.MakeIntrinsic();
    //    //
    //    //            return new GenericInstantiator.EdgeAggregator(backend.utilities.list.Utilities.makeList(Angle.AcquireFigureAngle(angle)), gcas, reflexAnnotation);
    //    //        }
    //
    //    public boolean IsComplementaryTo(Angle thatAngle)
    //    {
    //        return MathUtilities.doubleEquals(this.measure + thatAngle.measure, 90);
    //    }
    //
    //    public boolean IsSupplementaryTo(Angle thatAngle)
    //    {
    //        return MathUtilities.doubleEquals(this.measure + thatAngle.measure, 180);
    //    }
    //
    //    public boolean IsStraightAngle()
    //    {
    //        return ray1.IsCollinearWith(ray2);
    //    }
    //
    //    // Checking for structural equality (is it the same segment) excluding the multiplier
    //    // This is either a direct comparison of the angle based on vertices or 
    //    @Override
    //    public boolean structurallyEquals(Object obj)
    //    {
    //        if (obj == null) return false;
    //        if (!(obj instanceof Angle)) return false;
    //        Angle angle = (Angle)obj;
    //
    //        // Measures better be the same.
    //        if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;
    //
    //        if (this.equates(angle)) return true;
    //
    //        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
    //                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    //    }
    //
    //    //Checking for equality of angles WITHOUT considering possible overlay of rays (i.e. two angles will be considered NOT equal
    //    //if they contain rays that coincide but are not equivalent)
    //    public boolean EqualRays(Object obj)
    //    {
    //        if (obj == null) return false;
    //        if (!(obj instanceof Angle)) return false;
    //        Angle angle = (Angle)obj ;
    //
    //        // Measures better be the same.
    //        if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;
    //
    //        return (angle.A.structurallyEquals(A) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(C)) ||
    //                (angle.A.structurallyEquals(C) && angle.B.structurallyEquals(B) && angle.C.structurallyEquals(A));
    //    }
    //
    //    //
    //    // Is this angle congruent to the given angle in terms of the coordinatization from the UI?
    //    //
    //    public boolean CoordinateCongruent(Angle a)
    //    {
    //        return MathUtilities.doubleEquals(a.measure, this.measure);
    //    }
    //
    //    public boolean CoordinateAngleBisector(Segment thatSegment)
    //    {
    //        if (!thatSegment.pointLiesBetweenEndpoints(this.GetVertex())) return false;
    //
    //        if (thatSegment.IsCollinearWith(this.ray1) || thatSegment.IsCollinearWith(this.ray2)) return false;
    //
    //        Point interiorPoint = this.IsOnInteriorExplicitly(thatSegment.getPoint1()) ? thatSegment._point1 : thatSegment._point2;
    //        if (!this.IsOnInteriorExplicitly(interiorPoint)) return false;
    //
    //        Angle angle1 = new Angle(A, GetVertex(), interiorPoint);
    //        Angle angle2 = new Angle(C, GetVertex(), interiorPoint);
    //
    //        return MathUtilities.doubleEquals(angle1.measure, angle2.measure);
    //    }
    //
    //    //
    //    // Is this angle proportional to the given segment in terms of the coordinatization from the UI?
    //    // We should not report proportional if the ratio between segments is 1
    //    //
    //    public backend.utilities.Pair<Integer, Integer> CoordinateProportional(Angle a)
    //    {
    //        return MathUtilities.RationalRatio(a.measure, this.measure);
    //    }
    //
    //    public boolean HasPoint(Point p)
    //    {
    //        if (A.equals(p)) return true;
    //        if (B.equals(p)) return true;
    //        if (C.equals(p)) return true;
    //
    //        return false;
    //    }
    //}


}
