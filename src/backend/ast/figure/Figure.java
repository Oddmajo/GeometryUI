/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @author Nick Celiberti
 */
package backend.ast.figure;


import java.util.ArrayList;
import java.util.List;

import backend.ast.figure.components.Arc;
import backend.ast.figure.components.MajorArc;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Polygon;
import backend.ast.figure.components.Sector;
import backend.ast.figure.components.Segment;
import backend.atoms.components.AtomicRegion;
import backend.atoms.components.Connection;
import backend.atoms.components.ShapeAtomicRegion;
import backend.utilities.exception.NotImplementedException;
import backend.utilities.translation.OutPair;
import backend.ast.GroundedClause;

public abstract class Figure extends GroundedClause
{
    protected Figure()
    {
        subFigures = new ArrayList<Figure>();
        superFigures = new ArrayList<Figure>();
        polygonalized = null;
        atoms = new ArrayList<AtomicRegion>();
        intersectingPoints = new ArrayList<Point>();
    }

    // Can we compute the area of this figure?
    public boolean IsComputableArea() { return false; }

//    public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known) { return false; }
//    public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known) { return -1; }


    public static final int NUM_SEGS_TO_APPROX_ARC = 72;
    public boolean PointLiesInside(Point pt) { return false; }
    public boolean PointLiesOn(Point pt) { return false; }
    public boolean PointLiesInOrOn(Point pt)
    {
        if (pt == null) return false;

        return PointLiesOn(pt) || PointLiesInside(pt);
    }
    public ArrayList<Segment> Segmentize() { return new ArrayList<Segment>(); }
    public List<Point> GetApproximatingPoints() { return new ArrayList<Point>(); }

    protected ArrayList<Point> intersectingPoints;
    public void AddIntersectingPoint(Point pt) { backend.utilities.ast_helper.Utilities.AddStructurallyUnique(intersectingPoints, pt); } 
    public void AddIntersectingPoints(ArrayList<Point> pts)
    { 
        for (Point pt : pts)
        {
            this.AddIntersectingPoint(pt);
        }

    }

    public ArrayList<Point> GetIntersectingPoints() { return intersectingPoints; }
    public void FindIntersection(Segment that, OutPair<Point, Point> out) { out.set(null, null); }
    public void FindIntersection(Arc that, OutPair<Point, Point> out) { out.set(null, null); }
    public ArrayList<Connection> MakeAtomicConnections() { return new ArrayList<Connection>(); }
    public boolean Covers(AtomicRegion a) throws NotImplementedException { throw new NotImplementedException(); }

    // An ORDERED ArrayList of collinear points.
    protected ArrayList<Point> collinear; 
    public ArrayList<Point> getCollinear() { return collinear; } 
    public void AddCollinearPoint(Point newPt) throws IllegalArgumentException { throw new IllegalArgumentException("Only segments or arcs have 'collinearity'"); }
    public void AddCollinearPoints(ArrayList<Point> pts) throws IllegalArgumentException
    { 
        for (Point pt : pts)
        {
            this.AddCollinearPoint(pt);
        }
    }
    public void ClearCollinear() throws IllegalArgumentException { throw new IllegalArgumentException("Only segments or arcs have 'collinearity'"); }

    protected ArrayList<Figure> superFigures;
    protected ArrayList<Figure> subFigures;
    public void addSuperFigure(Figure f) { if (!backend.utilities.ast_helper.Utilities.HasStructurally(superFigures, f)) superFigures.add(f); }
    public void addSubFigure(Figure f) { if (!backend.utilities.ast_helper.Utilities.HasStructurally(subFigures, f)) subFigures.add(f); }
    public boolean isShared() { return superFigures.size() > 1; }
    public ArrayList<Figure> getSuperFigures() { return superFigures; }

    protected Polygon polygonalized;
    public Polygon GetPolygonalized() { return polygonalized; }
    protected void setPolygonalized(Polygon poly) { this.polygonalized = poly; }
    public ArrayList<AtomicRegion> atoms;
    public ArrayList<AtomicRegion> getAtoms() { return atoms; }
    protected void setAtoms(ArrayList<AtomicRegion> atoms) { this.atoms = atoms; } 
//    public Polygon GetPolygonalized() throws Exception { return null; }
    public String CheapPrettyString() { return "TBD"; }

