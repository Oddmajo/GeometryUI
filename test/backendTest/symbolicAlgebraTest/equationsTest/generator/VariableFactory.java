package backendTest.symbolicAlgebraTest.equationsTest.generator;

import java.util.ArrayList;
import java.util.HashMap;

import backend.ast.figure.components.Segment;

public class VariableFactory
{
    private static char currentVariable = 'a';
    
    private static HashMap<Segment, Character> hashmap = new HashMap<Segment, Character>(100);
    static int variableCounter = 97;

    public VariableFactory()
    {

    }
    
    public VariableFactory(ArrayList<Segment> segmentList)
    {
        genVariable(segmentList);
    }

    public static Character genVariable(Segment newSegment)
    {
        if (!hashmap.containsKey(newSegment))
        {
            hashmap.put(newSegment, currentVariable);
            updateName();
        }
        return hashmap.get(newSegment);
    }


    public static ArrayList<Character> genVariable(ArrayList<Segment> segmentList)
    {
        ArrayList<Character> result = new ArrayList<Character>();
        for (Segment s : segmentList)
        {
            result.add(genVariable(s));
        }
        return result;

    }
    private static void updateName()
    {

        currentVariable++;
    }

}
