package backend.ast.Descriptors.Relations.Proportionalities;

import backend.ast.Descriptors.Descriptor;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.ast.figure.components.triangles.Triangle;
import backend.utilities.Pair;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.math.MathUtilities;
import backend.utilities.exception.ExceptionHandler;

public class ProportionalAngles extends Descriptor
{
	protected Angle smallerAngle;
	protected Angle largerAngle;
	protected Pair<Integer, Integer> proportion;
	protected double dictatedProportion;
	
	public Angle getSmallerAngle()
	{
		return smallerAngle;
	}
	public Angle getLargerAngle()
	{
		return largerAngle;
	}
	public Pair<Integer, Integer> getProportion()
	{
		return proportion;
	}
	public double getDictatedProportion()
	{
		return dictatedProportion;
	}
	
	public ProportionalAngles(Angle angle1, Angle angle2)
	{
		super();
		smallerAngle = angle1.getMeasure() < angle2.getMeasure() ? angle1 : angle2;
        largerAngle = angle1.getMeasure() < angle2.getMeasure() ? angle2 : angle1;

        proportion = MathUtilities.RationalRatio(angle1.getMeasure(), angle2.getMeasure());

        // Non-rational ratios which may have arisen due to dual congruenceg implying proportionality
        if (proportion.getKey() == -1 && proportion.getValue() == -1)
        {
            dictatedProportion = angle1.getMeasure() / angle2.getMeasure() < 1 ? angle2.getMeasure() / angle1.getMeasure() : angle1.getMeasure() / angle2.getMeasure();
        }
        else dictatedProportion = (double)(proportion.getKey()) / proportion.getValue();

        if (Utilities.CompareValues(dictatedProportion, 1))
        {
        	ExceptionHandler.throwException(new Exception("An angle Proportion should not be 1:1 " +this.toString()));
        }

        // Reinit the multipliers to basic values
//        smallerAngle.setMultiplier(1);
//        largerAngle.setMultiplier(1);
        //smallerAngle.multiplier = 1;
        //largerAngle.multiplier = 1;
	}
	
	 // Return the number of shared angles in both congruences
    public int SharesNumClauses(CongruentAngles thatCas)
    {
        int numShared = smallerAngle.equals(thatCas.first()) || smallerAngle.equals(thatCas.second()) ? 1 : 0;
        numShared += largerAngle.equals(thatCas.first()) || largerAngle.equals(thatCas.second()) ? 1 : 0;

        return numShared;
    }

    public boolean LinksTriangles(Triangle ct1, Triangle ct2)
    {
        return (ct1.HasAngle(smallerAngle) && ct2.HasAngle(largerAngle)) ||
               (ct1.HasAngle(largerAngle) && ct2.HasAngle(smallerAngle));
    }

    //
    // Compare the numeric proportion between the relations
    //
    public boolean ProportionallyEquals(ProportionalAngles that)
    {
        if (this.proportion.getKey() == -1 && this.proportion.getValue() == -1)
        {
            return Utilities.CompareValues(this.dictatedProportion, that.dictatedProportion);
        }

        return this.proportion.getKey() == that.proportion.getKey() && this.proportion.getValue() == that.proportion.getValue();
    }
    
    @Override
    public boolean structurallyEquals(Object obj)
    {
    	if(obj != null && obj instanceof ProportionalAngles)
    	{
    		ProportionalAngles p = (ProportionalAngles)obj;
    		return smallerAngle.structurallyEquals(p.smallerAngle) && largerAngle.structurallyEquals(p.largerAngle);
    	}
    	
    	//if the null check or instanceof fails it probably should return false
    	return false;
    }

    @Override
    public boolean equals(Object obj)
    {
    	if(obj != null && obj instanceof ProportionalAngles)
    	{
    		ProportionalAngles p = (ProportionalAngles)obj;
    		return smallerAngle.equals(p.smallerAngle) && largerAngle.equals(p.largerAngle) && super.equals(obj);
    	}
    	
    	//if the null check or instanceof fails it probablly should return false
    	return false;
    	
    }

    @Override
    public int getHashCode()
    {
        //Change this if the object is no longer immutable!!!
        return super.getHashCode();
    }

    // Return the shared angle in both congruences
    public Angle AngleShared(CongruentAngles thatCas)
    {
        if (SharesNumClauses(thatCas) != 1) return null;

        return smallerAngle.equals(thatCas.first()) || smallerAngle.equals(thatCas.second()) ? smallerAngle : largerAngle;
    }

    // Return the shared angle in both congruences
    public Angle OtherAngle(Angle thatAngle)
    {
        if (smallerAngle.equals(thatAngle)) return largerAngle;
        if (largerAngle.equals(thatAngle)) return smallerAngle;

        return null;
    }

