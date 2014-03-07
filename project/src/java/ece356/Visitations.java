/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "Visitations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visitations.findAll", query = "SELECT v FROM Visitations v"),
    @NamedQuery(name = "Visitations.findByDoctor", query = "SELECT v FROM Visitations v WHERE v.visitationsPK.doctor = :doctor"),
    @NamedQuery(name = "Visitations.findByTimeScheduled", query = "SELECT v FROM Visitations v WHERE v.timeScheduled = :timeScheduled"),
    @NamedQuery(name = "Visitations.findByStartTime", query = "SELECT v FROM Visitations v WHERE v.visitationsPK.startTime = :startTime"),
    @NamedQuery(name = "Visitations.findByEndTime", query = "SELECT v FROM Visitations v WHERE v.endTime = :endTime"),
    @NamedQuery(name = "Visitations.findByCreationTime", query = "SELECT v FROM Visitations v WHERE v.visitationsPK.creationTime = :creationTime")})
public class Visitations implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitationsPK visitationsPK;
    @Column(name = "time_scheduled")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeScheduled;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Lob
    @Column(name = "comments")
    private String comments;
    @Lob
    @Column(name = "revision_comments")
    private String revisionComments;
    @JoinColumn(name = "surgery_id", referencedColumnName = "surgery_id")
    @ManyToOne
    private Surgeries surgeryId;
    @JoinColumn(name = "created_by", referencedColumnName = "username")
    @ManyToOne
    private Directory createdBy;
    @JoinColumn(name = "prescription_id", referencedColumnName = "prescription_id")
    @ManyToOne
    private Prescriptions prescriptionId;
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "diagnosis_id")
    @ManyToOne
    private Diagnoses diagnosisId;
    @JoinColumn(name = "procedure_id", referencedColumnName = "procedure_id")
    @ManyToOne
    private Procedures procedureId;
    @JoinColumn(name = "doctor", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Doctors doctors;
    @JoinColumn(name = "patient", referencedColumnName = "username")
    @ManyToOne
    private Patients patient;

    public Visitations() {
    }

    public Visitations(VisitationsPK visitationsPK) {
        this.visitationsPK = visitationsPK;
    }

    public Visitations(int doctor, Date startTime, Date creationTime) {
        this.visitationsPK = new VisitationsPK(doctor, startTime, creationTime);
    }

    public VisitationsPK getVisitationsPK() {
        return visitationsPK;
    }

    public void setVisitationsPK(VisitationsPK visitationsPK) {
        this.visitationsPK = visitationsPK;
    }

    public Date getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(Date timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRevisionComments() {
        return revisionComments;
    }

    public void setRevisionComments(String revisionComments) {
        this.revisionComments = revisionComments;
    }

    public Surgeries getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(Surgeries surgeryId) {
        this.surgeryId = surgeryId;
    }

    public Directory getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Directory createdBy) {
        this.createdBy = createdBy;
    }

    public Prescriptions getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Prescriptions prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Diagnoses getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Diagnoses diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Procedures getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Procedures procedureId) {
        this.procedureId = procedureId;
    }

    public Doctors getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitationsPK != null ? visitationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visitations)) {
            return false;
        }
        Visitations other = (Visitations) object;
        if ((this.visitationsPK == null && other.visitationsPK != null) || (this.visitationsPK != null && !this.visitationsPK.equals(other.visitationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Visitations[ visitationsPK=" + visitationsPK + " ]";
    }
    
}
