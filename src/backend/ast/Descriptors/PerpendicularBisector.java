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
package backend.ast.Descriptors;

import backend.ast.figure.components.Segment;

public class PerpendicularBisector extends Perpendicular
{
	private Segment bisector;
	
	
	public Segment getBisector()
	{
		return bisector;
	}
	
	//public PerpendicularBisector(Point i, Segment l, Segment bisector, string just) : base(i, l, bisector, just)
    //{
    //    this.bisector = bisector;
    //}
	public PerpendicularBisector(Intersection inter, Segment bisector)
	{
		super(inter);
		this.bisector = bisector;
	}
	
	@Override
	public int GetHashCode()
	{
		return super.GetHashCode();
	}
	
	@Override
	public boolean Equals(Object obj)
	{
		if(obj != null && obj instanceof PerpendicularBisector)
		{
			PerpendicularBisector p = (PerpendicularBisector)obj;
			
			return intersect.Equals(p.intersect) && lhs.Equals(p.lhs) && rhs.Equals(p.rhs);
		}
		
		
		//This is untested but should work since if the if statement fails then it wasn't an obj to begin with
		return false;
	}
	
	@Override
	public String toString()
	{
		return "PerpendicularBisector(" + bisector.toString() + " Bisects(" + this.OtherSegment(bisector) + ") at " + this.intersect + ")";
	}
}
