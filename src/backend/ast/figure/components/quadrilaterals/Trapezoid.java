package backend.ast.figure.components.quadrilaterals;

import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Quadrilateral;
import backend.ast.figure.components.Segment;
import backend.utilities.Pair;
import backend.utilities.exception.ExceptionHandler;

public class Trapezoid extends Quadrilateral
{
    /// <summary>
    /// Represents a triangle, which consists of 3 segments
    /// </summary>
    private Segment baseSegment;
    private Segment oppBaseSegment;
    private Segment leftLeg;
    private Segment rightLeg;

    private Angle topLeftBaseAngle;
    private Angle topRightBaseAngle;
    private Angle bottomLeftBaseAngle;
    private Angle bottomRightBaseAngle;

    public Segment getBaseSegment() { return baseSegment; }
    public Segment getOppBaseSegment() { return oppBaseSegment; }
    public Segment getLeftLeg() { return leftLeg; }
    public Segment getRightLeg() { return rightLeg; }

    public Angle getTopLeftBaseAngle () { return topLeftBaseAngle; }
    public Angle getTopRightBaseAngle() { return topRightBaseAngle; }
    public Angle getBottomLeftBaseAngle() { return bottomLeftBaseAngle; }
    public Angle getBottomRightBaseAngle() { return bottomRightBaseAngle; }

    private Segment median;
    private boolean medianChecked = true;
    private boolean medianValid = true;
    public boolean IsMedianValid() { return medianValid; }
    public boolean IsMedianChecked() { return medianChecked; }
    public void SetMedianInvalid() { medianValid = false; }
    public void SetMedianChecked(boolean val) { medianChecked = val; }

    public Trapezoid(Quadrilateral quad) 
    {
        this(quad.left, quad.right, quad.top, quad.bottom);
    }

    public Trapezoid(Segment left, Segment right, Segment top, Segment bottom)
    {
        super(left, right, top, bottom);
        if (left.IsParallelWith(right))
        {
            if (left.length() > right.length())
            {
                baseSegment = left;
                oppBaseSegment = right;
                leftLeg = top;
                rightLeg = bottom;
            }
            else
            {
                baseSegment = right;
                oppBaseSegment = left;
                leftLeg = top;
                rightLeg = bottom;
            }
        }
        else if (top.IsParallelWith(bottom))
        {
            if (top.length() > bottom.length())
            {
                baseSegment = top;
                oppBaseSegment = bottom;
                leftLeg = left;
                rightLeg = right;
            }
            else
            {
                baseSegment = bottom;
                oppBaseSegment = top;
                leftLeg = left;
                rightLeg = right;
            }
        }
        else
        {
            ExceptionHandler.throwException( new IllegalArgumentException("Quadrilateral does not define a trapezoid; no sides are parallel: " + this));
        }

        topLeftBaseAngle = GetAngle(new Angle(leftLeg, oppBaseSegment));
        topRightBaseAngle = GetAngle(new Angle(rightLeg, oppBaseSegment));
        bottomLeftBaseAngle = GetAngle(new Angle(leftLeg, baseSegment));
        bottomRightBaseAngle = GetAngle(new Angle(rightLeg, baseSegment));

//        FindMedian();
    }

    //
    // Find the figure segment which acts as the median of this trapezoid
    //
//    public void FindMedian()
//    {
//        if (Segment.figureSegments.size() == 0)
//        {
//            //Segments have not yet been recorded for the figure, wait to check for median
//            SetMedianChecked(false);
//            return;
//        }
//
//        for(Segment medianCand : Segment.figureSegments)
//        {
//            // The median is parallel to the bases.
//            if (medianCand.IsParallelWith(this.baseSegment) && medianCand.IsParallelWith(this.oppBaseSegment))
//            {
//                // The median must be between the bases and connect to the legs.
//                Point leftIntersection = leftLeg.FindIntersection(medianCand);
//                if (leftLeg.PointLiesOnAndExactlyBetweenEndpoints(leftIntersection))
//                {
//                    Point rightIntersection = rightLeg.FindIntersection(medianCand);
//                    if (rightLeg.PointLiesOnAndExactlyBetweenEndpoints(rightIntersection))
//                    {
//                        // Success, we have a median
//                        // Acquire the exact figure segment (if it exists) otherwise the segment which contains the median
//                        this.median = Segment.GetFigureSegment(leftIntersection, rightIntersection);
//
//                        // If we have a median at all in the figure
//                        if (this.median != null)
//                        {
//                            // If this is not the exact median, create the exact median. 
//                            Segment actualMedian = new Segment(leftIntersection, rightIntersection);
//                            if (!this.median.StructurallyEquals(actualMedian))
//                            {
//                                this.median = actualMedian;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        if (this.median == null) this.SetMedianInvalid();
//
//        SetMedianChecked(true);
//    }

