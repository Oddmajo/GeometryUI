package backend.ast.Descriptors.Relations;

import java.util.ArrayList;
import java.util.List;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.Relations.Congruences.CongruentTriangles;
import backend.ast.Descriptors.Relations.Proportionalities.GeometricSegmentRatioEquation;
import backend.ast.Descriptors.Relations.Proportionalities.ProportionalSegments;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.hypergraph.Annotation;
import backend.utilities.ast_helper.Utilities;

public class SimilarTriangles extends Descriptor
{
    private static final String NAME = "Similar Triangles";
    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.MEDIAN_DEFINITION);
	private Triangle st1;
	private Triangle st2;
	
	public Triangle getFirstTriangle()
	{
		return st1;
	}
	public Triangle getSecondTriangle()
	{
		return st2;
	}
	
	public SimilarTriangles(Triangle t1, Triangle t2)
	{
		super();
		ANNOTATION.active = RuleFactory.JustificationSwitch.SIMILARITY;
		st1 = t1;
		st2 = t2;
	}
	
	@Override 
	public boolean isReflexive()
	{
		return st1.structurallyEquals(st2);
	}
	
//	  public Triangle OtherTriangle(Triangle that)
//      {
//          if (st1.Equals(that)) return st2;
//          if (st2.Equals(that)) return st1;
//
//          return null;
//      }
//
//      public Triangle SharedTriangle(SimilarTriangles sts)
//      {
//          if (st1.StructurallyEquals(sts.st1) && st1.StructurallyEquals(sts.st2)) return st1;
//          if (st2.StructurallyEquals(sts.st1) && st2.StructurallyEquals(sts.st2)) return st2;
//
//          return null;
//      }
//
//      public int SharesNumTriangles(SimilarTriangles sts)
//      {
//          int shared = st1.StructurallyEquals(sts.st1) && st1.StructurallyEquals(sts.st2) ? 1 : 0;
//          shared += st2.StructurallyEquals(sts.st1) && st2.StructurallyEquals(sts.st2) ? 1 : 0;
//
//          return shared;
//      }
	
	@Override
	public boolean structurallyEquals(Object obj)
	{
		if(obj != null && obj instanceof SimilarTriangles)
		{
			SimilarTriangles sts = (SimilarTriangles)obj;
			return st1.structurallyEquals(sts.st1) && st2.structurallyEquals(sts.st2) ||
            st1.structurallyEquals(sts.st2) && st2.structurallyEquals(sts.st1);
		}
		
		//if the null check and instanceof fails it probably should return false
		return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof SimilarTriangles)
		{
			SimilarTriangles sts = (SimilarTriangles)obj;
			return (st1.equals(sts.st1) && st2.equals(sts.st2)) || (st1.equals(sts.st2) && st2.equals(sts.st1));
		}
		
		//if the null check and instanceof fails it probably should return false
		return false;
	}
	
	@Override
	public int getHashCode()
	{
		return super.getHashCode();
	}
	
	
