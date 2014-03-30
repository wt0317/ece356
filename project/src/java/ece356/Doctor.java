/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

/**
 *
 * @author Rakin
 */
public class Doctor {
    private int username;
    private String name;
    private int patientcount;
    private String licenceId;
    private String dateHired;
    
    public Doctor(){
    }
    
    public Doctor(int username) {
        this.username = username;
    }
    
    public Doctor(int username, String name) {
        this.username = username;
        this.name = name;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getPatientCount() {
        return patientcount;
    }

    public void setPatientCount(int patientcount) {
        this.patientcount = patientcount;
    }
    public String getLicenseId() {
        return licenceId;
    }

    public void setLicenseId(String licenceId) {
        this.licenceId = licenceId;
    }
        public String getDateHired() {
        return dateHired;
    }

    public void setDateHired(String dateHired) {
        this.dateHired = dateHired;
    }

}
