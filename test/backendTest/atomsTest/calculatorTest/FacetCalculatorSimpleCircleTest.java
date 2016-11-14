/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backendTest.atomsTest.calculatorTest;

import org.junit.Test;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.MinimalCycle;
import backend.atoms.calculator.Primitive;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;

public class FacetCalculatorSimpleCircleTest
{

    @Test public void FacetCalculator_SingleCircleTest()
    {
        System.out.println("Running FacetCalculator_SingleCircleTest...");
        
        //
        // construct the circle planar graph how it would be created
        // in the planar graph constructor ( i think )
        //
        
        // create the graph
        PlanarGraph graph = new PlanarGraph();
        
        // create points
        Point point1 = new Point("point1", 4, 4);
        Point point2 = new Point("point2", 2, 4);
        Point point3 = new Point("point3", 4, 6);
        Point point4 = new Point("point4", 6, 4);
        Point point5 = new Point("point5", 4, 2);
        
        // add points to the graph
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addNode(point4);
        graph.addNode(point5);
        
        // add edges to the graph
        graph.addUndirectedEdge(point1, point2, 2, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point1, point4, 2, EdgeType.REAL_SEGMENT);
        graph.addUndirectedEdge(point2, point3, 2, EdgeType.REAL_ARC);
        graph.addUndirectedEdge(point3, point4, 2, EdgeType.REAL_ARC);
        graph.addUndirectedEdge(point4, point5, 2, EdgeType.REAL_ARC);
        graph.addUndirectedEdge(point5, point2, 2, EdgeType.REAL_ARC);
        
        //print the graph
        //System.out.println(graph.toString());
        
        // create the facet calculator
        FacetCalculator calc = new FacetCalculator(graph);
        
        // print the primitives from the facet calculator
        System.out.println("Primitives: ");
        for (Primitive prim : calc.GetPrimitives())
        {
            if (prim instanceof MinimalCycle)
            {
                MinimalCycle mincycle = (MinimalCycle) prim;
                System.out.println("\t" + mincycle.toString());
            }
            
        }
        
        System.out.println("Done");
    }
    
}
