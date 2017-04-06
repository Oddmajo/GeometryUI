package backend.ast.Descriptors;

import java.util.HashSet;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.MaximalSegments;

/**
 * Class to be used within MaximalIntersections
 * <p>
 * Keeps a list of all "sub-intersections" for a maximal intersections.
 * A "sub-intersection" is an intersection between two subsegments, while
 * a maximal intersection is an intersection between two maximal segments.
 * @author Drew W
 *
 */
public class MaximalIntersection extends Intersection
{
    // The "main" or maximal intersection
    private Intersection _maximalIntersection;
    public Intersection getMaximalIntersection() { return _maximalIntersection; }
    
    // List of "sub-intersections" within the maximal intersection
    private HashSet<Intersection> _subintersections;
    public HashSet<Intersection> getSubintersections() { return _subintersections; }
    
    public MaximalIntersection(Intersection in)
    {
        super();
        
        // set the maximal intersection
        _maximalIntersection = in;
        
        // initialize subintersections list
        _subintersections = new HashSet<>();
    }
    
    public MaximalIntersection(Point i, Segment l, Segment r)
    {
        super(i,l,r);
        
        // set the maximal intersection
        _maximalIntersection = new Intersection(i,l,r);
        
        // initialize subsegments list
        _subintersections = new HashSet<>();
    }
    
    /**
     * Allows addition of subintersections
     * @param i subintersection to be added
     * @return true if addition is successful
     */
    public boolean addSubintersection(Intersection i)
    {
        // check if segments are subsegments of the MaximalSegments that
        // make up the maximal intersection
        
//        // get MaximalSegments instance
//        MaximalSegments maximalSegments = MaximalSegments.getInstance();
//        
//        // first check that the points are the same
//        if ( (MaximalSegments.getMaximalSegment(i.getlhs()) == _maximalIntersection.getlhs() && 
//                MaximalSegments.getMaximalSegment(i.getrhs()) == _maximalIntersection.getrhs()) ||
//                (MaximalSegments.getMaximalSegment(i.getlhs()) == _maximalIntersection.getrhs() && 
//                MaximalSegments.getMaximalSegment(i.getrhs()) == _maximalIntersection.getlhs()))
//        {
//            if (this.contains(i))
//            {
//                return false; // not added, already contained
//            }
//            else // add the subintersection
//            {
//                return _subintersections.add(i);
//            }
//        }
        // not a subintersection; return false
        return false;
    }
    
    /**
     * Checks if the provided intersection is STRUCTURALLY contained within the 
     * subintersections lsit.
     * @param i provided intersection
     * @return true if the provided intersection is structurally contained
     *
     */
    public boolean contains(Intersection i)
    {
        // check each intersection in the subintersections list
        for (Intersection subInt : _subintersections)
        {
            if (i.structurallyEquals(subInt))
            {
                return true;
            }
        }
        
        // no match
        return false;
    }
    
    /* (non-Javadoc)
     * @see backend.ast.Descriptors.Intersection#equals(java.lang.Object)
     * 
     * Check if another MaximalInterectin is structurally equal to this
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof MaximalIntersection)
        {
            MaximalIntersection mi = (MaximalIntersection) obj;
            
            // we only care if the maximal intersections are the same
            if (_maximalIntersection.structurallyEquals(mi.getMaximalIntersection()))
            {
                return true;
            }
        }        
        return false;
    }
    
    @Override
    public String toString()
    {
        String output = "maximal intersection(" + getMaximalIntersection() + "): subsegments {";
        if (getSubintersections() != null)
        for (Intersection sub : getSubintersections())
        {
            output += sub.toString() + ", ";
        }
        output += "}";
        
        return output;
    }
}
