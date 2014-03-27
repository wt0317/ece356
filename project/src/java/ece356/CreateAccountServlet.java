/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wilson
 */
public class CreateAccountServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateAccountServlet.class.getName());
    
    public static String hashPW(String password) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
             logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return generatedPassword;
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
        String url = "/createAccount.jsp";
        try {
            int username = -1;

            HashMap<String,String> doctors = UserDAO.getDoctorsUsernameAndName();
            request.setAttribute("doctors", doctors);

            if (request.getParameter("submit") != null) {
                String role = request.getParameter("role");

                username = UserDAO.insertUser(request.getParameter("name"), hashPW(request.getParameter("password")), role, request.getParameter("address"), request.getParameter("phone"));

                if (username != -1) {
                    if (role.equals("Patient")) {
                        PatientDAO.insertPatient(username, request.getParameter("healthCard"), request.getParameter("sin"), request.getParameter("doctor"), request.getParameter("health"), request.getParameter("comments"));
                    } else if (role.equals("Doctor")) {
                        LicenseDAO.insertLicense(request.getParameter("license"), request.getParameter("licenseIssue"), request.getParameter("licenseExpiry"));   
                        DoctorDAO.insertDoctor(username, request.getParameter("license"), request.getParameter("dateHired"));
                    } else if (role.equals("Staff")) {
                        StaffAssignmentDAO.insertStaffAssignment(true, username, request.getParameterValues("assignedDoctors"));
                    }
                }
            }

            request.setAttribute("username", username);  
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
