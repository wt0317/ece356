/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Johnny
 */
public class AppointmentDAO {
    public static void insertAppointment(String title, String startTime, String endTime, int doctor, int patient, int createdBy) throws ClassNotFoundException, SQLException {
        // TODO: query and put into list
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String insertDoctor = "INSERT INTO Appointments (title, start_time, end_time, doctor, patient, created_by) VALUES (?,?,?,?,?,?)";
            stmt = con.prepareStatement(insertDoctor);
            stmt.setString(1, title);
            stmt.setString(2, startTime);
            stmt.setString(3, endTime);
            stmt.setInt(4, doctor);
            stmt.setInt(5, patient);
            stmt.setInt(6, createdBy);
            
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static List<Appointment> getAllAppointments() throws ClassNotFoundException, SQLException{
        List<Appointment> appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String selectAppointments = "SELECT * FROM Appointments WHERE doctor = '332231'";
            stmt = con.prepareStatement(selectAppointments);
            ResultSet resultAppointments = stmt.executeQuery();
            
            while(resultAppointments.next()) {                
                appointments.add(new Appointment(
                        resultAppointments.getString("title"),
                        resultAppointments.getString("start_time"),
                        resultAppointments.getString("end_time"),
                        resultAppointments.getInt("patient"),
                        resultAppointments.getInt("doctor"),
                        resultAppointments.getInt("created_by")
                ));
            }
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        return appointments;
    }
}
