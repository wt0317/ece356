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
class Appointment {
    private String startTime;
    private String endTime;
    private int doctor;
    private int patient;
    private int createdBy;
    private int creationTime;
    
    public Appointment(String startTime, String endTime, int doctor, int patient, int createdBy, int creationTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.patient = patient;
        this.createdBy = createdBy;
        this.creationTime = creationTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDoctor() {
        return doctor;
    }
    
    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getCreationTime() {
        return creationTime;
    }
}
