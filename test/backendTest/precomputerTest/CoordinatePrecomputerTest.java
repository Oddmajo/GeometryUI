package backendTest.precomputerTest;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.AlgebraicParallel;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Midpoint;
import backend.ast.Descriptors.Parallel;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.SegmentBisector;
import backend.ast.Descriptors.Strengthened;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.CongruentSegments;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalAngles;
import backend.ast.Descriptors.Relations.Proportionalities.SegmentRatio;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.quadrilaterals.IsoscelesTrapezoid;
import backend.ast.figure.components.quadrilaterals.Kite;
import backend.ast.figure.components.quadrilaterals.Parallelogram;
import backend.ast.figure.components.quadrilaterals.Quadrilateral;
import backend.ast.figure.components.quadrilaterals.Rectangle;
import backend.ast.figure.components.quadrilaterals.Rhombus;
import backend.ast.figure.components.quadrilaterals.Square;
import backend.ast.figure.components.quadrilaterals.Trapezoid;
import backend.ast.figure.components.triangles.EquilateralTriangle;
import backend.ast.figure.components.triangles.IsoscelesTriangle;
import backend.ast.figure.components.triangles.RightTriangle;
import backend.ast.figure.components.triangles.Triangle;
import backend.precomputer.CoordinatePrecomputer;
import backend.utilities.logger.LoggerFactory;
import backendTest.astTest.figure.EquationSegment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.angles.RightAngle;
import backend.ast.figure.components.arcs.MajorArc;
import backend.ast.figure.components.arcs.MinorArc;
import backend.ast.figure.components.arcs.Semicircle;

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
			if(d instanceof Parallel)
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
			if(d instanceof Parallel)
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
			if(d instanceof Parallel)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
		
	}
	
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
			if(d instanceof CongruentSegments)
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
			if(d instanceof CongruentSegments)
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
			if(d instanceof Perpendicular)
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
			if(d instanceof Perpendicular)
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
			if(d instanceof PerpendicularBisector)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	//This test fails when it is a Perpendicular Bisector because PerpBisector inheirts from Perp 
	@Test
	public void simpleSegmentBisector()
	{
		System.out.print("Starting Precomputer Simple SegmentBisector test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,10));
		Segment seg2 = new Segment(new Point("",0,5), new Point("",-6,7));
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
			if(d instanceof SegmentBisector)
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
			if(d instanceof SegmentRatio)
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
		System.out.print("Starting Precomputer Right Triangle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Triangle tri = new Triangle(new Point("",0,0), new Point("",1,0), new Point("",0,1));
		clauses.add(tri);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		compute.CalculateStrengthening();
		if(compute.GetStrengthenedClauses().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Strengthened d : compute.GetStrengthenedClauses())
		{
			if(d.getStrengthened() instanceof RightTriangle)
			{
				System.out.println("Passed");
				return;
			}
			
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void equilateralTriangle()
	{
		System.out.print("Starting Precomputer Equilateral Triangle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Triangle tri = new Triangle(new Point("",0,0), new Point("",6,0), new Point("",3,3*Math.sqrt(3)));
		clauses.add(tri);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		compute.CalculateStrengthening();
		if(compute.GetStrengthenedClauses().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Strengthened d : compute.GetStrengthenedClauses())
		{
			if(d.getStrengthened() instanceof EquilateralTriangle)
			{
				System.out.println("Passed");
				return;
			}
			
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void isoscelesTriangle()
	{
		System.out.print("Starting Precomputer Isosceles Triangle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Triangle tri = new Triangle(new Point("",2,6), new Point("",8,2), new Point("",11,13));
		clauses.add(tri);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		compute.CalculateStrengthening();
		if(compute.GetStrengthenedClauses().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Strengthened d : compute.GetStrengthenedClauses())
		{
			if(d.getStrengthened() instanceof IsoscelesTriangle)
			{
				System.out.println("Passed");
				return;
			}
			
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleCongruentAngles()
	{
		System.out.print("Starting Precompute Simple Congruent Angle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,1));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",1,0));
		Segment seg3 = new Segment(new Point("",2,2), new Point("",2,3));
		Segment seg4 = new Segment(new Point("",2,2), new Point("",3,2));
		Angle ang1 = new Angle(seg1,seg2);
		Angle ang2 = new Angle(seg3,seg4);
		clauses.add(ang1);
		clauses.add(ang2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof CongruentAngles)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
		
	}
	
	@Test
	public void simpleSuppAngles()
	{
		System.out.print("Starting Precompute Simple Supplementary Angle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",0,1));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",1,0));
		Segment seg3 = new Segment(new Point("",0,0), new Point("",0,1));
		Segment seg4 = new Segment(new Point("",0,0), new Point("",-1,0));
		Angle ang1 = new Angle(seg1,seg2);
		Angle ang2 = new Angle(seg3,seg4);
		clauses.add(ang1);
		clauses.add(ang2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof Supplementary)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void simpleCompAngles()
	{
		System.out.print("Starting Precomputer Simple Complementary Angle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",1,0));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",1,1));
		Segment seg3 = new Segment(new Point("",0,0), new Point("",1,1));
		Segment seg4 = new Segment(new Point("",0,0), new Point("",0,1));
		Angle ang1 = new Angle(seg1,seg2);
		Angle ang2 = new Angle(seg3,seg4);
		clauses.add(ang1);
		clauses.add(ang2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof Complementary)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	@Test
	public void propAngles()
	{
		System.out.print("Starting Precomputer Proportional Angle test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0), new Point("",1,0));
		Segment seg2 = new Segment(new Point("",0,0), new Point("",1,1));
		Segment seg3 = new Segment(new Point("",0,0), new Point("",1,0));
		Segment seg4 = new Segment(new Point("",0,0), new Point("",0,1));
		Angle ang1 = new Angle(seg1,seg2);
		Angle ang2 = new Angle(seg3,seg4);
		clauses.add(ang1);
		clauses.add(ang2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() == 0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof ProportionalAngles)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}

	//curently fails cause triangle.getAngles is returning null
	@Test
	public void congruentTriangles()
	{
		System.out.print("Starting Precomputer Congruent Triangles test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Triangle t1 = new Triangle(new Point("",0,0), new Point("",1,1), new Point("",1,0));
		Triangle t2 = new Triangle(new Point("",2,0), new Point("",1,1), new Point("",3,1));
		clauses.add(t1);
		clauses.add(t2);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() ==0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d: compute.GetPrecomputedRelations())
		{
			if(d instanceof CongruentTriangles)
			{
				System.out.println("Passed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}
	
	//incomplete until the above test is also fixed
	@Test
	public void similarTriangles()
	{
		
	}
	
	@Test
	public void angleBisector()
	{
		System.out.print("Starting Precomputer angleBisector test...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Segment seg1 = new Segment(new Point("",0,0),new Point("",1,1));
		Segment seg2 = new Segment(new Point("",0,0),new Point("",1,0));
		Segment seg3 = new Segment(new Point("",0,0),new Point("",0,1));
		Angle ang1 = new Angle(seg2,seg3); 
		clauses.add(seg1);
		clauses.add(ang1);
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		compute.CalculateRelations();
		if(compute.GetPrecomputedRelations().size() ==0)
		{
			System.out.println("Failed");
			assert(false);
		}
		for(Descriptor d : compute.GetPrecomputedRelations())
		{
			if(d instanceof AngleBisector)
			{
				System.out.println("Passsed");
				return;
			}
		}
		System.out.println("Failed");
		assert(false);
	}

}
