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
public class LicenseDAO {
    public static void insertLicense(String licenseID, String licenseIssue, String licenseExpiry)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertLicense = "INSERT INTO Licenses (license_id, license_issue_date, license_expiry_date) VALUES (?,?,?)";
            stmt = con.prepareStatement(insertLicense);
            stmt.setString(1, licenseID);
            stmt.setString(2, licenseIssue);
            stmt.setString(3, licenseExpiry);
            
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
