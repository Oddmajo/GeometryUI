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
package ast.Descriptors;

import ast.figure.components.Segment;
import utilities.exception.ExceptionHandler;

/// <summary>
/// Describes a point that lies on a segment.
/// </summary>
public class Parallel extends Descriptor
{
    private Segment segment1;
    private Segment segment2;
    
    public Segment getSegment1()
    {
        return segment1;
    }
    public Segment getSegment2()
    {
        return segment2;
    }
    
    public Parallel(Segment segment1, Segment segment2)
    {
        super();
        this.segment1 = segment1;
        this.segment2 = segment2;
        
        if(!segment1.IsParallelWith(segment2))
        {
            ExceptionHandler.throwException(new ArgumentException("Given lines are not parallel: " + segment1 +
                        " ; " + segment2));
        }
    }
    
    //This should never be true, otherwise they are coinciding
    @Override
    public boolean IsReflexive()
    {
        return segment1.StructurallyEquals(segment2);
    }
    
    @Override
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    public Segment OtherSegment(Segment that)
    {
        if(segment1.Equals(that))
        {
            return segment2;
        }
        if(segment2.Equals(that)) 
        {
            return segment1;
        }
        return null;
    }
    public Segment CoincidesWith(Segment that)
    {
        if (segment1.IsCollinearWith(that)) 
        {
            return segment1;
        }
        if (segment2.IsCollinearWith(that))
        {
            return segment2;
        }

        return null;
    }
    
    public Segment SharedSegment(Parallel thatParallel)
    {
        if (segment1.IsCollinearWith(thatParallel.segment1) && segment1.IsCollinearWith(thatParallel.segment2))
        {
            return segment1;
        }
        if (segment2.IsCollinearWith(thatParallel.segment1) && segment2.IsCollinearWith(thatParallel.segment2))
        {
            return segment2;
        }

        return null;
    }
    
    public int SharesNumClauses(Parallel thatParallel)
    {
        int shared = segment1.IsCollinearWith(thatParallel.segment1) && segment1.IsCollinearWith(thatParallel.segment2) ? 1 : 0;
        shared += segment2.IsCollinearWith(thatParallel.segment1) && segment2.IsCollinearWith(thatParallel.segment2) ? 1 : 0;

        return shared;
    }
    
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj != null)
        {
            Parallel p = (Parallel)obj;
            if (p == null)
            {
                return false;
            }
            return (segment1.StructurallyEquals(p.segment1) && segment2.StructurallyEquals(p.segment2)) ||
                   (segment1.StructurallyEquals(p.segment2) && segment2.StructurallyEquals(p.segment1));
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean Equals(Object obj)
    {
        if(obj != null)
        {
            Parallel p = (Parallel)obj;
            if(p == null)
            {
                return false;
            }
            return (segment1.Equals(p.segment1) && segment2.Equals(p.segment2)) ||
                    (segment1.Equals(p.segment2) && segment2.Equals(p.segment1)) && super.Equals(obj);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public String toString()
    {
        return "Parallel(" + segment1.toString() + ", " + segment2.toString() + ") " + justification;
    }
    
    private static final String NAME = ""9;
}
