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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wilson
 */
public class VisitationChangelogServlet extends HttpServlet {

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
        String url = "/visitationChangelog.jsp";
        Connection con = null;
        List<Visitation> visitations = new ArrayList<Visitation>();
        try {            
            con = DBAO.getConnection();

            String getPatientsQuery = "select CONCAT_WS(',', patient, doctor, start_time) as pkey,\n" +
                "patient,doctor,procedure_id,diagnosis_id,prescription_id,time_scheduled,\n" +
                "start_time,end_time,creation_time,created_by,surgery_id,comments,revision_comments\n" +
                "from Visitations order by patient,doctor,start_time desc,creation_time desc";
            PreparedStatement getPatientsStmt = con.prepareStatement(getPatientsQuery);
            ResultSet rs = getPatientsStmt.executeQuery();
            String pkey = null;
            while (rs.next()) {
                Visitation visitation = new Visitation(rs.getInt("patient"),rs.getInt("doctor"),rs.getString("start_time"));
                if (pkey == null) {
                    pkey = rs.getString("pkey");
                    visitations.add(visitation);
                    continue;
                }
                if (rs.getString("pkey").equals(pkey)) {
//                    add to existing record                    
                    visitations.get(visitations.size()-1).addChangelog(visitation);
                } else {
//                    make new visitation record
                    pkey = rs.getString("pkey");
                    visitations.add(visitation);
                }
            }
            request.setAttribute("visitations", visitations);
        }
        catch (Exception e) {
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
