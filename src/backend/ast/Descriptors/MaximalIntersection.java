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

    public MaximalIntersection(Intersection in)
    {
        super();

        // set the maximal intersection
        _maximalIntersection = in;
    }

    public MaximalIntersection(Point i, Segment l, Segment r)
    {
        super(i,l,r);

        // set the maximal intersection
        _maximalIntersection = new Intersection(i,l,r);
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
        // get point and segments from i
        Point p = i.getIntersect();
        Segment s1 = i.getlhs();
        Segment s2 = i.getrhs();

        // compute whether intersection is contained and return
        return computeContains(p, s1, s2);
    }

    public boolean contains(Point p, Segment s1, Segment s2)
    {        
        // compute whether intersection is contained and return
        return computeContains(p, s1, s2);       
    }

    private boolean computeContains(Point p, Segment s1, Segment s2)
    {
        // check if the intersection is at the same point first
        if (_maximalIntersection.getIntersect().structurallyEquals(p))
        {
            // then check if the segments are subsegments 
            // get MaximalSegments instance
            MaximalSegments maximalSegments = MaximalSegments.getInstance();
            if ((maximalSegments.getMaximalSegment(s1).structurallyEquals(_maximalIntersection.getlhs()) && 
                    maximalSegments.getMaximalSegment(s2).structurallyEquals(_maximalIntersection.getrhs())) ||
                    (maximalSegments.getMaximalSegment(s1).structurallyEquals(_maximalIntersection.getrhs()) && 
                    maximalSegments.getMaximalSegment(s2).structurallyEquals(_maximalIntersection.getlhs())))
            {
                return true; // segments are subsegments of maximal intersection's segments
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
        String output = "maximal intersection(" + getMaximalIntersection() + ")";

        return output;
    }
}
