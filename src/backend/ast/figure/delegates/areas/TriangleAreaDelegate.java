package backend.ast.figure.delegates.areas;

import backend.ast.figure.components.angles.Angle;

public class TriangleAreaDelegate
{

    public TriangleAreaDelegate()
    {
        // TODO Auto-generated constructor stub
    }

    //
    // CTA: Triangle
    //
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
//    //
//    // Area-Related Computations
//    //
//    // Hero's Formula.
//    protected double HeroArea(double s1, double s2, double s3)
//    {
//        double semip = 0.5 * (s1 + s2 + s3);
//
//        return Math.sqrt(semip * (semip - s1) * (semip - s2) * (semip - s3));
//    }
//    // SAS: 1/2 a * b * sin C
//    protected double TrigArea(double a, double b, double theta)
//    {
//        return 0.5 * a * b * Math.sin(Angle.toRadians(theta));
//    }
//    // Classic: 1/2 base * height
//    protected double Area(double b, double h)
//    {
//        return 0.5 * b * h;
//    }
//    protected double RationalArea(double b, double h) { return Area(b, h); }
//    protected double RationalArea(double s1, double s2, double s3) { return HeroArea(s1, s2, s3); }
//    @Override
//    public boolean IsComputableArea() { return true; }

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
}
