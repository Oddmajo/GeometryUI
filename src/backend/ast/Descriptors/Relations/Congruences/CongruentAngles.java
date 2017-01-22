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
package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

//
// This class has two roles:
// 1) Congruent Pair of Angles
// 2) To avoid redundancy and bloat in the hypergraph, it also mimics a basic geometric equation
// So \angle ABC \cong \angle DEF also means m\angle ABC = m\angle DEF (as a GEOMETRIC equation)
//
public class CongruentAngles extends Congruent
{
	private Angle ca1;
	private Angle ca2;
	
	//the original code had these getters even though that it was a public property in C#. So i did not make two getters
	public Angle GetFirstAngle()
	{
		return ca1;
	}
	public Angle GetSecondAngle()
	{
		return ca2;
	}
	
	//Deduced Node Information
	public CongruentAngles(Angle a1, Angle a2)
	{
		super();
		if(!Utilities.CompareValues(a1.getMeasure(), a2.getMeasure()))
		{
			ExceptionHandler.throwException(new ArgumentException("Two congruent angles deduced congruent although angle measures differ: " + a1 + " " + a2));
		}
		ca1 = a1;
		ca2 = a2;
	}
	
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof CongruentAngles)
		{
			CongruentAngles cas = (CongruentAngles)obj;
			
			if(!Utilities.CompareValues(this.ca1.getMeasure(), cas.ca1.getMeasure()))
			{
				return false;
			}
			return (ca1.structurallyEquals(cas.ca1) && ca2.structurallyEquals(cas.ca2)) ||
	                   (ca1.structurallyEquals(cas.ca2) && ca2.structurallyEquals(cas.ca1));
		}
		
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return this.structurallyEquals(obj);
	}
	
	@Override
	public boolean isReflexive()
	{
		return ca1.equals(ca2);
	}
	
	//return the number of shared angles in both congruences
	// @Override //This was overriden in C# but the super doesn't take parameters
	public int SharesNumClauses(Congruent thatCC)
	{
		if( thatCC != null && thatCC instanceof CongruentAngles)
		{
			CongruentAngles ccas = (CongruentAngles)thatCC;
			
			int numShared = ca1.equates(ccas.ca1) || ca1.equates(ccas.ca2) ? 1: 0;
			numShared += ca2.equates(ccas.ca1) || ca2.equates(ccas.ca2) ? 1 : 0;
			return numShared;
		}
		
		//This is untested but should be correct. If thatCC is null or not an instance then it can have anything shared
		return 0;
	}
	
	//return the shared angle in both congruences
	public Segment SegmentShared()
	{
		
		if (ca1.getRay1().asSegment().isCollinearWith(ca2.getRay1().asSegment()) || ca1.getRay1().asSegment().isCollinearWith(ca2.getRay2().asSegment()))
		{
			return ca1.getRay1().asSegment();
		}
        if (ca1.getRay2().asSegment().isCollinearWith(ca2.getRay1().asSegment()) || ca1.getRay2().asSegment().isCollinearWith(ca2.getRay2().asSegment())) 
        {
        	return ca1.getRay2().asSegment();
        }
        return null;
	}
	
	//return the shared angle in both congruences
	public Angle AngleShared(CongruentAngles cas)
	{
		if (ca1.equates(cas.ca1) || ca1.equates(cas.ca2))
		{
			return ca1;
		}
        if (ca2.equates(cas.ca1) || ca2.equates(cas.ca2))
    	{
    		return ca2;
    	}
        return null;
	}
	
	//return the shared angle in both congruences
	public Segment AreAdjacent()
	{
		return ca1.IsAdjacentTo(ca2);
	}
	
	//given one of the angles in the pair, return the other
	public Angle OtherAngle(Angle cs)
	{
		if (cs.equates(ca1))
		{
			return ca2;
		}
        if (cs.equates(ca2))
    	{
    		return ca1;
    	}
        return null;
	}
	
	//given one of the angles in the pair, return the other
	public boolean HasAngle(Angle cs)
	{
		if (cs.equates(ca1))
		{
			return true;
		}
        if (cs.equates(ca2))
    	{
    		return true;
    	}
        return false;
	}
	
	public boolean LinksTriangles(Triangle ct1, Triangle ct2)
	{
		return ct1.HasAngle(ca1) && ct2.HasAngle(ca2) || ct1.HasAngle(ca2) && ct2.HasAngle(ca1);
	}
	
	@Override
	public int getHashCode()
	{
		return super.getHashCode();
	}
	
	@Override
	public String toString()
	{
		return "Congruent(" + ca1.toString() + ", " + ca2.toString() + ") " + justification;
	}
	
	@Override
	public String toPrettyString()
	{
		return ca1.toPrettyString() + " is congruent to " + ca2.toPrettyString() + " .";
	}
	
}
