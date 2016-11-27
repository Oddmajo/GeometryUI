package backend.deductiveRules.generalRules;

import backend.ast.GroundedClause;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public abstract class GeometryRule implements Rule
{
    protected QueryableHypergraph<GroundedClause, Annotation> _qhg;
    
    public GeometryRule(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        _qhg = qhg;
    }
}
