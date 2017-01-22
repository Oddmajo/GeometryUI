package backend.atoms.main;

import java.util.List;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.PointFactory;

//
// Contains a pre-processed figure:
//    @param Point Factory
//    @param List of ALL segments
//    @param List of all circles
//    @param List of polygons (from the pre-computer)
//
public class EssentialFigure
{
    private PointFactory _points;
    public PointFactory getPointFactory() { return _points; }

    private List<Segment> _segments;
    public List<Segment> getSegments() { return _segments; }
    
    private List<Circle> _circles;
    public List<Circle> getCircles() { return _circles; }
    
    private List<List<Polygon>> _polygons;
    public List<List<Polygon>> getPolygons() { return _polygons; }
    
    public EssentialFigure(PointFactory points, List<Segment> segments, List<Circle> circles, List<List<Polygon>> polygons)
    {
        _points = points;
        _segments = segments;
        _circles = circles;
        _polygons = polygons;
    }
}
