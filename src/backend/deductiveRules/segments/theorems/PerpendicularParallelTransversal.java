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

        deductions.addAll(deducePerpendicularParallelTransversal(parallels, intersections));

        return deductions;
    }

    public Set<Deduction> deducePerpendicularParallelTransversal(List<Parallel> perpendiculars, List<Intersection> intersections)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // create HashMap to emulate GroupBy & Concat
        HashMap<Segment, ArrayList<Segment>> query1 = new HashMap<Segment, ArrayList<Segment>>();
        ArrayList<Segment> keyList = new ArrayList<>();

        List<Intersection> foundCand = new ArrayList<Intersection>(); //Variable holding intersections that will used for theorem
        List<GroundedClause> antecedent = new ArrayList<>();

        //
        // loop through intersections to group them and add them query1
        // list of all segments in the intersection list by individual segment and list of intersecting segments
        //
        for (Intersection i : intersections) 
        {
            //
            // GroupBy lhs
            //
            // if lhs is already a group
            if (query1.containsKey(i.getlhs()))
            {
                // if rhs is not in the group, add it
                if (!query1.get(i).contains(i.getrhs()))
                {
                    query1.get(i).add(i.getrhs());
                }
            }
            // lhs is not a group, add it
            else
            {
                ArrayList<Segment> rhsList = new ArrayList<>();
                rhsList.add(i.getrhs());
                query1.put(i.getlhs(), rhsList);
                keyList.add(i.getlhs());
            }

            //
            // GroupBy rhs
            //
            // if rhs is already a group
            if (query1.containsKey(i.getrhs()))
            {
                // if lhs is not in the group, add it
                if (!query1.get(i).contains(i.getlhs()))
                {
                    query1.get(i).add(i.getlhs());
                }
            }
            // rhs is not a group, add it
            else
            {
                ArrayList<Segment> lhsList = new ArrayList<>();
                lhsList.add(i.getlhs());
                query1.put(i.getrhs(), lhsList);
                keyList.add(i.getrhs());
            }
        }


        //
        // for each parallel
        //
        for (Parallel perpendicular : perpendiculars)
        {
            //
            //Iterate through all segments intersected by each key segment
            //
            for (int i = 0; i < keyList.size(); i++)
            {
                Segment key = keyList.get(i);
                ArrayList<Segment> currentKeyList = query1.get(key);
                for (int j = 0; j < currentKeyList.size(); j++)
                {
                    //
                    // //If a segment that intersected both parallel lines was found, find the intersection objects.  
                    //
                    if (currentKeyList.contains(perpendicular.getSegment1()) && currentKeyList.contains(perpendicular.getSegment2()))
                    {
                        // query2
                        ArrayList<Intersection> query2 = new ArrayList<>();
                        for (Intersection intersect : intersections)
                        {
                            if (intersect.getlhs().equals(key) || intersect.getrhs().equals(key))
                            {
                                query2.add(intersect);
                            }
                        }

                        // query3
                        ArrayList<Intersection> query3 = new ArrayList<>();
                        for (Intersection intersect : query2)
                        {
                            if (intersect.getlhs().equals(perpendicular.getSegment1()) || intersect.getlhs().equals(perpendicular.getSegment1()) 
                                    || intersect.getrhs().equals(perpendicular.getSegment1()) || intersect.getrhs().equals(perpendicular.getSegment2()))
                            {
                                query3.add(intersect);
                            }
                        }

                        if (!query3.isEmpty())
                        {
                            Boolean isPerpendicularTrue = false;
                            Boolean isPerendicularFalse = false;
                            for (Intersection intersect : query3)
                            {
                                if (intersect.isPerpendicular())
                                {
                                    isPerpendicularTrue = true;
                                }
                                else
                                {
                                    isPerendicularFalse = false;
                                }

                                //if there exists both an intersection that is labeled perpendicular and an intersection 
                                // that is not labeled perpendicular
                                if (isPerpendicularTrue && isPerendicularFalse)
                                {
                                    foundCand.addAll(query3);
                                }
                            }
                        }
                        antecedent = Utilities.MakeList(perpendicular);
                    }

                }
            }
        }

        //TODO: Make sure there will only be one set of intersections found at a time
        if (foundCand.size() > 1)
        {
            antecedent.addAll((foundCand));  //Add the two intersections to antecedent

            int index;


            index = (foundCand.get(0).isPerpendicular() == false) ? 0 : 1;
            // this should not be possible anymore... Drew
            //foundCand.get(index).setPerpendicular(true);
            Point newPoint = foundCand.get(index).getlhs().segmentIntersection(foundCand.get(index).getrhs());
            Intersection newIntersection = new Intersection(newPoint, foundCand.get(index).getlhs(),foundCand.get(index).getrhs());
            
            // should this be a strengthened perpendicular?
            Perpendicular newPerpendicular = new Perpendicular(newIntersection);


            //Add the new perpendicular set
            deductions.add(new Deduction(antecedent, newPerpendicular, ANNOTATION));

        }

        return deductions;
    }

    // original C# code for reference
    //    public static Set<Deduction> Instantiate(Parallel parallel, Intersection intersection)
    //    {
    //
    //        //Exit if c is neither a parallel set nor an intersection
    //        if (!(c is Parallel) && !(c is Intersection)) return new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
    //
    //        List<Intersection> foundCand = new List<Intersection>(); //Variable holding intersections that will used for theorem
    //
    //        // The list of new grounded clauses if they are deduced
    //        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newGrounded = new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
    //
    //
    //        if (c is Parallel)
    //        {
    //            Parallel newParallel = (Parallel)c;
    //            candParallel.Add((Parallel)c);
    //
    //            //Create a list of all segments in the intersection list by individual segment and list of intersecting segments
    //            var query1 = candIntersection.GroupBy(m => m.lhs, m => m.rhs).Concat(candIntersection.GroupBy(m => m.rhs, m => m.lhs));
    //
    //            //Iterate through all segments intersected by each key segment
    //            foreach (var group in query1)
    //            {
    //                if (group.Contains(newParallel.segment1) && group.Contains(newParallel.segment2))
    //                {
    //                    //If a segment that intersected both parallel lines was found, find the intersection objects.  
    //                    var query2 = candIntersection.Where(m => m.lhs.Equals(group.Key)).Concat(candIntersection.Where(m => m.rhs.Equals(group.Key)));
    //                    var query3 = query2.Where(m => m.lhs.Equals(newParallel.segment1) || m.lhs.Equals(newParallel.segment2) || m.rhs.Equals(newParallel.segment1) || m.rhs.Equals(newParallel.segment2));
    //                    if (query3.Any(m => m.isPerpendicular == true) && query3.Any(m => m.isPerpendicular == false))
    //                    {
    //                        //if there exists both an intersection that is labeled perpendicular and an intersection that is not labeled perpendicular
    //                        foundCand.AddRange(query3);
    //                    }
    //                    antecedent = Utilities.MakeList<GroundedClause>(newParallel); //Add parallel set to antecedents
    //
    //                }
    //            }
    //
    //        }
    //        else if (c is Intersection)
    //        {
    //
    //            candIntersection.Add((Intersection)c);
    //            Intersection newintersect = (Intersection)c;
    //
    //            var query1 = candIntersection.GroupBy(m => m.lhs, m => m.rhs).Concat(candIntersection.GroupBy(m => m.rhs, m => m.lhs));
    //
    //            foreach (Parallel p in candParallel)
    //            {
    //                foreach (var group in query1)
    //                {
    //                    if (group.Contains(p.segment1) && group.Contains(p.segment2))
    //                    {
    //                        //list intersections involving intersecting segement and two paralell segments 
    //                        var query2 = candIntersection.Where(m => m.lhs.Equals(group.Key)).Concat(candIntersection.Where(m => m.rhs.Equals(group.Key)));
    //                        var query3 = query2.Where(m => m.lhs.Equals(p.segment1) || m.lhs.Equals(p.segment2) || m.rhs.Equals(p.segment1) || m.rhs.Equals(p.segment2));
    //
    //                        if (query3.Any(m => m.isPerpendicular == true) && query3.Any(m => m.isPerpendicular == false))
    //                        {
    //                            //if there exists both an intersection that is labeled perpendicular and an intersection that is not labeled perpendicular
    //                            foundCand.AddRange(query3);
    //                        }
    //
    //                        antecedent = Utilities.MakeList<GroundedClause>(p);
    //
    //                    }
    //                }
    //            }
    //
    //        }
    //
    //
    //        //TODO: Make sure there will only be one set of intersections found at a time
    //        if (foundCand.Count() > 1)
    //        {
    //            antecedent.AddRange((IEnumerable<GroundedClause>)(foundCand));  //Add the two intersections to antecedent
    //
    //            int index;
    //
    //            index = (foundCand[0].isPerpendicular == false) ? 0 : 1;
    //            foundCand[index].setPerpendicular(true);
    //            Perpendicular newPerpendicular = new Perpendicular(foundCand[index].lhs,foundCand[index].rhs, NAME);
    //
    //
    //            //Add the new perpendicular set
    //            newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, newPerpendicular));
    //
    //        }
    //
    //        return newGrounded;
    //    }

}
