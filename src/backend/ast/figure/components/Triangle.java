package backend.ast.figure.components;

import java.util.ArrayList;
import backend.ast.GroundedClause;
import backend.utilities.translation.OutPair;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.Descriptors.Strengthened;


public class Triangle extends Polygon
{
    /// <summary>
    /// Represents a triangle, which consists of 3 segments
    /// </summary>
    private Point Point1;
    private Point Point2;
    private Point Point3;

    private Segment SegmentA;
    private Segment SegmentB;
    private Segment SegmentC;
    private Angle AngleA;
    private Angle AngleB;
    private Angle AngleC;
    protected boolean isRight;
    public boolean givenRight;
    protected boolean provenRight;
    protected Angle rightAngle;
    protected boolean isIsosceles;
    protected boolean provenIsosceles;
    protected boolean isEquilateral;
    protected boolean provenEquilateral;
    private ArrayList<Triangle> congruencePairs;
    public boolean WasDeducedCongruent(Triangle that) { return congruencePairs.contains(that); }
    private ArrayList<Triangle> similarPairs;
    public boolean WasDeducedSimilar(Triangle that) { return similarPairs.contains(that); }

    @Override
    public boolean strengthened() { return provenIsosceles || provenRight || provenEquilateral; }

    public Point getPoint1() { return Point1; }
    public Point getPoint2() { return Point2; }
    public Point getPoint3() { return Point3; }

    public Segment getSegmentA() { return SegmentA; }
    public Segment getSegmentB() { return SegmentB; }
    public Segment getSegmentC() { return SegmentC; }
    public Angle getAngleA() { return AngleA; }
    public Angle getAngleB() { return AngleB; }
    public Angle getAngleC() { return AngleC; }

    public boolean isRight() { return isRight; }
    public boolean provenRight() { return provenRight; }
    public Angle rightAngle() { return rightAngle; }
    public boolean isIsosceles() { return isIsosceles; }
    public boolean provenIsosceles() { return provenIsosceles; }
    public boolean isEquilateral() { return isEquilateral; }
    public boolean provenEquilateral() { return provenEquilateral; }
    private ArrayList<Triangle> congruencePairs() { return congruencePairs; }
    private ArrayList<Triangle> similarPairs() { return similarPairs; }

    public Triangle(Segment a, Segment b, Segment c)
    {
        setTriangle(a, b, c);
    }
    
    public Triangle(Point a, Point b, Point c)
    {
        setTriangle(a, b, c);
    }
    
    public <T> Triangle(ArrayList<T> obj)  
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
    
    /// <summary>
    /// Create a new triangle bounded by the 3 given segments. The set of points that define these segments should have only 3 distinct elements.
    /// </summary>
    /// <param name="a">The segment opposite point a</param>
    /// <param name="b">The segment opposite point b</param>
    /// <param name="c">The segment opposite point c</param>
    private void setTriangle(Segment a, Segment b, Segment c) 
    {
        SegmentA = a;
        SegmentB = b;
        SegmentC = c;

        Point1 = SegmentA.getPoint1();
        Point2 = SegmentA.getPoint2();
        Point3 = Point1.equals(SegmentB.getPoint1()) || Point2.equals(SegmentB.getPoint1()) ? SegmentB.getPoint2() : SegmentB.getPoint1();

        AngleA = new Angle(Point1, Point2, Point3);
        AngleB = new Angle(Point2, Point3, Point1);
        AngleC = new Angle(Point3, Point1, Point2);

        isRight = isRightTriangle();
        provenRight = false;
        givenRight = false;
        isIsosceles = IsIsosceles();
        provenIsosceles = false;
        isEquilateral = IsEquilateral();
        provenEquilateral = false;

        congruencePairs = new ArrayList<Triangle>();
        similarPairs = new ArrayList<Triangle>();
        
        orderedSides = new ArrayList<Segment>();
        orderedSides.add(a);
        orderedSides.add(b);
        orderedSides.add(c);

        addSuperFigureToDependencies();
    }

    private void setTriangle(Point a, Point b, Point c)
    {
        setTriangle(new Segment(a, b), new Segment(b, c), new Segment(a, c));
    }

//    public Triangle(ArrayList<Point> pts) 
//    {
//        this(pts.get(0), pts.get(1), pts.get(2));
//    }


    //    @Override
    //    public void DumpXML(Action<string, List<GroundedClause>> write)
    //    {
    //        List<GroundedClause> children = new List<GroundedClause>();
    //        for (Point pt : points)
    //        {
    //            children.add(pt);
    //        }
    //        write("Triangle", children);
    //    }

