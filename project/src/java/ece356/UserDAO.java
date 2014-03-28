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
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Wilson
 */
public class UserDAO {
    public static int insertUser(String name, String password, String role, String address, String phone)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertDirectory = "INSERT INTO Directory"
                    + "(name, password, role, address, phone_number) VALUES"
                    + "(?,?,?,?,?)";                
            stmt = con.prepareStatement(insertDirectory, Statement.RETURN_GENERATED_KEYS);                
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.setString(4, address);
            stmt.setString(5, phone);
            
            if (stmt.executeUpdate() > 0) {
                ResultSet primaryKey = stmt.getGeneratedKeys();
                if (primaryKey.next()) {
                    return primaryKey.getInt(1);
                }
            }
            return -1;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static HashMap<String,String> getUsernameAndName(String role)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String selectAll = "SELECT username, name FROM Directory WHERE role = ?";
            stmt = con.prepareStatement(selectAll);
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            HashMap<String,String> result = new HashMap<String,String>();
            while (rs.next()) {
                result.put(rs.getString(1), rs.getString(2));
            }
            
            return result;
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
