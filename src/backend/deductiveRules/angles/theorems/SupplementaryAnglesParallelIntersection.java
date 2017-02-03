package backend.deductiveRules.angles.theorems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.Intersection;
import backend.ast.Descriptors.Perpendicular;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.parallel.Parallel;
import backend.ast.figure.components.Point;
import backend.ast.figure.components.Segment;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.ast_helper.Utilities;

public class SupplementaryAnglesParallelIntersection extends Theorem
{
    private static final String NAME = "Supplementary Angles Parallel Intersection Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.SUPPLEMENTARY_ANGLES_PARALLEL_INTERSECTION);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public SupplementaryAnglesParallelIntersection(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceSupplementaryAnglesParallelIntersection());

        return deductions;
    }
    
    public Set<Deduction> deduceSupplementaryAnglesParallelIntersection()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        Set<Parallel> parallels = _qhg.getParallels();      
        Set<Intersection> intersections = _qhg.getIntersections();

        deductions.addAll(deduceSupplementaryAnglesParallelIntersection(parallels, intersections));

        return deductions;
    }
    
    public Set<Deduction> deduceSupplementaryAnglesParallelIntersection(Set<Parallel> parallels, Set<Intersection> intersections)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // create HashMap to emulate GroupBy & Concat
        HashMap<Segment, ArrayList<Segment>> query1 = new HashMap<Segment, ArrayList<Segment>>();
        ArrayList<Segment> keyList = new ArrayList<>();

        List<Intersection> foundCand = new ArrayList<Intersection>(); //Variable holding intersections that will used for theorem
        Parallel foundParallelSet = null;
        Segment foundTransversal;
        List<GroundedClause> antecedent = new ArrayList<>();

        //
        // loop through intersections to group them and add them to query1
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
        for (Parallel perpendicular : parallels)
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
                        foundCand.addAll(query3);

                        foundParallelSet = perpendicular;
                        foundTransversal = key;

                        antecedent = Utilities.MakeList(perpendicular);
                    }

                }
            }
        }

        if (foundCand.size() > 1)
        {
            antecedent.addAll((foundCand));  //Add the two intersections to antecedent
            Supplementary cca1;
            Supplementary cca2;

            int seg1index;
            int seg2index;

            //Match first and second intersection points with first and second segments
            if (foundCand.get(0).getlhs() == foundParallelSet.getSegment1() || foundCand.get(0).getrhs() == foundParallelSet.getSegment1())
            {
                seg1index = 0;
                seg2index = 1;
            }
            else
            {
                seg1index = 1;
                seg2index = 0;
            }


            Angle ang1Seg1 = new Angle(foundParallelSet.getSegment1().getPoint1(), foundCand.get(seg1index).getIntersect(), foundCand.get(seg2index).getIntersect());
            Angle ang2Seg1 = new Angle(foundParallelSet.getSegment1().getPoint2(), foundCand.get(seg1index).getIntersect(), foundCand.get(seg2index).getIntersect());
            Angle ang1Seg2 = new Angle(foundParallelSet.getSegment2().getPoint1(), foundCand.get(seg2index).getIntersect(), foundCand.get(seg1index).getIntersect());
            Angle ang2Seg2 = new Angle(foundParallelSet.getSegment2().getPoint2(), foundCand.get(seg2index).getIntersect(), foundCand.get(seg1index).getIntersect());



            /*
            ConcreteAngle ang1Set1 = new ConcreteAngle(foundCand[0].lhs.Point1, foundCand[0].intersect, foundCand[0].rhs.Point1);
            ConcreteAngle ang2Set1 = new ConcreteAngle(foundCand[0].lhs.Point2, foundCand[0].intersect, foundCand[0].rhs.Point1);
            ConcreteAngle ang1Set2 = new ConcreteAngle(foundCand[1].lhs.Point1, foundCand[1].intersect, foundCand[1].rhs.Point1);
            ConcreteAngle ang2Set2 = new ConcreteAngle(foundCand[1].lhs.Point2, foundCand[1].intersect, foundCand[1].rhs.Point1);
             */
            //Supplementary angles will be the matching angles on different segments
            //TODO: Make sure they're on the same side





            if (ang1Seg1.getMeasure() == ang1Seg2.getMeasure())
            {
                cca1 = new Supplementary(ang1Seg1, ang2Seg2);
                cca2 = new Supplementary(ang1Seg2, ang2Seg1);
            }
            else
            {
                cca1 = new Supplementary(ang1Seg1, ang1Seg2);
                cca2 = new Supplementary(ang2Seg1, ang2Seg2);
            }


            //Add the two new supplementary angle sets
            deductions.add(new Deduction(antecedent, cca1, ANNOTATION));
            deductions.add(new Deduction(antecedent, cca2, ANNOTATION));

        }

        return deductions;
    }
    
