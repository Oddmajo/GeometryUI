package backend.ast.figure.delegates.areas;

public class CircleAreaDelegate extends AreaDelegate
{
    public CircleAreaDelegate()
    {
        // TODO Auto-generated constructor stub
    }
    
//  @Override
//  public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//  {
//      // Any _radius known?
//      for (Segment this_radius : radii)
//      {
//          double length = known.GetSegmentLength(this_radius);
//          if (length > 0) return true;
//      }
//
//      return false;
//  }
//  public override double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//  {
//      // Any _radius known?
//      double length = -1;
//      for (Segment this_radius : radii)
//      {
//          length = known.GetSegmentLength(this_radius);
//          if (length > 0) break;
//      }
//
//      if (length < 0) return -1;
//
//      return Area(length);
//  }
}
