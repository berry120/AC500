/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kent.shed.ac500;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author mjrb5
 */
public class TimeInfo {

    private final int hours;
    private final int minutes;
    private final int seconds;
    private final int dayOfWeek;
    private final int dayOfMonth;
    private final int month;
    private final int year;
    private final TimeZoneStatus timeZoneStatus;
    private final GeneralStatus generalStatus;

    TimeInfo(byte[] arr) {
        hours = (arr[0] - 48) * 10 + (arr[1] - 48);
        int tempMinutes = (arr[2] - 48) * 10 + (arr[3] - 48);
        int tempSeconds = ((arr[4] - 48) * 10 + (arr[5] - 48)) + 1; //Responds on the next second with previous second's time...
        if (tempSeconds >= 60) {
            tempMinutes += tempSeconds/60;
            tempSeconds %= 60;
        }
        seconds = tempSeconds;
        minutes = tempMinutes;
        dayOfWeek = arr[6] - 48;
        dayOfMonth = (arr[7] - 48) * 10 + (arr[8] - 48);
        month = (arr[9] - 48) * 10 + (arr[10] - 48);
        year = (arr[11] - 48) * 10 + (arr[12] - 48);
        timeZoneStatus = new TimeZoneStatus(arr[13]);
        generalStatus = new GeneralStatus(arr[14]);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getDayOfWeekNum() {
        return dayOfWeek;
    }

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

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public TimeZoneStatus getTimeZoneStatus() {
        return timeZoneStatus;
    }

    public GeneralStatus getGeneralStatus() {
        return generalStatus;
    }
    
    public Calendar asCalendar() {
        Calendar ret = Calendar.getInstance(TimeZone.getTimeZone(getTimeZoneStatus().getTimeZone()));
        ret.setLenient(false);
        ret.set(getYear()+2000, getMonth()-1, getDayOfMonth(), getHours(), getMinutes(), getSeconds());
        return ret;
    }
    
    public Date asDate() {
        return asCalendar().getTime();
    }
    
    public LocalDateTime asLocalDateTime() {
        return LocalDateTime.of(getYear()+2000, getMonth(), getDayOfMonth(), getHours(), getMinutes(), getSeconds());
    }
    
    public ZonedDateTime asZonedDateTime() {
        return ZonedDateTime.of(getYear()+2000, getMonth(), getDayOfMonth(), getHours(), getMinutes(), getSeconds(), 0, ZoneId.of(getTimeZoneStatus().getTimeZone()));
    }

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

    private static String pad(int num) {
        String ret = Integer.toString(num);
        if (ret.length() == 1) {
            return "0" + ret;
        }
        return ret;
    }

}
