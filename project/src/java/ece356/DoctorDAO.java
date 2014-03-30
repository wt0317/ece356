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
    
}
