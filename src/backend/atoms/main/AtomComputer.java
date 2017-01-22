package backend.atoms.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backend.ast.figure.components.Circle;
import backend.ast.figure.components.polygon.Polygon;
import backend.atoms.components.AtomicRegion;
import backend.atoms.localComponents.AtomCircle;

//
// Main routine for atomic region computation:
//    given basic notions of a figure:
//    @param An essential figure (points, segments, circles, and polygons) 
public class AtomComputer
{
    //
    // Atomic region computation is based on the following sequence of computations:
    //
    //    1) Order circles by radius (size)
    //    2) Identify all intersection points related to circles (polygons and segments with Circles).
    //    3) Construct segments representing radii and chords for each circle.
    //    4) Extend any concave polygon inward to its edges.
    //    5) Extends orphans
    //
    public List<AtomicRegion> compute(EssentialFigure fig)
    {
        //    1) Order circles by radius (size)
        List<AtomCircle> circles = orderCircles(fig.getCircles());
        return null;
        
        //
        
        
        // Now that
     
    }

    /*
    * 1) Convert Circles to local representation (AtomCircle)
    * 2) Order the AtomCircles by radius: small to large
    *
    * @param circles list of generic circle objects
    * @return ordered list of local circle objects
    */
    private static List<AtomCircle> orderCircles(List<Circle> circles)
    {
        List<AtomCircle> ordered = new ArrayList<AtomCircle>();
        
//        circles.forEach((circle) -> ordered.add(new AtomCircle(circle)));

        Collections.sort(ordered);
        
        return ordered;
    }
    
    /*
    * 1) Convert Circles to local representation (AtomCircle)
    * 2) Order the AtomCircles by radius: small to large
    *
    * @param circles list of generic circle objects
    * @return ordered list of local circle objects
    */
    private static List<AtomCircle> extendConcavePolygons(List<List<Polygon>> polygons)
    {
        List<AtomCircle> ordered = new ArrayList<AtomCircle>();
        
        //circles.forEach((circle) -> ordered.add(new AtomCircle(circle)));

        Collections.sort(ordered);
        
        return ordered;
    }
    
