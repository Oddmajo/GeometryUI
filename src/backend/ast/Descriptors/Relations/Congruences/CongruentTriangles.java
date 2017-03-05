package backend.ast.Descriptors.Relations.Congruences;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.hypergraph.Annotation;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.NotImplementedException;

public class CongruentTriangles extends Congruent
{
	protected Triangle ct1;
	protected Triangle ct2;
	
	public Triangle getFirstTriangle()
	{
		return ct1;
	}
	public Triangle getSecondTriangle()
	{
		return ct2;
	}
	
	public CongruentTriangles(Triangle t1, Triangle t2)
	{
		super();
		ct1 = t1;
		ct2 = t2;
	}
	
	@Override
	public boolean isReflexive()
	{
		return ct1.structurallyEquals(ct2);
	}
	
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof CongruentTriangles)
		{
			CongruentTriangles cts = (CongruentTriangles)obj;
			
			//the point must equate in order
			Pair<Triangle, Triangle> pair1;
			Pair<Triangle, Triangle> pair2;
			if(ct1.structurallyEquals(cts.ct1) && ct2.structurallyEquals(cts.ct2))
			{
				pair1 = new Pair<Triangle, Triangle>(ct1, cts.ct1);
				pair2 = new Pair<Triangle, Triangle>(ct2, cts.ct2);
			}
			else if(ct1.structurallyEquals(cts.ct2) && ct2.structurallyEquals(cts.ct1))
			{
				pair1 = new Pair<Triangle, Triangle>(ct1, cts.ct2);
				pair2 = new Pair<Triangle, Triangle>(ct2, cts.ct1);
			}
			else
			{
				return false;
			}
			
			
//			if (!VerifyMappingOrder(pair1, pair2)) return false;
//
//            return ct1.StructurallyEquals(cts.ct1) && ct2.StructurallyEquals(cts.ct2) ||
//                   ct1.StructurallyEquals(cts.ct2) && ct2.StructurallyEquals(cts.ct1);
			
			ExceptionHandler.throwException(new NotImplementedException());
		}
		
		//if the check fails it should return false
		return false;
	}
	
//	public override bool Equals(Object c)
//    {
//        CongruentTriangles cts = c as CongruentTriangles;
//        if (cts == null) return false;
//
//        // The point must equate in order
//        KeyValuePair<Triangle, Triangle> pair1;
//        KeyValuePair<Triangle, Triangle> pair2;
//        if (ct1.Equals(cts.ct1) && ct2.Equals(cts.ct2))
//        {
//            pair1 = new KeyValuePair<Triangle, Triangle>(ct1, cts.ct1);
//            pair2 = new KeyValuePair<Triangle, Triangle>(ct2, cts.ct2);
//        }
//        else if (ct1.Equals(cts.ct2) && ct2.Equals(cts.ct1))
//        {
//            pair1 = new KeyValuePair<Triangle, Triangle>(ct1, cts.ct2);
//            pair2 = new KeyValuePair<Triangle, Triangle>(ct2, cts.ct1);
//        }
//        else return false;
//
//        if (!VerifyMappingOrder(pair1, pair2)) return false;
//
//        return base.Equals(c);
//    }

