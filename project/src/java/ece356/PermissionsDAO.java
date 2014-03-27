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
public class PermissionsDAO {
    public static void insertPermission(String employee, int patient)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertPermission = "INSERT INTO Permissions (employee, patient, accessibility) VALUES (?,?,?)";
            stmt = con.prepareCall(insertPermission);
            stmt.setString(1, employee);
            stmt.setInt(2, patient);
            stmt.setInt(3, 1);
            stmt.executeUpdate();
        } finally {
            if (stmt!= null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
