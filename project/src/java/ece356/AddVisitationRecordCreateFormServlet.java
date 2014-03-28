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
public class AddVisitationRecordCreateFormServlet extends HttpServlet {

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
        Connection con = null;

        //Actions
        //1. Query list of diagnoses
        //2. Query list of prescriptions
        //3. Query list of surgeries
        
        try {
            
            con = DBAO.getConnection();
           
            HttpSession session = request.getSession();
            
            PreparedStatement getDiagnoses = null;
            PreparedStatement getPrescriptions = null;
            PreparedStatement getSurgeries = null;
            PreparedStatement getProcedures=null;

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
            
            getServletContext().getRequestDispatcher(url).forward(request, response);
            
            
        } 
        catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }
        
        finally {
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