    //
    // CTA Formerly in Circle class
    //
//  public ArrayList<Area_Based_Analyses.Atomizer.AtomicRegion> Atomize(List<Point> figurePoints)
//  {
//      ArrayList<Segment> constructedChords = new ArrayList<Segment>();
//      ArrayList<Segment> constructedRadii = new ArrayList<Segment>();
//      ArrayList<Point> imagPoints = new ArrayList<Point>();
//
//      ArrayList<Point> interPts = GetIntersectingPoints();
//
//      //
//      // Construct the radii
//      //
//      switch (interPts.size())
//      {
//          // If there are no points of interest, the circle is the atomic region.
//          case 0:
//              return Utilities.makeList<AtomicRegion>(new ShapeAtomicRegion(this));
//
//              // If only 1 intersection point, create the diameter.
//          case 1:
//              Point opp = Utilities.AcquirePoint(figurePoints, this.OppositePoint(interPts.get(0)));
//              constructedRadii.add(new Segment(_center, interPts.get(0)));
//              constructedRadii.add(new Segment(_center, opp));
//              imagPoints.add(opp);
//              interPts.add(opp);
//              break;
//
//          default:
//              for (Point interPt : interPts)
//              {
//                  constructedRadii.add(new Segment(_center, interPt));
//              }
//              break;
//      }
//
//      //
//      // Construct the chords
//      //
//      ArrayList<Segment> chords = new ArrayList<Segment>();
//      for (int p1 = 0; p1 < interPts.size() - 1; p1++)
//      {
//          for (int p2 = p1 + 1; p2 < interPts.size(); p2++)
//          {
//              Segment chord = new Segment(interPts[p1], interPts[p2]);
//              if (!DefinesDiameter(chord)) constructedChords.add(chord);
//          }
//      }
//
//      //
//      // Do any of the created segments result : imaginary intersection points.
//      //
//      for (Segment chord : constructedChords)
//      {
//          for (Segment _radius : constructedRadii)
//          {
//              Point inter = Utilities.AcquireRestrictedPoint(figurePoints, chord.FindIntersection(_radius), chord, _radius);
//              if (inter != null)
//              {
//                  chord.addCollinearPoint(inter);
//                  _radius.addCollinearPoint(inter);
//
//                  // if (!Utilities.HasStructurally<Point>(figurePoints, inter)) imagPoints.add(inter);
//                  backend.utilities.list.Utilities.addUnique(imagPoints, inter);
//              }
//          }
//      }
//
//      for (int c1 = 0; c1 < constructedChords.size() - 1; c1++)
//      {
//          for (int c2 = c1 + 1; c2 < constructedChords.size(); c2++)
//          {
//              Point inter = constructedChords[c1].FindIntersection(constructedChords[c2]);
//              inter = Utilities.AcquireRestrictedPoint(figurePoints, inter, constructedChords[c1], constructedChords[c2]);
//              if (inter != null)
//              {
//                  constructedChords[c1].addCollinearPoint(inter);
//                  constructedChords[c2].addCollinearPoint(inter);
//
//                  //if (!Utilities.HasStructurally<Point>(figurePoints, inter)) imagPoints.add(inter);
//                  backend.utilities.list.Utilities.addUnique(imagPoints, inter);
//              }
//          }
//      }
//
//      //
//      // Add all imaginary points to the list of figure points.
//      //
//      backend.utilities.list.Utilities.addUniqueList(figurePoints, imagPoints);
//
//      //
//      // Construct the Planar graph for atomic region identification.
//      //
//      Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph graph = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph();
//
//      //
//      // Add all imaginary points, intersection points, and _center.
//      //
//      for (Point pt : imagPoints)
//      {
//          graph.addNode(pt);
//      }
//
//      for (Point pt : interPts)
//      {
//          graph.addNode(pt);
//      }
//
//      graph.addNode(this._center);
//
//      //
//      // Add all chords and radii as edges.
//      //
//      for (Segment chord : constructedChords)
//      {
//          for (int p = 0; p < chord.collinear.size() - 1; p++)
//          {
//              graph.addUndirectedEdge(chord.collinear[p], chord.collinear[p + 1],
//                      new Segment(chord.collinear[p], chord.collinear[p + 1]).Length,
//                      Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
//          }
//      }
//
//      for (Segment _radius : constructedRadii)
//      {
//          for (int p = 0; p < _radius.collinear.size() - 1; p++)
//          {
//              graph.addUndirectedEdge(_radius.collinear[p], _radius.collinear[p + 1],
//                      new Segment(_radius.collinear[p], _radius.collinear[p + 1]).Length,
//                      Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_SEGMENT);
//          }
//      }
//
//      //
//      // Add all arcs
//      //
//      ArrayList<Point> arcPts = this.ConstructAllMidpoints(interPts);
//      for (int p = 0; p < arcPts.size(); p++)
//      {
//          graph.addNode(arcPts[p]);
//          graph.addNode(arcPts[(p + 1) % arcPts.size()]);
//
//          graph.addUndirectedEdge(arcPts[p], arcPts[(p + 1) % arcPts.size()],
//                  new Segment(arcPts[p], arcPts[(p + 1) % interPts.size()]).Length,
//                  Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.EdgeType.REAL_ARC);
//      }
//
//      //
//      // Convert the planar graph to atomic regions.
//      //
//      Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph copy = new Area_Based_Analyses.Atomizer.UndirectedPlanarGraph.PlanarGraph(graph);
//      FacetCalculator atomFinder = new FacetCalculator(copy);
//      ArrayList<Primitive> primitives = atomFinder.GetPrimitives();
//      ArrayList<AtomicRegion> atoms = PrimitiveToRegionConverter.Convert(graph, primitives, Utilities.makeList<Circle>(this));
//
//      //
//      // A filament may result : the creation of a major AND minor arc; both are not required.
//      // Figure out which one to omit.
//      // Multiple semi-circles may arise as well; omit if they can be broken into constituent elements.
//      //
//      List <AtomicRegion> trueAtoms = new ArrayList<AtomicRegion>();
//
//      for (int a1 = 0; a1 < atoms.size(); a1++)
//      {
//          boolean trueAtom = true;
//          for (int a2 = 0; a2 < atoms.size(); a2++)
//          {
//              if (a1 != a2)
//              {
//                  if (atoms[a1].contains(atoms[a2]))
//                  {
//                      trueAtom = false;
//                      break;
//                  }
//
//              }
//          }
//
//          if (trueAtom) trueAtoms.add(atoms[a1]);
//      }
//
//      atoms = trueAtoms;
//
//      return trueAtoms;
//  }

