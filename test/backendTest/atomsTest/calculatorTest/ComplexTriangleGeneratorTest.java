package backendTest.atomsTest.calculatorTest;

import java.util.HashMap;

import org.junit.Test;

import backend.ast.figure.components.EquationSegment;
import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;

public class ComplexTriangleGeneratorTest
{

    @Test public void segmentsGeneratedPointsSetTest()
    {
        System.out.print("Running segmentsGeneratedPointsSetTest...");
        
        // create three points for the triangle
        Point point1 = new Point("p1", 1, 1);
        Point point2 = new Point("p2", 7, 8);
        Point point3 = new Point("p3", 14, 1);
        
        // create the triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(point1, point2, point3, 3);

        // get segment 1 and 2 points and segments
        LexicographicPoints seg1points = triGen.getS1points();
        LexicographicPoints seg2points = triGen.getS2points();
        
        // print segment 1 points
        //System.out.println("Segment 1 Points: ");
        int i = 0;
        while (!seg1points.isEmpty())
        {
            Point p = seg1points.extractMin();
//            System.out.print("\tpoint " + i + ": " + p.toString());
//            System.out.println(" segment: " + hashS1Points.get(p).toString());
            assert(p.getX() > 7 && p.getX() < 14);
            assert(p.getY() > 1 && p.getY() < 8);
            i++;
        }
        
        // print segment 2 points
        //System.out.println("Segment 2 Points: ");
        i = 0;
        while (!seg2points.isEmpty())
        {
            Point p = seg2points.extractMin();
            //System.out.print("\tpoint " + i + ": " + p.toString());
            //System.out.println(" segment: " + hashS2Points.get(p).toString());
            assert(p.getX() > 1 && p.getX() < 14);
            assert(p.getY() == 1);
            i++;
        }
        
        System.out.println("Done");
    }
    
    @Test public void intersectionCalculatorTest()
    {
        System.out.print("Running intersectionCalculatorTest...");
        
        // create points
        Point p1 = new Point("p1", 0, 4);
        Point p2 = new Point("p2", 4, 0);
        Point p3 = new Point("p3", 0, 1);
        Point p4 = new Point("p4", 5, 3);
        Point p5 = new Point("p5", 3, -1);
        Point p6 = new Point("p6", 3, 5);
        
        // create segments
        EquationSegment s1 = new EquationSegment(p1, p2);
        EquationSegment s2 = new EquationSegment(p3, p4);
        EquationSegment s3 = new EquationSegment(p5, p6);
        
        // calculate intersection points
        Point intersect1 = ComplexTriangleGenerator.getIntersectionOfSegments(s1, s2);
        Point intersect2 = ComplexTriangleGenerator.getIntersectionOfSegments(s1, s3);
        Point intersect3 = ComplexTriangleGenerator.getIntersectionOfSegments(s3, s2);
        
        // make assertions
        assert(intersect1.toString().equals("intersection(2.143, 1.857)"));
        assert(intersect2.toString().equals("intersection(3.000, 1.000)"));
        assert(intersect3.toString().equals("intersection(3.000, 2.200)"));
        
        System.out.println("Done");
    }
    
    @Test public void calculateCrossPointsTest()
    {
        System.out.println("Running calculateCrossPointsTest...");
        
        // create three points for the triangle
        Point point1 = new Point("p1", 1, 1);
        Point point2 = new Point("p2", 7, 8);
        Point point3 = new Point("p3", 14, 1);
        
        // create the triangle generator
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(point1, point2, point3, 3);
        
        // get the hashgraph and the key Points list (seg2Points)
        HashMap<Point, LexicographicPoints> hashgraph = triGen.getHashgraph();
        // get segment 1 and 2 points and segments
        LexicographicPoints seg1points = triGen.getS1points();
        HashMap<Point, EquationSegment> hashS1Points = triGen.getHashS1Points();
        LexicographicPoints seg2points = triGen.getS2points();
        HashMap<Point, EquationSegment> hashS2Points = triGen.getHashS2Points();
        
        // print segment 1 points
        //System.out.println("Segment 1 Points: ");
        for (int i = 0; i < seg1points.size(); i++)
        {
            Point p = seg1points.get(i);
//            System.out.print("\tpoint " + i + ": " + p.toString());
//            System.out.println(" segment: " + hashS1Points.get(p).toString());
            assert(p.getX() > 7 && p.getX() < 14);
            assert(p.getY() > 1 && p.getY() < 8);
        }
        
        // print segment 2 points
        //System.out.println("Segment 2 Points: ");
        for (int i = 0; i < seg2points.size(); i++)
        {
            Point p = seg2points.get(i);
//            System.out.print("\tpoint " + i + ": " + p.toString());
//            System.out.println(" segment: " + hashS2Points.get(p).toString());
            assert(p.getX() > 1 && p.getX() < 14);
            assert(p.getY() == 1);
        }
        
        // print out each LexicographPoints list for each key point
        for (int i = 0; i < seg2points.size(); i++)
        {
            System.out.println("Key Point: " + seg2points.get(i));
            LexicographicPoints currentList = hashgraph.get(seg2points.get(i));
            for (int j = 0; j < currentList.size(); j++)
            {
                System.out.println("\tsegment point: " + currentList.get(j));
            }
        }
        
        System.out.println("Done");
        
    }
    
    @Test public void buildPlanarGraphTest()
    {
        System.out.print("Running buildPlanarGraphTest...");
        
        // create three points for the triangle
        Point point1 = new Point("p1", 1, 1);
        Point point2 = new Point("p2", 7, 8);
        Point point3 = new Point("p3", 14, 1);
        
        // create the triangle generator
        int n = 3;
        ComplexTriangleGenerator triGen = new ComplexTriangleGenerator(point1, point2, point3, n);
        
        // get the graph
        PlanarGraph graph = triGen.getGraph();
        
        // make sure graph is correct
        //System.out.println("graph.count(): " + graph.count());
        assert(graph.count() == ((n*n) + (2*n) + 3));
        
        System.out.println("Done");
    }

}
