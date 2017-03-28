package backend.ast.Descriptors.Relations.Proportionalities;

import backend.ast.Descriptors.Descriptor;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.math.MathUtilities;

/// <summary>
/// Describes a point that lies on a segmant.
/// </summary>
public class SegmentRatio extends Descriptor
{
	protected Segment smallerSegment;
	protected Segment largerSegment;
	protected Pair<Integer,Integer> proportion;
	protected double dictatedProportion;
	protected boolean knownProportion;
	
	public boolean ProportionValueKnown()
	{
		return knownProportion;
	}
	public void MakeProportionValueKnown()
	{
		knownProportion = true;
	}
	public Segment getSmallerSegment()
	{
		return smallerSegment;
	}
	public Segment getLargerSegment()
	{
		return largerSegment;
	}
	public Pair<Integer,Integer> getProportion()
	{
		return proportion;
	}
	public double getDictatedProportion()
	{
		return dictatedProportion;
	}
	
	public SegmentRatio(Segment segment1, Segment segment2)
	{
		super();
		smallerSegment = segment1.length() < segment2.length() ? segment1 : segment2;
		largerSegment = segment1.length() < segment2.length() ? segment2 : segment1;

        proportion = MathUtilities.RationalRatio(segment1.length(), segment2.length());
     // A similar triangle may induce proportional segments even though the triangles are congruent
        //if (proportion.Key == 1 && proportion.Value == 1)
        //{
        //    throw new Exception("A segment proportion should not be 1:1 " + this.ToString());
        //}

        // Non-rational ratios which may have arisen due to dual congruenceg implying proportionality
        if(proportion.getKey() == -1 && proportion.getValue() == -1)
        {
        	dictatedProportion = segment1.length() / segment2.length() < 1 ? segment2.length() /segment1.length() : segment1.length() / segment2.length();
        }
        else
        {
        	dictatedProportion = (double)(proportion.getKey()) / proportion.getValue();
        }
        
        // A similar triangle may induce proportional segments even though the triangles are congruent
        //if (Utilities.CompareValues(dictatedProportion, 1))
        //{
        //    throw new Exception("A segment proportion should not be 1:1 " + this.ToString());
        //}

        // Reinit the multipliers to basic values
//        smallerSegment.multiplier = 1;
//        largerSegment.multiplier = 1;
        //smallerSegment.setMultiplier(1);
        //largerSegment.setMultiplier(1);

        knownProportion = false;
	}
	
//	// Return the number of shared segments in both congruences
//    public int SharesNumClauses(CongruentSegments thatCS)
//    {
//        //CongruentSegments css = thatPS as CongruentSegments;
//
//        //if (css == null) return 0;
//
//        int numShared = smallerSegment.Equals(thatCS.cs1) || smallerSegment.Equals(thatCS.cs2) ? 1 : 0;
//        numShared += largerSegment.Equals(thatCS.cs1) || largerSegment.Equals(thatCS.cs2) ? 1 : 0;
//
//        return numShared;
//    }
//
    public Boolean HasSegment(Segment that)
    {
        return smallerSegment.structurallyEquals(that) || largerSegment.structurallyEquals(that);
    }

    public Boolean LinksTriangles(Triangle ct1, Triangle ct2)
    {
        return (ct1.HasSegment(smallerSegment) && ct2.HasSegment(largerSegment)) ||
               (ct1.HasSegment(largerSegment) && ct2.HasSegment(smallerSegment));
    }

    //
    // Compare the numeric proportion between the relations
    //
    public Boolean ProportionallyEquals(SegmentRatio that)
    {
        if (this.proportion.getKey() == -1 && this.proportion.getKey() == -1)
        {
            return Utilities.CompareValues(this.dictatedProportion, that.dictatedProportion);
        }

        return this.proportion.getKey() == that.proportion.getKey() && this.proportion.getKey() == that.proportion.getKey();
    }

    @Override
    public boolean structurallyEquals(Object obj)
    {
        SegmentRatio p = (SegmentRatio)obj;
        if (p == null) return false;
        return smallerSegment.structurallyEquals(p.smallerSegment) && largerSegment.structurallyEquals(p.largerSegment);
    }

