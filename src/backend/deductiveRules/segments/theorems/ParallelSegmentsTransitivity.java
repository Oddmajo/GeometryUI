package backend.deductiveRules.segments.theorems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;
import backend.utilities.exception.ExceptionHandler;
import backend.utilities.exception.NotImplementedException;

public class ParallelSegmentsTransitivity extends Theorem
{

    private static final String NAME = "Parallel Segments Transitivity Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.PARALLEL_SEGMENTS_TRANSITIVITY);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public ParallelSegmentsTransitivity(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceParallelSegmentsTransitivity());

        return deductions;
    }

    //
    // Parallel(Segment(A, B), Segment(C, D))  && Parallel(Segment(A, B), Segment(E, F) -> 
    //                                              Parallel(Segment(C, D), Segment(E, F)
    //                                               
    public Set<Deduction> deduceParallelSegmentsTransitivity()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Parallel> parallels = _qhg.getParallels();

        deductions.addAll(deduceParallelSegmentsTransitivity(parallels));

        return deductions;
    }

    public Set<Deduction> deduceParallelSegmentsTransitivity(Set<Parallel> parallels)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // create HashMap to emulate GroupBy & Concat
        HashMap<Segment, ArrayList<Segment>> query1 = new HashMap<Segment, ArrayList<Segment>>();
        ArrayList<Segment> keyList = new ArrayList<>();

        List<Intersection> foundCand = new ArrayList<Intersection>(); //Variable holding intersections that will used for theorem
        List<GroundedClause> antecedent = new ArrayList<>();

        //
        // loop through intersections to group them and add them to query1
        // list of all segments in the intersection list by individual segment and list of intersecting segments
        //
        for (Parallel i : parallels) 
        {
            //
            // GroupBy segment1
            //
            // if segment1 is already a group
            if (query1.containsKey(i.getSegment1()))
            {
                // if segment2 is not in the group, add it
                if (!query1.get(i).contains(i.getSegment2()))
                {
                    query1.get(i).add(i.getSegment2());
                }
            }
            // segment1 is not a group, add it
            else
            {
                ArrayList<Segment> segment2List = new ArrayList<>();
                segment2List.add(i.getSegment2());
                query1.put(i.getSegment1(), segment2List);
                keyList.add(i.getSegment1());
            }

            //
            // GroupBy segment2
            //
            // if segment2 is already a group
            if (query1.containsKey(i.getSegment2()))
            {
                // if segment1 is not in the group, add it
                if (!query1.get(i).contains(i.getSegment1()))
                {
                    query1.get(i).add(i.getSegment1());
                }
            }
            // segment2 is not a group, add it
            else
            {
                ArrayList<Segment> lhsList = new ArrayList<>();
                lhsList.add(i.getSegment1());
                query1.put(i.getSegment2(), lhsList);
                keyList.add(i.getSegment2());
            }
        }


        //
        // for each parallel
        //
        for (Segment group : keyList)
        {
            //
            //Iterate through all segments in the associated list in query1
            //
            List<Segment> groupList = query1.get(group);
            for (Segment seg : groupList)
            {
                // Dr. Alvin Needs to look at this class
                ExceptionHandler.throwException(new NotImplementedException());
            }
        }

        //TODO: Make sure there will only be one set of intersections found at a time
        if (foundCand.size() > 1)
        {
            antecedent.addAll((foundCand));  //Add to antecedent
            Parallel newParallel;

            // Dr. Alvin Needs to look at this class
            ExceptionHandler.throwException(new NotImplementedException());
            //newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, newParallel));


        }

        return deductions;
    }

    // original C# for reference
//    public static List<KeyValuePair<List<GroundedClause>, GroundedClause>> Instantiate(GroundedClause c)
//    {
//
//        //Exit if c is not parallel
//        if (!(c is Parallel)) return new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
//
//        List<Parallel> foundCand = new List<Parallel>(); //Variable holding parallel relations that will used for theorem
//
//        // The list of new grounded clauses if they are deduced
//        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newGrounded = new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
//
//        if (c is Parallel)
//        {
//            Parallel newParallel = (Parallel)c;
//            candParallel.Add((Parallel)c);
//
//            //Create a list of all segments part of a parallel set grouped by what other segments they are parallel to
//            var query1 = candParallel.GroupBy(m => m.segment1, m => m.segment2).Concat(candParallel.GroupBy(m => m.segment1, m => m.segment2));
//
//            //Iterate through the groups of parallel relations
//            foreach (var group in query1)
//            {
//                foreach (ConcreteSegment segment in group)
//                {
//                    //var query2 = candParallel.Where(m => m.segment1 == 
//
//                    //Add two parallel sets leading to third parallel set
//                    //foundCand.Add()
//                }
//
//            }
//        }
//
//
//        if (foundCand.Count() >= 1)
//        {
//            antecedent.AddRange((IEnumerable<GroundedClause>)(foundCand));  //Add the two intersections to antecedent
//            Parallel newParallel;
//            //new Parallel()
//            //newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, newParallel));
//
//        }
//
//        return newGrounded;
//    }
//
}