    protected void addSuperFigureToDependencies()
    {
        Utilities.AddUniqueStructurally(SegmentA.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(SegmentB.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(SegmentC.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point1.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point2.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point3.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleA.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleB.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleC.getSuperFigures(), this);
    }

    public void SetProvenToBeRight() { provenRight = true; }
    public void SetProvenToBeIsosceles() { provenIsosceles = true; } 
    public void SetProvenToBeEquilateral() { provenEquilateral = true; }


    //
    //
    // Given 1 side of a right triangle and an angle, calculate the other 2 sides.
    //
    //
//    private ArrayList<Pair<Segment, Double>> CalcSidesHypotenuseKnown(RightTriangle tri, Angle knownAngle, double knownAngleVal, Segment hypotenuse, double hypotVal)
//    {
//        ArrayList<Pair<Segment, Double>> pairs = new ArrayList<Pair<Segment, Double>>();
//
//        double oppSideLength = hypotVal * Math.sin(Angle.toRadians(knownAngleVal));
//        pairs.add(new Pair<Segment,Double>(tri.GetOppositeSide(knownAngle), oppSideLength));
//
//        double adjSideLength = hypotVal * Math.cos(Angle.toRadians(knownAngleVal));
//        pairs.add(new Pair<Segment, Double>(knownAngle.OtherRay(hypotenuse), adjSideLength));
//
//        return pairs;
//    }
//    private ArrayList<Pair<Segment, Double>> CalcSidesHypotenuseUnknown(RightTriangle tri, Angle knownAngle, double knownAngleVal, Segment knownSide, double sideVal)
//    {
//        ArrayList<Pair<Segment, Double>> pairs = new ArrayList<Pair<Segment, Double>>();
//
//        Segment hypotenuse = tri.GetHypotenuse();
//        Segment oppSideOfAngle = tri.GetOppositeSide(knownAngle);
//        Segment adjacent = knownAngle.OtherRay(hypotenuse);
//
//        if (oppSideOfAngle.StructurallyEquals(knownSide))
//        {
//            double adjVal = sideVal / Math.tan(Angle.toRadians(knownAngleVal));
//
//            pairs.add(new Pair<Segment, Double>(adjacent, adjVal));
//            pairs.add(new Pair<Segment, Double>(hypotenuse, Math.sqrt(sideVal * sideVal + adjVal * adjVal)));
//        }
//        else if (adjacent.StructurallyEquals(knownSide))
//        {
//            double oppVal = sideVal * Math.tan(Angle.toRadians(knownAngleVal));
//
//            pairs.add(new Pair<Segment, Double>(oppSideOfAngle, oppVal));
//            pairs.add(new Pair<Segment, Double>(hypotenuse, Math.sqrt(sideVal * sideVal + oppVal * oppVal)));
//        }
//
//        return pairs;
//    }
    
//    private ArrayList<Pair<Segment, Double>> CalcSides(RightTriangle tri, Angle rightAngle, Angle knownAngle, double knownAngleVal, Segment knownSeg, double knownSegVal)
//    {
//        //
//        // Determine the nature of the known Segment w.r.t. to the known angle.
//        //
//        Segment hypotenuse = tri.GetHypotenuse();
//
//        // Hypotenuse known
//        if (knownSeg.StructurallyEquals(hypotenuse))
//        {
//            return CalcSidesHypotenuseKnown(tri, knownAngle, knownAngleVal, hypotenuse, knownSegVal);
//        }
//        else
//        {
//            return CalcSidesHypotenuseUnknown(tri, knownAngle, knownAngleVal, knownSeg, knownSegVal);
//        }
//    }

//    public ArrayList<Pair<Segment, Double>> RightTriangleTrigApplies(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        ArrayList<Pair<Segment, Double>> pairs = new ArrayList<Pair<Segment, Double>>();
//
//        if (!(this instanceof RightTriangle) && !this.provenRight) return pairs;
//
//        //
//        // Make a right 
//        //
//        RightTriangle rightTri = new RightTriangle(this);
//        Angle otherAngle1, otherAngle2;
//        Angle rightAngle = rightTri.rightAngle;
//        OutPair<Angle, Angle> out = new OutPair(otherAngle1, otherAngle2);
//        rightTri.GetOtherAngles(rightAngle, out);
//        otherAngle1 = out.getKey();
//        otherAngle2 = out.getValue();
//        double angleMeasure1 = known.GetAngleMeasure(otherAngle1);
//        double angleMeasure2 = known.GetAngleMeasure(otherAngle2);
//
//        // Need to know one side length
//        if (angleMeasure1 < 0 && angleMeasure2 < 0) return pairs;
//
//        double knownSegVal = -1;
//        Segment knownSeg = null;
//        for (Segment side : orderedSides)
//        {
//            knownSegVal = known.GetSegmentLength(side);
//            if (knownSegVal > 0)
//            {
//                knownSeg = side;
//                break;
//            }
//        }
//
//        // Need to know one side length
//        if (knownSegVal < 0) return pairs;
//
//        // Need at least one measure.
//        if (angleMeasure1 > 0) return CalcSides(rightTri, rightAngle, otherAngle1, angleMeasure1, knownSeg, knownSegVal);
//        if (angleMeasure2 > 0) return CalcSides(rightTri, rightAngle, otherAngle2, angleMeasure2, knownSeg, knownSegVal);
//
//        return pairs;
//    }

    //
    // Given known values, can the third side be determined: isosceles right triangle (with base known)
    //
    //    public ArrayList<Pair<Segment, Double>> IsoscelesRightApplies(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        ArrayList<Pair<Segment, Double>> pairs = new ArrayList<Pair<Segment, Double>>();
    //
    //        if (!this.provenIsosceles || !this.provenRight) return pairs;
    //
    //        //
    //        // Make an isosceles triangle to acquire segments.
    //        //
    //        IsoscelesTriangle isoTri = new IsoscelesTriangle(this);
    //        Segment baseSeg = isoTri.baseSegment;
    //
    //        double baseVal = known.GetSegmentLength(baseSeg);
    //
    //        if (baseVal < -1) return pairs;
    //
    //        // Compute the value of the other sides.
    //        double otherSideVal = Math.Sqrt(Math.Pow(baseVal, 2) / 2.0);
    //
    //        // Get the other sides.
    //        Segment otherSide1, otherSide2;
    //        OutPair<Segment, Segment> out = new Outpair(otherSide1, otherSide2);
    //        isoTri.GetOtherSides(baseSeg, out);
    //        otherSide1 = out.getKey();
    //        otherSide2 = out.getValue();
    //
    //        pairs.Add(new Pair<Segment, Double>(otherSide1, otherSideVal));
    //        pairs.Add(new Pair<Segment, Double>(otherSide2, otherSideVal));
    //
    //        return pairs;
    //    }

    //
    // Given known values, can the third side be determined: isosceles right triangle (with base known)
    //
    //    public ArrayList<Pair<Segment, Double>> CalculateBaseOfIsosceles(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        ArrayList<Pair<Segment, Double>> pairs = new ArrayList<Pair<Segment, Double>>();
    //
    //        if (!this.provenIsosceles || !(this instanceof IsoscelesTriangle)) return pairs;
    //
    //        //
    //        // Make an isosceles triangle to acquire segments.
    //        //
    //        IsoscelesTriangle isoTri = new IsoscelesTriangle(this);
    //        Segment baseSeg = isoTri.baseSegment;
    //
    //        double baseVal = known.GetSegmentLength(baseSeg);
    //        if (baseVal > 0) return pairs;
    //
    //        //
    //        // Get the other sides.
    //        //
    //        Segment otherSide1, otherSide2;
    //        OutPair<Segment, Segment> out = new OutPair(otherSide1, otherSide2);
    //        isoTri.GetOtherSides(baseSeg, out);
    //        otherSide1 = out.getKey();
    //        otherSide2 = out.getValue();
    //        // If we know 1 we should know the other, check anyway
    //        double otherVal1 = known.GetSegmentLength(otherSide1);
    //        double otherVal2 = known.GetSegmentLength(otherSide2);
    //
    //        if (otherVal1 < 0 && otherVal2 < 0) return pairs;
    //
    //        //
    //        // Get the base angle.
    //        //
    //        double baseAngleVal = known.GetAngleMeasure(isoTri.baseAngleOppositeLeg1);
    //        if (baseAngleVal < 0) baseAngleVal = known.GetAngleMeasure(isoTri.baseAngleOppositeLeg2);
    //
    //        if (baseAngleVal < 0) return pairs;
    //
    //        //
    //        // Compute the value of the base
    //        //
    //        double baseSideVal = 2.0 * otherVal1 * Math.Cos(Angle.toRadians(baseAngleVal));
    //
    //        pairs.Add(new Pair<Segment, Double>(baseSeg, baseSideVal));
    //
    //        return pairs;
    //    }

    //
    // Given known values, can the third side be determined.
    //
    //    public Pair<Segment, Double> PythagoreanTheoremApplies(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        Pair<Segment, Double> nullPair = new Pair<Segment, Double>(null, -1);
    //
    //        if (!(this instanceof RightTriangle) && !this.provenRight) return nullPair;
    //
    //        // Acquire the known lengths and determine if 2 of 3 are known (1 unknown).
    //        Segment hypotenuse = GetHypotenuse();
    //        Segment otherSeg1, otherSeg2;
    //        Segment unknown = null;
    //        OutPair<Segment, Segment> out = new OutPair(otherSeg1, otherSeg2);
    //        GetOtherSides(hypotenuse, out);
    //        otherSeg1 = out.getKey();
    //        otherSeg2 = out.getValue();
    //
    //        int knownCount = 0;
    //        double hypLength = known.GetSegmentLength(hypotenuse);
    //        if (hypLength > 0) knownCount++;
    //        else unknown = hypotenuse;
    //
    //        double other1Length = known.GetSegmentLength(otherSeg1);
    //        if (other1Length > 0) knownCount++;
    //        else unknown = otherSeg1;
    //
    //        double other2Length = known.GetSegmentLength(otherSeg2);
    //        if (other2Length > 0) knownCount++;
    //        else unknown = otherSeg2;
    //
    //        if (knownCount != 2) return nullPair;
    //
    //        // Which side don't we know?
    //        // Hypotenuse unknown.
    //        if (hypLength < 0)
    //        {
    //            return new Pair<Segment, Double>(unknown, Math.Sqrt(Math.Pow(other1Length, 2) + Math.Pow(other2Length, 2)));
    //        }
    //        else
    //        {
    //            double otherKnown = other1Length < 0 ? other2Length : other1Length;
    //            return new Pair<Segment, Double>(unknown, Math.Sqrt(Math.Pow(hypLength, 2) - Math.Pow(otherKnown, 2)));
    //        }
    //    }

    //
    // Maintain a public repository of all triangles objects in the figure.
    //
    public static void Clear()
    {
        figureTriangles.clear();
    }
    public static ArrayList<Triangle> figureTriangles = new ArrayList<Triangle>();
    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
        if (clause instanceof Triangle) figureTriangles.add((Triangle)clause);
    }
    public static Triangle GetFigureTriangle(Point pt1, Point pt2, Point pt3)
    {
        Triangle candTriangle = new Triangle(pt1, pt2, pt3);

        // Search for exact segment first
        for (Triangle tri : figureTriangles)
        {
            if (tri.structurallyEquals(candTriangle)) return tri;
        }

        return null;
    }

    /// <summary>
    /// Determines if this is a right traingle.
    /// </summary>
    /// <returns>TRUE if this is a right triangle.</returns>
    public boolean isRightTriangle()
    {
        return  backend.utilities.math.MathUtilities.doubleEquals(AngleA.measure, 90) ||
                backend.utilities.math.MathUtilities.doubleEquals(AngleB.measure, 90) ||
                backend.utilities.math.MathUtilities.doubleEquals(AngleC.measure, 90);
    }

    /// <summary>
    /// Determines if this is an isosceles traingle.
    /// </summary>
    /// <returns>TRUE if this is an isosceles triangle.</returns>
    private boolean IsIsosceles()
    {
        Segment[] segments = new Segment[3];
        segments[0] = SegmentA;
        segments[1] = SegmentB;
        segments[2] = SegmentC;

        for (int i = 0; i < segments.length; i++)
        {
            if (segments[i]._length == segments[i + 1 < segments.length ? i + 1 : 0].length())
            {
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Determines if this is an equilateral traingle.
    /// </summary>
    /// <returns>TRUE if this is an equilateral triangle.</returns>
    private boolean IsEquilateral()
    {
        return backend.utilities.math.MathUtilities.doubleEquals(SegmentA.length(), SegmentB.length()) &&
                backend.utilities.math.MathUtilities.doubleEquals(SegmentB.length(), SegmentC.length());
    }

    public Angle GetOppositeAngle(Segment s)
    {
        Point oppVertex = this.OtherPoint(s);

        if (oppVertex.equals(AngleA.GetVertex())) return AngleA;
        if (oppVertex.equals(AngleB.GetVertex())) return AngleB;
        if (oppVertex.equals(AngleC.GetVertex())) return AngleC;

        return null;
    }

    public void GetOtherAngles(Angle that, OutPair<Angle, Angle> out)
    {
        Angle outAng1 = null;
        Angle outAng2 = null;

        if (AngleA.structurallyEquals(that))
        {
            outAng1 = AngleB;
            outAng2 = AngleC;
        }
        else if (AngleB.structurallyEquals(that))
        {
            outAng1 = AngleA;
            outAng2 = AngleC;
        }
        else if (AngleC.structurallyEquals(that))
        {
            outAng1 = AngleA;
            outAng2 = AngleB;
        }

        out = new OutPair<Angle, Angle>(outAng1, outAng2);
    }

    public void GetOtherSides(Segment s, OutPair<Segment, Segment> out)
    {
        Segment outSeg1 = null;
        Segment outSeg2 = null;

        if (s.structurallyEquals(SegmentA))
        {
            outSeg1 = SegmentB;
            outSeg2 = SegmentC;
        }
        else if (s.structurallyEquals(SegmentB))
        {
            outSeg1 = SegmentA;
            outSeg2 = SegmentC;
        }
        else if (s.structurallyEquals(SegmentC))
        {
            outSeg1 = SegmentA;
            outSeg2 = SegmentB;
        }
        out = new OutPair<Segment, Segment>(outSeg1, outSeg2);
    }

    public Segment GetOppositeSide(Angle angle)
    {
        Point vertex = angle.GetVertex();

        if (!SegmentA.hasPoint(vertex)) return SegmentA;
        if (!SegmentB.hasPoint(vertex)) return SegmentB;
        if (!SegmentC.hasPoint(vertex)) return SegmentC;

        return null;
    }

    public Segment GetOppositeSide(Point vertex)
    {
        if (!SegmentA.hasPoint(vertex)) return SegmentA;
        if (!SegmentB.hasPoint(vertex)) return SegmentB;
        if (!SegmentC.hasPoint(vertex)) return SegmentC;

        return null;
    }

    public Segment OtherSide(Angle a)
    {
        Point vertex = a.GetVertex();

        if (!SegmentA.hasPoint(vertex)) return SegmentA;
        if (!SegmentB.hasPoint(vertex)) return SegmentB;
        if (!SegmentC.hasPoint(vertex)) return SegmentC;

        return null;
    }

    public Segment GetSegmentWithPointOnOrExtends(Point pt)
    {
        if (SegmentA.PointLiesOn(pt)) return SegmentA;
        if (SegmentB.PointLiesOn(pt)) return SegmentB;
        if (SegmentC.PointLiesOn(pt)) return SegmentC;

        return null;
    }

    public Segment GetSegmentWithPointDirectlyOn(Point pt)
    {
        if (Segment.Between(pt, SegmentA.getPoint1(), SegmentA.getPoint2())) return SegmentA;
        if (Segment.Between(pt, SegmentB.getPoint1(), SegmentB.getPoint2())) return SegmentB;
        if (Segment.Between(pt, SegmentC.getPoint1(), SegmentC.getPoint2())) return SegmentC;

        return null;
    }

    public void MakeIsosceles()
    {
        if (!IsIsosceles())
        {
            //NC  Was Console.Write in source
            System.out.println("Deduced fact that this triangle is isosceles does not match empirical information for " + this.toString());
        }

        provenIsosceles = true;
    }

    @Override
    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }

    public boolean LiesOn(Segment cs)
    {
        return SegmentA.IsCollinearWith(cs) || SegmentB.IsCollinearWith(cs) || SegmentC.IsCollinearWith(cs);
    }

    // Does this triangle have this specific angle with these vertices
    private boolean HasThisSpecificAngle(Angle ca)
    {
        return (HasSegment(ca.ray1) && HasSegment(ca.ray2)); // Could call SharedVertex
    }

    // Does the given angle correspond to any of the angles?
    public boolean ExtendsAnAngle(Angle ca)
    {
        return ExtendsSpecificAngle(ca) != null;
    }

    // Does the given angle correspond to any of the angles?
    public Angle ExtendsSpecificAngle(Angle ca)
    {
        if (AngleA.Equates(ca)) return AngleA;
        if (AngleB.Equates(ca)) return AngleB;
        if (AngleC.Equates(ca)) return AngleC;

        return null;
    }

    // Does the given angle correspond to any of the angles?
    public Angle GetAngleWithVertex(Point pt)
    {
        if (AngleA.GetVertex().equals(pt)) return AngleA;
        if (AngleB.GetVertex().equals(pt)) return AngleB;
        if (AngleC.GetVertex().equals(pt)) return AngleC;

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
        if (SegmentA.equals(cs.SegmentA) || SegmentA.equals(cs.SegmentB) || SegmentA.equals(cs.SegmentC))
        {
            return SegmentA;
        }
        if (SegmentB.equals(cs.SegmentA) || SegmentB.equals(cs.SegmentB) || SegmentB.equals(cs.SegmentC))
        {
            return SegmentB;
        }
        if (SegmentC.equals(cs.SegmentA) || SegmentC.equals(cs.SegmentB) || SegmentC.equals(cs.SegmentC))
        {
            return SegmentC;
        }

        return null;
    }

    //
    // Acquire the particular angle which belongs to this triangle (of a congruence)
    //
    //    public Angle AngleBelongs(CongruentAngles ccas)
    //    {
    //        if (HasAngle(ccas.ca1)) return ccas.ca1;
    //        if (HasAngle(ccas.ca2)) return ccas.ca2;
    //        return null;
    //    }

    //
    // Acquire the particular angle which belongs to this triangle (of a congruence)
    //
    public Angle OtherAngle(Angle thatAngle1, Angle thatAngle2)
    {
        if (AngleA.Equates(thatAngle1) && AngleB.Equates(thatAngle2) || AngleA.Equates(thatAngle2) && AngleB.Equates(thatAngle1)) return AngleC;
        if (AngleB.Equates(thatAngle1) && AngleC.Equates(thatAngle2) || AngleB.Equates(thatAngle2) && AngleC.Equates(thatAngle1)) return AngleA;
        if (AngleA.Equates(thatAngle1) && AngleC.Equates(thatAngle2) || AngleA.Equates(thatAngle2) && AngleC.Equates(thatAngle1)) return AngleB;

        return null;
    }

    public boolean IsIncludedAngle(Segment s1, Segment s2, Angle a)
    {
        if (!HasSegment(s1) || !HasSegment(s2) && !HasAngle(a)) return false;

        // If the shared vertex between the segments is the vertex of this given angle, then
        // the angle is the included angle as desired
        return s1.SharedVertex(s2).equals(a.GetVertex());
    }

    // Of the congruent pair, return the segment that applies to this triangle
    //    public Segment GetSegment(CongruentSegments ccss)
    //    {
    //        if (HasSegment(ccss.cs1)) return ccss.cs1;
    //        if (HasSegment(ccss.cs2)) return ccss.cs2;
    //
    //        return null;
    //    }

    // Of the propportional pair, return the segment that applies to this triangle
    //    public Segment GetSegment(SegmentRatio prop)
    //    {
    //        if (HasSegment(prop.smallerSegment)) return prop.smallerSegment;
    //        if (HasSegment(prop.largerSegment)) return prop.largerSegment;
    //
    //        return null;
    //    }

    public boolean HasPoint(Point p)
    {
        if (Point1.equals(p)) return true;
        if (Point2.equals(p)) return true;
        if (Point3.equals(p)) return true;

        return false;
    }

    public Point OtherPoint(Segment cs)
    {
        if (!cs.hasPoint(Point1)) return Point1;
        if (!cs.hasPoint(Point2)) return Point2;
        if (!cs.hasPoint(Point3)) return Point3;

        return null;
    }

    public Point OtherPoint(Point p1, Point p2)
    {
        if (SegmentA.hasPoint(p1) && SegmentA.hasPoint(p2)) return OtherPoint(SegmentA);
        if (SegmentB.hasPoint(p1) && SegmentB.hasPoint(p2)) return OtherPoint(SegmentB);
        if (SegmentC.hasPoint(p1) && SegmentC.hasPoint(p2)) return OtherPoint(SegmentC);

        return null;
    }

    public Pair<Angle, Angle> GetAcuteAngles()
    {
        if (AngleA.measure >= 90) return new Pair<Angle,Angle>(AngleB, AngleC);
        if (AngleB.measure >= 90) return new Pair<Angle,Angle>(AngleA, AngleC);
        if (AngleC.measure >= 90) return new Pair<Angle,Angle>(AngleA, AngleB);

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
    public Segment GetLongestSide()
    {
        if (SegmentA.length() > SegmentB.length())
        {
            if (SegmentA.length() > SegmentC.length())
            {
                return SegmentA;
            }
        }
        else if (SegmentB.length() > SegmentC.length())
        {
            return SegmentB;
        }

        return SegmentC;
    }

    //
    // return the hypotenuse if we know we have a right triangle
    //
    public Segment GetHypotenuse()
    {
        if (!isRight) return null;

        return GetLongestSide();
    }

    //
    // Two sides known, return the third side
    //
    public Segment OtherSide(Segment s1, Segment s2)
    {
        if (!HasSegment(s1) || !HasSegment(s2)) return null;
        if (!SegmentA.equals(s1) && !SegmentA.equals(s2)) return SegmentA;
        if (!SegmentB.equals(s1) && !SegmentB.equals(s2)) return SegmentB;
        if (!SegmentC.equals(s1) && !SegmentC.equals(s2)) return SegmentC;
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
    //        //tri.SegmentA.SetJustification("Intrinsic");
    //        //tri.SegmentB.SetJustification("Intrinsic");
    //        //tri.SegmentC.SetJustification("Intrinsic");
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.SegmentA, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.SegmentB, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.SegmentC, intrinsicAnnotation));
    //
    //        //tri.AngleA.SetJustification("Intrinsic");
    //        //tri.AngleB.SetJustification("Intrinsic");
    //        //tri.AngleC.SetJustification("Intrinsic");
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.AngleA, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.AngleB, intrinsicAnnotation));
    //        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, tri.AngleC, intrinsicAnnotation));
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
        Angle triangleAngle = GetAngleWithVertex(extAngle.GetVertex());

        if (triangleAngle == null) return false;

        // Acquire the ray that is shared between the angle and the triangle
        Segment sharedSegment = triangleAngle.SharedRay(extAngle);

        if (sharedSegment == null) return false;

        // Acquire the other side of the triangle
        Segment otherTriangleSegment = triangleAngle.OtherRay(sharedSegment);

        if (otherTriangleSegment == null) return false;

        // Acquire the ray that is not shared
        Segment exteriorSegment = extAngle.OtherRay(sharedSegment);

        if (exteriorSegment == null) return false;

        //           DISALLOW                                     ALLOW
        //              /                                           /
        //             / \                                         / \
        //            /TRI\                                       /TRI\
        //           /-----\                                     /-----\
        //                 /                                            \
        //                /                                              \
        //               /                                                \
        return otherTriangleSegment.IsCollinearWith(exteriorSegment);
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
    public Pair<Segment, Segment> OtherSides(Segment candidate)
    {
        if (SegmentA.equals(candidate)) return new Pair<Segment,Segment>(SegmentB, SegmentC);
        if (SegmentB.equals(candidate)) return new Pair<Segment, Segment>(SegmentA, SegmentC);
        if (SegmentC.equals(candidate)) return new Pair<Segment, Segment>(SegmentA, SegmentB);

        return new Pair<Segment, Segment>(null, null);
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
    public Segment CoincidesWithASide(Segment candidate)
    {
        if (SegmentA.IsCollinearWith(candidate)) return SegmentA;
        if (SegmentB.IsCollinearWith(candidate)) return SegmentB;
        if (SegmentC.IsCollinearWith(candidate)) return SegmentC;

        return null;
    }

    // Determine if the given segment is coinciding with one of the triangle sides; return that 
    //    public Segment DoesParallelCoincideWith(Parallel p)
    //    {
    //        if (CoincidesWithASide(p.segment1) != null) return p.segment1;
    //        if (CoincidesWithASide(p.segment2) != null) return p.segment2;
    //
    //        return null;
    //    }

    //
    // Given a point on the triangle, return the two angles not at that vertex
    //
    public void AcquireRemoteAngles(Point givenVertex, OutPair<Angle, Angle> out)
    {
        Angle remote1 = null;
        Angle remote2 = null;

        if (!HasPoint(givenVertex)) return;

        if (AngleA.GetVertex().equals(givenVertex))
        {
            remote1 = AngleB;
            remote2 = AngleC;
        }
        else if (AngleB.GetVertex().equals(givenVertex))
        {
            remote1 = AngleA;
            remote2 = AngleC;
        }
        else
        {
            remote1 = AngleA;
            remote2 = AngleB;
        }
        out = new OutPair<Angle, Angle>(remote1, remote2);
    }

    //
    // Have we deduced a congrunence between this triangle and t already?
    //
    public boolean HasEstablishedCongruence(Triangle t)
    {
        return congruencePairs.contains(t);
    }

    // Add to the list of congruent triangles
    public void AddCongruentTriangle(Triangle t)
    {
        congruencePairs.add(t);
    }

    //
    // Have we deduced a similarity between this triangle and t already?
    //
    public boolean HasEstablishedSimilarity(Triangle t)
    {
        return similarPairs.contains(t);
    }

    // Add to the list of similar triangles
    public void AddSimilarTriangle(Triangle t)
    {
        similarPairs.add(t);
    }

    public Point GetVertexOn(Segment thatSegment)
    {
        if (Segment.IntersectAtSamePoint(this.SegmentA, this.SegmentB, thatSegment)) return SegmentA.SharedVertex(SegmentB);
        if (Segment.IntersectAtSamePoint(this.SegmentA, this.SegmentC, thatSegment)) return SegmentA.SharedVertex(SegmentC);
        if (Segment.IntersectAtSamePoint(this.SegmentB, this.SegmentC, thatSegment)) return SegmentB.SharedVertex(SegmentC);

        return null;
    }

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
            triThis.add(corrSegmentsThis.get(i).SharedVertex(corrSegmentsThis.get(i + 1 < 3 ? i + 1 : 0)));
            triThat.add(corrSegmentsThat.get(i).SharedVertex(corrSegmentsThat.get(i + 1 < 3 ? i + 1 : 0)));
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

    
     //Can this triangle be strengthened to Isosceles, Equilateral, Right, or Right Isosceles?
    
        public static ArrayList<Strengthened> CanBeStrengthened(Triangle thatTriangle)
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
                if (thatTriangle.isEquilateral)
                {
                    strengthened.add(new Strengthened(thatTriangle, new EquilateralTriangle(thatTriangle)));
                }
    
                //
                // Isosceles -> Right Isosceles
                //
                if (thatTriangle.isRight)
                {
                    strengthened.add(new Strengthened(thatTriangle, new RightTriangle(thatTriangle)));
                }
    
                return strengthened;
            }
    
            //
            // Scalene -> Isosceles
            //
            if (thatTriangle.isIsosceles)
            {
                strengthened.add(new Strengthened(thatTriangle, new IsoscelesTriangle(thatTriangle)));
            }
    
            //
            // Scalene -> Equilateral
            //
            if (thatTriangle.isEquilateral)
            {
                strengthened.add(new Strengthened(thatTriangle, new EquilateralTriangle(thatTriangle)));
            }
    
            //
            // Scalene -> Right
            //
            if (!(thatTriangle instanceof RightTriangle))
            {
                if (thatTriangle.isRight)
                {
                    strengthened.add(new Strengthened(thatTriangle, new RightTriangle(thatTriangle)));
                }
            }
    
            return strengthened;
        }

