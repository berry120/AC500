/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kent.shed.ac500;

import java.util.ArrayList;
import java.util.List;
import jssc.SerialPort;

/**
 *
 * @author mjrb5
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<Byte> bytesList = new ArrayList<>();
            SerialPort serialPort = new SerialPort("COM2");
            serialPort.openPort();
            serialPort.setParams(300, 7, 2, SerialPort.PARITY_EVEN);
            serialPort.writeString("o");
            Thread.sleep(200);
            serialPort.writeString("\r");
            Thread.sleep(200);
            serialPort.readBytes(2); //Swallow echoed chars
            while (true) {
                try {
                    byte[] bytes = serialPort.readBytes();
                    if (bytes == null) {
                        continue;
                    }
                    for (byte b : bytes) {
                        bytesList.add(b);
                        if (bytesList.size() == 16) {
                            parse(bytesList);
                            System.exit(0);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parse(List<Byte> bytesList) {
        int hours = (bytesList.get(0) - 48) * 10 + (bytesList.get(1) - 48);
        int minutes = (bytesList.get(2) - 48) * 10 + (bytesList.get(3) - 48);
        int seconds = ((bytesList.get(4) - 48) * 10 + (bytesList.get(5) - 48))+1; //Responds on the next second with previous second's time...
        int dayOfWeek = bytesList.get(6) - 48;
        int dayOfMonth = (bytesList.get(7) - 48) * 10 + (bytesList.get(8) - 48);
        int month = (bytesList.get(9) - 48) * 10 + (bytesList.get(10) - 48);
        int year = (bytesList.get(11) - 48) * 10 + (bytesList.get(12) - 48);
        System.out.println(hours + ":" + minutes + ":" + seconds);
        System.out.println("Day of week: " + dayOfWeek);
        System.out.println(dayOfMonth + "/" + month + "/" + year);
        System.out.println(new TimeZoneStatus(bytesList.get(13)));
        System.out.println(new GeneralStatus(bytesList.get(14)));
    }
}
