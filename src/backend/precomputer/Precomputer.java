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
import java.util.HashSet;
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
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
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
import backend.utilities.MaximalSegments;
import backend.utilities.Pair;
import backend.utilities.PointFactory;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.DebugException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;
public class Precomputer
{
    protected ArrayList<Arc> _arcs;
    public ArrayList<Arc> getArcs() { return _arcs; }

    protected ArrayList<Segment> _segments;
    public ArrayList<Segment> getSegments() { return _segments; }

    protected ArrayList<Circle> _circles;
    public ArrayList<Circle> getCircles() { return _circles; }

    protected ArrayList<Point> _points;
    public ArrayList<Point> getPoints() { return _points; }

    protected ArrayList<Sector> _sectors;
    public ArrayList<Sector> getSectors() { return _sectors; }

    protected ArrayList<Collinear> _collinears;
    public ArrayList<Collinear> getCollinears() { return _collinears; }
    
    protected MaximalSegments _maximalSegments;
    public MaximalSegments getMaximalSegments() { return _maximalSegments; }
    
    protected boolean _analyzed;
    /** @return Whether the Precomputer analysis was invoked and completed */
    public boolean analyzed() { return _analyzed; }
    
    public Precomputer(ArrayList<GroundedClause> figure)
    {
        _arcs = new ArrayList<Arc>();
        _circles = new ArrayList<Circle>();
        _segments = new ArrayList<Segment>();
        _points = new ArrayList<Point>();
        _sectors = new ArrayList<Sector>();
        _collinears = new ArrayList<Collinear>();
        _maximalSegments = MaximalSegments.getInstance();

        // Given a single list, split into constituencies
        filterClauses(figure);
        _analyzed = false;
    }

    /**
     * Precomputation is based on 4 basic lists of elements
     * @param c -- a list of Circles
     * @param a -- a list of Arcs
     * @param s -- a list of Segments
     * @param p -- a list of Points
     * 
     * Note: this constructor is preferred.
     */
    public Precomputer(ArrayList<Circle> c, ArrayList<Arc> a, ArrayList<Segment> s, ArrayList<Point> p)
    {
        _circles = c != null ? c : new ArrayList<Circle>();
        _arcs = a != null ? a : new ArrayList<Arc>();
        _segments = s != null ? s : new ArrayList<Segment>();
        _points = p != null ? p : new ArrayList<Point>();
        _sectors = new ArrayList<Sector>();
        _collinears = new ArrayList<Collinear>();
        _maximalSegments = MaximalSegments.getInstance();
        
        _analyzed = false;
    }

    /**
     * Alternative precomputer construction with only points and segments
     * @param p -- a list of Points
     * @param s -- a list of Segments
     */
    public Precomputer(ArrayList<Point> p, ArrayList<Segment> s)
    {
        _points  = p;
        _segments = s;

        _circles = new ArrayList<Circle>();
        _arcs = new ArrayList<Arc>();
        _sectors = new ArrayList<Sector>();
        _collinears = new ArrayList<Collinear>();
        _maximalSegments = MaximalSegments.getInstance();
        
        _analyzed = false;
    }

    /**
     * Invoke the precomputation procedure.
     */
    public void analyze()
    {
        PointFactory.initialize(_points);

        computePoints();
        computeMaximalSegments();
        computeSegmentRelations();
        computeArcRelations();
        computeCircleRelations();
        computeCollinears();
        
        // Analysis is complete
        _analyzed = true;
    }
    
