/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

/**
 *
 * @author Johnny
 */
public class StaffAssignment {
    private int staff;
    private int doctor;
    private boolean enabled;
    
    public StaffAssignment(int staff, int doctor, boolean enabled){
        this.staff = staff;
        this.doctor = doctor;
        this.enabled = enabled;
    }
    
    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }
    
    public int getDoctor() {
        return doctor;
    }
    
    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
