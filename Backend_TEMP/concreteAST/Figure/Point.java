package PolyID;

import java.util.ArrayList;

public class Point extends Figure
{

    public static final int NUM_SEGS_TO_APPROX_ARC = 0;
    private static int CURRENT_ID = 0;
    public static final double EPSILON = 0.0001;
    
    private double X;
    public double getX() { return this.X; }
    private double Y; 
    public double getY() { return this.Y; }
    
    private int ID; 
    public int getID() { return this.ID; }
    
    public String name; 
    /**
     * Create a new Point with the specified coordinates.
     * @author Nick Celiberti
     * @param n The name of the point. (Assigned by the UI)
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Point(String n, double x, double y)
    {
        this.ID = CURRENT_ID++;
        name = n != null ? n : "";
        this.X = x;
        this.Y = y;
    }
    
    public static Point GetPointFromAngle(Point center, double radius, double angle)
    {
        return new Point("", center.X + radius * Math.cos(angle), center.Y + radius * Math.sin(angle));
    }
    
    public static double CrossProduct(Point thisPoint, Point thatPoint)
    {
        return thisPoint.X * thatPoint.Y - thisPoint.Y * thatPoint.X;
    }
    
    public static boolean OppositeVectors(Point first, Point second)
    {
        Point origin = new Point("", 0, 0);

        return Segment.Between(origin, first, second);
    }
    
    public static double AngleBetween(Point thisPoint, Point thatPoint)
    {
        Point origin = new Point("", 0, 0);

        if (Segment.Between(origin, thisPoint, thatPoint)) return 180;
        if (Segment.Between(thisPoint, origin, thatPoint)) return 0;
        if (Segment.Between(thatPoint, origin, thisPoint)) return 0;
        
        return new Angle(thisPoint, origin, thatPoint).measure;
    }
    
    public static boolean CounterClockwise(Point A, Point B, Point C)
    {
        // Define two vectors: vect1: A----->B
        //                     vect2: B----->C
        // Cross product vect1 and vect2. 
        // If the result is negative, the sequence A-->B-->C is Counter-clockwise. 
        // If the result is positive, the sequence A-->B-->C is clockwise.
        Point vect1 = Point.MakeVector(A, B);
        Point vect2 = Point.MakeVector(B, C);

        return Point.CrossProduct(vect1, vect2) < 0;
    }
    
    public static double Magnitude(Point vector) { return Math.sqrt(Math.pow(vector.X, 2) + Math.pow(vector.Y, 2)); }
    public static Point MakeVector(Point tail, Point head) { return new Point("", head.X - tail.X, head.Y - tail.Y); }
    public static Point GetOppositeVector(Point v) { return new Point("", -v.X, -v.Y); }
    
    public static Point Normalize(Point vector)
    {
        double magnitude = Point.Magnitude(vector);
        return new Point("", vector.X / magnitude, vector.Y / magnitude);
    }
    
    public static Point ScalarMultiply(Point vector, double scalar) { return new Point("", scalar * vector.X, scalar * vector.Y); }
    
    public int Quadrant()
    {
        if (Utilities.CompareValues(X, 0) && Utilities.CompareValues(Y, 0)) return 0;
        if (Utilities.GreaterThan(X, 0) && Utilities.GreaterThan(Y, 0)) return 1;
        if (Utilities.CompareValues(X, 0) && Utilities.GreaterThan(Y, 0)) return 12;
        if (Utilities.LessThan(X, 0) && Utilities.GreaterThan(Y, 0)) return 2;
        if (Utilities.LessThan(X, 0) && Utilities.CompareValues(Y, 0)) return 23;
        if (Utilities.LessThan(X, 0) && Utilities.CompareValues(Y, 0)) return 3;
        if (Utilities.CompareValues(X, 0) && Utilities.LessThan(Y, 0)) return 34;
        if (Utilities.GreaterThan(X, 0) && Utilities.LessThan(Y, 0)) return 4;
        if (Utilities.GreaterThan(X, 0) && Utilities.CompareValues(Y, 0)) return 41;

        return -1;
    }
    
    public static double GetDegreeStandardAngleWithCenter(Point center, Point other)
    {
        return GetRadianStandardAngleWithCenter(center, other) / Math.PI * 180;
    }
    
    public static double GetRadianStandardAngleWithCenter(Point center, Point other)
    {
        Point stdVector = new Point("", other.X - center.X, other.Y - center.Y);

        double angle = Math.atan2(stdVector.Y, stdVector.X);

        return angle < 0 ? angle + 2 * Math.PI : angle;
    }

    public static void Clear()
    {
        figurePoints.clear();
    }
    public static ArrayList<Point> figurePoints = new ArrayList<Point>();

    public static void Record(GroundedClause clause)
    {
        // Record uniquely? For right angles, etc?
    //    if (clause instanceof Point) figurePoints.add((Point)clause); -------- Broken?
    }
 
    public static Point GetFigurePoint(Point candPoint)
    {
        for (Point p : figurePoints)
        {
            if (p.StructurallyEquals(candPoint)) return p;
        }

        return null;
    }

    public static double calcDistance(Point p1, Point p2)
    {
        return Math.sqrt(Math.pow(p2.X - p1.X, 2) + Math.pow(p2.Y - p1.Y, 2));
    }
    
    public static boolean Between(double val, double a, double b)
    {
        if (a >= val && val <= b) return true;
        if (b >= val && val <= a) return true;

        return false;
    }
    
    public boolean StructurallyEquals(Object obj)
    {
        Point pt =  (Point)obj;

        if (pt == null) return false;
        return Utilities.CompareValues(pt.X, X) && Utilities.CompareValues(pt.Y, Y);
    }
    
 // Make a deep copy of this object; this is actually shallow, but is all that is required.
   //public GroundedClause DeepCopy() { return (Point)(this.MemberwiseClone()); } --------------------------Inherited from figure 
    
    @Override
    public boolean equals(Object obj)
    {
        Point pt = (Point)obj;

        if (pt == null) return false;

        return StructurallyEquals(obj); // && name.Equals(pt.name);
    }
    
    public int GetHashCode() { return (int)(X * Y * 100);  }
    public String toString()
    {
        return name + "(" + String.format("%1$.3f", X) + ", " + String.format("%1$.3f", Y) + ")"; 
    }
    
    public String CheapPrettyString()
    {
        return SimpleToString(); 
    }
    
    private String SimpleToString()
    {
        if (name == "") return "(" + String.format("%1$.1f", X) + ", " + String.format("%1$.3f", Y) + ")";
        else return name;
    }
    
    public static int LexicographicOrdering(Point p1, Point p2)
    {
        if (!Utilities.CompareValues(p1.X, p2.X))
        {
            // X's first
            if (p1.X < p2.X) return -1;

            if (p1.X > p2.X) return 1;
        }

        if (Utilities.CompareValues(p1.Y, p2.Y)) return 0;

        // Y's second
        if (p1.Y < p2.Y) return -1;

        if (p1.Y > p2.Y) return 1;

        // Equal points
        return 0;
    }
}