//	 //
//    // Are these two congruence pairs equivalent when we check the corresponding points exactly
//    //
//    private bool VerifyMappingOrder(KeyValuePair<Triangle, Triangle> pair1, KeyValuePair<Triangle, Triangle> pair2)
//    {
//        // Determine how the points are mapped from thisTriangle to thatTriangle
//        List<Point> triangle11Pts = pair1.Key.points;
//        List<Point> triangle12Pts = pair1.Value.points;
//
//        int[] indexMap = new int[3];
//        for (int p11 = 0; p11 < triangle11Pts.Count; p11++)
//        {
//            for (int p12 = 0; p12 < triangle12Pts.Count; p12++)
//            {
//                if (triangle11Pts[p11].StructurallyEquals(triangle12Pts[p12]))
//                {
//                    indexMap[p11] = p12;
//                    break;
//                }
//            }
//        }
//
//        // Verify that the second pairing maps the exact same way as the first pairing
//        List<Point> triangle21Pts = pair2.Key.points;
//        List<Point> triangle22Pts = pair2.Value.points;
//        for (int i = 0; i < indexMap.Length; i++)
//        {
//            if (!triangle21Pts[i].StructurallyEquals(triangle22Pts[indexMap[i]])) return false;
//        }
//
//        return true;
//    }
//
    // Acquire a direct correspondence between thatTriangle and the other triangle in the congruence pair
    // Returns: <that, other>
    public Dictionary<Point, Point> OtherTriangle(Triangle thatTriangle)
    {
        Dictionary<Point, Point> correspondence = this.ct1.PointsCorrespond(thatTriangle);
        Dictionary<Point, Point> otherCorrespondence = new Hashtable<Point, Point>();

        List<Point> ct1Pts = this.ct1.getPoints();
        List<Point> ct2Pts = this.ct2.getPoints();

        // Acquire correspondence between thatTriangle and the other triangle (ct2)
        if (correspondence != null)
        {
            for (int p = 0; p < 3; p++ )
            {
                Point thatPt = correspondence.get(ct1Pts.get(p));
                if (thatPt == null) ExceptionHandler.throwException(new ArgumentException("Something strange happened in Triangle correspondence."));
                otherCorrespondence.put(thatPt, ct2Pts.get(p));
            }

            return otherCorrespondence;
        }

        correspondence = this.ct2.PointsCorrespond(thatTriangle);
        if (correspondence != null)
        {
            for (int p = 0; p < 3; p++)
            {
                Point thatPt = correspondence.get(ct2Pts.get(p));
                if (thatPt == null) ExceptionHandler.throwException(new ArgumentException("Something strange happened in Triangle correspondence."));
                otherCorrespondence.put(thatPt, ct1Pts.get(p));
            }

            return otherCorrespondence;
        }

        return null;
    }
//
	
    public Dictionary<Point, Point> HasTriangle(Triangle thatTriangle)
    {
        Dictionary<Point, Point> correspondence = this.ct1.PointsCorrespond(thatTriangle);
        if (correspondence != null) return correspondence;

        correspondence = this.ct2.PointsCorrespond(thatTriangle);
        if (correspondence != null) return correspondence;

        return null;
    }
//
//    public override int GetHashCode()
//    {
//        //Change this if the object is no longer immutable!!!
//        return base.GetHashCode();
//    }
//
//    //
//    // This congruence is given from the user, so handle any type of processing needed to prevent
//    // 'reproving' facts implied by this congruence
//    //
//    public void ProcessGivens()
//    {
//        ct1.AddCongruentTriangle(ct2);
//        ct2.AddCongruentTriangle(ct1);
//    }
//
//
    private static final String CPCTC_NAME = "CPCTC";
    private static Annotation cpctcAnnotation = new Annotation(CPCTC_NAME, RuleFactory.JustificationSwitch.TRIANGLE_CONGREUNCE);

    //
    // Create the three resultant angles from each triangle to create the congruency of angles
    //
    public static List<GroundedClause> GenerateCPCTCSegments(List<Point> orderedTriOnePts, List<Point> orderedTriTwoPts)
    {
        List<GroundedClause> congSegments = new ArrayList<GroundedClause>();

        // Cycle through the points creating the angles: ABC - DEF ; BCA - EFD ; CAB - FDE
        for (int i = 0; i < orderedTriOnePts.size(); i++)
        {
            Segment cs1 = new Segment(orderedTriOnePts.get(0), orderedTriOnePts.get(1));
            Segment cs2 = new Segment(orderedTriTwoPts.get(0), orderedTriTwoPts.get(1));
            congSegments.add(new GeometricCongruentSegments(cs1, cs2));

            // rotate the lists
            Point tmp = orderedTriOnePts.get(0);
            orderedTriOnePts.remove(0);
            orderedTriOnePts.add(tmp);

            tmp = orderedTriTwoPts.get(0);
            orderedTriTwoPts.remove(0);
            orderedTriTwoPts.add(tmp);
        }

        return congSegments;
    }

    //
    // Create the three resultant angles from each triangle to create the congruency of angles
    //
    public static List<GroundedClause> GenerateCPCTCAngles(List<Point> orderedTriOnePts,
                                                           List<Point> orderedTriTwoPts)
    {
        List<GroundedClause> congAngles = new ArrayList<GroundedClause>();

        // Cycle through the points creating the angles: ABC - DEF ; BCA - EFD ; CAB - FDE
        for (int i = 0; i < orderedTriOnePts.size(); i++)
        {
            congAngles.add(new GeometricCongruentAngles(new Angle(orderedTriOnePts), new Angle(orderedTriTwoPts)));

            // rotate the lists
            Point tmp = orderedTriOnePts.get(0);
            orderedTriOnePts.remove(0);
            orderedTriOnePts.add(tmp);

            tmp = orderedTriTwoPts.get(0);
            orderedTriTwoPts.remove(0);
            orderedTriTwoPts.add(tmp);
        }

        return congAngles;
    }

    public static List<Deduction> GenerateCPCTC(CongruentTriangles ccts,
                                                     List<Point> orderedTriOnePts,
                                                     List<Point> orderedTriTwoPts)
    {
        ArrayList<Deduction> newGrounded = new ArrayList<Deduction>();

        List<GroundedClause> antecedent = Utilities.MakeList(ccts);
        List<GroundedClause> congAngles = GenerateCPCTCAngles(orderedTriOnePts, orderedTriTwoPts);

        for (GroundedClause ccas : congAngles)
        {
            GeometricCongruentAngles ccas2 = (GeometricCongruentAngles) ccas;
            newGrounded.add(new Deduction(antecedent, ccas2, cpctcAnnotation));
        }

        List<GroundedClause> congSegments = GenerateCPCTCSegments(orderedTriOnePts, orderedTriTwoPts);
        for (GroundedClause ccss : congSegments)
        {
            newGrounded.add(new Deduction(antecedent, ccss, cpctcAnnotation));
        }

        return newGrounded;
    }
