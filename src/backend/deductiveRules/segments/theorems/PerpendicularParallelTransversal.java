package backend.deductiveRules.segments.theorems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.parallel.Parallel;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;

public class PerpendicularParallelTransversal extends Theorem
{
    
    private static final String NAME = "Perpendicular Parallel Transversal Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.TRANSVERSAL_PERPENDICULAR_TO_PARALLEL_IMPLY_BOTH_PERPENDICULAR);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public PerpendicularParallelTransversal(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deducePerpendicularParallelTransversal());

        return deductions;
    }
    
    public Set<Deduction> deducePerpendicularParallelTransversal()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<Parallel> parallels = _qhg.getParallels();      
        List<Intersection> intersections = _qhg.getIntersections();

        for (Parallel p : parallels)
        {
            for (Intersection i : intersections)
            {
                deductions.addAll(deducePerpendicularParallelTransversal(p, i));
            }
        }

        return deductions;
    }
    
    //
    public static Set<Deduction> deducePerpendicularParallelTransversal(Parallel parallel, Intersection intersection)
    {

        //Exit if c is neither a parallel set nor an intersection
        if (!(c is Parallel) && !(c is Intersection)) return new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();

        List<Intersection> foundCand = new List<Intersection>(); //Variable holding intersections that will used for theorem

        // The list of new grounded clauses if they are deduced
        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newGrounded = new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();


        if (c is Parallel)
        {
            Parallel newParallel = (Parallel)c;
            candParallel.Add((Parallel)c);

            //Create a list of all segments in the intersection list by individual segment and list of intersecting segments
            var query1 = candIntersection.GroupBy(m => m.lhs, m => m.rhs).Concat(candIntersection.GroupBy(m => m.rhs, m => m.lhs));

            //Iterate through all segments intersected by each key segment
            foreach (var group in query1)
            {
                if (group.Contains(newParallel.segment1) && group.Contains(newParallel.segment2))
                {
                    //If a segment that intersected both parallel lines was found, find the intersection objects.  
                    var query2 = candIntersection.Where(m => m.lhs.Equals(group.Key)).Concat(candIntersection.Where(m => m.rhs.Equals(group.Key)));
                    var query3 = query2.Where(m => m.lhs.Equals(newParallel.segment1) || m.lhs.Equals(newParallel.segment2) || m.rhs.Equals(newParallel.segment1) || m.rhs.Equals(newParallel.segment2));
                    if (query3.Any(m => m.isPerpendicular == true) && query3.Any(m => m.isPerpendicular == false))
                    {
                        //if there exists both an intersection that is labeled perpendicular and an intersection that is not labeled perpendicular
                        foundCand.AddRange(query3);
                    }
                    antecedent = Utilities.MakeList<GroundedClause>(newParallel); //Add parallel set to antecedents
                    
                }
            }

        }
        else if (c is Intersection)
        {

            candIntersection.Add((Intersection)c);
            Intersection newintersect = (Intersection)c;

            var query1 = candIntersection.GroupBy(m => m.lhs, m => m.rhs).Concat(candIntersection.GroupBy(m => m.rhs, m => m.lhs));

            foreach (Parallel p in candParallel)
            {
                foreach (var group in query1)
                {
                    if (group.Contains(p.segment1) && group.Contains(p.segment2))
                    {
                        //list intersections involving intersecting segement and two paralell segments 
                        var query2 = candIntersection.Where(m => m.lhs.Equals(group.Key)).Concat(candIntersection.Where(m => m.rhs.Equals(group.Key)));
                        var query3 = query2.Where(m => m.lhs.Equals(p.segment1) || m.lhs.Equals(p.segment2) || m.rhs.Equals(p.segment1) || m.rhs.Equals(p.segment2));

                    if (query3.Any(m => m.isPerpendicular == true) && query3.Any(m => m.isPerpendicular == false))
                    {
                        //if there exists both an intersection that is labeled perpendicular and an intersection that is not labeled perpendicular
                        foundCand.AddRange(query3);
                    }

                        antecedent = Utilities.MakeList<GroundedClause>(p);
                       
                    }
                }
            }

        }

        
        //TODO: Make sure there will only be one set of intersections found at a time
        if (foundCand.Count() > 1)
        {
            antecedent.AddRange((IEnumerable<GroundedClause>)(foundCand));  //Add the two intersections to antecedent
            
            int index;

            index = (foundCand[0].isPerpendicular == false) ? 0 : 1;
            foundCand[index].setPerpendicular(true);
            Perpendicular newPerpendicular = new Perpendicular(foundCand[index].lhs,foundCand[index].rhs, NAME);
            

            //Add the new perpendicular set
            newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, newPerpendicular));
        
        }

        return newGrounded;
    }

}
