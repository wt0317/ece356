/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Wilson
 */
@Entity
@Table(name = "Doctors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctors.findAll", query = "SELECT d FROM Doctors d"),
    @NamedQuery(name = "Doctors.findByUsername", query = "SELECT d FROM Doctors d WHERE d.username = :username"),
    @NamedQuery(name = "Doctors.findByDateHired", query = "SELECT d FROM Doctors d WHERE d.dateHired = :dateHired"),
    @NamedQuery(name = "Doctors.findByTerminationDate", query = "SELECT d FROM Doctors d WHERE d.terminationDate = :terminationDate")})
public class Doctors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private Integer username;
    @Column(name = "date_hired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHired;
    @Column(name = "termination_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date terminationDate;
    @JoinColumn(name = "license_id", referencedColumnName = "license_id")
    @ManyToOne
    private Licenses licenseId;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Directory directory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctors")
    private Collection<Appointments> appointmentsCollection;
    @OneToMany(mappedBy = "defaultDoctor")
    private Collection<Patients> patientsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctors")
    private Collection<Visitations> visitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctors")
    private Collection<StaffAssignments> staffAssignmentsCollection;

    public Doctors() {
    }

    public Doctors(Integer username) {
        this.username = username;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Date getDateHired() {
        return dateHired;
    }

    public void setDateHired(Date dateHired) {
        this.dateHired = dateHired;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Licenses getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Licenses licenseId) {
        this.licenseId = licenseId;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @XmlTransient
    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    @XmlTransient
    public Collection<Patients> getPatientsCollection() {
        return patientsCollection;
    }

    public void setPatientsCollection(Collection<Patients> patientsCollection) {
        this.patientsCollection = patientsCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctors)) {
            return false;
        }
        Doctors other = (Doctors) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Doctors[ username=" + username + " ]";
    }
    
}
