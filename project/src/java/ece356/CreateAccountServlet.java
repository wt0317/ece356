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
        Connection con = null;
        PreparedStatement stmt = null;
        String url = "/createAccount.jsp";
        try {
            try {
                int username = -1;
                
                con = DBAO.getConnection();

                String selectAllDoctors = "SELECT username, name FROM Directory WHERE role = 'Doctor'";
                stmt = con.prepareStatement(selectAllDoctors);
                ResultSet rsDoctors = stmt.executeQuery();
                HashMap<String,String> doctors = new HashMap<String,String>();
                while (rsDoctors.next()) {
                    doctors.put(rsDoctors.getString(1), rsDoctors.getString(2));
                }
                request.setAttribute("doctors", doctors);

                if (request.getParameter("submit") != null) {
                    String role = request.getParameter("role");

                    String insertDirectory = "INSERT INTO Directory"
                            + "(name, password, role, address, phone_number) VALUES"
                            + "(?,?,?,?,?)";                
                    stmt = con.prepareStatement(insertDirectory, Statement.RETURN_GENERATED_KEYS);                
                    stmt.setString(1, request.getParameter("name"));
                    stmt.setString(2, hashPW(request.getParameter("password")));
                    stmt.setString(3, role);
                    stmt.setString(4, request.getParameter("address"));
                    stmt.setString(5, request.getParameter("phone"));
                    
                    
                    if (stmt.executeUpdate() > 0) {
                        ResultSet pk = stmt.getGeneratedKeys();
                        if (pk.next()) {
                            username = pk.getInt(1);
                        }
                        if (role.equals("Patient")) {
                            String insertPatient = "INSERT INTO Patients"
                                    + "(username, health_card, social_insurance_number, default_doctor, current_health, comment) VALUES"
                                    + "(?,?,?,?,?,?)";                
                            stmt = con.prepareStatement(insertPatient);                
                            stmt.setInt(1, username);
                            stmt.setString(2, request.getParameter("healthCard"));
                            stmt.setString(3, request.getParameter("sin"));
                            stmt.setString(4, request.getParameter("doctor"));
                            stmt.setString(5, request.getParameter("health"));
                            stmt.setString(6, request.getParameter("comments"));                            
                        } else if (role.equals("Doctor")) {
                            String insertLicense = "INSERT INTO Licenses (license_id, license_issue_date, license_expiry_date) VALUES (?,?,?)";
                            stmt = con.prepareStatement(insertLicense);
                            stmt.setString(1, request.getParameter("license"));
                            stmt.setString(2, request.getParameter("licenseIssue"));
                            stmt.setString(3, request.getParameter("licenseExpiry"));
                            stmt.executeUpdate();
                            
                            String insertDoctor = "INSERT INTO Doctors (username, license_id, date_hired) VALUES (?,?,?)";
                            stmt = con.prepareStatement(insertDoctor);
                            stmt.setInt(1, username);
                            stmt.setString(2, request.getParameter("license"));
                            stmt.setString(3, request.getParameter("dateHired"));
                        }
                        stmt.executeUpdate();
                    }
                }
                
                request.setAttribute("username", username);  
            } catch (ClassNotFoundException e) {
                request.setAttribute("exception", e);
                url = "/error.jsp";
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        } catch (SQLException e) {
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
