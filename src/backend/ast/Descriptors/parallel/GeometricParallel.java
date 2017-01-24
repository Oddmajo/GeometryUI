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

public class GeometricParallel extends Parallel
{
    public GeometricParallel(Segment segment1, Segment segment2)
    {
        super(segment1, segment2);
    }

    @Override public boolean isAlgebraic() { return false; }
    @Override public boolean isGeometric() { return true; }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;

        if (!(obj instanceof GeometricParallel)) return false;
        
        return super.equals(obj);
    }

    @Override
    public String toString()
    {
        return "GeometricParallel(" + segment1.toString() + ", " + segment2.toString() + ") " + justification;
    }

    @Override
    public String toPrettyString()
    {
        return segment1.toPrettyString() + " is parallel to " + segment2.toPrettyString() + ".";
    }
}
