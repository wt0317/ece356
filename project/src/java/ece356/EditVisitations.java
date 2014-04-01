/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author FireChemist
 */
public class EditVisitations extends HttpServlet {

    public Timestamp getTimestamp(HttpServletRequest request, String name) {
        Timestamp timestamp;
        String test = request.getParameter(name).replace("T", " ");
        timestamp = Timestamp.valueOf(test);
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
        VisitationDAOResult visitationDAOResult = null;
        Timestamp timeStart0;
        Timestamp timeCreation0;
        String url;
        Connection con = null;
        try {

            con = DBAO.getConnection();
            
            HttpSession session = request.getSession();
            
            timeStart0 = getTimestamp(request, "startTime");
            timeCreation0 = getTimestamp(request, "creationTime");

            visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForDoctorSEARCH(Integer.parseInt(request.getParameter("doctorUsername")), "", -1, -1, -1, -1, -1, "", "", null, null, timeStart0, timeStart0, null, null, timeCreation0, timeCreation0, request, response);

            //Save time scheduled
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Date date = format.parse(visitationDAOResult.getVisitationRecords().get(0).getTimeScheduled());
            String timeScheduled = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);

            //Save start time
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            date = format.parse(visitationDAOResult.getVisitationRecords().get(0).getStartTime());
            String timeStart = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
            
            //Save end time
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            date = format.parse(visitationDAOResult.getVisitationRecords().get(0).getEndTime());
            String timeEnd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);

            //Forward to edit.jsp
            request.setAttribute("timeScheduled", timeScheduled);
            request.setAttribute("startTime", timeStart);
            request.setAttribute("endTime", timeEnd);
            request.setAttribute("patientName", visitationDAOResult.getVisitationRecords().get(0).getPatientName());
            request.setAttribute("creationTime", visitationDAOResult.getVisitationRecords().get(0).getCreationTime());
            request.setAttribute("createdBy", visitationDAOResult.getVisitationRecords().get(0).getCreatedBy());
            request.setAttribute("procedureName", visitationDAOResult.getVisitationRecords().get(0).getProcedureName());
            request.setAttribute("diagnosisName", visitationDAOResult.getVisitationRecords().get(0).getDiagnosisName());
            request.setAttribute("prescriptionName", visitationDAOResult.getVisitationRecords().get(0).getPrescriptionName());
            request.setAttribute("surgeryName", visitationDAOResult.getVisitationRecords().get(0).getSurgeryName());
            request.setAttribute("freeformComments", visitationDAOResult.getVisitationRecords().get(0).getComments());
            request.setAttribute("revisionComments", visitationDAOResult.getVisitationRecords().get(0).getRevisionComments());

            PreparedStatement getDiagnoses = null;
            PreparedStatement getPrescriptions = null;
            PreparedStatement getSurgeries = null;
            PreparedStatement getProcedures = null;

            ResultSet resultDiagnoses;
            ResultSet resultPrescriptions;
            ResultSet resultSurgeries;
            ResultSet resultProcedures;

            List<String> diagnoses = new ArrayList<String>();
            List<String> prescriptions = new ArrayList<String>();
            List<String> surgeries = new ArrayList<String>();
            List<String> procedures = new ArrayList<String>();

            String diagnosesQuery = "SELECT diagnosis_name FROM Diagnoses";
            String prescriptionsQuery = "SELECT prescription_name FROM Prescriptions";
            String surgeriesQuery = "SELECT surgery_name FROM Surgeries";
            String proceduresQuery = "SELECT procedure_name FROM Procedures";

            getDiagnoses = con.prepareStatement(diagnosesQuery);
            resultDiagnoses = getDiagnoses.executeQuery();

            getPrescriptions = con.prepareStatement(prescriptionsQuery);
            resultPrescriptions = getPrescriptions.executeQuery();

            getSurgeries = con.prepareStatement(surgeriesQuery);
            resultSurgeries = getSurgeries.executeQuery();

            getProcedures = con.prepareStatement(proceduresQuery);
            resultProcedures = getProcedures.executeQuery();

            while (resultDiagnoses.next()) {
                diagnoses.add(resultDiagnoses.getString(1));
            }
            while (resultPrescriptions.next()) {
                prescriptions.add(resultPrescriptions.getString(1));
            }
            while (resultSurgeries.next()) {
                surgeries.add(resultSurgeries.getString(1));
            }
            while (resultProcedures.next()) {
                procedures.add(resultProcedures.getString(1));
            }

            session.setAttribute("diagnoses", diagnoses);
            session.setAttribute("prescriptions", prescriptions);
            session.setAttribute("surgeries", surgeries);
            session.setAttribute("procedures", procedures);

            url = "/editVisitation.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            url = "/editVisitation.jsp";
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
