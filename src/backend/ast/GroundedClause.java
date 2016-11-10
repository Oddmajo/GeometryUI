/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.ast;

import java.util.ArrayList;
import java.util.Scanner;

import backend.utilities.list.Utilities;
import backend.ast.ASTException;
import backend.utilities.exception.*;

/**
 * A First Order Logic clause that describes a property about a geometric drawing
 * @author Chris Alvin
 * @author Drew Whitmire
 */
public abstract class GroundedClause implements Cloneable
{
    
    // A unique integer identifier (from the hypergraph)
    private int clauseId;
    @SuppressWarnings("unused")
    private void setID(int id) { clauseId = id; }
    public int getID() { return clauseId; }

    // Intrinsic as defined theoretically: characteristics of a figure that cannot be proven.
    private boolean intrinsic;
    public boolean isIntrinsic() { return intrinsic; }
    public void makeIntrinsic() { intrinsic = true; mayBeSourceNode = false; }

    // For problem generation, indicate if this node is given 
    private boolean given;
    public boolean isGiven() { return given; }
    public void makeGiven() { given = true; }

    // Denotes: A + A -> A
    private boolean purelyAlgebraic = false;
    public boolean isPurelyAlgebraic() { return purelyAlgebraic; }
    public void makePurelyAlgebraic() { purelyAlgebraic = true; }

    // Contains all predecessors
    private ArrayList<Integer> generalPredecessors;
    public ArrayList<Integer> getGeneralPredecessors() { return generalPredecessors; }
    
    // Contains only Relation-based predecessors
    private ArrayList<Integer> relationPredecessors;
    public ArrayList<Integer> getRelationPredecessors() { return relationPredecessors; }

    public boolean hasRelationPredecessor(GroundedClause gc) { return relationPredecessors.contains(gc.clauseId); }
    public boolean hasGeneralPredecessor(GroundedClause gc) { return generalPredecessors.contains(gc.clauseId) || relationPredecessors.contains(gc.clauseId); }

    // Contains all figure fact predecessor / components (e.g. a triangle has 3 segments, 3 angles, and 3 points, etc) 
    private ArrayList<Integer> figureComponents;
    public ArrayList<Integer> getFigureComponents() { return figureComponents; }
    public void addComponent(int component) {Utilities.addUnique(figureComponents, component); }
    public void addComponentList(ArrayList<Integer> componentList) {Utilities.AddUniqueList(figureComponents, componentList); }
    

    // Can this node be strengthened to the given node?
    public boolean canBeStrengthenedTo(GroundedClause gc) { return false; }
    // For problems: if a theorem or result is obvious and should never be a real source node for a problem

    private boolean mayBeSourceNode = true;
    public void setNotASourceNode() { mayBeSourceNode = false; }
    public boolean isAbleToBeASourceNode() { return mayBeSourceNode; }

    private boolean mayBeGoalNode = true;
    public void setNotAGoalNode() { mayBeGoalNode = false; }
    public boolean isAbleToBeAGoalNode() { return !intrinsic && mayBeGoalNode; }

    private boolean isObviousDefinition = false;
    public void setClearDefinition() { isObviousDefinition = true; }
    public boolean isClearDefinition() { return isObviousDefinition; }
    
   
    
    /**
     * I am commenting this out because I am currently working on FacetIdentification
     * and I do not need to figure out where the definition of Action is right now
     * @param write
     * @author Drew Whitmire
     */
    /*
    public void DumpXML(Action<String, ArrayList<GroundedClause>> write)
    {
        write("TBD", new ArrayList<GroundedClause>());
        
    }
    */

    public void addRelationPredecessor(GroundedClause gc)
    {
        if (gc.clauseId == -1)
        {
            //Debug.WriteLine("ERROR: id is -1: " + gc.toString());
            ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + gc.toPrettyString()));
        }
        Utilities.addUnique(relationPredecessors, gc.clauseId);
    }
    public void addGeneralPredecessor(GroundedClause gc)
    {
        if (gc.clauseId == -1)
        {
            ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + gc.toPrettyString()));
        }
        Utilities.addUnique(generalPredecessors, gc.clauseId);
    }
    public void addRelationPredecessors(ArrayList<Integer> preds)
    {
        for (int pred : preds)
        {
            if (pred == -1)
            {
                ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + pred));
            }
            Utilities.addUnique(relationPredecessors, pred);
        }
    }
    public void addGeneralPredecessors(ArrayList<Integer> preds)
    {
        for (int pred : preds)
        {
            if (pred == -1)
            {
                ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + pred));
            }
            Utilities.addUnique(generalPredecessors, pred);
        }
    }

    private boolean axiomatic;
    public boolean isAxiomatic() { return axiomatic; }
    public void makeAxiomatic() { axiomatic = true; mayBeSourceNode = false; }
    public boolean isAlgebraic() { return false; } // By default we will say a node is geometric
    public boolean isGeometric() { return true; }  //  and not algebraic
    public boolean isReflexive() { return false; }
    public boolean strengthened() { return false; }

    public GroundedClause()
    {
        justification = "";
        multiplier = 1;
        clauseId = -1;
        axiomatic = false;
        generalPredecessors = new ArrayList<Integer>();
        relationPredecessors = new ArrayList<Integer>();
        figureComponents = new ArrayList<Integer>();
    }

    // The justification for when a node is deduced
    protected String justification;
    public String getJustification() { return justification; }
    public void setJustification(String j) { justification = j; }

    //
    // For equation simplification
    //
    private int multiplier;
    public int getMulitplier() { return multiplier; }
    public void setMultiplier(int x) { multiplier = x; }
    
    public ArrayList<GroundedClause> collectTerms()
    {
        return new ArrayList<GroundedClause>(Utilities.makeList(this));
    }
    
    
    public boolean equals(Object obj)
    {
        if (obj == null || (GroundedClause)obj == null)
            return false;
        GroundedClause that = (GroundedClause) obj;
        return multiplier == that.multiplier; // && clauseId == that.clauseId;
    }

    public boolean structurallyEquals(Object obj) { return false; }

    //
    // For substitution and algebraic Simplifications
    //
    public boolean containsClause(GroundedClause clause)
    {
        if (this.equals(clause))
            return true;
        return false;
     }
    public void substitute(GroundedClause c1, GroundedClause c2)  {  }
    
    public GroundedClause deepCopy()
    { 
            try
            {
                return  (GroundedClause) this.clone();
            }
            catch (CloneNotSupportedException e)
            {
                ExceptionHandler.throwException(e);
            } 
            return this;
        
    }

    public int getHashCode() { return super.hashCode(); }

    public String toString()
    {
        return this.toString();
    }
    
    public String toPrettyString()
    {
        return this.toString();
    }
    
    


}
