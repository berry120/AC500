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

    private final byte timeZoneByte;

    TimeZoneStatus(byte timeZoneByte) {
        this.timeZoneByte = timeZoneByte;
    }
    
    public String getTimeZone() {
        String timeZone;
        if(isBST()) {
            timeZone = "BST";
        }
        else if(isUTC()) {
            timeZone = "UTC";
        }
        else {
            timeZone = "Unknown";
        }
        return timeZone;
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
        return "Time zone: " + getTimeZone() + "\n"
                + "Change impending: " + isChangeImpending();
    }

}
