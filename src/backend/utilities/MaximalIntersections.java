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
    // set of MaximalIntersections
    static private HashSet<MaximalIntersection> _maximalIntersections;
    public HashSet<MaximalIntersection> getMaximalIntersections() { return _maximalIntersections; }
    
    
    public MaximalIntersections()
    {
        // initialize MaximalIntersections list
        _maximalIntersections = new HashSet<>();
    }
    
    public MaximalIntersections(HashSet<MaximalIntersection> maxInters)
    {
     // initialize MaximalIntersections list
        _maximalIntersections = maxInters;
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
            // TODO: check if contained in another MaximalIntersection
            if (mi.equals(max))
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
    
    public boolean addSubintersection(Intersection sub)
    {
        // try to add to each MaximalIntersection (is check on addition, returns 
        // false if not a subintersection)
        for (MaximalIntersection max : _maximalIntersections)
        {
            // if successfully added, return true
            if (max.addSubintersection(sub))
            {
                return true;
            }
        }
        
        // not a subintersection of any Maximal Intersections, return false
        return false;
    }
    
    public boolean addSubintersections(Set<Intersection> subSet)
    {
        // bool to check if all Maximal Segments are added, default true
        boolean allAdded = true;
        
        // loop over set and call singular addSubsegment
        for (Intersection i : subSet)
        {
            // if Maximal Segment isn't added, flip allAdded
            if (!addSubintersection(i))
            {
                allAdded = false;
            }
        }
        
        // return whether or not all maximal segments were added
        return allAdded;
    }
    
    /**
     * Given an intersections, return the corresponding MaximalIntersection's intersection
     * @param i
     * @return
     */
    static public Intersection getMaximalIntersection(Intersection i)
    {
        // go through all Maximal Intersections
        for (MaximalIntersection max : _maximalIntersections)
        {
            // if i is a subintersection of max
            if ( (MaximalSegments.getMaximalSegment(i.getlhs()) == max.getlhs() && 
                    MaximalSegments.getMaximalSegment(i.getrhs()) == max.getrhs()) ||
                    (MaximalSegments.getMaximalSegment(i.getlhs()) == max.getrhs() && 
                    MaximalSegments.getMaximalSegment(i.getrhs()) == max.getlhs()))
            {
                // make sure the subintersectin is actually contained in the maximal intersection
                for (Intersection maximalSub : max.getSubintersections())
                {
                    if (maximalSub.structurallyEquals(i))
                    {
                        return max.getMaximalIntersection();
                    }
                }
            }
        }
        
        // no match found, return null
        return null;
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
