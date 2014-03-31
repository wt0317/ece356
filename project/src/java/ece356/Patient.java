/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

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
        private String comment;
        
        // attributes not part of Patients table
        private String name;
        private List<Permission> permissions;
	
	public Patient(){
		
	}
        public Patient (int username, String healthCard, String sin, int numOfVisits, Doctor defaultDoctor, String currentHealth, String comment) {
            this.username = username;
            this.healthCard = healthCard;
            this.sin = sin;
            this.numOfVisits = numOfVisits;
            this.defaultDoctor = defaultDoctor;
            this.currentHealth = currentHealth;
            this.comment = comment;
        } 
        
        public Patient(int username, String name) {
            this.username = username;
            this.name = name;
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
        public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        
        public List<Permission> getPermissions() {
            return permissions;
        }
        public void addPermission(Permission permission) {
            if (permissions == null)
                permissions = new ArrayList<Permission>();
            
            permissions.add(permission);
        }
        
        public String toJSON() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
	
	
}

