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
package backend.precomputer;



import java.util.ArrayList;
import java.util.Iterator;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Altitude;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Arcs_and_Circles.CircleCircleIntersection;
import backend.ast.Descriptors.Arcs_and_Circles.CircleSegmentIntersection;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentArcs;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.*;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.Arc;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.arcs.Semicircle;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.triangles.Triangle;
import backend.ast.figure.delegates.PointLiesOnDelegate;
import backend.factComputer.FactComputer;
import backend.utilities.Pair;
import backend.utilities.PointFactory;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;
public class CoordinatePrecomputer
{
    
      private ArrayList<Arc> arcs;
      private ArrayList<Segment> segments;
      private ArrayList<Circle> circles;
      private ArrayList<Point> points;
      private ArrayList<Sector> sectors;
    
    
    public CoordinatePrecomputer(ArrayList<GroundedClause> figure)
    {
//        circles = new ArrayList<Circle>();
//        quadrilaterals = new ArrayList<Quadrilateral>();
//        triangles = new ArrayList<Triangle>();
//        segments = new ArrayList<Segment>();
//        angles = new ArrayList<Angle>();
//        collinear = new ArrayList<Collinear>();
//
//        inMiddles = new ArrayList<InMiddle>();
//        intersections = new ArrayList<Intersection>();
//        perpendiculars = new ArrayList<Perpendicular>();
//        parallels = new ArrayList<Parallel>();
//
//        minorArcs = new ArrayList<MinorArc>();
//        majorArcs = new ArrayList<MajorArc>();
//        semiCircles = new ArrayList<Semicircle>();
//        sectors = new ArrayList<Sector>();
//        arcInMiddle = new ArrayList<ArcInMiddle>();
//
        arcs = new ArrayList<Arc>();
        circles = new ArrayList<Circle>();
        segments = new ArrayList<Segment>();
        points = new ArrayList<Point>();
        sectors = new ArrayList<Sector>();
        FilterClauses(figure);
        findRelations();
        //FactComputer facts = new FactComputer(circles,arcs,segments,points,sectors);
    }
    
    //this should be the used constructor
    public CoordinatePrecomputer(ArrayList<Circle> c, ArrayList<Arc> a, ArrayList<Segment> s, ArrayList<Point> p)
    {
        circles = c;
        arcs = a;
        segments = s;
        points = p;
        
        sectors = new ArrayList<Sector>();
        PointFactory.initialize(points);
        findRelations();
        //FactComputer facts = new FactComputer(circles,arcs,segments,points,sectors);
    }
    
