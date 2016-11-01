/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.atoms.components;

import backend.ast.figure.Figure;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;

/**
 * @author Drew W
 *
 */
public class ShapeAtomicRegion extends AtomicRegion
{
    // Variables
    private Figure shape;
    public Figure getShape() { return shape; }

    /**
     * Constructs a ShapeAtomicRegion from a Figure
     * @param f     the given Figure
     */
    public ShapeAtomicRegion(Figure f)
    {
        super();
        shape = f;
        connections = f.MakeAtomicConnections();
    }

    /**
     * Changes the shape to the given Figure
     * @param f     the given Figure
     */
    public void ReshapeForStrenghthening(Figure f)
    {
        shape = f;
    }

    /* 
     * Returns true if the given Figure is congruent to this
     * @return      true if the shapes are congruent
     */
    public boolean CoordinateCongruent(Figure that)
    {
        return shape.CoordinateCongruent(that);
    }
    
    /**
     * Returns true if the given AtomicRegion is congruent to this
     * @param that  the given AtomicRegion
     * @return      true if that AtomicRegion is congruent to this
     */
    public boolean CoordinateCongruent(AtomicRegion that)
    {
        if (that != null && that instanceof ShapeAtomicRegion)
        {
        ShapeAtomicRegion shapeAtom = (ShapeAtomicRegion) that;
        return this.shape.CoordinateCongruent(shapeAtom.shape);
        }
        
        else { return false; }
    }

    // Can the area of this region be calcualted?
    public boolean IsComputableArea() { return true; }
    
//    /* 
//     * Needs the class KnownMeasurementsAggregator
//     */
//    public double GetArea(KnownMeasurementsAggregator known)
//    {
//        if (thisArea > 0) return thisArea;
//
//        thisArea = shape.GetArea(known);
//
//        return thisArea;
//    }

    /* 
     * Returns true if the given point lies on this Figure
     * @param pt    the given point
     */
    public boolean PointLiesOn(Point pt)
    {
        if (pt == null) return false;

        return shape.PointLiesOn(pt);
    }

    /* 
     * Returns true if the given point lies inside this Figure
     */
    public boolean PointLiesInside(Point pt)
    {
        if (pt == null || this.PointLiesOn(pt)) { return false; }
        
        else { return shape.PointLiesInside(pt); }
    }

    /* Takes a shape turns it into an approximate polygon (if needed)
     * by converting all arcs into approximated arcs using many line segments.
     */
    public Polygon GetPolygonalized()
    {
        if (polygonalized != null) { return polygonalized; }

        polygonalized = shape.GetPolygonalized();

        return polygonalized;
    }

    /* 
     * Returns the hashcode of the Figure
     */
    public int GetHashCode() { return super.GetHashCode(); }

    /* 
     * Returns true if the given object equals this
     */
    public boolean Equals(Object obj)
    {
        if (obj != null && obj instanceof ShapeAtomicRegion)
        {
            ShapeAtomicRegion thatAtom = (ShapeAtomicRegion) obj;
            return (shape.structurallyEquals(thatAtom.shape) && super.Equals(obj) );
        }
        else { return false; }
    }

    /* 
     * Returns the Figure as a string
     */
    public String ToString()
    {
        return "ShapeAtom: (" + shape.toString() + ")";
    }

    /* 
     * Returns the Figure as a string
     */
    public String CheapPrettyString()
    {
        return shape.CheapPrettyString();
    }

    /* 
     * Returns true if this contains the given AtomicRegion
     * @param that      the given AtomicRegion
     */
    public boolean Contains(AtomicRegion that)
    {
        if (that != null && that instanceof ShapeAtomicRegion)
        {
            ShapeAtomicRegion thatAtom = (ShapeAtomicRegion) that;
            
            return this.shape.Contains(thatAtom.shape);
            
        }
        else
        {
            return super.Contains(that);
        }
    }
}