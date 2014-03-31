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

  /**
   *
   * @param username
   * @param password
   * @return
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public static Object getUserInfo(int username, String password, String role) throws ClassNotFoundException, SQLException {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      if (role.equals("Patient")){
        Patient patient = new Patient();
        con = DBAO.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Patients as p WHERE p.username = '" + username + "'");
        rs = pst.executeQuery();
        if (rs.next()) {
          patient.setUsername(rs.getInt("username"));
          patient.setHealthCard(rs.getString("health_card"));
          patient.setSin(rs.getString("social_insurance_number"));
          patient.setDefaultDoctor(new Doctor(rs.getInt("default_doctor")));
        }
        return patient;
      } else if (role.equals("Doctor")){
        Doctor doctor = new Doctor();
        con = DBAO.getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Doctors as p WHERE p.username = '" + username + "'");
        rs = pst.executeQuery();
        if (rs.next()) {
          doctor.setUsername(rs.getInt("username"));
          doctor.setLicenseId(rs.getString("license_id"));
          doctor.setDateHired(rs.getString("date_hired"));
          System.out.println(doctor.getLicenseId());
          System.out.println(doctor.getDateHired());
        }
        return doctor;
      } else {
        return null;
      }
    } finally {
      if (stmt != null) {
        stmt.close();
      }
      if (con != null) {
        con.close();
      }
    }
  }

  public static void updateUser(int username, String name, String address, String phoneNum, String hin, String sin, String role) throws ClassNotFoundException, SQLException {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      con = DBAO.getConnection();
      PreparedStatement pst;
      if (role.equals("Patient")) {
        pst = con.prepareStatement("UPDATE Patients SET health_card = '" + hin + "', social_insurance_number = '" + sin + "' WHERE username = '" + username + "'");
        pst.executeUpdate();
      }
      pst = con.prepareStatement("UPDATE Directory SET name='" + name + "', address= '" + address + "', phone_number='" + phoneNum + "' WHERE username= '" + username + "'");
      pst.executeUpdate();
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
