/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.calculator;

import java.util.ArrayList;

import backend.ast.ASTException;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.arcs.Semicircle;
import backend.atoms.components.AtomicRegion;
import backend.atoms.components.Connection.ConnectionType;
import backend.atoms.components.ShapeAtomicRegion;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.atoms.undirectedPlanarGraph.PlanarGraphEdge;

/**
 * @author Drew W
 *
 */
public class Filament extends Primitive
{
    
    public ArrayList<Point> points = null;

    /**
     * Instantiate a filament
     */
    public Filament()
    {
        points = new ArrayList<Point>();
    }
    
    /**
     * Add a Point to the filament
     * @param pt    the Point to be added
     */
    public void add(Point pt)
    {
        points.add(pt);
    }
    
    public String toString() 
    {
        String retS = "Filament { ";
        
        // traverse the array and add each point
        for (int i = 0; i < points.size(); i++)
        {
            retS += points.get(i);
            if (i < (points.size() - 1))
                retS += ", ";
        }
        
        retS += " }";
        
        return retS;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    //
    //                              Atomic Region Construction
    //
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * A matrix storing the memoized versions of atomic regions found: n x n where n is the number of points in the filament.
     */
    private Agg[][] memoized;
    
    /**
     * Extract the atomic regions.
     * @param graph     to extract regions from
     * @param circles   to store regions in
     * @return          the list of atomic regions
     * @throws ASTException 
     */
    public ArrayList<AtomicRegion> extractAtomicRegions(PlanarGraph graph, ArrayList<Circle> circles) throws ASTException
    {
        memoized = new Agg[points.size()][points.size()];
        
        // recursively construct
        return makeRegions(graph, circles, 0, points.size() - 1).atoms;
    }
    
    
    /**
     * Aggregation of variables
     * @author Chris Alvin
     * @modified Drew Whitmire
     *
     */
    class Agg
    {
        public int beginPointIndex;
        public int endPointIndex;
        public int coveredPoints;
        public Circle outerCircle;
        public ArrayList<AtomicRegion> atoms;

        public Agg(int b, int e, int cov, Circle outCirc, ArrayList<AtomicRegion> ats)
        {
            beginPointIndex = b;
            endPointIndex = e;
            coveredPoints = cov;
            outerCircle = outCirc;
            atoms = ats;
        }
    }
    
    private Agg makeRegions(PlanarGraph graph, ArrayList<Circle> circles, int beginIndex, int endIndex) throws ASTException
    {
        // if there is already a region stored at the given position
        if (memoized[beginIndex][endIndex] != null) return memoized[beginIndex][endIndex];
        
        //find the circle for these given points
        Circle outerCircle = null;
        for (Circle circle : circles)
        {
            if (circle.pointLiesOn(points.get(beginIndex)) && circle.pointLiesOn(points.get(endIndex))) outerCircle = circle;
        }
        
        // Base Case: Gap between the given indices is 1
        if (endIndex - beginIndex == 1)
        {
            return new Agg(beginIndex, endIndex, -1, outerCircle, handleConnection(graph, circles, points.get(beginIndex), points.get(endIndex)));
        }
        
        // Look at all combinations of indices from beginIndex to endIndex
        // Start with larger gaps between indices -> small gaps
        Agg maxLeftCoveredAgg = null;
        Agg maxRightCoveredAgg = null;
        int maxCoveredNodes = 0;
        for (int gap = endIndex - beginIndex - 1; gap > 0; gap--)
        {
            for (int index = beginIndex; index < endIndex; index++)
            {
                Agg left = makeRegions(graph, circles, index, index + gap);
                Agg right = makeRegions(graph, circles, index + gap, endIndex);
                
                // Check for new maximum coverage
                if (left.coveredPoints + right.coveredPoints > maxCoveredNodes)
                {
                    maxLeftCoveredAgg = left;
                    maxRightCoveredAgg = right;
                }
                
                // Found complete coverage
                if (left.coveredPoints + right.coveredPoints == endIndex - beginIndex + 1)
                {
                    maxCoveredNodes = endIndex - beginIndex + 1;
                    break;
                }
            }
        }
        
        //
        // We have the two maximal circles: create the new regions
        //
        // The atoms from the left / right
        ArrayList<AtomicRegion> atoms = new ArrayList<>();
        atoms.addAll(maxLeftCoveredAgg.atoms);
        atoms.addAll(maxRightCoveredAgg.atoms);
        
        // New regions are based on this outer circle minus the left / right outer circles
        AtomicRegion newAtomTop = new AtomicRegion();
        AtomicRegion newAtomBottom = new AtomicRegion();
        
        // The outer circle
        newAtomTop.addConnection(points.get(beginIndex), points.get(endIndex), ConnectionType.ARC, outerCircle);
        newAtomBottom.addConnection(points.get(beginIndex), points.get(endIndex), ConnectionType.ARC, outerCircle);
        
        // The left / right maximal circle
        // these lines were cut off, I need to talk to Dr. Alvin
        newAtomTop.addConnection(points.get(maxLeftCoveredAgg.beginPointIndex), points.get(maxLeftCoveredAgg.endPointIndex), ConnectionType.ARC, outerCircle); 
        newAtomBottom.addConnection(points.get(maxLeftCoveredAgg.beginPointIndex), points.get(maxLeftCoveredAgg.endPointIndex), ConnectionType.ARC, outerCircle);
        
        newAtomTop.addConnection(points.get(maxRightCoveredAgg.beginPointIndex), points.get(maxRightCoveredAgg.endPointIndex), ConnectionType.ARC, outerCircle);
        newAtomBottom.addConnection(points.get(maxRightCoveredAgg.beginPointIndex), points.get(maxRightCoveredAgg.endPointIndex), ConnectionType.ARC, outerCircle);
        
        atoms.add(newAtomTop);
        atoms.add(newAtomBottom);
        
        //
        // Make / Return the new aggregator
        return new Agg(beginIndex, endIndex, maxCoveredNodes, outerCircle, atoms);
    }

    
    /**
     * Private method for the makeRegion function
     * @param graph     input graph to handle connections on
     * @param circles   input circles
     * @param pt1       point 1 to get the edge from
     * @param pt2       point 2 to get the edge from
     * @return          the list of AtomicRegions
     * @throws ASTException
     */
    private ArrayList<AtomicRegion> handleConnection(PlanarGraph graph, ArrayList<Circle> circles, Point pt1, Point pt2) throws ASTException
    {
        ArrayList<AtomicRegion> atoms = new ArrayList<>();
        
        PlanarGraphEdge edge = graph.getEdge(pt1, pt2);
        
        if (edge == null)
        {
            return atoms;
        }
        
        // 
        // Find the one circle that applies to this set of points
        //
        Circle theCircle = null;
        
        for (Circle circle : circles)
        {
            if (circle.pointLiesOn(pt1) && circle.pointLiesOn(pt2))
            {
                theCircle = circle;
            }
        }
        
        switch (edge.edgeType)
        {
            case REAL_ARC:
            case REAL_DUAL:
                atoms.addAll(createSectors(theCircle, pt1, pt2));
                break;
            case EXTENDED_SEGMENT:
                break;
            case REAL_SEGMENT:
                break;
            default:
                break;
        }
        
        return atoms;
    }
    
    /**
     * Private method for the handleConnection method to create Sectors
     * @param circle        input circle
     * @param pt1           point to get an edge from
     * @param pt2           point to get an edge to
     * @return              the list of AtomicRegions
     * @throws ASTException
     */
    private ArrayList<AtomicRegion> createSectors(Circle circle, Point pt1, Point pt2) throws ASTException
    {
        ArrayList<AtomicRegion> atoms = new ArrayList<AtomicRegion>();
        
        Segment diameter = new Segment(pt1, pt2);
        
        if (circle.DefinesDiameter(diameter))
        {
            Point midpt = circle.getMidpoint(pt1, pt2);
            atoms.add(new ShapeAtomicRegion(new Sector(new Semicircle(circle, pt1, pt2, midpt, diameter))));
            atoms.add(new ShapeAtomicRegion(new Sector(new Semicircle(circle, pt1, pt2, circle.OppositePoint(midpt), diameter))));
        }
        else 
        {
            atoms.add(new ShapeAtomicRegion(new Sector(new MinorArc(circle, pt1, pt2))));
            atoms.add(new ShapeAtomicRegion(new Sector(new MajorArc(circle, pt1, pt2))));
        }
        
        return atoms;
    }
    
}
