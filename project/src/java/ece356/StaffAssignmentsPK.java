/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Wilson
 */
@Embeddable
public class StaffAssignmentsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "staff")
    private int staff;
    @Basic(optional = false)
    @Column(name = "doctor")
    private int doctor;

    public StaffAssignmentsPK() {
    }

    public StaffAssignmentsPK(int staff, int doctor) {
        this.staff = staff;
        this.doctor = doctor;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) staff;
        hash += (int) doctor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaffAssignmentsPK)) {
            return false;
        }
        StaffAssignmentsPK other = (StaffAssignmentsPK) object;
        if (this.staff != other.staff) {
            return false;
        }
        if (this.doctor != other.doctor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.StaffAssignmentsPK[ staff=" + staff + ", doctor=" + doctor + " ]";
    }
    
}
