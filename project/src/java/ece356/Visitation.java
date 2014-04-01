/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import com.google.gson.Gson;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FireChemist
 */
public class Visitation {

    private int patient;
    private int doctor;
    private int procedureId;
    private int diagnosisId;
    private int prescriptionId;
    private int createdBy;
    private int surgeryId;
    private String patientName;
    private String doctorName;
    private String createdName;
    private String procedureName;
    private String diagnosisName;
    private String prescriptionName;
    private String surgeryName;
    private String timeScheduled;
    private String startTime;
    private String endTime;
    private String creationTime;
    private String comments;
    private String revisionComments;
    private boolean legal;
    
    List<Visitation> changelog;

    public Visitation() {

    }
    
    public Visitation (int patient, int doctor, String startTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.startTime = startTime;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }
    
    public void setPatientName(String patient) {
        this.patientName = patient;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public void setDiagnosisName(String DiagnosisName) {
        this.diagnosisName = DiagnosisName;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public void setTimeScheduled(Timestamp timeScheduled) {
        this.timeScheduled = timeScheduled.toString();
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime.toString();
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime.toString();
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime.toString();
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public void setSurgeryId(int surgeryId) {
        this.surgeryId = surgeryId;
    }

    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRevisionComments(String revisionComments) {
        this.revisionComments = revisionComments;
    }

    public int getPatient() {
        return patient;
    }
     
    public String getPatientName() {
        return patientName;
    }

    public int getDoctor() {
        return doctor;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getCreatedName() {
        return createdName;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public int getdiagnosisId() {
        return diagnosisId;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public String getTimeScheduled() {
        return timeScheduled;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getSurgeryId() {
        return surgeryId;
    }

    public String getSurgeryName() {
        return surgeryName;
    }

    public String getComments() {
        return comments;
    }

    public String getRevisionComments() {
        return revisionComments;
    }
    
    public void addChangelog(Visitation record) {
        if (changelog == null) {
            changelog = new ArrayList<Visitation>();
        }
        changelog.add(record);
    }
    public List<Visitation> getChangelog() {
        if (this.changelog == null) {
            return new ArrayList<Visitation>();
        }
        return this.changelog;
    }
    
    public int numChanges() {
        if (this.changelog == null)
            return 0;
        return this.changelog.size();
    }
    
    public boolean isLegal() {
        return legal;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }
    
    public String toJSON() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }

}