  //private double CentralAngleMeasure(Point pt1, Point pt2)
  //{
  //    return (new MinorArc(this, pt1, pt2)).GetMinorArcMeasureDegrees();
  //}
//
//  public void ConstructImpliedAreaBasedSectors(out ArrayList<Sector> minorSectors,
//          out ArrayList<Sector> majorSectors,
//          out ArrayList<Semicircle> semicircles)
//  {
//      minorSectors = new ArrayList<Sector>();
//      majorSectors = new ArrayList<Sector>();
//      semicircles = new ArrayList<Semicircle>();
//
//      // Points of interest for atomic region identification (and thus arc / sectors).
//      ArrayList<Point> interPts = this.OrderPoints(GetIntersectingPoints());
//
//      // If there are no points of interest, the circle is the atomic region.
//      if (!interPts.Any()) return;
//
//      // Cycle through all n C 2 intersection points and resultant arcs / sectors.
//      for (int p1 = 0; p1 < interPts.size() - 1; p1++)
//      {
//          for (int p2 = p1 + 1; p2 < interPts.size(); p2++)
//          {
//              //
//              // Do we have a diameter?
//              //
//              Segment diameter = new Segment(interPts[p1], interPts[p2]);
//              if (this.DefinesDiameter(diameter))
//              {
//                  // Create two semicircles; for simplicity, we choose the points on the semi-circle to be midpoints o neither, respective side.
//                  Point midpoint = this.Midpoint(interPts[p1], interPts[p2]);
//                  Point oppMidpoint = this.OppositePoint(midpoint);
//
//                  // Altogether, these 4 points define 4 quadrants (with the _center).
//                  semicircles.add(new Semicircle(this, interPts[p1], interPts[p2], midpoint, diameter));
//                  semicircles.add(new Semicircle(this, interPts[p1], interPts[p2], oppMidpoint, diameter));
//              }
//
//              //
//              // Normal major / minor sector construction.
//              //
//              else
//              {
//                  minorSectors.add(new Sector(new MinorArc(this, interPts[p1], interPts[p2])));
//                  majorSectors.add(new Sector(new MajorArc(this, interPts[p1], interPts[p2])));
//              }
//          }
//      }
//  }
//
//  /// <summary>
//  /// Make a set of connections for atomic region analysis.
//  /// </summary>
//  /// <returns></returns>
//  public override ArrayList<Connection> MakeAtomicConnections()
//  {
//      ArrayList<Segment> segments = this.Segmentize();
//      ArrayList<Connection> connections = new ArrayList<Connection>();
//
//      for (Segment approxSide : segments)
//      {
//          connections.add(new Connection(approxSide.getPoint1(), approxSide.getPoint2(), ConnectionType.ARC,
//                  new MinorArc(this, approxSide.getPoint1(), approxSide.getPoint2())));
//      }
//
//      return connections;
//  }
}
