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

    private String port;

    public AC500(String port) {
        this.port = port;
    }

    public TimeInfo getTime() {
        try {
            List<Byte> bytesList = new ArrayList<>();
            SerialPort serialPort = new SerialPort(port);
            serialPort.openPort();
            serialPort.setParams(300, 7, 2, SerialPort.PARITY_EVEN);
            serialPort.writeString("o");
            Thread.sleep(200);
            serialPort.writeString("\r");
            Thread.sleep(200);
            serialPort.readBytes(2); //Swallow echoed chars
            while (bytesList.size() < 16) {
                byte[] bytes = serialPort.readBytes();
                if (bytes == null) {
                    continue;
                }
                for (byte b : bytes) {
                    bytesList.add(b);
                    if (bytesList.size() == 16) {
                        break;
                    }
                }
            }
            return new TimeInfo(bytesList.toArray(new Byte[bytesList.size()]));
        } catch (SerialPortException | InterruptedException ex) {
            throw new IllegalStateException("Couldn't get time from serial port", ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AC500 ac500 = new AC500("COM2");
        System.out.println(ac500.getTime());
    }

}
