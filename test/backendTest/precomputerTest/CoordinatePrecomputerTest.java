package backendTest.precomputerTest;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.AlgebraicParallel;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MidPoint;
import backend.ast.Descriptors.Parallel;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.EquationSegment;
import backend.ast.figure.components.MajorArc;
import backend.ast.figure.components.MinorArc;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Quadrilateral;
import backend.ast.figure.components.RightAngle;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.Triangle;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.precomputer.CoordinatePrecomputer;
import backend.utilities.logger.LoggerFactory;
import backend.ast.figure.components.Semicircle;

import org.junit.Test;
import org.junit.Assert;

public class CoordinatePrecomputerTest 
{	
	
	@Test
	public void simpleVerticalParallelSegment()
	{
		System.out.print("Starting Precomputer Simple Vertical parallel Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0),new Point("",0,10));
		Segment seg2 = new Segment(new Point("",1,0), new Point("",1,9));
		
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Parallel"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleHorizontalParallelSegment()
	{
		System.out.print("Starting Precomputer Simple Horizontal parallel Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg3 = new Segment(new Point("",0,0), new Point("",10,0));
		Segment seg4 = new Segment(new Point("",0,1), new Point("",9,1));
		clauses.add(seg3);
		clauses.add(seg4);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Parallel"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleParallelSegment()
	{
		System.out.print("Starting Precomputer Simple parallel Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,1), new Point("",1,2));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",1,1));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Parallel"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
		
	}
	
	
	//currently fails cause it is somehow calling figure.coordinatecongruent
	@Test
	public void simpleZeroCongruentSegment()
	{
		System.out.print("Starting Precomputer Simple zero length congruent Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,0));
		Segment seg2 = new Segment(new Point("",1,0), new Point("",1,0));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Congruent"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleCongruentSegment()
	{
		System.out.print("Starting Precomputer Simple congruent Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",1,1));
		Segment seg2 = new Segment(new Point("",1,1), new Point("",2,2));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Congruent"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleVerticalPerpendicularSegment()
	{
		System.out.print("Starting Precomputer Simple Vertical Perpdenicular Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,-1), new Point("",0,1));
		Segment seg2 = new Segment(new Point("",-1,0), new Point("",1,0));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Perpendicular"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simplePerpendicularSegment()
	{
		System.out.print("Starting Precomputer Simple Perpdenicular Segment test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",2,2));
		Segment seg2 = new Segment(new Point("",0,1), new Point("",1,0));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Perpendicular"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simplePerpendicularSegmentBisector()
	{
		System.out.print("Starting Precomputer Simple Perpendicular SegmentBisector test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,10));
		Segment seg2 = new Segment(new Point("",-2,5), new Point("",2,5));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("PerpendicularBisector"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleSegmentBisector()
	{
		System.out.print("Starting Precomputer Simple SegmentBisector test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,10));
		Segment seg2 = new Segment(new Point("",1,5), new Point("",-6,5));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d.toString().contains("Bisector"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	
	//please look deeper into this test
	@Test
	public void simpleRatio()
	{
		System.out.print("Starting Precomputer Simple Ratio test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,1));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",0,3));
		clauses.add(seg1);
		clauses.add(seg2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			//System.out.println(d.toString());
			if(d.toString().contains("Ratio"))
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void rightTriangle()
	{
		System.out.println("Starting Precomputer Right Triangle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Triangle tri = new Triangle(new Point("",0,0), new Point("",1,0), new Point("",0,1));
		clauses.add(tri);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof Perpendicular)
			{
				System.out.println("asdf");
			}
		}
		System.out.println("Failed");
		
	}
	
}
