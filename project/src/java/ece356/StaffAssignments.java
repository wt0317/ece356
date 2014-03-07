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
@Table(name = "Staff_Assignments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffAssignments.findAll", query = "SELECT s FROM StaffAssignments s"),
    @NamedQuery(name = "StaffAssignments.findByStaff", query = "SELECT s FROM StaffAssignments s WHERE s.staffAssignmentsPK.staff = :staff"),
    @NamedQuery(name = "StaffAssignments.findByDoctor", query = "SELECT s FROM StaffAssignments s WHERE s.staffAssignmentsPK.doctor = :doctor"),
    @NamedQuery(name = "StaffAssignments.findByEnabled", query = "SELECT s FROM StaffAssignments s WHERE s.enabled = :enabled")})
public class StaffAssignments implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StaffAssignmentsPK staffAssignmentsPK;
    @Column(name = "enabled")
    private Boolean enabled;
    @JoinColumn(name = "doctor", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Doctors doctors;
    @JoinColumn(name = "staff", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Directory directory;

    public StaffAssignments() {
    }

    public StaffAssignments(StaffAssignmentsPK staffAssignmentsPK) {
        this.staffAssignmentsPK = staffAssignmentsPK;
    }

    public StaffAssignments(int staff, int doctor) {
        this.staffAssignmentsPK = new StaffAssignmentsPK(staff, doctor);
    }

    public StaffAssignmentsPK getStaffAssignmentsPK() {
        return staffAssignmentsPK;
    }

    public void setStaffAssignmentsPK(StaffAssignmentsPK staffAssignmentsPK) {
        this.staffAssignmentsPK = staffAssignmentsPK;
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

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffAssignmentsPK != null ? staffAssignmentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaffAssignments)) {
            return false;
        }
        StaffAssignments other = (StaffAssignments) object;
        if ((this.staffAssignmentsPK == null && other.staffAssignmentsPK != null) || (this.staffAssignmentsPK != null && !this.staffAssignmentsPK.equals(other.staffAssignmentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.StaffAssignments[ staffAssignmentsPK=" + staffAssignmentsPK + " ]";
    }
    
}
