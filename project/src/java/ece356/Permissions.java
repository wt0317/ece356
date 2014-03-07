/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson
 */
@Entity
@Table(name = "Permissions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permissions.findAll", query = "SELECT p FROM Permissions p"),
    @NamedQuery(name = "Permissions.findByEmployee", query = "SELECT p FROM Permissions p WHERE p.permissionsPK.employee = :employee"),
    @NamedQuery(name = "Permissions.findByPatient", query = "SELECT p FROM Permissions p WHERE p.permissionsPK.patient = :patient"),
    @NamedQuery(name = "Permissions.findByAccessibility", query = "SELECT p FROM Permissions p WHERE p.accessibility = :accessibility"),
    @NamedQuery(name = "Permissions.findByEnabled", query = "SELECT p FROM Permissions p WHERE p.enabled = :enabled")})
public class Permissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PermissionsPK permissionsPK;
    @Column(name = "accessibility")
    private Character accessibility;
    @Column(name = "enabled")
    private Boolean enabled;
    @JoinColumn(name = "patient", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Patients patients;
    @JoinColumn(name = "employee", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Directory directory;

    public Permissions() {
    }

    public Permissions(PermissionsPK permissionsPK) {
        this.permissionsPK = permissionsPK;
    }

    public Permissions(int employee, int patient) {
        this.permissionsPK = new PermissionsPK(employee, patient);
    }

    public PermissionsPK getPermissionsPK() {
        return permissionsPK;
    }

    public void setPermissionsPK(PermissionsPK permissionsPK) {
        this.permissionsPK = permissionsPK;
    }

    public Character getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Character accessibility) {
        this.accessibility = accessibility;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permissionsPK != null ? permissionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permissions)) {
            return false;
        }
        Permissions other = (Permissions) object;
        if ((this.permissionsPK == null && other.permissionsPK != null) || (this.permissionsPK != null && !this.permissionsPK.equals(other.permissionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Permissions[ permissionsPK=" + permissionsPK + " ]";
    }
    
}
