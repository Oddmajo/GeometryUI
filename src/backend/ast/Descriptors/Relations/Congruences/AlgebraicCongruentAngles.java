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

import backend.ast.figure.components.angles.Angle;

//
// This class has two roles:
// 1) Congruent Pair of Angles
// 2) To avoid redundancy and bloat in the hypergraph, it also mimics a basic geometric equation
// So \angle ABC \cong \angle DEF also means m\angle ABC = m\angle DEF (as a GEOMETRIC equation)
//
public class AlgebraicCongruentAngles extends CongruentAngles
{
    public AlgebraicCongruentAngles(Angle a1, Angle a2)
    {
        super(a1, a2);
    }

    @Override public boolean isAlgebraic() { return true; }
    @Override public boolean isGeometric() { return false; }

    @Override
    public String toString()
    {
        return "AlgebraicCongruent(" + ca1.toString() + ", " + ca2.toString() + ") " + justification;
    }	
}
