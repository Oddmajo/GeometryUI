package channels.fromUI;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
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
        /* The Diagram 'b' includes 4 points within its ArrayList<Point>
         * They will be in the following order:
         * 0, Bottom Left
         * 1, Bottom Right
         * 2, Top Right
         * 3, Top Left
         */
        ArrayList<Point> corners = b.getPoints();
        /* The Diagram 'boundary' included 4 segments within its ArrayList<Segment>
         * They will be in the following order:
         * 0, Left
         * 1, Bottom
         * 2, Right
         * 3, Top
         */
        ArrayList<Segment> borders = b.getSegments();
        
        System.out.println(b.toString());
        
        Point initPoint1 = translatePoint(l.getP1());
        Point initPoint2 = translatePoint(l.getP2());
        
        Segment initSegment = new Segment(initPoint1, initPoint2);
        double slope = initSegment.slope();
        
        
        double dXLeft = initPoint1.getX() - corners.get(0).getX();
        double dYLeft = dXLeft * slope;
        Point leftAnchor = new Point("Left Anchor", initPoint1.getX()-dXLeft, initPoint1.getY()-dYLeft);
        //Check if this point is above or below the Y-borders
        
        double dXRight = corners.get(2).getX() - initPoint1.getX();
        double dYRight = dXRight * slope;
        Point rightAnchor = new Point("Right Anchor", initPoint1.getX()+dXRight, initPoint1.getY()+dYRight);
        //Check if this point is above or below the Y-borders
        
        Segment lineSegment = new Segment(leftAnchor, rightAnchor);
        System.out.println(lineSegment.toString());
        /* Intersect Cases:
         *   1: Left and Right
         *   2: Left and Bottom
         *   3: Left and Top
         *   4: Right and Bottom
         *   5: Right and Top
         *   6: Top and Bottom
         */
        if(lineSegment.LooseCrosses(borders.get(0)) && lineSegment.LooseCrosses(borders.get(2)))
        {
            //The line intersects the left and right borders - nothing else needs to be done
            System.out.println("1");
            return lineSegment;
        }
        
        if(lineSegment.LooseCrosses(borders.get(0)) && lineSegment.LooseCrosses(borders.get(1)))
        {
            //The line intersects the left and bottom borders - find the bottom intersection and create a new segment
            Point bottom = lineSegment.segmentIntersection(borders.get(1));
            System.out.println("2");
            return new Segment(leftAnchor, bottom);
        }
        if(lineSegment.LooseCrosses(borders.get(0)) && lineSegment.LooseCrosses(borders.get(3)))
        {
            //The line intersects the left and top borders - find the top intersection and create a new segment
            Point top = lineSegment.segmentIntersection(borders.get(3));
            System.out.println("3");
            return new Segment(leftAnchor, top);
        }
        if(lineSegment.LooseCrosses(borders.get(2)) && lineSegment.LooseCrosses(borders.get(1)))
        {
            //The line intersects the right and bottom borders - find bottom intersection and create a new segment
            Point bottom = lineSegment.segmentIntersection(borders.get(1));
            System.out.println("4");
            return new Segment(bottom, rightAnchor);
        }
        if(lineSegment.LooseCrosses(borders.get(2)) && lineSegment.LooseCrosses(borders.get(3)))
        {
            //The line intersects the right and top borders - find the top intersection and create a new segment
            Point top = lineSegment.segmentIntersection(borders.get(3));
            System.out.println("5");
            return new Segment(top, rightAnchor);
        }
        else //if(lineSegment.crosses(borders.get(1)) && lineSegment.crosses(borders.get(3)))
        {
            //The line intersects the top and bottom borders - find the intersections and create a new segment
            Point top = lineSegment.segmentIntersection(borders.get(3));
            Point bottom = lineSegment.segmentIntersection(borders.get(1));
            System.out.println("6");
            return new Segment(top, bottom);
        }
    }
    
    public static LineObject translateParallel(ParallelObject l)
    {
        LineObject original = (LineObject) l.getL();
        PointObject point = l.getP1();
        return null;
    }
}
