/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kent.shed.ac500;

import java.util.ArrayList;
import java.util.List;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author mjrb5
 */
public class AC500 {

    private final String port;

    public AC500(String port) {
        this.port = port;
    }

    public TimeInfo getTime() {
        byte[] arr = getBytes("o", 16);
        return new TimeInfo(arr);
    }
    
    public void startReceptionAttempt() {
        getBytes("h", 0);
    }

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

    /**
     * Just testing
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AC500 ac500 = new AC500("COM2");
        System.out.println(ac500.getTime());
        ac500.startReceptionAttempt();
        System.out.println(ac500.getTime());
    }

}
