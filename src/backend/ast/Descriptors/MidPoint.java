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
package backend.ast.Descriptors;

/// <summary>
/// Represents an angle (degrees), defined by 3 points.
/// </summary>
public class MidPoint extends InMiddle
{
	public MidPoint(InMiddle im)
	{
		super(im.point, im.segment);
	}
	
	@Override
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof MidPoint)
		{
			MidPoint midptObj = (MidPoint)obj;
			return point.StructurallyEquals(midptObj.point) && segment.StructurallyEquals(midptObj.segment);
		}
		//this is untested but if the if statement isn't hit then it probably should return false anyways
    	return false;
	}
	
	@Override
	public boolean Equals(Object obj)
	{
		if(obj != null && obj instanceof MidPoint)
		{
			MidPoint midptObj = (MidPoint)obj;
			return point.Equals(midptObj.point) && segment.Equals(midptObj.segment) && super.Equals(obj);
		}
		//this is untested but if the if statement isn't hit then it probably should return false anyways
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
		return "Midpoint(" + point.toString() + ", " + segment.toString() + ") " + justification;
	}
}