package backend.ast.figure.components;

import java.util.HashSet;

/**
 * Class to be used within MaximalSegments
 * <p>
 * Keeps a list of all subsegments for a maximal segment.
 * A subsegment is a segment which is coinciding with the maximal segment.
 * @author Drew W
 *
 */
public class MaximalSegment extends Segment
{
    
    // The Longest Segment
    private Segment _maximalSegment;
    public Segment getMaximalSegment() { return _maximalSegment; }
    
    // List of sub-segments within the maximal segment
    private HashSet<Segment> _subsegments;
    public HashSet<Segment> getSubsegments() { return _subsegments; }

    public MaximalSegment(Segment in)
    {
        super(in);
        
        // set maximal segment
        _maximalSegment = in;
        
        // initialize subsegments list
        _subsegments = new HashSet<>();
    }

    public MaximalSegment(Point p1, Point p2)
    {
        super(p1, p2);

        // set maximal segment
        _maximalSegment = new Segment(p1, p2);
        
        // initialize subsegments list
        _subsegments = new HashSet<>();
    }
    
    /**
     * Allows addition of subsegments
     * @param s the segment to add as a subsegment
     * @return true if addition is successful
     */
    public boolean addSubsegment(Segment s)
    {
        // only add if s is coinciding with the maximal segment
        if (s.containedIn(_maximalSegment))
        {
            // check if already structurally contained within the set
            if (this.contains(s))
            {
                return false; // not added, already contained
            }
            else // add the subsegment
            {
                return _subsegments.add(s);
            }
        }
        else // not coinciding, return false
        {
            return false;
        }
    }
    
    /* (non-Javadoc)
     * @see backend.ast.figure.components.Segment#contains(backend.ast.figure.components.Segment)
     * 
     * Checks if the provided segment is STRUCTURALLY contained within the subsegments list.
     */
    @Override
    public boolean contains(Segment s)
    {
        // check each segment in the subsegments set
        for (Segment sub : _subsegments)
        {
            if (s.structurallyEquals(sub))
            {
                return true;
            }
        }
        
        // no match; return false
        return false;
    }
    
    /**
     * @param ms
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof MaximalSegment)
        {
            MaximalSegment ms = (MaximalSegment) obj;
            //TODO Check if we want to see if they are coinciding
            // We only care if the maximal segments are the same
            if (_maximalSegment.structurallyEquals(ms.getMaximalSegment()))
            {
                return true;
            }
            
        }
        return false;
    }

}
