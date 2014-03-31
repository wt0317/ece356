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
    
    public static void deleteAppointment(String startTime, int doctor) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            String deleteAppointment = "DELETE FROM Appointments WHERE start_time='" + startTime + "' AND doctor='" + doctor + "'";
            stmt = con.prepareStatement(deleteAppointment);
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static List<Appointment> getAllAppointments(String doctor) throws ClassNotFoundException, SQLException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String selectAppointments = "SELECT a.title, a.start_time, a.end_time, a.doctor, d.name, p.name, c.name FROM Appointments a JOIN Directory d ON a.doctor = d.username JOIN Directory p ON a.patient = p.username JOIN Directory c ON a.created_by = c.username WHERE doctor = '"+ doctor +"'";
            stmt = con.prepareStatement(selectAppointments);
            ResultSet resultAppointments = stmt.executeQuery();
            
            while(resultAppointments.next()) {                
                appointments.add(new Appointment(
                        resultAppointments.getString("a.title"),
                        resultAppointments.getString("a.start_time"),
                        resultAppointments.getString("a.end_time"),
                        resultAppointments.getInt("a.doctor"),
                        resultAppointments.getString("d.name"),
                        resultAppointments.getString("p.name"),
                        resultAppointments.getString("c.name")
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
    
    public static List<Appointment> getAllFutureAppointments(int patient) throws ClassNotFoundException, SQLException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String selectAppointments = "SELECT a.title, a.start_time, a.end_time, a.doctor, d.name, p.name, c.name FROM Appointments a JOIN Directory d ON a.doctor = d.username JOIN Directory p ON a.patient = p.username JOIN Directory c ON a.created_by = c.username WHERE patient = '"+ String.valueOf(patient) +"' AND start_time > NOW()";
            stmt = con.prepareStatement(selectAppointments);
            ResultSet resultAppointments = stmt.executeQuery();
            
            while(resultAppointments.next()) {                
                appointments.add(new Appointment(
                        resultAppointments.getString("a.title"),
                        resultAppointments.getString("a.start_time"),
                        resultAppointments.getString("a.end_time"),
                        resultAppointments.getInt("a.doctor"),
                        resultAppointments.getString("d.name"),
                        resultAppointments.getString("p.name"),
                        resultAppointments.getString("c.name")
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
