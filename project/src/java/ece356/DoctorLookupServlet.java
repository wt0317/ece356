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
public class DoctorLookupServlet extends HttpServlet {

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

        String url = "/lookupDoctorSummary.jsp";

        List<Doctor> listDoctorSummary = new ArrayList<Doctor>();
        PreparedStatement getDoctorSummary = null;
        PreparedStatement getPatientCount = null;
        boolean empty = true;
        Connection con = null;
        ResultSet rs;
        ResultSet rs2; 

        try {
            con = DBAO.getConnection();

            //Lookup doctor by name
            if (!request.getParameterMap().containsKey("username")) {
                String doctorQuery
                        = "SELECT username, name FROM Directory "
                        + "WHERE name LIKE ? and role = 'doctor'";

                getDoctorSummary = con.prepareStatement(doctorQuery);
                getDoctorSummary.setString(1, "%"+request.getParameter("name")+"%");
                rs = getDoctorSummary.executeQuery();

                //There is at least one result
                while (rs.next()) {
                    empty = false;
                    Doctor d = new Doctor();
                    d.setUsername(rs.getInt(1));
                    d.setName(rs.getString(2));
                    
                   String patientCountQuery
                          = "SELECT COUNT(default_doctor) AS total FROM Patients "
                          + "WHERE default_doctor = ?";
                    getPatientCount = con.prepareStatement(patientCountQuery);
                    getPatientCount.setInt(1, rs.getInt(1));
                    rs2 = getPatientCount.executeQuery();
                    while(rs2.next()){
                        d.setPatientCount(rs2.getInt("total"));
                    }
                    
                    listDoctorSummary.add(d);
                }
                
                request.setAttribute("queryDone", true);
                if (empty) {
                    request.setAttribute("listDoctorEmpty", true);
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
                else{
                    request.setAttribute("listDoctorEmpty", false);
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
