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
	private int numOfVisits;
	private Doctor defaultDoctor;
	private String currentHealth;
	
	public Patient(){
		
	}
	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
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
	public int getNumOfVisits() {
		return numOfVisits;
	}
	public void setNumOfVisits(int numOfVisits) {
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
	
	
}

