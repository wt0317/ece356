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
public class LookupVisitationRecordsServlet extends HttpServlet {

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
             
            try {
                VisitationDAOResult visitationDAOResult = VisitationDAO.getPatientVisitationRecordsForDoctor(username);
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
