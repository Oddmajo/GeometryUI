///*
// * iTutor – an intelligent tutor of mathematics
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
package backend.ast.Descriptors.Arcs_and_Circles;

import backend.ast.Descriptors.CircleIntersection;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;

public class CircleCircleIntersection extends CircleIntersection
{
    protected Circle otherCircle;
    
    public Circle getOtherCircle()
    {
        return otherCircle;
    }
    
    public CircleCircleIntersection(Point p, Circle c1, Circle c2)
    {
    	//The C# code had just two parameters. So this needs to be checked for correctness
        super(p,c1,null); 
        
        otherCircle = c2;
        
        //Find the intersection points
        Point pt1, pt2;
        
        /////////////////////////////////////////////////////////////////////////theCircle.FindIntersection(otherCircle, out pt1, out pt2);
        intersection1 = pt1;
        intersection2 = pt2;
    }
    
    //If the arcs intersect at a single point
    @Override
    public boolean isTangent()
    {
    	return intersection1 != null && intersection2 == null;
    }
    
    //if the segment starts on this arc and extends outward
    @Override
    public boolean standsOn()
    {
    	return false;
    }
    
    //If not tangent, circles pass through each other
    @Override
    public boolean StructurallyEquals(Object obj)
    {
    	if(obj != null & obj instanceof CircleCircleIntersection)
    	{
    		CircleCircleIntersection inter = (CircleCircleIntersection)obj;
    		return this.otherCircle.StructurallyEquals(inter.otherCircle) &&
    					this.getIntersect().StructurallyEquals(inter.getIntersect()) &&
    					this.otherCircle.StructurallyEquals(inter.theCircle);
    	}
    	return false;
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if(obj != null & obj instanceof CircleCircleIntersection)
    	{
    		CircleCircleIntersection inter = (CircleCircleIntersection)obj;
    		return this.otherCircle.equals(inter.otherCircle) &&
    					this.getIntersect().equals(inter.getIntersect()) &&
    					this.otherCircle.equals(inter.theCircle);
    	}
    	return false;
    }
    
    @Override
    public int GetHashCode()
    {
    	return super.GetHashCode();
    }
    
    @Override
    public String toString()
    {
    	return "CircleCircleIntersection(" + intersect.toString() + ", " + theCircle.toString() + ", " + otherCircle.toString() + ") " + justification;
    }
}