    @Override
    public String toString()
    {
    	return "Proportional(" + largerAngle.toString() + " < " + dictatedProportion + " > " + smallerAngle.toString() + ") " + justification;
    }
    
    
//    //
//    // Convert an equation to a proportion: 2AM = MC -> Proportional(Angle(A, M), Angle(M, C))        public static List<GenericInstantiator.EdgeAggregator>
//    //
//    private static readonly string ATOM_NAME = "Atomic Angle Equations are Proportional";
//    private static Hypergraph.EdgeAnnotation atomAnnotation = new Hypergraph.EdgeAnnotation(ATOM_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//
//    public static List<GenericInstantiator.EdgeAggregator> Instantiate(GroundedClause clause)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        if (!(clause is AngleEquation)) return newGrounded;
//
//        Equation original = clause as Equation;
//
//        Equation copyEq = (Equation)original.DeepCopy();
//        FlatEquation flattened = new FlatEquation(copyEq.lhs.CollectTerms(), copyEq.rhs.CollectTerms());
//
//        if (flattened.lhsExps.Count == 1 && flattened.rhsExps.Count == 1)
//        {
//            KeyValuePair<int, int> ratio = Utilities.RationalRatio(flattened.lhsExps[0].multiplier, flattened.rhsExps[0].multiplier);
//            if (ratio.Key != -1)
//            {
//                if (ratio.Key <= 2 && ratio.Value <= 2)
//                {
//                    AlgebraicProportionalAngles prop = new AlgebraicProportionalAngles((Angle)flattened.lhsExps[0].DeepCopy(),
//                                                                                       (Angle)flattened.rhsExps[0].DeepCopy());
//
//                    List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(original);
//                    newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, prop, atomAnnotation));
//                }
//            }
//        }
//
//        return newGrounded;
//    }
//
//    private static readonly string PROP_TRANS_NAME = "Angle Proportional / Congruence Transitivity";
//    private static Hypergraph.EdgeAnnotation propAnnotation = new Hypergraph.EdgeAnnotation(PROP_TRANS_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//    public static List<GenericInstantiator.EdgeAggregator> CreateTransitiveProportion(ProportionalAngles pss, CongruentAngles conAngs)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        //// Did either of these proportions come from the other?
//        //if (pss.HasRelationPredecessor(conAngs) || conAngs.HasRelationPredecessor(pss)) return newGrounded;
//
//        //
//        // Create the antecedent clauses
//        //
//        List<GroundedClause> antecedent = new List<GroundedClause>();
//        antecedent.Add(pss);
//        antecedent.Add(conAngs);
//
//        //
//        // Create the consequent clause
//        //
//        Angle shared = pss.AngleShared(conAngs);
//
//        AlgebraicProportionalAngles newPS = new AlgebraicProportionalAngles(pss.OtherAngle(shared), conAngs.OtherAngle(shared));
//
//        // Update relationship among the congruence pairs to limit cyclic information generation
//        //newPS.AddPredecessor(pss);
//        //newPS.AddPredecessor(conAngs);
//
//        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, newPS, propAnnotation));
//
//        return newGrounded;
//    }
//
//    //
//    // Convert a proportion to an equation: Proportional(Angle(A, M), Angle(M, C)) -> 2AM = MC
//    //
//    private static readonly string DEF_NAME = "Defintion of Proportional Angles";
//    private static Hypergraph.EdgeAnnotation defAnnotation = new Hypergraph.EdgeAnnotation(DEF_NAME, EngineUIBridge.JustificationSwitch.SIMILARITY);
//
//    public static List<GenericInstantiator.EdgeAggregator> InstantiateProportion(GroundedClause clause)
//    {
//        List<GenericInstantiator.EdgeAggregator> newGrounded = new List<GenericInstantiator.EdgeAggregator>();
//
//        ProportionalAngles propAngs = clause as ProportionalAngles;
//        if (propAngs == null) return newGrounded;
//
//        // Do not generate equations based on 'forced' proportions
//        if (propAngs.proportion.Key == -1 || propAngs.proportion.Value == -1) return newGrounded;
//
//        // Create a product on the left hand side
//        Multiplication productLHS = new Multiplication(new NumericValue(propAngs.proportion.Key), propAngs.smallerAngle.DeepCopy());
//
//        // Create a product on the right hand side, if it applies.
//        GroundedClause rhs = propAngs.largerAngle.DeepCopy();
//        if (propAngs.proportion.Value > 1)
//        {
//            rhs = new Multiplication(new NumericValue(propAngs.proportion.Key), rhs);
//        }
//
//        //
//        // Create the equation 
//        //
//        Equation newEquation = null;
//        if (propAngs is AlgebraicProportionalAngles)
//        {
//            newEquation = new AlgebraicAngleEquation(productLHS, rhs);
//        }
//        else if (propAngs is GeometricProportionalAngles)
//        {
//            newEquation = new GeometricAngleEquation(productLHS, rhs);
//        }
//
//        List<GroundedClause> antecedent = Utilities.MakeList<GroundedClause>(propAngs);
//        newGrounded.Add(new GenericInstantiator.EdgeAggregator(antecedent, newEquation, defAnnotation));
//
//        return newGrounded;
//    }
}


