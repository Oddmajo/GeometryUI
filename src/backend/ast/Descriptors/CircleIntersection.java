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

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;

public class CircleIntersection extends Descriptor
{
    private Point intersect;
    private Circle circle1;
    private Circle circle2;
    
    public Point getIntersect()
    {
        return intersect;
    }
    
    public Circle getCircle1()
    {
        return circle1;
    }
    
    public Circle getCircle2()
    {
        return circle2;
    }
    
    /// <summary>
    /// Create a new intersection between two circles.
    /// </summary>
    /// <param name="i">The point of intersection.</param>
    /// <param name="c1">A circle.</param>
    /// <param name="c2">An intersecting circle to c1.</param>
    public CircleIntersection(Point i, Circle c1, Circle c2)
    {
        intersect = i;
        circle1 = c1;
        circle2 = c2;
    }
    
    /// <summary>
    /// Tests if the two intersections are structurally equal.
    /// </summary>
    /// <param name="obj">The other object to test.</param>
    /// <returns>True if the parameter is structurally equal to this intersection.</returns>
    @Override
    public boolean structurallyEquals(Object obj)
    {
        if(obj != null)
        {
            CircleIntersection ci = (CircleIntersection)obj;
            return (intersect.structurallyEquals(ci.circle1) && circle2.structurallyEquals(ci.circle2)) ||
                    (circle1.structurallyEquals(ci.circle2) && circle2.structurallyEquals(ci.circle1));
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public String toString()
    {
        return "CircleIntersection(" + intersect.toString() + ", " + circle1.toString() + ", " + circle2.toString() +
                ") " + justification;
    }
}
