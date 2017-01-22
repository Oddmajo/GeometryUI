package backendTest.astTest.figure;

import java.util.Random;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;

/**
 * This class is to construct test graphs in which the equation of a line segment
 * is needed.
 * @author Drew W
 *
 */
public class EquationSegment extends Segment
{
    // The constant "b" for the line equation y = mx + b
    // slope "m" is included in the super class Segment
    private double _constant;
    public double getConstant() { return _constant; };
    
    public EquationSegment(Point p1, Point p2)
    {
        super(p1, p2);
        
        getEquation();
    }
    
    
    public Point getRandomPoint() 
    { 
        return generateRandomPoint(); 
    }
    
    /**
     * Internal function to calculate the constant value
     */
    private void getEquation()
    {
        // if the segment is not a vertical line
        if (!Double.isInfinite(_slope))
        {
            _constant = _slope * -(_point1.getX()) + _point1.getY();
        }
        else
        {
            _constant = 0;
        }
    }
    
    /**
     * Internal function to calculate a random point on the line segment.
     * Generates an x value with the bounds of the two points, then
     * calculates the y value with the equation of the line segment
     * @return  the generated Point
     */
    private Point generateRandomPoint()
    {
        // constant to exclude the lower bound
        final double EPSILON = 0.0000001;
        
        // new random number generator
        Random generator = new Random();
        
        // random multiplier, nextDouble() returns a value between 0 and 1
        double randomMultiplier = Math.abs(generator.nextInt());
        
        // if the segment is not a vertical line
        if (!Double.isInfinite(_slope))
        {
            // ranges for generation, so points are on the segment
            double xBound1 = _point1.getX();
            double xBound2 = _point2.getX();
            
            double xStart;
            double xEnd;
            
            // decide which bound is the start and which is the end
            if (xBound1 > xBound2) { xStart = xBound2; xEnd = xBound1; }
            else { xStart = xBound1; xEnd = xBound2; }
            
            //generate a random x value within the x bounds
            double xDiff = xEnd - xStart;
            double xPoint = ((generator.nextDouble() * randomMultiplier) % xDiff) + xStart + EPSILON;
            if (xPoint > xEnd)
            {
                xPoint -= EPSILON;
            }
            
            // calculate the y value with the x value and the equation
            // y = mx + b
            double yPoint = (_slope * xPoint) + _constant;
            
            // create a new Point and return it
            Point generatedPoint = new Point ("generatedPoint", xPoint, yPoint);
            return generatedPoint;
        }
        else // segment is a vertical line
        {
            // ranges for generation, so points are on the segment
            double yBound1 = _point1.getY();
            double yBound2 = _point2.getY();
            
            double yStart;
            double yEnd;
            
            // decide which bound is the start and which is the end
            if (yBound1 > yBound2) { yStart = yBound2; yEnd = yBound1; }
            else { yStart = yBound1; yEnd = yBound2; }
            
            //generate a random y value within the x bounds
            double yDiff = yEnd - yStart;
            double yPoint = ((generator.nextDouble() * randomMultiplier) % yDiff) + yStart + EPSILON;
            
            // set the x value
            double xPoint = _point1.getX();
            
            // create a new Point and return it
            Point generatedPoint = new Point ("generatedPoint", xPoint, yPoint);
            return generatedPoint;
        }
    }

}
