package backend.deductiveRules.angles.theorems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import backend.ast.GroundedClause;
import backend.ast.Descriptors.AnglePairRelation;
import backend.ast.Descriptors.Complementary;
import backend.ast.Descriptors.InMiddle;
import backend.ast.Descriptors.Median;
import backend.ast.Descriptors.Supplementary;
import backend.ast.Descriptors.Relations.Congruences.CongruentAngles;
import backend.ast.Descriptors.Relations.Congruences.GeometricCongruentAngles;
import backend.ast.figure.components.angles.Angle;
import backend.deductiveRules.Deduction;
import backend.deductiveRules.RuleFactory;
import backend.deductiveRules.generalRules.Theorem;
import backend.hypergraph.Annotation;
import backend.hypergraph.QueryableHypergraph;
import backend.utilities.exception.ArgumentException;
import backend.utilities.exception.ExceptionHandler;

public class RelationsOfCongruentAnglesAreCongruent extends Theorem
{

    private static final String NAME = "Relations Of Congruent Angles Are Congruent Theorem";
    public String getName() { return NAME; }
    public String getDescription() { return getName(); }

    private final static Annotation ANNOTATION = new Annotation(NAME, RuleFactory.JustificationSwitch.RELATIONS_OF_CONGRUENT_ANGLES_ARE_CONGRUENT);
    @Override public Annotation getAnnotation() { return ANNOTATION; }

    public RelationsOfCongruentAnglesAreCongruent(QueryableHypergraph<GroundedClause, Annotation> qhg)
    {
        super(qhg);
        ANNOTATION.active = RuleFactory.JustificationSwitch.RELATIONS_OF_CONGRUENT_ANGLES_ARE_CONGRUENT;
    }

    @Override
    public Set<Deduction> deduce()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        deductions.addAll(deduceDirectRelations());
        deductions.addAll(deduceIndirectRelations());

        return deductions;
    }

    public Set<Deduction> deduceDirectRelations()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<AnglePairRelation> aprs = _qhg.getAnglePairRelations();   

        if (aprs.size() > 1) {
            for (int i = 0; i < aprs.size() - 1; i++)
            {
                for (int j = i + 1; j < aprs.size(); j++)
                {
                    deductions.addAll(deduceDirectRelations(aprs.get(i), aprs.get(j)));
                }
            }
        }
        return deductions;
    }

    public Set<Deduction> deduceIndirectRelations()
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        List<AnglePairRelation> aprs = _qhg.getAnglePairRelations();  
        List<CongruentAngles> cas = _qhg.getCongruentAngles();

        if (aprs.size() > 1) {
            for (CongruentAngles ca : cas) 
            {
                for (int i = 0; i < aprs.size() - 1; i++)
                {
                    for (int j = i + 1; j < aprs.size(); j++)
                    {
                        deductions.addAll(deduceIndirectRelations(ca, aprs.get(i), aprs.get(j)));
                    }
                }
            }
        }
        return deductions;
    }

    //
    // AnglePairRelation(Angle(1), Angle(2)),
    // AnglePairRelation(Angle(3), Angle(4)),
    // Congruent(Angle(2), Angle(4)) -> Congruent(Angle(1), Angle(3))
    //                                  AnglePairRelation(Angle(1), Angle(4)),
    //                                  AnglePairRelation(Angle(2), Angle(3)),
    //
    //         |   /
    //         |1 /
    //         | / 2
    //         |/____________________
    //         |   /
    //         |3 /
    //         | / 4
    //         |/____________________
    //
    //
    // AnglePairRelation(Angle(1), Angle(2)),
    // AnglePairRelation(Angle(3), Angle(4)),
    // Congruent(Angle(2), Angle(4)) -> Congruent(Angle(1), Angle(3))
    //                                  AnglePairRelation(Angle(1), Angle(4)),
    //                                  AnglePairRelation(Angle(2), Angle(3)),
    //
    //                      /              /
    //                     /              /
    //                 1  /              /\ 2
    //       ____________/              /__\__________________
    //                      /              /
    //                     /              /
    //                 3  /              /\ 4
    //       ____________/              /__\__________________
    //s
    private static Set<Deduction> deduceDirectRelations(AnglePairRelation relation1, AnglePairRelation relation2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Do we have the same type of relation?
        if (relation1.getClass() != relation2.getClass()) return deductions;

        // Acquire the shared angle
        Angle shared = relation1.AngleShared(relation2);
        if (shared == null) return deductions;

        Angle otherAngle1 = relation1.OtherAngle(shared);
        Angle otherAngle2 = relation2.OtherAngle(shared);

        // Avoid generating a reflexive relationship
        if (otherAngle1.equates(otherAngle2)) return deductions;

        // The other two angles are then congruent
        GeometricCongruentAngles gcas = new GeometricCongruentAngles(otherAngle1, otherAngle2);

        // Construct hyperedge
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(relation1);
        antecedent.add(relation2);

        deductions.add(new Deduction(antecedent, gcas, ANNOTATION));

        return deductions;
    }

    private static Set<Deduction> deduceIndirectRelations(CongruentAngles cas, AnglePairRelation relation1, AnglePairRelation relation2)
    {
        HashSet<Deduction> deductions = new HashSet<Deduction>();

        // Do we have the same type of relation?
        if (relation1.getClass() != relation2.getClass()) return deductions;

        //
        // Determine the shared values amongst the relations
        //
        Angle shared1 = relation1.AngleShared(cas);
        if (shared1 == null) return deductions;

        Angle shared2 = cas.OtherAngle(shared1);
        if (!relation2.HasAngle(shared2)) return deductions;

        Angle otherAngle1 = relation1.OtherAngle(shared1);
        Angle otherAngle2 = relation2.OtherAngle(shared2);

        // Avoid generating a reflexive relationship
        if (otherAngle1.equates(otherAngle2)) return deductions;

        //
        // Congruent(Angle(1), Angle(3))
        //
        // The other two angles from the relation pairs are then congruent
        GeometricCongruentAngles gcas = new GeometricCongruentAngles(otherAngle1, otherAngle2);

        // Avoid direct cyclic congruent angle generation
        if (cas.structurallyEquals(gcas)) return deductions;

        // Construct hyperedge
        List<GroundedClause> antecedent = new ArrayList<GroundedClause>();
        antecedent.add(cas);
        antecedent.add(relation1);
        antecedent.add(relation2);

        //
        // AnglePairRelation(Angle(1), Angle(4)),
        // AnglePairRelation(Angle(2), Angle(3)),
        //
        if (relation1 instanceof Complementary && relation2 instanceof Complementary)
        {
            Complementary comp1 = new Complementary(shared1, otherAngle2);
            Complementary comp2 = new Complementary(shared2, otherAngle1);

            deductions.add(new Deduction(antecedent, comp1, ANNOTATION));
            deductions.add(new Deduction(antecedent, comp2, ANNOTATION));
        }
        else if (relation1 instanceof Supplementary && relation2 instanceof Supplementary)
        {
            Supplementary supp1 = new Supplementary(shared1, otherAngle2);
            Supplementary supp2 = new Supplementary(shared2, otherAngle1);

            deductions.add(new Deduction(antecedent, supp1, ANNOTATION));
            deductions.add(new Deduction(antecedent, supp2, ANNOTATION));
        }
        else
        {
            ExceptionHandler.throwException(new ArgumentException("RelationsOfCongruent:: Expected a supplementary or complementary angle, not " + relation1.getClass()));
        }

        deductions.add(new Deduction(antecedent, gcas, ANNOTATION));

        return deductions;
    }

}
