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
 * @author rkn24
 */
public class DoctorSummaryServlet extends HttpServlet {

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

        /*Ability to monitor any doctor to determine how many patients he/she saw in
          a given time period, how many times a given patient was seen, what the 
          diagnosis/result was, any drugs prescribed, etc.
        */
      
        String url = "/doctorSummary.jsp";

        List<Doctor> listDoctorSummary = new ArrayList<Doctor>();
        PreparedStatement getDoctorSummary = null;
        PreparedStatement getPatientCount = null;
        PreparedStatement getDoctorPatientCount = null;
        boolean empty = true;
        int numPatients = 0;
        String doctorName = request.getParameter("doctorName");
        int doctorid = Integer.parseInt(request.getParameter("doctor"));
        Connection con = null;
        ResultSet rs0;
        ResultSet rs1;
        ResultSet rs2;
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String patientCountQuery = "";
        
        if(startDate != null && endDate != null && startDate != "" && endDate != ""){
          request.setAttribute("startDate", startDate);
          request.setAttribute("endDate", endDate);
          patientCountQuery 
                  = "SELECT COUNT(*) AS total FROM Visitations "
                  + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? AND start_time >= '"+startDate+" 00:00:00' AND end_time <= '"+endDate+" 00:00:00' )";
        }
        else{
          patientCountQuery 
                  = "SELECT COUNT(*) AS total FROM Visitations "
                  + "WHERE start_time in (SELECT DISTINCT start_time FROM Visitations WHERE doctor = ? AND patient = ? )";
        }

        try {
            con = DBAO.getConnection();

            //Lookup doctor by name
            if (!request.getParameterMap().containsKey("username")) {
                String doctorPatientCountQuery
                          = "SELECT COUNT(default_doctor) AS total FROM Patients "
                          + "WHERE default_doctor = ?";
                getDoctorPatientCount = con.prepareStatement(doctorPatientCountQuery);
                getDoctorPatientCount.setInt(1, doctorid);
                rs0 = getDoctorPatientCount.executeQuery();
                
                while(rs0.next()){
                  numPatients = rs0.getInt("total");
                }
                
                if(numPatients > 0){
                  String doctorSummaryQuery
                            = "SELECT username FROM Patients "
                            + "WHERE default_doctor = ?";
                  getDoctorSummary = con.prepareStatement(doctorSummaryQuery);
                  getDoctorSummary.setInt(1, doctorid);
                  rs1 = getDoctorSummary.executeQuery();

                  //There is at least one result
                  while (rs1.next()) {
                      if(empty){
                        empty = false;
                      }
                      Doctor d = new Doctor();
                      d.setUsername(rs1.getInt(1));                           
                      getPatientCount = con.prepareStatement(patientCountQuery);
                      getPatientCount.setInt(1, doctorid);
                      getPatientCount.setInt(2, rs1.getInt(1));
                      rs2 = getPatientCount.executeQuery();
                      while(rs2.next()){
                          d.setPatientCount(rs2.getInt("total"));
                      }
                       //d.setPatientCount(1);
                      listDoctorSummary.add(d);
                  }
                }
                
                request.setAttribute("queryDone", true);
                if (empty) {
                    request.setAttribute("doctor", doctorid);
                    request.setAttribute("doctorName", doctorName);
                    request.setAttribute("listDoctorSummaryEmpty", true);
                    request.setAttribute("numPatients", numPatients);
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
                else{
                    request.setAttribute("doctor", doctorid);
                    request.setAttribute("doctorName", doctorName);
                    request.setAttribute("listDoctorSummaryEmpty", false);
                    request.setAttribute("numPatients", numPatients);
                    request.setAttribute("listDoctorSummary", listDoctorSummary);
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/error.jsp";
        }

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
