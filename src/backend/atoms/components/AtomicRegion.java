/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.components;

import java.util.ArrayList;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Segment;
import backend.atoms.components.Connection.ConnectionType;
import backend.utilities.exception.NotImplementedException;
import backend.utilities.translation.OutPair;
import backend.utilities.translation.OutSingle;

/**
 * This class needs a lot of translating and looking over.
 * @author Drew W
 *
 */
public class AtomicRegion
{
    private boolean ordered; // Are the connections ordered?
    protected ArrayList<Connection> connections;
    public ArrayList<Connection> getConnections() { return connections; } 
    protected ArrayList<Figure> owners;
    public ArrayList<Figure> getOwners() { return owners; } 
    private Figure topOwner;

    //
    // <------- The following are for processing atomic regions.
    //
    private boolean knownAtomic;
    public void setKnownAtomic() { knownAtomic = true; }
    public boolean isKnownAtomic() { return knownAtomic; }
    public void clear()
    {
        knownAtomic = false;
        containedAtoms = new ArrayList<AtomicRegion>();
    }

    private ArrayList<AtomicRegion> containedAtoms;
    public void setContained(ArrayList<AtomicRegion> contained) { containedAtoms = contained; }
    public boolean isKnownNonAtomic() { return !containedAtoms.isEmpty(); }
    public ArrayList<AtomicRegion> getContainedAtoms() { return containedAtoms; }
    public boolean unknownAtomicStatus() { return !isKnownAtomic() && !isKnownNonAtomic(); }
    //
    // <------- End processing atomic region members
    //
    
    
    //
    // <------- Figure Synthesis (begin) --------------------------------------------------------------------------
    //
    //
    // Is this shape congruent to the other shape based purely on coordinates.
    //
    public boolean CoordinateCongruent(Figure that) throws NotImplementedException
    {
        throw new NotImplementedException();
    }

//    private static void SplitConnections(AtomicRegion atom, out List<Segment> segs, out List<Arc> arcs)
//    {
//        segs = new List<Segment>();
//        arcs = new List<Arc>();
//
//        foreach (Connection conn in atom.connections)
//        {
//            if (conn.segmentOrArc != null)
//            {
//                if (conn.type == ConnectionType.SEGMENT) segs.Add(conn.segmentOrArc as Segment);
//                if (conn.type == ConnectionType.ARC) arcs.Add(conn.segmentOrArc as Arc);
//            }
//        }
//    }
//
//    public virtual bool CoordinateCongruent(AtomicRegion that)
//    {
//        //
//        // Collect segment and arc connections.
//        //
//        List<Segment> thisSegs = new List<Segment>();
//        List<Arc> thisArcs = new List<Arc>();
//        List<Segment> thatSegs = new List<Segment>();
//        List<Arc> thatArcs = new List<Arc>();
//
//        SplitConnections(this, out thisSegs, out thisArcs);
//        SplitConnections(that, out thatSegs, out thatArcs);
//
//        //
//        // We must have the same number of each type of connections
//        //
//        if (thisSegs.Count != thatSegs.Count) return false;
//        if (thisArcs.Count != thatArcs.Count) return false;
//
//        //
//        // This is a naive approach since a more formal approach should follow order of connections; should generally work
//        //
//        //
//        // An atomic region must have the same number of segments, each of the same length.
//        //
//        bool[] marked = new bool[thisSegs.Count];
//        foreach (Segment thisSeg in thisSegs)
//        {
//            bool found = false;
//            for (int c = 0; c < thatSegs.Count; c++)
//            {
//                if (!marked[c])
//                {
//                    if (thisSeg.CoordinateCongruent(thatSegs[c]))
//                    {
//                        marked[c] = true;
//                        found = true;
//                        break;
//                    }
//                }
//            }
//            if (!found) return false;
//        }
//        // Redundant:
//        // if (marked.Contains(false)) return false;
//
//        // Exit early if no arcs.
//        if (!thisArcs.Any()) return true;
//
//        //
//        // An atomic region must have the same number of arcs, each of the same length (using the radius of the circle).
//        //
//        marked = new bool[thisArcs.Count];
//        foreach (Arc thisArc in thisArcs)
//        {
//            bool found = false;
//            for (int c = 0; c < thatArcs.Count; c++)
//            {
//                if (!marked[c])
//                {
//                    if (thisArc.CoordinateCongruent(thatArcs[c]))
//                    {
//                        marked[c] = true;
//                        found = true;
//                        break;
//                    }
//                }
//            }
//            if (!found) return false;
//        }
//        // Redundant:
//        // if (marked.Contains(false)) return false;
//
//        return true;
//    }
//    //
//    // <------- Figure Synthesis (end) --------------------------------------------------------------------------
//    //
//
    // A version of this region that is an approximate polygon.
    protected Polygon polygonalized; //{ get; protected set; }

