/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wilson
 */
@Embeddable
public class AppointmentsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "doctor")
    private int doctor;

    public AppointmentsPK() {
    }

    public AppointmentsPK(Date startTime, int doctor) {
        this.startTime = startTime;
        this.doctor = doctor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
        hash += (startTime != null ? startTime.hashCode() : 0);
        hash += (int) doctor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentsPK)) {
            return false;
        }
        AppointmentsPK other = (AppointmentsPK) object;
        if ((this.startTime == null && other.startTime != null) || (this.startTime != null && !this.startTime.equals(other.startTime))) {
            return false;
        }
        if (this.doctor != other.doctor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.AppointmentsPK[ startTime=" + startTime + ", doctor=" + doctor + " ]";
    }
    
}
