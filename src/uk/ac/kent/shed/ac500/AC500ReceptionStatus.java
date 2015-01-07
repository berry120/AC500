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
 * Encapsulates information regarding the reception status of the AC500.
 *
 * @author Michael Berry
 */
public class AC500ReceptionStatus {

    private final boolean receiving;
    private final int quality;

    AC500ReceptionStatus(byte[] arr) {
        receiving = (arr[0] & 1) == 1;
        quality = arr[1] - 48;
    }

    /**
     * Determine if the AC500 is currently receiving time information.
     *
     * @return true if the AC500 is receiving time information, false otherwise.
     */
    public boolean isReceiving() {
        return receiving;
    }

    /**
     * Get the quality of the radio signal, between 0-5 where 0 is no signal and
     * 5 is an excellent signal. A signal of 2 or less is unlikely to
     * successfully synchronise. This method will only return a useful value
     * when the AC500 is currently receiving time, at other times it will always
     * return 0.
     *
     * @return the current signal quality, between 0-5.
     */
    public int getQuality() {
        return quality;
    }

    /**
     * Get a string representation of the reception status.
     *
     * @return a string representation of the reception status.
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Receiving: ").append(receiving).append("\n");
        if (receiving) {
            ret.append("Quality: ").append(quality).append("/5");
        }
        return ret.toString();
    }

}