    public ArrayList<Point> GetApproximatingPoints() { return GetPolygonalized().getPoints(); }

    public AtomicRegion()
    {
        ordered = false;
        connections = new ArrayList<Connection>();
        owners = new ArrayList<Figure>();
        topOwner = null;
        knownAtomic = false;
        polygonalized = null;
        thisArea = -1;
    }

    public Figure GetTopMostShape() { return topOwner; }

//    // Add a shape to the list which contains this region.
//    public virtual void AddOwner(Figure f)
//    {
//        if (!Utilities.AddStructurallyUnique<Figure>(owners, f)) return;
//
//        // Check if this new atomic region is the outermost owner.
//        if (topOwner == null || f.Contains(topOwner)) topOwner = f;
//    }
//
//    public virtual void AddOwners(List<Figure> fs)
//    {
//        foreach (Figure f in fs) AddOwner(f);
//    }

    public void ClearOwners()
    {
        owners.clear();
        topOwner = null;
    }

    // Convert the region to a polygon and then use the normal detection technique to determine if it is in the interior.
    public boolean PointLiesInside(Point pt)
    {
        if (pt == null || PointLiesOn(pt)) { return false; }

        else { return GetPolygonalized().IsInPolygon(pt); }
    }

//    public virtual bool PointLiesInOrOn(Point pt)
//    {
//        if (pt == null) return false;
//
//        return PointLiesOn(pt) || PointLiesInside(pt);
//    }

    //
    // Can the area of this region be calculated?
    //
//    public double GetArea(KnownMeasurementsAggregator known) { return thisArea; }
    protected double thisArea;

    // Can the area of this region be calculated?
    public boolean IsComputableArea() { return false; }

    public int GetHashCode() { return super.hashCode(); }

    public boolean Equals(Object obj)
    {
        if (obj != null && obj instanceof AtomicRegion)
        {
            AtomicRegion thatAtom = (AtomicRegion) obj;
            
            if (this.connections.size() != thatAtom.connections.size()) { return false; }
    
            for (Connection conn : this.connections)
            {
                if (!thatAtom.HasConnection(conn)) return false;
            }
    
            return true;
        }
        
        else { return false; }
    }

    public void addConnection(Point e1, Point e2, ConnectionType t, Figure owner)
    {
        connections.add(new Connection(e1, e2, t, owner));
    }

