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
package backend.ast.figure.components;
import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.utilities.math.MathUtilities;




public class Angle extends Figure
{
    protected Point A; 
    protected Point B; 
    protected Point C; 
    protected Segment ray1; 
    protected Segment ray2; 
    protected double measure; 

    public Point getA() { return A;}
    public Point getB() { return B;}
    public Point getC() { return C;}
    public Segment getRay1() { return ray1;}
    public Segment getRay2() { return ray2;}
    public double getMeasure() { return measure;}

    public Point GetVertex() { return B; }

 // Make a deep copy of this object
    @Override
    public GroundedClause deepCopy() throws CloneNotSupportedException
    {
        Angle other = (Angle)(this.clone());
        other.A = (Point)this.A.DeepCopy();
        other.B = (Point)this.B.DeepCopy();
        other.C = (Point)this.C.DeepCopy();
        other.ray1 = (Segment)this.ray1.deepCopy();
        other.ray2 = (Segment)this.ray2.deepCopy();

        return other;
    }
    
    public Angle(Segment ray1, Segment ray2) throws IllegalArgumentException
    {
        Point vertex = ray1.SharedVertex(ray2);

        if (vertex == null) throw new IllegalArgumentException("Rays do not share a vertex: " + ray1 + " " + ray2);

        this.A = ray1.OtherPoint(vertex);
        this.B = vertex;
        this.C = ray2.OtherPoint(vertex);
        this.ray1 = ray1;
        this.ray2 = ray2;
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0) throw new IllegalArgumentException("Measure of " + this.toString() + " is ZERO");
    }


    /**
     * Create a new angle.
     * @param a A point defining the angle.
     * @param b A point defining the angle. This is the point the angle is actually at.
     * @param c A point defining the angle.
     */
    public Angle(Point a, Point b, Point c)
    {
        if (a.structurallyEquals(b) || b.structurallyEquals(c) || a.structurallyEquals(c))
        {
            return;
            //Commented out : Source Code ------
            //throw new ArgumentException("Angle constructed with redundant vertices.");
        }
        this.A = a;
        this.B = b;
        this.C = c;
        ray1 = new Segment(a, b);
        ray2 = new Segment(b, c);
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0)
        {
            //Commented out : Source Code ------
            //System.Diagnostics.Debug.WriteLine("NO-OP");
            //throw new ArgumentException("Measure of " + this.toString() + " is ZERO");
        }
    }


    public Angle(ArrayList<Point> pts)
    {
        if (pts.size() != 3)
        {
            throw new IllegalArgumentException("Angle constructed with only " + pts.size() + " vertices.");
        }

        this.A = pts.get(0);
        this.B = pts.get(1);
        this.C = pts.get(3);

        if (A.structurallyEquals(B) || B.structurallyEquals(C) || A.structurallyEquals(C))
        {
            throw new IllegalArgumentException("Angle constructed with redundant vertices.");
        }

        ray1 = new Segment(A, B);
        ray2 = new Segment(B, C);
        this.measure = toDegrees(findAngle(A, B, C));

        if (measure <= 0)
        {
            throw new IllegalArgumentException("Measure of " + this.toString() + " is ZERO");
        }
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
        if (backend.utilities.math.MathUtilities.doubleEquals(Math.abs(cosAngle), 1))
        {
            cosAngle = cosAngle < 0 ? -1 : 1;
        }

        // 90 degrees
        if (backend.utilities.math.MathUtilities.doubleEquals(cosAngle, 0)) cosAngle = 0;

        return Math.acos(cosAngle);
    }

    /**
     * Converts radians into degrees.
     * @param radians An angle in radians.
     * @return An angle in degrees
     */
    public static double toDegrees(double radians)
    {
        return radians * 180 / Math.PI;
    }

    /**
     * Converts degrees into radians
     * @param degrees An angle in degrees
     * @return An angle in radians
     */
    public static double toRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }


    //
    // Looks for a single shared ray
    //
    public Segment SharedRay(Angle ang)
    {
        if (ray1.rayOverlays(ang.ray1) || ray1.rayOverlays(ang.ray2)) return ray1;

        if (ray2.rayOverlays(ang.ray1) || ray2.rayOverlays(ang.ray2)) return ray2;

        return null;
    }

    //
    // Is the given angle the same as this angle? that is, the vertex is the same and the rays coincide
    // (not necessarily with the same endpoints)
    // Can't just be collinear, must be collinear and on same side of an angle
    //
    public boolean equates(Angle thatAngle)
    {
        //if (this.Equals(thatAngle)) return true;

        // Vertices must equate
        if (!this.GetVertex().equals(thatAngle.GetVertex())) return false;

        // Rays must originate at the vertex and emanate outward
        return (ray1.rayOverlays(thatAngle.ray1) && ray2.rayOverlays(thatAngle.ray2)) ||
                (ray2.rayOverlays(thatAngle.ray1) && ray1.rayOverlays(thatAngle.ray2));
    }


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

    @Override
    public int getHashCode() { return super.getHashCode(); }

    public Point SameVertex(Angle ang)
    {
        return GetVertex().equals(ang.GetVertex()) ? GetVertex() : null;
    }

    public Segment SharesOneRayAndHasSameVertex(Angle ang)
    {
        if (SameVertex(ang) == null) return null;

        return SharedRay(ang);
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
        //Angle tempAngle = new Angle(shared.OtherPoint(GetVertex()), GetVertex(), this.OtherRayEquates(shared).OtherPoint(GetVertex()));

        //if (tempAngle.IsOnInterior(otherThat.OtherPoint(GetVertex())) return null; 

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
        if (ray1.PointLiesOn(pt))
        {
            // Point between the endpoints of the ray.
            if (Segment.Between(pt, GetVertex(), ray1.OtherPoint(GetVertex()))) return true;
            // Point is on the ray, but extended in the right direction.
            return Segment.Between(ray1.OtherPoint(GetVertex()), GetVertex(), pt);
        }
        if (ray2.PointLiesOn(pt))
        {
            // Point between the endpoints of the ray.
            if (Segment.Between(pt, GetVertex(), ray2.OtherPoint(GetVertex()))) return true;
            // Point is on the ray, but extended in the right direction.
            return Segment.Between(ray2.OtherPoint(GetVertex()), GetVertex(), pt);
        }

        Angle newAngle1 = new Angle(A, GetVertex(), pt);
        Angle newAngle2 = new Angle(C, GetVertex(), pt);

        // This is an angle addition scenario, BUT not with these two angles; that is, one is contained in the other.
        if (MathUtilities.doubleEquals(newAngle1.measure + newAngle2.measure, this.measure)) return true;

        return newAngle1.measure + newAngle2.measure <= this.measure;
    }

    //
    // Is this point in the interior of the angle?
    //
    public boolean IsOnInteriorExplicitly(Point pt)
    {
        if (ray1.PointLiesOn(pt)) return false;
        if (ray2.PointLiesOn(pt)) return false;

        Angle newAngle1 = new Angle(A, GetVertex(), pt);
        Angle newAngle2 = new Angle(C, GetVertex(), pt);

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
        if (seg.hasPoint(A) && seg.hasPoint(B)) return C;
        if (seg.hasPoint(A) && seg.hasPoint(C)) return B;
        if (seg.hasPoint(B) && seg.hasPoint(C)) return A;

        if (seg.PointLiesOn(A) && seg.PointLiesOn(B)) return C;
        if (seg.PointLiesOn(A) && seg.PointLiesOn(C)) return B;
        if (seg.PointLiesOn(B) && seg.PointLiesOn(C)) return A;

        return null;
    }

    //
    // Given one ray of the angle, return the other ray
    //
    public Segment OtherRay(Segment seg)
    {
        if (ray1.equals(seg)) return ray2;
        if (ray2.equals(seg)) return ray1;

        return null;
    }

    //
    // Given one ray of the angle, return the other ray
    //
    public Segment OtherRayEquates(Segment seg)
    {
        if (ray1.rayOverlays(seg)) return ray2;
        if (ray2.rayOverlays(seg)) return ray1;

        if (ray1.IsCollinearWith(seg)) return ray2;
        if (ray2.IsCollinearWith(seg)) return ray1;

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
        if (seg1.equals(ray1) && seg2.equals(ray2) || seg1.equals(ray2) && seg2.equals(ray1)) return true;

        // Check overlaying angle
        Point shared = seg1.SharedVertex(seg2);

        if (shared == null) return false;

        Angle thatAngle = new Angle(seg1.OtherPoint(shared), shared, seg2.OtherPoint(shared));

        return this.Equates(thatAngle);
    }

    private static final int[] VALID_CONCRETE_SPECIAL_ANGLES = { 30, 45 }; // 0 , 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330 }; // 15, 22.5, ...


    private static boolean IsSpecialAngle(double measure)
    {
        for (int d : VALID_CONCRETE_SPECIAL_ANGLES)
        {
            if (MathUtilities.GCD((int)measure, d) == d) return true;
        }

        return false;
    }

    @Override
    public boolean containsClause(GroundedClause target)
    {
        return this.equals(target);
    }

    //
    // Is the given angle the same as this angle? that is, the vertex is the same and the rays coincide
    // (not necessarily with the same endpoints)
    // Can't just be collinear, must be collinear and on same side of an angle
    //
    public boolean Equates(Angle thatAngle)
    {
        //if (this.Equals(thatAngle)) return true;

        // Vertices must equate
        if (!this.GetVertex().equals(thatAngle.GetVertex())) return false;

        // Rays must originate at the vertex and emanate outward
        return (ray1.rayOverlays(thatAngle.ray1) && ray2.rayOverlays(thatAngle.ray2)) ||
                (ray2.rayOverlays(thatAngle.ray1) && ray1.rayOverlays(thatAngle.ray2));
    }

    // Does this angle lie between the two lines? This is mainly for a parallelism check
