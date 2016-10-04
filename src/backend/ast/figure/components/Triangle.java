package backend.ast.figure.components;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Polygon
{
    // CTA: MUST be protected with getters ONLY
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
    public Triangle(List<Point> points)
    {
        this(points.get(0), points.get(1), points.get(2));
    }
    public Triangle(Segment ... segments)
    {
        this(segments[0], segments[1], segments[2]);
    }
    public Triangle(Segment a, Segment b, Segment c)
    {
        SegmentA = a;
        SegmentB = b;
        SegmentC = c;

        Point1 = SegmentA.getPoint1();
        Point2 = SegmentA.getPoint2();
        Point3 = Point1.equals(SegmentB.getPoint1()) || Point2.equals(SegmentB.getPoint1()) ? SegmentB.getPoint2() : SegmentB.getPoint1();

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
        backend.utilities.list.Utilities.addUniqueStructurally(SegmentA.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(SegmentB.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(SegmentC.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(Point1.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(Point2.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(Point3.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(AngleA.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(AngleB.getSuperFigures(), this);
        backend.utilities.list.Utilities.addUniqueStructurally(AngleC.getSuperFigures(), this);
    }
    private boolean IsEquilateral()
    {
        return backend.utilities.math.Utilities.doubleEquals(SegmentA.length(), SegmentB.length()) &&
                backend.utilities.math.Utilities.doubleEquals(SegmentB.length(), SegmentC.length());
    }
    
    private boolean IsIsosceles()
    {
        Segment[] segments = { SegmentA, SegmentB, SegmentC};

        for (int i = 0; i < segments.length; i++)
        {
            if (backend.utilities.math.Utilities.doubleEquals(segments[i].length(), segments[(i + 1) % segments.length].length())) return true;
        }

        return false;
    }
    
    private boolean isRightTriangle()
    {
        return backend.utilities.math.Utilities.doubleEquals(AngleA.measure, 90) ||
               backend.utilities.math.Utilities.doubleEquals(AngleB.measure, 90) ||
               backend.utilities.math.Utilities.doubleEquals(AngleC.measure, 90);
    }
}