    public void AddConnection(Connection conn)
    {
        connections.add(conn);
    }

//    public bool HasPoint(Point that)
//    {
//        foreach (Connection conn in connections)
//        {
//            if (conn.HasPoint(that)) return true;
//        }
//
//        return false;
//    }
//
//    //  |)
//    //  | )
//    //  |  )
//    //  | )
//    //  |)
//    public bool IsDefinedByChordCircle()
//    {
//        if (connections.Count != 2) return false;
//
//        if (!HasSegmentConnection() || !HasArcConnection()) return false;
//
//        return AllConnectionsHaveSameEndpoints();
//    }
//
//    //  ---\
//    //   )  \
//    //    )  \
//    //     )  |
//    //    )  /
//    //   )  /
//    //  ---/
//    public bool IsDefinedByCircleCircle()
//    {
//        if (connections.Count != 2) return false;
//
//        if (!HasSegmentConnection() && HasArcConnection()) return true;
//
//        return AllConnectionsHaveSameEndpoints();
//    }
//
//    public bool DefinesAPolygon()
//    {
//        return !HasArcConnection();
//    }
//
//    private int NumSegmentConnections()
//    {
//        int count = 0;
//        foreach (Connection conn in connections)
//        {
//            if (conn.type == ConnectionType.SEGMENT) count++;
//        }
//
//        return count;
//    }
//
//    private int NumArcConnections()
//    {
//        int count = 0;
//        foreach (Connection conn in connections)
//        {
//            if (conn.type == ConnectionType.ARC) count++;
//        }
//
//        return count;
//    }
//
//    private bool HasSegmentConnection()
//    {
//        foreach (Connection conn in connections)
//        {
//            if (conn.type == ConnectionType.SEGMENT) return true;
//        }
//
//        return false;
//    }
//
//    private bool HasArcConnection()
//    {
//        foreach (Connection conn in connections)
//        {
//            if (conn.type == ConnectionType.ARC) return true;
//        }
//
//        return false;
//    }
//
//    private bool AllConnectionsHaveSameEndpoints()
//    {
//        Point e1 = connections[0].endpoint1;
//        Point e2 = connections[1].endpoint2;
//
//        for (int c = 1; c < connections.Count; c++)
//        {
//            if (!connections[c].HasPoint(e1) || !connections[c].HasPoint(e2)) return false;
//        }
//
//        return true;
//    }

    public boolean HasConnection(Connection that)
    {
        for (Connection conn : this.connections)
        {
            if (conn.StructurallyEquals(that)) return true;
        }

        return false;
    }

    public boolean PointLiesOn(Point pt)
    {
        if (pt == null) return false;

        for (Connection conn : connections)
        {
            if (conn.PointLiesOn(pt)) return true;
        }

        return false;
    }

    public boolean PointLiesOnOrInside(Point pt)
    {
        if (pt == null) return false;

        return PointLiesOn(pt) || PointLiesInside(pt);
    }

    public boolean PointLiesExterior(Point pt)
    {
        if (pt == null) return false;

        return !PointLiesOnOrInside(pt);
    }

