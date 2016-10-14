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
package ast.Descriptors.Arcs_and_Circles;

import ast.Descriptors.CircleIntersection;
import ast.figure.components.Circle;
import ast.figure.components.Point;

public class CircleCircleIntersection extends CircleIntersection
{
    protected Circle otherCircle;
    
    public Circle getOtherCircle()
    {
        return otherCircle;
    }
    
    public CircleCircleIntersection(Point p, Circle c1, Circle c2)
    {
        super(p,c1,null); //yeah this line needs to be checked out hardcore
        
        otherCircle = c2;
        
        //Find the intersection points
        Point pt1, pt2;
    }
}
