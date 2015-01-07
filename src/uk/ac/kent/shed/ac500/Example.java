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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An example class to demonstrate the basic functions of the AC500 library.
 *
 * @author Michael Berry
 */
public class Example {

    private static final String AC500_PORT = "COM2"; //Change to your port

    /**
     * Demonstrates receiving the time from the AC500 as a Java 8 ZonedDateTime
     * object.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        AC500 ac500 = new AC500(AC500_PORT);
        ZonedDateTime ldt = ac500.getTime().asZonedDateTime(); //Get current time as the Java 8 LocalDateTime object
        DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
        System.out.println(dtf.format(ldt));
//        ac500.startReceptionAttempt(); //Uncomment to tell the ac500 to start seeking the time
    }

}
