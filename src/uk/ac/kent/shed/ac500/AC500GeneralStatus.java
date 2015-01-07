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
 * Encapsulated the general status of the AC500, returned with its time
 * information.
 *
 * @author Michael Berry
 */
public class AC500GeneralStatus {

    private final byte statusByte;

    AC500GeneralStatus(byte statusByte) {
        this.statusByte = statusByte;
    }

    /**
     * Determine if the AC500 has currently valid time information.
     *
     * @return true if the AC500 has valid time information, false otherwise.
     */
    public boolean hasValidTimeInfo() {
        return (statusByte & 0b1) == 0b1;
    }

    /**
     * Determine if the AC500 has had at least one successful time reception
     * since 2.30AM.
     *
     * @return true if the AC500 has at least one successful time reception,
     * false otherwise.
     */
    public boolean successfulSince230() {
        return (statusByte & 0b10) == 0b010;
    }

    /**
     * Determine if the last reception attempt was successful.
     *
     * @return true if the last reception attempt was successful, false
     * otherwise.
     */
    public boolean lastReceptionSuccessful() {
        return (statusByte & 0b100) == 0;
    }

    /**
     * Determine if the battery voltage is low.
     *
     * @return true if the battery voltage is low, false otherwise.
     */
    public boolean lowBatteryVoltage() {
        return (statusByte & 0b1000) == 0b1000;
    }

    /**
     * Get a string representation of the general status.
     *
     * @return a string representation of the general status.
     */
    @Override
    public String toString() {
        return "Valid time info: " + hasValidTimeInfo() + "\n"
                + "Successful reception attempt since 0230: " + successfulSince230() + "\n"
                + "Successful last reception attempt: " + lastReceptionSuccessful() + "\n"
                + "Low battery voltage: " + lowBatteryVoltage();
    }
}
