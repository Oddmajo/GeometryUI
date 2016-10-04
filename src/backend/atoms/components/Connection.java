package backend.atoms.components;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Arc;
import backend.ast.figure.components.MajorArc;
import backend.ast.figure.components.MinorArc;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.Semicircle;
import backend.utilities.translation.OutPair;

//
// Aggregation class for each segment of an atomic region.
//
public class Connection
{
    public enum ConnectionType { SEGMENT, ARC }

    public Point endpoint1;
    public Point endpoint2;
    public ConnectionType type;

    // The shape which has this connection. 
    public Figure segmentOrArc;

    public Connection(Point e1, Point e2, ConnectionType t, Figure so)
    {
        endpoint1 = e1;
        endpoint2 = e2;
        type = t;
        segmentOrArc = so;
    }

    public boolean HasPoint(Point p) { return endpoint1.Equals(p) || endpoint2.Equals(p); }

    public Point OtherEndpoint(Point p)
    {
        if (endpoint1.StructurallyEquals(p)) return endpoint2;
        if (endpoint2.StructurallyEquals(p)) return endpoint1;
        return null;
    }

    @Override
    public String toString()
    {
        return "< " + endpoint1.name + ", " + endpoint2.name + "(" + type + ") >";
    }

    public String CheapPrettyString()
    {
        return (type == ConnectionType.SEGMENT ? "Seg(" : "Arc(") + endpoint1.CheapPrettyString() + endpoint2.SimpleToString() + ")";
    }

    public boolean StructurallyEquals(Connection that)
    {
        if (!this.HasPoint(that.endpoint1) || !this.HasPoint(that.endpoint2)) return false;

        if (type != that.type) return false;

        return segmentOrArc.StructurallyEquals(that.segmentOrArc);
    }

    //
    // Create an approximation of the arc by using a set number of arcs.
    //
    public List<Segment> Segmentize()
    {
        if (this.type == ConnectionType.SEGMENT)
        {
            return backend.utilities.list.Utilities.makeList(new Segment(this.endpoint1, this.endpoint2));
        }

        return segmentOrArc.Segmentize();
    }

    public boolean PointLiesOn(Point pt)
    {
        if (this.type == ConnectionType.SEGMENT)
        {
            return ((Segment)this.segmentOrArc).PointLiesOnAndBetweenEndpoints(pt);
        }
        else if (this.type == ConnectionType.ARC)
        {
            return ((Arc)segmentOrArc).PointLiesOn(pt);
        }

        return false;
    }

    public boolean PointLiesStrictlyOn(Point pt)
    {
        if (this.HasPoint(pt)) return false;

        return PointLiesOn(pt);
    }

    public Point Midpoint()
    {
        if (this.type == ConnectionType.SEGMENT)
        {
            return ((Segment)segmentOrArc).Midpoint();
        }
        else if (this.type == ConnectionType.ARC)
        {
            return ((Arc)segmentOrArc).Midpoint();
        }

        return null;
    }

