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
public class TimeZoneStatus {

    private byte timeZoneByte;

    public TimeZoneStatus(byte timeZoneByte) {
        this.timeZoneByte = timeZoneByte;
    }

    public boolean isUTC() {
        return (timeZoneByte & 0b100) == 0b100;
    }

    public boolean isBST() {
        return (timeZoneByte & 0b10) == 0b10;
    }

    public boolean isChangeImpending() {
        return (timeZoneByte & 1) == 1;
    }

    @Override
    public String toString() {
        String timeZone;
        if(isBST()) {
            timeZone = "BST";
        }
        else if(isUTC()) {
            timeZone = "UTC";
        }
        else {
            timeZone = "Error";
        }
        return "Time zone: " + timeZone + "\n"
                + "Change impending: " + isChangeImpending();
    }

}
