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

        try {
            con = DBAO.getConnection();

            //Lookup patient by name
            if (!request.getParameterMap().containsKey("username")) {
                String patientQuery
                        = "SELECT username FROM Directory "
                        + "WHERE name LIKE ? and role = 'patient'";

                getPatientID = con.prepareStatement(patientQuery);
                getPatientID.setString(1, "%" + request.getParameter("name") + "%");
                rs1 = getPatientID.executeQuery();

                //There is at least one result
                while (rs1.next()) {
                    empty = false;
                    countPatient++;
                    listPatientID.add(rs1.getInt("username"));
                }

                //Force user to input patient ID
                if (countPatient > 1) {
                    request.setAttribute("error", "Duplicate");
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("listPatientID", listPatientID);
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //No result
                if (empty) {
                    request.setAttribute("error", "Invalid");
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //Good to display result (Diagnosis/result, Drugs Prescribed)
                visitationQuery
                        = "SELECT diagnosis_id, diagnosis_name, "
                        + "prescription_id, prescription_name FROM "
                        + "Visitations NATURAL JOIN Prescriptions "
                        + "NATURAL JOIN Diagnoses WHERE patient = ?";

                patientSummary = con.prepareStatement(visitationQuery);
                assert (listPatientID.size() == 1);
                patientSummary.setInt(1, listPatientID.get(0));
                rs1 = patientSummary.executeQuery();

                numberOfVisitsQuery
                        = "SELECT COUNT(*) AS total "
                        + "FROM Visitations WHERE patient = ?";
                numberOfVisits = con.prepareStatement(numberOfVisitsQuery);
                numberOfVisits.setInt(1, listPatientID.get(0));
                rs2 = numberOfVisits.executeQuery();
                
                userid = listPatientID.get(0);
            } //Lookup patient by username
            else {

                //Good to display result (Diagnosis/result, Drugs Prescribed)
                visitationQuery
                        = "SELECT diagnosis_id, diagnosis_name, "
                        + "prescription_id, prescription_name FROM "
                        + "Visitations NATURAL JOIN Prescriptions "
                        + "NATURAL JOIN Diagnoses WHERE patient = ?";

                patientSummary = con.prepareStatement(visitationQuery);
                patientSummary.setInt(1, Integer.parseInt(request.getParameter("username")));
                rs1 = patientSummary.executeQuery();

                numberOfVisitsQuery = "SELECT COUNT(*) AS total "
                        + "FROM Visitations WHERE patient = ?";
                numberOfVisits = con.prepareStatement(numberOfVisitsQuery);
                numberOfVisits.setInt(1, Integer.parseInt(request.getParameter("username")));
                rs2 = numberOfVisits.executeQuery();
                
                userid = Integer.parseInt(request.getParameter("username"));
            }

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
            request.setAttribute("resultName", PatientDAO.getName(userid));

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
