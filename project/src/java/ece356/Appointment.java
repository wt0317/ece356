/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johnny
 */
public class Appointment {
    private String title;
    private String startTime;
    private String endTime;
    private int doctor;
    private int patient;
    private int createdBy;
    private int creationTime;
    private String doctorName;
    private String patientName;
    private String createdByName;
    
    public Appointment(String title, String startTime, String endTime, int doctor, int patient, int createdBy){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.patient = patient;
        this.createdBy = createdBy;
    }
    
    public Appointment(String title, String startTime, String endTime, int doctor, String doctorName, String patientName, String createdByName) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.createdByName = createdByName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorname(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    
    public String toStringJSON() {
        String text = "{"
            + "\"title\": \"" + this.title + "\","
            + "\"start\": \"" + this.startTime + "\","
            + "\"end\": \"" + this.endTime + "\","
            + "\"doctorID\":" + this.doctor + ","
            + "\"doctor\": \"" + this.doctorName + "\","
            + "\"patient\": \"" + this.patientName + "\","
            + "\"creator\": \"" + this.createdByName + "\""
        +"}";        
        return text;
    }
    
    public String getTimeString(String dateTime) throws ParseException {
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat printFormat = new SimpleDateFormat("h:mm a");
        java.util.Date date = parseFormat.parse(dateTime);
        return printFormat.format(date);
    }

    public String getDateString(String dateTime) throws ParseException {
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat printFormat = new SimpleDateFormat("EEE, MMMMM dd, yyyy");
        java.util.Date date = parseFormat.parse(dateTime);
        return printFormat.format(date);
    }    
}