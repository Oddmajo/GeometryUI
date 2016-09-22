package PolyID;

import java.util.ArrayList;

public class Connection
{
    public enum ConnectionType { SEGMENT, ARC }
    
    public Point endpoint1;
    public Point endpoint2;
    public ConnectionType type;

    // The shape which has this connection. 
    public Figure segmentOrArc;
    
    public Connection(Point e1, Point e2, ConnectionType t, Figure so)
    {
        endpoint1 = e1;
        endpoint2 = e2;
        type = t;
        segmentOrArc = so;
    }

    public ArrayList<Segment> Segmentize()
    {
        if(this.type == ConnectionType.SEGMENT) return Utilities.MakeList(new Segment(this.endpoint1, this.endpoint2));

        return segmentOrArc.Segmentize();
    }
    
}
