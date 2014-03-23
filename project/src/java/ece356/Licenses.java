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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "Licenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Licenses.findAll", query = "SELECT l FROM Licenses l"),
    @NamedQuery(name = "Licenses.findByLicenseId", query = "SELECT l FROM Licenses l WHERE l.licenseId = :licenseId"),
    @NamedQuery(name = "Licenses.findByLicenseIssueDate", query = "SELECT l FROM Licenses l WHERE l.licenseIssueDate = :licenseIssueDate"),
    @NamedQuery(name = "Licenses.findByLicenseExpiryDate", query = "SELECT l FROM Licenses l WHERE l.licenseExpiryDate = :licenseExpiryDate")})
public class Licenses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "license_id")
    private String licenseId;
    @Column(name = "license_issue_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date licenseIssueDate;
    @Column(name = "license_expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date licenseExpiryDate;
    @OneToMany(mappedBy = "licenseId")
    private Collection<Doctors> doctorsCollection;

    public Licenses() {
    }

    public Licenses(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public Date getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(Date licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    @XmlTransient
    public Collection<Doctors> getDoctorsCollection() {
        return doctorsCollection;
    }

    public void setDoctorsCollection(Collection<Doctors> doctorsCollection) {
        this.doctorsCollection = doctorsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licenseId != null ? licenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Licenses)) {
            return false;
        }
        Licenses other = (Licenses) object;
        if ((this.licenseId == null && other.licenseId != null) || (this.licenseId != null && !this.licenseId.equals(other.licenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Licenses[ licenseId=" + licenseId + " ]";
    }
    
}
