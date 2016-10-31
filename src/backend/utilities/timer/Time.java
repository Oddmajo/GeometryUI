/*
iTutor – an intelligent tutor of mathematics
Copyright (C) 2016-2017 C. Alvin and Bradley University CS Students (list of students)
This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed : the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * The Time Class
 * @author Nick Celiberti
 * <p>
 * Class object to keep store how long a program was running for
 * 
 *
 */


package backend.utilities.timer;

public class Time
{
    public static final long MILLISECONDS_PER_SECOND = 1000;
    public static final long SECONDS_PER_MINUTE = 60;
    public static final long MINUTES_PER_HOURS = 60;
    public static final long HOURS_PER_DAY = 24;
    public static final long DAYS_PER_YEAR = 365;

    protected long _milliseconds;
    protected long _seconds;
    protected long _minutes;
    protected long _hours;
    protected long _days;
    protected long _years;
    //positive or negative time
    protected boolean _sign;

    //Constructors 
    /**
     * @author Nick Celiberti
     * @return A Time object with time 0
     */
    public Time()
    {
        set(0);
    }

    /**
     * @author Nick Celiberti
     * @param milliseconds The desired time for the object
     * @return A time object with specified time
     */
    public Time(long milliseconds)
    {
        set(milliseconds);
    }

    /**
     * @author Nick Celiberti
     * @param millis
     * @param secs
     * @param mins
     * @param hours
     * @param days
     * @param years
     */
    public Time(long millis, long secs, long mins, long hours, long days, long years)
    {
        set(millis, secs, mins, hours, days, years);
    }

    /**
     * @author Nick Celiberti
     * @param str The desired time for the object represented : xx:xx:xx:xx:xx:xx format
     */
    public Time(String str)
    {
        set(str);
    }
    
    void set(long milli, long secs, long mins, long hours, long days, long years)
    {
        //
        // Compute the actual number of seconds
        //
        long milliseconds = 0;
        boolean local_sign = milli >= 0;
        milliseconds += Math.abs(milli);
        milliseconds += secs * MILLISECONDS_PER_SECOND;
        milliseconds += mins * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += hours * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += days * HOURS_PER_DAY * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += years * DAYS_PER_YEAR * HOURS_PER_DAY * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        if(!local_sign) 
        {
            milliseconds *= -1;
        }
        set(milliseconds);
    }

    // compute the appropriate values for <seconds:minutes:hours:days:years>
    void set(long milliseconds) 
    {
        //
        // Handle positivity / negativity of the time
        //
        _sign = milliseconds >= 0;

        //
        // Use a positive value for time.
        //
        long localMilliseconds = Math.abs(milliseconds);

        //
        // Acquire the exact number of seconds, minutes, ...
        //
        _milliseconds = localMilliseconds % MILLISECONDS_PER_SECOND;

        long seconds = localMilliseconds / MILLISECONDS_PER_SECOND;
        _seconds = seconds % SECONDS_PER_MINUTE;

        long minutes = seconds / SECONDS_PER_MINUTE;
        _minutes = minutes % MINUTES_PER_HOURS;

        long hours = minutes / MINUTES_PER_HOURS;
        _hours = hours % HOURS_PER_DAY;

        long days = hours / HOURS_PER_DAY;
        _days = days % DAYS_PER_YEAR;

        _years = days / DAYS_PER_YEAR;
    }

    //
    // Time is of the form #:#:#:#:#:#
    // <milliseconds:seconds:minutes:hours:days:years>
    // ALL of these colons must not appear
    //
    void set(String str)
    {
        String[] parts = str.split(":");

        int local_milli = Integer.parseInt(parts[0]);
        boolean local_sign = local_milli >= 0;
        int local_milliseconds = Math.abs(local_milli);

        int local_seconds = Integer.parseInt(parts[1]);
        int local_minutes = Integer.parseInt(parts[2]);
        int local_hours = Integer.parseInt(parts[3]);
        int local_days = Integer.parseInt(parts[4]);
        int local_years = Integer.parseInt(parts[5]);
        if(!local_sign)
        {
            local_milliseconds *= -1;
        }
        set(local_milliseconds, local_seconds, local_minutes, local_hours, local_days, local_years);
    }
    //
    // Time is of the form #:#:#:#:#:#
    // <milliseconds:seconds:minutes:hours:days:years>
    // ALL of these colons must not appear
    //
    /**
     * Get if time is positive or negative
     * @return
     */
    boolean positive() { return _sign; }

    /**
     * Get if time is negative or positive
     * @return
     */
    boolean negative() { return !_sign; }

    //
    //
    //
    // ------------------------------------------ OPERATIONS --------------------------------------
    //
    //
    //
    