    //Given the initial list of segments from the UI we can determine all maximal segments
    //A MaximalSegment is the largest segment.
    //A-B-C-D  the segment AD is the maximal segment here
    //the only time a UI segment would not be maximal if UI passes AB and BC and A is collinear with C. Then the maximalSegments should be AC
    private void computeMaximalSegments()
    {
        //currently this does not checking for chainging of segments with overlap
        for(int s1 = 0; s1 < _segments.size() - 1; s1++)
        {
            for(int s2 = s1 + 1; s2 < _segments.size(); s2++)
            {
                //are the on the same line? and do they intersect? if both then they are a new "maximalSegment"
                //if same line and do not intersect then you have something like A---------B     C----------D
                //we are looking for something like A-----C--B-------D 
                
                //we are checking to see where the overlap occurs
                if(_segments.get(s1).isCollinearWith(_segments.get(s2)))
                {
                    if((_segments.get(s1).pointLiesOnSegment(_segments.get(s2).getPoint1()))  && (_segments.get(s1).pointLiesOnSegment(_segments.get(s2).getPoint2()))) //use a different way to check for maximal??
                    {
                        _maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper( _segments.get(s1) )));
                    }
                    else if(_segments.get(s1).pointLiesOnSegment(_segments.get(s2).getPoint1()))
                    {
                        Double d1 = Point.calcDistance(_segments.get(s1).getPoint1(), _segments.get(s2).getPoint2());
                        Double d2 = Point.calcDistance(_segments.get(s1).getPoint2(), _segments.get(s2).getPoint2());
                        if(MathUtilities.greaterThan(d1, d2))
                        {
                            _maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment( _segments.get(s1).getPoint1() , _segments.get(s2).getPoint2() ))));
                        }
                        else if(MathUtilities.greaterThan(d2, d1))
                        {
                            _maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment( _segments.get(s2).getPoint2(), _segments.get(s1).getPoint1() ))));
                        }
                        //Shouldn't happen
                        else if(MathUtilities.doubleEquals(d1, d2))
                        {
                            //_maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment(  ))));
                        }
                    }
                    else if(_segments.get(s1).pointLiesOnSegment(_segments.get(s2).getPoint2()))
                    {
                        Double d1 = Point.calcDistance(_segments.get(s1).getPoint1(), _segments.get(s2).getPoint1());
                        Double d2 = Point.calcDistance(_segments.get(s1).getPoint2(), _segments.get(s2).getPoint1());
                        if(MathUtilities.greaterThan(d1, d2))
                        {
                            _maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment(_segments.get(s1).getPoint1(),_segments.get(s2).getPoint1()))));
                        }
                        else if(MathUtilities.greaterThan(d2, d1))
                        {
                            _maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment(_segments.get(s1).getPoint2(),_segments.get(s2).getPoint1()))));
                        }
                        //Shouldn't happen
                        else if(MathUtilities.doubleEquals(d1, d2))
                        {
                            //_maximalSegments.addMaximalSegment(new MaximalSegment(MaximalSegmentHelper(new Segment())));
                        }
                    }
                }
            }
        }
    }
    
    private Segment MaximalSegmentHelper(Segment maybeMaximal)
    {
        for(int i = 0; i < _segments.size(); i++)
        {
            if(maybeMaximal.isCollinearWith(_segments.get(i)))
            {
                if(maybeMaximal.pointLiesOnSegment(_segments.get(i).getPoint1()))
                {
                    Double d1 = Point.calcDistance(maybeMaximal.getPoint1(), _segments.get(i).getPoint2());
                    Double d2 = Point.calcDistance(maybeMaximal.getPoint2(), _segments.get(i).getPoint2());
                    if(MathUtilities.greaterThan(d1, d2))
                    {
                        return new MaximalSegment(MaximalSegmentHelper(new Segment(maybeMaximal.getPoint1(),_segments.get(i).getPoint2())));
                    }
                    else if(MathUtilities.greaterThan(d2, d1))
                    {
                        return new MaximalSegment(MaximalSegmentHelper(new Segment(maybeMaximal.getPoint2(),_segments.get(i).getPoint2() )));
                    }
                    //Shouldn't happen
                    else if(MathUtilities.doubleEquals(d1, d2))
                    {
                        //
                    }
                }
                else if(maybeMaximal.pointLiesOnSegment(_segments.get(i).getPoint2()))
                {
                    Double d1 = Point.calcDistance(maybeMaximal.getPoint1(), _segments.get(i).getPoint1());
                    Double d2 = Point.calcDistance(maybeMaximal.getPoint2(), _segments.get(i).getPoint1());
                    if(MathUtilities.greaterThan(d1, d2))
                    {
                        return new MaximalSegment(MaximalSegmentHelper(new Segment(maybeMaximal.getPoint1(),_segments.get(i).getPoint1())));
                    }
                    else if(MathUtilities.greaterThan(d2, d1))
                    {
                        return new MaximalSegment(MaximalSegmentHelper(new Segment(maybeMaximal.getPoint2(),_segments.get(i).getPoint1())));
                    }
                    //Shouldn't happen
                    else if(MathUtilities.doubleEquals(d1, d2))
                    {
                        //
                    }
                }
            }
        }
        
        return maybeMaximal;
    }
    
    private void computeCollinears()
    {
        for(MaximalSegment ms : _maximalSegments.getMaximalSegments())
        {
            Collinear c = new Collinear();
            for(Point p : _points)
            {
                if(ms.pointLiesOn(p))
                {
                    c.AddCollinearPoint(p);
                }
            }
        }
    }
    
    private void computePoints()
    { 
        for(Segment s : _segments)
        {
            if(!_points.contains(s.getPoint1()))
            {
                if(!PointFactory.contains(s.getPoint1()))
                {
                    PointFactory.generatePoint(s.getPoint1());
                }
                _points.add(s.getPoint1());
            }
            if(!_points.contains(s.getPoint2()))
            {
                if(!PointFactory.contains(s.getPoint2()))
                {
                    PointFactory.generatePoint(s.getPoint2());
                }
                _points.add(s.getPoint2());
            }
            for(Segment s2 : _segments)
            {
                if(!s.equals(s2))
                  {
                      if(s.segmentIntersection(s2)!=null)
                      {
                          if(!PointFactory.isGenerated(s.segmentIntersection(s2)))
                          {
                              if(!structurallyContains(_points,s.segmentIntersection(s2)))
                              {
                                  _points.add(PointFactory.generatePoint(s.segmentIntersection(s2))); //this should find the intersection point and make a new point
                              }
                              else
                              {
                                  PointFactory.generatePoint(s.segmentIntersection(s2)); //do we need this since if it isn't generated then we don't have the point? but I put it in for safety
                              }
                          }
                      }
                  }
            }
        }
    }
    
    private void computeSegmentRelations()
    {
        Object[] maximal1 = _maximalSegments.getMaximalSegments().toArray();
        MaximalSegment[] ms1 = (MaximalSegment[])maximal1;
        
        for(int msi = 0 ; msi < _maximalSegments.getMaximalSegments().size()-1; msi++)
        {
            //get all the points on the maximalSegment
            ArrayList<Point> segPoints = new ArrayList<Point>();
            for(Point p: _points)
            {
                if(ms1[msi].pointLiesOnSegment(p))
                {
                    segPoints.add(p);
                }
            }
            //for each set of points on the maximalsegment we need to create a new segment. (we are allowing for the maximalSegment to be generated as a subsegment)
            for(int p1 = 0; p1 < segPoints.size() - 1 ; p1++)
            {
                for(int p2 = p1+1; p2<segPoints.size(); p2++)
                {
                        ms1[msi].addSubsegment(new Segment(segPoints.get(p1),segPoints.get(p2)));
                }
            }
        }
        
//        ArrayList<Segment> segs = new ArrayList<Segment>();
//        for(Segment s : _segments)
//        {
//            if(!_points.contains(s.getPoint1()))
//            {
//                if(!PointFactory.contains(s.getPoint1()))
//                    PointFactory.generatePoint(s.getPoint1());
//                _points.add(s.getPoint1());
//            }
//            if(!_points.contains(s.getPoint2()))
//            {   
//                if(!PointFactory.contains(s.getPoint2()))
//                    PointFactory.generatePoint(s.getPoint2());
//                _points.add(s.getPoint2());
//            }
//            for(Segment s2 : _segments)
//            {
//                if(!s.equals(s2))
//                {
//                    if(s.segmentIntersection(s2)!=null)
//                    {
//                        if(!PointFactory.isGenerated(s.segmentIntersection(s2)))
//                        {
//                            if(!structurallyContains(_points,s.segmentIntersection(s2)))
//                            {
//                                _points.add(PointFactory.generatePoint(s.segmentIntersection(s2))); //this should find the intersection point and make a new point right?
//                            }
//                            else
//                            {
//                                PointFactory.generatePoint(s.segmentIntersection(s2)); //do we need this since if it isn't generated then we don't have the point? but I put it in for safety
//                            }
//                        }
//                    }
//                }
//            }
//
//            for(Point p : _points)
//            {
//                // A-B-C 
//                //need to check if AB & BC & AC are in the segment list and that all three points are added to points
//                //will check for midpoint in fact computer
//                if(s.pointLiesOnSegment(p)) 
//                {
//                    //check to see if this segment is already created
//                    boolean made1 = false;
//                    boolean made2 = false;
//                    for(Segment seg : _segments)
//                    {
//                        if( (seg.getPoint1().structurallyEquals(s.getPoint1()) && seg.getPoint2().structurallyEquals(p)) )
//                        {
//                            //then this segment is created
//                            made1 = true;
//                        }
//                        if((seg.getPoint1().structurallyEquals(p) && seg.getPoint2().structurallyEquals(s.getPoint2())))
//                        {
//                            made2=true;
//                        }
//                    }
//                    //if they are not found to be made then we need to make the segments
//                    if(!made1)
//                    {
//                        if(!s.getPoint1().structurallyEquals(p))
//                        {
//                            segs.add(new Segment(s.getPoint1(),p));
//                        }
//                    }
//                    if(!made2)
//                    {
//                        if(!s.getPoint2().structurallyEquals(p))
//                        {
//                            segs.add(new Segment(p,s.getPoint2()));
//                        }
//                    }
//                }
//                //Either B-A-C or A-B-C or A-B-C
//                //but maybe this will would create segments that shouldn't exist?
//                //                if(s.pointLiesOnLine(p))
//                //                {
//                //                    
//                //                }
//            }
//
//
//        }
//        _segments.addAll(segs);
    }

    private void computeArcRelations()
    {
        //might need to add checking to see if any random point is on the circle?
        for(Arc a : _arcs)
        {
            //sectors.add(new Sector(a));

            //check both endpoints to see if they are contained in points
            //  if not, add them
            //  does an issue potentially pop up regarding this? unsure, consult with Tom/Alvin
            if(!_points.contains(a.getEndpoint1()))
            {
                if(!PointFactory.contains(a.getEndpoint1()))
                    PointFactory.generatePoint(a.getEndpoint1());
                _points.add(a.getEndpoint1());   
            }
            if(!_points.contains(a.getEndpoint2()))
            {
                if(!PointFactory.contains(a.getEndpoint2()))
                    PointFactory.generatePoint(a.getEndpoint2());
                _points.add(a.getEndpoint2());   
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

            _sectors.add(new Sector(a));

            //The handler for circles will add complementary angles
        }
    }

    private void computeCircleRelations()
    {
        for(Circle c: _circles)
        {
            //Make sure the centerpoint is in points
            if(!_points.contains(c.getCenter()))
            {
                if(!PointFactory.contains(c.getCenter()))
                    PointFactory.generatePoint(c.getCenter());
                _points.add(c.getCenter());
            }

            //go through the list of points
            //  for each point, check if it is on the circle
            //  if not, add it to 
            for(Point p : _points)
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
                if(!_points.contains(circlePoints.get(i)))
                {
                    if(!PointFactory.contains(circlePoints.get(i)))
                        PointFactory.generatePoint(circlePoints.get(i));
                    _points.add(circlePoints.get(i));
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

                    if(!_arcs.contains(one))
                    {
                        _arcs.add(one);
                        _sectors.add(new Sector(one));
                    }
                    if(!_arcs.contains(two)) 
                    {
                        _arcs.add(two);
                        _sectors.add(new Sector(two));
                    }
                }
            }
            //use the hash so we can figure out the arcs to create.
            //then create a new sector based off of that arc
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

    /*
     * @param figure -- a list of clauses describing the given figure.
     * This method splits those clauses into appropriate bins.
     */
    private void filterClauses(ArrayList<GroundedClause> figure)
    {
        for(GroundedClause clause : figure)
        {
            if(clause != null)
            {
                if(clause instanceof Circle)
                {
                    _circles.add((Circle)clause);
                }
                else if(clause instanceof Arc)
                {
                    _arcs.add((Arc)clause);
                }
                else if (clause instanceof Segment)
                {
                    _segments.add((Segment)clause);
                }
                else if(clause instanceof Point)
                {
                    // This is weird as an artifact of the original C# code.
                    if(!PointFactory.isGenerated((Point)clause))
                    {
                        PointFactory.generatePoint((Point)clause);
                    }
                    if(!_points.contains((Point)clause))
                    {
                        _points.add((Point)clause);
                    }
                }
            }
        }
    }
}
