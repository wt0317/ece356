/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wilson
 */
public class LookupPatientServlet extends HttpServlet {

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
        String url = "/lookupPatient.jsp";
        Connection con = null;
        List<Patient> listPatients = new ArrayList<Patient>();
        try {
            con = DBAO.getConnection();
            
            // TODO: need to account for permissions for different roles
            String getPatientsQuery = "SELECT U.username, U.health_card, U.social_insurance_number, U.number_of_visits, U.default_doctor, "
                    + "U.current_health, U.comment, P.`name` AS patient_name, D.`name` AS doctor_name "
                    + "FROM Patients U, Directory P, Directory D WHERE U.username = P.username AND U.default_doctor = D.username";
            PreparedStatement getPatientsStmt = con.prepareStatement(getPatientsQuery);
            ResultSet rs = getPatientsStmt.executeQuery();
            while (rs.next()) {
                Doctor d = new Doctor(rs.getInt("default_doctor"), rs.getString("doctor_name"));
                Patient p = new Patient(rs.getInt("username"), rs.getString("health_card"), rs.getString("social_insurance_number"),
                                        rs.getInt("number_of_visits"), d, rs.getString("current_health"), rs.getString("comment"));
                p.setName(rs.getString("patient_name"));
                listPatients.add(p);
            }
            request.setAttribute("listPatients", listPatients);
        }
        catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
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
