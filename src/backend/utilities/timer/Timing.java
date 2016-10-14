/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * 
 * @author Nick Celiberti
 * <p>
 * Class to perform the timing for programs and give the result : a Time object 
 * </p>
 *
 */

package utilities.timer;

public class Timing
{

    private long startTime;
    private long accumulatedTime;
    
    /**
     * Initiates the class to keep track of run time. 
     */
    public void start()
    {
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Sets the runtime to 0 and then begins keeping track of run time.
     */
    public void reset()
    {
        startTime = System.currentTimeMillis();
        accumulatedTime = 0;
    }
    
    /**
     * Stops the timer and then adds the runtime to its total
     */
    public void stop()
    {
        long stopTime = System.currentTimeMillis();
        accumulatedTime += stopTime - startTime;
    }
    
    /**
     * @return The total runtime : a Time object
     */
    public Time getTime()
    {
        return new Time(accumulatedTime);
    }
}