    //
    // Do all the endpoints in that region lie within this region?
    // And, are all intersection points, if any, on this perimeter?
    //
    public boolean Contains(AtomicRegion that)
    {
        //
        // Do all vertices of that lie on the interior of this atomic region?
        //
        ArrayList<Point> thatVertices = that.GetVertices();
        for (Point vertex : thatVertices)
        {
            if (!this.PointLiesOnOrInside(vertex)) return false;
        }

        //
        // Check all midpoints of connections are on the interior.
        //
        for (Connection thatConn : that.connections)
        {
            if (!this.PointLiesOnOrInside(thatConn.Midpoint())) return false;
        }

        //
        // For any intersections between the atomic regions, the resultant points of intersection must be on the perimeter.
        //
        ArrayList<IntersectionAgg> intersections = this.GetIntersections(that);
        for (IntersectionAgg agg : intersections)
        {
            if (agg.overlap)
            {
                // No-Op
            }
            else
            {
                if (!this.PointLiesOn(agg.intersection1)) return false;
                if (agg.intersection2 != null)
                {
                    if (!this.PointLiesOn(agg.intersection2)) return false;
                }
            }
        }

        return true;
    }

//    //
//    // Do all the endpoints in that region lie within this region?
//    // There should be no intersection points.
//    //
//    public bool StrictlyContains(AtomicRegion that)
//    {
//        //
//        // Do all vertices of that lie on the interior of this atomic region?
//        //
//        List<Point> thatVertices = that.GetVertices();
//        foreach (Point vertex in thatVertices)
//        {
//            if (!this.PointLiesInside(vertex)) return false;
//        }
//
//        // There should be no intersections
//        return !this.GetIntersections(that).Any();
//    }
//
    //
    // A region (that) lies inside this with one intersection.
    //
    public boolean ContainsWithOneInscription(AtomicRegion that)
    {
        //
        // Do all vertices of that lie on the interior of this atomic region?
        //
        ArrayList<Point> thatVertices = that.GetVertices();
        for (Point vertex : thatVertices)
        {
            if (!this.PointLiesOnOrInside(vertex)) return false;
        }

        // There should be only ONE intersection
        return this.GetIntersections(that).size() == 1;
    }
//
//    //
//    // A region (that) lies inside this with one intersection.
//    //
//    public bool ContainsWithGreaterOneInscription(AtomicRegion that)
//    {
//        //
//        // Do all vertices of that lie on the interior of this atomic region?
//        //
//        List<Point> thatVertices = that.GetVertices();
//        foreach (Point vertex in thatVertices)
//        {
//            if (!this.PointLiesOnOrInside(vertex)) return false;
//        }
//
//        // There should be only ONE intersection
//        return this.GetIntersections(that).Count > 1;
//    }
//
//    //
//    // All vertices of that region on the perimeter of this.
//    // Number of intersections must equate to the number of vertices.
//    //
//    public bool Inscribed(AtomicRegion that)
//    {
//        List<Point> thatVertices = that.GetVertices();
//        foreach (Point vertex in thatVertices)
//        {
//            if (!this.PointLiesOn(vertex)) return false;
//        }
//
//        return this.GetIntersections(that).Count == thatVertices.Count;
//    }
//
//    public bool Circumscribed(AtomicRegion that)
//    {
//        return that.Inscribed(this);
//    }
//
//    public bool HasVertexExteriorTo(AtomicRegion that)
//    {
//        List<IntersectionAgg> intersections = this.GetIntersections(that);
//
//        foreach (IntersectionAgg agg in intersections)
//        {
//            if (!agg.overlap)
//            {
//                if (!this.PointLiesOnOrInside(agg.intersection1)) return true;
//            }
//        }
//
//        return false;
//    }
//
//    //
//    // If there is no interaction between these atomic regions OR just touching
//    //
//    public bool OnExteriorOf(AtomicRegion that)
//    {
//        List<IntersectionAgg> intersections = this.GetIntersections(that);
//
//        // All vertices cannot be interior to the region.
//        List<Point> thatVertices = that.GetVertices();
//        foreach (Point vertex in thatVertices)
//        {
//            if (this.PointLiesInside(vertex)) return false;
//        }
//
//        // All intersections must be overlap; only point-based intersections which are on the perimeter.
//        foreach (IntersectionAgg agg in intersections)
//        {
//            if (!agg.overlap)
//            {
//                if (agg.intersection2 != null) return false;
//                if (!this.PointLiesOn(agg.intersection1)) return false;
//            }
//            else // agg.overlap
//            {
//                // No-Op
//            }
//        }
//
//        return true;
//    }
//
//    //
//    // If there is no interaction between these atomic regions OR just touching
//    //
//    public bool InteriorOfWithTouching(AtomicRegion that)
//    {
//        List<IntersectionAgg> intersections = this.GetIntersections(that);
//
//        // All vertices cannot be interior to the region.
//        List<Point> thatVertices = that.GetVertices();
//        foreach (Point vertex in thatVertices)
//        {
//            if (this.PointLiesOnOrInside(vertex)) return false;
//        }
//
//        // All intersections must overlap; only point-based intersections which are on the perimeter.
//        foreach (IntersectionAgg agg in intersections)
//        {
//            if (agg.overlap)
//            {
//                // No-Op
//            }
//            else
//            {
//                if (PointLiesExterior(agg.intersection1)) return false;
//                if (agg.intersection2 != null)
//                {
//                    if (PointLiesExterior(agg.intersection2)) return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    //
//    // Imagine 2 equilateral triangles to make the Star of David: all 
//    //
//    public bool OverlapWithSingleConnections(AtomicRegion that)
//    {
//        List<IntersectionAgg> intersections = this.GetIntersections(that);
//
//        foreach (IntersectionAgg agg in intersections)
//        {
//            if (agg.overlap)
//            {
//                // No-Op
//            }
//            else
//            {
//                if (agg.intersection1 == null || agg.intersection2 == null) return false;
//            }
//        }
//
//        return true;
//    }
//
//    //
//    // Does this region overlap the other based on points (not intersections).
//    //
//    private bool Overlap(List<Point> points)
//    {
//        bool interiorPoint = false;
//        bool exteriorPoint = false;
//        foreach (Point pt in points)
//        {
//            if (this.PointLiesInside(pt)) interiorPoint = true;
//            else if (!this.PointLiesOn(pt)) exteriorPoint = true;
//        }
//        return interiorPoint && exteriorPoint;
//    }
//
//    public bool OverlapsWith(AtomicRegion that)
//    {
//        // Point based overlapping.
//        if (Overlap(that.GetApproximatingPoints())) return true;
//
//        // Crossing-based overlap.
//        List<IntersectionAgg> intersections = this.GetIntersections(that);
//        foreach (IntersectionAgg agg in intersections)
//        {
//            if (agg.thisConn.Crosses(agg.thatConn)) return true;
//        }
//        
//        return false;
//    }

