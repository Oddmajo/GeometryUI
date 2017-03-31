package backend.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import backend.ast.figure.components.angles.Angle;
import backend.utilities.math.MathUtilities;

//
// Design Patterns:
//    Singleton
//    
// Stores all angles into equivalence classes (HashSets) using a representative angle as search-key
// That representative angle is contained in the equivalence class for the angle
//
public class AngleEquivalenceRelation
{
    private HashMap<Angle, HashSet<Angle>> _eqClasses;

    public AngleEquivalenceRelation()
    {
        _eqClasses = new HashMap<Angle, HashSet<Angle>>();
    }

    private static AngleEquivalenceRelation _instance;
    public static AngleEquivalenceRelation getInstance()
    {
        if (_instance == null) _instance = new AngleEquivalenceRelation();
        return _instance;
    } 

    /**
     * @return the representative list of angles
     */
    public Set<Angle> getRepresentative() { return _eqClasses.keySet(); }
    
    /**
     * @param that -- an angle
     * Adds this angle to the appropriate equivalence class in this container;
     * If no equivalence class exists, one is created with the given angle as the representative angle.
     */
    public void add(Angle that)
    {
        // Iterate through the list of known representative angles
        for (Angle angle : _eqClasses.keySet())
        {
            // If the angles equate, add to the equivalence class for that angle
            if (that.equates(angle))
            {
                _eqClasses.get(angle).add(that);
                return;
            }
        }

        // If we've reached this point we have a new equivalence class.
        // The representative angle is this new angle (and won't change)
        HashSet<Angle> set = new HashSet<Angle>();
        set.add(that);
        _eqClasses.put(that, set);
    }
    
    /**
     * @param that -- an angle
     * @return the equivalent set of angles for that.
     */
    public HashSet<Angle> getEquivalent(Angle that)
    {
        // Iterate through the list of known representative angles
        for (Angle angle : _eqClasses.keySet())
        {
            // If the angles equate, add to the equivalence class for that angle
            if (that.equates(angle)) return _eqClasses.get(angle);
        }

        // Failed to find an equivalence class
        return new HashSet<Angle>();
    }

    /**
     * @param that -- an angle
     * @return the equivalent set of angles for that.
     */
    public HashSet<Angle> getCongruent(Angle that)
    {
        HashSet<Angle> angles = new HashSet<Angle>();
        
        // Iterate through the list of known representative angles
        for (Angle angle : _eqClasses.keySet())
        {
            // If the angle measures are equal add the representative angle and its equivalence class
            if (angle.equalMeasure(that))
            {
                angles.addAll(_eqClasses.get(angle));
            }
        }

        return angles;
    }
    
    /**
     * @param that -- an measure angle
     * @return the set of angles with the same measure as that
     */
    public HashSet<Angle> getCongruent(double that)
    {
        HashSet<Angle> angles = new HashSet<Angle>();
        
        // Iterate through the list of known representative angles
        for (Angle angle : _eqClasses.keySet())
        {
            // If the angle measures are equal add the representative angle and its equivalence class
            if (MathUtilities.doubleEquals(angle.getMeasure(), that))
            {
                angles.addAll(_eqClasses.get(angle));
            }
        }

        return angles;
    }
    
    /**
     * @return all angles (combined set of equivalence class angles)
     */
    public HashSet<Angle> getAllAngles()
    {
        HashSet<Angle> angles = new HashSet<Angle>();
        
        // Iterate through all value sets
        for (HashSet<Angle> set : _eqClasses.values())
        {
            angles.addAll(set);
        }

        return angles;
    }
    
    /**
     * Clear the container completely.
     */
    public void clear()
    {
        // Clear all value lists
        for (HashSet<Angle> set : _eqClasses.values())
        {
            set.clear();
        }

        // Clear the overall container
        _eqClasses.clear();
    }
    
    /**
     * @return the number of angles in this container
     */
    public int size()
    {
        int sz = 0;
        
        for (HashSet<Angle> set : _eqClasses.values())
        {
            sz += set.size();
        }

        return sz;
    }
    
    /**
     * @return String representation of this container in tabular form
     */
    @Override
    public String toString()
    {
        String retS = "";
        
        for (HashMap.Entry<Angle, HashSet<Angle>> entry : _eqClasses.entrySet())
        {
            retS += entry.getKey().toString() + ": ";
            
            for (Angle angle : entry.getValue())
            {
                retS += angle.toString() + " ";
            }
            
            retS += "\n";
        }
        
        return retS;                
    }
}