    //
    // Are the legs congruent (for an isosceles trapezoid)?
    //
    //            public boolean CreatesCongruentLegs(CongruentSegments css)
    //            {
    //                if (leftLeg.StructurallyEquals(css.cs1) && rightLeg.StructurallyEquals(css.cs2)) return true;
    //                if (leftLeg.StructurallyEquals(css.cs2) && rightLeg.StructurallyEquals(css.cs1)) return true;
    //
    //                return false;
    //            }

    //            @Override
    //            public boolean IsStrongerThan(Polygon that)
    //            {
    //                if (that instanceof Kite) return false;
    //                if (that instanceof Parallelogram) return false;
    //                if (that instanceof Quadrilateral) return true;
    //
    //                return false;
    //            }

    //            @Override
    //            public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                return GetArea(known) > 0;
    //            }

    private boolean IsHeight(Segment that)
    {
        //
        // Is this segment perpendicular to the bases?
        //
        if (!baseSegment.IsPerpendicularTo(that)) return false;
        if (!oppBaseSegment.IsPerpendicularTo(that)) return false;

        // Are the endpoints on / or extensions of the trapezoid bases?
        if (baseSegment.PointLiesOn(that.getPoint1()) && oppBaseSegment.PointLiesOn(that.getPoint2())) return true;
        if (baseSegment.PointLiesOn(that.getPoint2()) && oppBaseSegment.PointLiesOn(that.getPoint1())) return true;

        return false;
    }

    //
    // Compute the area of the trapezoid using the A = 1/2 * (b_1 + b_2) * h
    //
    //            public double GetBaseBasedArea(double height, Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                double baseLength1 = known.GetSegmentLength(baseSegment);
    //                if (baseLength1 < 0) return -1;
    //
    //                double baseLength2 = known.GetSegmentLength(oppBaseSegment);
    //                if (baseLength2 < 0) return -1;
    //
    //                return 0.5 * (baseLength1 + baseLength2) * height;
    //            }

    //
    // Compute the area of the trapezoid using the A = median * height
    //
    //            public double GetMedianBasedArea(double height, Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                if (!IsMedianValid()) return -1;
    //
    //                double medianLength = known.GetSegmentLength(median);
    //                if (medianLength < 0) return -1;
    //
    //                return medianLength * height;
    //            }

    //            public double GetHeight(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                for (Pair<Segment, Double> pair : known.GetKnownSegments())
    //                {
    //                    if (this.IsHeight(pair.getKey())) return pair.getValue();
    //                }
    //
    //                return -1;
    //            }

    //
    // Attempt trapezoidal formulas; if they fail, call the base method: splitting into triangles.
    //
    //            @Override
    //            public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                // Acquire the height of the trapezoid: if it's a known value.
    //                double height = GetHeight(known);
    //                if (height < 0) return -1;
    //
    //                double area = GetBaseBasedArea(height, known);
    //                if (area > 0) return area;
    //
    //                area = GetMedianBasedArea(height, known);
    //                if (area > 0) return area;
    //
    //                return super.GetArea(known);
    //            }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof Trapezoid)) return false;
        Trapezoid thatTrap = (Trapezoid)obj;

        if (thatTrap instanceof IsoscelesTrapezoid) return false;

        //return base.StructurallyEquals(obj);
        return super.HasSamePoints((Quadrilateral)obj);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if(!(obj instanceof Trapezoid)) return false;
        Trapezoid thatTrap = (Trapezoid)obj;

        if (thatTrap instanceof IsoscelesTrapezoid) return false;

        return structurallyEquals(obj);
    }

    @Override
    public String toString()
    {
        return "Trapezoid(" + topLeft.toString() + ", " + topRight.toString() + ", " +
                bottomRight.toString() + ", " + bottomLeft.toString() + ")";
    }

    @Override
    public int getHashCode() { return super.getHashCode(); }

    @Override
    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();
        for (Point pt : points) str.append(pt.CheapPrettyString());
        return "Trap(" + str.toString() + ")";
    }

}
