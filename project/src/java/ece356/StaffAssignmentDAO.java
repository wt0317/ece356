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
public class StaffAssignmentDAO {
    public static void insertStaffAssignment(boolean isStaff, int user, String[] assigned)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertAssignment = "INSERT INTO Staff_Assignments (staff, doctor) VALUES (?,?)";
            stmt = con.prepareCall(insertAssignment);
            for (String a : assigned) {
                if (isStaff) {
                    stmt.setInt(1, user);
                    stmt.setString(2, a);
                } else {
                    stmt.setString(1, a);
                    stmt.setInt(2, user);
                }
                stmt.executeUpdate();
            }
            
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
