package backendTest.atomsTest.calculatorTest;

import java.util.HashMap;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;
import backend.atoms.undirectedPlanarGraph.EdgeType;
import backend.atoms.undirectedPlanarGraph.PlanarGraph;
import backendTest.astTest.figure.EquationSegment;

/**
 * Generates and constructs a PlanarGraph with (n+1)(n+1) facets
 * with randomly generated facets
 * @author Drew W
 *
 */
public class ComplexTriangleGenerator
{
    // number of points to generate on each of the two segments
    private final int numPoints;
    
    // Points and Segments to make the initial triangle
    private Point point1;
    private Point point2;
    private Point point3;
    private EquationSegment segment1;
    private EquationSegment segment2;
    
    // lexicographic lists to keep track of the generated points
    private LexicographicPoints s1points;
    public LexicographicPoints getS1points() { return s1points; }
    private LexicographicPoints s2points;
    public LexicographicPoints getS2points() { return s2points; }
    
    // containers to hold all of the cross segments
    private HashMap<Point, EquationSegment> hashS1points;
    public HashMap<Point, EquationSegment> getHashS1Points() { return hashS1points; }
    private HashMap<Point, EquationSegment> hashS2points;
    public HashMap<Point, EquationSegment> getHashS2Points() { return hashS2points; }
    
    // HashMap to store the graph points
    // each key is a generated points along segment2
    // each value is a lexicographic list of points along the segment
    // from point2 to the key point
    private HashMap<Point, LexicographicPoints> hashgraph;
    public HashMap<Point, LexicographicPoints> getHashgraph() { return hashgraph; }
    
    // PlanarGraph that is generated
    private PlanarGraph graph;
    public PlanarGraph getGraph() { return graph; }

    /**
     * Takes three points with which to construct a triangle, generates
     * (n+1)(n+1) facets
     * @param p1    The first triangle point
     * @param p2    The second triangle point
     * @param p3    The third triangle point
     * @param n     The number of points to generate on each segment
     */
    public ComplexTriangleGenerator(Point p1, Point p2, Point p3, int n)
    {
        // initialize vars
        s1points = new LexicographicPoints();
        s2points = new LexicographicPoints();
        hashS1points = new HashMap<Point, EquationSegment>();
        hashS2points = new HashMap<Point, EquationSegment>();
        hashgraph = new HashMap<Point, LexicographicPoints>();
        graph = new PlanarGraph();
        
        // get the number of points
        numPoints = n;
        
        // Construct the triangle
        point1 = p1;
        point2 = p2;
        point3 = p3;
        
        // Note: segment 1 is across from point1, segment2 is across
        //       from point2, etc.
        segment1 = new EquationSegment(point2, point3);
        segment2 = new EquationSegment(point1, point3);
        
        // generate the first set of segments
        generateFirstSegmentSet();
        
        // generate the second set of segments which
        // cross the first set of segments
        generateSecondSegmentSet();
        
        // calculate the points where all of the segments cross
        calculateCrossPoints();
        
        // generate the PlanarGraph
        buildPlanarGraph();
    }
    
    /**
     * Generates the segments from point1 to the randomly generated points
     * on segment1 (which is across from point1),adds the points to the sorted
     * segment1 list, and  adds the point-segment 
     * pairs to the segment1 HashMap 
     */
    private void generateFirstSegmentSet()
    {
        // generate n points
        for (int i = 0; i < numPoints; i++)
        {
            // generate a new point and segment from point1 to the new point
            Point newPoint = segment1.getRandomPoint();
            EquationSegment newSeg = new EquationSegment(point1, newPoint);
            
            // add the point to the lexicographic list 
            s1points.add(newPoint);
            
            // add the point-segment pair to the segment1 points HashMap
            hashS1points.put(newPoint, newSeg);
        }
    }
    
    /**
     * Generates the segments from point2 to the randomly generated points
     * on segment2 (which is across from point2),adds the points to the sorted
     * segment2 list, and  adds the point-segment 
     * pairs to the segment2 HashMap 
     */
    private void generateSecondSegmentSet()
    {
        // generate n points
        for (int i = 0; i < numPoints; i++)
        {
            // generate a new point and segment from point2 to the new point
            Point newPoint = segment2.getRandomPoint();
            EquationSegment newSeg = new EquationSegment(point2, newPoint);
            
            // add the point to the lexicrographic list
            s2points.add(newPoint);
            
            // add the point-segment pair to the segment2 points HashMap
            hashS2points.put(newPoint, newSeg);
        }
    }
    
