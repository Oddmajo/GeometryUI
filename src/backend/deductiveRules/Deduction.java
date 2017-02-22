package backend.deductiveRules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.hypergraph.Annotation;
import backend.utilities.list.Utilities;

// @author C. Alvin
//
// An aggregation class that corresponds directly to a hyperedge
//   * set of source nodes
//   * target node
//   * annotation
//
public class Deduction
{
    protected Set<GroundedClause> _antecedent;
    public Set<GroundedClause> getAntecedent() { return _antecedent; }
    
    protected GroundedClause _consequent;
    public GroundedClause getConsequent() { return _consequent; }
    
    protected Annotation _annotation;
    public Annotation getAnnotation() { return _annotation; }

    public Deduction(Set<GroundedClause> antecedent, GroundedClause consequent, Annotation annotation)
    {
        _antecedent = antecedent;
        _consequent = consequent;
        _annotation = annotation;
    }
    
    public Deduction(List<GroundedClause> antecedent, GroundedClause consequent, Annotation annotation)
    {
        _antecedent = new HashSet<GroundedClause>(antecedent);
        _consequent = consequent;
        _annotation = annotation;
    }

    public Deduction(GroundedClause antecedent, GroundedClause consequent, Annotation annotation)
    {
        this(Utilities.makeList(antecedent), consequent, annotation);
    }

    public Deduction(Deduction that)
    {
        this(that._antecedent, that._consequent, that._annotation);
    }
    
    public boolean addAntecedent(GroundedClause ante)
    {
        return _antecedent.add(ante);
    }


    //
    // Critical equality check for deductions
    //    Must be fast and accurate
    //
    @Override
    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (!(o instanceof Deduction)) return false;
        Deduction that = (Deduction)o;

        if (this._antecedent.size() != that._antecedent.size()) return false;

        if (!_annotation.equals(that._annotation)) return false;

        if (!_consequent.equals(that._consequent)) return false;

        //
        // Check the antecedents align; set-based comparison
        //
        return _antecedent.equals(that._antecedent);
    }
}
