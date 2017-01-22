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

import backend.ast.figure.components.angles.Angle;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

/// <summary>
/// Describes a point that lies on a angmant.
/// </summary>
public class Supplementary extends AnglePairRelation
{
	public Supplementary(Angle ang1, Angle ang2 )
	{
		super(ang1,ang2);
		if(!Utilities.CompareValues(ang1.getMeasure() + ang2.getMeasure(), 180))
		{
			ExceptionHandler.throwException(new ArgumentException("Supplementary Angles must sum to 180: " + ang1 + " " + ang2));
		}
	}
	
	@Override
	public int getHashCode()
	{
		//change this if the object is no longer immutable
		return super.getHashCode();
	}
	
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof Supplementary)
		{
			Supplementary supp = (Supplementary)obj;
			return super.structurallyEquals(supp);
		}
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Supplementary)
		{
			Supplementary supp = (Supplementary)obj;
			return super.equals(supp);
		}
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public String toString()
	{
		return "Supplementary(" + angle1 + ", " + angle2 + ") " +justification;
	}
}
