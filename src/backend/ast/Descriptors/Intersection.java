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
package backend.ast.Descriptors;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.utilities.Pair;

public class Intersection extends Descriptor
{
    protected Point intersect;
    protected Segment lhs;
    protected Segment rhs;
    
    public Point getIntersect()
    {
    	return intersect;
    }
    public Segment getlhs()
    {
    	return lhs;
    }
    public Segment getrhs()
    {
    	return rhs;
    }
    
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
        if (lhs.pointLiesBetweenEndpoints(rhs.getPoint1())) return rhs.getPoint2();
        if (lhs.pointLiesBetweenEndpoints(rhs.getPoint2())) return rhs.getPoint1();
        if (rhs.pointLiesBetweenEndpoints(lhs.getPoint1())) return lhs.getPoint2();
        if (rhs.pointLiesBetweenEndpoints(lhs.getPoint2())) return lhs.getPoint1();

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
        Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
        
        // A valid transversal is required for this shape
        if(!this.CreatesAValidTransversalWith(thatInter))
        {
        	return nullPair;
        }
        
        //Restrict to desired combination
        if(this.StandsOnEndpoint() && thatInter.StandsOnEndpoint())
        {
        	return nullPair;
        }
        
        //Determine which is the stands and which is the endpoint
        Intersection endpointInter = null;
        Intersection standsInter = null;
        
        
        //THIS NEEDS TO BE TESTED FOR CORRECTNESS!
        if(this.StandsOnEndpoint() && thatInter.standsOn())
        {
        	endpointInter = this;
        	standsInter = thatInter;
        }
        else if(thatInter.StandsOnEndpoint() && this.standsOn())
        {
        	//This is the old code and I believe either this or the one above is a logic error
//        	endpointInter = this;
//        	standsInter = thatInter;
        	endpointInter = thatInter;
        	standsInter = this;
        }
        else
        {
        	return nullPair;
        }
        
        //Avoid some shapes
        Segment transversal = this.AcquireTransversal(thatInter);
        Segment transversalStands = standsInter.GetCollinearSegment(transversal);
        
        Point top = null;
        Point bottom = null;
        if(Segment.Between(standsInter.intersect, transversalStands.getPoint1(), endpointInter.intersect))
        {
        	top = transversalStands.getPoint1();
        	bottom = transversalStands.getPoint2();
        }
        else
        {
        	top = transversalStands.getPoint2();
        	bottom = transversalStands.getPoint1();
        }
        
        // Avoid: ____  Although this shouldn't happen since both intersections do not stand on endpoints
        //        ____|
        //
        // Also avoid Simple F-Shape
        //
        if(transversal.has(top) || transversal.has(bottom))
        {
        	return nullPair;
        }
        
        //Determine S shape
        Point offStands = standsInter.CreatesTShape();
        
        Segment parallelEndpoint = endpointInter.OtherSegment(transversal);
        Point offEndpoint = parallelEndpoint.other(endpointInter.intersect);
        
        Segment crossingTester = new Segment(offStands, offEndpoint);
        Point intersection = transversal.lineIntersection(crossingTester);
        