    public boolean CoordinateCongruent(Figure that) { return false; }

    //
    // Shape hierarchy for shaded region solution / problem synthesis.
    //

    protected ShapeAtomicRegion thisAtomicRegion;
    
        public ShapeAtomicRegion GetFigureAsAtomicRegion()
        {
            if (thisAtomicRegion == null) thisAtomicRegion = new ShapeAtomicRegion(this);
            return thisAtomicRegion;
        }

//        private Area_Based_Analyses.TreeNode<Figure> shapeHierarchy;
//        public boolean HierarchyEstablished() { return shapeHierarchy != null; }
//        public Area_Based_Analyses.TreeNode<Figure> Hierarchy() { return shapeHierarchy; }
//        public void MakeLeaf()
//        {
//            shapeHierarchy = new Area_Based_Analyses.TreeNode<Figure>(this);
//        }
//
//
//        public void SetChildren(ArrayList<Figure> children)
//        {
//            if (shapeHierarchy == null) shapeHierarchy = new Area_Based_Analyses.TreeNode<Figure>(this);
//
//            for (Figure child : children)
//            {
//                shapeHierarchy.addChild(child.Hierarchy());
//            }
//        }

        public void AddAtomicRegion(AtomicRegion atom)
        {
            // Avoid adding an atomic region which is itself
            //if (atom is ShapeAtomicRegion)
            //{
            //    if ((atom as ShapeAtomicRegion).shape.StructurallyEquals(this)) return;
            //}

            if (atoms.contains(atom)) return;

            atoms.add(atom);
        }

        public void AddAtomicRegions(List<AtomicRegion> atoms)
        {
            for (AtomicRegion atom : atoms)
            {
                AddAtomicRegion(atom);
            }
        }




    //
    // A shape within this shape?
    //

    public boolean Contains(Figure that)
    {
        return thisAtomicRegion.Contains(that.GetFigureAsAtomicRegion());
    }

            public boolean Contains(ArrayList<Point> figurePoints, AtomicRegion atom)
            {
                // A figure contains itself.
                ShapeAtomicRegion shapeAtom = (ShapeAtomicRegion)atom;
                if (shapeAtom != null)
                {
                    if (this.structurallyEquals(shapeAtom.getShape())) return true;
                }
    
                //
                // Do all vertices of that lie on the interior of this figure
                //
                ArrayList<Point> thatVertices = atom.GetVertices();
                for (Point vertex : thatVertices)
                {
                    if (!this.PointLiesInOrOn(vertex)) return false;
                }
    
                //
                // Check all midpoints of conenctions are on the interior.
                //
                for (Connection thatConn : atom.getConnections())
                {
                    if (!this.PointLiesInOrOn(thatConn.Midpoint())) return false;
                }
    
                //
                // For any intersections between the atomic regions, the resultant points of intersection must be on the perimeter.
                //
                AtomicRegion thisFigureRegion = this.GetFigureAsAtomicRegion();
                ArrayList<AtomicRegion.IntersectionAgg> intersections = thisFigureRegion.GetIntersections(figurePoints, atom);
                for (AtomicRegion.IntersectionAgg agg : intersections)
                {
                    if (agg.overlap)
                    {
                        // No-Op
                    }
                    else
                    {
                        // An approximation may result : an intersection inside the figure (although we would expect on)
                        if (!this.PointLiesInOrOn(agg.intersection1)) return false;
                        if (agg.intersection2 != null)
                        {
                            if (!this.PointLiesInOrOn(agg.intersection2)) return false;
                        }
                    }
                }
    
                return true;
            }

    //
    // Is this figure (or atomic region approx by polygon) completely contained : the given figure?
    //
    public boolean Contains(Polygon thatPoly)
    {
        //
        // Special Cases:
        //
        // Disambiguate Major Sector from Minor Sector
        if (this instanceof Sector && ((Sector)this ).getArc() instanceof MajorArc)
        {
            // Not only do all the points need to be inside, the midpoints do as well.
            for(Segment side : thatPoly.getOrderedSides())
            {
                Point midpt = side.Midpoint();
                if (!this.PointLiesInOrOn(midpt)) return false;
            }
        }

        //
        // General Case
        //
        for (Point thatPt : thatPoly.getPoints())
        {
            if (!this.PointLiesInOrOn(thatPt)) return false;
        }

        return true;
    }
}
