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
public class VisitationsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "doctor")
    private int doctor;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    public VisitationsPK() {
    }

    public VisitationsPK(int doctor, Date startTime, Date creationTime) {
        this.doctor = doctor;
        this.startTime = startTime;
        this.creationTime = creationTime;
    }

    public int getDoctor() {
        return doctor;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) doctor;
        hash += (startTime != null ? startTime.hashCode() : 0);
        hash += (creationTime != null ? creationTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitationsPK)) {
            return false;
        }
        VisitationsPK other = (VisitationsPK) object;
        if (this.doctor != other.doctor) {
            return false;
        }
        if ((this.startTime == null && other.startTime != null) || (this.startTime != null && !this.startTime.equals(other.startTime))) {
            return false;
        }
        if ((this.creationTime == null && other.creationTime != null) || (this.creationTime != null && !this.creationTime.equals(other.creationTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.VisitationsPK[ doctor=" + doctor + ", startTime=" + startTime + ", creationTime=" + creationTime + " ]";
    }
    
}
