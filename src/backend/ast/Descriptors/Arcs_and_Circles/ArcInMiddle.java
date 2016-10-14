///*
// * iTutor � an intelligent tutor of mathematics
//
//Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of
//
//students)
//
//This program is free software: you can redistribute it and/or modify it under
//
//the terms of the GNU Affero General Public License as published by the Free
//
//Software Foundation, either version 3 of the License, or (at your option) any
//
//later version.
//
//This program is distributed : the hope that it will be useful, but WITHOUT
//
//ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
//
//FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
//
//details.
// */
///**
// * @author Tom_Nielsen
// *
// */

package ast.Descriptors.Arcs_and_Circles;

import ast.Descriptors.Descriptor;
import ast.figure.components.Arc;
import ast.figure.components.Point;

public class ArcInMiddle extends Descriptor
{
    private Point point;
    private Arc arc;
    
    public Point getPoint()
    {
        return point;
    }
    
    public Arc getArc()
    {
        return arc;
    }
    
    //// old previously commented out code
    //// Can this relationship can strengthened to a Midpoint?
    ////
    //public Strengthened CanBeStrengthened()
    //{
    //    if (Utilities.CompareValues(Point.calcDistance(point, Arc.Point1), Point.calcDistance(point, Arc.Point2)))
    //    {
    //        return new Strengthened(this, new Midpoint(this));
    //    }

    //    return null;
    //}

    //public override bool CanBeStrengthenedTo(GroundedClause gc)
    //{
    //    Midpoint midpoint = gc as Midpoint;
    //    if (midpoint == null) return false;

    //    return this.point.StructurallyEquals(midpoint.point) && this.Arc.StructurallyEquals(midpoint.Arc);
    //}
    
    @Override
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj != null)
        {
            ArcInMiddle im = (ArcInMiddle)obj;
            return im.point.Equals(this.point) && im.arc.StructurallyEquals(this.arc);
        }
        else
        {
            return false;
        }
        
    }
    
    @Override
    public boolean Equals(Object obj)
    {
        if(obj != null)
        {
            ArcInMiddle im = (ArcInMiddle)obj;
            return im.point.Equals(this.point) && im.arc.Equals(this.arc);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public String toString()
    {
        return "ArcInMiddle(" + this.point.toString() + ", " + this.arc.toString() + ") " + justification;
    }
}
