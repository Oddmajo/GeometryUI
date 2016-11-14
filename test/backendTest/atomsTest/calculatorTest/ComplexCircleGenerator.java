package backendTest.atomsTest.calculatorTest;

import java.util.Random;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.Point;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backendTest.atomsTest.calculatorTest.lexicographicPointsTest.LexicographicPointsByY;

/**
 * Creates a PlanarGraph in which the outer shape is a fixed circle.
 * The circle must be centered at (0,0).
 * Generates 1 random point on the left (negative x value) side of the circle
 * and n random points on the right (positive x value)
 * Then draw segments from each random point on the right to the point on the left
 * 
 * The generated graph should produce numPoints + 1 facets.
 * @author Drew W
 *
 */
public class ComplexCircleGenerator
{
    // declarations
    private Circle theCircle;
    
    private Point leftPoint;
    private LexicographicPointsByY rightPoints;
    
    private int numPoints;
    
    private Random generator;
    
    private PlanarGraph theGraph;
    public PlanarGraph getGraph() { return theGraph; }
    

    public ComplexCircleGenerator(Circle c, int n)
    {
        // store the circle and number of points
        theCircle = c;
        numPoints = n;
        
        // instantiate the rightPoints list, generator, and graph
        rightPoints = new LexicographicPointsByY();
        generator = new Random();
        theGraph = new PlanarGraph();
        
        // generate the left point
        generateLeftPoint();
        
        // generate the right points
        generateRightPoints();
        
        //generate the graph
        generatePlanarGraph();
    }
    
    /**
     * Generate the single point on the left half of the graph
     */
    private void generateLeftPoint()
    {
        leftPoint = generateCirclePoint("Left Point", Sign.NEGATIVE);
    }
    
    /**
     * Generate numPoints points on the right half of the graph, add each
     * to the Lexicographic list rightPoints
     */
    private void generateRightPoints()
    {
        //
        // Generate numPoints points and add each to the Lexicographic list
        //
        for (int i = 0; i < numPoints; i++)
        {
            String name = "Right Point " + i;
            Point p = generateCirclePoint(name, Sign.POSITIVE);
            rightPoints.add(p);
        }
    }
    
    /**
     * Randomly generate a point on the circle.  Specify whether the point should
     * be on the left or right half of the circle and the name of the point.
     * @param name  the name of the point
     * @param s     the sign (positive or negative) of the x value (whether the 
     *              point will be on the left or right half of the graph)
     * @return      the generated Point
     */
    private Point generateCirclePoint(String name, Sign s)
    {
        //
        // generate an x value between 0 and positive or negative circle radius
        //
        // first get large random double
        double rand = Math.abs(generator.nextDouble() * generator.nextInt());
        
        // then get the random x value within the bounds
        double xVal;
        
        // if sign should be positive
        if (s == Sign.POSITIVE)
        {
            xVal = (rand % theCircle.getRadius());
        }
        // if the sign should be negative
        else 
        {
            xVal = -( rand % theCircle.getRadius());
        }
        
        
        //
        // choose one of the two possible y values randomly
        //
        int yChoice = generator.nextInt(2);
        double yVal;
        
        // choose the positive y value
        if (yChoice == 0)
        {
            double radSquare = Math.pow(theCircle.getRadius(), 2);
            double xValSquare = Math.pow(xVal, 2);
            yVal = Math.sqrt(radSquare - xValSquare);
        }
        // choose the negative y value
        else
        {
            double radSquare = Math.pow(theCircle.getRadius(), 2);
            double xValSquare = Math.pow(xVal, 2);
            yVal = -(Math.sqrt(radSquare - xValSquare));
        }
        
        // set the left point
         return new Point(name, xVal, yVal);
        
    }
    
    /**
     * Generate the PlanarGraph
     * 
     * First add all of the nodes, then add the edges between them
     */
    private void generatePlanarGraph()
    {
        //
        // First add all Nodes (points)
        // 
        // add the left point
        theGraph.addNode(leftPoint);
        
        // add all rightPoints
        for (int i = 0; i < rightPoints.size(); i++)
        {
            theGraph.addNode(rightPoints.get(i));
        }
        
        //
        // Then add edges between Nodes
        //
        
        // first add arcs to first and last Nodes in rightPoints from leftPoint
        // these are outer circle edges
        // add middle points, so each arc is comprised of two arcs
        
        // top segment
        Point topSegmentMidpoint = theCircle.Midpoint(leftPoint, rightPoints.get(rightPoints.size()-1));
        theGraph.addNode(topSegmentMidpoint);
        theGraph.addUndirectedEdge(leftPoint, topSegmentMidpoint, 0, EdgeType.REAL_ARC);
        theGraph.addUndirectedEdge(topSegmentMidpoint, rightPoints.get(rightPoints.size()-1), 0, EdgeType.REAL_ARC);
        
        // bottom segment
        Point bottomSegmentMidpoint = theCircle.Midpoint(leftPoint, rightPoints.get(0));
        theGraph.addNode(bottomSegmentMidpoint);
        theGraph.addUndirectedEdge(leftPoint, bottomSegmentMidpoint, 0, EdgeType.REAL_ARC);
        theGraph.addUndirectedEdge(bottomSegmentMidpoint, rightPoints.get(0), 0, EdgeType.REAL_ARC);
        
        
        // then add straight edges from the leftPoint to each rightPoint
        for (int i = 0; i < rightPoints.size(); i++)
        {
            theGraph.addUndirectedEdge(leftPoint, rightPoints.get(i), 0, EdgeType.REAL_SEGMENT);
        }
        
        // finally add arcs between neighboring rightPoints
        for (int i = 0; i < rightPoints.size() - 1; i++)
        {
            theGraph.addUndirectedEdge(rightPoints.get(i), rightPoints.get(i + 1), 0, EdgeType.REAL_ARC);
        }
        
    }

}
