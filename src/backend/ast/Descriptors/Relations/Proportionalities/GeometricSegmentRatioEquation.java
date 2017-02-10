package backend.ast.Descriptors.Relations.Proportionalities;

public class GeometricSegmentRatioEquation extends SegmentRatioEquation
{

    public GeometricSegmentRatioEquation(SegmentRatio r1, SegmentRatio r2)
    { 
        super(r1, r2);
    }

    public Boolean IsAlgebraic() { return false; }
    public Boolean IsGeometric() { return true; }

    public Boolean Equals(Object obj)
    {
        if (obj != null && obj instanceof GeometricSegmentRatioEquation)
        {
        GeometricSegmentRatioEquation gsr = (GeometricSegmentRatioEquation)obj;

        return super.Equals(gsr);
        }
        return false;
    }
    public String ToString()
    {
        return "GeometricProportionalEquation(" + lhs.toString() + " = " + rhs.toString() + ") ";
    }

}
