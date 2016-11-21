package backendTest.atomsTest.calculatorTest;

import java.util.HashMap;
import java.util.Random;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.EquationSegment;
import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backend.utilities.GeometryVector;
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
    
    // horizontal segments
    private Point leftPoint;
    private LexicographicPointsByY rightPoints;
    private HashMap<Point, EquationSegment> rightSegs;
    
    //vertical segments
    private Point topPoint;
    private LexicographicPoints bottomPoints;
    private HashMap<Point, EquationSegment> bottomSegs;
    
    // the number of points to generate on each "side"
    // of the circle
    private int numPoints;
    
    private Random generator;
    
    // graph 
    private PlanarGraph theGraph;
    public PlanarGraph getGraph() { return theGraph; }
    private HashMap<Point, LexicographicPoints> hashgraph;
    

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
        generateRightPointsAndSegments();
        
        // create the top point
        topPoint = new Point("top point", 0, c.getRadius());
        
        // generate the bottom points
        generateBottomPointsAndSegments();
        
        // find all cross-points
        calculateCrossPoints();
        
        //generate the graph
        // this needs to be rewritten
        generatePlanarGraph();
    }
    
    /**
     * Generate the single point on the left half of the graph
     */
    private void generateLeftPoint()
    {
        // randomly choose positive or negative y value
        int yChoice = generator.nextInt(2);
        Sign ySign;
        if (yChoice == 0) { ySign = Sign.POSITIVE; }
        else { ySign = Sign.NEGATIVE; }
        
        // generate the point
        leftPoint = generateCirclePoint("Left Point", Sign.NEGATIVE, ySign);
    }
    
    /**
     * Generate numPoints points on the right half of the graph, add each
     * to the Lexicographic list rightPoints
     */
    private void generateRightPointsAndSegments()
    {
        //
        // Generate numPoints points and add each to the Lexicographic list
        // Create a segement from leftPoint to each generated point and add
        // the generated point-segment pair to rightSegments HashMap
        //
        for (int i = 0; i < numPoints; i++)
        {
            String name = "Right Point " + i;
            // randomly choose positive or negative y value
            int yChoice = generator.nextInt(2);
            Sign ySign;
            if (yChoice == 0) { ySign = Sign.POSITIVE; }
            else { ySign = Sign.NEGATIVE; }
            
            // generate the Point
            Point p = generateCirclePoint(name, Sign.POSITIVE, ySign);
            
            // add the point to the list and create the segment
            rightPoints.add(p);
            EquationSegment s = new EquationSegment(leftPoint, p);
            rightSegs.put(p, s);
        }
    }
    
    /**
     * Generate numPoints points on the bottom half of the circle between
     * LeftPoint and rightPoints[0].  Add each to the Lexicographic list and
     * create a segment between topPoint and the generated list
     */
    private void generateBottomPointsAndSegments()
    {
        //
        // Generate numPoints points and add each to the Lexicographic list
        // Create a segement from topPoint to each generated point and add
        // the generated point-segment pair to bottomSegments HashMap
        //
        for (int i = 0; i < numPoints; i++)
        {
            String name = "Bottom Point " + i;
            // randomly choose positive or negative x value
            int xChoice = generator.nextInt(2);
            Sign xSign;
            if (xChoice == 0) { xSign = Sign.POSITIVE; }
            else { xSign = Sign.NEGATIVE; }
            
            // generate a point between leftPoint.x and rightPoints[0].x
            Point p = generateCirclePoint(name, xSign, Sign.NEGATIVE);
            while (Double.compare(p.getX(), leftPoint.getX()) < 0 || Double.compare(p.getX(), rightPoints.get(0).getX()) > 0)
            {
                p = generateCirclePoint(name, xSign, Sign.NEGATIVE);
            }
            
            // add the point to the list and create the segment
            bottomPoints.add(p);
            EquationSegment s = new EquationSegment(topPoint, p);
            bottomSegs.put(p, s);
        }
    }
    
    private void calculateCrossPoints()
    {
     // for each point in s2points
        for (int i = 0; i < bottomPoints.size(); i++)
        {
            // create a new lexicographic list to hold each cross point
            // for this segment associated with this key Point
            LexicographicPoints lexPoints = new LexicographicPoints();
            
            // get the keyPoint and its associated segment
            Point keyPoint = bottomPoints.get(i);
            EquationSegment keySeg = bottomSegs.get(keyPoint);
            
            // for each point is s1points
            for (int j = 0; j < rightPoints.size(); j++)
            {
                // get the "cross" point and its associated segment
                Point crossPoint = rightPoints.get(j);
                EquationSegment crossSeg = rightSegs.get(crossPoint);
                
                // calculate the intersection between the key segment and
                // the "cross" segment
                Point calcPoint = ComplexTriangleGenerator.getIntersectionOfSegments(keySeg, crossSeg);
                
                // add the calculated point to the lexicographic list for this keyPoint
                lexPoints.add(calcPoint);
            }
            
            // add the keyPoint - lexPoints pair to the HashMap hashgraph
            hashgraph.put(keyPoint, lexPoints);
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
    private Point generateCirclePoint(String name, Sign leftright, Sign topbottom)
    {
        //
        // generate an x value between 0 and positive or negative circle radius
        //
        // first get large random double
        double rand = Math.abs(generator.nextDouble() * generator.nextInt());
        
        // then get the random x value within the bounds
        double xVal;
        
        // if sign should be positive
        if (leftright == Sign.POSITIVE)
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
        double yVal;
        
        // choose the positive y value
        if (topbottom == Sign.POSITIVE)
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
     * This needs to be re-written for the cross-hatch version.
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
        //Point topSegmentMidpoint = theCircle.Midpoint(leftPoint, rightPoints.get(rightPoints.size()-1));
        Point topSegmentMidpoint = ComplexCircleGenerator.vectorArcMidpoint(theCircle, leftPoint, rightPoints.get(rightPoints.size()-1));
        theGraph.addNode(topSegmentMidpoint);
        theGraph.addUndirectedEdge(leftPoint, topSegmentMidpoint, 0, EdgeType.REAL_ARC);
        theGraph.addUndirectedEdge(topSegmentMidpoint, rightPoints.get(rightPoints.size()-1), 0, EdgeType.REAL_ARC);
        
        // bottom segment
        //Point bottomSegmentMidpoint = theCircle.Midpoint(leftPoint, rightPoints.get(0));
        Point bottomSegmentMidpoint = ComplexCircleGenerator.vectorArcMidpoint(theCircle, leftPoint, rightPoints.get(0));
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
    
    public static Point vectorArcMidpoint(Circle c, Point p1, Point p2)
    {
        // get vectors for the two given points
        GeometryVector vec1 = new GeometryVector(p1);
        GeometryVector vec2 = new GeometryVector(p2);
        
        // get a normalized midpoint vector
        GeometryVector midpoint = vec1.add(vec2);
        midpoint.scale(0.5);
        midpoint.normalize();
        
        // put the vector on the circle
        midpoint.scale(c.getRadius());
        
        // return the point
        return new Point("arc midpoint", midpoint.getX(), midpoint.getY());
        
    }

}
