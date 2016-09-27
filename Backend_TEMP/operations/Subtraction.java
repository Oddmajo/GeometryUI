package operations;

import java.util.List;

public class Subtraction extends ArithmeticOperation
{
    {
        public Subtraction() 
        {
            super();
        }


        public Subtraction(GroundedClause left, GroundedClause right) {
            super(left, right);
        }


        public List<GroundedClause> collectTerms()
        {
            List<GroundedClause> list = new List<GroundedClause>();

            list.AddRange(leftExp.CollectTerms());

            for (GroundedClause gc : rightExp.CollectTerms())
            {
                GroundedClause copyGC = gc.DeepCopy();

                copyGC.multiplier *= -1;
                list.Add(copyGC);
            }

            return list;
        }

        public String toString()
        {
            return "(" + leftExp.ToString() + " - " + rightExp.ToString() + ")";
        }

        public boolean equals(Object obj)
        {

            if (obj == null || (Subtraction)obj == null) return false;
            return super.equals((Subtraction)obj);
        }

        public int getHashCode()
        {
            //Change this if the object is no longer immutable!!!
            return super.getHashCode();
        }
    }