//    //TODO: this currently describes something else
//    // Intersect(X, Segment(A, B), Segment(C, D)) -> Congruent(Angle(A, X, C), Angle(B, X, D)),
//    //                                               Congruent(Angle(A, X, D), Angle(C, X, B))
//    //
//    public static List<KeyValuePair<List<GroundedClause>, GroundedClause>> Instantiate(GroundedClause c)
//    {
//
//        //Exit if c is neither a parallel set nor an intersection
//        if (!(c is Parallel) && !(c is Intersection)) return new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
//
//        List<Intersection> foundCand = new List<Intersection>(); //Variable holding intersections that will used for theorem
//
//        Parallel foundParallelSet = null;
//        ConcreteSegment foundTransversal;
//
//        // The list of new grounded clauses if they are deduced
//        List<KeyValuePair<List<GroundedClause>, GroundedClause>> newGrounded = new List<KeyValuePair<List<GroundedClause>, GroundedClause>>();
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
//                    var query3 = candIntersection.Where(m => m.lhs.Equals(newParallel.segment1) || m.lhs.Equals(newParallel.segment2) || m.rhs.Equals(newParallel.segment1) || m.rhs.Equals(newParallel.segment2));
//                    foundCand.AddRange(query3);
//
//                    foundParallelSet = newParallel;
//                    foundTransversal = group.Key;
//
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
//                        var query2 = candIntersection.Where(m => m.lhs.Equals(group.Key)).Concat(candIntersection.Where(m => m.rhs.Equals(group.Key)));
//                        var query3 = candIntersection.Where(m => m.lhs.Equals(p.segment1) || m.lhs.Equals(p.segment2) || m.rhs.Equals(p.segment1) || m.rhs.Equals(p.segment2));
//                        foundCand.AddRange(query3);
//
//                        foundParallelSet = p;
//                        foundTransversal = group.Key;
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
//
//        if (foundCand.Count() > 1)
//        {
//            antecedent.AddRange((IEnumerable<GroundedClause>)(foundCand));  //Add the two intersections to antecedent
//            ConcreteSupplementaryAngles cca1;
//            ConcreteSupplementaryAngles cca2;
//
//            int seg1index;
//            int seg2index;
//
//            //Match first and second intersection points with first and second segments
//            if (foundCand[0].lhs == foundParallelSet.segment1 || foundCand[0].rhs == foundParallelSet.segment1)
//            {
//                seg1index = 0;
//                seg2index = 1;
//            }
//            else
//            {
//                seg1index = 1;
//                seg2index = 0;
//            }
//
//
//            ConcreteAngle ang1Seg1 = new ConcreteAngle(foundParallelSet.segment1.Point1, foundCand[seg1index].intersect, foundCand[seg2index].intersect);
//            ConcreteAngle ang2Seg1 = new ConcreteAngle(foundParallelSet.segment1.Point2, foundCand[seg1index].intersect, foundCand[seg2index].intersect);
//            ConcreteAngle ang1Seg2 = new ConcreteAngle(foundParallelSet.segment2.Point1, foundCand[seg2index].intersect, foundCand[seg1index].intersect);
//            ConcreteAngle ang2Seg2 = new ConcreteAngle(foundParallelSet.segment2.Point2, foundCand[seg2index].intersect, foundCand[seg1index].intersect);
//
//
//
//            /*
//            ConcreteAngle ang1Set1 = new ConcreteAngle(foundCand[0].lhs.Point1, foundCand[0].intersect, foundCand[0].rhs.Point1);
//            ConcreteAngle ang2Set1 = new ConcreteAngle(foundCand[0].lhs.Point2, foundCand[0].intersect, foundCand[0].rhs.Point1);
//            ConcreteAngle ang1Set2 = new ConcreteAngle(foundCand[1].lhs.Point1, foundCand[1].intersect, foundCand[1].rhs.Point1);
//            ConcreteAngle ang2Set2 = new ConcreteAngle(foundCand[1].lhs.Point2, foundCand[1].intersect, foundCand[1].rhs.Point1);
//             */
//            //Supplementary angles will be the matching angles on different segments
//            //TODO: Make sure they're on the same side
//
//
//
//
//
//            if (ang1Seg1.measure == ang1Seg2.measure)
//            {
//                cca1 = new ConcreteSupplementaryAngles(ang1Seg1, ang2Seg2, NAME);
//                cca2 = new ConcreteSupplementaryAngles(ang1Seg2, ang2Seg1, NAME);
//            }
//            else
//            {
//                cca1 = new ConcreteSupplementaryAngles(ang1Seg1, ang1Seg2, NAME);
//                cca2 = new ConcreteSupplementaryAngles(ang2Seg1, ang2Seg2, NAME);
//            }
//
//
//            //Add the two new supplementary angle sets
//            newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, cca1));
//            newGrounded.Add(new KeyValuePair<List<GroundedClause>, GroundedClause>(antecedent, cca2));
//
//        }
//
//        return newGrounded;
//    }
//
}
