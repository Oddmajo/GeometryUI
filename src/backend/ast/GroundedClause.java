/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package backend.ast;

import java.util.ArrayList;

/**
 * A First Order Logic clause that describes a property about a geometric drawing
 * @author Chris Alvin
 * @author Drew Whitmire
 */
public abstract class GroundedClause
{
    
    // A unique integer identifier (from the hypergraph)
    private int clauseId;
    @SuppressWarnings("unused")
    private void SetID(int id) { clauseId = id; }
    public int GetID() { return clauseId; }

    // Intrinsic as defined theoretically: characteristics of a figure that cannot be proven.
    private boolean intrinsic;
    public boolean IsIntrinsic() { return intrinsic; }
    public void MakeIntrinsic() { intrinsic = true; mayBeSourceNode = false; }

    // For problem generation, indicate if this node is given 
    private boolean given;
    public boolean IsGiven() { return given; }
    public void MakeGiven() { given = true; }

    // Denotes: A + A -> A
    private boolean purelyAlgebraic = false;
    public boolean IsPurelyAlgebraic() { return purelyAlgebraic; }
    public void MakePurelyAlgebraic() { purelyAlgebraic = true; }

    // Contains all predecessors
    private ArrayList<Integer> generalPredecessors;
    public ArrayList<Integer> GetGeneralPredecessors() { return generalPredecessors; }
    
    // Contains only Relation-based predecessors
    private ArrayList<Integer> relationPredecessors;
    public ArrayList<Integer> GetRelationPredecessors() { return relationPredecessors; }

    public boolean HasRelationPredecessor(GroundedClause gc) { return relationPredecessors.contains(gc.clauseId); }
    public boolean HasGeneralPredecessor(GroundedClause gc) { return generalPredecessors.contains(gc.clauseId) || relationPredecessors.contains(gc.clauseId); }

    // Contains all figure fact predecessor / components (e.g. a triangle has 3 segments, 3 angles, and 3 points, etc) 
    private ArrayList<Integer> figureComponents;
    public ArrayList<Integer> GetFigureComponents() { return figureComponents; }
    public void AddComponent(int component) { backend.utilities.ast_helper.Utilities.AddUnique(figureComponents, component); }
    public void AddComponentList(ArrayList<Integer> componentList) { backend.utilities.ast_helper.Utilities.AddUniqueList(figureComponents, componentList); }


    // Can this node be strengthened to the given node?
    public boolean CanBeStrengthenedTo(GroundedClause gc) { return false; }
    // For problems: if a theorem or result is obvious and should never be a real source node for a problem

    private boolean mayBeSourceNode = true;
    public void SetNotASourceNode() { mayBeSourceNode = false; }
    public boolean IsAbleToBeASourceNode() { return mayBeSourceNode; }

    private boolean mayBeGoalNode = true;
    public void SetNotAGoalNode() { mayBeGoalNode = false; }
    public boolean IsAbleToBeAGoalNode() { return !intrinsic && mayBeGoalNode; }

    private boolean isObviousDefinition = false;
    public void SetClearDefinition() { isObviousDefinition = true; }
    public boolean IsClearDefinition() { return isObviousDefinition; }
    
    
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

    public void AddRelationPredecessor(GroundedClause gc)
    {
        if (gc.clauseId == -1)
        {
            //Debug.WriteLine("ERROR: id is -1: " + gc.ToString());
            backend.utilities.exception.ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + gc.toPrettyString()));
        }
        backend.utilities.ast_helper.Utilities.AddUnique(relationPredecessors, gc.clauseId);
    }
    public void AddGeneralPredecessor(GroundedClause gc)
    {
        if (gc.clauseId == -1)
        {
            backend.utilities.exception.ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + gc.toPrettyString()));
        }
        backend.utilities.ast_helper.Utilities.AddUnique(generalPredecessors, gc.clauseId);
    }
    public void AddRelationPredecessors(ArrayList<Integer> preds)
    {
        for (int pred : preds)
        {
            if (pred == -1)
            {
                backend.utilities.exception.ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + pred));
            }
            backend.utilities.ast_helper.Utilities.AddUnique(relationPredecessors, pred);
        }
    }
    public void AddGeneralPredecessors(ArrayList<Integer> preds)
    {
        for (int pred : preds)
        {
            if (pred == -1)
            {
                backend.utilities.exception.ExceptionHandler.throwException(new ASTException("ERROR: id is -1: " + pred));
            }
            backend.utilities.ast_helper.Utilities.AddUnique(generalPredecessors, pred);
        }
    }

    private boolean axiomatic;
    public boolean IsAxiomatic() { return axiomatic; }
    public void MakeAxiomatic() { axiomatic = true; mayBeSourceNode = false; }
    public boolean IsAlgebraic() { return false; } // By default we will say a node is geometric
    public boolean IsGeometric() { return true; }  //  and not algebraic
    public boolean IsReflexive() { return false; }
    public boolean Strengthened() { return false; }

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
    public String GetJustification() { return justification; }
    public void SetJustification(String j) { justification = j; }

    //
    // For equation simplification
    //
    private int multiplier;
    public int getMulitplier() { return multiplier; }
    
    public ArrayList<GroundedClause> CollectTerms()
    {
        return new ArrayList<GroundedClause>(backend.utilities.ast_helper.Utilities.MakeList(this));
    }
    
    
    public boolean Equals(Object obj)
    {
        GroundedClause that = (GroundedClause) obj;
        if (that == null) return false;
        return multiplier == that.multiplier; // && clauseId == that.clauseId;
    }

    public boolean StructurallyEquals(Object obj) { return false; }

    //
    // For substitution and algebraic Simplifications
    //
    public boolean ContainsClause(GroundedClause clause) { return false; }
    public void Substitute(GroundedClause c1, GroundedClause c2) { }
    public GroundedClause DeepCopy() throws CloneNotSupportedException { return (GroundedClause)this.clone(); }

    public int GetHashCode() { return super.hashCode(); }

    public abstract String toString();

    public String toPrettyString() { return Integer.toString(clauseId); }

}