//
//    //
//    // Generate all corresponding conngruent components.
//    // Ensure vertices correpsond appropriately.
//    //
//    public static List<GenericInstantiator.EdgeAggregator> Instantiate(GroundedClause clause)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        CongruentTriangles conTris = clause as CongruentTriangles;
//        if (conTris == null) return newGrounded;
//
//        List<Point> orderedTriOnePts = new List<Point>();
//        List<Point> orderedTriTwoPts = new List<Point>();
//
//        if (!conTris.VerifyCongruentTriangles(out orderedTriOnePts, out orderedTriTwoPts)) return newGrounded;
//
//        return GenerateCPCTC(conTris, orderedTriOnePts, orderedTriTwoPts);
//    }
//
//    public bool VerifyCongruentTriangles()
//    {
//        List<Point> orderedTriOnePts = new List<Point>();
//        List<Point> orderedTriTwoPts = new List<Point>();
//
//        return VerifyCongruentTriangles(out orderedTriOnePts, out orderedTriTwoPts);
//    }
//
//
//    private bool VerifyCongruentTriangles(out List<Point> orderedTriOnePts, out List<Point> orderedTriTwoPts)
//    {
//        // Ensure the points are ordered appropriately.
//        // CongruentTriangle ensures points are mapped.
//
//        List<Angle> triOneAngles = ct1.angles;
//        List<Angle> triTwoAngles = ct2.angles;
//
//        orderedTriOnePts = new List<Point>();
//        orderedTriTwoPts = new List<Point>();
//        bool[] marked = new bool[3];
//
//        //
//        // Check angles correspondence
//        //
//        for (int i = 0; i < triOneAngles.Count; i++)
//        {
//            for (int j = 0; j < triTwoAngles.Count; j++)
//            {
//                if (!marked[j])
//                {
//                    if (Utilities.CompareValues(triOneAngles[i].measure, triTwoAngles[j].measure))
//                    {
//                        orderedTriOnePts.Add(triOneAngles[i].GetVertex());
//                        orderedTriTwoPts.Add(triTwoAngles[j].GetVertex());
//                        marked[j] = true;
//                        break;
//                    }
//                }
//            }
//        }
//
//        // Similarity has failed 
//        if (marked.Contains(false))
//        {
//            return false;
//        }
//
//        //
//        // The established correspondence can now be verified with segment lengths
//        //
//        for (int v = 0; v < 2; v++)
//        {
//            double seg1Distance = Point.calcDistance(orderedTriOnePts[v], orderedTriOnePts[v + 1 < 3 ? v + 1 : 0]);
//            double seg2Distance = Point.calcDistance(orderedTriTwoPts[v], orderedTriTwoPts[v + 1 < 3 ? v + 1 : 0]);
//
//            if (!Utilities.CompareValues(seg1Distance, seg2Distance))
//            {
//                return false;
//            }
//        }
//
//        return true;
//    }
	
	
	@Override 
	public String toString()
	{
		return "Congruent(" + ct1.toString() + ", " + ct2.toString() + ") " + justification;
	}
}
