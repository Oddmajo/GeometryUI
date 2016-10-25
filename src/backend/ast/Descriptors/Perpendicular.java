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
    	if(lhs.CoordinatePerpendicular(rhs) == null)
    	{
    		
    	}
    	
    	originalInter = inter;
    }
    
    @Override
    public int GetHashCode()
    {
    	return super.GetHashCode();
    }
    
    @Override
    public boolean StructurallyEquals(Object obj)
    {
    	if(obj != null && obj instanceof Perpendicular)
    	{
    		Perpendicular perp = (Perpendicular)obj;
    		// inner null check was in original, we shouldn't need but left for safety until tested
    		//if(perp == null)
    		//{
    		// return false;
    		//}
    		return intersect.Equals(perp.intersect) && ((lhs.StructurallyEquals(perp.lhs) && rhs.StructurallyEquals(perp.rhs)) ||
    													(lhs.StructurallyEquals(perp.rhs) && rhs.StructurallyEquals(perp.lhs)));
    	}
    }
    
    @Override
    public boolean Equals(Object obj)
    {
    	if(obj != null && obj instanceof PerpendicularBisector)
    	{
    		return (PerpendicularBisector)obj.equals(this);
    	}
    	
    	if(obj != null && obj instanceof Perpendicular)
    	{
    		Perpendicular p = (Perpendicular)obj;
    	}
    	
    	return intersect.Equals(p.intersect) && lhs.Equals(p.lhs) && rhs.Equals(p.rhs);
    }
    
    @Override
    public String toString()
    {
    	return "Perpendicular(" + intersect.toString() + ", " + lhs.toString() + ", " + rhs.toString() + ") " + justification;
    }
}
