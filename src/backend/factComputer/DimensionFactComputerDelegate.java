package backend.factComputer;

import java.util.ArrayList;

import backend.ast.figure.components.angles.Angle;
import backend.symbolicAlgebra.NumericValue;
import backend.symbolicAlgebra.equations.AngleEquation;
import backend.symbolicAlgebra.equations.GeometricAngleEquation;
import backend.utilities.AngleEquivalenceRelation;

public class DimensionFactComputerDelegate
{
    /*
     * @return an equation for each angle measure: a one-to-one correspondence m<ABC = measure 
     */
    public static ArrayList<AngleEquation> computeAllAngleMeasureEquations()
    {
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (Angle angle : AngleEquivalenceRelation.getInstance().getAllAngles())
        {
            eqs.add(new GeometricAngleEquation(angle, new NumericValue(angle.getMeasure())));
        }

        return eqs;
    }

    /*
     * @return an equation for each angle measure: a one-to-one correspondence m<ABC = measure 
     * Do so for only specific, special angle measures: 30, 45, 60, 90 (as defined in the Angle class)
     */
    public static ArrayList<AngleEquation> computeRestrictedAngleMeasureEquations()
    {
        ArrayList<AngleEquation> eqs = new ArrayList<AngleEquation>();

        for (Angle angle : AngleEquivalenceRelation.getInstance().getAllAngles())
        {
            if (Angle.isSpecialAngle(angle.getMeasure()))
            {
                eqs.add(new GeometricAngleEquation(angle, new NumericValue(angle.getMeasure())));
            }
        }

        return eqs;
    }

}
