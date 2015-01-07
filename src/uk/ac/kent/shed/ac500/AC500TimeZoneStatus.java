/*
 * uk.kent.shed.ac500
 * Copyright (C) 2014 Michael Berry
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

/**
 * Encapsulated information regarding the current time zone - either UTC or BST,
 * depending on the time of year.
 *
 * @author Michael Berry
 */
public class AC500TimeZoneStatus {

    private final byte timeZoneByte;

    AC500TimeZoneStatus(byte timeZoneByte) {
        this.timeZoneByte = timeZoneByte;
    }

    /**
     * Get the current timezone, either BST or UTC. If the data appears corrupt
     * or inconsistent for some reason, the method will return "Unknown".
     *
     * @return the current timezone.
     */
    public String getTimeZone() {
        String timeZone;
        if (isBST()) {
            timeZone = "BST";
        } else if (isUTC()) {
            timeZone = "UTC";
        } else {
            timeZone = "Unknown";
        }
        return timeZone;
    }

    /**
     * Determine if the timezone is currently UTC.
     *
     * @return true if the timezone is UTC, false otherwise.
     */
    public boolean isUTC() {
        return (timeZoneByte & 0b100) == 0b100;
    }

    /**
     * Determine if the timezone is currently BST.
     *
     * @return true if the timezone is BST, false otherwise.
     */
    public boolean isBST() {
        return (timeZoneByte & 0b10) == 0b10;
    }

    /**
     * Determine if the timezone is about to change (within the next hour.)
     *
     * @return true if the timezone is about to change, false otherwise.
     */
    public boolean isChangeImpending() {
        return (timeZoneByte & 1) == 1;
    }

    /**
     * Provide a string representation of the time zone information.
     *
     * @return a string representation of the time zone information.
     */
    @Override
    public String toString() {
        return "Time zone: " + getTimeZone() + "\n"
                + "Change impending: " + isChangeImpending();
    }

}
