/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.components;

import java.util.ArrayList;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.atoms.calculator.FacetCalculator;
import backend.atoms.calculator.Primitive;
import backend.atoms.calculator.PrimitiveToRegionConverter;
import backend.atoms.components.Connection.ConnectionType;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.translation.OutSingle;

public class AtomComposer
{

  ////
    //// The given atomic region is the 'outside' perimeter.
    //// All information is based on intersection points inside or on the perimeter.
    ////
    //List<AtomicRegion> Compose(List<Point> figurePoints, AtomicRegion theAtom,
    //                           List<AtomicRegion> intersecting, List<AtomicRegion> contained)
    //{
    //    List<AtomicRegion> atoms = new List<AtomicRegion>();

    //    foreach (AtomicRegion 

    //    return atoms;
    //}


    //
    // Combine two atoms together into a set of atomic regions.
    // toadd refers to atomic regions that are new / modified. 
    // toRemove is a subset of the atoms that may be removed from the worklist since they have been replaced as a non-atomic.
    //
    public static void Compose(ArrayList<Point> figurePoints, AtomicRegion thisAtom, AtomicRegion thatAtom, OutSingle<ArrayList<AtomicRegion>> toAdd, OutSingle<ArrayList<AtomicRegion>> toRemove) 
    {
        //toAdd = new OutSingle<ArrayList<AtomicRegion>>();
        //toRemove = new OutSingle<ArrayList<AtomicRegion>>();

        //
        // Do these regions interact at all?
        //
        if (thisAtom.OnExteriorOf(thatAtom)) return;

        //
        // If there is a single inscribed node then this is containment.
        // If there are no intersections then the regions may be (1) disjoint or (2) containment.
        //
        if (thisAtom.StrictlyContains(thatAtom) || thisAtom.ContainsWithOneInscription(thatAtom))
        {
            // why are these commented out?
            
            //AtomicRegion diff = GenerateDifferenceRegion(thisAtom, thatAtom);
            //toAdd(diff);
            //toRemove.Add(thisAtom);
            //return;
        }
        if (thatAtom.StrictlyContains(thisAtom) || thatAtom.ContainsWithOneInscription(thisAtom))
        {
            // why is this commmented out?
            
//            return GenerateDifferenceRegion(thatAtom, thisAtom);
        }


        //
        // The atoms overlap.
        //
        Overlap(figurePoints, thisAtom, thatAtom, toAdd, toRemove);
    }

    @SuppressWarnings("unused")
    private static ArrayList<AtomicRegion> GenerateDifferenceRegion(AtomicRegion outer, AtomicRegion inner)
    {
        return Utilities.MakeList(new DifferenceAtomicRegion(outer, inner));
    }

    //
    // Compose this atomic region with a segment
    // This operation will return a list of atomic regions iff the segment passes through the atomic region; this
    // creates two new atomic regions since the segment divides the atom.
    //
    //public static List<AtomicRegion> Compose(AtomicRegion thisAtom, Segment that, Figure owner)
    //{
    //    List<AtomicRegion> newAtoms = new List<AtomicRegion>();
    //    List<AtomicRegion.IntersectionAgg> intersections = thisAtom.GetIntersections(that);

    //    // If there is only 1 intersection then we may have a 'corner' inside of the atomic region.
    //    if (intersections.Count == 1) return newAtoms;

    //    if (intersections.Count > 2) throw new ArgumentException("More than 3 intersections due to a segment during atomic region composition");

    //    // If there are two intersection points, this atomic region is split into 2 regions.
    //    if (intersections.Count != 2)
    //    {
    //        throw new ArgumentException("Expected 2 intersections due to a segment during atomic region composition; have " + intersections.Count);
    //    }

    //    //
    //    // Split the region into 2 new atomic regions.
    //    //
    //    // (0) Order the connections of this atomic region.
    //    // (1) Make a copy of the list of connections
    //    // (2) Replace 2 intersected connections with (up to) 4 new connections. 
    //    // (3) Add this segment connection
    //    //
    //    thisAtom.OrderConnections();

