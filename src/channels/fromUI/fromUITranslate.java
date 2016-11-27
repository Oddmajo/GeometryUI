package channels.fromUI;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.SegmentObject;

public class FromUITranslate
{
    public static Point translatePoint(PointObject p)
    {
        return new Point(p.getName(),p.getX(),p.getY());
    }
    
    public static Segment translateSegment(SegmentObject s)
    {
        Point p1 = translatePoint(s.getP1());
        Point p2 = translatePoint(s.getP2());
        return new Segment(p1, p2);
    }
}
