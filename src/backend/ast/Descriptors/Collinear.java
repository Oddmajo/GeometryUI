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

import java.util.ArrayList;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class Collinear extends Descriptor
{
    private ArrayList<Point> points;
    
    
    public ArrayList<Point> getPoints()
    {
    	return points;
    }
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
        if(points.size() <2)
        {
        	ExceptionHandler.throwException( new ArgumentException("A Collinear relationship requires at least 2 points: " + this.toString()));
        }
        
        //create a segment of the endpoints to compare all points for collinearity
        Segment line = new Segment(points.get(0), points.get(points.size() -1 ));
        
        for(Point pt : points)
        {
        	if(!line.pointLiesOn(pt))
        	{
        		ExceptionHandler.throwException(new ArgumentException("Point " + pt + " is not collinear with line " + line.toString()));
        	}
        	if(!line.pointLiesBetweenEndpoints(pt))
        	{
        		ExceptionHandler.throwException(new ArgumentException("Point " + pt + " is not between the endpoints of segment " + line.toString()));
        	}
        }
    }
    
    public void AddCollinearPoint(Point newPt)
    {
    	//traverse list to find where to insert the new point in the list in the proper order
    	for(int p =0; p<points.size(); p++)
    	{
    		if(Segment.Between(newPt, points.get(p), points.get(p+1)))
    		{
    			points.add(p+1, newPt);
    			return;
    		}
    	}
    	points.add(newPt);
    }
    
    @Override
    public boolean equals(Object obj)
    {
    	if(obj != null && obj instanceof Collinear)
    	{
    		Collinear collObj = (Collinear)obj;
    		
    		
    		//check all points
    		for(Point pt: collObj.points)
    		{
    			if(!points.contains(pt))
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    	
    	//This is untested but should be correct. IF the if isn't hit then it should never be equal
    	return false;
    }
    
    @Override
    public int getHashCode()
    {
    	return super.getHashCode();
    }
    
    @Override
    public String toString()
    {
    	ArrayList<String> strings = new ArrayList<String>();
    	for(Point p : points)
    	{
    		strings.add(p.toString());
    	}
    	
    	
    	//old code return "Collinear(" + string.Join(",", strings.ToArray()) + ")";
    	//This is untested
    	return "Collinear(" + String.join(",", strings) + ")";
    }
    
}

