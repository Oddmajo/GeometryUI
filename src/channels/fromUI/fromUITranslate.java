package channels.fromUI;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.delegates.intersections.IntersectionDelegate;
import rene.zirkel.objects.LineObject;
import rene.zirkel.objects.ParallelObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.objects.TwoPointLineObject;

public class FromUITranslate
{
    public static Point translatePoint(PointObject p)
    {
        return new Point(p.getName(),p.getX(),p.getY());
    }
    
    /**
     * This function takes a TwoPointLineObject, which is extended by SegmentObject, RayObject, and LineObject
     *  This is because each of these needs to be translated into a single Segment object before other translations
     * @param TwoPointLineObject
     * @return Segment
     */
    public static Segment translateSegment(TwoPointLineObject s)
    {
        Point p1 = translatePoint(s.getP1());
        Point p2 = translatePoint(s.getP2());
        return new Segment(p1, p2);
    }
    public static Segment translateLine(LineObject l, Diagram b)
    {
        
        /* The Diagram 'boundary' included 4 segments within its ArrayList<Segment>
         * They will be in the following order:
         * 0, Left
         * 1, Bottom
         * 2, Right
         * 3, Top
         */
        ArrayList<Segment> borders = b.getSegments();
        
        Point initPoint1 = translatePoint(l.getP1());
        Point initPoint2 = translatePoint(l.getP2());
        Segment sSegment = new Segment(initPoint1, initPoint2);
        //For each border, find the line intersection, then check to see if that is within the diagram
        //that intersection will either be outside the border or on it.
        
        ArrayList<Point> intersections = new ArrayList<Point>();
        Point check;
        for(Segment border : borders)
        {
            check = IntersectionDelegate.lineIntersection(border, sSegment);
            if(border.pointLiesOn(check))
                intersections.add(check);
        }
        //There should only be two intersections
        if(intersections.size() > 2)
        {
            //This should go through each point to check what happened
            //  duplicate points would have been made if one or more intersection occurs exactly at a corner
            //  go through and get 2 non-equal points, purge the others
            //  TODO: Verify this happens, implement handling
            return new Segment(intersections.get(0),intersections.get(1));
            
        }
        else
        {
            return new Segment(intersections.get(0),intersections.get(1));
        }
    }
    
    public static Segment translateParallel(ParallelObject l, Diagram b)
    {
        Segment original = translateLine((LineObject)l.getL(), d);
        Point point = translatePoint(l.getP1());
        
        Point temp = new Point("", point.getX()+1, point.getY()+original.slope());
        
        Segment line = new Segment(point, temp);
        
        //From here it is the same logic as translating a line
        ArrayList<Segment> borders = b.getSegments();
        
        ArrayList<Point> intersections = new ArrayList<Point>();
        Point check;
        for(Segment border : borders)
        {
            check = IntersectionDelegate.lineIntersection(border, line);
            if(border.pointLiesOn(check))
                intersections.add(check);
        }
        //There should only be two intersections
        if(intersections.size() > 2)
        {
            //This should go through each point to check what happened
            //  duplicate points would have been made if one or more intersection occurs exactly at a corner
            //  go through and get 2 non-equal points, purge the others
            //  TODO: Verify this happens, implement handling
            return new Segment(intersections.get(0),intersections.get(1));
            
        }
        else
        {
            return new Segment(intersections.get(0),intersections.get(1));
        }
        
        
        return null;
    }
}
