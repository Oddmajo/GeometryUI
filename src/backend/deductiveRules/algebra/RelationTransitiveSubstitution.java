package backend.deductiveRules.algebra;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Relations.AlgebraicSimilarTriangles;
import backend.ast.Descriptors.Relations.GeometricSimilarTriangles;
import backend.ast.Descriptors.Relations.SimilarTriangles;
import backend.ast.Descriptors.parallel.AlgebraicParallel;
import backend.ast.Descriptors.parallel.GeometricParallel;
import backend.ast.Descriptors.parallel.Parallel;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Definition;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RelationTransitiveSubstitution extends Definition
{
    private static final String NAME = "Relation Transitive Substitution";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.RELATION_TRANSITIVE_SUBSTITUTION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

      // Transitivity of Parallel Lines
      private static HashSet<GeometricParallel> geoParallel;
      private static HashSet<AlgebraicParallel> algParallel;

      // Transitvity of Similar Triangles
      private static HashSet<GeometricSimilarTriangles> geoSimilarTriangles;
      private static HashSet<AlgebraicSimilarTriangles> algSimilarTriangles;


    public RelationTransitiveSubstitution(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.RELATION_TRANSITIVE_SUBSTITUTION;
        
        geoParallel = _qhg.getGeometricParallels();
        algParallel = _qhg.getAlgebraicParallels();
        geoSimilarTriangles = _qhg.getGeometricSimilarTriangles();
        algSimilarTriangles = _qhg.getAlgebraicSimilarTriangles();
    }
    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<>();
        deductions.addAll(duduceEquations());
        return deductions;
    }

    //
    // Implements transitivity with Relations (Parallel, Similar)
    // Relation(A, B), Relation(B, C) -> Relation(A, C)
    //
    // Generation of new relations instanceof restricted to the following rules; let G be Geometric and A algebriac
    //     G + G -> A
    //     G + A -> A
    //     A + A -X> A  <- Not allowed
    //
    public static ArrayList<Deduction> duduceEquations()
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();
        
        HashSet<Parallel> paras = _qhg.getParallels();
        HashSet<SimilarTriangles> simTris = _qhg.getSimilarTriangles();

        // Has this clause been generated before?
        // Since generated clauses will eventually be instantiated as well, this will reach a fixed point and stop.
        // Uniqueness of clauses needs to be handled by the class calling this
//        if (ClauseHasBeenDeduced(clause)) return deductions; --Checked : loops

        // A reflexive expression provides no information of interest or consequence.
//        if (clause.IsReflexive()) return deductions;  --Checked : loops

        //
        // Process the clause
        //
        
        for(Parallel para : paras)
        {
            if(!para.isReflexive())
            {
                deductions.addAll(HandleNewParallelRelation(para));
            }
        }
        
        for(SimilarTriangles simTri : simTris)
        {
            if(!simTri.isReflexive())
            {
                deductions.addAll(HandleNewSimilarTrianglesRelation(simTri));
            }
        }
            

        // Add the new clause to the right list for later combining
//        AddToAppropriateList(clause);

        return deductions;
    }

    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of Parallel Segments
    //
    private static ArrayList<Deduction> HandleNewParallelRelation(Parallel parallel)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricParallel gp : geoParallel)
        {
            deductions.addAll(CreateTransitiveParallelSegments(gp, parallel));
        }

        if (parallel instanceof GeometricParallel)
        {
            // New transitivity? G + A -> A
            for (AlgebraicParallel ap : algParallel)
            {
                deductions.addAll(CreateTransitiveParallelSegments(ap, parallel));
            }
        }

        return deductions;
    }

    //
    // For generation of transitive Parallel Lines
    //
    private static ArrayList<Deduction> CreateTransitiveParallelSegments(Parallel parallel1, Parallel parallel2)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // If there instanceof a deduction relationship between the given congruences, do not perform another substitution
        // CTA: remove?
        if (parallel1.hasGeneralPredecessor(parallel2))
        {
            return deductions;
        }

        int numSharedExps = parallel1.SharesNumClauses(parallel2);
        switch (numSharedExps)
        {
            case 0:
                // Nothing instanceof shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                return Parallel.CreateTransitiveParallel(parallel1, parallel2);

            case 2:
                // This instanceof either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;
            
            default:

                throw new Exception("Parallel Statements may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return deductions;
    }


    //
    // Generate all new relationships from a Geoemetric, Congruent Pair of SimilarTriangles Segments
    //
    private static ArrayList<Deduction> HandleNewSimilarTrianglesRelation(SimilarTriangles simTris)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // New transitivity? G + G -> A
        for (GeometricSimilarTriangles gsts : geoSimilarTriangles)
        {
            deductions.addAll(CreateTransitiveSimilarTriangles(gsts, simTris));
        }

        if (simTris instanceof GeometricSimilarTriangles)
        {
            // New transitivity? G + A -> A
            for (AlgebraicSimilarTriangles asts : algSimilarTriangles)
            {
                deductions.addAll(CreateTransitiveSimilarTriangles(asts, simTris));
            }
        }

        return deductions;
    }

    //
    // For generation of transitive SimilarTriangles Lines
    //
    private static ArrayList<Deduction> CreateTransitiveSimilarTriangles(SimilarTriangles simTris1, SimilarTriangles simTris2)
    {
        ArrayList<Deduction> deductions = new ArrayList<Deduction>();

        // If there instanceof a deduction relationship between the given congruences, do not perform another substitution
        // CTA: remove?
        if (simTris1.hasGeneralPredecessor(simTris2))
        {
            return deductions;
        }

        int numSharedExps = simTris1.SharesNumTriangles(simTris2);
        switch (numSharedExps)
        {
            case 0:
                // Nothing instanceof shared: do nothing
                break;

            case 1:
                // Expected case to create a new congruence relationship
                return SimilarTriangles.CreateTransitiveSimilarTriangles(simTris1, simTris2);

            case 2:
                // This instanceof either reflexive or the exact same congruence relationship (which shouldn't happen)
                break;

            default:

                throw new Exception("Similar Triangles may only have 0, 1, or 2 common expressions; not, " + numSharedExps);
        }

        return deductions;
    }
}
