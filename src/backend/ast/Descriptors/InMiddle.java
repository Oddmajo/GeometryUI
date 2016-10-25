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

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

public class InMiddle extends Descriptor
{
    public Point point;
    public Segment segment;
    
    /// <summary>
    /// Create a new InMiddle statement
    /// </summary>
    /// <param name="p">A point that lies on the segment</param>
    /// <param name="segment">A segment</param>
    public InMiddle(Point p, Segment segment)// :base()
    {
        this.point = p;
        this.segment = segment;
    }
    
    @Override
    public void DumpXML(Action<String, List<GroundedClause>> write)
    {
        GroundedClause[] children = ;
        write("InMiddle", new List<GroundedClause>(children));
    }
    
    //
    // Can this relationship can strengthened to a Midpoint?
    //
    public Strengthened canBeStrengthened()
    {
        
    }
    
}
