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
    public static ResultSet findDoctor(String name)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBAO.getConnection();
            
            String doctorQuery = "SELECT username FROM Directory WHERE name LIKE ? and role = 'doctor'";
            stmt = con.prepareStatement(doctorQuery);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return rs;
    }
    private Integer username;
    public Doctor() {
    }

    public Doctor(Integer username) {
        this.username = username;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

}
