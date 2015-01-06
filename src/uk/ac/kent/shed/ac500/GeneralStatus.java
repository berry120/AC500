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
public class GeneralStatus {
    
    private byte statusByte;
    
    public GeneralStatus(byte statusByte) {
        this.statusByte = statusByte;
    }
    
    public boolean containsValidTimeInfo() {
        return (statusByte & 0b1) == 0b1;
    }
    
    public boolean successfulSince230() {
        return (statusByte & 0b10) == 0b010;
    }
    
    public boolean lastReceptionSuccessful() {
        return (statusByte & 0b100) == 0;
    }
    
    public boolean lowBatteryVoltage() {
        return (statusByte & 0b1000) == 0b1000;
    }
    
    @Override
    public String toString() {
        return "Valid time info: " + containsValidTimeInfo() + "\n" +
                "Successful reception attempt since 0230: " + successfulSince230() + "\n" +
                "Successful last reception attempt: " + lastReceptionSuccessful() + "\n" + 
                "Low battery voltage: " + lowBatteryVoltage();
    }
}
