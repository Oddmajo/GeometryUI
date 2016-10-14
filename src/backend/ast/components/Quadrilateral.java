package ast.figure.components;

import java.util.ArrayList;
import java.util.List;

import ast.GroundedClause;
import utilities.Pair;
import utilities.translation.OutPair;


/// <summary>
/// Represents a quadrilateral (defined by 4 segments)
/// </summary>
public class Quadrilateral extends Polygon
{
    public Point topLeft;
    public Point getTopLeft() { return topLeft; }
    public Point topRight;
    public Point getTopRight() { return topRight; }
    public Point bottomLeft;
    public Point getBottomLeft() { return bottomLeft; }
    public Point bottomRight;
    public Point getBottomRight() { return bottomRight; }

    public Segment left;
    public Segment getLeft() { return left; }
    public Segment right;
    public Segment getRight() { return right; }
    public Segment top;
    public Segment getTop() { return top; }
    public Segment bottom;
    public Segment getBottom() { return bottom; }

    public Angle topLeftAngle;
    public Angle getTopLeftAngle() { return topLeftAngle; }
    public Angle topRightAngle;
    public Angle getTopRightAngle() { return topRightAngle; }
    public Angle bottomLeftAngle;
    public Angle getBottomLeftAngle() { return bottomLeftAngle; }
    public Angle bottomRightAngle;
    public Angle getBottomRightAngle() { return bottomRightAngle; }
    
    //
    // Diagonals
    //
//    public Intersection diagonalIntersection { get; private set; }
//    public void SetIntersection(Intersection diag) { diagonalIntersection = diag; }

    protected Segment topLeftBottomRightDiagonal;
    public Segment getTLBRDiagonal() { return topLeftBottomRightDiagonal; }
    private boolean tlbrDiagonalValid = true;
    public boolean TopLeftDiagonalIsValid() { return tlbrDiagonalValid; }
    public void SetTopLeftDiagonalInValid() { tlbrDiagonalValid = false; }
    private Pair<Triangle, Triangle> triPairTLBR;

    protected Segment bottomLeftTopRightDiagonal;
    public Segment getBLTRDiagonal() { return bottomLeftTopRightDiagonal; }
    private boolean bltrDiagonalValid = true;
    public boolean BottomRightDiagonalIsValid() { return bltrDiagonalValid; }
    public void SetBottomRightDiagonalInValid() { bltrDiagonalValid = false; }
    private Pair<Triangle, Triangle> triPairBLTR;

    public Quadrilateral(Segment left, Segment right, Segment top, Segment bottom)
    {
        super();
        
        //
        // Segments
        //
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        orderedSides = new ArrayList<Segment>();
        orderedSides.add(left);
        orderedSides.add(top);
        orderedSides.add(right);
        orderedSides.add(bottom);

        //
        // Points
        //
        this.topLeft = left.SharedVertex(top);
        if (topLeft == null)
        {
            return;
            // throw new ArgumentException("Top left point is invalid: " + top + " " + left);
        }
        this.topRight = right.SharedVertex(top);
        if (topRight == null) throw new IllegalArgumentException("Top left point is invalid: " + top + " " + right);

        this.bottomLeft = left.SharedVertex(bottom);
        if (bottomLeft == null) throw new IllegalArgumentException("Bottom left point is invalid: " + bottom + " " + left);

        this.bottomRight = right.SharedVertex(bottom);
        if (bottomRight == null) throw new IllegalArgumentException("Bottom right point is invalid: " + bottom + " " + right);

        points = new ArrayList<Point>();
        points.add(topLeft);
        points.add(topRight);
        points.add(bottomRight);
        points.add(bottomLeft);

        // Verify that we have 4 unique points
        for (int i = 0; i < points.size() - 1; i++)
        {
            for (int j = i + 1; j < points.size(); j++)
            {
                if (points.get(i).StructurallyEquals(points.get(j)))
                {
                    throw new IllegalArgumentException("Points of quadrilateral are not distinct: " + points.get(i) + " " + points.get(j));
                }
            }
        }

        //
        // Diagonals
        //
        this.topLeftBottomRightDiagonal = new Segment(topLeft, bottomRight);
        this.bottomLeftTopRightDiagonal = new Segment(bottomLeft, topRight);
        //this.diagonalIntersection = null;
        triPairTLBR = new Pair<Triangle, Triangle>(new Triangle(topLeft, bottomLeft, bottomRight), new Triangle(topLeft, topRight, bottomRight));
        triPairBLTR = new Pair<Triangle, Triangle>(new Triangle(bottomLeft, topLeft, topRight), new Triangle(bottomLeft, bottomRight, topRight));

        //
        // Angles
        //
        this.topLeftAngle = new Angle(bottomLeft, topLeft, topRight);
        this.topRightAngle = new Angle(topLeft, topRight, bottomRight);
        this.bottomRightAngle = new Angle(topRight, bottomRight, bottomLeft);
        this.bottomLeftAngle = new Angle(bottomRight, bottomLeft, topLeft);

        angles = new ArrayList<Angle>();
        angles.add(topLeftAngle);
        angles.add(topRightAngle);
        angles.add(bottomLeftAngle);
        angles.add(bottomRightAngle);

        // this.FigureSynthesizerConstructor();

        addSuperFigureToDependencies();
    }