    //    AtomicRegion newAtom1 = new AtomicRegion();
    //    AtomicRegion newAtom2 = new AtomicRegion();

    //    bool[] marked = new bool[intersections.Count];
    //    bool atom1 = true;
    //    foreach (Connection conn in thisAtom.connections)
    //    {
    //        bool found = false;
    //        for (int i = 0; i < intersections.Count; i++)
    //        {
    //            if (!marked[i])
    //            {
    //                marked[i] = true;
    //                if (conn.Equals(intersections[i].Key))
    //                {
    //                    found = true;

    //                    //
    //                    // How does this new segment intersect this connection?
    //                    //
    //                    // Endpoint
    //                    if (conn.HasPoint(intersections[i].Value))
    //                    {
    //                        // No-op
    //                    }
    //                    // Split this connection in the middle
    //                    else
    //                    {
    //                        Connection newConn1 = new Connection(conn.endpoint1, intersections[i].Value, conn.type, conn.segmentOwner);
    //                        Connection newConn2 = new Connection(conn.endpoint2, intersections[i].Value, conn.type, conn.segmentOwner);

    //                        //
    //                        // Which atomic region owns which new connection.
    //                        //
    //                        if (newAtom1.HasPoint(conn.endpoint1))
    //                        {
    //                            newAtom1.AddConnection(newConn1);
    //                            newAtom2.AddConnection(newConn2);
    //                        }
    //                        else if (newAtom2.HasPoint(conn.endpoint1))
    //                        {
    //                            newAtom2.AddConnection(newConn1);
    //                            newAtom1.AddConnection(newConn2);
    //                        }
    //                        // Neither atomic region has the point (possibly the first connection encountered).
    //                        else
    //                        {
    //                            // Arbitrary assignment
    //                            newAtom1.AddConnection(newConn1);
    //                            newAtom2.AddConnection(newConn2);
    //                        }
    //                    }

    //                    // Shift to the second (new) atomic region.
    //                    atom1 = false;
    //                }
    //            }
    //        }

    //        // This is not a splittable connection so just add it to the list.
    //        if (!found)
    //        {
    //            if (atom1) newAtom1.AddConnection(conn);
    //            else newAtom2.AddConnection(conn);
    //        }
    //    }

    //    //
    //    // Add this new segment as a connection to both atomic regions.
    //    //
    //    newAtom1.AddConnection(intersections[0].Value, intersections[1].Value, ConnectionType.SEGMENT, owner);
    //    newAtom2.AddConnection(intersections[0].Value, intersections[1].Value, ConnectionType.SEGMENT, owner);

    //    // Order the connections in the new regions we created.
    //    newAtom1.OrderConnections();
    //    newAtom2.OrderConnections();
    //    newAtoms.Add(newAtom1);
    //    newAtoms.Add(newAtom2);

    //    return newAtoms;
    //}

