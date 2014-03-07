/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Wilson
 */
@Entity
@Table(name = "Directory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Directory.findAll", query = "SELECT d FROM Directory d"),
    @NamedQuery(name = "Directory.findByUsername", query = "SELECT d FROM Directory d WHERE d.username = :username"),
    @NamedQuery(name = "Directory.findByName", query = "SELECT d FROM Directory d WHERE d.name = :name"),
    @NamedQuery(name = "Directory.findByPassword", query = "SELECT d FROM Directory d WHERE d.password = :password"),
    @NamedQuery(name = "Directory.findByRole", query = "SELECT d FROM Directory d WHERE d.role = :role"),
    @NamedQuery(name = "Directory.findByEnabled", query = "SELECT d FROM Directory d WHERE d.enabled = :enabled")})
public class Directory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private Integer username;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "enabled")
    private Boolean enabled;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "directory")
    private Doctors doctors;
    @OneToMany(mappedBy = "createdBy")
    private Collection<Appointments> appointmentsCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "directory")
    private Patients patients;
    @OneToMany(mappedBy = "createdBy")
    private Collection<Visitations> visitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
    private Collection<StaffAssignments> staffAssignmentsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "directory")
    private Collection<Permissions> permissionsCollection;

    public Directory() {
    }

    public Directory(Integer username) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Doctors getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
    }

    @XmlTransient
    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    @XmlTransient
    public Collection<Visitations> getVisitationsCollection() {
        return visitationsCollection;
    }

    public void setVisitationsCollection(Collection<Visitations> visitationsCollection) {
        this.visitationsCollection = visitationsCollection;
    }

    @XmlTransient
    public Collection<StaffAssignments> getStaffAssignmentsCollection() {
        return staffAssignmentsCollection;
    }

    public void setStaffAssignmentsCollection(Collection<StaffAssignments> staffAssignmentsCollection) {
        this.staffAssignmentsCollection = staffAssignmentsCollection;
    }

    @XmlTransient
    public Collection<Permissions> getPermissionsCollection() {
        return permissionsCollection;
    }

    public void setPermissionsCollection(Collection<Permissions> permissionsCollection) {
        this.permissionsCollection = permissionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Directory)) {
            return false;
        }
        Directory other = (Directory) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Directory[ username=" + username + " ]";
    }
    
}
