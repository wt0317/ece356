/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

/**
 *
 * @author Wilson
 */
public class Permission {
    private int username;
    private boolean accessibility;
    private boolean enabled = true;
    
    private String name;
    
    public Permission (int username, boolean accessibility) {
        this.username = username;
        this.accessibility = accessibility;
    }
    
    public int getUsername() {
        return username;
    }
    
    public boolean getAccessibility() {
        return accessibility;
    }
    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
