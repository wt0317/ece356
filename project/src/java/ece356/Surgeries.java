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
@Table(name = "Surgeries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Surgeries.findAll", query = "SELECT s FROM Surgeries s"),
    @NamedQuery(name = "Surgeries.findBySurgeryId", query = "SELECT s FROM Surgeries s WHERE s.surgeryId = :surgeryId"),
    @NamedQuery(name = "Surgeries.findBySurgeryName", query = "SELECT s FROM Surgeries s WHERE s.surgeryName = :surgeryName")})
public class Surgeries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "surgery_id")
    private Integer surgeryId;
    @Column(name = "surgery_name")
    private String surgeryName;
    @OneToMany(mappedBy = "surgeryId")
    private Collection<Visitations> visitationsCollection;

    public Surgeries() {
    }

    public Surgeries(Integer surgeryId) {
        this.surgeryId = surgeryId;
    }

    public Integer getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(Integer surgeryId) {
        this.surgeryId = surgeryId;
    }

    public String getSurgeryName() {
        return surgeryName;
    }

    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
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
        hash += (surgeryId != null ? surgeryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Surgeries)) {
            return false;
        }
        Surgeries other = (Surgeries) object;
        if ((this.surgeryId == null && other.surgeryId != null) || (this.surgeryId != null && !this.surgeryId.equals(other.surgeryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Surgeries[ surgeryId=" + surgeryId + " ]";
    }
    
}
