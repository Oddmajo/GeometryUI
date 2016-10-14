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

import java.util.ArrayList;
import ast.figure.components.Point;

public class Collinear extends Descriptor
{
    public ArrayList<Point> points;
    
    //We assume the points are ordered how they appear
    //but we verify just in case
    public Collinear(ArrayList<Point> pts) 
    {
        super();
        points = new ArrayList<Point>(pts);
        verify();
    }
    
    // We assume the points are ordered how they appear
    // But we verify just in case
    public Collinear()
    {
        super();
        points = new ArrayList<Point>();
    }
    public void verify()
    {
        //if(points.Count)
    }
}

//This is the code that is being translated in this file
//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//
//namespace GeometryTutorLib.ConcreteAST
//{
//    public class Collinear : Descriptor
//    {
//        public List<Point> points { get; private set; }
//
//        
//         We assume the points are ordered how they appear
//         But we verify just in case
//        public Collinear(List<Point> pts) : base()
//        {
//            points = new List<Point>(pts);

//            Verify();
//        }

        //
        // We assume the points are ordered how they appear
        // But we verify just in case
//        public Collinear() : base()
//        {
//            points = new List<Point>();
//        }

//        private void Verify()
//        {
//            if (points.Count < 2) throw new ArgumentException("A Collinear relationship requires at least 2 points: " + this.ToString());

            // Create a segment of the endpoints to compare all points for collinearity
//            Segment line = new Segment(points[0], points[points.Count - 1]);

//            foreach (Point pt in points)
//            {
//                if (!line.PointLiesOn(pt))
//                {
//                    throw new ArgumentException("Point " + pt + " is not collinear with line " + line.ToString());
//                }
//
//                if (!line.PointLiesOnAndBetweenEndpoints(pt))
//                {
//                    throw new ArgumentException("Point " + pt + " is not between the endpoints of segment " + line.ToString());
//                }
//            }
//        }
//
//        public void AddCollinearPoint(Point newPt)
//        {
            // Traverse list to find where to insert the new point in the list in the proper order
//            for (int p = 0; p < points.Count - 1; p++)
//            {
//                if (Segment.Between(newPt, points[p], points[p + 1]))
//                {
//                    points.Insert(p + 1, newPt);
//                    return;
//                }
//            }
//            points.Add(newPt);
//        }
//
//        public override bool Equals(Object obj)
//        {
//            Collinear collObj = obj as Collinear;
//            if (collObj == null) return false;
//
            // Check all points
//            foreach (Point pt in collObj.points)
//            {
//                if (!points.Contains(pt)) return false;
//            }
//            return true;
//        }
//
//        public override int GetHashCode() { return base.GetHashCode(); }
//
//        public override string ToString()
//        {
//            List<String> strings = new List<String>();
//            foreach (Point p in points) strings.Add(p.ToString());
//            return "Collinear(" + string.Join(",", strings.ToArray()) + ")";
//        }
//    }
//}