    //
    // Takes a non-shape atomic region and turns it into an approximate polygon,
    // by converting all arcs into approximated arcs using many line segments.
    //
    public Polygon GetPolygonalized()
    {
        if (polygonalized != null) { return polygonalized; }

        ArrayList<Segment> sides = new ArrayList<Segment>();
        for (Connection conn : connections)
        {
            sides.addAll(conn.Segmentize());
        }

        polygonalized = Polygon.MakePolygon(sides);

        return polygonalized;
    }

    //
    // Does the given connection pass through the atomic region? Or is, it completely outside of the region?
    //
//    public bool NotInteriorTo(Connection that)
//    {
//        if (this.PointLiesInside(that.endpoint1)) return false;
//        if (this.PointLiesInside(that.endpoint2)) return false;
//
//        int standOnCount = 0;
//        foreach (Connection thisConn in this.connections)
//        {
//            if (thisConn.Crosses(that)) return false;
//            if (thisConn.StandsOnNotEndpoint(that)) standOnCount++;
//        }
//
//        return standOnCount <= 1;
//    }
//
    //
    // Acquire the two connections that have the given point.
    //
    private void GetConnections(Point pt, OutSingle<Connection> conn1, OutSingle<Connection> conn2)
    {
        ArrayList<Connection> conns = new ArrayList<Connection>();

        for (Connection conn : connections)
        {
            if (conn.HasPoint(pt)) conns.add(conn);
        }

        conn1.set(conns.get(0));
        conn2.set(conns.get(0));
    }

    //
    // Order the connections so that one endpoint leads to the next (sequentially).
    //
    @SuppressWarnings("null")
    public void OrderConnections()
    {
        if (connections.isEmpty()) { return; }

        ArrayList<Connection> orderedConns = new ArrayList<Connection>();

        // Add the arbitrary first connection and choose a point to follow 
        orderedConns.add(connections.get(0));
        Point currPoint = connections.get(0).endpoint1;
        Connection currConn = connections.get(0);

        for (int i = 1; i < connections.size(); i++)
        {
            OutSingle<Connection> c1 = null;
            OutSingle<Connection> c2 = null;
            GetConnections(currPoint, c1, c2);
            if (currConn.equals(c1))
            {
                orderedConns.add(c2.get());
                currConn = c2.get();
                currPoint = c2.get().OtherEndpoint(currPoint);
            }
            else if (currConn.equals(c2))
            {
                orderedConns.add(c1.get());
                currConn = c1.get();
                currPoint = c1.get().OtherEndpoint(currPoint);
            }
        }

        // Update the actual connections with the ordered set.
        connections = orderedConns;
        ordered = true;
    }

