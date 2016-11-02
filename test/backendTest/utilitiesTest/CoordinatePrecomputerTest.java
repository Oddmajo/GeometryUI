package backendTest.utilitiesTest;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Random;
import backend.ast.GroundedClause;
import backend.ast.Descriptors.AlgebraicParallel;
import backend.ast.Descriptors.Collinear;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.MidPoint;
import backend.ast.Descriptors.Parallel;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.PerpendicularBisector;
import backend.ast.Descriptors.Arcs_and_Circles.ArcInMiddle;
import backend.ast.figure.components.Angle;
import backend.ast.figure.components.Circle;
import backend.ast.figure.components.EquationSegment;
import backend.ast.figure.components.MajorArc;
import backend.ast.figure.components.MinorArc;
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
import backend.ast.figure.components.Semicircle;

import org.junit.Test;
import org.junit.Assert;

public class CoordinatePrecomputerTest 
{	
	
	private ArrayList<GroundedClause> setClauses()
	{
		System.out.print("Setting up Clauses...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		//clauses.add(null);
		//clauses.add(new Collinear(null));
		clauses.add(new InMiddle(null,null));
		clauses.add(new MidPoint(new InMiddle(null,null)));
		clauses.add(new Perpendicular(new Intersection(null,null,null)));
		clauses.add(new PerpendicularBisector(null,null));
		System.out.println("done with 6");
		clauses.add(new Circle(null,0));
		clauses.add(new Quadrilateral(null));
		clauses.add(new Kite(null));
		clauses.add(new Parallelogram(null)); 
		clauses.add(new Rectangle(null));
		clauses.add(new Rhombus(null));
		System.out.println("done with 12");
		clauses.add(new Square(null));
		clauses.add(new Trapezoid(null));
		clauses.add(new IsoscelesTrapezoid(null));
		clauses.add(new Triangle(null));
		clauses.add(new IsoscelesTriangle(null));
		clauses.add(new EquilateralTriangle(null,null,null));
		clauses.add(new RightTriangle(null));
		//clauses.add(new Angle(null));
		//clauses.add(new RightAngle(null));
		clauses.add(new Segment(null,null));
		System.out.println("done with 20");
		clauses.add(new EquationSegment(null,null));
		clauses.add(new Parallel(null,null));
		clauses.add(new AlgebraicParallel(null,null));
		clauses.add(new Intersection(null,null,null));
		clauses.add(new MinorArc(null,null,null));
		System.out.println("done with 25");
		clauses.add(new MajorArc(null,null,null));
		clauses.add(new Semicircle(null,null,null,null,null));
		clauses.add(new Sector(null));
		clauses.add(new ArcInMiddle());
		System.out.print("Done setting clauses\n");
		return clauses;
	}
	@Test
	public void filterTest()
	{
		System.out.println("starting Precomputer filter test ...");
		ArrayList<GroundedClause> clauses = setClauses();
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		
		assertEquals(compute.getCircles().size(), 1);
		assertEquals(compute.getQuadrilaterals().size(), 8);
		assertEquals(compute.getTriangles().size(), 4);
		assertEquals(compute.getSegments().size(), 2);
		//assertEquals(compute.getAngles().size(), 1);//should be 2
		assertEquals(compute.getCollinear().size(), 1);
	
		assertEquals(compute.getInMiddles().size(), 2);
		assertEquals(compute.getIntersections().size(), 3);
		assertEquals(compute.getPerpendiculars().size(), 2);
		assertEquals(compute.getParallels().size(), 2);
		
		assertEquals(compute.getMinorArcs().size(), 1);
		assertEquals(compute.getMajorArcs().size(), 1);
		assertEquals(compute.getSemiCircles().size(), 1);
		assertEquals(compute.getSectors().size(), 1);
		assertEquals(compute.getArcInMiddle().size(), 1);
		System.out.println("Done!");
	}
	
	
}
