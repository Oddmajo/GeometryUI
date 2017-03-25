/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package backend.ast.figure.components;

import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.ast.figure.Figure;
import backend.ast.figure.components.angles.Angle;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.math.MathUtilities;

/**
 * A 2D Point (x, y) only
 * Points are ordered lexicographically (thus implementing the Comparable interface)
 * 
 * @author Nick Celiberti
 * @author C. Alvin
 */
public class Point extends Figure implements Comparable
{
    public static final int NUM_SEGS_TO_APPROX_ARC = 0;
    private static int CURRENT_ID = 0;
    public static final double EPSILON = 0.0001;
    public static final Point ORIGIN;

    static
    {
        ORIGIN = new Point("origin", 0, 0);
    }

    private double X;
    public double getX() { return this.X; }
    private double Y; 
    public double getY() { return this.Y; }

    private int ID; 
    /**
     * Get the unique identifier for this point.
     * @return 
     */
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

    /**
     * Expects a radian angle measurement
     * @param center Center
     * @param radius Radius
     * @param angle Angle
     * @return Point
     */
    public static Point GetPointFromAngle(Point center, double radius, double angle)
    {
        return new Point("", center.X + radius * Math.cos(angle), center.Y + radius * Math.sin(angle));
    }

    /**
     * Assumes our points represent vectors in standard position.
     * @param thisPoint 
     * @param thatPoint
     * @return Cross Product
     */
    public static double CrossProduct(Point thisPoint, Point thatPoint)
    {
        return thisPoint.X * thatPoint.Y - thisPoint.Y * thatPoint.X;
    }

    /**
     * Assumes our points represent vectors in standard position.
     * @param first
     * @param second
     * @return
     */
    public static boolean OppositeVectors(Point first, Point second)
    {
        Point origin = new Point("", 0, 0);

        return Segment.Between(origin, first, second);
    }

    /**
     * Angle measure (in degrees) between two vectors in standard position.
     * @param thisPoint
     * @param thatPoint
     * @return Angle measure (in degrees) between two vectors in standard position.
     */
    public static double AngleBetween(Point thisPoint, Point thatPoint)
    {
        Point origin = new Point("", 0, 0);

        if (Segment.Between(origin, thisPoint, thatPoint)) return 180;
        if (Segment.Between(thisPoint, origin, thatPoint)) return 0;
        if (Segment.Between(thatPoint, origin, thisPoint)) return 0;

        return new Angle(thisPoint, origin, thatPoint).getMeasure();
    }

    /**
     * 
     * @param A Point A
     * @param B Point B
     * @param C Point C
     * @return True if angle is Counter-Clockwise : False if angle is Clockwise.
     */
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

    /**
     * Calculates the magnitude.
     * @param vector 
     * @return Magnitude of given vector
     */
    public static double Magnitude(Point vector) { return Math.sqrt(Math.pow(vector.X, 2) + Math.pow(vector.Y, 2)); }
    /**
     * Creates a vector
     * @param tail 
     * @param head
     * @return A point representing the vector
     */
    public static Point MakeVector(Point tail, Point head) { return new Point("", head.X - tail.X, head.Y - tail.Y); }
    /**
     * Finds the opposite vector
     * @param v vector
     * @return Opposite vector of v
     */
    public static Point GetOppositeVector(Point v) { return new Point("", -v.X, -v.Y); }

    /**
     * Normalize a vector
     * @param vector assumed unnormalized vector
     * @return Normalized vector
     */
    public static Point Normalize(Point vector)
    {
        double magnitude = Point.Magnitude(vector);
        return new Point("", vector.X / magnitude, vector.Y / magnitude);
    }

    /**
     * Scalar Multiply a vector
     * @param vector 
     * @param scalar
     * @return vector that has been multiplied.
     */
    public static Point ScalarMultiply(Point vector, double scalar) { return new Point("", scalar * vector.X, scalar * vector.Y); }

    //    // CTA: Used? NC: Not that I'm aware of
    //    public int Quadrant()
    //    {
    //        if (backend.utilities.math.Utilities.doubleEquals(X, 0) && backend.utilities.math.Utilities.doubleEquals(Y, 0)) return 0;
    //        if (backend.utilities.math.Utilities.greaterThan(X, 0) && backend.utilities.math.Utilities.greaterThan(Y, 0)) return 1;
    //        if (backend.utilities.math.Utilities.doubleEquals(X, 0) && backend.utilities.math.Utilities.greaterThan(Y, 0)) return 12;
    //        if (backend.utilities.math.Utilities.lessThan(X, 0) && backend.utilities.math.Utilities.greaterThan(Y, 0)) return 2;
    //        if (backend.utilities.math.Utilities.lessThan(X, 0) && backend.utilities.math.Utilities.doubleEquals(Y, 0)) return 23;
    //        if (backend.utilities.math.Utilities.lessThan(X, 0) && backend.utilities.math.Utilities.doubleEquals(Y, 0)) return 3;
    //        if (backend.utilities.math.Utilities.doubleEquals(X, 0) && backend.utilities.math.Utilities.lessThan(Y, 0)) return 34;
    //        if (backend.utilities.math.Utilities.greaterThan(X, 0) && backend.utilities.math.Utilities.lessThan(Y, 0)) return 4;
    //        if (backend.utilities.math.Utilities.greaterThan(X, 0) && backend.utilities.math.Utilities.doubleEquals(Y, 0)) return 41;
    //
    //        return -1;
    //    }

