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
public class StaffAssignmentServlet extends HttpServlet {

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
        String url = "/staffAssignment.jsp";
        Connection con = null;
        try {
            HashMap<String,String> allStaff = UserDAO.getUsernameAndName("Staff");
            request.setAttribute("allStaff", allStaff);
            
            con = DBAO.getConnection();
            
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userObject");
            
            String assignmentQuery = "SELECT D.username, D.name FROM Staff_Assignments S, Directory D "
                    + "WHERE S.staff=D.username AND S.doctor=?";
            PreparedStatement assignmentStmt = con.prepareStatement(assignmentQuery);
            assignmentStmt.setInt(1, user.getUsername());
            ResultSet assignmentRS = assignmentStmt.executeQuery();
            
            HashMap<String,String> assignedStaff = new HashMap<String,String>();
            
            while (assignmentRS.next()) {
                assignedStaff.put(assignmentRS.getString("username"), assignmentRS.getString("name"));
            }
            
            request.setAttribute("assignedStaff", assignedStaff);
            
        } catch (Exception e) {
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
