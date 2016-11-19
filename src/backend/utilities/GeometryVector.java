package backend.utilities;

import backend.ast.figure.components.Point;

public class GeometryVector
{
    // variables
    private double x;
    public double getX() { return x; }
    
    private double y;
    public double getY() { return y; }
    
    private double magnitude;
    public double getMagnitude() { return magnitude; }

    
    /**
     * Create a vector from a Point
     * @param p
     */
    public GeometryVector(Point p)
    {
        // get the x and y values
        x = p.getX();
        y = p.getY();
        
        // calculate magnitude
        magnitude = Math.sqrt( x*x + y*y );
    }
    
    /**
     * Create a vector from another vector
     * @param otherVector
     */
    public GeometryVector(GeometryVector otherVector)
    {
        x = otherVector.getX();
        y = otherVector.getY();
        
        magnitude = otherVector.getMagnitude();
    }
    
    /**
     * Create a vector from an x and y coordinate
     * @param otherX
     * @param otherY
     */
    public GeometryVector(double otherX, double otherY)
    {
        x = otherX;
        y = otherY;
        
        magnitude = Math.sqrt( x*x + y*y );
    }
    
    /**
     * Add another vector to this one, return the new vector
     * @param otherVector
     * @return
     */
    public GeometryVector add(GeometryVector otherVector)
    {
        double newX = x + otherVector.getX();
        double newY = y + otherVector.getY();
        
        return new GeometryVector(newX, newY);
    }
    
    /**
     * Subtract another vector from this one, return the new vector
     * @param otherVector
     * @return
     */
    public GeometryVector subtract(GeometryVector otherVector)
    {
        double newX = x - otherVector.getX();
        double newY = y - otherVector.getY();
        
        return new GeometryVector(newX, newY);
    }
    
    /**
     * Scale this vector by scale
     * @param scale
     * @return
     */
    public void scale(double scale)
    {
        x *= scale;
        y *= scale;
        
        magnitude = Math.sqrt( x*x + y*y );
    }
    
    
    /**
     * Normalize this vector (scale the vector so the magnitude is 1)
     */
    public void normalize() 
    {
        x /= magnitude;
        y /= magnitude;
        
        magnitude = Math.sqrt( x*x + y*y );
    }
    
    /**
     * Return true if other vector equals this vector
     * @param otherVector
     * @return
     */
    public boolean equals(GeometryVector otherVector)
    {
        if (otherVector != null)
        {
            if (x == otherVector.getX() && y == otherVector.getY() && magnitude == otherVector.getMagnitude())
            {
                return true;
            }
        }
        
        return false;
    }

}
