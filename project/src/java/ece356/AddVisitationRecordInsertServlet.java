/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.Date;
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
public class AddVisitationRecordInsertServlet extends HttpServlet {

    public int getID(HttpServletRequest request, HttpServletResponse response, Connection con, String selectCriteria, String table, String column, String search)
            throws SQLException, ServletException, IOException {
        PreparedStatement stmt = null;
        String query = "SELECT $criteria FROM $table WHERE $column = ?";
        PrintWriter out = response.getWriter();
        String url;
        int ID = -1;

        try {
            String modifiedQuery1 = query.replace("$criteria", selectCriteria);
            String modifiedQuery2 = modifiedQuery1.replace("$table", table);
            String modifiedQuery3 = modifiedQuery2.replace("$column", column);
            stmt = con.prepareStatement(modifiedQuery3);
            stmt.setString(1, search);

            ResultSet rs = stmt.executeQuery();

            assert (rs.next());
            if (rs.next()) {
                ID = rs.getInt(1);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
        return ID;
    }

    public Timestamp getTimestamp(HttpServletRequest request, String name) {
        Timestamp timestamp;
        String test = request.getParameter(name).replace("T", " ");
        String test2 = test.concat(":00");
        timestamp = Timestamp.valueOf(test2);
        return timestamp;
    }

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = "/addVisitationRecord.jsp";
        List<Integer> listPatientID = new ArrayList<Integer>();
        PreparedStatement getPatientID = null;
        PreparedStatement getProcedureID = null;
        ResultSet rsPatientID;
        ResultSet rsProcedureID;
        int procedureID = -1;
        int diagnosisID = -1;
        int prescriptionID = -1;
        int surgeryID = -1;
        boolean empty = true;
        Connection con = null;
        int countPatient = 0;
        int doctorUsername;
        Timestamp timestamp = null;

        try {

            con = DBAO.getConnection();

            HttpSession session = request.getSession();

            //Insert patient by name
            if (!request.getParameterMap().containsKey("username")) {

                //Make sure patient name is unique
                String patientQuery
                        = "SELECT username FROM Directory "
                        + "WHERE name = ? and role = 'patient'";

                getPatientID = con.prepareStatement(patientQuery);
                getPatientID.setString(1, request.getParameter("name"));
                rsPatientID = getPatientID.executeQuery();

                //There is at least one result
                while (rsPatientID.next()) {
                    empty = false;
                    countPatient++;
                    listPatientID.add(rsPatientID.getInt("username"));
                }

                //Force user to input patient ID
                if (countPatient > 1) {
                    request.setAttribute("error", "Duplicate");
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("listPatientID", listPatientID);
                    request.setAttribute("timeScheduled", request.getParameter("timeScheduled"));
                    request.setAttribute("startTime", request.getParameter("startTime"));
                    request.setAttribute("endTime", request.getParameter("endTime"));
                    request.setAttribute("freeformComments", request.getParameter("freeformComments"));
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //No result
                if (empty) {
                    request.setAttribute("error", "Invalid");
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //Get doctor username
                doctorUsername = ((User) session.getAttribute("userObject")).getUsername().intValue();

                //Get procedure_id
                procedureID = getID(request, response, con, "procedure_id", "Procedures", "procedure_name", request.getParameter("procedure"));

                //Get diagnosis_id
                diagnosisID = getID(request, response, con, "diagnosis_id", "Diagnoses", "diagnosis_name", request.getParameter("diagnosis"));

                //Get prescription_id
                prescriptionID = getID(request, response, con, "prescription_id", "Prescriptions", "prescription_name", request.getParameter("prescription"));

                //Get surgery_id
                surgeryID = getID(request, response, con, "surgery_id", "Surgeries", "surgery_name", request.getParameter("surgery"));

                //Convert time scheduled to Timestamp
                Timestamp timeScheduled = getTimestamp(request, "timeScheduled");

                //Convert start time to Timestamp
                Timestamp startTime = getTimestamp(request, "startTime");

                //Convert end time to Timestamp
                Timestamp endTime = getTimestamp(request, "endTime");

                //Test start time is before end time
                if (startTime.getTime() >= endTime.getTime()) {
                    request.setAttribute("error", "invalidEndTime");
                    request.setAttribute("timeScheduled", request.getParameter("timeScheduled"));
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("freeformComments", request.getParameter("freeformComments"));
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //Perform insert into Visitation
                assert (listPatientID.size() == 1);
                VisitationDAO.insertVisitation(
                        listPatientID.get(0).intValue(),
                        doctorUsername, procedureID, diagnosisID, prescriptionID,
                        timeScheduled, startTime, endTime, doctorUsername, surgeryID,
                        request.getParameter("freeformComments"), "");
  
                request.setAttribute("success", "Added visitation record!");
                url = "/lookupVisitationRecord.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);

            } //Insert patient by username
            else {
                //Get doctor username
                doctorUsername = ((User) session.getAttribute("userObject")).getUsername().intValue();

                //Get procedure_id
                procedureID = getID(request, response, con, "procedure_id", "Procedures", "procedure_name", request.getParameter("procedure"));

                //Get diagnosis_id
                diagnosisID = getID(request, response, con, "diagnosis_id", "Diagnoses", "diagnosis_name", request.getParameter("diagnosis"));

                //Get prescription_id
                prescriptionID = getID(request, response, con, "prescription_id", "Prescriptions", "prescription_name", request.getParameter("prescription"));

                //Get surgery_id
                surgeryID = getID(request, response, con, "surgery_id", "Surgeries", "surgery_name", request.getParameter("surgery"));

                //Convert time scheduled to Timestamp
                Timestamp timeScheduled = getTimestamp(request, "timeScheduled");

                //Convert start time to Timestamp
                Timestamp startTime = getTimestamp(request, "startTime");

                //Convert end time to Timestamp
                Timestamp endTime = getTimestamp(request, "endTime");

                //Test start time is before end time
                if (startTime.getTime() >= endTime.getTime()) {
                    request.setAttribute("error", "invalidEndTime");
                    request.setAttribute("timeScheduled", request.getParameter("timeScheduled"));
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("freeformComments", request.getParameter("freeformComments"));
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                    return;
                }

                //Perform insert into Visitation
                VisitationDAO.insertVisitation(
                        Integer.valueOf(request.getParameter("username")),
                        doctorUsername, procedureID, diagnosisID, prescriptionID,
                        timeScheduled, startTime, endTime, doctorUsername, surgeryID,
                        request.getParameter("freeformComments"), "");

                request.setAttribute("success", "Added visitation record!");
                url = "/lookupVisitationRecord.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } finally {
            out.close();
        }
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
