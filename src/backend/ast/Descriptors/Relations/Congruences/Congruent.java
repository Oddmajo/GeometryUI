///*
// * iTutor – an intelligent tutor of mathematics
//
//Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of
//
//students)
//
//This program is free software: you can redistribute it and/or modify it under
//
//the terms of the GNU Affero General Public License as published by the Free
//
//Software Foundation, either version 3 of the License, or (at your option) any
//
//later version.
//
//This program is distributed : the hope that it will be useful, but WITHOUT
//
//ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
//
//FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
//
//details.
// */
///**
// * @author Tom_Nielsen
// *
// */
package backend.ast.Descriptors.Relations.Congruences;

import java.util.ArrayList;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Descriptor;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;

public abstract class Congruent extends Descriptor
{
	public Congruent()
	{
		super();
	}
	
	
	//the original code had virtual, and that clearly isn't a keyword in java
	public int SharesNumClauses()
	{
		return 0;
	}
	
	
	
//	private static String NAME = "Transitivity";
//	private static Hypergraph.EdgeAnnotation annotation = new Hypergraph.EdgeAnnotation(NAME, EngineUIBridge.JustificationSwitch.TRANSITIVE_SUBSTITUTION);
//	
//	 public static ArrayList<GenericInstantiator.EdgeAggregator> CreateTransitiveCongruence(Congruent congruent1, Congruent congruent2)
//	 {
//		 ArrayList<GenericInstantiator.EdgeAggregator> newGrounded = new ArrayList<GenericInstantiator.EdgeAggregator>();
//		 
//		 //create the antecedent clauses
//		 ArrayList<GroundedClause> antecedent = new ArrayList<GroundedClause>();
//		 antecedent.add(congruent1);
//		 antecedent.add(congruent2);
//		 
//		 //create the consequent clause
//		 Congruent newCC = null;
//		 if(congruent1 != null && congruent1 instanceof CongruentSegments)
//		 {
//			 CongruentSegments css1 = (CongruentSegments)congruent1;
//			 CongruentSegments css2 = (CongruentSegments)congruent2;
//			 
//			 Segment shared = css1.SegmentShared(css2);
//			 
//			 newCC = new AlgebraicCongruentSegments(css1.OtherSegment(shared), css2.OtherSegment(shared));
//		 }
//		 else if(congruent1 instanceof CongruentAngles)
//		 {
//			 CongruentSegments css1 = (CongruentSegments)congruent1;
//			 CongruentSegments css2 = (CongruentSegments)congruent2;
//			 
//			 Angle shared = css1.AngleShared(css2);
//			 newCC = new AlgebraicCongruentAngles(css1.OtherAngle(shared), css2.OtherAngle(shared));
//		 }
//		 
//		 if(newCC == null)
//		 {
//			 System.Diagnostics.Debug.WriteLine("");
//			 ExceptionHandler("Unexpected problem in Atomic Subsitution...");
//		 }
//		 
//		 newGrounded.add(new GenericInstantiator.EdgeAggregator(antecedent, newCC, annotation));
//		 
//		 return newGrounded;
//	 }
}