    /**
     * Returns a degree angle measurement between [0, 360]. 
     * @param center
     * @param other
     * @return degree angle measurement between [0, 360]
     */
    public static double GetDegreeStandardAngleWithCenter(Point center, Point other)
    {
        return GetRadianStandardAngleWithCenter(center, other) / Math.PI * 180;
    }

    /**
     * Returns a radian angle measurement between [0, 2PI]. 
     * @param center
     * @param other
     * @return Radian angle measurement between [0, 2PI]. 
     */
    public static double GetRadianStandardAngleWithCenter(Point center, Point other)
    {
        Point stdVector = new Point("", other.X - center.X, other.Y - center.Y);

        double angle = Math.atan2(stdVector.Y, stdVector.X);

        return angle < 0 ? angle + 2 * Math.PI : angle;
    }

    //    /**
    //     * Maintain a public repository of all segment objects in the figure.
    //     */
    //    public static void clear() { figurePoints.clear(); }
    //    public static ArrayList<Point> figurePoints = new ArrayList<Point>();
    //
    //    public static void Record(GroundedClause clause)
    //    {
    //        // Record uniquely? For right angles, etc?
    //        if (clause instanceof Point) figurePoints.add((Point)clause);
    //    }
    //
    //    public static Point GetFigurePoint(Point candPoint)
    //    {
    //        for (Point p : figurePoints)
    //        {
    //            if (p.structurallyEquals(candPoint)) return p;
    //        }
    //
    //        return null;
    //    }

    /**
     * Calculates the distance between 2 points
     * @param p1 Point 1
     * @param p2 Point 2
     * @return The distance between points
     */
    public static double calcDistance(Point p1, Point p2)
    {
        return Math.sqrt(Math.pow(p2.X - p1.X, 2) + Math.pow(p2.Y - p1.Y, 2));
    }

    /**
     * Determines if value is between a and b
     * @param val Value to test
     * @param a Bound 1
     * @param b Bound 2
     * @return True if a <= val <= b or b<= val <= a : False if (val < a and val < b) or (a < val and b < val)
     */
    public static boolean Between(double val, double a, double b)
    {
        if (a >= val && val <= b) return true;
        if (b >= val && val <= a) return true;

        return false;
    }

    // Make a deep copy of this object; this is actually shallow, but is all that is required.
    public GroundedClause DeepCopy() 
    { 
        try
        {
            return (Point)(this.clone());
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            ExceptionHandler.throwException(e);
        }
        return this; 
    } 

    @Override
    public boolean structurallyEquals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;

        Point pt =  (Point)obj;

        return MathUtilities.doubleEquals(pt.X, X) && MathUtilities.doubleEquals(pt.Y, Y);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (!structurallyEquals(obj)) return false;

        return name.equals(((Point)obj).name);
    }

    @Override
    public String toString()
    {
        if (X == (int) X && Y == (int) Y)
        {
            return "(" + X + ", " + Y + ")";
        }
        return "(" + String.format("%1$.3f", X) + ", " + String.format("%1$.3f", Y) + ")"; 
    }

    @Override
    public String CheapPrettyString()
    {
        return SimpleToString(); 
    }

    @Override
    public String toPrettyString() { return name; }

    public String SimpleToString()
    {
        if (name == "") return "Point(" + String.format("%1$.1f", X) + ", " + String.format("%1$.3f", Y) + ")";
        else return name;
    }

    /**
     * 
     * @param p1 Point 1
     * @param p2 Point 2
     * @return Lexicographically: p1 < p2 return -1 : p1 == p2 return 0 : p1 > p2 return 1
     *         Order of X-coordinates first; order of Y-coordinates second
     */
    public static int LexicographicOrdering(Point p1, Point p2)
    {
        // Epsilon-based equality of both coordinates
        if (MathUtilities.doubleEquals(p1.X, p2.X) &&
            MathUtilities.doubleEquals(p1.Y, p2.Y)) return 0;

        // X's first
        if (p1.X < p2.X) return -1;

        if (p1.X > p2.X) return 1;

        // Y's second
        if (p1.Y < p2.Y) return -1;

        if (p1.Y > p2.Y) return 1;

        // Equal points: this should NOT happen
        return 0;
    }

    @Override
    public int compareTo(Object o)
    {
        if (o == null) return 1;
        if (!(o instanceof Point)) return 1;

        return Point.LexicographicOrdering(this, (Point)o);
    }


}