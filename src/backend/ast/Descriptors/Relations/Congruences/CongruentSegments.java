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
import backend.ast.figure.components.Triangle;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;

//
// This class has two roles:
// 1) Congruent Pair of segments
// 2) To avoid redundancy and bloat in the hypergraph, it also mimics a basic geometric equation
// So AB \cong CD also means AB = CD (as a GEOMETRIC equation)
//
public class CongruentSegments extends Congruent
{
	private Segment cs1;
	private Segment cs2;
	
	public Segment getcs1()
	{
		return cs1;
	}
	public Segment getcs2()
	{
		return cs2;
	}
	
	
	//THESE ARE REDUNDANT AND ONE OF THE TWO REDUNDANT METHODS SHOULD BE TAKEN OUT
	public Segment GetFirstSegment() { return cs1; }
    public Segment GetSecondSegment() { return cs2; }
    
    
    
	public CongruentSegments(Segment s1, Segment s2)
	{
		super();
		if(!Utilities.CompareValues(s1.length(), s2.length()))
		{
			throw new ArgumentException;
		}
		cs1 = s1;
		cs2 = s2;
	}
	
	@Override
	public int GetHashCode()
	{
		return super.GetHashCode();
	}
	
	public Boolean HasSegment(Segment cs)
	{
		return cs1.Equals(cs) || cs2.Equals(cs);
	}
	
	//return the number of shared segments in both congruences
	@Override
	public int SharesNumClauses(Congruent thatCC)
	{
		if(thatCC != null && thatCC instanceof CongruentSegments)
		{
			CongruentSegments ccss = (CongruentSegments)thatCC;
			int numShared = cs1.Equals(ccss.cs1) || cs1.Equals(ccss.cs2) ? 1 : 0;
            numShared += cs2.Equals(ccss.cs1) || cs2.Equals(ccss.cs2) ? 1 : 0;

            return numShared;
		}
		
		//This is untested but should work. If thatCC is null and not an instance of then they can't share things in common
		return 0;
	}
	
	//return the shared segment in both congruences
	public Segment SegmentShared(CongruentSegments thatCC)
	{
		if(SharesNumClauses(thatCC) != 1)
		{
			return null;
		}
		return cs1.Equals(thatCC.cs1) || cs1.Equals(thatCC.cs2) ? cs1 : cs2;
	}
	
	//given one of the segments in the pair, return the other
	public Segment OtherSegment(Segment cs)
	{
		if(cs.Equals(cs1))
		{
			return cs2;
		}
		else if(cs.Equals(cs2))
		{
			return cs1;
		}
		return null;
	}
	
	@Override
	public boolean IsReflexive()
	{
		return cs1.Equals(cs2);
	}
	
	public boolean LinksTriangles(Triangle ct1, Triangle ct2)
	{
		return ct1.HasSegment(cs1) && ct2.HasSegment(cs2) || ct1.HasSegment(cs2) && ct2.HasSegment(cs1);
	}
	
	public boolean SharedVertex(CongruentSegments ccs)
	{
		return ccs.cs1.SharedVertex(cs1) != null || ccs.cs1.SharedVertex(cs2) != null ||
                ccs.cs2.SharedVertex(cs1) != null || ccs.cs2.SharedVertex(cs2) != null;
	}
	
	public Segment SharedSegment(CongruentSegments ccs)
	{
		if (ccs.cs1.StructurallyEquals(this.cs1) || ccs.cs2.StructurallyEquals(this.cs1)) return this.cs1;
        if (ccs.cs1.StructurallyEquals(this.cs2) || ccs.cs2.StructurallyEquals(this.cs2)) return this.cs2;

        return null;
	}
	
	@Override
	public boolean StructurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof CongruentSegments)
		{
			CongruentSegments ccs = (CongruentSegments)obj;
			return (cs1.StructurallyEquals(ccs.cs1) && cs2.StructurallyEquals(ccs.cs2)) ||
	                   (cs1.StructurallyEquals(ccs.cs2) && cs2.StructurallyEquals(ccs.cs1));
		}
		
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public boolean Equals(Object obj)
	{
		if(obj != null && obj instanceof CongruentSegments)
		{
			CongruentSegments ccs = (CongruentSegments)obj;
			return (cs1.Equals(ccs.cs1) && cs2.Equals(ccs.cs2)) || (cs1.Equals(ccs.cs2) && cs2.Equals(ccs.cs1)) && super.Equals(obj);
		}
		
		//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
	}
	
	@Override
	public String toString()
    {
        return "Congruent(" + cs1.toString() + ", " + cs2.toString() + ") " + justification;
    }

	@Override
    public String toPrettyString()
    {
        return cs1.toPrettyString() + " is congruent to " + cs2.toPrettyString() + ".";
    }
}
