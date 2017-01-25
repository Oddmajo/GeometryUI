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

package backend.deductiveRules.angles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AngleBisector;
import backend.ast.Descriptors.Collinear;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.list.Utilities;

/**
 * @author Drew W
 *
 */
public class StraightAngleDefinition extends Definition
{

    private static final String NAME = "Straight Angle Definition";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }
    
    private static Annotation annotation = new Annotation(NAME, RuleFactory.JustificationSwitch.STRAIGHT_ANGLE_DEFINITION);
    public Annotation getAnnotation() { return annotation; }

    public StraightAngleDefinition(QueryableHypergraph<GroundedClause, Annotation> qhg) 
    { 
        super(qhg); 
        annotation.active = RuleFactory.JustificationSwitch.STRAIGHT_ANGLE_DEFINITION;
    }
    
    /* 
     *  I believe this is supposed to get all straight angle clauses from a 
     *  QueryableHyperGraph.  This should be checked for correctness.
     *  @author Drew Whitmire
     *  <p>
     *  @return
     */
    //
    // Collinear(A, B, C, D, ...) -> Angle(A, B, C), Angle(A, B, D), Angle(A, C, D), Angle(B, C, D),...
    // All angles will have measure 180^o
    // There will be nC3 resulting clauses.
    //
    @Override
    public HashSet<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
        deductions.addAll(deduceStraightAngles());
        
        return deductions;
    }
    
    
    /**
     * I am not yet sure how this is supposed to function.  This is where I need 
     * to continue next time I work on the project.
     * @author Drew Whitmire
     * <p>
     * @return
     */
    public HashSet<Deduction> deduceStraightAngles()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();
        
     // Acquire all Midpoint clauses from the hypergraph
        List<Collinear> straightAngles = _qhg.getCollinear();
                
        for (Collinear sa : straightAngles)
        {
           Set<Deduction> returned = deduceFromStraightAngle(sa);
            
           deductions.addAll(returned);
        }
        
        return deductions;
    }

    /**
     * Collinear(A, B, C, D, ...) -> Angle(A, B, C), Angle(A, B, D), Angle(A, C, D), Angle(B, C, D),...
     * All angles will have measure 180^o
     * There will be nC3 resulting clauses.
     * <p>
     * This is (nearly) directly from the C# code.
     * @author Drew Whitmire
     * <p>
     * @param clause
     * @return
     */
    public static HashSet<Deduction> deduceFromStraightAngle(Collinear clause)
    {
       HashSet<Deduction> newGrounded = new HashSet<Deduction>();
        
        if (clause != null)
        {
            Collinear cc = (Collinear) clause;

            for (int i = 0; i < cc.getPoints().size() - 2; i++)
            {
                for (int j = i + 1; j < cc.getPoints().size() - 1; j++)
                {
                    for (int k = j + 1; k < cc.getPoints().size(); k++)
                    {
                        Angle newAngle = new Angle(cc.getPoints().get(i), cc.getPoints().get(j), cc.getPoints().get(k));
                        ArrayList<GroundedClause> antecedent = Utilities.makeList(cc);
                        newGrounded.add(new Deduction(antecedent, newAngle, annotation));
                    }
                }
            }
        }
        
        return newGrounded;
    }

}
