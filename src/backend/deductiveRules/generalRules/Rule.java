package backend.deductiveRules.generalRules;

import java.util.Set;

import backend.ast.GroundedClause;
import backend.deductiveRules.Deduction;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public interface Rule
{
	// Each rule must return a set of deduction (that correspond directly to hyperedges)
    Set<Deduction> deduce();
    
    // Each rule should have a shorthand name
    String getName();
    
    // Each rule should have a longer description
    String getDescription();
    
    // Each rule defines an annotation; this annotation is static to each class.
    Annotation getAnnotation();
}
