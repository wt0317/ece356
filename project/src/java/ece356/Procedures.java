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
@Table(name = "Procedures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procedures.findAll", query = "SELECT p FROM Procedures p"),
    @NamedQuery(name = "Procedures.findByProcedureId", query = "SELECT p FROM Procedures p WHERE p.procedureId = :procedureId"),
    @NamedQuery(name = "Procedures.findByProcedureName", query = "SELECT p FROM Procedures p WHERE p.procedureName = :procedureName")})
public class Procedures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "procedure_id")
    private Integer procedureId;
    @Column(name = "procedure_name")
    private String procedureName;
    @OneToMany(mappedBy = "procedureId")
    private Collection<Visitations> visitationsCollection;

    public Procedures() {
    }

    public Procedures(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public Integer getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
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
        hash += (procedureId != null ? procedureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Procedures)) {
            return false;
        }
        Procedures other = (Procedures) object;
        if ((this.procedureId == null && other.procedureId != null) || (this.procedureId != null && !this.procedureId.equals(other.procedureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Procedures[ procedureId=" + procedureId + " ]";
    }
    
}
