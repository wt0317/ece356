/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sieyor
 */

public class PatientServlet extends HttpServlet {

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
                String url;
                try {    
                    //Create a new session object
                    
                    HttpSession session = request.getSession();

                    int username = Integer.parseInt(request.getParameter("username"));
                    String name = request.getParameter("name");
                    String address = request.getParameter("address");
                    String phoneNum = request.getParameter("phonenum");
                    String hin = request.getParameter("hin");
                    String sin = request.getParameter("sin");
                    
                    DBAO.updateUser(username, name, address, phoneNum, hin, sin);
                    
                    Directory user = new Directory();
                    user.setName(name);
                    user.setRole("patient");
                    //Set attributes of session object
                    
                    session.setAttribute("userObject", user);
                    
                    //Redirect to appropriate page
                    url="/welcome.jsp";       
                } catch (Exception e) {
                    request.setAttribute("exception", e);
                    url="/error.jsp";
                }

                getServletContext().getRequestDispatcher("/welcome.jsp").forward(request, response);
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