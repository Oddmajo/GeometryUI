/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package PolyID;

// Commented Methods need AtomicRegion, Area_Based_Analyses, Utilities, superFigures, ShapeHiearchy, 

import java.util.ArrayList;

public abstract class Figure extends GroundedClause
    {
        protected Figure()
        {
            subFigures = new ArrayList<Figure>();
            superFigures = new ArrayList<Figure>();
            polygonalized = null;
            //atoms = new ArrayList<AtomicRegion>();
            intersectingPoints = new ArrayList<Point>();
        }

        // Can we compute the area of this figure?
        public boolean IsComputableArea() { return false; }

        /*
            public boolean CanAreaBeComputed(Area_Based_Analyses.KnownMeasurementsAggregator known) { return false; }
            public double GetArea(Area_Based_Analyses.KnownMeasurementsAggregator known) { return -1; }          
         */

        
        public final int NUM_SEGS_TO_APPROX_ARC = 72;
        public boolean PointLiesInside(Point pt) { return false; }
        public boolean PointLiesOn(Point pt) { return false; }
        public boolean PointLiesInOrOn(Point pt)
        {
            if (pt == null) return false;

            return PointLiesOn(pt) || PointLiesInside(pt);
        }
        public ArrayList<Segment> Segmentize() { return new ArrayList<Segment>(); }
        public ArrayList<Point> GetApproximatingPoints() { return new ArrayList<Point>(); }

        protected ArrayList<Point> intersectingPoints;
        public void AddIntersectingPoint(Point pt) { /*Utilities.AddStructurallyUnique<Point>(intersectingPoints, pt); */} //// Needs Utlities
        public void AddIntersectingPoints(ArrayList<Point> pts)
        { 
            for (Point pt : pts)
            {
                this.AddIntersectingPoint(pt);
            }

        }

        public ArrayList<Point> GetIntersectingPoints() { return intersectingPoints; }
        public void FindIntersection(Segment that, Point inter1, Point inter2) { inter1 = null; inter2 = null; }
        ////public void FindIntersection(Arc that, Point inter1, Point inter2) { inter1 = null; inter2 = null; }
        ////public ArrayList<Connection> MakeAtomicConnections() { return new ArrayList<Connection>(); }
        ////public boolean Covers(AtomicRegion a) { throw new NotImplementedException(); }

        // An ORDERED ArrayList of collinear points.
        protected ArrayList<Point> collinear; 
            public ArrayList<Point> getCollinear() { return collinear; } 
        public void AddCollinearPoint(Point newPt) throws ArgumentException { throw new ArgumentException("Only segments or arcs have 'collinearity'"); }
        public void AddCollinearPoints(ArrayList<Point> pts) throws ArgumentException
        { 
            for (Point pt : pts)
            {
                this.AddCollinearPoint(pt);
            }
        }
        public void ClearCollinear() throws ArgumentException { throw new ArgumentException("Only segments or arcs have 'collinearity'"); }

        protected ArrayList<Figure> superFigures;
        protected ArrayList<Figure> subFigures;
        ////public void AddSuperFigure(Figure f) { if (!Utilities.HasStructurally<Figure>(superFigures, f)) superFigures.add(f); }
        ////public void AddSubFigure(Figure f) { if (!Utilities.HasStructurally<Figure>(subFigures, f)) subFigures.add(f); }
        private Polygon polygonalized;
            public Polygon getPolygonalized() { return polygonalized; }
            protected void setPolygonalized(Polygon poly) { this.polygonalized = poly; }
        ////public ArrayList<AtomicRegion> atoms;
            ////public ArrayList<AtomicRegion> getAtoms() { return atoms; }
            ////protected void setAtoms(ArrayList<AtomicRegion> atoms) { this.atoms = atoms; } 
        public Polygon GetPolygonalized() { return null; }
        public String CheapPrettyString() { return "TBD"; }

        //
        // Shape hierarchy for shaded region solution / problem synthesis.
        //
       
        protected ShapeAtomicRegion thisAtomicRegion;
        /* -------------------------- Requires Atomic Region, Area_Based_Analyses, and shapeHierarchy --------------------
        public ShapeAtomicRegion GetFigureAsAtomicRegion()
        {
            if (thisAtomicRegion == null) thisAtomicRegion = new ShapeAtomicRegion(this);
            return thisAtomicRegion;
        }

        private Area_Based_Analyses.TreeNode<Figure> shapeHierarchy;
        public boolean HierarchyEstablished() { return shapeHierarchy != null; }
        public Area_Based_Analyses.TreeNode<Figure> Hierarchy() { return shapeHierarchy; }
        public void MakeLeaf()
        {
            shapeHierarchy = new Area_Based_Analyses.TreeNode<Figure>(this);
        }

        
        public void SetChildren(ArrayList<Figure> children)
        {
            if (shapeHierarchy == null) shapeHierarchy = new Area_Based_Analyses.TreeNode<Figure>(this);

            for (Figure child : children)
            {
                shapeHierarchy.AddChild(child.Hierarchy());
            }
        }

        public void AddAtomicRegion(AtomicRegion atom)
        {
            // Avoid adding an atomic region which is itself
            //if (atom is ShapeAtomicRegion)
            //{
            //    if ((atom as ShapeAtomicRegion).shape.StructurallyEquals(this)) return;
            //}

            if (atoms.Contains(atom)) return;

            atoms.Add(atom);
        }

        public void AddAtomicRegions(List<AtomicRegion> atoms)
        {
            foreach (AtomicRegion atom in atoms)
            {
                AddAtomicRegion(atom);
            }
        }


        public boolean isShared() { return superFigures.Count > 1; }
        */
        public ArrayList<Figure> getSuperFigures() { return superFigures; }

        //
        // A shape within this shape?
        //
        
        /*
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
                if (this.StructurallyEquals(shapeAtom.shape)) return true;
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
            for (Connection thatConn : atom.connections)
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
                    // An approximation may result in an intersection inside the figure (although we would expect on)
                    if (!this.PointLiesInOrOn(agg.intersection1)) return false;
                    if (agg.intersection2 != null)
                    {
                        if (!this.PointLiesInOrOn(agg.intersection2)) return false;
                    }
                }
            }

            return true;
        }
    */
        //
        // Is this figure (or atomic region approx by polygon) completely contained in the given figure?
        //
        //public boolean Contains(Polygon thatPoly)
        //{
        //    //
        //    // Special Cases:
        //    //
        //    // Disambiguate Major Sector from Minor Sector
        //    if (this is Sector && (this as Sector).theArc is MajorArc)
        //    {
        //        // Not only do all the points need to be inside, the midpoints do as well.
        //        foreach(Segment side in thatPoly.orderedSides)
        //        {
        //            Point midpt = side.Midpoint();
        //            if (!this.PointLiesInOrOn(midpt)) return false;
        //        }
        //    }

        //    //
        //    // General Case
        //    //
        //    foreach (Point thatPt in thatPoly.points)
        //    {
        //        if (!this.PointLiesInOrOn(thatPt)) return false;
        //    }

        //    return true;
        //}
    }
