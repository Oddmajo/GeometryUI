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
package ast.Descriptors;

import ast.figure.components.Point;
import ast.figure.components.Segment;
import utilities.Pair;

public class Intersection extends Descriptor
{
    private Point intersect;
    private Segment lhs;
    private Segment rhs;
    
    public Intersection()
    {
        super();
    }
    public Intersection(Point i, Segment l, Segment r)
    {
        super();
        intersect = i;
        lhs = l;
        rhs = r;
    }
    
    //If an endpoint of one segment is on the other segment
    public boolean standsOn()
    {
        return Segment.Between(rhs.getPoint1(), lhs.getPoint1(), lhs.getPoint2()) ||
                Segment.Between(rhs.getPoint2(), lhs.getPoint1(), lhs.getPoint2()) ||
                Segment.Between(lhs.getPoint1(), rhs.getPoint1(), rhs.getPoint2()) ||
                Segment.Between(lhs.getPoint2(), rhs.getPoint1(), rhs.getPoint2());
    }
    
    //
    // The intersection creates a  T
    //
    //   |
    //   |__________
    //   |
    //   |
    //
    // Returns the non-collinear point, if it exists
    //
    public Point CreatesTShape()
    {
        if (StandsOnEndpoint()) 
        {
            return null;
        }

        // Find the non-collinear end-point
        if (lhs.PointLiesOnAndBetweenEndpoints(rhs.getPoint1())) return rhs.getPoint2();
        if (lhs.PointLiesOnAndBetweenEndpoints(rhs.getPoint2())) return rhs.getPoint1();
        if (rhs.PointLiesOnAndBetweenEndpoints(lhs.getPoint1())) return lhs.getPoint2();
        if (rhs.PointLiesOnAndBetweenEndpoints(lhs.getPoint2())) return lhs.getPoint1();

        return null;
    }
    
    //  top
    //                    o
    //  offStands  oooooooe
    //                    e
    //offEndpoint   eeeeeee
    //                    o
    //                 bottom
    //                       Returns: <offEndpoint, offStands>
    public Pair<Point, Point> CreatesSimplePIShape(Intersection thatInter)
    {
        
    }
}
