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

import backend.ast.GroundedClause;
import backend.ast.figure.components.Point;
import backend.ast.Descriptors.Midpoint;
import backend.ast.figure.components.Segment;
import backend.utilities.ast_helper.Utilities;

public class InMiddle extends Descriptor
{
    protected Point _point;
    public Point getPoint() { return _point; }

    protected Segment _segment;
    public Segment getSegment() { return _segment; }
    
    /// <summary>
    /// Create a new InMiddle statement
    /// </summary>
    /// <param name="p">A point that lies on the segment</param>
    /// <param name="segment">A segment</param>
    public InMiddle(Point p, Segment segment)// :base()
    {
    	super();
        this._point = p;
        this._segment = segment;
    }
    
//    @Override
//    public void DumpXML(Action<String, ArrayList<GroundedClause>> write)
//    {
//        GroundedClause[] children = {point, segment};
//        write("InMiddle", new ArrayList<GroundedClause>(children));
//    }
    
    //
    // Can this relationship can strengthened to a Midpoint?
    //
    public Strengthened canBeStrengthened()
    {
    	if (Utilities.CompareValues(Point.calcDistance(_point, _segment.getPoint1()), Point.calcDistance(_point, _segment.getPoint2())))
        {
            return new Strengthened(this, new Midpoint(this));
        }
    	
    	return null;
    }
    
    @Override	
    public boolean canBeStrengthenedTo(GroundedClause gc)
    {
    	if(gc != null && gc instanceof Midpoint)
    	{
    		Midpoint midpoint = (Midpoint)gc;
    		return this._point.structurallyEquals(midpoint._point) && this._segment.structurallyEquals(midpoint._segment);
    	}
    	
    	//this is untested but if the if statement isn't hit then it probably should return false anyways
    	return false;
    }
    
    @Override
    public int hashCode()
    {
        return _point.hashCode() + _segment.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	//Causes infinite recursion -> if (obj is Midpoint) return (obj as Midpoint).Equals(this);
    	
    	if(obj != null && obj instanceof InMiddle)
    	{
    		InMiddle im = (InMiddle)obj;
    		
    		return im._point.equals(_point) && im._segment.equals(_segment);
    	}
    	
    	//this is untested but if the if statement isn't hit then it probably should return false anyways
    	return false;
    }
    
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if(obj != null && obj instanceof InMiddle)
        {
            InMiddle im = (InMiddle)obj;
            
            return im._point.structurallyEquals(_point) && im._segment.structurallyEquals(_segment);
        }
        
        //this is untested but if the if statement isn't hit then it probably should return false anyways
        return false;
    }
    
    @Override
    public String toString()
    {
    	return "InMiddle(" + _point.toString() + ", " + _segment.toString() + ") " + justification;
    }
    
}
