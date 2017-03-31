package backend.ast.Descriptors.Relations.Proportionalities;

import java.util.ArrayList;
import java.util.List;

import backend.ast.Descriptors.Descriptor;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.Pair;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

/// <summary>
/// Describes a point that lies on a segmant.
/// </summary>
public class SegmentRatioEquation extends Descriptor
{

    protected ProportionalSegments lhs;
    public ProportionalSegments getlhs() { return lhs; }

    protected ProportionalSegments rhs;
    public ProportionalSegments getrhs() { return rhs; }

    private ArrayList<Segment> segments;
    //public KeyValuePair<int, int> proportion { get; protected set; }
    //public double dictatedProportion { get; protected set; }

    public SegmentRatioEquation(ProportionalSegments ell, ProportionalSegments r)
    {
        super();

        if (!ell.ProportionallyEquals(r))
        {
            ExceptionHandler.throwException(new ArgumentException("Ratios of segments are not proportionally equal: " + ell + " " + r));
        }

        lhs = ell;
        rhs = r;

        segments = new ArrayList<Segment>();
        segments.add(lhs.smallerSegment);
        segments.add(lhs.largerSegment);
        segments.add(rhs.smallerSegment);
        segments.add(rhs.largerSegment);
    }

    //// Return the number of shared segments in both congruences
    //public int SharesNumClauses(CongruentSegments thatCS)
    //{
    //    //CongruentSegments css = thatPS as CongruentSegments;

    //    //if (css == null) return 0;

    //    int numShared = smallerSegment.Equals(thatCS.cs1) || smallerSegment.Equals(thatCS.cs2) ? 1 : 0;
    //    numShared += largerSegment.Equals(thatCS.cs1) || largerSegment.Equals(thatCS.cs2) ? 1 : 0;

    //    return numShared;
    //}

    //
    // This equation must be able to relate two segments from triangle 1 and two from triangle 2
    //

    public Boolean LinksTriangles(Triangle ct1, Triangle ct2)
    {
        int count1 = 0;
        int count2 = 0;
        ArrayList<Boolean> marked = new ArrayList<>();
        for (int s = 0; s < segments.size(); s++)
        {
            if (ct1.HasSegment(segments.get(s)))
            {
                marked.set(s, true);
                count1++;
            }
            if (ct2.HasSegment(segments.get(s)))
            {
                marked.set(s, true);
                count2++;
            }
        }

        if (marked.contains(false)) return false;

        return count1 == 2 && count2 == 2;
    }

    public Pair<Segment, Segment> GetSegments(Triangle tri)
    {
        // Collect the applicable segments.
        List<Segment> theseSegments = new ArrayList<Segment>();
        for (Segment segment : segments)
        {
            if (tri.HasSegment(segment)) theseSegments.add(segment);
        }

        // Check for error condition
        if (theseSegments.size() != 2) return new Pair<Segment, Segment>(null, null);

        // Place the larger segment first
        Pair<Segment, Segment> pair;
        if (theseSegments.get(0).length() > theseSegments.get(1).length())
        {
            pair = new Pair<Segment, Segment>(theseSegments.get(0), theseSegments.get(1));
        }
        else
        {
            pair = new Pair<Segment, Segment>(theseSegments.get(1), theseSegments.get(0));
        }

        return pair;
    }

    //
    //  if x / y = z / w   and we are checking for  x / z OR y / w
    //
    private Boolean HasImpliedRatio(ProportionalSegments thatRatio)
    {
        if (thatRatio.HasSegment(lhs.largerSegment) && thatRatio.HasSegment(rhs.largerSegment)) return true;
        if (thatRatio.HasSegment(lhs.smallerSegment) && thatRatio.HasSegment(rhs.smallerSegment)) return true;

        return false;
    }

    private ProportionalSegments GetOtherImpliedRatio(ProportionalSegments thatRatio)
    {
        if (thatRatio.HasSegment(lhs.largerSegment) && thatRatio.HasSegment(rhs.largerSegment))
        {
            return new ProportionalSegments(lhs.smallerSegment, rhs.smallerSegment);
        }

        if (thatRatio.HasSegment(lhs.smallerSegment) && thatRatio.HasSegment(rhs.smallerSegment))
        {
            return new ProportionalSegments(lhs.largerSegment, rhs.largerSegment);
        }

        return null;
    }

    public Boolean SharesRatio(SegmentRatioEquation that)
    {
        return GetSharedRatio(that) != null;
    }

    public ProportionalSegments GetSharedRatio(SegmentRatioEquation that)
    {
        // Check for the obvious shared ratio
        if (lhs.structurallyEquals(that.lhs)) return that.lhs;
        if (lhs.structurallyEquals(that.rhs)) return that.rhs;
        if (rhs.structurallyEquals(that.lhs)) return that.lhs;
        if (rhs.structurallyEquals(that.rhs)) return that.rhs;

        if (HasImpliedRatio(that.lhs)) return that.lhs;
        if (HasImpliedRatio(that.rhs)) return that.rhs;

        return null;
    }

    public ProportionalSegments GetOtherRatio(ProportionalSegments that)
    {
        // Check for the obvious shared ratio
        if (lhs.structurallyEquals(that)) return this.rhs;
        if (rhs.structurallyEquals(that)) return this.lhs;

        return GetOtherImpliedRatio(that);
    }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj != null && obj instanceof SegmentRatioEquation)
        {
            SegmentRatioEquation that = (SegmentRatioEquation)obj;

            return lhs.structurallyEquals(that.lhs) && rhs.structurallyEquals(that.rhs) ||
                    lhs.structurallyEquals(that.rhs) && rhs.structurallyEquals(that.lhs);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof SegmentRatioEquation)
        {
            SegmentRatioEquation that = (SegmentRatioEquation)obj;

            return lhs.equals(that.lhs) && rhs.equals(that.rhs) ||
                    lhs.equals(that.rhs) && rhs.equals(that.lhs);
        }
        return false;
    }
    
    @Override
    public String toString()
    {
        return "ProportionalEquation(" + lhs.toString() + " = " + rhs.toString() + ") ";
    }

}
