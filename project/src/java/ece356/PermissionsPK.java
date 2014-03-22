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
public class PermissionsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "employee")
    private int employee;
    @Basic(optional = false)
    @Column(name = "patient")
    private int patient;

    public PermissionsPK() {
    }

    public PermissionsPK(int employee, int patient) {
        this.employee = employee;
        this.patient = patient;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) employee;
        hash += (int) patient;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermissionsPK)) {
            return false;
        }
        PermissionsPK other = (PermissionsPK) object;
        if (this.employee != other.employee) {
            return false;
        }
        if (this.patient != other.patient) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.PermissionsPK[ employee=" + employee + ", patient=" + patient + " ]";
    }
    
}