//	private static readonly string ANGLES_NAME = "Angles of Similar Triangles are Congruent";
//    private static readonly string SEGMENTS_NAME = "Segments of Similar Triangles are Proportional";
//
//    private static Hypergraph.EdgeAnnotation angleAnnotation = new Hypergraph.EdgeAnnotation(ANGLES_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//    private static Hypergraph.EdgeAnnotation segmentAnnotation = new Hypergraph.EdgeAnnotation(SEGMENTS_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
    //
    // Create the three resultant angles from each triangle to create the congruency of angles
    //
    private static List<Deduction> GenerateSegmentRatio(SimilarTriangles simTris,
                                                                                 List<Point> orderedTriOnePts,
                                                                                 List<Point> orderedTriTwoPts)
    {
        //
        // Cycle through the points creating the angles: ABC - DEF ; BCA - EFD ; CAB - FDE
        //
        List<ProportionalSegments> ratios = new ArrayList<ProportionalSegments>();
        for (int i = 0; i < orderedTriOnePts.size(); i++)
        {
            Segment cs1 = new Segment(orderedTriOnePts.get(0), orderedTriOnePts.get(1));
            Segment cs2 = new Segment(orderedTriTwoPts.get(0), orderedTriTwoPts.get(1));
            ProportionalSegments ratio = new ProportionalSegments(cs1, cs2);

            ratios.add(ratio);

            // rotate the lists
            Point tmp = orderedTriOnePts.get(0);
            orderedTriOnePts.remove(0);
            orderedTriOnePts.add(tmp);

            tmp = orderedTriTwoPts.get(0);
            orderedTriTwoPts.remove(0);
            orderedTriTwoPts.add(tmp);
        }

        //
        // Take the ratios and create ratio equations.
        //
        List<GroundedClause> ratioEqs = new ArrayList<GroundedClause>();
        for (int i = 0; i < ratios.size(); i++)
        {
            ratioEqs.add(new GeometricSegmentRatioEquation(ratios.get(i), ratios.get((i + 1) % ratios.size())));
        }

        //
        // Construct the new deduced edges: proportional segments.
        //
        List<Deduction> newGrounded = new ArrayList<Deduction>();

        List<GroundedClause> antecedent = Utilities.MakeList(simTris);
        for (GroundedClause eq : ratioEqs)
        {
            newGrounded.add(new Deduction(antecedent, eq, ANNOTATION));
        }

        return newGrounded;
    }

    //
    // Create the three resultant angles from each triangle to create the congruency of angles
    //
    public static List<Deduction> GenerateCongruentAngles(SimilarTriangles simTris,
                                                                                   List<Point> orderedTriOnePts,
                                                                                   List<Point> orderedTriTwoPts)
    {
        ANNOTATION.active = RuleFactory.JustificationSwitch.SIMILARITY;

        List<GroundedClause> congAngles = CongruentTriangles.GenerateCPCTCAngles(orderedTriOnePts, orderedTriTwoPts);

        //
        // Construct the new deduced edges: congruent angles.
        //
        List<Deduction> newGrounded = new ArrayList<Deduction>();
        List<GroundedClause> antecedent = Utilities.MakeList(simTris);
        for (GroundedClause ccas : congAngles)
        {
            newGrounded.add(new Deduction(antecedent, ccas, ANNOTATION));
        }

        return newGrounded;
    }

    public static List<Deduction> GenerateComponents(SimilarTriangles simTris,
                                                                              List<Point> orderedTriOnePts,
                                                                              List<Point> orderedTriTwoPts)
    {
        List<Deduction> angles = GenerateCongruentAngles(simTris, orderedTriOnePts, orderedTriTwoPts);
        List<Deduction> segments = GenerateSegmentRatio(simTris, orderedTriOnePts, orderedTriTwoPts);
        angles.addAll(segments);

        return angles;
    }
//
//    private static readonly string NAME = "Transitivity";
//    private static Hypergraph.EdgeAnnotation transAnnotation = new Hypergraph.EdgeAnnotation(NAME, EngineUIBridge.JustificationSwitch.TRANSITIVE_SIMILAR);
//
//    public static List<GenericInstantiator.EdgeAggregator> CreateTransitiveSimilarTriangles(SimilarTriangles simTris1, SimilarTriangles simTris2)
//    {
//        transAnnotation.active = EngineUIBridge.JustificationSwitch.TRANSITIVE_SIMILAR;
//
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        // Did either of these congruences come from the other?
//        // CTA: We don't need this anymore since use is restricted by class TransitiveSubstitution
//        //if (simTris1.HasRelationPredecessor(simTris2) || simTris2.HasRelationPredecessor(simTris1)) return newGrounded;
//
//        // Create the antecedent clauses
//        List<GroundedClause> antecedent = new List<GroundedClause>();
//        antecedent.Add(simTris1);
//        antecedent.Add(simTris2);
//
//        // Create the consequent clause
//        Triangle shared = simTris1.SharedTriangle(simTris2);
//
//        AlgebraicSimilarTriangles newAP = new AlgebraicSimilarTriangles(simTris1.OtherTriangle(shared), simTris2.OtherTriangle(shared));
//
//        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, newAP, transAnnotation));
//
//        return newGrounded;
//    }
	
	@Override
	public String toString()
	{
		return "Similar(" + st1.toString() + ", " + st2.toString() + ") " + justification;
	}
}