    public Quadrilateral(List<Segment> segs)
    {
        this(segs.get(0), segs.get(1), segs.get(2), segs.get(3));
        
        if (segs.size() != 4) throw new IllegalArgumentException("Quadrilateral constructed with " + segs.size() + " segments.");
    }

    public static Quadrilateral MakeQuadrilateral(Point a, Point b, Point c, Point d)
    {
        Segment left = new Segment(a, d);
        Segment right = new Segment(b, c);
        Segment top = new Segment(a, b);
        Segment bottom = new Segment(c, d);

        Quadrilateral quad = null;
        try
        {
            quad = new Quadrilateral(left, right, top, bottom);
        }
        catch (Exception e)
        {
            left = new Segment(a, d);
            right = new Segment(b, c);
            top = new Segment(a, c);
            bottom = new Segment(b, d);

            quad = new Quadrilateral(left, right, top, bottom);
        }

        return quad;
    }

    protected void addSuperFigureToDependencies()
    {
        utilities.list.Utilities.addUniqueStructurally(topLeft.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(topRight.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(bottomLeft.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(bottomRight.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(left.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(right.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(bottom.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(top.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(topLeftAngle.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(topRightAngle.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(bottomLeftAngle.getSuperFigures(), this);
        utilities.list.Utilities.addUniqueStructurally(bottomRightAngle.getSuperFigures(), this);
    }

    public boolean IsStrictQuadrilateral()
    {
//        if (this instanceof Parallelogram) return false;
//        if (this instanceof Trapezoid) return false;
//        if (this instanceof Kite) return false;

        return true;
    }

    //
    // Acquire one of the quadrilateral angles.
    //
    public Angle GetAngle(Angle thatAngle)
    {
        for (Angle angle : angles)
        {
            if (angle.equates(thatAngle)) return angle;
        }

        return null;
    }

    //
    // Does this quadrilateral have the given side (exactly, no extension)?
    //
    public boolean HasAngle(Angle thatAngle)
    {
        for (Angle angle : angles)
        {
            if (angle.equates(thatAngle)) return true;
        }

        return false;
    }

    //
    // Does this quadrilateral have the given side (exactly, no extension)?
    //
    public boolean HasSide(Segment segment)
    {
        for(Segment side : orderedSides)
        {
            if (side.StructurallyEquals(segment)) return true;
        }

        return false;
    }

    //
    // Does this quadrilateral contain the segment on a side?
    //
    public Segment HasSubsegmentSide(Segment segment)
    {
        for (Segment side : orderedSides)
        {
            if (side.HasSubSegment(segment)) return side;
        }

        return null;
    }

    //
    // Are the two given segments on the opposite sides of this quadrilateral?
    //
    public boolean AreOppositeSides(Segment segment1, Segment segment2)
    {
        if (!this.HasSide(segment1) || !this.HasSide(segment2)) return false;

        if (top.StructurallyEquals(segment1) && bottom.StructurallyEquals(segment2)) return true;
        if (top.StructurallyEquals(segment2) && bottom.StructurallyEquals(segment1)) return true;

        if (left.StructurallyEquals(segment1) && right.StructurallyEquals(segment2)) return true;
        if (left.StructurallyEquals(segment2) && right.StructurallyEquals(segment1)) return true;

        return false;
    }

    //
    // Are the two given segments on the opposite sides of this quadrilateral?
    //
    public boolean AreOppositeSubsegmentSides(Segment segment1, Segment segment2)
    {
        if (this.HasSubsegmentSide(segment1) == null || this.HasSubsegmentSide(segment2) == null) return false;

        if (top.HasSubSegment(segment1) && bottom.HasSubSegment(segment2)) return true;
        if (top.HasSubSegment(segment2) && bottom.HasSubSegment(segment1)) return true;

        if (left.HasSubSegment(segment1) && right.HasSubSegment(segment2)) return true;
        if (left.HasSubSegment(segment2) && right.HasSubSegment(segment1)) return true;

        return false;
    }

    //
    // Are the two given angles on the opposite sides of this quadrilateral?
    //
    public boolean AreOppositeAngles(Angle angle1, Angle angle2)
    {
        if (!this.HasAngle(angle1) || !this.HasAngle(angle2)) return false;

        if (topLeftAngle.equates(angle1) && bottomRightAngle.equates(angle2)) return true;
        if (topLeftAngle.equates(angle2) && bottomRightAngle.equates(angle1)) return true;

        if (topRightAngle.equates(angle1) && bottomLeftAngle.equates(angle2)) return true;
        if (topRightAngle.equates(angle2) && bottomLeftAngle.equates(angle1)) return true;

        return false;
    }

    //
    // Are the two given segments adjacent with this quadrilateral?
    //
    public boolean AreAdjacentSides(Segment segment1, Segment segment2)
    {
        if (!this.HasSide(segment1) || !this.HasSide(segment2)) return false;

        if (top.StructurallyEquals(segment1) && left.StructurallyEquals(segment2)) return true;
        if (top.StructurallyEquals(segment2) && left.StructurallyEquals(segment1)) return true;

        if (top.StructurallyEquals(segment2) && right.StructurallyEquals(segment1)) return true;
        if (top.StructurallyEquals(segment1) && right.StructurallyEquals(segment2)) return true;

        if (bottom.StructurallyEquals(segment1) && left.StructurallyEquals(segment2)) return true;
        if (bottom.StructurallyEquals(segment2) && left.StructurallyEquals(segment1)) return true;

        if (bottom.StructurallyEquals(segment2) && right.StructurallyEquals(segment1)) return true;
        if (bottom.StructurallyEquals(segment1) && right.StructurallyEquals(segment2)) return true;

        return false;
    }

//    //
//    // Does this parallel set apply to this quadrilateral?
//    //
//    public boolean HasOppositeParallelSides(Parallel parallel)
//    {
//        return AreOppositeSides(parallel.segment1, parallel.segment2);
//    }
//
//    //
//    // Does this parallel set apply to this quadrilateral?
//    //
//    public boolean HasOppositeParallelSubsegmentSides(Parallel parallel)
//    {
//        return AreOppositeSubsegmentSides(parallel.segment1, parallel.segment2);
//    }
//
//    //
//    // Does this congruent pair apply to this quadrilateral?
//    //
//    public boolean HasOppositeCongruentSides(CongruentSegments cs)
//    {
//        return AreOppositeSides(cs.cs1, cs.cs2);
//    }
//
//    //
//    // Does this congruent pair of angles apply to this quadrilateral?
//    //
//    public boolean HasOppositeCongruentAngles(CongruentAngles cas)
//    {
//        return AreOppositeAngles(cas.ca1, cas.ca2);
//    }
//
//    //
//    // Does this parallel set apply to this quadrilateral?
//    //
//    public boolean HasAdjacentCongruentSides(CongruentSegments cs)
//    {
//        return AreAdjacentSides(cs.cs1, cs.cs2);
//    }

    //
    // Acquire the other 2 sides not : this parallel relationship; works for a n-gon (polygon) as well.
    //
    public List<Segment> GetOtherSides(List<Segment> inSegments)
    {
        List<Segment> outSegments = new ArrayList<Segment>();

        // This quadrilateral must have these given segments to return valid data.
        for (Segment inSeg : inSegments)
        {
            if (!this.HasSide(inSeg)) return outSegments;
        }

        //
        // Traverse given segments partitioning this quad into : / out.
        //
        for (Segment side : orderedSides)
        {
            if (!inSegments.contains(side))
            {
                outSegments.add(side);
            }
        }

        return outSegments;
    }

    //
    // Acquire the other 2 sides not : this parallel relationship; works for a n-gon (polygon) as well.
    //
    public List<Segment> GetOtherSubsegmentSides(List<Segment> inSegments)
    {
        // This quadrilateral must have these given segments to return valid data.
        List<Segment> inSegsMappedToQuad = new ArrayList<Segment>();
        for (Segment inSeg : inSegments)
        {
            Segment side = this.HasSubsegmentSide(inSeg);
            if (side == null) return new ArrayList<Segment>();
            inSegsMappedToQuad.add(side);
        }

        //
        // Traverse given segments partitioning this quad into : / out.
        //
        List<Segment> outSegments = new ArrayList<Segment>();
        for (Segment side : orderedSides)
        {
            if (!inSegsMappedToQuad.contains(side))
            {
                outSegments.add(side);
            }
        }

        return outSegments;
    }

//    //
//    // Acquire the other 2 sides not : this parallel relationship.
//    //
//    public List<Segment> GetOtherSides(Parallel parallel)
//    {
//        List<Segment> segs = new ArrayList<Segment>();
//        segs.add(parallel.segment1);
//        segs.add(parallel.segment2);
//
//        return GetOtherSides(segs);
//    }
//
//    //
//    // Acquire the other 2 sides not : this parallel relationship.
//    //
//    public List<Segment> GetOtherSubsegmentSides(Parallel parallel)
//    {
//        List<Segment> segs = new ArrayList<Segment>();
//        segs.add(parallel.segment1);
//        segs.add(parallel.segment2);
//
//        return GetOtherSubsegmentSides(segs);
//    }
//
//    //
//    // Coordinate-based determination if we have a parallelogram.
//    //
//    public boolean VerifyParallelogram()
//    {
//        if (!left.IsParallelWith(right)) return false;
//        if (!top.IsParallelWith(bottom)) return false;
//
//        if (!Utilities.doubleEquals(left.Length, right.Length)) return false;
//        if (!Utilities.doubleEquals(top.Length, bottom.Length)) return false;
//
//        if (!Utilities.doubleEquals(topLeftAngle.measure, bottomRightAngle.measure)) return false;
//        if (!Utilities.doubleEquals(topRightAngle.measure, bottomLeftAngle.measure)) return false;
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have a rhombus.
//    //
//    public boolean VerifyRhombus()
//    {
//        if (!VerifyParallelogram()) return false;
//
//        if (!Utilities.doubleEquals(left.Length, top.Length)) return false;
//        if (!Utilities.doubleEquals(left.Length, bottom.Length)) return false;
//
//        // Redundant
//        if (!Utilities.doubleEquals(right.Length, top.Length)) return false;
//        if (!Utilities.doubleEquals(right.Length, bottom.Length)) return false;
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have a Square
//    //
//    public boolean VerifySquare()
//    {
//        if (!VerifyRhombus()) return false;
//
//        if (!Utilities.doubleEquals(topLeftAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(topRightAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(bottomLeftAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(bottomRightAngle.measure, 90)) return false;
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have a Rectangle
//    //
//    public boolean VerifyRectangle()
//    {
//        if (!VerifyParallelogram()) return false;
//
//        if (!Utilities.doubleEquals(topLeftAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(topRightAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(bottomLeftAngle.measure, 90)) return false;
//        if (!Utilities.doubleEquals(bottomRightAngle.measure, 90)) return false;
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have a Trapezoid
//    //
//    public boolean VerifyTrapezoid()
//    {
//        boolean lrParallel = left.IsParallelWith(right);
//        boolean tbParallel = top.IsParallelWith(bottom);
//
//        // XOR of parallel opposing sides
//        if (lrParallel && tbParallel) return false;
//        if (!lrParallel && !tbParallel) return false;
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have an Isosceles Trapezoid
//    //
//    public boolean VerifyIsoscelesTrapezoid()
//    {
//        if (!VerifyTrapezoid()) return false;
//
//        //
//        // Determine parallel sides, then compare lengths of other sides
//        //
//        if (left.IsParallelWith(right))
//        {
//            if (!Utilities.doubleEquals(top.Length, bottom.Length)) return false;
//        }
//        else if (top.IsParallelWith(bottom))
//        {
//            if (!Utilities.doubleEquals(left.Length, right.Length)) return false;
//        }
//
//        return true;
//    }
//
//    //
//    // Coordinate-based determination if we have an Isosceles Trapezoid
//    //
//    public boolean VerifyKite()
//    {
//        //
//        // Adjacent sides must equate : length
//        //
//        if (Utilities.doubleEquals(left.Length, top.Length) && Utilities.doubleEquals(right.Length, bottom.Length)) return true;
//
//        if (Utilities.doubleEquals(left.Length, bottom.Length) && Utilities.doubleEquals(right.Length, top.Length)) return true;
//
//        return false;
//    }
//
//    //
//    // Can this Quadrilateral be strengthened to any of the specific quadrilaterals (parallelogram, kite, square, etc)?
//    //
//    public static List<Strengthened> CanBeStrengthened(Quadrilateral thatQuad)
//    {
//        List<Strengthened> strengthened = new ArrayList<Strengthened>();
//
//        if (thatQuad.VerifyParallelogram())
//        {
//            strengthened.add(new Strengthened(thatQuad, new Parallelogram(thatQuad)));
//        }
//
//        if (thatQuad.VerifyRectangle())
//        {
//            strengthened.add(new Strengthened(thatQuad, new Rectangle(thatQuad)));
//        }
//
//        if (thatQuad.VerifySquare())
//        {
//            strengthened.add(new Strengthened(thatQuad, new Square(thatQuad)));
//        }
//
//        if (thatQuad.VerifyRhombus())
//        {
//            strengthened.add(new Strengthened(thatQuad, new Rhombus(thatQuad)));
//        }
//
//        if (thatQuad.VerifyKite())
//        {
//            strengthened.add(new Strengthened(thatQuad, new Kite(thatQuad)));
//        }
//
//        if (thatQuad.VerifyTrapezoid())
//        {
//            Trapezoid newTrap = new Trapezoid(thatQuad);
//            strengthened.add(new Strengthened(thatQuad, newTrap));
//
//            if (thatQuad.VerifyIsoscelesTrapezoid())
//            {
//                strengthened.add(new Strengthened(newTrap, new IsoscelesTrapezoid(thatQuad)));
//            }
//        }
//
//        return strengthened;
//    }

    protected boolean HasSamePoints(Quadrilateral quad)
    {
        for (Point p : quad.points)
        {
            if (!this.points.contains(p)) return false;
        }

        return true;
    }

    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Quadrilateral)) return false;
        Quadrilateral thatQuad = (Quadrilateral)obj;

        if (!thatQuad.IsStrictQuadrilateral()) return false;

        return this.HasSamePoints(thatQuad);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Quadrilateral)) return false;
        Quadrilateral thatQuad = (Quadrilateral)obj;

        if (!thatQuad.IsStrictQuadrilateral()) return false;

        return this.HasSamePoints(thatQuad);
    }


    //
    // generate a Quadrilateral object, if the 4 segments construct a valid quadrilateral.
    //
    public static Quadrilateral GenerateQuadrilateral(List<Segment> segments)
    {
        if (segments.size() < 4) return null;

        return GenerateQuadrilateral(segments.get(0), segments.get(1), segments.get(2), segments.get(3));
    }
    public static Quadrilateral GenerateQuadrilateral(Segment s1, Segment s2, Segment s3, Segment s4)
    {
        //    ____
        //   |
        //   |____
        // Check a C shape of 3 segments; the 4th needs to be opposite 
        OutPair<Segment, Segment> out = new OutPair<Segment, Segment>();
        Segment left = AcquireMiddleSegment(s1, s2, s3, out);
        Segment top = out.first();
        Segment bottom = out.second();
        
        // Check C for the top, bottom, and right sides
        if (left == null) return null;

        Segment right = s4;

        Segment rightMid = AcquireMiddleSegment(top, bottom, right, out);

        // The middle segment we acquired must match the 4th segment
        if (!right.StructurallyEquals(rightMid)) return null;

        //
        // The top / bottom cannot cross; bowtie or hourglass shape
        // A valid quadrilateral will have the intersections outside of the quad, that is defined
        // by the order of the three points: intersection and two endpts of the side
        //
        Point intersection = top.FindIntersection(bottom);

        // Check for parallel lines, then in-betweenness
        if (intersection != null && !Double.isNaN(intersection.getX()) && !Double.isNaN(intersection.getY()))
        {
            if (Segment.Between(intersection, top.getPoint1(), top.getPoint2())) return null;
            if (Segment.Between(intersection, bottom.getPoint1(), bottom.getPoint2())) return null;
        }

        // The left / right cannot cross; bowtie or hourglass shape
        intersection = left.FindIntersection(right);

        // Check for parallel lines, then in-betweenness
        if (intersection != null && !Double.isNaN(intersection.getX()) && !Double.isNaN(intersection.getY()))
        {
            if (Segment.Between(intersection, left.getPoint1(), left.getPoint2())) return null;
            if (Segment.Between(intersection, right.getPoint1(), right.getPoint2())) return null;
        }

        //
        // Verify that we have 4 unique points; And not different shapes (like a star, or triangle with another segment)
        //
        List<Point> pts = new ArrayList<Point>();
        pts.add(left.SharedVertex(top));
        pts.add(left.SharedVertex(bottom));
        pts.add(right.SharedVertex(bottom));
        pts.add(right.SharedVertex(top));
        for (int i = 0; i < pts.size() - 1; i++)
        {
            for (int j = i + 1; j < pts.size(); j++)
            {
                if (pts.get(i).StructurallyEquals(pts.get(j)))
                {
                    return null;
                }
            }
        }

        return new Quadrilateral(left, right, top, bottom);
    }

    //            top
    // shared1  _______   off1
    //         |
    //   mid   |
    //         |_________   off2
    //            bottom
    private static Segment AcquireMiddleSegment(Segment seg1, Segment seg2, Segment seg3, OutPair<Segment, Segment> outTopBottom) // out Segment top, out Segment bottom)
    {
        Segment top;
        Segment bottom;
        if (seg1.SharedVertex(seg2) != null && seg1.SharedVertex(seg3) != null)
        {
            top = seg2;
            bottom = seg3;
            outTopBottom.set(top, bottom); 
            return seg1;
        }

        if (seg2.SharedVertex(seg1) != null && seg2.SharedVertex(seg3) != null)
        {
            top = seg1;
            bottom = seg3;
            outTopBottom.set(top, bottom);
            return seg2;
        }

        if (seg3.SharedVertex(seg1) != null && seg3.SharedVertex(seg2) != null)
        {
            top = seg1;
            bottom = seg2;
            outTopBottom.set(top, bottom);
            return seg3;
        }

        top = null;
        bottom = null;

        outTopBottom.set(null, null);

        return null;
    }


    //
    // Area-Related Computations
    //
    @Override
    public boolean IsComputableArea() { return true; }

//    //
//    // As a general mechanism, can we split up this quadrilateral into two triangles and find those individual areas?
//    // We must try two combinations of triangle splitting.
//    //
//    protected double SplitTriangleArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        //
//        // Check the areas of each pairs, only if a diagonal is evident.
//        //
//        if (TopLeftDiagonalIsValid())
//        {
//            double area1 = triPairTLBR.Key.GetArea(known);
//            double area2 = triPairTLBR.Value.GetArea(known);
//
//            if (area1 > 0 && area2 > 0) return area1 + area2;
//        }
//
//        if (BottomRightDiagonalIsValid())
//        {
//            double area1 = triPairBLTR.Key.GetArea(known);
//            double area2 = triPairBLTR.Value.GetArea(known);
//
//            if (area1 > 0 && area2 > 0) return area1 + area2;
//        }
//
//        return -1;
//    }

//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        return SplitTriangleArea(known) > 0;
//    }
//
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        return SplitTriangleArea(known);
//    }

    //
    // Maintain a public repository of all triangles objects : the figure.
    //
    public static void clear()
    {
        figureQuadrilaterals.clear();
    }
    public static List<Quadrilateral> figureQuadrilaterals = new ArrayList<Quadrilateral>();
    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
        if (clause instanceof Quadrilateral) figureQuadrilaterals.add((Quadrilateral)clause);
    }
    public static Quadrilateral GetFigureQuadrilateral(Quadrilateral q)
    {
        // Search for exact segment first
        for (Quadrilateral quad : figureQuadrilaterals)
        {
            if (quad.StructurallyEquals(q)) return quad;
        }

        return null;
    }

    @Override
    public String toString()
    {
        return "Quadrilateral(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }
    
    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) str.append(pt.CheapPrettyString());
        return "Quad(" + str.toString() + ")";
    }
}
