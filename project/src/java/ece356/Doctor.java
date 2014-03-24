/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author Rakin
 */
public class Doctor {
    public static void findDoctor(String name)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String doctorQuery = "SELECT username FROM Directory WHERE name LIKE ? and role = 'doctor'";
            stmt = con.prepareStatement(doctorQuery);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
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
