/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Wilson
 */
public class PatientDAO {
    public static void insertPatient(int username, String healthCard, String sin, String doctor, String health, String comments)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertPatient = "INSERT INTO Patients"
                    + "(username, health_card, social_insurance_number, default_doctor, current_health, comment) VALUES"
                    + "(?,?,?,?,?,?)";                
            stmt = con.prepareStatement(insertPatient);                
            stmt.setInt(1, username);
            stmt.setString(2, healthCard);
            stmt.setString(3, sin);
            stmt.setString(4, doctor);
            stmt.setString(5, health);
            stmt.setString(6, comments);
            
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
