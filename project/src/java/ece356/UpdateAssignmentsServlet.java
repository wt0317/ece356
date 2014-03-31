/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wilson
 */
public class UpdateAssignmentsServlet extends HttpServlet {

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
        PreparedStatement stmt = null;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userObject");
            
            con = DBAO.getConnection();
            
            String[] addStaff = {};
            if (request.getParameterValues("add[]") != null)
                addStaff = request.getParameterValues("add[]");
            for (String s : addStaff) {
                String insertAssignment = "INSERT INTO Staff_Assignments (staff, doctor) VALUES (?,?)";
                stmt = con.prepareCall(insertAssignment);
                stmt.setString(1, s);
                stmt.setInt(2, user.getUsername());
                stmt.executeUpdate();
            }
            
            String[] removeStaff = {};
            if (request.getParameterValues("remove[]") != null)
                removeStaff = request.getParameterValues("remove[]");
            for (String s : removeStaff) {
                String deleteAssignment = "DELETE FROM Staff_Assignments WHERE staff=? AND doctor=?";
                stmt = con.prepareCall(deleteAssignment);
                stmt.setString(1, s);
                stmt.setInt(2, user.getUsername());
                stmt.executeUpdate();
            }
            
            response.getWriter().print("{\"success\": true}");
            response.getWriter().flush();
        } catch (Exception e) {
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