    @Override
    public boolean canBeStrengthenedTo(GroundedClause gc)
    {
        if(gc == null) return false;
        if(!(gc instanceof Triangle)) return false;
        Triangle tri = (Triangle)gc;

        //if (gc is Strengthened)
        //{
        //    return this.StructurallyEquals((gc as Strengthened).original);
        //}

        // Handles isosceles, right, or equilateral
        if (!this.structurallyEquals(gc)) return false;

        // Ensure we know the original has been 'proven' (given) to be a particular type of triangle
        if (tri.provenIsosceles) this.provenIsosceles = true;
        if (tri.provenEquilateral) this.provenEquilateral = true;
        if (tri.provenRight) this.provenRight = true;

        return true;
    }

    public boolean CoordinateMedian(Segment thatSegment)
    {
        //
        // Two sides must intersect the median at a single point
        //
        Point midptIntersection = null;
        Point coincidingIntersection = null;
        Segment oppSide = null;
        if (Segment.IntersectAtSamePoint(SegmentA, SegmentB, thatSegment))
        {
            coincidingIntersection = SegmentA.FindIntersection(SegmentB);
            midptIntersection = SegmentC.FindIntersection(thatSegment);
            oppSide = SegmentC;
        }
        else if (Segment.IntersectAtSamePoint(SegmentA, SegmentC, thatSegment))
        {
            coincidingIntersection = SegmentA.FindIntersection(SegmentC);
            midptIntersection = SegmentB.FindIntersection(thatSegment);
            oppSide = SegmentB;
        }
        else if (Segment.IntersectAtSamePoint(SegmentB, SegmentC, thatSegment))
        {
            coincidingIntersection = SegmentB.FindIntersection(SegmentC);
            midptIntersection = SegmentA.FindIntersection(thatSegment);
            oppSide = SegmentA;
        }

        if (midptIntersection == null || oppSide == null) return false;

        // It is possible for the segment to be parallel to the opposite side; results in NaN.
        if (midptIntersection.getX() == Double.NaN || midptIntersection.getY() == Double.NaN) return false;

        // The intersection must be on the potential median
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(coincidingIntersection)) return false;