    @Override
    public boolean equals(Object obj)
    {
        SegmentRatio p = (SegmentRatio)obj;
        if (p == null) return false;
        return smallerSegment.equals(p.smallerSegment) && largerSegment.equals(p.largerSegment) && super.equals(obj);
    }
//
//    public override int GetHashCode() { return base.GetHashCode(); }
//
//    public bool IsDistinctFrom(SegmentRatio thatProp)
//    {
//        if (this.smallerSegment.StructurallyEquals(thatProp.smallerSegment)) return false;
//        if (this.largerSegment.StructurallyEquals(thatProp.largerSegment)) return false;
//
//        return true;
//    }
//
//    // Return the shared segment in both congruences
//    public Segment SegmentShared(CongruentSegments thatCC)
//    {
//        if (SharesNumClauses(thatCC) != 1) return null;
//
//        return smallerSegment.Equals(thatCC.cs1) || smallerSegment.Equals(thatCC.cs2) ? smallerSegment : largerSegment;
//    }
//
//    // Return the shared segment in both congruences
//    public Segment OtherSegment(Segment thatSegment)
//    {
//        if (smallerSegment.Equals(thatSegment)) return largerSegment;
//        if (largerSegment.Equals(thatSegment)) return smallerSegment;
//
//        return null;
//    }
//
	@Override
    public String toString()
    {
        if (knownProportion)
        {
            return largerSegment.toString() + " / " + smallerSegment.toString() + " = " + dictatedProportion;
        }

        return "Ratio(" + largerSegment.toString() + " / " + smallerSegment.toString() + ") ";
    }

    //
    // Convert an equation to a proportion: 2AM = MC -> Proportional(Segment(A, M), Segment(M, C))
    //
    //
//    private static readonly string ATOM_NAME = "Atomic Segment Equations are Proportional";
//    private static Hypergraph.EdgeAnnotation atomAnnotation = new Hypergraph.EdgeAnnotation(ATOM_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//
//    public static List<GenericInstantiator.EdgeAggregator> InstantiateEquation(GroundedClause clause)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        if (!(clause is SegmentEquation)) return newGrounded;
//
//        Equation original = clause as Equation;
//
//        Equation copyEq = (Equation)original.DeepCopy();
//        FlatEquation flattened = new FlatEquation(copyEq.lhs.CollectTerms(), copyEq.rhs.CollectTerms());
//
//        if (flattened.lhsExps.Count != 1 || flattened.rhsExps.Count != 1) return newGrounded;
//
//        KeyValuePair<int, int> ratio = Utilities.RationalRatio(flattened.lhsExps[0].multiplier, flattened.rhsExps[0].multiplier);
//        if (ratio.Key != -1)
//        {
//            if (ratio.Key <= 2 && ratio.Value <= 2)
//            {
//                SegmentRatio prop = new SegmentRatio((Segment)flattened.lhsExps[0].DeepCopy(),
//                                                     (Segment)flattened.rhsExps[0].DeepCopy());
//                prop.MakeProportionValueKnown();
//
//                List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(original);
//                newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, prop, atomAnnotation));
//            }
//        }
//
//        return newGrounded;
//    }
//
//    private static readonly string PROP_TRANS_NAME = "Segment Proportionality Substitution";
//    private static Hypergraph.EdgeAnnotation propAnnotation = new Hypergraph.EdgeAnnotation(PROP_TRANS_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//    public static List<GenericInstantiator.EdgeAggregator> CreateProportionEquation(SegmentRatio ratio1, SegmentRatio ratio2)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        // Double-Check that the ratios are, in-fact, known.
//        if (!ratio1.ProportionValueKnown() || !ratio2.ProportionValueKnown()) return newGrounded;
//
//        //
//        // Create the antecedent clauses
//        //
//        List<GroundedClause> antecedent = new List<GroundedClause>();
//        antecedent.Add(ratio1);
//        antecedent.Add(ratio2);
//
//        // Create the consequent proportionality equation.
//        GeometricSegmentRatioEquation gsreq = new GeometricSegmentRatioEquation(ratio1, ratio2);
//
//        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, gsreq, propAnnotation));
//
//        return newGrounded;
//    }
}
