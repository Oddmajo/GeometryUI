The Queryable Hypergraph (QHG) is overwhelming at first, but it is not bad to 
work with you understand how it works.

The two pieces that really need explanation are adding to containers (node
addition) and retrieving from containers (edge addition). Explanations are
as follows:

ADDING NODES: addToQueryableContainers
	This is what is called in addNode.  It decides which container
	the given node (grounded clause or fact) will be added to.  NODES
	ARE ONLY ADDED TO THE MOST SPECIFIC CONTAINER AVAILABLE. 
	For example: Midpoint extends InMiddles.
		Given a Midpoint object, the system will add the grounded
		clause to the Midpoint container, but NOT the InMiddles
		container.

ADDING EDGES: retrieval from containers
	An edge is created from a Deduction, which is created from an Axiom,
	Theorem, or Definition.

	The source GroundedClause(s) (antecedents) in the deduction are 
	existing nodes from the QHG.  The target GroundedClause (consequent)
	(always singular) should exist in the hypergraph already as a node,
	but is a STRUCTURALLY EQUAL copy.  This means the consequent has the 
	same information, but it does not contain the clauseID needed to add 
	an edge to that node in the QHG.

	Because of this, we must find the 'real' node that exists in the QHG
	to add an edge to it.  We check against each container if the target
	is an instance of the type of grounded clause in the container; if so,
	we check any more specific containers.  If a match is made on a most 
	specific container possible, we check if the node is structurally equal
	to all of the nodes within that container until we get a match.  If a 
	match is not made on a more specific container, we check structural 
	equality on the nodes in the container until we find a match.

Equations are handled in the same way as GroundedClauses, but there is a 
separate class that keeps track of them (EquationQueryHandler).