    //
    // Find the intersection points between this conenction and that; 2 points may result. (2 with arc / segment)
    //
    public void FindIntersection(List<Point> figurePoints, Connection that, OutPair<Point, Point> out)
    {
        OutPair<Point, Point> localOut = new OutPair<Point, Point>(); 

        if (that.type == ConnectionType.ARC)
        {
            this.segmentOrArc.FindIntersection((Arc)that.segmentOrArc, localOut);
        }
        else
        {
            this.segmentOrArc.FindIntersection((Segment)that.segmentOrArc, localOut);
        }

        Segment thatSeg = (that.segmentOrArc instanceof Segment) ? (Segment)that.segmentOrArc : null;
        Arc thatArc = (that.segmentOrArc instanceof Arc) ? (Arc)that.segmentOrArc : null;

        Segment thisSeg = (this.segmentOrArc instanceof Segment) ? (Segment)this.segmentOrArc : null;
        Arc thisArc = (this.segmentOrArc instanceof Arc) ? (Arc)this.segmentOrArc : null;

        //
        // Normalize the points to the points in the drawing.
        //
        Point pt1 = null;
        Point pt2 = null;
        if (thisSeg != null && thatSeg != null)
        {
            pt1 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt1, thisSeg, thatSeg);
            pt2 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt2, thisSeg, thatSeg);
        }
        else if (thisSeg != null && thatArc != null)
        {
            pt1 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt1, thisSeg, thatArc);
            pt2 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt2, thisSeg, thatArc);
        }
        else if (thisArc != null && thatSeg != null)
        {
            pt1 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt1, thatSeg, thisArc);
            pt2 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt2, thatSeg, thisArc);
        }
        else if (thisArc != null && thatArc != null)
        {
            pt1 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt1, thisArc, thatArc);
            pt2 = backend.utilities.ast_helper.Utilities.AcquireRestrictedPoint(figurePoints, pt2, thisArc, thatArc);
        }

        out.set(pt1,  pt2);
    }

    public void FindIntersection(Connection that, OutPair<Point, Point> out)
    {
        OutPair<Point, Point> localOut = new OutPair<Point, Point>();
        if (that.type == ConnectionType.ARC)
        {
            this.segmentOrArc.FindIntersection((Arc)that.segmentOrArc, localOut);
        }
        else
        {
            this.segmentOrArc.FindIntersection((Segment)that.segmentOrArc, localOut);
        }

        out.set(localOut.first(), localOut.second());
    }

    //
    // If that is a segment which is smaller and is a subsegment of this.
    // If that is an arc which is smaller and is a subsrc of this.
    //
    public boolean IsSubConnection(Connection that)
    {
        if (that.type != this.type) return false;

        if (this.type == ConnectionType.ARC)
        {
            return ((Arc)this.segmentOrArc).HasSubArc((Arc)that.segmentOrArc);
        }
        else if (this.type == ConnectionType.SEGMENT)
        {
            return ((Segment)this.segmentOrArc).HasSubSegment((Segment)that.segmentOrArc);
        }

        return false;
    }

    //
    // If one of the endpoints of that is inside of this; and vice versa.
    //
    public boolean Overlap(Connection that)
    {
        if (that.type != this.type) return false;

        if (this.type == ConnectionType.ARC)
        {
            if (!((Arc)this.segmentOrArc).StructurallyEquals((Arc)this.segmentOrArc)) return false;

            // If the arcs just touch, it's not overlap.
            if (this.segmentOrArc instanceof MinorArc)
            {
                MinorArc minor = (MinorArc)this.segmentOrArc;
                if (minor.PointLiesStrictlyOn(that.endpoint1) || minor.PointLiesStrictlyOn(that.endpoint2)) return true;
            }
            else if (this.segmentOrArc instanceof Semicircle)
            {
                Semicircle semi = (Semicircle)this.segmentOrArc;
                if (semi.PointLiesStrictlyOn(that.endpoint1) || semi.PointLiesStrictlyOn(that.endpoint2)) return true;
            }
            else if (this.segmentOrArc instanceof MajorArc)
            {
                MajorArc major = (MajorArc)this.segmentOrArc;
                if (major.PointLiesStrictlyOn(that.endpoint1) || major.PointLiesStrictlyOn(that.endpoint2)) return true;
            }
            return false;
        }
        else if (this.type == ConnectionType.SEGMENT)
        {
            Segment thisSegment = (Segment)this.segmentOrArc;
            Segment thatSegment = (Segment)that.segmentOrArc;

            if (!thisSegment.IsCollinearWith(thatSegment)) return false;

            return thisSegment.CoincidingWithOverlap(thatSegment);
        }

        return false;
    }

    //
    // Does the segment or arc stand on the segment or arg? That is, the intersection point lies on the end of this or that?
    //
    public boolean StandsOnNotEndpoint(Connection that)
    {
        if (StandsOnEndpoint(that)) return false;

        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(that, out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        if (pt2 != null) return false;

        if (this.HasPoint(pt1) && that.PointLiesOn(pt1)) return true;
        if (that.HasPoint(pt1) && this.PointLiesOn(pt1)) return true;

        return false;
    }

    public boolean StandsOnEndpoint(Connection that)
    {
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(that, out);
        Point pt1 = out.first();
        Point pt2 = out.second();

        if (pt2 != null) return false;

        return this.HasPoint(pt1) && that.HasPoint(pt1);
    }

    //
    // Intersects at a single point (not at the endpoint of either connection).
    //
    public boolean Crosses(Connection that)
    {
        if (this.type == that.type && this.type == ConnectionType.SEGMENT) return SegmentSegmentCrossing(that);
        else if (this.type == that.type && this.type == ConnectionType.ARC) return ArcArcCrossing(that);
        else if (this.type != that.type) return MixedCrossing(that);

        return false;
    }
    
    public boolean SegmentSegmentCrossing(Connection that)
    {
        return ((Segment)this.segmentOrArc).LooseCrosses((Segment)that.segmentOrArc);
    }

    public boolean ArcArcCrossing(Connection that)
    {
        return ((Arc)this.segmentOrArc).Crosses((Arc)that.segmentOrArc);
    }

    public boolean MixedCrossing(Connection that)
    {
        OutPair<Point, Point> out = new OutPair<Point, Point>();
        this.FindIntersection(that, out);
        Point pt1 = out.first();
        Point pt2 = out.second();
        
        // Must intersect.
        if (pt1 == null && pt2 == null) return false;

        // If the endpoints align, this is not a crossing.
        if (this.HasPoint(that.endpoint1) && this.HasPoint(that.endpoint2)) return false;

        // A segment cuts through an arc in two points.
        if (this.type != that.type && pt1 != null && pt2 != null) return true;

        // Catch-all since we have the true cases for 2 intersections.
        if (pt2 != null) return false;

        // We only have one intersection point now: Point1
        // Check for the StandsOn relationship.
        return this.PointLiesStrictlyOn(pt1) && that.PointLiesStrictlyOn(pt1);
    }

    public boolean DefinesArcSegmentRegion(Connection that)
    {
        if (this.type == that.type) return false;

        // Endpoints align.
        return this.HasPoint(that.endpoint1) && this.HasPoint(that.endpoint2);
    }
}