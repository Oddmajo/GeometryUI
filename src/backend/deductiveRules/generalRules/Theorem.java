package backend.deductiveRules.generalRules;

import backend.ast.GroundedClause;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public abstract class Theorem extends GeometryRule
{
    public Theorem(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }
}
