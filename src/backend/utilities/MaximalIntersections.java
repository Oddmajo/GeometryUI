package backend.utilities;

import java.util.HashSet;
import java.util.Set;

import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MaximalIntersection;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;

/**
 * A class to keep track of all MaximalIntersections and return an intersection's
 * MaximalIntersection
 * @author Drew W
 *
 */
public class MaximalIntersections
{
    // The instance
    private static MaximalIntersections _instance = null;
    
    // set of MaximalIntersections
    static private HashSet<MaximalIntersection> _maximalIntersections;
    public HashSet<MaximalIntersection> getMaximalIntersections() { return _maximalIntersections; }
    
    
    protected MaximalIntersections()
    {
        // initialize MaximalIntersections list
        _maximalIntersections = new HashSet<>();
    }
    
    public static MaximalIntersections getInstance()
    {
        if(_instance == null) 
        {
            _instance = new MaximalIntersections();            
        }

        return _instance;
    }
    
    /**
     * Add a MaximalIntersection to the list if not already contained
     * @param mi MaximalIntersectin to add
     * @return true if mi is added
     */
    public boolean addMaximalIntersection(MaximalIntersection mi)
    {
        // check if contained already
        for (MaximalIntersection max : _maximalIntersections)
        {
            // if mi equals another maximal intersection or if mi is a 
            // subintersection of max
            if (mi.equals(max) || max.contains(mi.getMaximalIntersection()))
            {
                return false; // already exists
            }
        }
        
        // not contained, add the maximal intersection
        return _maximalIntersections.add(mi);
    }
    
    /**
     * Add a set of MaximalIntersection objects to the list if not already contained
     * @param miSet set of MaximalIntersection object to add
     * @return true if all objects added
     */
    public boolean addMaximalIntersections(Set<MaximalIntersection> miSet)
    {
     // bool to check if all Maximal Segments are added, default true
        boolean allAdded = true;
        int added = 0;
        int total = 0;
        
        // loop over set and call singular addMaximalSegment
        for (MaximalIntersection mi : miSet)
        {
            // if Maximal Segment isn't added, flip allAdded
            if (!addMaximalIntersection(mi))
            {
                ExceptionHandler.throwException(new DebugException("MaximalIntersection not added: " + mi));
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
            ExceptionHandler.throwException(new DebugException("addMaximalIntersections: added " + added + " of " + total));
        }
        
        // return whether or not all maximal segments were added
        return allAdded;
    }      
    
    /**
     * Given an intersections, return the corresponding MaximalIntersection's intersection
     * @param i
     * @return
     */
    public Intersection getMaximalIntersection(Intersection i)
    {
        // go through all Maximal Intersections
        for (MaximalIntersection max : _maximalIntersections)
        {
            // if i is a subintersection of max
            if (max.contains(i))
            {
                // contains checks if the points are the same and then
                // that the segments of i are subsegments of max
                
                // return the maximal intersection
                return max.getMaximalIntersection();
            }
        }
        
        // no match found, return null
        return null;
    }
    
    public static void clear()
    {
        _maximalIntersections.clear();
        _instance = null;
    }
    
    @Override
    public String toString()
    {
        String output = "Maximal Intersections [\n";
        
        for (MaximalIntersection max : _maximalIntersections)
        {
            output += max.toString() + "\n";
        }
        output += "]";
        
        return output;
    }
}
