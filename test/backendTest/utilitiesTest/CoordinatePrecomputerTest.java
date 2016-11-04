package backendTest.utilitiesTest;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
	
	private ArrayList<GroundedClause> setClauses()
	{
		System.out.print("Setting up Clauses...");
		ArrayList<GroundedClause> clauses = new ArrayList<GroundedClause>();
		Random generator = new Random();
		clauses.add(null);
		ArrayList<Point> pts = new ArrayList<Point>();
		Point pt1 = new Point(new String(""), 0,0);
		Point pt2 = new Point(new String(""),0,1);
		pts.add(pt1);
		pts.add(pt2);
		clauses.add(new Collinear(pts));
		Point pt3 = new Point(new String(""),0,2);
		Segment seg1 = new Segment(pt1,pt3);
		InMiddle in = new InMiddle(pt2,seg1);
		clauses.add(in);
		clauses.add(new MidPoint(in));
		Point pt4 = new Point(new String(""),1,1);
		Segment seg2 = new Segment(pt2,pt4);
		clauses.add(new Perpendicular(new Intersection(pt2,seg1,seg2)));
		clauses.add(new PerpendicularBisector(new Intersection(pt2,seg1,seg2),seg2));
		//clauses.add(new Circle(pt1,1));
		Point pt5 = new Point(new String(""),1,0);
		Segment seg3 = new Segment(pt1,pt2);
		Segment seg4 = new Segment(pt4,pt5);
		Segment seg5 = new Segment(pt5,pt1);
		clauses.add(new Quadrilateral(seg3,seg2,seg4,seg5));
		clauses.add(new Kite(new Quadrilateral(seg3,seg4,seg2,seg5))); 
		clauses.add(new Parallelogram(new Quadrilateral(seg3,seg4,seg2,seg5))); 
		clauses.add(new Rectangle(new Quadrilateral(seg3,seg4,seg2,seg5)));
		clauses.add(new Rhombus(new Quadrilateral(seg3,seg4,seg2,seg5)));
		clauses.add(new Square(new Quadrilateral(seg3,seg4,seg2,seg5)));
		clauses.add(new Trapezoid(new Quadrilateral(seg3,seg4,seg2,seg5)));
		clauses.add(new IsoscelesTrapezoid(new Quadrilateral(seg3,seg4,seg2,seg5)));
		clauses.add(new Triangle(pt1,pt2,pt5));
		clauses.add(new IsoscelesTriangle(new Triangle(pt1,pt2,pt5)));
		//clauses.add(new EquilateralTriangle(new Triangle(pt1, pt5, new Point(new String(""),0,0))));
		clauses.add(new RightTriangle(new Triangle(pt1,pt2,pt5)));
		clauses.add(new Angle(pt2,pt1,pt5));
		clauses.add(new RightAngle(pt2,pt1,pt5));
		clauses.add(seg1);
		clauses.add(new EquationSegment(pt1,pt2));
 		clauses.add(new Parallel(seg3,seg4));
		clauses.add(new AlgebraicParallel(seg3,seg4));
		clauses.add(new Intersection(pt2,seg1,seg2));
		//clauses.add(new MinorArc(new Circle(pt1,1),pt2,pt5));
		//clauses.add(new MajorArc(new Circle(pt1,1),pt2,pt5));
		//clauses.add(new Semicircle(null,null,null,null,null));
		//clauses.add(new Sector(null));
		//clauses.add(new ArcInMiddle(null,null));
		System.out.print("Done setting clauses\n");
		return clauses;
	}
	@Test
	public void filterTest()
	{
		System.out.println("starting Precomputer filter test ...");
		try {
			LoggerFactory.initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<GroundedClause> clauses = setClauses();
		CoordinatePrecomputer compute = new CoordinatePrecomputer(clauses);
		
//		assertEquals(compute.getCircles().size(), 1);
//		assertEquals(compute.getQuadrilaterals().size(), 8);
//		assertEquals(compute.getTriangles().size(), 4);
//		assertEquals(compute.getSegments().size(), 2);
//		//assertEquals(compute.getAngles().size(), 1);//should be 2
//		assertEquals(compute.getCollinear().size(), 1);
//	
//		assertEquals(compute.getInMiddles().size(), 2);
//		assertEquals(compute.getIntersections().size(), 3);
//		assertEquals(compute.getPerpendiculars().size(), 2);
//		assertEquals(compute.getParallels().size(), 2);
//		
//		assertEquals(compute.getMinorArcs().size(), 1);
//		assertEquals(compute.getMajorArcs().size(), 1);
//		assertEquals(compute.getSemiCircles().size(), 1);
//		assertEquals(compute.getSectors().size(), 1);
//		assertEquals(compute.getArcInMiddle().size(), 1);
		System.out.println("Done!");
		LoggerFactory.close();
	}
	
	
	
}
