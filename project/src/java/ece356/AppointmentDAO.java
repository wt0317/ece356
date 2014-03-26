/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johnny
 */
public class AppointmentDAO {
    public static void insertAppointment(String startTime, String endTime, int doctor, int patient, int createdBy) throws ClassNotFoundException, SQLException {
        // TODO: query and put into list
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String insertDoctor = "INSERT INTO Appointments (startTime, endTime, doctor, patient, createdBy) VALUES (?,?,?,?,?)";
            stmt = con.prepareStatement(insertDoctor);
            stmt.setString(1, startTime);
            stmt.setString(2, endTime);
            stmt.setInt(3, doctor);
            stmt.setInt(4, patient);
            stmt.setInt(5, createdBy);
            
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
}
