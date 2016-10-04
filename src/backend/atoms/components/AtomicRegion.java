package backend.atoms.components;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Segment;

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
    public void SetKnownAtomic() { knownAtomic = true; }
    public boolean IsKnownAtomic() { return knownAtomic; }
    public void Clear()
    {
        knownAtomic = false;
        containedAtoms = new ArrayList<AtomicRegion>();
    }

    private ArrayList<AtomicRegion> containedAtoms;
    public void SetContained(ArrayList<AtomicRegion> contained) { containedAtoms = contained; }
    public boolean IsKnownNonAtomic() { return !containedAtoms.isEmpty(); }
    public ArrayList<AtomicRegion> GetContainedAtoms() { return containedAtoms; }
    public boolean UnknownAtomicStatus() { return !IsKnownAtomic() && !IsKnownNonAtomic(); }
    //
    // <------- End processing atomic region members
    //


    // A version of this region that is an approximate polygon.
    protected Polygon polygonalized;

    public List<Point> GetApproximatingPoints() throws IllegalArgumentException { return GetPolygonalized().getPoints(); }

    private Polygon GetPolygonalized() throws IllegalArgumentException
    {
        if (polygonalized != null) return polygonalized;

        ArrayList<Segment> sides = new ArrayList<Segment>();
        for (Connection conn : connections)
        {
            sides.addAll(conn.Segmentize());
        }

        polygonalized = Polygon.MakePolygon(sides);

        return polygonalized;
    }
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

    //
    // Can the area of this region be calculated?
    //
    protected double thisArea;
}