    public static void Overlap(ArrayList<Point> figurePoints, AtomicRegion thisAtom, AtomicRegion thatAtom, OutSingle<ArrayList<AtomicRegion>> toAdd, OutSingle<ArrayList<AtomicRegion>> toRemove) 
    {
        //
        // Acquire all arcs and segments.
        //
        ArrayList<Arc> graphArcs = new ArrayList<Arc>();
        ArrayList<Segment> graphSegments = new ArrayList<Segment>();
        for (Connection thisConn : thisAtom.connections)
        {
            if (thisConn.type == ConnectionType.SEGMENT && thisConn.segmentOrArc instanceof Segment) 
            {
                Segment seg = (Segment) thisConn.segmentOrArc;
                graphSegments.add(seg);
            }
            else if (thisConn.segmentOrArc instanceof Arc)
            {
                Arc arc = (Arc) thisConn.segmentOrArc;
                graphArcs.add(arc);
            }
        }

        for (Connection thatConn : thatAtom.connections)
        {
            if (thatConn.type == ConnectionType.SEGMENT && thatConn.segmentOrArc instanceof Segment) 
            {
               Segment seg = (Segment) thatConn.segmentOrArc;
               graphSegments.add(seg);
            }
            else if (thatConn.segmentOrArc instanceof Arc)
            {
                Arc arc = (Arc) thatConn.segmentOrArc;
                graphArcs.add(arc);
            }
        }

        //
        // Clear collinearities of all segments / arcs.
        //
        ArrayList<Circle> circles = new ArrayList<Circle>(); // get the list of applicable circles to these atoms.
        for (Segment seg : graphSegments) 
        {
            seg.ClearCollinear();
        }
        for (Arc arc : graphArcs)
        {
            Utilities.AddStructurallyUnique(circles, arc.getCircle());
            arc.ClearCollinear();
        }

        //
        // All points of interest for these atoms.
        //
        ArrayList<Point> allPoints = new ArrayList<Point>();
        allPoints.addAll(thisAtom.GetVertices());
        allPoints.addAll(thatAtom.GetVertices());

        //
        // Determine 'collinearities' for the intersections.
        //
        ArrayList<AtomicRegion.IntersectionAgg> intersections = thisAtom.GetIntersections(figurePoints, thatAtom);

        ArrayList<Point> intersectionPts = new ArrayList<Point>();
        for (AtomicRegion.IntersectionAgg agg : intersections)
        {
            if (agg.intersection1 != null)
            {
                if (!Utilities.HasStructurally(allPoints, agg.intersection1)) intersectionPts.add(agg.intersection1);
                agg.thisConn.segmentOrArc.addCollinearPoint(agg.intersection1);
                agg.thatConn.segmentOrArc.addCollinearPoint(agg.intersection1);
            }
            if (agg.intersection2 != null)
            {
                if (!Utilities.HasStructurally(allPoints, agg.intersection2)) intersectionPts.add(agg.intersection2);
                intersectionPts.add(agg.intersection2);
                agg.thisConn.segmentOrArc.addCollinearPoint(agg.intersection2);
                agg.thatConn.segmentOrArc.addCollinearPoint(agg.intersection2);
            }
        }

        // Add any unlabeled intersection points.
        allPoints.addAll(intersectionPts);

        //
        // Construct the Planar graph for atomic region identification.
        //
        PlanarGraph graph = new PlanarGraph();

        // Add the points as nodes in the graph.
        for (Point pt : allPoints)
        {
            graph.addNode(pt);
        }

        //
        // Edges are based on all the collinear relationships.
        //
        for (Segment segment : graphSegments)
        {
            for (int p = 0; p < segment.getCollinear().size() - 1; p++)
            {
                graph.addUndirectedEdge(segment.getCollinear().get(p), segment.getCollinear().get(p+1),
                                        new Segment(segment.getCollinear().get(p), segment.getCollinear().get(p+1)).length(),
                                        EdgeType.REAL_SEGMENT);
            }
        }

        for (Arc arc : graphArcs)
        {
            for (int p = 0; p < arc.getCollinear().size() - 1; p++)
            {
                graph.addUndirectedEdge(arc.getCollinear().get(p), arc.getCollinear().get(p+1),
                                        new Segment(arc.getCollinear().get(p), arc.getCollinear().get(p+1)).length(),
                                        EdgeType.REAL_ARC);
            }
        }

        //
        // Convert the planar graph to atomic regions.
        //
        PlanarGraph copy = new PlanarGraph(graph);
        FacetCalculator atomFinder = new FacetCalculator(copy);
        ArrayList<Primitive> primitives = atomFinder.GetPrimitives();
        ArrayList<AtomicRegion> atoms = PrimitiveToRegionConverter.Convert(graph, primitives, circles);

        //
        // Determine ownership of the atomic regions.
        //
        for (AtomicRegion atom : atoms)
        {
            if (thisAtom.Contains(atom))
            {
                atom.AddOwners(thisAtom.owners);
            }
            if (thatAtom.Contains(atom))
            {
                atom.AddOwners(thatAtom.owners);
            }
        }

        toAdd.set(atoms); 
        toRemove.set(new ArrayList<AtomicRegion>()); 
        toRemove.get().add(thisAtom);
        toRemove.get().add(thatAtom);
    }

}