        															// S-shape    // PI-Shape
        return transversal.pointLiesBetweenEndpoints(intersection) ? nullPair : new Pair<Point, Point>(offEndpoint, offStands);
    }
    
    //
    // Creates an F-Shape
    //   top
    //    _____ offEnd     <--- Stands on Endpt
    //   |
    //   |_____ offStands  <--- Stands on 
    //   |
    //   | 
    //  bottom
    //   Order of non-collinear points is order of intersections: <this, that>
    public Pair<Point, Point> CreatesFShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is require for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
        {
        	return nullPair;
        }
    	
    	//Avoid both standing on an endpoint
    	if(this.StandsOnEndpoint() && thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	Intersection endpt = null;
    	Intersection standsOn = null;
    	
    	if(this.StandsOnEndpoint() && thatInter.standsOn())
    	{
    		endpt = this;
    		standsOn = this;
    	}
    	else if(thatInter.StandsOnEndpoint() && this.standsOn())
    	{
    		endpt = thatInter;
    		standsOn = this;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Segment transversalStands = standsOn.GetCollinearSegment(transversal);
    	
    	//Determine Top and bottom to avoid PI shape
    	Point top = null;
    	Point bottom = null;
    	if(Segment.Between(standsOn.intersect, transversalStands.getPoint1(), endpt.intersect))
    	{
    		bottom = transversalStands.getPoint1();
    		top = transversalStands.getPoint2();
    	}
    	else
    	{
    		bottom = transversalStands.getPoint2();
    		top = transversalStands.getPoint1();
    	}
    	
    	// Avoid: ____  Although this shouldn't happen since both intersections do not stand on endpoints
        //        ____|
    	if(transversal.has(top) && transversal.has(bottom))
    	{
    		return nullPair;
    	}
    	
    	//Also avoid simple PI shape
    	if(!transversal.has(top) && !transversal.has(bottom))
    	{
    		return nullPair;
    	}
    	
    	//Find the two points that make the points of the F
    	Segment parallelEndPt = endpt.OtherSegment(transversal);
    	Segment parallelStands = standsOn.OtherSegment(transversal);
    	
    	Point offEnd = transversal.pointLiesOn(parallelEndPt.getPoint1()) ? parallelEndPt.getPoint2() : parallelEndPt.getPoint1();
    	Point offStands = transversal.pointLiesOn(parallelStands.getPoint1()) ? parallelStands.getPoint2() : parallelStands.getPoint1();
    	
    	// Check this is not a crazy F
        //        _____
        //       |
        //   ____| 
        //       |
        //       | 
    	Point intersection = transversal.lineIntersection(new Segment(offEnd, offStands));
    	
    	if(transversal.pointLiesBetweenEndpoints(intersection))
    	{
    		return nullPair;
    	}
    	
    	// Return in the order of 'off' points: <this, that>
    	return this.equals(endpt) ? new Pair<Point, Point>(offEnd, offStands) : new Pair<Point, Point>(offStands, offEnd);
    }
    
    //
    // Creates a Topped F-Shape
    //            top
    // offLeft __________ offEnd    <--- Stands on
    //             |
    //             |_____ off       <--- Stands on 
    //             |
    //             | 
    //           bottom
    //
    //   Returns: <bottom, off>
    public Pair<Intersection, Point> CreatesToppedFShape(Intersection thatInter)
    {
    	Pair<Intersection, Point> nullPair = new Pair<Intersection, Point>(null,null);
    	
    	//a valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	//avoid both standing on an endpoint OR crossing
    	if(this.StandsOnEndpoint() || thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	if(this.Crossing() || thatInter.Crossing())
    	{
    		return nullPair;
    	}
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	Intersection standsOnTop = null;
    	Intersection standsOnBottom = null;
    	
    	//Top has 2 points on the transversal; bottom has 3
    	Segment nonTransversalThis = this.OtherSegment(transversal);
    	Segment nonTransversalThat = thatInter.OtherSegment(transversal);
    	
    	
    	//The below may be a logic error since there are two if statements that can result in the same thing happening
    	if(transversal.pointLiesBetweenEndpoints(nonTransversalThis.getPoint1()) ||
    		transversal.pointLiesBetweenEndpoints(nonTransversalThis.getPoint2()))
    	{
    		//             |
            //         ____|                <--- Stands on
            //             |
            //             |_____ off       <--- Stands on 
            //             |
            //             | 
    		if(transversal.pointLiesBetweenEndpoints(nonTransversalThat.getPoint1()) ||
    				transversal.pointLiesBetweenEndpoints(nonTransversalThat.getPoint2()))
    		{
    			return nullPair;
    		}
    		standsOnBottom = this;
    		standsOnTop = thatInter;
    	}
    	else if(transversal.pointLiesBetweenEndpoints(nonTransversalThat.getPoint1()) ||
    			transversal.pointLiesBetweenEndpoints(nonTransversalThat.getPoint2()))
    	{
    		standsOnBottom = this;
    		standsOnTop= thatInter;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	//check that the bottom extends the transversal
    	if(!standsOnBottom.GetCollinearSegment(transversal).strictContains(transversal))
    	{
    		return nullPair;
    	}
    	
    	Point off = standsOnBottom.OtherSegment(transversal).other(standsOnBottom.intersect);
    	
    	return new Pair<Intersection, Point>(standsOnBottom, off);
    }
    
    //
    // Creates a PI
    //
    //   |______
    //   |
    //   |______
    //   |
    //
    //   Order of non-collinear points is order of intersections: <this, that>
    public Pair<Point, Point> CreatesPIShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	Point thisNonCollinear = this.CreatesTShape();
    	Point thatNonCollinear = thatInter.CreatesTShape();
    	
    	if(thisNonCollinear == null || thatNonCollinear == null) 
    	{
    		return nullPair;
    	}

        // Verify that the shape is PI and not an S-shape; look for the intersection point NOT between the endpoints of the transversal 
        //
        // The transversal should be valid
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Point intersection = transversal.lineIntersection(new Segment(thisNonCollinear, thatNonCollinear));
    	
    	//S-Shape
    	if(transversal.pointLiesBetweenEndpoints(intersection)) 
    	{
    		return nullPair;
    	}
    	
    	//PI-Shape
    	return new Pair<Point,Point>(thisNonCollinear, thatNonCollinear);
    }
    
    //
    //                    o
    //                    eoooooooo  offStands
    //                    e
    //offEndpoint   eeeeeee
    //                    o
    //                       Returns: <offEndpoint, offStands>
    public Pair<Point,Point> CreatesSimpleSShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	//Restrict to desired combination
    	if(this.StandsOnEndpoint() && thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	//Determine which is the stands and which is the endpoint
    	Intersection endpointInter = null;
    	Intersection standsInter = null;
    	if(this.StandsOnEndpoint() && thatInter.standsOn())
    	{
    		endpointInter = this;
    		standsInter = thatInter;
    	}
    	//
    	//
    	//
    	//
    	// This was how it was written on the old code but I Believe this should be flipped 
    	//PLEASE VERIFY THIS IS RIGHT AND THER IS NO BUG EVEN IN OLD CODE
    	else if(thatInter.StandsOnEndpoint() && this.standsOn())
    	{
    		//This NEEDS TO BE TEST
//    		//old code
//    		endpointInter = this;
//    		standsInter = thatInter;
    		endpointInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	//Determine S shape
    	Point offStands = standsInter.CreatesTShape();
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Segment parallelEndpoint = endpointInter.OtherSegment(transversal);
    	Point offEndpoint = parallelEndpoint.other(endpointInter.intersect);
    	
    	Segment crossingTester = new Segment(offStands, offEndpoint);
    	Point intersection = transversal.lineIntersection(crossingTester);
    	
    	return transversal.pointLiesBetweenEndpoints(intersection) ? new Pair<Point, Point>(offEndpoint,offStands) : nullPair;
    }
    
    //
    // Creates a Leaner-Shape (a bench you can sit on)
    //
    //                 top
    //                  |______ tipStands
    //                  |
    //   tipEndpt ______|
    //
    //   Returns <tipEndpoint, tipStands>
    public Pair<Point, Point> CreatesLeanerShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null, null);
    	
    	// A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	if(this.StandsOnEndpoint() && thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	//Determine which is the stands and which is the endpoint
    	Intersection endpointInter = null;
    	Intersection standsInter = null;
    	if(this.StandsOnEndpoint() && thatInter.standsOn())
    	{
    		endpointInter = this;
    		standsInter = thatInter;
    	}
    	else if(thatInter.StandsOnEndpoint() && this.standsOn())
    	{
    		endpointInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	//Acquire Points
    	Point tipStands = standsInter.CreatesTShape();
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Segment parallelEndpoint = endpointInter.OtherSegment(transversal);
    	
    	Point tipEndpoint = parallelEndpoint.other(endpointInter.intersect);
    	
    	//Determine sides
    	Segment crossingTester = new Segment(tipEndpoint, tipStands);
    	Point intersection = transversal.lineIntersection(crossingTester);
    	
    	//F-shape
    	if(!transversal.pointLiesBetweenEndpoints(intersection))
    	{
    		return nullPair;
    	}
    	
    	//Desired leaner shape
    	return new Pair<Point, Point>(tipEndpoint, tipStands);
    }
    
    //
    // Creates an S-Shape
    //
    //         |______
    //         |
    //   ______|
    //         |
    //
    //   Order of non-collinear points is order of intersections: <this, that>
    public Pair<Point, Point> CreatesStandardSShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	Point thisNonCollinear = this.CreatesTShape();
    	Point thatNonCollinear = thatInter.CreatesTShape();
    	
    	if(thisNonCollinear == null || thatNonCollinear == null)
    	{
    		return nullPair;
    	}
    	
    	//
        // Verify that the shape is PI and not an S-shape; look for the intersection point NOT between the endpoints of the transversal 
        //
        // The transversal should be valid
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Point intersection = transversal.lineIntersection(new Segment(thisNonCollinear, thatNonCollinear));
    	
    	//PI-Shape
    	if(!transversal.pointLiesBetweenEndpoints(intersection))
    	{
    		return nullPair;
    	}
    	
    	//S-Shape
    	return new Pair<Point,Point>(thisNonCollinear, thatNonCollinear);
    }
    
    //
    // Creates a basic S-Shape with standsOnEndpoints
    //
    //                  ______ offThat
    //                 |
    //   offThis ______|
    //
    // Return <offThis, offThat>
    public Pair<Point,Point> CreatesBasicSShape(Intersection thatInter)
    {
    	Pair<Point,Point> nullPair = new Pair<Point,Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	if(!this.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	if(!thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	//Determine offThis and offThat
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	Segment parallelThis = this.OtherSegment(transversal);
    	Segment parallelThat = thatInter.OtherSegment(transversal);
    	
    	Point offThis = transversal.pointLiesBetweenEndpoints(parallelThis.getPoint1()) ? parallelThis.getPoint2() : parallelThis.getPoint1();
    	Point offThat = transversal.pointLiesBetweenEndpoints(parallelThat.getPoint1()) ? parallelThat.getPoint2() : parallelThat.getPoint1();
    	
    	//Avoid C-like scenario
    	Segment crossingTester = new Segment(offThis, offThat);
    	Point intersection = transversal.lineIntersection(crossingTester);
    	
    	//C-shape
    	if(!transversal.pointLiesBetweenEndpoints(intersection))
    	{
    		return nullPair;
    	}
    	
    	//S-shape
    	return new Pair<Point, Point>(offThis, offThat);
    }
    
    //
    // Creates a basic S-Shape with standsOnEndpoints
    //
    //   offThis   ______
    //                   |
    //   offThat   ______|
    //
    // Return <offThis, offThat>
    public Pair<Point, Point> CreatesBasicCShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	if(!this.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	if(!thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	//Determine offThis and offThat
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	Segment parallelThis = this.OtherSegment(transversal);
    	Segment parallelThat = thatInter.OtherSegment(transversal);
    	
    	Point offThis = transversal.pointLiesBetweenEndpoints(parallelThis.getPoint1()) ? parallelThis.getPoint2() : parallelThis.getPoint1();
    	Point offThat = transversal.pointLiesBetweenEndpoints(parallelThat.getPoint1()) ? parallelThat.getPoint2() : parallelThat.getPoint1();
    	
    	//Avoid S-shape scenario
    	Segment crossingTester = new Segment(offThis, offThat);
    	Point intersection = transversal.lineIntersection(crossingTester);
    	
    	//We may have parallel crossingTester and transversal; that's ok
    	if(crossingTester.isParallel(transversal))
    	{
    		return new Pair<Point, Point>(offThis, offThat);
    	}
    	
    	//S-Shape
    	if(transversal.pointLiesBetweenEndpoints(intersection))
    	{
    		return nullPair;
    	}
    	
    	//C-Shape
    	return new Pair<Point, Point>(offThis, offThat);
    }
    
    //
    // Creates a Chair
    //
    // |     |                  |
    // |_____|____   leftInter  |_________ tipOfT
    // |                        |     |
    // |                        |     |
    //                         off   tipOfT
    //
    //                                bottomInter
    //
    //                                               <leftInter, bottomInter>
    // Returns the legs of the chair in specific ordering: <off, bottomTip>
    public Pair<Point, Point> CreatesChairShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null, null);
    	
    	//A valid transversal is requied for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	//Both intersections must be standing on (and not endpoint)
    	if(!this.standsOn() || !thatInter.standsOn())
    	{
    		return nullPair;
    	}
    	if(this.StandsOnEndpoint() || thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	Point thisTipOfT = this.CreatesTShape();
    	Point thatTipOfT = thatInter.CreatesTShape();
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	Intersection leftInter = null;
    	Intersection bottomInter = null;
    	
        // Avoid:
        // |
        // |______
        // |     |
        // |     |
        // this is leftInter
    	Point bottomTip = null;
    	if(transversal.pointLiesOn(thisTipOfT))
    	{
    		if(transversal.pointLiesBetweenEndpoints(thisTipOfT))
    		{
    			return nullPair;
    		}
    		
    		leftInter = this;
    		bottomInter = thatInter;
    		bottomTip = thisTipOfT;
    	}
    	
    	//thatInter is leftiner
    	else if(transversal.pointLiesOn(thatTipOfT))
    	{
    		if(transversal.pointLiesBetweenEndpoints(thatTipOfT))
    		{
    			return nullPair;
    		}
    		leftInter = thatInter;
    		bottomInter = this;
    		bottomTip = thisTipOfT;
    	}
    	//Otherwise, this indicates a PI-shape scenario
    	else
    	{
    		return nullPair;
    	}
    	
    	//Returns the bottom of the legs of the chair
    	Segment ParallelLeft = leftInter.OtherSegment(transversal);
    	Segment crossingTester = new Segment(ParallelLeft.getPoint1(), bottomTip);
    	Point intersection = transversal.lineIntersection(crossingTester);
    	
    	Point off = transversal.pointLiesBetweenEndpoints(intersection) ? ParallelLeft.getPoint2() : ParallelLeft.getPoint1();
    	
    	return new Pair<Point, Point>(off, bottomTip);
    }
    
    //
    // Creates an H-Shape
    //
    // |     |
    // |_____|
    // |     |
    // |     |
    //
    public boolean CreatesHShape(Intersection thatInter)
    {
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return false;
    	}
    		
		//Both intersections must be standing on (and not endpoints)
		if(!this.standsOn() || !thatInter.standsOn())
		{
			return false;
		}
		if(this.StandsOnEndpoint() || this.StandsOnEndpoint())
		{
			return false;
		}
		
		Point thisTipOfT = this.CreatesTShape();
		Point thatTipOfT = thatInter.CreatesTShape();
		
		Segment transversal = this.AcquireTransversal(thatInter);
		
		//The tips of the intersections must be within the transversal (at the endpoint) for an H
		if(!transversal.pointLiesBetweenEndpoints(thisTipOfT))
		{
			return false;
		}
		if(!transversal.pointLiesBetweenEndpoints(thatTipOfT))
		{
			return false;
		}
		
		return true;
    	
    }
    
    //
    // Creates a shape like a crazy person flying
    //
    // |     |
    // |_____|___ off
    // |     |
    // |     |
    //
    // Similar to H-shape with an extended point
    // Returns the 'larger' intersection that contains the point: off
    public Pair<Intersection, Point> CreatesFlyingShape(Intersection thatInter)
    {
    	Pair<Intersection, Point> nullPair = new Pair<Intersection, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	//both intersections must be standing on (and not endpoints)
    	if(!this.standsOn() || !thatInter.standsOn())
    	{
    		return nullPair;
    	}
    	if(this.StandsOnEndpoint() || thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	Point thisTipOfT = this.CreatesTShape();
    	Point thatTipOfT = thatInter.CreatesTShape();
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	//we have an H-Shape if the tips of the intersections are at the endpoints of the transversal
    	if(transversal.pointLiesBetweenEndpoints(thisTipOfT) && transversal.pointLiesBetweenEndpoints(thatTipOfT))
    	{
    		return nullPair;
    	}
    	
    	Intersection retInter = null;
    	Point off = null;
    	if(transversal.pointLiesBetweenEndpoints(thisTipOfT))
    	{
    		retInter = thatInter;
    		off = thatTipOfT;
    	}
    	else
    	{
    		retInter = this;
    		off = thisTipOfT;
    	}
    	
    	return new Pair<Intersection, Point>(retInter, off);
    }
    
    // Corresponding angles if:
    //                      offRightEnd
    // standsOn (o)   o       e
    //                o       e    standsOnEndpoint (e)
    // offLeftEnd  eeeoeeeeeeee
    //                o
    //                o           
    //
    // Returns <offLeftEnd, offRightEnd>
    //
    public Pair<Point, Point> CreatesSimpleTShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point,Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	if(this.StandsOnEndpoint() && thatInter.StandsOnEndpoint())
    	{
    		return nullPair;
    	}
    	
    	//Determine which is the crossing intersection and which stands on the endpoints
    	Intersection endpointInter = null;
    	Intersection standsInter = null;
    	if(this.StandsOnEndpoint() && thatInter.standsOn())
    	{
    		endpointInter = this;
    		standsInter = thatInter;
    	}
    	
    	else if(thatInter.Crossing() && this.StandsOnEndpoint())
    	{
    		endpointInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	//Determine if the endpoint intersection extends beyond the stands parallel line
    	Segment transversal = this.AcquireTransversal(thatInter);
    	Segment transversalEndpoint = endpointInter.GetCollinearSegment(transversal);
    	
    	if(transversal.pointLiesBetweenEndpoints(transversalEndpoint.getPoint1()) &&
    		transversal.pointLiesBetweenEndpoints(transversalEndpoint.getPoint2()))
    	{
    		return nullPair;
    	}
    	
    	//Acquire the returning points
    	Segment parallelEndpoint = endpointInter.OtherSegment(transversal);
    	Point offLeftEnd = transversalEndpoint.other(endpointInter.intersect);
    	Point offRightEnd = parallelEndpoint.other(endpointInter.intersect);
    	
    	return new Pair<Point, Point>(offLeftEnd, offRightEnd);
    		
    }
    
    //
    // Creates a shape like an extended t
    //     offCross                          offCross  
    //      |                                   |
    // _____|____                         ______|______
    //      |                                   |
    //      |_____ offStands     offStands _____|
    //
    // Returns <offStands, offCross>
    public Pair<Point, Point> CreatesCrossedTShape(Intersection thatInter)
    {
    	Pair<Point, Point> nullPair = new Pair<Point, Point>(null,null);
    	
    	//A valid transversal is required for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return nullPair;
    	}
    	
    	//determine which is the crossing intersection and which stands on the endpoints
    	Intersection crossingInter = null;
    	Intersection standsInter = null;
    	if(this.Crossing() && thatInter.StandsOnEndpoint())
    	{
    		crossingInter = this;
    		standsInter = thatInter;
    	}
    	else if(thatInter.Crossing() && this.StandsOnEndpoint())
    	{
    		crossingInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return nullPair;
    	}
    	
    	//Acquire the returning points
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	Segment parallelStands = standsInter.OtherSegment(transversal);
    	Point offStands = transversal.pointLiesOn(parallelStands.getPoint1()) ? parallelStands.getPoint2() : parallelStands.getPoint1();
    	
    	Segment transversalCross = crossingInter.GetCollinearSegment(transversal);
    	Point offCross = Segment.Between(crossingInter.intersect, transversalCross.getPoint1(), standsInter.intersect) ? transversalCross.getPoint1() : transversalCross.getPoint2();
    	
    	return new Pair<Point, Point>(offStands, offCross);
    }
    
    //
    // Creates a flying shape using a CROSSING intersection
    //     offCross
    //        |
    //  ______|______ <--crossingInter
    //        |
    //   _____|_____  <--standsInter
    //
    // Returns <offCross>
    //
    public Point CreatesFlyingShapeWithCrossing(Intersection thatInter)
    {
    	//A valid transversal is required for this shape	
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return null;
    	}
    	
    	//we should not have endpoint standing as that is handled elsewhere
    	if(this.StandsOnEndpoint() || thatInter.StandsOnEndpoint())
    	{
    		return null;
    	}
    	
    	//Determine which is the crossing intersection and which stands on the endpoints
    	Intersection crossingInter = null;
    	Intersection standsInter = null;
    	if(this.Crossing() && thatInter.standsOn())
    	{
    		crossingInter = this;
    		standsInter = thatInter;
    	}
    	else if(thatInter.Crossing() && this.standsOn())
    	{
    		crossingInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return null;
    	}
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	// Stands on intersection must have BOTH points not on the transversal line
        //        |
        //  ______|______
        //        |
        //        |_____
        //        |
        //        |
    	if(!transversal.pointLiesOn(standsInter.CreatesTShape()))
    	{
    		return null;
    	}
    	
    	//Success, we have the desired shape
    	//Acquire return point: offCross
    	Segment transversalCrossing = crossingInter.GetCollinearSegment(transversal);
    	
    	return Segment.Between(crossingInter.intersect, transversalCrossing.getPoint1(), standsInter.intersect) ? transversalCrossing.getPoint1() : transversalCrossing.getPoint2();
    }
    
    //
    // Creates a flying shape using a CROSSING intersection
    //     offCross
    //        |
    //  ______|______
    //        |
    //        |_____
    //        |
    //        |
    //
    // Returns <offCross>
    //
    public Point CreatesExtendedChairShape(Intersection thatInter)
    {
    	// A valid transversal is requied for this shape
    	if(!this.CreatesAValidTransversalWith(thatInter))
    	{
    		return null;
    	}
    	
    	//we should not have endpoint standing as that is handled elsewhere
    	if(this.StandsOnEndpoint() || thatInter.StandsOnEndpoint())
    	{
    		return null;
    	}
    	
    	//Determine which is the crossing intersection and which stands on the endpoints
    	Intersection crossingInter = null;
    	Intersection standsInter = null;
    	if(this.Crossing() && thatInter.standsOn())
    	{
    		crossingInter = this;
    		standsInter = thatInter;
    	}
    	else if(thatInter.Crossing() && this.standsOn() )
    	{
    		crossingInter = thatInter;
    		standsInter = this;
    	}
    	else
    	{
    		return null;
    	}
    	
    	Segment transversal = this.AcquireTransversal(thatInter);
    	
    	// Avoid this shape:
        //        |
        //  ______|______
        //        |
        //   _____|_____
    	if(transversal.pointLiesOn(standsInter.CreatesTShape()))
    	{
    		return null;
    	}
    	
    	//Success, we have the desired shape
    	//Acquire return point
    	Segment transversalCrossing = crossingInter.GetCollinearSegment(transversal);
    	
    	return Segment.Between(crossingInter.intersect, transversalCrossing.getPoint1(), standsInter.intersect) ? transversalCrossing.getPoint1() : transversalCrossing.getPoint2();
    }
    
    //If an endpoint of one segment is on the other segment
    public boolean StandsOnEndpoint()
    {
    	if(lhs.getPoint1().equals(rhs.getPoint1()) || lhs.getPoint1().equals(rhs.getPoint2()))
    	{
    		return true;
    	}
    	if(lhs.getPoint2().equals(rhs.getPoint1()) || lhs.getPoint2().equals(rhs.getPoint2()))
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean Crossing()
    {
    	return !standsOn() && !StandsOnEndpoint();
    }
    
    public boolean IsStraightAngleIntersection()
    {
    	return !StandsOnEndpoint();
    }
    
    //If an endpoint of one segment is on the other segment
    public boolean HasSegment(Segment s)
    {
    	return lhs.structurallyEquals(s) || rhs.structurallyEquals(s);
    }
    
    //if an endpoint of one segment is on the other segment
    public boolean HasSubSegment(Segment s)
    {
    	return lhs.contains(s) ||rhs.contains(s);
    }
    
    public Segment GetCollinearSegment(Segment thatSegment)
    {
    	if(lhs.isCollinearWith(thatSegment))
    	{
    		return lhs;
    	}
    	if(rhs.isCollinearWith(thatSegment))
    	{
    		return rhs;
    	}
    	
    	return null;
    }
    
    //if a common segment exists (a transversal that cuts across both intersections), return that common segment
    public Segment CommonSegment(Intersection thatInter)
    {
    	if (lhs.structurallyEquals(thatInter.lhs) || lhs.isCollinearWith(thatInter.lhs))
		{
			return lhs;
		}
        if (lhs.structurallyEquals(thatInter.rhs) || lhs.isCollinearWith(thatInter.rhs))
    	{
        	return lhs;
    	}
        if (rhs.structurallyEquals(thatInter.lhs) || rhs.isCollinearWith(thatInter.lhs))
    	{
        	return rhs;
    	}
        if (rhs.structurallyEquals(thatInter.rhs) || rhs.isCollinearWith(thatInter.rhs))
    	{
        	return rhs;
    	}
        return null;
    }
    
    //If a common segment exists( a transversal that cuts across both intersections), retuyrn that common segment
    public Segment CommonSegment(Parallel thatParallel)
    {
    	if (lhs.structurallyEquals(thatParallel.getSegment1()) || lhs.isCollinearWith(thatParallel.getSegment1()))
		{
    		return lhs;
		}
        if (lhs.structurallyEquals(thatParallel.getSegment2()) || lhs.isCollinearWith(thatParallel.getSegment2()))
    	{
        	return lhs;
    	}
        if (rhs.structurallyEquals(thatParallel.getSegment1()) || rhs.isCollinearWith(thatParallel.getSegment1()))
    	{
        	return rhs;
    	}
        if (rhs.structurallyEquals(thatParallel.getSegment2()) || rhs.isCollinearWith(thatParallel.getSegment2()))
    	{
        	return rhs;
    	}

        return null;
    }
    
    //If a common segment exists (a transversal that cuts across both intersections), return that common segment
    public boolean InducesNonStraightAngle(Angle thatAngle)
    {
    	// The given vertex must match the intersection point of the two lines intersection
    	if(!intersect.equals(thatAngle.getVertex()))
    	{
    		return false;
    	}
    	 //   /
        //  /
        // /_______
        //
        if (this.StandsOnEndpoint())
        {
            return thatAngle.equates(new Angle(lhs.other(intersect), intersect, rhs.other(intersect)));
        }
        //          /
        //         /
        // _______/_______
        //
        else if (this.standsOn())
        {
            Point off = this.CreatesTShape();
            Segment baseSegment = lhs.pointLiesBetweenEndpoints(intersect) ? lhs : rhs;

            if (thatAngle.equates(new Angle(baseSegment.getPoint1(), intersect, off)))
    		{
            	return true;
    		}
            if (thatAngle.equates(new Angle(baseSegment.getPoint2(), intersect, off)))
        	{
            	return true;
        	}
        }
//      /
        // _______/_______
        //       /
        //      /
        else if (this.Crossing())
        {
            if (thatAngle.equates(new Angle(lhs.getPoint1(), intersect, rhs.getPoint1())))
        	{
            	return true;
        	}
            if (thatAngle.equates(new Angle(lhs.getPoint1(), intersect, rhs.getPoint2())))
        	{
            	return true;
        	}
            if (thatAngle.equates(new Angle(lhs.getPoint2(), intersect, rhs.getPoint1())))
        	{
            	return true;
        	}
            if (thatAngle.equates(new Angle(lhs.getPoint2(), intersect, rhs.getPoint2())))
        	{
            	return true;
        	}
        }
        return false;
    }
    
    //
    // If a common segment exists (a transversal that cuts across both intersections), return that common segment
    //
    public Angle GetInducedNonStraightAngle(CongruentAngles congAngles)
    {
    	if(this.InducesNonStraightAngle(congAngles.GetFirstAngle()))
    	{
    		return congAngles.GetFirstAngle();
    	}
    	if(this.InducesNonStraightAngle(congAngles.GetSecondAngle()))
    	{
    		return congAngles.GetSecondAngle();
    	}
    	return null;
    }
    public Angle GetInducedNonStraightAngle(Supplementary supp)
    {
    	if (this.InducesNonStraightAngle(supp.getAngle1()))
		{
    		return supp.angle1;
		}
        if (this.InducesNonStraightAngle(supp.getAngle2()))
    	{
        	return supp.angle2;
    	}

        return null;
    }
    
    //
    // Are both angles induced by this intersection either as vertical angles or adjacent angles
    //
    public boolean InducesBothAngles(CongruentAngles conAngles)
    {
    	return this.InducesNonStraightAngle(conAngles.GetFirstAngle()) && this.InducesNonStraightAngle(conAngles.GetSecondAngle());
    }
    
    public Segment OtherSegment(Segment thatSegment)
    {
    	if (lhs.equals(thatSegment))
		{
    		return rhs;
		}
        if (rhs.equals(thatSegment))
    	{
        	return lhs;
    	}

        if (lhs.isCollinearWith(thatSegment))
    	{
        	return rhs;
    	}
        if (rhs.isCollinearWith(thatSegment))
    	{
        	return lhs;
    	}

        return null;
    }
    
    @Override
    public boolean canBeStrengthenedTo(GroundedClause gc)
    {
    	if(gc !=null && gc instanceof Perpendicular)
    	{
    		Perpendicular perp = (Perpendicular)gc;
    		
    		return intersect.equals(perp.intersect) && ((lhs.structurallyEquals(perp.lhs) && rhs.structurallyEquals(perp.rhs)) ||
    													(lhs.structurallyEquals(perp.rhs) && rhs.structurallyEquals(perp.lhs)));
    	}
    	
    	return false;
    }
    
    //
    // Is the given segment collinear with this intersection
    //
    public boolean ImpliesRay(Segment s)
    {
        if (!intersect.equals(s.getPoint1()) && !intersect.equals(s.getPoint2()))
    	{
        	return false;
    	}

        return lhs.isCollinearWith(s) || rhs.isCollinearWith(s);
    }
    
    public boolean DefinesBothRays(Segment thatSeg1, Segment thatSeg2)
    {
        return ImpliesRay(thatSeg1) && ImpliesRay(thatSeg2);
    }
    
    public boolean CreatesAValidTransversalWith(Intersection thatInter)
    {
        Segment transversal = this.AcquireTransversal(thatInter);
        if (transversal == null)
    	{
    		return false;
    	}

        // Ensure the non-traversal segments align with the parallel segments
        Segment nonTransversalThis = this.OtherSegment(transversal);
        Segment nonTransversalThat = thatInter.OtherSegment(transversal);

        Segment thisTransversalSegment = this.OtherSegment(nonTransversalThis);
        Segment thatTransversalSegment = thatInter.OtherSegment(nonTransversalThat);

        // Parallel lines should not coincide
        if (nonTransversalThis.isCollinearWith(nonTransversalThat))
    	{
        	return false;
    	}

        // Avoid:
        //      |            |
        //    __|    ________|
        //      |            |
        //      |            |

        // Both intersections (transversal segments) must contain the actual transversal
        return thatTransversalSegment.contains(transversal) && thisTransversalSegment.contains(transversal);
    }
    
    //
    // Returns the exact transversal between the intersections
    //
    public Segment AcquireTransversal(Intersection thatInter)
    {
        // The two intersections should not be at the same vertex
        if (intersect.equals(thatInter.intersect))
    	{
        	return null;
    	}

        Segment common = CommonSegment(thatInter);
        if (common == null)
    	{
        	return null;
    	}

        // A legitimate transversal must belong to both intersections (is a subsegment of one of the lines)
        Segment transversal = new Segment(this.intersect, thatInter.intersect);

        Segment thisTransversal = this.GetCollinearSegment(transversal);
        Segment thatTransversal = thatInter.GetCollinearSegment(transversal);

        if (!thisTransversal.contains(transversal))
    	{
        	return null;
    	}
        if (!thatTransversal.contains(transversal))
    	{
        	return null;
    	}

        return transversal;
    }
    
    public boolean isPerpendicular()
    {
        return lhs.isSegmentPerpendicular(rhs);
    }
    
    @Override
    public boolean structurallyEquals(Object obj)
    {
    	if(obj !=null && obj instanceof Perpendicular)
    	{
    		Perpendicular perp = (Perpendicular)obj;
    		return perp.structurallyEquals(this);
    	}
    	
    	
    	if(obj != null && obj instanceof Intersection)
    	{
    		Intersection inter = null;
    		inter = (Intersection)obj;
    		return intersect.equals(inter.intersect) && ((lhs.structurallyEquals(inter.lhs) && rhs.structurallyEquals(inter.rhs)) ||
    				(lhs.structurallyEquals(inter.rhs) && rhs.structurallyEquals(inter.lhs)));
    	}
    	
    	return false;
    	
    }
    
    @Override 
    public boolean equals(Object obj)
    {
    	if(obj != null && obj instanceof PerpendicularBisector)
    	{
    		PerpendicularBisector perpBi = (PerpendicularBisector)obj;
    		return obj.equals(this);
    	}
    	if(obj != null && obj instanceof Perpendicular)
    	{
    		Perpendicular perp = (Perpendicular)obj;
    		return perp.equals(this);
    	}
    	
    	
    	if(obj != null && obj instanceof Intersection)
    	{
    		Intersection inter = null;
    		inter = (Intersection)obj;
    		return structurallyEquals(inter) && super.equals(inter);
    	}
    	
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
    	return intersect.toPrettyString() + " is the intersection of " + lhs.toPrettyString() + " and " + rhs.toPrettyString() + ".";
    }
    
}
