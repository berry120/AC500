/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.kent.shed.ac500;

/**
 *
 * @author mjrb5
 */
public class ReceptionStatus {

    private boolean receiving;
    private int quality;

    ReceptionStatus(byte[] arr) {
        receiving = (arr[0] & 1) == 1;
        quality = arr[1] - 48;
    }

    public boolean isReceiving() {
        return receiving;
    }

    public int getQuality() {
        return quality;
    }

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