        // The midpoint intersection must be on the potential median
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(midptIntersection)) return false;

        if (!Segment.Between(coincidingIntersection, thatSegment.getPoint1(), thatSegment.getPoint2())) return false;

        if (!oppSide.PointLiesOnAndBetweenEndpoints(midptIntersection)) return false;

        // Midpoint of the remaining side needs to align
        return midptIntersection.equals(oppSide.Midpoint());
    }

    //
    // Is this segment an altitude based on the coordinates (precomputation)
    //
    public boolean CoordinateAltitude(Segment thatSegment)
    {
        //
        // Check to see if the altitude is actually one of the sides of the triangle
        //
        if (this.HasSegment(thatSegment) && this.isRight)
        {
            // Find the right angle; the altitude must be one of those segments
            if (backend.utilities.math.MathUtilities.doubleEquals(this.AngleA.measure, 90)) return AngleA.HasSegment(thatSegment);
            if (backend.utilities.math.MathUtilities.doubleEquals(this.AngleB.measure, 90)) return AngleB.HasSegment(thatSegment);
            if (backend.utilities.math.MathUtilities.doubleEquals(this.AngleC.measure, 90)) return AngleC.HasSegment(thatSegment);
        }

        //
        // Two sides must intersect the given segment at a single point
        //
        Point otherIntersection = null;
        Point thisIntersection = null;
        Segment oppSide = null;
        if (Segment.IntersectAtSamePoint(SegmentA, SegmentB, thatSegment))
        {
            thisIntersection = SegmentA.FindIntersection(SegmentB);
            otherIntersection = SegmentC.FindIntersection(thatSegment);
            oppSide = SegmentC;
        }
        if (Segment.IntersectAtSamePoint(SegmentA, SegmentC, thatSegment))
        {
            thisIntersection = SegmentA.FindIntersection(SegmentC);
            otherIntersection = SegmentB.FindIntersection(thatSegment);
            oppSide = SegmentB;
        }
        if (Segment.IntersectAtSamePoint(SegmentB, SegmentC, thatSegment))
        {
            thisIntersection = SegmentB.FindIntersection(SegmentC);
            otherIntersection = SegmentA.FindIntersection(thatSegment);
            oppSide = SegmentA;
        }

        if (otherIntersection == null || oppSide == null) return false;

        // Avoid a dangling altitude:
        //
        // |\
        // | \
        // |  \
        //     \
        // Need to make sure 'this' and the the 'other' intersection is actually on the potential altitude segment
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(thisIntersection)) return false;
        if (!thatSegment.PointLiesOnAndBetweenEndpoints(otherIntersection)) return false;

        // We require a perpendicular intersection
        return backend.utilities.math.MathUtilities.doubleEquals((new Angle(thisIntersection, otherIntersection, oppSide.getPoint1())).measure, 90);
    }

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

    //
    // Area-Related Computations
    //
    // Hero's Formula.
    protected double HeroArea(double s1, double s2, double s3)
    {
        double semip = 0.5 * (s1 + s2 + s3);

        return Math.sqrt(semip * (semip - s1) * (semip - s2) * (semip - s3));
    }
    // SAS: 1/2 a * b * sin C
    protected double TrigArea(double a, double b, double theta)
    {
        return 0.5 * a * b * Math.sin(Angle.toRadians(theta));
    }
    // Classic: 1/2 base * height
    protected double Area(double b, double h)
    {
        return 0.5 * b * h;
    }
    protected double RationalArea(double b, double h) { return Area(b, h); }
    protected double RationalArea(double s1, double s2, double s3) { return HeroArea(s1, s2, s3); }
    @Override
    public boolean IsComputableArea() { return true; }

    // Does Hero's Formula apply?
    //    private double HeroArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        ArrayList<Double> lengths = new ArrayList<Double>();
    //
    //        for (Segment side : orderedSides)
    //        {
    //            double leng = known.GetSegmentLength(side);
    //
    //            if (leng < 0) return -1;
    //
    //            lengths.add(leng);
    //        }
    //
    //        return HeroArea(lengths.get(0), lengths.get(1), lengths.get(2));
    //    }

    // Does the included Trig Formula apply?
    //    private double TrigArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        for (int s = 0; s < orderedSides.size(); s++)
    //        {
    //            double a = known.GetSegmentLength(orderedSides.get(s));
    //            double b = known.GetSegmentLength(orderedSides.get((s+1) % orderedSides.size()));
    //
    //            double theta = known.GetAngleMeasure(new Angle(orderedSides.get(s), orderedSides.get((s+1) % orderedSides.size())));
    //
    //            if (a > 0 && b > 0 && theta > 0)
    //            {
    //                return TrigArea(a, b, theta);
    //            }
    //        }
    //
    //        return -1;
    //    }

    // Does the included Trig Formula apply?
    //    private double ClassicArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        // Right triangle with height being one of the sides.
    //        if (this instanceof RightTriangle || this.provenRight)
    //        {
    //            Segment hypotenuse = GetHypotenuse();
    //            Segment otherSeg1, otherSeg2;
    //            OutPair<Segment, Segment> out = new OutPair(otherSeg1, otherSeg2);
    //            GetOtherSides(hypotenuse, out);
    //            otherSeg1 = out.getKey();
    //            otherSeg2 = out.getValue();
    //
    //            double b = known.GetSegmentLength(otherSeg1);
    //            double h = known.GetSegmentLength(otherSeg2);
    //
    //            return b > 0 && h > 0 ? Area(b, h) : -1;
    //        }
    //
    //        for (int s = 0; s < orderedSides.size(); s++)
    //        {
    //            double b = known.GetSegmentLength(orderedSides.get(s));
    //
    //            //
    //            // How to handle heights? Need to have altitudes for this triangle saved.
    //            //
    //            int h = 0;
    //
    //            if (b > 0 && h > 0)
    //            {
    //                return Area(b, h);
    //            }
    //        }
    //
    //        return -1;
    //    }

    //    @Override
    //    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        if (ClassicArea(known) > 0) return true;
    //
    //        if (TrigArea(known) > 0) return true;
    //
    //        if (HeroArea(known) > 0) return true;
    //
    //        return false;
    //    }

    //    @Override
    //    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //    {
    //        double area = ClassicArea(known);
    //        if (area > 0) return area;
    //
    //        area = TrigArea(known);
    //        if (area > 0) return area;
    //
    //        return HeroArea(known);
    //    }


    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Triangle)) return false;
        Triangle thatTriangle = (Triangle)obj;

        return thatTriangle.HasPoint(this.Point1) && thatTriangle.HasPoint(this.Point2) && thatTriangle.HasPoint(this.Point3);
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
            
    
            // Is this a strengthened version?
            if (triangle.provenIsosceles != this.provenIsosceles) return false;
    
            // Is this a strenghtened version?
            if (triangle.provenRight != this.provenRight) return false;
    
            return structurallyEquals(obj) && super.equals(obj);
        }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder();

        if (provenEquilateral)
        {
            str.append("Equilateral ");
        }
        else if (provenIsosceles)
        {
            str.append("Isosceles ");
        }

        if (provenRight)
        {
            str.append("Right ");
        }
        str.append("Triangle(" + Point1.toString() + ", " + Point2.toString() + ", " + Point3.toString() + ") ");
        return str.toString();
    }

    @Override
    public String toPrettyString()
    {
        StringBuilder str = new StringBuilder();

        if (provenEquilateral)
        {
            str.append("Equilateral ");
        }
        else if (provenIsosceles)
        {
            str.append("Isosceles ");
        }

        if (provenRight)
        {
            str.append("Right ");
        }
        str.append("Triangle(" + Point1.toPrettyString() + ", " + Point2.toPrettyString() + ", " + Point3.toPrettyString() + ") ");
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
    //        str.append("Tri(" + Point1.CheapPrettyString() + Point2.CheapPrettyString() + Point3.CheapPrettyString() + ")");
    //
    //        return str.toString();
}