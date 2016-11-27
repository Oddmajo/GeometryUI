package backend.deductiveRules.generalRules;

import backend.ast.GroundedClause;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public abstract class Definition extends GeometryRule
{
    public Definition(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }
}