    /**
     * Calculates the points where all of the generated segments cross
     */
    private void calculateCrossPoints()
    {
        // for each point in s2points
        for (int i = 0; i < s2points.size(); i++)
        {
            // create a new lexicographic list to hold each cross point
            // for this segment associated with this key Point
            LexicographicPoints lexPoints = new LexicographicPoints();
            
            // get the keyPoint and its associated segment
            Point keyPoint = s2points.get(i);
            EquationSegment keySeg = hashS2points.get(keyPoint);
            
            // for each point is s1points
            for (int j = 0; j < s1points.size(); j++)
            {
                // get the "cross" point and its associated segment
                Point crossPoint = s1points.get(j);
                EquationSegment crossSeg = hashS1points.get(crossPoint);
                
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
     * Generates a PlanarGraph from the triangle and all of the crossing
     * segments that were generated
     */
    private void buildPlanarGraph()
    {              
        // add the outer points of the triangle and the point1-point2 edge
        graph.addNode(point1);
        graph.addNode(point2);
        graph.addNode(point3);
        graph.addUndirectedEdge(point1, point2, 0, EdgeType.REAL_SEGMENT);
        
        //
        // add the nodes from the lexicographic lists
        //
        
        // add s1points and s2points
        for (int i = 0; i < s1points.size(); i++)
        {
            graph.addNode(s1points.get(i));
            graph.addNode(s2points.get(i));
            
            // add all inner points
            LexicographicPoints nodeList = hashgraph.get(s2points.get(i));
            for (int k = 0; k < nodeList.size(); k++)
            {
                graph.addNode(nodeList.get(k));
            }
        }
        
        
        //
        // Add edges
        // first go through p1 - p2 edge and first hashgraph segment (left-most)
        //
        
        // these facets are triangles instead of quadrilaterals
        int keyCount = 0;
        Point currentKey = s2points.get(keyCount);
        LexicographicPoints currentList = hashgraph.get(currentKey);
        for (int i = 0; i < currentList.size(); i++)
        {
            graph.addUndirectedEdge(point1, currentList.get(i), 0, EdgeType.REAL_SEGMENT);
        }
        // add the edge from point1 to the current Key
        graph.addUndirectedEdge(point1, currentKey, 0, EdgeType.REAL_SEGMENT);
        
        //
        // add all edges that interconnect the inner points in the triangle
        //
        LexicographicPoints nextList = hashgraph.get(s2points.get(keyCount));
        Point nextKey = s2points.get(keyCount);
        for ( ; keyCount < s2points.size() - 1; keyCount++)
        {
            // increment current and next lists
            currentList = nextList;
            currentKey = nextKey;
            nextList = hashgraph.get(s2points.get(keyCount+1));
            nextKey = s2points.get(keyCount+1);
            
            // add the edges
            
            // if both keys' x values are <= than the point2 x value
            if (currentKey.getX() <= point2.getX() && nextKey.getX() <= point2.getX())
            {
                graph.addUndirectedEdge(currentKey, nextKey, 0, EdgeType.REAL_SEGMENT);
                graph.addUndirectedEdge(currentKey, currentList.get(0), 0, EdgeType.REAL_SEGMENT);
                for (int i = 0; i < currentList.size(); i++)
                {
                    graph.addUndirectedEdge(currentList.get(i), nextList.get(i), 0, EdgeType.REAL_SEGMENT);
                    if (i != currentList.size() - 1)
                    {
                        graph.addUndirectedEdge(currentList.get(i), currentList.get(i+1), 0, EdgeType.REAL_SEGMENT);
                    }
                    else
                    {
                        graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                    }
                }
            }
            
            // only the current key value is <= point2 x value
            else if (currentKey.getX() <= point2.getX())
            {
                graph.addUndirectedEdge(currentKey, nextKey, 0, EdgeType.REAL_SEGMENT);
                graph.addUndirectedEdge(currentKey, currentList.get(0), 0, EdgeType.REAL_SEGMENT);
                for (int i = 0; i < currentList.size(); i++)
                {
                    graph.addUndirectedEdge(currentList.get(i), nextList.get(nextList.size() - (i+1)), 0, EdgeType.REAL_SEGMENT);
                    if (i != currentList.size() - 1)
                    {
                        graph.addUndirectedEdge(currentList.get(i), currentList.get(i+1), 0, EdgeType.REAL_SEGMENT);
                    }
                    else
                    {
                        graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                    }
                }
            }
            
            // neither keys' x values are <= point2 x value
            else
            {
                graph.addUndirectedEdge(currentKey, nextKey, 0, EdgeType.REAL_SEGMENT);
                graph.addUndirectedEdge(currentKey, currentList.get(currentList.size() - 1), 0, EdgeType.REAL_SEGMENT);
                for (int i = currentList.size() - 1; i >= 0; i--)
                {
                    graph.addUndirectedEdge(currentList.get(i), nextList.get(i), 0, EdgeType.REAL_SEGMENT);
                    if (i != 0)
                    {
                        graph.addUndirectedEdge(currentList.get(i), currentList.get(i-1), 0, EdgeType.REAL_SEGMENT);
                    }
                    else
                    {
                        graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                    }
                }
            }
        }
        
        //
        // add the edges from the right-most inner Lexicographic Point list to the s1points
        //
        
        // increment current lists
        currentList = nextList;
        currentKey = nextKey;
        
        // add edges depending on orientation
        if (currentKey.getX() <= point2.getX() && point3.getX() <= point2.getX())
        {
            graph.addUndirectedEdge(currentKey, point3, 0, EdgeType.REAL_SEGMENT);
            graph.addUndirectedEdge(currentKey, currentList.get(0), 0, EdgeType.REAL_SEGMENT);
            for (int i = 0; i < currentList.size(); i++)
            {
                graph.addUndirectedEdge(currentList.get(i), s1points.get(i), 0, EdgeType.REAL_SEGMENT);
                if (i != currentList.size() - 1)
                {
                    graph.addUndirectedEdge(currentList.get(i), currentList.get(i+1), 0, EdgeType.REAL_SEGMENT);
                }
                else
                {
                    graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                }
            }
        }
        
        // only the current key value is <= point2 x value
        else if (currentKey.getX() <= point2.getX())
        {
            graph.addUndirectedEdge(currentKey, point3, 0, EdgeType.REAL_SEGMENT);
            graph.addUndirectedEdge(currentKey, currentList.get(0), 0, EdgeType.REAL_SEGMENT);
            for (int i = 0; i < currentList.size(); i++)
            {
                graph.addUndirectedEdge(currentList.get(i), s1points.get(nextList.size() - (i+1)), 0, EdgeType.REAL_SEGMENT);
                if (i != currentList.size() - 1)
                {
                    graph.addUndirectedEdge(currentList.get(i), currentList.get(i+1), 0, EdgeType.REAL_SEGMENT);
                }
                else
                {
                    graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                }
            }
        }
        
        // neither keys' x values are <= point2 x value
        else
        {
            graph.addUndirectedEdge(currentKey, point3, 0, EdgeType.REAL_SEGMENT);
            graph.addUndirectedEdge(currentKey, currentList.get(currentList.size() - 1), 0, EdgeType.REAL_SEGMENT);
            for (int i = currentList.size() - 1; i >= 0; i--)
            {
                graph.addUndirectedEdge(currentList.get(i), s1points.get(i), 0, EdgeType.REAL_SEGMENT);
                if (i != 0)
                {
                    graph.addUndirectedEdge(currentList.get(i), currentList.get(i-1), 0, EdgeType.REAL_SEGMENT);
                }
                else
                {
                    graph.addUndirectedEdge(currentList.get(i), point2, 0, EdgeType.REAL_SEGMENT);
                }
            }
        }
        
        //
        // add the edges between point2, s1points, and point3
        //
        if (point3.getX() <= point2.getX())
        {
            graph.addUndirectedEdge(point3, s1points.get(0), 0, EdgeType.REAL_SEGMENT);
            for (int i = 0; i < s1points.size() - 2; i++)
            {
                graph.addUndirectedEdge(s1points.get(i), s1points.get(i+1), 0, EdgeType.REAL_SEGMENT);
            }
            graph.addUndirectedEdge(s1points.get(s1points.size() - 1), point2, 0, EdgeType.REAL_SEGMENT);
        }
        else
        {
            graph.addUndirectedEdge(point2, s1points.get(0), 0, EdgeType.REAL_SEGMENT);
            for (int i = 0; i < s1points.size() - 1; i++)
            {
                graph.addUndirectedEdge(s1points.get(i), s1points.get(i+1), 0, EdgeType.REAL_SEGMENT);
            }
            graph.addUndirectedEdge(s1points.get(s1points.size() - 1), point3, 0, EdgeType.REAL_SEGMENT);
        }
    }
    
    /**
     * Calculates the intersection point between two given segments
     * @param s1    the first segment
     * @param s2    the second segment
     * @return      the intersection Point
     */
    public static Point getIntersectionOfSegments(EquationSegment s1, EquationSegment s2)
    {
        // declare variables
        double slopeVar;
        double constVar;
        double xVal;
        double yVal;
        
        // s1 is a vertical line segment
        if (Double.isInfinite(s1.slope()))
        {
            // get the x value from the vertical line s1
            xVal = s1.getPoint1().getX();
            
            // find the y value
            yVal = s2.slope() * xVal + s2.getConstant();
        }
        // s2 is a vertical line segment
        else if (Double.isInfinite(s2.slope()))
        {
            // get the x value from the vertical line s2
            xVal = s2.getPoint1().getX();
            
            // find the y value
            yVal = s1.slope() * xVal + s1.getConstant();
        }
        // neither lines are vertical line segments
        else
        {
            // find the x value
            slopeVar = s2.slope() - s1.slope();
            constVar = s1.getConstant() - s2.getConstant();
            xVal = constVar / slopeVar;
            
            // find the y value
            yVal= s1.slope() * xVal + s1.getConstant();
            
        }
        
        // create the point and return it
        return new Point("intersection", xVal, yVal);
    }

}
