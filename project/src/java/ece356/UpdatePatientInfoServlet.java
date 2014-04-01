/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wilson
 */
public class UpdatePatientInfoServlet extends HttpServlet {

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
        Connection con = null;
        try {            
            con = DBAO.getConnection();
            
            String updateDirectoryQuery = "UPDATE Directory SET name=? WHERE username=?";
            PreparedStatement updateDirectoryStmt = con.prepareStatement(updateDirectoryQuery);
            updateDirectoryStmt.setString(1, request.getParameter("name"));
            updateDirectoryStmt.setString(2, request.getParameter("username"));
            updateDirectoryStmt.executeUpdate();
            
            String updatePatientQuery = "UPDATE Patients SET health_card=?,social_insurance_number=?,default_doctor=?,current_health=?,comment=? "
                    + "WHERE username=?";
            PreparedStatement updatePatientStmt = con.prepareStatement(updatePatientQuery);
            updatePatientStmt.setString(1, request.getParameter("healthCard"));
            updatePatientStmt.setString(2, request.getParameter("sin"));
            updatePatientStmt.setString(3, request.getParameter("doctorName"));
            updatePatientStmt.setString(4, request.getParameter("health"));
            updatePatientStmt.setString(5, request.getParameter("comment"));
            updatePatientStmt.setString(6, request.getParameter("username"));
            updatePatientStmt.executeUpdate();
            
            request.setAttribute("updated", true);
        }
        catch (Exception e) {
            request.setAttribute("exception", e);
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
        response.sendRedirect("GetPatientInfoServlet?updated=true&username=" + request.getParameter("username"));
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
