/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

/**
 *
 * @author FireChemist
 */
public class PatientSummary {
    private int diagnosis_id;
    private int prescription_id;
    private String prescription_name;
    private String diagnosis_name;
    
    public void setDiagnosis_id(int diagnosis_id) {
        this.diagnosis_id = diagnosis_id;
    }
    
    public void setDiagnosis_name(String diagnosis_name) {
        this.diagnosis_name = diagnosis_name;
    }
    
    public void setPrescription_name(String prescription_name) {
        this.prescription_name = prescription_name;
    }
    
    public void setPrescription_id(int prescription_id) {
        this.prescription_id = prescription_id;
    }
    
    public int getDiagnosis_id() {
        return diagnosis_id;
    }
    
    public int getPrescription_id() {
        return prescription_id;
    }
    
    public String getPrescription_name() {
        return prescription_name;
    }
    
    public String getDiagnosis_name() {
        return diagnosis_name;
    }
}
