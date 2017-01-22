package backendTest.astTest.figure.delegates.generators;

import java.util.List;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class SegmentGenerator extends AbstractGenerator
{
    protected PointGenerator _pointGen;

    public SegmentGenerator()
    {
        super();
        _pointGen = new PointGenerator();
    }

    /*
     * @return a segment in the window (based on two random points)
     */
    public Segment genSegment() { return new Segment(_pointGen.genPoint(), _pointGen.genPoint()); }

    /*
     * @return a chord (segment with endpoints on the circle) 
     */
    public Segment genChordOn(Circle circle)
    {
        List<Point> points = _pointGen.genSnappingPointsOn(circle, 2);
        
        return new Segment(points.get(0), points.get(1));
    }
    
    /*
     * @return a diameter (chord passing through the center with endpoints on the circle) 
     */
    public Segment genDiameterOn(Circle circle)
    {
        Point p1 = _pointGen.genPointOn(circle);
        Point p2 = circle.OppositePoint(p1);
        
        return new Segment(p1, p2);
    }
    
    /*
     * @return a radius (passing through the center with endpoint on the circle) 
     */
    public Segment genRadius(Circle circle)
    {
        return new Segment(circle.getCenter(), _pointGen.genPointOn(circle));
    }
}