//    public boolean OnInteriorOf(Intersection inter1, Intersection inter2)
//    {
//        Intersection angleBelongs = null;
//        Intersection angleDoesNotBelong = null;
//
//        // Determine the intersection to which the angle belongs
//        if (inter1.InducesNonStraightAngle(this))
//        {
//            angleBelongs = inter1;
//            angleDoesNotBelong = inter2;
//        }
//        else if (inter2.InducesNonStraightAngle(this))
//        {
//            angleBelongs = inter2;
//            angleDoesNotBelong = inter1;
//        }
//
//        if (angleBelongs == null || angleDoesNotBelong == null) return false;
//
//        // Make the transversal out of the points of intersection
//        Segment transversal = new Segment(angleBelongs.intersect, angleDoesNotBelong.intersect);
//        Segment angleRayOnTraversal = this.ray1.IsCollinearWith(transversal) ? ray1 : ray2;
//
//        // Is the endpoint of the angle (on the transversal) between the two intersection points?
//        // Or, is that same endpoint on the far end beyond the other line: the other intersection point lies between the other points
//        return Segment.Between(angleRayOnTraversal.OtherPoint(this.GetVertex()), angleBelongs.intersect, angleDoesNotBelong.intersect) ||
//                Segment.Between(angleDoesNotBelong.intersect, angleBelongs.intersect, angleRayOnTraversal.OtherPoint(this.GetVertex())); 
//    }

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

    //
    // Maintain a public repository of all angle objects in the figure
    //
    public static void Clear()
    {
        figureAngles.clear();
        candidateTriangles.clear();
        knownSharedAngles.clear();
    }
    public static ArrayList<Angle> figureAngles = new ArrayList<Angle>();
    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
        if (clause instanceof Angle) figureAngles.add((Angle)clause);
    }
    public static Angle AcquireFigureAngle(Angle thatAngle)
    {
        for (Angle angle : figureAngles)
        {
            if (angle.Equates(thatAngle)) return angle;
        }
        return null;
    }

    //
    // Each angle is congruent to itself; only generate if both rays are shared
    //
    private static ArrayList<Triangle> candidateTriangles = new ArrayList<Triangle>();
    private static ArrayList<Angle> knownSharedAngles = new ArrayList<Angle>();
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

    public boolean IsStraightAngle()
    {
        return ray1.IsCollinearWith(ray2);
    }

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

    //
    // Is this angle congruent to the given angle in terms of the coordinatization from the UI?
    //
    public boolean CoordinateCongruent(Angle a)
    {
        return MathUtilities.doubleEquals(a.measure, this.measure);
    }

    public boolean CoordinateAngleBisector(Segment thatSegment)
    {
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(this.GetVertex())) return false;

        if (thatSegment.IsCollinearWith(this.ray1) || thatSegment.IsCollinearWith(this.ray2)) return false;

        Point interiorPoint = this.IsOnInteriorExplicitly(thatSegment.getPoint1()) ? thatSegment._point1 : thatSegment._point2;
        if (!this.IsOnInteriorExplicitly(interiorPoint)) return false;

        Angle angle1 = new Angle(A, GetVertex(), interiorPoint);
        Angle angle2 = new Angle(C, GetVertex(), interiorPoint);
        
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

    public boolean HasPoint(Point p)
    {
        if (A.equals(p)) return true;
        if (B.equals(p)) return true;
        if (C.equals(p)) return true;

        return false;
    }

    public boolean HasSegment(Segment seg)
    {
        return ray1.rayOverlays(seg) || ray2.rayOverlays(seg);
    }

    // CTA: Be careful with equality; this is object-based equality
    // If we check for angle measure equality that is distinct.
    // If we check to see that a different set of remote vertices describes this angle, that is distinct.
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Angle)) return false;
        Angle angle = (Angle)obj ;

        // Measures must be the same.
        if (!MathUtilities.doubleEquals(this.measure, angle.measure)) return false;

        return super.equals(obj) && structurallyEquals(obj);
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
