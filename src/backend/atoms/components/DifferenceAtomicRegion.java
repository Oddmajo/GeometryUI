/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.components;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.polygon.Polygon;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class DifferenceAtomicRegion extends AtomicRegion
{

    private AtomicRegion outerShape;  // { get; private set; }
    public AtomicRegion getOuterShape() { return outerShape; }
    
    private ArrayList<AtomicRegion> innerShapes; // { get; private set; }
    public ArrayList<AtomicRegion> getInnerShapes() { return innerShapes; }

    public DifferenceAtomicRegion(AtomicRegion outer, AtomicRegion inner) // : base()
    {
        super();
        outerShape = outer;
        innerShapes = new ArrayList<AtomicRegion>();
        innerShapes.add(inner);
    }

    // Can the area of this region be calcualted?
    @Override
    public boolean IsComputableArea()
    {
        if (!outerShape.IsComputableArea()) return false;
        
        for (AtomicRegion inner : innerShapes)
        {
            if (!inner.IsComputableArea()) return false;
        }
        return true;
    }

    // I don't know where/what the KnownMeasurementsAggregator is - Drew
//    @Override
//    public double GetArea(KnownMeasurementsAggregator known)
//    {
//        double outerArea = outerShape.GetArea(known);
//        if (outerArea < 0) return -1;
//
//        double innerArea = 0;
//        foreach (AtomicRegion inner in innerShapes)
//        {
//            double thisInnerArea = inner.GetArea(known);
//            if (thisInnerArea < 0) return -1;
//
//            innerArea += thisInnerArea;
//        }
//
//        return outerArea - innerArea;
//    }

    @Override
    public boolean PointLiesInside(Point pt)
    {
        if (!outerShape.PointLiesInside(pt)) return false;

        for (AtomicRegion inner : innerShapes)
        {
            if (inner.PointLiesInOrOn(pt)) return false;
        }

        return true;
    }

    // I don't think this has been implemented yet... Drew
//    //
//    // Takes a shape turns it into an approximate polygon (if needed)
//    // by converting all arcs into approximated arcs using many line segments.
//    //
//    @Override
//    public Polygon GetPolygonalized()
//    {
//        ExceptionHandler.throwException( new ArgumentException("Difference region is not a polygon."));
//        // return null;
//    }

    public boolean HasInnerAtom(AtomicRegion that)
    {
        for (AtomicRegion inner : innerShapes)
        {
            if (inner.Equals(that)) return true;
        }

        return false;
    }

    @Override
    public boolean Equals(Object obj)
    {
        //DifferenceAtomicRegion thatAtom = obj as DifferenceAtomicRegion;
        //if (thatAtom == null) return false;
        if (obj != null && obj instanceof DifferenceAtomicRegion)
        {
            DifferenceAtomicRegion thatAtom = (DifferenceAtomicRegion) obj;
            
            if (!outerShape.Equals(thatAtom.outerShape)) return false;
    
            if (this.innerShapes.size() != thatAtom.innerShapes.size()) return false;
    
            for (AtomicRegion inner : innerShapes)
            {
                if (!thatAtom.HasInnerAtom(inner)) return false;
            }
    
            return true;
        }
        
        return false;
    }

    @Override
    public String ToString()
    {
        String retString = "DifferenceAtom: (" + outerShape.ToString() + " - ";

        for (int i = 0; i < innerShapes.size(); i++)
        {
            retString += innerShapes.get(i).ToString();
            if (i < innerShapes.size() - 1) retString += " - ";
        }

        return retString;
    }

}
