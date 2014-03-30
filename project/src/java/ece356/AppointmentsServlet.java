/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johnny
 */
public class AppointmentsServlet extends HttpServlet {

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

        String url = "/appointments.jsp";

        try {
            //Create a new session object
            HttpSession session = request.getSession();
            //Redirect to appropriate page
            User user = ((User) session.getAttribute("userObject"));
            String role = user.getRole();

            if (role.equals("Doctor")) {
                Doctor appDoc = new Doctor(user.getUsername(), user.getName());
                request.setAttribute("appDoc", appDoc);
            } else if (role.equals("Staff")) {
                List<Doctor> doctors = StaffAssignmentDAO.getAllAssignedDoctors(user.getUsername());
                if(doctors.isEmpty()){
                    request.setAttribute("noAppDoc", true);
                } else{
                    if(request.getParameter("appDocUser") != null && request.getParameter("appDocName") != null){
                        Integer appDocUser = Integer.parseInt(request.getParameter("appDocUser"));
                        String appDocName = (String) request.getParameter("appDocName");
                        request.setAttribute("appDoc", new Doctor(appDocUser, appDocName, DoctorDAO.getAllPatients(appDocUser)));
                    } else {
                        request.setAttribute("appDoc", doctors.get(0));
                    }
                    request.setAttribute("docList", doctors);
                }
            } else {
                url = "/error.jsp";
            }
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
        String text = "";
        if ("true".equals(request.getParameter("insert"))) {
            try {
                HttpSession session = request.getSession();

                AppointmentDAO.insertAppointment(
                        request.getParameter("title"),
                        request.getParameter("start"),
                        request.getParameter("end"),
                        Integer.parseInt(request.getParameter("doctorID")),
                        Integer.parseInt(request.getParameter("patientID")),
                        ((User) session.getAttribute("userObject")).getUsername()
                );
                text = "Successfully added.";
            } catch (Exception e) {
                text = "Error has occured: " + e.getLocalizedMessage();
            } finally {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(text);
            }
        } else if ("true".equals(request.getParameter("select"))) {
            List<Appointment> appointments;
            try {
                appointments = AppointmentDAO.getAllAppointments(request.getParameter("doctor"));
                text += "{\"events\": [";
                for (int i = 0; i < appointments.size(); i++) {
                    text += appointments.get(i).toStringJSON();
                    if (i != appointments.size() - 1) {
                        text += ",";
                    }
                }
                text += "]}";
            } catch (Exception e) {
                text = "Error has occured: " + e.getLocalizedMessage();
            } finally {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(text);
            }
        } else if ("true".equals(request.getParameter("delete"))){
            try {
                AppointmentDAO.deleteAppointment(request.getParameter("startTime"), Integer.parseInt(request.getParameter("doctor")));
                text = "Successfully removed.";
            } catch (Exception e) {
                text = "Error has occured: " + e.getLocalizedMessage();
            } finally {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(text);
            }
        } else {
            processRequest(request, response);
        }
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
