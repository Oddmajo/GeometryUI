package PolyID;

import java.util.ArrayList;

public class Triangle extends Polygon
{
    public Segment SegmentA;
    public Segment SegmentB;
    public Segment SegmentC;
    public Point Point1;
    public Point Point2;
    public Point Point3;
    public Angle AngleA;
    public Angle AngleB;
    public Angle AngleC;
    protected boolean isRight;
    public boolean provenRight;
    public boolean givenRight;
    protected boolean isIsosceles;
    public boolean provenIsosceles;
    protected boolean isEquilateral;
    public boolean provenEquilateral;
    private ArrayList<Triangle> congruencePairs;
    private ArrayList<Triangle> similarPairs;
    public Triangle(ArrayList<Segment> segs)
    {
        super();
        SegmentA = segs.get(0);
        SegmentB = segs.get(1);
        SegmentC = segs.get(2);
    }
    public Triangle(Point a, Point b, Point c)
    {
        this(new Segment(a, b), new Segment(b, c), new Segment(a, c));
    }
    public Triangle(Segment a, Segment b, Segment c)
    {
        SegmentA = a;
        SegmentB = b;
        SegmentC = c;

        Point1 = SegmentA.Point1;
        Point2 = SegmentA.Point2;
        Point3 = Point1.equals(SegmentB.Point1) || Point2.equals(SegmentB.Point1) ? SegmentB.Point2 : SegmentB.Point1;

        AngleA = new Angle(Point1, Point2, Point3);
        AngleB = new Angle(Point2, Point3, Point1);
        AngleC = new Angle(Point3, Point1, Point2);

        isRight = isRightTriangle();
        provenRight = false;
        givenRight = false;
        isIsosceles = IsIsosceles();
        provenIsosceles = false;
        isEquilateral = IsEquilateral();
        provenEquilateral = false;

        congruencePairs = new ArrayList<Triangle>();
        similarPairs = new ArrayList<Triangle>();

        addSuperFigureToDependencies();
    }
    
    protected void addSuperFigureToDependencies()
    {
        Utilities.AddUniqueStructurally(SegmentA.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(SegmentB.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(SegmentC.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point1.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point2.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(Point3.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleA.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleB.getSuperFigures(), this);
        Utilities.AddUniqueStructurally(AngleC.getSuperFigures(), this);
    }
    private boolean IsEquilateral()
    {
        return Utilities.CompareValues(SegmentA.Length, SegmentB.Length) &&
                Utilities.CompareValues(SegmentB.Length, SegmentC.Length);
    }
    private boolean IsIsosceles()
    {
        Segment[] segments = new Segment[3];
        segments[0] = SegmentA;
        segments[1] = SegmentB;
        segments[2] = SegmentC;

        for (int i = 0; i < segments.length; i++)
        {
            if (Utilities.CompareValues(segments[i].Length, segments[(i + 1) % segments.length].Length)) return true;
        }

        return false;
    }
    private boolean isRightTriangle()
    {
        return Utilities.CompareValues(AngleA.measure, 90) ||
                Utilities.CompareValues(AngleB.measure, 90) ||
                Utilities.CompareValues(AngleC.measure, 90);
    }

}
