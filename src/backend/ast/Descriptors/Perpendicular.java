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

import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class Perpendicular extends Intersection
{
    private Intersection originalInter;
    
    public Intersection getoriginalInter()
    {
        return originalInter;
    }
    
    public Perpendicular(Intersection inter)
    {   
    	super(inter.intersect, inter.lhs, inter.rhs);
    	
    	//check if truly perpendicular
    	if(inter.isPerpendicular())    	    
    	{
    		ExceptionHandler.throwException(new ArgumentException("Intersection is not perpendicular: " + inter.toString()));
    	}
    	
    	originalInter = inter;
    }
    
    @Override
    public boolean structurallyEquals(Object obj)
    {
    	if(obj != null && obj instanceof Perpendicular)
    	{
    		Perpendicular perp = (Perpendicular)obj;
    		// inner null check was in original, we shouldn't need but left for safety until tested
    		//if(perp == null)
    		//{
    		// return false;
    		//}
    		return intersect.equals(perp.intersect) && ((lhs.structurallyEquals(perp.lhs) && rhs.structurallyEquals(perp.rhs)) ||
    													(lhs.structurallyEquals(perp.rhs) && rhs.structurallyEquals(perp.lhs)));
    	}
    	//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if(obj != null && obj instanceof PerpendicularBisector)
    	{
    		return ((PerpendicularBisector)obj).equals(this);
    	}
    	
    	if(obj != null && obj instanceof Perpendicular)
    	{
    		Perpendicular p = (Perpendicular)obj;
    		return intersect.equals(p.intersect) && lhs.equals(p.lhs) && rhs.equals(p.rhs);
    	}
    	
    	//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
    	
    }
    
    @Override
    public String toString()
    {
    	return "Perpendicular(" + intersect.toString() + ", " + lhs.toString() + ", " + rhs.toString() + ") " + justification;
    }
}
