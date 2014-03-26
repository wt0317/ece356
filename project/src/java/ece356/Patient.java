/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

/**
 *
 * @author Sieyor
 */

public class Patient {

	private int username;
	private String healthCard;
	private String sin;
	private String numOfVisits;
	private Doctor defaultDoctor;
	private String currentHealth;
        private String comment;
	
	public Patient(){
		
	}
        public Patient (String username, String healthCard, String sin, String numOfVisits, Doctor defaultDoctor, String currentHealth, String comment) {
            this.username = username;
            this.healthCard = healthCard;
            this.sin = sin;
            this.numOfVisits = numOfVisits;
            this.defaultDoctor = defaultDoctor;
            this.currentHealth = currentHealth;
            this.comment = comment;
        } 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHealthCard() {
		return healthCard;
	}
	public void setHealthCard(String healthCard) {
		this.healthCard = healthCard;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getNumOfVisits() {
		return numOfVisits;
	}
	public void setNumOfVisits(String numOfVisits) {
		this.numOfVisits = numOfVisits;
	}
	public Doctor getDefaultDoctor() {
		return defaultDoctor;
	}
	public void setDefaultDoctor(Doctor defaultDoctor) {
		this.defaultDoctor = defaultDoctor;
	}
	public String getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(String currentHealth) {
		this.currentHealth = currentHealth;
	}
        public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}

