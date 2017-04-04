package backend.utilities;

import java.util.HashSet;

import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Segment;

/**
 * A class to keep track of all MaximalSegments and return a segment's 
 * MaximalSegment
 * @author Drew W
 *
 */
public class MaximalSegments
{
    // Set of MaximalSegmnts
    private HashSet<MaximalSegment> _maximalSegments;
    public HashSet<MaximalSegment> getMaximalSegments() { return _maximalSegments; }
    

    public MaximalSegments()
    {
        // initialize MaximalSegments list
        _maximalSegments = new HashSet<>();
    }
    
    public MaximalSegments(HashSet<MaximalSegment> maxSegs)
    {
        // initialize MaximalSegments list
        _maximalSegments = maxSegs;
    }
    
    /**
     * Add a MaximalSegment to the list if not already contained
     * @param ms
     * @return
     */
    public boolean addMaximalSegment(MaximalSegment ms)
    {
        //check if contained already
        for (MaximalSegment max : _maximalSegments)
        {
            if (ms.equals(max))
            {
                return false; // already exists
            }
        }
        
        // not contained, add the Maximal Segment
        return _maximalSegments.add(ms);
    }
    
    /**
     * Add a subsegment to the appropriate MaximalSegment object
     * @param sub the subsegment to add
     * @return true if addition is successful
     */
    public boolean addSubsegment(Segment sub)
    {
        // check if coinciding with each MaximalSegment
        for (MaximalSegment max : _maximalSegments)
        {
            if (sub.containedIn(max.getMaximalSegment()))
            {
                // add the subsegment to the coinciding MaximalSegment
                return max.addSubsegment(sub);
            }
        }
        
        // no coinciding MaximalSegments
        return false;
    }
    
    public Segment getMaximalSegment(Segment sub)
    {
        // check if coinciding with each MaximalSegment
        for (MaximalSegment max : _maximalSegments)
        {
            if (sub.containedIn(max.getMaximalSegment()))
            {
                // make sure the subsegment is actually contained in the Maximal Segment
                for (Segment maximalSub : max.getSubsegments())
                {
                    if (maximalSub.structurallyEquals(sub))
                    {
                        return max.getMaximalSegment();
                    }
                }
            }
        }
        
        // No match found, return null
        return null;
    }
    
    public String toString()
    {
        String output = "Maximal Segments [\n";
        
        for (MaximalSegment max : _maximalSegments)
        {
            output += "maximal segment(" + max.getMaximalSegment() + "): subsegments {";
            if (max.getSubsegments() != null)
            for (Segment sub : max.getSubsegments())
            {
                output += sub.toString() + ", ";
            }
            output += "}\n";
        }
        output += "]";
        
        return output;
    }

}
