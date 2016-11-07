package backend.atoms.components;

import java.util.ArrayList;

import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class DifferenceAtomicRegion extends AtomicRegion
{

    private AtomicRegion outerShape;  // { get; private set; }
    public AtomicRegion getOuterShape() { return outerShape; }
    
    private ArrayList<AtomicRegion> innerShapes; // { get; private set; }
    public ArrayList<AtomicRegion> getInnerShapes() { return innerShapes; }

    public DifferenceAtomicRegion(AtomicRegion outer, AtomicRegion inner) // : base()
    {
        super();
        outerShape = outer;
        innerShapes = new ArrayList<AtomicRegion>();
        innerShapes.add(inner);
    }

    // Can the area of this region be calcualted?
    @Override
    public boolean IsComputableArea()
    {
        if (!outerShape.IsComputableArea()) return false;
        
        for (AtomicRegion inner : innerShapes)
        {
            if (!inner.IsComputableArea()) return false;
        }
        return true;
    }

    // I don't know where/what the KnownMeasurementsAggregator is - Drew
//    @Override
//    public double GetArea(KnownMeasurementsAggregator known)
//    {
//        double outerArea = outerShape.GetArea(known);
//        if (outerArea < 0) return -1;
//
//        double innerArea = 0;
//        foreach (AtomicRegion inner in innerShapes)
//        {
//            double thisInnerArea = inner.GetArea(known);
//            if (thisInnerArea < 0) return -1;
//
//            innerArea += thisInnerArea;
//        }
//
//        return outerArea - innerArea;
//    }

    @Override
    public boolean PointLiesInside(Point pt)
    {
        if (!outerShape.PointLiesInside(pt)) return false;

        for (AtomicRegion inner : innerShapes)
        {
            if (inner.PointLiesInOrOn(pt)) return false;
        }

        return true;
    }

    // I don't think this has been implemented yet... Drew
//    //
//    // Takes a shape turns it into an approximate polygon (if needed)
//    // by converting all arcs into approximated arcs using many line segments.
//    //
//    @Override
//    public Polygon GetPolygonalized()
//    {
//        ExceptionHandler.throwException( new ArgumentException("Difference region is not a polygon."));
//        // return null;
//    }


    @Override
    public boolean Equals(Object obj)
    {
        //DifferenceAtomicRegion thatAtom = obj as DifferenceAtomicRegion;
        //if (thatAtom == null) return false;
        if (obj != null && obj instanceof DifferenceAtomicRegion)
        {
            DifferenceAtomicRegion thatAtom = (DifferenceAtomicRegion) obj;
            
            if (!outerShape.Equals(thatAtom.outerShape)) return false;
    
            if (this.innerShapes.size() != thatAtom.innerShapes.size()) return false;
    
            for (AtomicRegion inner : innerShapes)
            {
                if (!thatAtom.HasInnerAtom(inner)) return false;
            }
    
            return true;
        }
    }

    @Override
    public String ToString()
    {
        String retString = "DifferenceAtom: (" + outerShape.ToString() + " - ";

        for (int i = 0; i < innerShapes.size(); i++)
        {
            retString += innerShapes.get(i).ToString();
            if (i < innerShapes.size() - 1) retString += " - ";
        }

        return retString;
    }

}
