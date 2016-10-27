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
import backend.ast.figure.components.Segment;

public class CircleSegmentIntersection extends CircleIntersection
{
	private Point intersect;
	private Circle circle;
	private Segment segment;
	
	public Point getIntersect()
	{
		return intersect;
	}
	public Circle getCircle()
	{
		return circle;
	}
	public Segment getSegment()
	{
		return segment;
	}
	
	 /// <summary>
    /// Create a new intersection between a segment and a circle.
    /// </summary>
    /// <param name="i">The point of intersection.</param>
    /// <param name="c">The circle.</param>
    /// <param name="s">The segment.</param>
	public CircleSegmentIntersection(Point i, Circle c, Segment s)
	{
		
		//THIS NEEDS TO BE TESTED 
		//The c# code just had a blank base() call so I added just nulls to the parameters since there is only one constructor for the class
		super(null,null,null);
		intersect = i;
		circle = c;
		segment = s;
	}
	
	/// <summary>
    /// Tests if the two intersections are structurally equal.
    /// </summary>
    /// <param name="obj">The other object to test.</param>
    /// <returns>True if the parameter is structurally equal to this intersection.</returns>
	@Override
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof CircleSegmentIntersection)
		{
			CircleSegmentIntersection csi = (CircleSegmentIntersection)obj;
			return intersect.StructurallyEquals(csi.intersect) && circle.StructurallyEquals(csi.circle)
					&& segment.StructurallyEquals(csi.segment);
		}
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public String toString()
	{
		return "CircleSegmentIntersection(" + intersect.toString() + ", " + circle.toString() + ", " +segment.toString() + ") " +justification;
	}
}
