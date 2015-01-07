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

import java.util.ArrayList;
import java.util.List;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Create an instance of this class to communicate with the AC500 and return
 * results.
 *
 * @author Michael Berry
 */
public class AC500 {

    private final String port;

    /**
     * Create an AC500 on a specified port. A connection is not opened at this
     * point, individual connections are made whenever methods are called.
     *
     * @param port the port to use (eg. COM2 on windows.)
     */
    public AC500(String port) {
        this.port = port;
    }

    /**
     * Query the AC500 for time info.
     *
     * @return the time info from the AC500.
     */
    public AC500TimeInfo getTime() {
        byte[] arr = getBytes("o", 16);
        return new AC500TimeInfo(arr);
    }

    /**
     * Query the AC500 for reception status.
     *
     * @return the reception status from the AC500.
     */
    public AC500ReceptionStatus getReceptionStatus() {
        byte[] arr = getBytes("g", 2);
        return new AC500ReceptionStatus(arr);
    }

    /**
     * Tell the AC500 to start a reception attempt.
     */
    public void startReceptionAttempt() {
        getBytes("h", 0);
    }

    /**
     * Send a string to the AC500 followed by carriage return, then get the
     * response as a byte array of a specified length.
     *
     * @param strToSend the string to send (carriage return will be added to the
     * end.)
     * @param numBytes the number of bytes to wait for in response.
     * @return the byte array formating the response.
     */
    private byte[] getBytes(String strToSend, int numBytes) {
        try {
            List<Byte> bytesList = new ArrayList<>();
            SerialPort serialPort = new SerialPort(port);
            serialPort.openPort();
            serialPort.setParams(300, 7, 2, SerialPort.PARITY_EVEN);
            serialPort.writeString(strToSend);
            Thread.sleep(200);
            serialPort.writeString("\r");
            Thread.sleep(200);
            serialPort.readBytes(strToSend.length() + 1); //Swallow echoed chars
            while (bytesList.size() < numBytes) {
                byte[] bytes = serialPort.readBytes();
                if (bytes == null) {
                    continue;
                }
                for (byte b : bytes) {
                    bytesList.add(b);
                    if (bytesList.size() >= numBytes) {
                        break;
                    }
                }
            }
            serialPort.closePort();
            byte[] ret = new byte[bytesList.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = bytesList.get(i);
            }
            return ret;
        } catch (SerialPortException | InterruptedException ex) {
            throw new IllegalStateException("Couldn't get time from serial port", ex);
        }
    }

}
