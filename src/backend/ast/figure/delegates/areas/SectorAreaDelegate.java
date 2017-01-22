package backend.ast.figure.delegates.areas;

public class SectorAreaDelegate extends AreaDelegate
{

    public SectorAreaDelegate()
    {
        // TODO Auto-generated constructor stub
    }
    
    //
    // Area-Related Computations
    //
    protected double Area(double radAngleMeasure, double radius)
    {
        return 0.5 * radius * radius * radAngleMeasure;
    }
    protected double RationalArea(double radAngleMeasure, double radius)
    {
        return Area(radAngleMeasure, radius) / Math.PI;
    }
//    @Override
//    public boolean IsComputableArea() { return true; }
//    @Override
//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        // Central Angle
//        if (known.GetAngleMeasure(this.theArc.GetCentralAngle()) < 0) return false;
//
//        // Radius / Circle 
//        return theArc._theCircle.CanAreaBeComputed(known);
//    }
//    @Override
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known)
//    {
//        if (theArc instanceof Semicircle) return ((Semicircle)theArc).GetArea(known);
//
//        // Central Angle; this instanceof minor arc measure by default
//        double angleMeasure = Angle.toRadians(known.GetAngleMeasure(this.theArc.GetCentralAngle()));
//
//        if (angleMeasure <= 0) return -1;
//
//        // Make a major arc measure, if needed.
//        if (theArc instanceof MajorArc) angleMeasure = 2 * Math.PI - angleMeasure;
//
//        // Radius / Circle
//        double circArea = theArc._theCircle.GetArea(known);
//
//        if (circArea <= 0) return -1;
//
//        // The area instanceof a proportion of the circle defined by the angle.
//        return (angleMeasure / (2 * Math.PI)) * circArea;
//    }

}
