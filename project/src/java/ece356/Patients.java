/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johnny
 */
@Entity
@Table(name = "Patients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patients.findAll", query = "SELECT p FROM Patients p"),
    @NamedQuery(name = "Patients.findByUsername", query = "SELECT p FROM Patients p WHERE p.username = :username"),
    @NamedQuery(name = "Patients.findByHealthCard", query = "SELECT p FROM Patients p WHERE p.healthCard = :healthCard"),
    @NamedQuery(name = "Patients.findBySocialInsuranceNumber", query = "SELECT p FROM Patients p WHERE p.socialInsuranceNumber = :socialInsuranceNumber"),
    @NamedQuery(name = "Patients.findByNumberOfVisits", query = "SELECT p FROM Patients p WHERE p.numberOfVisits = :numberOfVisits")})
public class Patients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private Integer username;
    @Column(name = "health_card")
    private String healthCard;
    @Column(name = "social_insurance_number")
    private String socialInsuranceNumber;
    @Column(name = "number_of_visits")
    private Integer numberOfVisits;
    @Lob
    @Column(name = "current_health")
    private String currentHealth;
    @Lob
    @Column(name = "comment")
    private String comment;
    @OneToMany(mappedBy = "patient")
    private Collection<Appointments> appointmentsCollection;
    @JoinColumn(name = "default_doctor", referencedColumnName = "username")
    @ManyToOne
    private Doctors defaultDoctor;
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Directory directory;
    @OneToMany(mappedBy = "patient")
    private Collection<Visitations> visitationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patients")
    private Collection<Permissions> permissionsCollection;
    private static EntityManagerFactory emf;

   public Patients() {
      
    }
    public Patients(Integer username) {
        this.username = username;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public String getHealthCard() {
        return healthCard;
    }

    public void setHealthCard(String healthCard) {
        this.healthCard = healthCard;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public Integer getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Integer numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public String getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(String currentHealth) {
        this.currentHealth = currentHealth;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @XmlTransient
    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    public Doctors getDefaultDoctor() {
        return defaultDoctor;
    }

    public void setDefaultDoctor(Doctors defaultDoctor) {
        this.defaultDoctor = defaultDoctor;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    @XmlTransient
    public Collection<Visitations> getVisitationsCollection() {
        return visitationsCollection;
    }

    public void setVisitationsCollection(Collection<Visitations> visitationsCollection) {
        this.visitationsCollection = visitationsCollection;
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
        if (!(object instanceof Patients)) {
            return false;
        }
        Patients other = (Patients) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ece356.Patients[ username=" + username + " ]";
    }
        public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String url;
                try {    
                    //Create a new session object
                    HttpSession session = request.getSession();
                    int username = Integer.parseInt(request.getParameter("username"));
                    String name = request.getParameter("name");
                    String address = request.getParameter("address");
                    String phoneNum = request.getParameter("phonenum");
                    String hin = request.getParameter("hin");
                    String sin = request.getParameter("sin");
                    Directory user = DBAO.updateUser(username, name, address, phoneNum, hin, sin);

                    //Set attributes of session object
                    session.setAttribute("userObject", user);

                    //Redirect to appropriate page
                    url="/welcome.jsp";       
                } catch (Exception e) {
                    request.setAttribute("exception", e);
                    url="/error.jsp";
                }

                getServletContext().getRequestDispatcher(url).forward(request, response);
            }

            // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /**
         * Handles the HTTP <code>GET</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Handles the HTTP <code>POST</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>

    }

}