    //
    // Acquire the set of point making up the vertices of this region.
    //
    public ArrayList<Point> GetVertices()
    {
        // Ensure ordered connections for simple acquisition.
        if (!ordered) OrderConnections();

        ArrayList<Point> vertices = new ArrayList<Point>();

        Point currPoint = connections.get(0).endpoint1;
        vertices.add(currPoint);

        for (int i = 1; i < connections.size(); i++)
        {
            currPoint = connections.get(i).OtherEndpoint(currPoint);
            vertices.add(currPoint);
        }

        return vertices;
    }

    public class IntersectionAgg
    {
        public Connection thisConn;
        public Connection thatConn;
        public Point intersection1;
        public Point intersection2;
        public boolean overlap;

        public boolean MixedTypes() { return thisConn.type != thatConn.type; }
    }

    //
    // Get the index of the intersection with the same point of intersection.
    //
    private int IntersectionIndex(ArrayList<IntersectionAgg> intersections, Point pt)
    {
        if (pt == null) return -1;

        for (int a = 0; a < intersections.size(); a++)
        {
            if (pt.structurallyEquals(intersections.get(a).intersection1)) return a;
            if (pt.structurallyEquals(intersections.get(a).intersection2)) return a;
        }

        return -1;
    }

    //
    // Add to the list of intersections only if the intersection point is not already in the list (avoids duplicates due to endpoints).
    // We have as many as 4 intersections since a segment may intersect the endpoint of twe connections.
    // This prevents against 4 intersections, ensure 2 intersections only.
    //
    private void AddIntersection(ArrayList<IntersectionAgg> intersections, IntersectionAgg newAgg)
    {
        //
        // Favor an intersection that intersects twice over an intersection that intersects once.
        //
        int index1 = IntersectionIndex(intersections, newAgg.intersection1);
        int index2 = IntersectionIndex(intersections, newAgg.intersection2);

        // Not found, so add this new intersection.
        if (index1 == -1 && index2 == -1)
        {
            intersections.add(newAgg);
            return;
        }

        IntersectionAgg agg1 = index1 != -1 ? intersections.get(index1) : null;
        IntersectionAgg agg2 = index2 != -1 ? intersections.get(index2) : null;

        // This, by default, means newAgg has 2 intersections.
        if (index1 != -1 && index2 != -1)
        {
            intersections.remove(agg1);
            intersections.remove(agg2);
            intersections.add(newAgg);
            return;
        }

        // Favor two intersections, if applicable.
        if (index1 != -1 && index2 == -1)
        {
            // Two intersections favored.
            if (newAgg.intersection1 != null && newAgg.intersection2 != null)
            {
                intersections.remove(agg1);
                intersections.add(newAgg);
            }
            else
            {
                // We already have 1 intersection.
            }
            return;
        }

        // Two intersections implied.
        if (index1 == -1 && index2 != -1)
        {
            intersections.remove(agg2);
            intersections.add(newAgg);
            return;
        }
    }


//
//    //
//    // Determine the intersection points / connections between this atomic region and another region.
//    //
    public ArrayList<IntersectionAgg> GetIntersections(ArrayList<Point> figurePoints, AtomicRegion thatAtom)
    {
        ArrayList<IntersectionAgg> intersections = new ArrayList<IntersectionAgg>();

        for (Connection thisConn : this.connections)
        {
            for (Connection thatConn : thatAtom.connections)
            {
                OutPair<Point, Point> inter = new OutPair<>();
                inter.set(null, null);
                thisConn.FindIntersection(figurePoints, thatConn, inter);
                
                // if intersections are found
                if (inter.getKey() != null)
                {
                    IntersectionAgg newAgg = new IntersectionAgg();
                    newAgg.thisConn = thisConn;
                    newAgg.thatConn = thatConn;
                    newAgg.intersection1 = inter.getKey();
                    newAgg.intersection2 = inter.getValue();
                    newAgg.overlap = thisConn.Overlap(thatConn);
                    AddIntersection(intersections, newAgg);
                }
                else if (thisConn.Overlap(thatConn))
                {
                    IntersectionAgg newAgg = new IntersectionAgg();
                    newAgg.thisConn = thisConn;
                    newAgg.thatConn = thatConn;
                    newAgg.intersection1 = null;
                    newAgg.intersection2 = null;
                    newAgg.overlap = true;
                    AddIntersection(intersections, newAgg);
                }
            }
        }

        return intersections;
    }

