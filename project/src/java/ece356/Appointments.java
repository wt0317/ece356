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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson
 */
@Entity
@Table(name = "Appointments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointments.findAll", query = "SELECT a FROM Appointments a"),
    @NamedQuery(name = "Appointments.findByStartTime", query = "SELECT a FROM Appointments a WHERE a.appointmentsPK.startTime = :startTime"),
    @NamedQuery(name = "Appointments.findByEndTime", query = "SELECT a FROM Appointments a WHERE a.endTime = :endTime"),
    @NamedQuery(name = "Appointments.findByDoctor", query = "SELECT a FROM Appointments a WHERE a.appointmentsPK.doctor = :doctor"),
    @NamedQuery(name = "Appointments.findByCreationTime", query = "SELECT a FROM Appointments a WHERE a.creationTime = :creationTime")})
public class Appointments implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AppointmentsPK appointmentsPK;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;
    @JoinColumn(name = "created_by", referencedColumnName = "username")
    @ManyToOne
    private Directory createdBy;
    @JoinColumn(name = "patient", referencedColumnName = "username")
    @ManyToOne
    private Patients patient;
    @JoinColumn(name = "doctor", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Doctors doctors;

    public Appointments() {
    }

    public Appointments(AppointmentsPK appointmentsPK) {
        this.appointmentsPK = appointmentsPK;
    }

    public Appointments(AppointmentsPK appointmentsPK, Date creationTime) {
        this.appointmentsPK = appointmentsPK;
        this.creationTime = creationTime;
    }

    public Appointments(Date startTime, int doctor) {
        this.appointmentsPK = new AppointmentsPK(startTime, doctor);
    }

    public AppointmentsPK getAppointmentsPK() {
        return appointmentsPK;
    }

    public void setAppointmentsPK(AppointmentsPK appointmentsPK) {
        this.appointmentsPK = appointmentsPK;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Directory getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Directory createdBy) {
        this.createdBy = createdBy;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public Doctors getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentsPK != null ? appointmentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointments)) {
            return false;
        }
        Appointments other = (Appointments) object;
        if ((this.appointmentsPK == null && other.appointmentsPK != null) || (this.appointmentsPK != null && !this.appointmentsPK.equals(other.appointmentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Appointments[ appointmentsPK=" + appointmentsPK + " ]";
    }
    
}
