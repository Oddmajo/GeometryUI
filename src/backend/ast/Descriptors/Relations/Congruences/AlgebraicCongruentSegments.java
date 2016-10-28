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
package backend.ast.Descriptors.Relations.Congruences;

import backend.ast.figure.components.Segment;

//
// This class has two roles:
// 1) Congruent Pair of segments
// 2) To avoid redundancy and bloat in the hypergraph, it also mimics a basic Algebraic equation
// So AB \cong CD also means AB = CD (as a Algebraic equation)
//
public class AlgebraicCongruentSegments extends CongruentSegments
{
	public AlgebraicCongruentSegments(Segment s1, Segment s2)
	{
		super(s1,s2);
	}
	
	@Override
	public int GetHashCode()
	{
		//change this if the object is no longer immutable
		return super.GetHashCode();
	}
	
	@Override
	public boolean IsAlgebraic()
	{
		return true;
	}
	@Override
	public boolean IsGeometric()
	{
		return false;
	}
	@Override
	public String toString()
	{
		return "AlgebraicCongruent(" + cs1.toString() + ", " + cs2.toString() + ") " + justification;
	}
}