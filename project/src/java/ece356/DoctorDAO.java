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
import java.util.List;

/**
 *
 * @author Wilson
 */
public class DoctorDAO {
    public static void insertDoctor(int username, String license, String dateHired)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertDoctor = "INSERT INTO Doctors (username, license_id, date_hired) VALUES (?,?,?)";
            stmt = con.prepareStatement(insertDoctor);
            stmt.setInt(1, username);
            stmt.setString(2, license);
            stmt.setString(3, dateHired);
            
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
    
    public static List<Patient> getAllPatients(int doctor) 
        throws ClassNotFoundException, SQLException {
        List<Patient> patients = new ArrayList<Patient>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = DBAO.getConnection();
            
            String selectPatients = "SELECT Permissions.patient, Directory.name FROM Permissions INNER JOIN Directory ON Permissions.patient=Directory.username WHERE employee = '"+ doctor +"'";
            stmt = con.prepareStatement(selectPatients);
            ResultSet resultPatients = stmt.executeQuery();
            
            while(resultPatients.next()) {                
                patients.add(new Patient(resultPatients.getInt("patient"), resultPatients.getString("name")));
            }
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return patients;
    }
}
