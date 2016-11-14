package backendTest.atomsTest.calculatorTest.lexicographicPointsTest;

import backend.ast.figure.components.Point;
import backend.atoms.calculator.lexicographicPoints.LexicographicPoints;

public class LexicographicPointsByY extends LexicographicPoints
{

    public LexicographicPointsByY()
    {
        super();
    }
    
    /**
     * Inserts an element to the list.
     * @param thatNode  the Point to insert into the list
     */
    private void insert(Point thatNode)
    {

        boolean added = false; // Drew added this
        
        // Empty List: add to the beginning.
        if (ordered.isEmpty())
        {
            ordered.add(thatNode);
            return;
        }
        
        // General Insertion
        for (int i = 0; i < ordered.size(); i++)
        {
            // compare only y values
            if (thatNode.getY() <= ordered.get(i).getY())
            {
                ordered.add(i, thatNode);
                added = true;
                break;
            }
        }
        // Add node to the end of the heap
        if (added == false)
        {
            ordered.add(thatNode);
        }
    }
    
    public void add(Point pt)
    {
        insert(pt);
    }

}
