package backend.instantiator;

import backend.equations.*;

import java.util.ArrayList;
import java.util.List;
import backend.hypergraph.*;
import backend.ast.GroundedClause;

public class EdgeAggregator
{
    private List<GroundedClause> antecedent;
    
    public List<GroundedClause> getAntecedent()
    {
        return antecedent;
    }
    
    public GroundedClause consequent;
    
    public GroundedClause getConsequent()
    {
        return consequent;
    }
   
    private EdgeAnnotation annotation;
    
    public EdgeAnnotation getAnnotation()
    {
        return annotation;
    }
    
    
    public EdgeAggregator(List<GroundedClause> antes, GroundedClause c, EdgeAnnotation ann)
    {
        antecedent = new ArrayList<GroundedClause>(antes);
        consequent = c;
        annotation = ann;
    }
}
