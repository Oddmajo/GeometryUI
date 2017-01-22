package backend.ast.figure;

import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.arcs.Arc;
import backend.utilities.translation.OutPair;

public abstract class DimensionalLength extends Figure
{
    public DimensionalLength() { super(); }

    public abstract boolean intersection(Arc arc, OutPair<Point, Point> out);
    public abstract boolean intersection(Segment segment, OutPair<Point, Point> out);

    public boolean findIntersection(DimensionalLength length, OutPair<Point, Point> out)
    {
        if (length instanceof Arc)
        {
            return this.intersection((Arc)length, out);
        }
        else if (length instanceof Segment)
        {
            return this.intersection((Segment)length, out);
        }

        return false;
    }
    
    protected ArrayList<Point> _collinear = new ArrayList<Point>();
    public List<Point> getCollinear() { return _collinear; }
    public void addCollinearPoint(Point p) { _collinear.add(p); }
    public void ClearCollinear() { _collinear.clear(); }
}
