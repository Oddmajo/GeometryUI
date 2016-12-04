/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.calculator;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.utilities.translation.OutPair;
import channels.fromUI.Diagram;

public class PlanarGraphConstructor
{
    
    ArrayList<Point> _points;
    ArrayList<Segment> _segments;

    /**
     * Constructor
     * Takes in a Diagram from the UI and calculates extra information
     * needed to do calculations
     * <p>
     * Calculated Information:
     *     Intersections (points and segments)
     *     
     * @param diagram    from the UI
     */
    public PlanarGraphConstructor(Diagram diagram)
    {
        // get the information from the diagram
        _points = diagram.getPoints();
        _segments = diagram.getSegments();
        int num_original_segments = _segments.size();
        
        //
        // find intersections between segments
        // if there are intersections, add points and segments
        //
        for (int i = 0; i < num_original_segments - 1; i++)
        {
            for (int j = i + 1; j < num_original_segments; j++)
            {
                OutPair<Point, Point> out = new OutPair<>();
                if (_segments.get(i).crosses(_segments.get(j)))
                {
                    _segments.get(i).FindIntersection(_segments.get(j), out);
                    if (out.getKey() != null && ( _segments.get(i).pointLiesOnAndExactlyBetweenEndpoints(out.getKey())
                            || _segments.get(j).pointLiesOnAndExactlyBetweenEndpoints(out.getKey())) )
                    {
                        if (!_points.contains(out.getKey()))
                        {
                            _points.add(out.getKey());
                            if (!(out.getKey().structurallyEquals(_segments.get(i).getPoint1())))
                                _segments.add(new Segment(out.getKey(), _segments.get(i).getPoint1()));
                            if (!(out.getKey().structurallyEquals(_segments.get(i).getPoint2())))
                                _segments.add(new Segment(out.getKey(), _segments.get(i).getPoint2()));
                            if (!(out.getKey().structurallyEquals(_segments.get(j).getPoint1())))
                                _segments.add(new Segment(out.getKey(), _segments.get(j).getPoint1()));
                            if (!(out.getKey().structurallyEquals(_segments.get(j).getPoint2())))
                                _segments.add(new Segment(out.getKey(), _segments.get(j).getPoint2()));
                        }
                    }
                    if (out.getValue() != null && ( _segments.get(i).pointLiesOnAndExactlyBetweenEndpoints(out.getValue())
                            || _segments.get(j).pointLiesOnAndExactlyBetweenEndpoints(out.getValue())) )
                    {
                        if (!_points.contains(out.getValue()))
                        {
                            _points.add(out.getValue());
                            if (!(out.getValue().structurallyEquals(_segments.get(i).getPoint1())))
                                _segments.add(new Segment(out.getValue(), _segments.get(i).getPoint1()));
                            if (!(out.getValue().structurallyEquals(_segments.get(i).getPoint2())))
                                _segments.add(new Segment(out.getValue(), _segments.get(i).getPoint2()));
                            if (!(out.getValue().structurallyEquals(_segments.get(j).getPoint1())))
                                _segments.add(new Segment(out.getValue(), _segments.get(j).getPoint1()));
                            if (!(out.getValue().structurallyEquals(_segments.get(j).getPoint2())))
                                _segments.add(new Segment(out.getValue(), _segments.get(j).getPoint2()));
                        }
                    }
                }
            }
        }
        System.out.println("PlanarGraphConstructor: Done finding intersections...");
    }
    
    /**
     * Construct the Planar Graph with both explicit information from the 
     * diagram and explicit information calculated in the constructor
     * @return the Planar Graph
     */
    public PlanarGraph constructGraph()
    {
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // add the points
        for (Point p : _points)
        {
            graph.addNode(p);
        }
        
        // add the segments
        for (Segment s: _segments)
        {
            graph.addUndirectedEdge(s.getPoint1(), s.getPoint2(), s.length(), EdgeType.REAL_SEGMENT);
        }
        
        return graph;
    }

}
