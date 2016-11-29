package backend.deductiveRules.generalRules;

import backend.ast.GroundedClause;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public abstract class Axiom extends GeometryRule
{
    public Axiom(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
    }
}
