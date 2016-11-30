package backend.atoms.calculator;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.translation.OutPair;
import channels.fromUI.Diagram;

public class PlanarGraphConstructor
{
    
    ArrayList<Point> _points;
    ArrayList<Segment> _segments;

    public PlanarGraphConstructor(Diagram diagram)
    {
        // get the information from the diagram
        _points = diagram.getPoints();
        _segments = diagram.getSegments();
        
        // find intersections between segments
        // if there are intersections, add points
        for (int i = 0; i < _segments.size() - 1; i++)
        {
            for (int j = i + 1; j < _segments.size(); j++)
            {
                OutPair<Point, Point> out = new OutPair<>();
                _segments.get(i).FindIntersection(_segments.get(j), out);
            }
        }
    }

}
