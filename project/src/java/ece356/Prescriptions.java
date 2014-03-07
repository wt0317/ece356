/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Wilson
 */
@Entity
@Table(name = "Prescriptions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prescriptions.findAll", query = "SELECT p FROM Prescriptions p"),
    @NamedQuery(name = "Prescriptions.findByPrescriptionId", query = "SELECT p FROM Prescriptions p WHERE p.prescriptionId = :prescriptionId"),
    @NamedQuery(name = "Prescriptions.findByPrescriptionName", query = "SELECT p FROM Prescriptions p WHERE p.prescriptionName = :prescriptionName"),
    @NamedQuery(name = "Prescriptions.findByLegal", query = "SELECT p FROM Prescriptions p WHERE p.legal = :legal")})
public class Prescriptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Column(name = "prescription_name")
    private String prescriptionName;
    @Column(name = "legal")
    private Boolean legal;
    @OneToMany(mappedBy = "prescriptionId")
    private Collection<Visitations> visitationsCollection;

    public Prescriptions() {
    }

    public Prescriptions(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionName() {
        return prescriptionName;
    }

    public void setPrescriptionName(String prescriptionName) {
        this.prescriptionName = prescriptionName;
    }

    public Boolean getLegal() {
        return legal;
    }

    public void setLegal(Boolean legal) {
        this.legal = legal;
    }

    @XmlTransient
    public Collection<Visitations> getVisitationsCollection() {
        return visitationsCollection;
    }

    public void setVisitationsCollection(Collection<Visitations> visitationsCollection) {
        this.visitationsCollection = visitationsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prescriptionId != null ? prescriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prescriptions)) {
            return false;
        }
        Prescriptions other = (Prescriptions) object;
        if ((this.prescriptionId == null && other.prescriptionId != null) || (this.prescriptionId != null && !this.prescriptionId.equals(other.prescriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Prescriptions[ prescriptionId=" + prescriptionId + " ]";
    }
    
}
