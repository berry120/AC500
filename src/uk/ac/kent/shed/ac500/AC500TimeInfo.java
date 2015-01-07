/*
 * uk.kent.shed.ac500
 * Copyright (C) 2015 Michael Berry
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.kent.shed.ac500;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Encapsulates the general time information returned by the AC500.
 *
 * @author Michael Berry
 */
public class AC500TimeInfo {

    private final int hours;
    private final int minutes;
    private final int seconds;
    private final int dayOfWeek;
    private final int dayOfMonth;
    private final int month;
    private final int year;
    private final AC500TimeZoneStatus timeZoneStatus;
    private final AC500GeneralStatus generalStatus;

    AC500TimeInfo(byte[] arr) {
        hours = (arr[0] - 48) * 10 + (arr[1] - 48);
        int tempMinutes = (arr[2] - 48) * 10 + (arr[3] - 48);
        int tempSeconds = ((arr[4] - 48) * 10 + (arr[5] - 48)) + 1; //Responds on the next second with previous second's time...
        if (tempSeconds >= 60) {
            tempMinutes += tempSeconds / 60;
            tempSeconds %= 60;
        }
        seconds = tempSeconds;
        minutes = tempMinutes;
        dayOfWeek = arr[6] - 48;
        dayOfMonth = (arr[7] - 48) * 10 + (arr[8] - 48);
        month = (arr[9] - 48) * 10 + (arr[10] - 48);
        year = ((arr[11] - 48) * 10 + (arr[12] - 48)) + 2000;
        timeZoneStatus = new AC500TimeZoneStatus(arr[13]);
        generalStatus = new AC500GeneralStatus(arr[14]);
    }

    /**
     * Get the current hours (0-23).
     *
     * @return the current hours (0-23).
     */
    public int getHours() {
        return hours;
    }

    /**
     * Get the current minutes (0-59).
     *
     * @return the current minutes (0-59).
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Get the current seconds (0-59).
     *
     * @return the current seconds (0-59).
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Get the number of the day of the week, where Monday is 1 and Sunday is 7.
     *
     * @return the number of the day of the week (starting from 1).
     */
    public int getDayOfWeekNum() {
        return dayOfWeek;
    }

    /**
     * Get the string representation of the day of the week.
     *
     * @return the string representation of the day of the week.
     */
    public String getDayOfWeek() {
        switch (dayOfWeek) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return null;
        }
    }

    /**
     * Get the current day of the month (1-31).
     *
     * @return the current day of the month (1-31).
     */
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Get the current month of the year (1-12).
     *
     * @return the current month of the year (1-12).
     */
    public int getMonth() {
        return month;
    }

    /**
     * Get the current year (eg. 2015)
     *
     * @return the current year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Get the current time zone status provided with this time info object.
     *
     * @return the current time zone status provided with this time info object.
     */
    public AC500TimeZoneStatus getTimeZoneStatus() {
        return timeZoneStatus;
    }

    /**
     * Get the current general status provided with this time info object.
     *
     * @return the current general status provided with this time info object.
     */
    public AC500GeneralStatus getGeneralStatus() {
        return generalStatus;
    }

    /**
     * Get a java.util.Calendar object from the time information contained in
     * this object.
     *
     * @return a java.util.Calendar object from the time information contained
     * in this object.
     */
    public Calendar asCalendar() {
        Calendar ret = Calendar.getInstance(TimeZone.getTimeZone(getTimeZoneStatus().getTimeZone()));
        ret.setLenient(false);
        ret.set(getYear(), getMonth() - 1, getDayOfMonth(), getHours(), getMinutes(), getSeconds());
        return ret;
    }

    /**
     * Get a java.util.Date object from the time information contained in this
     * object.
     *
     * @return a java.util.Date object from the time information contained in
     * this object.
     */
    public Date asDate() {
        return asCalendar().getTime();
    }

    /**
     * Get a java.time.LocalDateTime object from the time information contained
     * in this object.
     *
     * @return a java.time.LocalDateTime object from the time information
     * contained in this object.
     */
    public LocalDateTime asLocalDateTime() {
        return LocalDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHours(), getMinutes(), getSeconds());
    }

    /**
     * Get a java.time.ZonedDateTime object from the time information contained
     * in this object.
     *
     * @return a java.time.ZonedDateTime object from the time information
     * contained in this object.
     */
    public ZonedDateTime asZonedDateTime() {
        return ZonedDateTime.of(getYear(), getMonth(), getDayOfMonth(), getHours(), getMinutes(), getSeconds(), 0, ZoneId.of(getTimeZoneStatus().getTimeZone()));
    }

    /**
     * Return the time information as a string in a default format.
     *
     * @return the time information as a string in a default format.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(getDayOfWeek());
        ret.append(" ");
        ret.append(pad(dayOfMonth));
        ret.append("/");
        ret.append(pad(month));
        ret.append("/");
        ret.append(pad(month));
        ret.append(" ");
        ret.append(pad(hours));
        ret.append(":");
        ret.append(pad(minutes));
        ret.append(":");
        ret.append(pad(seconds));
        ret.append(" ");
        ret.append(getTimeZoneStatus().getTimeZone());
        return ret.toString();
    }

    /**
     * Pad out a number to at least 2 digits.
     *
     * @param num the number to pad.
     * @return the number in string format, padded to 2 digits if the number is
     * only 1 digit long.
     */
    private static String pad(int num) {
        String ret = Integer.toString(num);
        if (ret.length() == 1) {
            return "0" + ret;
        }
        return ret;
    }

}
