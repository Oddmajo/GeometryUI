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
import backend.ast.GroundedClause;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Parallel;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Arcs_and_Circles.CircleCircleIntersection;
import backend.ast.Descriptors.Arcs_and_Circles.CircleSegmentIntersection;
import backend.ast.figure.components.*;
public class CoordinatePrecomputer
{
    private ArrayList<Circle> circles;
    private ArrayList<Quadrilateral> quadrilaterals;
    private ArrayList<Triangle> triangles;
    private ArrayList<Segment> segments;
    private ArrayList<Angle> angles;
    private ArrayList<Collinear> collinear;
    
    private ArrayList<InMiddle> inMiddles;
    private ArrayList<Intersection> intersections;
    private ArrayList<Perpendicular> perpendiculars;
    private ArrayList<Parallel> parallels;
    
    private ArrayList<MinorArc> minorArcs;
    private ArrayList<MajorArc> majorArcs;
    private ArrayList<Semicircle> semiCircles;
    private ArrayList<Sector> sectors;
    private ArrayList<ArcInMiddle> arcInMiddle;
    
    private ArrayList<CircleSegmentIntersection> csIntersections;
    private ArrayList<CircleCircleIntersection> ccIntersections;
    
    public CoordinatePrecomputer(ArrayList<GroundedClause> figure)
    {
        circles = new ArrayList<Circle>();
        quadrilaterals = new ArrayList<Quadrilateral>();
        triangles = new ArrayList<Triangle>();
        segments = new ArrayList<Segment>();
        angles = new ArrayList<Angle>();
        collinear = new ArrayList<Collinear>();

        inMiddles = new ArrayList<InMiddle>();
        intersections = new ArrayList<Intersection>();
        perpendiculars = new ArrayList<Perpendicular>();
        parallels = new ArrayList<Parallel>();

        minorArcs = new ArrayList<MinorArc>();
        majorArcs = new ArrayList<MajorArc>();
        semiCircles = new ArrayList<Semicircle>();
        sectors = new ArrayList<Sector>();
        arcInMiddle = new ArrayList<ArcInMiddle>();

        FilterClauses(figure);
    }
    
    //Split the figure into the constituent clauses
    public void FilterClauses(ArrayList<GroundedClause> figure)
    {
        for(GroundedClause clause : figure)
        {
            if(clause instanceof Circle && clause != null)
            {
                circles.add((Circle)clause);
            }
            else if(clause instanceof Quadrilateral && clause != null)
            {
                quadrilaterals.add((Quadrilateral)clause);
            }
            else if(clause instanceof Triangle && clause != null)
            {
                triangles.add((Triangle)clause);
            }
            
        }
    }
}
