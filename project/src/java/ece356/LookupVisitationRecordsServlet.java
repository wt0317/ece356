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
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class LookupVisitationRecordsServlet extends HttpServlet {

    public Timestamp getTimestamp(HttpServletRequest request, String name) {
        Timestamp timestamp;
        String test = request.getParameter(name).replace("T", " ");
        String test2 = test.concat(":00");
        timestamp = Timestamp.valueOf(test2);
        return timestamp;
    }

    public int checkTimestamps(HttpServletRequest request, HttpServletResponse response, String start, String end, String errorMessage1, String errorMessage2, String errorMessage3)
            throws ServletException, IOException {
        String url;
        Timestamp timestampStart;
        Timestamp timestampEnd;

        if (!request.getParameter(start).equals("") && request.getParameter(end).equals("")) {
            //Error - Missing Time Scheduled 2
            //request.setAttribute("error", "true");
            //request.setAttribute("errorMessage", errorMessage1);
            //url = "/searchVisitationRecords.jsp";
            //getServletContext().getRequstDispatcher(url).forward(request, response);
            System.out.println("HIT!");
            return 1;
        }
        if (!request.getParameter(end).equals("") && request.getParameter(start).equals("")) {
            //request.setAttribute("error", "true");
            //request.setAttribute("errorMessage", errorMessage2);
            //url = "/searchVisitationRecords.jsp";
            //getServletContext().getRequestDispatcher(url).forward(request, response);
            return 1;
        }

        if (!request.getParameter(start).equals("") && !request.getParameter(end).equals("")) {
            //Get timestamps since we know they both contain data
            timestampStart = getTimestamp(request, start);
            timestampEnd = getTimestamp(request, end);

            //Error - Time Scheduled 1 is after Time Scheduled 2
            if (timestampStart.getTime() >= timestampEnd.getTime()) {
                //request.setAttribute("error", "true");
                //request.setAttribute("errorMessage", errorMessage3);
                //url = "/searchVisitationRecords.jsp";
                //getServletContext().getRequestDispatcher(url).forward(request, response);
                return 1;
            }
        }
        return 0;
    }

    public int getID(HttpServletRequest request, HttpServletResponse response, String selectCriteria, String table, String column, String search)
            throws SQLException, ServletException, IOException {
        PreparedStatement stmt = null;
        String query = "SELECT $criteria FROM $table WHERE $column = ?";
        PrintWriter out = response.getWriter();
        String url;
        int ID = -1;
        Connection con = null;
        try {

            con = DBAO.getConnection();
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
        HttpSession session = request.getSession();

        String role = ((User) session.getAttribute("userObject")).getRole();
        int username = ((User) session.getAttribute("userObject")).getUsername();
        String url = null;

        if (role.equals("Patient")) {
            try {
                VisitationDAOResult visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForPatient(username);
                request.setAttribute("columnNames", visitationDAOResult.getColumnNames());
                request.setAttribute("records", visitationDAOResult.getVisitationRecords());
                request.setAttribute("count", visitationDAOResult.getCount());
                request.setAttribute("status", "Valid");
                url = "/lookupVisitationRecord.jsp";
            } catch (Exception e) {
                request.setAttribute("exception", e);
                url = "/error.jsp";
            } finally {
                getServletContext().getRequestDispatcher(url).forward(request, response);
                out.close();
            }
        }

        if (role.equals("Doctor")) {

            String patientName = "";
            int patientUsername = -1;
            int diagnosisID = -1;
            int procedureID = -1;
            int prescriptionID = -1;
            int surgeryID = -1;
            String comments = "";
            String revisionComments = "";
            Timestamp timeScheduled1 = null;
            Timestamp timeScheduled2 = null;
            Timestamp timeStart1 = null;
            Timestamp timeStart2 = null;
            Timestamp timeEnd1 = null;
            Timestamp timeEnd2 = null;
            Timestamp timeCreation1 = null;
            Timestamp timeCreation2 = null;
            int flag0, flag1, flag2, flag3 = 0;

            try {

                VisitationDAOResult visitationDAOResult = null;

                //List patients based on search criteria
                if (request.getParameter("button") != null && request.getParameter("button").equals("search")) {

                    //Gather criteria and convert to id if necessary
                    //Get patient name
                    if (!request.getParameter("patientName").equals("")) {
                        patientName = request.getParameter("patientName");
                        System.out.println(patientName);
                    }

                    if (!request.getParameter("patientUsername").equals("")) {
                        patientUsername = Integer.parseInt(request.getParameter("patientUsername"));
                        System.out.println(patientUsername);
                    }

                    if (!request.getParameter("diagnosisName").equals("Any")) {
                        diagnosisID = getID(request, response, "diagnosis_id", "Diagnoses", "diagnosis_name", request.getParameter("diagnosisName"));
                        System.out.println("Diagnosis ID: " + diagnosisID);
                    }
                    if (!request.getParameter("procedureName").equals("Any")) {
                        procedureID = getID(request, response, "procedure_id", "Procedures", "procedure_name", request.getParameter("procedureName"));
                        System.out.println("Procedure ID: " + procedureID);
                    }
                    if (!request.getParameter("prescriptionName").equals("Any")) {
                        prescriptionID = getID(request, response, "prescription_id", "Prescriptions", "prescription_name", request.getParameter("prescriptionName"));
                        System.out.println("Prescription ID: " + prescriptionID);
                    }
                    if (!request.getParameter("surgeryName").equals("Any")) {
                        surgeryID = getID(request, response, "surgery_id", "Surgeries", "surgery_name", request.getParameter("surgeryName"));
                        System.out.println("Surgery ID: " + surgeryID);
                    }
                    if (!request.getParameter("comments").equals("")) {
                        comments = request.getParameter("comments");
                        System.out.println(comments);
                    }
                    if (!request.getParameter("revisionComments").equals("")) {
                        revisionComments = request.getParameter("revisionComments");
                        System.out.println(revisionComments);
                    }

                    //TIMESTAMP ERROR CHECKING
                    //Perform 3 checks for Time Scheduled Range
                    //Check Time Scheduled 2 is not "" if Time Scheduled 1 exists
                    //Check Time Scheduled 1 is not "" if Time Scheduled 2 exists
                    //Check Time Scheduled 1 is before Time Scheduled 2 
                    flag0 = checkTimestamps(request, response, "timeScheduled1", "timeScheduled2", "Time Scheduled field 2 is missing!", "Time Scheduled field 1 is missing!", "Time Scheduled field 1 is after or the same as Time Scheduled field 2!");
                    flag1 = checkTimestamps(request, response, "timeStart1", "timeStart2", "Start Time field 2 is missing!", "Start Time field 1 is missing!", "Start Time field 1 is after or the same as Start Time field 2!");
                    flag2 = checkTimestamps(request, response, "timeEnd1", "timeEnd2", "End Time field 2 is missing!", "End Time field 1 is missing!", "End Time field 1 is after or the same as End Time field 2!");
                    flag3 = checkTimestamps(request, response, "timeCreation1", "timeCreation2", "Creation Time field 1 is missing!", "Creation Time field 2 is missing!", "Creation Time field 1 is after or the same as Creation Time field 2!");

                    //Error
                    if (flag0 == 1 || flag1 == 1 || flag2 == 1 || flag3 == 1) {
                        url = "/searchVisitationRecords.jsp";
                        request.setAttribute("error", "true");
                        request.setAttribute("errorMessage", "Date range incorrect.");
                        System.out.println("Error reached!");
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                        return;
                    }

                    //All good, continue to get timestamps
                    if (!request.getParameter("timeScheduled1").equals("") && !request.getParameter("timeScheduled2").equals("")) {
                        timeScheduled1 = getTimestamp(request, "timeScheduled1");
                        timeScheduled2 = getTimestamp(request, "timeScheduled2");
                        System.out.println(1);
                    }

                    if (!request.getParameter("timeEnd1").equals("") && !request.getParameter("timeEnd2").equals("")) {
                        timeEnd1 = getTimestamp(request, "timeEnd1");
                        timeEnd2 = getTimestamp(request, "timeEnd2");
                        System.out.println(2);
                    }

                    if (!request.getParameter("timeStart1").equals("") && !request.getParameter("timeStart2").equals("")) {
                        timeStart1 = getTimestamp(request, "timeStart1");
                        timeStart2 = getTimestamp(request, "timeStart2");
                        System.out.println(3);
                    }

                    if (!request.getParameter("timeCreation1").equals("") && !request.getParameter("timeCreation2").equals("")) {
                        timeCreation1 = getTimestamp(request, "timeCreation1");
                        timeCreation2 = getTimestamp(request, "timeCreation2");
                        System.out.println(4);
                    }

                    System.out.println("ABC");
                    if (timeScheduled1 != null) {
                        System.out.println(timeScheduled1.toString());
                    }
                    if (timeScheduled2 != null) {
                        System.out.println(timeScheduled2.toString());
                    }
                    if (timeStart1 != null) {
                        System.out.println(timeStart1.toString());
                    }
                    if (timeStart2 != null) {
                        System.out.println(timeStart2.toString());
                    }
                    if (timeEnd1 != null) {
                        System.out.println(timeEnd1.toString());
                    }
                    if (timeEnd2 != null) {
                        System.out.println(timeEnd2.toString());
                    }
                    if (timeCreation1 != null) {
                        System.out.println(timeCreation1.toString());
                    }
                    if (timeCreation2 != null) {
                        System.out.println(timeCreation2.toString());
                    }
                    System.out.println("Done2");

                    visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForDoctorSEARCH(
                            username,
                            patientName,
                            patientUsername,
                            diagnosisID,
                            procedureID,
                            prescriptionID,
                            surgeryID,
                            comments,
                            revisionComments,
                            timeScheduled1,
                            timeScheduled2,
                            timeStart1,
                            timeStart2,
                            timeEnd1,
                            timeEnd2,
                            timeCreation1,
                            timeCreation2, request, response
                    );
                    System.out.println("Done3");
                } //List all patients
                else {
                    visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForDoctor(username);
                }

                request.setAttribute("columnNames", visitationDAOResult.getColumnNames());
                request.setAttribute("records", visitationDAOResult.getVisitationRecords());
                request.setAttribute("count", visitationDAOResult.getCount());
                request.setAttribute("status", "Valid");
                url = "/lookupVisitationRecord.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } catch (Exception e) {
                request.setAttribute("exception", e);
                url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } finally {
                out.close();
            }
        }

        if (role.equals("Staff")) {
            //List all visitation records that this staff has access to
            try {
                VisitationDAOResult visitationDAOResult = null;
                visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForStaff(username);
                request.setAttribute("columnNames", visitationDAOResult.getColumnNames());
                request.setAttribute("records", visitationDAOResult.getVisitationRecords());
                request.setAttribute("count", visitationDAOResult.getCount());
                request.setAttribute("status", "Valid");
                url = "/lookupVisitationRecord.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } catch (Exception e) {
                request.setAttribute("exception", e);
                url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } finally {
                out.close();
            }
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
