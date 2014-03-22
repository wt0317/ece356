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
@Table(name = "Diagnoses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diagnoses.findAll", query = "SELECT d FROM Diagnoses d"),
    @NamedQuery(name = "Diagnoses.findByDiagnosisId", query = "SELECT d FROM Diagnoses d WHERE d.diagnosisId = :diagnosisId"),
    @NamedQuery(name = "Diagnoses.findByDiagnosisName", query = "SELECT d FROM Diagnoses d WHERE d.diagnosisName = :diagnosisName")})
public class Diagnoses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "diagnosis_id")
    private Integer diagnosisId;
    @Column(name = "diagnosis_name")
    private String diagnosisName;
    @OneToMany(mappedBy = "diagnosisId")
    private Collection<Visitations> visitationsCollection;

    public Diagnoses() {
    }

    public Diagnoses(Integer diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Integer getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Integer diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
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
        hash += (diagnosisId != null ? diagnosisId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diagnoses)) {
            return false;
        }
        Diagnoses other = (Diagnoses) object;
        if ((this.diagnosisId == null && other.diagnosisId != null) || (this.diagnosisId != null && !this.diagnosisId.equals(other.diagnosisId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Diagnoses[ diagnosisId=" + diagnosisId + " ]";
    }
    
}
