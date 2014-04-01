/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FireChemist
 */
public class LookupPatientSummaryServlet extends HttpServlet {

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

        String url = "/lookupPatientSummary.jsp";

        List<Integer> listPatientID = new ArrayList<Integer>();
        List<PatientSummary> patientResults = new ArrayList<PatientSummary>();
        PreparedStatement getPatientID = null;
        PreparedStatement patientSummary = null;
        PreparedStatement numberOfVisits = null;
        int countPatient = 0;
        int countVisits = 0;
        boolean empty = true;
        Connection con = null;
        ResultSet rs1;
        ResultSet rs2;
        String visitationQuery;
        String numberOfVisitsQuery;
        int userid;
        int doctorid = Integer.parseInt(request.getParameter("doctor"));
        int patientid = Integer.parseInt(request.getParameter("username"));
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        try {
            con = DBAO.getConnection();
            
            if(startDate != null && endDate != null && startDate != "" && endDate != ""){
              request.setAttribute("startDate", startDate);
              request.setAttribute("endDate", endDate);
              numberOfVisitsQuery 
                      = "SELECT COUNT(*) AS total FROM Visitations "
                      + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? AND start_time >= '"+startDate+" 00:00:00' AND end_time <= '"+endDate+" 00:00:00' )";
            
              visitationQuery
                    = "SELECT diagnosis_id, diagnosis_name, "
                    + "prescription_id, prescription_name FROM "
                    + "Visitations NATURAL JOIN Prescriptions "
                    + "NATURAL JOIN Diagnoses "
                    + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? AND start_time >= '"+startDate+" 00:00:00' AND end_time <= '"+endDate+" 00:00:00' )";
            }
            else{
              numberOfVisitsQuery 
                      = "SELECT COUNT(*) AS total FROM Visitations "
                      + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? )";
            
              visitationQuery
                    = "SELECT diagnosis_id, diagnosis_name, "
                    + "prescription_id, prescription_name FROM "
                    + "Visitations NATURAL JOIN Prescriptions "
                    + "NATURAL JOIN Diagnoses "
                    + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? )";
            }

            //Good to display result (Diagnosis/result, Drugs Prescribed)
            

            patientSummary = con.prepareStatement(visitationQuery);
            patientSummary.setInt(1, doctorid);
            patientSummary.setInt(2, patientid);
            rs1 = patientSummary.executeQuery();

            numberOfVisits = con.prepareStatement(numberOfVisitsQuery);
            numberOfVisits.setInt(1, doctorid);
            numberOfVisits.setInt(2, patientid);
            rs2 = numberOfVisits.executeQuery();

            userid = Integer.parseInt(request.getParameter("username"));
            

            while (rs1.next()) {
                PatientSummary ps = new PatientSummary();
                ps.setDiagnosis_id(rs1.getInt(1));
                ps.setDiagnosis_name(rs1.getString(2));
                ps.setPrescription_id(rs1.getInt(3));
                ps.setPrescription_name(rs1.getString(4));
                patientResults.add(ps);
            }

            while (rs2.next()) {
                countVisits = rs2.getInt("total");
            }

            request.setAttribute("status", "Valid");
            request.setAttribute("patientResults", patientResults);
            request.setAttribute("countVisits", countVisits);
            request.setAttribute("resultName", userid);

        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
        //getServletContext().getRequestDispatcher(url).forward(request, response);
        //Get patient ID from Patient table
        //Search vistation records according to patient ID
        //Create object of list type to store records
        //Bind object to session
    }
        //Call jsp

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
    }
}