    public ArrayList<Arc> getArcs()
    {
        return arcs;
    }
    public ArrayList<Circle> getCircles()
    {
        return circles;
    }
    public ArrayList<Segment> getSegments()
    {
        return segments;
    }
    public ArrayList<Point> getPoints()
    {
        return points;
    }
    public ArrayList<Sector> getSectors()
    {
        return sectors;
    }
    
    
    private void findRelations()
    {
        ArrayList<Segment> segs = new ArrayList<Segment>();
        for(Segment s : segments)
        {
            if(!points.contains(s.getPoint1()))
            {
                if(!PointFactory.contains(s.getPoint1()))
                    PointFactory.generatePoint(s.getPoint1());
                points.add(s.getPoint1());
            }
            if(!points.contains(s.getPoint2()))
            {   
                if(!PointFactory.contains(s.getPoint2()))
                    PointFactory.generatePoint(s.getPoint2());
                points.add(s.getPoint2());
            }
            for(Segment s2 : segments)
            {
                if(!s.equals(s2))
                {
                    if(s.segmentIntersection(s2)!=null)
                    {
                        if(!PointFactory.isGenerated(s.segmentIntersection(s2)))
                        {
                            if(!structurallyContains(points,s.segmentIntersection(s2)))
                            {
                                points.add(PointFactory.generatePoint(s.segmentIntersection(s2))); //this should find the intersection point and make a new point right?
                            }
                            else
                            {
                                PointFactory.generatePoint(s.segmentIntersection(s2)); //do we need this since if it isn't generated then we don't have the point? but I put it in for safety
                            }
                        }
                    }
                }
            }
            
            for(Point p : points)
            {
                // A-B-C 
                //need to check if AB & BC & AC are in the segment list and that all three points are added to points
                //will check for midpoint in fact computer
                if(s.pointLiesOnSegment(p)) 
                {
                    //check to see if this segment is already created
                    boolean made1 = false;
                    boolean made2 = false;
                    for(Segment seg : segments)
                    {
                        if( (seg.getPoint1().structurallyEquals(s.getPoint1()) && seg.getPoint2().structurallyEquals(p)) )
                        {
                            //then this segment is created
                            made1 = true;
                        }
                        if((seg.getPoint1().structurallyEquals(p) && seg.getPoint2().structurallyEquals(s.getPoint2())))
                        {
                            made2=true;
                        }
                    }
                    //if they are not found to be made then we need to make the segments
                    if(!made1)
                    {
                        if(!s.getPoint1().structurallyEquals(p))
                        {
                            segs.add(new Segment(s.getPoint1(),p));
                        }
                    }
                    if(!made2)
                    {
                        if(!s.getPoint2().structurallyEquals(p))
                        {
                            segs.add(new Segment(p,s.getPoint2()));
                        }
                    }
                }
                //Either B-A-C or A-B-C or A-B-C
                //but maybe this will would create segments that shouldn't exist?
//                if(s.pointLiesOnLine(p))
//                {
//                    
//                }
            }
            

        }
        segments.addAll(segs);
        
      //might need to add checking to see if any random point is on the circle?
        for(Arc a : arcs)
        {
            //sectors.add(new Sector(a));
            
            //check both endpoints to see if they are contained in points
            //  if not, add them
            //  does an issue potentially pop up regarding this? unsure, consult with Tom/Alvin
            if(!points.contains(a.getEndpoint1()))
            {
                if(!PointFactory.contains(a.getEndpoint1()))
                    PointFactory.generatePoint(a.getEndpoint1());
                points.add(a.getEndpoint1());   
            }
            if(!points.contains(a.getEndpoint2()))
            {
                if(!PointFactory.contains(a.getEndpoint2()))
                    PointFactory.generatePoint(a.getEndpoint2());
                points.add(a.getEndpoint2());   
            }
            
            //if the two endpoints are not in the circle's PointsOnCircle list, add them
            if(!a.getCircle().getPointsOnCircle().contains(a.getEndpoint1()))
            {
                a.getCircle().addPointOnCircle(a.getEndpoint1());
            }
            if(!a.getCircle().getPointsOnCircle().contains(a.getEndpoint2()))
            {
                a.getCircle().addPointOnCircle(a.getEndpoint2());
            }
            
            sectors.add(new Sector(a));
            
            //The handler for circles will add complementary angles
        }
        
        for(Circle c: circles)
        {
            //Make sure the centerpoint is in points
            if(!points.contains(c.getCenter()))
            {
                if(!PointFactory.contains(c.getCenter()))
                    PointFactory.generatePoint(c.getCenter());
                points.add(c.getCenter());
            }
            
            //go through the list of points
            //  for each point, check if it is on the circle
            //  if not, add it to 
            for(Point p : points)
            {
                if(PointLiesOnDelegate.pointLiesOn(c, p))
                {
                    if(!c.getPointsOnCircle().contains(p))
                        c.addPointOnCircle(p);
                }
            }
            //go through the list of all points on the circle
            //  for each point, make sure it is in the list of points
            //  then, for each point and every other point not yet processed,
            //  see if the corresponding arc has been created
            ArrayList<Point> circlePoints = c.getPointsOnCircle();
            for(int i = 0; i < circlePoints.size(); i++)
            {
                if(!points.contains(circlePoints.get(i)))
                {
                    if(!PointFactory.contains(circlePoints.get(i)))
                        PointFactory.generatePoint(circlePoints.get(i));
                    points.add(circlePoints.get(i));
                }
                for(int j = i + 1; j < circlePoints.size(); j++)
                {
                    //for each pair of points, need to check if the two points create an arc or semicircle 
                    Arc one = null, two = null;
                    if(c.DefinesDiameter(new Segment(circlePoints.get(i),circlePoints.get(j))))
                    {
                        //The first constructor will find any other point on the circle to use as a middlepoint
                        //  if no point is found,
                        one = new Semicircle(c, circlePoints.get(i), circlePoints.get(j));
                        two = new Semicircle(c, (Semicircle)one);
                    }
                    else
                    {
                        one = new MajorArc(c, circlePoints.get(i), circlePoints.get(j));
                        two = new MinorArc(c, circlePoints.get(i), circlePoints.get(j));
                    }
                    
                    if(!arcs.contains(one))
                    {
                        arcs.add(one);
                        sectors.add(new Sector(one));
                    }
                    if(!arcs.contains(two)) 
                    {
                        arcs.add(two);
                        sectors.add(new Sector(two));
                    }
                }
            }
            //use the hash so we can figure out the arcs to create.
            //then create a new sector based off of that arc
        }
    }
    
    //Split the figure into the constituent clauses
    private void FilterClauses(ArrayList<GroundedClause> figure)
    {
        for(GroundedClause clause : figure)
        {
        	if(clause != null)
        	{
	            if(clause instanceof Circle)
	            {
	                circles.add((Circle)clause);
	            }
	            else if(clause instanceof Arc)
	            {
	                arcs.add((Arc)clause);
	            }
	            else if (clause instanceof Segment)
	            {
	                segments.add((Segment)clause);
	            }
	            else if(clause instanceof Point)
	            {
	                if(!PointFactory.isGenerated((Point)clause))
	                {
	                    PointFactory.generatePoint((Point)clause);
	                }
	                if(!points.contains((Point)clause))
	                {
	                    points.add((Point)clause);
	                }
	            }

        	}
        }
    }
    
    private boolean structurallyContains(ArrayList<Point> list, Point that)
    {
        for(Point thisS : list)
        {
            if(thisS.structurallyEquals(that))
            {
                return true;
            }
        }
        return false;
    }
}
