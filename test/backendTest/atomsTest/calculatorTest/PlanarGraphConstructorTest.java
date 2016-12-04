/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backendTest.atomsTest.calculatorTest;

import java.util.ArrayList;

import org.junit.Test;

import backend.atoms.calculator.PlanarGraphConstructor;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.atoms.undirectedPlanarGraph.PlanarGraphNode;
import channels.fromUI.Diagram;

public class PlanarGraphConstructorTest
{

    @Test public void PlanarGraphConstuctorTriangleTest()
    {
        System.out.println("Running PlanarGraphConstuctorTriangleTest");
        
        // create the triangle diagram
        Diagram diagram = new Diagram();
        diagram.premade_Triangles();
        
        // construct the planar graph
        PlanarGraphConstructor graphConstructor = new PlanarGraphConstructor(diagram);
        
        // get the planar graph
        PlanarGraph graph = graphConstructor.constructGraph();
        
        // print the graph to check
        System.out.println("Planar Graph:");
        System.out.println(graph.toString());
        
        // count the total degree of the graph
        ArrayList<PlanarGraphNode> nodes = graph.getNodes();
        int count = 0;
        for (PlanarGraphNode n : nodes)
        {
            count += n.nodeDegree();
        }
        System.out.println("Total Node Count: " + graph.count());
        System.out.println("Total Graph Degree: " + count);
        
        if (count ==  28 && graph.count() == 6)
        {
            System.out.println("Pass");
        }
        else
        {
            System.out.println("Fail");
            assert(false);
        }
    }

}
