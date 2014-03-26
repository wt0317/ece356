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
    private Integer username;
    private String name;
    private Integer patientcount;
    
    public Doctor(){
    }
    
    public Doctor(Integer username) {
        this.username = username;
    }

    public Integer getUsername() {
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
    
    public Integer getPatientCount() {
        return patientcount;
    }

    public void setPatientCount(Integer patientcount) {
        this.patientcount = patientcount;
    }

}
