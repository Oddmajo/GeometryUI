//
// iTutor – an intelligent tutor of mathematics
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
// 
///
//  @author Tom_Nielsen
// 
// 
package backend.ast.Descriptors;

import backend.ast.GroundedClause;

/// <summary>
/// Describes a figure being strengthened.
/// </summary>

public class Strengthened extends Descriptor
{
    private GroundedClause original;
    private GroundedClause strengthened;
    
    public GroundedClause getOriginal()
    {
        return original;
    }
    public GroundedClause getStrengthened()
    {
        return strengthened;
    }
    
    public Strengthened(GroundedClause orig, GroundedClause streng)
    {
        super();
        original = orig;
        strengthened = streng;
    }
    
    @Override
    public int GetHashCode()
    {
        return super.GetHashCode();
    }
    
    
    @Override
    public boolean StructurallyEquals(Object obj)
    {
        if(obj != null && obj instanceof Strengthened)
        {
            Strengthened s = (Strengthened)obj;
         // should be uneeded but left it in since it was in the original
//            if(s == null)
//            {
//                return false;
//            }
            return this.getOriginal().StructurallyEquals(s.getOriginal()) && this.getStrengthened().StructurallyEquals(s.getStrengthened());
        }
        else
        {
            return false;
        }
        
    }
    
    
    /// this still needs the function getType defined which is not currently defines
    /// please remove when this comment when this is completed
    @Override
    public boolean Equals(Object obj)
    {
        if(obj != null)
        {
            Strengthened thatS = (Strengthened)obj;
            if(thatS == null)
            {
                return false;
            }
            return this.getOriginal().Equals(thatS.getOriginal()) && this.getStrengthened().getType() == thatS.getStrengthened().getType();
        }
    }
    
    @Override
    public String toString()
    {
        return "Strengthened(" + getOriginal().toString() + " -> " + getStrengthened().toString() + ") " + justification;
    }
}