    private ArrayList<IntersectionAgg> GetIntersections(AtomicRegion thatAtom)
    {
        ArrayList<IntersectionAgg> intersections = new ArrayList<IntersectionAgg>();

        for (Connection thisConn : this.connections)
        {
            for (Connection thatConn : thatAtom.connections)
            {
                OutPair<Point, Point> inter = new OutPair<>();
                inter.set(null, null);
                thisConn.FindIntersection(thatConn, inter);

                if (thisConn.Overlap(thatConn))
                {
                    IntersectionAgg newAgg = new IntersectionAgg();
                    newAgg.thisConn = thisConn;
                    newAgg.thatConn = thatConn;
                    newAgg.intersection1 = null;
                    newAgg.intersection2 = null;
                    newAgg.overlap = true;
                    AddIntersection(intersections, newAgg);
                }
                else if (inter.getKey() != null)
                {
                    IntersectionAgg newAgg = new IntersectionAgg();
                    newAgg.thisConn = thisConn;
                    newAgg.thatConn = thatConn;
                    newAgg.intersection1 = inter.getKey();
                    newAgg.intersection2 = inter.getValue();
                    newAgg.overlap = thisConn.Overlap(thatConn);
                    AddIntersection(intersections, newAgg);
                }

            }
        }

        return intersections;
    }

    //
    // Convert an atomic region to a planar graph.
    //
//    public UndirectedPlanarGraph.PlanarGraph ConvertToPlanarGraph()
//    {
//        UndirectedPlanarGraph.PlanarGraph graph = new UndirectedPlanarGraph.PlanarGraph();
//
//        if (!ordered) OrderConnections();
//
//        //
//        // Traverse the connections and add vertices / edges 
//        //
//        foreach (Connection conn in connections)
//        {
//            graph.AddNode(conn.endpoint1);
//            graph.AddNode(conn.endpoint2);
//
//            graph.AddUndirectedEdge(conn.endpoint1, conn.endpoint2,
//                                    new Segment(conn.endpoint1, conn.endpoint2).Length,
//                                    conn.type == ConnectionType.ARC ? UndirectedPlanarGraph.EdgeType.REAL_ARC : UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
//        }
//
//        return graph;
//    }

    public String ToString()
    {
        StringBuilder str = new StringBuilder();

        str.append("Atom: {");

        for (int c = 0; c < connections.size(); c++)
        {
            str.append(connections.get(c).toString());
            if (c < connections.size() - 1) str.append(", ");
        }

        str.append(" }");

        return str.toString();
    }

    public String CheapPrettyString()
    {
        StringBuilder str = new StringBuilder();

        str.append("{");

        for (int c = 0; c < connections.size(); c++)
        {
            str.append(connections.get(c).CheapPrettyString());
            if (c < connections.size() - 1) str.append(", ");
        }

        str.append("}");

        return str.toString();
    }
}