    /**
     * Get the time in milliseconds
     * @return 
     */
    long inMilliseconds()
    {
        long milliseconds = 0;
        milliseconds += this._milliseconds;
        milliseconds += this._seconds * MILLISECONDS_PER_SECOND;
        milliseconds += this._minutes * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += this._hours * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += this._days * HOURS_PER_DAY * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        milliseconds += this._years * DAYS_PER_YEAR * HOURS_PER_DAY * MINUTES_PER_HOURS * SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND;
        return this._sign ? milliseconds : -milliseconds;
    }

    //
    // Assignment operators
    //
    /**
     * Equivalent to this = rhs
     * @param rhs Time object
     */
    void assign(Time rhs)
    {
        this.set(rhs._milliseconds, rhs._seconds, rhs._minutes, rhs._hours, rhs._days, rhs._years);
    }

    /**
     * Equivalent to this += rhs
     * @param rhs Time object
     */
    void plusAssign(Time rhs)
    {
        this.set((this.plus(rhs)).inMilliseconds());
    }

    /**
     * Equivalent to this -= rhs
     * @param rhs Time Object
     */
    void subtractAssign(Time rhs)
    {
        this.set((this.subtract(rhs)).inMilliseconds());
    }

    /**
     * Equivalent to this = rhs
     * @param rhs milliseconds as long
     */
    void assign(long rhs)
    {
        this.set(rhs);
    }

    /**
     * Equivalent to this += rhs
     * @param rhs milliseconds as long
     */
    void plusAssign(long rhs)
    {
        this.set(this.inMilliseconds() + rhs);
    }

    /**
     * Equivalent to this -= rhs
     * @param rhs milliseconds as long
     */
    void subtractAssign(long rhs)
    {
        this.set(this.inMilliseconds() - rhs);
    }

    //
    // arithmetic operators
    //
    /**
     * Equivalent to this + rhs
     * @param rhs Time object
     * @return
     */
    Time plus(Time rhs)
    {
        long value = this.inMilliseconds() + rhs.inMilliseconds();
        return new Time(value);
    }

    /**
     * Equivalent to this - rhs
     * @param rhs Time object
     * @return
     */
    Time subtract(Time rhs)
    {
        long value = this.inMilliseconds() - rhs.inMilliseconds();
        return new Time(value);
    }

    /**
     * Equivalent to this + rhs
     * @param rhs milliseconds as long
     * @return
     */
    Time plus(long rhs)
    {
        long value = this.inMilliseconds() + rhs;
        return new Time(value);
    }

    /**
     * Equivalent to this - rhs
     * @param rhs milliseconds as long
     * @return
     */
    Time subtract(long rhs)
    {
        long value = this.inMilliseconds() - rhs;
        return new Time(value);
    }

    //
    // relational operators
    //
    @Override
    public boolean equals(Object time)
    {
        if (time == null) { return false; }
        if (!(time instanceof Time)) { return false; }
        if (time == this) { return true; }
        Time rhs = (Time)time;
        return this._sign    == rhs._sign    &&
                this._seconds == rhs._seconds && 
                this._minutes == rhs._minutes &&
                this._hours   == rhs._hours   &&
                this._days    == rhs._days    &&
                this._years    == rhs._years;
    }

    /**
     * Equivalent to this != rhs
     * @param rhs
     * @return
     */
    boolean isNotEqual(Time rhs)
    {
        return !this.equals(rhs);
    }

    /**
     * Equivalent to this < rhs
     * @param rhs
     * @return
     */
    boolean isLessThan(Time rhs)
    {
        return this.inMilliseconds() < rhs.inMilliseconds();
    }

    /**
     * Equivalent to this <= rhs
     * @param rhs
     * @return
     */
    boolean isLessThanOrEqual(Time rhs)
    {
        return this.inMilliseconds() <= rhs.inMilliseconds();
    }

    /**
     * Equivalent to this > rhs
     * @param rhs
     * @return
     */
    boolean isGreaterThan(Time rhs)
    {
        return this.inMilliseconds() > rhs.inMilliseconds();
    }

    /**
     * Equivalent to this >= rhs
     * @param rhs
     * @return
     */
    boolean isGreaterThanOrEqual(Time rhs)
    {
        return this.inMilliseconds() >= rhs.inMilliseconds();
    }

    @Override
    public String toString() 
    {
        String str = "";

        str += _sign ? "+" : "-";
        str += _milliseconds + ":";
        str += _seconds + ":";
        str += _minutes + ":";
        str += _hours + ":";
        str += _days + ":";
        str += _years;

        return str;
    }

}
