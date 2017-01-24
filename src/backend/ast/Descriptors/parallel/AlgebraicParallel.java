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
package backend.ast.Descriptors.parallel;

import backend.ast.figure.components.Segment;

public class AlgebraicParallel extends Parallel
{
	public AlgebraicParallel(Segment segment1, Segment segment2)
	{
		super(segment1,segment2);
	}
	
	@Override
	public int getHashCode()
	{
		//change this if the object is no longer immutable!!
		return super.getHashCode();
	}
	
	@Override
	public boolean isAlgebraic()
	{
		return true;
	}
	
	@Override
	public boolean isGeometric()
	{
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof AlgebraicParallel)
		{
			AlgebraicParallel gp = (AlgebraicParallel)obj;
			
			
			//I believe this is a bug but this is how it was originally written
			return super.equals(obj);
		}
		
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public String toString()
	{
		return "AlgebraicParallel(" + super.getSegment1().toString() + ", " + super.getSegment2().toString() + ") " + justification;
	}
}
