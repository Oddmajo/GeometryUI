package backend.ast.figure.delegates.areas;

public class QuadrilateralAreaDelegate extends AreaDelegate
{

    public QuadrilateralAreaDelegate()
    {
        // TODO Auto-generated constructor stub
    }

    //
    // CTA: General Quadrilateral
    //
    //
    //
    // Area-Related Computations
    //
//    @Override
//    public boolean IsComputableArea() { return true; }

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
    // CTA: Isosceles Trapezoid
    //
    //
    // Attempt trapezoidal formulas; if they fail, call the base method: splitting into triangles.
    //
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        if (calculatedHeight > 0)
//        {
//            double area = GetBaseBasedArea(calculatedHeight, known);
//        }
//
//        return super.GetArea(known);
//    }
    
    //
    // CTA: Kite
    //
//  @Override
//  public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//  {
//      // Acquire the diagonals.
//      if (this.topLeftBottomRightDiagonal == null || this.bottomLeftTopRightDiagonal == null)
//      {
//          ExceptionHandler.throwException(new DebugException(("No-Op")));
//      }
//
//      double diag1Length = known.GetSegmentLength(this.bottomLeftTopRightDiagonal);
//      double diag2Length = known.GetSegmentLength(this.topLeftBottomRightDiagonal);
//
//      // Multiply base * height.
//      double thisArea = -1;
//
//      if (diag1Length < 0 || diag2Length < 0) thisArea = -1;
//      else thisArea = 0.5 * diag1Length * diag2Length;
//
//      return thisArea > 0 ? thisArea : SplitTriangleArea(known);
//  }
    
    
    
    //
    // CTA: Rectangle
    //
    //
    // Calculate base * height ; defer to splitting triangles from there.
    //
    public double Area(double b, double h) { return b * h; }

//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        double[] sideVals = new double[orderedSides.size()];
//
//        for (int s = 0; s < orderedSides.size(); s++)
//        {
//            sideVals[s] = known.GetSegmentLength(orderedSides.get(s));
//        }
//
//        // One pair of adjacent sides is required for the area computation.
//        for (int s = 0; s < sideVals.length; s++)
//        {
//            double baseVal = sideVals[s];
//            double heightVal = sideVals[(s+1) % sideVals.length];
//
//            if (baseVal > 0 && heightVal > 0) return Area(baseVal, heightVal);
//        }
//
//        return SplitTriangleArea(known);
//    }
    
    //
    // CTA: Trapezoid
    //
    //
    //            @Override
    //            public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
    //            {
    //                return GetArea(known) > 0;
    //            }
    
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
    
}
