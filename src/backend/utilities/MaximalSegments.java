package backend.utilities;

import java.util.HashSet;
import java.util.Set;

import backend.ast.figure.components.MaximalSegment;
import backend.ast.figure.components.Segment;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;

/**
 * A class to keep track of all MaximalSegments and return a segment's 
 * MaximalSegment
 * <p>
 * Uses the Singleton design
 * @author Drew W
 *
 */
public class MaximalSegments
{

    // The instance
    private static MaximalSegments _instance = null;

    // Set of MaximalSegments
    private static HashSet<MaximalSegment> _maximalSegments;
    public HashSet<MaximalSegment> getMaximalSegments() { return _maximalSegments; }


    protected MaximalSegments()
    {
        // initialize MaximalSegments list
        _maximalSegments = new HashSet<>();
    }

    public static MaximalSegments getInstance()
    {
        if(_instance == null) 
        {
            _instance = new MaximalSegments();            
        }

        return _instance;
    }

    /**
     * Add a MaximalSegment to the list if not already contained
     * @param ms MaximalSegment object to add 
     * @return true if MaximalSegment is added
     */
    public boolean addMaximalSegment(MaximalSegment ms)
    {
        //check if contained already
        for (MaximalSegment max : _maximalSegments)
        {
            if (ms.getMaximalSegment().containedIn(max))
            {
                return false; // already exists
            }
        }

        // not contained, add the Maximal Segment
        return _maximalSegments.add(ms);
    }

    /**
     * Add a set of MaximalSegment objects to the list if not already 
     * contained
     * @param msSet set of MaximalSegment objects to add
     * @return true if all objects added
     */
    public boolean addMaximalSegments(Set<MaximalSegment> msSet)
    {
        // bool to check if all Maximal Segments are added, default true
        boolean allAdded = true;
        int added = 0;
        int total = 0;

        // loop over set and call singular addMaximalSegment
        for (MaximalSegment ms : msSet)
        {
            // if Maximal Segment isn't added, flip allAdded
            if (!addMaximalSegment(ms))
            {
                ExceptionHandler.throwException(new DebugException("MaximalSegment not added: " + ms));
                allAdded = false;
            }
            else
            {
                added++;
            }
            total++;
        }

        if (!allAdded)
        {
            ExceptionHandler.throwException(new DebugException("addMaximalSegments: added " + added + " of " + total));
        }

        // return whether or not all maximal segments were added
        return allAdded;
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

    public boolean addSubsegments(Set<Segment> subSet)
    {
        // bool to check if all Maximal Segments are added, default true
        boolean allAdded = true;

        // loop over set and call singular addSubsegment
        for (Segment s : subSet)
        {
            // if Maximal Segment isn't added, flip allAdded
            if (!addSubsegment(s))
            {
                allAdded = false;
            }
        }

        // return whether or not all maximal segments were added
        return allAdded;
    }

    /**
     * Given a subsegment, return the corresponding MaximalSegment's segment
     * @param sub
     * @return
     */
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
    
    public static void clear()
    {
        _maximalSegments.clear();
        _instance = null;
    }

    @Override
    public String toString()
    {
        String output = "Maximal Segments [\n";

        for (MaximalSegment max : _maximalSegments)
        {
            output += max.toString() + "\n";
        }
        output += "]";

        return output;
    }

}
