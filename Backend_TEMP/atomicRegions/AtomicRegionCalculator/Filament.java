/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package atomicRegions.AtomicRegionCalculator;

import java.util.ArrayList;
import atomicRegions.UndirectedPlanarGraph.PlanarGraph;

import atomicRegions.AtomicRegion;
import concreteAST.Figure.Circle;
import concreteAST.Figure.Point;

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
    public void Add(Point pt)
    {
        points.add(pt);
    }
    
    public String ToString() 
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
     */
    public ArrayList<AtomicRegion> ExtractAtomicRegions(PlanarGraph graph, ArrayList<Circle> circles)
    {
        memoized = new Agg[points.size()][points.size()];
        
        // recursively construct
        return MakeRegions(graph, circles, 0, points.size() - 1).atoms;
    }
    
    
    /**
     * I need to find out what this class is/does
     * @author Drew W
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
    
    private Agg MakeRegions(PlanarGraph graph, ArrayList<Circle> circles, int beginIndex, int endIndex)
    {
        // if there is already a region stored at the given position
        if (memoized[beginIndex][endIndex] != null) return memoized[beginIndex][endIndex];
        
        //find the circle for these given points
        Circle outerCircle = null;
        for (Circle circle : circles)
        {
            if (circle.PointLiesOn(points.get(beginIndex)) && circle.PointLiesOn(points.get(endIndex))) outerCircle = circle;
        }
        
        // Base Case: Gap between the given indices is 1
        // this is where I need to continue
        
        
        int maxCoveredNodes = 0;
        ArrayList<AtomicRegion> atoms = null;
        return new Agg(beginIndex, endIndex, maxCoveredNodes, outerCircle, atoms);
    }
    
    
}
