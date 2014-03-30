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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johnny
 */
class StaffAssignmentDAO {
    public static List<Doctor> getAllAssignedDoctors(int staff) 
                throws ClassNotFoundException, SQLException {
        List<Doctor> doctors = new ArrayList<Doctor>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String selectDoctors = "SELECT Staff_Assignments.doctor, Directory.name FROM Staff_Assignments INNER JOIN Directory ON Staff_Assignments.doctor=Directory.username WHERE staff = '"+ staff +"'";
            stmt = con.prepareStatement(selectDoctors);
            ResultSet resultDoctors = stmt.executeQuery();
            
            while(resultDoctors.next()) {                
                doctors.add(new Doctor(resultDoctors.getInt("doctor"), resultDoctors.getString("name"), DoctorDAO.getAllPatients(resultDoctors.getInt("doctor"))));
            }
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return doctors;
    }
}